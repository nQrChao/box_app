package com.zqhy.app.core.view.main.welfare;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.kcrason.dynamicpagerindicatorlibrary.DynamicPagerIndicator;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.view.activity.ActivityListFragment;
import com.zqhy.app.network.statistics.JiuYaoStatisticsApi;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.sp.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class WelfareMainFragment extends BaseFragment {

    private final String SP_TASK_POP_TIPS = "SP_TASK_POP_TIPS";

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_welfare_main;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private int mTopDefaultColor;
    private int mTopSelectedColor;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        bindViews();
    }

    private DynamicPagerIndicator mDynamicPagerIndicator;
    private SlidingTabLayout mSlidingTabLayout;
    private LinearLayout mLlContentLayout;
    private ViewPager mViewPager;
    private ImageView mIvTaskQuestion;

    private void bindViews() {
        mDynamicPagerIndicator = findViewById(R.id.dynamic_pager_indicator);
        mSlidingTabLayout = findViewById(R.id.sliding_tab_layout);
        mLlContentLayout = findViewById(R.id.ll_content_layout);
        mViewPager = findViewById(R.id.view_pager);
        mIvTaskQuestion = findViewById(R.id.iv_task_question);

        mIvTaskQuestion.setOnClickListener(v -> {
            //点击跳转--等级&积分相关说明
            showUserLevelRule();
            SPUtils spUtils = new SPUtils(_mActivity, Constants.SP_COMMON_NAME);
            spUtils.putBoolean(SP_TASK_POP_TIPS, true);
            if (popupWindow != null) {
                popupWindow.dismiss();
            }
            JiuYaoStatisticsApi.getInstance().eventStatistics(8, 109);
        });
        mIvTaskQuestion.setVisibility(View.GONE);

        ArrayList<Fragment> fragmentList = new ArrayList<>();

//        fragmentList.add(TaskCenterFragment.newInstance(false));
        fragmentList.add(ActivityListFragment.newInstance(3));

//        String[] labels = new String[]{"赚金", "活动"};
        //2019.10.21 去掉“赚金板块”、“等级&积分说明？”入口
        String[] labels = new String[]{"活动推荐"};

        MyPagerAdapter mAdapter = new MyPagerAdapter(getChildFragmentManager(), fragmentList, labels);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(fragmentList.size());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                slidingTabSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mSlidingTabLayout.setViewPager(mViewPager);

        mTopDefaultColor = ContextCompat.getColor(_mActivity, R.color.color_232323);
        mTopSelectedColor = ContextCompat.getColor(_mActivity, R.color.color_232323);
        updateIndicator();

        showPopTips();

        post(() -> slidingTabSelected(0));
    }


    /**
     * 选择Tab
     *
     * @param position
     */
    private void slidingTabSelected(int position) {
        if (mViewPager == null) {
            return;
        }
        int size = 2;
        for (int i = 0; i < size; i++) {
            try {
                TextView indexTextView = mSlidingTabLayout.getTitleView(i);
                if (indexTextView != null) {
                    if (position == i) {
                        //selected
                        indexTextView.getPaint().setFakeBoldText(true);
                        indexTextView.setTextSize(22);
                    } else {
                        //unselected
                        indexTextView.getPaint().setFakeBoldText(false);
                        indexTextView.setTextSize(18);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (popupWindow != null) {
            if (hidden) {
                popupWindow.dismiss();
            } else {
                showPopTips();
            }
        }
    }

    private void updateIndicator() {
        if (mSlidingTabLayout != null) {
            mSlidingTabLayout.setIndicatorColor(mTopSelectedColor);

            mSlidingTabLayout.setTextSelectColor(mTopSelectedColor);
            mSlidingTabLayout.setTextUnselectColor(mTopDefaultColor);
        }
    }

    private boolean isShowPopTips = false;
    PopupWindow popupWindow;

    private void showPopTips() {
        if (!isShowPopTips) {
            return;
        }
        SPUtils spUtils = new SPUtils(_mActivity, Constants.SP_COMMON_NAME);
        boolean task_pop_tips = spUtils.getBoolean(SP_TASK_POP_TIPS);

        if (!task_pop_tips && mIvTaskQuestion.getVisibility() == View.VISIBLE) {
            if (popupWindow == null) {
                popupWindow = new PopupWindow();
                WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.RIGHT;
                ImageView contentView = new ImageView(_mActivity);
                contentView.setImageResource(R.mipmap.img_pop_task_center);

                popupWindow.setContentView(contentView);
                popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(false);
                popupWindow.setOutsideTouchable(false);
                popupWindow.update();
                ColorDrawable dw = new ColorDrawable(0000000000);
                popupWindow.setBackgroundDrawable(dw);
                contentView.setOnClickListener(v -> {
                    mIvTaskQuestion.performClick();
                });
            }
            if (!_mActivity.isFinishing()) {
                popupWindow.showAsDropDown(mIvTaskQuestion, 0, (int) (-6 * density));
            }
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;
        private String[] mTitles;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public MyPagerAdapter(FragmentManager fm, List<Fragment> mFragments, String[] mTitles) {
            super(fm);
            this.mFragments = mFragments;
            this.mTitles = mTitles;
        }

        @Override
        public int getCount() {
            return mFragments == null ? 0 : mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
