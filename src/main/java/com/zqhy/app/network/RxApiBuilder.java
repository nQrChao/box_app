package com.zqhy.app.network;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx2.adapter.FlowableBody;
import com.zqhy.app.config.URL;
import com.zqhy.app.network.utils.AppUtils;
import com.zqhy.app.network.utils.Des;

import java.util.Map;
import java.util.TreeMap;

import io.reactivex.Flowable;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author Administrator
 * @date 2018/11/2
 */

public class RxApiBuilder extends OkGoApiBuilder {

    private static final String TAG = RxApiBuilder.class.getSimpleName();

    public RxApiBuilder() {
    }

    /**
     *
     * @param params
     * @return
     */
    public Flowable create(Map<String, String> params) {
        String apiUrl = URL.BASE_URL;
        Map<String, String> targetParams = addCommonParams(params);
        Logs.e(TAG, "targetParams:" + AppUtils.MapToString(targetParams));

        //DES加密数据
        final Map<String, String> map = new TreeMap<>();
        map.put("data", Des.encode(AppUtils.MapToString(targetParams)));

        Logs.e(TAG, "原串(发送)：" + map.get("data"));

        return OkGo.<String>post(apiUrl)
                .params(map)
                .converter(new StringConvert(){
                    @Override
                    public String convertResponse(Response response) throws Throwable {
                        ResponseBody body = response.body();
                        if (body == null) {
                            return null;
                        }
                        Logs.e(TAG,body.string());
                        return body.string();
                    }
                })
                .adapt(new FlowableBody<>());
    }


    public Flowable create(String params) {
        String apiUrl = URL.BASE_URL;
        Logs.e(TAG, "targetParams:" + params);

        //DES加密数据
        final Map<String, String> map = new TreeMap<>();
        map.put("data", Des.encode(params));

        Logs.e(TAG, "原串(发送)：" + map.get("data"));

        return OkGo.<String>post(apiUrl)
                .params(map)
                .converter(new StringConvert(){
                    @Override
                    public String convertResponse(Response response) throws Throwable {
                        ResponseBody body = response.body();
                        if (body == null) {
                            return null;
                        }
                        Logs.e(TAG,body.string());
                        return body.string();
                    }
                })
                .adapt(new FlowableBody<>());
    }
}
