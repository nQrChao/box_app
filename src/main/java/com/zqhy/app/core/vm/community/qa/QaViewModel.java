package com.zqhy.app.core.vm.community.qa;

import android.app.Application;
import androidx.annotation.NonNull;

import com.zqhy.app.core.data.repository.community.qa.QaRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.vm.BaseViewModel;

/**
 * @author Administrator
 */
public class QaViewModel extends BaseViewModel<QaRepository> {

    public QaViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 游戏问题列表
     *
     * @param gameid
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getQaListData(int gameid, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getQaListData(gameid, page, pageCount, onNetWorkListener);
        }
    }

    /**
     * 问题详情
     *
     * @param qid
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getQaDetailData(int qid, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getQaDetailData(qid, page, pageCount, onNetWorkListener);
        }
    }

    /**
     * 用户提问
     *
     * @param gameid
     * @param content
     * @param onNetWorkListener
     */
    public void useAskQuestion(int gameid, String content, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.useAskQuestion(gameid, content, onNetWorkListener);
        }
    }

    /**
     * 用户回答问题
     *
     * @param qid
     * @param content
     * @param onNetWorkListener
     */
    public void userAnswerQuestion(int qid, String content, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.userAnswerQuestion(qid, content, onNetWorkListener);
        }
    }

    /**
     * 用户问答中心数据
     *
     * @param uid
     * @param onNetWorkListener
     */
    public void getUserQaCenterData(int uid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getUserQaCenterData(uid, onNetWorkListener);
        }
    }

    /**
     * 用户问题列表/回答列表
     *
     * @param type              1回答列表 2问题列表
     * @param uid
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getUserQaListData(int type, int uid, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getUserQaListData(type, uid, page, pageCount, onNetWorkListener);
        }
    }

    /**
     * 回答点赞
     *
     * @param aid
     * @param onNetWorkListener
     */
    public void setAnswerLike(int aid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.setAnswerLike(aid, onNetWorkListener);
        }
    }


    /**
     * 问题解决
     *
     * @param qid
     * @param onNetWorkListener
     */
    public void setQaSolve(int qid,OnNetWorkListener onNetWorkListener){
        if (mRepository != null) {
            mRepository.setQaSolve(qid, onNetWorkListener);
        }
    }


    /**
     * 邀请回答
     *
     * @param uid
     * @param onNetWorkListener
     */
    public void getUserCanAnswerQuestionListData(int uid, OnNetWorkListener onNetWorkListener){
        if (mRepository != null) {
            mRepository.getUserCanAnswerQuestionListData(uid, onNetWorkListener);
        }
    }
}
