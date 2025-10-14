package com.zqhy.app.core.view.login.gamelogin;

import static com.box.mod.game.ModManager.LOGIN_CANCEL;
import static com.box.mod.game.ModManager.LOGIN_OK;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.EditText;

import com.box.common.IMUtilsKt;
import com.box.common.data.model.AppUserInfo;
import com.box.common.sdk.ImSDK;
import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.hjq.toast.Toaster;
import com.kunminx.architecture.ui.callback.UnPeekLiveData;
import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.model.TokenRet;
import com.zqhy.app.App;
import com.zqhy.app.base.BaseActivity;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.config.InviteConfig;
import com.zqhy.app.config.WxControlConfig;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.view.login.event.AuthLoginEvent;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.utils.AppUtils;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.report.AllDataReportAgency;
import com.zqhy.app.utils.sp.SPUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/27
 */

public class ModGameLoginActivity extends BaseActivity<ModGameLoginViewModel>{
    private String packagename;
    @Override
    public int getLayoutId() {
        return R.layout.mod_activity_login;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);

        if (TextUtils.isEmpty(App.getDeviceBean().getOaid())){
            IMUtilsKt.getOAIDWithRetry(
                    this,
                    oaid -> {
                        App.getDeviceBean().setOaid(oaid);
                        return kotlin.Unit.INSTANCE;
                    }
            );
        }

        packagename = getIntent().getStringExtra("packagename");

        List<String> list = UserInfoModel.getInstance().getLoggedAccount();
        if (list == null || list.size() == 0) {
            if (TextUtils.isEmpty(packagename)){
                showSuccess();
                if (AuthLoginEvent.instance().isSupport){//支持终端认证
                    AuthLoginEvent.instance().oneKMeyLogin(ModGameLoginActivity.this, new AuthLoginEvent.OneKeyLogin(){

                        @Override
                        public void onSuccess(TokenRet dataBean) {
                            Logs.d("OneKeyLogin", dataBean.toString());
                            oneKeyLogin(dataBean.getToken());
                        }

                        @Override
                        public void onError(String error) {
                            if (error.equals(ResultCode.MSG_ERROR_USER_SWITCH)){
                                loadRootFragment(R.id.fl_container, ModGameLoginFragment.newInstance());
                            }else if (error.equals(ResultCode.MSG_ERROR_USER_CANCEL)){
                                Toaster.show(error);
                                finish();
                            }else {
                                loadRootFragment(R.id.fl_container, ModGameLoginFragment.newInstance());
                            }
                        }
                    });
                }else{
                    loadRootFragment(R.id.fl_container, ModGameLoginFragment.newInstance());
                }
            }else {
                new Handler().postDelayed(() -> {
                    showSuccess();
                    if (AuthLoginEvent.instance().isSupport){//支持终端认证
                        AuthLoginEvent.instance().oneKMeyLogin(ModGameLoginActivity.this, new AuthLoginEvent.OneKeyLogin(){

                            @Override
                            public void onSuccess(TokenRet dataBean) {
                                Logs.d("OneKeyLogin", dataBean.toString());
                                oneKeyLogin(dataBean.getToken());
                            }

                            @Override
                            public void onError(String error) {
                                if (error.equals(ResultCode.MSG_ERROR_USER_SWITCH)){
                                    loadRootFragment(R.id.fl_container, ModGameLoginFragment.newInstance());
                                    Toaster.show(error);
                                }else if (error.equals(ResultCode.MSG_ERROR_USER_CANCEL)){
                                    Toaster.show(error);
                                    finish();
                                }else {
                                    loadRootFragment(R.id.fl_container, ModGameLoginFragment.newInstance());
                                }
                            }
                        });
                    }else{
                        loadRootFragment(R.id.fl_container, ModGameLoginFragment.newInstance());
                    }
                }, 3000);
                //AuthLoginEvent.instance().init();
            }
        } else {
            showSuccess();
            loadRootFragment(R.id.fl_container, ModGameLoginFragment.newInstance());
        }

        if (App.isShowEasyFloat){
            showEasyFloat(this, "login");
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

                        if (baseVo.getData().isCan_bind_password()){
                            showCommentTipsDialog();
                        }else{
                            Logs.e("oneKeyLogin","oneKeyLogin_onSuccess");
                            Intent intent = new Intent();
                            setResult(LOGIN_OK, intent);
                            finish();
                        }
                    } else {
                        Toaster.show(baseVo.getMsg());
                        loadRootFragment(R.id.fl_container, ModGameLoginFragment.newInstance());
                        Logs.e("oneKeyLogin","oneKeyLogin_Error");
                        Intent intent = new Intent();
                        setResult(LOGIN_CANCEL, intent);
                        finish();
                    }
                }
            });
        }
    }

    private CustomDialog setPwdDialog;
    public void showCommentTipsDialog() {
        if (setPwdDialog == null) {
            setPwdDialog = new CustomDialog(this, LayoutInflater.from(this).inflate(R.layout.layout_dialog_login_set_pwd, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        }
        setPwdDialog.setCancelable(false);

        EditText mEtPassword1 = setPwdDialog.findViewById(R.id.et_password);
        EditText mEtRePassword = setPwdDialog.findViewById(R.id.et_repassword);
        setPwdDialog.findViewById(R.id.tv_cancel).setOnClickListener(v -> {
            if (setPwdDialog != null && setPwdDialog.isShowing()){
                setPwdDialog.dismiss();
            }
            Logs.e("oneKeyLogin","modLoginActivity_showTip");
            Intent intent = new Intent();
            setResult(LOGIN_OK, intent);
            finish();
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

    private void bindPassword(String password) {
        if (mViewModel != null) {
            mViewModel.bindPassword(password, new OnBaseCallback<UserInfoVo>() {
                @Override
                public void onSuccess(UserInfoVo baseVo) {
                    if (baseVo != null && baseVo.getData() != null && !TextUtils.isEmpty(baseVo.getData().getToken())) {
                        if (setPwdDialog != null && setPwdDialog.isShowing()){
                            setPwdDialog.dismiss();
                        }
                        Logs.e("oneKeyLogin","bindPassword_onSuccess");
                        Intent intent = new Intent();
                        setResult(LOGIN_OK, intent);
                        finish();
                    } else {
                        Toaster.show( baseVo.getMsg());
                        Logs.e("oneKeyLogin","bindPassword_Error");
                        Intent intent = new Intent();
                        setResult(LOGIN_CANCEL, intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (getTopFragment() != null){
                if ("com.zqhy.app.core.view.login.gamelogin.ModGameLoginFragment".equals(getTopFragment().getClass().getName())){
                    finish();
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!TextUtils.isEmpty(packagename)){
            if (UserInfoModel.getInstance().getUserInfo() != null){
                UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
                int uid = userInfo.getUid();
                String token = userInfo.getToken();
                if (checkApkExist(ModGameLoginActivity.this, packagename)) {
                    Intent newIntent = new Intent();
                    Logs.e("向sdk发送广播");
                    newIntent.setAction("sdk.authlogin");//这里可以自己定义
                    Bundle bundle=new Bundle();
                    bundle.putString("uid", String.valueOf(uid));
                    bundle.putString("token", token);
                    bundle.putString("packagename", packagename);
                    newIntent.putExtras(bundle);
                    sendBroadcast(newIntent);
                    Intent intent = new Intent();
                    setResult(LOGIN_OK, intent);
                    finish();
                    return;
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(EventCenter eventCenter) {
        if (eventCenter.getEventCode() == EventConfig.ACTION_LOGIN_EVENT_CODE){
            finish();
        }
    }

    private static boolean checkApkExist(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName))
            return false;
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
