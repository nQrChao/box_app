package com.zqhy.app.core.data.model.user;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

public class MoneyCardOrderVo extends BaseVo {
    public List<CardOrderInfo> data;

    public List<CardOrderInfo> getData() {
        return data;
    }

    public class CardOrderInfo{
        /**
         *    "id": 1,
         *         "pay_time":"1561234567",//充值时间
         *         "expiry_time": "1561234567",//有效期至时间
         *         "purchase_days": "30",//购买天数
         *         "purchase_member_type": 1//购买类型 1 周卡 2 月卡  3季卡
         */

        int id;
        String pay_time;
        String expiry_time;
        String purchase_days;

        String type_label;
        int purchase_member_type;
        private int good_type;

        public int getGood_type() {
            return good_type;
        }

        public void setGood_type(int good_type) {
            this.good_type = good_type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getExpiry_time() {
            return expiry_time;
        }

        public void setExpiry_time(String expiry_time) {
            this.expiry_time = expiry_time;
        }

        public String getPurchase_days() {
            return purchase_days;
        }

        public void setPurchase_days(String purchase_days) {
            this.purchase_days = purchase_days;
        }

        public int getPurchase_member_type() {
            return purchase_member_type;
        }

        public void setPurchase_member_type(int purchase_member_type) {
            this.purchase_member_type = purchase_member_type;
        }

        public String getType_label() {
            return type_label;
        }

        public void setType_label(String type_label) {
            this.type_label = type_label;
        }
    }
}
