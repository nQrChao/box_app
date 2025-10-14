package com.zqhy.app.core.data.model.community;

import com.zqhy.app.core.data.model.user.UserInfoVo;

/**
 * @author Administrator
 */
public class CommunityInfoVo {

    /**
     * user_nickname : 叼炸天
     * user_icon : http://p2user.jiuyao666.com/2019/03/04/5c7c9c007381b.png
     * act_num : 1
     * user_level : 1
     */

    private int    uid;
    private String user_nickname;
    private String user_icon;
    private String act_num;
    private int    user_level;
    /**
     * comment_count : 1
     * be_praised_count : 0
     */

    private int    comment_count;
    private int    be_praised_count;
    /**
     * question_verify_count : 1
     * answer_verify_count : 1
     */

    private int    question_verify_count;
    private int    answer_verify_count;

    private UserInfoVo.VipMemberVo vip_member;

    /**
     * 用户VIP等级
     */
    private int vip_level;

    public int getVip_level() {
        return vip_level;
    }

    public void setUser_id(int user_id) {
        this.uid = user_id;
    }

    public int getUser_id() {
        return uid;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_icon() {
        return user_icon;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }

    public String getAct_num() {
        return act_num;
    }

    public void setAct_num(String act_num) {
        this.act_num = act_num;
    }

    public int getUser_level() {
        return user_level;
    }

    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getBe_praised_count() {
        return be_praised_count;
    }

    public void setBe_praised_count(int be_praised_count) {
        this.be_praised_count = be_praised_count;
    }

    public int getQuestion_verify_count() {
        return question_verify_count;
    }

    public void setQuestion_verify_count(int question_verify_count) {
        this.question_verify_count = question_verify_count;
    }

    public int getAnswer_verify_count() {
        return answer_verify_count;
    }

    public void setAnswer_verify_count(int answer_verify_count) {
        this.answer_verify_count = answer_verify_count;
    }

    public UserInfoVo.VipMemberVo getVip_member() {
        return vip_member;
    }

    public void setVip_member(UserInfoVo.VipMemberVo vip_member) {
        this.vip_member = vip_member;
    }
}
