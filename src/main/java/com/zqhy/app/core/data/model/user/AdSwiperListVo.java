package com.zqhy.app.core.data.model.user;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/11/25-15:33
 * @description
 */
public class AdSwiperListVo extends BaseVo {

    private List<AdSwiperBean> data;

    public List<AdSwiperBean> getData() {
        return data;
    }

    public void setData(List<AdSwiperBean> data) {
        this.data = data;
    }

    public static class AdSwiperBean{
        private String page_type;
        private String pic;
        private String                        jump_target;
        private AppBaseJumpInfoBean.ParamBean param;

        public String getPage_type() {
            return page_type;
        }

        public void setPage_type(String page_type) {
            this.page_type = page_type;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
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
