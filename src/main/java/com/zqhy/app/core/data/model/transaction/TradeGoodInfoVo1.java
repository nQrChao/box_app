package com.zqhy.app.core.data.model.transaction;

import java.util.List;

/**
 * @author Administrator
 */
public class TradeGoodInfoVo1 {
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

    private String gid;
    private String uid;
    private String gameid;
    private String game_type;
    private String goods_title;
    private int goods_price;
    private int goods_type;
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
    private List<PicBean> pic;
    private long show_time;

    public int getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(int goods_type) {
        this.goods_type = goods_type;
    }

    public List<PicBean> getPic() {
        return pic;
    }

    public void setPic(List<PicBean> pic) {
        this.pic = pic;
    }

    public class PicBean{
//                │           "pid": 171988,
//                │           "gid": 25799,
//                │           "pic_path": "https:\/\/trade.cqxiayou.com\/upload\/trade\/20231011\/30ff4000758928ed742285d3f607b7fd.jpg",
//                │           "pic_width": 0,
//                │           "pic_height": 0

         int pid;
         int gid;
         int pic_height;
         int pic_width;
         String pic_path;

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public int getPic_height() {
            return pic_height;
        }

        public void setPic_height(int pic_height) {
            this.pic_height = pic_height;
        }

        public int getPic_width() {
            return pic_width;
        }

        public void setPic_width(int pic_width) {
            this.pic_width = pic_width;
        }

        public String getPic_path() {
            return pic_path;
        }

        public void setPic_path(String pic_path) {
            this.pic_path = pic_path;
        }
    }

    private String fail_reason;

    private int game_is_close;

    /**
     * 是否是自己发布的商品   0:否; 1:是
     */
    private int is_seller;

    private int count_down;


    /**
     * 1 出售中
     * 2 已出售
     */
    private int isSelled;

    public String getGame_suffix() {
        return game_suffix;
    }

    public void setGame_suffix(String game_suffix) {
        this.game_suffix = game_suffix;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGameid() {
        return gameid;
    }

    public void setGameid(String gameid) {
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

    public void setGoods_price(int goods_price) {
        this.goods_price = goods_price;
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


    public long getShow_time() {
        return show_time;
    }

    public void setShow_time(long show_time) {
        this.show_time = show_time;
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
    private int play_count;
    private String goods_description;

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

    public int getPlay_count() {
        return play_count;
    }

    public void setPlay_count(int play_count) {
        this.play_count = play_count;
    }
}
