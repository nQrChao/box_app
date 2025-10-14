package com.zqhy.app.core.data.model.game.new0809;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.new0809.item.CommonStyle1DataBeanVo;
import com.zqhy.app.core.data.model.game.new0809.item.LunboDataBeanVo;
import com.zqhy.app.core.data.model.game.new0809.item.MainMenuVo;
import com.zqhy.app.core.data.model.game.new0809.item.MainPageItemVo;

import java.util.List;

/**
 * @author Administrator
 * @date 2021/8/13 0013-9:29
 * @description
 */
public class MainXingYouDataVo extends BaseVo {

    public DataBean data;

    public static class DataBean {
        public List<String> module;
        public DataVo       data;

    }

    public static class DataVo {
        public LunboDataBeanVo           xy_lunbo;
        public MainMenuVo                xy_menu;
        public RenQiDataBeanVo           xy_renqi;
        public MainPageItemVo            xy_zhongbang;
        public ZuiXingShangJiaDataBeanVo xy_zuixinshangjia;
        public LunboDataBeanVo           xy_chaping;
        public HaoYouTuiJianDataBeanVo   xy_haoyoutuijian;
        public CommonStyle1DataBeanVo    xy_mianfeiwan;
        public MainPageItemVo            xy_gengduohaowan;
        public GamePicSliderDataBeanVo   xy_game_pic_slider;
        public CurrentRecommendDataBeanVo   xy_current_recommend;
    }


    public static class RenQiDataBeanVo extends CommonDataBeanVo {
        public List<GameInfoVo> data;
    }


    public static class ZuiXingShangJiaDataBeanVo extends CommonDataBeanVo {
        public List<GameInfoVo> data;
    }

    public static class HaoYouTuiJianDataBeanVo extends CommonDataBeanVo {
        public List<GameInfoVo> data;
    }

    public static class GamePicSliderDataBeanVo extends CommonDataBeanVo {
        public List<GameInfoVo> data;
    }

    public static class CurrentRecommendDataBeanVo extends CommonDataBeanVo {
        public List<GameInfoVo> data;
    }
}
