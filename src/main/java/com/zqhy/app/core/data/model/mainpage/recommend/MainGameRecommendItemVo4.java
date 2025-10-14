package com.zqhy.app.core.data.model.mainpage.recommend;

import com.zqhy.app.core.data.model.mainpage.MainGameRecommendVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2021/6/7-10:46
 * @description
 */
public class MainGameRecommendItemVo4 {

    private String                               tabNewName;
    private String                               tabHotName;
    private List<MainGameRecommendVo.GameDataVo> mGameNewList;
    private List<MainGameRecommendVo.GameDataVo> mGameHotList;

    public static MainGameRecommendItemVo4 createItem(MainGameRecommendVo.ItemBean bt_new, MainGameRecommendVo.ItemBean bt_hot) {
        MainGameRecommendItemVo4 itemVo4 = new MainGameRecommendItemVo4();
        if (bt_new != null) {
            itemVo4.tabNewName = bt_new.getTitle();
            if (bt_new.getData() != null && bt_new.getData().size() > 0) {
                itemVo4.mGameNewList = bt_new.getData();
            }
        }

        if (bt_hot != null) {
            itemVo4.tabHotName = bt_hot.getTitle();
            if (bt_hot.getData() != null && bt_hot.getData().size() > 0) {
                itemVo4.mGameHotList = bt_hot.getData();
            }
        }
        return itemVo4;
    }

    public List<MainGameRecommendVo.GameDataVo> getGameNewList() {
        return mGameNewList;
    }

    public List<MainGameRecommendVo.GameDataVo> getGameHotList() {
        return mGameHotList;
    }

    public String getTabNewName() {
        return tabNewName;
    }

    public String getTabHotName() {
        return tabHotName;
    }
}
