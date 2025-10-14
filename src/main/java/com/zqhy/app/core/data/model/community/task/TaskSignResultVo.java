package com.zqhy.app.core.data.model.community.task;

import com.zqhy.app.core.data.model.BaseVo;

/**
 * @author leeham2734
 * @date 2020/10/30-11:41
 * @description
 */
public class TaskSignResultVo extends BaseVo {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean{
        /**
         * integral : 2
         * act_num : 1
         */

        private int integral;
        private int act_num;
        private int continued_days;

        public int getIntegral() {
            return integral;
        }

        public int getAct_num() {
            return act_num;
        }

        public int getContinued_days() {
            return continued_days;
        }
    }
}
