package com.box.main.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import com.box.base.base.activity.BaseModVmDbActivity
import com.box.base.network.NetState
import com.box.common.sdk.eventViewModel
import com.box.common.utils.loge

import com.zqhy.app.newproject.R
import com.zqhy.app.newproject.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseModVmDbActivity<SplashActivityModel, ActivitySplashBinding>() {
    override val mViewModel: SplashActivityModel by viewModels()
    override fun layoutId(): Int = R.layout.activity_splash

    override fun onNetworkStateChanged(netState: NetState) {

    }

    override fun initView(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        mViewModel.start()
        //throw IllegalArgumentException("are you ok?")
    }

    override fun createObserver() {
        mViewModel.navigationEvent.observe(this) { target ->
            when (target) {
                NavigationTarget.GO_TO_LOGIN -> {
                    // 跳转到登录页
                    // startActivity(Intent(applicationContext, ActivityLogin::class.java))
                    finish() // 跳转后结束当前页
                }
                NavigationTarget.GO_TO_MAIN -> {
                    // 跳转到主页
                    // startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish() // 跳转后结束当前页
                }
                null -> {
                    loge("GO_TO_NULL")
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


    /**********************************************Click**************************************************/
    inner class ProxyClick {
        fun confirm() {

        }

    }
}