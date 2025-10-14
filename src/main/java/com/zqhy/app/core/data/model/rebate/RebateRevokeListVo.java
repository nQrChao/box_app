package com.zqhy.app.core.data.model.rebate;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/16
 */

public class RebateRevokeListVo extends BaseVo{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean{
        /**
         * rmk_id : 1
         * rmk_content : 还要继续充值,申请撤回重新提交金额
         */

        private int rmk_id;
        private String rmk_content;

        public int getRmk_id() {
            return rmk_id;
        }

        public void setRmk_id(int rmk_id) {
            this.rmk_id = rmk_id;
        }

        public String getRmk_content() {
            return rmk_content;
        }

        public void setRmk_content(String rmk_content) {
            this.rmk_content = rmk_content;
        }
    }
}
