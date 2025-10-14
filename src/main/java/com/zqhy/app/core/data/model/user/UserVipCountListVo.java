package com.zqhy.app.core.data.model.user;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/11/25-17:30
 * @description
 */
public class UserVipCountListVo extends BaseVo {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean {

        /**
         * amount : 10
         * vip_score : 220
         * type_id : 7
         * add_time : 1606285553
         * remark : 活动测试2
         * type_name : 参与活动
         */

        private int    amount;
        private int    vip_score;
        private int    type_id;
        private long   add_time;
        private String remark;
        private String type_name;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getVip_score() {
            return vip_score;
        }

        public void setVip_score(int vip_score) {
            this.vip_score = vip_score;
        }

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }
    }
}
