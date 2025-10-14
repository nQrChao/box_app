package com.zqhy.app.network;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;


import com.blankj.utilcode.util.NetworkUtils;
import com.chaoji.im.sdk.ImSDK;
import com.chaoji.other.blankj.utilcode.util.Logs;
import com.chaoji.other.cnoaid.oaid.DeviceIdentifier;
import com.lzy.okgo.OkGo;
import com.zqhy.app.App;
import com.zqhy.app.DeviceBean;
import com.zqhy.app.Setting;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.tool.utilcode.AppsUtils;
import com.zqhy.app.core.tool.utilcode.DeviceUtils;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.listener.NetworkPollingListener;
import com.zqhy.app.network.utils.AppUtils;
import com.zqhy.app.network.utils.Des;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.push.PushIntentService;
import com.zqhy.app.utils.JiuYaoDeviceUtils;
import com.zqhy.app.utils.TsDeviceUtils;
import com.zqhy.app.utils.sp.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * @author Administrator
 * @date 2018/11/2
 */

public class OkGoApiBuilder {

    private static final String TAG = OkGoApiBuilder.class.getSimpleName();

    /**
     * 不需要添加username和token参数的api
     */
    protected String[] notNeedUsernameApis = new String[]{"get_code", "get_userinfo"};

    /**
     * 添加公共参数
     *
     * @param params
     * @return
     */
    public Map<String, String> addCommonParams(Map<String, String> params) {
        if (params == null) {
            params = new TreeMap<>();
        }

        //1 Android    2 iOS
        params.put("client_type", "1");
        /*****2017.11.09 注释以下代码 ********************************************/
        /*****2017.12.11 更新需求 所有api都发oldtgid*******************************************************/
        String api = params.get("api");
        params.put("oldtgid", AppUtils.getChannelFromApk());
        params.put("tgid", AppUtils.getTgid());

        /*****2017.12.10  api = get_code 里字段username 对应的是手机号*****************************************************************/

        UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
        if (isNeedAddUsernameParam(api)) {
            if (userInfoBean != null) {
                params.put("uid", String.valueOf(userInfoBean.getUid()));
                params.put("token", userInfoBean.getToken());
            }
        }

        /*****2018.01.18 所有api都发is_special*****************************************************************/
        try {
            if (userInfoBean != null) {
                params.put("is_special", String.valueOf(userInfoBean.getIs_special()));
            } else {
                params.put("is_special", "0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            params.put("is_special", "0");
        }

        /*******2018.03.17 增加固定参数 version***************************************************************************************/
        int versionCode = AppsUtils.getAppVersionCode(App.instance());
        params.put("api_version", "20250904");
        params.put("version", String.valueOf(versionCode));
        params.put("vc", "1");
        params.put("plat_id", "4");//中心统一平台标识 4：小点

        /*******2018.07.10 增加固定参数 mac iemi androidid uuid***************************************************************************************/

        //添加权限判断 如果没有权限 公共参数不添加用户设备数据
        SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
        boolean isAgreement = spUtils.getBoolean("app_private_yes", false);
        boolean isAgreement1 = spUtils.getBoolean("app_audit_private_yes", false);
        if (isAgreement || isAgreement1){//同意一种类型隐私弹窗就可以上报设备信息了
            DeviceBean deviceBean = App.getDeviceBean();
            if (deviceBean.getMac() == null){
                deviceBean.setMac(DeviceUtils.getMacAddress(App.instance()));
            }
            if (deviceBean.getImei() == null){
                deviceBean.setImei(DeviceUtils.getIMEI_1(App.instance()));
            }
            if (deviceBean.getIp() == null){
                deviceBean.setIp(NetworkUtils.getIPAddress(true));
            }
            if (deviceBean.getAndroidid() == null){
                deviceBean.setAndroidid(DeviceUtils.getAndroidID(App.instance()));
            }
            if (deviceBean.getOaid() == null) {
                deviceBean.setOaid(ImSDK.appViewModelInstance.getOaid());
            }
            if (deviceBean.getDevice_id() == null) {
                deviceBean.setDevice_id(DeviceUtils.getUniqueId(App.instance()));
            }
            if (deviceBean.getTs_device_version() == null){
                deviceBean.setTs_device_version(String.valueOf(Build.VERSION.RELEASE));
            }
            if (deviceBean.getTs_device_version_code() == null){
                deviceBean.setTs_device_version_code(String.valueOf(Build.VERSION.SDK_INT));
            }
            if (deviceBean.getTs_device_brand() == null){
                deviceBean.setTs_device_brand(String.valueOf(Build.BRAND));
            }
            if (deviceBean.getTs_device_model() == null){
                deviceBean.setTs_device_model(String.valueOf(Build.MODEL));
            }

            params.put("mac", deviceBean.getMac());
            params.put("guid", DeviceIdentifier.getGUID(App.instance()));
            params.put("canvas", DeviceIdentifier.getCanvasFingerprint() );
            params.put("imei", deviceBean.getImei());
            params.put("ip", deviceBean.getIp());
            params.put("androidid", deviceBean.getAndroidid());
            params.put("oaid", deviceBean.getOaid());
            params.put("device_id", deviceBean.getDevice_id());
            params.put("ts_device_version", deviceBean.getTs_device_version());
            params.put("ts_device_version_code", deviceBean.getTs_device_version_code());
            params.put("ts_device_brand", deviceBean.getTs_device_brand());
            params.put("ts_device_model", deviceBean.getTs_device_model());
        }

        params.put("ua", Setting.USER_AGENT);
        //params.put("debug", "1");

        SPUtils spUtils1 = new SPUtils(App.instance(), PushIntentService.SP_PUSH_SERVICE);
        String client_id = spUtils1.getString(PushIntentService.PUSH_CLIENT_ID);

        if (!TextUtils.isEmpty(client_id)) {
            params.put("client_id", client_id);
        }


        /**
         * 2020.05.11 新增
         */
        params.put("appid", BuildConfig.APP_UPDATE_ID);

        params.put("android_infos", DeviceUtils.getModel()+"|"+Build.BRAND+"|"+Build.VERSION.RELEASE+"|"+Build.VERSION.SDK_INT+"|"+com.chaoji.other.blankj.utilcode.util.AppUtils.getAppName()+"|"+Build.SUPPORTED_ABIS[0]+"|"+com.chaoji.other.blankj.utilcode.util.AppUtils.getAppSignaturesSHA1().get(0));

        //sign签名
        params.put("sign", AppUtils.getSignKey(params));

        for (String key : params.keySet()) {
            String value = params.get(key);
            //2018.07.18 参数 如果value值为null，替换成""，和签名保持一致
            if (value == null) {
                value = "";
            }
            params.put(key, value);

            try {
                String encodeValue = URLEncoder.encode(value, "UTF-8");
                params.put(key, encodeValue);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return params;
    }


    /**
     * 判断api是否需要添加username和token参数
     *
     * @param api
     * @return
     */
    protected boolean isNeedAddUsernameParam(String api) {
        if (TextUtils.isEmpty(api)) {
            return true;
        }
        for (int i = 0; i < notNeedUsernameApis.length; i++) {
            if (api.equals(notNeedUsernameApis[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 2018.06.29 新增轮询Api地址 rxjava版本
     */
    @SuppressLint("CheckResult")
    public void pollingUrls(final NetworkPollingListener networkPollingListener) {
        Observable.create((ObservableEmitter<Integer> observableEmitter) -> {
            String[] HttpUrlS = URL.HTTP_URLS;
            for (int i = 0; i < HttpUrlS.length; i++) {
                try {
                    String apiUrl = HttpUrlS[i] + "/ok.txt";
                    Logs.e(TAG, "当前轮询的api地址为：" + apiUrl);
                    Response response = OkGo.get(apiUrl)
                            .client(new OkHttpClient.Builder()
                                    .connectTimeout(3000, TimeUnit.MILLISECONDS)
                                    .readTimeout(3000, TimeUnit.MILLISECONDS)
                                    .writeTimeout(3000, TimeUnit.MILLISECONDS)
                                    .build())
                            .execute();
                    String result = response.body().string();

                    Logs.e(TAG, "response.code() = " + response.code());
                    Logs.e(TAG, "pollingUrlRunnable result :" + result);

                    if (response.code() == 200) {
                        URL.HTTP_URL = HttpUrlS[i];
                        observableEmitter.onNext(NetworkPollingListener.POLLING_SUCCESS);
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            observableEmitter.onNext(NetworkPollingListener.POLLING_FAILURE);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    if (integer == NetworkPollingListener.POLLING_SUCCESS) {
                        EventBus.getDefault().post(new EventCenter(EventConfig.REFRESH_API_SERVICE_EVENT_CODE, integer));
                        networkPollingListener.onSuccess();
                    } else if (integer == NetworkPollingListener.POLLING_FAILURE) {
                        networkPollingListener.onFailure();
                    }
                }, throwable -> throwable.printStackTrace());
    }


    /**
     * 创建postData
     *
     * @param params
     * @return
     */
    public String createPostData(Map<String, String> params) {
        Map<String, String> targetParams = addCommonParams(params);
        Logs.e("targetParams:" + AppUtils.MapToString(targetParams));
        //DES加密数据
        final Map<String, String> map = new TreeMap<>();
        try {
            map.put("data", URLEncoder.encode(Des.encode(AppUtils.MapToString(targetParams)), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

       Logs.e("原串(发送)：" + map.get("data"));
        return map.get("data");
    }
}
