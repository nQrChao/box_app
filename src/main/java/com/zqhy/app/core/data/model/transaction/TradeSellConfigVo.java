package com.zqhy.app.core.data.model.transaction;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 */
public class TradeSellConfigVo extends BaseVo {

    private SellConfig data;

    public SellConfig getData() {
        return data;
    }

    public class SellConfig {
        /**
         * "rate": 5, //交易手续费费率
         * "min_charge": 5, //最低手续费
         * "super_user_rate": 2, //会员交易手续费费率
         * "super_user_min_charge": 3, //会员最低手续费
         * "is_super_user": true //是否为会员，布尔值
         */
        float rate;
        float min_charge;
        float super_user_rate;
        float super_user_min_charge;
        int is_super_user;
        private GoodsConfig trade_info;

        public float getRate() {
            return rate;
        }

        public void setRate(float rate) {
            this.rate = rate;
        }

        public float getMin_charge() {
            return min_charge;
        }

        public void setMin_charge(float min_charge) {
            this.min_charge = min_charge;
        }

        public float getSuper_user_rate() {
            return super_user_rate;
        }

        public void setSuper_user_rate(float super_user_rate) {
            this.super_user_rate = super_user_rate;
        }

        public float getSuper_user_min_charge() {
            return super_user_min_charge;
        }

        public void setSuper_user_min_charge(float super_user_min_charge) {
            this.super_user_min_charge = super_user_min_charge;
        }

        public boolean isIs_super_user() {
            return is_super_user!=0;
        }

        public int getIs_super_user() {
            return is_super_user;
        }

        public void setIs_super_user(int is_super_user) {
            this.is_super_user = is_super_user;
        }

        public GoodsConfig getTrade_goods() {
            return trade_info;
        }

        public void setTrade_goods(GoodsConfig trade_goods) {
            this.trade_info = trade_goods;
        }

        //当传入gid参数时返回
        public class GoodsConfig {
            /**
             * "gamename": "盖世强者（GM加倍送充）",
             * "gameicon": "http://p1.tsyule.cn/2022/02/15/620b0e6d115d1.gif",
             * "xh_pay_total": "4164.00", //充值金额
             * "gameid": "416", //游戏ID
             * "xh_reg_day": , //小号注册天数
             * "xh_showname": "小号1", //小号显示名
             * "xh_username": "xh_123123",//小号机读名
             * "ban_trade_info": "不可出售原因", //为空字符串则可出售
             */

            String gamename;
            String gameicon;
            String xh_pay_total;
            int gameid;
            String xh_reg_day;
            String xh_showname;
            String xh_username;
            String ban_trade_info;
            float xh_pay_game_total;
            float rmb_total;

            public float getRmb_total() {
                return rmb_total;
            }

            public void setRmb_total(float rmb_total) {
                this.rmb_total = rmb_total;
            }

            public float getXh_pay_game_total() {
                return xh_pay_game_total;
            }

            public void setXh_pay_game_total(float xh_pay_game_total) {
                this.xh_pay_game_total = xh_pay_game_total;
            }

            public String getGamename() {
                return CommonUtils.getGamename(gamename);
            }

            public String getOtherGameName(){
                return CommonUtils.getOtherGameName(gamename);
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

            public String getXh_pay_total() {
                return xh_pay_total;
            }

            public void setXh_pay_total(String xh_pay_total) {
                this.xh_pay_total = xh_pay_total;
            }

            public String getGameid() {
                return gameid+"";
            }

            public void setGameid(int gameid) {
                this.gameid = gameid;
            }

            public String getXh_reg_day() {
                return xh_reg_day;
            }

            public void setXh_reg_day(String xh_reg_day) {
                this.xh_reg_day = xh_reg_day;
            }

            public String getXh_showname() {
                return xh_showname;
            }

            public void setXh_showname(String xh_showname) {
                this.xh_showname = xh_showname;
            }

            public String getXh_username() {
                return xh_username;
            }

            public void setXh_username(String xh_username) {
                this.xh_username = xh_username;
            }

            public String getBan_trade_info() {
                return ban_trade_info;
            }

            public void setBan_trade_info(String ban_trade_info) {
                this.ban_trade_info = ban_trade_info;
            }
        }
    }
}
