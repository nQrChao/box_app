package com.zqhy.app.core.data.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.setting.WxPayPlugVo;
import com.zqhy.app.core.data.model.version.VersionVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.rx.RxSubscriber;
import com.zqhy.app.newproject.BuildConfig;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/28
 */

public class AppRepository extends BaseRepository {

    public void getAppVersion(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "get_version");
        params.put("appid", BuildConfig.APP_UPDATE_ID);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<VersionVo>() {
                        }.getType();

                        VersionVo versionVo = gson.fromJson(result, type);

                        if(onNetWorkListener != null){
                            onNetWorkListener.onSuccess(versionVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getWxPayPulg(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "get_wx_plug");
        params.put("appid", BuildConfig.APP_UPDATE_ID);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<WxPayPlugVo>() {
                        }.getType();

                        WxPayPlugVo wxPayPlugVo = gson.fromJson(result, type);
                        if(onNetWorkListener != null){
                            onNetWorkListener.onSuccess(wxPayPlugVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

}
