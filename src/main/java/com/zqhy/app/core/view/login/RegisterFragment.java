package com.zqhy.app.core.view.login;

import static com.box.mod.game.ModManager.LOGIN_OK;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.utilcode.RegularUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.vm.login.LoginViewModel;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.report.AllDataReportAgency;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.utils.PermissionHelper;
import com.zqhy.app.utils.sp.SPUtils;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/19
 */

public class RegisterFragment extends BaseFragment<LoginViewModel> {

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_REGISTER_STATE;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_register;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("账号注册");
        setTitleBottomLine(View.GONE);
        bindViews();
    }

    private EditText     mEtRegisterAccount;
    private EditText     mEtRegisterAccountPassword;
    private CheckBox     mCbPasswordVisible;
    private EditText     mEtRegisterAccountPasswordConfirm;
    private CheckBox     mCbPasswordVisibleConfirm;
    private Button    mBtnRegister;
    private ImageView mIvAgree;
    private TextView  mTvAgree;

    private void bindViews() {
        mEtRegisterAccount = findViewById(R.id.et_register_account);
        mEtRegisterAccountPassword = findViewById(R.id.et_register_account_password);
        mCbPasswordVisible = findViewById(R.id.cb_password_visible);
        mEtRegisterAccountPasswordConfirm = findViewById(R.id.et_register_account_password_confirm);
        mCbPasswordVisibleConfirm = findViewById(R.id.cb_password_visible_confirm);
        mBtnRegister = findViewById(R.id.btn_register);

        mIvAgree = findViewById(R.id.iv_agree);
        mTvAgree = findViewById(R.id.tv_agree);
        mIvAgree.setClickable(false);

        mBtnRegister.setOnClickListener(view -> {
            registerByUserName();
        });

        mEtRegisterAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(mEtRegisterAccount.getText().toString().trim())){
                    findViewById(R.id.iv_account_delete).setVisibility(View.GONE);
                }else {
                    findViewById(R.id.iv_account_delete).setVisibility(View.VISIBLE);
                }
            }
        });

        mEtRegisterAccountPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(mEtRegisterAccountPassword.getText().toString().trim())){
                    findViewById(R.id.iv_password_delete).setVisibility(View.GONE);
                }else {
                    findViewById(R.id.iv_password_delete).setVisibility(View.VISIBLE);
                }
            }
        });

        mEtRegisterAccountPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(mEtRegisterAccountPasswordConfirm.getText().toString().trim())){
                    findViewById(R.id.iv_repassword_delete).setVisibility(View.GONE);
                }else {
                    findViewById(R.id.iv_repassword_delete).setVisibility(View.VISIBLE);
                }
            }
        });

        findViewById(R.id.iv_account_delete).setOnClickListener(v -> {
            mEtRegisterAccount.setText("");
        });

        findViewById(R.id.iv_password_delete).setOnClickListener(v -> {
            mEtRegisterAccountPassword.setText("");
        });

        findViewById(R.id.iv_repassword_delete).setOnClickListener(v -> {
            mEtRegisterAccountPasswordConfirm.setText("");
        });

        mCbPasswordVisible.setOnCheckedChangeListener(onCheckedChangeListener);
        mCbPasswordVisibleConfirm.setOnCheckedChangeListener(onCheckedChangeListener);

        mCbPasswordVisibleConfirm.setChecked(true);

        findViewById(R.id.ll_agree).setOnClickListener(view -> {
            if (!isChecked){
                isChecked = true;
                mIvAgree.setImageResource(R.mipmap.ic_login_checked);
            }else {
                isChecked = false;
                mIvAgree.setImageResource(R.mipmap.ic_login_un_check);
            }
        });
        findViewById(R.id.iv_agree).setOnClickListener(view -> {
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
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = (compoundButton, b) -> {
        switch (compoundButton.getId()) {
            case R.id.cb_password_visible:
                CBCheckChange(mEtRegisterAccountPassword, b);
                break;
            case R.id.cb_password_visible_confirm:
                CBCheckChange(mEtRegisterAccountPasswordConfirm, b);
                break;
            default:
                break;

        }
    };

    private boolean isChecked = false;
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


    /**
     * 用户名注册
     */
    private void registerByUserName() {
        String account = mEtRegisterAccount.getText().toString().trim();
        if (!RegularUtils.isUserName(account)) {
            Toaster.show( mEtRegisterAccount.getHint());
            return;
        }

        String password = mEtRegisterAccountPassword.getText().toString().trim();

        if (CommonUtils.isChinese(password)) {
            Toaster.show( "密码不支持中文");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toaster.show( mEtRegisterAccountPassword.getHint());
            return;
        }
        if (password.length() < 6 || password.length() > 18) {
            Toaster.show( mEtRegisterAccountPassword.getHint());
            return;
        }

        String rePassword = mEtRegisterAccountPasswordConfirm.getText().toString().trim();

        if (TextUtils.isEmpty(rePassword)) {
            Toaster.show( mEtRegisterAccountPasswordConfirm.getHint());
            return;
        }
        if (!TextUtils.equals(password, rePassword)) {
            Toaster.show( "两次密码不一致");
            return;
        }
        if (!isChecked){
            //Toaster.show( "请先阅读并同意协议信息");
            showVipTipsDialog(account, password);
            return;
        }
        userNameRegister(account, password);
    }

    /**
     * 发送用户名注册请求
     *
     * @param strUsername
     * @param strPassword
     */
    private void userNameRegister(String strUsername, String strPassword) {
        if (mViewModel != null) {
            mViewModel.userNameRegister(strUsername, strPassword, new OnBaseCallback<UserInfoVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                    loading("正在注册...");
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }


                @Override
                public void onSuccess(UserInfoVo data) {
                    registerResponse(data, strPassword);
                }
            });
        }
    }

    private void registerResponse(UserInfoVo userInfoVo, String password) {
        loadingComplete();
        if (userInfoVo != null) {
            if (userInfoVo.isStateOK() && userInfoVo.getData() != null && !TextUtils.isEmpty(userInfoVo.getData().getToken())) {
                Logs.e(userInfoVo.getData().toString());
                Toaster.show("注册成功");
                AllDataReportAgency.getInstance().register(String.valueOf(userInfoVo.getData().getUid()), userInfoVo.getData().getUsername(), userInfoVo.getData().getTgid());
                saveUserInfo(userInfoVo.getLoginAccount(), password, userInfoVo.getData());
                //_mActivity.finish();
            } else {
                Toaster.show( userInfoVo.getMsg());
            }
        }
    }

    private void saveUserInfo(String loginAccount, String password, UserInfoVo.DataBean dataBean) {
        if (mViewModel != null) {
            mViewModel.saveUserInfo(loginAccount, password, dataBean);
        }
        if (!BuildConfig.GET_PERMISSIONS_FIRSR){
            SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
            boolean hasShow = spUtils.getBoolean("IS_PERMISSIONS_DIALOG", false);
            if (!hasShow){
                spUtils.putBoolean("IS_PERMISSIONS_DIALOG", true);
                showPermissionsTipDialog(loginAccount, dataBean);
            }else{
                Intent intent = new Intent();
                _mActivity.setResult(LOGIN_OK, intent);
                _mActivity.finish();
            }
        }else {
            Intent intent = new Intent();
            _mActivity.setResult(LOGIN_OK, intent);
            _mActivity.finish();
        }
    }

    private String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public void showPermissionsTipDialog(String loginAccountm, UserInfoVo.DataBean dataBean){
        if (_mActivity != null) {
            CustomDialog authorityDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.dialog_login_authority_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            authorityDialog.setCancelable(false);
            authorityDialog.setCanceledOnTouchOutside(false);

            TextView mTvContent = authorityDialog.findViewById(R.id.tv_content);
            TextView mTvCancel = authorityDialog.findViewById(R.id.tv_cancel);
            TextView mTvConfirm = authorityDialog.findViewById(R.id.tv_confirm);

            mTvContent.setText("\"" + _mActivity.getResources().getString(R.string.app_name) + "\"" + "正在向您获取“存储”权限，同意后，将用于本APP账号缓存，以后登录均可快速选择缓存的账号直接登录。");
            mTvCancel.setOnClickListener(v -> {
                if (authorityDialog != null && authorityDialog.isShowing()){
                    authorityDialog.dismiss();
                }
                Intent intent = new Intent();
                _mActivity.setResult(LOGIN_OK, intent);
                _mActivity.finish();
            });
            mTvConfirm.setOnClickListener(v -> {
                if (authorityDialog != null && authorityDialog.isShowing()){
                    authorityDialog.dismiss();
                }
                PermissionHelper.checkPermissions(new PermissionHelper.OnPermissionListener() {
                    @Override
                    public void onGranted() {
                        mViewModel.saveAccountToSDK(loginAccountm, dataBean.getPassword());
                        Intent intent = new Intent();
                        _mActivity.setResult(LOGIN_OK, intent);
                        _mActivity.finish();
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        Intent intent = new Intent();
                        _mActivity.setResult(LOGIN_OK, intent);
                        _mActivity.finish();
                    }
                }, permissions);
            });
            authorityDialog.show();
        }
    }

    private void showVipTipsDialog(String account, String password){
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
            userNameRegister(account, password);
        });
        tipsDialog.show();
    }
}
