package com.zqhy.app.core.data.model.user.newvip;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.user.SuperVipMemberInfoVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2021/2/3-12:11
 * @description
 */
public class SuperUserInfoVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        private SuperVipMemberInfoVo.DataBean.MemberRewardBanner ad_banner;
        private String show_vip_ios;//是否显示ios尊享版区块
        private int super_user_count;//多少人开通了会员
        private long start_time;//会员有效期开始时间
        private long expiry_time;//如果大于0，则同时表明当前用户在会员有效期内
        private int week;//当天周几，数字
        private String week_label;//当天周几，中文
        private long next_cycle_time;//下次更新会员礼包时间，秒级时间戳
        private String sign_reward;//今日签到获取的奖励
        private int month_price;//多少人购买
        private List<AllSignRewardVo> all_sign_reward_list;
        private List<CouponVo> coupon_list;
        private List<DayRewardVo> day_reward_list;
        private List<CardTypeVo> card_type_list;
        private String coupon_block_label;//优惠券右上方说明
        private String buy_goods_description;//会员神券标题右侧描述
        private List<VoucherVo> buy_goods_list;//会员神券列表

        public String getBuy_goods_description() {
            return buy_goods_description;
        }

        public void setBuy_goods_description(String buy_goods_description) {
            this.buy_goods_description = buy_goods_description;
        }

        public List<VoucherVo> getBuy_goods_list() {
            return buy_goods_list;
        }

        public void setBuy_goods_list(List<VoucherVo> buy_goods_list) {
            this.buy_goods_list = buy_goods_list;
        }

        public String getCoupon_block_label() {
            return coupon_block_label;
        }

        public void setCoupon_block_label(String coupon_block_label) {
            this.coupon_block_label = coupon_block_label;
        }

        public String getShow_vip_ios() {
            return show_vip_ios;
        }

        public void setShow_vip_ios(String show_vip_ios) {
            this.show_vip_ios = show_vip_ios;
        }

        public SuperVipMemberInfoVo.DataBean.MemberRewardBanner getAd_banner() {
            return ad_banner;
        }

        public void setAd_banner(SuperVipMemberInfoVo.DataBean.MemberRewardBanner ad_banner) {
            this.ad_banner = ad_banner;
        }

        public int getSuper_user_count() {
            return super_user_count;
        }

        public void setSuper_user_count(int super_user_count) {
            this.super_user_count = super_user_count;
        }

        public long getStart_time() {
            return start_time;
        }

        public void setStart_time(long start_time) {
            this.start_time = start_time;
        }

        public int getWeek() {
            return week;
        }

        public void setWeek(int week) {
            this.week = week;
        }

        public String getWeek_label() {
            return week_label;
        }

        public void setWeek_label(String week_label) {
            this.week_label = week_label;
        }

        public long getNext_cycle_time() {
            return next_cycle_time;
        }

        public void setNext_cycle_time(long next_cycle_time) {
            this.next_cycle_time = next_cycle_time;
        }

        public long getExpiry_time() {
            return expiry_time;
        }

        public void setExpiry_time(long expiry_time) {
            this.expiry_time = expiry_time;
        }

        public String getSign_reward() {
            return sign_reward;
        }

        public void setSign_reward(String sign_reward) {
            this.sign_reward = sign_reward;
        }

        public int getMonth_price() {
            return month_price;
        }

        public void setMonth_price(int month_price) {
            this.month_price = month_price;
        }

        public List<AllSignRewardVo> getAll_sign_reward_list() {
            return all_sign_reward_list;
        }

        public void setAll_sign_reward_list(List<AllSignRewardVo> all_sign_reward_list) {
            this.all_sign_reward_list = all_sign_reward_list;
        }

        public List<CouponVo> getCoupon_list() {
            return coupon_list;
        }

        public void setCoupon_list(List<CouponVo> coupon_list) {
            this.coupon_list = coupon_list;
        }

        public List<DayRewardVo> getDay_reward_list() {
            return day_reward_list;
        }

        public void setDay_reward_list(List<DayRewardVo> day_reward_list) {
            this.day_reward_list = day_reward_list;
        }

        public List<CardTypeVo> getCard_type_list() {
            return card_type_list;
        }

        public void setCard_type_list(List<CardTypeVo> card_type_list) {
            this.card_type_list = card_type_list;
        }
    }

    public static class AllSignRewardVo{
        private int uid;
        private String title;//奖励内容
        private String user_nickname;//玩家昵称

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }
    }

    public static class CouponVo {
        private int coupon_id;//代金券ID
        private int amount;//代金券面额
        private String range;//代金券使用条件
        private String expiry_label;//代金券有有效期说明
        private String has_get;//是否已获取

        public int getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(int coupon_id) {
            this.coupon_id = coupon_id;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getRange() {
            return range;
        }

        public void setRange(String range) {
            this.range = range;
        }

        public String getExpiry_label() {
            return expiry_label;
        }

        public void setExpiry_label(String expiry_label) {
            this.expiry_label = expiry_label;
        }

        public String getHas_get() {
            return has_get;
        }

        public void setHas_get(String has_get) {
            this.has_get = has_get;
        }
    }

    public static class DayRewardVo {
        private int id;//奖励ID
        private String type;//奖励类型
        private String title;//奖励说明
        private String icon;//奖励图标
        private String price;//价格
        private String price_type; //价格类型
        private String label;//按钮文字
        private String price_label;//已格式化的价格
        private String buy_count;//每日购买上限
        private int has_get;//今日已购买次数
        private String cycle;//字符串类型 限购周期，1 每天 2 每周 3 每月

        public String getCycle() {
            return cycle;
        }

        public void setCycle(String cycle) {
            this.cycle = cycle;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getPrice_label() {
            return price_label;
        }

        public void setPrice_label(String price_label) {
            this.price_label = price_label;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPrice_type() {
            return price_type;
        }

        public void setPrice_type(String price_type) {
            this.price_type = price_type;
        }

        public String getBuy_count() {
            return buy_count;
        }

        public void setBuy_count(String buy_count) {
            this.buy_count = buy_count;
        }

        public int getHas_get() {
            return has_get;
        }

        public void setHas_get(int has_get) {
            this.has_get = has_get;
        }
    }

    public static class CardTypeVo {
        private int id;
        private String name;
        private int day;
        private int price;
        private int sub_price;
        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getSub_price() {
            return sub_price;
        }

        public void setSub_price(int sub_price) {
            this.sub_price = sub_price;
        }
    }

    public static class VoucherVo {
        private String buy_id;//会员券id
        private String buy_name;//会员券名称
        private String buy_description;//每张券的灰色文字描述，用|分割换行
        private String buy_goods_value;
        private int status;//购买状态,0 可购买， 1 已达上限
        private String buy_rmb_price;//购买价格，右侧数字
        private String coupon_pft_total;//减免金额，左侧数字

        public String getBuy_id() {
            return buy_id;
        }

        public void setBuy_id(String buy_id) {
            this.buy_id = buy_id;
        }

        public String getBuy_name() {
            return buy_name;
        }

        public void setBuy_name(String buy_name) {
            this.buy_name = buy_name;
        }

        public String getBuy_description() {
            return buy_description;
        }

        public void setBuy_description(String buy_description) {
            this.buy_description = buy_description;
        }

        public String getBuy_goods_value() {
            return buy_goods_value;
        }

        public void setBuy_goods_value(String buy_goods_value) {
            this.buy_goods_value = buy_goods_value;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getBuy_rmb_price() {
            return buy_rmb_price;
        }

        public void setBuy_rmb_price(String buy_rmb_price) {
            this.buy_rmb_price = buy_rmb_price;
        }

        public String getCoupon_pft_total() {
            return coupon_pft_total;
        }

        public void setCoupon_pft_total(String coupon_pft_total) {
            this.coupon_pft_total = coupon_pft_total;
        }
    }
}
