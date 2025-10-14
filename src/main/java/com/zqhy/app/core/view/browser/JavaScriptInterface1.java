package com.zqhy.app.core.view.browser;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.hjq.toast.Toaster;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.smtt.sdk.WebView;
import com.zqhy.app.App;
import com.zqhy.app.config.OnPayConfig;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.h5pay.PayResultInfo;
import com.zqhy.app.core.data.model.user.PayInfoVo;
import com.zqhy.app.core.inner.AbsAliPayCallback;
import com.zqhy.app.core.pay.BaseWxPayReceiver;
import com.zqhy.app.core.pay.PayResultVo;
import com.zqhy.app.core.pay.alipay.AliPayCallBack;
import com.zqhy.app.core.pay.alipay.AliPayInstance;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.ui.eventbus.WxPayCallBack;
import com.zqhy.app.core.view.ReportTouTiaoDataActivity;
import com.zqhy.app.report.AllDataReportAgency;
import com.zqhy.app.utils.pay.WeChatPayInstance;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;


public class JavaScriptInterface1 {

    private Activity mActivity;
    private WebView mWebView;

    public JavaScriptInterface1(Activity c, WebView mWebView) {
        mActivity = c;
        this.mWebView = mWebView;
    }

    @JavascriptInterface
    public String getInterface() {
        return "webApi";
    }

    private String gameid;

    public void setGameid(String gameid) {
        this.gameid = gameid;
    }

    private boolean isVeCloud = false;

    public void setVeCloud(boolean veCloud) {
        isVeCloud = veCloud;
    }

    /**
     * 支付宝支付回调
     *
     * @param json
     */
    @JavascriptInterface
    public void aliPay(final String json) {
        Logs.e("aliPay");
        Logs.e("json：" + json);
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Gson gson = new Gson();
                    Type type = new TypeToken<PayInfoVo>() {
                    }.getType();
                    PayInfoVo payInfoVo = gson.fromJson(json, type);
                    if (payInfoVo.isStateOK() && payInfoVo.getData() != null) {
                        OnPayConfig.setPayConfig(payInfoVo.getData().getOut_trade_no(), payInfoVo.getData().getAmount(), OnPayConfig.PAY_TYPE_ALI);
                        doAliPay(payInfoVo.getData());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void doAliPay(PayInfoVo.DataBean dataBean) {
        PayResultVo payResultVo = new PayResultVo(dataBean.getOut_trade_no(), dataBean.getAmount());
        AliPayCallBack aliPayCallBack = new AbsAliPayCallback() {
            @Override
            public void onAliPaySuccess(PayResultVo payResultVo) {
                Toaster.show( "支付成功");
                //show dialog
                onH5PayBack(1, "支付宝充值成功", 1);
            }

            @Override
            public void onCancel() {
                Toaster.show( "支付取消");
                onH5PayBack(3, "支付宝支付取消", 1);
            }

            @Override
            public void onFailure(String resultStatus) {
                Logs.e("resultStatus:" + resultStatus);
                Toaster.show( "支付失败");
                onH5PayBack(2, "支付宝支付失败---" + resultStatus, 1);

            }
        };
        if ("jump".equals(dataBean.getAct())) {
            BrowserActivity.newInstance(mActivity, dataBean.getPay_url());
        } else if ("v1".equalsIgnoreCase(dataBean.getVersion())) {
            AliPayInstance.getInstance().pay(mActivity, payResultVo, dataBean.getPay_str(), aliPayCallBack);
        } else if ("v2".equalsIgnoreCase(dataBean.getVersion())) {
            AliPayInstance.getInstance().payV2(mActivity, payResultVo, dataBean.getPay_str(), aliPayCallBack);
        }
    }

    /**
     * 微信支付回调
     *
     * @param json
     */
    @JavascriptInterface
    public void wxPay(final String json) {
        Logs.e("wxPay");
        Logs.e("json:" + json);
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Gson gson = new Gson();
                    Type type = new TypeToken<PayInfoVo>() {
                    }.getType();
                    PayInfoVo payInfoVo = gson.fromJson(json, type);
                    if (payInfoVo.isStateOK() && payInfoVo.getData() != null) {
                        if ("jump".equals(payInfoVo.getData().getAct())) {
                            BrowserActivity.newInstance(mActivity, payInfoVo.getData().getPay_url());
                        } else {
                            OnPayConfig.setPayConfig(payInfoVo.getData().getOut_trade_no(), payInfoVo.getData().getAmount(), OnPayConfig.PAY_TYPE_WECHAT);
                            doWechatPay(payInfoVo.getData());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * app客户端加一个函数h5Goback()，不带参数，作用是回到app。
     */
    @JavascriptInterface
    public void h5Goback() {
        Logs.e("BrowserCloudPayActivity h5Goback");
        if (mActivity != null)
            mActivity.finish();
    }

    /**
     * 回到游戏
     */
    @JavascriptInterface
    public void goBackGame() {
        Logs.e("BrowserCloudPayActivity goBackGame");
        if (mActivity != null)
            mActivity.finish();
    }

    /**
     * 关闭页面
     */
    @JavascriptInterface
    public void closeMe() {
        Logs.e("BrowserCloudPayActivity closeMe");
        if (mActivity != null)
            mActivity.finish();
    }

    private void doWechatPay(PayInfoVo.DataBean dataBean) {
        WeChatPayInstance.getInstance().startPay(mActivity, dataBean);
    }

    public void onWechatPayBack(WxPayCallBack wxPayCallBack) {
        if ("SUCCESS".equalsIgnoreCase(wxPayCallBack.getReturn_code())) {
            Toaster.show( "支付成功");
            //show dialog
            onH5PayBack(1, wxPayCallBack.getReturn_code(), 3);
        } else if ("FAIL".equalsIgnoreCase(wxPayCallBack.getReturn_code())) {
            Logs.e("resultStatus:" + wxPayCallBack.getReturn_msg());
            Toaster.show( "支付失败");
            onH5PayBack(2, wxPayCallBack.getReturn_msg(), 3);
        } else if ("CANCEL".equalsIgnoreCase(wxPayCallBack.getReturn_code())) {
            Toaster.show( "支付取消");
            onH5PayBack(3, wxPayCallBack.getReturn_code(), 3);
        }
    }


    /**
     * H5支付成功回调给js
     *
     * @param status 1成功 2失败 3取消
     * @param msg    消息
     * @param type   1 支付宝  2 微信(safepay)  3微信（btgame）
     */
    private void onH5PayBack(int status, String msg, int type) {
        PayResultInfo payResultInfo = new PayResultInfo(status, msg, type);
        Gson gson = new Gson();
        String json = gson.toJson(payResultInfo);
        Logs.e("payBackToH5:json = " + json);
        if (isVeCloud) {
            //payBackToH5:json = {"msg":"SUCCESS","status":1,"type":3}
            //payBackToH5:json = {"msg":"支付宝充值成功","status":1,"type":1}
            //VeGameEngine.getInstance().getMessageChannel().sendMessage(json, false);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(json);
                int state = jsonObject.optInt("status",0);
                if (mActivity != null&& state == 1) {
                    mActivity.finish();
                }
            } catch (Exception e) {
                Logs.e("payBackToH5 Exception " + e);
            }
            return;
        }
        if (mWebView != null) {
            //调用js中的函数：backtogame()
            mWebView.loadUrl("javascript:backtogame('" + json + "')");
        }
    }

    /**
     * @param type 1 成功   0 取消   -1 失败
     */
    public void onShareResultToJs(String type) {
        Logs.e("onShareResultToJs type = " + type);
        if (mWebView != null) {
            //调用js中的函数：onShareResult()
            mWebView.loadUrl("javascript:onShareResult('" + type + "')");
        }
    }

    /**
     * 网页条App内页统一方法
     *
     * @param json
     */
    @JavascriptInterface
    public void JumpAppAction(String json) {
        Logs.e("AppJumpAction Json = " + json);
        mActivity.runOnUiThread(() -> {
            AppJumpAction(json);
        });
    }

    private void AppJumpAction(String json) {
        new AppJumpAction(mActivity).jumpAction(json);
    }

    /**
     * sdk 数据过度 app
     * 2021.05.18
     *
     * @param json
     */
    @JavascriptInterface
    public void appDataTransfer(final String json) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //todo 微信支付回调
                String sdk_tag = "app_" + App.getContext().getPackageName() + "_appDataTransfer";
                ReportTouTiaoDataActivity.postData(sdk_tag, json);
            }
        });
    }


