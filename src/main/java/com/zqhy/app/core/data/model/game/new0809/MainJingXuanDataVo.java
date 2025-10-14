package com.zqhy.app.core.data.model.game.new0809;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.new0809.item.CommonStyle1DataBeanVo;
import com.zqhy.app.core.data.model.game.new0809.item.LunboDataBeanVo;
import com.zqhy.app.core.data.model.game.new0809.item.LunboGameDataBeanVo;
import com.zqhy.app.core.data.model.game.new0809.item.MainMenuVo;
import com.zqhy.app.core.data.model.game.new0809.item.MainPageItemVo;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;

import java.util.List;

/**
 * @author Administrator
 * @date 2021/8/13 0013-19:07
 * @description
 */
public class MainJingXuanDataVo extends BaseVo {

    public MainJingXuanDataVo.DataBean data;

    public static class DataBean {
        public List<String>        module;
        public DataVo              data;
    }

    public static class DataVo {
        public LunboGameDataBeanVo     jx_gamelunbo;
        public MainMenuVo              jx_menu;
        public LunboDataBeanVo         jx_lunbo;
        public CommonStyle1DataBeanVo  jx_topjingxuan;
        public HuaDongDataBeanVo       jx_huadong;
        public HaoYouTuiJianDataBeanVo jx_haoyoutuijian;
        public LunboDataBeanVo         jx_chaping;
        public MainPageItemVo          jx_zidingyi;
        public DanTuiDataBeanVo        jx_dantui;
        public HuoDongTuTuiDataBeanVo  jx_huodong;
        public MoreTuiJianDataBeanVo   jx_moretuijian;
        public JXTabGameListDataBeanVo   jx_tab_gamelist;
    }


    public static class HuaDongDataBeanVo extends CommonDataBeanVo {
        public List<AppJumpInfoBean> data;
    }

    public static class HaoYouTuiJianDataBeanVo extends CommonDataBeanVo {
        public List<GameInfoVo> data;
    }

    public static class DanTuiDataBeanVo extends CommonDataBeanVo {
        public List<GameInfoVo> data;
    }

    public static class DanTuiItemDataBeanVo extends CommonDataBeanVo {
        public GameInfoVo mGameInfoVo;

        public List<GameInfoVo> mGameLisVo;
    }

    public static class HuoDongTuTuiDataBeanVo extends CommonDataBeanVo {
        public List<XingYouDataJumpInfoVo> data;
    }

    public static class HuoDongTuTuiItemDataBeanVo extends CommonDataBeanVo {
        public List<XingYouDataJumpInfoVo> data;
    }

    public static class MoreTuiJianDataBeanVo extends CommonDataBeanVo {
        public List<XingYouDataJumpInfoVo> data;
    }

    public static class JXTabGameListDataBeanVo extends CommonDataBeanVo {
        public List<TabGameListVo> data;
    }

    public static class TabGameListVo{
        public String label;
        public List<GameInfoVo> items;
        public boolean labelSelect;
    }
}
