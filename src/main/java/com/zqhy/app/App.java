package com.zqhy.app;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.multidex.BuildConfig;

import com.blankj.utilcode.util.ProcessUtils;
import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.chaoji.im.sdk.ApkUtils;
import com.chaoji.im.sdk.ImSDK;
import com.chaoji.other.blankj.utilcode.util.Logs;
import com.danikula.videocache.HttpProxyCacheServer;
import com.lzy.okserver.OkDownload;
import com.mvvm.http.HttpHelper;
import com.mvvm.stateview.EmptyDataState;
import com.mvvm.stateview.ErrorState;
import com.mvvm.stateview.LoadingState;
import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.tqzhang.stateview.core.LoadState;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.xuexiang.xui.XUI;
import com.zqhy.app.core.BaseApplication;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.main.MainActivity;
import com.zqhy.app.crash.CrashHandler;
import com.zqhy.app.db.AppDatabase;
import com.zqhy.app.network.utils.AppUtils;
import com.zqhy.app.report.AllDataReportAgency;
import com.zqhy.app.report.ReportLog;
import com.zqhy.app.utils.AppManager;
import com.zqhy.app.utils.sdcard.SdCardManager;
import com.zqhy.app.widget.state.EmptyState;

import org.litepal.LitePal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.chaoji.mod.game.ModManager;
import com.zqhy.mod.ModProviderImpl;
import com.chaoji.mod.ui.activity.game.ChannelSdkImpl;
import com.chaoji.mod.game.sdk.ModSdkManager;
/**
 * @author：tqzhang on 18/4/19 17:57
 */
public class App extends BaseApplication implements ComponentCallbacks2, Application.ActivityLifecycleCallbacks {

    private static App mInstance;

    public static App instance() {
        return mInstance;
    }

    public static Context getContext() {
        return mInstance;
    }

