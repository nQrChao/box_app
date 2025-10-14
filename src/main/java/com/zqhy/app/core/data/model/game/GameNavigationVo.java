package com.zqhy.app.core.data.model.game;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public class GameNavigationVo {

    /**
     * id : 35
     * name : 体育游戏
     */

    public int genre_id;
    private String genre_name;
    private int type;
    private String bg_color;
    private String bg_color_left;
    private String bg_color_right;

    public int getGenre_id() {
        return genre_id;
    }

    public String getGenre_name() {
        return genre_name;
    }

    /**
     * 分类, 1:游戏玩法; 2: 类型题材  0:自定义
     *
     * @return
     */
    public int getType() {
        return type;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public void setGenre_name(String genre_name) {
        this.genre_name = genre_name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBg_color() {
        return bg_color;
    }

    public void setBg_color(String bg_color) {
        this.bg_color = bg_color;
    }

    public String getBg_color_left() {
        return bg_color_left;
    }

    public String getBg_color_right() {
        return bg_color_right;
    }
}
