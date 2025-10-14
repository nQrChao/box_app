package com.zqhy.app.core.data.model.chat;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.utils.CommonUtils;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/9
 */

public class ChatRecommendVo extends BaseVo{
    List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public class DataBean {

        private int gameid;
        private String gameicon;
        private String gamename;
        private String team_name;
        private int play_count;

        public int getPlay_count() {
            return play_count;
        }

        public void setPlay_count(int play_count) {
            this.play_count = play_count;
        }

        public String getTeam_name() {
            return team_name;
        }

        public void setTeam_name(String team_name) {
            this.team_name = team_name;
        }

        public int getGameid() {
            return gameid;
        }

        public void setGameid(int gameid) {
            this.gameid = gameid;
        }

        public String getGameicon() {
            return gameicon;
        }

        public void setGameicon(String gameicon) {
            this.gameicon = gameicon;
        }

        public String getGamename() {
            return CommonUtils.getGamename(gamename);
        }

        public void setGamename(String gamename) {
            this.gamename = gamename;
        }
    }
}
