package com.zqhy.app.core.dialog;

import android.content.Context;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.base.Request;
import com.zqhy.app.core.data.model.version.VersionVo;
import com.zqhy.app.core.tool.AppUtil;
import com.zqhy.app.core.tool.utilcode.AppsUtils;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.sdcard.SdCardManager;
import com.zqhy.app.utils.sp.SPUtils;

import java.io.File;

/**
 * @author Administrator
 * @date 2018/11/28
 */

public class VersionDialogHelper {

    private String SP_VERSION = "SP_VERSION";
    private String TIME_VERSION = "TIME_VERSION";


    private Context mContext;
    private SPUtils spUtils;
    private long versionLimitTime = 24 * 60 * 60 * 1000;

    private OnVersionListener onVersionListener;

    public VersionDialogHelper(Context mContext) {
        this.mContext = mContext;
        spUtils = new SPUtils(mContext, SP_VERSION);
    }

    public VersionDialogHelper(Context mContext, OnVersionListener onVersionListener) {
        this.mContext = mContext;
        this.onVersionListener = onVersionListener;
        spUtils = new SPUtils(mContext, SP_VERSION);
    }

    public void showVersion(VersionVo.DataBean dataBean) {
        showVersion(false, dataBean);
    }

    public void showVersion(boolean isNeedUpdate, VersionVo.DataBean dataBean) {
        if (dataBean == null) {
            return;
        }
        int updateCode = dataBean.getVercode();
        int isForce = dataBean.getIsforce();
        if (updateCode > AppsUtils.getAppInfo(mContext).getVersionCode()) {
            try {
                if (isForce == 1) {
                    //强制更新
                    showVersionDialog(isNeedUpdate, dataBean);
                } else {
                    //非强制更新，有时间限制
                    long lastAppVersionTime = spUtils.getLong(TIME_VERSION);
                    if (isNeedUpdate || (System.currentTimeMillis() - lastAppVersionTime > versionLimitTime)) {
                        showVersionDialog(isNeedUpdate, dataBean);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (isNeedUpdate) {
                //ToastT.warning(mContext, "已是最新版本");
                Toaster.show("已是最新版本");
            }
        }
    }

    private boolean isCheck = false;
    private void showVersionDialog(boolean isNeedUpdate, VersionVo.DataBean dataBean) {
        CustomDialog versionDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_update_new, null),
                ScreenUtils.getScreenWidth(mContext),
                WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        /*TextView mTvUpdateMessage = versionDialog.findViewById(R.id.tv_update_message);
        TextView mBtnCancel = versionDialog.findViewById(R.id.btn_cancel);
        TextView mBtnUpdate = versionDialog.findViewById(R.id.btn_update);

        mBtnCancel.setOnClickListener(view -> {
            if (versionDialog != null && versionDialog.isShowing()) {
                versionDialog.dismiss();
            }
        });
        mBtnCancel.setVisibility(dataBean.getIsforce() == 1 ? View.GONE : View.VISIBLE);
        mBtnUpdate.setOnClickListener(view -> {
            showDownloadDialog(dataBean);
        });

        versionDialog.setCanceledOnTouchOutside(isNeedUpdate);
        versionDialog.setCancelable(isNeedUpdate);
        mTvUpdateMessage.setText(dataBean.getUpdateContent());*/

        TextView mTvVersion = versionDialog.findViewById(R.id.tv_version);
        TextView mTvSize = versionDialog.findViewById(R.id.tv_size);
        TextView mTvContent = versionDialog.findViewById(R.id.tv_content);
        TextView mTvUpdate = versionDialog.findViewById(R.id.tv_update);
        TextView mTvCancel = versionDialog.findViewById(R.id.tv_cancel);
        TextView mTvBottom = versionDialog.findViewById(R.id.tv_bottom);

        mTvVersion.setText("发现新版本" + dataBean.getVersion());
        mTvSize.setText("大小" + dataBean.getPackage_size());
        mTvContent.setText(dataBean.getUpdateContent());

        mTvUpdate.setOnClickListener(v -> {
            showDownloadDialog(dataBean);
        });

        mTvCancel.setVisibility(dataBean.getIsforce() == 1 ? View.INVISIBLE : View.VISIBLE);
        mTvCancel.setOnClickListener(v -> {
            if (mTvCancel.getVisibility() != View.INVISIBLE){
                if (versionDialog != null && versionDialog.isShowing()) {
                    versionDialog.dismiss();
                }
            }
        });

        isCheck = false;
        mTvBottom.setOnClickListener(v -> {
            if (isCheck){
                isCheck = false;
                mTvBottom.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_update_dialog_check), null, null, null);
            }else {
                isCheck = true;
                mTvBottom.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_update_dialog_checked), null, null, null);
            }
        });

        versionDialog.setCanceledOnTouchOutside(isNeedUpdate);
        versionDialog.setCancelable(isNeedUpdate);
        versionDialog.show();
        spUtils.putLong(TIME_VERSION, System.currentTimeMillis());
    }


    CustomDialog downloadDialog;

    /*private TextView mTvTitle;
    private TextView mCount;
    private TextView mTextSize;
    private TextView mTvNetSpeed;
    private NumberProgressBar mProgress;
    private Button mCancel;*/

    private TextView mTvVersion;
    private TextView mTvSize;
    private TextView mTvContent;
    private ProgressBar mProgressBar;
    private TextView mTvPlan;
    private TextView mTvSpeed;
    private TextView mTvCancel;
    private void showDownloadDialog(VersionVo.DataBean dataBean) {
        if (downloadDialog == null) {
            downloadDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_download_new, null),
                    ScreenUtils.getScreenWidth(mContext), WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            downloadDialog.setCanceledOnTouchOutside(false);
            if (dataBean.getIsforce() == 1) {
                downloadDialog.setCancelable(false);
            }
            /*mTvTitle = downloadDialog.findViewById(R.id.tv_title);
            mCount = downloadDialog.findViewById(R.id.count);
            mTextSize = downloadDialog.findViewById(R.id.text_size);
            mTvNetSpeed = downloadDialog.findViewById(R.id.tvNetSpeed);
            mProgress = downloadDialog.findViewById(R.id.progress);
            mCancel = downloadDialog.findViewById(R.id.cancel);


            mCancel.setOnClickListener(view -> {
                OkGo.delete(dataBean.getAppdir());
                downloadDialog.dismiss();
                if (onVersionListener != null) {
                    onVersionListener.onCancel();
                }
            });*/
            mTvVersion = downloadDialog.findViewById(R.id.tv_version);
            mTvSize = downloadDialog.findViewById(R.id.tv_size);
            mTvContent = downloadDialog.findViewById(R.id.tv_content);
            mProgressBar = downloadDialog.findViewById(R.id.download_progress);
            mTvPlan = downloadDialog.findViewById(R.id.tv_plan);
            mTvSpeed = downloadDialog.findViewById(R.id.tv_speed);
            mTvCancel = downloadDialog.findViewById(R.id.tv_cancel);

            mTvVersion.setText("发现新版本" + dataBean.getVersion());
            mTvSize.setText("大小" + dataBean.getPackage_size());
            mTvContent.setText(dataBean.getUpdateContent());

            mProgressBar.setMax(100);

            mTvCancel.setOnClickListener(view -> {
                OkGo.delete(dataBean.getAppdir());
                downloadDialog.dismiss();
                if (onVersionListener != null) {
                    onVersionListener.onCancel();
                }
            });
        }
        //mTextSize.setText("已完成：0.00M/0.00M");

        downloadDialog.show();
        downloadApk(dataBean);
    }

    private void downloadApk(VersionVo.DataBean versionBean) {
        String appName = mContext.getResources().getString(R.string.app_name);
        String fileName = appName + "_v" + versionBean.getVercode() + "_" + versionBean.getIsforce();

        OkGo.<File>get(versionBean.getAppdir())
                .tag(this)
                .execute(new FileCallback(SdCardManager.getInstance().getDownloadApkDir().getPath(), fileName) {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                        if (downloadDialog != null && downloadDialog.isShowing()) {
                            downloadDialog.dismiss();
                        }
                        File file = response.body();
                        if (file.exists()) {
                            AppUtil.install(mContext, file);
                        }
                    }

                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        super.onStart(request);
                        //mCount.setText("正在下载...");
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<File> response) {
                        super.onError(response);
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        String downloadLength = Formatter.formatFileSize(mContext, progress.currentSize);
                        String totalLength = Formatter.formatFileSize(mContext, progress.totalSize);
                        /*mTextSize.setText(downloadLength + "/" + totalLength);
                        String netSpeed = Formatter.formatFileSize(mContext, progress.speed);
                        mTvNetSpeed.setText(netSpeed + "/S");
                        mProgress.setMax(100);
                        mProgress.setProgress((int) (progress.fraction * 100));*/

                        mProgressBar.setProgress((int) (progress.fraction * 100));
                        mTvPlan.setText((int) (progress.fraction * 100) + "%");
                        mTvSpeed.setText(Formatter.formatFileSize(mContext, progress.speed) + "/s");
                    }
                });
    }
}
