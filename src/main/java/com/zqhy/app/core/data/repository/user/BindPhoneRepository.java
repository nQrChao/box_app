package com.zqhy.app.core.data.repository.user;

import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.user.BindPhoneTempVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.rx.RxSubscriber;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class BindPhoneRepository extends BaseRepository {

    /**
     * 绑定手机号
     *
     * @param mob              填写的手机号
     * @param verificationCode
     * @return
     */
    public void bindPhone(String mob, String verificationCode, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "bound_mobile");
        params.put("code", verificationCode);
        params.put("mobile", mob);
        params.put("type", "1");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        BindPhoneTempVo bindPhoneTempVo = new BindPhoneTempVo();
                        bindPhoneTempVo.setState(baseResponseVo.getState());
                        bindPhoneTempVo.setMsg(baseResponseVo.getMsg());
                        if (bindPhoneTempVo.isStateOK()) {
                            BindPhoneTempVo.DataBean dataBean = new BindPhoneTempVo.DataBean();
                            dataBean.setMob(mob);
                            bindPhoneTempVo.setData(dataBean);

                            UserInfoModel.getInstance().setBindMobile(mob);
                        }
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(bindPhoneTempVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }

    /**
     * 解绑手机号
     *
     * @param mob              发送已经绑定的手机号
     * @param verificationCode
     * @return
     */
    public void unBindPhone(String mob, String verificationCode, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "bound_mobile");
        params.put("code", verificationCode);
        params.put("mobile", mob);
        params.put("type", "2");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        if (baseResponseVo.isStateOK()) {
                            UserInfoModel.getInstance().setUnBindMobile();
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

}
