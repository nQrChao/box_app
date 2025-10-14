package com.zqhy.app.report;

import android.content.Context;

import com.bytedance.ads.convert.BDConvert;
import com.bytedance.applog.AppLog;
import com.bytedance.applog.InitConfig;
import com.bytedance.applog.game.GameReportHelper;
import com.bytedance.applog.util.UriConstants;
import com.box.other.blankj.utilcode.util.Logs;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.config.OnPayConfig;
import com.zqhy.app.core.pay.PayResultVo;
import com.zqhy.app.network.utils.AppUtils;
import com.zqhy.app.newproject.BuildConfig;

import java.util.List;

/**
 * @author leeham2734
 * @date 2021/3/25-10:52
 * @description
 */
public class TtDataReportAgency extends AbsReportAgency {

    private static final String TAG = "TtDataReportAgency";

    private static volatile TtDataReportAgency ourInstance;

    public static TtDataReportAgency getInstance() {
        if (ourInstance == null) {
            synchronized (TtDataReportAgency.class) {
                if (ourInstance == null) {
                    ourInstance = new TtDataReportAgency();
                }
            }
        }
        return ourInstance;
    }

    @Override
    public void init(Context mContext) {
        String TeaAgent_Aid = BuildConfig.TOUTIAO_APPID;
        /*String channel = HumeSDK.getChannel(mContext);

        if (TextUtils.isEmpty(TeaAgent_Aid)) {
            Logs.d(TAG, "TeaAgent_Aid is empty");
            return;
        }

        if (TextUtils.isEmpty(channel)) {
            Logs.d(TAG, "HumeSDK.getChannel() = null");
            channel = AppUtils.getTgid();
        } else {
            if (channel.contains("_")) {
                channel = channel.split("_")[0];
            }
            if (!TextUtils.isEmpty(channel)){
                AppUtils.setTgid(channel);
                AppUtils.setDefaultTgid(channel);
            }
        }*/
        String channel = AppUtils.getChannelFromApk();
        Logs.d(TAG, "channel = " + channel);
        final InitConfig config = new InitConfig(TeaAgent_Aid, channel);
        config.setUriConfig(UriConstants.DEFAULT);
        config.setImeiEnable(false);//建议关停获取IMEI（出于合规考虑）
        config.setAutoTrackEnabled(true);//全埋点开关，true开启，false关闭
        config.setMacEnable(false);
        config.setSerialNumberEnable(false);
        config.setGaidEnabled(false);
        config.setLogger((msg, t) -> Logs.e("AppLog", msg, t));
        config.setLogEnable(true);
        config.setEnablePlay(true);
        BDConvert.getInstance().init(mContext, AppLog.getInstance());
        AppLog.init(mContext, config);
        AppLog.start();
    }

    @Override
    protected void startApp(Context context) {

    }

    @Override
    public void register(String client, String accountID, String username, String tgid) {
        if (isIncludeTouTiaoSDK()) {
            GameReportHelper.onEventRegister(client, true);
            sendReportRegisterData(client, username, tgid);
        }
    }

    @Override
    protected void login(String client, String accountID, String username, String tgid) {
        if (isIncludeTouTiaoSDK()) {
            GameReportHelper.onEventLogin(client, true);
        }
    }

    @Override
    public void purchase(String client, String pay_way, PayResultVo payResultVo, String uid, String tgid) {
        if (isIncludeTouTiaoSDK()) {
            float amount = payResultVo.getFloatAmount();
            String out_trade_no = payResultVo.getOut_trade_no();
            if (amount >= OnPayConfig.getToutiaoReportAmountLimit()) {
                //内置事件 “支付”，属性：商品类型，商品名称，商品ID，商品数量，支付渠道，币种，是否成功（必传），金额（必传）
                if (amount < 1F) amount = 1F;
                GameReportHelper.onEventPurchase(client, "bt", String.valueOf(System.currentTimeMillis()), 1, pay_way, "¥", true, (int) amount);
                //GameReportHelper.onEventPurchase(client, "bt", String.valueOf(System.currentTimeMillis()), 1, "wechat", "¥", true, (int) amount);
                sendReportAdData(client, out_trade_no, pay_way, amount, uid, tgid);
            }
        }
    }

    @Override
    protected String getReportWay() {
        return "toutiao";
    }


    public boolean isIncludeTouTiaoSDK() {
        return (BuildConfig.IS_CONTAINS_TOUTIAO_SDK);
    }


    /**
     * 过滤tgid
     *
     * @return
     */
    private boolean filterTgids() {
        String appTgid = AppUtils.getChannelFromApk();

        List<String> tgids = AppConfig.getToutiao_tgids();
        if (tgids != null && !tgids.isEmpty()) {
            for (String tgid : tgids) {
                if (appTgid.startsWith(tgid)) {
                    return true;
                }
            }
        } else {
            Logs.e("filter_tgids is empty......");
            return true;
        }
        return false;
    }

    public void onResume(Context context) {
        if (isIncludeTouTiaoSDK()) {
            AppLog.onResume(context);
        }
    }

    public void onPause(Context context) {
        if (isIncludeTouTiaoSDK()) {
            AppLog.onPause(context);
        }
    }
}
