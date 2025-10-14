package com.zqhy.app.core.data.model.rebate;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/22
 */

public class RebateServerListVo extends BaseVo{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean{

        String gamename;
        /**
         * servername : 一区
         * role_name : 叼炸天
         * role_id : 3360
         */

        private String servername;
        private String role_name;
        private String role_id;

        public void setGamename(String gamename) {
            this.gamename = gamename;
        }

        public String getGamename() {
            return gamename;
        }

        public String getServername() {
            return servername;
        }

        public void setServername(String servername) {
            this.servername = servername;
        }

        public String getRole_name() {
            return role_name;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
        }

        public String getRole_id() {
            return role_id;
        }

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }
    }
}
