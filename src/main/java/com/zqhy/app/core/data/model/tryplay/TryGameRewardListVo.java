package com.zqhy.app.core.data.model.tryplay;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 */
public class TryGameRewardListVo extends BaseVo {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean{

        /**
         * id : 5
         * uid : 7
         * ttid : 10
         * tid : 1
         * reward_time : 1555313480
         * reward_integral : 30
         * username : hi000007
         */

        private int id;
        private int uid;
        private int ttid;
        private int tid;
        private String reward_time;
        private int reward_integral;
        private String username;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getTtid() {
            return ttid;
        }

        public void setTtid(int ttid) {
            this.ttid = ttid;
        }

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        public String getReward_time() {
            return reward_time;
        }

        public void setReward_time(String reward_time) {
            this.reward_time = reward_time;
        }

        public int getReward_integral() {
            return reward_integral;
        }

        public void setReward_integral(int reward_integral) {
            this.reward_integral = reward_integral;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
