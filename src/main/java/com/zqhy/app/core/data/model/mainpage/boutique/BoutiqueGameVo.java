package com.zqhy.app.core.data.model.mainpage.boutique;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public class BoutiqueGameVo {

    private int gameid;
    private int game_type;
    private String gameicon;
    private String gamename;

    private String game_download;

    private int is_flash;
    private float flash_discount;
    private float discount;

    /**
     * 标签
     */
    private String label_name;
    /**
     * 游戏图片上标签, (bt游戏数据)
     */
    private String game_label;
    /**
     * 游戏类型字符串
     */
    private String genre_str;


    public int getGameid() {
        return gameid;
    }

    public int getGame_type() {
        return game_type;
    }

    public String getGameicon() {
        return gameicon;
    }

    public String getGamename() {
        return gamename;
    }

    /**
     * 是否隐藏折扣标签, 1:隐藏; 0:不隐藏
     */
    private int hide_discount_label;

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


    public String getGame_download() {
        return game_download;
    }

    public int getIs_flash() {
        return is_flash;
    }

    public float getFlash_discount() {
        return flash_discount;
    }

    public float getDiscount() {
        return discount;
    }

    public String getLabel_name() {
        return label_name;
    }

    public String getGame_label() {
        return game_label;
    }

    public String getGenre_str() {
        return genre_str;
    }
}
