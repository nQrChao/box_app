package com.zqhy.app.core.view.main;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.base.AbsViewModel;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.mainpage.HomeBTGameIndexVo;
import com.zqhy.app.core.data.model.mainpage.figurepush.GameFigurePushVo;
import com.zqhy.app.core.data.model.splash.AppStyleConfigs;
import com.zqhy.app.core.data.model.splash.SplashVo;
import com.zqhy.app.utils.cache.ACache;
import com.zqhy.app.utils.sdcard.SdCardManager;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @author leeham2734
 * @date 2020/8/19-18:11
 * @description
 */
public abstract class AbsMainGameListFragment<T extends AbsViewModel> extends BaseListFragment<T> {

    protected int game_type;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        //2018.06.25 首页增加列表滑动跟随底部导航显示隐藏
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                onRecyclerViewScrolled();
                int newState = recyclerView.getScrollState();
                if (newState != RecyclerView.SCROLL_STATE_DRAGGING) {

                }
                onRecyclerViewScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    hideToolbar();
                }

                if (dy < 0) {
                    showToolbar();
                }
            }
        });
    }

    @Override
    protected String getStateEventTag() {
        return String.valueOf(game_type);
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_MAIN_PAGE_STATE;
    }

    protected void onRecyclerViewScrolled() {
    }

    /**
     * 跳转主页面-游戏
     *
     * @param index 0 分类  1 开服
     */
    public void goToMainGamePage(int index) {
        if (getParentFragment() != null && getParentFragment() instanceof MainFragment) {
            if (_mActivity instanceof MainActivity) {
                ((MainActivity) _mActivity).goToMainGamePage(index);
            }
        }
    }

    /**
     * 跳转主页面-游戏-分类
     *
     * @param game_genre_id
     */
    public void goToMainGamePageByGenreId(int game_type, int game_genre_id) {
        if (getParentFragment() != null && getParentFragment() instanceof MainFragment) {
            if (_mActivity instanceof MainActivity) {
                ((MainActivity) _mActivity).goToMainGamePageByGenreId(game_type, game_genre_id);
            }
        }
    }

    /**
     * 设置App主题样式
     * 首页顶部样式
     */
    protected void setAppAdBanner() {
        File file = SdCardManager.getFileMenuDir(_mActivity);
        if (file == null) {
            return;
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            String json = ACache.get(file).getAsString(AppStyleConfigs.JSON_KEY);

            Type listType = new TypeToken<SplashVo.AppStyleVo.DataBean>() {
            }.getType();
            Gson gson = new Gson();
            SplashVo.AppStyleVo.DataBean appStyleVo = gson.fromJson(json, listType);

            if (appStyleVo != null) {
                if (appStyleVo.getInterstitial() != null) {
                    addData(appStyleVo.getInterstitial());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected GameFigurePushVo createGameFigurePushVo(HomeBTGameIndexVo.TablePlaque.DataBean item) {
        return createGameFigurePushVo(item, false);
    }

    protected GameFigurePushVo createGameFigurePushVo(HomeBTGameIndexVo.TablePlaque.DataBean item, boolean showAllGameText) {
        GameFigurePushVo gameFigurePushVo = new GameFigurePushVo();
        gameFigurePushVo.setPage_type(item.getPage_type());
        gameFigurePushVo.setParam(item.getParam());

        gameFigurePushVo.setPic(item.getPic());
        gameFigurePushVo.setTitle(item.getTitle());
        gameFigurePushVo.setTitle2(item.getSubtitle());
        gameFigurePushVo.setTitle2_color("#FF4E5E");
        gameFigurePushVo.setShowAllGameText(showAllGameText);
        return gameFigurePushVo;
    }

    protected boolean checkCollectionNotEmpty(Collection collection) {
        return collection != null && !collection.isEmpty();
    }
}
