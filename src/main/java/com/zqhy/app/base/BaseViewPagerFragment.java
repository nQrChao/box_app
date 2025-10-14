package com.zqhy.app.base;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.kcrason.dynamicpagerindicatorlibrary.DynamicPagerIndicator;
import com.mvvm.base.AbsViewModel;
import com.mvvm.stateview.LoadingState;
import com.zqhy.app.adapter.DynamicFragmentPagerAdapter;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/8
 */

public abstract class BaseViewPagerFragment<T extends AbsViewModel> extends BaseFragment<T> {

    protected FrameLayout mLlDynamicPager;

    protected ViewPager mViewPager;

    protected DynamicFragmentPagerAdapter mAdapter;

    protected List<BaseFragment> mFragments;

    protected List<String> mTitles;

    protected String[] mArrTitles;

    private LinearLayout mLlRootview;

    private LinearLayout layout_top;

    protected View mIndicatorLine;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_viewpager;
    }

    @Override
    public int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        loadManager.showStateView(LoadingState.class);
        mLlDynamicPager = findViewById(R.id.ll_dynamic_pager);
        mIndicatorLine = findViewById(R.id.indicator_line);
        layout_top = findViewById(R.id.layout_top);

        View pageLayout = createPagerLayout();
        mLlDynamicPager.addView(pageLayout);
        createPagerIndicator();
        mViewPager = getViewById(R.id.view_pager);
        mLlRootview = getViewById(R.id.ll_rootview);

        if (isAddStatusBarLayout()) {
            View mStatusBarLayout = LayoutInflater.from(_mActivity).inflate(R.layout.layout_status_bar, null);
            mLlRootview.addView(mStatusBarLayout, 0);
            if (isSetImmersiveStatusBar()) {
                setImmersiveStatusBar(true);
            }
        }

        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();
    }

    protected LinearLayout getTopLinearLayout(){
        return layout_top;
    }

    protected DynamicPagerIndicator mIndicator;
    protected FrameLayout           mFlIndicatorLayout;

    protected View createPagerLayout() {
        View view = LayoutInflater.from(_mActivity).inflate(R.layout.layout_indicator_common, null);
        mFlIndicatorLayout = view.findViewById(R.id.fl_indicator_layout);
        mIndicator = view.findViewById(R.id.dynamic_pager_indicator);
        return view;
    }

    protected DynamicPagerIndicator createPagerIndicator() {
        return mIndicator;
    }

    /**
     * 是否添加状态栏布局
     *
     * @return
     */
    protected boolean isAddStatusBarLayout() {
        return false;
    }

    /**
     * init adapter
     */
    protected void setAdapter() {
        mTitles.addAll(Arrays.asList(createPageTitle()));
        mAdapter = new DynamicFragmentPagerAdapter(getChildFragmentManager(), createFragments(), createPageTitle());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mTitles.size());
        mIndicator.setViewPager(mViewPager);
    }

    /**
     * create ViewPager title
     *
     * @return String[]
     */
    protected abstract String[] createPageTitle();

    /**
     * create Fragment
     *
     * @return List<BaseFragment>
     */
    protected abstract List<Fragment> createFragments();

    protected void setIndicatorLine(int visibility) {
        if (mIndicatorLine != null) {
            mIndicatorLine.setVisibility(visibility);
        }
    }

}
