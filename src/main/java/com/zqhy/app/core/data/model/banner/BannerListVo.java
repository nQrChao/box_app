package com.zqhy.app.core.data.model.banner;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public class BannerListVo {

    public final int INDICATOR_LOCATION_INSIDE_BOTTOM_RIGHT   = 1;
    public final int INDICATOR_LOCATION_OUTSIDE_BOTTOM_CENTER = 2;


    private List<BannerVo> data;

    private int game_type;

    public BannerListVo(List<BannerVo> data) {
        this.data = data;
    }

    public BannerListVo(List<BannerVo> data, int itemHeightDp) {
        this.data = data;
        this.itemHeightDp = itemHeightDp;
    }

    public List<BannerVo> getData() {
        return data;
    }



    public int     itemHeightDp;
    public boolean isLoop             = true;
    public int     INDICATOR_LOCATION = INDICATOR_LOCATION_INSIDE_BOTTOM_RIGHT;
}
