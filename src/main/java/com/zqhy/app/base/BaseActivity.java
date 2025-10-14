package com.zqhy.app.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.chaoji.other.blankj.utilcode.util.Logs;
import com.chaoji.other.hjq.toast.Toaster;
import com.chaoji.other.immersionbar.ImmersionBar;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.SidePattern;
import com.mvvm.base.AbsLifecycleActivity;
import com.mvvm.base.AbsViewModel;
import com.mvvm.base.BaseMvvmFragment;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.umeng.analytics.MobclickAgent;
import com.zqhy.app.App;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;
import com.zqhy.app.core.data.model.share.InviteDataVo;
import com.zqhy.app.core.tool.MResource;
import com.zqhy.app.core.tool.OsUtil;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.KeyboardUtils;
import com.zqhy.app.core.ui.dialog.LoadingDialog;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.browser.BrowserActivity1;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.core.view.game.forum.tool.EditLinearLayout;
import com.zqhy.app.core.view.login.LoginActivity;
import com.zqhy.app.core.view.main.MainActivity;
import com.zqhy.app.glide.GlideCircleTransform;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.listener.NetworkPollingListener;
import com.zqhy.app.newproject.R;
import com.zqhy.app.report.TtDataReportAgency;
import com.zqhy.app.share.ShareHelper;
import com.zqhy.app.share.WxShareEvent;
import com.zqhy.app.utils.AppManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/8
 */

public abstract class BaseActivity<T extends AbsViewModel> extends AbsLifecycleActivity<T> {

    private LoadingDialog loadingDialog;
    private BroadcastReceiver loginReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLoading();
        //添加到栈中
        AppManager.getInstance().addActivity(this);
        EventBus.getDefault().register(this);

        loginReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                String action=intent.getAction();
                if(action.equals("sdk.authlogin")){
                    finish();
                }
            }
        };
        IntentFilter loginfilter = new IntentFilter();
        loginfilter.addAction("sdk.authlogin");
        registerReceiver(loginReceiver, loginfilter);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        TtDataReportAgency.getInstance().onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        TtDataReportAgency.getInstance().onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        dismissNetWorkDialog();
        super.onDestroy();
        //从栈中移除
        AppManager.getInstance().finishActivity(this);
        EventBus.getDefault().unregister(this);
        unregisterReceiver(loginReceiver);
    }

    private Dialog noNetWorkDialog;

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(EventCenter eventCenter) {
        onUserLoginOrLogoutListener(eventCenter);
        onEventNetWorkListener(eventCenter);
        onApiServiceListener(eventCenter);
        //onShareListener(eventCenter);
    }

    /**
     * 监听用户登录登出
     *
     * @param eventCenter
     */
    private void onUserLoginOrLogoutListener(EventCenter eventCenter) {
        if (eventCenter.getEventCode() == EventConfig.USER_LOGIN_EVENT_CODE) {
            Logs.e("用户重新登录了");
            onUserReLogin();
        }
    }

    protected void onUserReLogin() {

    }

    /**
     * ApiService监测
     *
     * @param eventCenter
     */
    private void onApiServiceListener(EventCenter eventCenter) {
        if (eventCenter.getEventCode() == EventConfig.REFRESH_API_SERVICE_EVENT_CODE) {
            int integer = (int) eventCenter.getData();
            if (integer == NetworkPollingListener.POLLING_SUCCESS) {
                if (mViewModel != null) {
                    mViewModel.refreshApiService();
                }
            }
        }
    }

    /**
     * 网络监测
     *
     * @param eventCenter
     */
    private void onEventNetWorkListener(EventCenter eventCenter) {
        if (eventCenter.getEventCode() == EventConfig.NET_STATE_OK_EVENT_CODE) {
            dismissNetWorkDialog();
        } else if (eventCenter.getEventCode() == EventConfig.NET_STATE_ERROR_EVENT_CODE) {
            if (noNetWorkDialog == null) {
                noNetWorkDialog = new AlertDialog.Builder(this)
                        .setTitle("无网络连接")
                        .setMessage("去开启网络?")
                        .setNegativeButton("否", (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        })
                        .setPositiveButton("是", (dialogInterface, i) -> {
                            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                            startActivity(intent);
                            dialogInterface.dismiss();
                        })
                        .setCancelable(false)
                        .create();
            }
            if (!noNetWorkDialog.isShowing()) {
                noNetWorkDialog.show();
            }
        }
    }

    private void dismissNetWorkDialog() {
        if (noNetWorkDialog != null && noNetWorkDialog.isShowing()) {
            noNetWorkDialog.dismiss();
        }
    }


    protected ShareHelper                   mShareHelper;
    protected InviteDataVo.InviteDataInfoVo inviteDataInfoVo;

    /**
     * 分享监测
     *
     * @param eventCenter
     */
    private void onShareListener(EventCenter eventCenter) {
        loadingComplete();
        if (eventCenter.getEventCode() == EventConfig.SHARE_INVITE_EVENT_CODE) {
            String eventTag = eventCenter.getTag();
            if (TextUtils.isEmpty(eventTag)) {
                return;
            }
            String eventKey = (String) getStateEventKey();
            InviteDataVo inviteDataVo = (InviteDataVo) eventCenter.getData();
            if (inviteDataVo != null && eventTag.equals(eventKey)) {
                if (inviteDataVo.isStateOK() && inviteDataVo.getData() != null) {
                    inviteDataInfoVo = inviteDataVo.getData().getInvite_info();
                    mShareHelper = new ShareHelper(this, new ShareHelper.OnShareListener() {
                        @Override
                        public void onSuccess() {
                            //ToastT.success("分享成功");
                            Toaster.show("分享成功");
                            Logs.e("OnShareListener : onSuccess");
                            onShareSuccess();
                        }

                        @Override
                        public void onError(String error) {
                            //Toaster.show("分享失败");
                            Toaster.show("分享失败");
                            Logs.e("OnShareListener : onError：" + error);
                        }

                        @Override
                        public void onCancel() {
                            //ToastT.normal("分享取消");
                            Toaster.show("分享取消");
                            Logs.e("OnShareListener : onCancel");
                        }
                    });
                    setShareInvite();
                }
            }
        } else if (eventCenter.getEventCode() == EventConfig.SHARE_WECHAT_CALLBACK_EVENT_CODE) {
            WxShareEvent mWxShareEvent = (WxShareEvent) eventCenter.getData();
            if (mWxShareEvent != null && mShareHelper != null) {
                mShareHelper.handleWxShareListener(mWxShareEvent);
            }
        }
    }


    @Override
    protected void dataObserver() {
        super.dataObserver();
    }

    public void setShareHelper(ShareHelper shareHelper) {
        mShareHelper = shareHelper;
    }


    public void setShareInvite() {

    }

    /**
     * 分享回调
     */
    protected void onShareSuccess() {
    }

    @Override
    protected void initStatusBar() {
        ImmersionBar
                .with(this)
                .navigationBarColor(R.color.white)
                .statusBarDarkFont(true)
                .autoDarkModeEnable(true, 0.2f)
                .init();
        View titleBar = findViewById(MResource.getResourceId(this, "id", "titleBar_Layout"));
        if(titleBar!=null){
            Logs.e("initStatusBar",titleBar.getTag());
            ImmersionBar.setTitleBar(this,titleBar);
        }else{
            if (!isStatusBarFullWindow()) {
                return;
            }
            setStatusBarFullWindowMode();
            FrameLayout fl_status_bar = findViewById(MResource.getResourceId(this, "id", "fl_status_bar"));
            if (fl_status_bar == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.height = ScreenUtil.getStatusBarHeight(this)+15;
                fl_status_bar.setLayoutParams(params);
            }
        }
    }

    protected boolean isStatusBarFullWindow() {
        return true;
    }

    private void initLoading() {
        loadingDialog = new LoadingDialog(this);
    }


    public void loading(String msg) {
        if (loadingDialog.isShowing()) {
            return;
        }
        loadingDialog.setTip(msg);
        loadingDialog.show();
    }

    public void loading(int resourceId) {
        loading(getResources().getString(resourceId));
    }

    public void loading() {
        loading("数据加载中...");
    }

    public void loadingComplete() {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 设置状态栏
     */
    protected void setStatusBarFullWindowMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //设置透明状态栏,这样才能让 ContentView 向上
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(getResources().getColor(R.color.transparent));

            ViewGroup mContentView = findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
                ViewCompat.setFitsSystemWindows(mChildView, false);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);

            //首先使 ChildView 不预留空间
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                ViewCompat.setFitsSystemWindows(mChildView, false);
            }
            int statusBarHeight = ScreenUtil.getStatusBarHeight(this);
            //需要设置这个 flag 才能设置状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //避免多次调用该方法时,多次移除了 View
            if (mChildView != null && mChildView.getLayoutParams() != null && mChildView.getLayoutParams().height == statusBarHeight) {
                //移除假的 View.
                mContentView.removeView(mChildView);
                mChildView = mContentView.getChildAt(0);
            }
            if (mChildView != null) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
                //清除 ChildView 的 marginTop 属性
                if (lp != null && lp.topMargin >= statusBarHeight) {
                    lp.topMargin -= statusBarHeight;
                    mChildView.setLayoutParams(lp);
                }
            }
        }
    }

    /**
     * 注册ActionBar后退键以及标题
     *
     * @param mTitle
     */
    protected void initActionBackBarAndTitle(String mTitle) {
        initActionBackBarAndTitle(mTitle, true);
    }

    /**
     * 注册ActionBar后退键以及标题
     *
     * @param mTitle
     */
    protected void initActionBackBarAndTitle(String mTitle, boolean isShowBack) {
        showActionBack(isShowBack);
        setTitle(mTitle);
    }

    /**
     * 注册ActionBar后退键以及标题
     *
     * @param resId
     */
    protected void initActionBackBarAndTitle(int resId) {
        initActionBackBarAndTitle(getResources().getString(resId));
    }

    /**
     * 注册ActionBar后退键以及标题
     *
     * @param resId
     */
    protected void initActionBackBarAndTitle(int resId, boolean isShowBack) {
        initActionBackBarAndTitle(getResources().getString(resId), isShowBack);
    }

    /**
     * 设置标题
     *
     * @param titleName
     */
    protected void setTitle(String titleName) {
        TextView mTitle = findViewById(MResource.getResourceId(this, "id", "tv_title"));
        RelativeLayout mTitleBar = findViewById(MResource.getResourceId(this, "id", "rl_title_bar"));
        if (mTitleBar != null && mTitle != null) {
            mTitleBar.setVisibility(View.VISIBLE);
            mTitle.setText(titleName);
        }
    }

    /**
     * 设置标题
     */
    @Override
    public void setTitle(int resId) {
        setTitle(getResources().getString(resId));
    }

    /**
     * 设置标题颜色
     */
    @Override
    public void setTitleColor(int resId) {
        TextView mTitle = findViewById(MResource.getResourceId(this, "id", "tv_title"));
        if (mTitle != null) {
            mTitle.setTextColor(resId);
        }
    }

    protected void setActionBackBar(int resId) {
        ImageView mImageView = findViewById(MResource.getResourceId(this, "id", "iv_back"));
        RelativeLayout mTitleBar = findViewById(MResource.getResourceId(this, "id", "rl_title_bar"));
        if (mTitleBar != null && mImageView != null) {
            mImageView.setImageResource(resId);
        }
    }

    protected void showActionBack(boolean isShowBack) {
        ImageView mImageView = findViewById(MResource.getResourceId(this, "id", "iv_back"));
        RelativeLayout mTitleBar = findViewById(MResource.getResourceId(this, "id", "rl_title_bar"));
        if (mTitleBar != null && mImageView != null) {
            mImageView.setOnClickListener(v -> {
                pop();
            });
            mImageView.setVisibility(isShowBack ? View.VISIBLE : View.GONE);
        }
    }

    /************************沉浸式设置*****************************************************/

    /**
     * @param statusBarPlaceColor
     */
    protected void setStatusBar(int statusBarPlaceColor) {
        View mflStatusBar = mRootView.findViewById(MResource.getResourceId(this, "id", "fl_status_bar"));
        if (mflStatusBar != null) {
            if (!OsUtil.isNeedSetStatusBar()) {
                mflStatusBar.setBackgroundColor(statusBarPlaceColor);
            }
        }
    }

    /**
     * 设置沉浸式状态栏
     *
     * @param fontIconDark 状态栏字体和图标颜色是否为深色
     */
    public void setImmersiveStatusBar(boolean fontIconDark) {
        setImmersiveStatusBar(fontIconDark, 0xffcccccc);
    }

    /**
     * 设置沉浸式状态栏
     *
     * @param fontIconDark        状态栏字体和图标颜色是否为深色
     * @param statusBarPlaceColor 设置statusBar背景色
     */
    public void setImmersiveStatusBar(boolean fontIconDark, int statusBarPlaceColor) {
        setStatusBar(statusBarPlaceColor);
        setTranslucentStatus();
        if (OsUtil.isNeedSetStatusBar()) {
            setStatusBarFontIconDark(fontIconDark);
        }
    }

    /**
     * 设置状态栏透明
     */
    private void setTranslucentStatus() {
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置Android状态栏的字体颜色，状态栏为亮色的时候字体和图标是黑色，状态栏为暗色的时候字体和图标为白色
     *
     * @param dark 状态栏字体是否为深色
     */
    private void setStatusBarFontIconDark(boolean dark) {
        // 小米MIUI
        if (OsUtil.isMIUI()) {
            try {
                Window window = getWindow();
                Class clazz = getWindow().getClass();
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                int darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    //状态栏亮色且黑色字体
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
                } else {
                    //清除黑色字体
                    extraFlagField.invoke(window, 0, darkModeFlag);
                }
            } catch (NoSuchMethodException e) {
                //e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


        // 魅族FlymeUI
        if (OsUtil.isFlyme()) {
            try {
                Window window = getWindow();
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

        }

        // android6.0+系统
        // 这个设置和在xml的style文件中用这个<item name="android:windowLightStatusBar">true</item>属性是一样的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (dark) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                int uiVisibility = getWindow().getDecorView().getSystemUiVisibility();
                getWindow().getDecorView().setSystemUiVisibility(uiVisibility | View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }


    private boolean isClickBlankAreaHideKeyboardEnable = true;

    /**
     * 点击空白区域隐藏键盘
     */
    protected boolean clickBlankAreaHideKeyboard() {
        return isClickBlankAreaHideKeyboardEnable;
    }

    public void setClickBlankAreaHideKeyboardEnable(boolean clickBlankAreaHideKeyboardEnable) {
        isClickBlankAreaHideKeyboardEnable = clickBlankAreaHideKeyboardEnable;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        /*if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev) && clickBlankAreaHideKeyboard()) {
                hideKeyboard(v.getWindowToken());
            }
        }*/
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            float x = ev.getRawX();
            float y = ev.getRawY();
            // 获取所有的 EditText 控件
            List<View> editTextList = getAllEditTexts(findViewById(android.R.id.content));
            boolean isEdit = false;
            // 检查触摸点是否在 EditText 内
            for (View editText : editTextList) {
                int[] location = new int[2];
                editText.getLocationOnScreen(location);
                int left = location[0];
                int top = location[1];
                int right = left + editText.getWidth();
                int bottom = top + editText.getHeight();
                if (x >= left && x <= right && y >= top && y <= bottom) {
                    // 触摸点在 EditText 内
                    System.out.println("触摸区域是 EditText");
                    isEdit = true;
                    break;
                }
            }

            if (!isEdit){
                if (clickBlankAreaHideKeyboard()) {
                    //hideKeyboard(v.getWindowToken());
                    KeyboardUtils.hideSoftInput(BaseActivity.this);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 递归获取所有的 不需要隐藏键盘的 控件 EditLinearLayout  EditText
    private List<View> getAllEditTexts(ViewGroup viewGroup) {
        List<View> editTextList = new ArrayList<>();
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof EditLinearLayout) {
                editTextList.add(child);
            }else if (child instanceof EditText) {
                editTextList.add(child);
            } else if (child instanceof ViewGroup) {
                editTextList.addAll(getAllEditTexts((ViewGroup) child));
            }
        }
        return editTextList;
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null) {
            if ((v instanceof EditText)) {
                int[] l = {0, 0};
                v.getLocationInWindow(l);
                int left = l[0],
                        top = l[1],
                        bottom = top + v.getHeight(),
                        right = left + v.getWidth();
                if (event.getX() > left && event.getX() < right
                        && event.getY() > top && event.getY() < bottom) {
                    // 点击EditText的事件，忽略它。
                    return false;
                } else {
                    return true;
                }
            } else {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (getTopFragment() != null && getTopFragment() instanceof BaseMvvmFragment) {
//            ((BaseMvvmFragment) getTopFragment()).onActivityResult(requestCode, resultCode, data);
//        }
        if (mShareHelper != null) {
            IUiListener iUiListener = mShareHelper.getIUiListener();
            Tencent.onActivityResultData(requestCode, resultCode, data, iUiListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (getTopFragment() != null && getTopFragment() instanceof BaseMvvmFragment) {
            ((BaseMvvmFragment) getTopFragment()).onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 检查是否登录
     */
    public boolean checkUserLogin() {
        if (!UserInfoModel.getInstance().isLogined()) {
            startActivity(new Intent(this, LoginActivity.class));
            return false;
        } else {
            return true;
        }
    }



    /**
     * 跳转详情
     *
     * @param json
     */
    protected void appJumpAction(String json) {
        Logs.e("AppJumpAction Json = " + json);
        if (TextUtils.isEmpty(json)) {
            return;
        }
        new AppJumpAction(this).jumpAction(json);
    }

    protected void appJumpAction(AppJumpInfoBean jumpInfoBean) {
        if (jumpInfoBean == null) {
            return;
        }
        new AppJumpAction(this).jumpAction(jumpInfoBean);
    }

    /**
     * 游戏详情
     *
     * @param gameid
     * @param game_type
     */
    public void goGameDetail(int gameid, int game_type) {
        GameDetailInfoFragment gameDetailInfoFragment = GameDetailInfoFragment.newInstance(gameid, game_type);
        FragmentHolderActivity.startFragmentInActivity(this, gameDetailInfoFragment);
    }
    public void goGameDetail(int gameid, int game_type,boolean isFromLocalGame) {
        GameDetailInfoFragment gameDetailInfoFragment = GameDetailInfoFragment.newInstance(gameid, game_type,false,"",false,isFromLocalGame);
        FragmentHolderActivity.startFragmentInActivity(this, gameDetailInfoFragment);
    }

    /**
     * 用户协议
     */
    public void goUserAgreement() {
        Intent intent = new Intent(this, BrowserActivity1.class);
        intent.putExtra("url", AppConfig.APP_REGISTRATION_PROTOCOL);
        startActivity(intent);
    }

    /**
     * 隐私协议
     */
    public void goPrivacyAgreement() {
        Intent intent = new Intent(this, BrowserActivity1.class);
        intent.putExtra("url", AppConfig.APP_PRIVACY_PROTOCOL);
        startActivity(intent);
    }


    /**
     * 用户协议
     */
    public void goAuditUserAgreement() {
        Intent intent = new Intent(this, BrowserActivity1.class);
        intent.putExtra("url", AppConfig.APP_AUDIT_REGISTRATION_PROTOCOL);
        startActivity(intent);
    }

    /**
     * 隐私协议
     */
    public void goAuditPrivacyAgreement() {
        Intent intent = new Intent(this, BrowserActivity1.class);
        intent.putExtra("url", AppConfig.APP_AUDIT_PRIVACY_PROTOCOL);
        startActivity(intent);
    }


    /**
     * 2020.05.20 动态替换strings.xml值
     */
    protected String getStringResByXML(int stringRes, Object... args) {
        try {
            return String.format(getResources().getString(stringRes), args);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 倒序获取栈内的activity 决定在哪个页面显示弹窗
     * @return
     */
    public Class getTheActivity(){
        List<Activity> activityList = App.getActivityList();
        for (int i = activityList.size() - 1; i < activityList.size(); i--) {
            if (i < 0) return LoginActivity.class;
            if (activityList.get(i).getClass() == FragmentHolderActivity.class || activityList.get(i).getClass() == MainActivity.class){
                return activityList.get(i).getClass();
            }
        }
        return LoginActivity.class;
    }

    /**
     * 倒序获取activity在栈内的位置 决定在哪个页面显示弹窗 因为只能出现一个
     * @return
     */
    public int getTheActivityPosition(){
        List<Activity> activityList = App.getActivityList();
        for (int i = activityList.size() - 1; i < activityList.size(); i--) {
            if (i < 0) {
                return -1;
            }
            if (activityList.get(i).getClass() == FragmentHolderActivity.class || activityList.get(i).getClass() == MainActivity.class){
                return i;
            }
        }
        return -1;
    }

    /**
     * 动态替换应用名
     *
     * @param stringRes
     * @return
     */
    protected String getAppNameByXML(int stringRes) {
        String applicationName = getString(R.string.app_name);
        return getStringResByXML(stringRes, applicationName);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics() );
        return res;
    }


    public void showEasyFloat(Activity activity, String tag){
        App.tagList.add(tag);
        View inflate = LayoutInflater.from(activity).inflate(R.layout.layout_back_to_game, null);
        inflate.findViewById(R.id.iv_game_back_close).setOnClickListener(view -> {
            for (String tagItem:App.tagList) {
                EasyFloat.dismiss(tagItem);
            }
            App.isShowEasyFloat = false;
        });
        ImageView ivGameIcon = inflate.findViewById(R.id.iv_game_back);
        GlideApp.with(this)
                .load(App.showEasyFloatGameIcon)
                .placeholder(R.mipmap.ic_user_login_new_sign)
                .error(R.mipmap.ic_user_login_new_sign)
                .transform(new GlideCircleTransform(this, (int) (2 * ScreenUtil.getScreenDensity(this))))
                .into(ivGameIcon);
        ivGameIcon.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(App.showEasyFloatPackageName)){
                Intent intent1 = getPackageManager().getLaunchIntentForPackage(App.showEasyFloatPackageName);
                intent1.putExtra("from","app");
                startActivity(intent1);
            }
            for (String tagItem:App.tagList) {
                EasyFloat.dismiss(tagItem);
            }
            App.isShowEasyFloat = false;
        });
        EasyFloat.with(activity)
                .setTag(tag)
                .setSidePattern(SidePattern.RESULT_LEFT)
                .setImmersionStatusBar(true)
                .setGravity(Gravity.START, 0, ScreenUtil.getScreenHeight(this) - ScreenUtil.dp2px(this, 120))
                .setLayout(inflate).show();
    }
}
