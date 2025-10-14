package com.zqhy.app.core.download;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.box.other.hjq.toast.Toaster;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.lzy.okserver.download.DownloadTask;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.core.data.model.game.GameExtraVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.inner.WifiDownloadActionListener;
import com.zqhy.app.core.tool.AppUtil;
import com.zqhy.app.core.tool.utilcode.NetWorkUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.notify.DownloadNotifyManager;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.utils.sdcard.SdCardManager;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.DecimalFormat;

/**
 * @author Administrator
 */
public class DownloaderHelper {

    private Context mContext;

    private ProgressBar mDownloadProgress;
    private TextView mTvDownload;


    private GameInfoVo gameInfoVo;

    public DownloaderHelper(Context mContext, ProgressBar mDownloadProgress, TextView mTvDownload, GameInfoVo gameInfoVo) {
        this.mContext = mContext;
        this.mDownloadProgress = mDownloadProgress;
        this.mTvDownload = mTvDownload;
        this.gameInfoVo = gameInfoVo;
    }

    public void refreshGameInfoVo(GameInfoVo gameInfoVo) {
        this.gameInfoVo = gameInfoVo;
    }

    /**
     * 下载监听
     */
    DownloadListener downloadListener = new DownloadListener("download") {
        @Override
        public void onStart(Progress progress) {

        }

        @Override
        public void onProgress(Progress progress) {
            refresh(progress);
            DownloadNotifyManager.getInstance().doNotify(progress);
        }

        @Override
        public void onError(Progress progress) {
            progress.exception.printStackTrace();
            GameExtraVo gameInfoVo = (GameExtraVo) progress.extra1;
            DownloadNotifyManager.getInstance().cancelNotify(gameInfoVo.getGameid());
            Toast.makeText(mContext, R.string.string_download_game_fail, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish(File file, Progress progress) {
            File targetFile = new File(progress.filePath);
            if (targetFile.exists()) {
                AppUtil.install(mContext, targetFile);
            }
        }

        @Override
        public void onRemove(Progress progress) {
            GameExtraVo gameInfoVo = (GameExtraVo) progress.extra1;
            DownloadNotifyManager.getInstance().cancelNotify(gameInfoVo.getGameid());
        }
    };

    public void setGameDownloadStatus() {
        if (gameInfoVo == null) {
            return;
        }
        if (!UserInfoModel.getInstance().isLogined()) {
            return;
        }
        Progress progress = DownloadManager.getInstance().get(gameInfoVo.getGameDownloadTag());
        if (progress == null) {
            mDownloadProgress.setVisibility(View.GONE);
            if (gameInfoVo.getGame_type() == 3) {
                mTvDownload.setText("开始玩");
            } else {
                mTvDownload.setText("立即下载");
            }
        } else {
            refresh(progress);
            DownloadTask task = OkDownload.restore(progress);
            if (task != null) {
                task.register(downloadListener);
            }
        }
    }

    /**
     * 点击下载游戏
     */
    public void download() {
        if (gameInfoVo.getIs_deny() == 1) {
            //ToastT.warning(mContext, "(T ^ T) 亲亲，此游戏暂不提供下载服务呢！");
            Toaster.show("此游戏暂不提供下载服务");
            return;
        }

        if (gameInfoVo.isIOSGameOnly()) {
            //ToastT.warning(mContext, "此为苹果游戏，请使用苹果手机下载哦！");
            Toaster.show("此为苹果游戏，请使用苹果手机下载");
            return;
        }

        String downloadErrorInfo = gameInfoVo.getGame_download_error();
        if (!TextUtils.isEmpty(downloadErrorInfo)) {
            //ToastT.warning(mContext, downloadErrorInfo);
            Toaster.show(downloadErrorInfo);
            return;
        }

        String downloadUrl = gameInfoVo.getGame_download_url();
        if (!CommonUtils.downloadUrlVerification(downloadUrl)) {
            //ToastT.warning(mContext, "_(:з」∠)_ 下载异常，请重登或联系客服看看哟~");
            Toaster.show("下载异常，请重登或联系客服");
            return;
        }

        Progress progress = DownloadManager.getInstance().get(gameInfoVo.getGameDownloadTag());
        if (progress == null) {
            checkWiFiType(() -> {
                //下载
                fileDownload(gameInfoVo);
            });
        } else {
            if (progress != null) {
                DownloadTask task = OkDownload.restore(progress);
                task.register(downloadListener);
                if (progress.status == Progress.NONE || progress.status == Progress.ERROR || progress.status == Progress.PAUSE || progress.status == Progress.WAITING) {
                    checkWiFiType(() -> {
                        if (task != null) {
                            task.start();
                        }
                    });
                } else if (progress.status == Progress.LOADING) {
                    //暂停
                    if (task != null) {
                        task.pause();
                    }
                } else if (progress.status == Progress.FINISH) {
                    //下载完成
                    GameExtraVo gameExtraInfoVo = (GameExtraVo) progress.extra1;
                    String packageName = gameExtraInfoVo == null ? "" : gameExtraInfoVo.getClient_package_name();
                    //打开
                    if (!TextUtils.isEmpty(packageName) && AppUtil.isAppAvailable(mContext, packageName)) {
                        AppUtil.open(mContext, packageName);
                    } else {
                        File targetFile = new File(progress.filePath);
                        //安装
                        if (targetFile.exists()) {
                            AppUtil.install(mContext, targetFile);
                        } else {
                            checkWiFiType(() -> {
                                //重新下载
                                checkWiFiType(() -> {
                                    if (task != null) {
                                        task.start();
                                    }
                                });
                            });
                        }
                    }
                }
            }
            refresh(progress);
        }
    }

    /**
     * 下载游戏
     *
     * @param gameInfoVo
     */
    private void fileDownload(GameInfoVo gameInfoVo) {
        GetRequest<File> request = OkGo.get(gameInfoVo.getGame_download_url());
        DownloadTask task = OkDownload.request(gameInfoVo.getGameDownloadTag(), request)
                .folder(SdCardManager.getInstance().getDownloadApkDir().getPath())
                .fileName(gameInfoVo.getGamename())
                .extra1(gameInfoVo.getGameExtraVo())
                .register(downloadListener)
                .save();
        task.start();
        EventBus.getDefault().post(new EventCenter(EventConfig.ACTION_ADD_DOWNLOAD_EVENT_CODE));
    }

    /**
     * 下载刷新
     *
     * @param progress
     */
    private void refresh(Progress progress) {
        if (progress == null) {
            return;
        }
        if (mDownloadProgress == null || mTvDownload == null) {
            return;
        }

        if (progress.status == Progress.LOADING || progress.status == Progress.WAITING) {
            float fProgress = progress.fraction;
            mDownloadProgress.setVisibility(View.VISIBLE);
            mDownloadProgress.setMax(100);
            mDownloadProgress.setProgress((int) (fProgress * 100));
            DecimalFormat df = new DecimalFormat("#0.00");
            mTvDownload.setText("已下载" + df.format(fProgress * 100) + "%");
        } else if (progress.status == Progress.NONE) {
            float fProgress = progress.fraction;
            mDownloadProgress.setVisibility(View.VISIBLE);
            mDownloadProgress.setMax(100);
            mDownloadProgress.setProgress((int) (fProgress * 100));
            mTvDownload.setText("继续下载");
        } else if (progress.status == Progress.PAUSE) {
            float fProgress = progress.fraction;
            mDownloadProgress.setVisibility(View.VISIBLE);
            mDownloadProgress.setMax(100);
            mDownloadProgress.setProgress((int) (fProgress * 100));
            mTvDownload.setText("暂停中...");
        } else if (progress.status == Progress.ERROR) {
            mDownloadProgress.setVisibility(View.VISIBLE);
            mTvDownload.setText("下载暂停，点击继续");
        } else if (progress.status == Progress.FINISH) {
            GameExtraVo gameInfoVo = (GameExtraVo) progress.extra1;
            String packageName = gameInfoVo == null ? "" : gameInfoVo.getClient_package_name();
            mDownloadProgress.setVisibility(View.GONE);
            //打开
            if (!TextUtils.isEmpty(packageName) && AppUtil.isAppAvailable(mContext, packageName)) {
                mTvDownload.setText("打开");
            } else {
                File targetFile = new File(progress.filePath);
                //安装
                if (targetFile.exists()) {
                    mTvDownload.setText("安装");
                } else {//下载
                    mTvDownload.setText("立即下载");
                }
            }
        }
    }

    /**
     * 检车网络状态
     *
     * @param wifiDownloadActionListener
     */
    public void checkWiFiType(WifiDownloadActionListener wifiDownloadActionListener) {
        int netWorkType = NetWorkUtils.getNetWorkType(mContext);
        switch (netWorkType) {
            case NetWorkUtils.NETWORK_WIFI:
                if (wifiDownloadActionListener != null) {
                    wifiDownloadActionListener.onDownload();
                }
                break;
            case NetWorkUtils.NETWORK_NO:
                //ToastT.warning(mContext, "当前无网络链接，请先链接网络");
                Toaster.show("当前无网络链接，请先链接网络");
                break;
            case NetWorkUtils.NETWORK_2G:
            case NetWorkUtils.NETWORK_3G:
            case NetWorkUtils.NETWORK_4G:
            case NetWorkUtils.NETWORK_UNKNOWN:
                showWifiDownloadTipsDialog(wifiDownloadActionListener);
                break;
            default:
                break;
        }
    }

    /**
     * 下载wifi/4G提示框
     *
     * @param wifiDownloadActionListener
     */
    protected void showWifiDownloadTipsDialog(WifiDownloadActionListener wifiDownloadActionListener) {
        CustomDialog wifiDownloadTipsDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_download_wifi_tips, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        TextView mTvCancel = (TextView) wifiDownloadTipsDialog.findViewById(R.id.tv_cancel);
        TextView mTvContinue = (TextView) wifiDownloadTipsDialog.findViewById(R.id.tv_continue);
        mTvCancel.setOnClickListener(v -> {
            if (wifiDownloadTipsDialog != null && wifiDownloadTipsDialog.isShowing()) {
                wifiDownloadTipsDialog.dismiss();
            }
        });
        mTvContinue.setOnClickListener(v -> {
            if (wifiDownloadTipsDialog != null && wifiDownloadTipsDialog.isShowing()) {
                wifiDownloadTipsDialog.dismiss();
            }
            if (wifiDownloadActionListener != null) {
                wifiDownloadActionListener.onDownload();
            }
        });

        wifiDownloadTipsDialog.show();
    }
}
