package com.zqhy.app.core.vm;

import com.box.other.blankj.utilcode.util.Logs;
import com.google.gson.JsonParseException;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.ServerException;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * @author Administrator
 * @date 2018/12/17
 */

public abstract class ExecuteCallback extends StringCallback {


    private OnNetWorkListener onNetWorkListener;

    public ExecuteCallback addListener(OnNetWorkListener onNetWorkListener) {
        this.onNetWorkListener = onNetWorkListener;
        return this;
    }


    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);
        if (onNetWorkListener != null) {
            onNetWorkListener.onBefore();
        }
    }

    @Override
    public void onSuccess(Response<String> response) {
        if (onNetWorkListener != null) {
            onNetWorkListener.onAfter();
        }
        String result = response.body().toString();
        Logs.e("result = " + result);
        if (checkNoLogin(result)) {
            Logs.e("用户token过期");
            if (onNetWorkListener != null) {
                onNetWorkListener.onFailure("用户token过期");
            }
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            handlerBaseVo(result);
        }
    }


    /**
     * 数据处理
     *
     * @param result
     */
    protected abstract void handlerBaseVo(String result);

    @Override
    public void onError(Response<String> response) {
        super.onError(response);
        Throwable e = response.getException();
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
        if (onNetWorkListener != null) {
            onNetWorkListener.onAfter();
            onNetWorkListener.onFailure(message);
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }

    private boolean checkNoLogin(String result) {
        try {
            JSONObject resultObj = new JSONObject(result);
            String data = resultObj.optString("data");
            if ("no_login".equals(data)) {
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
