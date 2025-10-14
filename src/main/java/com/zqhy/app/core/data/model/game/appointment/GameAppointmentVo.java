package com.zqhy.app.core.data.model.game.appointment;

import android.text.TextUtils;

import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.utils.CommonUtils;

import java.util.List;

/**
 * @author leeham
 * @date 2020/3/27-16:12
 * @description
 */
public class GameAppointmentVo {

    private int gameid;
    private String gamename;
    private int    game_type;
    private String gameicon;
    private List<GameInfoVo.GameLabelsBean> top_labels;
    private float client_size;
    private int appointment_count;
    private int game_status;
    private String online_text;
    private long appointment_begintime;

    /**
     * 游戏类型字符串, (游戏列表数据)
     */
    private String genre_str;

    public String getGenre_str() {
        return genre_str;
    }

    public void setGenre_str(String genre_str) {
        this.genre_str = genre_str;
    }

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public String getGamename() {
        return CommonUtils.getGamename(gamename);
    }

    public String getOtherGameName(){
        return CommonUtils.getOtherGameName(gamename);
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

    public List<GameInfoVo.GameLabelsBean> getTop_labels() {
        return top_labels;
    }

    public void setTop_labels(List<GameInfoVo.GameLabelsBean> top_labels) {
        this.top_labels = top_labels;
    }

    public float getClient_size() {
        return client_size;
    }

    public void setClient_size(float client_size) {
        this.client_size = client_size;
    }

    public int getAppointment_count() {
        return appointment_count;
    }

    public void setAppointment_count(int appointment_count) {
        this.appointment_count = appointment_count;
    }

    public int getGame_status() {
        return game_status;
    }

    public void setGame_status(int game_status) {
        this.game_status = game_status;
    }

    public String getOnline_text() {
        return online_text;
    }

    public void setOnline_text(String online_text) {
        this.online_text = online_text;
    }

    public long getAppointment_begintime() {
        return appointment_begintime;
    }

    public void setAppointment_begintime(long appointment_begintime) {
        this.appointment_begintime = appointment_begintime;
    }

    public String getCalendarTitle(){
        return gamename + "- 上线提醒";
    }
}
