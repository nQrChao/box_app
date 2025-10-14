package com.zqhy.app.core.data.model.mainpage;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;

import java.util.List;

/**
 * @author pc
 * @date 2019/11/26-17:49
 * @description
 */
public class ChoiceListVo extends BaseVo {

    private List<DataBean> data;

    private int game_type;

    public int getGame_type() {
        return game_type;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public void setGame_type(int game_type) {
        this.game_type = game_type;
    }

    public static class DataBean extends AppBaseJumpInfoBean {
        private String pic;
        private String label_name;
        private String title;
        private String gamename;
        private String top_label_name;

        private int game_type;

        public DataBean(String page_type, ParamBean param) {
            super(page_type, param);
        }


        public String getPic() {
            return pic;
        }

        public String getLabel_name() {
            return label_name;
        }

        public String getTitle() {
            return title;
        }

        public String getGamename() {
            return gamename;
        }

        public String getTop_label_name() {
            return top_label_name;
        }

        public int getGame_type() {
            return game_type;
        }

        public void setGame_type(int game_type) {
            this.game_type = game_type;
        }
    }

}
