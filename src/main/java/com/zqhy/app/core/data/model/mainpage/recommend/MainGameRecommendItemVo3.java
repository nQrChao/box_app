package com.zqhy.app.core.data.model.mainpage.recommend;

import com.zqhy.app.core.data.model.mainpage.MainGameRecommendVo;

/**
 * @author leeham2734
 * @date 2021/6/7-10:46
 * @description
 */
public class MainGameRecommendItemVo3 {


    private MainGameRecommendVo.GameDataVo picItem;
    private MainGameRecommendVo.ItemBean   data;

    public static MainGameRecommendItemVo3 createItem(MainGameRecommendVo.ItemBean coupon_game, MainGameRecommendVo.ItemBean illstration) {
        MainGameRecommendItemVo3 itemVo3 = new MainGameRecommendItemVo3();

        if (illstration != null && illstration.getData() != null && illstration.getData().size() > 0) {
            itemVo3.picItem = illstration.getData().get(0);
        }
        itemVo3.data = coupon_game;

        return itemVo3;
    }

    public MainGameRecommendVo.GameDataVo getPicItem() {
        return picItem;
    }

    public MainGameRecommendVo.ItemBean getData() {
        return data;
    }
}
