package com.zqhy.app.core.data.model.user;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;

import java.io.Serializable;
import java.util.List;

public class SuperVipMemberInfoVo extends BaseVo {
    public DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean implements Serializable {
        private MemberRewardBanner ad_banner;
        private int open_super_user;
        private int buy_user_count;
        private int day_reward;
        private long user_card_expiry_time;
        private String open_money_card;
        private String has_get_reward;

        private List<CardType> card_type_list;
        private List<MemberRewardBanner> banner_list;
        private List<GameInfoVo> flb_usage_game;//可用福利币的游戏
        private List<GameInfoVo> flb_usage_low_discount_game;//可用福利币的0.1折游戏
        private String gold_game_query_url;//可用福利币游戏查询，如果查询0.1折游戏，加参数 discount=0.1

        private String coupon_game_query_url;//可用折扣券游戏查询类别
        private int discount_coupon_id;//
        private List<DiscountCardType> discount_card_type_list;//折扣卡类型
        private DiscountCardInfo discount_card_info;//用户折扣卡信息
        private List<GameInfoVo> discount_coupon_usage_game;//可用折扣券游戏列表

        public String getCoupon_game_query_url() {
            return coupon_game_query_url;
        }

        public void setCoupon_game_query_url(String coupon_game_query_url) {
            this.coupon_game_query_url = coupon_game_query_url;
        }

        public int getDiscount_coupon_id() {
            return discount_coupon_id;
        }

        public void setDiscount_coupon_id(int discount_coupon_id) {
            this.discount_coupon_id = discount_coupon_id;
        }

        public List<DiscountCardType> getDiscount_card_type_list() {
            return discount_card_type_list;
        }

        public void setDiscount_card_type_list(List<DiscountCardType> discount_card_type_list) {
            this.discount_card_type_list = discount_card_type_list;
        }

        public DiscountCardInfo getDiscount_card_info() {
            return discount_card_info;
        }

        public void setDiscount_card_info(DiscountCardInfo discount_card_info) {
            this.discount_card_info = discount_card_info;
        }

        public List<GameInfoVo> getDiscount_coupon_usage_game() {
            return discount_coupon_usage_game;
        }

        public void setDiscount_coupon_usage_game(List<GameInfoVo> discount_coupon_usage_game) {
            this.discount_coupon_usage_game = discount_coupon_usage_game;
        }

        public List<GameInfoVo> getFlb_usage_game() {
            return flb_usage_game;
        }

        public void setFlb_usage_game(List<GameInfoVo> flb_usage_game) {
            this.flb_usage_game = flb_usage_game;
        }

        public List<GameInfoVo> getFlb_usage_low_discount_game() {
            return flb_usage_low_discount_game;
        }

        public void setFlb_usage_low_discount_game(List<GameInfoVo> flb_usage_low_discount_game) {
            this.flb_usage_low_discount_game = flb_usage_low_discount_game;
        }

        public String getGold_game_query_url() {
            return gold_game_query_url;
        }

        public void setGold_game_query_url(String gold_game_query_url) {
            this.gold_game_query_url = gold_game_query_url;
        }

        public static class MemberRewardBanner {
            private String page_type;
            private String pic;
            private String jump_target;
            private AppBaseJumpInfoBean.ParamBean param;

            public String getPage_type() {
                return page_type;
            }

            public void setPage_type(String page_type) {
                this.page_type = page_type;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getJump_target() {
                return jump_target;
            }

            public void setJump_target(String jump_target) {
                this.jump_target = jump_target;
            }

            public AppBaseJumpInfoBean.ParamBean getParam() {
                return param;
            }

            public void setParam(AppBaseJumpInfoBean.ParamBean param) {
                this.param = param;
            }
        }

        public static class CardType {
            private int id;
            private String name;
            private int day;
            private int give;
            private int reward;
            private int price;
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

            public int getGive() {
                return give;
            }

            public void setGive(int give) {
                this.give = give;
            }

            public int getReward() {
                return reward;
            }

            public void setReward(int reward) {
                this.reward = reward;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }
        }

        public MemberRewardBanner getAd_banner() {
            return ad_banner;
        }

        public void setAd_banner(MemberRewardBanner ad_banner) {
            this.ad_banner = ad_banner;
        }

        public int getOpen_super_user() {
            return open_super_user;
        }

        public void setOpen_super_user(int open_super_user) {
            this.open_super_user = open_super_user;
        }

        public int getBuy_user_count() {
            return buy_user_count;
        }

        public void setBuy_user_count(int buy_user_count) {
            this.buy_user_count = buy_user_count;
        }

        public int getDay_reward() {
            return day_reward;
        }

        public void setDay_reward(int day_reward) {
            this.day_reward = day_reward;
        }

        public long getUser_card_expiry_time() {
            return user_card_expiry_time;
        }

        public void setUser_card_expiry_time(long user_card_expiry_time) {
            this.user_card_expiry_time = user_card_expiry_time;
        }

        public String getOpen_money_card() {
            return open_money_card;
        }

        public void setOpen_money_card(String open_money_card) {
            this.open_money_card = open_money_card;
        }

        public String getHas_get_reward() {
            return has_get_reward;
        }

        public void setHas_get_reward(String has_get_reward) {
            this.has_get_reward = has_get_reward;
        }

        public List<CardType> getCard_type_list() {
            return card_type_list;
        }

        public void setCard_type_list(List<CardType> card_type_list) {
            this.card_type_list = card_type_list;
        }

        public List<MemberRewardBanner> getBanner_list() {
            return banner_list;
        }

        public void setBanner_list(List<MemberRewardBanner> banner_list) {
            this.banner_list = banner_list;
        }

        public static class DiscountCardType {
            private int id;
            private String name;
            private int day;
            private int more_day;
            private int total_worth;
            private int day_worth;
            private int price;
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

            public int getMore_day() {
                return more_day;
            }

            public void setMore_day(int more_day) {
                this.more_day = more_day;
            }

            public int getTotal_worth() {
                return total_worth;
            }

            public void setTotal_worth(int total_worth) {
                this.total_worth = total_worth;
            }

            public int getDay_worth() {
                return day_worth;
            }

            public void setDay_worth(int day_worth) {
                this.day_worth = day_worth;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }
        }

        public static class DiscountCardInfo {
            private long user_card_expiry_time;//过期时间
            private String has_get_reward;//当前折扣券是否已领取,yes/no
            private String is_active;//当前折扣卡是否生效中,yes/no
            private Config config;

            public Config getConfig() {
                return config;
            }

            public void setConfig(Config config) {
                this.config = config;
            }

            public long getUser_card_expiry_time() {
                return user_card_expiry_time;
            }

            public void setUser_card_expiry_time(long user_card_expiry_time) {
                this.user_card_expiry_time = user_card_expiry_time;
            }

            public String getHas_get_reward() {
                return has_get_reward;
            }

            public void setHas_get_reward(String has_get_reward) {
                this.has_get_reward = has_get_reward;
            }

            public String getIs_active() {
                return is_active;
            }

            public void setIs_active(String is_active) {
                this.is_active = is_active;
            }

            public static  class Config{
                private int id;
                private String open_exchange;//是否开启了兑换
                private String exchange_start_time;//兑换开启时间
                private String exchange_end_time;//兑换结束时间
                private String exchange_price;//兑换价格

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getOpen_exchange() {
                    return open_exchange;
                }

                public void setOpen_exchange(String open_exchange) {
                    this.open_exchange = open_exchange;
                }

                public String getExchange_start_time() {
                    return exchange_start_time;
                }

                public void setExchange_start_time(String exchange_start_time) {
                    this.exchange_start_time = exchange_start_time;
                }

                public String getExchange_end_time() {
                    return exchange_end_time;
                }

                public void setExchange_end_time(String exchange_end_time) {
                    this.exchange_end_time = exchange_end_time;
                }

                public String getExchange_price() {
                    return exchange_price;
                }

                public void setExchange_price(String exchange_price) {
                    this.exchange_price = exchange_price;
                }
            }
        }
    }
}
