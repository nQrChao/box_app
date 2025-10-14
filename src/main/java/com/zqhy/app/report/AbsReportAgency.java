package com.zqhy.app.report;

import android.content.Context;

import com.box.common.sdk.ImSDK;
import com.box.other.blankj.utilcode.util.Logs;
import com.google.gson.Gson;
import com.zqhy.app.App;
import com.zqhy.app.Setting;
import com.zqhy.app.core.data.model.ReportAdData;
import com.zqhy.app.core.data.model.ReportRegisterData;
import com.zqhy.app.core.pay.PayResultVo;
import com.zqhy.app.network.utils.AppUtils;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.utils.JiuYaoDeviceUtils;

/**
 * @author Administrator
 */
public abstract class AbsReportAgency {

    protected boolean isReportChannel = BuildConfig.DEBUG;

    protected boolean isFinishInit = false;

    public static final int LIMIT_AMOUNT = 0;

    /**
     * 初始化SDK
     */
    public abstract void init(Context mContext);

    /**
     * 上报App启动（激活）
     */
    protected abstract void startApp(Context context);

    /**
     * 上报注册事件
     *
     * @param accountID 用户ID
     * @param username  用户名
     */
    protected abstract void register(String client, String accountID, String username, String tgid);

    /**
     * 上报登录事件
     *
     * @param accountID
     * @param username
     */
    protected abstract void login(String client, String accountID, String username, String tgid);

    /**
     * 上报付费事件
     *
     * @param pay_way     付费类型
     * @param payResultVo 付费信息
     */
    protected abstract void purchase(String client, String pay_way, PayResultVo payResultVo, String uid, String tgid);

    protected abstract String getReportWay();

    protected void setPrivacyStatus(boolean isAllowed) {

    }

    protected void log(String tag, String message) {
        if (BuildConfig.DEBUG || Setting.IS_DEBUGING) {
            Logs.e(tag, message);
        }
    }

    /**
     * 注册
     * <p>
     * 操作日志
     * 2021.06.30 表新增 user_tgid device_id 字段
     *
     * @param uid  用户名id
     * @param tgid
     */
    protected void sendReportRegisterData(String client, String uid, String tgid) {
        try {
            ReportRegisterData registerData = new ReportRegisterData();
            registerData.deviceinfo = ReportRegisterData.getDeviceInfo() + "-tgid-" + tgid;
            registerData.packageinfo = BuildConfig.APPLICATION_ID + "-" + getReportWay() + "-" + AppUtils.getChannelFromApk();
            registerData.username = uid;
            registerData.user_tgid = tgid;
            registerData.device_id = JiuYaoDeviceUtils.getUniqueId(App.getContext());
            registerData.client = client;
            registerData.oaid = ImSDK.appViewModelInstance.getOaid();
            ReportLog.postRegister(new Gson().toJson(registerData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 付费
     * <p>
     * 操作日志
     * 2021.06.30 表新增uid device_id字段
     *
     * @param out_trade_no
     * @param payWay
     * @param amount
     */
    protected void sendReportAdData(String client, String out_trade_no, String payWay, float amount, String uid, String tgid) {
        if (amount >= LIMIT_AMOUNT) {
            try {
                ReportAdData adData = new ReportAdData();
                adData.gid = tgid;
                adData.money = String.valueOf(amount);
                adData.payWay = payWay;
                adData.type = BuildConfig.APPLICATION_ID + "-" + getReportWay() + "-";
                //                adData.userName = DeviceUtils.getIMEI_1(App.instance());
                adData.uid = uid;
                adData.device_id = JiuYaoDeviceUtils.getUniqueId(App.getContext());
                adData.out_trade_no = out_trade_no;
                adData.client = client;
                adData.oaid = ImSDK.appViewModelInstance.getOaid();
                ReportLog.postData(new Gson().toJson(adData));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
