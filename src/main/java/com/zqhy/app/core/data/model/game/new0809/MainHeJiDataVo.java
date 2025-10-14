package com.zqhy.app.core.data.model.game.new0809;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.List;

/**
 * @author Administrator
 * @date 2021/8/14 0014-15:45
 * @description
 */
public class MainHeJiDataVo extends BaseVo {

    public DataBean data;


    public static class DataBean {
        public String           sub_title;
        public String           title;
        public String           pic;
        public String           labels;
        public int              game_type;
        public String           description;
        public String bg_color;
        public String title_color;
        public String title_border_color;
        public List<GameInfoVo> list;

    }
}
