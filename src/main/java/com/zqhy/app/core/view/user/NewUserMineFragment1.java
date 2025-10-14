package com.zqhy.app.core.view.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.GsonUtils;
import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.chaoji.other.blankj.utilcode.util.Logs;
import com.chaoji.other.hjq.toast.Toaster;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zqhy.app.Setting;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameAppointmentOpVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.appointment.UserAppointmentVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;
import com.zqhy.app.core.data.model.jump.JumpBean;
import com.zqhy.app.core.data.model.message.MessageInfoVo;
import com.zqhy.app.core.data.model.message.MessageListVo;
import com.zqhy.app.core.data.model.new_game.UserAppointmentListVo;
import com.zqhy.app.core.data.model.share.InviteDataVo;
import com.zqhy.app.core.data.model.user.AdSwiperListVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.data.model.user.UserVoucherVo;
import com.zqhy.app.core.data.model.welfare.MyFavouriteGameListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.view.AbsPayBuyFragment;
import com.zqhy.app.core.view.activity.MainActivityFragment;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.community.integral.CommunityIntegralMallFragment;
import com.zqhy.app.core.view.community.user.CommunityUserFragment;
import com.zqhy.app.core.view.game.GameDownloadManagerFragment;
import com.zqhy.app.core.view.invite.InviteFriendFragment;
import com.zqhy.app.core.view.kefu.FeedBackFragment;
import com.zqhy.app.core.view.main.holder.UserAppointmentTabItemHolder1;
import com.zqhy.app.core.view.message.MessageMainFragment;
import com.zqhy.app.core.view.recycle_new.XhNewRecycleMainFragment;
import com.zqhy.app.core.view.user.newvip.NewUserVipFragment;
import com.zqhy.app.core.view.user.provincecard.NewProvinceCardFragment;
import com.zqhy.app.core.view.user.welfare.MyCardListFragment;
import com.zqhy.app.core.view.user.welfare.MyCouponsListFragment;
import com.zqhy.app.core.view.user.welfare.holder.FavouriteGameItemHolder1;
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

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pc
 * @time 2019/11/12 15:20
 * @description
 */
public class NewUserMineFragment1 extends AbsPayBuyFragment<KefuViewModel> {

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
        return R.layout.fragment_user_mine_new2;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content;
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
        if (UserInfoModel.getInstance().isLogined()){
            getMyFavouriteGameData();
        }else {
            headerView.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
            mAdapter.clear();
            mAdapter.notifyDataSetChanged();
        }


    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private XRecyclerView mRecyclerView;
    private BaseRecyclerAdapter mAdapter;
    private String listType = "game";
    private FrameLayout mFlMessage;
    private RelativeLayout mTitleBar_Layout;
    private TextView mViewMessageTips;
    private ImageView mIvSetting;
    private ImageView mIvIcon;
    private ImageView mIvVipIcon;
    private LinearLayout mLlLayoutLogin;
    private TextView mTvNickname;
    private TextView mTvUsername;
    private TextView mTvRealNameStatus;
    private TextView mTvBindPhone;
    private ConstraintLayout mClChat;
    private LinearLayout mLlCount;
    private TextView mTvCount;
    private LinearLayout mLlLayoutNoLogin;
    private LinearLayout mLlPtb;
    private TextView mTvPtbCount;
    private TextView mTvGive;
    private LinearLayout mLlCoupon;
    private TextView mTvCouponCount;
    private Banner         mBanner;
    private TextView mTvUserMain;
    private ConstraintLayout mClVip;
    private ConstraintLayout mClProvinceCard;
    private RecyclerView mRecyclerViewMenu;

