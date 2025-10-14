package com.zqhy.app.core.data.repository.chat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.Setting;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.chat.AddChatVo;
import com.zqhy.app.core.data.model.chat.ChatActivityRecommendVo;
import com.zqhy.app.core.data.model.chat.ChatRecommendVo;
import com.zqhy.app.core.data.model.chat.ChatTeamNoticeListVo;
import com.zqhy.app.core.data.model.chat.ChatUserVo;
import com.zqhy.app.core.data.model.game.GameDataVo;
import com.zqhy.app.core.data.model.splash.SplashVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.rx.RxObserver;
import com.zqhy.app.network.rx.RxSubscriber;
import com.zqhy.app.utils.sp.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.Observable;

/**
 * @author Administrator
 */
public class ChatRepository extends BaseRepository {

    public void getChatRecommend(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "chat_team_recommend");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<ChatRecommendVo>() {
                        }.getType();

                        ChatRecommendVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }

    public void addChat(String gameid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "add_chat");
        params.put("gameid", gameid);
        params.put("simulator", String.valueOf(Setting.simulator));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<AddChatVo>() {
                        }.getType();

                        AddChatVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }

    public void chatActivityRecommend(String tid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "chat_activity_recommend");
        params.put("tid", tid);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<ChatActivityRecommendVo>() {
                        }.getType();

                        ChatActivityRecommendVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }

    public void chatTeamNotice(String tid, String page, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "chat_team_notice_list");
        params.put("tid", tid);
        params.put("page", page);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<ChatTeamNoticeListVo>() {
                        }.getType();

                        ChatTeamNoticeListVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }

    public void chatGetGameId(String tid,OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "chat_get_gameid_by_tid");
        params.put("tid", tid);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<GameDataVo>() {}.getType();

                        GameDataVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }

    public void chatRuleTxt(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "chat_rule_txt");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<ChatUserVo>() {}.getType();

                        ChatUserVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }

    public void getNetWorkData(String tid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params1 = new TreeMap<>();
        params1.put("api", "chat_activity_recommend");
        params1.put("tid", tid);

        Map<String, String> params2 = new TreeMap<>();
        params2.put("api", "chat_team_notice_list");
        params2.put("tid", tid);

        Map<String, String> params3 = new TreeMap<>();
        params3.put("api", "chat_get_gameid_by_tid");
        params3.put("tid", tid);

        Observable<BaseResponseVo> observable1 = iApiService.postClaimData2("chat_activity_recommend", createPostData(params1));
        Observable<BaseResponseVo> observable2 = iApiService.postClaimData2("chat_team_notice_list", createPostData(params2));
        Observable<BaseResponseVo> observable3 = iApiService.postClaimData2("chat_get_gameid_by_tid", createPostData(params3));

        Observable.zip(observable1, observable2, observable3, (baseResponseVo, baseResponseVo2, baseResponseVo3) -> {
                    Gson gson = new Gson();
                    ChatActivityRecommendVo chatActivityRecommendVo = gson.fromJson(gson.toJson(baseResponseVo), new TypeToken<ChatActivityRecommendVo>() {}.getType());
                    if (chatActivityRecommendVo != null && chatActivityRecommendVo.isStateOK()) {
                        Setting.activityList = chatActivityRecommendVo.getData();
                        Setting.tid = tid;
                    }

                    ChatTeamNoticeListVo chatTeamNoticeListVo = gson.fromJson(gson.toJson(baseResponseVo2), new TypeToken<ChatTeamNoticeListVo>() {}.getType());
                    if (chatTeamNoticeListVo != null && chatTeamNoticeListVo.isStateOK()) {
                        Setting.noticeList = chatTeamNoticeListVo.getData();
                    }

                    GameDataVo gameDataVo = gson.fromJson(gson.toJson(baseResponseVo3), new TypeToken<GameDataVo>() {}.getType());

                    return gameDataVo;
                }).compose(RxSchedulers.io_main_o())

                .subscribeWith(new RxObserver<GameDataVo>() {
                    @Override
                    public void onSuccess(GameDataVo gameDataVo) {
                        onNetWorkListener.onSuccess(gameDataVo);
                    }

                    @Override
                    public void onFailure(String msg) {
                        onNetWorkListener.onFailure(msg);
                    }
                });

    }
}
