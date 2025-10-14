package com.zqhy.app.core.data.model.user;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/21
 */

public class VipCouponListVo extends BaseVo{

    private List<GameInfoVo> data;

    public List<GameInfoVo> getData() {
        return data;
    }
}
