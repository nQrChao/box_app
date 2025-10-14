package com.zqhy.app.core.view.user;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.box.other.hjq.toast.Toaster;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zqhy.app.Setting;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;
import com.zqhy.app.core.data.model.message.MessageInfoVo;
import com.zqhy.app.core.data.model.message.MessageListVo;
import com.zqhy.app.core.data.model.share.InviteDataVo;
import com.zqhy.app.core.data.model.user.AdSwiperListVo;
import com.zqhy.app.core.data.model.user.SuperVipMemberInfoVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.data.model.user.UserVoucherVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.activity.MainActivityFragment;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.community.task.TaskCenterFragment;
import com.zqhy.app.core.view.community.user.CommunityUserFragment;
import com.zqhy.app.core.view.game.GameDownloadManagerFragment;
import com.zqhy.app.core.view.invite.InviteFriendFragment;
import com.zqhy.app.core.view.kefu.FeedBackFragment;
import com.zqhy.app.core.view.main.new_game.NewGameAppointmentFragment;
import com.zqhy.app.core.view.message.MessageMainFragment;
import com.zqhy.app.core.view.rebate.RebateMainFragment;
import com.zqhy.app.core.view.recycle_new.XhNewRecycleMainFragment;
import com.zqhy.app.core.view.user.newvip.NewUserVipFragment;
import com.zqhy.app.core.view.user.provincecard.NewProvinceCardFragment;
import com.zqhy.app.core.view.user.welfare.MyCardListFragment;
import com.zqhy.app.core.view.user.welfare.MyCouponsListFragment;
import com.zqhy.app.core.view.user.welfare.MyFavouriteGameListFragment;
import com.zqhy.app.core.vm.kefu.KefuViewModel;
import com.zqhy.app.db.table.message.MessageDbInstance;
import com.zqhy.app.db.table.message.MessageVo;
import com.zqhy.app.glide.GlideCircleTransform;
import com.zqhy.app.glide.GlideRoundTransformNew;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.share.ShareHelper;
import com.zqhy.app.utils.sp.SPUtils;

import java.util.List;

/**
 * @author pc
 * @time 2019/11/12 15:20
 * @description
 */
public class NewUserMineFragment extends BaseFragment<KefuViewModel> {

