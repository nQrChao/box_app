package com.zqhy.app.core.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.text.TextUtils
import android.view.KeyEvent
import android.view.WindowManager
import android.webkit.WebView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.chaoji.base.base.activity.BaseVmDbActivity
import com.chaoji.base.ext.parseModState
import com.chaoji.base.ext.parseState
import com.chaoji.base.network.NetState
import com.chaoji.im.checkInstallInHoursDifference
import com.chaoji.im.data.model.MarketInit
import com.chaoji.im.sdk.ImSDK
import com.chaoji.im.sdk.ImSDK.Companion.eventViewModelInstance
import com.chaoji.im.sdk.appViewModel
import com.chaoji.im.utils.MMKVUtil
import com.chaoji.mod.ui.activity.ModActivityMain
import com.chaoji.mod.ui.activity.ModActivityPreview
import com.chaoji.mod.ui.activity.game.ModActivityGameBrowser
import com.chaoji.mod.ui.activity.game.ModActivityLocalGameBrowser
import com.chaoji.mod.ui.xpop.ModXPopupCenterProtocol
import com.chaoji.mod.ui.xpop.ModXPopupCenterTip
import com.chaoji.other.blankj.utilcode.util.ActivityUtils
import com.chaoji.other.blankj.utilcode.util.ColorUtils
import com.chaoji.other.blankj.utilcode.util.GsonUtils
import com.chaoji.other.blankj.utilcode.util.Logs
import com.chaoji.other.blankj.utilcode.util.StringUtils
import com.chaoji.other.hjq.toast.Toaster
import com.chaoji.other.xpopup.XPopup
import com.google.gson.Gson
import com.mvvm.event.LiveBus
import com.umeng.analytics.MobclickAgent
import com.zqhy.app.App
import com.zqhy.app.Setting
import com.zqhy.app.config.AppConfig
import com.zqhy.app.config.Constants
import com.zqhy.app.config.OnPayConfig
import com.zqhy.app.config.WxControlConfig
import com.zqhy.app.core.data.model.AppMenuBeanVo
import com.zqhy.app.core.data.model.splash.AppStyleConfigs
import com.zqhy.app.core.data.model.splash.SplashVo
import com.zqhy.app.core.data.model.splash.SplashVo.AppStyleVo
import com.zqhy.app.core.view.login.LoginActivity
import com.zqhy.app.core.view.login.LoginFragment
import com.zqhy.app.core.view.main.MainActivity
import com.zqhy.app.core.view.splash.AppStyleHelper
import com.zqhy.app.model.UserInfoModel
import com.zqhy.app.network.utils.AppUtils
import com.zqhy.app.newproject.BuildConfig
import com.zqhy.app.newproject.R
import com.zqhy.app.newproject.databinding.AppActivitySplashBinding
import com.zqhy.app.report.AllDataReportAgency
import com.zqhy.app.report.TtDataReportAgency
import com.zqhy.app.utils.AppManager
import com.zqhy.app.utils.cache.ACache
import com.zqhy.app.utils.sp.SPUtils
import com.zqhy.mod.game.GameLauncher
import com.zqhy.sdk.db.SdkManager
import org.greenrobot.eventbus.EventBus
import kotlin.system.exitProcess
import com.chaoji.common.R as RC

@SuppressLint("CustomSplashScreen")
class AppSplashActivity : BaseVmDbActivity<AppSplashModel, AppActivitySplashBinding>() {
    //是否来自SDK跳转
    private var isMod = false
    private var isFromSDK = false
    private val LOGIN_REQUEST_CODE = 100 // 定义一个唯一的请求码
    private lateinit var loginLauncher: ActivityResultLauncher<Intent>

    companion object {
        const val SP_MARKET_INIT: String = "SP_MARKET_INIT"
    }

    private val SP_MARKET_INIT_HAS_STATUS = "SP_MARKET_INIT_HAS_STATUS"
    private val SP_MARKET_INIT_STATUS_VALUE = "SP_MARKET_INIT_STATUS_VALUE"
    private val SP_LAST_TIME_DENY_PERMISSION = "SP_LAST_TIME_DENY_PERMISSION"
    private var targetIntent: Intent? = null


    override fun layoutId(): Int {
        return R.layout.app_activity_splash
    }

