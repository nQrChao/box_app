package com.zqhy.app.core.data.model.game.detail;

import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/20
 */

public class GameRecommendListVo {

    private List<GameInfoVo.RecommendInfo> recommend_info;
    private int gdm;//1表示GM游戏
    private String gdm_url;

    public int getGdm() {
        return gdm;
    }

    public void setGdm(int gdm) {
        this.gdm = gdm;
    }

    public String getGdm_url() {
        return gdm_url;
    }

    public void setGdm_url(String gdm_url) {
        this.gdm_url = gdm_url;
    }

    public List<GameInfoVo.RecommendInfo> getRecommend_info() {
        return recommend_info;
    }

    public void setRecommend_info(List<GameInfoVo.RecommendInfo> recommend_info) {
        this.recommend_info = recommend_info;
    }
}
