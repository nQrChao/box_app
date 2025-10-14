package com.zqhy.app.core.data.model.community.qa;

import com.zqhy.app.core.data.model.community.CommunityInfoVo;

/**
 * @author Administrator
 */
public class AnswerInfoVo {

    private String content;
    /**
     * aid : 1
     * uid : 28300
     * like_count : 0
     * add_time : 1551862019
     * add_intergral_amount : 0
     * community_info : {"user_nickname":"手机用户1713","user_icon":"","act_num":"0","user_level":1}
     */

    private int aid;
    private int uid;
    private int like_count;
    private long add_time;
    private int reward_integral;
    private CommunityInfoVo community_info;
    private int me_like;


    public String getContent() {
        return content;
    }

    public int getAid() {
        return aid;
    }

    public int getUid() {
        return uid;
    }

    public int getLike_count() {
        return like_count;
    }

    public long getAdd_time() {
        return add_time;
    }

    public int getReward_integral() {
        return reward_integral;
    }

    public CommunityInfoVo getCommunity_info() {
        return community_info;
    }

    public int getMe_like() {
        return me_like;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public void setReward_integral(int reward_integral) {
        this.reward_integral = reward_integral;
    }

    public void setCommunity_info(CommunityInfoVo community_info) {
        this.community_info = community_info;
    }

    public void setMe_like(int me_like) {
        this.me_like = me_like;
    }
}
