package com.zqhy.app.network.demo;


import android.app.Application;
import androidx.annotation.NonNull;

import com.mvvm.base.AbsViewModel;

/**
 *
 * @author Administrator
 * @date 2018/11/6
 */

public class NewWorkViewModel extends AbsViewModel<NewWorkRepository> {

    public NewWorkViewModel(@NonNull Application application) {
        super(application);
    }


    public void execute(String params) {
        mRepository.execute(params);
    }
}
