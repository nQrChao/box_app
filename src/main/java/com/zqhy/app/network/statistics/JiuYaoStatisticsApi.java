package com.zqhy.app.network.statistics;


import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.hjq.toast.Toaster;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zqhy.app.App;
import com.zqhy.app.core.BuildConfig;
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
public class JiuYaoStatisticsApi {

    private static final boolean isUseless = true;

    private static final String TAG = "JiuYaoStatisticsApi";

    private static final String api = "https://jyapptjapi.jiuyao666.com/index.php/Etapi/";

    private static volatile JiuYaoStatisticsApi instance;

    private JiuYaoStatisticsApi() {
    }

    public static JiuYaoStatisticsApi getInstance() {
        if (instance == null) {
            synchronized (JiuYaoStatisticsApi.class) {
                if (instance == null) {
                    instance = new JiuYaoStatisticsApi();
                }
            }
        }
        return instance;
    }


    /**
     * 事件统计
     *
     * @param module
     * @param event
     */
    public void eventStatistics(int module, int event) {
        eventStatistics(module, event, 0);

    }

    /**
     * 事件统计
     *
     * @param module   模块id
     * @param event    事件id
     * @param position 位置
     */
    public void eventStatistics(int module, int event, int position) {
        if (isUseless) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("module = ").append(module).append(",")
                .append("event = ").append(event).append(",")
                .append("position = ").append(position);

        Logs.e(sb.toString());
        if (BuildConfig.DEBUG) {
            Toaster.show(sb.toString());
        }

        Map<String, String> params = new TreeMap<>();

        params.put("api", "click_track");
        params.put("client_type", "1");
        params.put("tgid", AppUtils.getTgid());
        params.put("imei", TsDeviceUtils.getDeviceIMEI(App.getContext()));

        String bp = module + "_" + event + "_" + position;
        params.put("bp", bp);

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
