package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.adapter.abs.AbsAdapter;
import com.zqhy.app.adapter.abs.EmptyAdapter1;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.detail.GameCardListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pc
 * @date 2019/12/18-14:22
 * @description
 */
public class NewGameGiftItemHolder extends BaseItemHolder<GameCardListVo, NewGameGiftItemHolder.ViewHolder> {

    private float                         density;
    private List<GameInfoVo.CardlistBean> cgNormalList   = new ArrayList<>();
    private List<GameInfoVo.CardlistBean> cgRechargeList = new ArrayList<>();
    private List<GameInfoVo.CardlistBean> cardList = new ArrayList<>();
    private CardListAdapter               mAdapter;
    private EmptyAdapter1                 emptyAdapter;
    private GameCardListVo                mGameCardListVo;


    public NewGameGiftItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_detail_gift_new;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameCardListVo item) {
        mGameCardListVo = item;
        List<GameInfoVo.CardlistBean> cardlist = item.getCardlist();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setSmoothScrollbarEnabled(true);
        holder.mRecyclerViewGift.setNestedScrollingEnabled(false);
        holder.mRecyclerViewGift.setLayoutManager(layoutManager);

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
        if (cgNormalList.size() > 0 && cgRechargeList.size() > 0){
            holder.mLlTabNormalGift.setVisibility(View.VISIBLE);
            holder.mLlTabRechargeGift.setVisibility(View.VISIBLE);
            item.setSelectListType(0);
            holder.mTvCgNormalGift.setTextSize(18);
            holder.mTvCgNormalGift.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            holder.mTvCgRechargeGift.setTextSize(14);
            holder.mTvCgRechargeGift.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }else if (cgNormalList.size() > 0 && cgRechargeList.size() == 0){
            holder.mLlTabNormalGift.setVisibility(View.VISIBLE);
            holder.mLlTabRechargeGift.setVisibility(View.GONE);
            item.setSelectListType(0);
            holder.mTvCgNormalGift.setTextSize(18);
            holder.mTvCgNormalGift.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            holder.mTvCgRechargeGift.setTextSize(14);
            holder.mTvCgRechargeGift.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }else if (cgNormalList.size() == 0 && cgRechargeList.size() > 0){
            holder.mLlTabNormalGift.setVisibility(View.GONE);
            holder.mLlTabRechargeGift.setVisibility(View.VISIBLE);
            item.setSelectListType(1);
            holder.mTvCgNormalGift.setTextSize(14);
            holder.mTvCgNormalGift.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            holder.mTvCgRechargeGift.setTextSize(18);
            holder.mTvCgRechargeGift.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }else if (cgNormalList.size() == 0 && cgRechargeList.size() == 0){
            holder.mLlTabNormalGift.setVisibility(View.VISIBLE);
            holder.mLlTabRechargeGift.setVisibility(View.GONE);
            item.setSelectListType(0);
            holder.mTvCgNormalGift.setTextSize(18);
            holder.mTvCgNormalGift.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            holder.mTvCgRechargeGift.setTextSize(14);
            holder.mTvCgRechargeGift.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
        mAdapter = new CardListAdapter(mContext, cardList);
        List<EmptyDataVo> emptyDataVoList = new ArrayList<>();
        emptyDataVoList.add(new EmptyDataVo(R.mipmap.img_empty_data_2));
        emptyAdapter = new EmptyAdapter1(mContext, emptyDataVoList);

        if (item.getSelectListType() == 0){
            if(item.isFold()){
                setDate(cgNormalList, Integer.MAX_VALUE, holder.mTvFold, holder.mRecyclerViewGift);
                holder.mTvFold.setText("收起列表");
                holder.mTvFold.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_game_detail_content_unfold), null);
            }else{
                setDate(cgNormalList, 3, holder.mTvFold, holder.mRecyclerViewGift);
                holder.mTvFold.setText("全部礼包");
                holder.mTvFold.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_game_detail_content_fold), null);
            }
        }else if (item.getSelectListType() == 1){
            if(item.isOtherFold()){
                setDate(cgRechargeList, Integer.MAX_VALUE, holder.mTvFold, holder.mRecyclerViewGift);
                holder.mTvFold.setText("收起列表");
                holder.mTvFold.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_game_detail_content_unfold), null);
            }else{
                setDate(cgRechargeList, 3, holder.mTvFold, holder.mRecyclerViewGift);
                holder.mTvFold.setText("全部礼包");
                holder.mTvFold.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_game_detail_content_fold), null);
            }
        }
        holder.mTvNormalGiftCount.setText("" + cgNormalList.size());
        holder.mTvRechargeGiftCount.setText("" + cgRechargeList.size());
        holder.mLlTabNormalGift.setOnClickListener(v -> {
            if (item.getSelectListType() != 0){
                item.setSelectListType(0);
                holder.mTvCgNormalGift.setTextSize(18);
                holder.mTvCgNormalGift.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                holder.mTvCgRechargeGift.setTextSize(14);
                holder.mTvCgRechargeGift.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                item.setFold(false);
                item.setOtherFold(false);
                holder.mTvFold.setText("全部礼包");
                holder.mTvFold.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_game_detail_content_fold), null);
                setDate(cgNormalList, 3, holder.mTvFold, holder.mRecyclerViewGift);
            }
        });
        holder.mLlTabRechargeGift.setOnClickListener(v -> {
            if (item.getSelectListType() != 1){
                item.setSelectListType(1);
                holder.mTvCgNormalGift.setTextSize(14);
                holder.mTvCgNormalGift.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                holder.mTvCgRechargeGift.setTextSize(18);
                holder.mTvCgRechargeGift.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                item.setFold(false);
                item.setOtherFold(false);
                holder.mTvFold.setText("全部礼包");
                holder.mTvFold.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_game_detail_content_fold), null);
                setDate(cgRechargeList, 3, holder.mTvFold, holder.mRecyclerViewGift);
            }
        });
        holder.mTvFold.setOnClickListener(v -> {
            if (item.getSelectListType() == 0){
                if(item.isFold()){
                    item.setFold(false);
                    setDate(cgNormalList, 3, holder.mTvFold, holder.mRecyclerViewGift);
                    holder.mTvFold.setText("全部礼包");
                    holder.mTvFold.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_game_detail_content_fold), null);
                }else{
                    item.setFold(true);
                    setDate(cgNormalList, Integer.MAX_VALUE, holder.mTvFold, holder.mRecyclerViewGift);
                    holder.mTvFold.setText("收起列表");
                    holder.mTvFold.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_game_detail_content_unfold), null);
                }
            }else{
                if(item.isOtherFold()){
                    item.setOtherFold(false);
                    setDate(cgRechargeList, 3, holder.mTvFold, holder.mRecyclerViewGift);
                    holder.mTvFold.setText("全部礼包");
                    holder.mTvFold.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_game_detail_content_fold), null);
                }else{
                    item.setOtherFold(true);
                    setDate(cgRechargeList, Integer.MAX_VALUE, holder.mTvFold, holder.mRecyclerViewGift);
                    holder.mTvFold.setText("收起列表");
                    holder.mTvFold.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_game_detail_content_unfold), null);
                }
            }
        });
        holder.mTvUserGift.setOnClickListener(v -> {
            if (_mFragment != null) {
                ((GameDetailInfoFragment) _mFragment).showMyGift();
            }
        });
    }

    private void setDate(List<GameInfoVo.CardlistBean> list, int maxSize, TextView textView, RecyclerView mRecyclerViewGift){
        if (list.size() < 3){
            textView.setVisibility(View.GONE);
        }else{
            textView.setVisibility(View.VISIBLE);
        }
        if (list.size() == 0){
            if (mRecyclerViewGift.getAdapter() != emptyAdapter) {
                mRecyclerViewGift.setAdapter(emptyAdapter);
            }
        }else{
            if (mRecyclerViewGift.getAdapter() != mAdapter){
                mRecyclerViewGift.setAdapter(mAdapter);
            }
            cardList.clear();
            for (int i = 0; i < list.size(); i++) {
                if (i < maxSize){
                    cardList.add(list.get(i));
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mLlTabNormalGift;
        private LinearLayout mLlTabRechargeGift;
        private TextView     mTvCgNormalGift;
        private TextView     mTvCgRechargeGift;
        private TextView     mTvNormalGiftCount;
        private TextView     mTvRechargeGiftCount;
        private TextView     mTvUserGift;
        private RecyclerView mRecyclerViewGift;
        private TextView mTvFold;

        public ViewHolder(View view) {
            super(view);
            mLlTabNormalGift = view.findViewById(R.id.ll_tab_normal_gift);
            mLlTabRechargeGift = view.findViewById(R.id.ll_tab_recharge_gift);
            mTvNormalGiftCount = view.findViewById(R.id.tv_normal_gift_count);
            mTvRechargeGiftCount = view.findViewById(R.id.tv_recharge_gift_count);
            mTvCgNormalGift = view.findViewById(R.id.tv_normal_gift);
            mTvCgRechargeGift = view.findViewById(R.id.tv_recharge_gift);
            mTvUserGift = view.findViewById(R.id.tv_user_gift);
            mRecyclerViewGift = view.findViewById(R.id.recyclerView_gift);
            mTvFold = view.findViewById(R.id.tv_fold);
        }
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
                        Toaster.show("复制成功");
                    }
                });
            }else{
                holder.mTvGiftBagCode.setVisibility(View.GONE);
                holder.mTvTips.setVisibility(View.VISIBLE);
                holder.mTvProgress.setText("剩余：" + (item.getCardcountall() == 0? 0: item.getCardkucun() * 100 / item.getCardcountall()) + "%");

                int finalCardLeftCount = item.getCardkucun();
                if (item.isCommentGift() && mGameCardListVo.getUser_already_commented() == 0){
                    holder.mTvReceive.setText("点评");
                    holder.mTvReceive.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.mTvReceive.setBackgroundResource(R.drawable.shape_55c0fe_5571fe_big_radius);
                }else{
                    if (finalCardLeftCount == 0) {
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
                    if (_mFragment != null) {
                        if (_mFragment.checkLogin()) {
                            if (_mFragment.checkUserBindPhoneByCardGift()) {
                                if(item.isCommentGift()){
                                    //点评
                                    ((GameDetailInfoFragment) _mFragment).getCommentGift(item.getCardid());
                                }else {
                                    if (finalCardLeftCount != 0) {
                                        //领取礼包
                                        if (item.getSign() == 1){
                                            if (UserInfoModel.getInstance().getUserInfo().getSuper_user().getStatus().equals("yes")){
                                                ((GameDetailInfoFragment) _mFragment).getCardInfo(item.getCardid());
                                            }else {
                                                ((GameDetailInfoFragment) _mFragment).showVipTipsDialog();
                                            }
                                        }else{
                                            ((GameDetailInfoFragment) _mFragment).getCardInfo(item.getCardid());
                                        }
                                    } else {
                                        //淘号
                                        ((GameDetailInfoFragment) _mFragment).getTaoCardInfo(item.getCardid());
                                    }
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
                if (_mFragment != null && _mFragment instanceof GameDetailInfoFragment)
                    ((GameDetailInfoFragment) _mFragment).showGiftDetail(item);
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
}
