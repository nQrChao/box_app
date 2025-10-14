package com.zqhy.app.core.data.model.welfare;

import android.text.TextUtils;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.utils.CommonUtils;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/15
 */

public class MyCouponsListVo extends BaseVo{

    public final static int mTabUnUsed = 0;
    public final static int mTabUsed = 1;
    public final static int mTabPastDue = 2;

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean{

        /**
         * gameid : 30
         * game_type : 2
         * amount : 2
         * use_cdt : 满10元可用
         * expiry : 11-23 17:24过期
         * coupon_name : 《大话萌仙7》2元代金券
         * range : 大话萌仙7专用
         */

        private int gameid;
        private int game_type;
        private float amount;
        private String use_cdt;
        private String expiry;
        private String coupon_name;
        private String range;
        private String xh_showname;
        private String xh_username;
        private String expiry_soon;
        private String badge;
        private int sign;
        private String used_time;
        private String used_game_name;
        private QueryInfo query_info;

        public QueryInfo getQuery_info() {
            return query_info;
        }

        public void setQuery_info(QueryInfo query_info) {
            this.query_info = query_info;
        }

        public String getUsed_game_name() {
            return CommonUtils.getGamename(used_game_name);
        }

        public String getOtherUsed_game_name() {
            return CommonUtils.getOtherGameName(used_game_name);
        }

        public void setUsed_game_name(String used_game_name) {
            this.used_game_name = used_game_name;
        }

        public String getUsed_time() {
            return used_time;
        }

        public void setUsed_time(String used_time) {
            this.used_time = used_time;
        }

        public String getXh_showname() {
            return xh_showname;
        }

        public void setXh_showname(String xh_showname) {
            this.xh_showname = xh_showname;
        }

        public int getSign() {
            return sign;
        }

        public void setSign(int sign) {
            this.sign = sign;
        }

        public String getXh_username() {
            return xh_username;
        }

        public void setXh_username(String xh_username) {
            this.xh_username = xh_username;
        }

        public String getExpiry_soon() {
            return expiry_soon;
        }

        public void setExpiry_soon(String expiry_soon) {
            this.expiry_soon = expiry_soon;
        }

        public String getBadge() {
            return badge;
        }

        public void setBadge(String badge) {
            this.badge = badge;
        }

        public int getGameid() {
            return gameid;
        }

        public void setGameid(int gameid) {
            this.gameid = gameid;
        }

        public int getGame_type() {
            return game_type;
        }

        public void setGame_type(int game_type) {
            this.game_type = game_type;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }

        public String getUse_cdt() {
            return use_cdt;
        }

        public void setUse_cdt(String use_cdt) {
            this.use_cdt = use_cdt;
        }

        public String getExpiry() {
            return expiry;
        }

        public void setExpiry(String expiry) {
            this.expiry = expiry;
        }

        public String getCoupon_name() {
            return coupon_name;
        }

        public void setCoupon_name(String coupon_name) {
            this.coupon_name = coupon_name;
        }

        public String getRange() {
            return CommonUtils.getGamename(range);
        }

        public String getOtherRange() {
            return CommonUtils.getOtherGameName(range);
        }

        public void setRange(String range) {
            this.range = range;
        }

        private int status;

        public int getStatus() {
            return status;
        }
        public void setStatus(int status){
            this.status = status;
        }
    }

    public static class QueryInfo{
        private String url;
        private String label;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
