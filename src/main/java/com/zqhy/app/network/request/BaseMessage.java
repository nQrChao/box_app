package com.zqhy.app.network.request;

import com.google.gson.annotations.SerializedName;

/**
 * 请求基础实体
 * @author 韩国桐
 * @version [0.1,2019-12-25]
 * @see [泛型]
 * @since [Bean]
 */
public class BaseMessage<T> {
    public static final String SUCCESS_FLAG="ok";
    public static final String NO_LOGIN="no_login";
    /** 返回结果码 **/
    public String state;
    /** 返回信息 **/
    @SerializedName(value = "msg",alternate = "message")
    public String message;
    /** 返回结果 **/
    public T data;

    /** 请求成功与否 **/
    public boolean isSuccess(){
        return SUCCESS_FLAG.equals(state);
    }

    public boolean noLogin(){
        return NO_LOGIN.equals(state);
    }
}