    @SuppressLint("NotifyDataSetChanged")
    private void bindViews() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = findViewById(R.id.recycler_view_me_new2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));

        mAdapter = new BaseRecyclerAdapter.Builder()
                .bind(UserAppointmentVo.class, new UserAppointmentTabItemHolder1(_mActivity))
                .bind(GameInfoVo.class, new FavouriteGameItemHolder1(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this)
                .setTag(R.id.tag_sub_fragment, this);

        mRecyclerView.setAdapter(mAdapter);
        initHeaderView();

        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                if (UserInfoModel.getInstance().isLogined()){
                    if ("game".equals(listType)){
                        if (gamePage < 0) {
                            if (mRecyclerView != null) {
                                mRecyclerView.refreshComplete();
                                mRecyclerView.loadMoreComplete();
                            }
                            return;
                        }
                        gamePage++;
                        getMyFavouriteGameData();
                    }else {
                        if (appointmentPage < 0) {
                            if (mRecyclerView != null) {
                                mRecyclerView.refreshComplete();
                                mRecyclerView.loadMoreComplete();
                            }
                            return;
                        }
                        appointmentPage++;
                        getNewGameAppointmentGameList();
                    }
                }else {
                    if (mRecyclerView != null) {
                        mRecyclerView.refreshComplete();
                        mRecyclerView.loadMoreComplete();
                    }
                }
            }
        });


//        mTitleBar_Layout = headerView.findViewById(R.id.titleBar_Layout);
//        ImmersionBar.with(this).fullScreen(true).init();
//        ImmersionBar.setTitleBar(_mActivity,mTitleBar_Layout);
        mFlMessage = headerView.findViewById(R.id.fl_message);
        mViewMessageTips = headerView.findViewById(R.id.view_message_tips);
        mIvSetting = headerView.findViewById(R.id.iv_setting);
        //if(BuildConfig.APP_TEMPLATE == 9999){
        //    mIvSetting.setVisibility(View.GONE);
        //}
        mIvIcon = headerView.findViewById(R.id.iv_icon);
        mIvVipIcon = headerView.findViewById(R.id.iv_vip_icon);
        mLlLayoutLogin = headerView.findViewById(R.id.ll_layout_login);
        mTvNickname = headerView.findViewById(R.id.tv_nickname);
        mTvUsername = headerView.findViewById(R.id.tv_username);
        mTvRealNameStatus = headerView.findViewById(R.id.tv_real_name_status);
        mTvBindPhone = headerView.findViewById(R.id.tv_bind_phone);
        mLlLayoutNoLogin = headerView.findViewById(R.id.ll_layout_no_login);
        mClChat = headerView.findViewById(R.id.cl_chat);
        mLlCount = headerView.findViewById(R.id.ll_count);
        mTvCount = headerView.findViewById(R.id.tv_count);
        mLlPtb = headerView.findViewById(R.id.ll_ptb);
        mTvPtbCount = headerView.findViewById(R.id.tv_ptb_count);
        mTvGive = headerView.findViewById(R.id.tv_give);
        mLlCoupon = headerView.findViewById(R.id.ll_coupon);
        mTvCouponCount = headerView.findViewById(R.id.tv_coupon_count);
        mBanner = headerView.findViewById(R.id.banner);
        mTvUserMain = headerView.findViewById(R.id.tv_user_main);
        mClVip = headerView.findViewById(R.id.cl_vip);
        mClProvinceCard = headerView.findViewById(R.id.cl_province_card);

        mRecyclerViewMenu = headerView.findViewById(R.id.recycler_view_menu);

        mSwipeRefreshLayout.setProgressViewOffset(true, -20, 100);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_3478f6,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

