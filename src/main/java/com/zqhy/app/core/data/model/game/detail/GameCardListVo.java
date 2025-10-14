package com.zqhy.app.core.data.model.game.detail;

import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.List;

/**
 * Created by Administrator on 2018/11/20.
 */

public class GameCardListVo {

    private List<GameInfoVo.CardlistBean> cardlist;
    private int selectListType = 0;//0免费礼包 1充值礼包
    private int user_already_commented;//当前用户是否已经点评此游戏  1：点评了； 0：未点评
    private boolean isFold = false;//免费礼包列表是否展开
    private boolean isOtherFold = false;//充值礼包列表是否展开

    public boolean isOtherFold() {
        return isOtherFold;
    }

    public void setOtherFold(boolean otherFold) {
        isOtherFold = otherFold;
    }

    public boolean isFold() {
        return isFold;
    }

    public void setFold(boolean fold) {
        isFold = fold;
    }

    public int getSelectListType() {
        return selectListType;
    }

    public void setSelectListType(int selectListType) {
        this.selectListType = selectListType;
    }

    public int getUser_already_commented() {
        return user_already_commented;
    }

    public void setUser_already_commented(int user_already_commented) {
        this.user_already_commented = user_already_commented;
    }

    public List<GameInfoVo.CardlistBean> getCardlist() {
        return cardlist;
    }

    public void setCardlist(List<GameInfoVo.CardlistBean> cardlist) {
        this.cardlist = cardlist;
    }
}
