package com.zqhy.app.network.statistics;


import com.box.other.blankj.utilcode.util.Logs;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zqhy.app.App;
import com.zqhy.app.config.URL;
import com.zqhy.app.network.utils.AppUtils;
import com.zqhy.app.network.utils.Des;
import com.zqhy.app.utils.TsDeviceUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 */
public class TsStatisticsApi {

    private static final boolean isUseless = false;

    private static final String TAG = "TsStatisticsApi";

    private static volatile TsStatisticsApi instance;

    private TsStatisticsApi() {
    }

    public static TsStatisticsApi getInstance() {
        if (instance == null) {
            synchronized (TsStatisticsApi.class) {
                if (instance == null) {
                    instance = new TsStatisticsApi();
                }
            }
        }
        return instance;
    }

    /**
     * 事件统计
     */
    public void eventStatistics(String action) {
        if (isUseless) {
            return;
        }
        final String api = URL.HTTP_URL + URL.URL_API;

        Map<String, String> params = new TreeMap<>();

        params.put("api", "event_statistic");
        params.put("action", action);
        params.put("client_type", "1");
        params.put("tgid", AppUtils.getTgid());
        params.put("imei", TsDeviceUtils.getDeviceIMEI(App.getContext()));

        //sign签名
        params.put("sign", AppUtils.getSignKey(params));

        Logs.e(TAG + "---targetParams:" + AppUtils.MapToString(params));
        //DES加密数据
        final Map<String, String> map = new TreeMap<>();
        try {
            String data = URLEncoder.encode(Des.encode(AppUtils.MapToString(params)), "UTF-8");
            map.put("data", data);
            OkGo.<String>post(api).params(map).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    if (response.isSuccessful()) {
                        String result = response.body();
                        Logs.e(TAG + "--- result = " + result);
                    }
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
