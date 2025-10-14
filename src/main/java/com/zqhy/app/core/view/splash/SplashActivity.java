package com.zqhy.app.core.view.splash;

import android.Manifest;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.GsonUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.box.common.IMUtilsKt;
import com.box.common.glide.GlideApp;
import com.box.common.sdk.ImSDK;
import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.hjq.toast.Toaster;
import com.gism.sdk.GismSDK;
import com.google.gson.Gson;
import com.tencent.smtt.sdk.WebView;
import com.zqhy.app.App;
import com.zqhy.app.Setting;
import com.zqhy.app.base.BaseActivity;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.OnPayConfig;
import com.zqhy.app.config.WxControlConfig;
import com.zqhy.app.core.data.model.AppMenuBeanVo;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.InitDataVo;
import com.zqhy.app.core.data.model.launch.StasInfo;
import com.zqhy.app.core.data.model.splash.AppStyleConfigs;
import com.zqhy.app.core.data.model.splash.MarketInfoVo;
import com.zqhy.app.core.data.model.splash.MarketInitVo;
import com.zqhy.app.core.data.model.splash.SplashVo;
import com.zqhy.app.core.data.model.user.RefundGamesVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.ui.permission.PermissionUtil;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.main.BrowseModeImageFragment;
import com.zqhy.app.core.view.main.BrowseModeMainFragment;
import com.zqhy.app.core.view.main.MainActivity;
import com.zqhy.app.core.vm.splash.SplashViewModel;
import com.zqhy.app.network.listener.NetworkPollingListener;
import com.zqhy.app.network.utils.AppUtils;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.report.AllDataReportAgency;
import com.zqhy.app.utils.AppManager;
import com.zqhy.app.utils.LifeUtil;
import com.zqhy.app.utils.PermissionHelper;
import com.zqhy.app.utils.cache.ACache;
import com.zqhy.app.utils.sp.SPUtils;
import com.zqhy.app.widget.CountDownTimerCopyFromAPI26;
import com.zqhy.app.widget.floatview.FloatViewManager;
import com.zqhy.sdk.db.SdkManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/2
 */

public class SplashActivity extends BaseActivity<SplashViewModel> {

