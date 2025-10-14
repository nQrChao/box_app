package com.zqhy.app.core.data.model.community.task;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.tryplay.TryGameItemVo;

import java.util.List;

/**
 * @author Administrator
 */
public class TaskStatusVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {

        /**
         * sign_status : 0
         * today_pay_status : 0
         * newbie_task_status : 0
         */

        private int sign_status;
        private int today_pay_status;
        private int today_pay_100_status;
        private int newbie_task_status;

        public int getSign_status() {
            return sign_status;
        }

        public void setSign_status(int sign_status) {
            this.sign_status = sign_status;
        }

        public int getToday_pay_status() {
            return today_pay_status;
        }

        public void setToday_pay_status(int today_pay_status) {
            this.today_pay_status = today_pay_status;
        }

        public int getNewbie_task_status() {
            return newbie_task_status;
        }

        public void setNewbie_task_status(int newbie_task_status) {
            this.newbie_task_status = newbie_task_status;
        }

        public int getToday_pay_100_status() {
            return today_pay_100_status;
        }

        public void setToday_pay_100_status(int today_pay_100_status) {
            this.today_pay_100_status = today_pay_100_status;
        }

        private TaskSignInfoVo.TaskAppJumpInfoBean center_illustration;

        public TaskSignInfoVo.TaskAppJumpInfoBean getCenter_illustration() {
            return center_illustration;
        }

        public void setCenter_illustration(TaskSignInfoVo.TaskAppJumpInfoBean center_illustration) {
            this.center_illustration = center_illustration;
        }


        /****2019.04.15新增试玩**********************/
        private List<TryGameItemVo.DataBean> trial_list;

        public List<TryGameItemVo.DataBean> getTrial_list() {
            return trial_list;
        }

        /**
         * 2019.07.04  新增试玩数量.
         * 当trial_count为0时 不显示限时任务.
         */
        private int trial_count;

        public int getTrial_count() {
            return trial_count;
        }
    }


}
