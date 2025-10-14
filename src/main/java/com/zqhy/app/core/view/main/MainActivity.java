package com.zqhy.app.core.view.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.im.IMUtilsKt;
import com.chaoji.im.sdk.ImSDK;
import com.chaoji.im.utils.floattoast.XToast;
import com.chaoji.other.blankj.utilcode.util.ActivityUtils;
import com.chaoji.other.blankj.utilcode.util.Logs;
import com.chaoji.other.blankj.utilcode.util.StringUtils;
import com.chaoji.other.hjq.toast.Toaster;
import com.gism.sdk.GismSDK;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.igexin.sdk.PushManager;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.SidePattern;
import com.mobile.auth.gatewayauth.utils.security.EmulatorDetector;
import com.mvvm.base.BaseMvvmFragment;
import com.zqhy.app.App;
import com.zqhy.app.Setting;
import com.zqhy.app.base.BaseActivity;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.config.SpConstants;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.InitDataVo;
import com.zqhy.app.core.data.model.game.GameDataVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.appointment.UserGameAppointmentVo;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;
import com.zqhy.app.core.data.model.message.MessageInfoVo;
import com.zqhy.app.core.data.model.message.MessageListVo;
import com.zqhy.app.core.data.model.sdk.SdkAction;
import com.zqhy.app.core.data.model.share.InviteDataVo;
import com.zqhy.app.core.data.model.splash.AppStyleConfigs;
import com.zqhy.app.core.data.model.splash.SplashVo;
import com.zqhy.app.core.data.model.user.RefundGamesVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.data.model.user.newvip.AllPopVo;
import com.zqhy.app.core.data.model.user.newvip.AppFloatIconVo;
import com.zqhy.app.core.data.model.user.newvip.BirthdayRewardVo;
import com.zqhy.app.core.data.model.user.newvip.ComeBackVo;
import com.zqhy.app.core.data.model.user.newvip.RmbusergiveVo;
import com.zqhy.app.core.data.model.user.newvip.SuperBirthdayRewardVo;
import com.zqhy.app.core.data.model.version.AppPopDataVo;
import com.zqhy.app.core.data.model.version.UnionVipDataVo;
import com.zqhy.app.core.data.model.version.VersionVo;
import com.zqhy.app.core.dialog.MainDialogHelper;
import com.zqhy.app.core.dialog.VersionDialogHelper;
import com.zqhy.app.core.inner.IAppBarScroll;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.core.tool.utilcode.SizeUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.view.AppSplashActivity;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.activity.ActivityAnnouncementFragment;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.browser.BrowserFragment;
import com.zqhy.app.core.view.classification.GameClassificationFragment;
import com.zqhy.app.core.view.community.integral.CommunityIntegralMallFragment;
import com.zqhy.app.core.view.community.task.TaskCenterFragment;
import com.zqhy.app.core.view.community.user.CommunityUserFragment;
import com.zqhy.app.core.view.currency.CurrencyMainFragment;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.core.view.invite.InviteFriendFragment;
import com.zqhy.app.core.view.kefu.KefuCenterFragment;
import com.zqhy.app.core.view.kefu.KefuHelperFragment;
import com.zqhy.app.core.view.login.LoginFragment;
import com.zqhy.app.core.view.login.event.AuthLoginEvent;
import com.zqhy.app.core.view.main.adapter.RmbusergiveAdapter;
import com.zqhy.app.core.view.main.dialog.AppPopDialogHelper;
import com.zqhy.app.core.view.main.dialog.MainPagerDialogHelper;
import com.zqhy.app.core.view.main.dialog.OnMainPagerClickListener;
import com.zqhy.app.core.view.main.new0809.MainPageTuiJianFragment;
import com.zqhy.app.core.view.message.MessageMainFragment;
import com.zqhy.app.core.view.rebate.RebateMainFragment;
import com.zqhy.app.core.view.recycle.XhRecycleMainFragment;
import com.zqhy.app.core.view.recycle_new.XhNewRecycleMainFragment;
import com.zqhy.app.core.view.setting.SettingManagerFragment;
import com.zqhy.app.core.view.strategy.DiscountStrategyFragment;
import com.zqhy.app.core.view.transaction.TransactionMainFragment1;
import com.zqhy.app.core.view.transfer.TransferMainFragment;
import com.zqhy.app.core.view.tryplay.TryGamePlayListFragment;
import com.zqhy.app.core.view.user.NewUserMineFragment1;
import com.zqhy.app.core.view.user.TopUpFragment;
import com.zqhy.app.core.view.user.welfare.MyCouponsListFragment;
import com.zqhy.app.core.view.user.welfare.MyFavouriteGameListFragment;
import com.zqhy.app.core.vm.main.MainViewModel;
import com.zqhy.app.db.table.message.MessageDbInstance;
import com.zqhy.app.db.table.message.MessageVo;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.statistics.JiuYaoStatisticsApi;
import com.zqhy.app.network.utils.AppUtils;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.push.DemoPushService;
import com.zqhy.app.push.PushIntentService;
import com.zqhy.app.share.ShareHelper;
import com.zqhy.app.utils.AppManager;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.utils.cache.ACache;
import com.zqhy.app.utils.sdcard.SdCardManager;
import com.zqhy.app.utils.sp.SPUtils;
import com.zqhy.app.widget.marqueeview.XMarqueeView;
import com.zqhy.app.widget.marqueeview.XMarqueeViewAdapter;
import com.chaoji.mod.view.FloatViewHelper;
import com.zqhy.mod.game.GameLauncher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class MainActivity extends BaseActivity<MainViewModel> implements View.OnClickListener {

    //private Boolean isOtherStyle = false;
    public int activityPosition;
    public boolean lingQuJump = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        //阿里一键登录
        AuthLoginEvent.instance().init(ImSDK.appViewModelInstance.getAppInfo().getValue());
//        if (TextUtils.isEmpty(App.getDeviceBean().getOaid())) {
//            IMUtilsKt.getOAIDWithRetry(
//                    this,
//                    oaid -> {
//                        App.getDeviceBean().setOaid(oaid);
//                        return kotlin.Unit.INSTANCE;
//                    }
//            );
//        }

        activityPosition = App.getActivityList().size() - 1;
        showSuccess();
        bindView();
        setAppStyle();
        Logs.e("MAIN_setAppStyle");
        SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
        boolean hasShow = spUtils.getBoolean(SpConstants.SP_HAS_SHOW_SINGLE_GAME);
        if (!hasShow && !Setting.HAS_SPLASH_JUMP) {
            try {
                int channelGameidFromApk = AppUtils.getChannelGameidFromApk();
                if (channelGameidFromApk != 0) {
                    getGameAdvertData(channelGameidFromApk);
                } else if (!TextUtils.isEmpty(Setting.POP_GAMEID) && !"0".equals(Setting.POP_GAMEID)) {
                    getGameAdvertData(Integer.parseInt(Setting.POP_GAMEID));
                }
            } catch (Exception e) {

            }
        }
        getAllPop(false);

        boolean emulator = EmulatorDetector.isEmulator(getApplicationContext());
        if (emulator) {
            Setting.simulator = 1;
            //Toaster.show("当前设备是模拟器");
        } else {
            // 运行在真实设备上
            Setting.simulator = 0;
            //ToastT.success("当前设备是真实设备");
        }
        //showFloat1();
        ImSDK.eventViewModelInstance.getStartGame().observe(this, aBoolean -> {
            if (aBoolean) {
                GameLauncher.INSTANCE.startLocalGame(this, GameLauncher.GAME_URL);
            } else {
                ActivityUtils.startActivity(MainActivity.class);
            }
        });

        ImSDK.eventViewModelInstance.getJumpGame().observe(this, gameId -> {
            startLocalGame();
        });
    }


    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        if (UserInfoModel.getInstance().isLogined()) {
            getAllPop(true);
        } else {
            mTvCount.setVisibility(View.GONE);
        }
    }

    private final String SP_VALUE_TRANSACTION_MAIN_TIPS = "SP_VALUE_TRANSACTION_MAIN_TIPS";

    private void transactionTip() {
        SPUtils spUtils = new SPUtils(this, Constants.SP_COMMON_NAME);
        boolean isShowTips = spUtils.getBoolean(SP_VALUE_TRANSACTION_MAIN_TIPS, false);
        if (!isShowTips) {
            mRootView.post(() -> showTransactionMainTipImage());
        }
    }

    private void showTransactionMainTipImage() {
        float density = ScreenUtil.getScreenDensity(this);
        FrameLayout mContainer = new FrameLayout(this);
        mContainer.setBackgroundColor(Color.parseColor("#BD000000"));

        ImageView imageView = new ImageView(this);
        Drawable drawable = getResources().getDrawable(R.mipmap.img_transaction_main_tip);
        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();

        int paramWidth = ScreenUtils.getScreenWidth(this);
        int paramHeight = paramWidth * drawableHeight / drawableWidth;

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(paramWidth, paramHeight);
        params.gravity = Gravity.BOTTOM;
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(params);
        imageView.setImageDrawable(drawable);

        mContainer.addView(imageView);


        View clickView = new View(this);

        int clickViewWidth = (int) (80 * density);
        int clickViewHeight = (int) (80 * density);

        FrameLayout.LayoutParams clickViewParams = new FrameLayout.LayoutParams(clickViewWidth, clickViewHeight);
        clickViewParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        clickViewParams.rightMargin = (int) (67 * density);

        mContainer.addView(clickView, clickViewParams);

        RelativeLayout.LayoutParams mParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mRootView.addView(mContainer, mParam);

        mContainer.setOnClickListener(null);
        clickView.setOnClickListener(v -> {
            mRootView.removeView(mContainer);
            SPUtils spUtils = new SPUtils(this, Constants.SP_COMMON_NAME);
            spUtils.putBoolean(SP_VALUE_TRANSACTION_MAIN_TIPS, true);
        });
    }


    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_MAIN_ACTIVITY_PAGE_STATE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //个推初始化
        PushManager.getInstance().initialize(this.getApplicationContext(), DemoPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), PushIntentService.class);
        HomePageJump(getIntent());
        //sdkAction();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        super.onNewIntent(intent);
        HomePageJump(intent);
        OtherJumpToHomePager(intent);
        //sdkAction();
        int tab_id = intent.getIntExtra("tab_id", -1);
        if (tab_id != -1) goToMainGamePage(tab_id);
        handleIntent(intent);
        setIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent != null && "detailJump".equals(intent.getStringExtra("detailJump"))) {
            // 在这里处理从详情页跳转过来的逻辑
            // 例如：刷新UI、跳转到特定Fragment等
            Log.d("MainActivity", "Received jump from detail.");
        }
    }

    private void sdkAction() {
        SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
        boolean isAgreement = spUtils.getBoolean("app_private_yes", false);
        if (!isAgreement) {
            startActivity(new Intent(MainActivity.this, AppSplashActivity.class));
            finish();
            return;
        }
        String from = getIntent().getStringExtra("from");
        String packageName = getIntent().getStringExtra("package");
        String action = getIntent().getStringExtra("action");
        String gameicon = getIntent().getStringExtra("gameicon");
        if (!TextUtils.isEmpty(from) && from.equals("sdk")) {
            App.isShowEasyFloat = true;
            App.showEasyFloatPackageName = packageName;
            App.showEasyFloatGameIcon = gameicon;
            showEasyFloat(this, "main");
            if (!TextUtils.isEmpty(action)) {
                new AppJumpAction(this).jumpAction(action);
            }
        }
    }

    /**
     * 其他页面跳转主页
     */
    private void OtherJumpToHomePager(Intent intent) {
        int isShowMainDialog = intent.getIntExtra("isShowMainDialog", 0);
        if (isShowMainDialog == 1) {
            if (mainPagerDialogHelper == null) {
                mainPagerDialogHelper = new MainPagerDialogHelper(this, onMainPagerClickListener);
            }
            mainPagerDialogHelper.showMainPagerDialog();
        }
    }

    /**
     * 主页跳转
     */
    private void HomePageJump(Intent intent) {
        if (intent == null) {
            return;
        }
        try {
            String from = getIntent().getStringExtra("from");
            String SDKPackageName = intent.getStringExtra("SDKPackageName");
            String gameicon = getIntent().getStringExtra("gameicon");
            if (!TextUtils.isEmpty(from) && from.equals("sdk")) {
                App.isShowEasyFloat = true;
                App.showEasyFloatPackageName = SDKPackageName;
                App.showEasyFloatGameIcon = gameicon;
                showEasyFloat(this, "main");
                /*if (!TextUtils.isEmpty(action)){
                    new AppJumpAction(this).jumpAction(action);
                }*/
            }

            String splash_jump = intent.getStringExtra("splash_jump");
            Setting.HAS_SPLASH_JUMP = !TextUtils.isEmpty(splash_jump);
            appJumpAction(splash_jump);

            String json = intent.getStringExtra("json");
            Logs.d("SDK_TAG:" + "MainActivity---------json:" + json);

            boolean isFromSDK = intent.getBooleanExtra("isFromSDK", false);

            Logs.d("SDK_TAG:" + "isFromSDK:" + isFromSDK);
            Logs.d("SDK_TAG:" + "SDKPackageName:" + SDKPackageName);

            Type type = new TypeToken<SdkAction>() {
            }.getType();
            Gson gson = new Gson();

            SdkAction sdkAction = gson.fromJson(json, type);

            if (sdkAction != null && !(isNeedSwitchUser(isFromSDK, SDKPackageName, sdkAction))) {
                sdkActionJump(isFromSDK, SDKPackageName, sdkAction);
            }
            intent.removeExtra("json");


            if (BuildConfig.APP_TEMPLATE == 9999  || BuildConfig.APP_TEMPLATE == 9998) {
                String splashJump = intent.getStringExtra("splashJump");
                if (splashJump != null) {
                    Logs.e("splashJump");
                    if ( !StringUtils.equals(BuildConfig.APP_UPDATE_ID, "1")
                            && !StringUtils.equals(BuildConfig.APP_UPDATE_ID, "11")
                            && !StringUtils.equals(BuildConfig.APP_UPDATE_ID, "16")
                            && !StringUtils.equals(BuildConfig.APP_UPDATE_ID, "43")
                            && BuildConfig.APP_TEMPLATE != 9998) {
                        showGameFloat();
                    }
                }

                String detailJump = intent.getStringExtra("detailJump");
                if (detailJump != null) {
                    Logs.e("detailJump");
                    if (
                             !StringUtils.equals(BuildConfig.APP_UPDATE_ID, "1")
                            && !StringUtils.equals(BuildConfig.APP_UPDATE_ID, "11")
                            && !StringUtils.equals(BuildConfig.APP_UPDATE_ID, "16")
                            && !StringUtils.equals(BuildConfig.APP_UPDATE_ID, "43")
                            && BuildConfig.APP_TEMPLATE != 9998) {
                        showGameFloat();
                    }
                }

                String jingQuJumpString = intent.getStringExtra("jingQuJump");
                if (jingQuJumpString != null) {
                    Logs.e("jingQuJump");
                    lingQuJump = true;
                    getAllPop(true);
                    if (
                            !StringUtils.equals(BuildConfig.APP_UPDATE_ID, "1")
                            && !StringUtils.equals(BuildConfig.APP_UPDATE_ID, "11")
                            && !StringUtils.equals(BuildConfig.APP_UPDATE_ID, "16")
                            && !StringUtils.equals(BuildConfig.APP_UPDATE_ID, "43")
                            && BuildConfig.APP_TEMPLATE != 9998) {
                        showGameFloat();
                    }
                }

                String gameJump = intent.getStringExtra("gameJump");
                if (gameJump != null) {
                    Logs.e("gameJump");
                    goGameDetail(Integer.parseInt(gameJump), 2, true);
                    // if (!StringUtils.isEmpty(BuildConfig.GAME_O_CHANNEL_NAME) && UserInfoModel.getInstance().isLogined()) {
                    if (BuildConfig.APP_TEMPLATE == 9999 || BuildConfig.APP_TEMPLATE == 9998) {
                        if (!StringUtils.equals(BuildConfig.APP_UPDATE_ID, "1")
                                && !StringUtils.equals(BuildConfig.APP_UPDATE_ID, "11")
                                && !StringUtils.equals(BuildConfig.APP_UPDATE_ID, "16")
                                && !StringUtils.equals(BuildConfig.APP_UPDATE_ID, "43")
                                && BuildConfig.APP_TEMPLATE != 9998) {
                            showGameFloat();
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startLocalGame() {
        GameLauncher.INSTANCE.startLocalGame(this, GameLauncher.GAME_URL);
    }

    private void showGameFloat() {
        if (mLocalGameFloat == null)
            mLocalGameFloat = FloatViewHelper.INSTANCE.createModFloatView(this, com.chaoji.other.blankj.utilcode.util.AppUtils.getAppIcon(), new Function0<Unit>() {
                @Override
                public Unit invoke() {
                    GameLauncher.INSTANCE.startLocalGame(MainActivity.this, GameLauncher.GAME_URL);
                    return null;
                }
            });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocalGameFloat != null && mLocalGameFloat.isShowing()) {
            mLocalGameFloat.cancel();
            mLocalGameFloat = null;
        }
    }

    private RelativeLayout mRootView;

    private FrameLayout mFlContainer;
    private FrameLayout mFlBottomToolbar;

    private LinearLayout mRadiogroup;
    private CheckedTextView mTabMainPage1;
    private CheckedTextView mTabMainPage2;
    private CheckedTextView mTabMainPage3;
    private CheckedTextView mTabMainPage4;
    private CheckedTextView mTabMainPage5;
    private ImageView mIvCenterButton;
    private LinearLayout mLlTabMainCenter;
    private TextView mTvCount;
    private FrameLayout frameLayout;

    MainPagerDialogHelper mainPagerDialogHelper;

    private void bindView() {
        frameLayout = findViewById(R.id.f_container);

        mRootView = findViewById(R.id.rootView);
        mFlContainer = findViewById(R.id.fl_container);
        mFlBottomToolbar = findViewById(R.id.fl_bottom_toolbar);
        mRadiogroup = findViewById(R.id.radiogroup);
        mTabMainPage1 = findViewById(R.id.tab_main_page_1);
        mTabMainPage2 = findViewById(R.id.tab_main_page_2);
        mTabMainPage3 = findViewById(R.id.tab_main_page_3);
        mTabMainPage4 = findViewById(R.id.tab_main_page_4);
        mTabMainPage5 = findViewById(R.id.tab_main_page_5);
        mLlTabMainCenter = findViewById(R.id.ll_tab_main_center);
        mIvCenterButton = findViewById(R.id.iv_center_button);
        mTvCount = findViewById(R.id.tv_count);
        if (BuildConfig.APP_UPDATE_ID != "1") {
            mTabMainPage3.setVisibility(View.GONE);
        }
        mTabMainPage1.setOnClickListener(this);
        mTabMainPage2.setOnClickListener(this);
        mTabMainPage3.setOnClickListener(this);
        mTabMainPage4.setOnClickListener(this);
        mTabMainPage5.setOnClickListener(this);

        //mainTab1Fragment = new MainFragment();
        mainTab2Fragment = NewMainGameFragment.newInstance(0);

        //mainTab3Fragment = TsServerListFragment.newInstance("开服表");
        mainTab3Fragment = new TransactionMainFragment1();

        InitDataVo.ProfileSettingVo profileSetting = Setting.PROFILE_SETTING;
        if (profileSetting != null && profileSetting.web_switch == 1 && !TextUtils.isEmpty(profileSetting.web_url)) {
            mainTab4Fragment = BrowserFragment.newInstance(profileSetting.web_url);
        } else {
            mainTab4Fragment = new NewUserMineFragment1();
        }
        //mainTab5Fragment = new KefuCenterFragment();
        mainTab5Fragment = TaskCenterFragment.newInstance(true);

        //mLlTabMainCenter.setOnClickListener(view -> {
        //showMainPageDialog();
        //            getKefuMessageData(); 2020.11.09首页取消消息入口

        //            getUserGameAppointmentInfo();  2020.05.11取消消息轮播
            /*
            if (mainPagerDialogHelper == null) {
                mainPagerDialogHelper = new MainPagerDialogHelper(this, onMainPagerClickListener);
            }
            mainPagerDialogHelper.showMainPagerDialog();
            saveHideMessageLastTime();
            if (mViewModel != null) {
                mViewModel.refreshUser();
            }
            if (mContent == mainTab1Fragment) {
                JiuYaoStatisticsApi.getInstance().eventStatistics(1, 15);
            } else if (mContent == mainTab2Fragment) {
                JiuYaoStatisticsApi.getInstance().eventStatistics(1, 74);
            } else if (mContent == mainTab3Fragment) {
                JiuYaoStatisticsApi.getInstance().eventStatistics(1, 106);
            } else if (mContent == mainTab4Fragment) {
                JiuYaoStatisticsApi.getInstance().eventStatistics(1, 123);
            }*/
        //startFragment(TaskCenterFragment.newInstance(true));
        //setBottomSelect(4);
        //initFragment(4);
        //});

        //onClick(mTabMainPage1);
        /*mRadiogroup.setOnCheckedChangeListener((group, checkedId) -> {
            for (int i = 0; i < group.getChildCount(); i++) {
                View childView = group.getChildAt(i);
                if (childView instanceof RadioButton) {
                    RadioButton radioButton = (RadioButton) childView;
                    radioButton.getPaint().setFakeBoldText(radioButton.getId() == checkedId);
                }
            }
        });
        mRadiogroup.check(R.id.tab_main_page_1);*/
        mTabMainPage1.performClick();

        //todo;
//        if (BuildConfig.IS_CHANGE_TRANSACTION) {//判断是不是修改交易为开服表的临时版本
//            mTabMainPage3.setText("开服表");
//        }
//        if (BuildConfig.IS_REPORT) {//是否为投放包(需要屏蔽掉交易，小号回收)
//            //mLlTabMainCenter.setVisibility(View.GONE);
//            mTabMainPage5.setVisibility(View.GONE);
//            mTabMainPage3.setText("福利中心");
//        }else {
//            //mLlTabMainCenter.setVisibility(View.VISIBLE);
//            mTabMainPage5.setVisibility(View.VISIBLE);
//            mTabMainPage3.setText("交易");
//        }
    }

    CustomDialog mainPageDialog;

    /**
     * 十字键弹窗
     */
    private void showMainPageDialog() {
        if (mainPageDialog == null) {
            mainPageDialog = new CustomDialog(this, LayoutInflater.from(this).inflate(R.layout.layout_dialog_main_page, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.BOTTOM);

            FlexboxLayout mFlexBoxLayout1 = mainPageDialog.findViewById(R.id.flex_box_layout_1);
            FlexboxLayout mFlexBoxLayout2 = mainPageDialog.findViewById(R.id.flex_box_layout_2);


            mFlexBoxLayout1.removeAllViews();
            mFlexBoxLayout1.addView(createDialogMenu(this, 1, R.mipmap.ic_main_page_menu_1, "任务赚金"));
            mFlexBoxLayout1.addView(createDialogMenu(this, 2, R.mipmap.ic_main_page_menu_2, "签到"));
            mFlexBoxLayout1.addView(createDialogMenu(this, 8, R.mipmap.ic_main_page_menu_8, "小号回收", R.mipmap.ic_main_game_new));
            mFlexBoxLayout1.addView(createDialogMenu(this, 3, R.mipmap.ic_main_page_menu_3, "试玩有奖"));

            mFlexBoxLayout2.removeAllViews();
            mFlexBoxLayout2.addView(createDialogMenu(this, 4, R.mipmap.ic_main_page_menu_4, "省钱攻略"));
            if (!AppConfig.isGonghuiChannel()) {
                mFlexBoxLayout2.addView(createDialogMenu(this, 5, R.mipmap.ic_main_page_menu_5, "邀请好友"));
            }
            mFlexBoxLayout2.addView(createDialogMenu(this, 6, R.mipmap.ic_main_page_menu_6, "申请返利"));
            mFlexBoxLayout2.addView(createDialogMenu(this, 7, R.mipmap.ic_main_page_menu_7, "在线客服"));


            TextView mTvDateOfDay = mainPageDialog.findViewById(R.id.tv_date_of_day);
            TextView mTvDayOfWeek = mainPageDialog.findViewById(R.id.tv_day_of_week);
            TextView mTvMonthAndYear = mainPageDialog.findViewById(R.id.tv_month_and_year);
            ImageView mIvClosed = mainPageDialog.findViewById(R.id.iv_closed);
            ImageView mIvNewsBanner = mainPageDialog.findViewById(R.id.iv_news_banner);

            setAppAdBanner(mIvNewsBanner);
            setDateView(mTvDayOfWeek, mTvDateOfDay, mTvMonthAndYear);

            /*mLlMainPageMessage.setOnClickListener(view -> {
                //我的消息
                if (checkUserLogin()) {
                    startFragment(new MessageMainFragment());
                    showMessageTip(false);
                }
                mainPageDialog.dismiss();
            });*/

            mIvClosed.setOnClickListener(view -> {
                mainPageDialog.dismiss();
            });
        }
        mainPageDialog.show();
    }

    protected void onMainMenuClick(int menuId) {
        switch (menuId) {
            case 1:
            case 2:
                //任务赚金
                //签到
                startFragment(TaskCenterFragment.newInstance());
                break;
            case 3:
                //试玩有奖
                startFragment(TryGamePlayListFragment.newInstance());
                break;
            case 4:
                //省钱攻略
                startFragment(new DiscountStrategyFragment());
                break;
            case 5:
                //邀请好友
                startFragment(new InviteFriendFragment());
                break;
            case 6:
                //返利申请
                if (checkUserLogin()) {
                    startFragment(new RebateMainFragment());
                }
                break;
            case 7:
                //客服消息
                startFragment(new KefuCenterFragment());
                break;
            case 8:
                //小号回收
                if (checkUserLogin()) {
                    startFragment(new XhNewRecycleMainFragment());
                }
                break;
        }
        if (mainPageDialog != null && mainPageDialog.isShowing()) {
            mainPageDialog.dismiss();
        }
    }

    private View createDialogMenu(Context mContext, int menuId, @DrawableRes int menuIconRes, String menuName) {
        return createDialogMenu(mContext, menuId, menuIconRes, menuName, 0);
    }

    private View createDialogMenu(Context mContext, int menuId, @DrawableRes int menuIconRes, String menuName, int resId) {
        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);

        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        relativeLayout.setBackgroundResource(R.drawable.ts_shape_f7f7f7_radius);
        relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(ScreenUtil.dp2px(mContext, 72), ViewGroup.LayoutParams.WRAP_CONTENT));

        ImageView image = new ImageView(mContext);
        image.setImageResource(menuIconRes);
        RelativeLayout.LayoutParams imageParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = ScreenUtil.dp2px(mContext, 5);
        imageParam.setMargins(margin, margin, margin, margin);
        imageParam.addRule(RelativeLayout.CENTER_IN_PARENT);
        image.setLayoutParams(imageParam);
        relativeLayout.addView(image);


        if (resId != 0) {
            ImageView newsImage = new ImageView(mContext);
            newsImage.setImageResource(resId);
            RelativeLayout.LayoutParams newsImageParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            newsImageParam.rightMargin = ScreenUtil.dp2px(mContext, 1);
            newsImageParam.topMargin = ScreenUtil.dp2px(mContext, 3);
            newsImageParam.leftMargin = ScreenUtil.dp2px(mContext, -12);
            newsImageParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);


            newsImage.setLayoutParams(newsImageParam);
            relativeLayout.addView(newsImage);
        }
        layout.addView(relativeLayout);

        TextView tv = new TextView(mContext);
        tv.setTextColor(ContextCompat.getColor(mContext, R.color.color_232323));
        tv.setTextSize(13);
        tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tv.setText(menuName);

        LinearLayout.LayoutParams tvParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvParam.gravity = Gravity.CENTER;
        tvParam.topMargin = ScreenUtil.dp2px(mContext, 4);
        tv.setLayoutParams(tvParam);

        layout.addView(tv);

        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);
        layout.setOnClickListener(view -> {
            onMainMenuClick(menuId);
        });
        return layout;
    }

    protected void setAppAdBanner(ImageView mIvNewsBanner) {
        mIvNewsBanner.setVisibility(View.GONE);
        try {
            File file = SdCardManager.getFileMenuDir(this);
            if (file == null) {
                return;
            }
            if (!file.exists()) {
                file.mkdirs();
            }

            String json = ACache.get(file).getAsString(AppStyleConfigs.JSON_KEY);

            Type listType = new TypeToken<SplashVo.AppStyleVo.DataBean>() {
            }.getType();
            Gson gson = new Gson();
            SplashVo.AppStyleVo.DataBean appStyleVo = gson.fromJson(json, listType);

            if (appStyleVo != null) {
                if (appStyleVo.getInterstitial() != null) {
                    mIvNewsBanner.setVisibility(View.VISIBLE);
                    AppJumpInfoBean item = appStyleVo.getInterstitial();
                    GlideUtils.loadRoundImage(this, item.getPic(), mIvNewsBanner, R.mipmap.img_placeholder_v_3);
                    mIvNewsBanner.setOnClickListener(view -> {
                        AppJumpAction appJumpAction = new AppJumpAction(this);
                        appJumpAction.jumpAction(item);
                        if (mainPageDialog != null && mainPageDialog.isShowing()) {
                            mainPageDialog.dismiss();
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mIvNewsBanner.setVisibility(View.GONE);
        }
    }

    private void setDateView(TextView mTvDayOfWeek, TextView mTvDateOfDay, TextView mTvMonthAndYear) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        int date = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);


        if (date < 10) {
            mTvDateOfDay.setText("0" + date);
        } else {
            mTvDateOfDay.setText(String.valueOf(date));
        }

        if (month < 10) {
            mTvMonthAndYear.setText("0" + month + "/" + year);
        } else {
            mTvMonthAndYear.setText(month + "/" + year);
        }

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0) {
            dayOfWeek = 0;
        }
        mTvDayOfWeek.setText(weekDays[dayOfWeek]);
    }


    /**
     * 消息红点提示
     *
     * @param isShow
     */
    private void showMessageTip(boolean isShow) {
        if (mainPageDialog != null) {
            View mViewMessageTips = mainPageDialog.findViewById(R.id.view_message_tips);
            mViewMessageTips.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }


    private MarqueeViewAdapter marqueeViewAdapter;

    /**
     * 设置新游预约轮播
     */
    private void setGameAppointmentTips(List<GameInfoVo> gameInfoVoList) {
        /*XMarqueeView mXMarqueeView = mainPageDialog.findViewById(R.id.x_marquee_view);
        if (mXMarqueeView == null) {
            return;
        }
        if (gameInfoVoList == null) {
            mXMarqueeView.setVisibility(View.GONE);
            return;
        }
        mXMarqueeView.setVisibility(View.VISIBLE);
        if (marqueeViewAdapter == null) {
            marqueeViewAdapter = new MarqueeViewAdapter(gameInfoVoList, this);
            mXMarqueeView.setAdapter(marqueeViewAdapter);
            mainPageDialog.setOnDismissListener(dialogInterface -> {
                mXMarqueeView.stopFlipping();
            });
        } else {
            marqueeViewAdapter.setData(gameInfoVoList);
        }*/
    }

    class MarqueeViewAdapter extends XMarqueeViewAdapter<GameInfoVo> {

        private Context mContext;

        public MarqueeViewAdapter(List<GameInfoVo> datas, Context context) {
            super(datas);
            mContext = context;
        }

        @Override
        public View onCreateView(XMarqueeView parent) {
            return LayoutInflater.from(mContext).inflate(R.layout.layout_marqueeview_item_view, null);
        }

        @Override
        public void onBindView(View parent, View view, int position) {
            GameInfoVo gameInfoVo = mDatas.get(position);
            //布局内容填充
            TextView tvOne = view.findViewById(R.id.marquee_tv_one);
            tvOne.setText(gameInfoVo.getGame_appointment_info());
            view.setOnClickListener(view1 -> {
                goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
                if (mainPageDialog != null) {
                    mainPageDialog.dismiss();
                }
            });
        }
    }

    /**
     * 设置App主题样式
     * App底部Tab
     */
    private void setAppStyle() {
        File file = SdCardManager.getFileMenuDir(this);
        if (file == null) {
            return;
        }
        String json = ACache.get(file).getAsString(AppStyleConfigs.JSON_KEY);

        Type listType = new TypeToken<SplashVo.AppStyleVo.DataBean>() {
        }.getType();
        Gson gson = new Gson();
        SplashVo.AppStyleVo.DataBean appStyleVo = gson.fromJson(json, listType);
        setDefaultStyle();

//        if (appStyleVo != null && appStyleVo.getApp_bottom_info() != null) {
//            setAppStyleInfo(appStyleVo);
//        } else {
//            //默认主题
//            if (isOtherStyle) {
//                switch (BuildConfig.APP_TEMPLATE) {
//                    case -1:
//                        setDefaultStyle();
//                        break;
//                    case 0:
//                        setTestStyle();
//                        break;
//                    case 1:
//                        setMoyuStyle();
//                        break;
//                    case 2:
//                        setBHWStyle();
//                        break;
//                    default:
//                        setMoyuStyle();
//                        break;
//                }
//            } else {
//                setDefaultStyle();
//            }
//        }
    }

    private BaseMvvmFragment mContent;

    BaseMvvmFragment mainTab1Fragment, mainTab2Fragment, mainTab3Fragment, mainTab4Fragment, mainTab5Fragment;

    private void changeTabFragment(BaseMvvmFragment tagFragment) {
        if (mContent == tagFragment) {
            return;
        }
        if (tagFragment == mainTab1Fragment && mainTab1Fragment instanceof MainFragment) {
            showFloat();
        } else {
            hideFloat();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!tagFragment.isAdded()) {
            if (mContent != null) {
                transaction.hide(mContent);
            }
            transaction.add(R.id.fl_container, tagFragment).commitAllowingStateLoss();
        } else {
            if (mContent != null) {
                transaction.hide(mContent);
            }
            transaction.show(tagFragment).commitAllowingStateLoss();
        }
        mContent = tagFragment;

        if (mContent == mainTab1Fragment) {
            JiuYaoStatisticsApi.getInstance().eventStatistics(2, 73);
        } else if (mContent == mainTab2Fragment) {
            JiuYaoStatisticsApi.getInstance().eventStatistics(1, 14);
        } else if (mContent == mainTab3Fragment) {
            JiuYaoStatisticsApi.getInstance().eventStatistics(1, 16);
        } else if (mContent == mainTab4Fragment) {
            JiuYaoStatisticsApi.getInstance().eventStatistics(1, 17);
        }
    }


    /**
     * @param isSelected true--Tab1选择 其他未选中  false --其他选择 Tab1未选中
     */
    public void onTab1ClickIncludeOthers(boolean isSelected) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (isSelected) {
            params.setMargins(0, 0, 0, 0);
        } else {
            float density = ScreenUtil.getScreenDensity(this);
            params.setMargins(0, 0, 0, (int) (48 * density));
        }
        mFlContainer.setLayoutParams(params);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_main_page_1: {
                setBottomSelect(0);
                initFragment(0);
            }
            break;
            case R.id.tab_main_page_2: {
                setBottomSelect(1);
                initFragment(1);
            }
            break;
            case R.id.tab_main_page_3: {
                setBottomSelect(2);
                initFragment(2);

                IMUtilsKt.countClick(() -> {
                    startFragment(new LoginFragment());
                    return null;
                });

            }
            break;
            case R.id.tab_main_page_4: {
                setBottomSelect(3);
                initFragment(3);
            }
            break;
            case R.id.tab_main_page_5: {
                setBottomSelect(4);
                initFragment(4);
                IMUtilsKt.countClick(() -> {
                    ImSDK.eventViewModelInstance.getStartGame().postValue(true);
                    return null;
                });
            }
            break;
            default:
                break;
        }
    }


    private void setBottomSelect(int index) {
        mTabMainPage1.setSelected(false);
        mTabMainPage1.setChecked(false);
        mTabMainPage2.setSelected(false);
        mTabMainPage2.setChecked(false);
        mTabMainPage3.setSelected(false);
        mTabMainPage3.setChecked(false);
        mTabMainPage4.setSelected(false);
        mTabMainPage4.setChecked(false);
        mTabMainPage5.setSelected(false);
        mTabMainPage5.setChecked(false);
        switch (index) {
            case 0:
                mTabMainPage1.setSelected(true);
                mTabMainPage1.setChecked(true);
                break;
            case 1:
                mTabMainPage2.setSelected(true);
                mTabMainPage2.setChecked(true);
                break;
            case 2:
                mTabMainPage3.setSelected(true);
                mTabMainPage3.setChecked(true);
                break;
            case 3:
                mTabMainPage4.setSelected(true);
                mTabMainPage4.setChecked(true);
                break;
            case 4:
                mTabMainPage5.setSelected(true);
                mTabMainPage5.setChecked(true);
                break;
        }
    }

    public void mainTab1Click() {
        mTabMainPage1.performClick();
    }

    public void mainTab2Click() {
        mTabMainPage2.performClick();
    }

    public void mainTab3Click() {
        mTabMainPage3.performClick();
    }

    public void mainTab4Click() {
        mTabMainPage4.performClick();
    }


    private void initFragment(int index) {
        switch (index) {
            case 0:
                if (mainTab1Fragment == null) {
                    mainTab1Fragment = new MainFragment();
                } else {
                    if (mContent == mainTab1Fragment && mainTab1Fragment instanceof MainFragment) {
                        MainFragment mainFragment = (MainFragment) this.mainTab1Fragment;
                        if (mainFragment.fragmentList.get(mainFragment.tabCurrentItem) instanceof MainPageTuiJianFragment) {
                            ((MainPageTuiJianFragment) mainFragment.fragmentList.get(mainFragment.tabCurrentItem)).scrollToTop();
                        } else {
                            mainFragment.backToRecyclerTop();
                        }
                    }
                }
                changeTabFragment(mainTab1Fragment);
                break;
            case 1:
                if (mainTab2Fragment == null) {
                    //                    mainTab2Fragment = new ServerFragment();
                    //                    mainTab2Fragment = new TsServerFragment();
                    mainTab2Fragment = NewMainGameFragment.newInstance(0);
                }
                changeTabFragment(mainTab2Fragment);
                break;
            case 2:
//                if (mainTab3Fragment == null) {
//                    //                    mainTab3Fragment = new ActivityAnnouncementFragment();
//                    if (BuildConfig.IS_CHANGE_TRANSACTION) {//判断是不是修改交易为开服表的临时版本
//                        mainTab3Fragment = TsServerListFragment.newInstance("开服表");
//                    }else if (BuildConfig.IS_REPORT){
//                        mainTab3Fragment = TaskCenterFragment.newInstance(true);
//                    }else {
//                        mainTab3Fragment = new TransactionMainFragment1();
//                    }
//                }
                if (mainTab3Fragment == null) {
                    mainTab3Fragment = new TransactionMainFragment1();
                    //mainTab3Fragment = TsServerListFragment.newInstance("开服表");
                }


                changeTabFragment(mainTab3Fragment);
                break;
            case 3:
                if (mainTab4Fragment == null) {
                    InitDataVo.ProfileSettingVo profileSetting = Setting.PROFILE_SETTING;
                    if (profileSetting != null && profileSetting.web_switch == 1 && !TextUtils.isEmpty(profileSetting.web_url)) {
                        mainTab4Fragment = BrowserFragment.newInstance(profileSetting.web_url);
                    } else {
                        mainTab4Fragment = new NewUserMineFragment1();
                    }
                }
                changeTabFragment(mainTab4Fragment);
                break;
            case 4:
                if (mainTab5Fragment == null) {
                    //福利中心
                    mainTab5Fragment = TaskCenterFragment.newInstance(true);
                    //mainTab5Fragment = BrowserFragment.newInstance("https://cocos.xiaodianyouxi.com/games/brainOut/");
                    //mainTab5Fragment = new KefuCenterFragment();
                }
                changeTabFragment(mainTab5Fragment);
                break;
            default:
                break;
        }
    }


    /**
     * 跳转主页面-游戏
     *
     * @param index 0 分类  1 开服
     */
    public void goToMainGamePage(int index) {
        if (mainTab2Fragment != null) {
            if (mainTab2Fragment instanceof NewMainGameFragment) {
                ((NewMainGameFragment) mainTab2Fragment).selectPageItem(index);
            }
        } else {
            if (mainTab2Fragment == null) {
                mainTab2Fragment = new NewMainGameFragment();
            }
        }
        changeTabFragment(mainTab2Fragment);
        mTabMainPage2.performClick();
    }

    /**
     * 跳转主页面-游戏-分类
     *
     * @param game_genre_id
     */
    public void goToMainGamePageByGenreId(int game_type, int game_genre_id) {
        if (mainTab2Fragment != null) {
            if (mainTab2Fragment instanceof NewMainGameFragment) {
                ((NewMainGameFragment) mainTab2Fragment).selectPageByGenreId();
            }
        } else {
            if (mainTab2Fragment == null) {
                mainTab2Fragment = NewMainGameFragment.newInstance(game_type, game_genre_id);
            }
        }
        changeTabFragment(mainTab2Fragment);
        mTabMainPage2.performClick();
    }

    private long exitTime;

    private final int BACK_OFF_INTERVAL_TIME = 2000;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (Jzvd.backPress()) {
                return true;
            }
            if (!isToolbarShow) {
                showBottomToolbar();
                return true;
            }

            if (BuildConfig.APP_TEMPLATE == 9999 || BuildConfig.APP_TEMPLATE == 9998) {
                IMUtilsKt.localExit(this);
            } else {
                if ((System.currentTimeMillis() - exitTime) > BACK_OFF_INTERVAL_TIME) {
                    Toaster.show( "再按一次退出" + getResources().getString(R.string.app_name));
                    exitTime = System.currentTimeMillis();
                } else {
                    if (AppConfig.isNeedShenMa()) {
                        GismSDK.onExitApp();
                    }
                    AppManager.getInstance().AppExit();
                }
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(EventCenter eventCenter) {
        super.onEvent(eventCenter);
        if (eventCenter.getEventCode() == EventConfig.USER_LOGIN_EVENT_CODE) {
            if (mainPagerDialogHelper != null) {
                mainPagerDialogHelper.refreshMainPagerDialog();
            }
            if (!Setting.REFUND_GAME_LIST_TGID.equals(AppUtils.getTgid())) {
                getRefundGames();
            }
        } else if (eventCenter.getEventCode() == EventConfig.SHOW_APP_CHANGE_NAME_EVENT_CODE) {
            if (mViewModel != null && mViewModel.mRepository != null) {
                UserInfoVo.DataBean user = (UserInfoVo.DataBean) eventCenter.getData();
                //app更名   懂游戏->巴兔游戏
                //mViewModel.mRepository.showAppChangeNameTipDialog(this, user);
                //                new AppPopDialogHelper(this, null).showUserRecallDialog(user);
            }
        } else if (eventCenter.getEventCode() == EventConfig.SEND_PUT_CHANNEL_ERR) {
            Log.e("SEND_PUT_CHANNEL_ERR", "SEND_PUT_CHANNEL_ERR");
            if (Setting.INPUT_CHANNEL_GAME_ID != 0) {
                sendErrLog();
            }
        }
    }

    private void sendErrLog() {
        if (mViewModel != null) {
            Map<String, String> params = new TreeMap<>();
            params.put("gameid", String.valueOf(Setting.INPUT_CHANNEL_GAME_ID));
            params.put("appid", BuildConfig.APP_UPDATE_ID);
            mViewModel.errLog(params, new OnBaseCallback() {
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


   /* @Override
    public void setShareInvite() {
        super.setShareInvite();
        if (mShareHelper != null && inviteDataInfoVo != null) {
            mShareHelper.showShareInviteFriend(inviteDataInfoVo);
        }
    }

    @Override
    protected void onShareSuccess() {
        super.onShareSuccess();
        if (mViewModel != null) {
            mViewModel.addInviteShare(1);
        }
    }*/


    private void getAppVersion() {
        if (mViewModel != null) {
            mViewModel.getAppVersion(new OnBaseCallback<VersionVo>() {
                @Override
                public void onSuccess(VersionVo versionVo) {
                    if (versionVo != null) {
                        if (versionVo.isStateOK()) {
                            if (versionVo.getData() != null) {
                                VersionDialogHelper versionDialogHelper = new VersionDialogHelper(MainActivity.this, () -> {
                                    if (versionVo.getData().getIsforce() == 1) {
                                        AppManager.getInstance().AppExit();
                                    }
                                });
                                versionDialogHelper.showVersion(versionVo.getData());
                            }
                        }
                    }
                }
            });
        }
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

    private void getAppPopData() {
        if (mViewModel != null) {
            mViewModel.getAppPopData(new OnBaseCallback<AppPopDataVo>() {
                @Override
                public void onSuccess(final AppPopDataVo data) {
                    if (data != null && data.isStateOK()) {
                        AppPopDialogHelper appPopDialogHelper = new AppPopDialogHelper(MainActivity.this, viewId -> {
                            switch (viewId) {
                                case R.id.image:
                                    appJumpAction(data.getData());
                                    break;
                                default:
                                    break;
                            }
                        });
                        appPopDialogHelper.showAppPopDialog(data.getData());
                    }
                }
            });
        }
    }


    private void getKefuMessageData() {
        if (mViewModel != null) {
            Runnable runnable1 = () -> {
                MessageVo messageVo = MessageDbInstance.getInstance().getMaxIdMessageVo(1);
                runOnUiThread(() -> {
                    int maxID = 0;
                    if (messageVo != null) {
                        maxID = messageVo.getMessage_id();
                    }
                    mViewModel.getKefuMessageData(getStateEventKey().toString(), maxID, new OnBaseCallback<MessageListVo>() {
                        @Override
                        public void onSuccess(MessageListVo messageListVo) {
                            if (messageListVo != null) {
                                if (messageListVo.isStateOK()) {
                                    saveMessageToDb(messageListVo.getData());
                                    Runnable runnable = () -> {
                                        int messageCount = MessageDbInstance.getInstance().getUnReadMessageCount(1);
                                        runOnUiThread(() -> {
                                            showMessageTip(messageCount > 0);
                                        });
                                    };
                                    new Thread(runnable).start();
                                }
                            }
                        }
                    });
                });
            };
            new Thread(runnable1).start();
        }
    }

    /**
     * 获取用户新游预约信息
     */
    private void getUserGameAppointmentInfo() {
        if (mViewModel != null) {
            SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
            String last_timestamp = spUtils.getString(SpConstants.SP_KEY_USER_GAME_APPOINTMENT_TIMESTAMP, "0");
            mViewModel.getUserGameAppointmentInfo(last_timestamp, new OnBaseCallback<UserGameAppointmentVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                    setGameAppointmentTips(null);
                }

                @Override
                public void onSuccess(UserGameAppointmentVo data) {
                    if (data != null && data.isStateOK()) {
                        if (data.getData() != null) {
                            String last_timestamp = String.valueOf(data.getData().getLast_timestamp());
                            spUtils.putString(SpConstants.SP_KEY_USER_GAME_APPOINTMENT_TIMESTAMP, last_timestamp);
                            if (data.getData().getData() != null && !data.getData().getData().isEmpty()) {
                                setGameAppointmentTips(data.getData().getData());
                            }
                        }
                    }
                }
            });
        }
    }

    private void saveMessageToDb(List<MessageInfoVo> messageInfoVoList) {
        Runnable runnable = () -> {
            if (messageInfoVoList != null) {
                for (MessageInfoVo messageInfoVo : messageInfoVoList) {
                    MessageVo messageVo = messageInfoVo.transformIntoMessageVo();
                    MessageDbInstance.getInstance().saveMessageVo(messageVo);
                }
            }
        };
        new Thread(runnable).start();
    }


    /**
     * 设置主题样式
     *
     * @param dataBean
     */
    private void setAppStyleInfo(SplashVo.AppStyleVo.DataBean dataBean) {
        if (dataBean == null) {
            return;
        }
        if (mViewModel == null) {
            return;
        }
        try {
            mViewModel.setBottomTabSelector(this, mTabMainPage1, AppStyleConfigs.TAB_MAIN_1_NORMAL_FILE_NAME, AppStyleConfigs.TAB_MAIN_1_SELECT_FILE_NAME);
            mViewModel.setBottomTabSelector(this, mTabMainPage2, AppStyleConfigs.TAB_MAIN_2_NORMAL_FILE_NAME, AppStyleConfigs.TAB_MAIN_2_SELECT_FILE_NAME);
            mViewModel.setBottomTabSelector(this, mTabMainPage3, AppStyleConfigs.TAB_MAIN_3_NORMAL_FILE_NAME, AppStyleConfigs.TAB_MAIN_3_SELECT_FILE_NAME);
            mViewModel.setBottomTabSelector(this, mTabMainPage4, AppStyleConfigs.TAB_MAIN_4_NORMAL_FILE_NAME, AppStyleConfigs.TAB_MAIN_4_SELECT_FILE_NAME);

            mIvCenterButton.setImageBitmap(mViewModel.decodeCenterBitmap(this, mViewModel.getMenuFilePath(this, AppStyleConfigs.TAB_MAIN_CENTER_FILE_NAME)));

            SplashVo.AppStyleVo.BottomInfo bottomInfo = dataBean.getApp_bottom_info();
            if (bottomInfo != null) {
                int selectColor = Color.parseColor(bottomInfo.getButton_selected_color());
                int normalColor = Color.parseColor(bottomInfo.getButton_default_color());

                mViewModel.setBottomTabColor(mTabMainPage1, normalColor, selectColor);
                mViewModel.setBottomTabColor(mTabMainPage2, normalColor, selectColor);
                mViewModel.setBottomTabColor(mTabMainPage3, normalColor, selectColor);
                mViewModel.setBottomTabColor(mTabMainPage4, normalColor, selectColor);
            }


        } catch (Exception e) {
            e.printStackTrace();
            setDefaultStyle();
        }
    }

    /**
     * 设置默认App主题样式
     */
    private void setDefaultStyle() {
        if (mViewModel == null) {
            return;
        }

        mViewModel.setDefaultBottomTabSelector(this, mTabMainPage1, R.drawable.ic_main_1_normal, R.drawable.ic_main_1_select);
        mViewModel.setDefaultBottomTabSelector(this, mTabMainPage2, R.drawable.ic_main_2_normal, R.drawable.ic_main_2_select);
        mViewModel.setDefaultBottomTabSelector(this, mTabMainPage3, R.drawable.ic_main_3_normal, R.drawable.ic_main_3_select);
//        if (BuildConfig.IS_REPORT) {
//            mViewModel.setDefaultBottomTabSelector(this, mTabMainPage3, R.mipmap.ic_main_3_normal_1, R.mipmap.ic_main_3_select_1);
//        } else {
//            mViewModel.setDefaultBottomTabSelector(this, mTabMainPage3, R.drawable.ic_main_kfb_normal, R.drawable.ic_main_kfb_select);
//        }
        mViewModel.setDefaultBottomTabSelector(this, mTabMainPage4, R.drawable.ic_main_4_normal, R.drawable.ic_main_4_select);
        //mViewModel.setDefaultBottomTabSelector(this, mTabMainPage5, R.drawable.ic_main_kf_normal, R.drawable.ic_main_kf_select);
        mViewModel.setDefaultBottomTabSelector(this, mTabMainPage5, R.drawable.ic_main_fuli_normal, R.drawable.ic_main_fuli_select);


        int selectColor = ContextCompat.getColor(this, R.color.color_232323);
        int normalColor = ContextCompat.getColor(this, R.color.color_7c7e80);

        mViewModel.setBottomTabColor(mTabMainPage1, normalColor, selectColor);
        mViewModel.setBottomTabColor(mTabMainPage2, normalColor, selectColor);
        mViewModel.setBottomTabColor(mTabMainPage3, normalColor, selectColor);
        mViewModel.setBottomTabColor(mTabMainPage4, normalColor, selectColor);
        mViewModel.setBottomTabColor(mTabMainPage5, normalColor, selectColor);

        mIvCenterButton.setImageResource(R.mipmap.ic_main_center_button_new);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mContent != null) {
            mContent.onActivityResult(requestCode, resultCode, data);
        }
    }


    /****2018.05.02 滑动隐藏底部导航************************************************************************************************************************************/
    private boolean isToolbarShow = true;
    private boolean isShowingToolbar = false;
    private boolean isHidingToolbar = false;

    /**
     * 显示底部导航
     */
    public void showBottomToolbar() {
        if (!isToolbarShow && !isShowingToolbar) {
            isShowingToolbar = true;

            float density = ScreenUtil.getScreenDensity(this);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(0, 0, 0, (int) (48 * density));

            int bottomToolbarHeight = mFlBottomToolbar.getHeight();

            ObjectAnimator animator = ObjectAnimator.ofFloat(mFlBottomToolbar, "translationY", bottomToolbarHeight, 0);
            animator.setDuration(getResources().getInteger(R.integer.duration_main_toolbar));
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    if (mContent instanceof IAppBarScroll) {
                        ((IAppBarScroll) mContent).expandAppBarLayout("translationY", bottomToolbarHeight, 0);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isToolbarShow = true;
                    mFlContainer.setLayoutParams(params);
                    if (mContent == mainTab1Fragment && mainTab1Fragment instanceof MainFragment) {
                        ((MainFragment) mainTab1Fragment).setLayoutFloatParams();
                    }
                    isShowingToolbar = false;
                }
            });
            animator.start();
            if (mContent == mainTab1Fragment && mainTab1Fragment instanceof MainFragment) {
                ((MainFragment) mainTab1Fragment).showFloatAnim();
            }
        }

    }

    /**
     * 隐藏底部导航
     */
    public void hideBottomToolbar() {
        if (isToolbarShow && !isHidingToolbar) {
            isHidingToolbar = true;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(0, 0, 0, 0);

            int bottomToolbarHeight = mFlBottomToolbar.getHeight();

            ObjectAnimator animator = ObjectAnimator.ofFloat(mFlBottomToolbar, "translationY", 0, bottomToolbarHeight);
            animator.setDuration(getResources().getInteger(R.integer.duration_main_toolbar));
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    if (mContent instanceof IAppBarScroll) {
                        ((IAppBarScroll) mContent).collapsingAppBarLayout("translationY", 0, bottomToolbarHeight);
                    }
                    mFlContainer.setLayoutParams(params);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isToolbarShow = false;
                    isHidingToolbar = false;
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    super.onAnimationRepeat(animation);
                    Logs.e("onAnimationRepeat");
                }
            });
            animator.start();

            if (mContent == mainTab1Fragment && mainTab1Fragment instanceof MainFragment) {
                ((MainFragment) mainTab1Fragment).hideFloatAnim();
            }
        }

    }

    /**
     * @param dx
     * @param dy
     */
    public void onRecyclerViewScrolled(View view, int dx, int dy) {
        if (mContent == mainTab1Fragment && mainTab1Fragment instanceof MainFragment) {
            ((MainFragment) mainTab1Fragment).onItemRecyclerViewScrolled(view, dx, dy);
        }
    }


    /**
     * MainPagerDialog 点击事件跳转
     */
    private OnMainPagerClickListener onMainPagerClickListener = viewId -> {
        switch (viewId) {
            case R.id.tag_main_menu_user_games:
                //我的游戏
                if (checkUserLogin()) {
                    startFragment(new MyFavouriteGameListFragment());
                    JiuYaoStatisticsApi.getInstance().eventStatistics(6, 85);
                }
                break;
            case R.id.tag_main_menu_rewards:
                //奖励申请
                if (checkUserLogin()) {
                    startFragment(new RebateMainFragment());
                    JiuYaoStatisticsApi.getInstance().eventStatistics(6, 86);
                }
                break;
            case R.id.tag_main_menu_welfare:
                //转游福利
                if (checkUserLogin()) {
                    startFragment(new TransferMainFragment());
                    JiuYaoStatisticsApi.getInstance().eventStatistics(6, 90);
                }
                break;
            case R.id.tag_main_menu_server:
                //开服表
                mainTab2Click();
                break;
            case R.id.tag_main_menu_game_classification:
                //游戏分类
                startFragment(new GameClassificationFragment());
                JiuYaoStatisticsApi.getInstance().eventStatistics(6, 88);
                break;
            case R.id.tag_main_menu_activity:
                //活动公告
                startFragment(new ActivityAnnouncementFragment());
                JiuYaoStatisticsApi.getInstance().eventStatistics(6, 89);
                break;
            case R.id.tag_main_menu_web_game:
                //BT页游
                break;
            case R.id.tag_main_menu_more:
                //敬请期待
                Toaster.show( "敬请期待");
                break;
            case R.id.tag_main_menu_try_game:
                //试玩赚钱
                startFragment(TryGamePlayListFragment.newInstance());
                JiuYaoStatisticsApi.getInstance().eventStatistics(6, 87);
                break;
            case R.id.tag_main_menu_xh_recycle:
                //小号回收
                if (checkUserLogin()) {
                    startFragment(new XhRecycleMainFragment());
                }
                break;
            case R.id.tag_main_menu_1:
                //省钱攻略
                startFragment(new DiscountStrategyFragment());
                JiuYaoStatisticsApi.getInstance().eventStatistics(6, 81);
                break;
            case R.id.tag_main_menu_2:
                //消息
                if (checkUserLogin()) {
                    startFragment(new MessageMainFragment());
                    JiuYaoStatisticsApi.getInstance().eventStatistics(6, 82);
                }
                break;
            case R.id.tag_main_menu_3:
                //货币明细
                if (checkUserLogin()) {
                    startFragment(new CurrencyMainFragment());
                    JiuYaoStatisticsApi.getInstance().eventStatistics(6, 83);
                }
                break;
            case R.id.tag_main_menu_4:
                //设置
                startFragment(new SettingManagerFragment());
                JiuYaoStatisticsApi.getInstance().eventStatistics(6, 84);
                break;
            case R.id.main_page_id_bottom_button_1:
                //bottom_button_1 邀请赚钱
                if (checkUserLogin()) {
                    UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
                    if (userInfo.getInvite_type() == 1) {
                        if (mViewModel != null) {
                            mViewModel.getShareData("1", new OnBaseCallback<InviteDataVo>() {

                                @Override
                                public void onSuccess(InviteDataVo data) {
                                    if (data != null && data.isStateOK() && data.getData() != null) {
                                        if (data.getData().getInvite_info() != null) {
                                            InviteDataVo.InviteDataInfoVo inviteInfo = data.getData().getInvite_info();
                                            new ShareHelper(MainActivity.this).shareToAndroidSystem(inviteInfo.getCopy_title(), inviteInfo.getCopy_description(), inviteInfo.getUrl());
                                        }
                                    }
                                }
                            });
                        }
                    } else {
                        startFragment(new InviteFriendFragment());
                    }
                    JiuYaoStatisticsApi.getInstance().eventStatistics(6, 91);
                }
                break;
            case R.id.main_page_id_bottom_button_2:
                //bottom_button_2 联系客服
                //                startFragment(new KefuMainFragment());
                startFragment(new KefuHelperFragment());
                JiuYaoStatisticsApi.getInstance().eventStatistics(6, 93);
                break;
            case R.id.main_page_id_gold:
                //金币充值
                if (checkUserLogin()) {
                    startFragment(TopUpFragment.newInstance());
                    JiuYaoStatisticsApi.getInstance().eventStatistics(6, 79);
                }
                break;
            case R.id.main_page_id_integral:
                //我的积分
                //2019.10.21 跳转指向 每日签到二级页面
                //                mainTab4Click();
                //                startFragment(new TaskSignInFragment());
                //2019.10.30 跳转积分商城
                startFragment(new CommunityIntegralMallFragment());
                JiuYaoStatisticsApi.getInstance().eventStatistics(6, 80);
                break;
            case R.id.main_page_id_login:
                //用户登录
                checkUserLogin();
                break;
            case R.id.main_page_id_user:
                //用户个人资料 玩家主页
                if (checkUserLogin()) {
                    int uid = UserInfoModel.getInstance().getUserInfo().getUid();
                    startFragment(CommunityUserFragment.newInstance(uid));
                    JiuYaoStatisticsApi.getInstance().eventStatistics(6, 78);
                }
                break;
            default:
                break;
        }
    };

    public void getSuperBirthdayRewardStatus() {
        if (mViewModel != null) {
            mViewModel.getSuperBirthdayRewardStatus(new OnBaseCallback<SuperBirthdayRewardVo>() {
                @Override
                public void onSuccess(SuperBirthdayRewardVo payInfoVo) {
                    if (payInfoVo != null && payInfoVo.isStateOK()) {
                        if (payInfoVo.getData().getStatus().equals("now")) {
                            getBirthdayReward();
                        }
                    } else {
                        Toaster.show( payInfoVo.getMsg());
                    }
                }
            });
        }
    }

    public void getBirthdayReward() {
        if (mViewModel != null) {
            mViewModel.getBirthdayReward(new OnBaseCallback<BirthdayRewardVo>() {
                @Override
                public void onSuccess(BirthdayRewardVo payInfoVo) {
                    if (payInfoVo != null && payInfoVo.isStateOK()) {
                        showBirthdayDialog(payInfoVo);
                    } else {
                        Toaster.show(payInfoVo.getMsg());
                    }
                }
            });
        }
    }

    private void getUnionVipPop() {
        if (!UserInfoModel.getInstance().isLogined()) {
            return;
        }

        if (mViewModel != null) {
            UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
            mViewModel.getUnionVipPop(String.valueOf(userInfo.getUid()), userInfo.getToken(), new OnBaseCallback<UnionVipDataVo>() {
                @Override
                public void onSuccess(final UnionVipDataVo data) {
                    if (data != null && data.isStateOK()) {
                        SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
                        spUtils.putLong("showUnionVipDialogTime", System.currentTimeMillis());
                        long unionVip = spUtils.getLong("unionVip");
                        if (data.getData().getUtime() < unionVip) {
                            return;
                        }
                        CustomDialog unionVipDialog = new CustomDialog(MainActivity.this, LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_dialog_union_vip, null),
                                ScreenUtils.getScreenWidth(MainActivity.this) - SizeUtils.dp2px(MainActivity.this, 40),
                                WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
                        unionVipDialog.setCanceledOnTouchOutside(false);
                        String text = data.getData().getText();
                        String type = "";
                        if (!TextUtils.isEmpty(text)) {
                            String[] split = text.split(" ");
                            if (split.length == 2) {
                                type = split[0];
                                text = split[1];
                            }
                        }
                        ((TextView) unionVipDialog.findViewById(R.id.tv_type)).setText(type);
                        ((TextView) unionVipDialog.findViewById(R.id.tv_contact)).setText(text);
                        if (data.getData() != null && !TextUtils.isEmpty(data.getData().getMsg()))
                            ((TextView) unionVipDialog.findViewById(R.id.tv_tips)).setText(data.getData().getMsg());

                        String finalText = text;
                        unionVipDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
                            /*if (unionVipDialog != null && unionVipDialog.isShowing()){
                                unionVipDialog.dismiss();
                            }*/
                            if (CommonUtils.copyString(MainActivity.this, finalText)) {
                                Toaster.show("联系方式已复制");
                            }
                        });
                        unionVipDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
                            if (unionVipDialog != null && unionVipDialog.isShowing()) {
                                unionVipDialog.dismiss();
                            }
                        });
                        unionVipDialog.show();
                        spUtils.putLong("unionVip", data.getData().getNtime());
                    }
                }
            });
        }
    }

    private void rmbusergive() {
        if (mViewModel != null) {
            mViewModel.rmbusergive(new OnBaseCallback<RmbusergiveVo>() {
                @Override
                public void onSuccess(RmbusergiveVo rmbusergiveVo) {
                    if (rmbusergiveVo != null && rmbusergiveVo.getData() != null && rmbusergiveVo.isStateOK()) {
                        if (0 < rmbusergiveVo.getData().getCoupon_total() && "yes".equals(rmbusergiveVo.getData().getHas_give())) {
                            showRmbusergive(rmbusergiveVo.getData());
                        }
                    }
                }
            });
        }
    }

    private void rmbusergiveGetReward(RmbusergiveVo.DataBean dataBean) {
        if (mViewModel != null) {
            mViewModel.rmbusergiveGetReward(new OnBaseCallback<RmbusergiveVo>() {
                @Override
                public void onSuccess(RmbusergiveVo rmbusergiveVo) {
                    if (rmbusergiveVo != null && rmbusergiveVo.isStateOK()) {
                        showRmbusergiveSucceed(dataBean);
                    }
                }
            });
        }
    }

    private void showBirthdayDialog(BirthdayRewardVo payInfoVo) {
        CustomDialog tipsDialog = new CustomDialog(MainActivity.this, LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_dialog_vip_birthday, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        ((TextView) tipsDialog.findViewById(R.id.tv_price)).setText(String.valueOf(payInfoVo.getData().getAmount()));
        ((TextView) tipsDialog.findViewById(R.id.tv_cdt)).setText("满" + payInfoVo.getData().getCdt() + "元可用");
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.show();
    }

    private void showRmbusergive(RmbusergiveVo.DataBean dataBean) {
        CustomDialog tipsDialog = new CustomDialog(MainActivity.this, LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_dialog_rmbusergive_ungain, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        tipsDialog.setCancelable(false);
        tipsDialog.setCanceledOnTouchOutside(false);
        ((TextView) tipsDialog.findViewById(R.id.tv_coupon_total)).setText(String.valueOf(dataBean.getCoupon_total()));
        RecyclerView recyclerView = (RecyclerView) tipsDialog.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RmbusergiveAdapter(MainActivity.this, dataBean.getCoupon_list()));
        tipsDialog.findViewById(R.id.ll_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            rmbusergiveGetReward(dataBean);
        });
        tipsDialog.show();
    }

    private void showRmbusergiveSucceed(RmbusergiveVo.DataBean dataBean) {
        CustomDialog tipsDialog = new CustomDialog(MainActivity.this, LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_dialog_rmbusergive_gained, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        tipsDialog.setCancelable(false);
        tipsDialog.setCanceledOnTouchOutside(false);
        RecyclerView recyclerView = (RecyclerView) tipsDialog.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RmbusergiveAdapter(MainActivity.this, dataBean.getCoupon_list()));
        tipsDialog.findViewById(R.id.ll_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing())
                tipsDialog.dismiss();
            //代金券
            if (UserInfoModel.getInstance().isLogined())
                //startFragment(GameWelfareFragment.newInstance(2));
                startFragment(new MyCouponsListFragment());
        });
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing())
                tipsDialog.dismiss();
        });
        tipsDialog.show();
    }

    private void getComeBack(boolean isRelogin) {
        if (mViewModel != null) {
            mViewModel.getComeBack(new OnBaseCallback<ComeBackVo>() {
                @Override
                public void onSuccess(ComeBackVo comeBackVo) {
                    if (comeBackVo != null && comeBackVo.getData() != null && comeBackVo.isStateOK()) {
                        if ("yes".equals(comeBackVo.getData().getIs_come_back())) {
                            if (0 <= comeBackVo.getData().getDay()) {
                                if (!isRelogin) {
                                    SPUtils spUtils = new SPUtils(MainActivity.this, Constants.SP_COMMON_NAME);
                                    if (CommonUtils.isTodayOrTomorrow(spUtils.getLong("showComeBackDialogTime", 0)) < 0) {
                                        spUtils.putLong("showComeBackDialogTime", System.currentTimeMillis());
                                        if (TextUtils.isEmpty(spUtils.getString("showComeBack", ""))
                                                || !spUtils.getString("showComeBack", "").equals(comeBackVo.getData().getId())) {
                                            spUtils.putString("showComeBack", comeBackVo.getData().getId());
                                            showComeBackDialog(comeBackVo.getData());
                                        } else {
                                            showComeBackDialogSecond(comeBackVo.getData());
                                        }
                                    }
                                }
                            }
                            ((MainFragment) mainTab1Fragment).mClMainComeBack.setVisibility(View.VISIBLE);
                            ((MainFragment) mainTab1Fragment).mClMainComeBack.setOnClickListener(v -> {
                                if (UserInfoModel.getInstance().isLogined()) {
                                    BrowserActivity.newInstance(MainActivity.this, comeBackVo.getData().getHd_url(), true);
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    private void showComeBackDialog(ComeBackVo.DataBean dataBean) {
        CustomDialog tipsDialog = new CustomDialog(MainActivity.this, LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_dialog_come_back, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        tipsDialog.setCancelable(false);
        tipsDialog.setCanceledOnTouchOutside(false);
        TextView mTvTips = (TextView) tipsDialog.findViewById(R.id.tv_tips);
        SpannableString spannableString = new SpannableString("将于" + CommonUtils.formatTimeStamp(dataBean.getEnd_time() * 1000, "yyyy-MM-dd ") + "过期");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FEA047")), 2, 12, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvTips.setText(spannableString);
        tipsDialog.findViewById(R.id.iv_close).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) {
                tipsDialog.dismiss();
            }
        });
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) {
                tipsDialog.dismiss();
            }
            BrowserActivity.newInstance(MainActivity.this, dataBean.getHd_url(), true);
        });
        tipsDialog.show();
    }

    private void showComeBackDialogSecond(ComeBackVo.DataBean dataBean) {
        CustomDialog tipsDialog = new CustomDialog(MainActivity.this, LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_dialog_come_back_second, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        tipsDialog.setCancelable(false);
        tipsDialog.setCanceledOnTouchOutside(false);
        TextView mTvTips = (TextView) tipsDialog.findViewById(R.id.tv_tips);
        SpannableString spannableString = new SpannableString("将于" + CommonUtils.formatTimeStamp(dataBean.getEnd_time() * 1000, "yyyy-MM-dd ") + "过期");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FEA047")), 2, 12, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvTips.setText(spannableString);
        ((TextView) tipsDialog.findViewById(R.id.tv_day)).setText(String.valueOf(dataBean.getDay()));
        tipsDialog.findViewById(R.id.iv_close).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) {
                tipsDialog.dismiss();
            }
        });
        tipsDialog.findViewById(R.id.iv_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) {
                tipsDialog.dismiss();
            }
            BrowserActivity.newInstance(MainActivity.this, dataBean.getHd_url(), true);
        });
        tipsDialog.show();
    }

    private void getGameAdvertData(final int gameid) {
        if (gameid > 0) {
            if (mViewModel != null) {
                mViewModel.getChannelGameData(gameid, new OnBaseCallback<GameDataVo>() {
                    @Override
                    public void onSuccess(GameDataVo data) {
                        if (data != null && data.isStateOK() && data.getData() != null) {
                            GameInfoVo gameInfoVo = data.getData();
                            //                            showSingleGameDialog(gameInfoVo);
                            if (gameInfoVo.getStatus() == 1) {
                                startFragment(GameDetailInfoFragment.newInstanceAdvert(gameid, gameInfoVo));
                            } else {
                                Logs.e("gameid = " + gameid + " 已经失效了");
                            }
                        }
                    }
                });
            }
        }
    }

    private void getAllPop(boolean isReLogin) {
        if (mViewModel != null) {
            mViewModel.getAllPop(new OnBaseCallback<AllPopVo>() {
                @Override
                public void onSuccess(AllPopVo allPopVo) {
                    if (allPopVo != null && allPopVo.getData() != null && allPopVo.isStateOK()) {
                        AllPopVo.DataBean popVoData = allPopVo.getData();
                        if (lingQuJump) {
                            BrowserActivity.newInstance(MainActivity.this, popVoData.getXinren_pop().url);
                            lingQuJump = false;
                        }
                        new MainDialogHelper(MainActivity.this, popVoData, mViewModel, isReLogin).show();
                        if (popVoData.getXinren_pop() != null) {
                            if (popVoData.getXinren_pop().rookies_show == 1) {
                                ((MainFragment) mainTab1Fragment).showVsMainRookies(true, popVoData.getXinren_pop().url);
                            } else {
                                ((MainFragment) mainTab1Fragment).showVsMainRookies(false, popVoData.getXinren_pop().url);
                            }
                        }
                        if (popVoData.getCome_back() != null && "yes".equals(popVoData.getCome_back().getIs_come_back())) {
                            if (!((MainFragment) mainTab1Fragment).isCloseComeBack) {
                                ((MainFragment) mainTab1Fragment).mClMainComeBack.setVisibility(View.VISIBLE);
                                ((MainFragment) mainTab1Fragment).mClMainComeBack.setOnClickListener(v -> {
                                    if (UserInfoModel.getInstance().isLogined()) {
                                        BrowserActivity.newInstance(MainActivity.this, popVoData.getCome_back().getHd_url(), true);
                                    }
                                });
                            } else {
                                ((MainFragment) mainTab1Fragment).mClMainComeBack.setVisibility(View.GONE);
                                ((MainFragment) mainTab1Fragment).mClMainComeBack.setOnClickListener(null);
                            }
                        }
                    }
                }
            });
        }
    }

    private void appFloatIcon() {
        if (mViewModel != null) {
            mViewModel.appFloatIcon(new OnBaseCallback<AppFloatIconVo>() {
                @Override
                public void onSuccess(AppFloatIconVo data) {
                    if (data != null && data.getData() != null) {
                        if (mContent == mainTab1Fragment) {
                            initFloat(data.getData());
                            showFloat();
                        } else {
                            hideFloat();
                        }
                    }
                }
            });
        }
    }

    private void startFragment(BaseFragment fragment) {
        //loadFragment(fragment);
        FragmentHolderActivity.startFragmentInActivity(this, fragment);
    }

    /**
     * SDK 跳转
     *
     * @param isFromSDK
     * @param SDKPackageName
     * @param sdkAction
     */
    private void sdkActionJump(boolean isFromSDK, String SDKPackageName, SdkAction sdkAction) {
        Logs.e("sdkActionJump");
        if (sdkAction == null) {
            return;
        }

        int uid = sdkAction.getUid();
        String token = sdkAction.getToken();
        String username = sdkAction.getUsername();

        Logs.e("uid = " + uid);
        Logs.e("username = " + username);

        if (SdkAction.ACTION_SDK_JUMP_GAME_DETAIL.equals(sdkAction.getAction())) {
            //SDK跳转游戏详情
            int gameid = sdkAction.getParam().getGameid();
            int game_type = sdkAction.getParam().getGame_type();
            Logs.d("SDK_TAG:" + "gameid:" + gameid);
            Logs.d("SDK_TAG:" + "game_type:" + game_type);
            if (gameid != 0) {
                startFragment(GameDetailInfoFragment.newInstance(gameid, game_type, isFromSDK, SDKPackageName));
            }
        }

        /*****2017.10.24 增加sdk跳转*****************************************/
        if (SdkAction.ACTION_SDK_JUMP_CUSTOMER_SERVICE_CENTER.equals(sdkAction.getAction())) {
            //SDK跳转客服页面
            startFragment(new KefuHelperFragment());
        }

        if (SdkAction.ACTION_SDK_JUMP_REBATES_CENTER.equals(sdkAction.getAction())) {
            if (!UserInfoModel.getInstance().isLogined()) {
                //用SDK的带过来的身份登录
                if (mViewModel != null) {
                    mViewModel.switchUser2(uid, token, username, (result) -> {
                        startFragment(new RebateMainFragment());
                    });
                }
            } else {
                startFragment(new RebateMainFragment());
            }
        }

        if (SdkAction.ACTION_SDK_JUMP_PTB_RECHARGE.equals(sdkAction.getAction())) {
            if (!UserInfoModel.getInstance().isLogined()) {
                //用SDK的带过来的身份登录
                if (mViewModel != null) {
                    mViewModel.switchUser2(uid, token, username, (result) -> {
                        startFragment(new TopUpFragment());
                    });
                }
            } else {
                startFragment(new TopUpFragment());
            }
        }

        /**
         * 2021.05.19 sdk->app跳转，过度到appJumpAction
         */
        if (SdkAction.ACTION_SDK_JUMP_COMMON.equals(sdkAction.getAction())) {
            AppJumpAction jumpAction = new AppJumpAction(this);
            jumpAction.jumpAction(new AppJumpInfoBean(sdkAction.getPage_type(), sdkAction.getParam()));
        }
    }

    private boolean isNeedSwitchUser(boolean isFromSDK, String SDKPackageName, SdkAction sdkAction) {
        if (sdkAction == null) {
            return false;
        }
        if (sdkAction.getUid() == 0) {
            return false;
        }
        if (TextUtils.isEmpty(sdkAction.getUsername())) {
            return false;
        }

        int uid = sdkAction.getUid();
        String token = sdkAction.getToken();
        String username = sdkAction.getUsername();

        UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
        if (userInfoBean == null) {
            Logs.e("userInfoBean == null");
            //App本身未登录
            if (mViewModel != null) {
                mViewModel.switchUser2(uid, token, username, (result) -> {
                    sdkActionJump(isFromSDK, SDKPackageName, sdkAction);
                });
            }
            return true;
        }

        if (userInfoBean.getUid() != sdkAction.getUid()) {
            //SDKUser != AppUser
            if (mViewModel != null) {
                mViewModel.showSwitchUserDialog(this, sdkAction, () -> {
                    sdkActionJump(isFromSDK, SDKPackageName, sdkAction);
                });
            }
            return true;
        } else {
            //SDKUser == AppUser
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Jzvd.releaseAllVideos();
    }

    private boolean isFirstLoad;

    private XToast mLocalGameFloat;

    @Override
    public void onResume() {
        super.onResume();
        //home back
        if (!isFirstLoad) {
            isFirstLoad = true;
            new Handler().postDelayed(() -> onClick(mTabMainPage1), 200);
        }
        JzvdStd.goOnPlayOnResume();
        if (UserInfoModel.getInstance().isLogined()) {
            EventBus.getDefault().post(new EventCenter(EventConfig.NIM_CLIENT_NEW_MESSAGE));
            //initChatCount();
        }

        //if (!StringUtils.isEmpty(BuildConfig.GAME_O_CHANNEL_NAME) && UserInfoModel.getInstance().isLogined()) {
//            if (mAppFloat == null) {
//                mAppFloat = new XToast<>(this)
//                        .setContentView(com.chaoji.common.R.layout.app_floatview) // 设置自定义布局
//                        .setGravity(Gravity.START | Gravity.TOP) // 设置初始位置在右上角
//                        .setYOffset(com.chaoji.other.blankj.utilcode.util.SizeUtils.dp2px(150f)) // Y 轴偏移，单位 px
//                        .setDraggable(new SpringHideDraggable(0.6f, 3000L)) // 设置为可拖拽
//                        .setOnClickListener((toast, view) -> {
//                            Toaster.show("你点击了图标");
//                        });
//                mAppFloat.show();
//            }
        //}
        //FloatViewManager.showAppFloat(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        //home back
        JzvdStd.goOnPlayOnPause();
//        if (mAppFloat != null) {
//            mAppFloat.cancel();
//            mAppFloat = null;
//        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logs.e(getClass().getName() + "---------onSaveInstanceState---------");
        if (UserInfoModel.getInstance().isLogined()) {
            UserInfoVo.DataBean user = UserInfoModel.getInstance().getUserInfo();
            int uid = user.getUid();
            String username = user.getUsername();
            String token = user.getToken();
            outState.putInt("uid", uid);
            outState.putString("username", username);
            outState.putString("token", token);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Logs.e(getClass().getName() + "---------onRestoreInstanceState---------");
        if (savedInstanceState != null) {
            int uid = savedInstanceState.getInt("uid", 0);
            String username = savedInstanceState.getString("username");
            String token = savedInstanceState.getString("token");
            if (mViewModel != null) {
                mViewModel.getUserInfo(uid, username, token);
            }
        }
    }

    private boolean isFloatInit = false;

    private void initFloat(AppFloatIconVo.DataBean data) {
        if (isFloatInit) {
            return;
        }

        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_home_left_view, null);
        ImageView ivIcon = inflate.findViewById(R.id.iv_icon);
        GlideUtils.loadNormalImage(this, data.getPic(), ivIcon, R.mipmap.ic_placeholder);
        inflate.setOnClickListener(v -> {
            AppJumpAction jumpAction = new AppJumpAction(this);
            jumpAction.jumpAction(new AppJumpInfoBean(data.getPage_type(), data.getParam()));
        });

        EasyFloat.with(this)
                .setTag("home_left_float")
                .setSidePattern(SidePattern.RESULT_LEFT)
                .setImmersionStatusBar(true)
                .setGravity(Gravity.START, 0, ScreenUtil.getScreenHeight(this) / 3 * 2)
                .setLayout(inflate)
                .show();
        isFloatInit = true;
    }

    private void showFloat() {
        if (!EasyFloat.isShow("home_left_float")) EasyFloat.show("home_left_float");
    }

    private void hideFloat() {
        if (EasyFloat.isShow("home_left_float")) EasyFloat.hide("home_left_float");
    }


    // 在 MainActivity.java 中添加此方法
    public void loadFragment(BaseFragment fragment) {
        // 使用 supportFragmentManager 在 ID 为 R.id.fl_container 的容器中替换 Fragment
        // 注意：R.id.fl_container 是您布局中已有的FrameLayout的ID
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.f_container, fragment)
                .addToBackStack(null) // 允许按返回键时返回上一个Fragment
                .commit();
    }
}
