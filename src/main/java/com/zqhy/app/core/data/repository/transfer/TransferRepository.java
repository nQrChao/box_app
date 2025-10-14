package com.zqhy.app.core.data.repository.transfer;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.mvvm.stateview.StateConstants;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.transfer.TransferActionVo;
import com.zqhy.app.core.data.model.transfer.TransferGameItemVo;
import com.zqhy.app.core.data.model.transfer.TransferGameListVo;
import com.zqhy.app.core.data.model.transfer.TransferRecordListVo;
import com.zqhy.app.core.data.model.transfer.TransferRecordVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.rx.RxSubscriber;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/17
 */

public class TransferRepository extends BaseRepository {

    public void getTransferGameMainData(int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "transfer_gamelist");
        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        String data = createPostData(params);


        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.EVENT_KEY_TRANSFER_MAIN_STATE, StateConstants.NET_WORK_STATE);
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<TransferGameListVo>() {
                        }.getType();

                        TransferGameListVo transferGameListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(transferGameListVo);
                        }
                        showPageState(Constants.EVENT_KEY_TRANSFER_MAIN_STATE, StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.EVENT_KEY_TRANSFER_MAIN_STATE, StateConstants.ERROR_STATE);
                    }
                }.addListener(onNetWorkListener)));
    }

    public void getTransferInfoData(int gameid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "transfer_gameinfo");
        params.put("gameid", String.valueOf(gameid));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.EVENT_KEY_TRANSFER_GAME_STATE, StateConstants.NET_WORK_STATE);
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<TransferGameItemVo>() {
                        }.getType();

                        TransferGameItemVo transferGameItemVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(transferGameItemVo);
                        }
                        showPageState(Constants.EVENT_KEY_TRANSFER_GAME_STATE, StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.EVENT_KEY_TRANSFER_GAME_STATE, StateConstants.ERROR_STATE);
                    }
                }.addListener(onNetWorkListener)));
    }

    public void getTransferRewardInfoData(String id, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "transfer_apply_reward");
        params.put("index_id", id);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<TransferActionVo>() {
                        }.getType();

                        TransferActionVo transferActionVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(transferActionVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }

    public void applyTransferReward(String index_id, String servername, String rolename, String role_id,
                                    String xh_username, String xh_uid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "transfer_apply_do");
        params.put("index_id", index_id);
        params.put("servername", servername);
        params.put("rolename", rolename);
        if (!TextUtils.isEmpty(role_id)) {
            params.put("role_id", role_id);
        }
        params.put("xh_username", xh_username);
        params.put("xh_uid", xh_uid);

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

    public void getTransferListData(int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "transfer_points_record");

        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.EVENT_KEY_TRANSFER_RECORD_STATE, StateConstants.NET_WORK_STATE);
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<TransferRecordListVo>() {
                        }.getType();

                        TransferRecordListVo transferRecordListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(transferRecordListVo);
                        }
                        showPageState(Constants.EVENT_KEY_TRANSFER_RECORD_STATE, StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.EVENT_KEY_TRANSFER_RECORD_STATE, StateConstants.ERROR_STATE);
                    }
                }.addListener(onNetWorkListener)));
    }

    public void getTransferRecordData(String index_id, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "transfer_apply_info");
        params.put("index_id", index_id);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.EVENT_KEY_TRANSFER_RECORD_DETAIL_STATE, StateConstants.NET_WORK_STATE);
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<TransferRecordVo>() {
                        }.getType();

                        TransferRecordVo transferRecordVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(transferRecordVo);
                        }
                        showPageState(Constants.EVENT_KEY_TRANSFER_RECORD_DETAIL_STATE, StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.EVENT_KEY_TRANSFER_RECORD_DETAIL_STATE, StateConstants.ERROR_STATE);
                    }
                }.addListener(onNetWorkListener)));
    }
}
