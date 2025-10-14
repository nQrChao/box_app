package com.tsdongyouxi.tsgame.douyinapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;

import com.bytedance.sdk.open.aweme.common.handler.IApiEventHandler;
import com.bytedance.sdk.open.aweme.common.model.BaseReq;
import com.bytedance.sdk.open.aweme.common.model.BaseResp;
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.ShareToContact;
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.share.DouYinShareEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author leeham2734
 * @date 2020/9/23-10:46
 * @description
 */
public class DouYinEntryActivity extends Activity implements IApiEventHandler {

    private static String TAG = DouYinEntryActivity.class.getName();

    DouYinOpenApi douYinOpenApi;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        douYinOpenApi = DouYinOpenApiFactory.create(this);
        douYinOpenApi.handleIntent(getIntent(), this);
    }

    @Override public void onReq(BaseReq req) {

    }


    @Override public void onResp(BaseResp resp) {
        Log.e(TAG,"error code:" + resp.errorCode + " error Msg:" + resp.errorMsg);
        if (resp instanceof ShareToContact.Response) {
            ShareToContact.Response response = (ShareToContact.Response) resp;
            EventBus.getDefault().post(new EventCenter(EventConfig.SHARE_DOUYIN_CALLBACK_EVENT_CODE,new DouYinShareEvent(response)));
            finish();
        }
    }

    @Override public void onErrorIntent(@Nullable Intent intent) {
        // 错误数据
        Log.e(TAG,"Intent出错");
    }
}
