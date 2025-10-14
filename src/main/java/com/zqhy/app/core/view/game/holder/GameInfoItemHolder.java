package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.box.common.glide.GlideApp;
import com.box.common.ui.adapter.HorizontalSpaceItemDecoration;
import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.hjq.toast.Toaster;
import com.danikula.videocache.HttpProxyCacheServer;
import com.donkingliang.imageselector.PreviewActivity;
import com.donkingliang.imageselector.entry.Image;
import com.google.android.flexbox.FlexboxLayout;
import com.zqhy.app.App;
import com.zqhy.app.Setting;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.detail.GameActivityVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.game.GameDetaiActivityFragment;
import com.zqhy.app.core.view.game.GameDetailGiftBagListFragment;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.core.view.game.GameDetailServerListFragment;
import com.zqhy.app.core.view.main.holder.GameLabelAdapter;
import com.zqhy.app.core.view.main.new0809.NewGameRankingActivityFragment;
import com.zqhy.app.core.view.user.BindPhoneFragment;
import com.zqhy.app.core.view.user.CertificationFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.utils.DisInterceptTouchListener;
import com.zqhy.app.widget.MarqueeTextView;
import com.zqhy.app.widget.video.CustomMediaPlayer.JZExoPlayer;
import com.zqhy.app.widget.video.JzvdStdVolumeAfterFullscreen;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * @author Administrator
 * @date 2018/11/20
 */

public class GameInfoItemHolder extends AbsItemHolder<GameInfoVo, GameInfoItemHolder.ViewHolder> {

    private       float density;
    private       int   mFixHeight;
    private final float videoScale = 16f / 9f;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration decoration;

    private ArrayList<String> bgColorList = new ArrayList<>();
    private String[] bgColorList1 = new String[]{"#323E58","#936785","#171848","#424981","#392734","#555368","#5C6B7F","#678279","#A66B6B","#221814","#4D4542","#4A4A47","#3B0902","#563422","#64472F","#573422","#1E2639","#805236","#3C1E1C","#000002","#060808","#4B3269","#563E46","#A66A6B","#693C36","#3D241D","#863816","#7A634A","#252530","#496C26","#263747","#091B3F","#23232A","#1E2738","#18191F","#0C2131","#2E90E1","#091C30","#0C0A10","#304467","#1182E7","#A73E1B","#3D5D76","#763D3D","#763D50","#413D76","#27AA75","#AA2769","#AA6027","#5727AA","#0E1826","#243164","#040221","#121B42","#0F0F0F","#0D161B","#210B07","#070A0F","#230F06","#533D76","#230A14","#280915","#7E1706","#AA2738","#141211","#130201","#241B0D","#734C45","#83415F","#773F56","#3470FF","#3F0102","#2E1B37","#410102","#400E35","#173F64","#1F1F45","#9F449C","#91635E","#7E5BBE","#342E34","#1E0C18","#1F0D19","#2B1808","#12153E","#87328D","#425101","#981103","#4D4333","#59442E"};


    public GameInfoItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
        mFixHeight = (int) (ScreenUtil.getScreenWidth(mContext) / videoScale);

        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        decoration = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
        decoration.setDrawable(mContext.getResources().getDrawable(R.drawable.main_pager_item_decoration_vertical));
        //"#323E58","#936785","#171848","#424981","#392734","#555368","#5C6B7F","#678279","#A66B6B","#221814","#4D4542","#4A4A47",
        // "#3B0902","#563422","#64472F","#573422","#1E2639","#805236","#3C1E1C","#000002","#060808","#4B3269","#563E46","#A66A6B",
        // "#693C36","#3D241D","#863816","#7A634A","#252530","#496C26","#263747","#091B3F","#23232A","#1E2738","#18191F","#0C2131",
        // "#2E90E1","#091C30","#0C0A10","#304467","#1182E7","#A73E1B","#3D5D76","#763D3D","#763D50","#413D76","#27AA75","#AA2769",
        // "#AA6027","#5727AA","#0E1826","#243164","#040221","#121B42","#0F0F0F","#0D161B","#210B07","#070A0F","#230F06","#533D76",
        // "#230A14","#280915","#7E1706","#AA2738","#141211","#130201","#241B0D","#734C45","#83415F","#773F56","#3470FF","#3F0102",
        // "#2E1B37","#410102","#400E35","#173F64","#1F1F45","#9F449C","#91635E","#7E5BBE","#342E34","#1E0C18","#1F0D19","#2B1808",
        // "#12153E","#87328D","#425101","#981103","#4D4333","#59442E"
        bgColorList.addAll(Arrays.asList(bgColorList1));

