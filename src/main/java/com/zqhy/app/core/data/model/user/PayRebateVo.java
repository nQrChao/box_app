package com.zqhy.app.core.data.model.user;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/11/25-15:33
 * @description
 */
public class PayRebateVo extends BaseVo {

    private PayRebateBean data;

    public PayRebateBean getData() {
        return data;
    }

    public void setData(PayRebateBean data) {
        this.data = data;
    }

    public static class PayRebateBean{
        private List<SliderBean> slider;
        private int pay_rate;

        public List<SliderBean> getSlider() {
            return slider;
        }

        public void setSlider(List<SliderBean> slider) {
            this.slider = slider;
        }

        public int getPay_rate() {
            return pay_rate;
        }

        public void setPay_rate(int pay_rate) {
            this.pay_rate = pay_rate;
        }
    }

    public static class SliderBean{
        private String client_type;
        private String pic;
        private String sort;
        private String page_type;
        private String jump_target;
        private AppBaseJumpInfoBean.ParamBean param;

        public String getClient_type() {
            return client_type;
        }

        public void setClient_type(String client_type) {
            this.client_type = client_type;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getPage_type() {
            return page_type;
        }

        public void setPage_type(String page_type) {
            this.page_type = page_type;
        }

        public String getJump_target() {
            return jump_target;
        }

        public void setJump_target(String jump_target) {
            this.jump_target = jump_target;
        }

        public AppBaseJumpInfoBean.ParamBean getParam() {
            return param;
        }

        public void setParam(AppBaseJumpInfoBean.ParamBean param) {
            this.param = param;
        }
    }
}
