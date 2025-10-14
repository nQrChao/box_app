package com.zqhy.app.core.data.model.transfer;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/17
 */

public class TransferGameListVo extends BaseVo{


    private TransferVo data;

    public TransferVo getData() {
        return data;
    }


    public static class TransferVo{
        int user_points;

        List<DataBean> apply_log;

        List<DataBean> transfer_reward_list;

        public int getUser_points() {
            return user_points;
        }

        public List<DataBean> getApply_log() {
            return apply_log;
        }

        public List<DataBean> getTransfer_reward_list() {
            return transfer_reward_list;
        }
    }

    public static class DataBean{
        /**
         * gameid : 6
         * gamename : 格斗武皇BT版
         * gameicon : http://p1.lehihi.cn/2017/03/31/58de03b46a16a.png
         * game_type : 1
         * genre_name : 动作
         * discount : null
         */

        private int gameid;
        private String gamename;
        private String gameicon;
        private int game_type;
        private String genre_name;
        private String discount;
        /**
         * index_id : 10
         * applytime : 1514859537
         * endtime : 0
         */

        private String index_id;
        private String applytime;
        private long endtime;


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

        public String getGameicon() {
            return gameicon;
        }

        public void setGameicon(String gameicon) {
            this.gameicon = gameicon;
        }

        public int getGame_type() {
            return game_type;
        }

        public void setGame_type(int game_type) {
            this.game_type = game_type;
        }

        public String getGenre_name() {
            return genre_name;
        }

        public void setGenre_name(String genre_name) {
            this.genre_name = genre_name;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getIndex_id() {
            return index_id;
        }

        public void setIndex_id(String index_id) {
            this.index_id = index_id;
        }

        public String getApplytime() {
            return applytime;
        }

        public void setApplytime(String applytime) {
            this.applytime = applytime;
        }

        public long getEndtime() {
            return endtime;
        }

        public void setEndtime(long endtime) {
            this.endtime = endtime;
        }
    }


}
