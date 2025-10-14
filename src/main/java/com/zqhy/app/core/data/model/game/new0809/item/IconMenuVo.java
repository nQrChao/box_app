package com.zqhy.app.core.data.model.game.new0809.item;

import com.zqhy.app.core.data.model.game.new0809.CommonDataBeanVo;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;

import java.util.List;

/**
 * @author Administrator
 * @date 2021/8/9 0009-15:22
 * @description
 */
public class IconMenuVo extends CommonDataBeanVo {
    public List<DataBean> data;

    public static class DataBean extends AppJumpInfoBean {
        public String title;
        public String title_color;
        public String icon;

        public DataBean(String page_type, ParamBean param) {
            super(page_type, param);
        }

    }

}
