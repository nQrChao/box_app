package com.zqhy.app.core.data.repository.rebate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.mvvm.stateview.StateConstants;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.rebate.RebateCommitVo;
import com.zqhy.app.core.data.model.rebate.RebateItemVo;
import com.zqhy.app.core.data.model.rebate.RebateListVo;
import com.zqhy.app.core.data.model.rebate.RebateRecordListVo;
import com.zqhy.app.core.data.model.rebate.RebateRevokeListVo;
import com.zqhy.app.core.data.model.rebate.RebateServerListVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.rx.RxSubscriber;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class RebateRepository extends BaseRepository {

    public void getRebateListData(int rebate_type, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "rebate_gamelist");
        params.put("game_type", String.valueOf(rebate_type));

        String data = createPostData(params);


        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.EVENT_KEY_REBATE_LIST_STATE, StateConstants.NET_WORK_STATE);
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<RebateListVo>() {
                        }.getType();

                        RebateListVo btRebateListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(btRebateListVo);
                        }
                        showPageState(Constants.EVENT_KEY_REBATE_LIST_STATE, StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.EVENT_KEY_REBATE_LIST_STATE, StateConstants.ERROR_STATE);
                    }
                }.addListener(onNetWorkListener)));
    }

    public void getRebateRecordListData(int rebate_type, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "rebate_apply_record");
        params.put("game_type", String.valueOf(rebate_type));

        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.EVENT_KEY_REBATE_RECORD_LIST_STATE, String.valueOf(rebate_type), StateConstants.NET_WORK_STATE);
                    }


                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<RebateRecordListVo>() {
                        }.getType();

                        RebateRecordListVo rebateRecordListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(rebateRecordListVo);
                        }
                        showPageState(Constants.EVENT_KEY_REBATE_RECORD_LIST_STATE, String.valueOf(rebate_type), StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.EVENT_KEY_REBATE_RECORD_LIST_STATE, String.valueOf(rebate_type), StateConstants.ERROR_STATE);
                    }
                }.addListener(onNetWorkListener)));
    }

    public void getRebateItemDetail(String apply_id, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "rebate_apply_info");

        params.put("apply_id", apply_id);
        String data = createPostData(params);


        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.EVENT_KEY_REBATE_RECORD_ITEM_STATE, apply_id, StateConstants.NET_WORK_STATE);
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<RebateItemVo>() {
                        }.getType();

                        RebateItemVo rebateItemVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(rebateItemVo);
                        }
                        showPageState(Constants.EVENT_KEY_REBATE_RECORD_ITEM_STATE, apply_id, StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.EVENT_KEY_REBATE_RECORD_ITEM_STATE, apply_id, StateConstants.ERROR_STATE);
                    }
                }.addListener(onNetWorkListener)));
    }

    public void getRebateRevokeRemark(String apply_id, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "rebate_revoke_remark");
        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<RebateRevokeListVo>() {
                        }.getType();

                        RebateRevokeListVo revokeListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(revokeListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void recallRebateApply(String apply_id, String rmk_id, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "rebate_apply_revoke");
        params.put("apply_id", apply_id);
        params.put("rmk_id", rmk_id);
        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseResponseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void rebateApply(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (params == null) {
            params = new TreeMap<>();

        }
        params.put("api", "rebate_apply");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<RebateCommitVo>() {
                        }.getType();

                        RebateCommitVo rebateCommitVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(rebateCommitVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 区服历史信息
     *
     * @param gameid
     * @param xh_username
     */
    public void getRebateServerHistory(int gameid, String xh_username, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "rebate_server_history");
        params.put("gameid", String.valueOf(gameid));
        params.put("xh_username", xh_username);

        String data = createPostData(params);


        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<RebateServerListVo>() {
                        }.getType();

                        RebateServerListVo rebateServerListVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(rebateServerListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }
}
