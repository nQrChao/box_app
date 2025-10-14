package com.zqhy.app.core.data.model.game.new0809;

import com.google.gson.annotations.SerializedName;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.new0809.item.LunboDataBeanVo;
import com.zqhy.app.core.data.model.game.new0809.item.MainMenuVo;

import java.util.List;

/**
 * @author Administrator
 * @date 2021/8/13 0013-19:07
 * @description
 */
public class MainTuiJianDataVo extends BaseVo {

    public MainTuiJianDataVo.DataBean data;

    public static class DataBean {
        public List<String>      module;
        public List<ModuleStyle> module_style;
        public DataVo            data;
    }

    public static class DataVo {
        public MainMenuVo           tj_menu;
        public LunboDataBeanVo      tj_lunbo;
        public CommonGameDataBeanVo tj_remenyouxi;
        public CommonGameDataBeanVo tj_mianfei;
        public TabGameDataBeanVo    tj_tab;
    }


    public static class CommonGameDataBeanVo extends CommonDataBeanVo {
        public List<GameInfoVo> data;
    }

    public static class TabGameDataBeanVo extends CommonDataBeanVo {
        public List<MainFuliStyle1Vo> data;
    }


    public static class ModuleStyle {
        public static final String TYPE_A = "a";
        public static final String TYPE_B = "b";
        public static final String TYPE_C = "c";

        public String api;
        @SerializedName("name")
        public String style;
    }

}
