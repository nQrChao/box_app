package com.zqhy.app.core.data.model.user;

import com.zqhy.app.core.data.model.BaseVo;

/**
 * @author leeham
 * @date 2020/4/11-14:16
 * @description
 */
public class UserVoucherVo extends BaseVo {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean{
        /**
         * voucher_unused : 111
         * voucher_used : 111
         * voucher_past_due : 111
         */

        private int voucher_unused;
        private int voucher_used;
        private int voucher_past_due;

        public int getVoucher_unused() {
            return voucher_unused;
        }

        public void setVoucher_unused(int voucher_unused) {
            this.voucher_unused = voucher_unused;
        }

        public int getVoucher_used() {
            return voucher_used;
        }

        public void setVoucher_used(int voucher_used) {
            this.voucher_used = voucher_used;
        }

        public int getVoucher_past_due() {
            return voucher_past_due;
        }

        public void setVoucher_past_due(int voucher_past_due) {
            this.voucher_past_due = voucher_past_due;
        }
    }
}
