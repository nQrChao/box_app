package com.zqhy.app.core.data.model.kefu;

import com.zqhy.app.core.data.model.BaseVo;

/**
 * @author Administrator
 * @date 2018/11/22
 */

public class KefuInfoDataVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        /**
         * jy_kf : {"qq_num":"800185872","is_yinxiao":1,"yinxiao_url":"http://wpa.b.qq.com/cgi/wpa.php?ln=1&key=XzgwMDE4NTg3Ml80Nzk1MDlfODAwMTg1ODcyXzJf","qq_qun":"607100636","qq_qun_key":"2SZq5ZtgaZWmBtGad6L7qSuntIkyXgul"}
         * h5_kf : {"qq_num":"2088157725","is_yinxiao":0,"yinxiao_url":"","qq_qun":"253720580","qq_qun_key":"-C5F1MHX3MZ6a5MS2qBBkCqekFlgh3p3"}
         * ts_email : tousu@lehihi.cn
         * kefu_phone : 027-88108655
         */

        private JyKfBean jy_kf;
        private H5KfBean h5_kf;
        private String   ts_email;
        private String   kefu_phone;
        private String kf_time;

        public String getKf_time() {
            return kf_time;
        }

        public void setKf_time(String kf_time) {
            this.kf_time = kf_time;
        }

        public JyKfBean getJy_kf() {
            return jy_kf;
        }

        public H5KfBean getH5_kf() {
            return h5_kf;
        }

        public String getTs_email() {
            return ts_email;
        }

        public String getKefu_phone() {
            return kefu_phone;
        }

        public static class JyKfBean {
            /**
             * qq_num : 800185872
             * is_yinxiao : 1
             * yinxiao_url : http://wpa.b.qq.com/cgi/wpa.php?ln=1&key=XzgwMDE4NTg3Ml80Nzk1MDlfODAwMTg1ODcyXzJf
             * qq_qun : 607100636
             * qq_qun_key : 2SZq5ZtgaZWmBtGad6L7qSuntIkyXgul
             */

            private String qq_num;
            private int    is_yinxiao;
            private String yinxiao_url;
            private String qq_qun;
            private String qq_qun_key;
            private int    yinxiao_jump_type;
            private String wechat_id;
            private String wechat_gzh;
            private String wechat_url;

            public String getWechat_url() {
                return wechat_url;
            }

            public void setWechat_url(String wechat_url) {
                this.wechat_url = wechat_url;
            }

            public String getQq_num() {
                return qq_num;
            }

            public void setQq_num(String qq_num) {
                this.qq_num = qq_num;
            }

            public int getIs_yinxiao() {
                return is_yinxiao;
            }

            public void setIs_yinxiao(int is_yinxiao) {
                this.is_yinxiao = is_yinxiao;
            }

            public String getYinxiao_url() {
                return yinxiao_url;
            }

            public int getYinxiao_jump_type() {
                return yinxiao_jump_type;
            }

            public void setYinxiao_url(String yinxiao_url) {
                this.yinxiao_url = yinxiao_url;
            }

            public String getQq_qun() {
                return qq_qun;
            }

            public void setQq_qun(String qq_qun) {
                this.qq_qun = qq_qun;
            }

            public String getQq_qun_key() {
                return qq_qun_key;
            }

            public void setQq_qun_key(String qq_qun_key) {
                this.qq_qun_key = qq_qun_key;
            }

            public String getWechat_id() {
                return wechat_id;
            }

            public void setWechat_id(String wechat_id) {
                this.wechat_id = wechat_id;
            }

            public String getWechat_gzh() {
                return wechat_gzh;
            }
        }

        public static class H5KfBean {
            /**
             * qq_num : 2088157725
             * is_yinxiao : 0
             * yinxiao_url :
             * qq_qun : 253720580
             * qq_qun_key : -C5F1MHX3MZ6a5MS2qBBkCqekFlgh3p3
             */

            private String qq_num;
            private int    is_yinxiao;
            private String yinxiao_url;
            private String qq_qun;
            private String qq_qun_key;

            public String getQq_num() {
                return qq_num;
            }

            public void setQq_num(String qq_num) {
                this.qq_num = qq_num;
            }

            public int getIs_yinxiao() {
                return is_yinxiao;
            }

            public void setIs_yinxiao(int is_yinxiao) {
                this.is_yinxiao = is_yinxiao;
            }

            public String getYinxiao_url() {
                return yinxiao_url;
            }

            public void setYinxiao_url(String yinxiao_url) {
                this.yinxiao_url = yinxiao_url;
            }

            public String getQq_qun() {
                return qq_qun;
            }

            public void setQq_qun(String qq_qun) {
                this.qq_qun = qq_qun;
            }

            public String getQq_qun_key() {
                return qq_qun_key;
            }

            public void setQq_qun_key(String qq_qun_key) {
                this.qq_qun_key = qq_qun_key;
            }
        }
    }
}
