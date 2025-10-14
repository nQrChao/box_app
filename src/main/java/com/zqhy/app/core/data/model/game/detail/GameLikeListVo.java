package com.zqhy.app.core.data.model.game.detail;

import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/20
 */

public class GameLikeListVo {

    private List<GameInfoVo> like_game_list;

    public List<GameInfoVo> getLike_game_list() {
        return like_game_list;
    }

    public void setLike_game_list(List<GameInfoVo> like_game_list) {
        this.like_game_list = like_game_list;
    }
}
