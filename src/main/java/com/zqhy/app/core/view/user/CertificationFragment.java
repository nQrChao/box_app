package com.zqhy.app.core.view.user;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.user.VerificationCodeVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.user.welfare.MyCouponsListFragment;
import com.zqhy.app.core.vm.user.CertificationViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class CertificationFragment extends BaseFragment<CertificationViewModel> implements View.OnClickListener {

    public static CertificationFragment newInstance(String tips) {
        CertificationFragment fragment = new CertificationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tips", tips);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static CertificationFragment newInstance() {
        return newInstance(null, null);
    }

    public static CertificationFragment newInstance(String name, String id_card_number) {
        CertificationFragment fragment = new CertificationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("id_card_number", id_card_number);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "实名认证页";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_certification;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("实名认证");
        showSuccess();
        bindView();
    }

    private TextView     mTvTips;
    private EditText     mEditRealName;
    private EditText     mEditIdCardNumber;
    private EditText     mEditPhoneNumber;
    private FrameLayout  mFlCode;
    private TextView     mTvSendCode;
    private LinearLayout mLlReSend;
    private TextView     mTvSecond;
    private TextView     mTvBindPhoneTips;
    private EditText     mEditCode;
    private Button       mBtnCommit;
    private TextView     mTvServer;

    private LinearLayout mLlUnCertification;
    private LinearLayout mLlCertificationSuccessful;
    private TextView     mTvRealName;
    private TextView     mTvIdCardNumber;
    private TextView     mTvPhoneNumber;
    private LinearLayout mLlCertificationMobile;

    private void bindView() {
        mTvTips = findViewById(R.id.tv_tips);
        mEditRealName = findViewById(R.id.edit_real_name);
        mEditIdCardNumber = findViewById(R.id.edit_id_card_number);
        mEditPhoneNumber = findViewById(R.id.edit_phone_number);
        mFlCode = findViewById(R.id.fl_code);
        mTvSendCode = findViewById(R.id.tv_send_code);
        mLlReSend = findViewById(R.id.ll_re_send);
        mTvSecond = findViewById(R.id.tv_second);
        mTvBindPhoneTips = findViewById(R.id.tv_bind_phone_tips);
        mEditCode = findViewById(R.id.edit_code);
        mBtnCommit = findViewById(R.id.btn_commit);
        mTvServer = findViewById(R.id.tv_server);

        mLlUnCertification = findViewById(R.id.ll_un_certification);
        mLlCertificationSuccessful = findViewById(R.id.ll_certification_successful);
        mTvRealName = findViewById(R.id.tv_real_name);
        mTvIdCardNumber = findViewById(R.id.tv_id_card_number);
        mTvPhoneNumber = findViewById(R.id.tv_phone_number);
        mLlCertificationMobile = findViewById(R.id.ll_certification_mobile);


        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(48 * density);
        gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        gd.setColors(new int[]{Color.parseColor("#139EF8"), Color.parseColor("#0572E6")});
        mBtnCommit.setBackground(gd);
        mBtnCommit.setOnClickListener(this);
        mTvSendCode.setOnClickListener(this);
        mTvServer.setOnClickListener(this);

        setCodeViewBg(false);
        setCurrentLayout();
    }


    private void setCurrentLayout() {
        if (UserInfoModel.getInstance().isSetCertification()) {
            //已实名
            mLlCertificationSuccessful.setVisibility(View.VISIBLE);
            mLlUnCertification.setVisibility(View.GONE);

            String real_name = UserInfoModel.getInstance().getUserInfo().getReal_name();
            mTvRealName.setText(real_name);
            String id_card = UserInfoModel.getInstance().getUserInfo().getIdcard();
            if (id_card.length() > 2) {
                for (int index = 1; index < id_card.length() - 2; index++) {
                    id_card.replace(id_card.charAt(index), '*');
                }
            }
            mTvIdCardNumber.setText(id_card);
            String mobile = UserInfoModel.getInstance().getUserInfo().getMobile();
            if (TextUtils.isEmpty(mobile)) {
                mLlCertificationMobile.setVisibility(View.GONE);
            } else {
                mLlCertificationMobile.setVisibility(View.VISIBLE);
                mTvPhoneNumber.setText(CommonUtils.hideTelNum(mobile));
            }
        } else {
            //未实名
            mLlUnCertification.setVisibility(View.VISIBLE);
            mLlCertificationSuccessful.setVisibility(View.GONE);
            if (UserInfoModel.getInstance().isBindMobile()) {
                String mobile = UserInfoModel.getInstance().getUserInfo().getMobile();
                mEditPhoneNumber.setText(CommonUtils.hideTelNum(mobile));
                mTvBindPhoneTips.setVisibility(View.GONE);
            } else {
                mTvBindPhoneTips.setVisibility(View.GONE);
            }
        }
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
                setCodeViewBg(false);
                recLen = 60;
                handler.removeCallbacks(this);
                return;
            }
            mTvSendCode.setVisibility(View.GONE);
            mLlReSend.setVisibility(View.VISIBLE);
            setCodeViewBg(true);
            mTvSecond.setText(String.valueOf(recLen) + "s");
            handler.postDelayed(this, 1000);
        }
    };

    private void setCodeViewBg(boolean isSendCode) {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(12 * density);
        if (isSendCode) {
            gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_eaeaea));
        } else {
            gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_0052ef));
        }
        mFlCode.setBackground(gd);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                String var1 = mEditRealName.getText().toString().trim();
                if (TextUtils.isEmpty(var1)) {
                    Toaster.show( mEditRealName.getHint());
                    return;
                }
                String var2 = mEditIdCardNumber.getText().toString().trim();
                if (TextUtils.isEmpty(var2)) {
                    Toaster.show( mEditIdCardNumber.getHint());
                    return;
                }

                int var5 = 1;
                if (UserInfoModel.getInstance().isBindMobile()) {
                    var5 = 2;
                }

                /*String var3 = mEditPhoneNumber.getText().toString().trim();
                if (TextUtils.isEmpty(var3)) {
                    Toaster.show( mEditPhoneNumber.getHint());
                    return;
                }


                String var4 = mEditCode.getText().toString().trim();
                if (TextUtils.isEmpty(var4)) {
                    Toaster.show( mEditCode.getHint());
                    return;
                }*/

                userCertification(var1, var2, null, null, var5);
                break;
            case R.id.tv_send_code:
                sendCode();
                break;
            case R.id.tv_server:
                //客服在线
                goKefuCenter();
                break;
            default:
                break;
        }
    }

    private void userCertification(String sfzname, String sfzid, String phoneNumber, String code, int is_check) {
        if (mViewModel != null) {
            mViewModel.userCertificationV2(sfzname, sfzid, code, phoneNumber, is_check, new OnBaseCallback() {
                @Override
                public void onBefore() {
                    super.onBefore();
                    loading("正在实名认证...");
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
                            mViewModel.refreshUserData();
                            //                            showCertificationSuccessful();
                            setCurrentLayout();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }


    private void showCertificationSuccessful() {
        CustomDialog dialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_ts_private_success, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        TextView mTvIntegralBalance = dialog.findViewById(R.id.tv_integral_balance);
        Button mBtnConfirm = dialog.findViewById(R.id.btn_confirm);
        TextView mTvApp = dialog.findViewById(R.id.tv_app);

        //        mTvApp.setText(getAppNameByXML(R.string.string_dyx_integral));
        mTvApp.setText("代金券是平台内的一种优惠券，可用于平台内的游戏消费时直接抵扣订单金额。");

        String txt = "恭喜获得10元代金券一张";
        SpannableString ss = new SpannableString(txt);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_ff0000)), 4, 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(new AbsoluteSizeSpan((int) (19 * density)), 9, txt.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvIntegralBalance.setText(ss);

        mBtnConfirm.setOnClickListener(v -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

            setFragmentResult(RESULT_OK, null);
            //            startWithPop(new CommunityIntegralMallFragment());
            //跳转我的礼券
            //startWithPop(GameWelfareFragment.newInstance(2));
            startFragment(new MyCouponsListFragment());
        });
        dialog.show();
    }

    /**
     * 获取验证码
     */
    private void sendCode() {
        String mobilePhone = mEditPhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(mobilePhone)) {
            Toaster.show( _mActivity.getResources().getString(R.string.string_phone_number_tips));
            return;
        }
        int is_check = 1;
        if (UserInfoModel.getInstance().isBindMobile()) {
            mobilePhone = UserInfoModel.getInstance().getUserInfo().getMobile();
            is_check = 2;
        }
        getVerificationCode(mobilePhone, is_check);
    }

    /**
     * 发送获取验证码请求
     *
     * @param mobilePhone
     */
    private void getVerificationCode(String mobilePhone, int is_check) {
        if (mViewModel != null) {
            mViewModel.getCode(mobilePhone, is_check, new OnBaseCallback<VerificationCodeVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                    loading("获取验证码...");
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onSuccess(VerificationCodeVo verificationCodeVo) {
                    if (verificationCodeVo != null) {
                        if (verificationCodeVo.isStateOK()) {
                            Toaster.show( _mActivity.getResources().getString(R.string.string_verification_code_sent));
                            handler.post(runnable);
                        } else {
                            Toaster.show( verificationCodeVo.getMsg());
                        }
                    }
                }
            });
        }
    }
}
