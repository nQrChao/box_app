package com.zqhy.app.core.data.model.community;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.community.comment.CommentInfoVo;

import java.util.List;

/**
 * @author Administrator
 */
public class CommunityUserListV2Vo extends BaseVo {

    private List<CommentInfoVo.DataBean> data;

    public List<CommentInfoVo.DataBean> getData() {
        return data;
    }

    public void setData(List<CommentInfoVo.DataBean> data) {
        this.data = data;
    }
}
