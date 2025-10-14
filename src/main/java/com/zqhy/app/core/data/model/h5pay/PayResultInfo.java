package com.zqhy.app.core.data.model.h5pay;

/**
 * @author Administrator
 * @date 2018/12/1
 */

public class PayResultInfo {
    int status;
    String msg;
    int type;

    public PayResultInfo(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public PayResultInfo(int status, String msg, int type) {
        this.status = status;
        this.msg = msg;
        this.type = type;
    }
}
