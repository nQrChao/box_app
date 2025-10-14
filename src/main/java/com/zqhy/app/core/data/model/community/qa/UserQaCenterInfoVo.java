package com.zqhy.app.core.data.model.community.qa;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.community.CommunityInfoVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.List;

/**
 * @author Administrator
 */
public class UserQaCenterInfoVo extends BaseVo {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean{

        private CommunityInfoVo community_info;

        private int more_answer_game_list;

        private List<GameInfoVo> can_answer_game_list;

        private List<QaCenterQuestionVo> user_answer_question_list;

        private List<QaCenterQuestionVo> user_question_list;

        public CommunityInfoVo getCommunity_info() {
            return community_info;
        }

        public int getMore_answer_game_list() {
            return more_answer_game_list;
        }

        public List<GameInfoVo> getCan_answer_game_list() {
            return can_answer_game_list;
        }

        public List<QaCenterQuestionVo> getUser_answer_question_list() {
            return user_answer_question_list;
        }

        public List<QaCenterQuestionVo> getUser_question_list() {
            return user_question_list;
        }
    }


    public static class QaCenterQuestionVo{

        /**
         * qid : 3
         * gameid : 1641
         * add_time : 1551861685
         * a_count : 1
         * content : 游戏不错，比较耐玩，题材可以不容易厌烦
         * gamename : 雪刀群侠传（满V版）
         * game_type : 1
         * gameicon : http://p1.jiuyao666.com/2019/02/26/5c74f7a168fc4.png
         * genre_str : 卡牌·武侠
         * reward_integral : 50
         */

        private int qid;
        private int gameid;
        private long add_time;
        private int a_count;
        private String content;
        private String gamename;
        private int game_type;
        private String gameicon;
        private String genre_str;
        private int reward_integral;

        /**
         * 审核状态 -1:未通过; 0: 待审核; 1:通过
         */
        private int verify_status;

        /**
         * 最后回答时间
         */
        private long last_answer_time;


        public int getQid() {
            return qid;
        }

        public void setQid(int qid) {
            this.qid = qid;
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

        public int getReward_integral() {
            return reward_integral;
        }

        public void setReward_integral(int reward_integral) {
            this.reward_integral = reward_integral;
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
    }
}
