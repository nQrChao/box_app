package com.zqhy.app.core.view.main;

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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.view.game.GameDownloadManagerFragment;
import com.zqhy.app.core.view.game.GameSearchFragment;
import com.zqhy.app.core.view.main.new0809.MainGameRankingFragment;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author pc
 * @date 2019/12/16-11:37
 * @description
 */
public class MainGameFragment extends BaseFragment {


    public static MainGameFragment newInstance(int index) {
        MainGameFragment fragment = new MainGameFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("page_index", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static MainGameFragment newInstance(int game_type, int game_genre_id) {
        return newInstance(game_type, game_genre_id, false);
    }

    public static MainGameFragment newInstance(int game_type, int game_genre_id, boolean isShowBack) {
        MainGameFragment fragment = new MainGameFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("game_type", game_type);
        bundle.putInt("game_genre_id", game_genre_id);
        bundle.putBoolean("is_show_back", isShowBack);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static MainGameFragment newInstancePaiHang(int tab_id) {
        MainGameFragment fragment = new MainGameFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("page_index", 1);
        bundle.putInt("tab_id", tab_id);
        bundle.putBoolean("is_show_back", true);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static MainGameFragment newInstanceFenLei(int game_type) {
        MainGameFragment fragment = new MainGameFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("page_index", 0);
        bundle.putInt("game_type", game_type);
        bundle.putBoolean("is_show_back", true);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "游戏库";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ts_main_game;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_container;
    }


    private int     page_index;
    private int     game_genre_id;
    private int     game_type = 1;
    private boolean isShowBack;
    private int     tab_id;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            page_index = getArguments().getInt("page_index", 0);
            game_genre_id = getArguments().getInt("game_genre_id", 0);
            game_type = getArguments().getInt("game_type", 1);
            isShowBack = getArguments().getBoolean("is_show_back", isShowBack);
            tab_id = getArguments().getInt("tab_id");
        }
        super.initView(state);
        bindViews();
        showSuccess();
    }


    private FixedIndicatorView mTabServerIndicator;
    private ViewPager          mViewPager;
    private TextView           mTvSearch;
    private ImageView          mIvBack;
    private ImageView          mIvMainHomePageSearch;
    private ImageView          mIvMainHomePageDownload;
    private ImageView          mIvMainHomePageMesssage;

    private void bindViews() {
        mTabServerIndicator = findViewById(R.id.tab_server_indicator);
        mViewPager = findViewById(R.id.view_pager);
        mTvSearch = findViewById(R.id.tv_search);
        mIvBack = findViewById(R.id.iv_back);
        mIvMainHomePageSearch = findViewById(R.id.iv_main_home_page_search);
        mIvMainHomePageDownload = findViewById(R.id.iv_main_home_page_download);
        mIvMainHomePageMesssage = findViewById(R.id.iv_main_home_page_messsage);

        if (isShowBack) {
            mIvBack.setVisibility(View.VISIBLE);
            mIvBack.setOnClickListener(view -> {
                pop();
            });
        } else {
            mIvBack.setVisibility(View.GONE);
        }

        setViewPager();
        mTvSearch.setOnClickListener(v -> {
            startFragment(new GameSearchFragment());
        });

        mIvMainHomePageDownload.setOnClickListener(view -> {
            //下载
            if (BuildConfig.IS_DOWNLOAD_GAME_FIRST || checkLogin()) {
                startFragment(new GameDownloadManagerFragment());
            }
        });
        mIvMainHomePageMesssage.setOnClickListener(view -> {
            //客服
            goMessageCenter();

        });
        mIvMainHomePageSearch.setOnClickListener(view -> {
            startFragment(new GameSearchFragment());
        });
    }

    private TabAdapter mTabAdapter;

    private void setViewPager() {
        Resources res = getResources();

        float unSelectSize = 15;
        float selectSize = 20;

        int selectColor = res.getColor(R.color.color_333333);
        int unSelectColor = res.getColor(R.color.color_333333);

        ArrayList<Fragment> fragmentList = new ArrayList<>();
        //        fragmentList.add(new MainRankingFragment());
        fragmentList.add(MainGameClassification2Fragment.newInstance(game_type, game_genre_id, false));
        fragmentList.add(MainGameRankingFragment.newInstance(tab_id));

        //        fragmentList.add(TsServerListFragment.newInstance());
        //        fragmentList.add(new MainGameClassificationFragment());
        //        fragmentList.add(new MainActivityFragment());

        //        String[] labels = new String[]{"排行", "开服", "分类", "活动"};
        //        String[] labels = new String[]{"排行", "开服", "分类"};
        String[] labels = new String[]{"分类", "排行"};

        mViewPager.setOffscreenPageLimit(fragmentList.size());
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
        //        post(() -> {
        //            try {
        //                mViewPager.setCurrentItem(page_index);
        //                selectPageByGenreId(game_type, game_genre_id);
        //            } catch (Exception e) {
        //                e.printStackTrace();
        //            }
        //        });

        mTabServerIndicator.post(() -> {
            indicatorViewPager.setCurrentItem(page_index, false);
            selectTabPager(mTabAdapter, page_index);
        });
    }

    /**
     * mainTab样式切换
     *
     * @param mTabAdapter
     * @param currentItem
     */
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
                mTvParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                mTvIndicator.setLayoutParams(mTvParams);

                float density = getResources().getDisplayMetrics().density;

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (30 * density), (int) (4 * density));
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                mLineIndicator.setLayoutParams(params);

                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(8 * density);
                gd.setColor(ContextCompat.getColor(_mActivity, key == currentItem ? R.color.color_4e76ff : R.color.white));
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


    /**
     * select item
     *
     * @param index
     */
    public void selectPageItem(int index) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(index, false);
        }
    }

    /**
     * 跳转 首页-游戏-分类-SubTabVo
     *
     * @param game_type
     * @param game_genre_id
     */
    public void selectPageByGenreId(int game_type, int game_genre_id) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(0, false);
            Fragment fragment = mTabAdapter.getFragmentForPage(0);
            if (fragment != null && fragment instanceof MainGameClassification2Fragment) {
                ((MainGameClassification2Fragment) fragment).selectTabGameTypeByGenreId(game_type, game_genre_id);
            }
        }
    }
}
