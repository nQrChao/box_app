package com.zqhy.app.core.data.model.mainpage;

import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/26
 */

public class H5PlayedVo {
    private List<GameInfoVo> data;

    public H5PlayedVo(List<GameInfoVo> data) {
        this.data = data;
    }

    public List<GameInfoVo> getData() {
        return data;
    }

}
