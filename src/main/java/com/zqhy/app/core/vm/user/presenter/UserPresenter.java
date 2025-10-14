package com.zqhy.app.core.vm.user.presenter;


import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.google.gson.Gson;
import com.mvvm.event.LiveBus;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.community.CommunityInfoVo;
import com.zqhy.app.core.data.model.community.qa.UserQaCanAnswerInfo;
import com.zqhy.app.core.data.model.message.InteractiveMessageListVo;
import com.zqhy.app.core.data.model.message.MessageInfoVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.login.LoginActivity;
import com.zqhy.app.core.view.login.ResetPasswordFragment;
import com.zqhy.app.db.table.message.MessageDbInstance;
import com.zqhy.app.db.table.message.MessageVo;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.request.UserRequest;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.sp.SPUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.Disposable;

import static com.zqhy.app.core.view.message.MessageMainFragment.SP_MESSAGE;
import static com.zqhy.app.core.view.message.MessageMainFragment.TAG_DYNAMIC_GAME_MESSAGE_LOG_TIME;

public class UserPresenter {

    public static final int MSG_KEFU   = 1;
    public static final int MSG_MY     = 2;
    public static final int MSG_SYSTEM = 3;
    public static final int MSG_GAME   = 4;
    public static final int MSG_QA     = 5;

    private UserRequest  request = new UserRequest();
    private Disposable   disposable;
    private int          count   = 0;
    private CustomDialog dialog;

    /**
     * 获取用户信息
     **/
    public void getUserInfo(OnUserInfoQueryCallBack callBack) {
        disposable = request.getUserInfo(callBack);
    }

    public void getUserInfoAudit(int uid, String token, OnUserInfoQueryCallBack callBack) {
        disposable = request.getUserInfoAduit(uid, token, callBack);
    }

    /**
     * 成功后存储本地数据
     **/
    public void setLocalUserData(UserInfoVo.DataBean data) {
        UserInfoModel.getInstance().login(data, false);
        LiveBus.getDefault().postEvent(Constants.EVENT_KEY_REFRESH_USERINFO_DATA,
                null, UserInfoModel.getInstance().getUserInfo());
    }

    /**
     * 是否有新消息
     **/
    public boolean isHasNewMessage() {
        return MessageDbInstance.getInstance().getUnReadMessageCount() > 0;
    }

    /**
     * 获取用户消息
     **/
    public List<MessageVo> getUserMessage() {
        List<MessageVo> list = new ArrayList<>();
        MessageVo kefuMsg = MessageDbInstance.getInstance().getMaxIdMessageVo(MSG_KEFU);
        MessageVo myMsg = MessageDbInstance.getInstance().getMaxIdMessageVo(MSG_MY);
        MessageVo sysMsg = MessageDbInstance.getInstance().getMaxIdMessageVo(MSG_SYSTEM);
        MessageVo gameMsg = MessageDbInstance.getInstance().getMaxIdMessageVo(MSG_GAME);
        MessageVo qaMsg = MessageDbInstance.getInstance().getMaxIdMessageVo(MSG_QA);
        Logs.e("sysMsg", "sysMsg" + sysMsg);
        if (sysMsg != null && sysMsg.message_is_read != -1) {
            list.add(sysMsg);
            Logs.e("sysMsg", "sysMsg" + new Gson().toJson(sysMsg));
        }
        if (qaMsg != null && qaMsg.message_is_read != -1) {
            list.add(qaMsg);
        }
        if (kefuMsg != null && kefuMsg.message_is_read != -1) {
            list.add(kefuMsg);
        }
        if (gameMsg != null && gameMsg.message_is_read != -1) {
            list.add(gameMsg);
        }
        if (myMsg != null && myMsg.message_is_read != -1) {
            list.add(myMsg);
        }

        Collections.sort(list, (o1, o2) -> {
            int i = o1.get_id() - o2.get_id();
            if (i == 0) {
                return o1.get_id() - o2.get_id();
            }
            return i;
        });
        return list;
    }

