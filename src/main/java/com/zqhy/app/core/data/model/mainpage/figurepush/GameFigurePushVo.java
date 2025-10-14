package com.zqhy.app.core.data.model.mainpage.figurepush;

import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public class GameFigurePushVo {

    private int gameid;
    private String gamename;
    private int game_type;

    private String title;
    private String title2;
    private String title2_color;
    private String pic;

    private String page_type;

    private AppBaseJumpInfoBean.ParamBean param;

    private List<Integer> eventList;

    public void addEvent(int event) {
        if (eventList == null) {
            eventList = new ArrayList<>();
        }
        eventList.add(event);
    }

    public List<Integer> getEventList() {
        return eventList;
    }

    private int eventPosition;

    public void setEventPosition(int eventPosition) {
        this.eventPosition = eventPosition;
    }

    public int getEventPosition() {
        return eventPosition;
    }

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getTitle2_color() {
        return title2_color;
    }

    public void setTitle2_color(String title2_color) {
        this.title2_color = title2_color;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getGame_type() {
        return game_type;
    }

    public void setGame_type(int game_type) {
        this.game_type = game_type;
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
        if ("gameinfo".equals(page_type) && param != null) {
            gameid = param.getGameid();
            game_type = param.getGame_type();
        }
    }


    private boolean isShowAllGameText;

    public void setShowAllGameText(boolean showAllGameText) {
        isShowAllGameText = showAllGameText;
    }

    public boolean showAllGameText(){
        return isShowAllGameText;
    }
}
