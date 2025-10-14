package com.zqhy.app.report;

import android.content.Context;
import android.util.Log;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.umeng.analytics.MobclickAgent;
import com.zqhy.app.App;
import com.zqhy.app.core.pay.PayResultVo;
import com.zqhy.app.model.UserInfoModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author leeham2734
 * @date 2021/3/25-10:28
 * @description
 */
class UmDataReportAgency extends AbsReportAgency {

    private static final    String             TAG = UmDataReportAgency.class.getSimpleName();
    private static volatile UmDataReportAgency ourInstance;

    public static UmDataReportAgency getInstance() {
        if (ourInstance == null) {
            synchronized (UmDataReportAgency.class) {
                if (ourInstance == null) {
                    ourInstance = new UmDataReportAgency();
                }
            }
        }
        return ourInstance;
    }

    @Override
    public void init(Context mContext) {

    }

    @Override
    protected void startApp(Context context) {
    }

    @Override
    protected void register(String client, String accountID, String username, String tgid) {
        Map regMap = new HashMap();
        regMap.put("userid", accountID);
        regMap.put("username", username);
        MobclickAgent.onEvent(App.getContext(), "__register", regMap);
        Logs.e(TAG, "register : client = " + client + ",accountID = " + accountID + ",username = " + username + ",tgid = " + tgid);
    }

    @Override
    protected void login(String client, String accountID, String username, String tgid) {
        Map loginMap = new HashMap();
        loginMap.put("userid", accountID);
        loginMap.put("username", username);
        MobclickAgent.onEvent(App.getContext(), "__login", loginMap);
        Logs.e(TAG, "login : client = " + client + ",accountID = " + accountID + ",username = " + username + ",tgid = " + tgid);
    }

    @Override
    protected void purchase(String client, String pay_way, PayResultVo payResultVo, String uid, String tgid) {
        Map successPayMap = new HashMap();
        String UserId = UserInfoModel.getInstance().isLogined() ? String.valueOf(UserInfoModel.getInstance().getUserInfo().getUid()) : null;
        successPayMap.put("userid", UserId);
        if (payResultVo != null) {
            successPayMap.put("orderid", payResultVo.getOut_trade_no());
            successPayMap.put("amount", payResultVo.getAmount());
        }
        MobclickAgent.onEvent(App.getContext(), "__finish_payment", successPayMap);
        Logs.e(TAG, "__finish_payment : client = " + client + ",pay_way = " + pay_way + ",payResultVo = " + payResultVo + ",userid = " + UserId);
    }

    @Override
    protected String getReportWay() {
        return "Umeng";
    }
}
