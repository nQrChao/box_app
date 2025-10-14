package com.zqhy.app.core.view.user;

import static com.chaoji.mod.game.ModManager.BIND_PHONE_OK;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.user.BindPhoneTempVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.data.model.user.VerificationCodeVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.kefu.KefuCenterFragment;
import com.zqhy.app.core.vm.user.BindPhoneViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class BindPhoneFragment extends BaseFragment<BindPhoneViewModel> implements View.OnClickListener {

    public static final int RESULT_CODE_BIND_PHONE    = 1001;
    public static final int RESULT_CODE_UN_BIND_PHONE = 1002;

    public static BindPhoneFragment newInstance() {
        return newInstance(false, "");
    }

    public static BindPhoneFragment newInstance(boolean isSetBindPhone, String mob) {
        BindPhoneFragment fragment = new BindPhoneFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isSetBindPhone", isSetBindPhone);
        bundle.putString("mob", mob);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user_bind_phone;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private boolean isSetBindPhone;
    private String  mob;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            isSetBindPhone = getArguments().getBoolean("isSetBindPhone");
            mob = getArguments().getString("mob");
        }
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle(!isSetBindPhone ? "绑定手机" : "解绑手机");
        bindView();
    }

    private LinearLayout mLlContentLayout;
    private LinearLayout mFlUnbind;
    private TextView     mTvUnbindTips;
    private LinearLayout mLlBindPhone;
    private EditText     mEtBindPhoneUnbind;
    private LinearLayout mLlBindCode;
    private EditText     mEtVerificationCodeUnbind;
    private TextView     mTvSendCodeUnbind;
    private LinearLayout mLlReSendUnbind;
    private TextView     mTvSecondUnbind;
    private Button       mBtnBindPhone;
    private TextView     mTvUnablePhone;
    private FrameLayout  mFlCode;

    private void bindView() {
        mLlContentLayout = findViewById(R.id.ll_content_layout);
        mFlUnbind = findViewById(R.id.fl_unbind);
        mTvUnbindTips = findViewById(R.id.tv_unbind_tips);
        mLlBindPhone = findViewById(R.id.ll_bind_phone);
        mEtBindPhoneUnbind = findViewById(R.id.et_bind_phone_unbind);
        mLlBindCode = findViewById(R.id.ll_bind_code);
        mEtVerificationCodeUnbind = findViewById(R.id.et_verification_code_unbind);
        mTvSendCodeUnbind = findViewById(R.id.tv_send_code_unbind);
        mLlReSendUnbind = findViewById(R.id.ll_re_send_unbind);
        mTvSecondUnbind = findViewById(R.id.tv_second_unbind);
        mBtnBindPhone = findViewById(R.id.btn_bind_phone);
        mTvUnablePhone = findViewById(R.id.tv_unable_phone);
        mFlCode = findViewById(R.id.fl_code);

        setViewBackground();
        setCodeViewBg(false);
        mTvSendCodeUnbind.setOnClickListener(this);
        mBtnBindPhone.setOnClickListener(this);
        mTvUnablePhone.setOnClickListener(this);

        if (!isSetBindPhone) {
            mEtBindPhoneUnbind.setEnabled(true);
            mBtnBindPhone.setText("确认绑定");
            //mTvUnablePhone.setVisibility(View.GONE);
            mTvUnbindTips.setText("* 绑定手机，保障账号安全！且绑定后可使用手机号登录，更方便！");
        } else {
            UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
            if (userInfoBean != null) {
                mEtBindPhoneUnbind.setEnabled(false);
                mEtBindPhoneUnbind.setText(CommonUtils.getHidePhoneNumber(userInfoBean.getMobile()));
            }
            //mTvUnablePhone.setVisibility(View.VISIBLE);
            mBtnBindPhone.setText("确认解绑");
            mTvUnbindTips.setText("* 解绑须知：\n1、绑定手机3天后，可进行验证解绑；\n2、解绑后不可再使用该手机号登录，仅能使用用户名、密码登录。因此请牢记你的用户名。");
        }

    }

    private void setViewBackground() {
        /*GradientDrawable gd1 = new GradientDrawable();
        gd1.setCornerRadius(3 * density);
        gd1.setColor(ContextCompat.getColor(_mActivity, R.color.color_f5f5f5));
        gd1.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.color_e2e2e2));

        mLlBindPhone.setBackground(gd1);
        mLlBindCode.setBackground(gd1);*/


        /*GradientDrawable gd2 = new GradientDrawable();
        gd2.setCornerRadius(5 * density);
        gd2.setColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));

        mBtnBindPhone.setBackground(gd2);*/

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send_code_unbind:
                if (!isSetBindPhone) {
                    bindingSendCode();
                } else {
                    unbindSendCode();
                }
                break;
            case R.id.btn_bind_phone:
                //绑定手机/解绑手机
                if (!isSetBindPhone) {
                    doBindPhone();
                } else {
                    doUnBindPhone();
                }
                break;
            case R.id.tv_unable_phone:
                //联系客服
                start(new KefuCenterFragment());
                break;
            default:
                break;
        }
    }

    private int recLen = 60;

    Handler  handler  = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen--;
            if (recLen < 0) {
                mTvSendCodeUnbind.setVisibility(View.VISIBLE);
                mLlReSendUnbind.setVisibility(View.GONE);
                recLen = 60;
                setCodeViewBg(false);
                handler.removeCallbacks(this);
                return;
            }
            mTvSendCodeUnbind.setVisibility(View.GONE);
            mLlReSendUnbind.setVisibility(View.VISIBLE);
            mTvSecondUnbind.setText(String.valueOf(recLen) + "s");
            setCodeViewBg(true);
            handler.postDelayed(this, 1000);
        }
    };

    private void setCodeViewBg(boolean isSendCode) {
        if (isSendCode){
            mFlCode.setBackgroundResource(R.drawable.ts_shape_d6d6d6_big_radius);
        }else{
            mFlCode.setBackgroundResource(R.drawable.bg_6972ff_ec18f8_radius_20);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    private String mobile;


    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.EVENT_KEY_USER_BIND_PHONE, BindPhoneTempVo.class).observe(this, bindPhoneTempVo -> {
            loadingComplete();

        });

        registerObserver(Constants.EVENT_KEY_USER_UN_BIND_PHONE, BaseResponseVo.class).observe(this, baseVo -> {
            loadingComplete();

        });

    }

    private void bindingSendCode() {
        String strPhone = mEtBindPhoneUnbind.getText().toString().trim();
        if (TextUtils.isEmpty(strPhone)) {
            Toaster.show( _mActivity.getResources().getString(R.string.string_phone_number_tips));
            return;
        }
        getVerificationCodeByPhone(strPhone);
    }

    private void unbindSendCode() {
        UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
        if (userInfoBean == null) {
            return;
        }
        getVerificationCodeByName(userInfoBean.getMobile());
    }

    /**
     * 发送验证码1
     *
     * @param phone
     */
    private void getVerificationCodeByPhone(String phone) {
        if (mViewModel != null) {
            mViewModel.getCode(phone, 1, new OnBaseCallback<VerificationCodeVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                    loading("");
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onSuccess(VerificationCodeVo baseVo) {
                    if (baseVo != null) {
                        if (baseVo.isStateOK()) {
                            Toaster.show( _mActivity.getResources().getString(R.string.string_verification_code_sent));
                            handler.post(runnable);
                            mobile = baseVo.getData();
                        } else {
                            Toaster.show( baseVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    /**
     * 发送验证码2
     *
     * @param mob
     */
    private void getVerificationCodeByName(String mob) {
        if (mViewModel != null) {
            loading("");
            mViewModel.getCode(mob, 2, new OnBaseCallback<VerificationCodeVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                    loading("");
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onSuccess(VerificationCodeVo baseVo) {
                    if (baseVo != null) {
                        if (baseVo.isStateOK()) {
                            Toaster.show( _mActivity.getResources().getString(R.string.string_verification_code_sent));
                            handler.post(runnable);
                            mobile = baseVo.getData();
                        } else {
                            Toaster.show( baseVo.getMsg());
                        }
                    }
                }
            });
        }
    }


    private void doUnBindPhone() {
        String verificationCode = mEtVerificationCodeUnbind.getText().toString().trim();
        if (TextUtils.isEmpty(verificationCode)) {
            Toaster.show( _mActivity.getResources().getString(R.string.string_verification_code_tips));
            return;
        }
        String mob = "";
        UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
        if (userInfoBean != null) {
            mob = userInfoBean.getMobile();
        }
        unBindPhone(mob, verificationCode);
    }

    private void doBindPhone() {
        String strPhone = mEtBindPhoneUnbind.getText().toString().trim();
        if (TextUtils.isEmpty(strPhone)) {
            Toaster.show( _mActivity.getResources().getString(R.string.string_phone_number_tips));
            return;
        }
        String verificationCode = mEtVerificationCodeUnbind.getText().toString().trim();
        if (TextUtils.isEmpty(verificationCode)) {
            Toaster.show( _mActivity.getResources().getString(R.string.string_verification_code_tips));
            return;
        }
        bindPhone(strPhone, verificationCode);
    }

    /**
     * 绑定手机号
     *
     * @param mob
     * @param verificationCode
     */
    private void bindPhone(String mob, String verificationCode) {
        if (mViewModel != null) {
            mViewModel.bindPhone(mob, verificationCode, new OnBaseCallback<BindPhoneTempVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onBefore() {
                    super.onBefore();
                    loading("");
                }

                @Override
                public void onSuccess(BindPhoneTempVo bindPhoneTempVo) {
                    if (bindPhoneTempVo != null) {
                        if (bindPhoneTempVo.isStateOK()) {
                            Toaster.show( "绑定成功");
                            Intent intent = new Intent();
                            _mActivity.setResult(BIND_PHONE_OK, intent);
                            _mActivity.finish();
                            setFragmentResult(RESULT_OK, null);
                            pop();
                        } else {
                            Toaster.show( bindPhoneTempVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    /**
     * 解绑手机号
     *
     * @param verificationCode
     */
    private void unBindPhone(String mob, String verificationCode) {
        if (mViewModel != null) {
            mViewModel.unBindPhone(mob, verificationCode, new OnBaseCallback() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onBefore() {
                    super.onBefore();
                    loading("");
                }

                @Override
                public void onSuccess(BaseVo baseVo) {
                    if (baseVo != null) {
                        if (baseVo.isStateOK()) {
                            Toaster.show( "解绑成功");
                            UserInfoModel.getInstance().getUserInfo().setMobile("");
                            setFragmentResult(RESULT_OK, null);
                            pop();
                        } else {
                            Toaster.show( baseVo.getMsg());
                        }
                    }
                }
            });
        }
    }
}
