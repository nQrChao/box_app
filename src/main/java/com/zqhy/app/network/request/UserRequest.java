package com.zqhy.app.network.request;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.model.community.qa.UserQaCanAnswerInfo;
import com.zqhy.app.core.data.model.message.InteractiveMessageListVo;
import com.zqhy.app.core.data.model.message.MessageInfoVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.vm.user.presenter.UserPresenter;
import com.zqhy.app.crash.CustomError;
import com.zqhy.app.network.OKHTTPUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class UserRequest extends BaseRequest {

    public static final String USER_INFO="get_userinfo";
    public static final String MSG_GAME="msg_gamechange";
    public static final String MSG_KEFU="msg_kefumsg";
    public static final String MSG_MY="comment_user_interaction";
    public static final String MSG_QA="comment_answer_invite";


    /**
     * 用户信息获取接口
     * **/
    public Disposable getUserInfoAduit(int uid, String token, UserPresenter.OnUserInfoQueryCallBack callBack){
        Map<String,String> map=new LinkedHashMap<>();
        map.put("api",USER_INFO);
        map.put("uid",String.valueOf(uid));
        map.put("token",String.valueOf(token));
        callBack.onStart();
        return okHttpUtils.postRequest(new TypeToken<BaseMessage<UserInfoVo.DataBean>>(){},
                URL.getOkGoHttpUrl(),map)
                .subscribe(objectBaseMessage -> {
                    if(objectBaseMessage.isSuccess()){
                        //成功进行解析跳转
                        callBack.onSuccess(objectBaseMessage.data);
                    }else{
                        //失败打印信息
                        if(TextUtils.isEmpty(objectBaseMessage.message)){
                            objectBaseMessage.message= OKHTTPUtil.ErrorInfo.BAD_SERVER.getErrorMessage();
                        }
                        callBack.onError(objectBaseMessage.message);
                    }
                }, CustomError::log);
    }

    /**
     * 用户信息获取接口
     * **/
    public Disposable getUserInfo(UserPresenter.OnUserInfoQueryCallBack callBack){
        Map<String,String> map=new LinkedHashMap<>();
        map.put("api",USER_INFO);
        map.put("coupon_number","1");
        map.put("rebate_apply","1");
        map.put("vip","1");
        map.put("shouchong","2");
        setUserMap(map);
        callBack.onStart();
        return okHttpUtils.postRequest(new TypeToken<BaseMessage<UserInfoVo.DataBean>>(){},
                URL.getOkGoHttpUrl(),map)
                .subscribe(objectBaseMessage -> {
                    if(objectBaseMessage.isSuccess()){
                        //成功进行解析跳转
                        callBack.onSuccess(objectBaseMessage.data);
                    }else{
                        //失败打印信息
                        if(TextUtils.isEmpty(objectBaseMessage.message)){
                            objectBaseMessage.message= OKHTTPUtil.ErrorInfo.BAD_SERVER.getErrorMessage();
                        }
                        callBack.onError(objectBaseMessage.message);
                    }
                }, CustomError::log);
    }

    /**
     * 用户信息获取接口
     * **/
    public Disposable getGameMessage(long logTime, UserPresenter.OnMessageQueryCallBack callBack){
        Map<String,String> map=new LinkedHashMap<>();
        map.put("api",MSG_GAME);
        if (logTime > 0) {
            map.put("logtime", String.valueOf(logTime));
        }
        setUserMap(map);
        return okHttpUtils.postRequestBackEnd(new TypeToken<BaseMessage<List<MessageInfoVo>>>(){},
                URL.getOkGoHttpUrl(),map)
                .subscribe(objectBaseMessage -> {
                    if(objectBaseMessage.isSuccess()){
                        //成功进行解析跳转
                        callBack.onSuccess(objectBaseMessage.data);
                    }else{
                        callBack.onSuccess(null);
                    }
                }, CustomError::log);
    }
    /**
     * 用户信息获取接口
     * **/
    public Disposable getKefuMessage(int maxId, UserPresenter.OnMessageQueryCallBack callBack){
        Map<String,String> map=new LinkedHashMap<>();
        map.put("api",MSG_KEFU);
        map.put("id", String.valueOf(maxId));
        setUserMap(map);
        return okHttpUtils.postRequestBackEnd(new TypeToken<BaseMessage<List<MessageInfoVo>>>(){},
                URL.getOkGoHttpUrl(),map)
                .subscribe(objectBaseMessage -> {
                    if(objectBaseMessage.isSuccess()){
                        //成功进行解析跳转
                        callBack.onSuccess(objectBaseMessage.data);
                    }else{
                        callBack.onSuccess(null);
                    }
                }, CustomError::log);
    }
    /**
     * 用户信息获取接口
     * **/
    public Disposable getMyMessage(UserPresenter.OnMessageMyQyeryCallBack callBack){
        Map<String,String> map=new LinkedHashMap<>();
        map.put("api",MSG_MY);
        map.put("last_id", String.valueOf(1));
        setUserMap(map);
        return okHttpUtils.postRequestBackEnd(new TypeToken<BaseMessage<List<InteractiveMessageListVo.DataBean>>>(){},
                URL.getOkGoHttpUrl(),map)
                .subscribe(objectBaseMessage -> {
                    if(objectBaseMessage.isSuccess()){
                        //成功进行解析跳转
                        callBack.onSuccess(objectBaseMessage.data);
                    }else{
                        callBack.onSuccess(null);
                    }
                }, CustomError::log);
    }

    /**
     * 用户信息获取接口
     * **/
    public Disposable getMyQaMessage(UserPresenter.OnQaMessageQueryCallBack callBack){
        Map<String,String> map=new LinkedHashMap<>();
        map.put("api",MSG_QA);
        map.put("last_id", String.valueOf(1));
        setUserMap(map);
        return okHttpUtils.postRequestBackEnd(new TypeToken<BaseMessage<UserQaCanAnswerInfo>>(){},
                URL.getOkGoHttpUrl(),map)
                .subscribe(objectBaseMessage -> {
                    if(objectBaseMessage.isSuccess()){
                        //成功进行解析跳转
                        callBack.onSuccess(objectBaseMessage.data);
                    }else{
                        callBack.onSuccess(null);
                    }
                }, CustomError::log);
    }
}
