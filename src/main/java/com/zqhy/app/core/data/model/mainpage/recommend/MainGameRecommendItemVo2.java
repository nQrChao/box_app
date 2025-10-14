package com.zqhy.app.core.data.model.mainpage.recommend;

import com.zqhy.app.core.data.model.mainpage.MainGameRecommendVo;

/**
 * @author leeham2734
 * @date 2021/6/7-10:46
 * @description
 */
public class MainGameRecommendItemVo2 {

    private MainGameRecommendVo.ItemBean mBean;

    public static MainGameRecommendItemVo2 createItem(MainGameRecommendVo.ItemBean itemBean) {
        MainGameRecommendItemVo2 itemVo2 = new MainGameRecommendItemVo2();
        if (itemBean != null) {
            itemVo2.mBean = itemBean;
        }
        return itemVo2;
    }

    public MainGameRecommendVo.ItemBean getBean() {
        return mBean;
    }
}
