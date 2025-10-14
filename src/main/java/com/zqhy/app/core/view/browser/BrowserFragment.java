package com.zqhy.app.core.view.browser;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author leeham2734
 * @date 2021/8/16-11:30
 * @description
 */
public class BrowserFragment extends BaseFragment {

    private JavaScriptInterface ajsi;

    public static BrowserFragment newInstance(String url) {
        BrowserFragment fragment = new BrowserFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static BrowserFragment newInstance(String url,boolean showStatusBar) {
        BrowserFragment fragment = new BrowserFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        if (!showStatusBar){
            bundle.putBoolean("showStatusBar", false);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_browser;
    }

    @Override
    public int getContentResId() {
        return R.id.fl_web_container;
    }

    private String url;
    private boolean showStatusBar = true;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            url = getArguments().getString("url");
            showStatusBar = getArguments().getBoolean("showStatusBar");
        }
        super.initView(state);
        showSuccess();
        addUserInfo();
        bindView();
        mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
        mWebView.removeJavascriptInterface("accessibility");
        mWebView.removeJavascriptInterface("accessibilityTraversal");
    }

    /**
     * 添加uid和token
     */
    private void addUserInfo() {
        if (!TextUtils.isEmpty(url)) {
            url = CommonUtils.filterUrlWithUidAndToken(url);
            Logs.e("(addUserInfo())url = " + url);
        }
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        addUserInfo();
        mWebView.loadUrl(url);
    }


    private LinearLayout mLlToolbar;
    private FrameLayout  mFlWebContainer;
    private FrameLayout  fl_status_bar;
    private ProgressBar  mProgress;

    private void bindView() {
        mLlToolbar = findViewById(R.id.titleBar_Layout);
        fl_status_bar = findViewById(R.id.fl_status_bar);
        mLlToolbar.setVisibility(View.GONE);
        mFlWebContainer = findViewById(R.id.fl_web_container);
        mProgress = findViewById(R.id.progress);
        if (!showStatusBar){
            //隐藏状态栏占位
            fl_status_bar.setVisibility(View.GONE);
        }
        initWebView();
    }

    WebView mWebView;

    private void initWebView() {
        mWebView = new WebView(_mActivity.getApplicationContext());
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

        ajsi = new JavaScriptInterface(_mActivity, mWebView);
        mWebView.addJavascriptInterface(ajsi, ajsi.getInterface());
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        mWebView.loadUrl(url);
    }

    @Override
    public void onResume() {
        super.onResume();
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
        File appCacheDir = _mActivity.getDir("webview", Context.MODE_PRIVATE);
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
            if (newProgress < 100) {
                mProgress.setVisibility(View.VISIBLE);
                mProgress.setProgress(newProgress);
            }
            if (newProgress == 100) {
                mProgress.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            String mTitle = title;
            setTitleText(mTitle);
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
            } else if (url.startsWith("https://wpa1.qq.com/o5TJZzSm?_type=wpa&qidian=true&ction=1")){//微信客服
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            } else if (url.startsWith("weixin:")){//微信客服
                try {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            } else {
                Map<String, String> extraHeaders = new HashMap<>();
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
