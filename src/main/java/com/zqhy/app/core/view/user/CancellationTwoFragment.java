package com.zqhy.app.core.view.user;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.user.CancellationVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.data.model.user.VerificationCodeVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.vm.user.CancellationViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class CancellationTwoFragment extends BaseFragment<CancellationViewModel> implements View.OnClickListener {

    public static CancellationTwoFragment newInstance() {
        CancellationTwoFragment fragment = new CancellationTwoFragment();
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
        return R.layout.fragment_cancellation_two;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    protected String getUmengPageName() {
        return "账号注销";
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("账号注销");
        bindView();
    }

    private EditText etCode;
    private TextView tvSendCode;
    private TextView tvNext;

    private void bindView() {
        etCode = findViewById(R.id.et_code);
        tvSendCode = findViewById(R.id.tv_send_code);
        tvNext = findViewById(R.id.tv_next);
        tvSendCode.setOnClickListener(this);
        tvNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send_code:
                getVerificationCodeByPhone();
                break;

            case R.id.tv_next:
                if (TextUtils.isEmpty(etCode.getText().toString().trim())) {
                    Toaster.show( "请输入密码");
                    return;
                }
                //checkCode(etCode.getText().toString().trim());
                startFragment(CancellationThreeFragment.newInstance(etCode.getText().toString().trim()));
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
                recLen = 60;
                handler.removeCallbacks(this);
                tvSendCode.setText("发送验证码");
                tvSendCode.setTextColor(Color.parseColor("#FFFFFF"));
                tvSendCode.setBackgroundResource(R.drawable.ts_shape_4e76ff_big_radius);
                return;
            }
            tvSendCode.setText(String.valueOf(recLen) + "s");
            tvSendCode.setBackgroundResource(R.drawable.ts_shape_d6d6d6_big_radius);
            tvSendCode.setTextColor(Color.parseColor("#4F76FF"));
            handler.postDelayed(this, 1000);
        }
    };

    /**
     * 发送验证码
     */
    private void getVerificationCodeByPhone() {
        UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
        if (mViewModel != null) {
            mViewModel.getCodeByUser(userInfo.getUid(), userInfo.getToken(), new OnBaseCallback<VerificationCodeVo>() {
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
                        } else {
                            Toaster.show( baseVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    /**
     * 验证验证码
     */
    private void checkCode(String code) {
        UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
        if (mViewModel != null) {
            mViewModel.checkCode(userInfo.getUid(), userInfo.getToken(), code, new OnBaseCallback<CancellationVo>() {
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
                public void onSuccess(CancellationVo baseVo) {
                    if (baseVo != null) {
                        if (baseVo.isStateOK()) {
                            startFragment(CancellationThreeFragment.newInstance(code));
                        } else {
                            Toaster.show( baseVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }
}
