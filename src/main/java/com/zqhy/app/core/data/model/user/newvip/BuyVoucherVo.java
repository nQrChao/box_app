package com.zqhy.app.core.data.model.user.newvip;

import com.zqhy.app.core.data.model.BaseVo;

/**
 *
 * @author Administrator
 * @date 2018/11/21
 */

public class BuyVoucherVo extends BaseVo{

    private BuyVoucherVo.DataBean data;

    public BuyVoucherVo.DataBean getData() {
        return data;
    }

    public static class DataBean {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
