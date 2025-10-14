package com.zqhy.app.core.data.repository.connection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.connection.ConnectionWayInfoVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.rx.RxSubscriber;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author pc
 * @date 2019/12/23-11:49
 * @description
 */
public class ConnectionRepository extends BaseRepository {

    public void getConnectionInfo(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "contact_us");

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),createPostData(params))
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseVo);
                        Type type = new TypeToken<ConnectionWayInfoVo>() {
                        }.getType();
                        ConnectionWayInfoVo connectionWayInfoVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(connectionWayInfoVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }).addListener(onNetWorkListener));

    }
}
