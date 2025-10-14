package com.zqhy.app.core.data.model.banner;

import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public class BannerVo {


    /**
     * game_type : 2
     * jump_target : 8319
     * lb_sort : 101
     * page_type : gameinfo
     * param : {"game_type":"2","gameid":"8319"}
     * pic : http://p1.btgame01.com/2018/11/07/5be23b8c041d7.jpg
     * type : 3
     */

    private int game_type;
    private String jump_target;
    private String lb_sort;
    private String page_type;
    private AppBaseJumpInfoBean.ParamBean param;
    private String pic;
    private String type;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getGame_type() {
        return game_type;
    }

    public String getJump_target() {
        return jump_target;
    }

    public String getLb_sort() {
        return lb_sort;
    }

    public String getPic() {
        return pic;
    }

    public String getType() {
        return type;
    }

    public AppBaseJumpInfoBean getJumpInfo() {
        return new AppBaseJumpInfoBean(page_type, param);
    }

    public void setGame_type(int game_type) {
        this.game_type = game_type;
    }

    public void setJump_target(String jump_target) {
        this.jump_target = jump_target;
    }


    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPage_type(String page_type) {
        this.page_type = page_type;
    }

    public void setParam(AppBaseJumpInfoBean.ParamBean param) {
        this.param = param;
    }
}
