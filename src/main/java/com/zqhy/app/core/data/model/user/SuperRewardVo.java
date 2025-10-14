package com.zqhy.app.core.data.model.user;

import com.zqhy.app.core.data.model.BaseVo;

/**
 * @author leeham2734
 * @date 2021/2/3-12:11
 * @description
 */
public class SuperRewardVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        private int reward;//奖励的平台币金额
        private String reward_type;//奖励类型 ptb 平台币、 integral 积分、 coupon 代金券

        public int getReward() {
            return reward;
        }

        public void setReward(int reward) {
            this.reward = reward;
        }

        public String getReward_type() {
            return reward_type;
        }

        public void setReward_type(String reward_type) {
            this.reward_type = reward_type;
        }
    }
}
