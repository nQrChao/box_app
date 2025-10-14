package com.zqhy.app.core.data.model;

/**
 *
 * @author Administrator
 * @date 2018/11/6
 */

public class BaseResponseVo<T> extends BaseVo{

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
