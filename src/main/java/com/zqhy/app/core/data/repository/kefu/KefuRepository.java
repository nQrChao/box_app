package com.zqhy.app.core.data.repository.kefu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.mvvm.stateview.StateConstants;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.community.CommunityUserVo;
import com.zqhy.app.core.data.model.game.GameAppointmentOpVo;
import com.zqhy.app.core.data.model.jump.JumpBean;
import com.zqhy.app.core.data.model.kefu.EvaluationVo;
import com.zqhy.app.core.data.model.kefu.KefuInfoDataVo;
import com.zqhy.app.core.data.model.kefu.KefuPersionListVo;
import com.zqhy.app.core.data.model.kefu.KefuQuestionInfoVo;
import com.zqhy.app.core.data.model.kefu.VipKefuInfoDataVo;
import com.zqhy.app.core.data.model.message.MessageListVo;
import com.zqhy.app.core.data.model.new_game.NewGameAppointmentListVo;
import com.zqhy.app.core.data.model.new_game.UserAppointmentListVo;
import com.zqhy.app.core.data.model.user.AdSwiperListVo;
import com.zqhy.app.core.data.model.user.SuperVipMemberInfoVo;
import com.zqhy.app.core.data.model.user.UserVoucherVo;
import com.zqhy.app.core.data.model.user.VipMemberInfoVo;
import com.zqhy.app.core.data.model.welfare.MyFavouriteGameListVo;
import com.zqhy.app.core.data.repository.user.UserRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.rx.RxSubscriber;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/22
 */

public class KefuRepository extends UserRepository {

    public void getKefuInfo(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "kefu_info");

        String data = createPostData(params);


        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<KefuInfoDataVo>() {
                        }.getType();

                        KefuInfoDataVo infoDataVo = gson.fromJson(result, type);

