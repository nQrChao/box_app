package com.zqhy.app.push;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.box.other.blankj.utilcode.util.Logs;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.zqhy.app.core.view.message.MessageMainFragment;
import com.zqhy.app.db.table.message.MessageDbInstance;
import com.zqhy.app.db.table.message.MessageVo;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.notify.NotificationUtils;
import com.zqhy.app.utils.sp.SPUtils;

/**
 * @author Administrator
 * @date 2017/1/12
 */

public class PushIntentService extends GTIntentService {

    public static String SP_PUSH_SERVICE = "SP_PUSH_SERVICE";
    public static String PUSH_CLIENT_ID = "PUSH_CLIENT_ID";


    @Override
    public void onReceiveServicePid(Context context, int pid) {
        Logs.e("onReceiveServicePid -> " + "pid = " + pid);
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        byte[] payload = msg.getPayload();
        if (payload != null) {
            String data = new String(payload);
            Logs.e("PushIntentService : 透传消息payload：data=" + data);
            try {
                MessageVo.OldMessageVo message = MessageVo.parseOldMessage(data);

                if (isNeedNotice(message)) {
                    //通知栏
                    pushNotification(context, message);
                }
                if (message.getMessage_type() == 3) {
                    //系统消息保存到DB
                    MessageVo messageVo = message.getMessageVo();
                    MessageDbInstance.getInstance().saveMessageVo(messageVo);
                } else if (message.getMessage_type() == 2) {
                    int targetUid = message.getUid();
                    if (UserInfoModel.getInstance().isLogined()) {
                        int uid = UserInfoModel.getInstance().getUserInfo().getUid();
                        if (uid == targetUid) {
                            SPUtils spUtils = new SPUtils(context, MessageMainFragment.SP_MESSAGE);
                            spUtils.putBoolean(MessageMainFragment.KEY_HAS_NEW_COMMENT_MESSAGE, true);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Logs.e("Exception:" + e.toString());
            }
        }
    }

    @Override
    public void onReceiveClientId(Context context, String client_id) {
        Logs.e("onReceiveClientId -> " + "client_id = " + client_id);
        SPUtils spUtils = new SPUtils(context, SP_PUSH_SERVICE);
        spUtils.putString(PUSH_CLIENT_ID, client_id);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {

    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {

    }


    private static int count = 1;

    private void pushNotification(Context context, MessageVo.OldMessageVo messageVo) {
        if (messageVo == null) {
            return;
        }
        NotificationUtils notificationUtils = new NotificationUtils(context);

        Intent intent = new Intent();
        intent.setClassName(getPackageName(), "com.zqhy.app.core.view.message.MessageActivity");
        intent.putExtra("page_type", messageVo.getPage_type());

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = notificationUtils.createPushNotification(messageVo.getTitle(), messageVo.getMsg(), pendingIntent);

        } else {
            notification = notificationUtils.createPushNotification_25(messageVo.getTitle(), messageVo.getMsg(), pendingIntent);
        }
        notificationUtils.sendNotification(count, notification);
        count++;
    }

    /****2018.01.20 未登录情况下 只有page_type=1或2的时候才收到消息*********************************/
    public boolean isNeedNotice(MessageVo.OldMessageVo messageVo) {
        if (messageVo == null) {
            return false;
        }
        if (UserInfoModel.getInstance().isLogined()) {
            return true;
        }
        return false;
    }

}
