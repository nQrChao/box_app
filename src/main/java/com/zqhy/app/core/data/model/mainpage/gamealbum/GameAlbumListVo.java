package com.zqhy.app.core.data.model.mainpage.gamealbum;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/7
 */

public class GameAlbumListVo {

    private String background;
    private String title;
    private String title_color;
    private int game_type;

    private List<GameItem> game_items;

    public GameAlbumListVo() {
    }


    public void setBackground(String background) {
        this.background = background;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitle_color(String title_color) {
        this.title_color = title_color;
    }

    public void setGame_items(List<GameItem> game_items) {
        this.game_items = game_items;
    }

    public String getBackground() {
        return background;
    }

    public String getTitle() {
        return title;
    }

    public String getTitle_color() {
        return title_color;
    }

    public int getGame_type() {
        return game_type;
    }

    public void setGame_type(int game_type) {
        this.game_type = game_type;
    }

    public List<GameItem> getGame_items() {
        return game_items;
    }

    public class GameItem{
        private int gameid;
        private String gamename;
        private String gameicon;
        private int game_type;

        private String genre_str;

        private String game_label;
        private float discount;
        private float flash_discount;

        private int is_flash;
        private int hide_discount_label;

        public int getGameid() {
            return gameid;
        }

        public String getGamename() {
            return gamename;
        }

        public String getGameicon() {
            return gameicon;
        }

        public int getGame_type() {
            return game_type;
        }

        public String getGenre_str() {
            return genre_str;
        }

        public String getGame_label() {
            return game_label;
        }

        public float getDiscount() {
            return discount;
        }

        public float getFlash_discount() {
            return flash_discount;
        }

        public int getHide_discount_label() {
            if(game_type == 1){
                return 1;
            }
            return hide_discount_label;
        }

        /**
         *
         * @return 0 隐藏全部折扣  1 显示普通折扣  2显示限时折扣
         */
        public int showDiscount(){
            if(getHide_discount_label() == 1){
                return 0;
            }else {
                if(is_flash == 1){
                    return 2;
                }else{
                    return 1;
                }
            }
        }
    }
}
