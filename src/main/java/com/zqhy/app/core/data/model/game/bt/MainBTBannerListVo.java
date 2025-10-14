package com.zqhy.app.core.data.model.game.bt;

import com.zqhy.app.core.data.model.mainpage.HomeBTGameIndexVo;

/**
 * @author leeham2734
 * @date 2020/8/20-10:49
 * @description
 */
public class MainBTBannerListVo {
    private HomeBTGameIndexVo.TablePlaque.DataBean item;

    public MainBTBannerListVo(HomeBTGameIndexVo.TablePlaque.DataBean item) {
        this.item = item;
    }

    public HomeBTGameIndexVo.TablePlaque.DataBean getItem() {
        return item;
    }
}
