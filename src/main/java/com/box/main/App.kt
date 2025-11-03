package com.box.main

import androidx.multidex.MultiDexApplication
import com.box.common.AppInit
import com.box.common.glide.GlideApp
import com.box.main.mod.ModProviderImpl
import com.box.mod.game.ModComService

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