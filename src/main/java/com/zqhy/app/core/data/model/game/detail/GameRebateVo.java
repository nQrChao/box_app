package com.zqhy.app.core.data.model.game.detail;

/**
 *
 * @author Administrator
 * @date 2018/11/20
 */

public class GameRebateVo {

    private String rebate_content;

    private String rebate_flash_content;

    private int game_type;

    private int max_rate;

    public int getGame_type() {
        return game_type;
    }

    public void setGame_type(int game_type) {
        this.game_type = game_type;
    }

    public String getRebate_content() {
        return rebate_content;
    }

    public void setRebate_content(String rebate_content) {
        this.rebate_content = rebate_content;
    }

    public String getRebate_flash_content() {
        return rebate_flash_content;
    }

    public void setRebate_flash_content(String rebate_flash_content) {
        this.rebate_flash_content = rebate_flash_content;
    }

    public int getMax_rate() {
        return max_rate;
    }

    public void setMax_rate(int max_rate) {
        this.max_rate = max_rate;
    }
}
