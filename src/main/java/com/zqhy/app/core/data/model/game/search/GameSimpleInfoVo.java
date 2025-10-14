package com.zqhy.app.core.data.model.game.search;

import com.zqhy.app.core.data.model.game.GameInfoVo;

/**
 * @author Administrator
 */
public class GameSimpleInfoVo {


    private int gameid;
    private int game_type;


    private String gamename;

    /**
     * 常规折扣
     */
    private float discount;


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

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    private String searchValue;

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    private int showDiscount;

    public int showDiscount() {
        return showDiscount;
    }

    public void setShowDiscount(int showDiscount) {
        this.showDiscount = showDiscount;
    }

    public GameInfoVo gameInfoVo;

    public GameInfoVo getGameInfoVo() {
        return gameInfoVo;
    }

    public void setGameInfoVo(GameInfoVo gameInfoVo) {
        this.gameInfoVo = gameInfoVo;
    }
}
