package com.zqhy.app.core.view.browser;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.blankj.utilcode.util.AppUtils;
import com.chaoji.other.blankj.utilcode.util.Logs;
import com.chaoji.other.hjq.toast.Toaster;
import com.chaoji.other.immersionbar.ImmersionBar;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zqhy.app.App;
import com.zqhy.app.base.BaseActivity;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.ui.eventbus.WxPayCallBack;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.receiver.WxPayReceiver;
import com.zqhy.app.utils.AndroidBug5497Workaround;
import com.zqhy.app.utils.CommonUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @date 2018/11/14
 */

public class BrowserActivity extends BaseActivity {

    private JavaScriptInterface ajsi;
    private JavaScriptInterface1 ajsi1;

    public static void newInstance(Activity activity, String url) {
        newInstance(activity, url, false, "", "");
    }

    public static void newInstance(Activity activity, String url, boolean needAddUserInfo) {
        newInstance(activity, url, false, "", "", needAddUserInfo);
    }

    public static void newInstance(Activity activity, String url, String referer) {
        Intent intent = new Intent(activity, BrowserActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("referer", referer);
        activity.startActivity(intent);
    }

    public static void newInstance(Activity activity, String url, boolean isH5Game, String gamename, String gameid) {
        Intent intent = new Intent(activity, BrowserActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("isH5Game", isH5Game);
        intent.putExtra("gameid", gameid);
        intent.putExtra("gamename", gamename);
        activity.startActivity(intent);
    }

    public static void newInstance(Activity activity, String url, boolean isH5Game, String gamename, String gameid, boolean needAddUserInfo) {
        Intent intent = new Intent(activity, BrowserActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("isH5Game", isH5Game);
        intent.putExtra("gameid", gameid);
        intent.putExtra("gamename", gamename);
        intent.putExtra("needAddUserInfo", needAddUserInfo);
        activity.startActivity(intent);
    }

    public static void newInstance(Activity activity, String url, boolean isH5Game, String gamename, String gameid, boolean needAddUserInfo, boolean showTitle) {
        Intent intent = new Intent(activity, BrowserActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("isH5Game", isH5Game);
        intent.putExtra("gameid", gameid);
        intent.putExtra("gamename", gamename);
        intent.putExtra("needAddUserInfo", needAddUserInfo);
        intent.putExtra("showTitle", showTitle);
        activity.startActivity(intent);
    }


    public static void newInstance1(Activity activity, String url, boolean showTitle) {
        Intent intent = new Intent(activity, BrowserActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("isH5Game", false);
        intent.putExtra("gameid", "");
        intent.putExtra("gamename", "");
        intent.putExtra("showTitle", showTitle);
        activity.startActivity(intent);
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_browser;
    }

    private String url;
    private boolean isH5Flag;
    private String gamename;
    private String gameid;
    //定义支付域名（替换成公司申请H5的域名即可）
    private String referer;
    private boolean needAddUserInfo = true;
    private boolean showTitle = true;

    private LinearLayout titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        url = getIntent().getStringExtra("url");
        if (url.contains("work.weixin.qq")) {//微信客服
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                // 设置数据为你想要打开的网址
                intent.setData(Uri.parse(url));
                // 启动Intent，这将打开设备上的默认浏览器（或询问用户选择浏览器）
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
        }


        referer = getIntent().getStringExtra("referer");
        Logs.e("url = " + url);
        isH5Flag = getIntent().getBooleanExtra("isH5Game", isH5Flag);
        gamename = getIntent().getStringExtra("gamename");
        gameid = getIntent().getStringExtra("gameid");
        needAddUserInfo = getIntent().getBooleanExtra("needAddUserInfo", true);
        showTitle = getIntent().getBooleanExtra("showTitle", true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        addUserInfo();
        super.onCreate(savedInstanceState);
        AndroidBug5497Workaround.assistActivity(this);
        setImmersiveStatusBar(true);
        if (App.isShowEasyFloat) {
            showEasyFloat(this, "browser");
        }
    }

    /**
     * 添加uid和token
     */
    private void addUserInfo() {
        if (TextUtils.isEmpty(url)) {
            finish();
        } else {
            if (needAddUserInfo && UserInfoModel.getInstance().isLogined()) {
                url = CommonUtils.filterUrlWithUidAndToken(url);
            }
            Logs.e("(addUserInfo())url = " + url);

            /*if (!url.contains("uid=") && !url.contains("&token=")) {
                String username = "";
                String token = "";

                UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
                if (userInfoBean == null) {
                    return;
                }

                username = userInfoBean.getUsername();
                token = userInfoBean.getToken();

                StringBuilder sb = new StringBuilder(url);
                if (url != null) {
                    if (!url.contains("?")) {
                        sb.append("?");
                    }
                    sb.append("uid=").append(username);
                    sb.append("&token=").append(token);
                }
                url = sb.toString();
                Logs.e("(addUserInfo())url = " + url);
            }*/
        }
    }

    private boolean isUserReLogin = false;

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        isUserReLogin = true;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        showSuccess();
        bindView();
        setH5FullScreen();
        mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
        mWebView.removeJavascriptInterface("accessibility");
        mWebView.removeJavascriptInterface("accessibilityTraversal");
    }

    private LinearLayout mLlToolbar;
    private TextView mTvClosePage;
    private ProgressBar mProgress;
    private ImageView mIvBack;
    private FrameLayout mFlWebContainer;


    private void bindView() {
        mLlToolbar = findViewById(R.id.titleBar_Layout);
        mTvClosePage = findViewById(R.id.tv_close_page);
        //mProgress = findViewById(R.id.progress);
        mIvBack = findViewById(R.id.iv_back);
        mFlWebContainer = findViewById(R.id.fl_web_container);
        ImmersionBar.setTitleBar(this, mLlToolbar);
        mTvClosePage.setOnClickListener(v -> {
            finish();
        });
        mIvBack.setOnClickListener(v -> {
            actionBack();
        });

        if (isH5Flag) {
            setTitle(gamename);
        }
        if (showTitle) {
            mLlToolbar.setVisibility(View.VISIBLE);
        } else {
            mLlToolbar.setVisibility(View.GONE);
        }
        initWebView();
    }

    WebView mWebView;

    private void initWebView() {
        mWebView = new WebView(this.getApplicationContext());
        //添加布局
        mFlWebContainer.addView(mWebView, 0, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        mWebView.setDrawingCacheEnabled(true);

        mWebView.setWebChromeClient(new GameWebChromeClient());
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setDownloadListener(new MyWebViewDownLoadListener());
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //设置自适应屏幕，两者合用
        //将图片调整到适合webview的大小
        mWebView.getSettings().setUseWideViewPort(true);
        // 缩放至屏幕的大小
        mWebView.getSettings().setLoadWithOverviewMode(true);
        if (Build.VERSION.SDK_INT >= 21) {
            mWebView.getSettings().setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        WebSettings webSettings = mWebView.getSettings();

        if (url != null && url.startsWith("file://")) {
            webSettings.setJavaScriptEnabled(false);
        } else {
            webSettings.setJavaScriptEnabled(true);
        }
        // init webview settings
        webSettings.setAllowContentAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        mWebView.loadUrl(url);

        ajsi = new JavaScriptInterface(this, mWebView);
        ajsi1 = new JavaScriptInterface1(this, mWebView);
        mWebView.addJavascriptInterface(ajsi, ajsi.getInterface());
        mWebView.addJavascriptInterface(ajsi1, ajsi1.getInterface());
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.EVENT_KEY_BROWSER_SHARE_CALLBACK, String.class).observe(this, value -> {
            if (ajsi != null) {
                ajsi.onShareResultToJs(value.toString());
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isH5Flag) {
                confirmExitH5Game();
                return true;
            } else {
                actionBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void actionBack() {
        if (mWebView != null && mWebView.canGoBack()) {
            mTvClosePage.setVisibility(View.VISIBLE);
            mWebView.goBack();
        } else {
            exit();
        }
    }

    private void confirmExitH5Game() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("是否退出" + gamename + "?")
                .setPositiveButton("是", (DialogInterface dialogInterface, int i) -> {
                    dialogInterface.dismiss();
                    exit();
                })
                .setNegativeButton("否", (DialogInterface dialogInterface, int i) -> {
                    dialogInterface.dismiss();
                })
                .create();
        dialog.show();
    }

    private void exit() {
        if (mWebView != null) {
            //调用js中的函数：backfatherpage()
            mWebView.loadUrl("javascript:backfatherpage()");
        }
        finish();
    }

    private void setH5FullScreen() {
        if (isH5Flag) {
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mLlToolbar.setVisibility(View.GONE);
            ajsi.setGameid(gameid);
        }
    }


    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventCenter eventCenter) {
        super.onEvent(eventCenter);
        if (eventCenter.getEventCode() == WxPayReceiver.WXPAY_EVENT_CODE && isH5Flag) {
            WxPayCallBack wxPayCallBack = (WxPayCallBack) eventCenter.getData();
            if (ajsi != null) {
                ajsi.onWechatPayBack(wxPayCallBack);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isUserReLogin) {
            addUserInfo();
            if (mWebView != null) {
                clearWebView();
                mWebView.removeAllViews();
                mWebView.destroy();
                mWebView = null;
            }
            initWebView();
            isUserReLogin = false;
        }
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            clearWebView();
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }

    private void clearWebView() {
        mWebView.clearHistory();
        mWebView.clearFormData();
        mWebView.clearCache(true);

        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(mWebView.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();

        cookieManager.removeAllCookie();
        cookieSyncManager.sync();

        clearWebViewCache();
    }

    /**
     * 清除WebView缓存
     */
    public void clearWebViewCache() {
        // WebView 缓存文件
        File appCacheDir = getDir("webview", Context.MODE_PRIVATE);
        if (appCacheDir.exists()) {
            deleteFile(appCacheDir);
        }
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        }
    }


    private class GameWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            /*if (newProgress < 100) {
                mProgress.setVisibility(View.VISIBLE);
                mProgress.setProgress(newProgress);
            }
            if (newProgress == 100) {
                mProgress.setVisibility(View.GONE);
            }*/
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            String mTitle = title;
            setTitle(mTitle);
        }
    }

    private class MyWebViewDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }


    private class MyWebViewClient extends WebViewClient {
        //定义支付域名（替换成公司申请H5的域名即可）
        private String referer;

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            Logs.e("shouldOverrideUrlLoading---> url:" + url);
            if (parseScheme(url)) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setComponent(null);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (url.contains("alipays://platformapi")) {
                if (!AppUtils.isAppInstalled("com.eg.android.AlipayGphone")) {
                    Toaster.show("请先下载安装支付宝");
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                return true;
            } else if (parseH5WxPayScheme(url) || parseH5WxPayScheme1(url)) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            } else if (parseQqScheme(url)) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            } else if (url.startsWith("https://wpa1.qq.com/o5TJZzSm?_type=wpa&qidian=true&ction=1")) {//微信客服
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            } else {
                Map<String, String> extraHeaders = new HashMap<>();
                extraHeaders.put("Referer", referer);
                webView.loadUrl(url, extraHeaders);
            }
            return true;
        }

        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            //            super.onReceivedSslError(webView, sslErrorHandler, sslError);
            CertifiUtils.OnCertificateOfVerification(sslErrorHandler, mWebView.getUrl());
        }
    }

    //2019.11.28 兼容支付宝网页链接调起支付宝App
    private boolean parseScheme(String url) {
        if (url == null) {
            return false;
        }
        if (url.contains("platformapi/startapp")) {
            return true;
        } else if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                && (url.contains("platformapi") && url.contains("startapp"))) {
            return true;
        }
        return false;
    }

    /**
     * 2020.2.18 微信H5支付跳转
     *
     * @param url
     * @return
     */
    private boolean parseH5WxPayScheme(String url) {
        if (url == null) {
            return false;
        }
        return url.startsWith("weixin://wap/pay?");
    }
    private boolean parseH5WxPayScheme1(String url) {
        if (url == null) {
            return false;
        }
        return url.startsWith("weixin://dl/business/?");
    }

    /**
     * 2020.05.29 打开 mqqwpa和mqqapi两个协议
     */
    private boolean parseQqScheme(String url) {
        if (url == null) {
            return false;
        }
        return url.startsWith("mqqapi") || url.startsWith("mqqwpa");
    }
}
