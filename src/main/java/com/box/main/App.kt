package com.box.main

import android.app.Application
import com.box.im.glide.GlideApp
import com.box.im.sdk.ImSDK

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ImSDK.instance.init(this)
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
