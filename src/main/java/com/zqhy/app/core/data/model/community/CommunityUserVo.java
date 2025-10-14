package com.zqhy.app.core.data.model.community;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 */
public class CommunityUserVo extends BaseVo {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean{
        /**
         * community_info : {"user_nickname":"叼炸天","user_icon":"http://p2user.jiuyao666.com/2019/03/04/5c7c9c007381b.png","act_num":"1","user_level":1}
         * game_track_list : [{"gameid":"1","gamename":"贪玩硬汉豪华2版--侵权不上线","game_type":"1","game_client":"3","gameicon":"http://p1.btgame01.com/2018/11/01/5bda726c030d2.png"},{"gameid":"865","gamename":"战仙传","game_type":"2","game_client":"3","gameicon":"http://p1.jiuyao666.com/2018/12/26/5c23281047a22.png"}]
         * game_track_count : 2
         * community_stat : {"comment_verify_count":"0","answer_verify_count":"0","be_praised_count":"0"}
         */

        private CommunityInfoVo community_info;
        private int game_track_count;
        private CommunityStatBean community_stat;
        private List<GameTrackListBean> game_track_list;

        private String is_super_user;

        public String getIs_super_user() {
            return is_super_user;
        }

        public void setIs_super_user(String is_super_user) {
            this.is_super_user = is_super_user;
        }

        public CommunityInfoVo getCommunity_info() {
            return community_info;
        }

        public void setCommunity_info(CommunityInfoVo community_info) {
            this.community_info = community_info;
        }

        public int getGame_track_count() {
            return game_track_count;
        }

        public void setGame_track_count(int game_track_count) {
            this.game_track_count = game_track_count;
        }

        public CommunityStatBean getCommunity_stat() {
            return community_stat;
        }

        public void setCommunity_stat(CommunityStatBean community_stat) {
            this.community_stat = community_stat;
        }

        public List<GameTrackListBean> getGame_track_list() {
            return game_track_list;
        }

        public void setGame_track_list(List<GameTrackListBean> game_track_list) {
            this.game_track_list = game_track_list;
        }

    }

    public static class CommunityStatBean {
        /**
         * comment_verify_count : 0
         * answer_verify_count : 0
         * be_praised_count : 0
         */

        private int comment_verify_count;
        private int answer_verify_count;
        private int be_praised_count;

        public int getComment_verify_count() {
            return comment_verify_count;
        }

        public void setComment_verify_count(int comment_verify_count) {
            this.comment_verify_count = comment_verify_count;
        }

        public int getAnswer_verify_count() {
            return answer_verify_count;
        }

        public void setAnswer_verify_count(int answer_verify_count) {
            this.answer_verify_count = answer_verify_count;
        }

        public int getBe_praised_count() {
            return be_praised_count;
        }

        public void setBe_praised_count(int be_praised_count) {
            this.be_praised_count = be_praised_count;
        }
    }

    public static class GameTrackListBean {
        /**
         * gameid : 1
         * gamename : 贪玩硬汉豪华2版--侵权不上线
         * game_type : 1
         * game_client : 3
         * gameicon : http://p1.btgame01.com/2018/11/01/5bda726c030d2.png
         */

        private int gameid;
        private String gamename;
        private int game_type;
        private String game_client;
        private String gameicon;

        public int getGameid() {
            return gameid;
        }

        public void setGameid(int gameid) {
            this.gameid = gameid;
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

        public String getGame_client() {
            return game_client;
        }

        public void setGame_client(String game_client) {
            this.game_client = game_client;
        }

        public String getGameicon() {
            return gameicon;
        }

        public void setGameicon(String gameicon) {
            this.gameicon = gameicon;
        }
    }
}