    public static final String SP_MESSAGE = "SP_MESSAGE";
    public static final String TAG_DYNAMIC_GAME_MESSAGE_LOG_TIME = "TAG_DYNAMIC_GAME_MESSAGE_LOG_TIME";

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_USER_MINE_STATE;
    }

    @Override
    protected String getUmengPageName() {
        return "我的";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user_mine_new;
    }

    @Override
    public int getContentResId() {
        return R.id.container;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        setStatusBar(0x00cccccc);
        showSuccess();
        bindViews();
        getUserVoucherCount();
        getKefuMessageData();
        getAdSwiperList();
        //initSuperData();
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NestedScrollView mContainer;
    private FrameLayout mFlMessage;
    private TextView mViewMessageTips;
    private ImageView mIvSetting;
    private ImageView mIvIcon;
    private ImageView mIvVipIcon;
    private LinearLayout mLlLayoutLogin;
    private TextView mTvNickname;
    private TextView mTvUsername;
    private TextView mTvRealNameStatus;
    private TextView mTvBindPhone;
    private LinearLayout mLlLayoutNoLogin;
    private LinearLayout mLlPtb;
    private TextView mTvPtbCount;
    private TextView mTvGive;
    private ImageView mIvGive;
    private LinearLayout mLlGold;
    private TextView mTvGoldCount;
    private LinearLayout mLlCoupon;
    private TextView mTvCouponCount;
    private RelativeLayout mRlNewVip;
    private TextView mTvNewVipStatus;
    private RelativeLayout mRlProvince;
    private TextView mTvProvinceStatus;
    private Banner         mBanner;
    private TextView       mTvGames;
    private TextView mTvGiftBag;
    private TextView mTvSignIn;
    private TextView mTvSign;
    private TextView mTvServer;
    private TextView mTvUserMain;
    private RelativeLayout mRlTask;
    private RelativeLayout mRlApply;
    private RelativeLayout mRlRecycle;
    private RelativeLayout mRlInvite;
    private RelativeLayout mRlCdk;
    private RelativeLayout mRlNotice;
    private RelativeLayout mRlFeedback;
    private ConstraintLayout mClVip;
    private ConstraintLayout mClProvinceCard;
    private ConstraintLayout mClOtherCard;

    private void bindViews() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mContainer = findViewById(R.id.container);

        mFlMessage = findViewById(R.id.fl_message);
        mViewMessageTips = findViewById(R.id.view_message_tips);
        mIvSetting = findViewById(R.id.iv_setting);
        //if(BuildConfig.APP_TEMPLATE == 9999){
        //    mIvSetting.setVisibility(View.INVISIBLE);
        //}
        mIvIcon = findViewById(R.id.iv_icon);
        mIvVipIcon = findViewById(R.id.iv_vip_icon);
        mLlLayoutLogin = findViewById(R.id.ll_layout_login);
        mTvNickname = findViewById(R.id.tv_nickname);
        mTvUsername = findViewById(R.id.tv_username);
        mTvRealNameStatus = findViewById(R.id.tv_real_name_status);
        mTvBindPhone = findViewById(R.id.tv_bind_phone);
        mLlLayoutNoLogin = findViewById(R.id.ll_layout_no_login);
        mLlPtb = findViewById(R.id.ll_ptb);
        mTvPtbCount = findViewById(R.id.tv_ptb_count);
        mTvGive = findViewById(R.id.tv_give);
        mIvGive = findViewById(R.id.iv_give);
        mLlGold = findViewById(R.id.ll_gold);
        mTvGoldCount = findViewById(R.id.tv_gold_count);
        mLlCoupon = findViewById(R.id.ll_coupon);
        mTvCouponCount = findViewById(R.id.tv_coupon_count);
        mRlNewVip = findViewById(R.id.rl_new_vip);
        mRlProvince = findViewById(R.id.rl_province);
        mTvNewVipStatus = findViewById(R.id.tv_new_vip_status);
        mTvProvinceStatus = findViewById(R.id.tv_province_status);
        mBanner = findViewById(R.id.banner);
        mTvGames = findViewById(R.id.tv_games);
        mTvGiftBag = findViewById(R.id.tv_giftbag);
        mTvSignIn = findViewById(R.id.tv_sign_in);
        mTvSign = findViewById(R.id.tv_sign);
        mTvServer = findViewById(R.id.tv_server);
        mTvUserMain = findViewById(R.id.tv_user_main);
        mRlTask = findViewById(R.id.rl_task);
        mRlApply = findViewById(R.id.rl_apply);
        mRlRecycle = findViewById(R.id.rL_recycle);
        mRlInvite = findViewById(R.id.rl_invite);
        mRlCdk = findViewById(R.id.rl_cdk);
        mRlNotice = findViewById(R.id.rl_notice);
        mRlFeedback = findViewById(R.id.rl_feedback);
        mClVip = findViewById(R.id.cl_vip);
        mClProvinceCard = findViewById(R.id.cl_province_card);
        mClOtherCard = findViewById(R.id.cl_other_card);

        mSwipeRefreshLayout.setProgressViewOffset(true, -20, 100);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_3478f6,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (UserInfoModel.getInstance().isLogined()){
                if (mViewModel != null) {
                    mViewModel.refreshUserDataWithNotification(data -> {
                        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        if (data != null && data.isNoLogin()) {
                            UserInfoModel.getInstance().logout();
                            checkLogin();
                        }
                    });
                }
            }else{
                if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        mContainer.setOnScrollChangeListener((NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) -> {
            if (scrollY == 0) {
                mSwipeRefreshLayout.setEnabled(true);
            } else {
                mSwipeRefreshLayout.setEnabled(false);
            }
        });

        /*ImageView mIvTopBg = findViewById(R.id.iv_top_bg);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mIvTopBg.getLayoutParams();
        layoutParams.height = ScreenUtil.dp2px(_mActivity, 209f) + ScreenUtil.getStatusBarHeight(_mActivity);
        mIvTopBg.setLayoutParams(layoutParams);*/

        mTvSign.setOnClickListener(v -> {
            /*//签到
            startFragment(TaskCenterFragment.newInstance(false));*/
            //客服在线
            goKefuCenter();
        });

        findViewById(R.id.iv_download).setOnClickListener(view -> {
            //下载
            if (BuildConfig.IS_DOWNLOAD_GAME_FIRST || checkLogin()) {
                startFragment(new GameDownloadManagerFragment());
            }
        });

        mFlMessage.setOnClickListener(view -> {
            //我的消息
            if (checkLogin()) {
                startFragment(new MessageMainFragment());
                showMessageTip(false);
            }
        });
        mIvSetting.setOnClickListener(view -> {
            //个人中心
            //if(BuildConfig.APP_TEMPLATE != 9999){
                startFragment(new UserInfoFragment());
            //}
        });
        mLlPtb.setOnClickListener(view -> {
            //我的平台币
            if (checkLogin()) {
                startFragment(TopUpFragment.newInstance());
            }
        });
        mTvGive.setOnClickListener(v -> {
            //赠币弹窗
            showGiveDialog();
        });
        mIvGive.setOnClickListener(v -> {
            //赠币弹窗
            showGiveDialog();
        });
        mLlGold.setOnClickListener(view -> {
            //赚取金币
            startFragment(TaskCenterFragment.newInstance());
        });
        mLlCoupon.setOnClickListener(view -> {
            //代金券
            if (checkLogin()) {
                //startFragment(GameWelfareFragment.newInstance(2));
                startFragment(new MyCouponsListFragment());
            }
        });
        mRlNewVip.setOnClickListener(view -> startFragment(new NewUserVipFragment()));
        mRlProvince.setOnClickListener(view -> {
            startFragment(NewProvinceCardFragment.newInstance(1));
        });
        mTvGames.setOnClickListener(view -> {
            if (checkLogin()) {
                startFragment(new MyFavouriteGameListFragment());
            }
        });
        mTvGiftBag.setOnClickListener(view -> {
            if (checkLogin()) {
                startFragment(new MyCardListFragment());
            }
        });
        mTvSignIn.setOnClickListener(view -> {
            //代金券
            if (checkLogin()) {
                //startFragment(GameWelfareFragment.newInstance(2));
                startFragment(new MyCouponsListFragment());
            }
        });
        mTvServer.setOnClickListener(view -> {
            /*//客服反馈
            goKefuCenter();*/
            //邀请赚钱
            if (checkLogin()) {
                UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
                if (userInfo.getInvite_type() == 1) {
                    if (mViewModel != null) {
                        mViewModel.getShareInviteData("1", new OnBaseCallback<InviteDataVo>() {

                            @Override
                            public void onBefore() {
                                super.onBefore();
                                loading();
                            }

                            @Override
                            public void onAfter() {
                                super.onAfter();
                                loadingComplete();
                            }

                            @Override
                            public void onSuccess(InviteDataVo data) {
                                if (data != null && data.isStateOK() && data.getData() != null){
                                    if (data.getData().getInvite_info() != null){
                                        InviteDataVo.InviteDataInfoVo inviteInfo = data.getData().getInvite_info();
                                        new ShareHelper(_mActivity).shareToAndroidSystem(inviteInfo.getCopy_title(), inviteInfo.getCopy_description(), inviteInfo.getUrl());
                                    }
                                }
                            }
                        });
                    }
                } else {
                    startFragment(new InviteFriendFragment());
                }
            }
        });
        mTvUserMain.setOnClickListener(view -> {
            if (checkLogin()) {
                int uid = UserInfoModel.getInstance().getUserInfo().getUid();
                startFragment(CommunityUserFragment.newInstance(uid));
            }
        });
        if (Setting.CLOUD_STATUS == 1){
            findViewById(R.id.cl_cloud).setVisibility(View.VISIBLE);
        }else {
            findViewById(R.id.cl_cloud).setVisibility(View.GONE);
        }
        if (!BuildConfig.NEED_BIPARTITION){//头条包不显示云挂机
            findViewById(R.id.cl_cloud).setVisibility(View.VISIBLE);
        }else {
            findViewById(R.id.cl_cloud).setVisibility(View.GONE);
        }
        findViewById(R.id.cl_cloud).setOnClickListener(view -> {
            //云挂机
            //if (checkLogin()) startFragment(CloudVeGuideFragment.newInstance());
            Toaster.show("功能开发中。。。");
        });
        /*if (MemoryInfoUtils.checkBipartition(_mActivity)){
            findViewById(R.id.cl_bipartition).setVisibility(View.VISIBLE);
        }else {
            findViewById(R.id.cl_bipartition).setVisibility(View.GONE);
        }*/
        /*if (!BuildConfig.NEED_BIPARTITION){//头条包不显示双开
            findViewById(R.id.cl_bipartition).setVisibility(View.VISIBLE);
        }else {
            findViewById(R.id.cl_bipartition).setVisibility(View.GONE);
        }
        findViewById(R.id.cl_bipartition).setOnClickListener(view -> {
            //双开
            if (AppUtil.isNotArmArchitecture()){
                startFragment(BipartitionListFragment.newInstance());
            }else {
                Toaster.show("当前设备不支持此功能！");
            }
        });*/

        mRlTask.setOnClickListener(view -> {
            //任务赚金
            startFragment(TaskCenterFragment.newInstance());
        });
        mRlApply.setOnClickListener(view -> {
            //申请返利
            if (checkLogin()) {
                startFragment(new RebateMainFragment());
            }
        });
        mRlRecycle.setOnClickListener(view -> {
            //小号回收
            if (checkLogin()) {
                startFragment(new XhNewRecycleMainFragment());
            }
        });

        if (BuildConfig.IS_REPORT) {//是否为投放包(需要屏蔽掉交易，小号回收)
            mRlRecycle.setVisibility(View.GONE);
            findViewById(R.id.view_recycle).setVisibility(View.GONE);
        }else {
            mRlRecycle.setVisibility(View.VISIBLE);
            findViewById(R.id.view_recycle).setVisibility(View.VISIBLE);
        }

        mRlInvite.setOnClickListener(view -> {
            //邀请赚钱
            if (checkLogin()) {
                UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
                if (userInfo.getInvite_type() == 1) {
                    if (mViewModel != null) {
                        mViewModel.getShareInviteData("1", new OnBaseCallback<InviteDataVo>() {

                            @Override
                            public void onBefore() {
                                super.onBefore();
                                loading();
                            }

                            @Override
                            public void onAfter() {
                                super.onAfter();
                                loadingComplete();
                            }

                            @Override
                            public void onSuccess(InviteDataVo data) {
                                if (data != null && data.isStateOK() && data.getData() != null){
                                    if (data.getData().getInvite_info() != null){
                                        InviteDataVo.InviteDataInfoVo inviteInfo = data.getData().getInvite_info();
                                        new ShareHelper(_mActivity).shareToAndroidSystem(inviteInfo.getCopy_title(), inviteInfo.getCopy_description(), inviteInfo.getUrl());
                                    }
                                }
                            }
                        });
                    }
                } else {
                    startFragment(new InviteFriendFragment());
                }
            }
        });
        mRlCdk.setOnClickListener(view -> {
            if (checkLogin()) {
                showCdkDialog();
            }
        });
        mRlNotice.setOnClickListener(view -> {
            //活动&公告
            startFragment(MainActivityFragment.newInstance("公告快讯"));
        });
        mRlFeedback.setOnClickListener(view -> {
            //意见反馈
            if (checkLogin()) {
                startFragment(new FeedBackFragment());
            }
        });
        findViewById(R.id.rl_service).setOnClickListener(view -> {
            //客服中心
            goKefuCenter();
        });
        findViewById(R.id.tv_user_agreement).setOnClickListener(v -> {
            //用户协议
            goUserAgreement();
        });
        findViewById(R.id.tv_privacy_agreement).setOnClickListener(v -> {
            //隐私协议
            goPrivacyAgreement();
        });
        findViewById(R.id.tv_anti_addiction).setOnClickListener(v -> {
            //防沉迷
            BrowserActivity.newInstance(_mActivity, "https://mobile.tsyule.cn/index.php/Index/view/?id=82773", false);
        });
        mClVip.setOnClickListener(v -> {
            startFragment(new NewUserVipFragment());
        });
        mClProvinceCard.setOnClickListener(v -> {
            startFragment(NewProvinceCardFragment.newInstance(1));
        });
        mClOtherCard.setOnClickListener(v -> {
            startFragment(NewProvinceCardFragment.newInstance(2));
        });
        findViewById(R.id.cl_activi_card).setOnClickListener(v -> {
            if (UserInfoModel.getInstance().isLogined()){
                BrowserActivity.newInstance(_mActivity, "https://hd.tsyule.cn/index.php/center?show_app=100&tgid=" + UserInfoModel.getInstance().getUserInfo().getTgid(), true);
            }else {
                BrowserActivity.newInstance(_mActivity, "https://hd.tsyule.cn/index.php/center?show_app=100", false);
            }
        });
        findViewById(R.id.iv_tips).setOnClickListener(view -> {
            startFragment(new NewGameAppointmentFragment());
        });
        initUser();
    }



    private void initUser(){
        UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
        if (userInfoBean != null) {
            mLlLayoutLogin.setVisibility(View.VISIBLE);
            mTvUserMain.setVisibility(View.VISIBLE);
            mLlLayoutNoLogin.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(userInfoBean.getUser_icon())) {
                GlideApp.with(_mActivity)
                        .asBitmap()
                        .load(userInfoBean.getUser_icon())
                        .placeholder(R.mipmap.ic_user_login_new_sign)
                        .error(R.mipmap.ic_user_login_new_sign)
                        .transform(new GlideCircleTransform(_mActivity, (int) (3 * ScreenUtil.getScreenDensity(_mActivity))))
                        .into(mIvIcon);
            } else {
                mIvIcon.setImageResource(R.mipmap.ic_user_login_new_sign);
            }
            mTvNickname.setText(userInfoBean.getUser_nickname());
            mTvUsername.setText("账号：" + userInfoBean.getUsername());
            if (TextUtils.isEmpty(userInfoBean.getMobile())) {
                mTvBindPhone.setText("手机绑定：未绑，点击绑定");
                mTvBindPhone.setOnClickListener(view -> {
                    startFragment(BindPhoneFragment.newInstance(false, ""));
                });
            } else {
                if (userInfoBean.getMobile().length() > 8){
                    mTvBindPhone.setText("手机绑定：" + userInfoBean.getMobile().replace(userInfoBean.getMobile().substring(3, 8), "*****"));
                }
                //mTvBindPhone.setText("手机绑定：" + userInfoBean.getMobile());
                mTvBindPhone.setOnClickListener(null);
            }
            if (TextUtils.isEmpty(userInfoBean.getReal_name()) || TextUtils.isEmpty(userInfoBean.getIdcard())){
                mTvRealNameStatus.setText("未实名");
                mTvRealNameStatus.setTextColor(Color.parseColor("#595674"));
                mTvRealNameStatus.setBackgroundResource(R.drawable.shape_white_big_radius);
            }else{
                mTvRealNameStatus.setText("已实名");
                mTvRealNameStatus.setTextColor(Color.parseColor("#595674"));
                mTvRealNameStatus.setBackgroundResource(R.drawable.shape_8c879a_big_radius);
            }
            //设置平台币
            mTvPtbCount.setText(String.valueOf(userInfoBean.getPingtaibi()));
            mTvGive.setText("含赠币：" + userInfoBean.getPtb_dc());
            //设置金币
            mTvGoldCount.setText(String.valueOf(userInfoBean.getIntegral()));

            mIvIcon.setOnClickListener(view -> {
                //用户个人资料 玩家主页
                if (checkLogin()) {
                    //if(BuildConfig.APP_TEMPLATE != 9999){
                        startFragment(new UserInfoFragment());
                    //}
                }
            });
            mLlLayoutLogin.setOnClickListener(view -> {
                //用户个人资料 玩家主页
                if (checkLogin()) {
                    //if(BuildConfig.APP_TEMPLATE != 9999){
                        startFragment(new UserInfoFragment());
                    //}
                }
            });
            if (userInfoBean.getSuper_user() != null){
                if (userInfoBean.getSuper_user().getStatus().equals("yes")){
                    mIvVipIcon.setImageResource(R.mipmap.ic_vip_open_new);
                    if (userInfoBean.getSuper_user().getSign().equals("yes")){
                        mTvNewVipStatus.setText("已签");
                        mTvNewVipStatus.setTextColor(Color.parseColor("#999999"));
                        mTvNewVipStatus.setBackgroundResource(R.drawable.shape_white_big_radius);
                    }else{
                        mTvNewVipStatus.setText("签到");
                        mTvNewVipStatus.setTextColor(Color.parseColor("#F84329"));
                        mTvNewVipStatus.setBackgroundResource(R.drawable.shape_white_big_radius);
                    }
                }else {
                    mTvNewVipStatus.setText("开通");
                    mTvNewVipStatus.setTextColor(Color.parseColor("#F84329"));
                    mTvNewVipStatus.setBackgroundResource(R.drawable.shape_white_big_radius);
                    mIvVipIcon.setImageResource(R.mipmap.ic_vip_unopen_new);
                }
            }
        }else {
            mLlLayoutLogin.setVisibility(View.GONE);
            mTvUserMain.setVisibility(View.GONE);
            mLlLayoutNoLogin.setVisibility(View.VISIBLE);
            GlideApp.with(_mActivity)
                    .asBitmap()
                    .load(R.mipmap.ic_user_login_new)
                    .placeholder(R.mipmap.ic_user_login_new)
                    .error(R.mipmap.ic_user_login_new)
                    .into(mIvIcon);
            mIvVipIcon.setImageResource(R.mipmap.ic_vip_unopen_new);
            mIvIcon.setOnClickListener(view -> checkLogin());
            mLlLayoutNoLogin.setOnClickListener(view -> checkLogin());
            mTvPtbCount.setText("0");
            mTvGive.setText("含赠币：0");
            mTvGoldCount.setText("0");

            mTvNewVipStatus.setText("开通");
            mTvNewVipStatus.setTextColor(Color.parseColor("#F84329"));
            mTvNewVipStatus.setBackgroundResource(R.drawable.shape_white_big_radius);

            mTvProvinceStatus.setText("开通");
            mTvProvinceStatus.setTextColor(Color.parseColor("#3C66FB"));
            mTvProvinceStatus.setBackgroundResource(R.drawable.shape_white_big_radius);
        }
    }

    private void getAdSwiperList(){
        if (mViewModel != null) {
            mViewModel.getAdSwiperList(new OnBaseCallback<AdSwiperListVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onSuccess(AdSwiperListVo data) {
                    if (data != null) {
                        if (data.isStateOK() && data.getData() != null) {
                            if (data.getData() != null && data.getData().size() > 0){
                                mBanner.setVisibility(View.VISIBLE);
                                ViewGroup.LayoutParams params = mBanner.getLayoutParams();
                                if (params != null) {
                                    params.height = (ScreenUtil.getScreenWidth(_mActivity) - ScreenUtil.dp2px(_mActivity, 20)) * 180 / 710;
                                    mBanner.setLayoutParams(params);
                                }
                                int bannerSize = data.getData().size();
                                //设置banner样式
                                mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                                //设置图片加载器
                                mBanner.setImageLoader(new ImageLoader() {
                                    @Override
                                    public void displayImage(Context context, Object path, ImageView imageView) {
                                        AdSwiperListVo.AdSwiperBean bannerVo = (AdSwiperListVo.AdSwiperBean) path;
                                        GlideApp.with(_mActivity)
                                                .load(bannerVo.getPic())
                                                .placeholder(R.mipmap.img_placeholder_v_load)
                                                .error(R.mipmap.img_placeholder_v_load)
                                                .transform(new GlideRoundTransformNew(_mActivity, 10))
                                                .into(imageView);
                                    }
                                });
                                //设置图片集合
                                mBanner.setImages(data.getData());
                                //设置banner动画效果
                                mBanner.setBannerAnimation(Transformer.Default);
                                //设置自动轮播，默认为true
                                if (bannerSize > 1) {
                                    //设置轮播时间
                                    mBanner.setDelayTime(5000);
                                    mBanner.isAutoPlay(true);
                                } else {
                                    mBanner.isAutoPlay(false);
                                }
                                mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                                //设置指示器位置（当banner模式中有指示器时）
                                mBanner.setIndicatorGravity(BannerConfig.RIGHT);
                                mBanner.setOnBannerListener(new OnBannerListener() {
                                    @Override
                                    public void OnBannerClick(int position) {
                                        AdSwiperListVo.AdSwiperBean adSwiperBean = data.getData().get(position);
                                        if (adSwiperBean != null) {
                                            if (_mActivity != null) {
                                                AppBaseJumpInfoBean appBaseJumpInfoBean = new AppBaseJumpInfoBean(adSwiperBean.getPage_type(), adSwiperBean.getParam());
                                                AppJumpAction appJumpAction = new AppJumpAction(_mActivity);
                                                appJumpAction.jumpAction(appBaseJumpInfoBean);
                                            }
                                        }
                                    }
                                });
                                //banner设置方法全部调用完毕时最后调用
                                mBanner.start();
                            }else {
                                mBanner.setVisibility(View.GONE);
                            }
                        }else {
                            mBanner.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }
    }

    private void initSuperData() {
        if (mViewModel != null) {
            mViewModel.getMoneyCardBaseInfo(new OnBaseCallback<SuperVipMemberInfoVo>() {
                @Override
                public void onSuccess(SuperVipMemberInfoVo data) {
                    if (data != null && data.getData() != null){
                        SuperVipMemberInfoVo.DataBean superViewMember = data.getData();
                        if (data.getData().getDiscount_card_info() != null){
                            SuperVipMemberInfoVo.DataBean.DiscountCardInfo discountCardInfo = data.getData().getDiscount_card_info();
                            if ("yes".equals(discountCardInfo.getIs_active()) && "yes".equals(superViewMember.getOpen_money_card())){
                                if ("yes".equals(superViewMember.getHas_get_reward()) && "yes".equals(discountCardInfo.getHas_get_reward())){
                                    mTvProvinceStatus.setText("已领");
                                    mTvProvinceStatus.setTextColor(Color.parseColor("#999999"));
                                    mTvProvinceStatus.setBackgroundResource(R.drawable.shape_white_big_radius);
                                } else {
                                    mTvProvinceStatus.setText("领取");
                                    mTvProvinceStatus.setTextColor(Color.parseColor("#3C66FB"));
                                    mTvProvinceStatus.setBackgroundResource(R.drawable.shape_white_big_radius);
                                }
                            }else if ("yes".equals(discountCardInfo.getIs_active()) && !"yes".equals(superViewMember.getOpen_money_card())){
                                if ("yes".equals(discountCardInfo.getHas_get_reward())){
                                    mTvProvinceStatus.setText("已领");
                                    mTvProvinceStatus.setTextColor(Color.parseColor("#999999"));
                                    mTvProvinceStatus.setBackgroundResource(R.drawable.shape_white_big_radius);
                                } else {
                                    mTvProvinceStatus.setText("领取");
                                    mTvProvinceStatus.setTextColor(Color.parseColor("#3C66FB"));
                                    mTvProvinceStatus.setBackgroundResource(R.drawable.shape_white_big_radius);
                                }
                            }else if (!"yes".equals(discountCardInfo.getIs_active()) && "yes".equals(superViewMember.getOpen_money_card())){
                                if ("yes".equals(superViewMember.getHas_get_reward())){
                                    mTvProvinceStatus.setText("已领");
                                    mTvProvinceStatus.setTextColor(Color.parseColor("#999999"));
                                    mTvProvinceStatus.setBackgroundResource(R.drawable.shape_white_big_radius);
                                } else {
                                    mTvProvinceStatus.setText("领取");
                                    mTvProvinceStatus.setTextColor(Color.parseColor("#3C66FB"));
                                    mTvProvinceStatus.setBackgroundResource(R.drawable.shape_white_big_radius);
                                }
                            }else {
                                mTvProvinceStatus.setText("开通");
                                mTvProvinceStatus.setTextColor(Color.parseColor("#3C66FB"));
                                mTvProvinceStatus.setBackgroundResource(R.drawable.shape_white_big_radius);
                            }
                        }else {
                            if (superViewMember.getOpen_money_card().equals("yes")){
                                if (superViewMember.getHas_get_reward().equals("yes")){
                                    mTvProvinceStatus.setText("已领");
                                    mTvProvinceStatus.setTextColor(Color.parseColor("#999999"));
                                    mTvProvinceStatus.setBackgroundResource(R.drawable.shape_white_big_radius);
                                }else{
                                    mTvProvinceStatus.setText("领取");
                                    mTvProvinceStatus.setTextColor(Color.parseColor("#3C66FB"));
                                    mTvProvinceStatus.setBackgroundResource(R.drawable.shape_white_big_radius);
                                }
                            }else {
                                mTvProvinceStatus.setText("开通");
                                mTvProvinceStatus.setTextColor(Color.parseColor("#3C66FB"));
                                mTvProvinceStatus.setBackgroundResource(R.drawable.shape_white_big_radius);
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        initUser();
        if (UserInfoModel.getInstance().isLogined()) {
            getUserVoucherCount();
            getKefuMessageData();
            //initSuperData();
            if (mViewModel != null){
                mViewModel.refreshUserDataWithoutNotification(new OnBaseCallback() {
                    @Override
                    public void onSuccess(BaseVo data) {
                        initUser();
                    }
                });
            }
        }else {
            mTvCouponCount.setText("0");
            mTvCouponCount.setVisibility(View.GONE);
        }
    }

    private void getUserVoucherCount() {
        if (mViewModel != null) {
            mViewModel.getUserVoucherCount(new OnBaseCallback<UserVoucherVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onSuccess(UserVoucherVo data) {
                    if (data != null) {
                        if (data.isStateOK() && data.getData() != null) {
                            setCouponCount(data.getData());
                        } else {
                            mTvCouponCount.setText("0");
                            mTvCouponCount.setVisibility(View.GONE);
                        }
                    }else {
                        mTvCouponCount.setText("0");
                        mTvCouponCount.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void setCouponCount(UserVoucherVo.DataBean dataBean){
        if (UserInfoModel.getInstance().isLogined()){
            mTvCouponCount.setText(String.valueOf(dataBean.getVoucher_unused()));
            if (dataBean.getVoucher_unused() <= 0){
                mTvCouponCount.setVisibility(View.GONE);
            }else {
                mTvCouponCount.setVisibility(View.VISIBLE);
            }
        }else {
            mTvCouponCount.setText("0");
            mTvCouponCount.setVisibility(View.GONE);
        }
    }

    private void redeemCode(String card_str) {
        if (mViewModel != null) {
            mViewModel.redeemCode(card_str, new OnBaseCallback<BaseVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            Toaster.show( "兑换成功");
                            getUserVoucherCount();
                            if (mViewModel != null){
                                mViewModel.refreshUserDataWithoutNotification(new OnBaseCallback() {
                                    @Override
                                    public void onSuccess(BaseVo data) {
                                        initUser();
                                    }
                                });
                            }
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }else{
                        Toaster.show( data.getMsg());
                    }
                }
            });
        }
    }

    private void getKefuMessageData() {
        if (mViewModel != null && UserInfoModel.getInstance().isLogined()) {
            Runnable runnable1 = () -> {
                int messageCount = MessageDbInstance.getInstance().getUnReadMessageCount();
                _mActivity.runOnUiThread(() -> {
                    showMessageTip(messageCount > 0);
                });
                MessageVo messageVo = MessageDbInstance.getInstance().getMaxIdMessageVo(1);
                _mActivity.runOnUiThread(() -> {
                    int maxID = 0;
                    if (messageVo != null) {
                        maxID = messageVo.getMessage_id();
                    }
                    mViewModel.getKefuMessageData(getStateEventKey().toString(), maxID, new OnBaseCallback<MessageListVo>() {
                        @Override
                        public void onSuccess(MessageListVo messageListVo) {
                            if (messageListVo != null) {
                                if (messageListVo.isStateOK()) {
                                    saveMessageToDb(messageListVo.getData());
                                }
                            }
                        }
                    });
                    SPUtils spUtils = new SPUtils(_mActivity, SP_MESSAGE);
                    long logTime = spUtils.getLong(TAG_DYNAMIC_GAME_MESSAGE_LOG_TIME, 0);

                    mViewModel.getDynamicGameMessageData(logTime, new OnBaseCallback<MessageListVo>() {
                        @Override
                        public void onSuccess(MessageListVo messageListVo) {
                            if (messageListVo != null) {
                                if (messageListVo.isStateOK() && messageListVo.getData() != null) {
                                    saveMessageToDb(4, messageListVo);
                                }
                            }
                            SPUtils spUtils = new SPUtils(_mActivity, SP_MESSAGE);
                            spUtils.putLong(TAG_DYNAMIC_GAME_MESSAGE_LOG_TIME, System.currentTimeMillis() / 1000);
                        }
                    });
                });
            };
            new Thread(runnable1).start();
        }
    }

    private void saveMessageToDb(List<MessageInfoVo> messageInfoVoList) {
        Runnable runnable = () -> {
            if (messageInfoVoList != null) {
                for (MessageInfoVo messageInfoVo : messageInfoVoList) {
                    MessageVo messageVo = messageInfoVo.transformIntoMessageVo();
                    MessageDbInstance.getInstance().saveMessageVo(messageVo);
                }
                int messageCount = MessageDbInstance.getInstance().getUnReadMessageCount();
                _mActivity.runOnUiThread(() -> {
                    showMessageTip(messageCount > 0);
                });
            }
        };
        new Thread(runnable).start();
    }

    private void saveMessageToDb(int type, MessageListVo messageListVo) {
        Runnable runnable = () -> {
            for (MessageInfoVo messageInfoVo : messageListVo.getData()) {
                MessageVo messageVo = messageInfoVo.transformIntoMessageVo();
                MessageDbInstance.getInstance().saveMessageVo(messageVo);
            }
            int messageCount = MessageDbInstance.getInstance().getUnReadMessageCount();
            _mActivity.runOnUiThread(() -> {
                showMessageTip(messageCount > 0);
            });
        };
        new Thread(runnable).start();
    }

    /**
     * 消息红点提示
     *
     * @param isShow
     */
    private void showMessageTip(boolean isShow) {
        if (mViewMessageTips != null) {
            mViewMessageTips.setVisibility(isShow && UserInfoModel.getInstance().isLogined() ? View.VISIBLE : View.GONE);
        }
    }

    private void showCdkDialog(){
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_user_mine_cdk, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        EditText input = (EditText) tipsDialog.findViewById(R.id.et_input);
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (TextUtils.isEmpty(input.getText().toString().trim())){
                Toaster.show("请输入兑换码");
                return;
            }
            redeemCode(input.getText().toString().trim());
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.findViewById(R.id.iv_close).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.show();
    }

    private void showGiveDialog(){
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_user_mine_give, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        TextView mTvContent = tipsDialog.findViewById(R.id.tv_content);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("福利币是通过平台相关福利活动获得的\n福利币可用游戏查询 >\n游戏内消费时，将优先扣除福利币。");
        spannableStringBuilder.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#0080FF"));
            }
            @Override
            public void onClick(@NonNull View widget) {
                BrowserActivity.newInstance(_mActivity, "https://hd.xiaodianyouxi.com/index.php/usage/gold_game");
            }
        }, 25, 29, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvContent.setText(spannableStringBuilder);
        mTvContent.setMovementMethod(LinkMovementMethod.getInstance());

        tipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.show();
    }
}
