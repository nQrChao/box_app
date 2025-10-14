package com.zqhy.app.core.data.repository.community;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.community.CommunityUserListV2Vo;
import com.zqhy.app.core.data.model.community.CommunityUserVo;
import com.zqhy.app.core.data.model.community.integral.ActValueListVo;
import com.zqhy.app.core.data.model.community.integral.IntegralDetailListVo;
import com.zqhy.app.core.data.model.community.integral.IntegralMallListVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.data.model.user.AdSwiperListVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.rx.RxSubscriber;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 */
public class CommunityRepository extends BaseRepository {

    public void getCommunityUserData(int uid, OnNetWorkListener onNetWorkListener) {
        Map<String,String> params = new TreeMap<>();
        params.put("api","community_user_center");
        params.put("user_id",String.valueOf(uid));
        String data = createPostData(params);


        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
        .compose(RxSchedulers.io_main())
        .subscribeWith(new RxSubscriber<BaseResponseVo>(params){
            @Override
            public void onSuccess(BaseResponseVo baseResponseVo) {
                Gson gson = new Gson();
                String result = gson.toJson(baseResponseVo);
                Type type = new TypeToken<CommunityUserVo>() {
                }.getType();

                CommunityUserVo communityUserVo = gson.fromJson(result, type);

                if (onNetWorkListener != null) {
                    onNetWorkListener.onSuccess(communityUserVo);
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        }.addListener(onNetWorkListener)));
    }

    public void getUserGameFootprintData(int uid, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String,String> params = new TreeMap<>();
        params.put("api","community_game_track");
        params.put("user_id",String.valueOf(uid));
        params.put("page",String.valueOf(page));
        params.put("pagecount",String.valueOf(pageCount));

        String data = createPostData(params);


        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params){
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<GameListVo>() {
                        }.getType();

                        GameListVo gameListVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(gameListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getIntegralMallData(OnNetWorkListener onNetWorkListener) {
        Map<String,String> params = new TreeMap<>();
        params.put("api","community_integral_mall");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params){
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<IntegralMallListVo>() {
                        }.getType();

                        IntegralMallListVo integralMallListVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(integralMallListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void exchangeCoupon(int coupon_id, OnNetWorkListener onNetWorkListener) {
        Map<String,String> params = new TreeMap<>();
        params.put("api","get_coupon");
        params.put("coupon_id", String.valueOf(coupon_id));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params){
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<BaseVo>() {
                        }.getType();

                        BaseVo baseVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void productExchange(int product_id, OnNetWorkListener onNetWorkListener) {
        Map<String,String> params = new TreeMap<>();
        params.put("api","community_product_exchange");
        params.put("product_id", String.valueOf(product_id));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params){
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<BaseVo>() {
                        }.getType();

                        BaseVo baseVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }


    public void getIntegralListData(int type, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String,String> params = new TreeMap<>();
        params.put("api","community_integral_balance");
        params.put("type", String.valueOf(type));
        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params){
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<IntegralDetailListVo>() {
                        }.getType();

                        IntegralDetailListVo detailListVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(detailListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getActValueListData(int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String,String> params = new TreeMap<>();
        params.put("api","community_actnum_balance");
        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params){
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<ActValueListVo>() {
                        }.getType();

                        ActValueListVo actValueListVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(actValueListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getCommentUserListV2(int user_id, int page, OnNetWorkListener onNetWorkListener) {
        Map<String,String> params = new TreeMap<>();
        params.put("api","comment_user_list_v2");
        params.put("user_id", String.valueOf(user_id));
        params.put("page", String.valueOf(page));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params){
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<CommunityUserListV2Vo>() {
                        }.getType();

                        CommunityUserListV2Vo actValueListVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(actValueListVo);
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
    public void getAdSwiperList(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "ad_swiper_list");
        params.put("id", "105");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);

                        Type listType = new TypeToken<AdSwiperListVo>() {
                        }.getType();

                        AdSwiperListVo vipMemberInfoVo = gson.fromJson(result, listType);
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
