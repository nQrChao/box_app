package com.zqhy.app.core.data.model.mainpage.gamealbum;

import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;

/**
 *
 * @author Administrator
 * @date 2018/11/7
 */

public class GameAlbumVo {

    private String pic;
    private String title;
    private String description;
    private String labels;

    private String page_type;
    private AppBaseJumpInfoBean.ParamBean param;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
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
