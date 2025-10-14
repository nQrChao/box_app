package com.zqhy.app.core.view.main;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.adapter.AdapterPool;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.game.GameSearchFragment;
import com.zqhy.app.network.statistics.JiuYaoStatisticsApi;
import com.zqhy.app.newproject.R;

import cn.jzvd.JZMediaManager;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdMgr;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public abstract class MainGameListFragment<T extends AbsViewModel> extends AbsMainGameListFragment<T> {

    public static final String SP_MAIN_PAGER = "SP_MAIN_PAGER";
    public static final String MAX_GAME_ID   = "MAX_GAME_ID";



    protected int page      = 1;
    protected int pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return AdapterPool.newInstance().getMainGameListAdapter(_mActivity)
                .setTag(R.id.tag_fragment, this)
                .setTag(R.id.tag_sub_fragment, this);
    }


    private LinearLayoutManager linearLayoutManager;

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        linearLayoutManager = new LinearLayoutManager(_mActivity);
        return linearLayoutManager;
    }

    private ImageView mIvListAction1;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        mIvListAction1 = findViewById(R.id.iv_list_action_1);
        mIvListAction1.setOnClickListener(view -> {
            FragmentHolderActivity.startFragmentInActivity(_mActivity, new GameSearchFragment());
            switch (game_type) {
                case 1:
                    JiuYaoStatisticsApi.getInstance().eventStatistics(1, 13);
                    break;
                case 2:
                    JiuYaoStatisticsApi.getInstance().eventStatistics(2, 32);
                    break;
                case 3:
                    JiuYaoStatisticsApi.getInstance().eventStatistics(3, 50);
                    break;
                case 4:
                    JiuYaoStatisticsApi.getInstance().eventStatistics(4, 64);
                    break;
                default:
                    break;
            }
        });

        //2019.05.29 划出屏幕释放JZ，同时也是不开启列表划出显示小窗
        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                try {
                    Jzvd jzvd = view.findViewById(R.id.video_player);
                    if (jzvd != null && jzvd.jzDataSource.containsTheUrl(JZMediaManager.getCurrentUrl())) {
                        Jzvd currentJzvd = JzvdMgr.getCurrentJzvd();
                        if (currentJzvd != null && currentJzvd.currentScreen != Jzvd.SCREEN_WINDOW_FULLSCREEN) {
                            Jzvd.releaseAllVideos();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onRecyclerViewScrolled() {
//        actionSearchView(linearLayoutManager, mIvListAction1);
    }

    protected void actionSearchView(LinearLayoutManager layoutManager, ImageView mIvActionSearch) {
        if (layoutManager == null) {
            return;
        }
        int position = layoutManager.findFirstVisibleItemPosition();
        int searchHeight = (int) ((30 + 14 * 2) * ScreenUtil.getScreenDensity(_mActivity));
        if (position <= 1) {
            View firstVisibleChildView = layoutManager.findViewByPosition(position);
            if (firstVisibleChildView != null) {
                int firstVisibleChildViewTop = firstVisibleChildView.getTop();
                if (searchHeight > (0 - firstVisibleChildViewTop)) {
                    mIvActionSearch.setVisibility(View.GONE);
                } else {
                    mIvActionSearch.setVisibility(View.VISIBLE);
                }
            }
        } else {
            mIvActionSearch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getNetData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        getNetData();
    }


    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getNetData();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (page < 0) {
            return;
        }
        getMoreNewData();
    }

    @Override
    public void onEvent(EventCenter eventCenter) {
        super.onEvent(eventCenter);
        if (eventCenter.getEventCode() == EventConfig.REFRESH_TRY_GAME_LAST_ID_EVENT_CODE) {
            notifyData();
        }
    }

    /**
     * 获取首页第一页数据
     */
    protected abstract void getNetData();

    /**
     * 获取更多数据
     */
    protected abstract void getMoreNewData();


    public int getGame_type() {
        return game_type;
    }


}
