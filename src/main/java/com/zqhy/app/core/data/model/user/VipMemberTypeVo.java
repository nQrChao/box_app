package com.zqhy.app.core.data.model.user;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author leeham
 * @date 2020/3/31-18:43
 * @description
 */
public class VipMemberTypeVo extends BaseVo {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean {
        private int    type_id;
        private String name;
        private int    amount;
        private int    days;
        private int    free_days;

        private final int voucher_price = 6;
        private       int new_benefit;

        private boolean isSelected = false;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public int getType_id() {
            return type_id;
        }

        public String getName() {
            return name;
        }

        public int getAmount() {
            return amount;
        }

        public int getDays() {
            return days;
        }

        public int getFree_days() {
            return free_days;
        }

        /**
         * 总天数
         *
         * @return
         */
        public int getAllDays() {
            return free_days + days;
        }

        /**
         * 总金额
         *
         * @return
         */
        public int getTotalAmount() {
            return getAllDays() * voucher_price;
        }

        /**
         * 优惠金额
         *
         * @return
         */
        public int getReducedPrice() {
            return getTotalAmount() - amount;
        }

        /**
         * 新手礼包卡
         * @return
         */
        public boolean isNewBenefit() {
            return new_benefit == 1;
        }
    }
}
