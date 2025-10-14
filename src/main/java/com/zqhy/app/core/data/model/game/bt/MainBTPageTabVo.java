package com.zqhy.app.core.data.model.game.bt;

import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/8/27-15:22
 * @description
 */
public class MainBTPageTabVo {

    private List<GameInfoVo> remen_list;
    private List<GameInfoVo> zuixin_list;

    public MainBTPageTabVo(List<GameInfoVo> remen_list, List<GameInfoVo> zuixin_list) {
        this.remen_list = remen_list;
        this.zuixin_list = zuixin_list;
    }

    private int game_type;

    public List<GameInfoVo> getRemen_list() {
        return remen_list;
    }

    public void setRemen_list(List<GameInfoVo> remen_list) {
        this.remen_list = remen_list;
    }

    public List<GameInfoVo> getZuixin_list() {
        return zuixin_list;
    }

    public void setZuixin_list(List<GameInfoVo> zuixin_list) {
        this.zuixin_list = zuixin_list;
    }

    public int getGame_type() {
        return game_type;
    }

    public void setGame_type(int game_type) {
        this.game_type = game_type;
    }
}
