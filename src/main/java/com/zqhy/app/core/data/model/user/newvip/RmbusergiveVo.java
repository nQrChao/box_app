package com.zqhy.app.core.data.model.user.newvip;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2021/2/3-12:11
 * @description
 */
public class RmbusergiveVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        private String has_give;//yes，已领取奖励，no 未领取
        private int coupon_total;//优惠券总额
        private List<CouponListBean> coupon_list;

        public String getHas_give() {
            return has_give;
        }

        public void setHas_give(String has_give) {
            this.has_give = has_give;
        }

        public int getCoupon_total() {
            return coupon_total;
        }

        public void setCoupon_total(int coupon_total) {
            this.coupon_total = coupon_total;
        }

        public List<CouponListBean> getCoupon_list() {
            return coupon_list;
        }

        public void setCoupon_list(List<CouponListBean> coupon_list) {
            this.coupon_list = coupon_list;
        }
    }

    public static class CouponListBean {
        private String coupon_name;
        private String condition;
        private double discount;

        public String getCoupon_name() {
            return coupon_name;
        }

        public void setCoupon_name(String coupon_name) {
            this.coupon_name = coupon_name;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }
    }
}
