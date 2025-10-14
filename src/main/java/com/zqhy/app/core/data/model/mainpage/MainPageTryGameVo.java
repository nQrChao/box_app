package com.zqhy.app.core.data.model.mainpage;

import com.zqhy.app.core.data.model.tryplay.TryGameItemVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/11/24-10:33
 * @description
 */
public class MainPageTryGameVo {
    private String title;
    private List<TryGameItemVo.DataBean> mInfoVoList;

    public MainPageTryGameVo(String title, List<TryGameItemVo.DataBean> infoVoList) {
        this.title = title;
        mInfoVoList = infoVoList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TryGameItemVo.DataBean> getInfoVoList() {
        return mInfoVoList;
    }

    public void setInfoVoList(List<TryGameItemVo.DataBean> infoVoList) {
        mInfoVoList = infoVoList;
    }
}
