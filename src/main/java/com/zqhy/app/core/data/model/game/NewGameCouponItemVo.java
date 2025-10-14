package com.zqhy.app.core.data.model.game;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/21
 */

public class NewGameCouponItemVo extends BaseVo{

    private List<GameInfoVo.CouponListBean> data;
    private GameInfoVo gameInfoVo;

    public GameInfoVo getGameInfoVo() {
        return gameInfoVo;
    }

    public void setGameInfoVo(GameInfoVo gameInfoVo) {
        this.gameInfoVo = gameInfoVo;
    }

    public List<GameInfoVo.CouponListBean> getData() {
        return data;
    }

    public void setData(List<GameInfoVo.CouponListBean> data) {
        this.data = data;
    }
}
