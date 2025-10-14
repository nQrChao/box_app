package com.zqhy.app.core.view.user.provincecard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.box.other.hjq.toast.Toaster;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;
import com.zqhy.app.core.data.model.user.CanUsGameListInfoVo;
import com.zqhy.app.core.data.model.user.PayInfoVo;
import com.zqhy.app.core.data.model.user.SuperRewardVo;
import com.zqhy.app.core.data.model.user.SuperVipMemberInfoVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.data.model.user.VipMemberInfoVo;
import com.zqhy.app.core.data.model.user.VipMemberTypeVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.AbsPayBuyFragment;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.core.view.user.CertificationFragment;
import com.zqhy.app.core.view.user.newvip.NewUserVipFragment;
import com.zqhy.app.core.view.user.provincecard.holder.ProvinceGameItemHolder;
import com.zqhy.app.core.view.user.provincecard.holder.ProvinceItemHolder;
import com.zqhy.app.core.view.user.provincecard.holder.SuperProvinceItemHolder;
import com.zqhy.app.core.vm.user.VipMemberViewModel;
import com.zqhy.app.glide.GlideRoundTransformNew;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.utils.sp.SPUtils;

import java.util.List;

/**
 * @author leeham
 * @date 2020/3/25-19:37
 * @description 2020.11.09 此页面改名未巴兔月卡
 */
public class ProvinceCardFragment extends AbsPayBuyFragment<VipMemberViewModel> implements View.OnClickListener {

