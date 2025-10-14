package com.zqhy.app.core.data.model.community;

import com.zqhy.app.core.data.model.BaseVo;

/**
 * @author Administrator
 */
public class UserIntegralVo extends BaseVo {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean{
        /**
         * integral : 220
         * is_special : 0
         */

        private int integral;
        private int is_special;

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public int getIs_special() {
            return is_special;
        }

        public void setIs_special(int is_special) {
            this.is_special = is_special;
        }
    }


}
