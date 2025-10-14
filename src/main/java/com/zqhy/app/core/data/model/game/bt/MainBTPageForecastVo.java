package com.zqhy.app.core.data.model.game.bt;

import com.zqhy.app.core.data.model.mainpage.HomeBTGameIndexVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/8/19-17:13
 * @description
 */
public class MainBTPageForecastVo {

    public MainBTPageForecastVo(List<HomeBTGameIndexVo.ReserveVo> data) {
        this.data = data;
    }

    private List<HomeBTGameIndexVo.ReserveVo> data;

    public List<HomeBTGameIndexVo.ReserveVo> getData() {
        return data;
    }
}
