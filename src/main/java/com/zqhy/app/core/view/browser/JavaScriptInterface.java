package com.zqhy.app.core.view.browser;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;


import com.box.common.IMUtilsKt;
import com.box.common.sdk.ImSDK;
import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.cnoaid.oaid.DeviceIdentifier;
import com.box.other.hjq.toast.Toaster;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.smtt.sdk.WebView;
import com.zqhy.app.App;
import com.zqhy.app.Setting;
import com.zqhy.app.config.OnPayConfig;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.h5pay.PayResultInfo;
import com.zqhy.app.core.data.model.user.PayInfoVo;
import com.zqhy.app.core.inner.AbsAliPayCallback;
import com.zqhy.app.core.pay.BaseWxPayReceiver;
import com.zqhy.app.core.pay.PayResultVo;
import com.zqhy.app.core.pay.alipay.AliPayCallBack;
import com.zqhy.app.core.pay.alipay.AliPayInstance;
import com.zqhy.app.core.tool.utilcode.DeviceUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.ui.eventbus.WxPayCallBack;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.ReportTouTiaoDataActivity;
import com.zqhy.app.core.view.kefu.KefuCenterFragment;
import com.zqhy.app.newproject.R;
import com.zqhy.app.report.AllDataReportAgency;
import com.zqhy.app.utils.JiuYaoDeviceUtils;
import com.zqhy.app.utils.pay.WeChatPayInstance;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/14
 */

public class JavaScriptInterface {

    private Activity mActivity;
    private WebView  mWebView;

    public JavaScriptInterface(Activity c, WebView mWebView) {
        mActivity = c;
        this.mWebView = mWebView;
    }

    @JavascriptInterface
    public String getInterface() {
        return "sdkcall";
    }

    private boolean isVeCloud = false;

    public void setVeCloud(boolean veCloud) {
        isVeCloud = veCloud;
    }
    private String gameid;

