package com.zqhy.app.report;

import android.content.Context;
import android.text.TextUtils;

import com.kwai.monitor.log.TurboAgent;
import com.kwai.monitor.log.TurboConfig;
import com.zqhy.app.config.OnPayConfig;
import com.zqhy.app.core.pay.PayResultVo;
import com.zqhy.app.network.utils.AppUtils;
import com.zqhy.app.newproject.BuildConfig;

/**
 * @author leeham2734
 * @date 2021/8/25-14:56
 * @description
 */
public class KsDataReportAgency extends AbsReportAgency {

    private static final String TAG = "KsDataReportAgency";

    private static volatile KsDataReportAgency ourInstance;

    private KsDataReportAgency() {
        isReportChannel = true;
        isReportChannel = isReportChannel && hasIdKey();
    }

    private boolean hasIdKey() {
        return !TextUtils.isEmpty(APPID) && !TextUtils.isEmpty(APPNAME);
    }

    public static KsDataReportAgency getInstance() {
        if (ourInstance == null) {
            synchronized (KsDataReportAgency.class) {
                if (ourInstance == null) {
                    ourInstance = new KsDataReportAgency();
                }
            }
        }
        return ourInstance;
    }

    private static final String APPID = BuildConfig.KUAISHOU_APP_ID;

    private static final String APPNAME = BuildConfig.KUAISHOU_APP_NAME;

    @Override
    public void init(Context mContext) {
        if (isReportChannel) {
            String channel = AppUtils.getTgid();
            TurboAgent.init(TurboConfig.TurboConfigBuilder.create(mContext)
                    .setAppId(APPID)
                    .setAppName(APPNAME)
                    .setAppChannel(channel)
                    .setEnableDebug(BuildConfig.DEBUG)
                    .build());
            isFinishInit = true;
            log(TAG, "init success");
        }
    }

    @Override
    protected void startApp(Context context) {
        if (isReportChannel && isFinishInit) {
            TurboAgent.onAppActive();
            log(TAG, "startApp");
        }
    }

    @Override
    public void register(String client, String accountID, String username, String tgid) {
        if (isReportChannel && isFinishInit) {
            TurboAgent.onRegister();
            sendReportRegisterData(client, accountID, tgid);
            log(TAG, "register\n accountID = " + accountID);
        }
    }

    @Override
    protected void login(String client, String accountID, String username, String tgid) {

    }

    @Override
    public void purchase(String client, String pay_way, PayResultVo payResultVo, String uid, String tgid) {
        if (isReportChannel && isFinishInit) {
            float amount = payResultVo.getFloatAmount();
            String out_trade_no = payResultVo.getOut_trade_no();
            if (amount >= OnPayConfig.getToutiaoReportAmountLimit()) {
                TurboAgent.onPay(amount);
                sendReportAdData(client, out_trade_no, pay_way, amount, uid, tgid);
                log(TAG, "purchase\n payResultVo = " + payResultVo);
            }
        }
    }

    @Override
    protected String getReportWay() {
        return "kuaishou";
    }
}
