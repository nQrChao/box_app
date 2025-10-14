package com.zqhy.app.core.data.model.sdk;

import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;

/**
 * @author Administrator
 * @date 2018/11/26
 */

public class SdkAction {

    /***sdk跳转游戏详情（礼包）**********/
    public static final String ACTION_SDK_JUMP_GAME_DETAIL             = "action_sdk_jump_game_detail";
    /***sdk跳转客服页面**********/
    public static final String ACTION_SDK_JUMP_CUSTOMER_SERVICE_CENTER = "action_sdk_jump_customer_service_center";
    /***sdk跳转返利申请页面**********/
    public static final String ACTION_SDK_JUMP_REBATES_CENTER          = "action_sdk_jump_rebates_center";
    /***sdk跳转金币充值页面**********/
    public static final String ACTION_SDK_JUMP_PTB_RECHARGE            = "action_sdk_jump_pay_gold";

    /**
     * sdk跳转App通用 ->对接AppJumpAction
     */
    public static final String ACTION_SDK_JUMP_COMMON = "action_sdk_jump_common";


    private int    uid;
    private String username;
    private String token;
    private String action;

    private Params param;
    private String page_type;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Params getParam() {
        return param;
    }

    public void setParams(Params param) {
        this.param = param;
    }

    public String getPage_type() {
        return page_type;
    }

    public void setPage_type(String page_type) {
        this.page_type = page_type;
    }

    public class Params extends AppBaseJumpInfoBean.ParamBean {

    }

}
