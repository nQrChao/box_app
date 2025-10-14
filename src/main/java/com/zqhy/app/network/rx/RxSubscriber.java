package com.zqhy.app.network.rx;


import android.content.Intent;

import com.box.other.blankj.utilcode.util.Logs;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.Setting;
import com.zqhy.app.base.BaseActivity;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.ui.receiver.NetStateReceiver;
import com.zqhy.app.core.view.login.LoginActivity;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.ServerException;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.utils.AppManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;

/**
 * @author tqzhang
 */
public abstract class RxSubscriber<T> extends DisposableSubscriber<T> {


    public RxSubscriber() {
        super();
    }

    public RxSubscriber(Map<String, String> params) {
        super();
        if (params != null) {
            this.api = params.get("api");
        }
    }

    private String api;
    private String tag;

    private long sentRequestAtMillis;
    private long receivedResponseAtMillis;
    private final int networkDelayMillis = 3000;

    public RxSubscriber(String tag, String api) {
        super();
        this.api = api;
        this.tag = tag;
    }


    private OnNetWorkListener onNetWorkListener;

    public RxSubscriber addListener(OnNetWorkListener onNetWorkListener) {
        this.onNetWorkListener = onNetWorkListener;
        return this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        sentRequestAtMillis = System.currentTimeMillis();
        if (onNetWorkListener != null) {
            onNetWorkListener.onBefore();
        }
        showLoading();
        if (!NetStateReceiver.isNetworkAvailable()) {
            onNoNetWork();
            if (onNetWorkListener != null) {
                onNetWorkListener.onAfter();
            }
            cancel();
            return;
        }
    }

    @Override
    public void onComplete() {
    }

    protected void showLoading() {

    }

    protected void onNoNetWork() {

    }

    @Override
    public void onError(Throwable e) {
        String message = null;
        if (e instanceof UnknownHostException) {
            message = "没有网络";
        } else if (e instanceof HttpException) {
            message = "网络错误";
        } else if (e instanceof SocketTimeoutException) {
            message = "网络连接超时";
        } else if (e instanceof JsonParseException
                || e instanceof JSONException) {
            message = "解析错误";
        } else if (e instanceof ConnectException) {
            message = "连接失败";
        } else if (e instanceof ServerException) {
            message = ((ServerException) e).message;
        }
        onFailure(message);
        if (onNetWorkListener != null) {
            onNetWorkListener.onAfter();
            onNetWorkListener.onFailure(message);
        }
    }

    @Override
    public void onNext(T t) {
        receivedResponseAtMillis = System.currentTimeMillis();
        if (onNetWorkListener != null) {
            onNetWorkListener.onAfter();
        }
        reportApi(api);
        checkResult(t);
    }


    /**
     * 检查服务器返回结果
     * 检查用户token是否过期
     *
     * @param t
     */
    private void checkResult(T t) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(t);
            Logs.json(json);
            String state = getJsonState(json);

            switch (state) {
                case "no_login":
                    EventBus.getDefault().post(new EventCenter(EventConfig.VE_CLOUD_NO_LOGIN));
                    if (shouldVerifyUserLogin(tag, api)) {
                        verifyUserLogin();
                    }
                    break;
            }
            if (Setting.HIDE_FIVE_FIGURE == 1){
                json = json.replaceAll("刷充", "");
                json = json.replaceAll("破解", "福利");
                json = json.replaceAll("现金", "代币");
                json = json.replaceAll("修改器", "屏蔽");
                json = json.replaceAll("GM版", "");
                json = json.replaceAll("GM", "管理员");
                Type listType = new TypeToken<BaseResponseVo>() {}.getType();
                BaseResponseVo baseResponseVo = gson.fromJson(json, listType);
                onSuccess((T) baseResponseVo);
            }else if ("99026".equals(BuildConfig.APP_UPDATE_ID)){
                json = json.replaceAll("GM", "管理员");

                Type listType = new TypeToken<BaseResponseVo>() {}.getType();
                BaseResponseVo baseResponseVo = gson.fromJson(json, listType);
                onSuccess((T) baseResponseVo);
            }else {
                onSuccess(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证用户Token
     */
    private void verifyUserLogin() {
        Logs.e("用户token过期");
        try {
            BaseActivity _mActivity = (BaseActivity) AppManager.getInstance().getTopActivity();
            UserInfoModel.getInstance().logout();
            _mActivity.startActivity(new Intent(_mActivity, LoginActivity.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * success
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * failure
     *
     * @param msg
     */
    public abstract void onFailure(String msg);

    private String getJsonState(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.isNull("state")) {
                return "";
            } else {
                String state = jsonObject.getString("state");
                return state;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    protected String[] notVerifyUserLoginApis = new String[]{
            "msg_kefumsg"
    };

    protected String[] notVerifyUserLoginTags = new String[]{
            String.valueOf(Constants.EVENT_KEY_MAIN_ACTIVITY_PAGE_STATE)
    };


    /**
     * 验证no_login是否需要登录
     *
     * @param tag
     * @param api
     * @return
     */
    private boolean shouldVerifyUserLogin(String tag, String api) {
        for (String keyTag : notVerifyUserLoginTags) {
            if (keyTag.equals(tag)) {
                for (String keyApi : notVerifyUserLoginApis) {
                    if (keyApi.equals(api)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    /**
     * 上报相应延迟Api
     *
     * @param api
     */
    public void reportApi(String api) {
        if ("monitor_slow_response".equals(api)) {
            return;
        }
        long networkTimeConsuming = receivedResponseAtMillis - sentRequestAtMillis;
        Logs.e("api = " + api + " cost " + networkTimeConsuming + "ms");
        if (networkTimeConsuming >= networkDelayMillis) {
            BaseRepository mRepository = new BaseRepository();

            Map<String, String> params = new TreeMap<>();
            params.put("api", "monitor_slow_response");
            params.put("api_name", api);
            params.put("response_time", String.valueOf(networkTimeConsuming));
            String data = mRepository.createPostData(params);

            mRepository.iApiService.postClaimData(URL.getApiUrl(params),data)
                    .compose(RxSchedulers.io_main())
                    .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                        @Override
                        public void onSuccess(BaseResponseVo baseVo) {

                        }

                        @Override
                        public void onFailure(String msg) {

                        }
                    }.addListener(onNetWorkListener));

        }
    }
}
