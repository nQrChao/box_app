package com.zqhy.app.core.data.model.game.new0809;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.banner.BannerVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.List;

/**
 * @author Administrator
 * @date 2021/8/14 0014-16:55
 * @description
 */
public class MainGameRankDataVo extends BaseVo {

    public DataBean data;

    public static class DataBean{
        public CoverBeanVo cover;
        public List<GameInfoVo> list;
        public List<TabBean> ranking;
        public String type;
    }

    public static class CoverBeanVo{
        public String pic;
    }

    public static class TabBean{
        public String type;
        public String label;
        public boolean isLabelSelect;
    }

}


