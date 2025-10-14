package com.zqhy.app.core.vm.server;

import android.app.Application;
import androidx.annotation.NonNull;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.core.data.repository.server.ServerRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;

import java.util.Map;

/**
 * @author Administrator
 * @date 2018/11/8
 */

public class ServerViewModel extends AbsViewModel<ServerRepository> {
    public ServerViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * 获取开服表列表
     *
     * @param dt        今天 today  明天 tomorrow  历史 lishi
     * @param page
     * @param pageCount
     */
    public void getServerList(Map<String, String> treeParams, String dt, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getServerList(treeParams, dt, page, pageCount,onNetWorkListener);
        }
    }

    /**
     * 游戏大厅-配置
     */
    public void getGameHallList(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getGameHallList(onNetWorkListener);
        }
    }
}
