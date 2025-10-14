package com.zqhy.app.core.vm.game;

import android.app.Application;
import androidx.annotation.NonNull;

import com.zqhy.app.core.data.repository.game.SearchGameRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.vm.classification.ClassificationViewModel;

import java.util.Map;

/**
 * @author Administrator
 */
public class SearchViewModel extends ClassificationViewModel<SearchGameRepository> {

    public SearchViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * 获取搜索数据
     *
     * @param onNetWorkListener
     */
    public void getGameSearchData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getGameSearchData(onNetWorkListener);
        }
    }

    /**
     * 游戏页：排行榜
     * @param api 新游榜 xinbang_page
     *            热游榜 rebang_page
     * @param onNetWorkListener
     */
    public void getMainRankingData(Map<String, String> params, OnNetWorkListener onNetWorkListener){
        if (mRepository != null) {
            mRepository.getMainRankingData(params,onNetWorkListener);
        }
    }

}
