package com.zqhy.app.core.data.model.mainpage.navigation;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameNavigationVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/7
 */

public class NewGameNavigationListVo extends BaseVo{
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean{
        private List<GameNavigationVo> genre_list;
        private List<SearchListBean> search_list;
        private int game_type;

        public DataBean(List<GameNavigationVo> genre_list, List<SearchListBean> search_list) {
            this.genre_list = genre_list;
            this.search_list = search_list;
        }

        public List<GameNavigationVo> getGenre_list() {
            return genre_list;
        }

        public void setGenre_list(List<GameNavigationVo> genre_list) {
            this.genre_list = genre_list;
        }

        public List<SearchListBean> getSearch_list() {
            return search_list;
        }

        public void setSearch_list(List<SearchListBean> search_list) {
            this.search_list = search_list;
        }

        public int getGame_type() {
            return game_type;
        }

        public void setGame_type(int game_type) {
            this.game_type = game_type;
        }

    }

    public class SearchListBean{
        private String game_type;
        private List<String> word;
        private String visible_word;
        private String search_word;

        public String getVisible_word() {
            return visible_word;
        }

        public void setVisible_word(String visible_word) {
            this.visible_word = visible_word;
        }

        public String getSearch_word() {
            return search_word;
        }

        public void setSearch_word(String search_word) {
            this.search_word = search_word;
        }

        public String getGame_type() {
            return game_type;
        }

        public void setGame_type(String game_type) {
            this.game_type = game_type;
        }

        public List<String> getWord() {
            return word;
        }

        public void setWord(List<String> word) {
            this.word = word;
        }
    }
}
