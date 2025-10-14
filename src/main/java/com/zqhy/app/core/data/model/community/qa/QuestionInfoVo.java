package com.zqhy.app.core.data.model.community.qa;

import com.zqhy.app.core.data.model.community.CommunityInfoVo;

import java.util.List;

/**
 * @author Administrator
 */
public class QuestionInfoVo {


    /**
     * qid : 3
     * add_time : 1551861685
     * a_count : 0
     * content : 5ri45oiP5LiN6ZSZ77yM5qU6L6D6ICQ546p77yM6aKY5p2Q5Yv5Lul5LiN5a655piT5Y6M54Om
     * answerlist : [{"content":"5ri45oiP5LiN6ZSZ77yM5qU6L6D6ICQ546p77yM6aKY5p2Q5Yv5Lul5LiN5a655piT5Y6M54Om"}]
     */

    private int qid;
    private long add_time;
    private int a_count;
    private String content;
    private List<AnswerInfoVo> answerlist;

    private CommunityInfoVo community_info;

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public int getA_count() {
        return a_count;
    }

    public void setA_count(int a_count) {
        this.a_count = a_count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<AnswerInfoVo> getAnswerlist() {
        return answerlist;
    }

    public void setAnswerlist(List<AnswerInfoVo> answerlist) {
        this.answerlist = answerlist;
    }

    public CommunityInfoVo getCommunity_info() {
        return community_info;
    }

    public void setCommunity_info(CommunityInfoVo community_info) {
        this.community_info = community_info;
    }
}
