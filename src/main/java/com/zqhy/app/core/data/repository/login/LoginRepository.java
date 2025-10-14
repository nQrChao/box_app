package com.zqhy.app.core.data.repository.login;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.rx.RxSubscriber;

import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * @author Administrator
 * @date 2018/11/2
 */
public class LoginRepository extends BaseRepository {


    private String token;
    private int    recall_pop;
    private String xx;
    private boolean can_bind_password = false;
    private String act = "";
    private int elevate;

    /**
     * 登录 用户名密码
     *
     * @param username
     * @param password
     * @param onNetWorkListener
     */
    public void login(String username, String password, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "login");
        params.put("username", username);
        params.put("password", password);

        String data = createPostData(params);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .observeOn(Schedulers.io())
                .flatMap(new Function<BaseResponseVo, Publisher<BaseResponseVo>>() {
                    @Override
                    public Publisher<BaseResponseVo> apply(@NonNull BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<UserInfoVo>() {
                        }.getType();

                        UserInfoVo userInfoVo = gson.fromJson(result, type);
                        if (userInfoVo != null && userInfoVo.getData() != null) {
                            act = userInfoVo.getData().getAct();
                            elevate = userInfoVo.getData().getElevate();
                        }
                        return mergeUserInfo(baseResponseVo, password, onNetWorkListener);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        UserInfoVo userInfoVo = handlerUserResponse(baseResponseVo, username);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(userInfoVo);
                        }
                        sendData(Constants.EVENT_KEY_REFRESH_USERINFO_DATA, UserInfoModel.getInstance().getUserInfo());
                    }

                    @Override
                    public void onFailure(String msg) {
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onFailure(msg);
                        }
                    }
                }).addListener(onNetWorkListener));
