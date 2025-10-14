package com.zqhy.app.core.data.repository.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.mvvm.stateview.StateConstants;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.game.ServerListVo;
import com.zqhy.app.core.data.model.mainpage.navigation.NewGameNavigationListVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.rx.RxSubscriber;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/8
 */

public class ServerRepository extends BaseRepository {


    public void getServerList(Map<String, String> treeParams, String dt, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        if (treeParams != null) {
            for (String key : treeParams.keySet()) {
                params.put(key, treeParams.get(key));
            }
        }
        String tag = dt;

        params.put("api", "serverlist");
        params.put("dt", dt);
        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        String data =  createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.EVENT_KEY_SERVER_LIST_STATE, tag, StateConstants.NET_WORK_STATE);
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<ServerListVo>() {
                        }.getType();

                        ServerListVo serverListVo = gson.fromJson(result, type);
                        if(onNetWorkListener != null){
                            onNetWorkListener.onSuccess(serverListVo);
                        }
                        showPageState(Constants.EVENT_KEY_SERVER_LIST_STATE, tag, StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.EVENT_KEY_SERVER_LIST_STATE, tag, StateConstants.ERROR_STATE);
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
}
