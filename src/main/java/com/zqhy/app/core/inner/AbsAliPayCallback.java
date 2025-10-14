package com.zqhy.app.core.inner;

import com.zqhy.app.config.OnPayConfig;
import com.zqhy.app.core.pay.PayResultVo;
import com.zqhy.app.core.pay.alipay.AliPayCallBack;
import com.zqhy.app.report.AllDataReportAgency;

/**
 * @author Administrator
 */
public abstract class AbsAliPayCallback implements AliPayCallBack {

    @Override
    public void onSuccess(PayResultVo payResultVo) {
        onAliPaySuccess(payResultVo);
        //2019.06.13 付费上报事件总览
        AllDataReportAgency.getInstance().purchase(OnPayConfig.PAY_TYPE_ALI, payResultVo);

    }

    @Override
    public void onWait() {

    }

    /**
     * 支付宝支付成功
     */
    public abstract void onAliPaySuccess(PayResultVo payResultVo);
}
