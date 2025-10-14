package com.zqhy.app.core.data.model.game.new0809;

import com.google.gson.annotations.SerializedName;
import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.List;

/**
 * @author Administrator
 * @date 2021/8/11 0011-16:15
 * @description
 */
public class MainFuliStyle1Vo {

    public  String         title;
    @SerializedName("gamelist")
    public  List<DataBean> data_list;
    private int            lastIndexItemGameId;
    private boolean        hasChangeItemGameId;


    public int getLastIndexItemGameId() {
        if (hasChangeItemGameId) {
            return lastIndexItemGameId;
        } else {
            return getDefaultItemGameId();
        }
    }

    public void setLastIndexItemGameId(int lastIndexItemGameId) {
        if (this.lastIndexItemGameId == lastIndexItemGameId) {
            return;
        }
        hasChangeItemGameId = true;
        this.lastIndexItemGameId = lastIndexItemGameId;
    }

    private int getDefaultItemGameId() {
        if (data_list == null || data_list.isEmpty()) {
            return 0;
        }
        return data_list.get(0).getGameid();
    }

    public static class DataBean extends GameInfoVo {
        public String game_icon_big_color    = "#140079FB";
        public String game_icon_middle_color = "#260079FB";
        public int    start_num              = 5;
        public String style;
    }
}
