package com.zqhy.app.core.data.model.game.appointment;

import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.utils.CommonUtils;

import java.util.List;

/**
 * @author leeham
 * @date 2020/3/27-16:12
 * @description
 */
public class UserAppointmentVo {

    private String gameid;
    private String gamename;
    private String gameicon;
    private String online_time;
    private String    game_type;

    public String getGame_type() {
        return game_type;
    }

    public void setGame_type(String game_type) {
        this.game_type = game_type;
    }

    public String getGameid() {
        return gameid;
    }

    public void setGameid(String gameid) {
        this.gameid = gameid;
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

    public String getOnline_time() {
        return online_time;
    }

    public void setOnline_time(String online_time) {
        this.online_time = online_time;
    }
}
