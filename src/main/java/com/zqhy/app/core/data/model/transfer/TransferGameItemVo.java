package com.zqhy.app.core.data.model.transfer;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/17
 */

public class TransferGameItemVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {

        private int user_points;
        private GameInfoVo gameinfo;

        private List<TransferRewardVo> transfer_reward_list;


        public int getUser_points() {
            return user_points;
        }

        public GameInfoVo getGameinfo() {
            return gameinfo;
        }

        public List<TransferRewardVo> getTransfer_reward_list() {
            return transfer_reward_list;
        }
    }


    public static class TransferRewardVo {
        /**
         * id : 2
         * c1 : 1
         * reward_type : 3
         * reward_content : 测试道具奖励
         * begintime : 0
         * endtime : 0
         * c2_more : 游戏已充值2
         * ex_more :
         */

        private int index_id;
        private int c1;
        private String reward_type;
        private String reward_content;
        private long begintime;
        private long endtime;
        private String c2_more;
        private String ex_more;

        private int rewark_able;
        /**
         * c2 : 0
         * begintime : 0
         * endtime : 0
         */

        private int c2;

        public int getIndex_id() {
            return index_id;
        }

        public void setIndex_id(int index_id) {
            this.index_id = index_id;
        }

        public int getC1() {
            return c1;
        }

        public void setC1(int c1) {
            this.c1 = c1;
        }

        public String getReward_type() {
            return reward_type;
        }

        public void setReward_type(String reward_type) {
            this.reward_type = reward_type;
        }

        public String getReward_content() {
            return reward_content;
        }

        public void setReward_content(String reward_content) {
            this.reward_content = reward_content;
        }

        public long getBegintime() {
            return begintime;
        }

        public void setBegintime(long begintime) {
            this.begintime = begintime;
        }

        public long getEndtime() {
            return endtime;
        }

        public void setEndtime(long endtime) {
            this.endtime = endtime;
        }

        public String getC2_more() {
            return c2_more;
        }

        public void setC2_more(String c2_more) {
            this.c2_more = c2_more;
        }

        public String getEx_more() {
            return ex_more;
        }

        public void setEx_more(String ex_more) {
            this.ex_more = ex_more;
        }

        public int getRewark_able() {
            return rewark_able;
        }

        public void setRewark_able(int rewark_able) {
            this.rewark_able = rewark_able;
        }

        public int getC2() {
            return c2;
        }

        public void setC2(int c2) {
            this.c2 = c2;
        }
    }
}
