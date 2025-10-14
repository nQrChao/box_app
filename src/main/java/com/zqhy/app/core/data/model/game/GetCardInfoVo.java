package com.zqhy.app.core.data.model.game;

import com.zqhy.app.core.data.model.BaseVo;

/**
 *
 * @author Administrator
 * @date 2018/11/21
 */

public class GetCardInfoVo extends BaseVo{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean{
        String card;

        public String getCard() {
            return card;
        }
    }
}
