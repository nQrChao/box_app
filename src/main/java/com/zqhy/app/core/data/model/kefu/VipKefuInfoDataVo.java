package com.zqhy.app.core.data.model.kefu;

import com.zqhy.app.core.data.model.BaseVo;

/**
 * @author Administrator
 */
public class VipKefuInfoDataVo extends BaseVo {

    public static final int KEFU_LEVEL_NOT_VIP = 0;
    public static final int KEFU_LEVEL_VIP_NOT_COMMIT = 1;
    public static final int KEFU_LEVEL_VIP_COMMITTED = 2;

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {

        /**
         * level : 2
         * vip_qq : 2852797725
         * vip_qq_url : http://q.url.cn/abB9S9?_type=wpa&qidian=true
         */

        /**
         * 0 : 不是Vip
         * 1 : 是Vip,但没有提交Vip客服申请
         * 2 : 已提交Vip客服申请
         */
        private int level;
        private String vip_qq;
        private String vip_qq_url;
        private int is_nt_finished;

        public int getLevel() {
            return level;
        }

        public String getVip_qq() {
            return String.valueOf(vip_qq);
        }

        public String getVip_qq_url() {
            return vip_qq_url;
        }

        public boolean isAllTaskFinished() {
            return is_nt_finished == 1;
        }

        public boolean isShowVipKefu(){
            return level == 2;
        }
    }
}
