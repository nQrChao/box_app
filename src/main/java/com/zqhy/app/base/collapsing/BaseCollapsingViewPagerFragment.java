package com.zqhy.app.base.collapsing;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.kcrason.dynamicpagerindicatorlibrary.DynamicPagerIndicator;
import com.mvvm.base.AbsViewModel;
import com.zqhy.app.adapter.DynamicFragmentPagerAdapter;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 */
public abstract class BaseCollapsingViewPagerFragment<T extends AbsViewModel> extends BaseCollapsingListFragment<T> {

    protected DynamicPagerIndicator mDynamicPagerIndicator;

    protected ViewPager mViewPager;

    protected DynamicFragmentPagerAdapter mAdapter;

    protected List<BaseFragment> mFragments;

    protected List<String> mTitles;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        mFlAppbarLayout.addView(createPagerLayout());
        mDynamicPagerIndicator = createPagerIndicator();
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();
    }

    @NonNull
    @Override
    protected BaseListFragment getListView() {
        return null;
    }

    @Override
    protected boolean isSetListFragment() {
        return false;
    }

    @Override
    protected View getListLayoutView() {
        return createViewPager();
    }

    protected DynamicPagerIndicator mIndicator;
    protected FrameLayout mFlIndicatorLayout;

    protected View createPagerLayout() {
        View view = LayoutInflater.from(_mActivity).inflate(R.layout.layout_indicator_common, null);
        mFlIndicatorLayout = view.findViewById(R.id.fl_indicator_layout);
        View lineView = new View(_mActivity);
        lineView.setBackgroundColor(ContextCompat.getColor(_mActivity,R.color.line_color));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int) (1 * density));
        params.gravity = Gravity.BOTTOM;
        lineView.setLayoutParams(params);
        mFlIndicatorLayout.addView(lineView);

        mIndicator = view.findViewById(R.id.dynamic_pager_indicator);
        return view;
    }

    protected DynamicPagerIndicator createPagerIndicator() {
        return mIndicator;
    }

    private ViewPager createViewPager() {
        ViewPager viewPager = new ViewPager(_mActivity);
        viewPager.setId(R.id.mViewPager);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        viewPager.setLayoutParams(params);
        mViewPager = viewPager;
        return viewPager;
    }

    /**
     * init adapter
     */
    protected void setAdapter() {
        mTitles.addAll(Arrays.asList(createPageTitle()));
        mAdapter = new DynamicFragmentPagerAdapter(getChildFragmentManager(), createFragments(), createPageTitle());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mTitles.size());
        mDynamicPagerIndicator.setViewPager(mViewPager);
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
}
