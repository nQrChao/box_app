package com.zqhy.app.core.data.model.user.newvip;

import com.zqhy.app.core.data.model.BaseVo;

/**
 * @author leeham2734
 * @date 2021/2/3-12:11
 * @description
 */
public class BirthdayRewardVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        private String title;//奖励内容
        private String reward_type;
        private int amount;//代金券面额
        private String cdt;//代金券使用条件

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getReward_type() {
            return reward_type;
        }

        public void setReward_type(String reward_type) {
            this.reward_type = reward_type;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getCdt() {
            return cdt;
        }

        public void setCdt(String cdt) {
            this.cdt = cdt;
        }
    }
}