    /**
     * 获取用户消息
     **/
    public void getUserMessageBackEnd() {
        //2.获取客服消息
        MessageVo kefuMsg = MessageDbInstance.getInstance().getMaxIdMessageVo(MSG_KEFU);
        int maxId = 0;
        if (kefuMsg != null) {
            maxId = kefuMsg.getMessage_id();
        }
        request.getKefuMessage(maxId, data -> {
            if (data != null) {
                for (MessageInfoVo messageInfoVo : data) {
                    MessageVo messageVo = messageInfoVo.transformIntoMessageVo();
                    messageVo.message_type = MSG_KEFU;
                    MessageVo msg = MessageDbInstance.getInstance().getMaxIdMessageVo(MSG_KEFU);
                    if (msg == null || msg.message_id != messageVo.message_id) {
                        MessageDbInstance.getInstance().saveMessageVo(messageVo);
                    }
                }
            }
        });
        //3.获取游戏动态
        SPUtils spUtils = new SPUtils(SP_MESSAGE);
        long logTime = spUtils.getLong(TAG_DYNAMIC_GAME_MESSAGE_LOG_TIME, 0);
        request.getGameMessage(logTime, data -> {
            if (data != null) {
                for (MessageInfoVo messageInfoVo : data) {
                    MessageVo messageVo = messageInfoVo.transformIntoMessageVo();
                    messageVo.message_type = MSG_GAME;
                    MessageVo msg = MessageDbInstance.getInstance().getMaxIdMessageVo(MSG_GAME);
                    if (msg == null || msg.message_id != messageVo.message_id) {
                        MessageDbInstance.getInstance().saveMessageVo(messageVo);
                    }
                }
            }
            SPUtils spUtils1 = new SPUtils(SP_MESSAGE);
            spUtils1.putLong(TAG_DYNAMIC_GAME_MESSAGE_LOG_TIME, System.currentTimeMillis() / 1000);
        });
        //4.获取我的互动
        request.getMyMessage(data -> {
            if (data != null && data.size() > 0) {
                List<MessageVo> messageList = getMessageVos(data);
                Logs.e("dada", messageList.size() + "");
                for (MessageVo messageInfoVo : messageList) {
                    if (UserInfoModel.getInstance().getUserInfo() != null) {
                        messageInfoVo.message_type = MSG_MY;
                        messageInfoVo.uid = UserInfoModel.getInstance().getUserInfo().getUid();
                        MessageVo msg = MessageDbInstance.getInstance().getMaxIdMessageVo(MSG_MY);
                        if (msg == null || msg.message_id != messageInfoVo.message_id) {
                            MessageDbInstance.getInstance().saveMessageVo(messageInfoVo);
                            Logs.e("dadasdasdadad", messageList.size() + "");
                        }
                    }
                }
            }
        });
        //5.获取回答邀请
        request.getMyQaMessage(data -> {
            if (data != null && data.getAnswer_invite_list() != null && data.getAnswer_invite_list().size() != 0) {
                UserQaCanAnswerInfo.AnswerInviteInfoVo item = data.getAnswer_invite_list().get(0);
                MessageVo qaMsg = MessageDbInstance.getInstance().getMaxIdMessageVo(MSG_QA);
                if (qaMsg == null || qaMsg.message_id != item.getQid()) {
                    if (UserInfoModel.getInstance().getUserInfo() != null) {
                        MessageVo messageVo = new MessageVo();
                        messageVo.message_id = item.getQid();
                        messageVo.uid = UserInfoModel.getInstance().getUserInfo().getUid();
                        messageVo.message_is_read = 0;
                        messageVo.message_title = "邀你回答，赚100积分/条";
                        messageVo.message_type = MSG_QA;
                        messageVo.message_content = "【" + item.getGamename() + "】" + item.getContent();
                        MessageDbInstance.getInstance().saveMessageVo(messageVo);
                    }
                }
            }
        });
    }

