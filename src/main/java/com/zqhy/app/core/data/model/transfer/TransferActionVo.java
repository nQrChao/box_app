package com.zqhy.app.core.data.model.transfer;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/17
 */

public class TransferActionVo extends BaseVo{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        private String reward_provide;
        private List<XhAccount> xh_list;

        public String getReward_provide() {
            return reward_provide;
        }

        public List<XhAccount> getXh_list() {
            return xh_list;
        }

    }

    public static class XhAccount {


        String uid;
        String xh_showname;
        /**
         * gameid : 336
         * logintime : 1543408782
         * xh_uid : 122000000011
         * xh_username : x_1543408181UB22
         */

        private String gameid;
        private String logintime;
        private String xh_uid;
        private String xh_username;

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

        public String getGameid() {
            return gameid;
        }

        public void setGameid(String gameid) {
            this.gameid = gameid;
        }

        public String getLogintime() {
            return logintime;
        }

        public void setLogintime(String logintime) {
            this.logintime = logintime;
        }

        public String getXh_uid() {
            return xh_uid;
        }

        public void setXh_uid(String xh_uid) {
            this.xh_uid = xh_uid;
        }

        public String getXh_username() {
            return xh_username;
        }

        public void setXh_username(String xh_username) {
            this.xh_username = xh_username;
        }
    }
}
