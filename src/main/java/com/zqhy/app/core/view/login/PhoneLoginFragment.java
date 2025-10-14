package com.zqhy.app.core.view.login;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.newproject.BuildConfig;

import androidx.annotation.NonNull;

import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.model.TokenRet;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.data.model.user.VerificationCodeVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.view.login.event.AuthLoginEvent;
import com.zqhy.app.core.vm.login.LoginViewModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.report.AllDataReportAgency;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Administrator
 * @date 2018/11/2
 */

public class PhoneLoginFragment extends BaseFragment<LoginViewModel> {

    public static PhoneLoginFragment newInstance() {
        PhoneLoginFragment fragment = new PhoneLoginFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_phone_login;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("");
        setTitleBottomLine(View.GONE);
        bindView();
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    private Button   mBtLogin;
    private EditText mEtPhone;
    private ImageView mIvPhoneDelete;
    private EditText mEtVerificationCode;
    private TextView mTvSendCode;
    private TextView mTvAutoLogin;
    private TextView mTvLine;
    private TextView mTvVerifyLogin;
    private ImageView mIvAgree;
    private TextView mTvAgree;

    private boolean isChecked = false;

    private void bindView() {
        mBtLogin = findViewById(R.id.btn_login);
        mEtPhone = findViewById(R.id.et_phone);
        mIvPhoneDelete = findViewById(R.id.iv_phone_delete);
        mEtVerificationCode = findViewById(R.id.et_verification_code);
        mTvSendCode = findViewById(R.id.tv_send_code);
        mTvAutoLogin = findViewById(R.id.tv_auto_login);
        mTvLine = findViewById(R.id.tv_line);
        mTvVerifyLogin = findViewById(R.id.tv_verify_login);
        mIvAgree = findViewById(R.id.iv_agree);
        mTvAgree = findViewById(R.id.tv_agree);
        findViewById(R.id.iv_login_back).setOnClickListener(v -> {
            pop();
        });
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(mEtPhone.getText().toString().trim())){
                    mIvPhoneDelete.setVisibility(View.GONE);
                }else {
                    mIvPhoneDelete.setVisibility(View.VISIBLE);
                }
            }
        });

        mIvPhoneDelete.setOnClickListener(v -> {
            mEtPhone.setText("");
        });

        mIvAgree.setOnClickListener(view -> {
            if (!isChecked){
                isChecked = true;
                mIvAgree.setImageResource(R.mipmap.ic_login_checked);
            }else {
                isChecked = false;
                mIvAgree.setImageResource(R.mipmap.ic_login_un_check);
            }
        });
        SpannableString spannableString = new SpannableString("我已阅读并同意用户协议、隐私协议接受免除或者限制责任、诉讼管辖约定等粗体标示条款");

        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#5571FE"));
            }

            @Override
            public void onClick(@NonNull View widget) {
                //用户协议
                goUserAgreement();
            }
        }, 7, 11, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#5571FE"));
            }

            @Override
            public void onClick(@NonNull View widget) {
                //隐私协议
                goPrivacyAgreement();
            }
        }, 12, 16, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvAgree.setText(spannableString);
        mTvAgree.setMovementMethod(LinkMovementMethod.getInstance());
        mTvAgree.setOnClickListener(view -> {
            if (!isChecked){
                isChecked = true;
                mIvAgree.setImageResource(R.mipmap.ic_login_checked);
            }else {
                isChecked = false;
                mIvAgree.setImageResource(R.mipmap.ic_login_un_check);
            }
        });
        mBtLogin.setOnClickListener(view -> {

            String strAccount = mEtPhone.getText().toString().trim();
            if (TextUtils.isEmpty(strAccount)) {
                Toaster.show( R.string.string_phone_number_tips);
                return;
            }
            String strVerificationCode = mEtVerificationCode.getText().toString().trim();
            if (TextUtils.isEmpty(strVerificationCode)) {
                Toaster.show( R.string.string_verification_code_tips);
                return;
            }
            if (!isChecked){
                showVipTipsDialog(strAccount, strVerificationCode);
            }else {
                mobileAutoLogin(strAccount, strVerificationCode);
            }
        });
        if (AuthLoginEvent.instance().isSupport){//支持终端认证
            mTvAutoLogin.setVisibility(View.VISIBLE);
            mTvLine.setVisibility(View.VISIBLE);
        }else {
            mTvAutoLogin.setVisibility(View.GONE);
            mTvLine.setVisibility(View.GONE);
        }
        mTvAutoLogin.setOnClickListener(view -> {
            AuthLoginEvent.instance().oneKMeyLogin(_mActivity, new AuthLoginEvent.OneKeyLogin(){

                @Override
                public void onSuccess(TokenRet dataBean) {
                    Logs.d("OneKeyLogin", dataBean.toString());
                    //showCommentTipsDialog();
                    oneKeyLogin(dataBean.getToken());
                }

                @Override
                public void onError(String error) {
                    if (error.equals(ResultCode.MSG_ERROR_USER_SWITCH)){
                    }else if (error.equals(ResultCode.MSG_ERROR_USER_CANCEL)){
                        Toaster.show(error);
                    }else {
                        Toaster.show(error);
                    }
                }
            });
        });

        mTvVerifyLogin.setOnClickListener(view -> {
            startFragment(new LoginFragment());
        });
        mTvSendCode.setOnClickListener(v -> {
            sendCode();
        });
    }

    public void sendCode() {
        String strAccount = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(strAccount)) {
            Toaster.show( R.string.string_phone_number_tips);
            return;
        }
        getVerificationCode(strAccount);
    }

    private int recLen = 60;
    Handler  handler  = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen--;
            if (recLen < 0) {
                recLen = 60;
                mTvSendCode.setText("获取验证码");
                mTvSendCode.setTextColor(Color.parseColor("#5571FE"));
                mTvSendCode.setClickable(true);
                handler.removeCallbacks(this);
                return;
            }
            mTvSendCode.setText(recLen + "s");
            mTvSendCode.setTextColor(Color.parseColor("#5571FE"));
            mTvSendCode.setClickable(false);
            handler.postDelayed(this, 1000);
        }
    };

    /**
     * 发送获取验证码请求
     *
     * @param mobilePhone
     */
    private void getVerificationCode(String mobilePhone) {
        if (mViewModel != null) {
            mViewModel.getCode(mobilePhone, 3, new OnBaseCallback<VerificationCodeVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                    loading("正在发送验证码");
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }


                @Override
                public void onSuccess(VerificationCodeVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            Toaster.show( _mActivity.getResources().getString(R.string.string_verification_code_sent));
                            handler.post(runnable);
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void oneKeyLogin(String one_key_token) {
        if (mViewModel != null) {
            loading("正在登录...");
            mViewModel.oneKeyLogin(one_key_token, new OnBaseCallback<UserInfoVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                    loading("正在登录...");
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onSuccess(UserInfoVo baseVo) {
                    if (baseVo != null && baseVo.getData() != null) {
                        Logs.e(baseVo.getData().toString());
                        Toaster.show("登录成功");
                        if (BuildConfig.IS_RETURN_REPORT) {//是否为回归上报注册（回归用户登录上报为注册）2023-12-07
                            if (!TextUtils.isEmpty(baseVo.getData().getAct()) && "register".equals(baseVo.getData().getAct())){
                                AllDataReportAgency.getInstance().register(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            } else if (!TextUtils.isEmpty(baseVo.getData().getAct()) && baseVo.getData().getElevate() == 1 && "login".equals(baseVo.getData().getAct())){
                                AllDataReportAgency.getInstance().register(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            } else {
                                AllDataReportAgency.getInstance().login(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            }
                        }else {
                            if (!TextUtils.isEmpty(baseVo.getData().getAct()) && "register".equals(baseVo.getData().getAct())){
                                AllDataReportAgency.getInstance().register(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            } else {
                                AllDataReportAgency.getInstance().login(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            }
                        }
                        if (baseVo.getData().isCan_bind_password()){
                            showCommentTipsDialog();
                        }else{
                            _mActivity.finish();
                        }
                    } else {
                        Toaster.show( baseVo.getMsg());
                    }
                }
            });
        }
    }

    private void mobileAutoLogin(String mobile, String code) {
        if (mViewModel != null) {
            loading("正在登录...");
            mViewModel.mobileAutoLogin(mobile, code, new OnBaseCallback<UserInfoVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                    loading("正在登录...");
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onSuccess(UserInfoVo baseVo) {
                    if (baseVo != null && baseVo.getData() != null && !TextUtils.isEmpty(baseVo.getData().getToken())) {
                        Logs.e(baseVo.getData().toString());
                        Toaster.show("登录成功");
                        if (BuildConfig.IS_RETURN_REPORT) {//是否为回归上报注册（回归用户登录上报为注册）2023-12-07
                            if (!TextUtils.isEmpty(baseVo.getData().getAct()) && "register".equals(baseVo.getData().getAct())){
                                AllDataReportAgency.getInstance().register(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            } else if (!TextUtils.isEmpty(baseVo.getData().getAct()) && baseVo.getData().getElevate() == 1 && "login".equals(baseVo.getData().getAct())){
                                AllDataReportAgency.getInstance().register(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            } else {
                                AllDataReportAgency.getInstance().login(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            }
                        }else {
                            if (!TextUtils.isEmpty(baseVo.getData().getAct()) && "register".equals(baseVo.getData().getAct())){
                                AllDataReportAgency.getInstance().register(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            } else {
                                AllDataReportAgency.getInstance().login(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            }
                        }
                        if (baseVo.getData().isCan_bind_password()){
                            showCommentTipsDialog();
                        }else{
                            _mActivity.finish();
                        }
                    } else {
                        Toaster.show( baseVo.getMsg());
                    }
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                }
            });
        }
    }

    private void bindPassword(String password) {
        if (mViewModel != null) {
            mViewModel.bindPassword(password, new OnBaseCallback<UserInfoVo>() {
                @Override
                public void onSuccess(UserInfoVo baseVo) {
                    if (baseVo != null && baseVo.getData() != null && !TextUtils.isEmpty(baseVo.getData().getToken())) {
                        if (setPwdDialog != null && setPwdDialog.isShowing()){
                            setPwdDialog.dismiss();
                        }
                        EventBus.getDefault().post(new EventCenter(EventConfig.ACTION_LOGIN_EVENT_CODE));
                        _mActivity.finish();
                    } else {
                        Toaster.show( baseVo.getMsg());
                    }
                }
            });
        }
    }

    private CustomDialog setPwdDialog;
    public void showCommentTipsDialog() {
        if (setPwdDialog == null) {
            setPwdDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_login_set_pwd, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        }
        setPwdDialog.setCancelable(false);

        EditText mEtPassword1 = setPwdDialog.findViewById(R.id.et_password);
        EditText mEtRePassword = setPwdDialog.findViewById(R.id.et_repassword);
        setPwdDialog.findViewById(R.id.tv_cancel).setOnClickListener(v -> {
            if (setPwdDialog != null && setPwdDialog.isShowing()){
                setPwdDialog.dismiss();
            }
            _mActivity.finish();
        });
        setPwdDialog.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            String pwd = mEtPassword1.getText().toString().trim();
            String repwd = mEtRePassword.getText().toString().trim();
            if (TextUtils.isEmpty(pwd)) {
                Toaster.show("请输入密码");
                return;
            }
            if (TextUtils.isEmpty(repwd)) {
                Toaster.show("请输入密码");
                return;
            }
            if (!pwd.equals(repwd)) {
                Toaster.show("两次密码不一致");
                return;
            }
            bindPassword(pwd);
        });

        setPwdDialog.show();
    }

    private void showVipTipsDialog(String strAccount, String strVerificationCode){
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_agreement_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        SpannableString spannableString = new SpannableString("进入下一步前，请先阅读并同意" + getString(R.string.app_name) + "的《服务条款》、《隐私政策》");
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#5571FE"));
            }

            @Override
            public void onClick(@NonNull View widget) {
                //用户协议
                goUserAgreement();
            }
        }, 15 + getString(R.string.app_name).length(), 21 + getString(R.string.app_name).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#5571FE"));
            }

            @Override
            public void onClick(@NonNull View widget) {
                //隐私协议
                goPrivacyAgreement();
            }
        }, 22 + getString(R.string.app_name).length(), 28 + getString(R.string.app_name).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ((TextView)tipsDialog.findViewById(R.id.tv_content)).setText(spannableString);
        ((TextView)tipsDialog.findViewById(R.id.tv_content)).setMovementMethod(LinkMovementMethod.getInstance());
        tipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            if (!isChecked){
                mIvAgree.performClick();
            }
            mobileAutoLogin(strAccount, strVerificationCode);
        });
        tipsDialog.show();
    }
}
