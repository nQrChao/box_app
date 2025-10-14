package com.zqhy.app.core.data.model.community.qa;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.community.CommunityInfoVo;

import java.util.List;

/**
 * @author Administrator
 */
public class QaDetailInfoVo extends BaseVo {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {

        /**
         * qid : 3
         * gameid : 1
         * add_time : 1551861685
         * a_count : 0
         * content : 5ri45oiP5LiN6ZSZ77yM5qU6L6D6ICQ546p77yM6aKY5p2Q5Yv5Lul5LiN5a655piT5Y6M54Om
         * community_info : {"user_nickname":"玩家","user_icon":"","act_num":null,"user_level":1}
         * gamename : 贪玩硬汉豪华2版--侵权不上线
         * gameicon : http://p1.btgame01.com/2018/11/01/5bda726c030d2.png
         * genre_str : 角色·休闲
         * count : 1
         * answerlist : [{"aid":"1","uid":"28300","like_count":"0","add_time":"1551862019","add_intergral_amount":"0","content":"5ri45oiP5LiN6ZSZ77yM5qU6L6D6ICQ546p77yM6aKY5p2Q5Yv5Lul5LiN5a655piT5Y6M54Om","community_info":{"user_nickname":"手机用户1713","user_icon":"","act_num":"0","user_level":1}}]
         */

        private int qid;
        private int gameid;
        private int game_type;
        private long add_time;
        private int a_count;
        private String content;
        private CommunityInfoVo community_info;
        private String gamename;
        private String gameicon;
        private String genre_str;
        private int count;
        /**
         * can_answer = -4 :　用户当天回答达到１０次, 已经顶天了
         * can_answer = -5 :  该问题已经有10个审核通过的回答了,无需再回答
         */
        private int can_answer;
        /**
         * 0，不能点已解决；1，可以点解决
         */
        private int can_solve;

        private List<AnswerInfoVo> answerlist;

        /**
         * 0，未解决，1，已解决
         */
        private int status;

        private int uid;

        private int play_count;

        private int can_question;

        public int getQid() {
            return qid;
        }

        public void setQid(int qid) {
            this.qid = qid;
        }

        public int getGameid() {
            return gameid;
        }

        public int getGame_type() {
            return game_type;
        }

        public void setGameid(int gameid) {
            this.gameid = gameid;
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

        public CommunityInfoVo getCommunity_info() {
            return community_info;
        }

        public void setCommunity_info(CommunityInfoVo community_info) {
            this.community_info = community_info;
        }

        public String getGamename() {
            return gamename;
        }

        public void setGamename(String gamename) {
            this.gamename = gamename;
        }

        public String getGameicon() {
            return gameicon;
        }

        public void setGameicon(String gameicon) {
            this.gameicon = gameicon;
        }

        public String getGenre_str() {
            return genre_str;
        }

        public void setGenre_str(String genre_str) {
            this.genre_str = genre_str;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getCan_answer() {
            return can_answer;
        }

        public List<AnswerInfoVo> getAnswerlist() {
            return answerlist;
        }

        public void setAnswerlist(List<AnswerInfoVo> answerlist) {
            this.answerlist = answerlist;
        }

        public int getCan_solve() {
            return can_solve;
        }

        public int getStatus() {
            return status;
        }

        public int getUid() {
            return uid;
        }

        public int getPlay_count() {
            return play_count;
        }

        public int getCan_question() {
            return can_question;
        }
    }
}
