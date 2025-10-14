package com.zqhy.app.core.view.main.new_game;

import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author leeham2734
 * @date 2020/8/24-11:28
 * @description
 */
public class NewGameMainFragment extends BaseFragment {

    public static NewGameMainFragment newInstance(int index) {
        NewGameMainFragment fragment = new NewGameMainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "新游";
    }

    @Override

    public int getLayoutResId() {
        return R.layout.fragment_main_new_game;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }


    private int index;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            index = getArguments().getInt("index", 0);
        }
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("新游");
        setTitleBottomLine(View.GONE);
        bindViews();
    }


    private FixedIndicatorView mTabServerIndicator;
    private ViewPager          mViewPager;

    private void bindViews() {
        mTabServerIndicator = findViewById(R.id.tab_server_indicator);
        mViewPager = findViewById(R.id.view_pager);

        setViewPager();
    }

    private TabAdapter mTabAdapter;

    private void setViewPager() {
        Resources res = getResources();

        float unSelectSize = 17;
        float selectSize = 17;

        int selectColor = res.getColor(R.color.color_232323);
        int unSelectColor = res.getColor(R.color.color_949494);

        ArrayList<Fragment> fragmentList = new ArrayList<>();
        //fragmentList.add(new NewGameStartingFragment());
        fragmentList.add(new NewGameTopListFragment());
        fragmentList.add(new NewGameAppointmentFragment());

        String[] labels = new String[]{"    一周新游top10    ", "  预约  "};
        mViewPager.setOffscreenPageLimit(fragmentList.size());
        mTabServerIndicator.setSplitMethod(FixedIndicatorView.SPLITMETHOD_WRAP);
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(mTabServerIndicator, mViewPager);
        mTabAdapter = new TabAdapter(getChildFragmentManager(), fragmentList, labels);
        mTabServerIndicator.setOnTransitionListener(new OnTransitionTextListener() {
            @Override
            public TextView getTextView(View tabItemView, int position) {
                return tabItemView.findViewById(R.id.tv_indicator);
            }
        }.setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));
        indicatorViewPager.setOnIndicatorPageChangeListener((preItem, currentItem) -> {
            selectTabPager(mTabAdapter, currentItem);
        });
        indicatorViewPager.setAdapter(mTabAdapter);

        mTabServerIndicator.post(() -> {
            indicatorViewPager.setCurrentItem(index, false);
            selectTabPager(mTabAdapter, index);
        });
    }

    private void selectTabPager(TabAdapter mTabAdapter, int currentItem) {
        if (mTabAdapter.getTabViewMap() != null) {
            for (Integer key : mTabAdapter.getTabViewMap().keySet()) {
                View itemView = mTabAdapter.getTabViewMap().get(key);
                TextView mTvIndicator = itemView.findViewById(R.id.tv_indicator);
                View mLineIndicator = itemView.findViewById(R.id.line_indicator);
                mTvIndicator.getPaint().setFakeBoldText(key == currentItem);
                RelativeLayout.LayoutParams mTvParams = (RelativeLayout.LayoutParams) mTvIndicator.getLayoutParams();
                if (mTvParams == null) {
                    mTvParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                }
                //                int width = ScreenUtil.getScreenWidth(_mActivity) / 3;
                //                mTvParams.width = width;
                mTvParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                mTvIndicator.setLayoutParams(mTvParams);

                float density = getResources().getDisplayMetrics().density;

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (24 * density), (int) (6 * density));
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                mLineIndicator.setLayoutParams(params);

                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(8 * density);
                gd.setColor(ContextCompat.getColor(_mActivity, key == currentItem ? R.color.color_3478f6 : R.color.white));
                mLineIndicator.setBackground(gd);
            }
        }
    }


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
            return mFragmentList == null ? 0 : mFragmentList.size();
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_ts_server_tab, container, false);
            }
            TextView mTvIndicator = convertView.findViewById(R.id.tv_indicator);
            mTvIndicator.setText(mTitles[position]);
            mTvIndicator.setMinWidth((int) (42 * density));
            mTvIndicator.setGravity(Gravity.CENTER);
            mTabViewMap.put(position, convertView);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            return mFragmentList.get(position);
        }

        public HashMap<Integer, View> getTabViewMap() {
            return mTabViewMap;
        }
    }
}
