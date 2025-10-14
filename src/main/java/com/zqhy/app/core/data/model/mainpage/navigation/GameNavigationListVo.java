package com.zqhy.app.core.data.model.mainpage.navigation;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameNavigationVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/7
 */

public class GameNavigationListVo extends BaseVo{
    private List<GameNavigationVo> data;
    private int game_type;


    public GameNavigationListVo(List<GameNavigationVo> data) {
        this.data = data;
    }


    public List<GameNavigationVo> getData() {
        return data;
    }

    public int getGame_type() {
        return game_type;
    }

    public void setGame_type(int game_type) {
        this.game_type = game_type;
    }
}
