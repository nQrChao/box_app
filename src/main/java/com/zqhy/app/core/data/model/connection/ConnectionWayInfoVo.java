package com.zqhy.app.core.data.model.connection;

import com.zqhy.app.core.data.model.BaseVo;

/**
 * @author pc
 * @date 2019/12/23-11:48
 * @description
 */
public class ConnectionWayInfoVo extends BaseVo {

    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public static class DataBean{

        /**
         * business_cooperation_email : 3304567145@qq.com
         * business_cooperation_mobile : +86-18602389115
         * business_cooperation_wechat_od : LF18602389115
         * open_platform_email : 3304567145@qq.com
         * open_platform_qq_number : 3304567145
         * open_platform_wechat_id : LF18602389115
         */

        private String business_cooperation_email;
        private String business_cooperation_mobile;
        private String business_cooperation_wechat_id;
        private String open_platform_email;
        private String open_platform_qq_number;
        private String open_platform_wechat_id;

        public String getBusiness_cooperation_email() {
            return business_cooperation_email;
        }

        public void setBusiness_cooperation_email(String business_cooperation_email) {
            this.business_cooperation_email = business_cooperation_email;
        }

        public String getBusiness_cooperation_mobile() {
            return business_cooperation_mobile;
        }

        public void setBusiness_cooperation_mobile(String business_cooperation_mobile) {
            this.business_cooperation_mobile = business_cooperation_mobile;
        }

        public String getBusiness_cooperation_wechat_id() {
            return business_cooperation_wechat_id;
        }

        public void setBusiness_cooperation_wechat_id(String business_cooperation_wechat_id) {
            this.business_cooperation_wechat_id = business_cooperation_wechat_id;
        }

        public String getOpen_platform_email() {
            return open_platform_email;
        }

        public void setOpen_platform_email(String open_platform_email) {
            this.open_platform_email = open_platform_email;
        }

        public String getOpen_platform_qq_number() {
            return open_platform_qq_number;
        }

        public void setOpen_platform_qq_number(String open_platform_qq_number) {
            this.open_platform_qq_number = open_platform_qq_number;
        }

        public String getOpen_platform_wechat_id() {
            return open_platform_wechat_id;
        }

        public void setOpen_platform_wechat_id(String open_platform_wechat_id) {
            this.open_platform_wechat_id = open_platform_wechat_id;
        }
    }
}
