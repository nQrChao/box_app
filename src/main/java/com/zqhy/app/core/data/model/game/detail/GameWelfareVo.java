package com.zqhy.app.core.data.model.game.detail;

import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.utils.CommonUtils;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/20
 */

public class GameWelfareVo {


    private int gameid;
    private String benefit_content;
    private float coupon_amount;

    private List<GameInfoVo.GameLabelsBean> game_labels;

    private int game_type;

    private int game_model;//1 横屏 2 竖屏   0 未知
    private String data_exchange;//双端数据互通 0 未设置 1 互通 2 不互通

    public GameWelfareVo(int gameid,String benefit_content,float coupon_amount) {
        this.gameid = gameid;
        this.benefit_content = benefit_content;
        this.coupon_amount = coupon_amount;
    }

    public List<GameInfoVo.GameLabelsBean> getGame_labels() {
        return game_labels;
    }

    public void setGame_labels(List<GameInfoVo.GameLabelsBean> game_labels) {
        this.game_labels = game_labels;
    }

    public String getBenefit_content() {
        return CommonUtils.getGamename(benefit_content);
    }

    public void setBenefit_content(String benefit_content) {
        this.benefit_content = benefit_content;
    }

    public float getCoupon_amount() {
        return coupon_amount;
    }

    public void setCoupon_amount(float coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public int getGame_type() {
        return game_type;
    }

    public void setGame_type(int game_type) {
        this.game_type = game_type;
    }

    public String getData_exchange() {
        return data_exchange;
    }

    public void setData_exchange(String data_exchange) {
        this.data_exchange = data_exchange;
    }

    public int getGame_model() {
        return game_model;
    }

    public void setGame_model(int game_model) {
        this.game_model = game_model;
    }
}
