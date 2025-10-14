package com.zqhy.app.core.data.model.chat;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;

/**
 * @author leeham2734
 * @date 2021/2/3-12:11
 * @description
 */
public class ChatTokenVo extends BaseVo {

    private DataBean data;

    public void setData(DataBean data) {
        this.data = data;
    }

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        private String accid;
        private String token;

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
    }
}
