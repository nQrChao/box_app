package com.zqhy.app.core.data.model.chat;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/9
 */

public class ChatActivityRecommendVo extends BaseVo {
    List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean extends AppBaseJumpInfoBean {

        public String name;
        public String icon;

        public DataBean(String page_type, ParamBean param) {
            super(page_type, param);
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
