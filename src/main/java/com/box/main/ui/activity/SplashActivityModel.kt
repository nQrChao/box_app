package com.box.main.ui.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.box.base.base.viewmodel.BaseViewModel
import com.box.base.callback.databind.StringObservableField
import com.box.base.ext.modRequestWithMsg
import com.box.base.ext.requestFlow
import com.box.base.state.ModResultStateWithMsg
import com.box.com.BuildConfig
import com.box.common.utils.mmkv.MMKVConfig
import com.box.common.data.AndroidStatusRequest
import com.box.common.data.model.ModInitBean
import com.box.common.data.model.ModStatusBean
import com.box.common.network.apiService
import com.box.other.blankj.utilcode.util.AppUtils
import com.box.other.immersionbar.BarHide
import kotlinx.coroutines.launch

enum class NavigationTarget {
    GO_TO_MAIN,  // 去主页
    GO_TO_LOGIN  // 去登录页
}

class SplashActivityModel : BaseViewModel(barHid = BarHide.FLAG_HIDE_BAR, isStatusBarEnabled = true) {
    var pName = StringObservableField(AppUtils.getAppName())
    val navigationEvent = MutableLiveData<NavigationTarget>()
    var androidStatusResult = MutableLiveData<ModResultStateWithMsg<ModStatusBean>>()
    var initializationInfoResult = MutableLiveData<ModResultStateWithMsg<ModInitBean>>()

    /**
     * 启动闪屏页的业务逻辑
     */
    fun start() {
        // 使用协程来处理存在的耗时操作
        viewModelScope.launch {
            // 在这里模拟一些初始化工作，例如网络请求、数据库读取等
            // 我用 delay(2000) 来模拟一个2秒的耗时任务
            //delay(2000)
            // 根据业务逻辑判断跳转目标
            if (MMKVConfig.userInfo == null) {
                // 如果用户信息为空，通知 Activity 跳转到登录页
                navigationEvent.postValue(NavigationTarget.GO_TO_LOGIN)
            } else {
                // 如果用户信息存在，通知 Activity 跳转到主页
                // 在这里你还可以执行一些登录后的初始化操作，比如登录等
                navigationEvent.postValue(NavigationTarget.GO_TO_MAIN)
            }
        }
    }

    fun postInitializationInfoDate() {
        val requestBody = AndroidStatusRequest(
            appId = BuildConfig.MOD_ID,
        )
        modRequestWithMsg(
            { apiService.postInitializationInfo(requestBody) },
            initializationInfoResult,
        )
    }


    fun postAndroidStatusDate() {
        val requestBody = AndroidStatusRequest(
            appId = BuildConfig.MOD_ID,
        )
        modRequestWithMsg(
            { apiService.postAndroidStatus(requestBody) },
            androidStatusResult,
        )
    }


    fun postInitAndStatus() {
        requestFlow {
            val initializationInfo = step(
                block = {
                    val requestBody = AndroidStatusRequest(appId = BuildConfig.MOD_ID)
                    apiService.postInitializationInfo(requestBody)
                },
                resultState = initializationInfoResult,
            )
            val androidStatus = step(
                block = {
                    val requestBody = AndroidStatusRequest(
                        appId = BuildConfig.MOD_ID,
                    )
                    apiService.postAndroidStatus(requestBody)
                },
                resultState = androidStatusResult
            )

        }


    }


}