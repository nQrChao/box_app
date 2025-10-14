package com.zqhy.app.core.data.model;

import com.zqhy.app.core.data.model.game.new0809.AppMenuVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2021/8/16-10:51
 * @description
 */
public class AppMenuBeanVo {
    public List<AppMenuVo> home_menu;
    public List<AppMenuVo> paihang_menu;


    public AppMenuBeanVo(List<AppMenuVo> home_menu, List<AppMenuVo> paihang_menu) {
        this.home_menu = home_menu;
        this.paihang_menu = paihang_menu;
    }
}