//        mSwipeRefreshLayout.setOnRefreshListener(() -> {
//            if (UserInfoModel.getInstance().isLogined()){
//                if (mViewModel != null) {
//                    mViewModel.refreshUserDataWithNotification(data -> {
//                        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
//                            mSwipeRefreshLayout.setRefreshing(false);
//                        }
//                        if (data != null && data.isNoLogin()) {
//                            UserInfoModel.getInstance().logout();
//                            checkLogin();
//                        }
//                    });
//                }
//                gameList.clear();
//                gameAppointmentList.clear();
//                listType = "";
//                gamePage = 1;
//                appointmentPage = 1;
//                headerView.findViewById(R.id.ll_games).performClick();
//
//            }else{
//                if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
//                    mSwipeRefreshLayout.setRefreshing(false);
//                }
//            }
//        });

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (UserInfoModel.getInstance().isLogined()){
                if (mViewModel != null) {
                    mViewModel.refreshUserDataWithNotification(data -> {
                        // 确保在主线程更新 UI
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(() -> {
                                if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                                    mSwipeRefreshLayout.setRefreshing(false); // 停止刷新动画
                                }
                                if (data != null) {
                                    if (data.isNoLogin()) {
                                        UserInfoModel.getInstance().logout();
                                        checkLogin();
                                    } else {
                                        gameList.clear();
                                        gameAppointmentList.clear();
                                        gamePage = 1;
                                        appointmentPage = 1;
                                        if (mAdapter != null) {
                                            mAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            });
                        }
                    });
                }
            }else{
                if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        /*mContainer.setOnScrollChangeListener((NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) -> {
            if (scrollY == 0) {
                mSwipeRefreshLayout.setEnabled(true);
            } else {
                mSwipeRefreshLayout.setEnabled(false);
            }
        });*/

        headerView.findViewById(R.id.iv_download).setOnClickListener(view -> {
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
        mClChat.setOnClickListener(view -> {
            //XKitRouter.withKey(RouterConstant.PATH_CONVERSATION_PAGE).withContext(_mActivity).navigate();
        });
        mLlPtb.setOnClickListener(view -> {
            //我的平台币
            if (checkLogin()) {
                startFragment(TopUpFragment.newInstance());
            }
        });
        headerView.findViewById(R.id.ll_ptb1).setOnClickListener(view -> {
            //我的平台币
            if (checkLogin()) {
                startFragment(TopUpFragment.newInstance());
            }
        });
        headerView.findViewById(R.id.ll_give).setOnClickListener(v -> {
            //赠币弹窗
            showGiveDialog();
        });
        mLlCoupon.setOnClickListener(view -> {
            //代金券
            if (checkLogin()) {
                //startFragment(GameWelfareFragment.newInstance(2));
                startFragment(new MyCouponsListFragment());
            }
        });
        headerView.findViewById(R.id.ll_coupon1).setOnClickListener(view -> {
            //代金券
            if (checkLogin()) {
                //startFragment(GameWelfareFragment.newInstance(2));
                startFragment(new MyCouponsListFragment());
            }
        });

        mTvUserMain.setOnClickListener(view -> {
            if (checkLogin()) {
                int uid = UserInfoModel.getInstance().getUserInfo().getUid();
                startFragment(CommunityUserFragment.newInstance(uid));
            }
        });
        if (Setting.CLOUD_STATUS == 1){
            headerView.findViewById(R.id.tv_cloud).setVisibility(View.VISIBLE);
            headerView.findViewById(R.id.view_clound).setVisibility(View.VISIBLE);
        }else {
            headerView.findViewById(R.id.tv_cloud).setVisibility(View.GONE);
            headerView.findViewById(R.id.view_clound).setVisibility(View.GONE);
        }
        /*if (!BuildConfig.NEED_BIPARTITION){//头条包不显示云挂机和双开
            headerView.findViewById(R.id.ll_cloud).setVisibility(View.VISIBLE);
        }else {
            headerView.findViewById(R.id.ll_cloud).setVisibility(View.GONE);
        }*/
        /*headerView.findViewById(R.id.tv_cloud).setOnClickListener(view -> {
            //云挂机
            if (checkLogin()) startFragment(CloudVeGuideFragment.newInstance());
        });
        headerView.findViewById(R.id.tv_bipartition).setOnClickListener(view -> {
            //双开
            if (AppUtil.isNotArmArchitecture()){
                startFragment(BipartitionListFragment.newInstance());
            }else {
                Toaster.show("当前设备不支持此功能！");
            }
        });*/

        headerView.findViewById(R.id.ll_games).setOnClickListener(view -> {
            if (listType == "game") return;
            listType = "game";

            headerView.findViewById(R.id.view_game).setVisibility(View.VISIBLE);
            headerView.findViewById(R.id.view_appointment).setVisibility(View.INVISIBLE);
            ((TextView) headerView.findViewById(R.id.tv_game)).setTextColor(Color.parseColor("#000000"));
            ((TextView) headerView.findViewById(R.id.tv_appointment)).setTextColor(Color.parseColor("#666666"));

            if (UserInfoModel.getInstance().isLogined()){
                headerView.findViewById(R.id.ll_empty).setVisibility(View.GONE);

                if (gameList.size() == 0){
                    getMyFavouriteGameData();
                }else {
                    mAdapter.setDatas(gameList);
                    mAdapter.notifyDataSetChanged();
                }
            }else {
                headerView.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
                mAdapter.clear();
                mAdapter.notifyDataSetChanged();
            }
        });
        headerView.findViewById(R.id.ll_appointment).setOnClickListener(view -> {
            if (listType == "appointment") return;
            listType = "appointment";

            headerView.findViewById(R.id.view_game).setVisibility(View.INVISIBLE);
            headerView.findViewById(R.id.view_appointment).setVisibility(View.VISIBLE);
            ((TextView) headerView.findViewById(R.id.tv_game)).setTextColor(Color.parseColor("#666666"));
            ((TextView) headerView.findViewById(R.id.tv_appointment)).setTextColor(Color.parseColor("#000000"));

            if (UserInfoModel.getInstance().isLogined()){
                headerView.findViewById(R.id.ll_empty).setVisibility(View.GONE);

                if (gameAppointmentList.size() == 0){
                    getNewGameAppointmentGameList();
                }else {
                    mAdapter.setDatas(gameAppointmentList);
                    mAdapter.notifyDataSetChanged();
                }
            }else {
                headerView.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
                mAdapter.clear();
                mAdapter.notifyDataSetChanged();
            }
        });
        mClVip.setOnClickListener(v -> {
            startFragment(new NewUserVipFragment());
        });
        mClProvinceCard.setOnClickListener(v -> {
            startFragment(NewProvinceCardFragment.newInstance(1));
        });
        initUser();

        //TODO
        //功能未完全走通，先注释
        initMenu();
    }

    private View headerView;
    private void initHeaderView() {
        headerView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_header_new_user_mine, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ScreenUtil.getScreenWidth(_mActivity), ViewGroup.LayoutParams.WRAP_CONTENT);
        headerView.setLayoutParams(params);
        mRecyclerView.addHeaderView(headerView);
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
            mTvPtbCount.setText(String.format("%.2f", userInfoBean.getPingtaibi() - userInfoBean.getPtb_dc()));
            mTvGive.setText(String.valueOf(userInfoBean.getPtb_dc()));

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
                }else {
                    mIvVipIcon.setImageResource(R.mipmap.ic_vip_unopen_new);
                }
            }
            initChatCount();
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
            mTvGive.setText("0");
            mLlCount.setVisibility(View.GONE);
        }
    }

    private void initChatCount(){
        //只查询排除免打扰之外的消息总未读数
        /*int unreadCount = NIMClient.getService(MsgService.class).getTotalUnreadCount(true);
        if (unreadCount > 0){
            mLlCount.setVisibility(View.VISIBLE);
            if(unreadCount > 99){
                mTvCount.setText("99+");
            }else {
                mTvCount.setText(String.valueOf(unreadCount));
            }
        }else {
            mLlCount.setVisibility(View.GONE);
        }*/
    }

    private void initMenu(){
        List<String> tabs = new ArrayList<>();
        //tabs.add("积分商城");
        //tabs.add("活动中心");
        //if (!BuildConfig.IS_REPORT) tabs.add("小号回收");
        //tabs.add("联系客服");
        //tabs.add("我的礼包");
        tabs.add("CDK兑换");
        tabs.add("邀请好友");
        tabs.add("公告快讯");
        tabs.add("投诉反馈");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerViewMenu.setLayoutManager(linearLayoutManager);
        mRecyclerViewMenu.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new MyViewHolder(LayoutInflater.from(_mActivity).inflate(R.layout.item_new_user_mine_menu_item, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                MyViewHolder viewHolder1 = (MyViewHolder)viewHolder;
                viewHolder1.mTvText.setText(tabs.get(i));
                switch (tabs.get(i)){
                    case "积分商城":
                        viewHolder1.mIvImage.setImageResource(R.mipmap.ic_new_user_mine_menu_1);
                        break;
                    case "活动中心":
                        viewHolder1.mIvImage.setImageResource(R.mipmap.ic_new_user_mine_menu_2);
                        break;
                    case "小号回收":
                        viewHolder1.mIvImage.setImageResource(R.mipmap.ic_new_user_mine_menu_5);
                        break;
                    case "联系客服":
                        viewHolder1.mIvImage.setImageResource(R.mipmap.ic_new_user_mine_menu_3);
                        break;
                    case "公告快讯":
                        viewHolder1.mIvImage.setImageResource(R.mipmap.ic_new_user_mine_menu_9);
                        break;
                    case "我的礼包":
                        viewHolder1.mIvImage.setImageResource(R.mipmap.ic_new_user_mine_menu_4);
                        break;
                    case "CDK兑换":
                        viewHolder1.mIvImage.setImageResource(R.mipmap.ic_new_user_mine_menu_8);
                        break;
                    case "邀请好友":
                        viewHolder1.mIvImage.setImageResource(R.mipmap.ic_new_user_mine_menu_7);
                        break;
                    case "投诉反馈":
                        viewHolder1.mIvImage.setImageResource(R.mipmap.ic_new_user_mine_menu_6);
                        break;
                    default:
                        viewHolder1.mIvImage.setImageResource(R.mipmap.ic_new_user_mine_menu_1);
                        break;
                }
                viewHolder1.itemView.setOnClickListener(view -> {
                    switch (tabs.get(i)){
                        case "积分商城":
                            startFragment(new CommunityIntegralMallFragment());
                            break;
                        case "活动中心":
                            if (UserInfoModel.getInstance().isLogined()){
                                BrowserActivity.newInstance(_mActivity, "https://hd.tsyule.cn/index.php/center?show_app=100&tgid=" + UserInfoModel.getInstance().getUserInfo().getTgid(), true);
                            }else {
                                BrowserActivity.newInstance(_mActivity, "https://hd.tsyule.cn/index.php/center?show_app=100", false);
                            }
                            break;
                        case "小号回收":
                            if (checkLogin()) startFragment(new XhNewRecycleMainFragment());
                            break;
                        case "联系客服":
                            goKefuCenter();
                            break;
                        case "公告快讯":
                            //活动&公告
                            startFragment(MainActivityFragment.newInstance("公告快讯"));
                            break;
                        case "我的礼包":
                            if (checkLogin()) startFragment(new MyCardListFragment());
                            break;
                        case "CDK兑换":
                            if (checkLogin()) showCdkDialog();
                            break;
                        case "邀请好友":
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
                            break;
                        case "投诉反馈":
                            if (checkLogin()) startFragment(new FeedBackFragment());
                            break;
                    }
                });
            }

            @Override
            public int getItemCount() {
                return tabs.size();
            }

            class MyViewHolder extends RecyclerView.ViewHolder{
                private ImageView mIvImage;
                private TextView mTvText;
                public MyViewHolder(@NonNull View itemView) {
                    super(itemView);
                    mIvImage = itemView.findViewById(R.id.iv_image);
                    mTvText = itemView.findViewById(R.id.tv_text);
                }
            }
        });

