package com.zqhy.app.core.data.model.game.coupon;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/8/28-10:37
 * @description
 */
public class GameCouponsListVo extends BaseVo {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean {
        private int itemId;

        private GameInfoVo     gameinfo;
        private List<CouponVo> coupon_list;

        public int getItemId() {
            return gameinfo == null ? 0 : gameinfo.getGameid();
        }

        public GameInfoVo getGameinfo() {
            return gameinfo;
        }

        public void setGameinfo(GameInfoVo gameinfo) {
            this.gameinfo = gameinfo;
        }

        public List<CouponVo> getCoupon_list() {
            return coupon_list;
        }

        public void setCoupon_list(List<CouponVo> coupon_list) {
            this.coupon_list = coupon_list;
        }
    }

    public static class CouponVo {

        /**
         * coupon_id : 5491
         * coupon_name : 满1000可用
         * amount : 200
         * remain_days : 还剩3天结束
         * status : 1
         */

        private int    coupon_id;
        private String coupon_name;
        private float  amount;
        private String remain_days;
        private int    status;

        public int getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(int coupon_id) {
            this.coupon_id = coupon_id;
        }

        public String getCoupon_name() {
            return coupon_name;
        }

        public void setCoupon_name(String coupon_name) {
            this.coupon_name = coupon_name;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getRemain_days() {
            return remain_days;
        }

        public void setRemain_days(String remain_days) {
            this.remain_days = remain_days;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
