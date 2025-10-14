package com.zqhy.app.core.data.model.launch;

import com.zqhy.app.core.data.model.BaseVo;


public class StasInfo extends BaseVo {

    private Status data;

    public Status getData() {
        return data;
    }

    public void setData(Status data) {
        this.data = data;
    }

    public static class Status {
        private int status;
        private String newtgid;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getNewtgid() {
            return newtgid;
        }

        public void setNewtgid(String newtgid) {
            this.newtgid = newtgid;
        }
    }
}
