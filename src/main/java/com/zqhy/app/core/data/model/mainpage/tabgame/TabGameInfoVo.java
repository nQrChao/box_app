package com.zqhy.app.core.data.model.mainpage.tabgame;

import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public class TabGameInfoVo {
    private List<GameInfoVo> remen_list;
    private List<GameInfoVo> zuixin_list;
    private static List<GameInfoVo> game_appointment_list;

    private int max_gameid;

    private int game_type;

    public TabGameInfoVo(List<GameInfoVo> remen_list, List<GameInfoVo> zuixin_list, int max_gameid) {
        this.remen_list = remen_list;
        this.zuixin_list = zuixin_list;
        this.max_gameid = max_gameid;
    }

    public int getGame_type() {
        return game_type;
    }

    public void setGame_type(int game_type) {
        this.game_type = game_type;
    }

    public int getMax_gameid() {
        return max_gameid;
    }

    public List<GameInfoVo> getRemen_list() {
        return remen_list;
    }

    public List<GameInfoVo> getZuixin_list() {
        return zuixin_list;
    }

    public void setGame_appointment_list(List<GameInfoVo> game_appointment_list) {
        this.game_appointment_list = game_appointment_list;
    }

    public List<GameInfoVo> getGame_appointment_list() {
        return game_appointment_list;
    }
}