    public void setGameid(String gameid) {
        this.gameid = gameid;
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
            BrowserGameActivity.newInstance(mActivity, dataBean.getPay_url());
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
                            BrowserGameActivity.newInstance(mActivity, payInfoVo.getData().getPay_url());
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
     * 关闭页面
     */
    @JavascriptInterface
    public void closeMe() {
        Logs.e("closeMe");
        if (mActivity != null)
            mActivity.finish();
    }


    /**
     * app客户端加一个函数h5Goback()，不带参数，作用是回到app。
     */
    @JavascriptInterface
    public void h5Goback() {
        Logs.e("h5Goback");
        mActivity.finish();
    }

    /**
     * 回到游戏
     */
    @JavascriptInterface
    public void goBackGame() {
        Logs.e("goBackGame");
        if (mActivity != null){
            mActivity.runOnUiThread(() -> {
                if (mWebView != null) {
                    //调用js中的函数：backfatherpage()
                    mWebView.loadUrl("javascript:backfatherpage()");
                }
                mActivity.finish();
            });
        }
    }

    /**
     * 开启新页面
     */
    @JavascriptInterface
    public void startNewWeb(String url) {
        Logs.e("startNewWeb");
        if (mActivity != null){
            mActivity.runOnUiThread(() -> {
                BrowserCloudPayActivity.newInstance(mActivity, url, true);
            });
        }
    }

    /**
     * 回到上一个页面
     */
    @JavascriptInterface
    public void actionBack() {
        Logs.e("actionBack");
        if (mActivity != null){
            mActivity.runOnUiThread(() -> {
                if (mWebView != null && mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    if (mWebView != null) {
                        //调用js中的函数：backfatherpage()
                        mWebView.loadUrl("javascript:backfatherpage()");
                    }
                    mActivity.finish();
                }
            });
        }
    }

    @JavascriptInterface
    public void showCloseTips(){
        Logs.e("showCloseTips");
        if (mActivity != null) {
            mActivity.runOnUiThread(() -> {
                //confirmExitH5Game();
                if (mWebView != null) {
                    //调用js中的函数：backfatherpage()
                    mWebView.loadUrl("javascript:backfatherpage()");
                }
                mActivity.finish();
            });
        }
    }

    private void confirmExitH5Game(){
        Logs.e("confirmExitH5Game");
        CustomDialog tipsDialog = new CustomDialog(mActivity, LayoutInflater.from(mActivity).inflate(R.layout.layout_dialog_cloud_exit, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            if (mWebView != null) {
                //调用js中的函数：backfatherpage()
                mWebView.loadUrl("javascript:backfatherpage()");
            }
            mActivity.finish();
        });
        tipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.show();
    }

    @JavascriptInterface
    public void toKefu(){

        if (mActivity != null) {
            mActivity.runOnUiThread(() -> {
                FragmentHolderActivity.startFragmentInActivity(mActivity, new KefuCenterFragment(), true);
                //BrowserCloudPayActivity.newInstance(mActivity, "https://sdkapi-ns1.tsyule.cn/index.php/Msdk/pay/?data=ajBqbGl0d3F0YWtOYlJsZ2piVmVFZS9MbUowbmxseCtGZXE2di9Qb2tZb2tBUW90d1lJMytYRGN3U2c5MDIwYXhzc1hNVnRtcnUvbFBaemR6Q2gzb1ozTG0vRENhRm5obDFud2dwRWlKRW1JVjBTZDJ2OWZTWjJoaGtZNU0wZXFoUStrTGNDOHlBVE9JbUxzdzNkYjBTeDdIU00wd2VWQlN4RTU1ZWJYUWtJMldPdDdFdFltWlhtZGVER05RZndlRStZak5CeTZLUHJNMXExMDE5eTc1V1FHWVFBZ2pzV0tXbUl0NnJRRHJOa3hmeTA1T2hKYXZpRUx1dHZIRFJETkhBZmNlYTByL2VHdHJOQ29FVnpYa1RTQVRudVN6RkdvTkh5V3ZBd0ljSThJYzRZWitlSkIxWERvWXhnRVkzWW5PZmhlK1ZPVE4zdEZ5aVd2bHd2NnJYc0Yzd2J4RGVYdW1Pd2NueFl0WHptZlZGcXlzUmhNT05NTWI3cEVWSHpwU3pFOGxxcFNycUhUazBUdllMVndDSFovV3dvNlpyeHJZWnN5Ly9lS3hwNENqemVLbWhOaVNiR1gzd3J3UlI2TTVLUEltT1ZOWjJtVGRiNVc1MGxuZUZpai8rMTArelFk&versionCode=30000&yunguaji=1", true);
            });
        }
    }

    private void doWechatPay(PayInfoVo.DataBean dataBean) {
        Logs.e("doWechatPay");
        WeChatPayInstance.getInstance().startPay(mActivity, dataBean);
    }

    public void onWechatPayBack(WxPayCallBack wxPayCallBack) {
        Logs.e("onWechatPayBack");
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
    public String getDeviceInfo() {
        return createDeviceInfo(mActivity);
    }

    private String createDeviceInfo(Context mContext) {
        Map<String, String> params = new TreeMap<>();

        params.put("client_type", "1");

        if (Setting.MAC_ADD != null) {
            params.put("mac",  Setting.MAC_ADD);
        }else {
            Setting.MAC_ADD = DeviceUtils.getMacAddress(mContext);
            params.put("mac", Setting.MAC_ADD);
        }

        if (Setting.IMEI != null) {
            params.put("imei",  Setting.IMEI);
        }else {
            Setting.IMEI = JiuYaoDeviceUtils.getDeviceIMEI(mContext);
            params.put("imei", Setting.IMEI);
        }

        if (Setting.ANDROID_ID != null) {
            params.put("androidid",  Setting.ANDROID_ID);
        }else {
            Setting.ANDROID_ID = DeviceUtils.getAndroidID(mContext);
            params.put("androidid", Setting.ANDROID_ID);
        }

        if (Setting.UUID != null) {
            params.put("uuid", Setting.UUID);
        }else {
            Setting.UUID = DeviceUtils.getUniqueId(mContext);
            params.put("uuid", Setting.UUID);
        }

        params.put("oaid", ImSDK.appViewModelInstance.getOaid());

        String deviceId = "";
        if (!TextUtils.isEmpty(params.get("uuid")) && !"unknown".equals(params.get("uuid"))) {
            if (Setting.UNIQUE_ID != null) {
                deviceId  = Setting.UNIQUE_ID;
            }else {
                Setting.UNIQUE_ID = JiuYaoDeviceUtils.getUniqueId(mContext);
                deviceId  = Setting.UNIQUE_ID;
            }
        } else if (!TextUtils.isEmpty(params.get("imei")) && !"unknown".equals(params.get("imei"))) {
            if (Setting.DEVICE_IMEI != null) {
                deviceId  = Setting.DEVICE_IMEI;
            }else {
                Setting.DEVICE_IMEI = JiuYaoDeviceUtils.getDeviceIMEI(mContext);
                deviceId  = Setting.DEVICE_IMEI;
            }
        } else if (!TextUtils.isEmpty(params.get("mac")) && !"unknown".equals(params.get("mac"))) {
            if (Setting.MAC_ADD != null) {
                deviceId  = Setting.MAC_ADD;
            }else {
                Setting.MAC_ADD = DeviceUtils.getMacAddress(mContext);
                deviceId  = Setting.MAC_ADD;
            }
        }
        params.put("device_id", deviceId);

        if (Setting.DEVICE_SIGN != null) {
            params.put("device_id_2", Setting.DEVICE_SIGN);
        }else {
            Setting.DEVICE_SIGN = JiuYaoDeviceUtils.getDeviceSign(mContext);
            params.put("device_id_2", Setting.DEVICE_SIGN);
        }
        params.put("guid", DeviceIdentifier.getGUID(App.instance()));
        params.put("canvas", DeviceIdentifier.getCanvasFingerprint() );
        params.put("ts_device_version", String.valueOf(Build.VERSION.RELEASE));
        params.put("ts_device_version_code", String.valueOf(Build.VERSION.SDK_INT));
        params.put("ts_device_brand", String.valueOf(Build.BRAND));
        params.put("ts_device_model", String.valueOf(Build.MODEL));

        return map2Json(params);
    }

    private String map2Json(Map<String, String> map) {
        String json = "";
        json += "{";
        for (String key : map.keySet()) {
            String value = map.get(key);
            json += "\"" + key + "\"" + ":" + "\"" + value + "\"" + ",";
        }
        json = json.substring(0, json.length() - 1);
        json += "}";
        return json;
    }

    @JavascriptInterface
    public void sendAdActive() {
        if (ImSDK.appViewModelInstance.getOaid() != null) {
            return;
        }
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                IMUtilsKt.getOAIDWithRetry(
                        mActivity,
                        oaid -> {
                            Setting.OAID = ImSDK.appViewModelInstance.getOaid();
                            return kotlin.Unit.INSTANCE;
                        }
                );
            }
        });
    }
}

