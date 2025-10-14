package com.zqhy.app.core.data.model.community.integral;

/**
 * @author leeham2734
 * @date 2020/11/25-10:48
 * @description
 */
public class IntegralMallTitleVo {

    private String title;
    private String subTitle;

    public IntegralMallTitleVo(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }
}
