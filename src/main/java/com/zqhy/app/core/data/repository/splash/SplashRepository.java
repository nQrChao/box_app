package com.zqhy.app.core.data.repository.splash;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.App;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.splash.MarketInfoVo;
import com.zqhy.app.core.vm.ExecuteCallback;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.InitDataVo;
import com.zqhy.app.core.data.model.splash.MarketInitVo;
import com.zqhy.app.core.data.model.splash.SplashVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.IApiService;
import com.zqhy.app.network.rx.RxObserver;
import com.zqhy.app.network.rx.RxSubscriber;
import com.zqhy.app.utils.sp.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.Observable;


public class SplashRepository extends BaseRepository {
    @SuppressLint("CheckResult")
    public void getNetWorkData() {
        SPUtils spUtils = new SPUtils(App.instance(), UserInfoModel.SP_USER_INFO_MODEL);
        int uid = spUtils.getInt(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_UID);
        String auth = spUtils.getString(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_AUTH);

        if (!TextUtils.isEmpty(auth)) {
            Observable<BaseResponseVo> observable1 = iApiService.postClaimData2("auto_login", createLoginByAuthPostBody(uid, auth));
            Observable<BaseResponseVo> observable2 = iApiService.postClaimData2("init", createAppInitPostBody());
            Observable<BaseResponseVo> observable3 = iApiService.postClaimData2("splash_screen", createSplashPostBody());

            Observable.zip(observable1, observable2, observable3, (baseResponseVo, baseResponseVo2, baseResponseVo3) -> {
                        Logs.e("baseResponseVo:" + baseResponseVo);
                        Logs.e("baseResponseVo2:" + baseResponseVo2);
                        Logs.e("baseResponseVo3:" + baseResponseVo3);
                        SplashVo splashVo = new SplashVo();
                        Gson gson = new Gson();

                        String result = gson.toJson(baseResponseVo);
                        UserInfoVo userInfoVo = gson.fromJson(result, new TypeToken<UserInfoVo>() {
                        }.getType());
                        splashVo.setAuthLogin(userInfoVo);
                        if (userInfoVo.isStateOK()) {
                            if (userInfoVo.getData() != null) {
                                getUserInfo(userInfoVo.getData().getUid(), userInfoVo.getData().getToken(), userInfoVo.getData().getUsername());
                                EventBus.getDefault().postSticky(new EventCenter(EventConfig.SHOW_APP_CHANGE_NAME_EVENT_CODE, userInfoVo.getData()));
                            }
                        }

                        String result2 = gson.toJson(baseResponseVo2);
                        splashVo.setAppInit(gson.fromJson(result2, new TypeToken<InitDataVo>() {
                        }.getType()));

                        String result3 = gson.toJson(baseResponseVo3);
                        splashVo.setSplashBeanVo(gson.fromJson(result3, new TypeToken<SplashVo.SplashBeanVo>() {
                        }.getType()));

                        return splashVo;
                    }).compose(RxSchedulers.io_main_o())

                    .subscribeWith(new RxObserver<SplashVo>() {
                        @Override
                        public void onSuccess(SplashVo splashVo) {
                            Logs.e("onSuccess:" + splashVo);
                            sendData(Constants.EVENT_KEY_SPLASH_DATA, splashVo);
                        }

                        @Override
                        public void onFailure(String msg) {

                        }
                    });
        } else {
            Observable<BaseResponseVo> observable2 = iApiService.postClaimData2("init", createAppInitPostBody());
            Observable<BaseResponseVo> observable3 = iApiService.postClaimData2("splash_screen", createSplashPostBody());

            Observable.zip(observable2, observable3, (baseResponseVo2, baseResponseVo3) -> {
                        Logs.e("baseResponseVo2:" + baseResponseVo2);
                        Logs.e("baseResponseVo3:" + baseResponseVo3);
                        SplashVo splashVo = new SplashVo();
                        Gson gson = new Gson();

                        String result2 = gson.toJson(baseResponseVo2);
                        splashVo.setAppInit(gson.fromJson(result2, new TypeToken<InitDataVo>() {
                        }.getType()));

                        String result3 = gson.toJson(baseResponseVo3);
                        splashVo.setSplashBeanVo(gson.fromJson(result3, new TypeToken<SplashVo.SplashBeanVo>() {
                        }.getType()));

                        return splashVo;
                    }).compose(RxSchedulers.io_main_o())

                    .subscribeWith(new RxObserver<SplashVo>() {
                        @Override
                        public void onSuccess(SplashVo splashVo) {
                            Logs.e("onSuccess:" + splashVo);
                            sendData(Constants.EVENT_KEY_SPLASH_DATA, splashVo);
                        }

                        @Override
                        public void onFailure(String msg) {

                        }
                    });
        }
    }

    /**
     * 自动登录接口
     */
    private String createLoginByAuthPostBody(int uid, String auth) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "auto_login");
        params.put("uid", String.valueOf(uid));
        params.put("auth", auth);
        return createPostData(params);
    }

    /**
     * 初始化接口
     */
    private String createAppInitPostBody() {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "init");
        params.put("mobile_type_name", Build.MANUFACTURER);
        return createPostData(params);
    }

    /**
     * 闪屏接口数据
     */
    private String createSplashPostBody() {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "splash_screen");

        return createPostData(params);
    }


    public void getMarketInit(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "market_init");
        params.put("client_type", "1");

        submitParams(params, onNetWorkListener, new ExecuteCallback() {
            @Override
            protected void handlerBaseVo(String result) {
                Logs.e("market_init:" + result);
                if (onNetWorkListener != null) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<MarketInitVo>() {
                    }.getType();
                    onNetWorkListener.onSuccess(gson.fromJson(result, type));
                }
            }
        });
    }

    public void getMarketInfo(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "market_data_appapi");
        params.put("market_data_typeid", "2");

        submitParams(params, onNetWorkListener, new ExecuteCallback() {
            @Override
            protected void handlerBaseVo(String result) {
                Logs.e("market_info:" + result);
                if (onNetWorkListener != null) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<MarketInfoVo>() {
                    }.getType();
                    onNetWorkListener.onSuccess(gson.fromJson(result, type));
                }
            }
        });
    }

    public void getNewInit(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "init");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<InitDataVo>() {
                        }.getType();

                        InitDataVo baseVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(baseVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void sendAdActive(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "ad_active");

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

                    }
                }.addListener(onNetWorkListener)));
    }
}
