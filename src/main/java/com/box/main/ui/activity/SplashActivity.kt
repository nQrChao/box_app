package com.box.main.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.box.base.base.activity.BaseVmDbActivity
import com.box.base.network.NetState
import com.box.im.sdk.ImSDK
import com.box.im.sdk.eventViewModel
import com.box.im.ui.activity.login.ActivityLogin
import com.box.im.utils.MMKVUtil
import com.zqhy.app.newproject.R
import com.zqhy.app.newproject.databinding.ActivitySplashBinding

class SplashActivity: BaseVmDbActivity<SplashModel, ActivitySplashBinding>() {
    override fun layoutId(): Int {
        return R.layout.activity_splash
    }

    override fun onNetworkStateChanged(it: NetState) {

    }

    override fun initView(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)

        val appUserInfo= MMKVUtil.getUser()
        if (appUserInfo==null){
            Handler(Looper.getMainLooper()).postDelayed({
                  startActivity(Intent(applicationContext, ActivityLogin::class.java))
                  finish()
            },2000)
        }else{
            Handler(Looper.getMainLooper()).postDelayed({
                //登录IM
                ImSDK.instance.imLogin(appUserInfo)
            },2000)
        }
        //throw IllegalArgumentException("are you ok?")
    }

    override fun createObserver() {
        eventViewModel.splashResult.observe(this){
            if (it){
                startActivity(Intent(applicationContext, ActivityMain::class.java))
                finish()
            }else{
                startActivity(Intent(applicationContext, ActivityLogin::class.java))
                finish()
            }
        }
    }
}