package com.zqhy.app.core.data.model.transaction;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 */
public class TradeZeroBuyGoodInfoListVo extends BaseVo {

    private List<TradeZeroBuyGoodInfo> data;

    public List<TradeZeroBuyGoodInfo> getData() {
        return data;
    }

    public class TradeZeroBuyGoodInfo{
        /**
         *   "gamename": "盖世强者（GM加倍送充）",
         *       "gameicon": "http://p1.tsyule.cn/2022/02/15/620b0e6d115d1.gif",
         *       "integral": "416",//兑换所需积分
         *       "xh_reg_day": "10",//注册天数
         *       "xh_pay_total": "4164.00",//充值金额
         *       "xh_server": "全区全服",//区服
         *       "gameid": "7746"
         */

        String gamename;
        String gameicon;
        String integral;
        String xh_reg_day;
        String xh_pay_total;
        String xh_server;
        String gameid;
        String id;
        String game_type;
        float xh_pay_game_total;

        public float getXh_pay_game_total() {
            return xh_pay_game_total;
        }

        public void setXh_pay_game_total(float xh_pay_game_total) {
            this.xh_pay_game_total = xh_pay_game_total;
        }

        public String getGame_type() {
            return game_type;
        }

        public void setGame_type(String game_type) {
            this.game_type = game_type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getXh_reg_day() {
            return xh_reg_day;
        }

        public void setXh_reg_day(String xh_reg_day) {
            this.xh_reg_day = xh_reg_day;
        }

        public String getXh_pay_total() {
            return xh_pay_total;
        }

        public void setXh_pay_total(String xh_pay_total) {
            this.xh_pay_total = xh_pay_total;
        }

        public String getXh_server() {
            return xh_server;
        }

        public void setXh_server(String xh_server) {
            this.xh_server = xh_server;
        }

        public String getGameid() {
            return gameid;
        }

        public void setGameid(String gameid) {
            this.gameid = gameid;
        }
    }
}
