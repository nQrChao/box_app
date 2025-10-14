package com.zqhy.app.core.data.model.user.newvip;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2021/2/3-12:11
 * @description
 */
public class DayRrewardListInfoVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        private int current_week;//今天是周几
        private List<RewardVo> list;

        public int getCurrent_week() {
            return current_week;
        }

        public void setCurrent_week(int current_week) {
            this.current_week = current_week;
        }

        public List<RewardVo> getList() {
            return list;
        }

        public void setList(List<RewardVo> list) {
            this.list = list;
        }
    }

    public static class RewardVo{
        private int week;//奖励所属周数，数字
        private String week_label;//奖励所属周数，中文
        private List<DayRewardVo> reward_list;
        private boolean isToday;
        private boolean isNotTo;

        public boolean isNotTo() {
            return isNotTo;
        }

        public void setNotTo(boolean notTo) {
            isNotTo = notTo;
        }

        public boolean isToday() {
            return isToday;
        }

        public void setToday(boolean today) {
            isToday = today;
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

        public List<DayRewardVo> getReward_list() {
            return reward_list;
        }

        public void setReward_list(List<DayRewardVo> reward_list) {
            this.reward_list = reward_list;
        }
    }

    public static class DayRewardVo{
        private String amount;
        private String type;
        private float price;
        private String price_type;
        private String buy_count;
        private String icon;
        private String title;
        private int get_count;
        private String price_label;

        public String getPrice_label() {
            return price_label;
        }

        public void setPrice_label(String price_label) {
            this.price_label = price_label;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getGet_count() {
            return get_count;
        }

        public void setGet_count(int get_count) {
            this.get_count = get_count;
        }
    }

}
