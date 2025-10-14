package com.zqhy.app.core.data.model.transaction;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 */
public class GameXhInfoVo extends BaseVo {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean{
        /**
         * uid : 6181571
         * username : xh_1505885814hio
         * paytotal : 8
         */

        private int id;
        private String uid;
        private String xh_showname;
        private float paytotal;
        private String username;

        private float total;
        private String xh_username;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getXh_showname() {
            return xh_showname;
        }

        public void setXh_showname(String xh_showname) {
            this.xh_showname = xh_showname;
        }

        public float getPaytotal() {
            return paytotal;
        }

        public void setPaytotal(float paytotal) {
            this.paytotal = paytotal;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public boolean isEnableRecycle() {
            return paytotal != 0;
        }

        public float getTotal() {
            return total;
        }

        public void setTotal(float total) {
            this.total = total;
        }

        public String getXh_username() {
            return xh_username;
        }

        public void setXh_username(String xh_username) {
            this.xh_username = xh_username;
        }

    }
}
