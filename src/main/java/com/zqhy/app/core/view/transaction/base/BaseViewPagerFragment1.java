package com.zqhy.app.core.view.transaction.base;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.kcrason.dynamicpagerindicatorlibrary.DynamicPagerIndicator;
import com.mvvm.base.AbsViewModel;
import com.mvvm.stateview.LoadingState;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.zqhy.app.adapter.DynamicFragmentPagerAdapter;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/8
 */

public abstract class BaseViewPagerFragment1<T extends AbsViewModel> extends BaseFragment<T> {

//    protected FrameLayout mLlDynamicPager;
    protected FixedIndicatorView indicator;

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
        return R.layout.fragment_viewpager1;
    }

    @Override
    public int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        loadManager.showStateView(LoadingState.class);
//        mLlDynamicPager = findViewById(R.id.ll_dynamic_pager);
        indicator = findViewById(R.id.tab_indicator);
        mIndicatorLine = findViewById(R.id.indicator_line);
        layout_top = findViewById(R.id.layout_top);

//        View pageLayout = createPagerLayout();
//        mLlDynamicPager.addView(pageLayout);
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
    public int        tabCurrentItem = 0;

    protected View createPagerLayout() {
        View view = LayoutInflater.from(_mActivity).inflate(R.layout.layout_indicator_common1, null);
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
//        mAdapter = new DynamicFragmentPagerAdapter(getChildFragmentManager(), createFragments(), createPageTitle());
//        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mTitles.size());
        mTabAdapter = new TabAdapter(getChildFragmentManager(), createFragments(),  createPageTitle());
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(indicator, mViewPager);
        indicatorViewPager.setOnIndicatorPageChangeListener((preItem, currentItem) -> {
            Log.d("TransactionFragment1","setOnIndicatorPageChangeListener-----currentItem"+currentItem);
            Log.d("TransactionFragment1","setOnIndicatorPageChangeListener-----preItem"+preItem);
            tabCurrentItem = currentItem;
            selectTabPager(mTabAdapter, currentItem);
        });

        indicatorViewPager.setAdapter(mTabAdapter);

        indicator.post(() -> {
            indicatorViewPager.setCurrentItem(1, false);
            selectTabPager(mTabAdapter, 1);
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(2);
//        int dynamicPagerHeight = 50;

//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        int topMargin = (int) (dynamicPagerHeight * density) + ScreenUtil.getStatusBarHeight(_mActivity);
//        params.setMargins(0, topMargin, 0, 0);

//        mViewPager.setLayoutParams(params);
    }

    private int mTopDefaultColor = Color.parseColor("#FFFFFF");

    private int mTopSelectedColor = Color.parseColor("#0079FB");

    private void selectTabPager(TabAdapter mTabAdapter, int currentItem) {
        if (mTabAdapter.getmTabViewMap() != null) {
            for (Integer key : mTabAdapter.getmTabViewMap().keySet()) {
                View itemView = (View) mTabAdapter.getmTabViewMap().get(key);
                TextView mTvIndicator = itemView.findViewById(R.id.tv_indicator);
                View mLineIndicator = itemView.findViewById(R.id.line_indicator);
                mTvIndicator.getPaint().setFakeBoldText(key == currentItem);
                mTvIndicator.setTextColor(Color.parseColor(key == currentItem?"#232323":"#9b9b9b"));
//                mTvIndicator.getPaint().setFakeBoldText(true);
                RelativeLayout.LayoutParams mTvParams = (RelativeLayout.LayoutParams) mTvIndicator.getLayoutParams();
                if (mTvParams == null) {
                    mTvParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                }
                mTvParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                mTvIndicator.setLayoutParams(mTvParams);


                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (30 * density), (int) (4 * density));
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                mLineIndicator.setLayoutParams(params);

                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(8 * density);
                gd.setColor(key == currentItem ? mTopSelectedColor : mTopDefaultColor);
                mLineIndicator.setBackground(gd);
            }
        }
    }

    private TabAdapter mTabAdapter;
    class TabAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        private List<Fragment> mFragmentList;
        private String[]       mTitles;

        private HashMap<Integer, View> mTabViewMap;


        public TabAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList, String[] mTitles) {
            super(fragmentManager);
            mFragmentList = fragmentList;
            this.mTitles = mTitles;
            mTabViewMap = new HashMap<>();
        }

        @Override
        public int getCount() {
            return mTitles == null ? 0 : mTitles.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_tab_main1, container, false);
            }
            TextView mTvIndicator = convertView.findViewById(R.id.tv_indicator);
            mTvIndicator.setText(mTitles[position]);
            mTvIndicator.setMinWidth((int) (32 * density));
            mTvIndicator.setGravity(Gravity.CENTER);
            mTabViewMap.put(position, convertView);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            Log.d("TransactionFragment1","getFragmentForPage-----position"+position);
            return mFragmentList.get(position);
        }

        public HashMap<Integer, View> getmTabViewMap() {
            return mTabViewMap;
        }
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
