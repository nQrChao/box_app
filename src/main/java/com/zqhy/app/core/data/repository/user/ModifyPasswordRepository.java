package com.zqhy.app.core.data.repository.user;

import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.rx.RxSubscriber;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Administrator
 * @date 2018/11/12
 */

public class ModifyPasswordRepository extends BindPhoneRepository{


    public void modifyLoginPassword(String mobile, String code, String newpwd, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "modify_pwd");
        params.put("code", code);
        params.put("mobile", mobile);
        params.put("password", newpwd);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
        .compose(RxSchedulers.io_main())
        .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
            @Override
            public void onSuccess(BaseResponseVo baseResponseVo) {
                if(onNetWorkListener != null){
                    onNetWorkListener.onSuccess(baseResponseVo);
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        }.addListener(onNetWorkListener)));
    }
}
