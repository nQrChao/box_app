package com.zqhy.app.core.view.login;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.user.VerificationCodeVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.kefu.KefuCenterFragment;
import com.zqhy.app.core.vm.login.LoginViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/19
 */

public class ResetPasswordFragment extends BaseFragment<LoginViewModel> implements View.OnClickListener {
    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_reset_password;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        bindViews();
        initActionBackBarAndTitle("忘记密码");
        setTitleBottomLine(View.GONE);
    }

    private LinearLayout mLlContentLayout;
    private EditText     mEtAccount;
    private EditText     mEtVerificationCode;
    private TextView     mTvSendCode;
    private EditText     mEtPassword;
    private CheckBox     mCbPasswordVisible;
    private Button       mBtnComplete;
    private TextView     mTvNotAcceptCode;

    private void bindViews() {
        mLlContentLayout = findViewById(R.id.ll_content_layout);
        mEtAccount = findViewById(R.id.et_account);
        mEtVerificationCode = findViewById(R.id.et_verification_code);
        mTvSendCode = findViewById(R.id.tv_send_code);
        mEtPassword = findViewById(R.id.et_password);
        mCbPasswordVisible = findViewById(R.id.cb_password_visible);
        mBtnComplete = findViewById(R.id.btn_complete);
        mTvNotAcceptCode = findViewById(R.id.tv_not_accept_code);

        mCbPasswordVisible.setOnCheckedChangeListener(onCheckedChangeListener);
        mCbPasswordVisible.setChecked(true);

        mTvSendCode.setOnClickListener(this);
        mTvNotAcceptCode.setOnClickListener(this);
        mBtnComplete.setOnClickListener(this);

//        GradientDrawable gd = new GradientDrawable();
//        gd.setCornerRadius(48 * density);
//        gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
//        gd.setColors(new int[]{ContextCompat.getColor(_mActivity, R.color.color_139ef8), ContextCompat.getColor(_mActivity, R.color.color_0572e6)});
//        mBtnComplete.setBackground(gd);

    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = (compoundButton, b) -> {
        switch (compoundButton.getId()) {
            case R.id.cb_password_visible:
                CBCheckChange(mEtPassword, b);
                break;
            default:
                break;

        }
    };

    private void CBCheckChange(EditText et, boolean b) {
        if (b) {
            //选择状态 显示明文--设置为可见的密码
            et.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            //默认状态显示密码--设置文本 要一起写才能起作用  InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
            et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        et.setSelection(et.getText().length());
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send_code:
                sendCode();
                break;
            case R.id.tv_not_accept_code:
                notAcceptCode();
                break;
            case R.id.btn_complete:
                complete();
                break;
            default:
                break;
        }
    }

    public void sendCode() {
        String strAccount = mEtAccount.getText().toString().trim();
        if (TextUtils.isEmpty(strAccount)) {
            Toaster.show( R.string.string_phone_number_tips);
            return;
        }
        getVerificationCode(strAccount);
    }

    public void notAcceptCode() {
        start(new KefuCenterFragment());
    }

    public void complete() {
        String strAccount = mEtAccount.getText().toString().trim();
        if (TextUtils.isEmpty(strAccount)) {
            Toaster.show( R.string.string_phone_number_tips);
            return;
        }
        String strPassword = mEtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(strPassword)) {
            Toaster.show( "请输入新密码");
            return;
        }
        String strVerificationCode = mEtVerificationCode.getText().toString().trim();
        if (TextUtils.isEmpty(strVerificationCode)) {
            Toaster.show( R.string.string_verification_code_tips);
            return;
        }
        resetLoginPassword(strAccount, strVerificationCode, strPassword);
    }


    /**
     * 发送获取验证码请求
     *
     * @param mobilePhone
     */
    private void getVerificationCode(String mobilePhone) {
        if (mViewModel != null) {

            mViewModel.getCode(mobilePhone, 2, new OnBaseCallback<VerificationCodeVo>() {
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

    /**
     * 充值登录密码
     *
     * @param mobilePhone
     * @param code
     * @param newpwd
     */
    private void resetLoginPassword(String mobilePhone, String code, String newpwd) {
        if (mViewModel != null) {
            mViewModel.resetLoginPassword(mobilePhone, code, newpwd, new OnBaseCallback() {

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
                            Toaster.show( "修改成功");
                            pop();
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }
}
