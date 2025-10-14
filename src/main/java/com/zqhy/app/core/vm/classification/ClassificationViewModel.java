package com.zqhy.app.core.vm.classification;

import android.app.Application;
import androidx.annotation.NonNull;

import com.zqhy.app.core.data.repository.classification.ClassificationRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.vm.BaseViewModel;

import java.util.Map;

/**
 * @author Administrator
 * @date 2018/11/9
 */

public class ClassificationViewModel<T extends ClassificationRepository> extends BaseViewModel<T> {

    public ClassificationViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * 获取游戏分类详情
     */
    public void getGameClassificationList(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getGameClassificationList(onNetWorkListener);
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


    /**
     * 获取游戏列表
     * @param params
     * @param onNetWorkListener
     */
    public void getGameList(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getGameList(params, onNetWorkListener);
        }
    }

    /**
     * 获取分类页推荐游戏
     * @param onNetWorkListener
     */
    public void getGameClassificationBastList(OnNetWorkListener onNetWorkListener){
        if (mRepository != null) {
            mRepository.getGameClassificationBastList(onNetWorkListener);
        }
    }

    /**
     * 获取游戏列表
     * @param params
     * @param onNetWorkListener
     */
    public void getGameListJx(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getGameListJx(params, onNetWorkListener);
        }
    }
}
