package com.zqhy.app.core.data.model.user;

import com.zqhy.app.core.data.model.BaseVo;

/**
 * @author leeham
 * @date 2020/3/30-12:52
 * @description
 */
public class VipMemberInfoVo extends BaseVo {
    public DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        private int vip_member_total_count;
        private int is_get_vip_member_voucher;

        public int getVip_member_total_count() {
            return vip_member_total_count;
        }

        public boolean is_get_vip_member_voucher() {
            return is_get_vip_member_voucher == 1;
        }
    }
}
