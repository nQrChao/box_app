package com.zqhy.app.core.data.model;

/**
 * @author leeham2734
 * @date 2021/9/8-14:50
 * @description
 */
public class RealNameCheckVo extends BaseVo {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        public int realname_state;
    }
}
