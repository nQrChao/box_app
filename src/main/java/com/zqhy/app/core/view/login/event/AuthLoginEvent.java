package com.zqhy.app.core.view.login.event;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;

import com.box.common.data.model.AppletsData;
import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.blankj.utilcode.util.StringUtils;
import com.mobile.auth.gatewayauth.AuthUIConfig;
import com.mobile.auth.gatewayauth.PhoneNumberAuthHelper;
import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.TokenResultListener;
import com.mobile.auth.gatewayauth.model.TokenRet;
import com.zqhy.app.App;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;

/**
 * [功能说明]
 *
 * @author 韩国桐
 * @version [0.1, 2021/1/7]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AuthLoginEvent {
    private PhoneNumberAuthHelper authHelper;
    public boolean isSupport = false;
    private int temp = 0;
    private static AuthLoginEvent mInstance = null;
    private String AUTH_LOGIN_SIGN = "";

    public AuthLoginEvent() {
    }

    public static AuthLoginEvent instance() {
        if (mInstance == null) {
            mInstance = new AuthLoginEvent();
        }
        return mInstance;
    }

    public void init(AppletsData appletsData) {
        if (StringUtils.isEmpty(BuildConfig.AUTH_LOGIN_SIGN_INFO)) {
            AUTH_LOGIN_SIGN = appletsData.getMarketjson().getAUTH_LOGIN_SIGN_INFO();
        } else {
            AUTH_LOGIN_SIGN = BuildConfig.AUTH_LOGIN_SIGN_INFO;
        }
        if (BuildConfig.DEBUG) {
            if (BuildConfig.APPLICATION_ID.equals("com.tsdongyouxi.tsgame")) {
                authHelper = PhoneNumberAuthHelper.getInstance(App.instance(), new TokenResultListener() {
                    @Override
                    public void onTokenSuccess(String result) {
                        Logs.d("onTokenSuccess", result);
                        onSuccess(result);
                    }

                    @Override
                    public void onTokenFailed(String result) {
                        Logs.d("onTokenSuccess", result);
                        onError(result);
                    }
                });
                authHelper.setAuthSDKInfo("20ibemUCdMs3/Fdl0aiz5lA//2+QBWLBTlt15/TGr+LYB1ZW5dloaKZqnZmVTNKd+L1dEZHVojac/VL95+czcicgQRpC1R9TobHUZRhoyWST6Q5p6NIM/rqYpB5CaFldgS+Bgk1AoRrnq28wJ4Pr6iIJSUQ7cWV+PRvw00bxcKpvBdlbsUZc4IsKznMBEoKYn+UauzYAzuLr5dmflrNnlSQV0xjB9iNJP6GSE16pIJtk5QvzFLKzRluulQS7VN96VSgRk2sF4dlMKE/dzycBzQkvmVRfo2wT9TXotsO/kMCIx609JsRWtcIdxEFsO+Ye");
                authHelper.checkEnvAvailable(PhoneNumberAuthHelper.SERVICE_TYPE_LOGIN);
            }
        } else {
            if (!TextUtils.isEmpty(AUTH_LOGIN_SIGN)) {
                authHelper = PhoneNumberAuthHelper.getInstance(App.instance(), new TokenResultListener() {
                    @Override
                    public void onTokenSuccess(String result) {
                        Logs.d("onTokenSuccess", result);
                        onSuccess(result);
                    }

                    @Override
                    public void onTokenFailed(String result) {
                        Logs.d("onTokenSuccess", result);
                        onError(result);
                    }
                });

                authHelper.setAuthSDKInfo(AUTH_LOGIN_SIGN);
                authHelper.checkEnvAvailable(PhoneNumberAuthHelper.SERVICE_TYPE_LOGIN);
            }
        }
    }

    public void oneKeyLogin(Activity activity, int temp) {
        this.temp = temp;
        authHelper.hideLoginLoading();
        authHelper.quitLoginPage();
        int width = (int) ScreenUtil.dp2px(App.instance(),
                ScreenUtil.getScreenWidth(App.instance()) - ScreenUtil.dp2px(App.instance(), 100));
        AuthUIConfig.Builder authUIConfig = new AuthUIConfig.Builder();
        authUIConfig.setStatusBarColor(Color.parseColor("#FFFFFF"))
                .setNavColor(Color.parseColor("#FFFFFF"))
                .setLightColor(true)
                .setNavReturnImgPath("ic_actionbar_back")
                .setWebNavColor(Color.parseColor("#FFFFFF"))
                .setWebNavTextColor(Color.parseColor("#232323"))
                .setBottomNavColor(Color.parseColor("#FFFFFF"))
                .setStatusBarHidden(false)
                .setSloganText(App.instance().getResources().getString(R.string.app_name) + "欢迎您！")
                .setSloganTextSize(12)
                .setSloganTextColor(Color.parseColor("#9B9B9B"))
                .setSwitchAccText("更多登录方式")
                .setSwitchAccTextSize(16)
                .setSwitchAccTextColor(Color.parseColor("#232323"))
                .setLogoImgPath("ic_launcher")
                .setNumberSize(29)
                .setNumberColor(Color.parseColor("#232323"))
                .setLogoWidth(80)
                .setLogoHeight(80)
                .setLogBtnTextSize(18)
                .setLogBtnWidth(width)
                .setLogBtnHeight(54)
                .setLogBtnBackgroundPath("shape_55c0fe_5571fe_big_radius")
                .setLogBtnText("本机一键登录")
                .setPrivacyState(false)
                .setPrivacyBefore("登录注册即表示同意")
                .setAppPrivacyOne("用户协议", AppConfig.APP_REGISTRATION_PROTOCOL)
                .setAppPrivacyTwo("隐私政策", AppConfig.APP_PRIVACY_PROTOCOL);
        authHelper.setAuthUIConfig(authUIConfig.create());
        authHelper.getLoginToken(activity, 3000);
    }

    /**
     * 一键登录
     **/
    public void oneKMeyLogin(Activity activity, OneKeyLogin oneKeyLogin) {
        this.oneKeyLogin = oneKeyLogin;
        oneKeyLogin(activity, 0);
    }

    public void remove() {
        authHelper.removeAuthRegisterXmlConfig();
    }


    public void onSuccess(String result) {
        TokenRet tokenRet = TokenRet.fromJson(result);
        switch (tokenRet.getCode()) {
            case ResultCode.CODE_ERROR_ENV_CHECK_SUCCESS:
                isSupport = true;
                break;
            case ResultCode.CODE_START_AUTHPAGE_SUCCESS:
                temp = 2;
                break;
            case ResultCode.CODE_SUCCESS:
                //发起我方的登录请求逻辑
                if (oneKeyLogin != null) {
                    oneKeyLogin.onSuccess(tokenRet);
                    authHelper.quitLoginPage();
                }

                break;
            case ResultCode.CODE_ERROR_USER_SWITCH:
                //发起切换逻辑
                authHelper.hideLoginLoading();
                authHelper.quitLoginPage();
                break;
        }

        if (ResultCode.CODE_SUCCESS.equals(tokenRet.getCode())) {
            Logs.d("AuthLoginEvent", tokenRet.getToken() + "");
        }
        Logs.d("AuthLoginEvent", "code：" + tokenRet.getCode());
        authHelper.hideLoginLoading();
    }

    public void onError(String result) {
        TokenRet tokenRet = TokenRet.fromJson(result);
        Logs.d("AuthLoginEvent", "code：" + tokenRet.getCode());
        String msg;
        switch (tokenRet.getCode()) {
            case ResultCode.CODE_ERROR_START_AUTHPAGE_FAIL:
                msg = ResultCode.MSG_ERROR_START_AUTHPAGE_FAIL;
                break;
            /*case ResultCode.CODE_ERROR_USER_SWITCH:
                //发起切换逻辑
                break;*/
            /*case ResultCode.CODE_ERROR_USER_CANCEL:
                break;*/
            case ResultCode.CODE_ERROR_NO_MOBILE_NETWORK_FAIL:
                msg = "移动网络未开启";
                break;
            case ResultCode.CODE_ERROR_OPERATOR_UNKNOWN_FAIL:
                msg = ResultCode.MSG_ERROR_OPERATOR_UNKNOWN_FAIL;
                break;
            case ResultCode.CODE_ERROR_UNKNOWN_FAIL:
                msg = ResultCode.MSG_ERROR_UNKNOWN_FAIL;
                break;
            case ResultCode.CODE_ERROR_FUNCTION_DEMOTE:
                msg = ResultCode.MSG_ERROR_FUNCTION_DEMOTE;
                break;
            case ResultCode.CODE_ERROR_FUNCTION_LIMIT:
                msg = ResultCode.MSG_ERROR_FUNCTION_LIMIT;
                break;
            case ResultCode.CODE_ERROR_USER_SWITCH:
                msg = ResultCode.MSG_ERROR_USER_SWITCH;
                break;
            case ResultCode.CODE_ERROR_USER_CANCEL:
                msg = ResultCode.MSG_ERROR_USER_CANCEL;
                break;
            default:
                msg = "一键登录失败，请使用其他方式登录";
                break;
        }
        oneKeyLogin.onError(msg);
        authHelper.hideLoginLoading();
        authHelper.quitLoginPage();
    }

    public void hid() {
        authHelper.hideLoginLoading();
    }

    public void quit() {
        authHelper.quitLoginPage();
    }


    private OneKeyLogin oneKeyLogin;

    public interface OneKeyLogin {
        void onSuccess(TokenRet dataBean);

        void onError(String error);
    }


}
