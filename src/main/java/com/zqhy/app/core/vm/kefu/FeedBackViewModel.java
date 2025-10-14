package com.zqhy.app.core.vm.kefu;

import android.app.Application;
import androidx.annotation.NonNull;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.core.data.repository.kefu.FeedBackRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;

/**
 *
 * @author Administrator
 * @date 2018/11/11
 */

public class FeedBackViewModel extends AbsViewModel<FeedBackRepository>{

    public FeedBackViewModel(@NonNull Application application) {
        super(application);
    }


    public void getFeedBackCount(OnNetWorkListener onNetWorkListener){
        if(mRepository != null){
            mRepository.getFeedBackCount(onNetWorkListener);
        }
    }

    public void commitFeedBack(String content, String qq_number, String cate_id,OnNetWorkListener onNetWorkListener){
        if(mRepository != null){
            mRepository.commitFeedBack(content,qq_number,cate_id,onNetWorkListener);
        }
    }
}
