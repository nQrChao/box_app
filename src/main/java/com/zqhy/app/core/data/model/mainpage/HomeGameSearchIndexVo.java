package com.zqhy.app.core.data.model.mainpage;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.search.GameSearchDataVo;

/**
 * @author Administrator
 */
public class HomeGameSearchIndexVo extends BaseVo {

    private IndexVo data;

    public void setData(IndexVo data) {
        this.data = data;
    }

    public IndexVo getData() {
        return data;
    }

    public static class IndexVo{
        private HomeGameIndexVo homeIndexData;

        private GameSearchDataVo gameSearch;


        public HomeGameIndexVo getHomeIndexData() {
            return homeIndexData;
        }

        public void setHomeIndexData(HomeGameIndexVo homeIndexData) {
            this.homeIndexData = homeIndexData;
        }

        public GameSearchDataVo getGameSearch() {
            return gameSearch;
        }

        public void setGameSearch(GameSearchDataVo gameSearch) {
            this.gameSearch = gameSearch;
        }
    }

}
