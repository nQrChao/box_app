package com.zqhy.app.core.data.repository.main;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.mvvm.stateview.StateConstants;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameAppointmentOpVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.data.model.game.new0809.MainCommonDataVo;
import com.zqhy.app.core.data.model.game.new0809.MainHeJiDataVo;
import com.zqhy.app.core.data.model.game.new0809.XyTabDataVo;
import com.zqhy.app.core.data.model.game.search.GameSearchDataVo;
import com.zqhy.app.core.data.model.mainpage.HomeBTGameIndexVo;
import com.zqhy.app.core.data.model.mainpage.HomeGameIndexVo;
import com.zqhy.app.core.data.model.mainpage.navigation.GameNavigationListVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.rx.RxSubscriber;
import com.zqhy.app.utils.cache.ACache;
import com.zqhy.app.utils.sdcard.SdCardManager;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public class BtGameRepository extends BaseRepository {


    public void getIndexGameData(int game_type, final int page, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "index_page");
        params.put("game_type", String.valueOf(game_type));

        //        params.put("page", String.valueOf(page));
        //        switch (game_type) {
        //            case 1:
        //                params.put("api", "bt_game_index");
        //                break;
        //            case 2:
        //                params.put("api", "cg_game_index");
        //                break;
        //            case 3:
        //                params.put("api", "h5_game_index");
        //                break;
        //            case 4:
        //                params.put("api", "dj_game_index");
        //                break;
        //            default:
        //                break;
        //        }

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.EVENT_KEY_MAIN_PAGE_STATE, String.valueOf(game_type), StateConstants.NET_WORK_STATE);
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseVo);
                        Type type = new TypeToken<HomeGameIndexVo>() {
                        }.getType();

                        saveDataToSdCard("JsonData_" + game_type, result);

                        HomeGameIndexVo homeGameIndexBean = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(homeGameIndexBean);
                        }

                        showPageState(Constants.EVENT_KEY_MAIN_PAGE_STATE, String.valueOf(game_type), StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.EVENT_KEY_MAIN_PAGE_STATE, String.valueOf(game_type), StateConstants.ERROR_STATE);
                    }
                }.addListener(onNetWorkListener)));
    }


    public void getIndexBTGameData(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "choice_home_page");
        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseVo);
                        Type type = new TypeToken<HomeBTGameIndexVo>() {
                        }.getType();

                        HomeBTGameIndexVo homeBTGameIndexVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(homeBTGameIndexVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                    }
                }.addListener(onNetWorkListener)));
    }


    public void getIndexPageListData(int game_type, final int page, final int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "index_gamelist");
        params.put("game_type", String.valueOf(game_type));
        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseVo);
                        Type type = new TypeToken<GameListVo>() {
                        }.getType();

                        saveDataToSdCard("JsonMoreData_" + game_type, result);

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


    protected void saveDataToSdCard(String fileName, String jsonData) {
        File file = SdCardManager.getInstance().getJsonDataDir();
        if (!file.exists()) {
            file.mkdir();
        }
        ACache.get(file).putWithKey(fileName, jsonData);
    }

    public void getGameNavigationData(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "game_genre");
        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<GameNavigationListVo>() {
                        }.getType();

                        GameNavigationListVo listVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(listVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getGameSearchData(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "game_s_best");
        params.put("client_type", String.valueOf(1));
        params.put("best_title", "1");

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

    /**
     * 预约列表数据
     *
     * @param onNetWorkListener
     */
    public void getAppointmentGameList(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "reserve_gamelist");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
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
     * 游戏类型换一批
     *
     * @param page
     * @param genre_id
     * @param game_type
     * @param onNetWorkListener
     */
    public void getGenreGameByPage(int page, int genre_id, int game_type, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "get_genre_game");
        params.put("page", String.valueOf(page));
        params.put("genre_id", String.valueOf(genre_id));
        params.put("game_type", String.valueOf(game_type));


        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
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
     * 领取新人礼包
     *
     * @param onNetWorkListener
     */
    public void getRookieCoupons(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "get_rookies_coupon");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
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

    /**
     * 新游：tab
     *
     * @param onNetWorkListener
     */
    public void getXyTabHomePageData(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "txy_home_page");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<XyTabDataVo>() {
                        }.getType();

                        XyTabDataVo baseVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 新游：首页
     *
     * @param onNetWorkListener
     */
    public void getXyHomePageData(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "xy_home_page");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<MainCommonDataVo>() {
                        }.getType();

                        MainCommonDataVo baseVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 折扣：首页
     *
     * @param onNetWorkListener
     */
    public void getZkHomePageData(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "cw_home_page");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<MainCommonDataVo>() {
                        }.getType();

                        MainCommonDataVo baseVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 新游：福利服
     *
     * @param onNetWorkListener
     */
    public void getFlfHomePageData(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "flf_home_page");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<MainCommonDataVo>() {
                        }.getType();

                        MainCommonDataVo baseVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 新游：精选
     *
     * @param onNetWorkListener
     */
    public void getJxHomePageData(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "jx_home_page");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<MainCommonDataVo>() {
                        }.getType();

                        MainCommonDataVo baseVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }


    /**
     * 新游：合集类
     *
     * @param menuParams
     * @param onNetWorkListener
     */
    public void getHjHomePageData(Map<String, String> menuParams, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "hj_home_page");
        if (menuParams != null) {
            for (String key : menuParams.keySet()) {
                params.put(key, menuParams.get(key));
            }
        }

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<MainHeJiDataVo>() {
                        }.getType();

                        MainHeJiDataVo baseVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 新游：推荐类
     *
     * @param onNetWorkListener
     */
    public void getTjHomePageData(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "tj_home_page");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<MainCommonDataVo>() {
                        }.getType();

                        MainCommonDataVo baseVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }
}
