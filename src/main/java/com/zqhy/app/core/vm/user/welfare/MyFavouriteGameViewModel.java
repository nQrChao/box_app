package com.zqhy.app.core.vm.user.welfare;

import android.app.Application;
import androidx.annotation.NonNull;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.core.data.repository.user.welfare.MyFavouriteGameRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;

/**
 *
 * @author Administrator
 * @date 2018/11/15
 */

public class MyFavouriteGameViewModel extends AbsViewModel<MyFavouriteGameRepository>{
    public MyFavouriteGameViewModel(@NonNull Application application) {
        super(application);
    }

    public void getMyFavouriteGameData(int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if(mRepository != null){
            mRepository.getMyFavouriteGameData(page,pageCount,onNetWorkListener);
        }
    }

    public void setGameUnFavorite(int gameid, OnNetWorkListener onNetWorkListener) {
        if(mRepository != null){
            mRepository.setGameUnFavorite(gameid,onNetWorkListener);
        }
    }
}
