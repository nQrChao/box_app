package com.zqhy.app.core.data.model.refund;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/5/11-17:02
 * @description
 */
public class RefundRecordListVo extends BaseVo {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean{

        /**
         * logtime : 1589186871
         * gameid : 1696
         * total : 36.00
         * gamename : 天天怼三国（送抽版）
         */

        private long logtime;
        private int gameid;
        private String total;
        private String gamename;

        public long getLogtime() {
            return logtime;
        }

        public void setLogtime(long logtime) {
            this.logtime = logtime;
        }

        public int getGameid() {
            return gameid;
        }

        public void setGameid(int gameid) {
            this.gameid = gameid;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getGamename() {
            return gamename;
        }

        public void setGamename(String gamename) {
            this.gamename = gamename;
        }
    }


}
