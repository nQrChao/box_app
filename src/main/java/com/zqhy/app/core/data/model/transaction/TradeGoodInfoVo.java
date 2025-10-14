package com.zqhy.app.core.data.model.transaction;

/**
 * @author Administrator
 */
public class TradeGoodInfoVo {
    /**
     * gid : 2
     * uid : 6211457
     * gameid : 4940
     * goods_title : 挂机封神录V6号
     * goods_price : 30
     * goods_status : -1
     * gamename : 挂机封神录
     * goods_pic : null
     * show_time : 1523520982
     */

    private int gid;
    private int uid;
    private int gameid;
    private String game_type;
    private String goods_title;
    private int goods_price;
    /**
     * 商品状态
     * 1: 待审核;
     * 2审核中;
     * 3:出售中;
     * 4:交易中;
     * 5:已购买;
     * 10:已出售;
     * -1:审核未通过;
     * -2:商品下架
     */
    private int goods_status;
    private String gamename;
    private String game_suffix;
    private String gameicon;
    private String goods_pic;
    private long trade_time;
    private long verify_time;
    private long show_time;

    private String fail_reason;

    private int game_is_close;

    /**
     * 是否是自己发布的商品   0:否; 1:是
     */
    private int is_seller;

    private int count_down;

    private int goods_type;

    /**
     * 1 出售中
     * 2 已出售
     */
    private int isSelled;

    private int can_bargain;
    private int auto_price;

    public int getCan_bargain() {
        return can_bargain;
    }

    public void setCan_bargain(int can_bargain) {
        this.can_bargain = can_bargain;
    }

    public int getAuto_price() {
        return auto_price;
    }

    public void setAuto_price(int auto_price) {
        this.auto_price = auto_price;
    }

    public int getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(int goods_type) {
        this.goods_type = goods_type;
    }

    public String getGid() {
        return gid+"";
    }

    public String getUid() {
        return uid+"";
    }

    public String getGame_suffix() {
        return game_suffix;
    }

    public void setGame_suffix(String game_suffix) {
        this.game_suffix = game_suffix;
    }

    public String getGameid() {
        return gameid+"";
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public String getGame_type() {
        return game_type;
    }

    public void setGame_type(String game_type) {
        this.game_type = game_type;
    }

    public String getGoods_title() {
        return goods_title;
    }

    public void setGoods_title(String goods_title) {
        this.goods_title = goods_title;
    }

    public String getGoods_price() {
        return goods_price+"";
    }


    public int getGoods_status() {
        return goods_status;
    }

    public void setGoods_status(int goods_status) {
        this.goods_status = goods_status;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getGoods_pic() {
        return goods_pic;
    }

    public void setGoods_pic(String goods_pic) {
        this.goods_pic = goods_pic;
    }

    public long getShow_time() {
        return show_time;
    }

    public void setShow_time(long show_time) {
        this.show_time = show_time;
    }

    public long getVerify_time() {
        return verify_time;
    }

    public void setVerify_time(long verify_time) {
        this.verify_time = verify_time;
    }

    public long getTrade_time() {
        return trade_time;
    }

    public void setTrade_time(long trade_time) {
        this.trade_time = trade_time;
    }

    public String getFail_reason() {
        return fail_reason;
    }

    public void setFail_reason(String fail_reason) {
        this.fail_reason = fail_reason;
    }

    public int getGame_is_close() {
        return game_is_close;
    }

    public void setGame_is_close(int game_is_close) {
        this.game_is_close = game_is_close;
    }

    public int getIs_seller() {
        return is_seller;
    }

    public void setIs_seller(int is_seller) {
        this.is_seller = is_seller;
    }

    public int getCount_down() {
        return count_down;
    }

    private long endTime;

    public void setEndTime(long endTime){
        this.endTime = endTime;
    }
    public long getEndTime() {
        return endTime;
    }

    public void setCount_down(int count_down) {
        this.count_down = count_down;
    }

    public int getIsSelled() {
        return isSelled;
    }

    public void setIsSelled(int isSelled) {
        this.isSelled = isSelled;
    }


    /*****2019.06.20 新增字段**********************************************************/

    private float xh_pay_total;

    public String getGameicon() {
        return gameicon;
    }

    public void setGameicon(String gameicon) {
        this.gameicon = gameicon;
    }

    public float getXh_pay_total() {
        return xh_pay_total;
    }

    public void setXh_pay_total(float xh_pay_total) {
        this.xh_pay_total = xh_pay_total;
    }

    /*****2022.06.09 新增字段**********************************************************/
/*      "xh_pay_game_total": 1250.1, //小号累充
              "profit_rate": 0.07, //折扣
              "server_info": "88区", //区服
              "genre_str": "角色.魔幻", //分类信息，可能为空字符串
              "play_count": "40073", //玩家数量*/
    private float xh_pay_game_total;
    private float profit_rate;
    private String server_info;
    private String genre_str;
    private String play_count;
    private String goods_description;
    private String xh_showname;

    public String getXh_showname() {
        return xh_showname;
    }

    public void setXh_showname(String xh_showname) {
        this.xh_showname = xh_showname;
    }

    public String getGoods_description() {
        return goods_description;
    }

    public void setGoods_description(String goods_description) {
        this.goods_description = goods_description;
    }

    public float getXh_pay_game_total() {
        return xh_pay_game_total;
    }

    public void setXh_pay_game_total(float xh_pay_game_total) {
        this.xh_pay_game_total = xh_pay_game_total;
    }

    public float getProfit_rate() {
        return profit_rate;
    }

    public void setProfit_rate(float profit_rate) {
        this.profit_rate = profit_rate;
    }

    public String getServer_info() {
        return server_info;
    }

    public void setServer_info(String server_info) {
        this.server_info = server_info;
    }

    public String getGenre_str() {
        return genre_str;
    }

    public void setGenre_str(String genre_str) {
        this.genre_str = genre_str;
    }

    public String getPlay_count() {
        return play_count;
    }

    public void setPlay_count(String play_count) {
        this.play_count = play_count;
    }
}
