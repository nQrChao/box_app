package com.zqhy.app.core.view.user.newvip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.chaoji.other.hjq.toast.Toaster;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;
import com.zqhy.app.core.data.model.user.AdSwiperListVo;
import com.zqhy.app.core.data.model.user.PayInfoVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.data.model.user.newvip.BirthdayRewardVo;
import com.zqhy.app.core.data.model.user.newvip.BuyVoucherVo;
import com.zqhy.app.core.data.model.user.newvip.GetRewardkVo;
import com.zqhy.app.core.data.model.user.newvip.SignLuckVo;
import com.zqhy.app.core.data.model.user.newvip.SuperBirthdayRewardVo;
import com.zqhy.app.core.data.model.user.newvip.SuperUserInfoVo;
import com.zqhy.app.core.data.model.user.newvip.VipMenuVo;
import com.zqhy.app.core.data.model.user.newvip.VipPrivilegeVo;
import com.zqhy.app.core.data.model.user.newvip.VipPrivilegeVo1;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.AbsPayBuyFragment;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.community.task.TaskCenterFragment;
import com.zqhy.app.core.view.login.LoginActivity;
import com.zqhy.app.core.view.transaction.sell.TransactionSellFragment;
import com.zqhy.app.core.view.user.CertificationFragment;
import com.zqhy.app.core.view.user.newvip.holder.SuperVipItemHolder;
import com.zqhy.app.core.view.user.newvip.holder.VipCouponItemHolder;
import com.zqhy.app.core.view.user.newvip.holder.VipMenuItemHolder;
import com.zqhy.app.core.view.user.newvip.holder.VipPrivilegeItemHolder;
import com.zqhy.app.core.view.user.newvip.holder.VipPrivilegeItemHolder1;
import com.zqhy.app.core.view.user.newvip.holder.VipRewardItemHolder;
import com.zqhy.app.core.view.user.newvip.holder.VipSignItemHolder;
import com.zqhy.app.core.view.user.newvip.holder.VipVoucherItemHolder;
import com.zqhy.app.core.vm.user.UserViewModel;
import com.zqhy.app.glide.GlideCircleTransform;
import com.zqhy.app.glide.GlideRoundTransformNew;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.utils.sp.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leeham2734
 * @date 2020/11/10-10:32
 * @description
 */
public class NewUserVipFragment extends AbsPayBuyFragment<UserViewModel> implements View.OnClickListener {

    private long fristExpiryTime = -1;//记录会员到期时间

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user_vip_new;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    protected String getUmengPageName() {
        return "VIP页面";
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        bindViews();
        initData();
        if (mViewModel != null) {
            mViewModel.refreshUserDataWithNotification(data -> {
                if (data != null && data.isNoLogin()) {
                    UserInfoModel.getInstance().logout();
                    setUserInfo();
                }
            });
        }
        if (UserInfoModel.getInstance().isLogined()) {
            getSuperBirthdayRewardStatus(false);
        }
        getAdSwiperList();
    }

