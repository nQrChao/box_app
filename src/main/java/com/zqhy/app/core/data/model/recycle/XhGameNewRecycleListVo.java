package com.zqhy.app.core.data.model.recycle;

import android.text.TextUtils;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.utils.CommonUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 */
public class XhGameNewRecycleListVo extends BaseVo {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }


    public static class DataBean implements Serializable {
        private int    gameid;
        private String xh_username;
        private float  sum_rmb_total;
        private String xh_showname;
        private float  total;
        private String gamename;
        private int    game_type;
        private String gameicon;
        private String genre_str;
        private String remark;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getGameid() {
            return gameid;
        }

        public void setGameid(int gameid) {
            this.gameid = gameid;
        }

        public String getXh_username() {
            return xh_username;
        }

        public void setXh_username(String xh_username) {
            this.xh_username = xh_username;
        }

        public float getSum_rmb_total() {
            return sum_rmb_total;
        }

        public void setSum_rmb_total(float sum_rmb_total) {
            this.sum_rmb_total = sum_rmb_total;
        }

        public String getXh_showname() {
            return xh_showname;
        }

        public void setXh_showname(String xh_showname) {
            this.xh_showname = xh_showname;
        }

        public float getTotal() {
            return total;
        }

        public void setTotal(float total) {
            this.total = total;
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

        public int getGame_type() {
            return game_type;
        }

        public void setGame_type(int game_type) {
            this.game_type = game_type;
        }

        public String getGameicon() {
            return gameicon;
        }

        public void setGameicon(String gameicon) {
            this.gameicon = gameicon;
        }

        public String getGenre_str() {
            return genre_str;
        }

        public void setGenre_str(String genre_str) {
            this.genre_str = genre_str;
        }
    }
}
