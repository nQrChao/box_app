package com.zqhy.app.core.data;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.mvvm.base.AbsRepository;
import com.mvvm.event.LiveBus;
import com.mvvm.http.HttpHelper;
import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.App;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.config.InviteConfig;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.RealNameCheckVo;
import com.zqhy.app.core.data.model.chat.ChatTokenVo;
import com.zqhy.app.core.data.model.chat.ChatUserVo;
import com.zqhy.app.core.data.model.community.UserIntegralVo;
import com.zqhy.app.core.data.model.launch.StasInfo;
import com.zqhy.app.core.data.model.share.InviteDataVo;
import com.zqhy.app.core.data.model.user.CancellationVo;
import com.zqhy.app.core.data.model.user.RefundGamesVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.data.model.user.VerificationCodeVo;
import com.zqhy.app.core.data.model.user.newvip.AppFloatIconVo;
import com.zqhy.app.core.data.model.user.newvip.BirthdayRewardVo;
import com.zqhy.app.core.data.model.user.newvip.SuperBirthdayRewardVo;
import com.zqhy.app.core.data.model.version.VersionVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.inner.OnResponseListener;
import com.zqhy.app.core.tool.AppUtil;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.vm.ExecuteCallback;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.IApiService;
import com.zqhy.app.network.OkGoApiBuilder;
import com.zqhy.app.network.listener.NetworkPollingListener;
import com.zqhy.app.network.rx.RxSubscriber;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.sp.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author：tqzhang on 18/7/26 16:15
 */

public class BaseRepository extends AbsRepository {
    private static final String TAG = BaseRepository.class.getSimpleName();

    public IApiService    iApiService;
    public OkGoApiBuilder okGoApiBuilder;

    public BaseRepository() {
        if (null == iApiService) {
            iApiService = HttpHelper.getInstance().create(URL.getHttpUrl(), IApiService.class);
        }
        okGoApiBuilder = new OkGoApiBuilder();
    }

    @Override
    public void refreshApiService() {
        iApiService = HttpHelper.getInstance().reCreate(URL.getHttpUrl(), IApiService.class);
    }


    protected void sendData(Object eventKey, Object t) {
        sendData(eventKey, null, t);
    }

    protected void sendData(Object eventKey, String tag, Object t) {
        LiveBus.getDefault().postEvent(eventKey, tag, t);
    }

    protected void showPageState(Object eventKey, String state) {
        postPageState(eventKey, null, state);
    }

    protected void showPageState(Object eventKey, String tag, String state) {
        postPageState(eventKey, tag, state);
    }


    /**
     * Event bus事件
     *
     * @param eventKey
     * @param tag
     * @param state
     */
    protected void postPageState(Object eventKey, String tag, String state) {
        String key;
        if (!TextUtils.isEmpty(tag)) {
            key = eventKey + tag;
        } else {
            key = (String) eventKey;
        }
        EventBus.getDefault().post(new EventCenter(EventConfig.PAGE_STATE_EVENT_CODE, key, state));
    }

    /**
     * 请求后台
     *
     * @param params            基本参数
     * @param onNetWorkListener
     */
    public void submitParams(Map<String, String> params, OnNetWorkListener onNetWorkListener, ExecuteCallback executeCallback) {
        submitWithPic(params, null, onNetWorkListener, executeCallback);
    }


    /**
     * 请求后台
     *
     * @param params            基本参数
     * @param fileParams        文件参数
     * @param onNetWorkListener
     */
    public void submitWithPic(Map<String, String> params, Map<String, File> fileParams, OnNetWorkListener onNetWorkListener, ExecuteCallback executeCallback) {
        String data = createPostData(params);
        PostRequest request = OkGo.post(URL.getLhhOkGoHttpUrl()).params("data", data);
        if (fileParams != null) {
            for (String key : fileParams.keySet()) {
                request.params(key, fileParams.get(key));
            }
        }
        request.execute(executeCallback.addListener(onNetWorkListener));
    }


