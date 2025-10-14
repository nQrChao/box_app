package com.zqhy.app.core.data.model.community.qa;

import com.zqhy.app.core.data.model.community.CommunityInfoVo;

import java.util.List;

/**
 * @author Administrator
 */
public class UserQaCanAnswerInfo {

    private CommunityInfoVo community_info;

    private List<AnswerInviteInfoVo> answer_invite_list;

    public CommunityInfoVo getCommunity_info() {
        return community_info;
    }

    public List<AnswerInviteInfoVo> getAnswer_invite_list() {
        return answer_invite_list;
    }


    public static class AnswerInviteInfoVo{

        /**
         * qid : 42
         * uid : 1276238
         * gameid : 146
         * add_time : 1553493920
         * a_count : 1
         * verify_status : 1
         * last_answer_time : 1553495349
         * status : 0
         * content : 怎么快速冲榜？
         * gamename : 我要当宰相（满V版）
         * game_type : 1
         * gameicon : http://p1.btgame01.com/2018/09/13/5b99d277df2ec.png
         * genre_str : 策略·休闲
         */

        private int qid;
        private int uid;
        private int gameid;
        private long add_time;
        private int a_count;
        private int verify_status;
        private long last_answer_time;
        private int status;
        private String content;
        private String gamename;
        private int game_type;
        private String gameicon;
        private String genre_str;

        public int getQid() {
            return qid;
        }

        public void setQid(int qid) {
            this.qid = qid;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getGameid() {
            return gameid;
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

        public int getVerify_status() {
            return verify_status;
        }

        public void setVerify_status(int verify_status) {
            this.verify_status = verify_status;
        }

        public long getLast_answer_time() {
            return last_answer_time;
        }

        public void setLast_answer_time(long last_answer_time) {
            this.last_answer_time = last_answer_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getGamename() {
            return gamename;
        }

        public void setGamename(String gamename) {
            this.gamename = gamename;
        }

        public int getGame_type() {
            return game_type;
        }

        public void setGame_type(int game_type) {
            this.game_type = game_type;
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
    }
}
