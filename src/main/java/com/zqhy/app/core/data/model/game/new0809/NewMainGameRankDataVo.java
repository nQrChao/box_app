package com.zqhy.app.core.data.model.game.new0809;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.List;

/**
 * @author Administrator
 * @date 2021/8/14 0014-16:55
 * @description
 */
public class NewMainGameRankDataVo extends BaseVo {

    public DataBean data;

    public static class DataBean{
        public List<TabBean> ranking;
    }

    public static class TabBean{
        public String type;
        public String label;
        public String api;
        public boolean isLabelSelect;
    }

}


