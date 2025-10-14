package com.zqhy.app.core.data.model.game.new0809;

import com.google.gson.Gson;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.new0809.item.LunboDataBeanVo;
import com.zqhy.app.core.data.model.game.new0809.item.MainMenuVo;
import com.zqhy.app.core.data.model.game.new0809.item.MainPageItemVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author leeham2734
 * @date 2021/8/23-16:44
 * @description
 */
public class XyTabDataVo extends BaseVo {

    public XyTabDataVo.DataBean data;

    public static class DataBean {
        public List<MenuDataBean> menu;
    }

    public static class MenuDataBean{
        private String label;
        private String api;
        private String page_type;
        private AppBaseJumpInfoBean.ParamBean param;

        private boolean labelSelect;

        public boolean isLabelSelect() {
            return labelSelect;
        }

        public void setLabelSelect(boolean labelSelect) {
            this.labelSelect = labelSelect;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getApi() {
            return api;
        }

        public void setApi(String api) {
            this.api = api;
        }

        public String getPage_type() {
            return page_type;
        }

        public void setPage_type(String page_type) {
            this.page_type = page_type;
        }

        public AppBaseJumpInfoBean.ParamBean getParam() {
            return param;
        }

        public void setParam(AppBaseJumpInfoBean.ParamBean param) {
            this.param = param;
        }
    }
}
