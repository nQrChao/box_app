package com.zqhy.app.core.data.repository.community.qa;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.community.qa.BaseItemVo;
import com.zqhy.app.core.data.model.community.qa.QAListVo;
import com.zqhy.app.core.data.model.community.qa.QaCenterQuestionListVo;
import com.zqhy.app.core.data.model.community.qa.QaDetailInfoVo;
import com.zqhy.app.core.data.model.community.qa.UserQaCanAnswerInfoVo;
import com.zqhy.app.core.data.model.community.qa.UserQaCenterInfoVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.rx.RxSubscriber;
import com.zqhy.app.network.utils.Base64;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 */
public class QaRepository extends BaseRepository {


    public void getQaListData(int gameid, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "comment_game_question_list");
        params.put("gameid", String.valueOf(gameid));
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
                        Type type = new TypeToken<QAListVo>() {
                        }.getType();

                        QAListVo qaListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(qaListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getQaDetailData(int qid, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "comment_question_info");
        params.put("qid", String.valueOf(qid));
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
                        Type type = new TypeToken<QaDetailInfoVo>() {
                        }.getType();

                        QaDetailInfoVo qaDetailInfoVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(qaDetailInfoVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }


    public void useAskQuestion(int gameid, String content, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "comment_question_add");
        params.put("gameid", String.valueOf(gameid));
        params.put("content", Base64.encode(content.getBytes()));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo<Integer>>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo<Integer> baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<BaseItemVo>() {
                        }.getType();

                        BaseItemVo baseItemVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseItemVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void userAnswerQuestion(int qid, String content, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "comment_answer_add");
        params.put("qid", String.valueOf(qid));
        params.put("content", Base64.encode(content.getBytes()));

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

    public void getUserQaCenterData(int uid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "comment_question_answer_center");
        params.put("user_id", String.valueOf(uid));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<UserQaCenterInfoVo>() {
                        }.getType();

                        UserQaCenterInfoVo userQaCenterInfoVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(userQaCenterInfoVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getUserQaListData(int type, int uid, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        if (type == 1) {
            params.put("api", "comment_my_answer_list");
        } else if (type == 2) {
            params.put("api", "comment_my_question_list");
        }
        params.put("user_id", String.valueOf(uid));
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
                        Type type = new TypeToken<QaCenterQuestionListVo>() {
                        }.getType();

                        QaCenterQuestionListVo qaCenterQuestionListVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(qaCenterQuestionListVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void setAnswerLike(int aid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "comment_answer_like");
        params.put("aid", String.valueOf(aid));

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

    public void setQaSolve(int qid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "comment_question_solve");
        params.put("qid", String.valueOf(qid));

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

    public void getUserCanAnswerQuestionListData(int uid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "comment_answer_invite");
        params.put("uid", String.valueOf(uid));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<UserQaCanAnswerInfoVo>() {
                        }.getType();

                        UserQaCanAnswerInfoVo userQaCanAnswerInfoVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(userQaCanAnswerInfoVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }
}
