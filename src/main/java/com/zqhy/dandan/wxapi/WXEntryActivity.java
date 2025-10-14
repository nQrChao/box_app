package com.zqhy.dandan.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.box.other.blankj.utilcode.util.Logs;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.share.WxShareEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Administrator
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = WXEntryActivity.class.getSimpleName();
    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        api = WXAPIFactory.createWXAPI(this, "");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initData();
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Logs.e(TAG, "BaseResp =" + resp);
        EventBus.getDefault().post(new EventCenter(EventConfig.SHARE_WECHAT_CALLBACK_EVENT_CODE,new WxShareEvent(resp)));
        finish();
    }
}
