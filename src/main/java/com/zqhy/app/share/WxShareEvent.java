package com.zqhy.app.share;


import com.tencent.mm.sdk.modelbase.BaseResp;

/**
 *
 * @author Administrator
 * @date 2018/11/5
 */

public class WxShareEvent {

    private BaseResp baseResp;
    public WxShareEvent(BaseResp resp) {
        baseResp = resp;
    }


    public BaseResp getBaseResp() {
        return baseResp;
    }
}
