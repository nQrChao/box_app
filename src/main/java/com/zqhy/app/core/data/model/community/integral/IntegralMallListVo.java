package com.zqhy.app.core.data.model.community.integral;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 */
public class IntegralMallListVo extends BaseVo {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        private List<CouponInfoVo> coupon_list;

        private int integral;

        public List<CouponInfoVo> getCoupon_list() {
            return coupon_list;
        }

        public void setCoupon_list(List<CouponInfoVo> coupon_list) {
            this.coupon_list = coupon_list;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }


        private List<ProductsVo> products;

        public List<ProductsVo> getProducts() {
            return products;
        }
    }


    public static class ProductsVo {

        /**
         * type_title : VIP周末限量抢兑
         * type_description : 周六,周日每日可兑换一次
         * product_list : [{"product_id":"1","product_name":"3平台币","price":"2500","product_pic":"http://p1.tsyule.cn/2020/11/23/5fbb745af223e.png"}]
         */

        private String               type_title;
        private String               type_description;
        private List<ProductsListVo> product_list;

        public String getType_title() {
            return type_title;
        }

        public String getType_description() {
            return type_description;
        }

        public List<ProductsListVo> getProduct_list() {
            return product_list;
        }
    }

    public static class ProductsListVo {

        /**
         * product_id : 1
         * product_name : 3平台币
         * price : 2500
         * product_pic : http://p1.tsyule.cn/2020/11/23/5fbb745af223e.png
         */

        private int                    product_id;
        private String                 product_name;
        private int                    price;
        private String                 product_pic;
        private List<ProductContentVo> product_content;

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getProduct_pic() {
            return product_pic;
        }

        public void setProduct_pic(String product_pic) {
            this.product_pic = product_pic;
        }

        public List<ProductContentVo> getProduct_content() {
            return product_content;
        }
    }

    public static class ProductContentVo {

        /**
         * title : 兑换内容
         * content : 3平台币
         */

        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class CouponInfoVo {
        /**
         * coupon_id : 1040
         * gameid : 0
         * game_type : 2
         * goods_pic :
         * amount : 5
         * use_cdt : 任意金额减5元
         * coupon_name : 5元代金券
         * total_count : 0
         * get_count : 0
         * expiry : 领取后5天有效
         * range : 折扣游戏专用
         * goods_type : 2
         * integral : 666
         * user_limit_count : 0
         */

        private int    coupon_id;
        private int    gameid;
        private int    game_type;
        private String goods_pic;
        private int    amount;
        private String use_cdt;
        private String coupon_name;
        private int    total_count;
        private int    get_count;
        private String expiry;
        private String range;
        private int    goods_type;
        private int    integral;
        private int    user_limit_count;

        public int getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(int coupon_id) {
            this.coupon_id = coupon_id;
        }

        public int getGameid() {
            return gameid;
        }

        public void setGameid(int gameid) {
            this.gameid = gameid;
        }

        public int getGame_type() {
            return game_type;
        }

        public void setGame_type(int game_type) {
            this.game_type = game_type;
        }

        public String getGoods_pic() {
            return goods_pic;
        }

        public void setGoods_pic(String goods_pic) {
            this.goods_pic = goods_pic;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getUse_cdt() {
            return use_cdt;
        }

        public void setUse_cdt(String use_cdt) {
            this.use_cdt = use_cdt;
        }

        public String getCoupon_name() {
            return coupon_name;
        }

        public void setCoupon_name(String coupon_name) {
            this.coupon_name = coupon_name;
        }

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public int getGet_count() {
            return get_count;
        }

        public void setGet_count(int get_count) {
            this.get_count = get_count;
        }

        public String getExpiry() {
            return expiry;
        }

        public void setExpiry(String expiry) {
            this.expiry = expiry;
        }

        public String getRange() {
            return range;
        }

        public void setRange(String range) {
            this.range = range;
        }

        public int getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(int goods_type) {
            this.goods_type = goods_type;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public int getUser_limit_count() {
            return user_limit_count;
        }

        public void setUser_limit_count(int user_limit_count) {
            this.user_limit_count = user_limit_count;
        }
    }
}
