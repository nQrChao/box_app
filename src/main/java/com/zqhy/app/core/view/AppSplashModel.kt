package com.zqhy.app.core.view

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.chaoji.base.base.viewmodel.BaseViewModel
import com.chaoji.base.ext.request
import com.chaoji.base.state.ResultState
import com.chaoji.im.appContext
import com.chaoji.im.checkInstallInHoursDifference
import com.chaoji.im.checkInstallInMinuteDifference
import com.chaoji.im.data.model.AppletsData
import com.chaoji.im.data.model.MarketInit
import com.chaoji.im.network.NetworkApi
import com.chaoji.im.network.apiService
import com.chaoji.im.utils.MMKVUtil
import com.chaoji.other.blankj.utilcode.util.AppUtils
import com.chaoji.other.blankj.utilcode.util.Logs
import com.chaoji.other.immersionbar.BarHide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mvvm.event.LiveBus
import com.mvvm.http.HttpHelper
import com.mvvm.http.rx.RxSchedulers
import com.zqhy.app.App
import com.zqhy.app.config.Constants
import com.zqhy.app.config.EventConfig
import com.zqhy.app.config.URL
import com.zqhy.app.core.data.model.BaseResponseVo
import com.zqhy.app.core.data.model.InitDataVo
import com.zqhy.app.core.data.model.splash.SplashVo
import com.zqhy.app.core.data.model.splash.SplashVo.SplashBeanVo
import com.zqhy.app.core.data.model.user.UserInfoVo
import com.zqhy.app.core.ui.eventbus.EventCenter
import com.zqhy.app.model.UserInfoModel
import com.zqhy.app.network.IApiService
import com.zqhy.app.network.OkGoApiBuilder
import com.zqhy.app.network.rx.RxObserver
import com.zqhy.app.network.rx.RxSubscriber
import com.zqhy.app.newproject.BuildConfig
import com.zqhy.app.utils.sp.SPUtils
import io.reactivex.Observable
import org.greenrobot.eventbus.EventBus
import java.util.TreeMap

class AppSplashModel : BaseViewModel(barHid = BarHide.FLAG_HIDE_BAR, isStatusBarEnabled = true) {
    var iApiService: IApiService = HttpHelper.getInstance().create(
        URL.getHttpUrl(),
        IApiService::class.java
    )
    private var okGoApiBuilder: OkGoApiBuilder = OkGoApiBuilder()
    var initInfoResult = MutableLiveData<ResultState<AppletsData>>()
    var marketInitResult = MutableLiveData<ResultState<MarketInit>>()
    fun xyInit() {
        request({
            val map = mutableMapOf<String, String>()
            map["api"] = "market_data_appapi"
            map["market_data_typeid"] = "2"
            apiService.postDataAppApi(NetworkApi.INSTANCE.createPostData(map)!!)
        }, initInfoResult)
    }

    fun marketInit() {
        request({
            val map = mutableMapOf<String, String>()
            map["api"] = "market_init"
            if (BuildConfig.APP_UPDATE_ID == "11") {
                val isMarketInit24 = MMKVUtil.isMarketInit24()
                if (checkInstallInHoursDifference(24) && !isMarketInit24) {
                    val packageInfo = appContext.packageManager.getPackageInfo(appContext.packageName, PackageManager.GET_PERMISSIONS)
                    map["lastUpdateTime"] = packageInfo.lastUpdateTime.toString()
                    MMKVUtil.setMarketInit24(true)
                }
            }
            apiService.marketInit(NetworkApi.INSTANCE.createPostData(map)!!)
        }, marketInitResult)
    }



