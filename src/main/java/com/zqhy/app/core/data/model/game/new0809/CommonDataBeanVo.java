package com.zqhy.app.core.data.model.game.new0809;

import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;

/**
 * @author Administrator
 * @date 2021/8/13 0013-19:11
 * @description
 */
public class CommonDataBeanVo {
    public int                   id;
    public String                module_alias;
    public String                module_title;
    public String                module_title_color;
    public String                module_sub_title;
    public String                module_sub_title_color;
    public int                   module_additional_id;
    public XingYouDataJumpInfoVo additional;

    public String                module_title_icon;
    public String                module_title_two;
    public String                module_title_two_color;

    public static class XingYouDataJumpInfoVo extends AppJumpInfoBean {

        public String icon;
        public String title;
        public String text;
        public String textcolor;
        public String text_color;

        public XingYouDataJumpInfoVo(String page_type, ParamBean param) {
            super(page_type, param);
        }
    }
}
