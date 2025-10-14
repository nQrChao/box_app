package com.zqhy.app.base.collapsing;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.listener.MyAppBarStateChangeListener;

/**
 * @author Administrator
 */
public abstract class BaseCollapsingListFragment<T extends AbsViewModel> extends BaseFragment<T> {


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_collapsing_list;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        bindViews();
    }

    protected FrameLayout mFlListTop;
    protected FrameLayout mFlListBottom;
    protected AppBarLayout mAppBarLayout;
    protected CollapsingToolbarLayout mCollapsing;
    protected FrameLayout mFlCollapsingLayout;
    protected Toolbar mToolbar;
    protected FrameLayout mFlAppbarLayout;
    protected FrameLayout mFlList;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private void bindViews() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setProgressViewOffset(true, -20, 100);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_ff8f19,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        setSwipeRefresh(null);


        mFlListTop = findViewById(R.id.fl_list_top);
        mFlListBottom = findViewById(R.id.fl_list_bottom);

        mAppBarLayout = findViewById(R.id.appBarLayout);
        mCollapsing = findViewById(R.id.collapsing);
        mFlCollapsingLayout = findViewById(R.id.fl_collapsing_layout);
        mToolbar = findViewById(R.id.toolbar);
        mFlAppbarLayout = findViewById(R.id.fl_appbar_layout);
        mFlList = findViewById(R.id.fl_list);

        mAppBarLayout.addOnOffsetChangedListener(new MyAppBarStateChangeListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (i >= 0) {
                    mSwipeRefreshLayout.setEnabled(isCanSwipeRefresh() && true);
                } else {
                    mSwipeRefreshLayout.setEnabled(false);
                }

                float totalRange = appBarLayout.getTotalScrollRange();
                float verticalOffset = Math.abs(i);

                int alpha = Math.round(verticalOffset / totalRange * 255);
                String hex = Integer.toHexString(alpha).toUpperCase();
                if (hex.length() == 1) {
                    hex = "0" + hex;
                }
                String srtColorRes = "#" + hex + "FFFFFF";
                try {
                    onAppBarLayoutOffsetChanged(i, Color.parseColor(srtColorRes));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.onOffsetChanged(appBarLayout, i);
            }

            @Override
            public void onStateChanged(AppBarLayout appBarLayout, MyAppBarStateChangeListener.State state) {
                onAppBarLayoutStateChanged(state);
            }
        });

        View toolBarView = getToolBarView();
        if (toolBarView != null) {
            mToolbar.addView(toolBarView);
        }

        View collapsingView = getCollapsingView();
        if (collapsingView != null) {
            mFlCollapsingLayout.addView(collapsingView);
        }
        setListFragment();
    }

    private void setListFragment() {
        if (isSetListFragment()) {
            Fragment fragmentList = getListView();
            if (fragmentList != null) {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.add(R.id.fl_list, getListView()).commit();
            }
        } else {
            View listLayoutView = getListLayoutView();
            if (listLayoutView != null) {
                mFlList.addView(listLayoutView);
            }
        }
    }

    protected void setToolbarVisibility(int visibility) {
        if (mToolbar != null) {
            mToolbar.setVisibility(visibility);
        }
    }

    /**
     * 是否设置BaseListFragment
     *
     * @return
     */
    protected boolean isSetListFragment() {
        return true;
    }

    /**
     * CollapsingLayout 布局
     *
     * @return
     */
    @NonNull
    protected abstract View getCollapsingView();

    /**
     * ToolBar 布局
     *
     * @return
     */
    @NonNull
    protected abstract View getToolBarView();

    /**
     * List 布局
     *
     * @return
     */
    @NonNull
    protected abstract BaseListFragment getListView();

    /**
     * List layout 布局
     *
     * @return
     */
    protected abstract View getListLayoutView();

    /**
     * mAppBarLayout
     * onOffsetChanged
     *
     * @param i
     * @param resColor
     */
    protected void onAppBarLayoutOffsetChanged(int i, int resColor) {

    }

    /**
     * mAppBarLayout
     * onStateChanged
     *
     * @param state
     */
    protected void onAppBarLayoutStateChanged(MyAppBarStateChangeListener.State state) {

    }

    protected boolean isCanSwipeRefresh() {
        return false;
    }

    /**
     * 设置刷新
     *
     * @param onRefreshListener
     */
    protected void setSwipeRefresh(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        mSwipeRefreshLayout.setEnabled(isCanSwipeRefresh());
        mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }

    /**
     * 设置刷新状态
     *
     * @param refreshing
     */
    protected void setSwipeRefreshing(boolean refreshing) {
        mSwipeRefreshLayout.setRefreshing(refreshing);
    }

}
