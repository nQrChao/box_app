package com.zqhy.app.core.data.model.user;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/24
 */

public class CurrencyListVo extends BaseVo {


    List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean {

        /**
         * logtime : 1542091827
         * jiapingtaibi : -10.00
         * type : 4
         * order_total : 10.00
         * togamestatus : 10
         * remark : 游戏消费
         * content : 充值游戏《测试测试1》
         */

        private long logtime;
        private float jiapingtaibi;
        private int type;
        private float order_total;
        private int togamestatus;
        private String remark;
        private String content;

        public long getLogtime() {
            return logtime;
        }

        public float getJiapingtaibi() {
            return jiapingtaibi;
        }

        public int getType() {
            return type;
        }

        public float getOrder_total() {
            return order_total;
        }

        public int getTogamestatus() {
            return togamestatus;
        }

        public String getRemark() {
            return remark;
        }

        public String getContent() {
            return content;
        }


        public void setLogtime(long logtime) {
            this.logtime = logtime;
        }

        public void setJiapingtaibi(float jiapingtaibi) {
            this.jiapingtaibi = jiapingtaibi;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setOrder_total(float order_total) {
            this.order_total = order_total;
        }

        public void setTogamestatus(int togamestatus) {
            this.togamestatus = togamestatus;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
