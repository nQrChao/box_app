package com.zqhy.app.core.data.model.message;

import com.google.gson.Gson;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;
import com.zqhy.app.db.table.message.MessageVo;

/**
 * Created by Administrator on 2018/11/13.
 */

public class MessageInfoVo {
    /**
     * id : 66
     * gameid : 7211
     * logtime : 1540798157
     * gamename : 大话仙游BT版
     * systemmsgtitle : 【收藏游戏-单独客户端更新】
     * systemmsg : Hi，您的游戏《大话仙游BT版》有新的（客户端版本）更新动态哦！快去瞧瞧吧！
     */

    private int id;
    private int gameid;
    private long logtime;
    private String gamename;
    private String msgtitle;
    private String msgcontent;

    private AppJumpInfoBean jump;
    private String jump_title;

    /***1,可以复制；0，不能复制****/
    private int cancopy;

    private int uid;
    private int message_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public long getLogtime() {
        return logtime;
    }

    public void setLogtime(long logtime) {
        this.logtime = logtime;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getMsgtitle() {
        return msgtitle;
    }

    public void setMsgtitle(String msgtitle) {
        this.msgtitle = msgtitle;
    }

    public String getMsg() {
        return msgcontent;
    }

    public void setMsg(String msg) {
        this.msgcontent = msg;
    }

    public AppJumpInfoBean getJump() {
        return jump;
    }

    public void setJump(AppJumpInfoBean jump) {
        this.jump = jump;
    }

    public String getJump_title() {
        return jump_title;
    }

    public void setJump_title(String jump_title) {
        this.jump_title = jump_title;
    }

    public int getCancopy() {
        return cancopy;
    }

    public void setCancopy(int cancopy) {
        this.cancopy = cancopy;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getMessage_type() {
        return message_type;
    }

    public void setMessage_type(int message_type) {
        this.message_type = message_type;
    }

    public MessageVo transformIntoMessageVo() {
        MessageVo messageInfoBean = new MessageVo();
        messageInfoBean.setMessage_id(id);
        messageInfoBean.setPage_type(13);
        messageInfoBean.setUid(uid);
        messageInfoBean.setMessage_type(message_type);
        messageInfoBean.setMessage_time(logtime);
        messageInfoBean.setMessage_is_read(0);
        messageInfoBean.setMessage_title(msgtitle);
        messageInfoBean.setMessage_content(msgcontent);
        messageInfoBean.setMessage_content_action_text(jump_title);
        messageInfoBean.setMessage_content_action(new Gson().toJson(jump));
        messageInfoBean.setIs_copy_message_content(cancopy);
        messageInfoBean.setGameid(gameid);

        return messageInfoBean;
    }
}
