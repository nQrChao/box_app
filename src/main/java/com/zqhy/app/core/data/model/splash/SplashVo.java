package com.zqhy.app.core.data.model.splash;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.InitDataVo;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;
import com.zqhy.app.core.data.model.user.UserInfoVo;

/**
 * @author Administrator
 * @date 2018/11/9
 */

public class SplashVo {

    private UserInfoVo authLogin;

    private InitDataVo appInit;

    private SplashBeanVo splashBeanVo;

    private AppStyleVo appStyleVo;

    public static class SplashBeanVo extends BaseVo {

        private DataBean data;

        public static class DataBean {
            private String pic;

            private String type;
            private String page_type;

            private AppJumpInfoBean.ParamBean param;

            public String getPic() {
                return pic;
            }

            public String getType() {
                return type;
            }

            public String getPage_type() {
                return page_type;
            }

            public AppJumpInfoBean.ParamBean getParam() {
                return param;
            }
        }

        public DataBean getData() {
            return data;
        }
    }

    public static class AppStyleVo extends BaseVo {

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public static class DataBean {

            private HeaderInfo app_header_info;

            private AppJumpInfoBean interstitial;

            private BottomInfo app_bottom_info;

            private SimpleInfo game_button_color;

            private SimpleInfo container_icon;

            public HeaderInfo getApp_header_info() {
                return app_header_info;
            }

            public AppJumpInfoBean getInterstitial() {
                return interstitial;
            }

            public BottomInfo getApp_bottom_info() {
                return app_bottom_info;
            }

            public SimpleInfo getGame_button_color() {
                return game_button_color;
            }

            public SimpleInfo getContainer_icon() {
                return container_icon;
            }

            public void setApp_header_info(HeaderInfo app_header_info) {
                this.app_header_info = app_header_info;
            }

            public void setApp_bottom_info(BottomInfo app_bottom_info) {
                this.app_bottom_info = app_bottom_info;
            }
        }

        public static class HeaderInfo {

            /**
             * default_color : #25ff58
             * header_bg : http://p1.btgame01.com/2018/01/29/5a6e8d905d41e.png
             * selected_color : #ff0000
             * header_bg_height : 176
             * header_bg_width : 720
             */

            private String default_color;
            private String header_bg;
            private String selected_color;
            private String header_bg_height;
            private String header_bg_width;

            private String url;

            public String getDefault_color() {
                return default_color;
            }

            public void setDefault_color(String default_color) {
                this.default_color = default_color;
            }

            public String getHeader_bg() {
                return header_bg;
            }

            public void setHeader_bg(String header_bg) {
                this.header_bg = header_bg;
            }

            public String getSelected_color() {
                return selected_color;
            }

            public void setSelected_color(String selected_color) {
                this.selected_color = selected_color;
            }

            public String getHeader_bg_height() {
                return header_bg_height;
            }

            public void setHeader_bg_height(String header_bg_height) {
                this.header_bg_height = header_bg_height;
            }

            public String getHeader_bg_width() {
                return header_bg_width;
            }

            public void setHeader_bg_width(String header_bg_width) {
                this.header_bg_width = header_bg_width;
            }

            public String getUrl() {
                return url;
            }
        }

        public static class BottomInfo {

            /**
             * button_default_color : #cccccc
             * index_button_default_icon : http://p1.btgame01.com/2018/01/29/5a6e910a81f62.png
             * index_button_selecte_icon : http://p1.btgame01.com/2018/01/29/5a6e910a822cc.png
             * server_button_default_icon : http://p1.btgame01.com/2018/01/29/5a6e910a825e4.png
             * server_button_selected_icon : http://p1.btgame01.com/2018/01/29/5a6e910a828f2.png
             * message_button_default_icon : http://p1.btgame01.com/2018/01/29/5a6e910a82c00.png
             * message_button_selected_icon : http://p1.btgame01.com/2018/01/29/5a6e910a82f1e.png
             * mine_button_default_icon : http://p1.btgame01.com/2018/01/29/5a6e910a83233.png
             * button_selected_color : #cccccc
             * mine_button_selected_icon : http://p1.btgame01.com/2018/01/29/5a6e910a83544.png
             * center_button_icon : http://p1.btgame01.com/2018/01/29/5a6e910a83848.png
             */


