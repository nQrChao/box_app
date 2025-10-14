package com.zqhy.app.core.vm.user.welfare;

import android.app.Application;
import androidx.annotation.NonNull;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.core.data.repository.user.welfare.MyGiftCardRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;

/**
 *
 * @author Administrator
 * @date 2018/11/15
 */

public class MyGiftCardViewModel extends AbsViewModel<MyGiftCardRepository>{
    public MyGiftCardViewModel(@NonNull Application application) {
        super(application);
    }

    public void getMyGiftCardData(int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if(mRepository != null){
            mRepository.getMyGiftCardData(page,pageCount,onNetWorkListener);
        }
    }
}
