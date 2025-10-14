package com.zqhy.app.core.data.repository.activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.mvvm.stateview.StateConstants;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.activity.ActivityInfoListVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.rx.RxSubscriber;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/17
 */

public class ActivityRepository extends BaseRepository {

    public void getActivityListData(int type, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "huodong_piclist");
        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        String data = createPostData(params);

        subscribe(params, type, data, onNetWorkListener);
    }

    public void getAnnouncementListData(final int type, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "gonggao_list");

        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));


        String data = createPostData(params);

        subscribe(params, type, data, onNetWorkListener);

    }

    private void subscribe(Map<String, String> params, int type, String data, OnNetWorkListener onNetWorkListener) {
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.EVENT_KEY_ACTIVITY_LIST_STATE, String.valueOf(type), StateConstants.NET_WORK_STATE);
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type listType = new TypeToken<ActivityInfoListVo>() {
                        }.getType();

                        ActivityInfoListVo activityInfoListVo = gson.fromJson(result, listType);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(activityInfoListVo);
                        }

                        showPageState(Constants.EVENT_KEY_ACTIVITY_LIST_STATE, String.valueOf(type), StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showPageState(Constants.EVENT_KEY_ACTIVITY_LIST_STATE, String.valueOf(type), StateConstants.ERROR_STATE);
                    }
                }.addListener(onNetWorkListener)));
    }
}
