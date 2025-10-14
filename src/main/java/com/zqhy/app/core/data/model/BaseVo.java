package com.zqhy.app.core.data.model;

import java.io.Serializable;

/**
 *
 * @author Administrator
 * @date 2017/4/9
 */

public class BaseVo implements Serializable {
    private String msg;
    private String state;

    public BaseVo() {
    }

    public BaseVo(String state, String msg) {
        this.msg = msg;
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isStateOK() {
        return "ok".equals(state);
    }

    public boolean isStateJump() {
        return "jump".equals(state);
    }

    public boolean isStateTip() {
        return "tip".equals(state);
    }

    public boolean isNoLogin() {
        return "no_login".equals(state);
    }

    @Override
    public String toString() {
        return "BaseVo{" +
                "msg='" + msg + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
