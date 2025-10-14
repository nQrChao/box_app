package com.zqhy.app.core.view.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.App;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.view.AppSplashActivity;
import com.zqhy.app.core.vm.AuditCommentRequest;
import com.zqhy.app.core.vm.MainListRequest;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.utils.sp.SPUtils;

/**
 * @author pc
 * @date 2019/12/2-16:17
 * @description
 */
public class SDKAuthLoginActivity extends Activity {

    private MainListRequest request;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        request = new MainListRequest();
        Logs.e("打开自动登录页面");
        if(BuildConfig.APP_TEMPLATE == 9999 || BuildConfig.APP_TEMPLATE == 9998){
            handleIntentGame(getIntent());
        }else{
            handleIntent(getIntent());
        }

        /*Logs.e("ActivitySize: " + App.getActivityList().size());
        for (int i = 0; i < App.getActivityList().size(); i++) {
            Logs.e("ActivityName: " + App.getActivityList().get(i).getComponentName());
        }
        finish();*/
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //handleIntent(intent);
    }

    /**
     * 处理intent数据
     */
    private void handleIntentGame(Intent intent) {
        Logs.e("handleIntent=====>" + intent.toString());
        String sdk_action = intent.getStringExtra("action");
        String packagename = intent.getStringExtra("packagename");

        if ("authlogin".equals(sdk_action)){
            SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
            boolean isAgreement = spUtils.getBoolean("app_private_yes", false);
            if(!isAgreement){
                startActivity(new Intent(SDKAuthLoginActivity.this, AppSplashActivity.class));
                finish();
                return;
            }
            SPUtils userSpUtils = new SPUtils(App.instance(), UserInfoModel.SP_USER_INFO_MODEL);
            int uid = userSpUtils.getInt(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_UID, -1);
            String token = userSpUtils.getString(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_TOKEN);
            if (uid != -1 && !TextUtils.isEmpty(token)){
                request.postCheckToken(String.valueOf(uid), token, new AuditCommentRequest.OnActionDo() {
                    @Override
                    public void onStart() {}

                    @Override
                    public void onSuccess() {
                        if (checkApkExist(SDKAuthLoginActivity.this, packagename)) {
                            Intent newIntent = new Intent();
                            Logs.e("向sdk发送广播");
                            newIntent.setAction("sdk.authlogin");//这里可以自己定义
                            Bundle bundle=new Bundle();
                            bundle.putString("uid", String.valueOf(uid));
                            bundle.putString("token", token);
                            bundle.putString("packagename", packagename);
                            newIntent.putExtras(bundle);
                            Logs.e("向双开空间内sdk发送广播");
                            sendBroadcast(newIntent);
                        }else {
                            Intent newIntent = new Intent();
                            Logs.e("向双开空间内sdk发送广播");
                            newIntent.setAction("sdk.authlogin");//这里可以自己定义
                            Bundle bundle=new Bundle();
                            bundle.putString("uid", String.valueOf(uid));
                            bundle.putString("token", token);
                            bundle.putString("packagename", packagename);
                            newIntent.putExtras(bundle);
                        }
                        finish();
                    }

                    @Override
                    public void onError(String error) {
                        Toaster.show(error);
                        startActivity(new Intent(SDKAuthLoginActivity.this, LoginActivity.class).putExtra("packagename", packagename));
                        finish();
                    }
                });
            }else{
                startActivity(new Intent(SDKAuthLoginActivity.this, LoginActivity.class).putExtra("packagename", packagename));
                finish();
            }
        }else{
            finish();
        }
    }



    /**
     * 处理intent数据
     */
    private void handleIntent(Intent intent) {
        Logs.e("handleIntent=====>" + intent.toString());
        String sdk_action = intent.getStringExtra("action");
        String packagename = intent.getStringExtra("packagename");

        if ("authlogin".equals(sdk_action)){
            SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
            boolean isAgreement = spUtils.getBoolean("app_private_yes", false);
            if(!isAgreement){
                startActivity(new Intent(SDKAuthLoginActivity.this, AppSplashActivity.class));
                finish();
                return;
            }
            SPUtils userSpUtils = new SPUtils(App.instance(), UserInfoModel.SP_USER_INFO_MODEL);
            int uid = userSpUtils.getInt(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_UID, -1);
            String token = userSpUtils.getString(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_TOKEN);
            if (uid != -1 && !TextUtils.isEmpty(token)){
                request.postCheckToken(String.valueOf(uid), token, new AuditCommentRequest.OnActionDo() {
                    @Override
                    public void onStart() {}

                    @Override
                    public void onSuccess() {
                        if (checkApkExist(SDKAuthLoginActivity.this, packagename)) {
                            Intent newIntent = new Intent();
                            Logs.e("向sdk发送广播");
                            newIntent.setAction("sdk.authlogin");//这里可以自己定义
                            Bundle bundle=new Bundle();
                            bundle.putString("uid", String.valueOf(uid));
                            bundle.putString("token", token);
                            bundle.putString("packagename", packagename);
                            newIntent.putExtras(bundle);
                            //GmSpaceObject.sendGmSpaceBroadcast(newIntent);

                            Logs.e("向双开空间内sdk发送广播");
                            sendBroadcast(newIntent);
                        }else {
                            Intent newIntent = new Intent();
                            Logs.e("向双开空间内sdk发送广播");
                            newIntent.setAction("sdk.authlogin");//这里可以自己定义
                            Bundle bundle=new Bundle();
                            bundle.putString("uid", String.valueOf(uid));
                            bundle.putString("token", token);
                            bundle.putString("packagename", packagename);
                            newIntent.putExtras(bundle);
                            //GmSpaceObject.sendGmSpaceBroadcast(newIntent);
                        }
                        finish();
                    }

                    @Override
                    public void onError(String error) {
                        Toaster.show(error);
                        startActivity(new Intent(SDKAuthLoginActivity.this, LoginActivity.class).putExtra("packagename", packagename));
                        finish();
                    }
                });
            }else{
                startActivity(new Intent(SDKAuthLoginActivity.this, LoginActivity.class).putExtra("packagename", packagename));
                finish();
            }
        }else{
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
