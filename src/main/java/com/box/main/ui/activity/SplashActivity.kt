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
import com.box.common.MMKVConfig
import com.box.common.appContext
import com.box.common.eventViewModel
import com.box.common.utils.logsE
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
                mDataBinding.splashLottie.removeAnimatorListener(this)
                mViewModel.postInitializationInfoDate()
            }
        })


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
        mViewModel.start()
    }

    /**********************************************Click**************************************************/
    inner class ProxyClick {
        fun confirm() {

        }

    }
}