                        if(onNetWorkListener != null){
                            onNetWorkListener.onSuccess(infoDataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getVipKefuInfo(OnNetWorkListener onNetWorkListener){
        Map<String, String> params = new TreeMap<>();
        params.put("api", "vip_kefu_info");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<VipKefuInfoDataVo>() {
                        }.getType();

                        VipKefuInfoDataVo infoDataVo = gson.fromJson(result, type);

                        if(onNetWorkListener != null){
                            onNetWorkListener.onSuccess(infoDataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getKefuList(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "kefu_list");

        String data = createPostData(params);


        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<KefuPersionListVo>() {
                        }.getType();

                        KefuPersionListVo kefuPersionListVo = gson.fromJson(result, type);

                        if(onNetWorkListener != null){
                            onNetWorkListener.onSuccess(kefuPersionListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 客服评价
     *
     * @param kid    客服ID
     * @param type   1，点赞，-1，差评
     * @param remark 评价备注
     */
    public void evaluateKefu(String kid, int type, String remark,OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "kefu_opinion");
        params.put("kid", kid);
        params.put("type", String.valueOf(type));
        params.put("remark", remark);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type dataType = new TypeToken<EvaluationVo>() {
                        }.getType();

                        EvaluationVo evaluationVo = gson.fromJson(result, dataType);
                        evaluationVo.setType(type);
                        if(onNetWorkListener != null){
                            onNetWorkListener.onSuccess(evaluationVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }

    /**
     * 客服问题接口
     *
     * @param onNetWorkListener
     */
    public void getKefuQuestionInfo(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "kefu_question_info");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type dataType = new TypeToken<KefuQuestionInfoVo>() {
                        }.getType();

                        KefuQuestionInfoVo kefuInfoVo = gson.fromJson(result, dataType);
                        if(onNetWorkListener != null){
                            onNetWorkListener.onSuccess(kefuInfoVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

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

    /**
     * 获取用户代金券具体数量
     * @param onNetWorkListener
     */
    public void getUserVoucherCount(OnNetWorkListener onNetWorkListener) {
        Map<String,String> params = new TreeMap<>();
        params.put("api","get_user_voucher_count");
        String data = createPostData(params);


        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params){
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<UserVoucherVo>() {
                        }.getType();

                        UserVoucherVo userVoucherVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(userVoucherVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }


    public void getKefuMessageData(String tag,int maxID,OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        String api = "msg_kefumsg";
        params.put("api", api);
        params.put("id", String.valueOf(maxID));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(tag,api) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<MessageListVo>() {
                        }.getType();

                        MessageListVo messageListVo = gson.fromJson(result, type);
                        if(onNetWorkListener != null){
                            onNetWorkListener.onSuccess(messageListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getKefuMessageData(int maxID, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "msg_kefumsg");
        params.put("id", String.valueOf(maxID));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<MessageListVo>() {
                        }.getType();

                        MessageListVo messageListVo = gson.fromJson(result, type);
                        if(onNetWorkListener != null){
                            onNetWorkListener.onSuccess(messageListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getDynamicGameMessageData(long logTime,OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "msg_gamechange");
        if (logTime > 0) {
            params.put("logtime", String.valueOf(logTime));
        }
        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<MessageListVo>() {
                        }.getType();

                        MessageListVo messageListVo = gson.fromJson(result, type);
                        if(onNetWorkListener != null){
                            onNetWorkListener.onSuccess(messageListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 获取省钱卡信息
     *
     * @param onNetWorkListener
     */
    public void getVipMemberInfo(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "get_vip_member_info");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);

                        Type listType = new TypeToken<VipMemberInfoVo>() {
                        }.getType();

                        VipMemberInfoVo vipMemberInfoVo = gson.fromJson(result, listType);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(vipMemberInfoVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 兑换码兑换
     */
    public void redeemCode(String card_str, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "redeem_code");
        params.put("card_str", card_str);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);

                        Type listType = new TypeToken<JumpBean>() {
                        }.getType();

                        JumpBean vipMemberInfoVo = gson.fromJson(result, listType);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(vipMemberInfoVo);
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
        params.put("id", "88");

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

    /**
     * 获取超级省钱卡基本信息
     *
     * @param onNetWorkListener
     */
    public void getMoneyCardBaseInfo(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "money_card_base_info");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);

                        Type listType = new TypeToken<SuperVipMemberInfoVo>() {
                        }.getType();

                        SuperVipMemberInfoVo vipMemberInfoVo = gson.fromJson(result, listType);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(vipMemberInfoVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                    }
                }.addListener(onNetWorkListener)));
    }

    public void getMyFavouriteGameData(int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "user_game");
        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        String data = createPostData(params);


        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.EVENT_KEY_MY_FAVOURITE_GAME_STATE, StateConstants.NET_WORK_STATE);
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<MyFavouriteGameListVo>() {
                        }.getType();

                        MyFavouriteGameListVo favouriteGameListVo = gson.fromJson(result, type);
                        if(onNetWorkListener != null){
                            onNetWorkListener.onSuccess(favouriteGameListVo);
                        }
                        showPageState(Constants.EVENT_KEY_MY_FAVOURITE_GAME_STATE, StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.EVENT_KEY_MY_FAVOURITE_GAME_STATE, StateConstants.ERROR_STATE);
                    }
                }.addListener(onNetWorkListener)));
    }

    public void setGameUnFavorite(int gameid,OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "game_favorite_cancel");
        params.put("gameid", String.valueOf(gameid));

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

    /**
     * 预约列表数据
     *
     * @param onNetWorkListener
     */
    public void getNewGameAppointmentGameList(int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "user_reserve_list");
        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<UserAppointmentListVo>() {
                        }.getType();

                        UserAppointmentListVo gameListVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(gameListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void gameAppointment(int gameid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "reserve");
        params.put("gameid", String.valueOf(gameid));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<GameAppointmentOpVo>() {
                        }.getType();

                        GameAppointmentOpVo gameAppointmentOpVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(gameAppointmentOpVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }
}