//        mRecyclerViewMenu.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//                if (layoutManager instanceof LinearLayoutManager){
//                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
//                    int itemCount = linearLayoutManager.getItemCount();
//                    int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
//                    if (itemCount > 0 && lastVisibleItemPosition >= tabs.size() - 1){
//                        headerView.findViewById(R.id.view_dot_1).setBackgroundResource(R.drawable.shape_e3e2e2_big_radius);
//                        headerView.findViewById(R.id.view_dot_2).setBackgroundResource(R.drawable.shape_3797ff_big_radius);
//                    }else {
//                        headerView.findViewById(R.id.view_dot_1).setBackgroundResource(R.drawable.shape_3797ff_big_radius);
//                        headerView.findViewById(R.id.view_dot_2).setBackgroundResource(R.drawable.shape_e3e2e2_big_radius);
//                    }
//                }
//            }
//        });
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

            listType = "";
            gameList.clear();
            gameAppointmentList.clear();
            gamePage = 1;
            appointmentPage = 1;
            headerView.findViewById(R.id.ll_games).performClick();
        }else {
            mTvCouponCount.setText("0张");

            headerView.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
            mAdapter.clear();
            mAdapter.notifyDataSetChanged();
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
                            mTvCouponCount.setText("0张");
                        }
                    }else {
                        mTvCouponCount.setText("0张");
                    }
                }
            });
        }
    }

    private void setCouponCount(UserVoucherVo.DataBean dataBean){
        if (UserInfoModel.getInstance().isLogined()){
            mTvCouponCount.setText(dataBean.getVoucher_unused() + "张");
        }else {
            mTvCouponCount.setText("0张");
        }
    }

    private void redeemCode(String card_str) {
        if (mViewModel != null) {
            mViewModel.redeemCode(card_str, new OnBaseCallback<JumpBean>() {
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
                public void onSuccess(JumpBean data) {
                    Logs.e("JUMBEAN:", GsonUtils.toJson(data));
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
                        }else if(data.isStateJump()){
                            //跳转到绑定手机
                            Toaster.show( "请绑定手机号");
                            AppJumpAction appJumpAction = new AppJumpAction(_mActivity);
                            appJumpAction.jumpAction(GsonUtils.toJson(data.getData()));
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

    private int gamePage      = 1;
    private int gamePageCount = 12;
    private List<GameInfoVo> gameList = new ArrayList<>();
    private void getMyFavouriteGameData() {
        mViewModel.getMyFavouriteGameData(gamePage, gamePageCount, new OnBaseCallback<MyFavouriteGameListVo>() {
            @Override
            public void onAfter() {
                super.onAfter();
                if (mRecyclerView != null) {
                    mRecyclerView.refreshComplete();
                    mRecyclerView.loadMoreComplete();
                }
            }

            @Override
            public void onSuccess(MyFavouriteGameListVo myFavouriteGameListVo) {
                if (myFavouriteGameListVo != null) {
                    if (myFavouriteGameListVo.isStateOK()) {
                        if (myFavouriteGameListVo.getData() != null && !myFavouriteGameListVo.getData().isEmpty()) {
                            if (gamePage == 1) {
                                gameList.clear();
                                gameList.addAll(myFavouriteGameListVo.getData());
                                mAdapter.setDatas(gameList);
                            }else {
                                gameList.addAll(myFavouriteGameListVo.getData());
                            }
                        } else {
                            if (gamePage == 1) {
                                gameList.clear();
                            } else {
                                gamePage = -1;
                            }
                            //mRecyclerView.setNoMore(true);
                            mAdapter.setDatas(gameList);
                        }
                        mAdapter.notifyDataSetChanged();

                        if (gameList.size() == 0){
                            headerView.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toaster.show( myFavouriteGameListVo.getMsg());
                    }
                }
            }
        });
    }

    /**
     * 取消收藏游戏
     *
     * @param gameid
     */
    public void setGameUnFavorite(int gameid) {
        if (mViewModel != null) {
            mViewModel.setGameUnFavorite(gameid, new OnBaseCallback() {
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
                public void onSuccess(BaseVo baseResponseVo) {

                    if (baseResponseVo != null) {
                        if (baseResponseVo.isStateOK()) {
                            Toaster.show( R.string.string_game_cancel_favorite_success);
                            gamePage = 1;
                            getMyFavouriteGameData();
                        } else {
                            Toaster.show( baseResponseVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    private int appointmentPage      = 1;
    private int appointmentPageCount = 12;
    private List<UserAppointmentVo> gameAppointmentList = new ArrayList<>();
    private void getNewGameAppointmentGameList() {
        if (mViewModel != null) {
            mViewModel.getNewGameAppointmentGameList(appointmentPage, appointmentPageCount, new OnBaseCallback<UserAppointmentListVo>() {
                @Override
                public void onSuccess(UserAppointmentListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null && !data.getData().isEmpty()) {
                                if (appointmentPage == 1) {
                                    gameAppointmentList.clear();
                                    gameAppointmentList.addAll(data.getData());
                                    mAdapter.setDatas(gameAppointmentList);
                                }else {
                                    gameAppointmentList.addAll(data.getData());
                                }
                            } else {
                                if (appointmentPage == 1) {
                                    gameAppointmentList.clear();
                                } else {
                                    appointmentPage = -1;
                                }
                                //mRecyclerView.setNoMore(true);
                                mAdapter.setDatas(gameAppointmentList);
                            }
                            mAdapter.notifyDataSetChanged();

                            if (gameAppointmentList.size() == 0){
                                headerView.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    if (mRecyclerView != null) {
                        mRecyclerView.refreshComplete();
                        mRecyclerView.loadMoreComplete();
                    }
                }
            });
        }
    }

    @Override
    public void onEvent(EventCenter eventCenter) {
        super.onEvent(eventCenter);
        if (eventCenter.getEventCode() == EventConfig.ACTION_GAME_APPOINTMENT_EVENT_CODE) {
            appointmentPage = 1;
            getNewGameAppointmentGameList();
        } else if (eventCenter.getEventCode() == EventConfig.NIM_CLIENT_NEW_MESSAGE){
            Log.e("Chat", "new message");
            initChatCount();
        }
    }

    /**
     * 操作说明, reserve: 预约; cancel: 取消预约
     *
     * @param gameid
     * @param item
     */
    public void gameAppointment(int gameid, UserAppointmentVo item) {
        if (mViewModel != null) {
            mViewModel.gameAppointment(gameid, new OnBaseCallback<GameAppointmentOpVo>() {
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
                public void onSuccess(GameAppointmentOpVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                String op = data.getData().getOp();
                                switch (op) {
                                    case "cancel":
                                        //cancelGameAppointmentCalendarReminder(item);
                                        Toaster.show( data.getMsg());
                                        break;
                                }
                            }
                            EventBus.getDefault().post(new EventCenter(EventConfig.ACTION_GAME_APPOINTMENT_EVENT_CODE));
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }


    @Override
    protected int getPayAction() {
        return 0;
    }

    @Override
    protected void onPayCancel() {
        super.onPayCancel();
        Logs.e("(onPayCancel = " + "NewUserMineFragment1-------------------------------");
    }

    @Override
    protected void onPaySuccess() {
        super.onPaySuccess();

        if (mViewModel != null){
            mViewModel.refreshUserDataWithoutNotification(new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    initUser();
                    Logs.e("(onPaySuccess = " + "NewUserMineFragment1-------------------------------");
                }
            });
        }
    }
}
