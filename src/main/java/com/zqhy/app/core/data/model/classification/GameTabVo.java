package com.zqhy.app.core.data.model.classification;

import com.zqhy.app.core.data.model.game.GameNavigationVo;
import com.zqhy.app.core.data.model.tab.SlidingTabVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/24
 */

public class GameTabVo extends GameNavigationVo implements SlidingTabVo {


    /**
     * 1 搜索条件 genre_id
     * 2 搜索条件 order
     * 3 搜索条件 kw
     * 4 搜索条件 has_hd
     */
    private int tab_type;

    private int tabId;

    public GameTabVo(int tab_type) {
        this.tab_type = tab_type;
    }

    public GameTabVo(int tabId, int tab_type) {
        this.tabId = tabId;
        this.tab_type = tab_type;
    }

    private String order;
    private String kw;
    private String has_hd;

    public int getTab_type() {
        return tab_type;
    }

    public void setTab_type(int tab_type) {
        this.tab_type = tab_type;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public String getHas_hd() {
        return has_hd;
    }

    public void setHas_hd(String has_hd) {
        this.has_hd = has_hd;
    }

    public List<String> gameTypeList;

    public void addGameType(String game_type) {
        if (gameTypeList == null) {
            gameTypeList = new ArrayList<>();
        }
        gameTypeList.add(game_type);
    }

    public boolean isContainsGameType(String game_type) {
        if (gameTypeList == null) {
            return false;
        }
        return gameTypeList.contains(game_type);
    }

    @Override
    public String getTabTitle() {
        return getGenre_name();
    }

    @Override
    public int getTabId() {
        if (getType() == 0) {
            return tabId;
        } else {
            return getGenre_id();
        }
    }
}
