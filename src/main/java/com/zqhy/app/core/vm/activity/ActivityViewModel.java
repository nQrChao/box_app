package com.zqhy.app.core.vm.activity;

import android.app.Application;
import androidx.annotation.NonNull;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.core.data.repository.activity.ActivityRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;

/**
 * @author Administrator
 * @date 2018/11/17
 */

public class ActivityViewModel extends AbsViewModel<ActivityRepository> {
    public ActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void getActivityListData(int type, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getActivityListData(type, page, pageCount, onNetWorkListener);
        }
    }

    public void getAnnouncementListData(int type, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if(mRepository != null){
            mRepository.getAnnouncementListData(type, page, pageCount, onNetWorkListener);
        }
    }
}
