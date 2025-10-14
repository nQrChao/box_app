package com.zqhy.app.core.data.model.game;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/21
 */

public class GameCouponListVo extends BaseVo{

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean{

        /**
         * coupon_id : 4
         * amount : 2
         * use_cdt : 单笔满10元可用
         * range : 适用于《大话萌仙7》
         * expiry : 领取后3天有效，请及时使用
         * status : 1
         */

        private int coupon_id;
        private float amount;
        private String use_cdt;
        private String range;
        private String expiry;
        private int status;

        public int getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(int coupon_id) {
            this.coupon_id = coupon_id;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }

        public String getUse_cdt() {
            return use_cdt;
        }

        public void setUse_cdt(String use_cdt) {
            this.use_cdt = use_cdt;
        }

        public String getRange() {
            return range;
        }

        public void setRange(String range) {
            this.range = range;
        }

        public String getExpiry() {
            return expiry;
        }

        public void setExpiry(String expiry) {
            this.expiry = expiry;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
