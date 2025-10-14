package com.zqhy.app.core.data.model.chat;

import com.zqhy.app.core.data.model.BaseVo;

/**
 *
 * @author Administrator
 * @date 2018/11/9
 */

public class AddChatVo extends BaseVo{
    Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        private String accid;
        private String token;
        private long tid;

        public String getAccid() {
            return accid;
        }

        public void setAccid(String accid) {
            this.accid = accid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public long getTid() {
            return tid;
        }

        public void setTid(long tid) {
            this.tid = tid;
        }
    }

}
