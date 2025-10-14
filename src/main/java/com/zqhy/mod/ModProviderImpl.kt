package com.zqhy.mod

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.chaoji.im.data.model.ModUserInfoBean
import com.chaoji.im.utils.MMKVUtil
import com.chaoji.mod.game.ModProvider
import com.chaoji.mod.ui.activity.ModActivityMain.Companion.activity
import com.chaoji.other.blankj.utilcode.util.ActivityUtils
import com.zqhy.app.App
import com.zqhy.app.config.InviteConfig
import com.zqhy.app.core.data.model.user.UserInfoVo
import com.zqhy.app.core.view.FragmentHolderActivity
import com.zqhy.app.core.view.currency.CurrencyMainFragment
import com.zqhy.app.core.view.game.GameDetailInfoFragment
import com.zqhy.app.core.view.login.LoginActivity
import com.zqhy.app.core.view.login.event.AuthLoginEvent
import com.zqhy.app.core.view.main.MainActivity
import com.zqhy.app.core.view.user.BindPhoneFragment
import com.zqhy.app.core.view.user.CancellationOneFragment
import com.zqhy.app.core.view.user.UserInfoFragment
import com.zqhy.app.model.UserInfoModel

class ModProviderImpl : ModProvider {

    override fun getApplicationContext(): Context {
        return App.instance()
    }

    // 使用 by lazy 可以在第一次使用时才初始化，并且保证线程安全
    private val spUtils by lazy {
        com.zqhy.app.utils.sp.SPUtils(App.getContext(), UserInfoModel.SP_USER_INFO_MODEL)
    }

    /**
     * 判断用户是否已登录。
     */
    override fun isUserLoggedIn(): Boolean {
        return UserInfoModel.getInstance().isLogined
    }

    override fun userLogout(activity: Activity) {
        FragmentHolderActivity.startFragmentInActivity(activity, CancellationOneFragment.newInstance())
    }

    /**
     * 获取用户 UID。
     */
    override fun getUserUid(): String {
        return spUtils.getInt(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_UID, 0).toString()
    }

    /**
     * 获取用户名。如果为空，则返回空字符串。
     */
    override fun getUserName(): String {
        return spUtils.getString(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_USERNAME) ?: ""
    }

    override fun logout() {
        UserInfoModel.getInstance().logout()
        spUtils.remove(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_USERNAME)
        spUtils.remove(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_AUTH)
        spUtils.remove(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_UID)
        spUtils.remove(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_TOKEN)
        InviteConfig.invite_type = 0
    }

    /**
     * 获取用户 Token。
     * 返回值设为可空的 String?，因为 Token 可能不存在。
     */
    override fun getUserToken(): String {
        return spUtils.getString(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_TOKEN) ?: ""
    }

    override fun getUserAuth(): String {
        return spUtils.getString(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_AUTH) ?: ""
    }

    override fun startLoginActivity(activity: Activity) {
        val intent = Intent(activity, LoginActivity::class.java)
        ActivityUtils.startActivity(intent)
    }

    override fun startLoginActivity(activity: Activity, loginLauncher: ActivityResultLauncher<Intent>) {
        // 满足任一条件，则启动 ModGameLoginActivity:
//        if (BuildConfig.DEBUG || eventViewModel.goTest.value in 1..2) {
//            val intent = Intent(activity, ModGameLoginActivity::class.java)
//            loginLauncher.launch(intent)
//        } else {
//            // 否则 (即非 DEBUG 环境且 goTest 值不是 1 或 2)，启动 GameLoginFragment
//            val intent = FragmentHolderActivity.getFragmentInActivity(activity, GameLoginFragment())
//            loginLauncher.launch(intent)
//        }
        val intent = Intent(activity, LoginActivity::class.java)
        intent.putExtra("game", "gameLogin")
        loginLauncher.launch(intent)
    }

    override fun startBindPhone(activity: Activity, loginLauncher: ActivityResultLauncher<Intent>) {
        val intent = FragmentHolderActivity.getFragmentInActivity(activity, BindPhoneFragment())
        intent.putExtra("game", "gameBind")
        loginLauncher.launch(intent)
    }

    override fun startModLoginActivity(activity: Activity, loginLauncher: ActivityResultLauncher<Intent>) {
        val intent = Intent(activity, LoginActivity::class.java)
        intent.putExtra("game", "gameLogin")
        loginLauncher.launch(intent)
    }

    override fun startMainActivity(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        intent.putExtra("detailJump", "detailJump")
        ActivityUtils.startActivity(intent)
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

    private fun handlerUserResponse(modUserInfo: ModUserInfoBean, loginAccount: String, password: String): ModUserInfoBean {
        modUserInfo.password = password
        modUserInfo.login_account = loginAccount
        //添加用户信息
        val dataBean = UserInfoVo.DataBean()
        dataBean.token = modUserInfo.token
//        dataBean.recall_pop = recall_pop
//        dataBean.isCan_bind_password = can_bind_password
//        dataBean.act = act
//        dataBean.elevate = elevate
        UserInfoModel.getInstance().login(dataBean)
        return modUserInfo
    }

    override fun hasOneKeyLogin(): Boolean {
        return AuthLoginEvent.instance().isSupport
    }

    override fun openPay(activity: Activity, price: String) {

    }

    override fun startCurrencyFragment() {
        FragmentHolderActivity.startFragmentInActivity(ActivityUtils.getTopActivity(), CurrencyMainFragment())
    }

}