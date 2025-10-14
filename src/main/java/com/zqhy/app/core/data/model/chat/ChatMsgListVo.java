package com.zqhy.app.core.data.model.chat;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/9
 */

public class ChatMsgListVo extends BaseVo {
    List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public class DataBean {

        private String msg;
        private String accid;
        private String icon;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getAccid() {
            return accid;
        }

        public void setAccid(String accid) {
            this.accid = accid;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
