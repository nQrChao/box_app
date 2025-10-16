package com.box.main.mod

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.blankj.utilcode.util.ActivityUtils
import com.box.common.data.model.ModUserInfoBean
import com.box.common.event.AppViewModel
import com.box.common.event.EventViewModel
import com.box.common.utils.MMKVUtil
import com.box.main.App
import com.box.main.appViewModel
import com.box.main.eventViewModel
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
    }

    /**
     * 获取用户名。如果为空，则返回空字符串。
     */
    override fun getUserName(): String {
    }

    override fun logout() {

    }

    override fun getUserToken(): String {
    }

    override fun getUserAuth(): String {
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
                val gameDetailInfoFragment = id?.let {
                    GameDetailInfoFragment.newInstance(
                        it.toInt(),
                        2,
                        false,
                        "",
                        false,
                        true
                    )
                }
                FragmentHolderActivity.startFragmentInActivity(context, gameDetailInfoFragment)
            }

            "启动" -> {
                val gameDetailInfoFragment = id?.let {
                    GameDetailInfoFragment.newInstance(
                        it.toInt(),
                        2,
                        false,
                        "",
                        false,
                        true
                    )
                }
                FragmentHolderActivity.startFragmentInActivity(context, gameDetailInfoFragment)
            }

            "领取" -> {
                val intent = Intent(
                    context,
                    MainActivity::class.java
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                intent.putExtra("jingQuJump", "jingQuJump")
                ActivityUtils.startActivity(intent)
            }
        }
    }

    override fun getMarketInitJson(): String {
        return MMKVUtil.getMarketInit() ?: ""
    }

    override fun hasOneKeyLogin(): Boolean {
        return false
    }

    override fun openPay(activity: Activity, price: String) {

    }

    override fun startCurrencyFragment() {
    }

}