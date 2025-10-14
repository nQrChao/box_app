package com.zqhy.app.core.data.model.game.coupon;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/8/28-13:47
 * @description
 */
public class GameStartingListVo extends BaseVo {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        private List<GameCouponsListVo.DataBean> coupon_data;
        private List<GameDataVo>                 game_data;

        public List<GameCouponsListVo.DataBean> getCoupon_data() {
            return coupon_data;
        }

        public void setCoupon_data(List<GameCouponsListVo.DataBean> coupon_data) {
            this.coupon_data = coupon_data;
        }

        public List<GameDataVo> getGame_data() {
            return game_data;
        }

        public void setGame_data(List<GameDataVo> game_data) {
            this.game_data = game_data;
        }
    }

    public static class GameDataVo {
        private String           name;
        private List<GameInfoVo> list;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<GameInfoVo> getList() {
            return list;
        }

        public void setList(List<GameInfoVo> list) {
            this.list = list;
        }
    }


}
