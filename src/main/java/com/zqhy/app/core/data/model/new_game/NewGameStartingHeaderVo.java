package com.zqhy.app.core.data.model.new_game;

import com.zqhy.app.core.data.model.game.coupon.GameCouponsListVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/8/24-12:18
 * @description
 */
public class NewGameStartingHeaderVo {

    private List<GameCouponsListVo.DataBean> mList;

    public NewGameStartingHeaderVo() {
    }

    public NewGameStartingHeaderVo(List<GameCouponsListVo.DataBean> list) {
        mList = list;
    }

    public void setList(List<GameCouponsListVo.DataBean> list) {
        mList = list;
    }

    public List<GameCouponsListVo.DataBean> getList() {
        return mList;
    }
}
