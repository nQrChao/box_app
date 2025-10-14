package com.zqhy.app.core.vm.connection;

import android.app.Application;
import androidx.annotation.NonNull;

import com.zqhy.app.core.data.repository.connection.ConnectionRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.vm.BaseViewModel;

/**
 * @author pc
 * @date 2019/12/23-11:50
 * @description
 */
public class ConnectionViewModel extends BaseViewModel<ConnectionRepository> {

    public ConnectionViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * 获取联系方式
     * @param onNetWorkListener
     */
    public void getConnectionInfo(OnNetWorkListener onNetWorkListener){
        if(mRepository != null){
            mRepository.getConnectionInfo(onNetWorkListener);
        }
    }
}
