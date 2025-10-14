package com.zqhy.app.core.data.model;

import android.text.TextUtils;

import com.zqhy.app.core.data.model.splash.SplashVo;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/18
 */

public class InitDataVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        private List<Integer>                frame;
        private SplashVo.AppStyleVo.DataBean theme;
        /**
         * 0:常规, 1:半隐藏;
         */
        private int                          invite_type;

        /**
         * 是否隐藏社群板块（主要包括点评和问答）
         * １:隐藏, ０是正常显示
         */
        private int hide_community;

        public List<Integer> getFrame() {
            return frame;
        }

        public SplashVo.AppStyleVo.DataBean getTheme() {
            return theme;
        }

        public int getInvite_type() {
            return invite_type;
        }

        /**
         * 1=》打开微信控制微信支付，
         * 0=》隐藏
         */
        private int wx_control;

        public int getWx_control() {
            return wx_control;
        }

        /**
         * 微信支付插件包名
         */
        private List<String> wxPay_packagenames;

        public List<String> getWxPay_packagenames() {
            return wxPay_packagenames;
        }


        /**
         * 头条上报付费金额限制
         */
        private int toutiao_report_amount_limit;

        public int getToutiao_report_amount_limit() {
            return toutiao_report_amount_limit;
        }


        /**
         * 公会渠道使用热云统计
         */
        private List<String> reyun_gonghui_tgids;

        public List<String> getReyun_gonghui_tgids() {
            return reyun_gonghui_tgids;
        }

        private ToutiaoPlugVo toutiao_plug;

        public ToutiaoPlugVo getToutiao_plug() {
            return toutiao_plug;
        }

        public int getHide_community() {
            return hide_community;
        }

        /**
         * 2021.08.16
         * 新增自定义AppMenu
         */

        private AppMenuBeanVo menu;

        public AppMenuBeanVo getMenu() {
            return menu;
        }

        public ProfileSettingVo profile_setting;

        private int hide_five_figure;

        public int getHide_five_figure() {
            return hide_five_figure;
        }

        public void setHide_five_figure(int hide_five_figure) {
            this.hide_five_figure = hide_five_figure;
        }

        private int cloud_status;//云挂机显示开关 1开启

        public int getCloud_status() {
            return cloud_status;
        }

        public void setCloud_status(int cloud_status) {
            this.cloud_status = cloud_status;
        }
        private String pop_gameid;//是否跳转详情页并显示弹窗

        public String getPop_gameid() {
            if (!TextUtils.isEmpty(pop_gameid)){
                if (pop_gameid.contains(".")){
                    pop_gameid = pop_gameid.substring(0, pop_gameid.indexOf("."));
                }
                return pop_gameid;
            }
            return "0";
        }

        public void setPop_gameid(String pop_gameid) {
            this.pop_gameid = pop_gameid;
        }

        private int show_tip;

        public int getShow_tip() {
            return show_tip;
        }

        public void setShow_tip(int show_tip) {
            this.show_tip = show_tip;
        }
    }

    public static class ToutiaoPlugVo {
        private List<String> toutiao_tgids;

        public List<String> getToutiao_tgids() {
            return toutiao_tgids;
        }
    }

    public static class ProfileSettingVo {
        public int    web_switch;
        public String web_url;
    }
}
