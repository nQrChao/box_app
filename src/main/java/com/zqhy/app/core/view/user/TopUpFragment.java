package com.zqhy.app.core.view.user;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.hjq.toast.Toaster;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zqhy.app.adapter.CoinChooseAdapter;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.config.SpConstants;
import com.zqhy.app.config.WxControlConfig;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.RealNameCheckVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;
import com.zqhy.app.core.data.model.user.PayInfoVo;
import com.zqhy.app.core.data.model.user.PayRebateVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.AbsPayBuyFragment;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.currency.CurrencyMainFragment;
import com.zqhy.app.core.vm.user.TopUpViewModel;
import com.zqhy.app.glide.GlideRoundTransformNew;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * 钱包充值
 *
 * @author Administrator
 * @date 2018/11/13
 */

public class TopUpFragment extends AbsPayBuyFragment<TopUpViewModel> implements View.OnClickListener {

    public static TopUpFragment newInstance() {
        TopUpFragment fragment = new TopUpFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "充值中心";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_top_up;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }


    private String sp_key;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        sp_key = "TOP_UP" + SpConstants.SP_REAL_NAME_STATE + UserInfoModel.getInstance().getUID();
        initActionBackBarAndTitle("充值中心");
        bindView();
        refreshUser();
        getAdSwiperList();
    }

    @Override
    protected int getPayAction() {
        return PAY_ACTION_TOP_UP;
    }

    private String amount;

    private TextView     mTvAccount;
    private TextView mTvGive;
    private LinearLayout mLlGive;
    private TextView     mTvPtbCount;
    private RecyclerView mRecyclerViewCoin;
    private LinearLayout mLlEtAmount;
    private EditText     mEtPtbCount;
    private TextView       mTvRecharge;

    private View           mLineShadow;
    private TextView       mTvRechargeAmount;
    private TextView       mTvRechargeDetail;
    private RelativeLayout mLlRechargeAlipay;
    private RelativeLayout mLlRechargeWechat;
    private Banner mBanner;
    private LinearLayout   mLlPayRate;
    private TextView       mTvPayRate;

    private void initViews() {
        mTvAccount = findViewById(R.id.tv_account);
        mTvGive = findViewById(R.id.tv_give);
        mLlGive = findViewById(R.id.ll_give);
        mTvPtbCount = findViewById(R.id.tv_ptb_count);
        mRecyclerViewCoin = findViewById(R.id.recyclerView_coin);
        mLlEtAmount = findViewById(R.id.ll_et_amount);
        mEtPtbCount = findViewById(R.id.et_ptb_count);
        mTvRecharge = findViewById(R.id.tv_recharge);
        mLineShadow = findViewById(R.id.line_shadow);
        mTvRechargeAmount = findViewById(R.id.tv_recharge_amount);
        mTvRechargeDetail = findViewById(R.id.tv_recharge_detail);
        mLlRechargeAlipay = findViewById(R.id.ll_recharge_alipay);
        mLlRechargeWechat = findViewById(R.id.ll_recharge_wechat);
        mBanner = findViewById(R.id.banner);
        mLlPayRate = findViewById(R.id.ll_pay_rate);
        mTvPayRate = findViewById(R.id.tv_pay_rate);

        setLayoutUser();
        setUserGold();

        mEtPtbCount.setOnFocusChangeListener((View view, boolean b) -> {
            if (b && coinsAdapter != null) {
                amount = "";
                coinsAdapter.releaseSelected();
            }
            setPtbEditBackground(b);
            setRechargeAmount();
        });
        setPtbEditBackground(false);

        mEtPtbCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(mEtPtbCount.getText().toString().trim())) {
                    amount = "";
                } else {
                    amount = mEtPtbCount.getText().toString().trim();
                }
                setRechargeAmount();
            }
        });
        mTvRecharge.setOnClickListener(this);
        mTvRechargeDetail.setOnClickListener(this);

        mLlGive.setOnClickListener(v -> {
            //赠币弹窗
            showGiveDialog();
        });
    }

    private void setRechargeAmount() {
        if (mTvRechargeAmount != null) {
            if (TextUtils.isEmpty(amount)) {
                mTvRechargeAmount.setVisibility(View.GONE);
            } else {
                mTvRechargeAmount.setVisibility(View.VISIBLE);
                mTvRechargeAmount.setText(amount + "");
            }
        }
    }

    private void setLayoutUser() {
        GradientDrawable gdShadow = new GradientDrawable();
        gdShadow.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        gdShadow.setColors(new int[]{Color.parseColor("#00000000"), Color.parseColor("#29000000")});
        mLineShadow.setBackground(gdShadow);
    }

    public void setUserGold() {
        UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
        if (userInfoBean == null) {
            return;
        }
        mTvAccount.setText(userInfoBean.getUsername() + "，账号余额：");
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String ptbPc = decimalFormat.format(userInfoBean.getPtb_dc());
        if ("0.00".equals(ptbPc)) ptbPc = "0";
        mTvGive.setText("含赠币：" + ptbPc);
        //mTvGive.setText("含赠币：" + userInfoBean.getPtb_dc());
        String ptb = String.format("%.2f", userInfoBean.getPingtaibi() - userInfoBean.getPtb_dc());
        if ("0.00".equals(ptb)) ptb = "0";
        mTvPtbCount.setText(ptb);
    }

    private void setPtbEditBackground(boolean isFocus) {
        if (mLlEtAmount != null) {
            if (isFocus){
                mLlEtAmount.setBackgroundResource(R.drawable.shape_fafafa_15_radius_with_line_0080ff);
            }else {
                mLlEtAmount.setBackgroundResource(R.drawable.shape_fafafa_15_radius);
            }
        }
    }

    private void bindView() {
        initViews();
        initList();

        mLlRechargeAlipay.setOnClickListener(v -> clickAliPay());
        mLlRechargeWechat.setOnClickListener(v -> clickWechat());

        if (WxControlConfig.isWxControl()) {
            mLlRechargeWechat.setVisibility(View.VISIBLE);
        } else {
            mLlRechargeWechat.setVisibility(View.GONE);
        }
        clickAliPay();
    }

    private void clickAliPay() {
        PAY_TYPE = PAY_TYPE_ALIPAY;
        mLlRechargeAlipay.setBackgroundResource(R.drawable.shape_fafafa_15_radius_with_line_0080ff);
        mLlRechargeWechat.setBackgroundResource(R.drawable.shape_fafafa_15_radius);
    }

    private void clickWechat() {
        PAY_TYPE = PAY_TYPE_WECHAT;
        mLlRechargeWechat.setBackgroundResource(R.drawable.shape_fafafa_15_radius_with_line_0080ff);
        mLlRechargeAlipay.setBackgroundResource(R.drawable.shape_fafafa_15_radius);
    }

    private CoinChooseAdapter coinsAdapter;

    private void initList() {
        GridLayoutManager layoutManager = new GridLayoutManager(_mActivity, 3);
        mRecyclerViewCoin.setLayoutManager(layoutManager);

        final String[] coins = getActivity().getResources().getStringArray(R.array.coin_list);

        coinsAdapter = new CoinChooseAdapter(getActivity(), Arrays.asList(coins));
        mRecyclerViewCoin.setAdapter(coinsAdapter);

        coinsAdapter.setOnItemClickListener((View v, int position, Object data) -> {
            mEtPtbCount.getText().clear();
            mEtPtbCount.clearFocus();
            amount = coins[position].split("@")[0];
            setRechargeAmount();
        });
        coinsAdapter.selectPosition(2);
        amount = "100";
        setRechargeAmount();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_recharge:
                String etAmount = mEtPtbCount.getText().toString().trim();
                if (TextUtils.isEmpty(amount) && TextUtils.isEmpty(etAmount)) {
                    Toaster.show( "请输入充值钱包数量");
                    return;
                }
                if (!TextUtils.isEmpty(etAmount)) {
                    amount = etAmount;
                }

                try {
                    float coin = Float.parseFloat(amount);
                    if (coin <= 0) {
                        Toaster.show( "请输入正确的钱包数量！");
                        return;
                    }
                    if (coin < 10) {
                        Toaster.show( "钱包数量10个起充！");
                        return;
                    }
                    //取消实名认证
                    //                    if (checkUserHasRealName()) {
                    //                    }
                    if (checkLogin()) {
                        doRechargeAction(String.valueOf(PAY_TYPE), coin);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_recharge_detail:
                startFragment(new CurrencyMainFragment());
                break;
            default:
                break;
        }
    }


    private void doRechargeAction(final String paytype, final float amount) {
        int code = AppConfig.getSbData(sp_key, 3);
        if (code == 2 || code == 1) {
            //only once
            doRecharge(paytype, amount);
        } else if (code == 3) {
            //always
            checkRealName(paytype, amount);
        }

    }

    private void doRecharge(String paytype, float amount) {
        if (mViewModel != null) {
            mViewModel.doRecharge(paytype, String.valueOf(amount), new OnBaseCallback<PayInfoVo>() {
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
                                doPay(payInfoVo.getData(), amount);
                            }
                        } else {
                            Toaster.show( payInfoVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void checkRealName(String paytype, float amount) {
        if (mViewModel != null) {
            mViewModel.realNameCheck(new OnBaseCallback<RealNameCheckVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                    loading();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                }

                @Override
                public void onSuccess(RealNameCheckVo realNameCheckVo) {
                    if (realNameCheckVo != null) {
                        if (realNameCheckVo.isStateOK()) {
                            doRecharge(paytype, amount);
                            AppConfig.putSbData(sp_key, 1);
                        } else if (realNameCheckVo.isStateTip()) {
                            if (realNameCheckVo.getData() != null) {
                                int code = realNameCheckVo.getData().realname_state;
                                AppConfig.putSbData(sp_key, code);
                                startFragment(CertificationFragment.newInstance());
                            }
                        } else {
                            Toaster.show( realNameCheckVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void getAdSwiperList(){
        if (mViewModel != null) {
            mViewModel.payRebate(new OnBaseCallback<PayRebateVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                }

                @Override
                public void onSuccess(PayRebateVo data) {
                    if (data != null) {
                        if (data.isStateOK() && data.getData() != null) {
                            if (data.getData() != null){
                                if (data.getData().getPay_rate() > 0 && data.getData().getPay_rate() < 100){
                                    mLlPayRate.setVisibility(View.VISIBLE);
                                    mTvPayRate.setText(data.getData().getPay_rate() + "%");
                                } else {
                                    mLlPayRate.setVisibility(View.GONE);
                                }
                                if (data.getData().getSlider() != null && data.getData().getSlider().size() > 0){
                                    mBanner.setVisibility(View.VISIBLE);
                                    ViewGroup.LayoutParams params = mBanner.getLayoutParams();
                                    if (params != null) {
                                        params.height = (ScreenUtil.getScreenWidth(_mActivity) - ScreenUtil.dp2px(_mActivity, 20)) * 180 / 710;
                                        mBanner.setLayoutParams(params);
                                    }
                                    int bannerSize = data.getData().getSlider().size();
                                    //设置banner样式
                                    mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                                    //设置图片加载器
                                    mBanner.setImageLoader(new ImageLoader() {
                                        @Override
                                        public void displayImage(Context context, Object path, ImageView imageView) {
                                            PayRebateVo.SliderBean bannerVo = (PayRebateVo.SliderBean) path;
                                            GlideApp.with(_mActivity)
                                                    .load(bannerVo.getPic())
                                                    .placeholder(R.mipmap.img_placeholder_v_load)
                                                    .error(R.mipmap.img_placeholder_v_load)
                                                    .transform(new GlideRoundTransformNew(_mActivity, 10))
                                                    .into(imageView);
                                        }
                                    });
                                    //设置图片集合
                                    mBanner.setImages(data.getData().getSlider());
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
                                            PayRebateVo.SliderBean adSwiperBean = data.getData().getSlider().get(position);
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
        setUserGold();
    }


    @Override
    protected void onPaySuccess() {
        super.onPaySuccess();
        if (mEtPtbCount != null) {
            mEtPtbCount.getText().clear();
            mEtPtbCount.clearFocus();
        }
        refreshUser();
    }

    @Override
    protected void onPayCancel() {
        super.onPayCancel();
        Logs.e("(onPayCancel = " + "TopUpFragment-------------------------------");
    }

    private void refreshUser() {
        if (mViewModel != null) {
            mViewModel.refreshUserData(new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    showSuccess();
                    Logs.e("(onPaySuccess = " + "TopUpFragment-------------------------------");
                    setUserGold();
                }
            });
        }
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