    private boolean isProvinceCilck = false;
    private boolean isSuperProvinceCilck = false;
    private long fristVipMemberLastDate = -1;
    private long fristDate = -1;
    private long fristCardExpiryTime = -1;
    private long fristTime = -1;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_province_card;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "省钱卡";
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        SPUtils spUtils = new SPUtils(_mActivity, Constants.SP_COMMON_NAME);
        isProvinceCilck = spUtils.getBoolean("isProvinceCilck", false);
        isSuperProvinceCilck = spUtils.getBoolean("isSuperProvinceCilck", false);
        bindViews();
        getVipTypesData(true);
        initData();
        initSuperData();
        if (UserInfoModel.getInstance().isLogined()){
            if (UserInfoModel.getInstance().getUserInfo().getVip_member() != null && fristVipMemberLastDate == -1){
                fristVipMemberLastDate = UserInfoModel.getInstance().getUserInfo().getVip_member().getVip_member_expire();
                fristDate = UserInfoModel.getInstance().getUserInfo().getVip_member().getVip_member_expire();
            }
        }
    }

    @Override
    protected int getPayAction() {
        return PAY_ACTION_VIP_MEMBER;
    }

    private TextView mTvProvinceNext;
    private TextView     mTvVipNext;
    private TextView     mTvInstructions;
    private RecyclerView mProvinceRecyclerView;
    private BaseRecyclerAdapter mProvinceAdapter;
    private TextView     mTvProvinceTips;
    private TextView     mTvProvinceTips1;
    private TextView     mTvProvinceCount;
    private TextView     mTvInstructionsTop;
    private TextView     mTvProvinceCannotUserGame;
    private View         mViewProvinceStatus;

    private TextView mTvVipCount;
    private TextView mTvVipDayReward;
    private TextView mTvVipTips;
    private TextView mTvVipTips1;
    private View mViewVipStatus;
    private RecyclerView mSuperProvinceRecyclerView;
    private BaseRecyclerAdapter mSuperProvinceAdapter;

    private ImageView mIvProvinceObtain;
    private TextView mTvProvincevalidity;

    private LinearLayout mLlBanner;
    private Banner mBanner;

    private void bindViews() {
        mTvProvinceNext = findViewById(R.id.tv_province_next);
        mProvinceRecyclerView = findViewById(R.id.recycler_view_province);
        mTvInstructions = findViewById(R.id.tv_instructions);
        mTvProvinceTips = findViewById(R.id.tv_province_tips);
        mTvProvinceTips1 = findViewById(R.id.tv_province_tips1);
        mTvProvinceCount = findViewById(R.id.tv_province_count);
        mViewProvinceStatus = findViewById(R.id.view_province_status);
        mTvProvinceCannotUserGame = findViewById(R.id.tv_province_cannot_use_game);
        mTvInstructionsTop = findViewById(R.id.tv_instructions_top);

        mTvVipNext = findViewById(R.id.tv_vip_next);
        mTvVipCount = findViewById(R.id.tv_vip_count);
        mTvVipDayReward = findViewById(R.id.tv_vip_day_reward);
        mTvVipTips = findViewById(R.id.tv_vip_tips);
        mTvVipTips1 = findViewById(R.id.tv_vip_tips1);
        mViewVipStatus = findViewById(R.id.view_vip_status);
        mSuperProvinceRecyclerView = findViewById(R.id.recycler_view_vip);

        mIvProvinceObtain = findViewById(R.id.iv_province_obtain);
        mTvProvincevalidity = findViewById(R.id.tv_province_validity);

        mLlBanner = findViewById(R.id.ll_banner);
        mBanner = findViewById(R.id.banner);

        mTvProvinceNext.setOnClickListener(this);
        mTvVipNext.setOnClickListener(this);
        mTvInstructions.setOnClickListener(this);
        mTvProvinceTips1.setOnClickListener(this);
        mTvProvinceCannotUserGame.setOnClickListener(this);
        mTvInstructionsTop.setOnClickListener(this);

        mTvVipTips1.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);

        findViewById(R.id.rl_province_tips1).setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mProvinceRecyclerView.setLayoutManager(linearLayoutManager);

        mProvinceAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(VipMemberTypeVo.DataBean.class, new ProvinceItemHolder(_mActivity, false))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);
        mProvinceRecyclerView.setAdapter(mProvinceAdapter);

        LinearLayoutManager superLinearLayoutManager = new LinearLayoutManager(_mActivity);
        superLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mSuperProvinceRecyclerView.setLayoutManager(superLinearLayoutManager);

        mSuperProvinceAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(SuperVipMemberInfoVo.DataBean.CardType.class, new SuperProvinceItemHolder(_mActivity,false))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);
        mSuperProvinceRecyclerView.setAdapter(mSuperProvinceAdapter);
    }

    private VipMemberInfoVo.DataBean vipMemberInfodata;
    private void initData() {
        if (mViewModel != null) {
            mViewModel.getVipMemberInfo(new OnBaseCallback<VipMemberInfoVo>() {
                @Override
                public void onSuccess(VipMemberInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                vipMemberInfodata = data.getData();
                                setUserInfo();
                            }
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private SuperVipMemberInfoVo.DataBean superVipMemberInfoVo;
    private void initSuperData() {
        if (mViewModel != null) {
            mViewModel.getMoneyCardBaseInfo(new OnBaseCallback<SuperVipMemberInfoVo>() {
                @Override
                public void onSuccess(SuperVipMemberInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                superVipMemberInfoVo = data.getData();
                                mSuperProvinceAdapter.setDatas(superVipMemberInfoVo.getCard_type_list());
                                mSuperProvinceAdapter.notifyDataSetChanged();
                                setSuperInfo();
                                if (UserInfoModel.getInstance().isLogined()){
                                    if (fristCardExpiryTime == -1) {
                                        fristCardExpiryTime = superVipMemberInfoVo.getUser_card_expiry_time();
                                        fristTime = superVipMemberInfoVo.getUser_card_expiry_time();
                                    }
                                    if (fristCardExpiryTime != 0 && superVipMemberInfoVo.getUser_card_expiry_time() != 0 && fristCardExpiryTime != superVipMemberInfoVo.getUser_card_expiry_time()) {
                                        if (fristTime == 0){
                                            showPaySuccessDialog(false);
                                        }else{
                                            showPaySuccessDialog(true);
                                        }
                                        fristCardExpiryTime = superVipMemberInfoVo.getUser_card_expiry_time();
                                        fristTime = superVipMemberInfoVo.getUser_card_expiry_time();
                                    }
                                }
                                setBanner();
                            }
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void getVipMemberVoucher() {
        if (mViewModel != null) {
            mViewModel.getVipMemberVoucher(new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            initData();
                            showVoucherDialog();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void getCardReward() {
        if (mViewModel != null) {
            mViewModel.getCardReward(new OnBaseCallback<SuperRewardVo>() {
                @Override
                public void onSuccess(SuperRewardVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            initSuperData();
                            showSuperDialog(data);
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    //设置省钱卡页面信息
    private void setUserInfo() {
        if (vipMemberInfodata != null){
            mTvProvinceCount.setText("已有" + CommonUtils.formatNumber(vipMemberInfodata.getVip_member_total_count()) + "人开通");
            boolean isVipMember = UserInfoModel.getInstance().isVipMember();
            if (isVipMember){
                findViewById(R.id.rl_province_tips).setVisibility(View.VISIBLE);
                mTvProvinceTips.setText("截至" + UserInfoModel.getInstance().getVipMemberLastDate() + "到期，");
                mTvProvincevalidity.setVisibility(View.VISIBLE);
                SpannableString spannableString = new SpannableString("截至" + UserInfoModel.getInstance().getVipMemberLastDate() + "到期>");
                spannableString.setSpan(new StyleSpan(Typeface.BOLD) , 2, UserInfoModel.getInstance().getVipMemberLastDate().length() + 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mTvProvincevalidity.setText(spannableString);
                mTvProvinceTips1.setVisibility(View.VISIBLE);
                if (vipMemberInfodata.is_get_vip_member_voucher()){
                    mTvProvinceNext.setText("今日已领");
                    mTvProvinceNext.setClickable(false);
                    mTvProvinceNext.setBackgroundResource(R.drawable.ts_shape_gradient_radius_c8c8c8_7f7f7f);
                    mViewProvinceStatus.setVisibility(View.GONE);
                }else{
                    mTvProvinceNext.setText("立即领取");
                    mTvProvinceNext.setClickable(true);
                    mTvProvinceNext.setBackgroundResource(R.drawable.ts_shape_gradient_radius_ffce00_ff9a00);
                    mViewProvinceStatus.setVisibility(View.VISIBLE);
                }
                SPUtils spUtils = new SPUtils(_mActivity, Constants.SP_COMMON_NAME);
                boolean isProvinceTipsClick = spUtils.getBoolean("isProvinceTipsClick", false);
                if (!isProvinceTipsClick){
                    spUtils.putBoolean("isProvinceTipsClick", true);
                    showProvinceDialog1();
                }
            }else{
                findViewById(R.id.rl_province_tips).setVisibility(View.GONE);
                mTvProvinceTips.setText("开通领平台通用券，抵扣现金充值");
                mTvProvincevalidity.setVisibility(View.GONE);
                mTvProvinceTips1.setVisibility(View.GONE);
                mTvProvinceNext.setText("立即开通");
                mTvProvinceNext.setClickable(true);
                mTvProvinceNext.setBackgroundResource(R.drawable.ts_shape_gradient_radius_ffce00_ff9a00);
                mViewProvinceStatus.setVisibility(!isProvinceCilck?View.VISIBLE:View.GONE);
            }
        }else{
            if (UserInfoModel.getInstance().isLogined()) {
                boolean isVipMember = UserInfoModel.getInstance().isVipMember();
                if (isVipMember){
                    mTvProvinceTips.setText("截至" + UserInfoModel.getInstance().getVipMemberLastDate() + "到期，");
                    mTvProvinceTips1.setVisibility(View.VISIBLE);
                    mTvProvinceNext.setText("立即领取");
                    mTvProvinceNext.setClickable(true);
                    mTvProvinceNext.setBackgroundResource(R.drawable.ts_shape_gradient_radius_ffce00_ff9a00);
                    mViewProvinceStatus.setVisibility(View.VISIBLE);
                }else{
                    mTvProvinceTips.setText("开通领平台通用券，抵扣现金充值");
                    mTvProvinceTips1.setVisibility(View.GONE);
                    mTvProvinceNext.setText("立即开通");
                    mTvProvinceNext.setClickable(true);
                    mTvProvinceNext.setBackgroundResource(R.drawable.ts_shape_gradient_radius_ffce00_ff9a00);
                    mViewProvinceStatus.setVisibility(!isProvinceCilck?View.VISIBLE:View.GONE);
                }
            }else{
                mTvProvinceTips.setText("开通领平台通用券，抵扣现金充值");
                mTvProvincevalidity.setVisibility(View.GONE);
                mTvProvinceTips1.setVisibility(View.GONE);
                mTvProvinceNext.setText("立即开通");
                mTvProvinceNext.setClickable(true);
                mTvProvinceNext.setBackgroundResource(R.drawable.ts_shape_gradient_radius_ffce00_ff9a00);
                mViewProvinceStatus.setVisibility(!isProvinceCilck?View.VISIBLE:View.GONE);
            }
        }
    }

    //设置超级省钱卡页面信息
    private void setSuperInfo(){
        if (superVipMemberInfoVo != null){
            //mTvVipCount.setText("已有" + CommonUtils.formatNumber(superVipMemberInfoVo.getBuy_user_count()) + "人开通");
            mTvVipCount.setText("1币=1元，适用于BT游戏");
            mTvVipDayReward.setText("每天领" + superVipMemberInfoVo.getDay_reward() + "元福利币");
            if (superVipMemberInfoVo.getOpen_money_card().equals("no")){
                mTvVipTips1.setVisibility(View.GONE);
                mTvVipTips.setText("开通立即返还全额福利币");
                mTvVipNext.setClickable(true);
                mTvVipNext.setBackgroundResource(R.drawable.ts_shape_gradient_radius_ffce00_ff9a00);
                mViewVipStatus.setVisibility(!isSuperProvinceCilck?View.VISIBLE:View.GONE);
            }else{
                mTvVipTips.setText("截止" + CommonUtils.formatTimeStamp(superVipMemberInfoVo.getUser_card_expiry_time() * 1000, "yyyy-MM-dd") + "到期，");
                mTvVipTips1.setVisibility(View.VISIBLE);
                if (superVipMemberInfoVo.getHas_get_reward().equals("no")){
                    mTvVipNext.setText("立即领取");
                    mTvVipNext.setClickable(true);
                    mTvVipNext.setBackgroundResource(R.drawable.ts_shape_gradient_radius_ffce00_ff9a00);
                    mViewVipStatus.setVisibility(View.VISIBLE);
                }else{
                    mTvVipNext.setText("今日已领");
                    mTvVipNext.setClickable(false);
                    mTvVipNext.setBackgroundResource(R.drawable.ts_shape_gradient_radius_c8c8c8_7f7f7f);
                    mViewVipStatus.setVisibility(View.GONE);
                }
            }
        }else{
            mTvVipTips1.setVisibility(View.VISIBLE);
            mTvVipCount.setText("1币=1元，可充任意游戏");
            mTvVipTips.setText("开通立即返还全额福利币");
            mTvVipNext.setText("立即开通");
            mTvVipNext.setClickable(true);
            mTvVipNext.setBackgroundResource(R.drawable.ts_shape_gradient_radius_ffce00_ff9a00);
            mViewVipStatus.setVisibility(!isSuperProvinceCilck?View.VISIBLE:View.GONE);
        }
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        getVipTypesData(false);
        initData();
        initSuperData();
        if (UserInfoModel.getInstance().isLogined()){
            if (UserInfoModel.getInstance().getUserInfo().getVip_member() != null && fristVipMemberLastDate == -1){
                fristVipMemberLastDate = UserInfoModel.getInstance().getUserInfo().getVip_member().getVip_member_expire();
                fristDate = UserInfoModel.getInstance().getUserInfo().getVip_member().getVip_member_expire();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mViewModel != null) {
            if (UserInfoModel.getInstance().isLogined()){
                UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
                mViewModel.getUserInfoCallBack1(userInfo.getUid(), userInfo.getToken(), userInfo.getUsername(), data -> {
                    initData();
                    initSuperData();

                    if (UserInfoModel.getInstance().isLogined()){
                        if (UserInfoModel.getInstance().getUserInfo().getVip_member() != null && fristVipMemberLastDate == -1){
                            fristVipMemberLastDate = UserInfoModel.getInstance().getUserInfo().getVip_member().getVip_member_expire();
                            fristDate = UserInfoModel.getInstance().getUserInfo().getVip_member().getVip_member_expire();
                        }
                        if (UserInfoModel.getInstance().getUserInfo().getVip_member() != null && UserInfoModel.getInstance().getUserInfo().getVip_member().getVip_member_expire() != 0 && fristVipMemberLastDate != UserInfoModel.getInstance().getUserInfo().getVip_member().getVip_member_expire()) {
                            if (fristDate == 0){
                                showPaySuccessDialog(false);
                            }else{
                                showPaySuccessDialog(true);
                            }
                            fristVipMemberLastDate = UserInfoModel.getInstance().getUserInfo().getVip_member().getVip_member_expire();
                            fristDate = UserInfoModel.getInstance().getUserInfo().getVip_member().getVip_member_expire();
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                pop();
                break;
            case R.id.tv_province_cannot_use_game:
            case R.id.tv_instructions_top:
                //getCannotUsGameList();
                BrowserActivity.newInstance(_mActivity, "https://mobile.xiaodianyouxi.com/index.php/Index/view/?id=100000051", true, "", "", false);
                break;
            case R.id.tv_province_next:
                if (!isProvinceCilck) {
                    SPUtils spUtils = new SPUtils(_mActivity, Constants.SP_COMMON_NAME);
                    spUtils.putBoolean("isProvinceCilck", true);
                    isProvinceCilck = true;
                    mViewProvinceStatus.setVisibility(View.GONE);
                }
                if (checkLogin()) {
                    if (UserInfoModel.getInstance().isVipMember()){
                        getVipMemberVoucher();
                    }else{
                        if (!TextUtils.isEmpty(UserInfoModel.getInstance().getUserInfo().getIdcard()) && !TextUtils.isEmpty(UserInfoModel.getInstance().getUserInfo().getReal_name())){
                            if (UserInfoModel.getInstance().getUserInfo().getAdult().equals("yes")){
                                showBuyVipMemberDialog();
                            }else{
                                Toaster.show("满18岁用户才能购买！");
                            }
                        }else {
                            startFragment(CertificationFragment.newInstance());
                        }
                    }
                }
                break;
            case R.id.tv_vip_next:
                if (!isSuperProvinceCilck) {
                    SPUtils spUtils = new SPUtils(_mActivity, Constants.SP_COMMON_NAME);
                    spUtils.putBoolean("isSuperProvinceCilck", true);
                    isSuperProvinceCilck = true;
                    mViewVipStatus.setVisibility(View.GONE);
                }
                if (checkLogin()) {
                    if (superVipMemberInfoVo != null){
                        if (superVipMemberInfoVo.getOpen_money_card().equals("no")){
                            /*if (superVipMemberInfoVo.getOpen_super_user() > 0){
                                showBuySuperVipDialog();
                            }else{
                                showSuperTipsDialog();
                            }*/
                            showBuySuperVipDialog();
                        }else {
                            getCardReward();
                        }
                    }
                }
                break;
            case R.id.tv_province_tips1:
                if (checkLogin()) {
                    if (!TextUtils.isEmpty(UserInfoModel.getInstance().getUserInfo().getIdcard()) && !TextUtils.isEmpty(UserInfoModel.getInstance().getUserInfo().getReal_name())){
                        if (UserInfoModel.getInstance().getUserInfo().getAdult().equals("yes")){
                            showBuyVipMemberDialog();
                        }else{
                            Toaster.show("满18岁用户才能购买！");
                        }
                    }else {
                        startFragment(CertificationFragment.newInstance());
                    }
                }
                break;

            case R.id.tv_vip_tips1:
                if (checkLogin()) {
                    if (!TextUtils.isEmpty(UserInfoModel.getInstance().getUserInfo().getIdcard()) && !TextUtils.isEmpty(UserInfoModel.getInstance().getUserInfo().getReal_name())) {
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
            case R.id.tv_instructions:
                //startFragment(new ProvinceCardInstructionFragment());
                break;
            case R.id.rl_province_tips1:
                if (checkLogin()) {
                    if (UserInfoModel.getInstance().isVipMember()){
                        showProvinceDialog();
                    }
                }
                break;
            default:
                break;
        }
    }

    private VipMemberTypeVo mVipMemberTypeVo;
    private void getVipTypesData(boolean isFrist) {
        if (mViewModel != null) {
            mViewModel.getVipTypes(new OnBaseCallback<VipMemberTypeVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                }

                @Override
                public void onSuccess(VipMemberTypeVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null && !data.getData().isEmpty()) {
                                mVipMemberTypeVo = data;
                                mProvinceAdapter.setDatas(mVipMemberTypeVo.getData());
                                mProvinceAdapter.notifyDataSetChanged();

                                if (isFrist){
                                    SPUtils spUtils = new SPUtils(_mActivity, Constants.SP_COMMON_NAME);
                                    long isProvinceTipsShow = spUtils.getLong("isProvinceTipsShow");//每天第一次弹框是否显示
                                    long isProvinceTipsShow2 = spUtils.getLong("isProvinceTipsShow2");//每天第二次弹框是否显示
                                    String provinceTips = spUtils.getString("provinceTipsType");//上一次弹框的样式
                                    if (!CommonUtils.isToday(isProvinceTipsShow)){//判断记录的时间戳是不是今天
                                        spUtils.putLong("isProvinceTipsShow", System.currentTimeMillis());
                                        if (UserInfoModel.getInstance().isLogined()){
                                            if (!UserInfoModel.getInstance().isVipMember()){
                                                boolean hasTrial = false;
                                                for (int i = 0; i < mVipMemberTypeVo.getData().size(); i++) {
                                                    if (mVipMemberTypeVo.getData().get(i).getType_id() == 4) hasTrial = true;
                                                }
                                                if (hasTrial){
                                                    spUtils.putString("provinceTipsType", "Tips");
                                                    //showTipsDialog();
                                                }else{
                                                    spUtils.putString("provinceTipsType", "");
                                                    //getCanUsGameList();
                                                }
                                            }
                                        }else{
                                            spUtils.putString("provinceTipsType", "Tips");
                                            //showTipsDialog();
                                        }
                                    }else if (!CommonUtils.isToday(isProvinceTipsShow2)){
                                        if (UserInfoModel.getInstance().isLogined()){
                                            spUtils.putLong("isProvinceTipsShow2", System.currentTimeMillis());
                                            if (!UserInfoModel.getInstance().isVipMember()){
                                                if (provinceTips.equals("Tips")){
                                                    spUtils.putString("provinceTipsType", "");
                                                    //getCanUsGameList();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private AppCompatTextView mTvVipMemberUsername;
    private RelativeLayout mRlPayAlipay;
    private ImageView      mIvPayAlipay;
    private RelativeLayout mRlPayWechat;
    private ImageView    mIvPayWechat;
    private Button        mBtnConfirm;
    private RecyclerView mDialogRecyclerView;
    private BaseRecyclerAdapter mDialogAdapter;
    private CustomDialog  dialog;
    private void showBuyVipMemberDialog() {
        if (dialog == null) {
            dialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_buy_province, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
            mTvVipMemberUsername = dialog.findViewById(R.id.tv_vip_member_username);
            mRlPayAlipay = dialog.findViewById(R.id.rl_pay_alipay);
            mIvPayAlipay = dialog.findViewById(R.id.iv_pay_alipay);
            mRlPayWechat = dialog.findViewById(R.id.rl_pay_wechat);
            mIvPayWechat = dialog.findViewById(R.id.iv_pay_wechat);
            mBtnConfirm = dialog.findViewById(R.id.btn_confirm);
            mDialogRecyclerView = dialog.findViewById(R.id.recycler_view_province_dialog);

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

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mDialogRecyclerView.setLayoutManager(linearLayoutManager);
            mDialogAdapter = new BaseRecyclerAdapter.Builder<>()
                    .bind(VipMemberTypeVo.DataBean.class, new ProvinceItemHolder(_mActivity, true))
                    .build().setTag(R.id.tag_fragment, _mActivity)
                    .setTag(R.id.tag_fragment, this);
            mDialogRecyclerView.setAdapter(mDialogAdapter);

            mDialogAdapter.setOnItemClickListener((v, position, data) -> {
                for (int i = 0; i < mDialogAdapter.getData().size(); i++) {
                    VipMemberTypeVo.DataBean dataBean = (VipMemberTypeVo.DataBean) mDialogAdapter.getData().get(i);
                    if (i == position){
                        dataBean.setSelected(true);
                    }else{
                        dataBean.setSelected(false);
                    }
                }
                mDialogAdapter.notifyDataSetChanged();
            });

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

            mBtnConfirm.setOnClickListener(view -> {
                int buyTypeId = 0;
                for (int i = 0; i < mDialogAdapter.getData().size(); i++) {
                    VipMemberTypeVo.DataBean dataBean = (VipMemberTypeVo.DataBean) mDialogAdapter.getData().get(i);
                    if (dataBean.isSelected()) {
                        buyTypeId = dataBean.getType_id();
                    }
                }
                buyVipMember(buyTypeId, PAY_TYPE);
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
            });

        }
        if (mVipMemberTypeVo != null){
            if (mVipMemberTypeVo.getData().size() > 0) mVipMemberTypeVo.getData().get(0).setSelected(true);
            mDialogAdapter.setDatas(mVipMemberTypeVo.getData());
            mDialogAdapter.notifyDataSetChanged();
        }
        UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
        mTvVipMemberUsername.setText(userInfo.getUsername());

        mRlPayAlipay.performClick();
        dialog.show();
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

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mDialogSuperRecyclerView.setLayoutManager(linearLayoutManager);
            mSuperDialogAdapter = new BaseRecyclerAdapter.Builder<>()
                    .bind(SuperVipMemberInfoVo.DataBean.CardType.class, new SuperProvinceItemHolder(_mActivity, true))
                    .build().setTag(R.id.tag_fragment, _mActivity)
                    .setTag(R.id.tag_fragment, this);
            mDialogSuperRecyclerView.setAdapter(mSuperDialogAdapter);
            if (superVipMemberInfoVo != null){
                List<SuperVipMemberInfoVo.DataBean.CardType> data = superVipMemberInfoVo.getCard_type_list();
                if (data != null && data.size() > 0){
                    data.get(0).setSelected(true);
                }
                mSuperDialogAdapter.setDatas(data);
                mSuperDialogAdapter.notifyDataSetChanged();
            }

            mSuperDialogAdapter.setOnItemClickListener((v, position, data) -> {
                for (int i = 0; i < mSuperDialogAdapter.getData().size(); i++) {
                    SuperVipMemberInfoVo.DataBean.CardType dataBean = (SuperVipMemberInfoVo.DataBean.CardType) mSuperDialogAdapter.getData().get(i);
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

            mBtnSuperConfirm.setOnClickListener(view -> {
                int buyTypeId = 0;
                for (int i = 0; i < mSuperDialogAdapter.getData().size(); i++) {
                    SuperVipMemberInfoVo.DataBean.CardType dataBean = (SuperVipMemberInfoVo.DataBean.CardType) mSuperDialogAdapter.getData().get(i);
                    if (dataBean.isSelected()) {
                        buyTypeId = dataBean.getId();
                    }
                }
                buySuperVipMember(buyTypeId, PAY_TYPE);
                if (superDialog != null && superDialog.isShowing()) superDialog.dismiss();
            });

        }
        UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
        mTvSuperVipMemberUsername.setText(userInfo.getUsername());

        mRlSuperPayAlipay.performClick();
        superDialog.show();
    }

    private void buyVipMember(int vip_member_type, int vip_member_pay_type) {
        if (mViewModel != null) {
            mViewModel.buyVipMember(vip_member_type, vip_member_pay_type, new OnBaseCallback<PayInfoVo>() {
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

    private void buySuperVipMember(int card_type, int pay_type) {
        if (mViewModel != null) {
            mViewModel.buySuperVipMember(card_type, pay_type, new OnBaseCallback<PayInfoVo>() {
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

    private void getCanUsGameList() {
        if (mViewModel != null) {
            mViewModel.getCanUsGameList(new OnBaseCallback<CanUsGameListInfoVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                }

                @Override
                public void onSuccess(CanUsGameListInfoVo gameListInfoVo) {
                    if (gameListInfoVo != null) {
                        if (gameListInfoVo.isStateOK()) {
                            if (gameListInfoVo.getData() != null && gameListInfoVo.getData().size() > 0) {
                                showCanUsGameDialog(gameListInfoVo.getData());
                            }
                        }
                    }
                }
            });
        }
    }

    private void getCannotUsGameList() {
        if (mViewModel != null) {
            mViewModel.getCannotUsGameList(new OnBaseCallback<CanUsGameListInfoVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                }

                @Override
                public void onSuccess(CanUsGameListInfoVo gameListInfoVo) {
                    if (gameListInfoVo != null) {
                        if (gameListInfoVo.isStateOK()) {
                            if (gameListInfoVo.getData() != null && gameListInfoVo.getData().size() > 0) {
                                showCannotUsGameDialog(gameListInfoVo.getData());
                            }
                        }
                    }
                }
            });
        }
    }

    private void showPaySuccessDialog(boolean isRenewal){
        CustomDialog voucherDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_province_pay_success, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        TextView tvTitle = voucherDialog.findViewById(R.id.tv_title);
        if (isRenewal){
            tvTitle.setText("续费成功");
        }else{
            tvTitle.setText("开通成功");
        }
        voucherDialog.findViewById(R.id.btn_confirm).setOnClickListener(view -> {
            if (voucherDialog != null && voucherDialog.isShowing()) voucherDialog.dismiss();
        });
        voucherDialog.show();
    }

    private CustomDialog provinceDialog;
    private void showProvinceDialog(){
        if (provinceDialog == null) provinceDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_province_obtain_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        provinceDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
            if (provinceDialog != null && provinceDialog.isShowing()) provinceDialog.dismiss();
        });
        ((TextView)provinceDialog.findViewById(R.id.tv_date)).setText(UserInfoModel.getInstance().getVipMemberLastDate());
        if (vipMemberInfodata != null && vipMemberInfodata.is_get_vip_member_voucher()){//已领
            ((TextView)provinceDialog.findViewById(R.id.tv_confirm)).setText("今日已领");
            provinceDialog.findViewById(R.id.tv_confirm).setBackgroundResource(R.drawable.shape_9d9d9d_big_radius);
            provinceDialog.findViewById(R.id.tv_confirm).setOnClickListener(null);
        }else {
            ((TextView)provinceDialog.findViewById(R.id.tv_confirm)).setText("领取");
            provinceDialog.findViewById(R.id.tv_confirm).setBackgroundResource(R.drawable.shape_ffe500_ff9600_big_radius);
            provinceDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
                if (provinceDialog != null && provinceDialog.isShowing()) provinceDialog.dismiss();
                getVipMemberVoucher();
            });
        }
        provinceDialog.show();
    }

    private CustomDialog provinceDialog1;
    private void showProvinceDialog1(){
        if (provinceDialog1 == null) provinceDialog1 = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_province_obtain_tips1, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        provinceDialog1.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (provinceDialog1 != null && provinceDialog1.isShowing()) provinceDialog1.dismiss();
        });
        provinceDialog1.show();
    }


    private void showVoucherDialog(){
        CustomDialog voucherDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_province_voucher_success, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        RelativeLayout mRlAdvertising = voucherDialog.findViewById(R.id.rl_advertising);
        ImageView mIvAdvertising = voucherDialog.findViewById(R.id.iv_advertising);
        if (superVipMemberInfoVo != null && superVipMemberInfoVo.getAd_banner() != null){
            mIvAdvertising.setVisibility(View.VISIBLE);
            mRlAdvertising.setVisibility(View.VISIBLE);
            GlideApp.with(_mActivity).asBitmap().load(superVipMemberInfoVo.getAd_banner().getPic()).transform(new GlideRoundTransformNew(_mActivity, 8)).into(mIvAdvertising);
            mIvAdvertising.setOnClickListener(v -> {
                new AppJumpAction(_mActivity).jumpAction(new AppBaseJumpInfoBean(superVipMemberInfoVo.getAd_banner().getPage_type(), superVipMemberInfoVo.getAd_banner().getParam()));
            });
            if (voucherDialog != null && voucherDialog.isShowing()) voucherDialog.dismiss();
        }
        voucherDialog.findViewById(R.id.btn_confirm).setOnClickListener(view -> {
            if (voucherDialog != null && voucherDialog.isShowing()) voucherDialog.dismiss();
        });
        voucherDialog.show();
    }

    private void showSuperDialog(SuperRewardVo data){
        CustomDialog voucherDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_super_province_voucher_success, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        RelativeLayout mRlAdvertising = voucherDialog.findViewById(R.id.rl_advertising);
        ImageView mIvAdvertising = voucherDialog.findViewById(R.id.iv_advertising);
        TextView mTvPrice = voucherDialog.findViewById(R.id.tv_price);
        if (data != null && data.getData() != null){
            if (data.getData().getReward_type().equals("ptb")){
                mTvPrice.setText(data.getData().getReward() + "福利币");
            }else if (data.getData().getReward_type().equals("integral")){
                mTvPrice.setText(data.getData().getReward() + "积分");
            }else if (data.getData().getReward_type().equals("coupon")){
                mTvPrice.setText(data.getData().getReward() + "代金券");
            }
        }
        if (superVipMemberInfoVo != null && superVipMemberInfoVo.getAd_banner() != null){
            mIvAdvertising.setVisibility(View.VISIBLE);
            mRlAdvertising.setVisibility(View.VISIBLE);
            GlideApp.with(_mActivity).asBitmap().load(superVipMemberInfoVo.getAd_banner().getPic()).transform(new GlideRoundTransformNew(_mActivity, 8)).into(mIvAdvertising);
            mIvAdvertising.setOnClickListener(v -> {
                new AppJumpAction(_mActivity).jumpAction(new AppBaseJumpInfoBean(superVipMemberInfoVo.getAd_banner().getPage_type(), superVipMemberInfoVo.getAd_banner().getParam()));
            });
            if (voucherDialog != null && voucherDialog.isShowing()) voucherDialog.dismiss();
        }
        voucherDialog.findViewById(R.id.btn_confirm).setOnClickListener(view -> {
            if (voucherDialog != null && voucherDialog.isShowing()) voucherDialog.dismiss();
        });
        voucherDialog.show();
    }

    private void showTipsDialog(){
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_province_card_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (checkLogin()){
                if (!TextUtils.isEmpty(UserInfoModel.getInstance().getUserInfo().getIdcard()) && !TextUtils.isEmpty(UserInfoModel.getInstance().getUserInfo().getReal_name())){
                    if (UserInfoModel.getInstance().getUserInfo().getAdult().equals("yes")){
                        showBuyVipMemberDialog();
                    }else{
                        Toaster.show("满18岁用户才能购买！");
                    }
                }else {
                    startFragment(CertificationFragment.newInstance());
                }
                if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            }
        });
        tipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.show();
    }

    private void showSuperTipsDialog(){
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_super_province_card_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        SpannableString ss = new SpannableString("超级尊享版卡为会员独享特权福利，仅限会员用户购买。(1福利币=1元，可充值全平台游戏）");
        ss.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#F71818"));
            }

            @Override
            public void onClick(@NonNull View widget) { }
        }, 25, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ((TextView) tipsDialog.findViewById(R.id.tv_vip_tips)).setText(ss);
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (checkLogin()){
                startFragment(new NewUserVipFragment());
                if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            }
        });
        tipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.show();
    }

    private void showCanUsGameDialog(List<GameInfoVo> gameInfoVos){
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_province_can_use_game, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        RecyclerView gameRecylerView = tipsDialog.findViewById(R.id.recycler_view_game);
        gameRecylerView.setLayoutManager(new GridLayoutManager(_mActivity, 4));
        BaseRecyclerAdapter mGameDialogAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(GameInfoVo.class, new ProvinceGameItemHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);
        gameRecylerView.setAdapter(mGameDialogAdapter);
        mGameDialogAdapter.setDatas(gameInfoVos);
        mGameDialogAdapter.notifyDataSetChanged();
        mGameDialogAdapter.setOnItemClickListener((v, position, data) -> {
            startFragment(GameDetailInfoFragment.newInstance(((GameInfoVo)data).getGameid(), ((GameInfoVo)data).getGame_type()));
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.findViewById(R.id.btn_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.show();
    }

    private void showCannotUsGameDialog(List<GameInfoVo> gameInfoVos){
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_province_can_use_game, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        ((TextView) tipsDialog.findViewById(R.id.tv_title)).setText("不可用券名单");
        tipsDialog.findViewById(R.id.tv_tips).setVisibility(View.GONE);
        RecyclerView gameRecylerView = tipsDialog.findViewById(R.id.recycler_view_game);
        gameRecylerView.setLayoutManager(new GridLayoutManager(_mActivity, 4));
        BaseRecyclerAdapter mGameDialogAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(GameInfoVo.class, new ProvinceGameItemHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);
        gameRecylerView.setAdapter(mGameDialogAdapter);
        mGameDialogAdapter.setDatas(gameInfoVos);
        mGameDialogAdapter.notifyDataSetChanged();
        mGameDialogAdapter.setOnItemClickListener((v, position, data) -> {
            startFragment(GameDetailInfoFragment.newInstance(((GameInfoVo)data).getGameid(), ((GameInfoVo)data).getGame_type()));
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.findViewById(R.id.btn_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.show();
    }

    private void setBanner(){
        if (superVipMemberInfoVo != null && superVipMemberInfoVo.getBanner_list() != null && superVipMemberInfoVo.getBanner_list().size() > 0){
            mLlBanner.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params = mBanner.getLayoutParams();
            if (params != null) {
                params.height = (ScreenUtil.getScreenWidth(_mActivity) - ScreenUtil.dp2px(_mActivity, 30)) * 180 / 710;
                mBanner.setLayoutParams(params);
            }
            int bannerSize = superVipMemberInfoVo.getBanner_list().size();
            //设置banner样式
            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置图片加载器
            mBanner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    SuperVipMemberInfoVo.DataBean.MemberRewardBanner bannerVo = (SuperVipMemberInfoVo.DataBean.MemberRewardBanner) path;
                    GlideApp.with(_mActivity)
                            .load(bannerVo.getPic())
                            .placeholder(R.mipmap.img_placeholder_v_load)
                            .error(R.mipmap.img_placeholder_v_load)
                            .transform(new GlideRoundTransformNew(_mActivity, 10))
                            .into(imageView);
                }
            });
            //设置图片集合
            mBanner.setImages(superVipMemberInfoVo.getBanner_list());
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
                    SuperVipMemberInfoVo.DataBean.MemberRewardBanner memberRewardBanner = superVipMemberInfoVo.getBanner_list().get(position);
                    if (memberRewardBanner != null) {
                        if (_mActivity != null) {
                            AppBaseJumpInfoBean appBaseJumpInfoBean = new AppBaseJumpInfoBean(memberRewardBanner.getPage_type(), memberRewardBanner.getParam());
                            AppJumpAction appJumpAction = new AppJumpAction(_mActivity);
                            appJumpAction.jumpAction(appBaseJumpInfoBean);
                        }
                    }
                }
            });
            //banner设置方法全部调用完毕时最后调用
            mBanner.start();
        }else {
            mLlBanner.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPaySuccess() {
        super.onPaySuccess();
        if (dialog != null) {
            dialog.dismiss();
        }
        if (mViewModel != null) {
            mViewModel.refreshUserData();
        }
    }

    @Override
    protected void onPayCancel() {
        super.onPayCancel();
        Log.e("onPayCancel", "onPayCancel");
    }
}
