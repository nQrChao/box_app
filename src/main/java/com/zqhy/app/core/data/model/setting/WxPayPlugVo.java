package com.zqhy.app.core.data.model.setting;

import com.zqhy.app.core.data.model.BaseVo;

/**
 *
 * @author Administrator
 * @date 2018/11/30
 */

public class WxPayPlugVo extends BaseVo{

    private DataBean data;

    public DataBean getData() {
        return data;
    }
    public static class DataBean{

        /**
         * package_name : com.zqhy.asia.wxplugin
         * wx_plug_url : http://p1.jiuyao.yuzhuagame.com/android_tools/btgame_wechat_pay_112.apk
         * wx_plug_name : BTGame收银台
         * wx_plug_icon : http://p1.btgame01.com/wx/icon.png
         * version_code : 0
         */

        private String package_name;
        private String wx_plug_url;
        private String wx_plug_name;
        private String wx_plug_icon;
        private int version_code;

        public String getPackage_name() {
            return package_name;
        }

        public void setPackage_name(String package_name) {
            this.package_name = package_name;
        }

        public String getWx_plug_url() {
            return wx_plug_url;
        }

        public void setWx_plug_url(String wx_plug_url) {
            this.wx_plug_url = wx_plug_url;
        }

        public String getWx_plug_name() {
            return wx_plug_name;
        }

        public void setWx_plug_name(String wx_plug_name) {
            this.wx_plug_name = wx_plug_name;
        }

        public String getWx_plug_icon() {
            return wx_plug_icon;
        }

        public void setWx_plug_icon(String wx_plug_icon) {
            this.wx_plug_icon = wx_plug_icon;
        }

        public int getVersion_code() {
            return version_code;
        }

        public void setVersion_code(int version_code) {
            this.version_code = version_code;
        }
    }
}
