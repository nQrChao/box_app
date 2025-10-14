package com.zqhy.app.core.data.model.game.search;

import android.text.TextUtils;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;
import com.zqhy.app.utils.CommonUtils;

import java.util.List;

/**
 * @author Administrator
 */
public class GameSearchDataVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean{

        private List<GameSearchDataVo.SearchJumpInfoVo> s_best;

        private SearchJumpInfoVo s_best_pic;

        private List<String> search_hot_word;

        public List<String> getSearch_hot_word() {
            return search_hot_word;
        }

        public void setSearch_hot_word(List<String> search_hot_word) {
            this.search_hot_word = search_hot_word;
        }

        public List<GameSearchDataVo.SearchJumpInfoVo> getS_best() {
            return s_best;
        }

        public SearchJumpInfoVo getS_best_pic() {
            return s_best_pic;
        }

        private String s_best_title;

        public String getS_best_title() {
            return s_best_title;
        }

        private String s_best_title_show;

        public String getS_best_title_show() {
            return s_best_title_show;
        }
    }

    public static class SearchJumpInfoVo extends AppJumpInfoBean {

        /**
         * game_type : 1
         * pic : http://p1.ceshi.jiuyao666.com/2019/06/18/5d08a8d9db7d0.jpg
         * jump_target : 1886
         * client_type : 3
         */
        private String icon;
        private String game_type;
        private String jump_target;
        private String client_type;
        /**
         * title : 测试
         * t_type : 1
         * title2 : 测试2
         * param : {"gameid":"1886","game_type":"1"}
         */

        private String title;
        private int t_type;
        private String title2;

        public SearchJumpInfoVo(String page_type, ParamBean param) {
            super(page_type, param);
        }

        public String getGame_type() {
            return game_type;
        }

        public String getJump_target() {
            return jump_target;
        }

        public String getClient_type() {
            return client_type;
        }

        public String getTitle() {
            return CommonUtils.getGamename(title);
        }

        public int getT_type() {
            return t_type;
        }

        public String getTitle2() {
            return title2;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
