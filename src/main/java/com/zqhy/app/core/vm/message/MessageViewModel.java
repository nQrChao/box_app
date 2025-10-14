package com.zqhy.app.core.vm.message;

import android.app.Application;
import androidx.annotation.NonNull;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.core.data.repository.message.MessageRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;

/**
 * @author Administrator
 * @date 2018/11/13
 */

public class MessageViewModel extends AbsViewModel<MessageRepository> {

    public MessageViewModel(@NonNull Application application) {
        super(application);
    }


    public void getAdBannerData( OnNetWorkListener onNetWorkListener) {
        if(mRepository != null){
            mRepository.getAdBannerData(onNetWorkListener);
        }
    }

    public void getKefuMessageData(int maxID, OnNetWorkListener onNetWorkListener) {
        if(mRepository != null){
            mRepository.getKefuMessageData(maxID,onNetWorkListener);
        }
    }

    public void getDynamicGameMessageData(long logTime,OnNetWorkListener onNetWorkListener) {
        if(mRepository != null){
            mRepository.getDynamicGameMessageData(logTime,onNetWorkListener);
        }
    }


    /**
     * 互动消息
     *
     * @param page
     * @param pageCount
     */
    public void getCommentMessages(int page,int pageCount,OnNetWorkListener onNetWorkListener){
        if(mRepository != null){
            mRepository.getCommentMessages(page,pageCount,onNetWorkListener);
        }
    }
}
