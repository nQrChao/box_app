package com.zqhy.app.core.data.model.mainpage.recommend;

import com.zqhy.app.core.data.model.mainpage.MainGameRecommendVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2021/6/7-10:46
 * @description
 */
public class MainGameRecommendItemVo1 {


    private List<MainGameRecommendVo.GameDataVo> iconMenu;
    private List<MainGameRecommendVo.GameDataVo> sliderBanner;


    public static MainGameRecommendItemVo1 createItem(MainGameRecommendVo.ItemBean icon_menu, MainGameRecommendVo.ItemBean slider) {
        MainGameRecommendItemVo1 itemVo1 = new MainGameRecommendItemVo1();

        if (icon_menu != null && icon_menu.getData() != null && icon_menu.getData().size() > 0) {
            itemVo1.iconMenu = icon_menu.getData();
        }
        if (slider != null && slider.getData() != null && slider.getData().size() > 0) {
            itemVo1.sliderBanner = slider.getData();
        }

        return itemVo1;
    }

    public List<MainGameRecommendVo.GameDataVo> getIconMenu() {
        return iconMenu;
    }

    public void setIconMenu(List<MainGameRecommendVo.GameDataVo> iconMenu) {
        this.iconMenu = iconMenu;
    }

    public List<MainGameRecommendVo.GameDataVo> getSliderBanner() {
        return sliderBanner;
    }

    public void setSliderBanner(List<MainGameRecommendVo.GameDataVo> sliderBanner) {
        this.sliderBanner = sliderBanner;
    }
}
