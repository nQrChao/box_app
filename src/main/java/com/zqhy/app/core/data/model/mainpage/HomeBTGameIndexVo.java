package com.zqhy.app.core.data.model.mainpage;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.banner.BannerVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameNavigationVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;
import com.zqhy.app.core.data.model.tryplay.TryGameItemVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/8/27-10:12
 * @description
 */
public class HomeBTGameIndexVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {

        /**
         * 首页轮播图
         */
        private List<BannerVo> slider_list;

        /**
         * 新手礼包, 当数据为NULL时不显示此板块
         */
        private RookiesCouponVo rookies_coupon_data;


        /**
         * 精选合集
         */
        private List<ChoiceListVo.DataBean> choice_list;


        /**
         * 人气力荐数据 (热门排序,12个)
         */
        private List<GameInfoVo> hot_list;
        /**
         * 玩点新鲜的数据(最新排序,4个)
         */
        private List<GameInfoVo> newest_list;


        /**
         * 新游预告
         * 即将上线(新游预约6个)
         */
        private List<ReserveVo> reserve_list;

        /**
         * 推荐位1
         */
        private List<GameInfoVo> recommend_list;

        /**
         * 推荐位2
         */
        private List<GameInfoVo> recommend_list2;

        /**
         * 热门分类
         */
        private List<GameNavigationVo> hot_genre_list;

        /**
         * 热门分类及游戏,3个热门分类,每人热门分类下6个游戏条目
         */
        private List<GenreGameVo> genre_game_data;

        /**
         * 首页插屏,最多7个;
         * 键值分别为position_1,position_2,..position_7;
         * 后面数字对应从上到下的位置
         */
        private TablePlaque table_plaque;

        public List<BannerVo> getSlider_list() {
            return slider_list;
        }

        public RookiesCouponVo getRookies_coupon_data() {
            return rookies_coupon_data;
        }

        public List<ChoiceListVo.DataBean> getChoice_list() {
            return choice_list;
        }

        public List<GameInfoVo> getHot_list() {
            return hot_list;
        }

        public List<GameInfoVo> getNewest_list() {
            return newest_list;
        }

        public List<ReserveVo> getReserve_list() {
            return reserve_list;
        }

        public List<GameInfoVo> getRecommend_list() {
            return recommend_list;
        }

        public List<GameInfoVo> getRecommend_list2() {
            return recommend_list2;
        }

        public List<GameNavigationVo> getHot_genre_list() {
            return hot_genre_list;
        }

        public List<GenreGameVo> getGenre_game_data() {
            return genre_game_data;
        }

        public TablePlaque getTable_plaque() {
            return table_plaque;
        }

        /**
         * 更多你可能喜欢(排行榜排序3个)
         */
        private List<GameInfoVo> more_like;

        public List<GameInfoVo> getMore_like() {
            return more_like;
        }

        /**
         * 试玩数据(4个)
         */
        private List<TryGameItemVo.DataBean> trial_list;

        public List<TryGameItemVo.DataBean> getTrial_list() {
            return trial_list;
        }

        /**
         * 2021.02.03 精品必玩数据
         */
        private List<GameInfoVo> quality_list;

        public List<GameInfoVo> getQuality_list() {
            return quality_list;
        }


        /*****2021.06.08********************************************************************/
        private List<RookiesCouponVo.GameVo> card648list;

        private String card648_url;
        private String rookies_coupon_url;
        private String get_now_url;


        public List<RookiesCouponVo.GameVo> getCard648list() {
            return card648list;
        }

        public String getCard648_url() {
            return card648_url;
        }

        public String getRookies_coupon_url() {
            return rookies_coupon_url;
        }

