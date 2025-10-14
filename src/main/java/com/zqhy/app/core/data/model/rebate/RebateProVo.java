package com.zqhy.app.core.data.model.rebate;

/**
 *
 * @author Administrator
 * @date 2018/11/16
 */

public class RebateProVo {

    private int pro_id;
    private String pro_title;
    private String pro_description;

    public int getPro_id() {
        return pro_id;
    }

    public String getPro_title() {
        return pro_title;
    }

    public String getPro_description() {
        return pro_description;
    }

    @Override
    public String toString() {
        return "BtRebateProBean{" +
                "pro_id=" + pro_id +
                ", pro_title='" + pro_title + '\'' +
                ", pro_description='" + pro_description + '\'' +
                '}';
    }
}
