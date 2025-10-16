package com.box.main

import androidx.multidex.MultiDexApplication
import com.box.com.glide.GlideApp
import com.box.common.AppInit // 确保这里的包名正确
import com.box.main.mod.ModProviderImpl
import com.box.mod.game.ModComService


/**
 * 应用的 Application 类
 */
class App : MultiDexApplication() {

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppInit.init(this)
        ModComService.init(ModProviderImpl())
    }

    override fun onLowMemory() {
        super.onLowMemory()
        GlideApp.get(this).onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        GlideApp.get(this).onTrimMemory(level)
    }
}