        public String getGet_now_url() {
            return get_now_url;
        }
    }

    public static class TablePlaque {
        private DataBean position_1;
        private DataBean position_2;
        private DataBean position_3;
        private DataBean position_4;
        private DataBean position_5;
        private DataBean position_6;
        private DataBean position_7;

        public DataBean getPosition_1() {
            return position_1;
        }

        public DataBean getPosition_2() {
            return position_2;
        }

        public DataBean getPosition_3() {
            return position_3;
        }

        public DataBean getPosition_4() {
            return position_4;
        }

        public DataBean getPosition_5() {
            return position_5;
        }

        public DataBean getPosition_6() {
            return position_6;
        }

        public DataBean getPosition_7() {
            return position_7;
        }

        public static class DataBean extends AppBaseJumpInfoBean {
            public DataBean(String page_type, ParamBean param) {
                super(page_type, param);
            }

            private String pic;
            private String title;
            private String subtitle;

            public String getPic() {
                return pic;
            }

            public String getTitle() {
                return title;
            }

            public String getSubtitle() {
                return subtitle;
            }
        }

    }

    public static class GenreGameVo {
        private int              genre_id;
        private String           genre_name;
        private List<GameInfoVo> list;
        private int              game_type;

        public int getGenre_id() {
            return genre_id;
        }

        public String getGenre_name() {
            return genre_name;
        }

        public List<GameInfoVo> getList() {
            return list;
        }

        public int getGame_type() {
            return game_type;
        }

        public void setGame_type(int game_type) {
            this.game_type = game_type;
        }

        public void setList(List<GameInfoVo> list) {
            this.list = list;
        }
    }

    public static class GenreVo {

        /**
         * genre_id : 59
         * genre_name : 三国
         * bg_color : #F33A4C
         */

        private String genre_id;
        private String genre_name;
        private String bg_color;

        public String getGenre_id() {
            return genre_id;
        }

        public void setGenre_id(String genre_id) {
            this.genre_id = genre_id;
        }

        public String getGenre_name() {
            return genre_name;
        }

        public void setGenre_name(String genre_name) {
            this.genre_name = genre_name;
        }

        public String getBg_color() {
            return bg_color;
        }

        public void setBg_color(String bg_color) {
            this.bg_color = bg_color;
        }
    }

    public static class ReserveVo {

        /**
         * gameid : 2186
         * gamename : 真江湖HD（万抽版）
         * game_type : 1
         * screenshot1 : http://p1.tsyule.cn/2020/06/11/5ee1d7e8df71a.png
         * is_first : 1
         * online_time : 1591927200
         * genre_str : 卡牌 武侠
         */

        private int    gameid;
        private String gamename;
        private int    game_type;
        private String screenshot1;
        private String is_first;
        private long   online_time;
        private String genre_str;

        public int getGameid() {
            return gameid;
        }

        public void setGameid(int gameid) {
            this.gameid = gameid;
        }

        public String getGamename() {
            return gamename;
        }

        public void setGamename(String gamename) {
            this.gamename = gamename;
        }

        public int getGame_type() {
            return game_type;
        }

        public void setGame_type(int game_type) {
            this.game_type = game_type;
        }

        public String getScreenshot1() {
            return screenshot1;
        }

        public void setScreenshot1(String screenshot1) {
            this.screenshot1 = screenshot1;
        }

        public String getIs_first() {
            return is_first;
        }

        public void setIs_first(String is_first) {
            this.is_first = is_first;
        }

        public long getOnline_time() {
            return online_time;
        }

        public void setOnline_time(long online_time) {
            this.online_time = online_time;
        }

        public String getGenre_str() {
            return genre_str;
        }

        public void setGenre_str(String genre_str) {
            this.genre_str = genre_str;
        }
    }

    public static class RookiesCouponVo {
        private int            total;
        private int            received_count;
        private List<DataBean> list;
        private List<GameVo>   card648list;

        private String card648_url;
        private String rookies_coupon_url;
        private String get_now_url;

        public int getTotal() {
            return total;
        }

        public int getReceived_count() {
            return received_count;
        }

        public List<DataBean> getList() {
            return list;
        }

        public List<GameVo> getCard648list() {
            return card648list;
        }

        public String getCard648_url() {
            return card648_url;
        }

        public String getRookies_coupon_url() {
            return rookies_coupon_url;
        }

        public String getGet_now_url() {
            return get_now_url;
        }

        public void setCard648list(List<GameVo> card648list) {
            this.card648list = card648list;
        }

        public void setCard648_url(String card648_url) {
            this.card648_url = card648_url;
        }

        public void setRookies_coupon_url(String rookies_coupon_url) {
            this.rookies_coupon_url = rookies_coupon_url;
        }

        public void setGet_now_url(String get_now_url) {
            this.get_now_url = get_now_url;
        }

        public static class DataBean {

            /**
             * coupon_id : 5329
             * coupon_name : 首充券
             * amount : 6
             * range : 通用券
             */

            private String coupon_id;
            private String coupon_name;
            private int    amount;
            private String range;

            public String getCoupon_id() {
                return coupon_id;
            }

            public void setCoupon_id(String coupon_id) {
                this.coupon_id = coupon_id;
            }

            public String getCoupon_name() {
                return coupon_name;
            }

            public void setCoupon_name(String coupon_name) {
                this.coupon_name = coupon_name;
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
        }

        public static class GameVo {
            private String gamename;
            private String gameicon;

            public String getGamename() {
                return gamename;
            }

            public String getGameicon() {
                return gameicon;
            }
        }
    }
}
