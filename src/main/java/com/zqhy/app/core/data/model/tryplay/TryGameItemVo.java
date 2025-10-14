package com.zqhy.app.core.data.model.tryplay;

import android.text.TextUtils;

import com.zqhy.app.core.data.model.AbsCountDownVo;
import com.zqhy.app.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class TryGameItemVo {

    private List<DataBean> tryGameList;

    public List<DataBean> getTryGameList() {
        return tryGameList;
    }

    public void addTryGameList(DataBean dataBean) {
        if (this.tryGameList == null) {
            this.tryGameList = new ArrayList<>();
        }
        if (dataBean == null) {
            return;
        }
        this.tryGameList.add(dataBean);
    }

    public void addTryGameList(List<DataBean> dataBean) {
        if (this.tryGameList == null) {
            this.tryGameList = new ArrayList<>();
        }
        if (dataBean == null) {
            return;
        }
        this.tryGameList.addAll(dataBean);
    }


    public static class DataBean extends AbsCountDownVo {

        /**
         * begintime : 04月15日 10:00
         * gameicon :
         * gameid : 1903
         * gamename : 屠龙裁决
         * got_total : 0
         * pic : http://p1.ceshi.jiuyao666.com/2019/04/12/5cb03ca8b6acb.png
         * status : 1.0
         * tid : 1
         * total : 780
         */

        private String begintime;
        private String gameicon;
        private int    gameid;
        private String gamename;
        private int    got_total;
        private String pic;

        /**
         * 1:未开启,不用倒计时,
         * 2:72小时内开启,开始倒计时,
         * 3:试玩中(离结束超过48小时). 没有倒计时,
         * 4:48小时内结束, 有倒计时,
         * 5:已结束
         */
        private int    status;
        private int    tid;
        private long   total;
        /**
         * tid : 2
         * gameid : 1895
         * total : 20000
         * endtime : 2019月04日
         * game_type : 1
         * genre_str :
         * count_down : 0
         */

        private String endtime;
        private int    game_type;
        private String genre_str;
        /**
         * 倒计时,单位:秒
         */
        private long   count_down;

        /**
         * "total_reward": "150",
         */
        private int total_reward;


        public String getBegintime() {
            return begintime;
        }

        public void setBegintime(String begintime) {
            this.begintime = begintime;
        }

        public String getGameicon() {
            return gameicon;
        }

        public void setGameicon(String gameicon) {
            this.gameicon = gameicon;
        }

        public int getGameid() {
            return gameid;
        }

        public void setGameid(int gameid) {
            this.gameid = gameid;
        }

        public String getGamename() {
            return CommonUtils.getGamename(gamename);
        }

        public String getOtherGameName(){
            return CommonUtils.getOtherGameName(gamename);
        }

        public void setGamename(String gamename) {
            this.gamename = gamename;
        }

        public int getGot_total() {
            return got_total;
        }

        public void setGot_total(int got_total) {
            this.got_total = got_total;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public int getGame_type() {
            return game_type;
        }

        public void setGame_type(int game_type) {
            this.game_type = game_type;
        }

        public String getGenre_str() {
            return genre_str;
        }

        public void setGenre_str(String genre_str) {
            this.genre_str = genre_str;
        }

        public long getCount_down() {
            return count_down;
        }

        public void setCount_down(long count_down) {
            this.count_down = count_down;
        }

        /**
         * 是否参加试玩
         *
         * @return
         */
        public boolean isJoinInTryGame() {
            // 0: 未参加; 1:已参加
            return status == 1;
        }

        public int getTotal_reward() {
            return total_reward;
        }

        /**
         * @return
         */
        public boolean isTryGameOverdue() {
            // 1:试玩中, -1:已过期
            return status == -1;
        }
    }
}
