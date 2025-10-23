package com.box.main.mod

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.box.common.AppInit
import com.box.common.MMKVConfig
import com.box.common.data.model.ModInfosBean
import com.box.main.App
import com.box.mod.game.ModProvider

class ModProviderImpl : ModProvider {

    override fun getApplicationContext(): Context {
        return App.instance
    }

    /**
     * 判断用户是否已登录。
     */
    override fun isUserLoggedIn(): Boolean {
        return false
    }

    override fun userLogout(activity: Activity) {
    }

    /**
     * 获取用户 UID。
     */
    override fun getUserUid(): String {
        return ""
    }

    /**
     * 获取用户名。如果为空，则返回空字符串。
     */
    override fun getUserName(): String {
        return ""
    }

    override fun logout() {

    }

    override fun getUserToken(): String {
        return ""
    }

    override fun getUserAuth(): String {
        return ""
    }

    override fun startLoginActivity(activity: Activity) {

    }

    override fun startLoginActivity(activity: Activity, loginLauncher: ActivityResultLauncher<Intent>) {

    }

    override fun startBindPhone(activity: Activity, loginLauncher: ActivityResultLauncher<Intent>) {

    }

    override fun startModLoginActivity(activity: Activity, loginLauncher: ActivityResultLauncher<Intent>) {

    }

    override fun startMainActivity(context: Context) {

    }

    override fun handleJumpAction(context: Context, action: String) {
        val parts = action.split("_")
        val type = parts.getOrNull(0)
        val id = parts.getOrNull(1)
        when (type) {
            "详情" -> {

            }

            "启动" -> {

            }

            "领取" -> {

            }
        }
    }

    override fun getMarketInitJson(): String {
        return MMKVConfig.modInit.toString()
    }

    override fun hasOneKeyLogin(): Boolean {
        return false
    }

    override fun openPay(activity: Activity, price: String) {

    }

    override fun startCurrencyFragment() {

    }

    override fun getModInfos() : ModInfosBean{
        return AppInit.getModInfos()
    }



}