package com.zqhy.app.core.data.model.mainpage;

import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pc
 * @date 2019/12/16-15:42
 * @description
 */
public class TopGameListVo {

    private List<GameInfoVo> mGameInfoVos;

    public TopGameListVo() {
        mGameInfoVos = new ArrayList<>();
    }

    public void addGameInfoVo(GameInfoVo gameInfoVo) {
        mGameInfoVos.add(gameInfoVo);
    }

    public List<GameInfoVo> getGameInfoVos() {
        return mGameInfoVos;
    }
}
