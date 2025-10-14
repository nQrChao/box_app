package com.zqhy.app.core.data.repository.community.comment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.community.comment.CommentInfoVo;
import com.zqhy.app.core.data.model.community.comment.ReplyListVo;
import com.zqhy.app.core.data.model.community.comment.UserCommentInfoVo;
import com.zqhy.app.core.data.repository.game.GameRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.rx.RxSubscriber;
import com.zqhy.app.network.utils.Base64;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 */
public class CommentRepository extends GameRepository {

    public void submitComment(Map<String, String> params, Map<String, File> fileParams, OnNetWorkListener onNetWorkListener) {
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

    public void getCommentDetailData(int cid, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "comment_info");
        params.put("cid", String.valueOf(cid));
        params.put("page", String.valueOf(page));
        params.put("client_type", "1");
        params.put("pagecount", String.valueOf(pageCount));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<CommentInfoVo>() {
                        }.getType();

                        CommentInfoVo commentInfoVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(commentInfoVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getCommentReplyData(int cid, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "comment_reply_list");
        params.put("cid", String.valueOf(cid));
        params.put("page", String.valueOf(page));
        params.put("client_type", "1");
        params.put("pagecount", String.valueOf(pageCount));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<ReplyListVo>() {
                        }.getType();

                        ReplyListVo replyListVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(replyListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void setCommentLike(int cid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "comment_like");
        params.put("cid", String.valueOf(cid));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
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

    public void setReplyLike(int rid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "comment_reply_like");
        params.put("rid", String.valueOf(rid));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
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

    public void setCommentReply(int cid, String content, int rid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "comment_reply_add");
        params.put("cid", String.valueOf(cid));
        params.put("content", Base64.encode(content.getBytes()));
        if (rid != 0) {
            params.put("torid", String.valueOf(rid));
        }

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
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

    public void getUserCommentData(int user_id, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "comment_user_list");
        params.put("user_id", String.valueOf(user_id));
        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<UserCommentInfoVo>() {
                        }.getType();

                        UserCommentInfoVo userCommentInfoVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(userCommentInfoVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }
}
