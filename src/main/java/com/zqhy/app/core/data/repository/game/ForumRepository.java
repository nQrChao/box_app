package com.zqhy.app.core.data.repository.game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.HttpHelper;
import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.forum.ForumCategoryVo;
import com.zqhy.app.core.data.model.forum.ForumDetailVo;
import com.zqhy.app.core.data.model.forum.ForumImageUploadVo;
import com.zqhy.app.core.data.model.forum.ForumReplyTopExplicitVo;
import com.zqhy.app.core.data.model.forum.ForumReplyTopLikeVo;
import com.zqhy.app.core.data.model.forum.ForumReplyTopVo;
import com.zqhy.app.core.data.repository.invite.InviteRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.IApiService;
import com.zqhy.app.network.rx.RxSubscriber;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2018/11/20
 */

public class ForumRepository extends InviteRepository {

    /**
     * 话题类型
     *
     */
    public void getCategoryData(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api","forum.topic");
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().testCreate(URL.NEW_API_URL, IApiService.class);
        addDisposable(iApiService1.categoryData(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<ForumCategoryVo>() {
                        }.getType();

                        ForumCategoryVo gameDataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(gameDataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }
    public void forumReport(Map<String, String> params,OnNetWorkListener onNetWorkListener) {

        params.put("api","forum.topic");
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().testCreate(URL.NEW_API_URL, IApiService.class);
        addDisposable(iApiService1.forumReport(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<ForumCategoryVo>() {
                        }.getType();

                        ForumCategoryVo gameDataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(gameDataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }
    public void forumReplyReport(Map<String, String> params,OnNetWorkListener onNetWorkListener) {
        params.put("api","forum.topic");
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().testCreate(URL.NEW_API_URL, IApiService.class);
        addDisposable(iApiService1.forumReplyReport(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<ForumCategoryVo>() {
                        }.getType();

                        ForumCategoryVo gameDataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(gameDataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }

    public void categoryPush(Map<String, String> params,OnNetWorkListener onNetWorkListener) {
        params.put("api","forum.topic");
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().testCreate(URL.NEW_API_URL, IApiService.class);
        addDisposable(iApiService1.categoryPush(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<BaseVo>() {
                        }.getType();

                        BaseVo gameDataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(gameDataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }
    public void forumDetailList(Map<String, String> params,OnNetWorkListener onNetWorkListener) {
        params.put("api","forum.topic");
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().testCreate(URL.NEW_API_URL, IApiService.class);
        addDisposable(iApiService1.forumDetailList(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<ForumDetailVo>() {
                        }.getType();

                        ForumDetailVo gameDataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(gameDataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }
    public void forumReplyTopList(Map<String, String> params,OnNetWorkListener onNetWorkListener) {
        params.put("api","forum.topic");
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().testCreate(URL.NEW_API_URL, IApiService.class);
        addDisposable(iApiService1.forumReplyTopList(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<ForumReplyTopVo>() {
                        }.getType();

                        ForumReplyTopVo gameDataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(gameDataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }
    public void forumReplySubList(Map<String, String> params,OnNetWorkListener onNetWorkListener) {
        params.put("api","forum.topic");
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().testCreate(URL.NEW_API_URL, IApiService.class);
        addDisposable(iApiService1.forumReplySubList(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<ForumReplyTopExplicitVo>() {
                        }.getType();

                        ForumReplyTopExplicitVo gameDataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(gameDataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }
    public void forumReplyTopLike(Map<String, String> params,OnNetWorkListener onNetWorkListener) {
        params.put("api","forum.topic");
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().testCreate(URL.NEW_API_URL, IApiService.class);
        addDisposable(iApiService1.forumReplyTopLike(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<ForumReplyTopLikeVo>() {
                        }.getType();

                        ForumReplyTopLikeVo gameDataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(gameDataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }
    public void forumReplyLike(Map<String, String> params,OnNetWorkListener onNetWorkListener) {
        params.put("api","forum.topic");
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().testCreate(URL.NEW_API_URL, IApiService.class);
        addDisposable(iApiService1.forumReplyLike(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<ForumReplyTopLikeVo>() {
                        }.getType();

                        ForumReplyTopLikeVo gameDataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(gameDataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }
    public void forumReplyRelease(Map<String, String> params,OnNetWorkListener onNetWorkListener) {
        params.put("api","forum.topic");
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().testCreate(URL.NEW_API_URL, IApiService.class);
        addDisposable(iApiService1.forumReplyRelease(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<BaseVo>() {
                        }.getType();

                        BaseVo gameDataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(gameDataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }
    public void forumReply(Map<String, String> params,OnNetWorkListener onNetWorkListener) {
        params.put("api","forum.topic");
        String data = createPostData(params);
        IApiService iApiService1 = HttpHelper.getInstance().testCreate(URL.NEW_API_URL, IApiService.class);
        addDisposable(iApiService1.forumReply(data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<BaseVo>() {
                        }.getType();

                        BaseVo gameDataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(gameDataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }


    public void imageUpload(String flag,File file, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api","forum.topic");
        String data = createPostData(params);
        RequestBody flag1 = RequestBody.create(MediaType.parse("text/plain"), flag);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("pic", file.getName(), requestFile);
        IApiService iApiService1 = HttpHelper.getInstance().testCreate(URL.NEW_API_URL, IApiService.class);
        addDisposable(iApiService1.imageUpload(flag1, data,filePart)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<ForumImageUploadVo>() {
                        }.getType();

                        ForumImageUploadVo gameDataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(gameDataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));

    }

}
