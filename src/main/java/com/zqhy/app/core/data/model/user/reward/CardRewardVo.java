package com.zqhy.app.core.data.model.user.reward;

import com.zqhy.app.core.data.model.BaseVo;

public class CardRewardVo extends BaseVo {
    public DataBean data;

    public DataBean getData() {
        return data;
    }

    public class DataBean{
        private int reward;
        private String reward_type;
    }
}
