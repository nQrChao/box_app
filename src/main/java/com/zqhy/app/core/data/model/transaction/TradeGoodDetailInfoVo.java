package com.zqhy.app.core.data.model.transaction;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 */
public class TradeGoodDetailInfoVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        /**
         * gid : 4
         * uid : 10930709
         * gameid : 3048
         * xh_username : xh_1522389934160
         * xh_showname : 一二三四
         * server_info : asfasfasfdasda
         * goods_description :
         * goods_price : 123416
         * goods_title : safafsafasfsafasfsaf
         * xh_ctime : 1522389934
         * xh_pay_total : 6
         * goods_status : 3
         * game_is_close : 0
         * pic_list : [{"id":"1","pic_path":"http://p2user.btgame01.com/2018/04/13/5ad013cef06f1.png","pic_width":"979","pic_height":"605"},{"id":"2","pic_path":"http://p2user.btgame01.com/2018/04/13/5ad013cf0565d.png","pic_width":"979","pic_height":"605"},{"id":"3","pic_path":"http://p2user.btgame01.com/2018/04/13/5ad013cf0e7c9.png","pic_width":"430","pic_height":"344"}]
         * gamename : 十万个小伙伴
         * gameicon : http://p1.lehihi.cn/2017/08/25/599fc6d3b5d47.png
         * game_type : 2
         * package_size : 3
         * genre_list : ["休闲","挂机"]
         * share_info : {}
         */

        public class ShareInfo {
            private Param param;
            String page_type;

            public class Param {
                String share_title;
                String share_text;
                String share_target_url;
                String share_image;

                public String getShare_title() {
                    return share_title;
                }

                public void setShare_title(String share_title) {
                    this.share_title = share_title;
                }

                public String getShare_text() {
                    return share_text;
                }

                public void setShare_text(String share_text) {
                    this.share_text = share_text;
                }

                public String getShare_target_url() {
                    return share_target_url;
                }

                public void setShare_target_url(String share_target_url) {
                    this.share_target_url = share_target_url;
                }

                public String getShare_image() {
                    return share_image;
                }

                public void setShare_image(String share_image) {
                    this.share_image = share_image;
                }
            }

            public Param getParam() {
                return param;
            }

            public void setParam(Param param) {
                this.param = param;
            }

            public String getPage_type() {
                return page_type;
            }

            public void setPage_type(String page_type) {
                this.page_type = page_type;
            }
        }

        private ShareInfo share_info;

        public ShareInfo getShare_info() {
            return share_info;
        }

        public void setShare_info(ShareInfo share_info) {
            this.share_info = share_info;
        }

        private int gid;

        private int uid;
        private int gameid;
        private String xh_username;
        private String xh_showname;
        private String server_info;
        private String goods_description;
        private String goods_price;
        private String goods_title;
        private String game_suffix;
        private long xh_ctime;
        private String xh_pay_total;
        private int goods_status;
        private int goods_type;

        private long verify_time;

        /**
         * 关联游戏是否下架 0:否; 1:是
         */
        private int game_is_close;
        private String gamename;
        private String gameicon;
        private String game_type;
        private float package_size;
        private List<PicListBean> pic_list;
        private String genre_list;

        private int cdays;
        private String fail_reason;
        /**
         * 是否是自己发布的商品   0:否; 1:是
         */
        private int is_seller;


        /**
         * 1: android端; 2: ios端; 3:双端
         */
        private int client_type;
        private int data_exchange;//双端数据互通 0 未设置 1 互通 2 不互通
        private int xh_client;//1: android端; 2: ios端 3:双端

        /**
         * 2018.05.03 增加二级密码
         */
        private String xh_passwd;
        /**
         * 2018.05.03 交易时间
         */
        private long trade_time;

        /**
         * 2018.05.08 是否有二级密码; 1:有; ０：木有
         */
        private int has_xh_passwd;
        /**
         * 2022.06.13 新ui
         * // #region 202206 app更新新增
         * "xh_pay_game_total": 1250.1, //小号累充
         * "profit_rate": 0.07, //折扣
         * "can_bargain": "1", //是否可以砍价 1 不允许、  2 允许
         * "genre_str": "角色•传奇", //分类信息，可能为空字符串
         * "play_count": "40073", //玩家数量
         * "is_collection": "no", //是否已收藏
         */

        private float xh_pay_game_total;
        private float profit_rate;
        private int can_bargain;
        private int auto_price;
        private String genre_str;
        private String play_count;
        private int is_favorite;

        public int getXh_client() {
            return xh_client;
        }

        public void setXh_client(int xh_client) {
            this.xh_client = xh_client;
        }

        public int getData_exchange() {
            return data_exchange;
        }

        public void setData_exchange(int data_exchange) {
            this.data_exchange = data_exchange;
        }

        public int getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(int goods_type) {
            this.goods_type = goods_type;
        }

        public int getAuto_price() {
            return auto_price;
        }

        public void setAuto_price(int auto_price) {
            this.auto_price = auto_price;
        }

        public String getGame_suffix() {
            return game_suffix;
        }

        public void setGame_suffix(String game_suffix) {
            this.game_suffix = game_suffix;
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

        public String getCan_bargain() {
            return can_bargain + "";
        }

        public void setCan_bargain(int can_bargain) {
            this.can_bargain = can_bargain;
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

        public int getIs_favorite() {
            return is_favorite;
        }

        public void setIs_favorite(int is_favorite) {
            this.is_favorite = is_favorite;
        }

        public String getGid() {
            return gid + "";
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public String getUid() {
            return uid + "";
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public void setGameid(int gameid) {
            this.gameid = gameid;
        }

        public String getGameid() {
            return gameid + "";
        }

        public String getXh_username() {
            return xh_username;
        }

        public void setXh_username(String xh_username) {
            this.xh_username = xh_username;
        }

        public String getXh_showname() {
            return xh_showname;
        }

        public void setXh_showname(String xh_showname) {
            this.xh_showname = xh_showname;
        }

        public String getServer_info() {
            return server_info;
        }

        public void setServer_info(String server_info) {
            this.server_info = server_info;
        }

        public String getGoods_description() {
            return goods_description;
        }

        public void setGoods_description(String goods_description) {
            this.goods_description = goods_description;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_title() {
            return goods_title;
        }

        public void setGoods_title(String goods_title) {
            this.goods_title = goods_title;
        }

        public long getXh_ctime() {
            return xh_ctime;
        }

        public void setXh_ctime(long xh_ctime) {
            this.xh_ctime = xh_ctime;
        }

        public String getXh_pay_total() {
            return xh_pay_total;
        }

        public void setXh_pay_total(String xh_pay_total) {
            this.xh_pay_total = xh_pay_total;
        }

        public int getGoods_status() {
            return goods_status;
        }

        public void setGoods_status(int goods_status) {
            this.goods_status = goods_status;
        }

        public int getGame_is_close() {
            return game_is_close;
        }

        public void setGame_is_close(int game_is_close) {
            this.game_is_close = game_is_close;
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

        public String getGame_type() {
            return game_type;
        }

        public void setGame_type(String game_type) {
            this.game_type = game_type;
        }

        public float getPackage_size() {
            return package_size;
        }

        public void setPackage_size(float package_size) {
            this.package_size = package_size;
        }

        public List<PicListBean> getPic_list() {
            return pic_list;
        }

        public void setPic_list(List<PicListBean> pic_list) {
            this.pic_list = pic_list;
        }

        public String getGenre_list() {
            return genre_list;
        }

        public void setGenre_list(String genre_list) {
            this.genre_list = genre_list;
        }

        public int getCdays() {
            return cdays;
        }

        public void setCdays(int cdays) {
            this.cdays = cdays;
        }

        public String getFail_reason() {
            return fail_reason;
        }

        public void setFail_reason(String fail_reason) {
            this.fail_reason = fail_reason;
        }

        public int getIs_seller() {
            return is_seller;
        }

        public void setIs_seller(int is_seller) {
            this.is_seller = is_seller;
        }

        public int getClient_type() {
            return client_type;
        }

        public void setClient_type(int client_type) {
            this.client_type = client_type;
        }

        public long getVerify_time() {
            return verify_time;
        }

        public void setVerify_time(long verify_time) {
            this.verify_time = verify_time;
        }

        public String getXh_passwd() {
            return xh_passwd;
        }

        public void setXh_passwd(String xh_passwd) {
            this.xh_passwd = xh_passwd;
        }

        public long getTrade_time() {
            return trade_time;
        }

        public void setTrade_time(long trade_time) {
            this.trade_time = trade_time;
        }

        public int getHas_xh_passwd() {
            if (xh_passwd != null) {
                if (!xh_passwd.isEmpty()) {
                    return 1;
                } else {
                    return 0;
                }
            }
            return 0;
        }

        public void setHas_xh_passwd(int has_xh_passwd) {
            this.has_xh_passwd = has_xh_passwd;
        }


    }

    public static class PicListBean {
        /**
         * id : 1
         * pic_path : http://p2user.btgame01.com/2018/04/13/5ad013cef06f1.png
         * pic_width : 979
         * pic_height : 605
         */

        private String pid;
        private String pic_path;
        private float pic_width;
        private float pic_height;


        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getPic_path() {
            return pic_path;
        }

        public void setPic_path(String pic_path) {
            this.pic_path = pic_path;
        }

        public float getPic_width() {
            return pic_width;
        }

        public void setPic_width(float pic_width) {
            this.pic_width = pic_width;
        }

        public float getPic_height() {
            return pic_height;
        }

        public void setPic_height(float pic_height) {
            this.pic_height = pic_height;
        }
    }
}
