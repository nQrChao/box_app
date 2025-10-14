package com.zqhy.app.core.data.model.mainpage;

import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/11/24-10:19
 * @description
 */
public class MainPageMoreLikeGameVo {
    private String           title;
    private List<GameInfoVo> mInfoVoList;

    public MainPageMoreLikeGameVo(String title, List<GameInfoVo> infoVoList) {
        this.title = title;
        mInfoVoList = infoVoList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<GameInfoVo> getInfoVoList() {
        return mInfoVoList;
    }

    public void setInfoVoList(List<GameInfoVo> infoVoList) {
        mInfoVoList = infoVoList;
    }
}
