package com.zqhy.app.network;

import androidx.annotation.NonNull;

import android.os.Build;
import android.text.TextUtils;

import com.box.common.sdk.ImSDK;
import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.cnoaid.oaid.DeviceIdentifier;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zqhy.app.App;
import com.zqhy.app.core.tool.utilcode.AppsUtils;
import com.zqhy.app.core.tool.utilcode.DeviceUtils;
import com.zqhy.app.network.request.BaseMessage;
import com.zqhy.app.network.utils.AppUtils;
import com.zqhy.app.network.utils.Des;
import com.zqhy.app.push.PushIntentService;
import com.zqhy.app.utils.JiuYaoDeviceUtils;
import com.zqhy.app.utils.json.IntegerDefault0Adapter;
import com.zqhy.app.utils.sp.SPUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * OKHTTP工具类
 *
 * @author 韩国桐
 * @version [0.1, 2019-12-24]
 * @see [OkHttpClient]
 * @since [HTTP 网络基类]
 */
public class OKHTTPUtil {
    public static final String TAG = "OKHTTPUtil";
    private static OkHttpClient client = new OkHttpClient();
    public final static int ERROR_400 = 400;
    private Gson gson;

    public OKHTTPUtil() {
        gson = new GsonBuilder().registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(int.class, new IntegerDefault0Adapter()).create();
    }

