package com.zqhy.app.core.data.model.version;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;

/**
 * @author Administrator
 */
public class AppPopDataVo extends BaseVo {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean extends AppJumpInfoBean {

        /**
         * 是否强制(1:是; 2:否)
         */
        private int pop_force;

        /**
         * 弹窗模式(1:每日弹出; 2:弹出1次)
         */
        private int pop_model;

        public DataBean(String page_type, ParamBean param) {
            super(page_type, param);
        }

        public int getPop_force() {
            return pop_force;
        }

        public void setPop_force(int pop_force) {
            this.pop_force = pop_force;
        }

        public int getPop_model() {
            return pop_model;
        }

        public void setPop_model(int pop_model) {
            this.pop_model = pop_model;
        }


        /**
         * 弹窗频率
         * <p>
         * 1:打开app弹一次
         * 2:每天打开app弹一次
         * 3:每次打开app弹出
         */
        private int frequency;

        /**
         * 用户是否可以终止弹窗,
         * <p>
         * 1:可以,
         * 0:不可以
         */
        private int terminable;

        public int getFrequency() {
            return frequency;
        }

        public int getTerminable() {
            return terminable;
        }

        private String pop_id;

        public String getPop_id() {
            return pop_id;
        }

        public void setPop_id(String pop_id) {
            this.pop_id = pop_id;
        }
    }

}
