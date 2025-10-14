package com.zqhy.app.core.data.model.game.detail;

import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/20
 */

public class GameDesVo {

    /**
     * 游戏介绍
     */
    private String game_description;

    /**
     * 游戏截屏,多个
     */
    private List<String> screenshot;

    /**
     * 视频封面图
     */
    private String video_pic;
    /**
     * 视频播放地址
     */
    private String video_url;

    private GameInfoVo.VipNews vipNews;

    private String data_exchange;//双端数据互通 0 未设置 1 互通 2 不互通

    private int game_model;//1 横屏 2 竖屏   0 未知

    public int getGame_model() {
        return game_model;
    }

    public void setGame_model(int game_model) {
        this.game_model = game_model;
    }

    public GameInfoVo.VipNews getVipNews() {
        return vipNews;
    }

    public void setVipNews(GameInfoVo.VipNews vipNews) {
        this.vipNews = vipNews;
    }

    public String getGame_description() {
        return game_description;
    }

    public void setGame_description(String game_description) {
        this.game_description = game_description;
    }

    public List<String> getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(List<String> screenshot) {
        this.screenshot = screenshot;
    }

    public String getVideo_pic() {
        return video_pic;
    }

    public void setVideo_pic(String video_pic) {
        this.video_pic = video_pic;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getData_exchange() {
        return data_exchange;
    }

    public void setData_exchange(String data_exchange) {
        this.data_exchange = data_exchange;
    }
}