    public void postFile(String actionUrl, String json, final OKFileCallback callback) {
        //参数不为空
        String params = "";
        if (json != null && json.length() > 0) {
            Logs.d(getClass().getSimpleName(), "URL: " + actionUrl + ", params: " + json);
            params = json;
        }
        Request request;
        if (!TextUtils.isEmpty(json)) {
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), params);
            request = new Request.Builder()
                    .url(actionUrl)
                    .post(body)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(actionUrl)
                    .get()
                    .build();
        }
        //通用请求设置
        client = client.newBuilder().connectTimeout(30000, TimeUnit.MILLISECONDS).
                readTimeout(30000, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true).build();

        //创建异步访问
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                if (e instanceof java.net.SocketTimeoutException) {
                    callback.onError(ErrorInfo.TIME_OUT);
                } else if (e instanceof java.net.ConnectException) {
                    callback.onError(ErrorInfo.FAIL_TO_OPEN_CONNECTION);
                } else if (e instanceof MalformedURLException) {
                    callback.onError(ErrorInfo.URL_INVALID);
                } else {
                    callback.onError(ErrorInfo.BAD_SERVER);
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                ResponseBody result;
                try {
                    //成功
                    result = response.body();
                    callback.onSuccess(result);
                } catch (Exception e) {
                    //转码失败
                    e.printStackTrace();
                    if (!response.isSuccessful()) {
                        if (response.code() > ERROR_400) {
                            callback.onError(ErrorInfo.UNSUPPORTED_ENCODE);
                        } else {
                            callback.onError(ErrorInfo.BAD_SERVER);
                        }
                    } else {
                        callback.onError(ErrorInfo.UNSUPPORTED_ENCODE);
                    }
                }
            }
        });
    }

    public void get(String actionUrl, Map<String, String> parms, final OKCallback callback) {
        //表单参数创建
        String parmsString = "";
        //参数不为空
        if (parms != null && parms.size() > 0) {
            try {
                parmsString = parseMap2String("GET", parms);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Logs.d(getClass().getSimpleName(), "URL: " + actionUrl + parmsString);
        }
        actionUrl += parmsString;
        Request request = new Request.Builder()
                .url(actionUrl)
                .get()
                .build();

        //通用请求设置
        client = client.newBuilder().connectTimeout(30000, TimeUnit.MILLISECONDS).
                readTimeout(30000, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true).build();

        //创建异步访问
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                if (e instanceof java.net.SocketTimeoutException) {
                    callback.onError(ErrorInfo.TIME_OUT);
                } else if (e instanceof java.net.ConnectException) {
                    callback.onError(ErrorInfo.FAIL_TO_OPEN_CONNECTION);
                } else if (e instanceof MalformedURLException) {
                    callback.onError(ErrorInfo.URL_INVALID);
                } else {
                    callback.onError(ErrorInfo.BAD_SERVER);
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                String result;
                try {
                    //成功
                    if (response.body() != null) {
                        result = response.body().string();
                        Logs.d(getClass().getSimpleName(), "result: " + result);
                        if (!response.isSuccessful()) {
                            callback.onError(ErrorInfo.BAD_SERVER);
                        } else {
                            callback.onSuccess(result);
                        }
                    } else {
                        callback.onError(ErrorInfo.BAD_SERVER);
                    }
                } catch (Exception e) {
                    //转码失败
                    e.printStackTrace();
                    if (!response.isSuccessful()) {
                        //返回值不等于200
                        if (response.code() > ERROR_400) {
                            callback.onError(ErrorInfo.BAD_SERVER);
                        } else {
                            callback.onError(ErrorInfo.BAD_SERVER);
                        }
                    } else {
                        callback.onError(ErrorInfo.BAD_SERVER);
                    }
                }
            }
        });
    }


    public void postJson(String actionUrl, String jsonObject, final OKCallback callback) {
        //参数不为空
        String parms = "";
        if (jsonObject != null && jsonObject.length() > 0) {
            Logs.d(getClass().getSimpleName(), "URL: " + actionUrl + ", params: " + jsonObject);
            parms = jsonObject;
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), parms);
        Request request = new Request.Builder()
                .url(actionUrl)
                .post(body)
                .build();

        //通用请求设置
        client = client.newBuilder().connectTimeout(30000, TimeUnit.MILLISECONDS).
                readTimeout(30000, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true).build();

        //创建异步访问
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                if (e instanceof java.net.SocketTimeoutException) {
                    callback.onError(ErrorInfo.TIME_OUT);
                } else if (e instanceof java.net.ConnectException) {
                    callback.onError(ErrorInfo.FAIL_TO_OPEN_CONNECTION);
                } else if (e instanceof MalformedURLException) {
                    callback.onError(ErrorInfo.URL_INVALID);
                } else {
                    callback.onError(ErrorInfo.BAD_SERVER);
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                String result;
                try {
                    //成功
                    if (response.body() != null) {
                        result = response.body().string();
                        Logs.d(getClass().getSimpleName(), "result: " + result);
                        if (!response.isSuccessful()) {
                            callback.onError(ErrorInfo.BAD_SERVER);
                        } else {
                            callback.onSuccess(result);
                        }
                    } else {
                        callback.onError(ErrorInfo.BAD_SERVER);
                    }
                } catch (Exception e) {
                    //转码失败
                    e.printStackTrace();
                    if (!response.isSuccessful()) {
                        //返回值不等于200
                        if (response.code() > ERROR_400) {
                            callback.onError(ErrorInfo.UNSUPPORTED_ENCODE);
                        } else {
                            callback.onError(ErrorInfo.BAD_SERVER);
                        }
                    } else {
                        callback.onError(ErrorInfo.UNSUPPORTED_ENCODE);
                    }
                }
            }
        });
    }

    public void post(String actionUrl, Map<String, String> parms, final OKCallback callback) {
        //表单参数创建
        String parmsString = "";
        //参数不为空
        if (parms != null && parms.size() > 0) {
            parmsString = generateQueryString(parms);
            Logs.d(getClass().getSimpleName(), "URL: " + actionUrl + ", params: " + parmsString);
            addCommonParams(parms);
            try {
                parmsString = URLEncoder.encode(Des.encode(AppUtils.MapToString(parms)), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        Logs.d(getClass().getSimpleName(), "URL: " + actionUrl + ", params: " + parmsString);
        FormBody.Builder builder = new FormBody.Builder();
        FormBody body = builder.add("data", parmsString).build();
        Request request = new Request.Builder()
                .url(actionUrl)
                .post(body)
                .build();

        //通用请求设置
        client = client.newBuilder().connectTimeout(15000, TimeUnit.MILLISECONDS).
                readTimeout(15000, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true).build();

        //创建异步访问
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                if (e instanceof java.net.SocketTimeoutException) {
                    callback.onError(ErrorInfo.TIME_OUT);
                } else if (e instanceof java.net.ConnectException) {
                    callback.onError(ErrorInfo.FAIL_TO_OPEN_CONNECTION);
                } else if (e instanceof MalformedURLException) {
                    callback.onError(ErrorInfo.URL_INVALID);
                } else {
                    callback.onError(ErrorInfo.BAD_SERVER);
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                String result;
                try {
                    //成功
                    if (response.body() != null) {
                        result = response.body().string();
                        String api = parms.get("api");
                        Logs.e(TAG, api + ",result: " + result);
                        if (!response.isSuccessful()) {
                            callback.onError(ErrorInfo.BAD_SERVER);
                        } else {
                            callback.onSuccess(result);
                        }
                    } else {
                        callback.onError(ErrorInfo.BAD_SERVER);
                    }
                } catch (Exception e) {
                    //转码失败
                    e.printStackTrace();
                    if (!response.isSuccessful()) {
                        //返回值不等于200
                        if (response.code() > ERROR_400) {
                            callback.onError(ErrorInfo.UNSUPPORTED_ENCODE);
                        } else {
                            callback.onError(ErrorInfo.BAD_SERVER);
                        }
                    } else {
                        callback.onError(ErrorInfo.UNSUPPORTED_ENCODE);
                    }
                }
            }
        });
    }

    public interface OKCallback {
        /**
         * 网络请求成功
         *
         * @param result 请求到的输入流
         */
        void onSuccess(String result);

        /**
         * 网络请求失败
         *
         * @param errorInfo 错误信息
         */
        void onError(ErrorInfo errorInfo);
    }

    public interface OKFileCallback {
        /**
         * 网络请求成功
         *
         * @param result 请求到的输入流
         */
        void onSuccess(ResponseBody result);

        /**
         * 网络请求失败
         *
         * @param errorInfo 错误信息
         */
        void onError(ErrorInfo errorInfo);
    }

    /**
     * 错误信息列举
     **/
    public enum ErrorInfo {
        /**
         * 访问地址错误
         **/
        URL_INVALID(8000, "服务异常！请稍后再试或联系客服"),
        /**
         * 网络超时
         **/
        TIME_OUT(8001, "当前网络不稳定，建议稍后再试！-1"),
        /**
         * 无法连接
         **/
        FAIL_TO_OPEN_CONNECTION(8002, "当前网络不稳定，请稍后再试！-2"),
        /**
         * 数据格式错误
         **/
        UNSUPPORTED_ENCODE(8003, "数据异常，请重试或联系客服"),
        /**
         * 服务异常
         **/
        BAD_SERVER(8005, "异常错误，请重试或联系客服");

        /**
         * 获得错误代码
         *
         * @return int
         */
        public int getErrorCode() {
            return errorCode;
        }

        /**
         * 获得错误说明
         *
         * @return string
         */
        public String getErrorMessage() {
            return errorMessage;
        }

        private int errorCode;
        private String errorMessage;

        ErrorInfo(int errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }
    }

    private String generateQueryString(Map<String, String> param) {
        StringBuilder builder = new StringBuilder();

        builder.append("\"");

        for (Map.Entry<String, String> entry : param.entrySet()) {
            if (builder.length() > 1) {
                builder.append("&");
            }
            builder.append(entry.getKey()).append("=").append(entry.getValue());
        }

        builder.append("\"");

        return builder.toString();
    }

    public static String parseMap2String(String method, Map<String, String> data) throws UnsupportedEncodingException {
        if (data == null || data.size() == 0) {
            return "";
        }

        Set<String> keys = data.keySet();
        boolean isFirst = true;
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            if (isFirst) {
                isFirst = false;
                if ("GET".equals(method)) {
                    sb.append("?");
                }
            } else {
                sb.append("&");
            }
            String value = data.get(key);
            if (value != null) {
                sb.append(URLEncoder.encode(key, "UTF-8")).append("=").append(URLEncoder.encode(data.get(key), "UTF-8"));
            }

        }
        return sb.toString();
    }

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
        params.put("oldtgid", AppUtils.getChannelFromApk());
        params.put("tgid", AppUtils.getTgid());
        int versionCode = AppsUtils.getAppVersionCode(App.instance());
        params.put("version", String.valueOf(versionCode));
        params.put("mac", DeviceUtils.getMacAddress(App.instance()));
        params.put("imei", JiuYaoDeviceUtils.getDeviceIMEI(App.instance()));
        params.put("androidid", DeviceUtils.getAndroidID(App.instance()));
        params.put("oaid", ImSDK.appViewModelInstance.getOaid());
        params.put("guid", DeviceIdentifier.getGUID(App.instance()));
        params.put("canvas", DeviceIdentifier.getCanvasFingerprint());
        params.put("uuid", DeviceUtils.getUniqueId(App.instance()));
        params.put("vc", "1");

        String deviceId = "";
        if (!TextUtils.isEmpty(params.get("uuid")) && !"unknown".equals(params.get("uuid"))) {
            deviceId = JiuYaoDeviceUtils.getUniqueId(App.getContext());
        } else if (!TextUtils.isEmpty(params.get("imei")) && !"unknown".equals(params.get("imei"))) {
            deviceId = JiuYaoDeviceUtils.getDeviceIMEI(App.getContext());
        } else if (!TextUtils.isEmpty(params.get("mac")) && !"unknown".equals(params.get("mac"))) {
            deviceId = DeviceUtils.getMacAddress(App.getContext());
        }
        params.put("device_id", deviceId);
        params.put("device_id_2", JiuYaoDeviceUtils.getDeviceSign(App.getContext()));
        SPUtils spUtils = new SPUtils(PushIntentService.SP_PUSH_SERVICE);
        String client_id = spUtils.getString(PushIntentService.PUSH_CLIENT_ID);
        if (!TextUtils.isEmpty(client_id)) {
            params.put("client_id", client_id);
        }

        params.put("android_infos", DeviceUtils.getModel() + "|" + Build.BRAND + "|" + Build.VERSION.RELEASE + "|" + Build.VERSION.SDK_INT + "|" + com.box.other.blankj.utilcode.util.AppUtils.getAppName() + "|" + Build.SUPPORTED_ABIS[0] + "|" + com.box.other.blankj.utilcode.util.AppUtils.getAppSignaturesSHA1().get(0));

        //sign签名
        params.put("sign", AppUtils.getSignKey(params));
        for (String key : params.keySet()) {
            String value = params.get(key);
            //2018.07.18 参数 如果value值为null，替换成""，和签名保持一致
            if (value == null) {
                value = "";
            }
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
     * 获取Observable请求
     *
     * @param tClass 泛型转换
     * @param url    访问路径
     **/
    public <T> Observable<BaseMessage<T>> postRequest(final TypeToken<BaseMessage<T>> tClass, final String url,
                                                      final String json) {
        return Observable.create((ObservableOnSubscribe<BaseMessage<T>>)
                subscriber -> postJson(url, json, new OKCallback() {
                    @Override
                    public void onSuccess(String result) {
                        //成功
                        try {
                            BaseMessage<T> item = gson.fromJson(result, tClass.getType());
                            subscriber.onNext(item);
                            subscriber.onComplete();
                        } catch (Exception e) {
                            initError(result, subscriber);
                        }
                    }

                    @Override
                    public void onError(ErrorInfo errorInfo) {
                        //失败
                        BaseMessage<T> item = new BaseMessage<>();
                        item.state = String.valueOf(errorInfo.getErrorCode());
                        item.message = errorInfo.getErrorMessage();
                        subscriber.onNext(item);
                        subscriber.onComplete();
                    }
                })).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取Observable请求
     *
     * @param tClass 泛型转换
     * @param url    访问路径
     **/
    public <T> Observable<BaseMessage<T>> getRequest(final TypeToken<BaseMessage<T>> tClass, final String url,
                                                     final Map<String, String> parms) {
        return Observable.create((ObservableOnSubscribe<BaseMessage<T>>)
                subscriber -> get(url, parms, new OKCallback() {
                    @Override
                    public void onSuccess(String result) {
                        //成功
                        try {
                            BaseMessage<T> item = gson.fromJson(result, tClass.getType());
                            subscriber.onNext(item);
                            subscriber.onComplete();
                        } catch (Exception e) {
                            e.printStackTrace();
                            initError(result, subscriber);
                        }
                    }

                    @Override
                    public void onError(ErrorInfo errorInfo) {
                        //失败
                        BaseMessage<T> item = new BaseMessage<>();
                        item.state = String.valueOf(errorInfo.getErrorCode());
                        item.message = errorInfo.getErrorMessage();
                        subscriber.onNext(item);
                        subscriber.onComplete();
                    }
                })).observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取Observable请求
     *
     * @param tClass 泛型转换
     * @param url    访问路径
     **/
    public <T> Observable<BaseMessage<T>> postRequest(final TypeToken<BaseMessage<T>> tClass, final String url,
                                                      final Map<String, String> parms) {
        return Observable.create((ObservableOnSubscribe<BaseMessage<T>>)
                subscriber -> post(url, parms, new OKCallback() {
                    @Override
                    public void onSuccess(String result) {
                        //成功
                        try {
                            BaseMessage<T> item = gson.fromJson(result, tClass.getType());
                            if (item != null) {
                                subscriber.onNext(item);
                                subscriber.onComplete();
                            } else {
                                item = new BaseMessage<>();
                                item.state = "500";
                                item.message = "网络异常";
                                subscriber.onNext(item);
                                subscriber.onComplete();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            initError(result, subscriber);
                        }
                    }

                    @Override
                    public void onError(ErrorInfo errorInfo) {
                        //失败
                        BaseMessage<T> item = new BaseMessage<>();
                        item.state = String.valueOf(errorInfo.getErrorCode());
                        item.message = errorInfo.getErrorMessage();
                        subscriber.onNext(item);
                        subscriber.onComplete();
                    }
                })).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取Observable请求
     *
     * @param tClass 泛型转换
     * @param url    访问路径
     **/
    public <T> Observable<BaseMessage<T>> postRequestBackEnd(final TypeToken<BaseMessage<T>> tClass, final String url,
                                                             final Map<String, String> parms) {
        return Observable.create((ObservableOnSubscribe<BaseMessage<T>>)
                subscriber -> post(url, parms, new OKCallback() {
                    @Override
                    public void onSuccess(String result) {
                        //成功
                        try {
                            BaseMessage<T> item = gson.fromJson(result, tClass.getType());
                            if (item != null) {
                                subscriber.onNext(item);
                                subscriber.onComplete();
                            } else {
                                item = new BaseMessage<>();
                                item.state = "500";
                                item.message = "网络异常";
                                subscriber.onNext(item);
                                subscriber.onComplete();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            initError(result, subscriber);
                        }
                    }

                    @Override
                    public void onError(ErrorInfo errorInfo) {
                        //失败
                        BaseMessage<T> item = new BaseMessage<>();
                        item.state = String.valueOf(errorInfo.getErrorCode());
                        item.message = errorInfo.getErrorMessage();
                        subscriber.onNext(item);
                        subscriber.onComplete();
                    }
                })).observeOn(Schedulers.io());
    }

    /**
     * 获取Observable请求
     *
     * @param url 访问路径
     **/
    public Observable<BaseMessage<ResponseBody>> postFileRequest(final String url,
                                                                 final String json) {
        return Observable.create((ObservableOnSubscribe<BaseMessage<ResponseBody>>)
                subscriber -> postFile(url, json, new OKFileCallback() {
                    @Override
                    public void onSuccess(ResponseBody result) {
                        //成功
                        BaseMessage<ResponseBody> item = new BaseMessage<>();
                        item.data = result;
                        subscriber.onNext(item);
                        subscriber.onComplete();
                    }

                    @Override
                    public void onError(ErrorInfo errorInfo) {
                        //失败
                        BaseMessage<ResponseBody> item = new BaseMessage<>();
                        item.state = String.valueOf(errorInfo.getErrorCode());
                        item.message = errorInfo.getErrorMessage();
                        subscriber.onNext(item);
                        subscriber.onComplete();
                    }
                })).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 一些异常情况的通用处理
     **/
    private <T> void initError(String result, ObservableEmitter<BaseMessage<T>> subscriber) {
        BaseMessage<String> item = gson.fromJson(result,
                new TypeToken<BaseMessage<String>>() {
                }.getType());
        BaseMessage<T> data = new BaseMessage<>();
        if (!TextUtils.isEmpty(item.message)) {
            data.message = item.message;
        } else if (!TextUtils.isEmpty(item.data)) {
            data.message = item.data;
        } else {
            data.message = "服务异常";
        }
        data.state = item.state;
        data.data = null;
        subscriber.onNext(data);
        subscriber.onComplete();
    }
}
