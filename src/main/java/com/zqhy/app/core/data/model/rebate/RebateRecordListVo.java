package com.zqhy.app.core.data.model.rebate;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/16
 */

public class RebateRecordListVo extends BaseVo {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean {
        /**
         * apply_id : 322514
         * xh_uid : 7407410
         * gameid : 4308
         * usable_total : 900.00
         * day_time : 07-26
         * gamename : 犬夜叉-寻玉の旅(满V版)
         * gameicon : http://p1.btgame01.com/2017/10/24/59eedd0441b97.png
         * game_type : 1
         * status : -1
         */

        /**
         * 数据申请ID
         */
        private String apply_id;
        /**
         * 小号UID
         */
        private String xh_uid;
        /**
         * 小号名称
         */
        private String xh_showname;
        /**
         * 游戏ID
         */
        private int gameid;
        /**
         * 申请金额
         */
        private String usable_total;
        /**
         * 充值时间 例如 20180726
         */
        private String day_time;
        /**
         * 游戏名称
         */
        private String gamename;
        /**
         * 游戏图标
         */
        private String gameicon;
        /**
         * 游戏类型
         */
        private int game_type;
        /**
         * 申请状态 1: 等待受理; 2:受理中; 10: 已完成; -1: 申请失败
         */
        private int status;

        public String getApply_id() {
            return apply_id;
        }

        public void setApply_id(String apply_id) {
            this.apply_id = apply_id;
        }

        public String getXh_uid() {
            return xh_uid;
        }

        public void setXh_uid(String xh_uid) {
            this.xh_uid = xh_uid;
        }

        public int getGameid() {
            return gameid;
        }

        public void setGameid(int gameid) {
            this.gameid = gameid;
        }

        public String getUsable_total() {
            return usable_total;
        }

        public void setUsable_total(String usable_total) {
            this.usable_total = usable_total;
        }

        public String getDay_time() {
            return day_time;
        }

        public void setDay_time(String day_time) {
            this.day_time = day_time;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getXh_showname() {
            return xh_showname;
        }

        public void setXh_showname(String xh_showname) {
            this.xh_showname = xh_showname;
        }
    }
}
