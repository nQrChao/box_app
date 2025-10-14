package com.zqhy.app.core.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.data.model.user.VerificationCodeVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.login.LoginActivity;
import com.zqhy.app.core.vm.user.ModifyPasswordViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;


/**
 * @author Administrator
 * @date 2018/11/12
 */

public class ModifyPasswordFragment extends BaseFragment<ModifyPasswordViewModel> {

    public static ModifyPasswordFragment newInstance() {
        ModifyPasswordFragment fragment = new ModifyPasswordFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_modify_password;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        bindView();
    }

    private int recLen = 60;

    Handler  handler  = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen--;
            if (recLen < 0) {
                mTvSendCode.setVisibility(View.VISIBLE);
                mLlReSend.setVisibility(View.GONE);
                recLen = 60;
                setCodeViewBg(false);
                handler.removeCallbacks(this);
                return;
            }
            setCodeViewBg(true);
            mTvSendCode.setVisibility(View.GONE);
            mLlReSend.setVisibility(View.VISIBLE);
            mTvSecond.setText(String.valueOf(recLen) + "s");
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }


    private TextView     mTvOldBindPhone;
    private EditText     mEtVerificationCode;
    private TextView     mTvSendCode;
    private LinearLayout mLlReSend;
    private TextView     mTvSecond;
    private EditText     mEtNewPayPassword;
    private Button       mBtnConfirm;
    private FrameLayout  mFlCode;

    private void bindView() {
        mTvOldBindPhone = findViewById(R.id.tv_old_bind_phone);
        mEtVerificationCode = findViewById(R.id.et_verification_code);
        mTvSendCode = findViewById(R.id.tv_send_code);
        mLlReSend = findViewById(R.id.ll_re_send);
        mTvSecond = findViewById(R.id.tv_second);
        mEtNewPayPassword = findViewById(R.id.et_new_pay_password);
        mBtnConfirm = findViewById(R.id.btn_confirm);
        mFlCode = findViewById(R.id.fl_code);

        setViewValue();

        setCodeViewBg(false);

        mBtnConfirm.setOnClickListener(view -> {
            String newPwd = mEtNewPayPassword.getText().toString().trim();

            if (CommonUtils.isChinese(newPwd)) {
                Toaster.show( "密码不支持中文");
                return;
            }

            if (TextUtils.isEmpty(newPwd)) {
                Toaster.show( mEtNewPayPassword.getHint());
                return;
            }

            String verificationCode = mEtVerificationCode.getText().toString().trim();
            if (TextUtils.isEmpty(verificationCode)) {
                Toaster.show( mEtVerificationCode.getHint());
                return;
            }
            String mob = "";
            UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
            if (userInfoBean != null) {
                mob = userInfoBean.getMobile();
            }
            modifyLoginPassword(mob, verificationCode, newPwd);
        });

        mTvSendCode.setOnClickListener(view -> {
            UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
            if (userInfo != null) {
                getVerificationCodeByName(userInfo.getMobile());
            }
        });
    }

    public void setViewValue() {
        initActionBackBarAndTitle("修改登录密码");

        InputFilter[] filters = {new InputFilter.LengthFilter(20)};
        mEtNewPayPassword.setFilters(filters);
        mEtNewPayPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
        if (userInfoBean != null) {
            mTvOldBindPhone.setText("当前绑定：" + CommonUtils.hideTelNum(userInfoBean.getMobile()));
        }
    }

    private void setCodeViewBg(boolean isSendCode) {
        if (isSendCode){
            mFlCode.setBackgroundResource(R.drawable.ts_shape_d6d6d6_big_radius);
        }else{
            mFlCode.setBackgroundResource(R.drawable.ts_shape_4e77fe_big_radius);
        }
    }

    private String targetMobile;

    /**
     * 获取验证码
     *
     * @param mobile
     */
    private void getVerificationCodeByName(String mobile) {
        if (mViewModel != null) {
            mViewModel.getVerificationCode(mobile, 2, new OnBaseCallback<VerificationCodeVo>() {
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
                public void onSuccess(VerificationCodeVo baseVo) {
                    loadingComplete();
                    if (baseVo != null) {
                        if (baseVo.isStateOK()) {
                            Toaster.show( _mActivity.getResources().getString(R.string.string_verification_code_sent));
                            handler.post(runnable);
                            targetMobile = baseVo.getData();
                        } else {
                            Toaster.show( baseVo.getMsg());
                        }
                    }
                }
            });
        }
    }


    /**
     * 修改密码
     *
     * @param code
     * @param newpwd
     */
    private void modifyLoginPassword(String mobile, String code, String newpwd) {
        if (mViewModel != null) {
            mViewModel.modifyLoginPassword(mobile, code, newpwd, new OnBaseCallback() {
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
                public void onSuccess(BaseVo baseVo) {
                    if (baseVo != null) {
                        if (baseVo.isStateOK()) {
                            Toaster.show( "修改密码成功，请重新登录");

                            //修改密码成功之后 登出 并且跳转登录
                            UserInfoModel.getInstance().logout();
                            startActivity(new Intent(_mActivity, LoginActivity.class));
                            _mActivity.finish();
                        } else {
                            Toaster.show( baseVo.getMsg());
                        }
                    }
                }
            });
        }
    }
}
