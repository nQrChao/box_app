package com.zqhy.app.core.data.repository.game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.game.new0809.MainGameRankDataVo;
import com.zqhy.app.core.data.model.game.search.GameSearchDataVo;
import com.zqhy.app.core.data.repository.classification.ClassificationRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.rx.RxSubscriber;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 */
public class SearchGameRepository extends ClassificationRepository {


    public void getGameSearchData(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "game_s_best");
        params.put("client_type", String.valueOf(1));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<GameSearchDataVo>() {
                        }.getType();

                        GameSearchDataVo gameSearchVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(gameSearchVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 游戏页：排行榜
     *
     * @param params            新游榜 xinbang_page
     *                          热游榜 rebang_page
     * @param onNetWorkListener
     */
    public void getMainRankingData(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<MainGameRankDataVo>() {
                        }.getType();

                        MainGameRankDataVo gameSearchVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(gameSearchVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }
}