    /**
     * 轮询Api地址
     *
     * @param networkPollingListener
     */
    public void pollingUrls(NetworkPollingListener networkPollingListener) {
        if (okGoApiBuilder != null) {
            okGoApiBuilder.pollingUrls(networkPollingListener);
        }
    }

    public String createPostData(Map<String, String> params) {
        return okGoApiBuilder.createPostData(params);
    }

    protected RequestBody createPostPicData(String data) {
        return RequestBody.create(MediaType.parse("text/plain"), data);
    }

    protected List<MultipartBody.Part> createPostPicPartData(Map<String, File> fileParams) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (String key : fileParams.keySet()) {
            File file = fileParams.get(key);
            Logs.e("key = " + key + "\n" + "value (File) = " + file.getPath());
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData(key, file.getName(), requestFile);
            parts.add(part);
        }
        return parts;
    }

    /**
     * 发送验证码请求
     * 获取手机验证码
     *
     * @param mobile
     * @param isCheck 1:手机号未注册可以发送短信; 2: 手机号注册了才会发送短信
     */
    public void getCode(String mobile, int isCheck, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "get_code");
        params.put("mobile", mobile);
        params.put("is_check", String.valueOf(isCheck));

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), createPostData(params))
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<VerificationCodeVo>() {
                        }.getType();

                        VerificationCodeVo verificationCodeVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(verificationCodeVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 获取用户详细信息
     */
    public void getUserInfo(int uid, String token, String username) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "get_userinfo");

        params.put("uid", String.valueOf(uid));
        params.put("token", token);
        params.put("get_super_user", "y");

        iApiService.postClaimData(URL.getApiUrl(params), createPostData(params))
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseVo);
                        Type type = new TypeToken<UserInfoVo>() {
                        }.getType();
                        UserInfoVo userInfoVo = gson.fromJson(result, type);

                        if (userInfoVo.isStateOK() && userInfoVo.getData() != null) {
                            //添加用户信息
                            UserInfoVo.DataBean dataBean = userInfoVo.getData();
                            dataBean.setUid(uid);
                            dataBean.setUsername(username);
                            dataBean.setToken(token);
                            UserInfoModel.getInstance().login(dataBean);
                        }
                        sendData(Constants.EVENT_KEY_REFRESH_USERINFO_DATA, UserInfoModel.getInstance().getUserInfo());
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
    }


    /**
     * 获取用户详细信息 (单独回调，无全局回调)
     *
     * @param uid
     * @param token
     * @param username
     * @param onNetWorkListener
     */
    public void getUserInfoWithoutNotification(int uid, String token, String username, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "get_userinfo");

        params.put("uid", String.valueOf(uid));
        params.put("token", token);
        params.put("get_super_user", "y");

        iApiService.postClaimData(URL.getApiUrl(params), createPostData(params))
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseVo);
                        Type type = new TypeToken<UserInfoVo>() {
                        }.getType();
                        UserInfoVo userInfoVo = gson.fromJson(result, type);

                        if (userInfoVo.isStateOK() && userInfoVo.getData() != null) {
                            //添加用户信息
                            UserInfoVo.DataBean dataBean = userInfoVo.getData();
                            dataBean.setUid(uid);
                            dataBean.setUsername(username);
                            dataBean.setToken(token);
                            UserInfoModel.getInstance().login(dataBean, false);
                        }
                        sendData(Constants.EVENT_KEY_REFRESH_USERINFO_DATA, UserInfoModel.getInstance().getUserInfo());
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(userInfoVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener));
    }

    /**
     * 获取用户详细信息
     *
     * @param uid
     * @param token
     * @param username
     * @param onResponseListener
     */
    public void getUserInfoCallBack(int uid, String token, String username, OnResponseListener onResponseListener)  {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "get_userinfo");

        params.put("uid", String.valueOf(uid));
        params.put("token", token);
        params.put("get_super_user", "y");

        String data = createPostData(params);

        Map<String, String> map = new TreeMap<>();
        map.put("data", data);

        OkGo.<String>post(URL.getOkGoHttpUrl())
                .params(map)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response == null) {
                            return;
                        }
                        Gson gson = new Gson();
                        String result = response.body();
                        Logs.e("result=" + result);
                        Type type = new TypeToken<UserInfoVo>() {
                        }.getType();
                        UserInfoVo userInfoVo = gson.fromJson(result, type);

                        if (userInfoVo.isStateOK() && userInfoVo.getData() != null) {
                            //添加用户信息
                            UserInfoVo.DataBean dataBean = userInfoVo.getData();
                            dataBean.setUid(uid);
                            dataBean.setUsername(username);
                            dataBean.setToken(token);
                            UserInfoModel.getInstance().login(dataBean);
                        }
                        if (onResponseListener != null) {
                            onResponseListener.onData(userInfoVo);
                        }

                    }
                });
    }

    public void getUserIntegral(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "get_user_integral");

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), createPostData(params))
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseVo);
                        Type type = new TypeToken<UserIntegralVo>() {
                        }.getType();
                        UserIntegralVo userIntegralVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(userIntegralVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }).addListener(onNetWorkListener));
    }

    /**
     * 获取分享数据
     */
    public void getShareData(String tag, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "invite_data");
        params.put("invite_type", tag);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<InviteDataVo>() {
                        }.getType();

                        InviteDataVo dataVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }

    /**
     * 获取分享数据
     */
    public void getShareData(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "invite_data");
        params.put("invite_type", String.valueOf(InviteConfig.invite_type));

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<InviteDataVo>() {
                        }.getType();

                        InviteDataVo dataVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }).addListener(onNetWorkListener));
    }

    /**
     * 实名检测
     *
     * @param type              gold 充值金币
     *                          trade 交易 注：在获取交易验证码前检测
     * @param onNetWorkListener
     */
    public void realNameCheck(String type, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "realname_check");
        params.put("type", type);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<RealNameCheckVo>() {
                        }.getType();

                        RealNameCheckVo dataVo = gson.fromJson(result, type);
                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dataVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }).addListener(onNetWorkListener));
    }


    /**
     * 设置埋点
     */
    public void setPoint(String api_point, Map<String, String> params) {
        if (params == null) {
            params = new TreeMap<>();
        }
        params.put("api", api_point);
        String data = createPostData(params);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }


    private final String SP_IS_SHOW_APP_CHANGE_NAME_DIALOG = "SP_IS_SHOW_APP_CHANGE_NAME_DIALOG";

    /**
     * 设置App更名提示弹窗
     */
    public void showAppChangeNameTipDialog(Activity _mActivity, UserInfoVo.DataBean user) {
        int versionCode = AppUtil.getVersionCode(App.getContext());
        if (versionCode != 10) {
            return;
        }
        final SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
        boolean isShow = spUtils.getBoolean(SP_IS_SHOW_APP_CHANGE_NAME_DIALOG);
        if (isShow) {
            return;
        }
        if (user == null || !user.isOlderUserByVersion9()) {
            return;
        }
        if (_mActivity != null) {
            CustomDialog dialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_change_app_name_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);

            Button mBtnGotIt = dialog.findViewById(R.id.btn_got_it);
            ImageView mIvImage = dialog.findViewById(R.id.iv_image);
            CheckBox mCbButton = dialog.findViewById(R.id.cb_button);

            mCbButton.setText("我已阅读");
            mIvImage.setImageResource(R.mipmap.img_tip_app_change_name);

            GradientDrawable gd2 = new GradientDrawable();
            gd2.setCornerRadius(30 * ScreenUtil.getScreenDensity(_mActivity));
            gd2.setColor(Color.parseColor("#C1C1C1"));
            mBtnGotIt.setBackground(gd2);
            mBtnGotIt.setEnabled(false);
            mBtnGotIt.setText("好  的");
            mBtnGotIt.setOnClickListener(view -> {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                spUtils.putBoolean(SP_IS_SHOW_APP_CHANGE_NAME_DIALOG, true);
            });
            mCbButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    GradientDrawable gd1 = new GradientDrawable();
                    gd1.setCornerRadius(30 * ScreenUtil.getScreenDensity(_mActivity));
                    if (b) {
                        gd1.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                        gd1.setColor(ContextCompat.getColor(_mActivity, R.color.color_0052ef));
                    } else {
                        gd1.setColor(Color.parseColor("#C1C1C1"));
                    }
                    mBtnGotIt.setBackground(gd1);
                    mBtnGotIt.setEnabled(b);
                }
            });

            dialog.show();
        }
    }

    /**
     * 检测用户帐号是否符合注销条件
     */
    public void userCancelCheck(int uid, String token, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "user_cancel_check");
        params.put("uid", String.valueOf(uid));
        params.put("token", token);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), createPostData(params))
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<CancellationVo>() {
                        }.getType();

                        CancellationVo cancellationVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(cancellationVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 通过用户身份获取验证码
     */
    public void getCodeByUser(int uid, String token, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "get_code_by_user");
        params.put("uid", String.valueOf(uid));
        params.put("token", token);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), createPostData(params))
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<VerificationCodeVo>() {
                        }.getType();

                        VerificationCodeVo verificationCodeVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(verificationCodeVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 验证验证码
     */
    public void checkCode(int uid, String token, String code, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "check_code");
        params.put("uid", String.valueOf(uid));
        params.put("token", token);
        params.put("code", code);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), createPostData(params))
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<CancellationVo>() {
                        }.getType();

                        CancellationVo cancellationVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(cancellationVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 通过密码注销用户
     */
    public void userCancel(int uid, String token, String code, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        //params.put("api", "user_cancel2");
        params.put("api", "user_cancel");
        params.put("uid", String.valueOf(uid));
        params.put("token", token);
        //params.put("code", code);
        params.put("password", code);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), createPostData(params))
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<CancellationVo>() {
                        }.getType();

                        CancellationVo cancellationVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(cancellationVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getAppVersion(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "get_version");
        params.put("appid", BuildConfig.APP_UPDATE_ID);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<VersionVo>() {
                        }.getType();

                        VersionVo versionVo = gson.fromJson(result, type);

                        if(onNetWorkListener != null){
                            onNetWorkListener.onSuccess(versionVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }


    /**
     * 获取会员生日奖励状态
     */
    public void getSuperBirthdayRewardStatus(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "super_user_birthday_reward_status");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<SuperBirthdayRewardVo>() {
                        }.getType();

                        SuperBirthdayRewardVo dayRrewardListInfoVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dayRrewardListInfoVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 领取生日奖励
     */
    public void getBirthdayReward(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "super_user_get_birthday_reward");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<BirthdayRewardVo>() {
                        }.getType();

                        BirthdayRewardVo dayRrewardListInfoVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(dayRrewardListInfoVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    /**
     * 领取生日奖励
     */
    public void getRefundGames(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "refund_games");

        String data = createPostData(params);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<RefundGamesVo>() {
                        }.getType();

                        RefundGamesVo refundGamesVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(refundGamesVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void errLog(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        params.put("api", "download_err");
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

    public void chatGetToken(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "chat_get_token");

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<ChatTokenVo>() {
                        }.getType();

                        ChatTokenVo allPopVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(allPopVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }

    public void getUidByAccount(String accid, OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "chat_get_uid_by_accid");
        params.put("accid", accid);

        String data = createPostData(params);

        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<ChatUserVo>() {
                        }.getType();

                        ChatUserVo allPopVo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(allPopVo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }


    public void getStatus(OnNetWorkListener onNetWorkListener) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "market_init");

        String data = createPostData(params);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params), data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                        Gson gson = new Gson();
                        String result = gson.toJson(baseResponseVo);
                        Type type = new TypeToken<StasInfo>() {
                        }.getType();

                        StasInfo stasInfo = gson.fromJson(result, type);

                        if (onNetWorkListener != null) {
                            onNetWorkListener.onSuccess(stasInfo);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }.addListener(onNetWorkListener)));
    }
}
