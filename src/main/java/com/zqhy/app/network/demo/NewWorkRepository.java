package com.zqhy.app.network.demo;

import android.text.TextUtils;

import com.box.other.blankj.utilcode.util.Logs;
import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.network.rx.RxSubscriber;
import com.zqhy.app.network.utils.AppConfig;
import com.zqhy.app.network.utils.AppUtils;
import com.zqhy.app.network.utils.Des;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/6
 */

public class NewWorkRepository extends BaseRepository {

    public void execute(String params) {
        addDisposable(iApiService.postClaimData(createPostData(params))
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>() {

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseVo) {
                        sendData(Constants.EVENT_KEY_NETWORK_DEMO, baseVo);
                    }

                    @Override
                    public void onFailure(String msg) {
                        Logs.e("onFailure:" + msg);
                    }
                }));
    }

    protected String createPostData(String params) {
        Map<String, String> mapParams = new TreeMap<>();

        String[] paramArrays = params.split("&", -1);
        for (String param : paramArrays) {
            try {
                String[] split = param.split("=", -1);
                String key = split[0];
                String value = split[1];
                if ("sign".equals(key)) {
                    continue;
                }
                mapParams.put(key, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Logs.e("params = " + params);

        String sign = getSignKey(mapParams);
        Logs.e("sign = " + sign);
        //sign签名
        mapParams.put("sign", sign);

        //DES加密数据
        final Map<String, String> map = new TreeMap<>();
        try {
            map.put("data", URLEncoder.encode(Des.encode(AppUtils.MapToString(mapParams)), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Logs.e("原串(发送)：" + map.get("data"));

        return map.get("data");
    }

    public String getSignKey(Map<String, String> params) {
        Map<String, String> newParams = new TreeMap<>();

        for (String key : params.keySet()) {
            String value = params.get(key);
            //2018.07.12 api sign签名时，如果参数为空，键值也需要加入到签名
            if (!TextUtils.isEmpty(value)) {
                String newValue = value.replace("*", "%2A");
                newParams.put(key, newValue);
            } else {
                newParams.put(key, "");
            }
        }
        String sign = (AppUtils.MapToString(newParams) + AppConfig.signKey);
        Logs.e("signstr(MD5前串):" + sign);
        return AppUtils.MD5(sign);
    }

}
