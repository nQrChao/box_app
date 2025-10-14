package com.zqhy.app.core.data.model.game;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/9
 */

public class GameHallJxHomeVo extends BaseVo{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean{
        private List<GameInfoVo> game_recommend_list;
        private List<CollectionListBean> collection_list;

        public List<GameInfoVo> getGame_recommend_list() {
            return game_recommend_list;
        }

        public void setGame_recommend_list(List<GameInfoVo> game_recommend_list) {
            this.game_recommend_list = game_recommend_list;
        }

        public List<CollectionListBean> getCollection_list() {
            return collection_list;
        }

        public void setCollection_list(List<CollectionListBean> collection_list) {
            this.collection_list = collection_list;
        }
    }

    public class CollectionListBean{
        private String cid;
        private String jump_target;
        private String client_type;
        private String show_start_time;
        private String show_end_time;
        private String sort;
        private String page_type;
        private AppBaseJumpInfoBean.ParamBean param;
        private String title;
        private List<GameInfoVo> game_list;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getJump_target() {
            return jump_target;
        }

        public void setJump_target(String jump_target) {
            this.jump_target = jump_target;
        }

        public String getClient_type() {
            return client_type;
        }

        public void setClient_type(String client_type) {
            this.client_type = client_type;
        }

        public String getShow_start_time() {
            return show_start_time;
        }

        public void setShow_start_time(String show_start_time) {
            this.show_start_time = show_start_time;
        }

        public String getShow_end_time() {
            return show_end_time;
        }

        public void setShow_end_time(String show_end_time) {
            this.show_end_time = show_end_time;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getPage_type() {
            return page_type;
        }

        public void setPage_type(String page_type) {
            this.page_type = page_type;
        }

        public AppBaseJumpInfoBean.ParamBean getParam() {
            return param;
        }

        public void setParam(AppBaseJumpInfoBean.ParamBean param) {
            this.param = param;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<GameInfoVo> getGame_list() {
            return game_list;
        }

        public void setGame_list(List<GameInfoVo> game_list) {
            this.game_list = game_list;
        }
    }
}
