package com.zqhy.app.core.data.model.game;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/9
 */

public class GameListVo extends BaseVo{

    private List<GameInfoVo> data;

    public List<GameInfoVo> getData() {
        return data;
    }

    public void setData(List<GameInfoVo> data) {
        this.data = data;
    }

    public void addGameInfo(GameInfoVo gameInfoVo){
        if(data == null){
            data = new ArrayList<>();
        }
        data.add(gameInfoVo);
    }
}
