package com.zqhy.app.core.data.model.game;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */

public class GameCollectionVo extends BaseVo{


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean{


        /**
         * title : 变态游戏合集①
         * pic : http://p1.btgame01.com/2018/07/13/5b4817ec824e7.png
         * labels : 双11福利,狂欢ing,超值活动
         * description : 亲爱的友友们，一年一度双11，福利不落单！除了平台狂欢活动，乐嗨嗨为心爱的为你准备了超值返利，精彩线下活动！单身午夜别徘徊，尽情乐开怀~
         * game_type : 1
         */

        private String title;
        private String pic;
        private String labels;
        private String description;
        private int game_type;

        private List<GameInfoVo> list;

        private int max_rate;

        private String game_summary;

        private GameInfoVo.GameLabelsBean first_label;

        public String getTitle() {
            return title;
        }

        public String getPic() {
            return pic;
        }

        public String getLabels() {
            return labels;
        }

        public String getDescription() {
            return description;
        }

        public int getGame_type() {
            return game_type;
        }

        public List<GameInfoVo> getList() {
            return list;
        }
    }

}