    private List<MessageVo> getMessageVos(List<InteractiveMessageListVo.DataBean> dataBeanList) {
        List<MessageVo> messageList = new ArrayList<>();
        for (InteractiveMessageListVo.DataBean dataBean : dataBeanList) {
            MessageVo messageBean = new MessageVo();
            int commentId = dataBean.getCid();
            messageBean.message_id = dataBean.getId();
            messageBean.setComment_id(commentId);
            messageBean.setQuestion_id(dataBean.getQid());
            messageBean.setAction_type(dataBean.getType());
            messageBean.setIs_go_the_original(1);
            CommunityInfoVo communityInfoVo = dataBean.getCommunity_info();
            String messageTitle = "";
            if (communityInfoVo != null) {
                if (dataBean.getType() == 1) {
                    //点评赞
                    messageTitle = communityInfoVo.getUser_nickname() + " 赞了我的点评";
                } else if (dataBean.getType() == 2) {
                    //回复
                    messageTitle = communityInfoVo.getUser_nickname() + " 回复了我";
                } else if (dataBean.getType() == 3) {
                    //点评赞
                    messageTitle = communityInfoVo.getUser_nickname() + " 赞了我的回复";
                } else if (dataBean.getType() == 4) {
                    //回答赞
                    messageTitle = communityInfoVo.getUser_nickname() + " 赞了我的回答";
                }
            }
            messageBean.setMessage_title(messageTitle);
            messageBean.setMessage_content(dataBean.getContent());
            messageBean.setMessage_time(dataBean.getAdd_time());
            messageBean.setMessage_type(MSG_MY);
            messageBean.setMessage_is_read(0);
            UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
            try {
                if (userInfo != null) {
                    messageBean.setUid(userInfo.getUid());
                } else {
                    messageBean.setUid(-1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            messageList.add(messageBean);
        }
        return messageList;
    }

    public boolean showErrorDialog(String error, BaseFragment fragment) {
        LoginActivity activity = (LoginActivity) fragment.getActivity();
        LoginErrorData item = LoginErrorData.getMap(error);
        if (item != null && activity != null) {
            if (error.equals("pass_err") && count <= 3) {
                count++;
                return false;
            } else {
                dialog = new CustomDialog(activity,
                        LayoutInflater.from(activity).inflate(R.layout.dialog_warn_login, null),
                        WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER, com.zqhy.app.core.R.style.sheetdialog);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                TextView content = dialog.findViewById(R.id.content);
                TextView add = dialog.findViewById(R.id.add);
                Button doAction = dialog.findViewById(R.id.btn_confirm);
                TextView skip = dialog.findViewById(R.id.btn_cancel);
                add.setText(item.errorMsg2);
                content.setText(item.errorMsg);
                doAction.setText(item.btnText);
                skip.setOnClickListener(v -> dialog.dismiss());
                doAction.setOnClickListener(v -> {
                    if (error.equals("pass_err")) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        fragment.start(new ResetPasswordFragment());
                    } else {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        //                        activity.jump();
                    }
                });
                if (dialog != null && !dialog.isShowing()) {
                    dialog.show();
                }
                return true;
            }

        } else {
            return false;
        }
    }

    public void close() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public interface OnUserInfoQueryCallBack {
        void onStart();

        void onSuccess(UserInfoVo.DataBean data);

        void onError(String error);
    }

    public interface OnMessageQueryCallBack {
        void onSuccess(List<MessageInfoVo> data);
    }

    public interface OnMessageMyQyeryCallBack {
        void onSuccess(List<InteractiveMessageListVo.DataBean> data);
    }

    public interface OnQaMessageQueryCallBack {
        void onSuccess(UserQaCanAnswerInfo data);
    }
}