    override fun initView(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        //添加到栈中
        AppManager.getInstance().addActivity(this)
        //获取跳转intent
        targetIntent = intent
        isFromSDK = intent.getBooleanExtra("isFromSDK", isFromSDK)
        //检测应用程序在主线程或特定线程上执行的某些潜在的性能问题和不规范行为
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()
        if(packageName == "com.xdyx.modall"){
            agreeInit()
        }else{
            mViewModel.xyInit()
        }
        //throw IllegalArgumentException("are you ok?")
        // 在这里注册专门处理登录结果的回调
        loginLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                // "result" 就是从登录页返回的结果
                if (result.resultCode == RESULT_OK) {
                    // 登录成功！
                    // 在这里处理登录成功后的逻辑，比如刷新用户信息，然后跳转主页
                    startMainActivity()
                } else {
                    // 用户取消登录或登录失败 (result.resultCode == RESULT_CANCELED)
                    Toaster.show("您需要登录才能继续")
                }
            }

        try {
            //获取WebView的USER_AGENT
            val webView = WebView(this)
            Setting.USER_AGENT = webView.settings.userAgentString
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun createObserver() {
        mViewModel.initInfoResult.observe(this) { it ->
            parseState(it, {
                //请求协议后获取的数据，协议标题，内容，用户协议地址，隐私协议地址，防沉迷地址，微信客服地址，备案号，备案号页面地址，自动登录sign
                appViewModel.appInfo.postValue(it)
                //判断是否已经同意协议
                if (!StringUtils.isEmpty(MMKVUtil.getShouQuan())) {
                    agreeInit()
                } else {
                    //还没同意授权，弹窗提示
                    XPopup.Builder(this@AppSplashActivity)
                        .dismissOnTouchOutside(false)
                        .dismissOnBackPressed(false)
                        .isDestroyOnDismiss(true)
                        .hasStatusBar(true)
                        .isLightStatusBar(true)
                        .animationDuration(5)
                        .navigationBarColor(ColorUtils.getColor(RC.color.xpop_shadow_color))
                        .hasNavigationBar(true)
                        .asCustom(
                            it?.let { it1 ->
                                ModXPopupCenterProtocol(this@AppSplashActivity, it1, {
                                    //不同意授权
                                    if (BuildConfig.APP_TEMPLATE == 9999
                                        && BuildConfig.APP_UPDATE_ID != "16"
                                        && BuildConfig.APP_UPDATE_ID != "43") {
                                        ActivityUtils.finishAllActivities()
                                        exitProcess(0)
                                    } else if (BuildConfig.APP_TEMPLATE == 9998) {
                                        ActivityUtils.finishAllActivities()
                                        exitProcess(0)
                                    } else {
                                        //弹预览模式
                                        XPopup.Builder(this@AppSplashActivity)
                                            .hasStatusBar(true)
                                            .animationDuration(5)
                                            .isLightStatusBar(true)
                                            .hasNavigationBar(true)
                                            .isDestroyOnDismiss(true)
                                            .dismissOnBackPressed(false)
                                            .dismissOnTouchOutside(false)
                                            .navigationBarColor(ColorUtils.getColor(RC.color.xpop_shadow_color))
                                            .asCustom(
                                                ModXPopupCenterTip(this@AppSplashActivity, it, {
                                                    //浏览模式，是一张图
                                                    ModActivityPreview.start(this@AppSplashActivity)
                                                }) {
                                                    //返回
                                                })
                                            .show()
                                    }
                                }) {
                                    agreeInit()
                                }
                            })
                        .show()
                }
            }, {
                Toaster.show(it.errorMsg)
            })
        }

        mViewModel.marketInitResult.observe(this) { itData ->
            parseState(itData, {
                MMKVUtil.saveMarketInit(GsonUtils.toJson(it))
                if (it?.status == 1) {
                    mViewModel.netWorkData(false)
                } else {
                    startLocalMain()
                }
            }, {
                Toaster.show(it.errorMsg)
            })
        }

        appViewModel.appInfo.observe(this@AppSplashActivity) {
            AppConfig.setRestoreProtocolUrl(it)
        }

        mViewModel.refundGamesResult.observe(this) { it ->
            parseModState(it, {
                if (it != null) {
                    if (it.ids.isNotEmpty()) {
                        Setting.REFUND_GAME_LIST = it.ids
                        Setting.REFUND_GAME_LIST_TGID = AppUtils.getTgid()
                    }
                }
            }, {
                //Toaster.show(it.msg)
            })
        }

        registerObserver<SplashVo>(Constants.EVENT_KEY_SPLASH_DATA, SplashVo::class.java).observe(
            this,
            Observer<SplashVo> { splashVo: SplashVo? ->
                if (splashVo != null) {
                    //Logs.e("splashVo:", GsonUtils.toJson(splashVo))
                    //showSplash(splashVo.splashBeanVo)
                    val initDataVo = splashVo.appInit
                    if (initDataVo != null && initDataVo.isStateOK) {
                        if (initDataVo.data != null) {
                            //                        WxControlConfig.wx_control = initDataVo.getData().getWx_control();
                            WxControlConfig.wx_control = 1
                            OnPayConfig.setToutiaoReportAmountLimit(
                                initDataVo.data.toutiao_report_amount_limit
                            )
                            AppConfig.setReyun_gonghui_tgids(
                                initDataVo.data.reyun_gonghui_tgids
                            )
                            if (initDataVo.data.toutiao_plug != null) {
                                AppConfig.setToutiao_tgids(
                                    initDataVo.data.toutiao_plug.toutiao_tgids
                                )
                            }
                            //setWxpayInstallPoint(initDataVo.data.wxPay_packagenames)
                            saveAppStyle(initDataVo.data.theme)
                            AppConfig.setHideCommunity(
                                initDataVo.data.hide_community
                            )

                            Setting.PROFILE_SETTING = initDataVo.data.profile_setting
                            Setting.HIDE_FIVE_FIGURE = initDataVo.data.hide_five_figure
                            Setting.CLOUD_STATUS = initDataVo.data.cloud_status
                            Setting.POP_GAMEID = initDataVo.data.pop_gameid
                            //Setting.SHOW_TIPS = initDataVo.data.show_tip
                            //储存appMenu到本地
                            saveAppMenus(initDataVo.data.menu)
                            startMainActivity()
                            Logs.e("startMain")
                        }
                    }
                }
            })

        eventViewModelInstance.startMJ.observe(this) {
            when (it) {
                true -> {
                    startLocalMain()
                    eventViewModelInstance.goTest.value = 1
                }

                false -> {
                    agreeInit()
                    eventViewModelInstance.goTest.value = 2
                }
            }
        }
        eventViewModelInstance.startGame.observe(this) {
            when (it) {
                true -> {
                    startLocalMain()
                    eventViewModelInstance.goTest.value = 1
                }

                false -> {
                    agreeInit()
                    eventViewModelInstance.goTest.value = 2
                }
            }
        }

    }

    /**
     * 保存appMenu
     */
    private fun saveAppMenus(vo: AppMenuBeanVo) {
        val gson = Gson()
        val json = gson.toJson(vo)
        ACache.get(this).put(AppStyleConfigs.APP_MENU_JSON_KEY, json)
    }

    /**
     * 保存App主题样式
     */
    private fun saveAppStyle(dataBean: AppStyleVo.DataBean?) {
        if (dataBean != null) {
            val json = Gson().toJson(dataBean)
            AppStyleHelper.getInstance().handlerAppStyle(this, json, dataBean)
        } else {
            AppStyleHelper.getInstance().removeAppStyleData(this)
        }
    }

    private val events: MutableList<Any> = mutableListOf()
    fun <T> registerObserver(eventKey: Any, tClass: Class<T>?): MutableLiveData<T> {
        return registerObserver(eventKey, null, tClass)
    }

    fun <T> registerObserver(
        eventKey: Any,
        tag: String?,
        tClass: Class<T>?
    ): MutableLiveData<T> {
        val event = if (TextUtils.isEmpty(tag)) {
            eventKey as String
        } else {
            eventKey.toString() + tag
        }
        events.add(event)
        return LiveBus.getDefault().subscribe(eventKey, tag, tClass)
    }

    private fun startLocalMain() {
        if (BuildConfig.APP_TEMPLATE == 9999 || BuildConfig.APP_TEMPLATE == 9998) {
            when (BuildConfig.APP_UPDATE_ID) {
                "11" -> {
                    GameLauncher.startLocalGame(this@AppSplashActivity)
                }
                "1", "16", "43" -> {
                    mViewModel.netWorkData(true)
                    getHandler().postDelayed({
                        ModActivityMain.start(this@AppSplashActivity)
                        finish()
                    }, 1000)
                }
                else -> {
                    GameLauncher.startLocalGame(this@AppSplashActivity, GameLauncher.GAME_URL)
                }
            }
            finish()
        } else {
            mViewModel.netWorkData(true)
            getHandler().postDelayed({
                ModActivityMain.start(this@AppSplashActivity)
                finish()
            }, 1000)

        }
    }





    private fun startMainActivity() {
        //startXDMain()
        val spUtils = SPUtils(App.instance(), UserInfoModel.SP_USER_INFO_MODEL)
        val auth = spUtils.getString(UserInfoModel.KEY_USER_INFO_MODEL_LAST_LOGIN_AUTH)
        if (!TextUtils.isEmpty(auth)) {
            startXDMain()
        } else {
            if (BuildConfig.APP_TEMPLATE == 9999) {
                if (BuildConfig.APP_UPDATE_ID == "11"
                    || BuildConfig.APP_UPDATE_ID == "16"
                    || BuildConfig.APP_UPDATE_ID == "43") {
                    startXDMain()
                } else {
                    GameLauncher.startLocalGame(this@AppSplashActivity, GameLauncher.GAME_URL)
                }
            } else {
                startXDMain()
            }
        }

    }

    private fun startXDMain() {
        val intent = Intent(this@AppSplashActivity, MainActivity::class.java)
        if (BuildConfig.APP_TEMPLATE == 9999 || BuildConfig.APP_TEMPLATE == 9998 || BuildConfig.APP_TEMPLATE == 8888) {
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            intent.putExtra("splashJump", "splashJump")
            Logs.e("startMain", "splashJump")
        } else {
            if (getIntent() != null) {
                val intentJson: String = getIntent().getStringExtra("json") ?: ""
                intent.putExtras(getIntent())
                Logs.e("SDK_TAG:-intentJson:$intentJson")
            }
        }
        ActivityUtils.startActivity(intent)
        finish()
    }


    private fun jumpMainActivity(jumpType: String) {
        val intent = Intent(this@AppSplashActivity, MainActivity::class.java)
        intent.putExtra("gameJump", jumpType)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        ActivityUtils.startActivity(intent)
        finish()
    }


    //同意协议后操作
    private fun agreeInit() {
        //记录已经同意授权
        initPrivacyAndSDKs()
        if(packageName == "com.xdyx.modall"){
            startLocalMain()
            return
        }
        when {
            eventViewModelInstance.goTest.value == 2 -> mViewModel.netWorkData(false)
            BuildConfig.APP_UPDATE_ID == "1" -> mViewModel.netWorkData(false)
            BuildConfig.APP_UPDATE_ID == "16" -> mViewModel.netWorkData(false)
            BuildConfig.APP_UPDATE_ID == "43" -> mViewModel.netWorkData(false)
            else -> {
                val marketInitStr = MMKVUtil.getMarketInit()
                Logs.e("MMKVUtil.getMarketInit():${GsonUtils.toJson(marketInitStr)}")
                if (StringUtils.isEmpty(marketInitStr)) {
                    mViewModel.marketInit()
                } else {
                    val marketInit = GsonUtils.fromJson(marketInitStr, MarketInit::class.java)
                    if (BuildConfig.APP_UPDATE_ID == "11") {
                        val isMarketInit24 = MMKVUtil.isMarketInit24()
                        if (checkInstallInHoursDifference(24) && !isMarketInit24) {
                            mViewModel.marketInit()
                        } else {
                            if (marketInit.status == 1) {
                                mViewModel.netWorkData(false)
                            } else {
                                startLocalMain()
                            }
                        }
                    } else {
                        if (marketInit.status == 1) {
                            mViewModel.netWorkData(false)
                        } else {
                            startLocalMain()
                        }
                    }

                }
            }
        }
    }


    private fun initPrivacyAndSDKs() {
        // 处理隐私协议和 SDK 初始化
        ImSDK.instance.initCNOAID()
        SPUtils(Constants.SP_COMMON_NAME).putBoolean("app_private_yes", true)
        MMKVUtil.saveShouQuan("SQ")
        (application as App).initActionOnSplash(application)
        AllDataReportAgency.getInstance().startApp(application)
        AllDataReportAgency.getInstance().setPrivacyStatus(true)
        SdkManager.getInstance().cacheAppTgid("tsyule")
        mViewModel.getOAID()
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (BuildConfig.APP_TEMPLATE == 9999) {
                if (BuildConfig.APP_UPDATE_ID == "11") {
                    ModActivityLocalGameBrowser.exit(this@AppSplashActivity)
                } else {
                    ModActivityGameBrowser.exit(this@AppSplashActivity)
                }
            }
            return true
        } else {
            return super.dispatchKeyEvent(event)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        targetIntent = intent

    }

    override fun onNetworkStateChanged(it: NetState) {

    }

    public override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
        TtDataReportAgency.getInstance().onResume(this)
    }

    public override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
        TtDataReportAgency.getInstance().onPause(this)
    }

    public override fun onDestroy() {
        super.onDestroy()
        AppManager.getInstance().finishActivity(this)
        EventBus.getDefault().unregister(this)
    }

    /**
     * 检查是否登录
     */
    private fun checkUserLogin(): Boolean {
        if (!UserInfoModel.getInstance().isLogined) {
            if (BuildConfig.DEBUG) {
                val intent = Intent(this, LoginActivity::class.java)
                loginLauncher.launch(intent)
            } else {
                loginLauncher.launch(
                    FragmentHolderActivity.getFragmentInActivity(
                        this,
                        LoginFragment()
                    )
                )
            }
            return false
        } else {
            return true
        }
    }


    inner class ProxyClick {

    }
}