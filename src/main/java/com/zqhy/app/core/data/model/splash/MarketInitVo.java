package com.zqhy.app.core.data.model.splash;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/12/19
 */

public class MarketInitVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        private int          status;
        private List<String> index_module;

        private int allow_comment;
        private int allow_download;
        private int allow_trading;

        private List<String> batu_index_module;
        private List<String> jiuyao_index_module;

        /**
         * 0  取index_module的值
         * 1  取batu_index_module的值
         * 2  取jiuyao_index_module的值
         */
        private int index_module_type = 1;

        private int    plug_status;
        private String android_down_url;
        private String pop_pic;
        private String pop_text;

        private int is_redirect;//是否需要跳转url
        private String redirect_url;//url地址

        public int getIs_redirect() {
            return is_redirect;
        }

        public void setIs_redirect(int is_redirect) {
            this.is_redirect = is_redirect;
        }

        public String getRedirect_url() {
            return redirect_url;
        }

        public void setRedirect_url(String redirect_url) {
            this.redirect_url = redirect_url;
        }

        public int getStatus() {
            return status;
        }

        public List<String> getIndex_module() {
            return index_module;
        }

        public List<String> getBatu_index_module() {
            return batu_index_module;
        }

        public List<String> getJiuyao_index_module() {
            return jiuyao_index_module;
        }

        public int getAllow_comment() {
            return allow_comment;
        }

        public int getAllow_download() {
            return allow_download;
        }

        public int getAllow_trading() {
            return allow_trading;
        }

        public int getIndex_module_type() {
            return index_module_type;
        }

        public int getPlug_status() {
            return plug_status;
        }

        public String getAndroid_download_url() {
            return android_down_url;
        }

        public String getPop_pic() {
            return pop_pic;
        }

        public String getPop_text() {
            return pop_text;
        }


        public int center_module;
        public int center_show;
        public int trading_module;
        public String index_hello;
        public String appurl;
        public String downpic;
        public int need_sfz;

        private String view_pic;

        public String getView_pic() {
            return view_pic;
        }

        public void setView_pic(String view_pic) {
            this.view_pic = view_pic;
        }
    }
}
