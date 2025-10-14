package com.zqhy.app.core.data.model.mainpage;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.banner.BannerVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.mainpage.boutique.BoutiqueGameVo;
import com.zqhy.app.core.data.model.tryplay.TryGameItemVo;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public class HomeGameIndexVo extends BaseVo {

    private DataBean data;

    public static class DataBean {
        /**
         * 2018.07.06
         * <p>
         * 统一字段
         * bt, 折扣, H5首页 添加返回字段
         * 游戏列表最大gameid,,用于判断是否新上架游戏
         */
        private int max_gameid;

        public int getMax_gameid() {
            return max_gameid;
        }

        /**
         * banner列表
         */
        private List<BannerVo> slider_list;


        private List<GameInfoVo> hot_list;
        private List<GameInfoVo> newest_list;

        private List<BoutiqueGameVo> recommend_list;
        private List<BoutiqueGameVo> fuli_list;

        /**
         * 精选列表
         */
        private List<GameInfoVo> selected_list;

        public void setMax_gameid(int max_gameid) {
            this.max_gameid = max_gameid;
        }

        public List<BannerVo> getSlider_list() {
            return slider_list;
        }

        public List<GameInfoVo> getHot_list() {
            return hot_list;
        }

        public List<GameInfoVo> getNewest_list() {
            return newest_list;
        }

        public List<BoutiqueGameVo> getRecommend_list() {
            return recommend_list;
        }


        public List<BoutiqueGameVo> getFuli_list() {
            return fuli_list;
        }

        public List<GameInfoVo> getSelected_list() {
            return selected_list;
        }


        private List<GameInfoVo> play_list;

        public List<GameInfoVo> getPlay_list() {
            return play_list;
        }


        /******2019.04.15新增试玩列表*********************************/

        private List<TryGameItemVo.DataBean> trial_list;

        public List<TryGameItemVo.DataBean> getTrial_list() {
            return trial_list;
        }


        /**
         * 2019.11.26 新增精选合集
         */

        private List<ChoiceListVo.DataBean> choice_list;

        public List<ChoiceListVo.DataBean> getChoice_list() {
            return choice_list;
        }


        /**
         * 2019.11.27 新增精品游戏
         */
        private List<GameInfoVo> rank_list;

        public List<GameInfoVo> getRank_list() {
            return rank_list;
        }

        /**
         * 2020.03.27 新增新游预约
         */
        private List<GameInfoVo> game_appointment_list;

        public List<GameInfoVo> getGame_appointment_list() {
            return game_appointment_list;
        }


        /**
         * 2020.11.23 新增今天新上数据
         */
        private List<GameInfoVo> today_list;


        /**
         * 2020.11.23 新增
         * 所有游戏, 仅所有游戏第一页(12个), 上划请求首页游戏列表接口 index_gamelist
         */
        private List<GameInfoVo> all_list;


        public List<GameInfoVo> getToday_list() {
            return today_list;
        }

        public List<GameInfoVo> getAll_list() {
            return all_list;
        }


        /**
         * 首页插屏,最多7个;
         * 键值分别为position_1,position_2,..position_7;
         * 后面数字对应从上到下的位置
         */
        private HomeBTGameIndexVo.TablePlaque table_plaque;

        public HomeBTGameIndexVo.TablePlaque getTable_plaque() {
            return table_plaque;
        }
    }

    public DataBean getData() {
        return data;
    }


}
