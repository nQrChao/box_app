package com.zqhy.app.core.view;

import android.os.Bundle;

import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.hjq.toast.Toaster;
import com.mvvm.base.AbsViewModel;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.OnPayConfig;
import com.zqhy.app.core.data.model.user.PayInfoVo;
import com.zqhy.app.core.inner.AbsAliPayCallback;
import com.zqhy.app.core.pay.PayResultVo;
import com.zqhy.app.core.pay.alipay.AliPayCallBack;
import com.zqhy.app.core.pay.alipay.AliPayInstance;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.ui.eventbus.WxPayCallBack;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.receiver.WxPayReceiver;
import com.zqhy.app.utils.pay.WeChatPayInstance;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author leeham
 * @date 2020/3/30-13:33
 * @description
 */
public abstract class AbsPayBuyFragment<T extends AbsViewModel> extends BaseFragment<T> {

    protected int PAY_TYPE = 0;

    protected final static int PAY_TYPE_ALIPAY = 1;
    protected final static int PAY_TYPE_WECHAT = 2;


    protected final static int PAY_ACTION_TOP_UP      = 1;
    protected final static int PAY_ACTION_TRANSACTION = 2;
    protected final static int PAY_ACTION_VIP_MEMBER  = 3;

    protected int PAY_ACTION = 0;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        PAY_ACTION = getPayAction();
    }


    /**
     * 付费分类
     */
    protected abstract int getPayAction();

    protected void doPay(PayInfoVo.DataBean dataBean) {
        String amount = dataBean.getAmount();
        doPay(dataBean, amount);
    }

    protected void doPay(PayInfoVo.DataBean dataBean, float amount) {
        doPay(dataBean, String.valueOf(amount));
    }

    protected void doPay(PayInfoVo.DataBean dataBean, String amount) {
        if (dataBean != null) {
            String payType = null;
            if (PAY_TYPE == PAY_TYPE_ALIPAY) {
                payType = OnPayConfig.PAY_TYPE_ALI;
                PayResultVo payResultVo = new PayResultVo(dataBean.getOut_trade_no(), amount);
                AliPayCallBack aliPayCallBack = new AbsAliPayCallback() {
                    @Override
                    public void onAliPaySuccess(PayResultVo payResultVo) {
                        //Toaster.show( "支付成功");
                        Toaster.show("支付成功");
                        //show dialog
                        onPaySuccess();
                    }

                    @Override
                    public void onCancel() {
                        //ToastT.normal(_mActivity, "支付取消");
                        Toaster.show("支付取消");
                        onPayCancel();
                    }

                    @Override
                    public void onFailure(String resultStatus) {
                        Logs.e("resultStatus:" + resultStatus);
                        //ToastT.normal(_mActivity, "支付失败");
                        Toaster.show("支付失败");
                        onPayFailure(resultStatus);
                    }
                };
                if ("jump".equals(dataBean.getAct())) {
                    BrowserActivity.newInstance(_mActivity, dataBean.getPay_url());
                } else if ("v1".equalsIgnoreCase(dataBean.getVersion())) {
                    AliPayInstance.getInstance().pay(_mActivity, payResultVo, dataBean.getPay_str(), aliPayCallBack);
                } else if ("v2".equalsIgnoreCase(dataBean.getVersion())) {
                    AliPayInstance.getInstance().payV2(_mActivity, payResultVo, dataBean.getPay_str(), aliPayCallBack);
                }
            } else if (PAY_TYPE == PAY_TYPE_WECHAT) {
                payType = OnPayConfig.PAY_TYPE_WECHAT;
                if ("jump".equals(dataBean.getAct())) {
                    BrowserActivity.newInstance(_mActivity, dataBean.getPay_url());
                } else {
                    WeChatPayInstance.getInstance().startPay(_mActivity, dataBean);
                }
            }
            try {
                OnPayConfig.setPayConfig(dataBean.getOut_trade_no(), amount, payType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventCenter eventCenter) {
        super.onEvent(eventCenter);
        if (eventCenter.getEventCode() == WxPayReceiver.WXPAY_EVENT_CODE) {
            WxPayCallBack wxPayCallBack = (WxPayCallBack) eventCenter.getData();
            if ("SUCCESS".equalsIgnoreCase(wxPayCallBack.getReturn_code())) {
                //Toaster.show( "支付成功");
                Toaster.show("支付成功");
                onPaySuccess();
            } else if ("FAIL".equalsIgnoreCase(wxPayCallBack.getReturn_code())) {
                Logs.e("resultStatus:" + wxPayCallBack.getReturn_msg());
                //ToastT.normal(_mActivity, "支付失败");
                Toaster.show("支付失败");
                onPayFailure(wxPayCallBack.getReturn_msg());
            } else if ("CANCEL".equalsIgnoreCase(wxPayCallBack.getReturn_code())) {
                //ToastT.normal(_mActivity, "支付取消");
                Toaster.show("支付取消");
                onPayCancel();
            }
        }
    }

    protected void onPaySuccess() {
    }

    protected void onPayCancel() {

    }

    protected void onPayFailure(String resultStatus) {

    }
}