        if (_mFragment != null) {
            bgColorList.remove(((GameDetailInfoFragment)_mFragment).getSelectBgColor());
        }
        Collections.shuffle(bgColorList);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameInfoVo item) {
        if (_mFragment != null){
            holder.mLlGameInfoTopBg.setBackgroundColor(Color.parseColor(((GameDetailInfoFragment)_mFragment).getSelectBgColor()));
            holder.mRlGameInfo.setBackgroundColor(Color.parseColor(((GameDetailInfoFragment)_mFragment).getSelectBgColor()));
        }

        setGameInfoPics(holder, item);

        GlideUtils.loadGameIcon(mContext, item.getGameicon(), holder.mGameIconIV);

        //游戏名字
        holder.mTvGameName.setText(item.getGamename());

        //BT游戏不显示折扣，以及部分游戏不显示折扣
        //显示折扣
        int showDiscount = item.showDiscount();
        if (item.getGdm() == 1){//头条包不显示GM标签
            holder.mTvDiscount4.setVisibility(View.VISIBLE);
            holder.mLlDiscount.setVisibility(View.GONE);
        }else {
            holder.mTvDiscount4.setVisibility(View.GONE);
            if (showDiscount == 1 || showDiscount == 2) {
                if (showDiscount == 1){
                    if (item.getDiscount() <= 0 || item.getDiscount() >= 10) {
                        if (item.getSelected_game() == 1){//是否是精选游戏
                            holder.mLlDiscount .setVisibility(View.VISIBLE);
                            holder.mLlDiscount1.setVisibility(View.GONE);
                            holder.mLlDiscount2.setVisibility(View.GONE);
                            holder.mTvDiscount3.setVisibility(View.VISIBLE);
                        }else {
                            holder.mLlDiscount .setVisibility(View.GONE);
                        }
                    }else {
                        holder.mLlDiscount .setVisibility(View.VISIBLE);
                    /*if (item.getSelected_game() == 1){//是否是精选游戏
                        holder.mLlDiscount1.setVisibility(View.VISIBLE);
                        holder.mLlDiscount2.setVisibility(View.VISIBLE);
                        holder.mTvDiscount3.setVisibility(View.GONE);
                        holder.mTvDiscount1.setText(String.valueOf(item.getDiscount()));
                        holder.mTvDiscount2.setText("精品游戏");
                        holder.mIvDiscount.setVisibility(View.GONE);
                    }else {
                        holder.mLlDiscount1.setVisibility(View.VISIBLE);
                        holder.mLlDiscount2.setVisibility(View.VISIBLE);
                        holder.mTvDiscount3.setVisibility(View.GONE);
                        holder.mTvDiscount1.setText(String.valueOf(item.getDiscount()));
                        holder.mTvDiscount2.setVisibility(View.VISIBLE);
                        holder.mTvDiscount2.setText("折扣说明");
                        holder.mIvDiscount.setVisibility(View.VISIBLE);
                    }*/
                        holder.mLlDiscount1.setVisibility(View.VISIBLE);
                        holder.mLlDiscount2.setVisibility(View.VISIBLE);
                        holder.mTvDiscount3.setVisibility(View.GONE);
                        holder.mTvDiscount1.setText(String.valueOf(item.getDiscount()));
                        holder.mTvDiscount2.setVisibility(View.VISIBLE);
                        holder.mTvDiscount2.setText("折扣说明");
                        holder.mIvDiscount.setVisibility(View.VISIBLE);
                    }
                }else if (showDiscount == 2){
                    if (item.getFlash_discount() <= 0 || item.getFlash_discount() >= 10) {
                        if (item.getSelected_game() == 1){//是否是精选游戏
                            holder.mLlDiscount .setVisibility(View.VISIBLE);
                            holder.mLlDiscount1.setVisibility(View.GONE);
                            holder.mLlDiscount2.setVisibility(View.GONE);
                            holder.mTvDiscount3.setVisibility(View.VISIBLE);
                        }else {
                            holder.mLlDiscount .setVisibility(View.GONE);
                        }
                    }else {
                        holder.mLlDiscount .setVisibility(View.VISIBLE);
                    /*if (item.getSelected_game() == 1){//是否是精选游戏
                        holder.mLlDiscount1.setVisibility(View.VISIBLE);
                        holder.mLlDiscount2.setVisibility(View.VISIBLE);
                        holder.mTvDiscount3.setVisibility(View.GONE);
                        holder.mTvDiscount1.setText(String.valueOf(item.getDiscount()));
                        holder.mTvDiscount2.setText("精品游戏");
                        holder.mIvDiscount.setVisibility(View.GONE);
                    }else {
                        holder.mLlDiscount1.setVisibility(View.VISIBLE);
                        holder.mLlDiscount2.setVisibility(View.VISIBLE);
                        holder.mTvDiscount3.setVisibility(View.GONE);
                        holder.mTvDiscount1.setText(String.valueOf(item.getDiscount()));
                        holder.mTvDiscount2.setVisibility(View.VISIBLE);
                        holder.mTvDiscount2.setText("折扣说明");
                        holder.mIvDiscount.setVisibility(View.VISIBLE);
                        holder.mIvDiscount.setVisibility(View.VISIBLE);
                    }*/
                        holder.mLlDiscount1.setVisibility(View.VISIBLE);
                        holder.mLlDiscount2.setVisibility(View.VISIBLE);
                        holder.mTvDiscount3.setVisibility(View.GONE);
                        holder.mTvDiscount1.setText(String.valueOf(item.getDiscount()));
                        holder.mTvDiscount2.setVisibility(View.VISIBLE);
                        holder.mTvDiscount2.setText("折扣说明");
                        holder.mIvDiscount.setVisibility(View.VISIBLE);
                        holder.mIvDiscount.setVisibility(View.VISIBLE);
                    }
                }
            }else {
                if (item.getSelected_game() == 1){//是否是精选游戏
                    holder.mLlDiscount .setVisibility(View.VISIBLE);
                    holder.mLlDiscount1.setVisibility(View.GONE);
                    holder.mLlDiscount2.setVisibility(View.GONE);
                    holder.mTvDiscount3.setVisibility(View.VISIBLE);
                }else {
                    holder.mLlDiscount .setVisibility(View.GONE);
                }
            }
        }


        holder.mLlDiscount.setOnClickListener(v -> {
            if (_mFragment != null){
                if (showDiscount == 1 || showDiscount == 2) ((GameDetailInfoFragment) _mFragment).showDiscountTipsDialog();
            }
        });

        if (!TextUtils.isEmpty(item.getOtherGameName())){//游戏后缀
            holder.mTvGameMiddle.setVisibility(View.VISIBLE);
            holder.mTvGameMiddle.setText(item.getOtherGameName());
        }else {
            holder.mTvGameMiddle.setVisibility(View.GONE);
        }

        holder.mTvGameMiddle.post(() -> {
            holder.mTvGameName.setMaxWidth(ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 130) - holder.mLlDiscount.getWidth() - holder.mTvGameMiddle.getWidth());
            holder.mTvGameName.requestFocus();
        });

        /*holder.mTvGameMiddle.setOnClickListener(v -> {
            if (_mFragment != null){
                //if (_mFragment.checkLogin()) BrowserCloudActivity.newInstance(_mFragment.getActivity(), "https://sdkapi.tsyule.cn/index.php/test/test_demo?xw=20240509&gamecode=eh7xuc&app=1",  true, item.getGamename(), String.valueOf(item.getGameid()));
                //if (_mFragment.checkLogin()) BrowserCloudActivity.newInstance(_mFragment.getActivity(), "https://sdkapi.tsyule.cn/index.php/test/test_demo?xw=20240509&gamecode=bj0ruf&app=1",  true, item.getGamename(), String.valueOf(item.getGameid()));
                if (_mFragment.checkLogin()) BrowserCloudActivity.newInstance(_mFragment.getActivity(), "https://sdkapi.tsyule.cn/index.php/test/test_demo?xw=20240514&gamecode=nqqvuq&app=1",  true, item.getGamename(), String.valueOf(item.getGameid()));
            }
        });*/

        //游戏类型
        setGameTypes(holder, item);
        //游戏标签
        setTagLayout(holder, item);

        holder.mTvInfoMiddle.setText(item.getGenre_str());
        holder.mTvOpenServer.setText(item.getServer_str());

        if (item.getCoupon_list() != null && item.getCoupon_list().size() > 0){
            DecimalFormat decimalFormat = new DecimalFormat("0.0");
            String format = decimalFormat.format(item.getCoupon_amount());
            String amount = (format.indexOf(".0") != -1)? format.substring(0, format.indexOf(".0")): format;
            holder.mTvTitleVoucher.setText("" + amount);
        }else {
            holder.mTvTitleVoucher.setText("0");
        }
        if (item.getCardlist() != null && item.getCardlist().size() > 0){
            holder.mTvTitleGiftBag.setText(String.valueOf(item.getCardlist().size()));
        }else {
            holder.mTvTitleGiftBag.setText("0");
        }
        GameActivityVo gameActivityVo = item.getGameActivityVo();
        gameActivityVo.setUserCommented(false);
        if (gameActivityVo.getActivity() != null && gameActivityVo.getActivity().size() > 0){
            List<GameActivityVo.ItemBean> itemBeanList = new ArrayList<>();
            itemBeanList.addAll(createNewsBeans(gameActivityVo, "normal"));
            holder.mTvTitleActivi.setText(String.valueOf(itemBeanList.size()));
        }else {
            holder.mTvTitleActivi.setText("0");
        }

        if (item.getLsb_card_info() == null){
            holder.mCl648.setVisibility(View.GONE);
        }else {
            if (TextUtils.isEmpty(item.getLsb_card_info().getCard())){
                holder.mCl648.setVisibility(View.VISIBLE);
                holder.mTvAction648.setText("领取");
                holder.mTvAction648.setOnClickListener(v -> {
                    if (_mFragment != null){
                        if(_mFragment.checkLogin()){
                            if (!UserInfoModel.getInstance().isBindMobile()) {
                                _mFragment.start(BindPhoneFragment.newInstance(false, ""));
                            }else {
                                if (!TextUtils.isEmpty(UserInfoModel.getInstance().getUserInfo().getIdcard()) && !TextUtils.isEmpty(UserInfoModel.getInstance().getUserInfo().getReal_name())){
                                    ((GameDetailInfoFragment)_mFragment).getLsbCard();
                                }else {
                                    _mFragment.startFragment(CertificationFragment.newInstance());
                                }
                            }
                        }
                    }
                });
            }else{
                holder.mCl648.setVisibility(View.VISIBLE);
                holder.mTvAction648.setText("去使用");
                holder.mTvAction648.setOnClickListener(v -> {
                    ((GameDetailInfoFragment)_mFragment).showLSBDetailDialog();
                });
            }
        }

        /*new Handler().postDelayed(() -> {
            if (item.getChat_status() == 1){
                holder.mLlChat.setVisibility(View.VISIBLE);
                if (((GameDetailInfoFragment)_mFragment).chatList != null && ((GameDetailInfoFragment)_mFragment).chatList.size() > 0){
                    holder.viewFlipper.setVisibility(View.VISIBLE);
                    holder.viewFlipper.removeAllViews();
                    for (ChatMsgListVo.DataBean bean:((GameDetailInfoFragment)_mFragment).chatList) {
                        View inflate = LayoutInflater.from(mContext).inflate(R.layout.viewflipper_item_chat_list, null, false);
                        GlideUtils.loadCircleImage(mContext, bean.getIcon(), inflate.findViewById(R.id.iv_icon), R.mipmap.ic_user_login_new_sign);
                        ((TextView) inflate.findViewById(R.id.tv_content)).setText(bean.getMsg());
                        holder.viewFlipper.addView(inflate);
                    }
                }else {
                    holder.viewFlipper.setVisibility(View.INVISIBLE);
                }
            }else {
                holder.mLlChat.setVisibility(View.GONE);
            }
        }, 500);*/

        holder.mTvOpenServer.setOnClickListener(v -> {
            if (_mFragment != null) {
                _mFragment.startFragment(GameDetailServerListFragment.newInstance(item));
            }
        });

        holder.mClVoucher.setOnClickListener(v -> {
            if (_mFragment != null) {
                //_mFragment.startFragment(GameDetailCouponListFragment.newInstance(item.getGameid()));
                ((GameDetailInfoFragment)_mFragment).gameinfoPartCoupon();
            }
        });

        holder.mClGiftBag.setOnClickListener(v -> {
            _mFragment.startFragment(GameDetailGiftBagListFragment.newInstance(item.getUser_already_commented(), item.getGamename(), item.getGameid()));
        });

        holder.mClActivi.setOnClickListener(v -> {
            if (_mFragment != null) {
                _mFragment.startFragment(GameDetaiActivityFragment.newInstance(item.getGamename(), item.getGameid()));
            }
        });
        holder.mTvJoin.setOnClickListener(view -> {
            //((GameDetailInfoFragment)_mFragment).addChat();
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_detail_info;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private LinearLayout mLlGameInfoTopBg;
        private RecyclerView mRecyclerView;
        private RecyclerView mIndicatorRecyclerView;
        private ImageView mGameIconIV;
        private MarqueeTextView mTvGameName;
        public  TextView       mTvGameMiddle;
        public  TextView       mTvInfoMiddle;
        public  TextView       mTvOpenServer;
        private RelativeLayout mRlGameInfo;
        public RecyclerView rvGameLabels;
        private FlexboxLayout  mTagLayout;
        private TextView       mTvInfoBottom;
        private ConstraintLayout mCl648;
        private TextView mTvAction648;
        private ConstraintLayout mClVoucher;
        private TextView mTvTitleVoucher;
        private ConstraintLayout mClGiftBag;
        private TextView mTvTitleGiftBag;
        private ConstraintLayout mClActivi;
        private TextView mTvTitleActivi;
        private LinearLayout mLlChat;
        private ViewFlipper viewFlipper;
        private TextView mTvJoin;

        private LinearLayout mLlDiscount;
        private LinearLayout mLlDiscount1;
        private LinearLayout mLlDiscount2;
        private TextView mTvDiscount1;
        private TextView mTvDiscount2;
        private TextView mTvDiscount3;
        private TextView mTvDiscount4;
        private ImageView mIvDiscount;

        public ViewHolder(View view) {
            super(view);
            mLlGameInfoTopBg = findViewById(R.id.ll_game_info_top_bg);
            mRecyclerView = findViewById(R.id.recycler_view);
            mIndicatorRecyclerView = findViewById(R.id.indicator_recycler_view);
            mGameIconIV = findViewById(R.id.gameIconIV);
            mTvGameName = findViewById(R.id.tv_game_name);
            mTvGameMiddle = findViewById(R.id.tv_game_suffix);
            mTvInfoMiddle = findViewById(R.id.tv_info_middle);
            mTvOpenServer = findViewById(R.id.tv_open_server);
            mRlGameInfo = findViewById(R.id.rl_game_info);
            rvGameLabels = findViewById(R.id.rv_game_labels);
            mTvInfoBottom = findViewById(R.id.tv_info_bottom);
            mTagLayout = findViewById(R.id.tag_layout);
            mCl648 = findViewById(R.id.cl_648);
            mTvAction648 = findViewById(R.id.tv_action_648);
            mClVoucher = findViewById(R.id.cl_voucher);
            mTvTitleVoucher = findViewById(R.id.tv_title_voucher);
            mClGiftBag = findViewById(R.id.cl_gift_bag);
            mTvTitleGiftBag = findViewById(R.id.tv_title_gift_bag);
            mClActivi = findViewById(R.id.cl_activi);
            mTvTitleActivi = findViewById(R.id.tv_title_activi);
            mLlChat = findViewById(R.id.ll_chat);
            viewFlipper = findViewById(R.id.viewflipper);
            mTvJoin = findViewById(R.id.tv_join);

            mLlDiscount = findViewById(R.id.ll_discount);
            mLlDiscount1 = findViewById(R.id.ll_discount_1);
            mLlDiscount2 = findViewById(R.id.ll_discount_2);
            mTvDiscount1 = findViewById(R.id.tv_discount_1);
            mTvDiscount2 = findViewById(R.id.tv_discount_2);
            mTvDiscount3 = findViewById(R.id.tv_discount_3);
            mTvDiscount4 = findViewById(R.id.tv_discount_4);
            mIvDiscount = findViewById(R.id.iv_discount);
        }
    }

    private List<MyExhibition> viewList = new ArrayList<>();
    private int indexPosition = 0;
    private void setGameInfoPics(ViewHolder holder, GameInfoVo item) {
        viewList.clear();
        if (!TextUtils.isEmpty(item.getVideo_pic()) && !TextUtils.isEmpty(item.getVideo_url())) {
            viewList.add(new MyExhibition("video", ((GameDetailInfoFragment)_mFragment).getSelectBgColor()));
        }
        if (item.getScreenshot() != null && item.getScreenshot().size() > 0) {
            for (int i = 0; i < item.getScreenshot().size(); i++) {
                viewList.add(new MyExhibition("pic", ((GameDetailInfoFragment)_mFragment).getSelectBgColor()));
            }
        }

        if (Setting.HIDE_FIVE_FIGURE == 1){
            viewList.clear();
        }

        //设置recycleView布局管理器
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);
        //holder.mRecyclerView.addItemDecoration(decoration);
        MyAdapter myAdapter = new MyAdapter(item, item.getScreenshot());
        holder.mRecyclerView.setAdapter(myAdapter);
        LinearSnapHelper pagerSnapHelper = new LinearSnapHelper();
        holder.mRecyclerView.setOnFlingListener(null);//避免出现An instance of OnFlingListener already set异常
        // 滑动后Snap
        pagerSnapHelper.attachToRecyclerView(holder.mRecyclerView);
        // 滑动时使父布局不响应事件
        holder.mRecyclerView.setOnTouchListener(new DisInterceptTouchListener());

        holder.mIndicatorRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        IndicatorAdapter indicatorAdapter = new IndicatorAdapter(viewList.size());
        holder.mIndicatorRecyclerView.setAdapter(indicatorAdapter);

        holder.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                if (indexPosition != firstVisibleItemPosition){//判断是否切换了position
                    indicatorAdapter.setPosition(firstVisibleItemPosition);
                    indicatorAdapter.notifyDataSetChanged();

                    if (_mFragment != null){
                        if (firstVisibleItemPosition <= 0){
                            holder.mLlGameInfoTopBg.setBackgroundColor(Color.parseColor(((GameDetailInfoFragment)_mFragment).getSelectBgColor()));
                            holder.mRlGameInfo.setBackgroundColor(Color.parseColor(((GameDetailInfoFragment)_mFragment).getSelectBgColor()));
                            ((GameDetailInfoFragment)_mFragment).setGameTitleColor(((GameDetailInfoFragment)_mFragment).getSelectBgColor());
                            for (int i = 0; i < viewList.size(); i++) {
                                viewList.get(i).setColor(((GameDetailInfoFragment)_mFragment).getSelectBgColor());
                            }
                            myAdapter.notifyItemRangeChanged(0, viewList.size(), "changeBg");
                        }else {
                            if (firstVisibleItemPosition % 2 == 0){
                                String s = bgColorList.get(firstVisibleItemPosition);
                                holder.mLlGameInfoTopBg.setBackgroundColor(Color.parseColor(s));
                                holder.mRlGameInfo.setBackgroundColor(Color.parseColor(s));
                                ((GameDetailInfoFragment)_mFragment).setGameTitleColor(s);
                                for (int i = 0; i < viewList.size(); i++) {
                                    viewList.get(i).setColor(bgColorList.get(firstVisibleItemPosition));
                                }
                                myAdapter.notifyItemRangeChanged(0, viewList.size(), "changeBg");
                            }
                        }
                    }

                    indexPosition = firstVisibleItemPosition;

                    Logs.e("firstVisibleItemPosition: " + firstVisibleItemPosition);
                }
            }
        });
    }

    /**
     * 小屏幕VideoView
     *
     * @param video_pic
     * @param video_url
     * @return
     */
    private View createMiniVideoView(String video_pic, String video_url) {
        final float videoScale = 16f / 9f;
        int mFixWidth = ScreenUtil.getScreenWidth(mContext);
        int mFixHeight = (int) (mFixWidth / videoScale);

        JzvdStdVolumeAfterFullscreen videoPlayer = new JzvdStdVolumeAfterFullscreen(mContext);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mFixWidth, mFixHeight);
        params.gravity = Gravity.CENTER_VERTICAL;
        videoPlayer.setLayoutParams(params);

        if (TextUtils.isEmpty(video_url)) {
            return videoPlayer;
        }

        GlideUtils.loadNormalImage(mContext, video_pic, videoPlayer.thumbImageView, R.mipmap.img_placeholder_v_2);

        Logs.e("视频链接：" + video_url);

        HttpProxyCacheServer proxy = App.getProxy(mContext);
        String proxyUrl = proxy.getProxyUrl(video_url);

        Logs.e("视频链接(proxyUrl)：" + proxyUrl);
        Jzvd.setMediaInterface(new JZExoPlayer());
        videoPlayer.setUp(proxyUrl, "", JzvdStd.SCREEN_WINDOW_LIST);
        //        videoPlayer.startVideo();
        return videoPlayer;
    }

    private void setGameTypes(@NonNull ViewHolder holder, @NonNull GameInfoVo gameInfoVo) {
        // 初始化 RecyclerView
        if (holder.rvGameLabels.getLayoutManager() == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    mContext, LinearLayoutManager.HORIZONTAL, false);
            holder.rvGameLabels.setLayoutManager(layoutManager);
            int spacing = (int) (1 * ScreenUtil.getScreenDensity(mContext));
            HorizontalSpaceItemDecoration decoration = new HorizontalSpaceItemDecoration(spacing);
            holder.rvGameLabels.addItemDecoration(decoration);
        }

        // 设置适配器
        if (holder.rvGameLabels.getAdapter() == null) {
            GameLabelAdapter adapter = new GameLabelAdapter(mContext);
            holder.rvGameLabels.setAdapter(adapter);
        }

        // 更新数据
        GameLabelAdapter adapter = (GameLabelAdapter) holder.rvGameLabels.getAdapter();

        if (gameInfoVo.getGame_labels() != null && !gameInfoVo.getGame_labels().isEmpty()) {
            adapter.setLabels(gameInfoVo.getGame_labels());
            holder.rvGameLabels.setVisibility(View.VISIBLE);
            holder.mTvInfoBottom.setVisibility(View.GONE);
        } else {
            holder.rvGameLabels.setVisibility(View.GONE);
            holder.mTvInfoBottom.setVisibility(View.VISIBLE);
            holder.mTvInfoBottom.setText(gameInfoVo.getGame_summary());
        }
    }

    private void setTagLayout(@NonNull ViewHolder holder, @NonNull GameInfoVo gameInfoVo){
        int viewCount = 0;
        holder.mTagLayout.removeAllViews();
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
        params.rightMargin = (int) (5 * ScreenUtil.getScreenDensity(mContext));

        if (gameInfoVo.getRebate_custom_label() != null){
            holder.mTagLayout.addView(createTagLabelView(gameInfoVo.getRebate_custom_label()), params);
            viewCount++;
        }

        View tagLabelView1;
        if (gameInfoVo.getRanking_hot() > 0){
            tagLabelView1 = createTagLabelView("榜", "热门榜 #" + gameInfoVo.getRanking_hot() + " >");
        }else {
            tagLabelView1 = createTagLabelView("榜", "热门榜 >");
        }

        View tagLabelView2;
        if (gameInfoVo.getRanking_select() > 0){
            tagLabelView2 = createTagLabelView("榜", "精品榜 #" + gameInfoVo.getRanking_select() + " >");
        }else {
            tagLabelView2 = createTagLabelView("榜", "精品榜 >");
        }

        tagLabelView1.setOnClickListener(v -> {
            //if (_mFragment != null) _mFragment.startFragment(MainGameRankingFragment.newInstance(6, true));
            if (_mFragment != null) _mFragment.startFragment(NewGameRankingActivityFragment.newInstance("hot"));
        });
        tagLabelView2.setOnClickListener(v -> {
            //if (_mFragment != null) _mFragment.startFragment(MainGameRankingFragment.newInstance(7, true));
            if (_mFragment != null) _mFragment.startFragment(NewGameRankingActivityFragment.newInstance("new"));
        });
        if (gameInfoVo.getRanking_hot() > 0) {
            holder.mTagLayout.addView(tagLabelView1, params);
            viewCount++;
        }
        if (gameInfoVo.getRanking_select() > 0) {
            holder.mTagLayout.addView(tagLabelView2, params);
            viewCount++;
        }
        if (gameInfoVo.getCustom_label() != null){
            for (GameInfoVo.CustomLabelBean customLabelBean:gameInfoVo.getCustom_label()) {
                View tagLabelView = createTagLabelView("热", customLabelBean.getLabel() + ">");
                tagLabelView.setOnClickListener(v -> {
                    if (_mFragment != null) new AppJumpAction(_mFragment.getActivity()).jumpAction(new AppBaseJumpInfoBean(customLabelBean.getPage_type(), customLabelBean.getParam()));
                });
                holder.mTagLayout.addView(tagLabelView, params);
                viewCount++;
            }
        }
        //预约状态不显示XXX人在玩标签
        if (!gameInfoVo.isGameAppointment()){
            holder.mTagLayout.addView(createTagLabelView("热", CommonUtils.formatNumberType2(gameInfoVo.getPlay_count()) + "人在玩"), params);
            viewCount++;
        }

        if (viewCount <= 2){
            HorizontalScrollView.LayoutParams layoutParams = (HorizontalScrollView.LayoutParams) holder.mTagLayout.getLayoutParams();
            layoutParams.leftMargin = ScreenUtil.dp2px(mContext, 90);
            holder.mTagLayout.setLayoutParams(layoutParams);
        }
    }

    private View createTagLabelView(String tag, String content) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_game_detail_top_tag_new, null);
        ((TextView)mView.findViewById(R.id.tv_tag)).setText(tag);
        ((TextView)mView.findViewById(R.id.tv_content)).setText(content);
        if ("热".equals(tag)){
            /*((TextView)mView.findViewById(R.id.tv_tag)).setBackgroundResource(R.drawable.shape_54a6fe_5_radius);
            ((LinearLayout)mView.findViewById(R.id.ll_tag)).setBackgroundResource(R.drawable.shape_54a6fe_5_radius_with_line);
            ((TextView)mView.findViewById(R.id.tv_content)).setTextColor(Color.parseColor("#54A6FE"));*/
            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(ScreenUtil.dp2px(mContext, 5));
            gd.setColor(Color.parseColor("#54A6FE"));
            ((TextView)mView.findViewById(R.id.tv_tag)).setBackground(gd);

            GradientDrawable gd1 = new GradientDrawable();
            gd1.setCornerRadius(ScreenUtil.dp2px(mContext, 5));
            gd1.setStroke(ScreenUtil.dp2px(mContext, 1), Color.parseColor("#54A6FE"));
            ((LinearLayout)mView.findViewById(R.id.ll_tag)).setBackground(gd1);

            ((TextView)mView.findViewById(R.id.tv_content)).setTextColor(Color.parseColor("#54A6FE"));
        }else if ("榜".equals(tag)){
            /*((TextView)mView.findViewById(R.id.tv_tag)).setBackgroundResource(R.drawable.shape_ff6a36_5_radius);
            ((LinearLayout)mView.findViewById(R.id.ll_tag)).setBackgroundResource(R.drawable.shape_ff6a36_5_radius_with_line);
            ((TextView)mView.findViewById(R.id.tv_content)).setTextColor(Color.parseColor("#FF6337"));*/
            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(ScreenUtil.dp2px(mContext, 5));
            gd.setColor(Color.parseColor("#FF6337"));
            ((TextView)mView.findViewById(R.id.tv_tag)).setBackground(gd);

            GradientDrawable gd1 = new GradientDrawable();
            gd1.setCornerRadius(ScreenUtil.dp2px(mContext, 5));
            gd1.setStroke(ScreenUtil.dp2px(mContext, 1), Color.parseColor("#FF6337"));
            ((LinearLayout)mView.findViewById(R.id.ll_tag)).setBackground(gd1);

            ((TextView)mView.findViewById(R.id.tv_content)).setTextColor(Color.parseColor("#FF6337"));
        }
        return mView;
    }

    private View createSpLabelView(String text) {
        TextView textView = new TextView(mContext);
        textView.setText(text);
        textView.setIncludeFontPadding(false);
        textView.setTextColor(Color.parseColor("#333333"));
        textView.setTextSize(9.5f);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(ScreenUtil.dp2px(mContext, 4));
        gd.setColor(Color.parseColor("#FFBE00"));
        textView.setBackground(gd);

        textView.setPadding(ScreenUtil.dp2px(mContext, 4), ScreenUtil.dp2px(mContext, 2), ScreenUtil.dp2px(mContext, 4), ScreenUtil.dp2px(mContext, 2));

        return textView;
    }

    private View createTagLabelView(GameInfoVo.RebateCustomLabel label) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_game_detail_top_tag_new, null);
        ((TextView)mView.findViewById(R.id.tv_tag)).setText(label.getTip());
        ((TextView)mView.findViewById(R.id.tv_content)).setText(label.getLable());
        try{
            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(ScreenUtil.dp2px(mContext, 5));
            gd.setColor(Color.parseColor(label.getColor()));
            ((TextView)mView.findViewById(R.id.tv_tag)).setBackground(gd);

            GradientDrawable gd1 = new GradientDrawable();
            gd1.setCornerRadius(ScreenUtil.dp2px(mContext, 5));
            gd1.setStroke(ScreenUtil.dp2px(mContext, 1), Color.parseColor(label.getColor()));
            ((LinearLayout)mView.findViewById(R.id.ll_tag)).setBackground(gd1);

            ((TextView)mView.findViewById(R.id.tv_content)).setTextColor(Color.parseColor(label.getColor()));
        }catch (Exception e){
            ((TextView)mView.findViewById(R.id.tv_tag)).setBackgroundResource(R.drawable.shape_ff6a36_5_radius);
            ((LinearLayout)mView.findViewById(R.id.ll_tag)).setBackgroundResource(R.drawable.shape_ff6a36_5_radius_with_line);
            ((TextView)mView.findViewById(R.id.tv_content)).setTextColor(Color.parseColor("#FF6337"));
        }
        return mView;
    }

    private View createLabelView(GameInfoVo.GameLabelsBean labelsBean) {
        TextView textView = new TextView(mContext);
        textView.setText(labelsBean.getLabel_name());
        textView.setMaxLines(1);
        textView.setIncludeFontPadding(false);
        try {
            textView.setTextColor(Color.parseColor("#666666"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        textView.setTextSize(10);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(ScreenUtil.dp2px(mContext, 4));
        try {
            gd.setStroke(ScreenUtil.dp2px(mContext, 0.5F), Color.parseColor("#DCDCDC"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        textView.setBackground(gd);
        textView.setPadding(ScreenUtil.dp2px(mContext, 4), ScreenUtil.dp2px(mContext, 2), ScreenUtil.dp2px(mContext, 4), ScreenUtil.dp2px(mContext, 2));
        return textView;
    }

    /**
     * 创建图集View
     *
     * @param picUrls
     * @param position
     * @return
     */
    private int picImageWidth;
    private View createGameInfoPicView(List<String> picUrls, int position) {
        String url = picUrls.get(position);
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        int mImageHeight = mFixHeight;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, mImageHeight);
        imageView.setLayoutParams(params);

        imageView.setImageResource(R.mipmap.img_placeholder_h);
        GlideApp.with(mContext)
                .asBitmap()
                .load(url)
                .centerCrop()
                .placeholder(R.mipmap.img_placeholder_h)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        int bitWidth = bitmap.getWidth();
                        int bitHeight = bitmap.getHeight();

                        imageView.setImageBitmap(bitmap);

                        int mImageWidth = (bitWidth * mImageHeight) / bitHeight;

                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                        params.gravity = Gravity.CENTER_VERTICAL;
                        params.width = mImageWidth;
                        params.height = mImageHeight;
                        picImageWidth = mImageWidth;
                        imageView.setLayoutParams(params);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable drawable) {

                    }
                });
        imageView.setOnClickListener(v -> {
            if (picUrls == null) {
                return;
            }
            //预览图片
            ArrayList<Image> images = new ArrayList();
            for (String path : picUrls) {
                Image image = new Image();
                image.setType(1);
                image.setHigh_path(path);
                image.setPath(path);
                images.add(image);
            }
            if (_mFragment != null) {
                PreviewActivity.openActivity(_mFragment.getActivity(), images, true, position, true);
            }
        });
        return imageView;
    }

    /**
     * 标示点适配器
     */
    protected class IndicatorAdapter extends RecyclerView.Adapter {

        private Drawable mSelectedDrawable;
        private Drawable mUnselectedDrawable;

        private int bannerSize;
        private int currentPosition = 0;

        private int indicatorMargin;//指示器间距

        public IndicatorAdapter(int bannerSize) {
            this.bannerSize = bannerSize;
            indicatorMargin = dp2px(5);
            if (mSelectedDrawable == null) {
                //绘制默认选中状态图形
                GradientDrawable selectedGradientDrawable = new GradientDrawable();
                selectedGradientDrawable.setShape(GradientDrawable.RECTANGLE);
                selectedGradientDrawable.setColor(Color.parseColor("#54A6FE"));
                selectedGradientDrawable.setSize(dp2px(10), dp2px(3));
                selectedGradientDrawable.setCornerRadius(dp2px(90));
                mSelectedDrawable = new LayerDrawable(new Drawable[]{selectedGradientDrawable});
            }
            if (mUnselectedDrawable == null) {
                //绘制默认未选中状态图形
                GradientDrawable unSelectedGradientDrawable = new GradientDrawable();
                unSelectedGradientDrawable.setShape(GradientDrawable.RECTANGLE);
                unSelectedGradientDrawable.setColor(Color.parseColor("#FFFFFF"));
                unSelectedGradientDrawable.setSize(dp2px(10), dp2px(3));
                unSelectedGradientDrawable.setCornerRadius(dp2px(90));
                mUnselectedDrawable = new LayerDrawable(new Drawable[]{unSelectedGradientDrawable});
            }
        }

        public void setPosition(int currentPosition) {
            this.currentPosition = currentPosition;
        }

        public void notifyIndicator(int bannerSize) {
            this.bannerSize = bannerSize;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImageView bannerPoint = new ImageView(mContext);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(indicatorMargin, indicatorMargin, indicatorMargin, indicatorMargin);
            bannerPoint.setLayoutParams(lp);
            return new RecyclerView.ViewHolder(bannerPoint) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ImageView bannerPoint = (ImageView) holder.itemView;
            bannerPoint.setBackground(currentPosition == position ? mSelectedDrawable : mUnselectedDrawable);
        }

        @Override
        public int getItemCount() {
            return bannerSize;
        }
    }

    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    /**
     * 创建new item
     *
     * @param gameActivityVo
     * @return
     */
    private List<GameActivityVo.ItemBean> createNewsBeans(GameActivityVo gameActivityVo, String status) {
        List<GameActivityVo.ItemBean> topMenuInfoBeanList = new ArrayList<>();

        if (gameActivityVo.getActivity() == null) {
            return topMenuInfoBeanList;
        }

        for (GameInfoVo.NewslistBean newslistBean : gameActivityVo.getActivity()) {
            if (newslistBean.getNews_status().equals(status)){
                GameActivityVo.ItemBean itemBean = new GameActivityVo.ItemBean();
                itemBean.setType(1);
                itemBean.setGemeId(gameActivityVo.getGameid());
                if (gameActivityVo.getTrial_info() != null){
                    itemBean.setTid(gameActivityVo.getTrial_info().getTid());
                }
                itemBean.setNewslistBean(newslistBean);
                topMenuInfoBeanList.add(itemBean);
            }
        }
        return topMenuInfoBeanList;
    }

    class MyAdapter extends RecyclerView.Adapter {

        private GameInfoVo item;
        private List<String> picUrls;
        public MyAdapter(GameInfoVo item, List<String> picUrls){
            this.item = item;
            this.picUrls = picUrls;
        }
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            if (i == 0){
                return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_game_exhibition, viewGroup, false));
            }else {
                return new MyViewHolder1(LayoutInflater.from(mContext).inflate(R.layout.item_game_exhibition1, viewGroup, false));
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            MyExhibition myExhibition = viewList.get(i);
            if (viewHolder instanceof MyViewHolder){
                View videoView = createMiniVideoView(item.getVideo_pic(), item.getVideo_url());
                FrameLayout.LayoutParams childParams = (FrameLayout.LayoutParams) videoView.getLayoutParams();
                ((MyViewHolder) viewHolder).mLlContent.removeAllViews();
                ((MyViewHolder) viewHolder).mLlContent.addView(videoView, childParams);

                String selectBgColor = myExhibition.getColor();
                String startColor = "#00" + selectBgColor.substring(1);
                String centerColor = "#80" + selectBgColor.substring(1);
                String endColor = "#FF" + selectBgColor.substring(1);

                GradientDrawable gd = new GradientDrawable();
                gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                gd.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                gd.setColors(new int[]{Color.parseColor(startColor), Color.parseColor(centerColor), Color.parseColor(endColor)});
                ((MyViewHolder) viewHolder).mViewTop.setBackground(gd);

                GradientDrawable gd1 = new GradientDrawable();
                gd1.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                gd1.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                gd1.setColors(new int[]{Color.parseColor(endColor), Color.parseColor(centerColor), Color.parseColor(startColor)});
                ((MyViewHolder) viewHolder).mViewBottom.setBackground(gd1);
            }else {
                int index;
                if(viewList.size() == picUrls.size()){
                    index = i;
                }else {
                    index = i -1;
                }
                View childView = createGameInfoPicView(picUrls, index);
                LinearLayout.LayoutParams childParams = (LinearLayout.LayoutParams) childView.getLayoutParams();
                childParams.width = picImageWidth;
                if (index == picUrls.size() - 1){
                    childParams.setMargins((int) (8 * density), 0, (int) (8 * density * picUrls.size()), 0);

                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) ((MyViewHolder1) viewHolder).mViewTop.getLayoutParams();
                    layoutParams.width = picImageWidth;
                    layoutParams.setMargins((int) (8 * density), 0, (int) (8 * density * picUrls.size()), 0);
                    ((MyViewHolder1) viewHolder).mViewTop.setLayoutParams(layoutParams);

                    ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) ((MyViewHolder1) viewHolder).mViewBottom.getLayoutParams();
                    layoutParams1.width = picImageWidth;
                    layoutParams1.setMargins((int) (8 * density), 0, (int) (8 * density * picUrls.size()), 0);
                    ((MyViewHolder1) viewHolder).mViewBottom.setLayoutParams(layoutParams1);
                }else {
                    childParams.setMargins((int) (8 * density), 0, 0, 0);
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) ((MyViewHolder1) viewHolder).mViewTop.getLayoutParams();
                    layoutParams.width = picImageWidth;
                    layoutParams.setMargins((int) (8 * density), 0, 0, 0);
                    ((MyViewHolder1) viewHolder).mViewTop.setLayoutParams(layoutParams);

                    ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) ((MyViewHolder1) viewHolder).mViewBottom.getLayoutParams();
                    layoutParams1.width = picImageWidth;
                    layoutParams1.setMargins((int) (8 * density), 0, 0, 0);
                    ((MyViewHolder1) viewHolder).mViewBottom.setLayoutParams(layoutParams1);
                }
                ((MyViewHolder1) viewHolder).mLlContent.removeAllViews();
                ((MyViewHolder1) viewHolder).mLlContent.addView(childView, childParams);

                String selectBgColor = myExhibition.getColor();
                String startColor = "#00" + selectBgColor.substring(1);
                String centerColor = "#80" + selectBgColor.substring(1);
                String endColor = "#FF" + selectBgColor.substring(1);

                GradientDrawable gd = new GradientDrawable();
                gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                gd.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                gd.setColors(new int[]{Color.parseColor(startColor), Color.parseColor(centerColor), Color.parseColor(endColor)});
                ((MyViewHolder1) viewHolder).mViewTop.setBackground(gd);

                GradientDrawable gd1 = new GradientDrawable();
                gd1.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                gd1.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                gd1.setColors(new int[]{Color.parseColor(endColor), Color.parseColor(centerColor), Color.parseColor(startColor)});
                ((MyViewHolder1) viewHolder).mViewBottom.setBackground(gd1);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List payloads) {
            super.onBindViewHolder(holder, position, payloads);
            if (payloads != null && payloads.size() > 0){
                if ("changeBg".equals(payloads.get(0))){
                    MyExhibition myExhibition = viewList.get(position);
                    if (holder instanceof MyViewHolder){
                        String selectBgColor = myExhibition.getColor();
                        String startColor = "#00" + selectBgColor.substring(1);
                        String centerColor = "#80" + selectBgColor.substring(1);
                        String endColor = "#FF" + selectBgColor.substring(1);

                        GradientDrawable gd = new GradientDrawable();
                        gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                        gd.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                        gd.setColors(new int[]{Color.parseColor(startColor), Color.parseColor(centerColor), Color.parseColor(endColor)});
                        ((MyViewHolder) holder).mViewTop.setBackground(gd);

                        GradientDrawable gd1 = new GradientDrawable();
                        gd1.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                        gd1.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                        gd1.setColors(new int[]{Color.parseColor(endColor), Color.parseColor(centerColor), Color.parseColor(startColor)});
                        ((MyViewHolder) holder).mViewBottom.setBackground(gd1);
                    }else {
                        String selectBgColor = myExhibition.getColor();
                        String startColor = "#00" + selectBgColor.substring(1);
                        String centerColor = "#80" + selectBgColor.substring(1);
                        String endColor = "#FF" + selectBgColor.substring(1);

                        GradientDrawable gd = new GradientDrawable();
                        gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                        gd.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                        gd.setColors(new int[]{Color.parseColor(startColor), Color.parseColor(centerColor), Color.parseColor(endColor)});
                        ((MyViewHolder1) holder).mViewTop.setBackground(gd);

                        GradientDrawable gd1 = new GradientDrawable();
                        gd1.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                        gd1.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                        gd1.setColors(new int[]{Color.parseColor(endColor), Color.parseColor(centerColor), Color.parseColor(startColor)});
                        ((MyViewHolder1) holder).mViewBottom.setBackground(gd1);
                    }
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if ("video".equals(viewList.get(position).getType())){
                return 0;
            }else {
                return 1;
            }
        }

        @Override
        public int getItemCount() {
            return viewList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            private LinearLayout mLlContent;
            private View mViewTop;
            private View mViewBottom;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                mLlContent = itemView.findViewById(R.id.ll_content);
                mViewTop = itemView.findViewById(R.id.view_top);
                mViewBottom = itemView.findViewById(R.id.view_bottom);
            }
        }

        class MyViewHolder1 extends RecyclerView.ViewHolder{
            private LinearLayout mLlContent;
            private View mViewTop;
            private View mViewBottom;

            public MyViewHolder1(@NonNull View itemView) {
                super(itemView);
                mLlContent = itemView.findViewById(R.id.ll_content);
                mViewTop = itemView.findViewById(R.id.view_top);
                mViewBottom = itemView.findViewById(R.id.view_bottom);
            }
        }
    }

    private class MyExhibition{
        private String type;
        private String color;

        public MyExhibition(String type, String color) {
            this.type = type;
            this.color = color;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }


}
