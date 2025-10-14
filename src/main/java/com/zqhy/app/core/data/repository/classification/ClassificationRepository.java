package com.zqhy.app.core.data.repository.classification;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.mvvm.stateview.StateConstants;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.game.GameHallJxHomeVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.data.model.mainpage.navigation.GameNavigationListVo;
import com.zqhy.app.core.data.model.mainpage.navigation.NewGameNavigationListVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.rx.RxSubscriber;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/9
 */

public class ClassificationRepository extends BaseRepository {


    public void getGameClassificationList(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "game_genre");

        String data = createPostData(params);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)

                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.EVENT_KEY_GAME_CLASSIFICATION_STATE, StateConstants.NET_WORK_STATE);
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<GameNavigationListVo>() {
                        }.getType();

                        GameNavigationListVo gameNavigationListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(gameNavigationListVo);
                        }

                        showPageState(Constants.EVENT_KEY_GAME_CLASSIFICATION_STATE, StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.EVENT_KEY_GAME_CLASSIFICATION_STATE, StateConstants.ERROR_STATE);
                    }
                }.addListener(onNetWorkListener)));
    }

    public void getGameHallList(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "game_hall_config");

        String data = createPostData(params);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)

                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.EVENT_KEY_GAME_CLASSIFICATION_STATE, StateConstants.NET_WORK_STATE);
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<NewGameNavigationListVo>() {
                        }.getType();

                        NewGameNavigationListVo gameNavigationListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(gameNavigationListVo);
                        }

                        showPageState(Constants.EVENT_KEY_GAME_CLASSIFICATION_STATE, StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.EVENT_KEY_GAME_CLASSIFICATION_STATE, StateConstants.ERROR_STATE);
                    }
                }.addListener(onNetWorkListener)));
    }


    public void getGameList(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        String list_type = "";
        if (params != null) {
            list_type = params.get("list_type");
        }

        String finalList_type = list_type;
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), createGameListPostBody(params))
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }

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

    /**
     * order :newest(最新排序),hot(热门排序),ranking(排行榜排序),selected(精选排序), discount: (折扣升序)
     *
     * @param params
     * @return
     */
    private String createGameListPostBody(Map<String, String> params) {
        if (params == null) {
            params = new TreeMap<>();
        }
        //        params.put("api", "gamelist");
        params.put("api", "gamelist_page");

        return createPostData(params);
    }

    /**
     * @param onNetWorkListener
     */
    public void getGameClassificationBastList(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "game_fl_best");

        String data = createPostData(params);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }

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

    /**
     * order :newest(最新排序),hot(热门排序),ranking(排行榜排序),selected(精选排序), discount: (折扣升序)
     *
     * @param params
     * @return
     */
    private String createGameListJxPostBody(Map<String, String> params) {
        if (params == null) {
            params = new TreeMap<>();
        }
        //        params.put("api", "gamelist");
        params.put("api", "game_hall_jx_home");

        return createPostData(params);
    }

    public void getGameListJx(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        String list_type = "";
        if (params != null) {
            list_type = params.get("list_type");
        }

        String finalList_type = list_type;
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), createGameListJxPostBody(params))
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<GameHallJxHomeVo>() {
                        }.getType();

                        GameHallJxHomeVo gameListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(gameListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                    }
                }.addListener(onNetWorkListener)));
    }
}
