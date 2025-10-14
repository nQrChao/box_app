package com.zqhy.app.core.data.model.easy_play;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author pc
 * @date 2019/12/23-11:48
 * @description
 */
public class RefundDetailVo extends BaseVo {

    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public class XHListData{
        String id;
        String pid;
        String xh_username;
        String xh_showname;
        String total;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getXh_username() {
            return xh_username;
        }

        public void setXh_username(String xh_username) {
            this.xh_username = xh_username;
        }

        public String getXh_showname() {
            return xh_showname;
        }

        public void setXh_showname(String xh_showname) {
            this.xh_showname = xh_showname;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }
    }
    public class DataBean{
        /**
         "gameid": "11524",
         "status": 1,
         "total": "65.80",
         "xh_showname": "小号1",
         "rate": "50",
         "amount": "32.90",
         "first_time": "1722933105",
         "valid_time": 1722940305,
         "last_time": 1723458705
         */
        String gameid;
        String total;
        String xh_showname;
        String rate;
        String amount;
        String first_time;
        long valid_time;
        long last_time;
        int status;
        List<XHListData> xh_list;

        public List<XHListData> getXh_list() {
            return xh_list;
        }

        public void setXh_list(List<XHListData> xh_list) {
            this.xh_list = xh_list;
        }

        public String getGameid() {
            return gameid;
        }

        public void setGameid(String gameid) {
            this.gameid = gameid;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getXh_showname() {
            return xh_showname;
        }

        public void setXh_showname(String xh_showname) {
            this.xh_showname = xh_showname;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getFirst_time() {
            return first_time;
        }

        public void setFirst_time(String first_time) {
            this.first_time = first_time;
        }

        public long getValid_time() {
            return valid_time;
        }

        public void setValid_time(long valid_time) {
            this.valid_time = valid_time;
        }

        public long getLast_time() {
            return last_time;
        }

        public void setLast_time(long last_time) {
            this.last_time = last_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
