package com.zqhy.app.report;

import android.content.Context;

import com.box.other.blankj.utilcode.util.Logs;
import com.gism.sdk.GismConfig;
import com.gism.sdk.GismEventBuilder;
import com.gism.sdk.GismSDK;
import com.zqhy.app.App;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.core.pay.PayResultVo;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.utils.AppUtils;
import com.zqhy.app.newproject.BuildConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author leeham2734
 * @date 2021/3/25-10:28
 * @description
 */
public class HuiChuanDataReportAgency extends AbsReportAgency {

    private static final String TAG = HuiChuanDataReportAgency.class.getSimpleName();
    private static volatile HuiChuanDataReportAgency ourInstance;

    public static HuiChuanDataReportAgency getInstance() {
        if (ourInstance == null) {
            synchronized (HuiChuanDataReportAgency.class) {
                if (ourInstance == null) {
                    ourInstance = new HuiChuanDataReportAgency();
                }
            }
        }
        return ourInstance;
    }

    @Override
    public void init(Context mContext) {
        //神马统计
        if (AppConfig.isNeedShenMa()){
            GismSDK.init(GismConfig.newBuilder(App.instance()).appID(BuildConfig.SHENMA_APPID).appName("batuyouxi").appChannel(AppUtils.getTgid()).build());
            GismSDK.debug();
            log(TAG, "init");
        }
    }

    @Override
    public void startApp(Context context) {
        if (AppConfig.isNeedShenMa()){
            GismSDK.onLaunchApp();
            log(TAG, "startApp");
        }
    }

    @Override
    public void register(String client, String accountID, String username, String tgid) {
        if (AppConfig.isNeedShenMa()) {
            Map regMap = new HashMap();
            regMap.put("userid", accountID);
            regMap.put("username", username);
            GismSDK.onEvent(GismEventBuilder.onRegisterEvent().isRegisterSuccess(true).registerType("mobile").putKeyValue("userid", accountID).putKeyValue("username", username).build());
            Logs.e(TAG, "register : client = " + client + ",accountID = " + accountID + ",username = " + username + ",tgid = " + tgid);
        }
    }

    @Override
    protected void login(String client, String accountID, String username, String tgid) {
        if (AppConfig.isNeedShenMa()) {
            Map loginMap = new HashMap();
            loginMap.put("userid", accountID);
            loginMap.put("username", username);
            GismSDK.onEvent(GismEventBuilder.onCustomEvent().action("login").putKeyValue("userid", accountID).putKeyValue("username", username).build());
            Logs.e(TAG, "login : client = " + client + ",accountID = " + accountID + ",username = " + username + ",tgid = " + tgid);
        }
    }

    @Override
    public void purchase(String client, String pay_way, PayResultVo payResultVo, String uid, String tgid) {
        if (AppConfig.isNeedShenMa()) {
            Map successPayMap = new HashMap();
            String UserId = UserInfoModel.getInstance().isLogined() ? String.valueOf(UserInfoModel.getInstance().getUserInfo().getUid()) : null;
            successPayMap.put("userid", UserId);
            if (payResultVo != null) {
                successPayMap.put("orderid", payResultVo.getOut_trade_no());
                successPayMap.put("amount", payResultVo.getAmount());
            }
            GismSDK.onEvent(GismEventBuilder.onPayEvent().isPaySuccess(true).payAmount(Float.valueOf(payResultVo.getAmount())).putKeyValue("userid", UserId).putKeyValue("orderid", payResultVo.getOut_trade_no()).build());
            Logs.e(TAG, "__finish_payment : client = " + client + ",pay_way = " + pay_way + ",payResultVo = " + payResultVo + ",userid = " + UserId);
        }
    }

    @Override
    protected String getReportWay() {
        return "HuiChuan";
    }

}
