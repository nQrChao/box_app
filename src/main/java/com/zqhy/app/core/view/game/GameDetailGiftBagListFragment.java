package com.zqhy.app.core.view.game;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.adapter.abs.AbsAdapter;
import com.zqhy.app.adapter.abs.EmptyAdapter1;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.game.GameDataVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GetCardInfoVo;
import com.zqhy.app.core.data.model.game.detail.GameCardListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.community.comment.WriteCommentsFragment;
import com.zqhy.app.core.view.game.dialog.CardDialogHelper;
import com.zqhy.app.core.view.user.newvip.NewUserVipFragment;
import com.zqhy.app.core.view.user.welfare.MyCardListFragment;
import com.zqhy.app.core.vm.game.GameViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动列表
 */
public class GameDetailGiftBagListFragment extends BaseFragment<GameViewModel> {

    public static GameDetailGiftBagListFragment newInstance(int user_already_commented, String gamename, int gameid) {
        GameDetailGiftBagListFragment fragment = new GameDetailGiftBagListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("user_already_commented", user_already_commented);
        bundle.putInt("gameid", gameid);
        bundle.putString("gamename", gamename);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_GAME_DETAIL_STATE;
    }

    @Override
    protected String getStateEventTag() {
        return String.valueOf(gameid);
    }

    private int user_already_commented;
    private String gamename;
    private int gameid;
    private final int ACTION_WRITE_COMMENT           = 0x444;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_game_gift_bag_list_new;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private LinearLayout mLlTabNormalGift;
    private LinearLayout mLlTabRechargeGift;
    private TextView     mTvCgNormalGift;
    private TextView     mTvCgRechargeGift;
    private TextView     mTvNormalGiftCount;
    private TextView     mTvRechargeGiftCount;
    private TextView     mTvUserGift;
    private RecyclerView mRecyclerViewGift;

    private List<GameInfoVo.CardlistBean> cgNormalList   = new ArrayList<>();
    private List<GameInfoVo.CardlistBean> cgRechargeList = new ArrayList<>();
    private List<GameInfoVo.CardlistBean> cardList = new ArrayList<>();
    private CardListAdapter mAdapter;
    private EmptyAdapter1                 emptyAdapter;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        if (getArguments() != null) {
            user_already_commented = getArguments().getInt("user_already_commented");
            gamename = getArguments().getString("gamename");
            gameid = getArguments().getInt("gameid");
        }

        initActionBackBarAndTitle("领取礼包");

        mLlTabNormalGift = findViewById(R.id.ll_tab_normal_gift);
        mLlTabRechargeGift = findViewById(R.id.ll_tab_recharge_gift);
        mTvNormalGiftCount = findViewById(R.id.tv_normal_gift_count);
        mTvRechargeGiftCount = findViewById(R.id.tv_recharge_gift_count);
        mTvCgNormalGift = findViewById(R.id.tv_normal_gift);
        mTvCgRechargeGift = findViewById(R.id.tv_recharge_gift);
        mTvUserGift = findViewById(R.id.tv_user_gift);
        mRecyclerViewGift = findViewById(R.id.recyclerView_gift);

        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        layoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerViewGift.setNestedScrollingEnabled(false);
        mRecyclerViewGift.setLayoutManager(layoutManager);

        mAdapter = new CardListAdapter(_mActivity, cardList);
        List<EmptyDataVo> emptyDataVoList = new ArrayList<>();
        emptyDataVoList.add(new EmptyDataVo(R.mipmap.img_empty_data_2));
        emptyAdapter = new EmptyAdapter1(_mActivity, emptyDataVoList);

        mRecyclerViewGift.setAdapter(mAdapter);

        mTvUserGift.setOnClickListener(v -> {
            //查看我的礼包
            if (checkLogin()) {
                startFragment(new MyCardListFragment());
            }
        });

