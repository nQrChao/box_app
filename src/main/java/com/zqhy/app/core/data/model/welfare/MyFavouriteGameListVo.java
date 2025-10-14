package com.zqhy.app.core.data.model.welfare;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/15
 */

public class MyFavouriteGameListVo extends BaseVo{

    private List<GameInfoVo> data;

    public List<GameInfoVo> getData() {
        return data;
    }

}
