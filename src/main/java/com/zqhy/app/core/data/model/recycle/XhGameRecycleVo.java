package com.zqhy.app.core.data.model.recycle;

import java.util.List;

/**
 * @author Administrator
 */
public class XhGameRecycleVo {

    private List<XhRecycleItemVo> xh_list;

    /**
     * gameid : 1882
     * recycle_ratio : 3%
     * gamename : 小小三国志
     * game_type : 3
     * gameicon :
     */

    private int    gameid;
    private String recycle_ratio;
    private String gamename;
    private int    game_type;
    private String gameicon;
    private String genre_str;

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public String getRecycle_ratio() {
        return recycle_ratio;
    }

    public void setRecycle_ratio(String recycle_ratio) {
        this.recycle_ratio = recycle_ratio;
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

    public String getGameicon() {
        return gameicon;
    }

    public void setGameicon(String gameicon) {
        this.gameicon = gameicon;
    }

    public List<XhRecycleItemVo> getXh_list() {
        return xh_list;
    }

    public void setXh_list(List<XhRecycleItemVo> xh_list) {
        this.xh_list = xh_list;
    }

    public String getGenre_str() {
        return genre_str;
    }

    public void setGenre_str(String genre_str) {
        this.genre_str = genre_str;
    }
}