    @Override
    protected int getPayAction() {
        return PAY_ACTION_VIP_MEMBER;
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageView mIvBack;
    private TextView mTvVipCount;
    private TextView mTvVipCountNew;
    private LinearLayout mLlUserUnLogin;
    private LinearLayout mLlUserVipInfo;
    private ImageView mIvUserIcon;
    private ImageView mIvVipStatus;
    private TextView mTvUserName;
    private TextView mTvUserNickname;
    private TextView mTvUserVipValidity;
    private TextView mTvOpenVip;

    private RelativeLayout mRlOpenTips;

    private TextView mTvGiftbagTips;
    private RecyclerView        mRecyclerViewVipGiftbag;
    private BaseRecyclerAdapter mVipGiftbagAdapter;

    private Toolbar      mToolbar;

    private ViewFlipper mViewFlipper;
    private TextView mTvSignIn;

    private TextView mTvSignTips;
    private LinearLayout mLlSignReward;
    private TextView mTvSignReward;

    private RecyclerView        mRecyclerViewDailyPrivileged;
    private BaseRecyclerAdapter mDailyPrivilegedAdapter;

    private TextView mTvInstruction;
    private RecyclerView mRecyclerViewMenu;
    private BaseRecyclerAdapter menuAdapter;
    private RecyclerView mRecyclerViewPrivilege;
    private BaseRecyclerAdapter privilegeAdapter;
    private RecyclerView mRecyclerViewVoucher;
    private BaseRecyclerAdapter voucherAdapter;
    private TextView mTvWelfareVouchTips;
    private NestedScrollView mScrollView;

    private List<VipPrivilegeVo>  mPrivilegeVoList  = new ArrayList<>();
    private List<VipPrivilegeVo1> mPrivilegeVoList1 = new ArrayList<>();

    private TextView mTvGiftDay;
    private LinearLayout mLlGiftDay;
    private TextView mTvWelfareVoucher;
    private View mViewWelfareVoucher;
    private LinearLayout mLlWelfareVoucher;
    private TextView mTvVipVoucher;
    private View mViewVipVoucher;
    private LinearLayout mLlVipVoucher;
    private Banner mBanner;
    private RelativeLayout titleBarLayout;

    @SuppressLint("WrongViewCast")
    private void bindViews() {
        titleBarLayout = findViewById(R.id.titleBar_Layout);

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mIvBack = findViewById(R.id.iv_back);
        mTvVipCount = findViewById(R.id.tv_vip_count);
        mTvVipCountNew = findViewById(R.id.tv_vip_count_new);
        mLlUserUnLogin = findViewById(R.id.ll_user_unlogin);
        mLlUserVipInfo = findViewById(R.id.ll_user_vip_info);
        mIvUserIcon = findViewById(R.id.iv_user_icon);
        mIvVipStatus = findViewById(R.id.iv_vip_status);
        mTvUserName = findViewById(R.id.tv_user_name);
        mTvUserNickname = findViewById(R.id.tv_user_nickname);
        mTvUserVipValidity = findViewById(R.id.tv_vip_validity);
        mTvOpenVip = findViewById(R.id.tv_open_vip);

        mRlOpenTips = findViewById(R.id.rl_open_tips);

        mToolbar = findViewById(R.id.toolbar);
        mTvWelfareVouchTips = findViewById(R.id.tv_welfare_vouch_tips);
        mScrollView = findViewById(R.id.scrollView);

        mTvGiftDay = findViewById(R.id.tv_gift_day);
        mLlGiftDay = findViewById(R.id.ll_gift_day);
        mTvWelfareVoucher = findViewById(R.id.tv_welfare_voucher);
        mViewWelfareVoucher = findViewById(R.id.view_welfare_voucher);
        mLlWelfareVoucher = findViewById(R.id.ll_welfare_vouch);
        mTvVipVoucher = findViewById(R.id.tv_vip_voucher);
        mViewVipVoucher = findViewById(R.id.view_vip_voucher);
        mLlVipVoucher = findViewById(R.id.ll_vip_voucher);

        mTvGiftbagTips = findViewById(R.id.tv_giftbag_tips);
        mRecyclerViewVipGiftbag = findViewById(R.id.recycler_view_vip_giftbag);
        mRecyclerViewVipGiftbag.setLayoutManager(new LinearLayoutManager(_mActivity){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mVipGiftbagAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(SuperUserInfoVo.CouponVo.class, new VipCouponItemHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);
        mRecyclerViewVipGiftbag.setAdapter(mVipGiftbagAdapter);

        mRecyclerViewVoucher = findViewById(R.id.recycler_view_voucher);
        mRecyclerViewVoucher.setLayoutManager(new LinearLayoutManager(_mActivity){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        voucherAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(SuperUserInfoVo.VoucherVo.class, new VipVoucherItemHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);
        mRecyclerViewVoucher.setAdapter(voucherAdapter);

        mViewFlipper = findViewById(R.id.viewflipper);
        mTvSignIn = findViewById(R.id.tv_sign_in);

        mTvSignTips = findViewById(R.id.tv_sign_tips);
        mLlSignReward = findViewById(R.id.ll_sign_reward);
        mTvSignReward = findViewById(R.id.tv_sign_reward);

        mBanner = findViewById(R.id.banner);

        mRecyclerViewDailyPrivileged = findViewById(R.id.recycler_view_daily_privileged);
        mRecyclerViewDailyPrivileged.setLayoutManager(new LinearLayoutManager(_mActivity){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mDailyPrivilegedAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(SuperUserInfoVo.DayRewardVo.class, new VipRewardItemHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);
        mRecyclerViewDailyPrivileged.setAdapter(mDailyPrivilegedAdapter);

        mTvInstruction = findViewById(R.id.tv_instructions);

        mRecyclerViewMenu = findViewById(R.id.recycler_view_meun);
        mRecyclerViewMenu.setLayoutManager(new GridLayoutManager(_mActivity, 2){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        menuAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(VipMenuVo.class, new VipMenuItemHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);
        mRecyclerViewMenu.setAdapter(menuAdapter);

        mRecyclerViewPrivilege = findViewById(R.id.recyclerview_privilege);
        mRecyclerViewPrivilege.setLayoutManager(new GridLayoutManager(_mActivity, 2){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        privilegeAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(VipPrivilegeVo.class, new VipPrivilegeItemHolder(_mActivity))
                .bind(VipPrivilegeVo1.class, new VipPrivilegeItemHolder1(_mActivity))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);
        mRecyclerViewPrivilege.setAdapter(privilegeAdapter);

        privilegeAdapter.setOnItemClickListener((v, position, data) -> {
            if (UserInfoModel.getInstance().isLogined()){
                UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
                if (userInfoBean.getSuper_user().getStatus().equals("yes")){
                    Toaster.show(((VipPrivilegeVo1)data).getContent());
                }else{
                    showBuySuperVipDialog();
                }
            }else{
                startActivity(new Intent(_mActivity, LoginActivity.class));
            }
        });

        mSwipeRefreshLayout.setProgressViewOffset(true, 0, 100);
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
            initData();
        });
        setPrivilegeDate();
        //setUserInfo();

        mIvBack.setOnClickListener(this);
        mIvUserIcon.setOnClickListener(this);
        mLlUserUnLogin.setOnClickListener(this);
        mTvOpenVip.setOnClickListener(this);
        mRlOpenTips.setOnClickListener(this);
        mTvSignIn.setOnClickListener(this);
        mTvInstruction.setOnClickListener(this);
        mTvGiftDay.setOnClickListener(this);
        mTvWelfareVoucher.setOnClickListener(this);
        mTvVipVoucher.setOnClickListener(this);

        checkSelect(0);


    }

    private void checkSelect(int type){
        SPUtils spUtils = new SPUtils(_mActivity, Constants.SP_COMMON_NAME);
        if (!spUtils.getBoolean("showWelfareVoucher", false)){
            mViewWelfareVoucher.setVisibility(View.VISIBLE);
        }
        if (!spUtils.getBoolean("showVipVoucher", false)){
            mViewVipVoucher.setVisibility(View.VISIBLE);
        }
        if (type == 0){
            mTvGiftDay.setBackgroundResource(R.drawable.shape_fd8f2f_big_radius);
            mTvWelfareVoucher.setBackgroundResource(R.drawable.shape_white_big_radius);
            mTvVipVoucher.setBackgroundResource(R.drawable.shape_white_big_radius);
            mTvGiftDay.setTextColor(Color.parseColor("#FFFFFF"));
            mTvWelfareVoucher.setTextColor(Color.parseColor("#000000"));
            mTvVipVoucher.setTextColor(Color.parseColor("#000000"));
            mLlGiftDay.setVisibility(View.VISIBLE);
            mLlWelfareVoucher.setVisibility(View.GONE);
            mLlVipVoucher.setVisibility(View.GONE);
        }else if(type == 1){
            spUtils.putBoolean("showWelfareVoucher", true);
            mViewWelfareVoucher.setVisibility(View.GONE);
            mTvGiftDay.setBackgroundResource(R.drawable.shape_white_big_radius);
            mTvWelfareVoucher.setBackgroundResource(R.drawable.shape_fd8f2f_big_radius);
            mTvVipVoucher.setBackgroundResource(R.drawable.shape_white_big_radius);
            mTvGiftDay.setTextColor(Color.parseColor("#000000"));
            mTvWelfareVoucher.setTextColor(Color.parseColor("#FFFFFF"));
            mTvVipVoucher.setTextColor(Color.parseColor("#000000"));
            mLlGiftDay.setVisibility(View.GONE);
            mLlWelfareVoucher.setVisibility(View.VISIBLE);
            mLlVipVoucher.setVisibility(View.GONE);
        }else if(type == 2){
            spUtils.putBoolean("showVipVoucher", true);
            mViewVipVoucher.setVisibility(View.GONE);
            mTvGiftDay.setBackgroundResource(R.drawable.shape_white_big_radius);
            mTvWelfareVoucher.setBackgroundResource(R.drawable.shape_white_big_radius);
            mTvVipVoucher.setBackgroundResource(R.drawable.shape_fd8f2f_big_radius);
            mTvGiftDay.setTextColor(Color.parseColor("#000000"));
            mTvWelfareVoucher.setTextColor(Color.parseColor("#000000"));
            mTvVipVoucher.setTextColor(Color.parseColor("#FFFFFF"));
            mLlGiftDay.setVisibility(View.GONE);
            mLlWelfareVoucher.setVisibility(View.GONE);
            mLlVipVoucher.setVisibility(View.VISIBLE);
        }
    }

    private List<VipMenuVo> initMenuDate(boolean showIOS){
        List<VipMenuVo> list = new ArrayList<VipMenuVo>();
        list.add(new VipMenuVo(2, "任务加成", "平台任务奖励加成\n单日最高额外赠550积分"));
        //if (!BuildConfig.IS_REPORT) list.add(new VipMenuVo(1, "交易特权", "手续费立减2%\n多赚无上限"));
        //list.add(new VipMenuVo(4, "IOS尊享版", "会员用户免费体验\n单独开通15元/月"));
        list.add(new VipMenuVo(3, "生日福利", "会员用户独享\n价值50元代金券"));
        list.add(new VipMenuVo(5, "游戏代金券", "独享福利·会员专享券"));
        list.add(new VipMenuVo(6, "游戏礼包", "独享福利·会员专享礼包"));
        return list;
    }

    private SuperUserInfoVo.DataBean mSuperUserInfoVo;
    private void initData() {
        if (mViewModel != null) {
            mViewModel.getSuperUserInfo(new OnBaseCallback<SuperUserInfoVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onSuccess(SuperUserInfoVo data) {
                    if (data != null && data.isStateOK()) {
                        mSuperUserInfoVo = data.getData();
                        if (mSuperUserInfoVo.getCoupon_list() != null){
                            mVipGiftbagAdapter.setDatas(mSuperUserInfoVo.getCoupon_list());
                            mVipGiftbagAdapter.notifyDataSetChanged();
                        }
                        if (mSuperUserInfoVo.getBuy_goods_list() != null){
                            voucherAdapter.setDatas(mSuperUserInfoVo.getBuy_goods_list());
                            voucherAdapter.notifyDataSetChanged();
                        }
                        if (mSuperUserInfoVo.getDay_reward_list() != null){
                            mDailyPrivilegedAdapter.setDatas(mSuperUserInfoVo.getDay_reward_list());
                            mDailyPrivilegedAdapter.notifyDataSetChanged();
                        }
                        mTvWelfareVouchTips.setText(mSuperUserInfoVo.getBuy_goods_description());
                        menuAdapter.setDatas(initMenuDate(!mSuperUserInfoVo.getShow_vip_ios().equals("no")));
                        menuAdapter.notifyDataSetChanged();

                        mViewFlipper.removeAllViews();
                        for (int i = 0; i < mSuperUserInfoVo.getAll_sign_reward_list().size(); i++) {
                            SuperUserInfoVo.AllSignRewardVo allSignRewardVo = mSuperUserInfoVo.getAll_sign_reward_list().get(i);
                            View inflate = LayoutInflater.from(_mActivity).inflate(R.layout.viewflipper_item, null, false);
                            ((TextView)inflate.findViewById(R.id.tv_nickname)).setText(allSignRewardVo.getUser_nickname());
                            ((TextView)inflate.findViewById(R.id.tv_content)).setText(allSignRewardVo.getTitle());
                            mViewFlipper.addView(inflate);
                        }
                        mTvVipCount.setText(CommonUtils.formatNumber((mSuperUserInfoVo.getSuper_user_count() * 2 + 500) * 12 / 10) + "人开通");

                        String count = CommonUtils.formatNumber((mSuperUserInfoVo.getSuper_user_count() * 2 + 500) * 12 / 10);
                        SpannableString spannableString = new SpannableString("已有" + count + "人享受权益");
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF450A")), 2, count.length() + 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        mTvVipCountNew.setText(spannableString);

                        if (TextUtils.isEmpty(mSuperUserInfoVo.getSign_reward())){
                            mTvSignTips.setVisibility(View.VISIBLE);
                            mLlSignReward.setVisibility(View.GONE);
                            mTvSignIn.setText("签到");
                            mTvSignIn.setTextColor(Color.parseColor("#FF3D63"));
                            mTvSignIn.setBackgroundResource(R.drawable.shape_ff3d63_big_radius_with_line);
                            mTvSignIn.setClickable(true);
                        }else{
                            mTvSignTips.setVisibility(View.GONE);
                            mLlSignReward.setVisibility(View.VISIBLE);
                            mTvSignReward.setText(mSuperUserInfoVo.getSign_reward());
                            mTvSignIn.setText("已签");
                            mTvSignIn.setTextColor(Color.parseColor("#FFFFFF"));
                            mTvSignIn.setBackgroundResource(R.drawable.ts_shape_big_radius_ababab_8a8a8a);
                            mTvSignIn.setClickable(false);
                        }
                        if (mSuperUserInfoVo.getExpiry_time() > 0){
                            mIvVipStatus.setImageResource(R.mipmap.ic_vip_open_new);
                            mTvOpenVip.setText("续费会员");
                            mTvUserVipValidity.setText("有效期：截至" + CommonUtils.formatTimeStamp(mSuperUserInfoVo.getExpiry_time() * 1000, "yyyy/MM/dd"));

                            if (UserInfoModel.getInstance().isLogined()){//缓存用户会员信息
                                UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
                                if (userInfoBean != null && !userInfoBean.getSuper_user().getStatus().equals("yes")){
                                    userInfoBean.getSuper_user().setStatus("yes");
                                }
                            }
                            String cycle_time = CommonUtils.formatTimeStamp(mSuperUserInfoVo.getNext_cycle_time() * 1000, "yyyy/MM/dd");
                            SpannableString ss = new SpannableString("将在" + cycle_time + "后刷新礼券");
                            ss.setSpan(new ClickableSpan() {
                                @Override
                                public void updateDrawState(@NonNull TextPaint ds) {
                                    ds.setColor(Color.parseColor("#F8505A"));
                                }

                                @Override
                                public void onClick(@NonNull View widget) { }
                            }, 2, 2 + cycle_time.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                            mTvGiftbagTips.setText(ss);
                        }else{
                            mIvVipStatus.setImageResource(R.mipmap.ic_vip_unopen_new);
                            mTvOpenVip.setText("开通会员");
                            mTvUserVipValidity.setText("开通会员，好礼享不停");

                            if (!TextUtils.isEmpty(mSuperUserInfoVo.getCoupon_block_label())){
                                SpannableString ss = new SpannableString("*" + mSuperUserInfoVo.getCoupon_block_label());
                                ss.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3c2a")), 0, 1, 0);
                                mTvGiftbagTips.setText(ss);
                            }else {
                                mTvGiftbagTips.setText("");
                            }
                        }

                        if (UserInfoModel.getInstance().isLogined()){
                            if (fristExpiryTime == -1) fristExpiryTime = mSuperUserInfoVo.getExpiry_time();//记录初始会员时间
                        }
                        if (fristExpiryTime != -1 && mSuperUserInfoVo.getExpiry_time() != 0 && fristExpiryTime != mSuperUserInfoVo.getExpiry_time()) {
                            showBuyVipSuccessDialog();//初始会员时间变更 说明用户购买了
                            fristExpiryTime = mSuperUserInfoVo.getExpiry_time();//记录初始会员时间
                        }
                        if (mDayRewardVo != null){//说明发生了调起支付事件
                            for (int i = 0; i < mSuperUserInfoVo.getDay_reward_list().size(); i++) {
                                SuperUserInfoVo.DayRewardVo dayRewardVo = mSuperUserInfoVo.getDay_reward_list().get(i);
                                if (dayRewardVo.getId() == mDayRewardVo.getId()){
                                    if (dayRewardVo.getHas_get() != mDayRewardVo.getHas_get()){//说明购买成功
                                        if (mDayRewardVo.getType().equals("ptb")){
                                            GetRewardkVo.DataBean dataBean = new GetRewardkVo.DataBean();
                                            dataBean.setTitle(mDayRewardVo.getTitle());
                                            showSuperDialog(dataBean, 2);
                                        }else if (mDayRewardVo.getType().equals("integral")){
                                            GetRewardkVo.DataBean dataBean = new GetRewardkVo.DataBean();
                                            dataBean.setTitle(mDayRewardVo.getTitle());
                                            showSuperDialog(dataBean, 2);
                                        }else if (mDayRewardVo.getType().equals("coupon")){
                                            GetRewardkVo.DataBean dataBean = new GetRewardkVo.DataBean();
                                            dataBean.setTitle(mDayRewardVo.getTitle());
                                            showCouponDialog(dataBean, 2);
                                        }
                                    }
                                }
                            }
                        }
                        setUserInfo();
                    }
                }
            });
        }
    }

    private void setPrivilegeDate(){
        mPrivilegeVoList.clear();
        mPrivilegeVoList.add(new VipPrivilegeVo(R.mipmap.ic_vip_privilege_monthly_gift, "价值30元", "会员福利券", "开通立赠秒回"));
        mPrivilegeVoList.add(new VipPrivilegeVo(R.mipmap.ic_vip_privilege_daily_ritual, "免费权益", "会员每日礼", "会员签到/兑换"));
        //mPrivilegeVoList.add(new VipPrivilegeVo(R.mipmap.ic_vip_privilege_ios_enjoy, "价值15元", "IOS尊享版", "免费体验"));
        //if (!BuildConfig.IS_REPORT) mPrivilegeVoList.add(new VipPrivilegeVo(R.mipmap.ic_vip_province_card, "省钱权益", "交易特权", "手续费立减2%"));
        mPrivilegeVoList.add(new VipPrivilegeVo(R.mipmap.ic_vip_privilege_brithday, "价值50元", "生日礼物", "赠送50元券"));
        mPrivilegeVoList.add(new VipPrivilegeVo(R.mipmap.ic_vip_privilege_bonus, "额外权益", "任务加成", "平台赚金额外赠"));
        mPrivilegeVoList.add(new VipPrivilegeVo(R.mipmap.ic_vip_privilege_arch, "价值百元", "会员神券", "限定会员购"));
        mPrivilegeVoList.add(new VipPrivilegeVo(R.mipmap.ic_vip_privilege_gift_bag, "独享权益", "游戏福利", "会员专享券/礼包"));
        mPrivilegeVoList1.clear();
        mPrivilegeVoList1.add(new VipPrivilegeVo1(R.mipmap.ic_vip_privilege_monthly_gift, "价值30元", "会员福利券", "每月专享礼券回馈，100%秒回本"));
        mPrivilegeVoList1.add(new VipPrivilegeVo1(R.mipmap.ic_vip_privilege_daily_ritual, "免费权益", "会员每日礼", "享会员签到福利、每日兑好礼"));
        //mPrivilegeVoList1.add(new VipPrivilegeVo1(R.mipmap.ic_vip_privilege_ios_enjoy, "价值15元", "IOS尊享版", "IOS推出尊享版期间，会员可免费体验"));
        //if (!BuildConfig.IS_REPORT) mPrivilegeVoList1.add(new VipPrivilegeVo1(R.mipmap.ic_vip_province_card, "省钱权益", "交易特权", "手续费立减2%，省的多"));
        mPrivilegeVoList1.add(new VipPrivilegeVo1(R.mipmap.ic_vip_privilege_brithday, "价值50元", "生日礼物", "每年赠送50元生日券"));
        mPrivilegeVoList1.add(new VipPrivilegeVo1(R.mipmap.ic_vip_privilege_bonus, "额外权益", "任务加成", "参与平台任务赚金享加成"));
        mPrivilegeVoList1.add(new VipPrivilegeVo1(R.mipmap.ic_vip_privilege_arch, "价值百元", "会员神券", "限定会员购"));
        mPrivilegeVoList1.add(new VipPrivilegeVo1(R.mipmap.ic_vip_privilege_gift_bag, "独享权益", "游戏福利", "会员专享券/礼包"));
    }

    private void setUserInfo(){
        if (UserInfoModel.getInstance().isLogined()){
            UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
            if (!TextUtils.isEmpty(userInfoBean.getUser_icon())) {
                GlideApp.with(_mActivity)
                        .asBitmap()
                        .load(userInfoBean.getUser_icon())
                        .placeholder(R.mipmap.ic_user_login_new_sign)
                        .error(R.mipmap.ic_user_login_new_sign)
                        .transform(new GlideCircleTransform(_mActivity, (int) (3 * ScreenUtil.getScreenDensity(_mActivity))))
                        .into(mIvUserIcon);
            } else {
                mIvUserIcon.setImageResource(R.mipmap.ic_user_login_new_sign);
            }
            if (userInfoBean.getSuper_user().getStatus().equals("yes")){
                ((TextView) findViewById(R.id.tv_vip_title_new)).setText("已享以下会员权益");
                mRlOpenTips.setVisibility(View.GONE);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity){
                    @Override
                    public boolean canScrollHorizontally() {
                        return false;
                    }
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mRecyclerViewPrivilege.setLayoutManager(linearLayoutManager);
                mRecyclerViewPrivilege.setAdapter(privilegeAdapter);
                privilegeAdapter.setDatas(mPrivilegeVoList1);
            }else {
                ((TextView) findViewById(R.id.tv_vip_title_new)).setText("开通会员享专属权益");
                mRlOpenTips.setVisibility(View.VISIBLE);
                mRecyclerViewPrivilege.setLayoutManager(new GridLayoutManager(_mActivity, 2){
                    @Override
                    public boolean canScrollHorizontally() {
                        return false;
                    }
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });
                mRecyclerViewPrivilege.setAdapter(privilegeAdapter);
                privilegeAdapter.setDatas(mPrivilegeVoList);
            }

            mLlUserUnLogin.setVisibility(View.GONE);
            mLlUserVipInfo.setVisibility(View.VISIBLE);
            mTvUserName.setText(userInfoBean.getUser_nickname());
            mTvUserNickname.setText("用户名：" + userInfoBean.getUsername());

        }else{
            mLlUserUnLogin.setVisibility(View.VISIBLE);
            mLlUserVipInfo.setVisibility(View.GONE);

            mRlOpenTips.setVisibility(View.VISIBLE);
            mRecyclerViewPrivilege.setLayoutManager(new GridLayoutManager(_mActivity, 2){
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            mRecyclerViewPrivilege.setAdapter(privilegeAdapter);
            privilegeAdapter.setDatas(mPrivilegeVoList);
        }
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        initData();
        setUserInfo();
        if (UserInfoModel.getInstance().isLogined()) {
            getSuperBirthdayRewardStatus(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                pop();
                break;
            case R.id.tv_instructions:
                //CommonActivityBrowser
                BrowserActivity.newInstance(_mActivity, "https://webapp.xiaodianyouxi.com/?fix_home=no&root=superUserHelp#/super/user_help", false, "", "", false,true);
                break;
            case R.id.iv_user_icon:
            case R.id.ll_user_unlogin:
                if (!UserInfoModel.getInstance().isLogined()){
                    startActivity(new Intent(_mActivity, LoginActivity.class));
                }
                break;
            case R.id.tv_open_vip:
            case R.id.rl_open_tips:
                if (checkLogin()){
                    if (!TextUtils.isEmpty(UserInfoModel.getInstance().getUserInfo().getIdcard()) && !TextUtils.isEmpty(UserInfoModel.getInstance().getUserInfo().getReal_name())){
                        if (UserInfoModel.getInstance().getUserInfo().getAdult().equals("yes")){
                            showBuySuperVipDialog();
                        }else{
                            Toaster.show("满18岁用户才能购买！");
                        }
                    }else {
                        startFragment(CertificationFragment.newInstance());
                    }
                }
                break;
            case R.id.tv_sign_in:
                if (checkLogin()){
                    if (mSuperUserInfoVo.getExpiry_time() > 0){
                        signLuck();
                    }else{
                        //Toaster.show( "请先开通会员");
                        showVipTipsDialog();
                    }
                }
                break;
            case R.id.tv_gift_day:
                checkSelect(0);
                break;
            case R.id.tv_welfare_voucher:
                checkSelect(1);
                break;
            case R.id.tv_vip_voucher:
                checkSelect(2);
                break;

            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public void buySuperUser(int card_type, int pay_type) {
        if (mViewModel != null) {
            mViewModel.buySuperUser(card_type, pay_type, new OnBaseCallback<PayInfoVo>() {
                @Override
                public void onSuccess(PayInfoVo payInfoVo) {
                    if (payInfoVo != null) {
                        if (payInfoVo.isStateOK()) {
                            if (payInfoVo.getData() != null) {
                                doPay(payInfoVo.getData());
                            }
                        } else {
                            Toaster.show( payInfoVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    public void getCoupon(int coupon_id) {
        if (mSuperUserInfoVo.getExpiry_time() <= 0){
            //Toaster.show( "请先开通会员");
            showVipTipsDialog();
            return;
        }
        if (mViewModel != null) {
            mViewModel.getCoupon(String.valueOf(coupon_id), new OnBaseCallback<BaseVo>() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null && data.isStateOK()) {
                        for (int i = 0; i < mVipGiftbagAdapter.getData().size(); i++) {
                            SuperUserInfoVo.CouponVo couponVo = (SuperUserInfoVo.CouponVo) mVipGiftbagAdapter.getData().get(i);
                            if (couponVo.getCoupon_id() == coupon_id){
                                couponVo.setHas_get("yes");
                                mVipGiftbagAdapter.notifyItemChanged(i);
                                Toaster.show("领取成功");
                            }
                        }

                    }else{
                        Toaster.show( data.getMsg());
                    }
                }
            });
        }
    }

    private SignLuckVo mSignLuckVo;
    public void signLuck() {
        if (mViewModel != null) {
            mViewModel.signLuck(new OnBaseCallback<SignLuckVo>() {
                @Override
                public void onSuccess(SignLuckVo data) {
                    if (data != null && data.isStateOK()) {
                        mSignLuckVo = data;
                        showSignLuckDialog();
                    }else{
                        Toaster.show( data.getMsg());
                    }
                }
            });
        }
    }

    public void getReward(int reward_id) {
        if (mViewModel != null) {
            mViewModel.getReward(reward_id, new OnBaseCallback<GetRewardkVo>() {
                @Override
                public void onSuccess(GetRewardkVo data) {
                    if (data != null && data.isStateOK()) {
                        mTvSignIn.setText("已签");
                        mTvSignIn.setTextColor(Color.parseColor("#FFFFFF"));
                        mTvSignIn.setBackgroundResource(R.drawable.ts_shape_big_radius_ababab_8a8a8a);
                        mTvSignIn.setClickable(false);
                        mTvSignTips.setVisibility(View.GONE);
                        mLlSignReward.setVisibility(View.VISIBLE);
                        mTvSignReward.setText(mSuperUserInfoVo.getSign_reward());
                        Toaster.show("签到成功");
                    }else{
                        Toaster.show( data.getMsg());
                    }
                }
            });
        }
    }

    public void rewardExchange(SuperUserInfoVo.DayRewardVo item, int type) {
        if (mViewModel != null) {
            mViewModel.rewardExchange(item.getId(), new OnBaseCallback<GetRewardkVo>() {
                @Override
                public void onSuccess(GetRewardkVo data) {
                    if (data != null && data.isStateOK()) {
                        initData();
                        if (data.getData().getReward_type().equals("ptb")){
                            showSuperDialog(data.getData(), type);
                        }else if (data.getData().getReward_type().equals("integral")){
                            //showSuperDialog(data.getData(), type);
                            Toaster.show("领取成功");
                        }else if (data.getData().getReward_type().equals("coupon")){
                            showCouponDialog(data.getData(), type);
                        }
                    }else if (data != null && data.getState().equals("no_integral")){
                        showGoTaskDialog();
                    }else{
                        Toaster.show( data.getMsg());
                    }
                }
            });
        }
    }

    private SuperUserInfoVo.DayRewardVo mDayRewardVo;
    public void rewardBuy(SuperUserInfoVo.DayRewardVo item, int pay_type) {
        if (mViewModel != null) {
            mViewModel.rewardBuy(item.getId(), pay_type, new OnBaseCallback<PayInfoVo>() {
                @Override
                public void onSuccess(PayInfoVo payInfoVo) {
                    if (payInfoVo != null && payInfoVo.isStateOK()) {
                        mDayRewardVo = item;
                        doPay(payInfoVo.getData());
                    }else{
                        Toaster.show( payInfoVo.getMsg());
                    }
                }
            });
        }
    }

    //购买每日神券
    public void buyVoucher(SuperUserInfoVo.VoucherVo item) {
        if (mSuperUserInfoVo.getExpiry_time() <= 0){
            showVipTipsDialog();
            return;
        }
        if (mViewModel != null) {
            mViewModel.buyVoucher(item.getBuy_id(),  new OnBaseCallback<BuyVoucherVo>() {
                @Override
                public void onSuccess(BuyVoucherVo buyVoucherVo) {
                    if (buyVoucherVo != null && buyVoucherVo.isStateOK()) {
                        BrowserActivity.newInstance(_mActivity, buyVoucherVo.getData().getUrl());
                    }else{
                        Toaster.show( buyVoucherVo.getMsg());
                    }
                }
            });
        }
    }

    private void checkTransaction() {
        if (mViewModel != null) {
            mViewModel.checkTransaction(new OnBaseCallback() {
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
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (checkLogin()) {
                                start(TransactionSellFragment.newInstance());
                            }
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    Toaster.show( message);
                }
            });
        }
    }

    public void getSuperBirthdayRewardStatus(boolean isClick) {
        if (mViewModel != null) {
            mViewModel.getSuperBirthdayRewardStatus(new OnBaseCallback<SuperBirthdayRewardVo>() {
                @Override
                public void onSuccess(SuperBirthdayRewardVo payInfoVo) {
                    if (payInfoVo != null && payInfoVo.isStateOK()) {
                        if (payInfoVo.getData().getStatus().equals("is_get")){//生日奖励已领取
                            if (isClick) Toaster.show( "已领取过今年的生日福利啦~");
                        }else if (payInfoVo.getData().getStatus().equals("no_yet")){
                            if (isClick) Toaster.show( "还未到您的生日哦！");
                        }else if (payInfoVo.getData().getStatus().equals("now")){
                            getBirthdayReward();
                        }else if (payInfoVo.getData().getStatus().equals("past")){
                            if (isClick) getBirthdayReward();
                        }else if (payInfoVo.getData().getStatus().equals("no_cert")){
                            if (isClick) {
                                Toaster.show("请先完成实名哦！");
                                startFragment(CertificationFragment.newInstance());
                            }
                        }else if (payInfoVo.getData().getStatus().equals("no_super_user")){
                            if (isClick) {
                                //Toaster.show("请开通会员即可享受专属会员特权哦~");
                                showVipTipsDialog();
                            }
                        }
                    }else{
                        Toaster.show( payInfoVo.getMsg());
                    }
                }
            });
        }
    }

    public void getBirthdayReward() {
        if (mViewModel != null) {
            mViewModel.getBirthdayReward(new OnBaseCallback<BirthdayRewardVo>() {
                @Override
                public void onSuccess(BirthdayRewardVo payInfoVo) {
                    if (payInfoVo != null && payInfoVo.isStateOK()) {
                        showBirthdayDialog(payInfoVo);
                    }else{
                        Toaster.show( payInfoVo.getMsg());
                    }
                }
            });
        }
    }

    private AppCompatTextView mTvSuperVipMemberUsername;
    private RelativeLayout mRlSuperPayAlipay;
    private ImageView      mIvSuperPayAlipay;
    private RelativeLayout mRlSuperPayWechat;
    private ImageView    mIvSuperPayWechat;
    private Button        mBtnSuperConfirm;
    private RecyclerView mDialogSuperRecyclerView;
    private BaseRecyclerAdapter mSuperDialogAdapter;
    private CustomDialog  superDialog;
    private void showBuySuperVipDialog() {
        if (superDialog == null) {
            superDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_buy_super_province, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
            mTvSuperVipMemberUsername = superDialog.findViewById(R.id.tv_super_vip_member_username);
            mRlSuperPayAlipay = superDialog.findViewById(R.id.rl_super_pay_alipay);
            mIvSuperPayAlipay = superDialog.findViewById(R.id.iv_super_pay_alipay);
            mRlSuperPayWechat = superDialog.findViewById(R.id.rl_super_pay_wechat);
            mIvSuperPayWechat = superDialog.findViewById(R.id.iv_super_pay_wechat);
            mBtnSuperConfirm = superDialog.findViewById(R.id.btn_super_confirm);
            mDialogSuperRecyclerView = superDialog.findViewById(R.id.recycler_view_super_province_dialog);

            mRlSuperPayAlipay.setOnClickListener(view -> {
                PAY_TYPE = PAY_TYPE_ALIPAY;
                mIvSuperPayAlipay.setImageResource(R.mipmap.ic_buy_selected);
                mIvSuperPayWechat.setImageResource(R.mipmap.ic_buy_unselect);
            });
            mRlSuperPayWechat.setOnClickListener(view -> {
                PAY_TYPE = PAY_TYPE_WECHAT;
                mIvSuperPayAlipay.setImageResource(R.mipmap.ic_buy_unselect);
                mIvSuperPayWechat.setImageResource(R.mipmap.ic_buy_selected);
            });

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity){
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mDialogSuperRecyclerView.setLayoutManager(linearLayoutManager);
            mSuperDialogAdapter = new BaseRecyclerAdapter.Builder<>()
                    .bind(SuperUserInfoVo.CardTypeVo.class, new SuperVipItemHolder(_mActivity))
                    .build().setTag(R.id.tag_fragment, _mActivity)
                    .setTag(R.id.tag_fragment, this);
            mDialogSuperRecyclerView.setAdapter(mSuperDialogAdapter);
            if (mSuperUserInfoVo != null){
                List<SuperUserInfoVo.CardTypeVo> data = mSuperUserInfoVo.getCard_type_list();
                if (data != null && data.size() > 0){
                    data.get(0).setSelected(true);
                }
                mSuperDialogAdapter.setDatas(data);
                mSuperDialogAdapter.notifyDataSetChanged();
            }

            mSuperDialogAdapter.setOnItemClickListener((v, position, data) -> {
                for (int i = 0; i < mSuperDialogAdapter.getData().size(); i++) {
                    SuperUserInfoVo.CardTypeVo dataBean = (SuperUserInfoVo.CardTypeVo) mSuperDialogAdapter.getData().get(i);
                    if (i == position){
                        dataBean.setSelected(true);
                    }else{
                        dataBean.setSelected(false);
                    }
                }
                mSuperDialogAdapter.notifyDataSetChanged();
            });

            mRlSuperPayAlipay.setOnClickListener(view -> {
                PAY_TYPE = PAY_TYPE_ALIPAY;
                mIvSuperPayAlipay.setImageResource(R.mipmap.ic_buy_selected);
                mIvSuperPayWechat.setImageResource(R.mipmap.ic_buy_unselect);
            });
            mRlSuperPayWechat.setOnClickListener(view -> {
                PAY_TYPE = PAY_TYPE_WECHAT;
                mIvSuperPayAlipay.setImageResource(R.mipmap.ic_buy_unselect);
                mIvSuperPayWechat.setImageResource(R.mipmap.ic_buy_selected);
            });

            mBtnSuperConfirm.setBackgroundResource(R.drawable.ts_shape_big_radius_ff6d3c_fb4c37);

            mBtnSuperConfirm.setOnClickListener(view -> {
                if (superDialog != null && superDialog.isShowing()) superDialog.dismiss();
                int buyTypeId = -1;
                for (int i = 0; i < mSuperDialogAdapter.getData().size(); i++) {
                    SuperUserInfoVo.CardTypeVo dataBean = (SuperUserInfoVo.CardTypeVo) mSuperDialogAdapter.getData().get(i);
                    if (dataBean.isSelected()) {
                        buyTypeId = dataBean.getId();
                    }
                }
                if (buyTypeId != -1){
                    buySuperUser(buyTypeId, PAY_TYPE);
                }
            });
        }
        UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
        mTvSuperVipMemberUsername.setText(userInfo.getUsername());
        if (mRlSuperPayAlipay != null) mRlSuperPayAlipay.performClick();
        superDialog.show();
    }

    private void showSignLuckDialog(){
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_vip_sign_luck, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        RecyclerView mSignRecyclerView = tipsDialog.findViewById(R.id.recycler_view_sign);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mSignRecyclerView.setLayoutManager(linearLayoutManager);
        BaseRecyclerAdapter mSignDialogAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(SignLuckVo.DataBean.class, new VipSignItemHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);
        mSignRecyclerView.setAdapter(mSignDialogAdapter);
        mSignDialogAdapter.setDatas(mSignLuckVo.getData());
        mSignDialogAdapter.notifyDataSetChanged();
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (checkLogin()){
                int selectId = -1;
                for (int i = 0; i < mSignLuckVo.getData().size(); i++) {
                    if (mSignLuckVo.getData().get(i).isSelected()){
                        selectId = mSignLuckVo.getData().get(i).getId();
                    }
                }
                if (selectId != -1){
                    getReward(selectId);
                }
            }
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        if (mSignDialogAdapter.getData().size() > 0){
            ((SignLuckVo.DataBean)mSignDialogAdapter.getData().get(0)).setSelected(true);
        }
        mSignDialogAdapter.setOnItemClickListener((v, position, data) -> {
            for (int i = 0; i < mSignDialogAdapter.getData().size(); i++) {
                ((SignLuckVo.DataBean)mSignDialogAdapter.getData().get(i)).setSelected(false);
            }
            ((SignLuckVo.DataBean)mSignDialogAdapter.getData().get(position)).setSelected(true);
            mSignDialogAdapter.notifyDataSetChanged();
        });
        tipsDialog.show();
    }

    public void showBirthdayTipsDialog(boolean isClick){
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_vip_birthday_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            if (isClick){
                getSuperBirthdayRewardStatus(true);
            }else{
                getBirthdayReward();
            }
        });
        tipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.show();
    }

    public void showIosTipsDialog(){
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_vip_ios_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.show();
    }

    public void showBuyTipsDialog(int type, SuperUserInfoVo.DayRewardVo item){
        if (mSuperUserInfoVo.getExpiry_time() <= 0){
            showVipTipsDialog();
            return;
        }
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_vip_buy_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        if (type == 0){
            ((TextView)tipsDialog.findViewById(R.id.tv_title)).setText("确认兑换");
            ((TextView)tipsDialog.findViewById(R.id.tv_content)).setText("请确认是否兑换");
            ((TextView)tipsDialog.findViewById(R.id.tv_confirm)).setText("确认兑换");
        }else if (type == 1){
            ((TextView)tipsDialog.findViewById(R.id.tv_title)).setText("确认领取");
            ((TextView)tipsDialog.findViewById(R.id.tv_content)).setText("请确认是否领取");
            ((TextView)tipsDialog.findViewById(R.id.tv_confirm)).setText("确认领取");
        }else if (type == 2){
            ((TextView)tipsDialog.findViewById(R.id.tv_title)).setText("确认购买");
            ((TextView)tipsDialog.findViewById(R.id.tv_content)).setText("请确认是否购买");
            ((TextView)tipsDialog.findViewById(R.id.tv_confirm)).setText("确认购买");
        }

        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            if (type == 0 || type == 1){
                rewardExchange(item, type);
            }else if (type == 2){
                showBuyRewardPayDialog(item);
            }
        });
        tipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.show();
    }

    public void showGoTaskDialog(){
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_vip_buy_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        ((TextView)tipsDialog.findViewById(R.id.tv_title)).setText("积分不足");
        ((TextView)tipsDialog.findViewById(R.id.tv_content)).setText("前往任务赚金，领海量积分");
        ((TextView)tipsDialog.findViewById(R.id.tv_confirm)).setText("确认前往");

        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            //任务赚金
            startFragment(TaskCenterFragment.newInstance());
        });
        tipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.show();
    }

    private void showBuyRewardPayDialog(SuperUserInfoVo.DayRewardVo item){
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_buy_reward, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

        AppCompatTextView mTvTitle = tipsDialog.findViewById(R.id.tv_title);
        AppCompatTextView mTvPrice = tipsDialog.findViewById(R.id.tv_price);

        RelativeLayout mRlPayAlipay = tipsDialog.findViewById(R.id.rl_super_pay_alipay);
        ImageView mIvPayAlipay = tipsDialog.findViewById(R.id.iv_super_pay_alipay);
        RelativeLayout mRlPayWechat = tipsDialog.findViewById(R.id.rl_super_pay_wechat);
        ImageView mIvPayWechat = tipsDialog.findViewById(R.id.iv_super_pay_wechat);
        Button mBtnConfirm = tipsDialog.findViewById(R.id.btn_super_confirm);

        mTvTitle.setText(item.getTitle());
        mTvPrice.setText(item.getPrice_label());

        mRlPayAlipay.setOnClickListener(view -> {
            PAY_TYPE = PAY_TYPE_ALIPAY;
            mIvPayAlipay.setImageResource(R.mipmap.ic_buy_selected);
            mIvPayWechat.setImageResource(R.mipmap.ic_buy_unselect);
        });
        mRlPayWechat.setOnClickListener(view -> {
            PAY_TYPE = PAY_TYPE_WECHAT;
            mIvPayAlipay.setImageResource(R.mipmap.ic_buy_unselect);
            mIvPayWechat.setImageResource(R.mipmap.ic_buy_selected);
        });
        mRlPayAlipay.performClick();

        mBtnConfirm.setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            rewardBuy(item, PAY_TYPE);
        });

        tipsDialog.show();
    }

    private void showCouponDialog(GetRewardkVo.DataBean dataBean, int type){
        CustomDialog voucherDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_province_voucher_success, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        RelativeLayout mRlAdvertising = voucherDialog.findViewById(R.id.rl_advertising);
        ImageView mIvAdvertising = voucherDialog.findViewById(R.id.iv_advertising);
        TextView mTvTitle = voucherDialog.findViewById(R.id.tv_title);
        TextView mTvPrice = voucherDialog.findViewById(R.id.tv_price);
        TextView mTvTips = voucherDialog.findViewById(R.id.tv_tips);
        if (type == 0){
            mTvTitle.setText("兑换成功");
            if ("coupon".equals(dataBean.getReward_type())) mTvTips.setText("已发送至账号，可在\"我的-代金券\"中查看");
        }else if (type == 1){
            mTvTitle.setText("领取成功");
        }else if (type == 2){
            mTvTitle.setText("购买成功");
        }
        mTvPrice.setText(dataBean.getTitle());
        if (mSuperUserInfoVo != null && mSuperUserInfoVo.getAd_banner() != null){
            mIvAdvertising.setVisibility(View.VISIBLE);
            mRlAdvertising.setVisibility(View.VISIBLE);
            GlideApp.with(_mActivity).asBitmap().load(mSuperUserInfoVo.getAd_banner().getPic()).transform(new GlideRoundTransformNew(_mActivity, 8)).into(mIvAdvertising);
            mIvAdvertising.setOnClickListener(v -> {
                new AppJumpAction(_mActivity).jumpAction(new AppBaseJumpInfoBean(mSuperUserInfoVo.getAd_banner().getPage_type(), mSuperUserInfoVo.getAd_banner().getParam()));
            });
            if (voucherDialog != null && voucherDialog.isShowing()) voucherDialog.dismiss();
        }
        voucherDialog.findViewById(R.id.btn_confirm).setOnClickListener(view -> {
            if (voucherDialog != null && voucherDialog.isShowing()) voucherDialog.dismiss();
        });
        voucherDialog.show();
    }

    private void showSuperDialog(GetRewardkVo.DataBean dataBean, int type){
        CustomDialog voucherDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_super_province_voucher_success, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        RelativeLayout mRlAdvertising = voucherDialog.findViewById(R.id.rl_advertising);
        ImageView mIvAdvertising = voucherDialog.findViewById(R.id.iv_advertising);
        TextView mTvTitle = voucherDialog.findViewById(R.id.tv_title);
        TextView mTvPrice = voucherDialog.findViewById(R.id.tv_price);
        TextView mTvTips = voucherDialog.findViewById(R.id.tv_tips);
        if (type == 0){
            mTvTitle.setText("兑换成功");
            if ("coupon".equals(dataBean.getReward_type())) mTvTips.setText("已发送至账号，可在\"我的-代金券\"中查看");
        }else if (type == 1){
            mTvTitle.setText("领取成功");
        }else if (type == 2){
            mTvTitle.setText("购买成功");
        }
        mTvPrice.setText(dataBean.getTitle());
        if (mSuperUserInfoVo != null && mSuperUserInfoVo.getAd_banner() != null){
            mIvAdvertising.setVisibility(View.VISIBLE);
            mRlAdvertising.setVisibility(View.VISIBLE);
            GlideApp.with(_mActivity).asBitmap().load(mSuperUserInfoVo.getAd_banner().getPic()).transform(new GlideRoundTransformNew(_mActivity, 8)).into(mIvAdvertising);
            mIvAdvertising.setOnClickListener(v -> {
                if (voucherDialog != null && voucherDialog.isShowing()) voucherDialog.dismiss();
                new AppJumpAction(_mActivity).jumpAction(new AppBaseJumpInfoBean(mSuperUserInfoVo.getAd_banner().getPage_type(), mSuperUserInfoVo.getAd_banner().getParam()));
            });
        }
        voucherDialog.findViewById(R.id.btn_confirm).setOnClickListener(view -> {
            if (voucherDialog != null && voucherDialog.isShowing()) voucherDialog.dismiss();
        });
        voucherDialog.show();
    }

    private void showBuyVipSuccessDialog(){
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_vip_buy_success, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        ((TextView)tipsDialog.findViewById(R.id.tv_content)).setText("有效期：\n" + CommonUtils.formatTimeStamp(mSuperUserInfoVo.getStart_time() * 1000, "yyyy/MM/dd") + "至" + CommonUtils.formatTimeStamp(mSuperUserInfoVo.getExpiry_time() * 1000, "yyyy/MM/dd"));
        tipsDialog.findViewById(R.id.rl_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.show();
    }

    private void showBirthdayDialog(BirthdayRewardVo payInfoVo){
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_vip_birthday, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        ((TextView)tipsDialog.findViewById(R.id.tv_price)).setText(String.valueOf(payInfoVo.getData().getAmount()));
        ((TextView)tipsDialog.findViewById(R.id.tv_cdt)).setText("满" + payInfoVo.getData().getCdt() + "元可用");
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.show();
    }

    private void showVipTipsDialog(){
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_vip_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (checkLogin()){
                if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
                if (!TextUtils.isEmpty(UserInfoModel.getInstance().getUserInfo().getIdcard()) && !TextUtils.isEmpty(UserInfoModel.getInstance().getUserInfo().getReal_name())){
                    if (UserInfoModel.getInstance().getUserInfo().getAdult().equals("yes")){
                        showBuySuperVipDialog();
                    }else{
                        Toaster.show("满18岁用户才能购买！");
                    }
                }else {
                    startFragment(CertificationFragment.newInstance());
                }
            }
        });
        tipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.show();
    }

    private void getAdSwiperList(){
        if (mViewModel != null) {
            mViewModel.getAdSwiperList(new OnBaseCallback<AdSwiperListVo>() {
                @Override
                public void onSuccess(AdSwiperListVo data) {
                    if (data != null) {
                        if (data.isStateOK() && data.getData() != null) {
                            if (data.getData() != null && data.getData().size() > 0){
                                mBanner.setVisibility(View.VISIBLE);
                                ViewGroup.LayoutParams params = mBanner.getLayoutParams();
                                if (params != null) {
                                    params.height = (ScreenUtil.getScreenWidth(_mActivity) - ScreenUtil.dp2px(_mActivity, 28)) * 180 / 710;
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
}
