package com.zqhy.app.core.data.repository.recycle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.recycle.XhGameNewRecycleListVo;
import com.zqhy.app.core.data.model.recycle.XhGameRecycleListVo;
import com.zqhy.app.core.data.model.recycle.XhRecycleCouponListVo;
import com.zqhy.app.core.data.model.recycle.XhRecycleRecordListVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.rx.RxSubscriber;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 */
public class RecycleRepository extends BaseRepository {
    /**
     * 可回收小号数据列表
     *
     * @param gameid
     * @param onNetWorkListener
     */
    public void getRecycleXhList(int gameid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "recycle_xh_list");
        if (gameid > 0) {
            params.put("gameid", String.valueOf(gameid));
        }

        String data = createPostData(params);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<XhGameRecycleListVo>() {
                        }.getType();

                        XhGameRecycleListVo xhGameRecycleListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(xhGameRecycleListVo);
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
     * 小号回收
     * 获取短信验证码
     *
     * @param gameid
     * @param onNetWorkListener
     */
    public void getRecycleCode(int gameid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "recycle_get_code");
        params.put("gameid", String.valueOf(gameid));

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
     * 小号回收提交
     *
     * @param xh_username
     * @param code
     * @param onNetWorkListener
     */
    public void xhRecycleAction(String xh_username, String code, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "recycle_record_add_new");
        params.put("xh_username", xh_username);
        params.put("code", code);


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
     * 回收小号记录列表
     *
     * @param onNetWorkListener
     */
    public void getXhRecycleRecord(int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "recycle_record_list");
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
                        Type type = new TypeToken<XhRecycleRecordListVo>() {
                        }.getType();

                        XhRecycleRecordListVo xhRecycleRecordListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(xhRecycleRecordListVo);
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
     * 回收小号赎回
     *
     * @param xh_username
     * @param onNetWorkListener
     */
    public void xhRecycleRansom(String xh_username, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "recycle_record_ransom_new");
        params.put("xh_username", xh_username);

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
     * 小号回收记录删除
     *
     * @param rid               recycle_record_list接口返回的id
     * @param onNetWorkListener
     */
    public void xhRecycleDelete(String rid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "recycle_record_del");
        params.put("rid", rid);

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
     * 回收可得到的优惠券数据接口
     *
     * @param xh_username
     * @param onNetWorkListener
     */
    public void getRecycleCouponList(String xh_username, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "recycle_coupon_list");
        params.put("xh_username", xh_username);

        String data = createPostData(params);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<XhRecycleCouponListVo>() {
                        }.getType();

                        XhRecycleCouponListVo xhRecycleCouponListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(xhRecycleCouponListVo);
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
     * 可回收小号数据列表
     *
     * @param page
     * @param pageCount
     */
    public void getRecycleNewXhList(int can, String order, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "recycle_xh_list_new");
        params.put("can", String.valueOf(can));
        params.put("order", String.valueOf(order));
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
                        Type type = new TypeToken<XhGameNewRecycleListVo>() {
                        }.getType();

                        XhGameNewRecycleListVo xhGameNewRecycleListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(xhGameNewRecycleListVo);
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
