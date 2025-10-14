package com.zqhy.app.core.data.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author leeham2734
 * @date 2021/5/13-17:06
 * @description
 */
public class ReportSdkDataBeanVo {

    private String   msg;
    private String   state;
    private String   event;
    private DataBean data;


    public static ReportSdkDataBeanVo parse(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            ReportSdkDataBeanVo beanVo = new ReportSdkDataBeanVo();

            beanVo.setState(jsonObject.optString("state"));
            beanVo.setMsg(jsonObject.optString("msg"));
            beanVo.setEvent(jsonObject.optString("event"));
            beanVo.setData(DataBean.parse(jsonObject.optJSONObject("data")));

            return beanVo;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public static class DataBean {
        public String uid;
        public String username;
        public String token;
        public String tgid;

        public int     action_type;
        public PayInfo pay_info;

        public String sdk_action;
        public String sdk_tag;
        public String sdk_tgid;
        public String sdk_account_id;
        public String sdk_username;


        public static DataBean parse(JSONObject jsonObject) {
            if (jsonObject == null) {
                return null;
            }
            DataBean dataBean = new DataBean();
            dataBean.action_type = jsonObject.optInt("action_type", 0);

            dataBean.uid = jsonObject.optString("uid", null);
            dataBean.username = jsonObject.optString("username", null);
            dataBean.token = jsonObject.optString("token", null);
            dataBean.tgid = jsonObject.optString("tgid", null);

            dataBean.pay_info = PayInfo.parse(jsonObject.optJSONObject("pay_info"));
            return dataBean;
        }


        @Override
        public String toString() {
            return "DataBean{" +
                    "uid='" + uid + '\'' +
                    ", username='" + username + '\'' +
                    ", token='" + token + '\'' +
                    ", tgid='" + tgid + '\'' +
                    ", action_type=" + action_type +
                    ", pay_info=" + pay_info +
                    ", sdk_action='" + sdk_action + '\'' +
                    ", sdk_tag='" + sdk_tag + '\'' +
                    ", sdk_tgid='" + sdk_tgid + '\'' +
                    ", sdk_account_id='" + sdk_account_id + '\'' +
                    ", sdk_username='" + sdk_username + '\'' +
                    '}';
        }
    }

    public static class PayInfo {
        private String pay_way;
        private String purchase_order_id;
        private String purchase_order_amount;
        private int    limit_amount;

        public PayInfo() {
        }

        public PayInfo(String pay_way, String purchase_order_id, String purchase_order_amount, int limit_amount) {
            this.pay_way = pay_way;
            this.purchase_order_id = purchase_order_id;
            this.purchase_order_amount = purchase_order_amount;
            this.limit_amount = limit_amount;
        }

        public static PayInfo parse(JSONObject jsonObject) {
            if (jsonObject == null) {
                return null;
            }
            PayInfo payInfo = new PayInfo();

            payInfo.setPay_way(jsonObject.optString("pay_way", null));
            payInfo.setPurchase_order_id(jsonObject.optString("purchase_order_id", null));
            payInfo.setPurchase_order_amount(jsonObject.optString("purchase_order_amount", null));
            payInfo.setLimit_amount(jsonObject.optInt("limit_amount", 0));

            return payInfo;
        }

        public String getPay_way() {
            return pay_way;
        }

        public void setPay_way(String pay_way) {
            this.pay_way = pay_way;
        }

        public String getPurchase_order_id() {
            return purchase_order_id;
        }

        public void setPurchase_order_id(String purchase_order_id) {
            this.purchase_order_id = purchase_order_id;
        }

        public String getPurchase_order_amount() {
            return purchase_order_amount;
        }

        public void setPurchase_order_amount(String purchase_order_amount) {
            this.purchase_order_amount = purchase_order_amount;
        }

        public int getLimit_amount() {
            return limit_amount;
        }

        public void setLimit_amount(int limit_amount) {
            this.limit_amount = limit_amount;
        }

        @Override
        public String toString() {
            return "PayInfo{" +
                    "pay_way='" + pay_way + '\'' +
                    ", purchase_order_id='" + purchase_order_id + '\'' +
                    ", purchase_order_amount='" + purchase_order_amount + '\'' +
                    ", limit_amount=" + limit_amount +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "ReportSdkDataBeanVo{" +
                "msg='" + msg + '\'' +
                ", state='" + state + '\'' +
                ", event='" + event + '\'' +
                ", data=" + data +
                '}';
    }
}
