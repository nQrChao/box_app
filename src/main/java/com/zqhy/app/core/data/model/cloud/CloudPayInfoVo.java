package com.zqhy.app.core.data.model.cloud;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/15
 */

public class CloudPayInfoVo extends BaseVo{

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean {
        private String id;
        private String name;
        private String price;
        private String need;
        private String day;
        private int chaozhi;
        private String amount;
        private String first_discount;
        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getNeed() {
            return need;
        }

        public void setNeed(String need) {
            this.need = need;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public int getChaozhi() {
            return chaozhi;
        }

        public void setChaozhi(int chaozhi) {
            this.chaozhi = chaozhi;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getFirst_discount() {
            return first_discount;
        }

        public void setFirst_discount(String first_discount) {
            this.first_discount = first_discount;
        }
    }

}
