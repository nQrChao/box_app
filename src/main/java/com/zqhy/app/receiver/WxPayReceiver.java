package com.zqhy.app.receiver;

import android.content.Context;
import android.content.Intent;

import com.zqhy.app.config.OnPayConfig;
import com.zqhy.app.core.pay.BaseWxPayReceiver;
import com.zqhy.app.core.pay.PayResultVo;
import com.zqhy.app.report.AllDataReportAgency;


/**
 * @author Administrator
 * @date 2018/10/29
 */

public class WxPayReceiver extends BaseWxPayReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if ("SUCCESS".equalsIgnoreCase(return_code)) {
            if (OnPayConfig.getPayType() == OnPayConfig.PAY_TYPE_WECHAT) {
                PayResultVo payResultVo = new PayResultVo(OnPayConfig.getOrderId(), OnPayConfig.getAmount());
                //2019.06.13 付费上报事件总览
                AllDataReportAgency.getInstance().purchase(OnPayConfig.PAY_TYPE_WECHAT, payResultVo);
            }
        }
    }
}