    public static final String SP_MARKET_INIT = "SP_MARKET_INIT";
    private static final String SP_MARKET_INIT_HAS_STATUS = "SP_MARKET_INIT_HAS_STATUS";
    private static final String SP_MARKET_INIT_STATUS_VALUE = "SP_MARKET_INIT_STATUS_VALUE";
    private static final String SP_LAST_TIME_DENY_PERMISSION = "SP_LAST_TIME_DENY_PERMISSION";

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_SPLASH;
    }


    private Intent targetIntent;
    private boolean isFromSDK = false;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        targetIntent = getIntent();
        isFromSDK = targetIntent.getBooleanExtra("isFromSDK", isFromSDK);
        //AppConfig.TIME_STAMP = System.currentTimeMillis();
        //long offsetTime = System.currentTimeMillis() - AppConfig.TIME_STAMP;
        //Logs.e("App 初始化耗时：" + offsetTime + "ms");
        //initApp();
        super.onCreate(savedInstanceState);
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                    return;
                }
            }
        }
        webView = new WebView(this);
        Setting.USER_AGENT = webView.getSettings().getUserAgentString();
        //切包,可能没用
        if (mViewModel != null && !Setting.APP_LOCAL_STATUS_ENABLE) {
            mViewModel.getStatus(new OnBaseCallback<StasInfo>() {
                @Override
                public void onSuccess(StasInfo data) {
                    if (data != null && data.getData() != null) {
                        //Setting.APP_STATUS = data.getData().getStatus();
                    }
                }
            });
        }

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void initApp() {
        //        App.instance().initApp();
        //webView
        //第一次加载页面的时候需要耗时去初始化 WebView 内核
        WebView mWebView = new WebView(new MutableContextWrapper(this.getApplication()));

        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        IMUtilsKt.getOAIDWithRetry(
                this,
                oaid -> {
                    sendAdActive();
                    App.getDeviceBean().setOaid(oaid);
                    return kotlin.Unit.INSTANCE;
                }
        );

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        targetIntent = intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    private TextView mTvCountDown;

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        showSuccess();
        bindView();
        mViewModel.getMarketInfo(new OnBaseCallback<MarketInfoVo>() {
            @Override
            public void onSuccess(MarketInfoVo data) {
                if (data.isStateOK() && data.getData() != null) {
                    Logs.e("MarketInfoVo:", GsonUtils.toJson(data));
                    ImSDK.appViewModelInstance.setAppInfo(data.getData());
                    startMain();
                    if (!showPrivateDialog(2, () -> check())) {
                        check();
                    }
                }else{
                    Toaster.show("网络错误");
                }
            }
        });


    }

    private void check() {
        /*if (checkPermissions(() -> marketInit())){
            marketInit();
        }*/
        if (BuildConfig.GET_PERMISSIONS_FIRSR) {
            if (checkPermissions(() -> marketInit())) {
                marketInit();
            }
        } else {
            marketInit();
            AllDataReportAgency.getInstance().setPrivacyStatus(true);
        }
    }

    /**
     * 发送安装微信插件节点
     */
    public void setWxpayInstallPoint(List<String> wxPayPackageNames) {
        if (mViewModel != null) {
            Map<String, String> param = checkWxPay(wxPayPackageNames);
            if (param.isEmpty()) {
                return;
            }
            mViewModel.setPoint("trace_wxpay_install", param);
        }
    }

    /**
     * 检查微信
     *
     * @param wxPayPackageNames
     * @return
     */
    private Map<String, String> checkWxPay(List<String> wxPayPackageNames) {
        Map<String, String> result = new TreeMap<>();
        if (wxPayPackageNames != null) {
            for (String pkn : wxPayPackageNames) {
                try {
                    PackageInfo packageInfo = getPackageManager().getPackageInfo(pkn, 0);
                    long firstInstallTime = packageInfo.firstInstallTime;
                    long lastUpdateTime = packageInfo.lastUpdateTime;

                    result.put("package_name", pkn);
                    result.put("first_install_time", String.valueOf(firstInstallTime));
                    result.put("last_install_time", String.valueOf(lastUpdateTime));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    private void startMain() {
//                boolean hasPermissions = PermissionHelper.hasPermissions(permissions);
//                boolean isNoPermission = isNoPermissionsAccessible(2 * 60 * 60 * 1000);
//                if (hasPermissions || isNoPermission) {
//                    marketInit();
//                    if (!isNoPermission) {
//                        setPermissionPoint(1);
//                    }
//                } else {
////                    showStorageDialog(() -> marketInit()));
////
////                    if (checkPermissions(() -> marketInit())){
////                        marketInit();
////                    }
//
//                    showStorageDialog(() -> marketInit());
//                }
    }

    private boolean checkPermissions(PositiveListener positiveListener) {
        boolean hasPermissions = PermissionHelper.hasPermissions(permissions);
        boolean isNoPermission = isNoPermissionsAccessible(2 * 60 * 60 * 1000);

        if (hasPermissions || isNoPermission) {
            if (hasPermissions) {
                setPermissionPoint(1);
            }
            return true;
        }
        return showStorageDialog(positiveListener);
    }

    /**
     * @param duration 单位ms
     * @return
     */
    private boolean isNoPermissionsAccessible(int duration) {
        SPUtils spUtils = new SPUtils(SP_MARKET_INIT);
        long lastTime = spUtils.getLong(SP_LAST_TIME_DENY_PERMISSION);
        return lastTime > 0;
        //        return System.currentTimeMillis() - lastTime < duration;
    }

    /**
     * @param tag              1正常模块  2审核模块
     * @param positiveListener
     * @return
     */


    private boolean showPrivateDialog(int tag, PositiveListener positiveListener) {
        SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
        String spKey = "app_private_yes";

        //AppConfig.setRestoreProtocolUrl();

        boolean isAgreement = spUtils.getBoolean(spKey, false);
        if (isAgreement) {
            return false;
        }

        CustomDialog privateDialog = new CustomDialog(this, LayoutInflater.from(this).inflate(R.layout.layout_dialog_ts_private_aishang, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        //CommonDialog privateDialog = new CommonDialog(this, LayoutInflater.from(this).inflate(R.layout.layout_dialog_ts_private_aishang, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        TextView mTvPrivate = privateDialog.findViewById(R.id.tv_private);
        Button mBtnConfirm = privateDialog.findViewById(R.id.btn_confirm);
        Button mBtnCancel = privateDialog.findViewById(R.id.btn_cancel);

        if (BuildConfig.IS_HAS_VISITORS) {
            mBtnCancel.setText("不同意");
        } else {
            mBtnCancel.setText("同意");
        }

        String appName = getString(R.string.app_name);

        StringBuilder sb = new StringBuilder();
        sb.append("欢迎使用").append(appName).append("APP！").append("\n");

        sb.append("我们根据最新的监管要求更新了平台的");
        int startIndex1 = sb.length();
        sb.append("《用户服务协议》");
        int endIndex1 = sb.length();
        sb.append("和");
        int startIndex2 = sb.length();
        sb.append("《隐私政策》");
        int endIndex2 = sb.length();
        sb.append("，请您在点击“同意”前仔细阅读并充分理解相关条款，其中的重点条款我们通过加粗等方式为您特别标注。为帮助您了解我们收集、使用、存储和共享个人信息的情况，以及您所享有的相关权利，我们提供摘要特向您说明如下：\n");
        sb.append("1、为保障服务所需，我们会申请系统权限收集您的手机设备信息用于信息推送和安全风控，并申请存储权限用于缓存游戏信息相关文件；\n");
        sb.append("2、基于您明确授权，我们可能会获取您的手机存储权限、相机权限用于游戏交易、点评&问答中发布图片内容；\n");
        sb.append("3、未经您同意，我们不会从第三方获取、共享或者对外提供您的信息，您也可以随时查询、更正或者删除您的个人信息；\n");
        sb.append("4、您有权拒绝或取消授权，取消后将不会影响您使用我们提供的其他服务；您可以随时在您的设备对相关授权进行管理，我们也提供账号注销的渠道。\n");
        sb.append("5、上述权限均非强制获取，拒绝也可正常进入，若无权限仅会导致部分功能无法正常使用。");
        SpannableString ss = new SpannableString(sb.toString());
        ss.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(ContextCompat.getColor(App.getContext(), R.color.color_3478f6));
            }

            @Override
            public void onClick(@NonNull View widget) {
                //用户协议
                if (tag == 1) {
                    goUserAgreement();
                } else if (tag == 2) {
                    goAuditUserAgreement();
                }
            }
        }, startIndex1, endIndex1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(ContextCompat.getColor(App.getContext(), R.color.color_3478f6));
            }

            @Override
            public void onClick(@NonNull View widget) {
                //隐私协议
                if (tag == 1) {
                    goPrivacyAgreement();
                } else if (tag == 2) {
                    goAuditPrivacyAgreement();
                }
            }
        }, startIndex2, endIndex2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }

            @Override
            public void onClick(@NonNull View widget) {
            }
        }, endIndex2, endIndex2 + 24, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvPrivate.setText(ss);
        mTvPrivate.setMovementMethod(LinkMovementMethod.getInstance());

        mBtnConfirm.setOnClickListener(v -> {
            spUtils.putBoolean(spKey, true);
            privateDialog.dismiss();
            if (positiveListener != null) {
                positiveListener.onConfirm();
            }
            /*if (mViewModel != null) {
                mViewModel.setPoint("trace_privacy", null);
            }
            AllDataReportAgency.getInstance().setPrivacyStatus(true);*/
        });

        if (AppConfig.isFuliChannel() || BuildConfig.IS_ALLOW_PRIVATE_DIALOG_REFUSE) {
            mBtnCancel.setVisibility(View.VISIBLE);
            mBtnCancel.setOnClickListener(v -> {
                privateDialog.dismiss();
                //GismSDK.onExitApp();
                //AppManager.getInstance().AppExit();
                if (BuildConfig.IS_HAS_VISITORS) {
                    showRankingDialog(tag, positiveListener);
                } else {
                    GismSDK.onExitApp();
                    AppManager.getInstance().AppExit();
                }
            });
        }
        if (!LifeUtil.isAlive(this)) {
            return false;
        }
        privateDialog.setCanceledOnTouchOutside(false);
        privateDialog.setCancelable(false);
        privateDialog.show();
        return true;
    }

    private void startFragment(BaseFragment fragment) {
        FragmentHolderActivity.startFragmentInActivity(this, fragment);
    }

    private void showRankingDialog(int tag, PositiveListener positiveListener) {
        SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
        CustomDialog rankDialog = new CustomDialog(this, LayoutInflater.from(this).inflate(R.layout.layout_dialog_ts_private_visitors, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        SpannableString ss = new SpannableString("如果您不同意《隐私协议》政策，将进入浏览模式，此模式下我们不会收集您的信息，只提供部分基础内容的浏览功能。");
        ss.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(ContextCompat.getColor(App.getContext(), R.color.color_3478f6));
            }

            @Override
            public void onClick(@NonNull View widget) {
                //隐私协议
                goAuditPrivacyAgreement();
            }
        }, 6, 12, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ((TextView) rankDialog.findViewById(R.id.tv_integral_balance)).setText(ss);
        ((TextView) rankDialog.findViewById(R.id.tv_integral_balance)).setMovementMethod(LinkMovementMethod.getInstance());

        rankDialog.findViewById(R.id.tv_exit).setOnClickListener(v -> {
            if (rankDialog != null) {
                rankDialog.dismiss();
                //ActivityModMain.Companion.start(this);
            }
            finish();
        });
        rankDialog.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            if (rankDialog != null) rankDialog.dismiss();
            /*String spKey = "app_private_yes";
            spUtils.putBoolean(spKey, true);
            rankDialog.dismiss();
            if (positiveListener != null) {
                positiveListener.onConfirm();
            }*/
            showPrivateDialog(tag, positiveListener);
        });
        rankDialog.findViewById(R.id.btn_cancel).setOnClickListener(v -> {
            if (rankDialog != null) rankDialog.dismiss();
//            startFragment(NewGameRankingActivityFragment.newInstance("hot"));
//            SplashActivity.this.finish();
            if (mViewModel != null) {
                mViewModel.getMarketInit(new OnBaseCallback<MarketInitVo>() {
                    @Override
                    public void onSuccess(MarketInitVo data) {
                        if (data.isStateOK() && data.getData() != null) {
                            if (data.getData().getStatus() == 1) {
                                mViewModel.getNewInit(new OnBaseCallback<InitDataVo>() {
                                    @Override
                                    public void onSuccess(InitDataVo data) {
                                        saveAppMenus(data.getData().getMenu());
                                        startFragment(new BrowseModeMainFragment());
                                        finish();
                                    }
                                });
                            } else if (data.getData().getStatus() == 0) {
                                if (!TextUtils.isEmpty(data.getData().getView_pic())) {
                                    startFragment(BrowseModeImageFragment.newInstance(data.getData().getView_pic()));
                                    finish();
                                }
                            }
                        }
                    }
                });
            }
        });
        rankDialog.setCanceledOnTouchOutside(false);
        rankDialog.setCancelable(false);
        rankDialog.show();
    }


    private void goMain() {
        if (!showPrivateDialog(1, () -> doGoMain())) {
            doGoMain();
        }
    }

    private void doGoMain() {
        /*if (checkPermissions(() -> startNetWorkPolling())) {
            startNetWorkPolling();
        }*/
        startApp();
        //startNetWorkPolling();
    }

    private ImageView mIvSplash;
    private TextView mTvSplash;
    private TextView mTvSplashIntro;

    private RelativeLayout mRlSplashBottom;

    private void bindView() {
        mIvSplash = findViewById(R.id.iv_splash);
        mTvSplash = findViewById(R.id.tv_splash);
        mTvSplashIntro = findViewById(R.id.tv_splash_intro);
        mRlSplashBottom = findViewById(R.id.rl_splash_bottom);
        mTvSplashIntro.setText(getAppNameByXML(R.string.string_dyx_splash));
        mTvSplashIntro.setVisibility(AppConfig.isDismissSplashCopyright() ? View.GONE : View.VISIBLE);

        //        setSplashImage();
    }

    private Handler mHandler = new Handler();

    private void startNetWorkPolling() {
        if (mViewModel != null) {
            mViewModel.pollingUrls(new NetworkPollingListener() {
                @Override
                public void onSuccess() {
                    //startApp();
                    SPUtils spUtils = new SPUtils(SplashActivity.this, SP_MARKET_INIT);
                    int isRedirect = spUtils.getInt("isRedirect", -1);
                    if (isRedirect > 0 && !TextUtils.isEmpty(spUtils.getString("redirectUrl"))) {
                        BrowserActivity.newInstance1(SplashActivity.this, spUtils.getString("redirectUrl"), false);
                        finish();
                        return;
                    }
                    boolean has_status = spUtils.getBoolean(SP_MARKET_INIT_HAS_STATUS);
                    int status_value = spUtils.getInt(SP_MARKET_INIT_STATUS_VALUE);
                    if (has_status && status_value == 1) {
                        goMain();
                    } else {
                        getMarketInit();
                    }
                }

                @Override
                public void onFailure() {
                    Toaster.show("当前网络不稳定，请稍后再试~~~");
                    mHandler.postDelayed(() -> finish(), 2000);
                }
            });
        }
    }


    private void startApp() {
        getNetworkData();
        mHandler.postDelayed(mainRunnable, 10000);
    }

    private Runnable mainRunnable = () -> startMainActivity();

    @Override
    protected void dataObserver() {
        super.dataObserver();

        registerObserver(Constants.EVENT_KEY_SPLASH_DATA, SplashVo.class).observe(this, splashVo -> {
            if (splashVo != null) {
                showSplash(splashVo.getSplashBeanVo());
                InitDataVo initDataVo = splashVo.getAppInit();
                if (initDataVo != null && initDataVo.isStateOK()) {
                    if (initDataVo.getData() != null) {
                        //                        WxControlConfig.wx_control = initDataVo.getData().getWx_control();
                        WxControlConfig.wx_control = 1;
                        OnPayConfig.setToutiaoReportAmountLimit(initDataVo.getData().getToutiao_report_amount_limit());
                        AppConfig.setReyun_gonghui_tgids(initDataVo.getData().getReyun_gonghui_tgids());
                        if (initDataVo.getData().getToutiao_plug() != null) {
                            AppConfig.setToutiao_tgids(initDataVo.getData().getToutiao_plug().getToutiao_tgids());
                        }
                        setWxpayInstallPoint(initDataVo.getData().getWxPay_packagenames());
                        saveAppStyle(initDataVo.getData().getTheme());
                        AppConfig.setHideCommunity(initDataVo.getData().getHide_community());

                        Setting.PROFILE_SETTING = initDataVo.getData().profile_setting;
                        Setting.HIDE_FIVE_FIGURE = initDataVo.getData().getHide_five_figure();
                        Setting.CLOUD_STATUS = initDataVo.getData().getCloud_status();
                        Setting.POP_GAMEID = initDataVo.getData().getPop_gameid();
                        Setting.SHOW_TIPS = initDataVo.getData().getShow_tip();
                        //储存appMenu到本地
                        saveAppMenus(initDataVo.getData().getMenu());
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelCountDown1();
        mHandler.removeCallbacks(mainRunnable);
        webView = null;
    }

    @Override
    public void finish() {
        if (!isFinishing()) {
            super.finish();
        }
    }

    CountDownTimerCopyFromAPI26 countDownTimer;

    private void setCountDown() {
        mTvCountDown = findViewById(R.id.tv_count_down);
        int splashCount = 2000;

        //        GradientDrawable gd = new GradientDrawable();
        //        gd.setColor(Color.parseColor("#70000000"));
        //        gd.setCornerRadius(30 * ScreenUtil.getScreenDensity(this));
        //        mTvCountDown.setBackground(gd);
        //        mTvCountDown.setTextColor(ContextCompat.getColor(this, R.color.white));

        countDown(splashCount);

        countDownTimer = new CountDownTimerCopyFromAPI26(splashCount, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countDown(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                mTvCountDown.setText("跳过 0s");
                startMainActivity();
            }
        };
        countDownTimer.start();

        mTvCountDown.setOnClickListener(view -> {
            cancelCountDown1();
            startMainActivity();
        });
    }

    private void cancelCountDown1() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void startMainActivity() {
        //ActivityModMain.Companion.start(this);
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        if (targetIntent != null) {
            String json = targetIntent.getStringExtra("json");
            Logs.d("SDK_TAG:" + "SplashActivity---------json:" + json);
            intent.putExtras(targetIntent);
        }
        startActivity(intent);
        //checkUserLogin();
        finish();
    }

    private void startMainActivity(Intent intent) {
        //ActivityModMain.Companion.start(this);
        intent.setClass(this, MainActivity.class);
        if (targetIntent != null) {
            String json = targetIntent.getStringExtra("json");
            Logs.d("SDK_TAG", "SplashActivity---------json:" + json);
            intent.putExtras(targetIntent);
        }
        startActivity(intent);
        finish();
    }

    private void countDown(long millLeft) {
        mTvCountDown.setVisibility(View.VISIBLE);
        mTvCountDown.setText("跳过 " + String.valueOf((millLeft + 1000) / 1000) + "s");
    }

    /**
     * 获取数据
     */
    private void getNetworkData() {
        if (mViewModel != null) {
            mViewModel.getNetWorkData();
        }
    }

    /**
     * 展示闪屏页
     */
    private void showSplash(SplashVo.SplashBeanVo splashBeanVo) {
        if (splashBeanVo != null) {
            if (splashBeanVo.isStateOK()) {
                SplashVo.SplashBeanVo.DataBean splashInfo = splashBeanVo.getData();
                if (splashInfo != null && LifeUtil.isAlive(SplashActivity.this)) {
                    if (mIvSplash != null) {
                        mIvSplash.setVisibility(View.VISIBLE);
                        if (mRlSplashBottom != null) {
                            mRlSplashBottom.setVisibility(View.VISIBLE);
                        }
                        GlideApp.with(this)
                                .asBitmap()
                                .load(splashInfo.getPic())
                                .centerCrop()
                                .into(new CustomTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                                        mIvSplash.setImageBitmap(bitmap);
                                        if (TextUtils.isEmpty(splashInfo.getPage_type()) || "no_jump".equals(splashInfo.getPage_type())) {
                                            mTvSplash.setVisibility(View.GONE);
                                        } else {
                                            mTvSplash.setVisibility(View.VISIBLE);
                                        }
                                        mTvSplash.setOnClickListener(v -> {
                                            if (TextUtils.isEmpty(splashInfo.getPage_type()) || "no_jump".equals(splashInfo.getPage_type())) {
                                                return;
                                            }
                                            cancelCountDown1();
                                            //闪屏跳转
                                            Intent intent = new Intent();
                                            String splashJson = new Gson().toJson(splashInfo);
                                            intent.putExtra("splash_jump", splashJson);
                                            startMainActivity(intent);
                                        });
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable drawable) {

                                    }
                                });
                    }
                }
                setCountDown();
            }
        }
    }

    /**
     * 保存App主题样式
     *
     * @param dataBean
     */
    private void saveAppStyle(SplashVo.AppStyleVo.DataBean dataBean) {
        if (dataBean != null) {
            String json = new Gson().toJson(dataBean);
            AppStyleHelper.getInstance().handlerAppStyle(this, json, dataBean);
        } else {
            AppStyleHelper.getInstance().removeAppStyleData(this);
        }
    }

    /**
     * 保存appMenu
     *
     * @param vo
     */
    private void saveAppMenus(AppMenuBeanVo vo) {
        Gson gson = new Gson();
        String json = gson.toJson(vo);
        ACache.get(this).put(AppStyleConfigs.APP_MENU_JSON_KEY, json);
    }

    /*************************************************************************************************************************************/

    private void marketInit() {
        ((App) getApplication()).initActionOnSplash(getApplication());
        initApp();
        AllDataReportAgency.getInstance().startApp(getApplication());
        SdkManager.getInstance().cacheAppTgid("tsyule");
        startNetWorkPolling();
        getRefundGames();
    }

    private void sendAdActive() {
        if (mViewModel != null) {
            mViewModel.sendAdActive(new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                }
            });
        }
    }

    private void getRefundGames() {
        if (mViewModel != null) {
            mViewModel.getRefundGames(new OnBaseCallback<RefundGamesVo>() {
                @Override
                public void onSuccess(RefundGamesVo data) {
                    if (data != null && data.getData() != null) {
                        if (data.getData().getIds() != null) {
                            Setting.REFUND_GAME_LIST = data.getData().getIds();
                            Setting.REFUND_GAME_LIST_TGID = AppUtils.getTgid();
                        }
                    }
                }
            });
        }
    }

    interface PositiveListener {
        void onConfirm();
    }

    private void getMarketInit() {
        if (mViewModel != null) {
            SPUtils spUtils = new SPUtils(SplashActivity.this, SP_MARKET_INIT);
            boolean has_status = spUtils.getBoolean(SP_MARKET_INIT_HAS_STATUS);
            mViewModel.getMarketInit(new OnBaseCallback<MarketInitVo>() {
                @Override
                public void onSuccess(MarketInitVo data) {
                    if (data != null && data.isStateOK()) {
                        MarketInitVo.DataBean dataBean = data.getData();
                        if (dataBean != null) {
                            if (!has_status) {
                                spUtils.putInt(SP_MARKET_INIT_STATUS_VALUE, dataBean.getStatus());
                                spUtils.putBoolean(SP_MARKET_INIT_HAS_STATUS, true);
                            }

                            int index_module_type = dataBean.getIndex_module_type();

                            List<String> index_module = new ArrayList<>();
                            if (index_module_type == 0 && dataBean.getIndex_module() != null) {
                                index_module.addAll(dataBean.getIndex_module());
                            } else if (index_module_type == 1 && dataBean.getBatu_index_module() != null) {
                                index_module.addAll(dataBean.getBatu_index_module());
                            } else if (index_module_type == 2) {
                                index_module.addAll(dataBean.getJiuyao_index_module());
                            }

                            if (!TextUtils.isEmpty(dataBean.getAndroid_download_url())) {
                                spUtils.putString(FloatViewManager.SP_ANDROID_DOWNLOAD_URL, dataBean.getAndroid_download_url());
                            } else {
                                spUtils.remove(FloatViewManager.SP_ANDROID_DOWNLOAD_URL);
                            }
                            if (!TextUtils.isEmpty(dataBean.getPop_pic())) {
                                spUtils.putString(FloatViewManager.SP_FLOAT_IMAGE_URL, dataBean.getPop_pic());
                            } else {
                                spUtils.remove(FloatViewManager.SP_FLOAT_IMAGE_URL);
                            }
                            if (!TextUtils.isEmpty(dataBean.getPop_text())) {
                                spUtils.putString(FloatViewManager.SP_FLOAT_IMAGE_DES, dataBean.getPop_text());
                            } else {
                                spUtils.remove(FloatViewManager.SP_FLOAT_IMAGE_DES);
                            }

                            Setting.TYPE = dataBean.center_module;
                            Setting.TRADE_TYPE = dataBean.trading_module;
                            Setting.SHOW_TYPE = dataBean.center_show;
                            Setting.HELLO_TXT = dataBean.index_hello;
                            Setting.SHOW_DOWNLOAD = dataBean.appurl;
                            Setting.DOWN_PIC = dataBean.downpic;
                            Setting.IS_REAL = dataBean.need_sfz;

                            chooseGoMain(index_module, index_module_type, dataBean.getIs_redirect(), dataBean.getRedirect_url());
                            //goMain();
                        }
                    } else {
                        goMain();
                    }
                }
            });
        }
    }

    private void chooseGoMain(List<String> index_module, int index_module_type, int is_redirect, String redirect_url) {

        SPUtils spUtils = new SPUtils(this, SP_MARKET_INIT);
        if (spUtils.getInt("isRedirect", -1) == -1) {
            spUtils.putInt("isRedirect", is_redirect);
            spUtils.putString("redirectUrl", redirect_url);
        }

        if (spUtils.getInt("isRedirect", -1) > 0 && is_redirect != 0 && !TextUtils.isEmpty(redirect_url)) {
            BrowserActivity.newInstance1(this, redirect_url, false);
        } else {
            goMain();
        }
        //finish();
    }

    private String[] permissions = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private CustomDialog dialog;

    private boolean showStorageDialog(PositiveListener positiveListener) {
        if (dialog == null) {
            dialog = new CustomDialog(this,
                    LayoutInflater.from(this).inflate(R.layout.layout_dialog_permissions, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
        }
        TextView mTvCommit = dialog.findViewById(R.id.tv_commit);
        TextView mTvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView mTvApp = dialog.findViewById(R.id.tv_app);
        mTvApp.setText(getAppNameByXML(R.string.string_dyx_permissions));

        mTvCommit.setOnClickListener(v -> {
            PermissionHelper.checkPermissions(new PermissionHelper.OnPermissionListener() {
                @Override
                public void onGranted() {
                    permissionComplete();
                    setPermissionPoint(1);
                    if (positiveListener != null) {
                        positiveListener.onConfirm();
                    }
                }

                @Override
                public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                    permissionComplete();
                    setPermissionPoint(0);
                    if (positiveListener != null) {
                        positiveListener.onConfirm();
                    }
                }
            }, permissions);
        });
        mTvCancel.setOnClickListener(v -> {
            permissionComplete();
            setPermissionPoint(0);
            if (positiveListener != null) {
                positiveListener.onConfirm();
            }
        });
        dialog.show();
        SPUtils spUtils = new SPUtils(SP_MARKET_INIT);
        spUtils.putLong(SP_LAST_TIME_DENY_PERMISSION, System.currentTimeMillis());
        return false;
    }

    private void baiDuRequestPermissionsResult(String[] permissions) {
        int[] grantResults = new int[permissions.length];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < grantResults.length; i++) {
                grantResults[i] = PermissionUtil.hasPermission(App.getContext(), permissions[i]) ? 0 : -1;
            }
        }
        //BaiduDataAgency.getInstance().onRequestPermissionsResult(0, permissions, grantResults);
    }


    /**
     * 检查权限完毕
     */
    private void permissionComplete() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        //        marketInit();
    }

    /**
     * @param is_authorization
     */
    private void setPermissionPoint(int is_authorization) {
        AllDataReportAgency.getInstance().setPrivacyStatus(true);
        if (is_authorization == 1) {
            baiDuRequestPermissionsResult(permissions);
        }
        /*if (mViewModel != null) {
            Map<String, String> params = new TreeMap<>();
            params.put("is_authorization", String.valueOf(is_authorization));
            mViewModel.setPoint("trace_permission", params);
        }*/
    }
}
