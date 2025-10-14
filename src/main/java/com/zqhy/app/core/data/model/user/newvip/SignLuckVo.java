package com.zqhy.app.core.data.model.user.newvip;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2021/2/3-12:11
 * @description
 */
public class SignLuckVo extends BaseVo {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean {
        private int    id;//奖励ID
        private String type;//奖励类型 ptb 平台币、 integral 积分、 coupon 代金
        private double    amount;//奖励面额,
        private String title;
        private String type_label;
        private boolean isSelected;

        public String getType_label() {
            return type_label;
        }

        public void setType_label(String type_label) {
            this.type_label = type_label;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
