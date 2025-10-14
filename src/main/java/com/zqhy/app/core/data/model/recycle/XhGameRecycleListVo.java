package com.zqhy.app.core.data.model.recycle;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.List;

/**
 * @author Administrator
 */
public class XhGameRecycleListVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }


    public static class DataBean{
        private List<GameInfoVo> game_list;

        private List<XhGameRecycleVo> game_xh_list;

        public List<GameInfoVo> getGame_list() {
            return game_list;
        }

        public List<XhGameRecycleVo> getGame_xh_list() {
            return game_xh_list;
        }
    }
}
