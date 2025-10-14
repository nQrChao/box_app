package com.zqhy.app.core.data.model.welfare;

import com.zqhy.app.core.data.model.BaseVo;

/**
 *
 * @author Administrator
 * @date 2018/11/15
 */

public class MyCouponRecordStatVo extends BaseVo{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean{
        private String game;
        private String platform;
        private String history;

        public String getGame() {
            return game;
        }

        public void setGame(String game) {
            this.game = game;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getHistory() {
            return history;
        }

        public void setHistory(String history) {
            this.history = history;
        }
    }
}
