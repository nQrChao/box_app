package com.zqhy.app.core.data.model.mainpage;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;

import java.util.List;

/**
 * @author leeham2734
 * @date 2021/6/7-9:58
 * @description
 */
public class MainGameRecommendVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {

        private ItemBean bt_hot;
        private ItemBean bt_new;
        private ItemBean coupon_game;
        private ItemBean hot_recommen;
        private ItemBean icon_menu;
        private ItemBean illstration;
        private ItemBean slider;


        public ItemBean getBt_hot() {
            return bt_hot;
        }

        public ItemBean getBt_new() {
            return bt_new;
        }

        public ItemBean getCoupon_game() {
            return coupon_game;
        }

        public ItemBean getHot_recommen() {
            return hot_recommen;
        }

        public ItemBean getIcon_menu() {
            return icon_menu;
        }

        public ItemBean getIllstration() {
            return illstration;
        }

        public ItemBean getSlider() {
            return slider;
        }
    }

    public static class ItemBean {
        private String           icon;
        private String           title;
        private String           title_color;
        private List<GameDataVo> data;

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

        public String getTitle_color() {
            return title_color;
        }

        public void setTitle_color(String title_color) {
            this.title_color = title_color;
        }

        public List<GameDataVo> getData() {
            return data;
        }

        public void setData(List<GameDataVo> data) {
            this.data = data;
        }
    }

    public static class GameDataVo {
        private List<GameLabelVo> game_labels;
        private int               game_type;
        private String            gameicon;
        private int               gameid;
        private String            gamename;
        private String            genre_str;
        private int               is_first;
        private int               play_count;
        private int               coupon_amount;
        private String            vip_label;
        private String            coin_label;

        public List<GameLabelVo> getGame_labels() {
            return game_labels;
        }

        public void setGame_labels(List<GameLabelVo> game_labels) {
            this.game_labels = game_labels;
        }

        public int getGame_type() {
            return game_type;
        }

        public void setGame_type(int game_type) {
            this.game_type = game_type;
        }

        public String getGameicon() {
            return gameicon;
        }

        public void setGameicon(String gameicon) {
            this.gameicon = gameicon;
        }

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

        public String getGenre_str() {
            return genre_str;
        }

        public void setGenre_str(String genre_str) {
            this.genre_str = genre_str;
        }

        public int getIs_first() {
            return is_first;
        }

        public void setIs_first(int is_first) {
            this.is_first = is_first;
        }

        public int getPlay_count() {
            return play_count;
        }

        public void setPlay_count(int play_count) {
            this.play_count = play_count;
        }

        public int getCoupon_amount() {
            return coupon_amount;
        }

        public void setCoupon_amount(int coupon_amount) {
            this.coupon_amount = coupon_amount;
        }

        public String getVip_label() {
            return vip_label;
        }

        public void setVip_label(String vip_label) {
            this.vip_label = vip_label;
        }

        public String getCoin_label() {
            return coin_label;
        }

        public void setCoin_label(String coin_label) {
            this.coin_label = coin_label;
        }

        public AppBaseJumpInfoBean getJumpInfo() {
            return new AppBaseJumpInfoBean(page_type, param);
        }

        public static class GameLabelVo {
            private String label_name;
            private String text_color;

            public String getLabel_name() {
                return label_name;
            }

            public void setLabel_name(String label_name) {
                this.label_name = label_name;
            }

            public String getText_color() {
                return text_color;
            }

            public void setText_color(String text_color) {
                this.text_color = text_color;
            }
        }


        private String                        icon;
        private String                        jump_target;
        private String                        page_type;
        private AppBaseJumpInfoBean.ParamBean param;

        private String title;
        private String title_color;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getJump_target() {
            return jump_target;
        }

        public void setJump_target(String jump_target) {
            this.jump_target = jump_target;
        }

        public String getPage_type() {
            return page_type;
        }

        public void setPage_type(String page_type) {
            this.page_type = page_type;
        }

        public AppBaseJumpInfoBean.ParamBean getParam() {
            return param;
        }

        public void setParam(AppBaseJumpInfoBean.ParamBean param) {
            this.param = param;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle_color() {
            return title_color;
        }

        public void setTitle_color(String title_color) {
            this.title_color = title_color;
        }


        private String pic;

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