    private HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        App app = (App) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer(mInstance);
    }

    private static DeviceBean mDeviceBean;

    public static DeviceBean getDeviceBean(){
        if (mDeviceBean == null){
            mDeviceBean = new DeviceBean();
        }
        return mDeviceBean;
    }

    public static boolean isShowEasyFloat = false;
    public static String showEasyFloatPackageName = "";
    public static String showEasyFloatGameIcon = "";
    public static List<String> tagList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ImSDK.Companion.getInstance().init(this);
        ModManager.INSTANCE.initialize(this, new ModProviderImpl());
        ModSdkManager.INSTANCE.initialize(this, new ChannelSdkImpl());
        CrashHandler.getInstance().init(mInstance, mInstance);
        //ReportLog.postErrorLog();
        //初始化推广id
        //AppUtils.initTgid();
        //initApp();
        new Handler(Looper.getMainLooper()).post(this::initNonCriticalComponents);
        registerActivityLifecycleCallbacks(this);
        /*if (ProcessUtils.isMainProcess()) {
            if (AppUtil.isNotArmArchitecture()) {
                GmSpaceObject.initialize(this, "", "", new IGmSpaceInitCallBack() {
                    @Override
                    public void initResult(boolean b, int i, String s) {
                        Log.i("csc", "初始化有没有成功" + b);
                    }
                });
            }
        }*/
    }

    private void initNonCriticalComponents() {
        initUI(this);
        initFlowDb();
        ApkUtils.initTgid(this);
        AppUtils.initTgid();
        initBaseState();
    }

    /**
     * 初始化Application
     */
    public void initApp() {
        initUI(this);
        //DbFlow初始化
        initFlowDb();
        ApkUtils.initTgid(this);
        AppUtils.initTgid();
        //友盟统计
        /*if (!AppConfig.isProAppid_105Channel()) {
            initUmeng(mInstance);
        }*/
        //初始化BaseState
        initBaseState();
        //AllDataReportAgency.getInstance().init(mInstance);
        //closeAndroidPDialog();
        /*if (BuildConfig.BAIDU_APP_ID != 0 && !TextUtils.isEmpty(BuildConfig.BAIDU_APP_KEY)){
            BaiduAction.enableClip(false);
            BaiduAction.init(this, BuildConfig.BAIDU_APP_ID, BuildConfig.BAIDU_APP_KEY);
        }*/
    }

    /**
     * 在同意协议之后调用
     */
    private boolean isInit = false;
    public void initActionOnSplash(Application application) {
        //避免多次初始化
        if (isInit) return;
        isInit = true;
        //腾讯SMTT
        initTbs();
        //初始化Okgo
        initDownloadServer();
        LitePal.initialize(this);
        //leanCloud日志上传
        //ReportLog.postErrorLog();
        //UM初始化
        //initUmeng(application);
        //上报信息初始化
        //AllDataReportAgency.getInstance().init(application);

        //错误提交（可不用）
        //CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        //CrashReport.initCrashReport(getApplicationContext(), "4802748c50", false, strategy);
        //获取设备信息(未知)
        //strategy.setDeviceModel(Build.MODEL);
        //设置TGID
        //strategy.setAppChannel(AppUtils.getChannelFromApk());


        if (ProcessUtils.isMainProcess()) {
            //initVeGameEngine();
        }
        //strategy.setAppChannel(AppUtils.getChannelFromApk());
        /*SDKOptions options = new SDKOptions();
        //填入App Key
        options.appKey = "";
        IMKitClient.init(getApplicationContext(), null, options);*/
    }

    private void initTbs() {
        QbSdk.disableSensitiveApi();
        // 在调用TBS初始化、创建WebView之前进行如下配置
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);
    }

    /*private void initVeGameEngine() {
        VeGameEngine gameEngine = VeGameEngine.getInstance();
        *//**
         * 请使用prepare()方法来初始化VeGameEngine，init()方法已废弃。
         *//*
        gameEngine.prepare(this);
        gameEngine.addCloudCoreManagerListener(new ICloudCoreManagerStatusListener() {
            *//**
             * 请在onPrepared()回调中监听VeGameEngine的生命周期，onInitialed()回调已废弃
             *//*
            @Override
            public void onInitialed() {

            }

            *//**
             * 1. (0x0001)	未初始化状态
             * 2. (0x0002)	初始化成功
             * 3. (0x0004)	正常调用 start 接口
             * 5. (0x0008)	收到云端首帧
             * 6. (0x0010)	停止拉取云端视音频流，但不会改变云端的运行状态
             *//*
            @Override
            public void onPrepared() {
                // SDK初始化是一个异步过程，在这个回调中监听初始化完成状态
                if (gameEngine.getStatus()==2){
                    Setting.VE_CLOUD_IS_INIT = true;
                }else {
                    Setting.VE_CLOUD_IS_INIT = false;
                }
                AcLog.d("VeGameEngine", "onPrepared :" + gameEngine.getStatus());
            }
        });
        VeGameEngine.setDebug(true);
    }*/

    private void initUI(Application application) {
        XUI.init(application);
        XUI.debug(BuildConfig.DEBUG);
    }

    private void initDownloadServer() {
        //先前说默认刷新频率块了，此处改成了1s一刷新，
        //现在又说刷新频率慢了，要求改回去，我只能表示呵呵
        //        OkGo.REFRESH_TIME = 1000;
        HttpHelper.getInstance().init(this);
        String path = SdCardManager.getInstance().getDownloadApkDir().getPath();
        //设置全局下载目录
        OkDownload.getInstance().setFolder(path);
        //设置同时下载数量
        OkDownload.getInstance().getThreadPool().setCorePoolSize(3);
    }

    /**
     * 初始化友盟统计
     *
     * @param mContext
     */
    public static void initUmeng(Context mContext) {
        String uMengAppKey = "682bde6cbc47b67d836ada5e";
        String channel = "CHANNAL_" + AppUtils.getTgid();
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
        UMConfigure.init(mContext, uMengAppKey, channel, UMConfigure.DEVICE_TYPE_PHONE, null);
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        Logs.e("initUmeng\n" + "uMengAppKey = " + uMengAppKey + "\nchannel = " + channel);
    }

    private void initFlowDb() {
        //DbFlow初始化
        FlowManager.init(FlowConfig.builder(mInstance)
                .addDatabaseConfig(DatabaseConfig.builder(AppDatabase.class)
                        .build())
                .build());
    }

    private void initBaseState() {
        new LoadState.Builder()
                .register(new ErrorState())
                .register(new LoadingState())
                .register(new EmptyDataState())
                .register(new EmptyState())
                .setDefaultCallback(LoadingState.class)
                .build();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        GlideApp.get(this).onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        GlideApp.get(this).onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        AppManager.getInstance().AppExit();
    }


    /**
     * 解决在Android P上的提醒弹窗

    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     */
    private static List<Activity> activityList = new ArrayList<>();

    public static List<Activity> getActivityList(){
        return activityList;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        activityList.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activityList.remove(activity);//因为移除了
        for (int i = 0; i < activityList.size(); i++) {
            Activity activity1 = activityList.get(i);
            if (activity1 instanceof MainActivity){
                ((MainActivity)activity1).activityPosition = i;
            }
            if (activity1 instanceof FragmentHolderActivity){
                ((FragmentHolderActivity)activity1).activityPosition = i;
            }
        }
    }

    /**
     * 设置 app 不随着系统字体的调整而变化

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics() );
        return res;
    }
     */
}
