package com.zqhy.app.core.data.model.transaction;

/**
 * @author Administrator
 */
public class TradeCollectInfoVo {

    /**
     *  // #region 202206 app更新新增
     *       "xh_pay_game_total": 1250.1, //小号累充
     *       "profit_rate": 0.07, //折扣
     *       "server_info": "88区", //区服
     *       "genre_str": "角色.魔幻", //分类信息，可能为空字符串
     *       "play_count": "40073", //玩家数量
     *       "collection_time": 12312312, //收藏时间，unix时间戳
     *       // #endregion
     *       "gid": "11782",
     *       "uid": "1684526",
     *       "gameid": "5005",
     *       "goods_title": "底子号出售。",
     *       "xh_pay_total": "1250.00",
     *       "goods_price": "88",
     *       "goods_status": "3",
     *       "fail_reason_id": "0",
     *       "gamename": "赤月龙城之原始神器",
     *       "game_type": "2",
     *       "gameicon": "https://sysimage.tsyule.cn/2021/02/25/603717d7c4a55.gif",
     *       "genre_ids": "62,70,75,0",
     *       "game_is_close": 0,
     *       "goods_pic": "https://images.tsyule.cn/2022/04/05/19/624c24a136541.jpg",
     *       "show_time": "1649157288",
     *       "count_down": 0,
     *       "fail_reason": ""
     */
    float xh_pay_game_total;
    float profit_rate;
    String server_info;
    String genre_str;
    String play_count;
    long add_time;
    long game_is_close;
    long count_down;
    int gid;
    int uid;
    int gameid;
    String goods_title;
    String game_suffix;
    String xh_pay_total;
    String goods_price;
    String goods_status;
    String fail_reason_id;
    String gamename;
    String game_type;
    String gameicon;
    String genre_ids;
    String goods_pic;
    String show_time;
    String fail_reason;
    String goods_description;
    int goods_type;

    public int getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(int goods_type) {
        this.goods_type = goods_type;
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

    public String getGame_suffix() {
        return game_suffix;
    }

    public void setGame_suffix(String game_suffix) {
        this.game_suffix = game_suffix;
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

    public long getCollection_time() {
        return add_time;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public long getGame_is_close() {
        return game_is_close;
    }

    public void setGame_is_close(long game_is_close) {
        this.game_is_close = game_is_close;
    }

    public long getCount_down() {
        return count_down;
    }

    public void setCount_down(long count_down) {
        this.count_down = count_down;
    }

    public String getGid() {
        return gid+"";
    }


    public String getUid() {
        return uid+"";
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

    public String getGoods_title() {
        return goods_title;
    }

    public void setGoods_title(String goods_title) {
        this.goods_title = goods_title;
    }

    public String getXh_pay_total() {
        return xh_pay_total;
    }

    public void setXh_pay_total(String xh_pay_total) {
        this.xh_pay_total = xh_pay_total;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_status() {
        return goods_status;
    }

    public void setGoods_status(String goods_status) {
        this.goods_status = goods_status;
    }

    public String getFail_reason_id() {
        return fail_reason_id;
    }

    public void setFail_reason_id(String fail_reason_id) {
        this.fail_reason_id = fail_reason_id;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getGame_type() {
        return game_type;
    }

    public void setGame_type(String game_type) {
        this.game_type = game_type;
    }

    public String getGameicon() {
        return gameicon;
    }

    public void setGameicon(String gameicon) {
        this.gameicon = gameicon;
    }

    public String getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(String genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getGoods_pic() {
        return goods_pic;
    }

    public void setGoods_pic(String goods_pic) {
        this.goods_pic = goods_pic;
    }

    public String getShow_time() {
        return show_time;
    }

    public void setShow_time(String show_time) {
        this.show_time = show_time;
    }

    public String getFail_reason() {
        return fail_reason;
    }

    public void setFail_reason(String fail_reason) {
        this.fail_reason = fail_reason;
    }
}
