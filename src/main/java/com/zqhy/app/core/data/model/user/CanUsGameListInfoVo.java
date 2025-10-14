package com.zqhy.app.core.data.model.user;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.List;

public class CanUsGameListInfoVo extends BaseVo {
    public List<GameInfoVo> data;

    public List<GameInfoVo> getData() {
        return data;
    }
}
