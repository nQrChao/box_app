package com.zqhy.app.core.data.model.version;

import com.zqhy.app.core.data.model.BaseVo;

/**
 * @author Administrator
 */
public class UnionVipDataVo extends BaseVo {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        private String text;//Vip客服QQ号
        private long utime;//最后更新时间
        private long ntime;//当前服务器时间
        private String msg;//提示信息

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public long getUtime() {
            return utime;
        }

        public void setUtime(long utime) {
            this.utime = utime;
        }

        public long getNtime() {
            return ntime;
        }

        public void setNtime(long ntime) {
            this.ntime = ntime;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}