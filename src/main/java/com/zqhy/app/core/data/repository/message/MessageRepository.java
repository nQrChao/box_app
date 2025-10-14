package com.zqhy.app.core.data.repository.message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.mvvm.stateview.StateConstants;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.message.InteractiveMessageListVo;
import com.zqhy.app.core.data.model.message.MessageBannerVo;
import com.zqhy.app.core.data.model.message.MessageListVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.rx.RxSubscriber;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/13
 */

public class MessageRepository extends BaseRepository {

    public void getAdBannerData(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "msg_msgad");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        showPageState(Constants.EVENT_KEY_MESSAGE_STATE, StateConstants.NET_WORK_STATE);
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<MessageBannerVo>() {
                        }.getType();

                        MessageBannerVo bannerVo = gson.fromJson(result, type);
                        if(onNetWorkListener != null){
                            onNetWorkListener.onSuccess(bannerVo);
                        }
                        showPageState(Constants.EVENT_KEY_MESSAGE_STATE, StateConstants.SUCCESS_STATE);
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getKefuMessageData(int maxID, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "msg_kefumsg");
        params.put("id", String.valueOf(maxID));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<MessageListVo>() {
                        }.getType();

                        MessageListVo messageListVo = gson.fromJson(result, type);
                        if(onNetWorkListener != null){
                            onNetWorkListener.onSuccess(messageListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getDynamicGameMessageData(long logTime,OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "msg_gamechange");
        if (logTime > 0) {
            params.put("logtime", String.valueOf(logTime));
        }
        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<MessageListVo>() {
                        }.getType();

                        MessageListVo messageListVo = gson.fromJson(result, type);
                        if(onNetWorkListener != null){
                            onNetWorkListener.onSuccess(messageListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getCommentMessages(int page,int pageCount,OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "comment_user_interaction");
        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
        .compose(RxSchedulers.io_main())
        .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

            @Override
            protected void onNoNetWork() {
                super.onNoNetWork();
                showPageState(Constants.EVENT_KEY_MESSAGE_LIST_STATE,StateConstants.NET_WORK_STATE);
            }

            @Override
            public void onSuccess(BaseResponseVo baseResponseVo) {
                Gson gson = new Gson();
                String result = gson.toJson(baseResponseVo);
                Type type = new TypeToken<InteractiveMessageListVo>() {
                }.getType();

                InteractiveMessageListVo messageListVo = gson.fromJson(result, type);
                if(onNetWorkListener != null){
                    onNetWorkListener.onSuccess(messageListVo);
                }
                showPageState(Constants.EVENT_KEY_MESSAGE_LIST_STATE,StateConstants.SUCCESS_STATE);
            }

            @Override
            public void onFailure(String msg) {
                showPageState(Constants.EVENT_KEY_MESSAGE_LIST_STATE,StateConstants.ERROR_STATE);
            }
        }.addListener(onNetWorkListener)));
    }
}
