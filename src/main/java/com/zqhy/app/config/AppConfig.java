package com.zqhy.app.config;

import android.text.TextUtils;

import com.chaoji.im.data.model.AppletsData;
import com.zqhy.app.App;
import com.zqhy.app.network.utils.AppUtils;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2018/11/27
 */

public class AppConfig {
    public static String APP_DATA_DECLARATION = App.getContext().getResources().getString(R.string.url_data_declaration);
    public static String APP_REGISTRATION_PROTOCOL = "";
    public static String APP_FILING = "";

    public static String APP_PRIVACY_PROTOCOL = "";

    public static String APP_AUDIT_REGISTRATION_PROTOCOL = "";

    public static String APP_AUDIT_PRIVACY_PROTOCOL ="";

    public static String APP_CANCELLATION_AGREEMENT = "";

    public static String APP_BUSINESS_COOPERATION = "";


    public static void setRestoreProtocolUrl(AppletsData appletsData) {
        APP_FILING = appletsData.getMarketjson().getApp_beianhao();

        APP_AUDIT_REGISTRATION_PROTOCOL = appletsData.getMarketjson().getXieyitanchuang_url_fuwu();

        APP_AUDIT_PRIVACY_PROTOCOL =appletsData.getMarketjson().getXieyitanchuang_url_yinsi();

        APP_CANCELLATION_AGREEMENT = appletsData.getMarketjson().getXieyitanchuang_url_zhuxiao();

        APP_BUSINESS_COOPERATION = appletsData.getMarketjson().getXieyitanchuang_url_guanyu();

        APP_REGISTRATION_PROTOCOL = appletsData.getMarketjson().getXieyitanchuang_url_fuwu() + "?tgid=" + AppUtils.getChannelFromApk();
        APP_PRIVACY_PROTOCOL = appletsData.getMarketjson().getXieyitanchuang_url_yinsi() + "?tgid=" + AppUtils.getChannelFromApk();
    }

    public static long TIME_STAMP = 0;

    public static boolean IS_ALLOW_NON_WIFI_PLAY_VIDEO = false;

    public static boolean IS_ALLOW_NON_WIFI_DOWNLOAD_GAME = false;


    /**
     * 判断是否为工会渠道
     *
     * @return
     * @工会
     */
    public static boolean isGonghuiChannel() {
        return "7".equals(BuildConfig.APP_UPDATE_ID) || "7001".equals(BuildConfig.APP_UPDATE_ID);
    }

    /**
     * 判断是否为官方渠道
     *
     * @return
     * @工会
     */
    public static boolean isOfficialChannel() {
        return "1".equals(BuildConfig.APP_UPDATE_ID) || "7".equals(BuildConfig.APP_UPDATE_ID);
    }

    /**
     * 判断隐藏闪屏的copyright
     *
     * @return
     * @赵飓
     */
    public static boolean isDismissSplashCopyright() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        return list.contains(BuildConfig.APP_UPDATE_ID);
    }

    /**
     * 判断是否为Pro2版本
     *
     * @return
     * @陈剑 appid = 105 友盟sdk在获取权限之后初始化
     */
    public static boolean isProAppid_105Channel() {
        return "105".equals(BuildConfig.APP_UPDATE_ID);
    }

    /**
     * 判断是否为福利版本
     *
     * @return
     * @刘曦
     */
    public static boolean isFuliChannel() {
        return "3".equals(BuildConfig.APP_UPDATE_ID);
    }

    /**
     * 判断是否为极速版本
     *
     * @return
     */
    public static boolean isJisuChannel() {
        return "6".equals(BuildConfig.APP_UPDATE_ID);
    }

    /**
     * 判断是否为退款版本
     *
     * @return
     */
    public static boolean isRefundChannel() {
        return "9".equals(BuildConfig.APP_UPDATE_ID);
    }

    /**
     * 判断神马统计SDK id是否为空
     */
    public static boolean isNeedShenMa() {
        return !TextUtils.isEmpty(BuildConfig.SHENMA_APPID);
    }


    /**
     * 改动需求，隐藏底部导航游戏，里的分类。只显示排行。
     *
     * @return
     */
    public static boolean isSpecial1Channel() {
        return "99000".equals(BuildConfig.APP_UPDATE_ID) || "99001".equals(BuildConfig.APP_UPDATE_ID);
    }

    /**
     * 公会渠道使用热云统计
     */
    private static List<String> reyun_gonghui_tgids;

    public static List<String> getReyun_gonghui_tgids() {
        return reyun_gonghui_tgids;
    }

    public static void setReyun_gonghui_tgids(List<String> reyun_gonghui_tgids) {
        AppConfig.reyun_gonghui_tgids = reyun_gonghui_tgids;
    }


    /**
     * 投放渠道使用头条推广的tgid列表
     */
    private static List<String> toutiao_tgids;

    public static List<String> getToutiao_tgids() {
        return toutiao_tgids;
    }

    public static void setToutiao_tgids(List<String> toutiao_tgids) {
        AppConfig.toutiao_tgids = toutiao_tgids;
    }


    private static int HIDE_COMMUNITY;

    public static void setHideCommunity(int hide_community) {
        HIDE_COMMUNITY = hide_community;
    }

    /**
     * 当值为１时隐藏，值为０时正常显示
     *
     * @return
     */
    public static boolean isHideCommunity() {
        return HIDE_COMMUNITY == 1;
    }


    /**
     * ==================================================================================================================================================================
     */

    private static Map<String, Integer> sbConfig = new HashMap<>();

    public static void putSbData(String key, int value) {
        sbConfig.put(key, value);
    }

    public static int getSbData(String key) {
        return getSbData(key, 0);
    }

    public static int getSbData(String key, int defaultValue) {
        if (!sbConfig.containsKey(key)) {
            return defaultValue;
        }
        return sbConfig.get(key);
    }
}