        mLlTabNormalGift.setOnClickListener(v -> {
            mTvCgNormalGift.setTextSize(18);
            mTvCgNormalGift.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            mTvCgRechargeGift.setTextSize(14);
            mTvCgRechargeGift.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

            cardList.clear();
            if (cgNormalList.size() > 0){
                cardList.addAll(cgNormalList);
                mRecyclerViewGift.setAdapter(mAdapter);
            }else {
                mRecyclerViewGift.setAdapter(emptyAdapter);
            }
        });
        mLlTabRechargeGift.setOnClickListener(v -> {
            mTvCgNormalGift.setTextSize(14);
            mTvCgNormalGift.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            mTvCgRechargeGift.setTextSize(18);
            mTvCgRechargeGift.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            cardList.clear();
            if (cgRechargeList.size() > 0){
                cardList.addAll(cgRechargeList);
                mRecyclerViewGift.setAdapter(mAdapter);
            }else {
                mRecyclerViewGift.setAdapter(emptyAdapter);
            }
        });
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        getNetWorkData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getNetWorkData();
    }


    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getNetWorkData();
    }

    private void getNetWorkData() {
        if (mViewModel != null) {
            mViewModel.getGameInfoPartFl(gameid, new OnBaseCallback<GameDataVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                }

                @Override
                public void onSuccess(GameDataVo gameDataVo) {
                    if (gameDataVo != null) {
                        if (gameDataVo.isStateOK()) {
                            GameInfoVo infoVo = gameDataVo.getData();
                            if (infoVo != null) {
                                GameCardListVo gameCardListVo = infoVo.getGameCardListVo();
                                List<GameInfoVo.CardlistBean> cardlist = gameCardListVo.getCardlist();
                                cgNormalList.clear();
                                cgRechargeList.clear();
                                if (cardlist != null && !cardlist.isEmpty()) {
                                    for (GameInfoVo.CardlistBean cardlistBean : cardlist) {
                                        if (cardlistBean.isCommentGift()) {
                                            cgNormalList.add(0, cardlistBean);//点评礼包
                                            continue;
                                        }
                                        if (cardlistBean.getCard_type() == 1) {
                                            cgNormalList.add(cardlistBean);
                                        } else if (cardlistBean.getCard_type() == 2) {
                                            cgRechargeList.add(cardlistBean);
                                        }
                                    }
                                }
                                mTvNormalGiftCount.setText("" + cgNormalList.size());
                                mTvRechargeGiftCount.setText("" + cgRechargeList.size());
                                cardList.clear();
                                if (cgNormalList.size() > 0){
                                    cardList.addAll(cgNormalList);
                                    mRecyclerViewGift.setAdapter(mAdapter);
                                }else if (cgRechargeList.size() > 0){
                                    cardList.addAll(cgRechargeList);
                                    mRecyclerViewGift.setAdapter(mAdapter);
                                }else {
                                    mRecyclerViewGift.setAdapter(emptyAdapter);
                                }
                            }
                        } else {
                            Toaster.show( gameDataVo.getMsg());
                            //ToastT.error(_mActivity, gameDataVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    public void showGiftDetail(GameInfoVo.CardlistBean cardlistBean) {
        CustomDialog cardDetailDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_card_detail, null),
                ScreenUtils.getScreenWidth(_mActivity),
                WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

        TextView tvGiftContent = cardDetailDialog.findViewById(R.id.tv_gift_content);
        TextView tvGiftUsage = cardDetailDialog.findViewById(R.id.tv_gift_usage);
        TextView tvGiftTime = cardDetailDialog.findViewById(R.id.tv_gift_time);
        TextView mTvGiftRequirement = cardDetailDialog.findViewById(R.id.tv_gift_requirement);
        LinearLayout mLlGiftRequirement = cardDetailDialog.findViewById(R.id.ll_gift_requirement);

        TextView tvClose2 = cardDetailDialog.findViewById(R.id.tv_close);
        tvClose2.setOnClickListener((v) -> {
            if (cardDetailDialog != null && cardDetailDialog.isShowing()) {
                cardDetailDialog.dismiss();
            }
        });

        tvGiftContent.setText(cardlistBean.getCardcontent());
        if (!TextUtils.isEmpty(cardlistBean.getCardusage())) {
            tvGiftUsage.setText(cardlistBean.getCardusage());
        } else {
            tvGiftUsage.setText("请在游戏内兑换使用");
        }
        if (cardlistBean.isRechargeGift()) {
            mLlGiftRequirement.setVisibility(View.VISIBLE);
            mTvGiftRequirement.setText(cardlistBean.getGiftRequirement());
        } else {
            mLlGiftRequirement.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(cardlistBean.getYouxiaoqi())) {
            tvGiftTime.setText(cardlistBean.getYouxiaoqi());
        } else {
            tvGiftTime.setText("无限制");
        }

        cardDetailDialog.show();
    }


    /**
     * 跳转点评页面
     */
    public void getCommentGift(int cardid){
        if (checkLogin()) {
            if (user_already_commented != 1) {
                startForResult(WriteCommentsFragment.newInstance(String.valueOf(gameid), gamename), ACTION_WRITE_COMMENT);
            }else{
                getCardInfo(cardid);
            }
        }
    }

    private CardDialogHelper cardDialogHelper;
    public void getCardInfo(int cardid) {
        if (mViewModel != null) {
            mViewModel.getCardInfo(gameid, cardid, new OnBaseCallback<GetCardInfoVo>() {
                @Override
                public void onSuccess(GetCardInfoVo getCardInfoVo) {
                    if (getCardInfoVo != null) {
                        if (getCardInfoVo.isStateOK()) {
                            if (getCardInfoVo.getData() != null) {
                                if (cardDialogHelper == null) {
                                    cardDialogHelper = new CardDialogHelper(GameDetailGiftBagListFragment.this);
                                }
                                cardDialogHelper.showGiftDialog(getCardInfoVo.getData().getCard(), false, "");
                                refreshCardList();
                            }
                        } else {
                            Toaster.show( getCardInfoVo.getMsg());
                            //ToastT.error(_mActivity, getCardInfoVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    private CustomDialog vipTipsDialog;
    public void showVipTipsDialog() {
        if (vipTipsDialog == null) {
            vipTipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_game_detail_vip_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        }
        vipTipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(v -> {
            if (vipTipsDialog != null && vipTipsDialog.isShowing()){
                vipTipsDialog.dismiss();
            }
        });
        vipTipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if (vipTipsDialog != null && vipTipsDialog.isShowing()){
                vipTipsDialog.dismiss();
            }
            startFragment(new NewUserVipFragment());
        });
        vipTipsDialog.show();
    }

    public void getTaoCardInfo(int cardid) {
        if (mViewModel != null) {
            mViewModel.getTaoCardInfo(gameid, cardid, new OnBaseCallback<GetCardInfoVo>() {
                @Override
                public void onSuccess(GetCardInfoVo getCardInfoVo) {
                    if (getCardInfoVo != null) {
                        if (getCardInfoVo.isStateOK()) {
                            if (getCardInfoVo.getData() != null) {
                                if (cardDialogHelper == null) {
                                    cardDialogHelper = new CardDialogHelper(GameDetailGiftBagListFragment.this);
                                }
                                cardDialogHelper.showSearchCardDialog(getCardInfoVo.getData().getCard(), false, "");
                            }
                        } else {
                            Toaster.show( getCardInfoVo.getMsg());
                            //ToastT.error(_mActivity, getCardInfoVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void refreshCardList() {
        getNetWorkData();
    }

    class CardListAdapter extends AbsAdapter<GameInfoVo.CardlistBean> {

        public CardListAdapter(Context context, List<GameInfoVo.CardlistBean> labels) {
            super(context, labels);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, GameInfoVo.CardlistBean item, int position) {
            CardListAdapter.ViewHolder holder = (CardListAdapter.ViewHolder) viewHolder;
            holder.mTvName.setText(item.getCardname());
            holder.mTvContent.setText(item.getCardcontent());

            if (item.isGetCard()){
                holder.mTvGiftBagCode.setVisibility(View.VISIBLE);
                holder.mTvTips.setVisibility(View.GONE);
                holder.mTvProgress.setText("礼包码：");
                holder.mTvGiftBagCode.setText(item.getCard());
                holder.mTvReceive.setText("复制");
                holder.mTvReceive.setTextColor(Color.parseColor("#FF6A36"));
                holder.mTvReceive.setBackgroundResource(R.drawable.ts_shape_f2f2f2_big_radius);
                holder.mTvReceive.setOnClickListener(view -> {
                    if (CommonUtils.copyString(mContext, item.getCard())) {
                        //ToastT.success("复制成功");
                        Toaster.show("复制成功");
                    }
                });
            }else{
                holder.mTvGiftBagCode.setVisibility(View.GONE);
                holder.mTvTips.setVisibility(View.VISIBLE);
                holder.mTvProgress.setText("剩余：" + (item.getCardcountall() == 0? 0: item.getCardkucun() * 100 / item.getCardcountall()) + "%");

                if (item.isCommentGift() && user_already_commented == 0){
                    holder.mTvReceive.setText("点评");
                    holder.mTvReceive.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.mTvReceive.setBackgroundResource(R.drawable.shape_55c0fe_5571fe_big_radius);
                }else{
                    if (item.getCardkucun() == 0) {
                        holder.mTvReceive.setText("淘号");
                        holder.mTvReceive.setTextColor(Color.parseColor("#FFFFFF"));
                        holder.mTvReceive.setBackgroundResource(R.drawable.shape_55c0fe_5571fe_big_radius);
                    } else {
                        holder.mTvReceive.setText("领取");
                        holder.mTvReceive.setTextColor(Color.parseColor("#FFFFFF"));
                        holder.mTvReceive.setBackgroundResource(R.drawable.shape_55c0fe_5571fe_big_radius);
                    }
                }

                holder.mTvReceive.setOnClickListener(view -> {
                    if (checkLogin()) {
                        if (checkUserBindPhoneByCardGift()) {
                            if(item.isCommentGift()){
                                //点评
                                getCommentGift(item.getCardid());
                            }else {
                                if (item.getCardkucun() != 0) {
                                    //领取礼包
                                    if (item.getSign() == 1){
                                        if (UserInfoModel.getInstance().getUserInfo().getSuper_user().getStatus().equals("yes")){
                                            getCardInfo(item.getCardid());
                                        }else {
                                            showVipTipsDialog();
                                        }
                                    }else{
                                        getCardInfo(item.getCardid());
                                    }
                                } else {
                                    //淘号
                                    getTaoCardInfo(item.getCardid());
                                }
                            }
                        }
                    }
                });
            }
            if (item.getCard_type() == 1) {
                holder.mTvTips.setText("免费赠送");
            }else if (item.getCard_type() == 2){
                holder.mTvTips.setText(item.getLabel());
            }

            if (item.getSign() == 1){
                holder.mTvVipTag.setVisibility(View.VISIBLE);
            }else {
                holder.mTvVipTag.setVisibility(View.GONE);
            }

            holder.mTvDetail.setOnClickListener(view -> {
                showGiftDetail(item);
            });
        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_game_list_card_new;
        }

        @Override
        public AbsViewHolder createViewHolder(View view) {
            return new CardListAdapter.ViewHolder(view);
        }

        class ViewHolder extends AbsViewHolder {

            private TextView mTvName;
            private TextView mTvContent;
            private TextView mTvProgress;
            private TextView mTvGiftBagCode;
            private TextView mTvTips;
            private TextView mTvReceive;
            private TextView mTvDetail;
            private TextView mTvVipTag;

            public ViewHolder(View view) {
                super(view);
                mTvName = findViewById(R.id.tv_name);
                mTvContent = view.findViewById(R.id.tv_content);
                mTvProgress = view.findViewById(R.id.tv_progress);
                mTvGiftBagCode = view.findViewById(R.id.tv_gift_bag_code);
                mTvTips = view.findViewById(R.id.tv_tips);
                mTvReceive = view.findViewById(R.id.tv_receive);
                mTvDetail = view.findViewById(R.id.tv_detail);
                mTvVipTag = view.findViewById(R.id.tv_vip_tag);
            }
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ACTION_WRITE_COMMENT:
                    user_already_commented = 1;
                    getNetWorkData();
                    break;

                default:
                    break;
            }
        }
    }
}
