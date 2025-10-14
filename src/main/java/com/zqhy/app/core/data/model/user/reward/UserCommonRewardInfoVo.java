package com.zqhy.app.core.data.model.user.reward;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2021/2/3-12:11
 * @description
 */
public class UserCommonRewardInfoVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        private List<RewardDataBean> reward_data;

        private String type;

        private ReceiveInfoVo receive_info;

        /**
         * 剩余时间; 主要针对status=1时; 单位:秒
         */
        private int left_time;
        /**
         * 礼包领取状态 0: 无可领礼包; 1:可领取; 10:已领取
         */
        private int status;

        public List<RewardDataBean> getReward_data() {
            return reward_data;
        }

        public String getType() {
            return type;
        }

        public ReceiveInfoVo getReceive_info() {
            return receive_info;
        }

        public int getLeft_time() {
            return left_time;
        }

        public int getStatus() {
            return status;
        }
    }

    public static class ReceiveInfoVo {

        /**
         * vip_level : 2
         * status : 1
         */

        private int vip_level;
        private int status;

        public int getVip_level() {
            return vip_level;
        }

        public int getStatus() {
            return status;
        }
    }

    public static class RewardDataBean {

        /**
         * name : 注册30天
         * reward : 60减5
         * status : 0
         */

        private String name;
        private String reward;
        private int    status;
        /**
         * vip_level : 2
         */

        private int    vip_level;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getReward() {
            return reward;
        }

        public void setReward(String reward) {
            this.reward = reward;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getVip_level() {
            return vip_level;
        }

        public void setVip_level(int vip_level) {
            this.vip_level = vip_level;
        }
    }
}
