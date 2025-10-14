package com.zqhy.app.core.data.model.user;

import com.zqhy.app.core.data.model.BaseVo;

/**
 *
 * @author Administrator
 * @date 2018/11/12
 */

public class BindPhoneTempVo extends BaseVo{

    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private String mob;

        public String getMob() {
            return mob;
        }

        public void setMob(String mob) {
            this.mob = mob;
        }
    }
}
