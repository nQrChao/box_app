package com.zqhy.app.core.data.model.transaction;

import com.zqhy.app.core.data.model.BaseVo;

/**
 * @author Administrator
 */
public class TradeXhInfoVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean{
        private String servername;

        public String getServername() {
            return servername;
        }

        public void setServername(String servername) {
            this.servername = servername;
        }
    }
}
