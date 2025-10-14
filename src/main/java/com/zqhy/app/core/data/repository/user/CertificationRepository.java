package com.zqhy.app.core.data.repository.user;

import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.rx.RxSubscriber;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class CertificationRepository extends BaseRepository {

    public void userCertification(String sfzname, String sfzid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "cert_add");

        params.put("real_name", sfzname);
        params.put("idcard", sfzid);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        if (baseResponseVo != null && baseResponseVo.isStateOK()) {
                            UserInfoModel.getInstance().setCertification(sfzname, sfzid);
                        }
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseResponseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void userCertification(String sfzname, String sfzid, String code, String mobile, int is_check, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "cert_add_v2");

        params.put("real_name", sfzname);
        params.put("idcard", sfzid);

        params.put("type", "1");
        //params.put("code", code);
        //params.put("mobile", mobile);
        params.put("is_check", String.valueOf(is_check));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        if (baseResponseVo != null && baseResponseVo.isStateOK()) {
                            UserInfoModel.getInstance().setCertification(sfzname, sfzid);
                        }
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseResponseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }


    public void getCertificationNum(OnNetWorkListener onNetWorkListener){
        Map<String,String> params = new TreeMap<>();
        params.put("api", "get_user_cert_num");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo<Double>>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo<Double> baseResponseVo) {
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseResponseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

}
