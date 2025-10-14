package com.zqhy.app.core.data.model.community.comment;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.community.CommunityInfoVo;

import java.util.List;

/**
 * @author Administrator
 */
public class UserCommentInfoVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean{

        private List<CommentInfoVo.DataBean> list;

        private CommunityInfoVo community_info;

        public List<CommentInfoVo.DataBean> getList() {
            return list;
        }

        public void setList(List<CommentInfoVo.DataBean> list) {
            this.list = list;
        }

        public CommunityInfoVo getCommunity_info() {
            return community_info;
        }

        public void setCommunity_info(CommunityInfoVo community_info) {
            this.community_info = community_info;
        }
    }
}
