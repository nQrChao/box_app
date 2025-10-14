package com.zqhy.app.core.data.repository.cloud;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.cloud.CloudCourseVo;
import com.zqhy.app.core.data.model.cloud.CloudDeviceListVo;
import com.zqhy.app.core.data.model.cloud.CloudNoticeVo;
import com.zqhy.app.core.data.model.cloud.CloudPayInfoVo;
import com.zqhy.app.core.data.model.cloud.CloudScreenVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.data.model.game.VeTokenVo;
import com.zqhy.app.core.data.model.user.PayInfoVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.rx.RxSubscriber;
import com.zqhy.app.network.utils.Base64;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/19
 */

public class CloudRepository extends BaseRepository {
    /**
     * 云挂机FAQ
     */
    public void getCourseData(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "guaji_faq");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<CloudCourseVo>() {
                        }.getType();

                        CloudCourseVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }

    /**
     * 云挂机问题反馈
     */
    public void kefuCloudFeedback(Map<String, String> params, Map<String, File> fileParams, OnNetWorkListener onNetWorkListener) {
        String data = createPostData(params);
        addDisposable(iApiService.postClaimDataWithPic(URL.getApiUrl(params),createPostPicData(data), createPostPicPartData(fileParams))
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
     * 云挂机-设备信息列表
     */
    public void getDeviceList(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "guaji_list");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<CloudDeviceListVo>() {
                        }.getType();

                        CloudDeviceListVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }
    /**
     * 火云挂机-设备信息列表
     */
    public void getVeDeviceList(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "volcengine_getlist");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<CloudDeviceListVo>() {
                        }.getType();

                        CloudDeviceListVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }

    /**
     * 云挂机-领取新人福利
     */
    public void freeTrial(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "guaji_getnew");

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

                        BaseVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }

    /**
     * 火云挂机-领取新人福利
     */
    public void freeVeTrial(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "volcengine_getnew");
        //params.put("api_version", "20231122");

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

                        BaseVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }

    /**
     * 云挂机-设备信息修改
     */
    public void updateDevice(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        params.put("api", "guaji_update");

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

                        BaseVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }

    /**
     * 云挂机-购买须知
     */
    public void getNotice(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "guaji_buyfaq");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<CloudNoticeVo>() {
                        }.getType();

                        CloudNoticeVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }

    /**
     * 云挂机-支付信息查询
     */
    public void getPayInfo(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "guaji_payinfo");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<CloudPayInfoVo>() {
                        }.getType();

                        CloudPayInfoVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }

    /**
     * 云挂机-发起支付
     */
    public void launchPay(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
//        params.put("api", "guaji_pay");
        params.put("api", "volcengine_pay");

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

                        PayInfoVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }

    /**
     * 云挂机-获取截图/停止挂机
     */
    public void getScreen(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        params.put("api", "guaji_screen");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<CloudScreenVo>() {
                        }.getType();

                        CloudScreenVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }


    /**
     * 云挂机-游戏列表
     */
    public void getGameList(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        params.put("api", "guaji_gamelist");

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

                        GameListVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }
    public void getToken(String gid ,OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "volcengine_getkey");
        params.put("gid", gid);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<VeTokenVo>() {
                        }.getType();

                        VeTokenVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }
    public void getTokenDemo(String gameid ,OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "volcengine_get_demo_key");
        params.put("gameid", gameid);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<VeTokenVo>() {
                        }.getType();

                        VeTokenVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }
    public void getTokenPlay(String gameid ,OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "volcengine_get_cloud_key");
        params.put("gameid", gameid);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<VeTokenVo>() {
                        }.getType();

                        VeTokenVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }

    /**
     * 火云挂机-游戏列表
     */
    public void getVeGameList(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        params.put("api", "volcengine_gamelist");

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

                        GameListVo dataVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }

    /**
     * 火云挂机-开始挂机前上报设备号
     */
    public void getVolcengineStart(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        params.put("api", "volcengine_start");

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

                        BaseVo dataVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }    /**
     * 火云试玩-开始前上报设备号
     */
    public void getVolcengineDemoStart(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        params.put("api", "volcengine_demo_start");

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

                        BaseVo dataVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }
    public void getVolcenginePlayStart(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        params.put("api", "volcengine_cloud_start");

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

                        BaseVo dataVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }
    public void getVolcengineStop(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        params.put("api", "volcengine_outof");

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

                        BaseVo dataVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }
    /**
     * 火云挂机-获取截图
     */
    public void getVeScreen(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        params.put("api", "volcengine_capture_screen");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<CloudScreenVo>() {
                        }.getType();

                        CloudScreenVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }
}
