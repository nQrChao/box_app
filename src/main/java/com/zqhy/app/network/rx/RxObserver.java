package com.zqhy.app.network.rx;

import android.content.Intent;

import com.box.other.blankj.utilcode.util.Logs;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.base.BaseActivity;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.ui.receiver.NetStateReceiver;
import com.zqhy.app.core.view.login.LoginActivity;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.ServerException;
import com.zqhy.app.utils.AppManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * @author Administrator
 * @date 2018/11/9
 */

public abstract class RxObserver<T> extends DisposableObserver<T> {

    public RxObserver(Map<String, String>... params) {
        super();
        if (params != null) {

        }
    }

    private OnNetWorkListener onNetWorkListener;

    public RxObserver addListener(OnNetWorkListener onNetWorkListener) {
        this.onNetWorkListener = onNetWorkListener;
        return this;
    }

    private long sentRequestAtMillis;
    private long receivedResponseAtMillis;
    private final int networkDelayMillis = 3000;

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
            onError(new Throwable("没有网络"));
            return;
        }
    }

    @Override
    public void onNext(T t) {
        receivedResponseAtMillis = System.currentTimeMillis();
        if (onNetWorkListener != null) {
            onNetWorkListener.onAfter();
        }
        checkResult(t);
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
    public void onComplete() {

    }

    protected void showLoading() {

    }

    protected void onNoNetWork() {

    }

    /**
     * 检查服务器返回结果
     * 检查用户token是否过期
     *
     * @param t
     */
    private void checkResult(T t) {
        String json = new Gson().toJson(t);
        Logs.json(json.toString());
        String state = getJsonState(json);

        switch (state) {
            case "no_login":
                verifyUserLogin();
                break;
            default:
                onSuccess(t);
                break;
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
                    });

        }
    }
}