    @SuppressLint("CheckResult")
    fun netWorkData(isMod: Boolean) {
        val spUtils = SPUtils(App.instance(), UserInfoModel.SP_USER_INFO_MODEL)
        val uid = spUtils.getInt(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_UID)
        val auth = spUtils.getString(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_AUTH)
        Logs.e("KEY_USER_INFO_MODEL_LAST_LOGIN_AUTH2:", auth)
        if (!TextUtils.isEmpty(auth)) {
            val observable1: Observable<BaseResponseVo<*>> =
                iApiService.postClaimData2("auto_login", createLoginByAuthPostBody(uid, auth))
            val observable2: Observable<BaseResponseVo<*>> =
                iApiService.postClaimData2("init", createAppInitPostBody())
            val observable3: Observable<BaseResponseVo<*>> =
                iApiService.postClaimData2("splash_screen", createSplashPostBody())

            Observable.zip<BaseResponseVo<*>, BaseResponseVo<*>, BaseResponseVo<*>, SplashVo>(
                observable1, observable2, observable3
            ) { baseResponseVo: BaseResponseVo<*>, baseResponseVo2: BaseResponseVo<*>, baseResponseVo3: BaseResponseVo<*> ->
                //Logs.e("baseResponseVo:$baseResponseVo")
                //Logs.e("baseResponseVo2:$baseResponseVo2")
                //Logs.e("baseResponseVo3:$baseResponseVo3")
                val splashVo = SplashVo()
                val gson = Gson()

                val result = gson.toJson(baseResponseVo)
                val userInfoVo = gson.fromJson<UserInfoVo>(result, object : TypeToken<UserInfoVo?>() {}.type)
                splashVo.authLogin = userInfoVo
                if (userInfoVo.isStateOK) {
                    if (userInfoVo.data != null) {
                        getUserInfo(
                            userInfoVo.data.uid,
                            userInfoVo.data.token,
                            userInfoVo.data.username
                        )
                        UserInfoModel.getInstance().login(userInfoVo.data, false)
                        if (!isMod) {
                            EventBus.getDefault().postSticky(EventCenter<Any?>(EventConfig.SHOW_APP_CHANGE_NAME_EVENT_CODE, userInfoVo.data))
                        }
                    }
                }

                val result2 = gson.toJson(baseResponseVo2)
                splashVo.appInit = gson.fromJson(result2, object : TypeToken<InitDataVo?>() {}.type)

                val result3 = gson.toJson(baseResponseVo3)
                splashVo.splashBeanVo = gson.fromJson(result3, object : TypeToken<SplashBeanVo?>() {}.type)
                splashVo
            }.compose<SplashVo>(RxSchedulers.io_main_o<SplashVo>())
                .subscribeWith(object : RxObserver<SplashVo>() {
                    override fun onSuccess(splashVo: SplashVo) {
                        if (!isMod) {
                            sendData(Constants.EVENT_KEY_SPLASH_DATA, splashVo)
                        }
                    }

                    override fun onFailure(msg: String) {
                    }
                })

        } else {
            val observable2 = iApiService.postClaimData2("init", createAppInitPostBody())
            val observable3 = iApiService.postClaimData2("splash_screen", createSplashPostBody())
            Observable.zip(
                observable2,
                observable3
            ) { baseResponseVo2: BaseResponseVo<*>, baseResponseVo3: BaseResponseVo<*> ->
                //Logs.e("baseResponseVo2:$baseResponseVo2")
                //Logs.e("baseResponseVo3:$baseResponseVo3")
                val splashVo = SplashVo()
                val gson = Gson()

                val result2 = gson.toJson(baseResponseVo2)
                splashVo.appInit = gson.fromJson(result2, object : TypeToken<InitDataVo?>() {}.type)

                val result3 = gson.toJson(baseResponseVo3)
                splashVo.splashBeanVo = gson.fromJson(result3, object : TypeToken<SplashBeanVo?>() {}.type)
                splashVo
            }.compose(RxSchedulers.io_main_o())
                .subscribeWith(object : RxObserver<SplashVo>() {
                    override fun onSuccess(splashVo: SplashVo) {
                        Logs.e("onSuccess:$splashVo")
                        if (!isMod) {
                            sendData(Constants.EVENT_KEY_SPLASH_DATA, splashVo)
                        }
                    }

                    override fun onFailure(msg: String) {
                    }
                })
        }

    }


    /**
     * 自动登录接口
     */
    private fun createLoginByAuthPostBody(uid: Int, auth: String): String {
        val params: MutableMap<String, String> = TreeMap()
        params["api"] = "auto_login"
        params["uid"] = uid.toString()
        params["auth"] = auth
        return NetworkApi.INSTANCE.createPostData(params)!!
    }

    /**
     * 初始化接口
     */
    private fun createAppInitPostBody(): String {
        val params: MutableMap<String, String> = TreeMap()
        params["api"] = "init"
        params["mobile_type_name"] = Build.MANUFACTURER
        return NetworkApi.INSTANCE.createPostData(params)!!
    }

    /**
     * 闪屏接口数据
     */
    private fun createSplashPostBody(): String {
        val params: MutableMap<String, String> = TreeMap()
        params["api"] = "splash_screen"
        return NetworkApi.INSTANCE.createPostData(params)!!
    }

    /**
     * 获取用户详细信息
     */
    @SuppressLint("CheckResult")
    fun getUserInfo(uid: Int, token: String, username: String?) {
        val params: MutableMap<String, String> = TreeMap()
        params["api"] = "get_userinfo"
        params["uid"] = uid.toString()
        params["token"] = token
        params["get_super_user"] = "y"

        iApiService.postClaimData(URL.getApiUrl(params), okGoApiBuilder.createPostData(params))
            .compose<BaseResponseVo<*>>(RxSchedulers.io_main<BaseResponseVo<*>>())
            .subscribeWith(object : RxSubscriber<BaseResponseVo<*>>(params) {
                override fun onSuccess(baseVo: BaseResponseVo<*>) {
                    val gson = Gson()
                    val result = gson.toJson(baseVo)
                    val type = object : TypeToken<UserInfoVo?>() {
                    }.type
                    val userInfoVo = gson.fromJson<UserInfoVo>(result, type)

                    if (userInfoVo.isStateOK && userInfoVo.data != null) {
                        //添加用户信息
                        val dataBean = userInfoVo.data
                        dataBean.uid = uid
                        dataBean.username = username
                        dataBean.token = token
                        UserInfoModel.getInstance().login(dataBean)
                    }
                    sendData(
                        Constants.EVENT_KEY_REFRESH_USERINFO_DATA,
                        UserInfoModel.getInstance().userInfo
                    )
                }


                override fun onFailure(msg: String) {
                }
            })
    }

    fun sendData(eventKey: Any?, t: Any?) {
        sendData(eventKey, null, t!!)
    }

    private fun sendData(eventKey: Any?, tag: String?, t: Any) {
        LiveBus.getDefault().postEvent(eventKey, tag, t)
    }




}