package com.zqhy.app.config;

import android.text.TextUtils;

import com.box.other.blankj.utilcode.util.Logs;
import com.zqhy.app.newproject.BuildConfig;

import java.util.Map;

/**
 * @author Administrator
 * @date 2018/11/1
 */
public class URL {

    private static final boolean is_Api_Debug = false;

    private static final String API_DEBUG_URL = BuildConfig.API_DEBUG_URL;
    //    public static final String NEW_API_URL = "http://community-api.cqxiayou.com/";
    //public static final String NEW_API_URL = "填入你们自己的URL（社群）";
    public static final String NEW_API_URL = "https://association.cqxiayou.com/";
    //交易模块api
    public static final String TRANSACTION_API_URL = "https://tradeapi.cqxiayou.com/";
    public static boolean getApiDebug() {
        return is_Api_Debug;
    }

    //"填入你们自己的测试URL（主APP）（可多个）"
    public static String[] HTTP_DEBUG_URLS = {
            "https://appapi-ns1.xiaodianyouxi.com"
    };

    //"填入你们自己的正式URL（主APP）（可多个）"
    public static String[] HTTP_RELEASE_URLS = {
            "https://appapi-ns1.xiaodianyouxi.com",
            "https://appapi-ns2.xiaodianyouxi.com"
    };

    /*public static String[] HTTP_RELEASE_URLS = {
            "http://appapi.dev.batuyx.com:8081/",
    };*/

    public static String[] HTTP_URLS = HTTP_RELEASE_URLS;

    public static String HTTP_URL = HTTP_URLS[0];

    public static String HTTP_API_TEST = "/index.php/App/index";

    public static String HTTP_TARGET_API = HTTP_API_TEST;

    public static String BASE_URL = HTTP_URLS[0] + HTTP_TARGET_API;

    /**
     * 获取Http地址
     */
    public static String getHttpUrl() {
        if (!TextUtils.isEmpty(API_DEBUG_URL)) {
            return API_DEBUG_URL;
        }
        return is_Api_Debug ? getTestHttpUrl() : HTTP_URL;
    }

    /**
     * 获取Http地址
     */
    public static String getTestHttpUrl() {
        return HTTP_DEBUG_URLS[0];
    }

    public static String getOkGoHttpUrl() {
        return (!TextUtils.isEmpty(API_DEBUG_URL) ? API_DEBUG_URL : HTTP_URL) + HTTP_API_TEST;
    }

    public static String getLhhOkGoHttpUrl() {
        return getOkGoHttpUrl();
        //        return "http://btgameapp-ns1.btgame01.com/index.php/Lhhapp";
    }

    public static final String URL_API = "index.php/App/index";

    public static String getApiUrl(Map<String, String> params) {
        if (params == null) {
            return "";
        }
        String api = params.get("api");
        return api;
    }

    public static String getApiUrl(String api) {
        String newApi = URL_API + "?Fapi=" + api;
        Logs.e("newApi=" + newApi);
        return newApi;
    }
}
