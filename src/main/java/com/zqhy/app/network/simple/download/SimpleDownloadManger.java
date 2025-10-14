package com.zqhy.app.network.simple.download;

import android.content.Context;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.base.Request;
import com.zqhy.app.core.tool.AppUtil;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.core.tool.utilcode.SizeUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.numberprogressbar.NumberProgressBar;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author leeham2734
 * @date 2021/4/15-15:42
 * @description
 */
public class SimpleDownloadManger {

    private static final String               TAG = "SimpleDownloadManger";
    private static       SimpleDownloadManger newInstance;

    private SimpleDownloadManger() {
    }

    public static SimpleDownloadManger getInstance() {
        if (newInstance == null) {
            newInstance = new SimpleDownloadManger();
        }
        return newInstance;
    }

    private Map<Long, File> map    = new HashMap<>();
    private Stack<Long>     mStack = new Stack<>();

    public void showAndDownloadApk(Context mContext, String url, String title, String describeStr, File saveFile) {
        //        if (saveFile.exists()) {
        //            AppUtil.install(mContext, saveFile);
        //            return;
        //        }
        CustomDialog downloadDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_download, null),
                ScreenUtils.getScreenWidth(mContext) - SizeUtils.dp2px(mContext, 40),
                WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        TextView mTvTitle = downloadDialog.findViewById(R.id.tv_title);
        TextView mCount = downloadDialog.findViewById(R.id.count);
        TextView mTextSize = downloadDialog.findViewById(R.id.text_size);
        TextView mTvNetSpeed = downloadDialog.findViewById(R.id.tvNetSpeed);
        NumberProgressBar mProgress = downloadDialog.findViewById(R.id.progress);
        Button mCancel = downloadDialog.findViewById(R.id.cancel);

        mTvTitle.setText(title);

        mCancel.setOnClickListener(view -> {
            downloadDialog.dismiss();
        });
        downloadDialog.setCanceledOnTouchOutside(false);
        downloadDialog.setCancelable(false);
        mTextSize.setText("已完成：0.00M/0.00M");
        mCount.setVisibility(View.GONE);
        downloadDialog.show();
        try {
            OkGo.<File>get(url)
                    .tag(this)
                    .execute(new FileCallback(saveFile.getParentFile().getAbsolutePath(), saveFile.getName()) {
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
                            mTextSize.setText(downloadLength + "/" + totalLength);
                            String netSpeed = Formatter.formatFileSize(mContext, progress.speed);
                            mTvNetSpeed.setText(netSpeed + "/S");
                            mProgress.setMax(100);
                            mProgress.setProgress((int) (progress.fraction * 100));
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void installApk(Context context) {
        Log.i(TAG, "==============start installApk======================");
        if (mStack.isEmpty()) {
            return;
        }
        long downLoadId = mStack.pop();
        File saveFile = map.get(downLoadId);
        AppUtil.install(context, saveFile);
    }

}
