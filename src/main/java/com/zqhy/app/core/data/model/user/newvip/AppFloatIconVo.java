package com.zqhy.app.core.data.model.user.newvip;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.new0809.XinRenPopDataVo;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;
import com.zqhy.app.core.data.model.version.AppPopDataVo;
import com.zqhy.app.core.data.model.version.UnionVipDataVo;
import com.zqhy.app.core.data.model.version.VersionVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2021/2/3-12:11
 * @description
 */
public class AppFloatIconVo extends BaseVo {

    private DataBean data;

    public void setData(DataBean data) {
        this.data = data;
    }

    public DataBean getData() {
        return data;
    }

    public static class DataBean extends AppJumpInfoBean {
        public DataBean(String page_type, ParamBean param) {
            super(page_type, param);
        }
        private String show_start_time;
        private String visible_tgid;
        private String show_end_time;

        public String getShow_start_time() {
            return show_start_time;
        }

        public void setShow_start_time(String show_start_time) {
            this.show_start_time = show_start_time;
        }

        public String getVisible_tgid() {
            return visible_tgid;
        }

        public void setVisible_tgid(String visible_tgid) {
            this.visible_tgid = visible_tgid;
        }

        public String getShow_end_time() {
            return show_end_time;
        }

        public void setShow_end_time(String show_end_time) {
            this.show_end_time = show_end_time;
        }
    }
}
