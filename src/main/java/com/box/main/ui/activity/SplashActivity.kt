package com.box.main.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import com.box.base.base.activity.BaseModVmDbActivity
import com.box.base.ext.parseModStateWithMsg
import com.box.base.network.NetState
import com.box.common.AppInit
import com.box.common.utils.mmkv.MMKVConfig
import com.box.common.appContext
import com.box.common.data.model.ModStatusBean
import com.box.common.eventViewModel
import com.box.common.utils.ext.logsE
import com.box.mod.ui.activity.ModActivityMain
import com.box.mod.ui.xpop.ModXPopupCenterProtocol
import com.box.other.blankj.utilcode.util.ColorUtils
import com.box.other.blankj.utilcode.util.GsonUtils
import com.box.other.hjq.toast.Toaster
import com.box.other.xpopup.XPopup
import com.boxapp.project.R
import com.boxapp.project.databinding.ActivitySplashBinding
import com.box.com.R as RC


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseModVmDbActivity<SplashActivityModel, ActivitySplashBinding>() {
    /** 动画是否播放完毕 */
    private var isAnimationFinished = false
    /** 网络请求链（包含 status）是否执行完毕 */
    private var isNetworkChainFinished = false
    /** 临时存储网络请求2的结果 */
    private var tempModStatusData: ModStatusBean? = null
    override val mViewModel: SplashActivityModel by viewModels()
    override fun layoutId(): Int = R.layout.activity_splash

    override fun onNetworkStateChanged(netState: NetState) {

    }

    override fun initView(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        mDataBinding.vm = mViewModel
        mDataBinding.click = ProxyClick()
        mDataBinding.splashLottie.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                logsE("Lottie 动画播放完毕")
                mDataBinding.splashLottie.removeAnimatorListener(this)
                isAnimationFinished = true // 标记动画已完成
                proceedToNextStepIfReady() // 检查是否可以进行下一步
            }
        })
        //立刻开始网络请求，与动画并行
        mViewModel.postInitializationInfoDate()

        //throw IllegalArgumentException("are you ok?")
    }

    override fun createObserver() {
        mViewModel.initializationInfoResult.observe(this) { resultState ->
            parseModStateWithMsg(
                resultState,
                onSuccess = { data, msg ->
                    logsE(GsonUtils.toJson(data))
                    if (MMKVConfig.permissionsUser) {
                        agreeInit()
                    } else {
                        XPopup.Builder(this@SplashActivity)
                            .dismissOnTouchOutside(false)
                            .dismissOnBackPressed(false)
                            .isDestroyOnDismiss(true)
                            .hasStatusBar(true)
                            .isLightStatusBar(true)
                            .animationDuration(5)
                            .navigationBarColor(ColorUtils.getColor(RC.color.xpop_shadow_color))
                            .hasNavigationBar(true)
                            .asCustom(
                                ModXPopupCenterProtocol(this@SplashActivity, data, {
                                    //取消
                                }) {
                                    //同意
                                    agreeInit()
                                }
                            )
                            .show()
                    }
                },
                onError = {
                    Toaster.show(it.msg)
                }
            )
        }

        mViewModel.androidStatusResult.observe(this) { resultState ->
            parseModStateWithMsg(
                resultState,
                onSuccess = { data, msg ->
                    logsE("网络请求链(androidStatus)执行完毕")
                    logsE(GsonUtils.toJson(data))
                    //不在这里直接执行，先存储数据和设置标志位
                    tempModStatusData = data
                    isNetworkChainFinished = true
                    proceedToNextStepIfReady()
                },
                onError = {
                    Toaster.show(it.msg)
                }
            )
        }


        mViewModel.navigationEvent.observe(this) { target ->
            when (target) {
                NavigationTarget.GO_TO_LOGIN -> {
                    // 跳转到登录页
                    // startActivity(Intent(applicationContext, ActivityLogin::class.java))
                    startModMain()
                    finish() // 跳转后结束当前页
                }
                NavigationTarget.GO_TO_MAIN -> {
                    // 跳转到主页
                    // startActivity(Intent(applicationContext, MainActivity::class.java))
                    startModMain()
                    finish() // 跳转后结束当前页
                }
                null -> {
                    logsE("GO_TO_NULL")
                }
            }
        }

        eventViewModel.splashResult.observe(this) {
            if (it) {
                finish()
            } else {
                finish()
            }
        }
    }

    fun startModMain() {
        ModActivityMain.start(appContext)
    }

    private fun agreeInit() {
        MMKVConfig.permissionsUser = true
        AppInit.initCNOAID()
        mViewModel.postAndroidStatusDate()
    }

    /**
     * 检查动画和网络是否均已完成
     */
    private fun proceedToNextStepIfReady() {
        logsE("检查是否就绪: 动画是否完成=$isAnimationFinished, 网络是否完成=$isNetworkChainFinished")
        // 只有当两个标志位都为 true 时，才执行最终操作
        if (isAnimationFinished && isNetworkChainFinished) {
            logsE("两者均已就绪，执行下一步")
            // 使用我们临时存储的数据
            tempModStatusData?.let {
                MMKVConfig.modStatus = it
                //做状态判断切包是否
                mViewModel.start()
            } ?: run {
                // 理论上 isNetworkChainFinished 为 true 时，tempModStatusData 不应为 null
                // 作为保险，添加一个日志
                logsE("错误: 网络已完成，但数据为空!")
            }
        }
    }

    /**********************************************Click**************************************************/
    inner class ProxyClick {
        fun confirm() {

        }

    }
}