package com.zqhy.app.core.vm.game;

import android.app.Application;

import androidx.annotation.NonNull;

import com.zqhy.app.core.data.repository.game.ForumRepository;
import com.zqhy.app.core.data.repository.game.GameRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.vm.BaseViewModel;

import java.io.File;
import java.util.Map;

public class ForumViewModel extends BaseViewModel<ForumRepository> {

    public ForumViewModel(@NonNull Application application) {
        super(application);
    }

    public void getCategoryData( OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCategoryData(onNetWorkListener);
        }
    }
    public void forumReport(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.forumReport(params,onNetWorkListener);
        }
    }
    public void forumReplyReport(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.forumReplyReport(params,onNetWorkListener);
        }
    }
    public void categoryPush(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.categoryPush(params,onNetWorkListener);
        }
    }

    public void forumDetailList(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.forumDetailList(params,onNetWorkListener);
        }
    }
    public void forumReplyTopList(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.forumReplyTopList(params,onNetWorkListener);
        }
    }

    public void forumReplySubList(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.forumReplySubList(params,onNetWorkListener);
        }
    }
    public void forumReplyTopLike(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.forumReplyTopLike(params,onNetWorkListener);
        }
    }
      public void forumReplyLike(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.forumReplyLike(params,onNetWorkListener);
        }
    }

    public void forumReply(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.forumReply(params,onNetWorkListener);
        }
    }
    public void forumReplyRelease(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.forumReplyRelease(params,onNetWorkListener);
        }
    }

    public void imageUpload(String flag, File file, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.imageUpload(flag,file,onNetWorkListener);
        }
    }

}
