package com.zqhy.app.core.data.model.user;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/11/25-17:30
 * @description
 */
public class CommunityDayTaskListVo extends BaseVo {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean {

        private int id;
        private String  name;
        private int integral;
        private int super_user_reward;
        private String  description;
        private String  status;
        private String content;
        private String btn_label;
        private String                        page_type;
        private AppBaseJumpInfoBean.ParamBean param;

        public String getBtn_label() {
            return btn_label;
        }

        public void setBtn_label(String btn_label) {
            this.btn_label = btn_label;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public int getSuper_user_reward() {
            return super_user_reward;
        }

        public void setSuper_user_reward(int super_user_reward) {
            this.super_user_reward = super_user_reward;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
