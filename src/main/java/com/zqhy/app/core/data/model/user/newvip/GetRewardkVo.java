package com.zqhy.app.core.data.model.user.newvip;

import com.zqhy.app.core.data.model.BaseVo;

/**
 * @author leeham2734
 * @date 2021/2/3-12:11
 * @description
 */
public class GetRewardkVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        private String title;//奖励内容
        private String reward_type;//奖励类型 ptb 平台币、 integral 积分、 coupon 代金券

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
    }
}
