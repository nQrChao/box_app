package com.zqhy.app.core.data.model.user;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;

import java.io.Serializable;
import java.util.List;

public class ExchangeCouponInfoVo extends BaseVo {
    public DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean implements Serializable {
        private DiscountCardInfo discount_card_info;//用户折扣卡信息
        private CouponInfo coupon_info;//优惠券

        public DiscountCardInfo getDiscount_card_info() {
            return discount_card_info;
        }

        public void setDiscount_card_info(DiscountCardInfo discount_card_info) {
            this.discount_card_info = discount_card_info;
        }

        public CouponInfo getCoupon_info() {
            return coupon_info;
        }

        public void setCoupon_info(CouponInfo coupon_info) {
            this.coupon_info = coupon_info;
        }

        public static class DiscountCardInfo {
            private long user_card_expiry_time;//过期时间
            private String has_get_reward;//当前折扣券是否已领取,yes/no
            private String has_exchange;//是否已兑换,yes/no
            private String is_active;//当前折扣卡是否生效中,yes/no
            private Config config;

            public String getHas_exchange() {
                return has_exchange;
            }

            public void setHas_exchange(String has_exchange) {
                this.has_exchange = has_exchange;
            }

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

        public static class CouponInfo {
            private int id;
            private String coupon_name;
            private String cdt;//满*可用
            private String pft;//减多少
            private String expiry_type;
            private String expiry_begin;
            private String expiry_end;
            private String expiry_hours;
            private String range;
            private String expiry_label;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCoupon_name() {
                return coupon_name;
            }

            public void setCoupon_name(String coupon_name) {
                this.coupon_name = coupon_name;
            }

            public String getCdt() {
                return cdt;
            }

            public void setCdt(String cdt) {
                this.cdt = cdt;
            }

            public String getPft() {
                return pft;
            }

            public void setPft(String pft) {
                this.pft = pft;
            }

            public String getExpiry_type() {
                return expiry_type;
            }

            public void setExpiry_type(String expiry_type) {
                this.expiry_type = expiry_type;
            }

            public String getExpiry_begin() {
                return expiry_begin;
            }

            public void setExpiry_begin(String expiry_begin) {
                this.expiry_begin = expiry_begin;
            }

            public String getExpiry_end() {
                return expiry_end;
            }

            public void setExpiry_end(String expiry_end) {
                this.expiry_end = expiry_end;
            }

            public String getExpiry_hours() {
                return expiry_hours;
            }

            public void setExpiry_hours(String expiry_hours) {
                this.expiry_hours = expiry_hours;
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
        }

    }
}
