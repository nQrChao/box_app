package com.zqhy.app.db.table.message;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.zqhy.app.db.AppDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author Administrator
 * @date 2018/11/11
 */
@Table(database = AppDatabase.class)
public class MessageVo extends BaseModel implements Serializable {

    /**
     * ID自增
     */
    @PrimaryKey(autoincrement = true)
    @Column
    public int _id;

    @Column
    public int page_type;
    @Column
    public int message_id;
    @Column
    public int uid;

    @Column
    public int is_copy_message_content;

    @Column
    public String message_title;
    @Column
    public String message_content;
    @Column
    public String message_content_action_text;
    @Column
    public String message_content_action;
    @Column
    public long message_time;

    /**
     * 0 未读
     * 1 已读
     */
    @Column
    public int message_is_read;
    @Column
    public int message_type;
    @Column
    public int gameid;


    @Override
    public boolean save(DatabaseWrapper wrapper) {
        return super.save(wrapper);
    }

    private int is_go_the_original;
    private int comment_id;
    private String message_tips;
    private int question_id;
    /**
     * 1 2 3、跳转评论；4，跳转问答
     */
    private int action_type;


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getPage_type() {
        return page_type;
    }

    public void setPage_type(int page_type) {
        this.page_type = page_type;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getIs_copy_message_content() {
        return is_copy_message_content;
    }

    public void setIs_copy_message_content(int is_copy_message_content) {
        this.is_copy_message_content = is_copy_message_content;
    }

    public String getMessage_title() {
        return message_title;
    }

    public void setMessage_title(String message_title) {
        this.message_title = message_title;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    public String getMessage_content_action_text() {
        return message_content_action_text;
    }

    public void setMessage_content_action_text(String message_content_action_text) {
        this.message_content_action_text = message_content_action_text;
    }

    public String getMessage_content_action() {
        return message_content_action;
    }

    public void setMessage_content_action(String message_content_action) {
        this.message_content_action = message_content_action;
    }

    public long getMessage_time() {
        return message_time;
    }

    public void setMessage_time(long message_time) {
        this.message_time = message_time;
    }

    public int getMessage_is_read() {
        return message_is_read;
    }

    public void setMessage_is_read(int message_is_read) {
        this.message_is_read = message_is_read;
    }

    public int getMessage_type() {
        return message_type;
    }

    public void setMessage_type(int message_type) {
        this.message_type = message_type;
    }

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public int getIs_go_the_original() {
        return is_go_the_original;
    }

    public void setIs_go_the_original(int is_go_the_original) {
        this.is_go_the_original = is_go_the_original;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getAction_type() {
        return action_type;
    }

    public void setAction_type(int action_type) {
        this.action_type = action_type;
    }

    public String getMessage_tips() {
        if (getGameid() > 0) {
            message_tips = "※如需取消此游戏动态通知，取消收藏该游戏即可";
        } else {
            message_tips = "";
        }
        return message_tips;
    }

    public void setMessage_tips(String message_tips) {
        this.message_tips = message_tips;
    }


    public static class OldMessageVo {

        /**
         * mid : 10579
         * title : sdsad大萨达都是
         * msg : sdsad大萨达都是sdsad大萨达都是
         * add_time : 1543548739
         * page_type : 1
         * gameid : 0
         * newsid : 0
         * message_type : 3
         * uid : 9
         */

        private int mid;
        private String title;
        private String msg;
        private long add_time;
        private int page_type;
        private int gameid;
        private int newsid;
        private int message_type;
        private int uid;

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public int getPage_type() {
            return page_type;
        }

        public void setPage_type(int page_type) {
            this.page_type = page_type;
        }

        public int getGameid() {
            return gameid;
        }

        public void setGameid(int gameid) {
            this.gameid = gameid;
        }

        public int getNewsid() {
            return newsid;
        }

        public void setNewsid(int newsid) {
            this.newsid = newsid;
        }

        public int getMessage_type() {
            return message_type;
        }

        public void setMessage_type(int message_type) {
            this.message_type = message_type;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        private int comment_id;
        private int verify_status;

        private String extendsX;

        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
        }

        public int getVerify_status() {
            return verify_status;
        }

        public void setVerify_status(int verify_status) {
            this.verify_status = verify_status;
        }

        public String getExtendsX() {
            return extendsX;
        }

        public void setExtendsX(String extendsX) {
            this.extendsX = extendsX;
        }

        private int read;

        public int getRead() {
            return read;
        }

        public void setRead(int read) {
            this.read = read;
        }


        private String jump;
        private String jump_title;

        public String getJump() {
            return jump;
        }

        public void setJump(String jump) {
            this.jump = jump;
        }

        public String getJump_title() {
            return jump_title;
        }

        public void setJump_title(String jump_title) {
            this.jump_title = jump_title;
        }

        public MessageVo getMessageVo(){
            MessageVo messageVo = new MessageVo();
            messageVo.setUid(uid);
            messageVo.setMessage_is_read(read);
            messageVo.setComment_id(comment_id);
            messageVo.setGameid(gameid);
            messageVo.setMessage_id(mid);
            messageVo.setMessage_time(add_time);
            messageVo.setMessage_title(title);
            messageVo.setMessage_content(msg);
            messageVo.setMessage_type(message_type);

            messageVo.setMessage_content_action(jump);
            messageVo.setMessage_content_action_text(jump_title);

            return messageVo;
        }
    }

    public static OldMessageVo parseOldMessage(String json) {
        OldMessageVo message = new OldMessageVo();
        try {
            JSONObject resultObj = new JSONObject(json);

            message.setMid(resultObj.optInt("mid"));
            message.setGameid(resultObj.optInt("gameid"));
            message.setNewsid(resultObj.optInt("newsid"));
            message.setAdd_time(resultObj.optLong("add_time"));
            message.setMsg(resultObj.optString("msg"));
            message.setTitle(resultObj.optString("title"));
            message.setPage_type(resultObj.optInt("page_type"));

            message.setMessage_type(resultObj.optInt("message_type"));
            message.setComment_id(resultObj.optInt("comment_id"));
            message.setVerify_status(resultObj.optInt("verify_status"));
            message.setExtendsX(resultObj.optString("extends"));
            message.setJump(resultObj.optString("jump"));
            message.setJump_title(resultObj.optString("jump_title"));

            message.setUid(resultObj.optInt("uid"));
            message.setRead(0);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }
}
