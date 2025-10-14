package com.zqhy.app.core.data.model.community.task;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 */
public class TaskItemVo extends BaseVo {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean{

        /**
         * tid : 1
         * task_icon : nt_1
         * task_name : 修改头像
         * task_count : 1
         * finished_count : 0
         * task_type : 1
         * reward_integral : 200
         * description : 给人第一眼的感觉很重要哦！
         * task_status : 0
         */

        private int tid;
        private String task_icon;
        private String task_name;
        private int task_count;
        private int finished_count;
        private int task_type;
        private int reward_integral;
        private String description;
        private int task_status;

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        public String getTask_icon() {
            return task_icon;
        }

        public void setTask_icon(String task_icon) {
            this.task_icon = task_icon;
        }

        public String getTask_name() {
            return task_name;
        }

        public void setTask_name(String task_name) {
            this.task_name = task_name;
        }

        public int getTask_count() {
            return task_count;
        }

        public void setTask_count(int task_count) {
            this.task_count = task_count;
        }

        public int getFinished_count() {
            return finished_count;
        }

        public void setFinished_count(int finished_count) {
            this.finished_count = finished_count;
        }

        public int getTask_type() {
            return task_type;
        }

        public void setTask_type(int task_type) {
            this.task_type = task_type;
        }

        public int getReward_integral() {
            return reward_integral;
        }

        public void setReward_integral(int reward_integral) {
            this.reward_integral = reward_integral;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getTask_status() {
            return task_status;
        }

        public void setTask_status(int task_status) {
            this.task_status = task_status;
        }
    }


}
