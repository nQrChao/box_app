package com.zqhy.app.core.data.model.game.bt;

import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.new0809.CommonDataBeanVo;
import com.zqhy.app.core.data.model.game.new0809.MainJingXuanDataVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/8/19-18:25
 * @description
 */
public class MainBTPageGameVo {

    private List<GameInfoVo> mGameInfoVoList;

    private String mainTitle;
    private String mainTitleColor;

    private boolean isLoad = false;

    private int game_type;

    public MainJingXuanDataVo.HaoYouTuiJianDataBeanVo jx_haoyoutuijian;

    public String getMainTitle() {
        return mainTitle;
    }

    public MainBTPageGameVo setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
        return this;
    }

    public MainBTPageGameVo setMainTitleColor(String mainTitleColor) {
        this.mainTitleColor = mainTitleColor;
        return this;
    }

    public List<GameInfoVo> getGameInfoVoList() {
        return mGameInfoVoList;
    }

    public MainBTPageGameVo setGameInfoVoList(List<GameInfoVo> gameInfoVoList) {
        mGameInfoVoList = gameInfoVoList;
        return this;
    }

    public MainBTPageGameVo setLoad(boolean isLoad) {
        this.isLoad = isLoad;
        return this;
    }

    public boolean isLoad() {
        return isLoad;
    }

    public MainBTPageGameVo setGame_type(int game_type) {
        this.game_type = game_type;
        return this;
    }

    public int getGame_type() {
        return game_type;
    }

    private int rowSize;

    public int getRowSize() {
        return rowSize;
    }

    public MainBTPageGameVo setRowSize(int rowSize) {
        this.rowSize = rowSize;
        return this;
    }

    public CommonDataBeanVo.XingYouDataJumpInfoVo additional;

    public MainBTPageGameVo setAdditional(CommonDataBeanVo.XingYouDataJumpInfoVo additional) {
        this.additional = additional;
        return this;
    }


    public int pageIndex;
}