/*

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {

                    @Override
                    public void onSuccess(BaseResponseVo baseVo) {
                        Logs.e("onSuccess:" + baseVo);
                        Gson gson = new Gson();
                        String result = gson.toJson(baseVo);
                        Type type = new TypeToken<UserInfoVo>() {
                        }.getType();

                        UserInfoVo userInfoVo = gson.fromJson(result, type);
                        if (userInfoVo.getData() != null) {
                            userInfoVo.getData().setPassword(password);
                        }
                        userInfoVo.setLoginAccount(username);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(userInfoVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        Logs.e("onFailure:" + msg);
                    }
                }.addListener(onNetWorkListener)));
 */
    }


    /**
     * 手机号注册接口
     *
     * @param mobile
     * @param code
     * @param password
     * @param onNetWorkListener
     */
    public void mobileRegister(String mobile, String code, String password, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "mobile_register");
        params.put("mobile", mobile);
        params.put("code", code);
        params.put("password", password);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .observeOn(Schedulers.io())
                .flatMap(new Function<BaseResponseVo, Publisher<BaseResponseVo>>() {
                    @Override
                    public Publisher<BaseResponseVo> apply(@NonNull BaseResponseVo baseResponseVo) throws Exception {
                        return mergeUserInfo(baseResponseVo, password, onNetWorkListener);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseVo) {
                        UserInfoVo userInfoVo = handlerUserResponse(baseVo, mobile);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(userInfoVo);
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
     * 自定义帐号注册
     *
     * @param username
     * @param password
     * @param onNetWorkListener
     * @return
     */
    public void userNameRegister(String username, String password, OnNetWorkListener onNetWorkListener) {

        Map<String, String> params = new TreeMap<>();
        params.put("api", "account_register");
        params.put("username", username);
        params.put("password", password);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .observeOn(Schedulers.io())
                .flatMap(new Function<BaseResponseVo, Publisher<BaseResponseVo>>() {
                    @Override
                    public Publisher<BaseResponseVo> apply(@NonNull BaseResponseVo baseResponseVo) throws Exception {
                        return mergeUserInfo(baseResponseVo, password, onNetWorkListener);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseVo) {
                        UserInfoVo userInfoVo = handlerUserResponse(baseVo, username);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(userInfoVo);
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
     * 创建 getUserInfo Observable
     * @param password
     * @return
     */
    private Publisher<BaseResponseVo> mergeUserInfo(BaseResponseVo baseResponseVo, String password, OnNetWorkListener onNetWorkListener) {
        Gson gson = new Gson();
        String result = gson.toJson(baseResponseVo);
        Type type = new TypeToken<UserInfoVo>() {
        }.getType();
        UserInfoVo userInfoVo = gson.fromJson(result, type);
        if (!userInfoVo.isStateOK()) {
            return Flowable.just(baseResponseVo);
        }
        xx = password;
        Map<String, String> params = new TreeMap<>();
        params.put("api", "get_userinfo");
        token = userInfoVo.getData().getToken();
        recall_pop = userInfoVo.getData().getRecall_pop();
        params.put("get_super_user", "y");
        params.put("uid", String.valueOf(userInfoVo.getData().getUid()));
        params.put("token", userInfoVo.getData().getToken());

        return iApiService.postClaimData(URL.getApiUrl(params), createPostData(params));
    }

    /**
     * 统一处理getUserInfo response
     *
     * @param baseResponseVo
     * @param loginAccount
     * @return
     */
    @NotNull
    private UserInfoVo handlerUserResponse(BaseResponseVo baseResponseVo, String loginAccount) {
        Gson gson = new Gson();
        String result = gson.toJson(baseResponseVo);
        Type type = new TypeToken<UserInfoVo>() {
        }.getType();
        UserInfoVo userInfoVo = gson.fromJson(result, type);

        if (userInfoVo.getData() != null) {
            userInfoVo.getData().setPassword(xx);
        }
        userInfoVo.setLoginAccount(loginAccount);

        if (userInfoVo.isStateOK() && userInfoVo.getData() != null) {
            //添加用户信息
            UserInfoVo.DataBean dataBean = userInfoVo.getData();
            dataBean.setToken(token);
            dataBean.setRecall_pop(recall_pop);
            dataBean.setCan_bind_password(can_bind_password);
            dataBean.setAct(act);
            dataBean.setElevate(elevate);
            UserInfoModel.getInstance().login(dataBean);
        }
        return userInfoVo;
    }

    /**
     * 找回密码
     *
     * @param mobilePhone
     * @param code
     * @param newpwd
     * @param onNetWorkListener
     */
    public void resetLoginPassword(String mobilePhone, String code, String newpwd, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "get_pwd");
        params.put("mobile", mobilePhone);
        params.put("code", code);
        params.put("password", newpwd);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
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
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onFailure(msg);
                        }
                    }
                }.addListener(onNetWorkListener)));

    }

    /**
     * 一键登录
     *
     * @param one_key_token
     * @param onNetWorkListener
     */
    public void oneKeyLogin(String one_key_token, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "one_key_login");
        params.put("one_key_token", one_key_token);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .observeOn(Schedulers.io())
                .flatMap(new Function<BaseResponseVo, Publisher<BaseResponseVo>>() {
                    @Override
                    public Publisher<BaseResponseVo> apply(@NonNull BaseResponseVo baseResponseVo) throws Exception {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<UserInfoVo>() {
                        }.getType();
                        UserInfoVo userInfoVo = gson.fromJson(result, type);
                        if (userInfoVo != null && userInfoVo.getData() != null) {
                            can_bind_password = userInfoVo.getData().isCan_bind_password();
                            act = userInfoVo.getData().getAct();
                            elevate = userInfoVo.getData().getElevate();
                        }
                        return mergeUserInfo(baseResponseVo, "", onNetWorkListener);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseVo) {
                        UserInfoVo userInfoVo = handlerUserResponse(baseVo, "");
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(userInfoVo);
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
     * 手机号登录
     *
     * @param mobile
     * @param code
     * @param onNetWorkListener
     */
    public void mobileAutoLogin(String mobile, String code, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "mobile_auto_login");
        params.put("mobile", mobile);
        params.put("code", code);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .observeOn(Schedulers.io())
                .flatMap(new Function<BaseResponseVo, Publisher<BaseResponseVo>>() {
                    @Override
                    public Publisher<BaseResponseVo> apply(@NonNull BaseResponseVo baseResponseVo) throws Exception {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<UserInfoVo>() {
                        }.getType();
                        UserInfoVo userInfoVo = gson.fromJson(result, type);
                        if (userInfoVo != null && userInfoVo.getData() != null) {
                            can_bind_password = userInfoVo.getData().isCan_bind_password();
                            act = userInfoVo.getData().getAct();
                            elevate = userInfoVo.getData().getElevate();
                        }
                        return mergeUserInfo(baseResponseVo, "", onNetWorkListener);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseVo) {
                        UserInfoVo userInfoVo = handlerUserResponse(baseVo, mobile);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(userInfoVo);
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
     * 绑定密码
     */
    public void bindPassword(String password, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "bind_password");
        params.put("password", password);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .observeOn(Schedulers.io())
                .flatMap(new Function<BaseResponseVo, Publisher<BaseResponseVo>>() {
                    @Override
                    public Publisher<BaseResponseVo> apply(@NonNull BaseResponseVo baseResponseVo) throws Exception {
                        return mergeUserInfo(baseResponseVo, password, onNetWorkListener);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<UserInfoVo>() {
                        }.getType();
                        UserInfoVo userInfoVo = gson.fromJson(result, type);

                        if (userInfoVo.getData() != null) {
                            userInfoVo.getData().setPassword(password);
                        }
                        userInfoVo.setLoginAccount(userInfoVo.getLoginAccount());
                        if (userInfoVo.isStateOK() && userInfoVo.getData() != null) {
                            //添加用户信息
                            UserInfoVo.DataBean dataBean = userInfoVo.getData();
                            dataBean.setToken(token);
                            dataBean.setRecall_pop(userInfoVo.getData().getRecall_pop());
                            dataBean.setCan_bind_password(false);
                            UserInfoModel.getInstance().login(dataBean);
                        }
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(userInfoVo);
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
