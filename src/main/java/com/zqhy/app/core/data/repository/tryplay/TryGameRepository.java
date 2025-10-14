package com.zqhy.app.core.data.repository.tryplay;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.tryplay.TryGameInfoVo;
import com.zqhy.app.core.data.model.tryplay.TryGameListVo;
import com.zqhy.app.core.data.model.tryplay.TryGameRewardListVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.rx.RxSubscriber;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 */
public class TryGameRepository extends BaseRepository {

    /**
     * 试玩列表
     *
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getTryGameListData(int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "trial_list");
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
                        Type type = new TypeToken<TryGameListVo>() {
                        }.getType();

                        TryGameListVo tryGameListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(tryGameListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onFailure(msg);
                        }
                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 试玩详情数据接口
     *
     * @param tid
     * @param onNetWorkListener
     */
    public void getTryGameDetailData(int tid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "trial_info");
        params.put("tid", String.valueOf(tid));

        String data = createPostData(params);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<TryGameInfoVo>() {
                        }.getType();

                        TryGameInfoVo tryGameInfoVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(tryGameInfoVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onFailure(msg);
                        }
                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 奖励播报
     *
     * @param onNetWorkListener
     */
    public void getTryGameRewardNoticeData(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "trial_reward_notice");

        String data = createPostData(params);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<TryGameRewardListVo>() {
                        }.getType();

                        TryGameRewardListVo tryGameRewardListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(tryGameRewardListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onFailure(msg);
                        }
                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 我的试玩列表
     *
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getUserTryGameListData(int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "user_trial_list");
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
                        Type type = new TypeToken<TryGameListVo>() {
                        }.getType();

                        TryGameListVo tryGameListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(tryGameListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onFailure(msg);
                        }
                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 领取奖励
     *
     * @param tid
     * @param ttids             任务ID集合. 多个用英文逗号(,)分割 例如: 9,10,11
     * @param onNetWorkListener
     */
    public void getTryGameReward(int tid, List<Integer> ttids, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "trial_receive_reward");
        params.put("tid", String.valueOf(tid));
        StringBuilder sb = new StringBuilder();
        if (ttids != null && !ttids.isEmpty()) {
            for (Integer i : ttids) {
                sb.append(String.valueOf(i)).append(",");
            }
        }

        params.put("ttids", sb.toString());

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
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onFailure(msg);
                        }
                    }
                }.addListener(onNetWorkListener)));
    }


    /**
     * 领取奖励
     *
     * @param task_id
     * @param onNetWorkListener
     */
    public void getTryGameReward(int task_id, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "trial_receive_reward");
        params.put("task_id", String.valueOf(task_id));

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
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onFailure(msg);
                        }
                    }
                }.addListener(onNetWorkListener)));
    }


    /**
     * 申请试玩
     *
     * @param tid
     * @param onNetWorkListener
     */
    public void setJoinTryGame(int tid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "trial_join");
        params.put("tid", String.valueOf(tid));

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
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onFailure(msg);
                        }
                    }
                }.addListener(onNetWorkListener)));
    }
}
