package com.zqhy.app.core.data.model.game;

import com.zqhy.app.core.data.model.community.comment.CommentInfoVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2022/4/11-9:59
 * @description
 */
public class GameShortCommentVo {
    /**
     * 短评列表
     */
    private List<CommentInfoVo.DataBean> short_comment_list;

    public List<CommentInfoVo.DataBean> getShort_comment_list() {
        return short_comment_list;
    }

    public void setShort_comment_list(List<CommentInfoVo.DataBean> short_comment_list) {
        this.short_comment_list = short_comment_list;
    }
}
