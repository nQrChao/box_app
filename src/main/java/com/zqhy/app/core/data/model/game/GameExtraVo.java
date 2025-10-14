package com.zqhy.app.core.data.model.game;

import com.zqhy.app.utils.CommonUtils;

import java.io.Serializable;

/**
 * @author Administrator
 * @date 2018/11/30
 */

public class GameExtraVo implements Serializable {

    private static final long serialVersionUID = 42L;

    /**
     * 此类为Progress extra1数据
     * 不能随意增添删减字段
     */

    private int gameid;
    private int game_type;
    private String gamename;
    private String gameicon;
    private String game_download_url;
    private String game_download_tag;
    private String client_package_name;
    private String channel;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

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
        return CommonUtils.getGamename(gamename);
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getGameicon() {
        return gameicon;
    }

    public void setGameicon(String gameicon) {
        this.gameicon = gameicon;
    }

    public String getClient_package_name() {
        return client_package_name;
    }

    public void setClient_package_name(String client_package_name) {
        this.client_package_name = client_package_name;
    }

    public String getGame_download_url() {
        return game_download_url;
    }

    public void setGame_download_url(String game_download_url) {
        this.game_download_url = game_download_url;
    }

    public String getGame_download_tag() {
        return game_download_tag;
    }

    public void setGame_download_tag(String game_download_tag) {
        this.game_download_tag = game_download_tag;
    }
}
