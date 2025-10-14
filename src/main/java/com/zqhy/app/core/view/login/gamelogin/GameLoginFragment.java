package com.zqhy.app.core.view.login.gamelogin;

import static com.box.mod.game.ModManager.LOGIN_CANCEL;
import static com.box.mod.game.ModManager.LOGIN_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.box.common.data.model.AppUserInfo;
import com.box.common.sdk.ImSDK;
import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.hjq.toast.Toaster;
import com.kunminx.architecture.ui.callback.UnPeekLiveData;
import com.mobile.auth.gatewayauth.model.TokenRet;
import com.zqhy.app.App;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.InviteConfig;
import com.zqhy.app.config.WxControlConfig;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.utils.AppUtils;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.report.AllDataReportAgency;
import com.zqhy.app.utils.PermissionHelper;
import com.zqhy.app.utils.sp.SPUtils;

import java.util.List;


public class GameLoginFragment extends BaseFragment<GameLoginViewModel>{

    public static GameLoginFragment newInstance() {
        GameLoginFragment fragment = new GameLoginFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_game_login;
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
        //setActionBackBar(R.mipmap.ic_actionbar_back_white);
        setTitleBottomLine(View.GONE);
        bindView();
    }
    private boolean isChecked = false;

    @SuppressLint("WrongViewCast")
    private void bindView() {
        if (GameAuthLoginEvent.instance().isSupport) {//支持终端认证
            GameAuthLoginEvent.instance().oneKMeyLogin(_mActivity, new GameAuthLoginEvent.OneKeyLogin() {
                @Override
                public void onSuccess(TokenRet dataBean) {
                    Logs.d("OneKeyLogin", dataBean.toString());
                    oneKeyLogin(dataBean.getToken());
                }

                @Override
                public void onError(String error) {
                    Toaster.show(error);
                    Logs.e("oneKeyLogin","手机登录失败");
                    Intent intent = new Intent();
                    _mActivity.setResult(Activity.RESULT_CANCELED, intent);
                    _mActivity.finish();
                }
            });
        } else {
            Toaster.show("手机登录失败");
            Logs.e("oneKeyLogin","手机登录失败");
            Intent intent = new Intent();
            _mActivity.setResult(LOGIN_CANCEL, intent);
            _mActivity.finish();
        }
    }


    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_LOGIN_STATE;
    }

    /*@Override
    public void start(ISupportFragment toFragment) {
        if (toFragment instanceof SupportFragment) {
            FragmentHolderActivity.startFragmentInActivity(_mActivity, (SupportFragment) toFragment);
        } else {
            super.start(toFragment);
        }
    }*/

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
                            if (!TextUtils.isEmpty(baseVo.getData().getAct()) && "register".equals(baseVo.getData().getAct())) {
                                AllDataReportAgency.getInstance().register(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            } else if (!TextUtils.isEmpty(baseVo.getData().getAct()) && baseVo.getData().getElevate() == 1 && "login".equals(baseVo.getData().getAct())) {
                                AllDataReportAgency.getInstance().register(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            } else {
                                AllDataReportAgency.getInstance().login(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            }
                        } else {
                            if (!TextUtils.isEmpty(baseVo.getData().getAct()) && "register".equals(baseVo.getData().getAct())) {
                                AllDataReportAgency.getInstance().register(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            } else {
                                AllDataReportAgency.getInstance().login(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getTgid());
                            }
                        }

                        UnPeekLiveData<AppUserInfo> appUserInfo = new UnPeekLiveData<>();
                        AppUserInfo US = new AppUserInfo(String.valueOf(baseVo.getData().getUid()), baseVo.getData().getUsername(), baseVo.getData().getToken(), baseVo.getData().getAuth(), baseVo.getData().getMobile());
                        appUserInfo.setValue(US);
                        ImSDK.appViewModelInstance.setUserInfo(appUserInfo);

                        UserInfoModel.getInstance().login(baseVo.getData());

                        SPUtils spUtils = new SPUtils(App.instance(), UserInfoModel.SP_USER_INFO_MODEL);
                        if (baseVo.getData() != null) {
                            Logs.e("KEY_USER_INFO_MODEL_LAST_LOGIN_AUTH:",baseVo.getData().getAuth());
                            spUtils.putString(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_USERNAME, baseVo.getData().getUsername());
                            spUtils.putString(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_AUTH, baseVo.getData().getAuth());
                            spUtils.putInt(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_UID, baseVo.getData().getUid());
                            spUtils.putString(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_TOKEN, baseVo.getData().getToken());
                            InviteConfig.invite_type = baseVo.getData().getInvite_type();
                            //添加测试账号
                            if ("jiuyao001".equals(baseVo.getData().getUsername()) || "testwang".equals(baseVo.getData().getUsername())) {
                                WxControlConfig.wx_control = 1;
                            }
                        } else {
                            spUtils.remove(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_USERNAME);
                            spUtils.remove(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_AUTH);
                            spUtils.remove(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_UID);
                            spUtils.remove(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_TOKEN);
                            InviteConfig.invite_type = 0;
                        }
                        AppUtils.setTgid(baseVo.getData() == null ? AppUtils.getChannelFromApk() : baseVo.getData().getTgid());

                        if (baseVo.getData().isCan_bind_password()) {
                            showCommentTipsDialog();
                        } else {
                            Logs.e("oneKeyLogin","oneKeyLogin_onSuccess");
                            Intent intent = new Intent();
                            _mActivity.setResult(LOGIN_OK, intent);
                            _mActivity.finish();
                        }
                    } else {
                        Toaster.show( baseVo.getMsg());
                        Logs.e("oneKeyLogin","oneKeyLogin_Error");
                        Intent intent = new Intent();
                        _mActivity.setResult(LOGIN_CANCEL, intent);
                        _mActivity.finish();
                    }
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
                        if (setPwdDialog != null && setPwdDialog.isShowing()) {
                            setPwdDialog.dismiss();
                        }
                        Logs.e("oneKeyLogin","bindPassword_onSuccess");
                        Intent intent = new Intent();
                        _mActivity.setResult(LOGIN_OK, intent);
                        _mActivity.finish();
                    } else {
                        Toaster.show( baseVo.getMsg());
                        Logs.e("oneKeyLogin","bindPassword_Error");
                        Intent intent = new Intent();
                        _mActivity.setResult(LOGIN_CANCEL, intent);
                        _mActivity.finish();
                    }
                }
            });
        }
    }

    private void saveUserInfo(String loginAccount, String password, UserInfoVo.DataBean dataBean) {
        if (mViewModel != null) {
            mViewModel.saveUserInfo(loginAccount, password, dataBean);
        }
        if (!BuildConfig.GET_PERMISSIONS_FIRSR) {
            SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
            boolean hasShow = spUtils.getBoolean("IS_PERMISSIONS_DIALOG", false);
            if (!hasShow) {
                spUtils.putBoolean("IS_PERMISSIONS_DIALOG", true);
                showPermissionsTipDialog(loginAccount, dataBean);
            } else {
                Logs.e("saveUserInfo","f");
                Intent intent = new Intent();
                _mActivity.setResult(LOGIN_OK, intent);
                _mActivity.finish();
            }
        } else {
            Logs.e("saveUserInfo","f");
            Intent intent = new Intent();
            _mActivity.setResult(LOGIN_OK, intent);
            _mActivity.finish();
        }
    }

    private String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public void showPermissionsTipDialog(String loginAccountm, UserInfoVo.DataBean dataBean) {
        if (_mActivity != null) {
            CustomDialog authorityDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.dialog_login_authority_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            authorityDialog.setCancelable(false);
            authorityDialog.setCanceledOnTouchOutside(false);

            TextView mTvContent = authorityDialog.findViewById(R.id.tv_content);
            TextView mTvCancel = authorityDialog.findViewById(R.id.tv_cancel);
            TextView mTvConfirm = authorityDialog.findViewById(R.id.tv_confirm);

            mTvContent.setText("\"" + _mActivity.getResources().getString(R.string.app_name) + "\"" + "正在向您获取“存储”权限，同意后，将用于本APP账号缓存，以后登录均可快速选择缓存的账号直接登录。");
            mTvCancel.setOnClickListener(v -> {
                if (authorityDialog != null && authorityDialog.isShowing()) {
                    authorityDialog.dismiss();
                }
                Logs.e("saveUserInfo","f");
                Intent intent = new Intent();
                _mActivity.setResult(LOGIN_OK, intent);
                _mActivity.finish();
            });
            mTvConfirm.setOnClickListener(v -> {
                if (authorityDialog != null && authorityDialog.isShowing()) {
                    authorityDialog.dismiss();
                }
                PermissionHelper.checkPermissions(new PermissionHelper.OnPermissionListener() {
                    @Override
                    public void onGranted() {
                        mViewModel.saveAccountToSDK(loginAccountm, dataBean.getPassword());
                        Logs.e("saveUserInfo","f");
                        Intent intent = new Intent();
                        _mActivity.setResult(LOGIN_OK, intent);
                        _mActivity.finish();
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        Logs.e("saveUserInfo","f");
                        Intent intent = new Intent();
                        _mActivity.setResult(LOGIN_OK, intent);
                        _mActivity.finish();
                    }
                }, permissions);
            });
            authorityDialog.show();
        }
    }

    public interface OnItemClickListener {
        void onItemDelete(String username);

        void onItemClick(String username);
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
            if (setPwdDialog != null && setPwdDialog.isShowing()) {
                setPwdDialog.dismiss();
            }
            Logs.e("saveUserInfo","f");
            Intent intent = new Intent();
            _mActivity.setResult(LOGIN_OK, intent);
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

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == 0x0002) {
            if (resultCode == RESULT_OK) {
                Logs.e("saveUserInfo","f");
                Intent intent = new Intent();
                _mActivity.setResult(LOGIN_OK, intent);
                _mActivity.finish();
            }
        }
    }
}
