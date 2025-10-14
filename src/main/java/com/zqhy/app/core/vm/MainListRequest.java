package com.zqhy.app.core.vm;

import android.text.TextUtils;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.google.gson.reflect.TypeToken;
import com.zqhy.app.model.PostAdData;
import com.zqhy.app.config.URL;
import com.zqhy.app.network.OKHTTPUtil;
import com.zqhy.app.network.request.BaseMessage;
import com.zqhy.app.network.request.BaseRequest;
import com.zqhy.app.network.utils.Base64;

import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class MainListRequest extends BaseRequest {

    public static final String AD_EVENT          = "ad_event";

    public Disposable postAdEvent(String jsonData) {
        return postAdEvent(jsonData, null);

    }

    public Disposable postAdEvent(String jsonData, OnMainDataGetCallBack callBack) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("api", AD_EVENT);
        map.put("client_type", "1");
        map.put("data", Base64.encode(jsonData.getBytes()));

        return okHttpUtils.postRequest(new TypeToken<BaseMessage<PostAdData>>() {
                                       },
                URL.getOkGoHttpUrl(), map)
                .subscribe(objectBaseMessage -> {
                    if (objectBaseMessage.isSuccess()) {
                        //成功进行解析跳转
                        Logs.e(AD_EVENT + " post success");
                        if (callBack != null) {
                            //成功进行解析跳转
                            callBack.onSuccess(objectBaseMessage.data);
                        }
                    } else {
                        //失败打印信息
                        if (TextUtils.isEmpty(objectBaseMessage.message)) {
                            objectBaseMessage.message = OKHTTPUtil.ErrorInfo.BAD_SERVER.getErrorMessage();
                        }
                        Logs.e(AD_EVENT + " post failure : " + objectBaseMessage.message);
                    }
                });
    }

    /**
     * 校验token是否有效
     */
    public Disposable postCheckToken(String uid, String token, AuditCommentRequest.OnActionDo callBack) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("api", "check_token");
        map.put("uid", uid);
        map.put("token", token);
        return okHttpUtils.postRequest(new TypeToken<BaseMessage<String>>(){},
                URL.getOkGoHttpUrl(),map)
                .subscribe(objectBaseMessage -> {
                    if(objectBaseMessage.isSuccess()){
                        //成功进行解析跳转
                        callBack.onSuccess();
                    }else{
                        //失败打印信息
                        if(TextUtils.isEmpty(objectBaseMessage.message)){
                            objectBaseMessage.message= OKHTTPUtil.ErrorInfo.BAD_SERVER.getErrorMessage();
                        }
                        callBack.onError(objectBaseMessage.message);
                    }
                });
    }

    public interface OnMainDataGetCallBack<T> {
        void onSuccess(T data);

        void onError(String error);
    }
}
