package com.zqhy.app.notify;

import android.app.Notification;
import android.os.Build;
import android.text.format.Formatter;

import com.lzy.okgo.model.Progress;
import com.zqhy.app.App;
import com.zqhy.app.core.data.model.game.GameExtraVo;

import java.text.DecimalFormat;

/**
 * @author Administrator
 * @date 2017/8/30
 */

public class DownloadNotifyManager {

    private static volatile DownloadNotifyManager instance;

    private DownloadNotifyManager() {
    }

    public static DownloadNotifyManager getInstance() {
        if (instance == null) {
            synchronized (DownloadNotifyManager.class) {
                if (instance == null) {
                    instance = new DownloadNotifyManager();
                }
            }
        }
        return instance;
    }

    private static final int               NOTIFY_ID = 100000;
    private              Notification      mNotification;
    private              NotificationUtils mNotificationManager;

    /**
     * @param progress
     */
    public void doNotify(Progress progress) {
        if (progress == null) {
            return;
        }

        GameExtraVo gameInfoVo = (GameExtraVo) progress.extra1;

        if (gameInfoVo == null) {
            return;
        }

        int notify_id = 0;

        try {
            notify_id = gameInfoVo.getGameid();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String notifyStatus = "";
        if (progress.status == Progress.NONE) {
            notifyStatus = "继续";
        } else if (progress.status == Progress.PAUSE) {
            notifyStatus = "暂停下载";
        } else if (progress.status == Progress.ERROR) {
            notifyStatus = "下载中断";
        } else if (progress.status == Progress.WAITING) {
            notifyStatus = "正在下载中...";
        } else if (progress.status == Progress.FINISH) {
            notifyStatus = "下载完成";
        } else if (progress.status == Progress.LOADING) {
            notifyStatus = "正在下载中...";
        }

        float fProgress = progress.fraction;
        DecimalFormat df = new DecimalFormat("#0.00");

        String contentTitle = "<" + gameInfoVo.getGamename() + ">" + notifyStatus;

        String downloadLength = Formatter.formatFileSize(App.instance(), progress.currentSize);
        String totalLength = Formatter.formatFileSize(App.instance(), progress.totalSize);
        String contentText = downloadLength + "/" + totalLength + "，进度：" + df.format(fProgress * 100) + "%";

        setUpNotification(notify_id, contentTitle, contentText, 100, (int) (fProgress * 100));
    }


    /**
     * @param contentTitle
     * @param contentText
     * @param maxProgress
     * @param progress
     */
    private void setUpNotification(int notify_id, String contentTitle, String contentText, int maxProgress, int progress) {
        if (mNotificationManager == null) {
            mNotificationManager = new NotificationUtils(App.instance());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotification = mNotificationManager.createDownloadNotification(contentTitle, contentText, maxProgress, progress);

        } else {
            mNotification = mNotificationManager.createDownloadNotification_25(contentTitle, contentText, maxProgress, progress);
        }
        mNotificationManager.sendNotification(notify_id + NOTIFY_ID, mNotification);
        if (progress == maxProgress) {
            mNotificationManager.cancelNotification(notify_id + NOTIFY_ID);
        }
    }

    /**
     * 取消
     *
     * @param gameid
     */
    public void cancelNotify(int gameid) {
        int notify_id = gameid;
        if (mNotificationManager != null) {
            mNotificationManager.cancelNotification(NOTIFY_ID + notify_id);
        }
    }


}
