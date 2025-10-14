package com.zqhy.app.core.vm.community.comment;

import android.app.Application;
import androidx.annotation.NonNull;
import android.text.TextUtils;

import com.zqhy.app.core.data.repository.community.comment.CommentRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.vm.BaseViewModel;
import com.zqhy.app.network.utils.Base64;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 */
public class CommentViewModel extends BaseViewModel<CommentRepository> {

    public CommentViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * 发布点评
     *
     * @param gameid
     * @param commentContent
     * @param cid
     * @param localPathList
     * @param onNetWorkListener
     */
    public void submitComment(String type_id,String gameid, String commentContent, String cid, List<File> localPathList, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            Map<String, String> params = new TreeMap<>();

            params.put("api", "comment_add");
            params.put("content", Base64.encode(commentContent.getBytes()));
            params.put("client_type", "1");
            params.put("gameid", gameid);
            if (!TextUtils.isEmpty(cid)) {
                params.put("cid", cid);
            }
            params.put("type_id",type_id);

            Map<String, File> fileParams = new TreeMap<>();
            if (localPathList != null) {
                for (int i = 0; i < localPathList.size(); i++) {
                    fileParams.put("upload_pic" + (i + 1), localPathList.get(i));
                }
            }

            mRepository.submitComment(params, fileParams, onNetWorkListener);
        }
    }

    /**
     * 修改点评
     *
     * @param cid
     * @param onNetWorkListener
     */
    public void modifyComment(String cid, OnNetWorkListener onNetWorkListener) {
    }


    /**
     * 删除评论已上传图片
     *
     * @param cid
     * @param pic_id
     * @param onNetWorkListener
     */
    public void deleteCommentPic(String cid, String pic_id, OnNetWorkListener onNetWorkListener) {
    }

    /**
     * 获取评论详情信息 (第一页)
     *
     * @param cid
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getCommentDetailData(int cid, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCommentDetailData(cid, page, pageCount, onNetWorkListener);
        }
    }


    /**
     * 获取评论详情回复列表信息（第二页）
     *
     * @param cid
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getCommentReplyData(int cid, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCommentReplyData(cid, page, pageCount, onNetWorkListener);
        }
    }


    /**
     * 评论点赞
     *
     * @param cid
     * @param onNetWorkListener
     */
    public void setCommentLike(int cid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.setCommentLike(cid, onNetWorkListener);
        }
    }

    /**
     * 评论-回复点赞
     *
     * @param rid
     * @param onNetWorkListener
     */
    public void setReplyLike(int rid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.setReplyLike(rid, onNetWorkListener);
        }

    }


    /**
     * 回复评论（评论回复）
     *
     * @param cid
     * @param content
     * @param rid               rid = 0 表示直接回复评论
     * @param onNetWorkListener
     */
    public void setCommentReply(int cid, String content, int rid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.setCommentReply(cid, content, rid, onNetWorkListener);
        }
    }

    /**
     * 获取用户点评中心
     *
     * @param user_id   被访问的用户
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getUserCommentData(int user_id, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getUserCommentData(user_id, page, pageCount, onNetWorkListener);
        }
    }


    /**
     * 获取点评分类
     *
     * @param onNetWorkListener
     */
    public void getCommentType(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCommentType(onNetWorkListener);
        }
    }
}
