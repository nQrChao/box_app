package com.zqhy.app.core.data.model.transaction;

import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 */
public class TradeMySellInfoVo {
    /**
     * "gamename": "盖世强者（GM加倍送充）",
     * "gameicon": "http://p1.tsyule.cn/2022/02/15/620b0e6d115d1.gif",
     * "xh_pay_total": "4164.00", //充值金额
     * "gameid": "416", //兑换所需积分
     * "xh_reg_day": , //小号注册天数
     * "xh_showname": "小号1", //小号显示名
     * "xh_username": "xh_123123",//小号机读名
     * "ban_trade_info": "不可出售原因", //不为空字符串则不可出售
     */
    String gamename;
    String gameicon;
    String xh_pay_total;
    int gameid;
    int xh_reg_day;
    String xh_showname;
    String xh_username;
    String ban_trade_info;
    String game_type;
    float rmb_total;
    float total;

    public float getXh_pay_game_total() {
        return total;
    }

    public float getRmb_total() {
        return rmb_total;
    }

    public void setRmb_total(float rmb_total) {
        this.rmb_total = rmb_total;
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

    public int getXh_reg_day() {
        return xh_reg_day;
    }

    public void setXh_reg_day(int xh_reg_day) {
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

    public String getGame_type() {
        return game_type;
    }

    public void setGame_type(String game_type) {
        this.game_type = game_type;
    }

    public void setBan_trade_info(String ban_trade_info) {
        this.ban_trade_info = ban_trade_info;
    }
}
