package com.zqhy.app.utils.pay;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.zqhy.app.core.data.model.user.PayInfoVo;
import com.zqhy.app.core.tool.AppUtil;
import com.zqhy.app.core.tool.utilcode.AppsUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.sdcard.SdCardManager;

import java.io.File;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/23
 */

public class WeChatPayInstance {

    private static final String TAG = WeChatPayInstance.class.getSimpleName();

    private static WeChatPayInstance mMethod;

    private WeChatPayInstance() {
    }

    public static WeChatPayInstance getInstance() {
        if (mMethod == null) {
            mMethod = new WeChatPayInstance();
        }
        return mMethod;
    }


    public void startPay(Context mContext, PayInfoVo.DataBean dataBean) {
        if (dataBean == null) {
            return;
        }

        if (isAppAvailable(mContext, dataBean.getPackage_name(), dataBean.getVersionCode())) {
            jumpWechatPay(mContext, dataBean.getPackage_name(), dataBean.getClassName(),dataBean.getOut_trade_no(), dataBean.getWx_url());
        } else {
            showDownloadApp(mContext, "检测您未安装最新版微信支付安全插件，请先下载！", dataBean.getWx_plug_name(), dataBean.getWx_plug_icon(), dataBean.getWx_plug_url());
        }
    }


    /**
     * 判断App是否可用/更新
     *
     * @param context
     * @param packageName       包名
     * @param targetVersionCode 版本号
     * @return
     */
    public boolean isAppAvailable(Context context, String packageName, int targetVersionCode) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                PackageInfo packageInfo = pinfo.get(i);
                String pn = packageInfo.packageName;
                int versionCode = packageInfo.versionCode;
                if (pn.equals(packageName)) {
                    Log.e(TAG, "packageName = " + packageName);
                    Log.i(TAG, "本地包versionCode = " + versionCode);
                    Log.i(TAG, "线上包versionCode = " + targetVersionCode);
                    return true && targetVersionCode <= versionCode;
                }
            }
        }
        return false;
    }

    /**
     * 跳转微信支付
     *
     * @param mContext
     * @param packageName
     * @param out_trade_no
     * @param wx_url
     */
    private void jumpWechatPay(Context mContext, String packageName, String className,String out_trade_no, String wx_url) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.putExtra("package", mContext.getPackageName());
        intent.putExtra("wx_url", wx_url);
        intent.putExtra("out_trade_no", out_trade_no);
        intent.putExtra("orientation", mContext.getResources().getConfiguration().orientation);
        intent.setComponent(new ComponentName(packageName, className));
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

        mContext.startActivity(intent);
    }


    /**
     * 下载App
     *
     * @param mContext
     * @param tipMessage
     * @param fileName
     * @param icon
     * @param url
     */
    private void showDownloadApp(Context mContext, String tipMessage, final String fileName, final String icon, final String url) {
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage(tipMessage)
                .setPositiveButton("确定", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    download(mContext, fileName, icon, url);
                })
                .setNegativeButton("取消", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })
                .setCancelable(false)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    private void download(Context mContext, String fileName, String icon, String url) {
        CustomDialog downloadDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_download_app, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        TextView mTvName = downloadDialog.findViewById(R.id.tvName);
        ProgressBar mProgressBar = downloadDialog.findViewById(R.id.progress_bar);
        Button mBtnDownload = downloadDialog.findViewById(R.id.btnDownload);
        ImageView mIvIcon = downloadDialog.findViewById(R.id.ivIcon);

        if (icon == null || icon.isEmpty()) {
            AppsUtils.AppInfo appInfo = AppsUtils.getAppInfo(mContext);
            String name = appInfo.getName();
            Drawable iconDrawable = appInfo.getIcon();
            mIvIcon.setImageDrawable(iconDrawable);
            mTvName.setText(name);
        } else {
            GlideUtils.loadCircleImage(mContext, icon, mIvIcon, R.mipmap.ic_placeholder);
            mTvName.setText(fileName);
        }

        mBtnDownload.setOnClickListener(v -> downloadDialog.dismiss());

        downloadDialog.setCancelable(false);
        downloadDialog.setCanceledOnTouchOutside(false);
        downloadDialog.show();

        OkGo.<File>get(url)
                .execute(new FileCallback(SdCardManager.getInstance().getDownloadApkDir().getPath(),
                        fileName) {
                    @Override
                    public void onSuccess(Response<File> response) {
                        File targetFile = response.body();
                        if (targetFile.exists()) {
                            AppUtil.install(mContext, targetFile);
                        }
                        if (downloadDialog != null && downloadDialog.isShowing()) {
                            downloadDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        mProgressBar.setMax(100);
                        mProgressBar.setProgress((int) (progress.fraction * 100));
                    }
                });
    }
}