            private String index_button_default_icon;
            private String index_button_selected_icon;

            private String server_button_default_icon;
            private String server_button_selected_icon;

            private String type_button_default_icon;
            private String type_button_selected_icon;

            private String service_button_default_icon;
            private String service_button_selected_icon;

            private String button_default_color;
            private String button_selected_color;

            private String center_button_icon;

            public String getButton_default_color() {
                return button_default_color;
            }

            public String getIndex_button_default_icon() {
                return index_button_default_icon;
            }

            public String getIndex_button_selected_icon() {
                return index_button_selected_icon;
            }

            public String getServer_button_default_icon() {
                return server_button_default_icon;
            }

            public String getServer_button_selected_icon() {
                return server_button_selected_icon;
            }

            public String getType_button_default_icon() {
                return type_button_default_icon;
            }

            public String getType_button_selected_icon() {
                return type_button_selected_icon;
            }

            public String getService_button_default_icon() {
                return service_button_default_icon;
            }

            public String getService_button_selected_icon() {
                return service_button_selected_icon;
            }

            public String getButton_selected_color() {
                return button_selected_color;
            }

            public String getCenter_button_icon() {
                return center_button_icon;
            }


            public void setIndex_button_default_icon(String index_button_default_icon) {
                this.index_button_default_icon = index_button_default_icon;
            }

            public void setIndex_button_selected_icon(String index_button_selected_icon) {
                this.index_button_selected_icon = index_button_selected_icon;
            }

            public void setServer_button_default_icon(String server_button_default_icon) {
                this.server_button_default_icon = server_button_default_icon;
            }

            public void setServer_button_selected_icon(String server_button_selected_icon) {
                this.server_button_selected_icon = server_button_selected_icon;
            }

            public void setType_button_default_icon(String type_button_default_icon) {
                this.type_button_default_icon = type_button_default_icon;
            }

            public void setType_button_selected_icon(String type_button_selected_icon) {
                this.type_button_selected_icon = type_button_selected_icon;
            }

            public void setService_button_default_icon(String service_button_default_icon) {
                this.service_button_default_icon = service_button_default_icon;
            }

            public void setService_button_selected_icon(String service_button_selected_icon) {
                this.service_button_selected_icon = service_button_selected_icon;
            }

            public void setButton_default_color(String button_default_color) {
                this.button_default_color = button_default_color;
            }

            public void setButton_selected_color(String button_selected_color) {
                this.button_selected_color = button_selected_color;
            }

            public void setCenter_button_icon(String center_button_icon) {
                this.center_button_icon = center_button_icon;
            }
        }

        public static class SimpleInfo {
            private String btn_color;
            private String container_icon;

            public String getColor() {
                return btn_color;
            }

            public void setColor(String color) {
                this.btn_color = color;
            }

            public String getIcon() {
                return container_icon;
            }

            public void setIcon(String icon) {
                this.container_icon = icon;
            }
        }
    }


    public UserInfoVo getAuthLogin() {
        return authLogin;
    }

    public void setAuthLogin(UserInfoVo authLogin) {
        this.authLogin = authLogin;
    }

    public InitDataVo getAppInit() {
        return appInit;
    }

    public void setAppInit(InitDataVo appInit) {
        this.appInit = appInit;
    }

    public SplashBeanVo getSplashBeanVo() {
        return splashBeanVo;
    }

    public void setSplashBeanVo(SplashBeanVo splashBeanVo) {
        this.splashBeanVo = splashBeanVo;
    }

    public AppStyleVo getAppStyleVo() {
        return appStyleVo;
    }

    public void setAppStyleVo(AppStyleVo appStyleVo) {
        this.appStyleVo = appStyleVo;
    }
}
