package com.zqhy.app.core.data.repository.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.user.AdSwiperListVo;
import com.zqhy.app.core.data.model.user.PayInfoVo;
import com.zqhy.app.core.data.model.user.PayRebateVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.rx.RxSubscriber;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/13
 */

public class TopUpRepository extends BaseRepository {

    /**
     * @param paytype 充值方式 1 支付宝  2 微信
     * @param amount
     * @return
     */
    public void doRecharge(String paytype, String amount, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "pay_gold");
        params.put("paytype", paytype);
        params.put("amount", amount);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<PayInfoVo>() {
                        }.getType();

                        PayInfoVo payInfoVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(payInfoVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 获取广告配置信息
     */
    public void payRebate(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "pay_rebate");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);

                        Type listType = new TypeToken<PayRebateVo>() {
                        }.getType();

                        PayRebateVo vipMemberInfoVo = gson.fromJson(result, listType);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(vipMemberInfoVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                    }
                }.addListener(onNetWorkListener)));
    }
}
