package com.zqhy.app.core.data.model.refund;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/5/11-9:28
 * @description
 */
public class RefundOrderListVo extends BaseVo {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean {

        /**
         * id : 1027959
         * logtime : 1589015397
         * gameid : 1203
         * rmb_total : 8.00
         * refund_status : 0
         * orderid : 15890153971027959
         */

        /**
         * 订单标识,退款时发送到服务器端
         */
        private String id;
        /**
         * 订单时间,精确到秒的时间戳
         */
        private long   logtime;
        private String gameid;

        /**
         * 订单中消费的人民币总额
         */
        private float rmb_total;
        /**
         * 退款状态: 0:不可退; 1:可退款
         */
        private int refund_status;
        private String orderid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getLogtime() {
            return logtime;
        }

        public void setLogtime(long logtime) {
            this.logtime = logtime;
        }

        public String getGameid() {
            return gameid;
        }

        public void setGameid(String gameid) {
            this.gameid = gameid;
        }

        public float getRmb_total() {
            return rmb_total;
        }

        public void setRmb_total(float rmb_total) {
            this.rmb_total = rmb_total;
        }

        public int getRefund_status() {
            return refund_status;
        }

        public boolean isCanRefund(){
            return refund_status == 1;
        }

        public void setRefund_status(int refund_status) {
            this.refund_status = refund_status;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }
    }
}
