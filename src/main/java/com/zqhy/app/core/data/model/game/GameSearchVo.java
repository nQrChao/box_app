package com.zqhy.app.core.data.model.game;

/**
 *
 * @author Administrator
 * @date 2018/11/25
 */

public class GameSearchVo {
    private int game_type;
    private String gameSearch;

    private int downloadImageRes;

    public GameSearchVo(int game_type) {
        this.game_type = game_type;
    }

    public String getGameSearch() {
        return gameSearch;
    }

    public void setGameSearch(String gameSearch) {
        this.gameSearch = gameSearch;
    }

    public int getGame_type() {
        return game_type;
    }

    public void setGame_type(int game_type) {
        this.game_type = game_type;
    }

    public int getDownloadImageRes() {
        return downloadImageRes;
    }

    public void setDownloadImageRes(int downloadImageRes) {
        this.downloadImageRes = downloadImageRes;
    }
}
