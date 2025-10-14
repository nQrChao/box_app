package com.zqhy.app.core.data.model.game.new0809;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.new0809.item.LunboDataBeanVo;

import java.util.List;

/**
 * @author Administrator
 * @date 2021/8/13 0013-19:07
 * @description
 */
public class MainFuLiFuDataVo extends BaseVo {

    public MainFuLiFuDataVo.DataBean data;

    public static class DataBean {
        public List<String> module;
        public DataVo       data;

    }

    public static class DataVo {
        public LunboDataBeanVo flf_lunbo;
        public GameDataBeanVo  flf_game;
    }

    public static class GameDataBeanVo extends CommonDataBeanVo {
        public List<MainFuliStyle1Vo> data;
    }
}
