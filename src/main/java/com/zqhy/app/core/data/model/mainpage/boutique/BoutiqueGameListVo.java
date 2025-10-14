package com.zqhy.app.core.data.model.mainpage.boutique;


import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/7
 */

public class BoutiqueGameListVo {

    private List<BoutiqueGameVo> data;

    private int game_type;

    public BoutiqueGameListVo(List<BoutiqueGameVo> data) {
        this.data = data;
    }

    public List<BoutiqueGameVo> getData() {
        return data;
    }

    public int getGame_type() {
        return game_type;
    }

    public void setGame_type(int game_type) {
        this.game_type = game_type;
    }
}