    /***************************************************************************************************************************/

    @JavascriptInterface
    public void wxH5PayBack(String infoJson) {
        Logs.e("wxH5PayBack");
        Logs.e("infoJson:" + infoJson);
        try {
            JSONObject object = new JSONObject(infoJson);
            String out_trade_no = object.optString("out_trade_no");
            String amount = object.optString("amount");
            String return_code = object.optString("return_code");
            String return_msg = object.optString("return_msg");
            handlerWxPayBack(mActivity, out_trade_no, amount, return_code, return_msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handlerWxPayBack(Context mContext, String out_trade_no, String amount, String return_code, String return_msg) {
        Logs.e("out_trade_no:" + out_trade_no + "\n" + "return_code:" + return_code + "\n" + "return_msg:" + return_msg + "\n");

        WxPayCallBack wxPayCallBack = new WxPayCallBack(out_trade_no, amount, return_code, return_msg);
        EventBus.getDefault().post(new EventCenter(BaseWxPayReceiver.WXPAY_EVENT_CODE, wxPayCallBack));

        if ("SUCCESS".equalsIgnoreCase(return_code)) {
            if (OnPayConfig.getPayType() == OnPayConfig.PAY_TYPE_WECHAT) {
                String target_out_trade_no = TextUtils.isEmpty(out_trade_no) ? OnPayConfig.getOrderId() : out_trade_no;
                String target_amount = TextUtils.isEmpty(amount) ? OnPayConfig.getAmount() : amount;

                PayResultVo payResultVo = new PayResultVo(target_out_trade_no, target_amount);
                //2019.06.13 付费上报事件总览
                AllDataReportAgency.getInstance().purchase(OnPayConfig.PAY_TYPE_WECHAT, payResultVo);
            }
        }
    }


    @JavascriptInterface
    public void payCallBack(final String json) {
        Logs.e("payCallback");
        Logs.e("json：" + json);
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = null;
                    jsonObject = new JSONObject(json);
//                        String message = jsonObject.optString("msg");
                    String state = jsonObject.optString("state");
                    /*if ("ok".equals(state)) {// 支付成功
                        if (isVeCloud) {
                            VeGameEngine.getInstance().getMessageChannel().sendMessage(json, false);
                        }
                    } else if ("cancel".equals(state)) {
                        if (isVeCloud) {
                            VeGameEngine.getInstance().getMessageChannel().sendMessage(json, false);
                        }
                    } else {
                        if (isVeCloud) {
                            VeGameEngine.getInstance().getMessageChannel().sendMessage(json, false);
                        }
                    }*/
                } catch (JSONException e) {
                    e.printStackTrace();

                }

                //                exitH5Activity(json, H5WebActivity.resultCode_pay);
            }
        });
    }
}

