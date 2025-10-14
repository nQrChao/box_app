package com.zqhy.app.core.view.main;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.collapsing.BaseCollapsingListFragment;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.data.model.game.GameNavigationVo;
import com.zqhy.app.core.data.model.game.search.GameSearchDataVo;
import com.zqhy.app.core.data.model.mainpage.navigation.GameNavigationListVo;
import com.zqhy.app.core.data.model.tab.SlidingTabVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.view.main.dialog.GameClassificationDialogHelper;
import com.zqhy.app.core.vm.game.SearchViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author pc
 * @date 2019/11/27-14:57
 * @description
 */
public class MainGameClassificationFragment extends BaseCollapsingListFragment<SearchViewModel> implements View.OnClickListener {

    private LinearLayout mLlContainer;

    @NonNull
    @Override
    protected View getCollapsingView() {
        View mCollapsingView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_main_game_classification_collapsing_view, null);
        mLlContainer = mCollapsingView.findViewById(R.id.ll_container);
        return mCollapsingView;
    }

    private void setFlGameLayout(List<GameInfoVo> data) {
        if (data != null && !data.isEmpty()) {
            mLlContainer.setVisibility(View.VISIBLE);
            mLlContainer.removeAllViews();
            for (GameInfoVo gameInfoVo : data) {
                View rootView = createGameBestView(gameInfoVo);
                int itemWidth = (ScreenUtil.getScreenWidth(_mActivity) - (int) (20 * density)) / 4;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, (int) (120 * density));
                params.topMargin = (int) (4 * density);
                mLlContainer.addView(rootView, params);
            }
        } else {
            mLlContainer.setVisibility(View.GONE);
        }
    }


    private View createGameBestView(GameInfoVo gameInfoVo) {
        LinearLayout rootView = new LinearLayout(_mActivity);
        rootView.setOrientation(LinearLayout.VERTICAL);
        rootView.setGravity(Gravity.CENTER);
        rootView.setBackgroundResource(R.drawable.ts_shadow_light_grey);

        ImageView image = new ImageView(_mActivity);
        GlideUtils.loadRoundImage(_mActivity, gameInfoVo.getGameicon(), image);

        int imageWidth = (int) (56 * density);
        int imageHeight = (int) (56 * density);
        LinearLayout.LayoutParams imagesGameName = new LinearLayout.LayoutParams(imageWidth, imageHeight);
        rootView.addView(image, imagesGameName);

        TextView mTvGameName = new TextView(_mActivity);
        mTvGameName.setText(gameInfoVo.getGamename());
        mTvGameName.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
        mTvGameName.setTextSize(10);
        mTvGameName.setSingleLine(true);
        mTvGameName.setEllipsize(TextUtils.TruncateAt.END);
        mTvGameName.setIncludeFontPadding(false);

        LinearLayout.LayoutParams gameNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        gameNameParams.leftMargin = (int) (8 * density);
        gameNameParams.rightMargin = (int) (8 * density);
        gameNameParams.topMargin = (int) (4 * density);
        gameNameParams.bottomMargin = (int) (2 * density);

        rootView.addView(mTvGameName, gameNameParams);

        TextView mTvLabel = new TextView(_mActivity);
        mTvLabel.setText(gameInfoVo.getLabel_name());
        mTvLabel.setTextSize(10);
        mTvLabel.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
        mTvLabel.setGravity(Gravity.CENTER);
        int xPadding = (int) (4 * density);
        mTvLabel.setPadding(xPadding, 0, xPadding, 0);
        mTvLabel.setSingleLine(true);
        mTvLabel.setEllipsize(TextUtils.TruncateAt.END);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(4 * density);
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_ff5400));
        mTvLabel.setBackground(gd);

        LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams((int) (52 * density), (int) (18 * density));
        rootView.addView(mTvLabel, labelParams);

        rootView.setOnClickListener(v -> {
            goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
        });
        return rootView;
    }

    private LinearLayout mFlGameSearchView;
    private TextView     mTvGameSearch;
    private ImageView    mIvModuleGame;
    private ImageView    mIvGameDownload;

    @NonNull
    @Override
    protected View getToolBarView() {
        /*View mToolBarView = LayoutInflater.from(_mActivity).inflate(R.layout.item_game_search, null);
        mFlGameSearchView = mToolBarView.findViewById(R.id.fl_game_search_view);
        mTvGameSearch = mToolBarView.findViewById(R.id.tv_game_search);
        mIvModuleGame = mToolBarView.findViewById(R.id.iv_module_game);
        mIvGameDownload = mToolBarView.findViewById(R.id.iv_game_download);

        mToolBarView.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.white));

        mFlGameSearchView.setOnClickListener(view -> {
            FragmentHolderActivity.startFragmentInActivity(_mActivity, new GameSearchFragment());
        });
        mIvGameDownload.setOnClickListener(v -> {
            if (checkLogin()) {
                FragmentHolderActivity.startFragmentInActivity(_mActivity, new GameDownloadManagerFragment());
            }
        });
        mIvModuleGame.setOnClickListener(v -> {
            //我的游戏
            if (checkLogin()) {
                FragmentHolderActivity.startFragmentInActivity(_mActivity, new GameWelfareFragment());
            }
        });

        mToolBarView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return mToolBarView;*/
        return null;
    }


    private LinearLayout        mLlGameCenterFirstTab;
    private FrameLayout         mFlGameCenterBt;
    private TextView            mTvGameCenterBt;
    private View                mLineGameCenterBt;
    private FrameLayout         mFlGameCenterDiscount;
    private TextView            mTvGameCenterDiscount;
    private View                mLineGameCenterDiscount;
    private FrameLayout         mFlGameCenterH5;
    private TextView            mTvGameCenterH5;
    private View                mLineGameCenterH5;
    private FrameLayout         mFlGameCenterSingle;
    private TextView            mTvGameCenterSingle;
    private View                mLineGameCenterSingle;
    private TextView            mTvGameClassification;
    private LinearLayout        mLlGameCenterSecondTab;
    private ScrollIndicatorView mTabIndicator;

    /**
     * 顶部固定的view
     *
     * @return
     */
    private View getAppBarLayout() {
        View mAppbarLayoutView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_main_game_classification_toolbar, null);
        bindAppBarLayoutView(mAppbarLayoutView);

        mTvGameClassification.setOnClickListener(view -> {
            //showDialog
            if (dialogHelper == null) {
                dialogHelper = new GameClassificationDialogHelper(_mActivity, (select_game_type, gameTabVo) -> {
                    if (game_type != select_game_type) {
                        // firstTab 不相同
                        switch (select_game_type) {
                            case 1:
                                selectBTTab();
                                break;
                            case 2:
                                selectDiscountTab();
                                break;
                            case 3:
                                selectH5Tab();
                                break;
                            case 4:
                                selectSingleTab();
                                break;
                        }
                    }
                    if (gameTabVo != null) {
                        refreshTabIndicatorList();
                        int position = 0;
                        for (int i = 0; i < gameTabVoList.size(); i++) {
                            GameTabIndicatorVo vo = gameTabVoList.get(i);
                            if (vo.getTabId() == gameTabVo.getTabId()) {
                                position = i;
                                break;
                            }
                        }
                        refreshAdapter(position);
                    }
                });
            }
            dialogHelper.showDialog(game_type, getAllTabList());
        });
        return mAppbarLayoutView;
    }

    private GameClassificationDialogHelper dialogHelper;

    private void bindAppBarLayoutView(View mAppbarLayoutView) {
        mLlGameCenterFirstTab = mAppbarLayoutView.findViewById(R.id.ll_game_center_first_tab);
        mFlGameCenterBt = mAppbarLayoutView.findViewById(R.id.fl_game_center_bt);
        mTvGameCenterBt = mAppbarLayoutView.findViewById(R.id.tv_game_center_bt);
        mLineGameCenterBt = mAppbarLayoutView.findViewById(R.id.line_game_center_bt);
        mFlGameCenterDiscount = mAppbarLayoutView.findViewById(R.id.fl_game_center_discount);
        mTvGameCenterDiscount = mAppbarLayoutView.findViewById(R.id.tv_game_center_discount);
        mLineGameCenterDiscount = mAppbarLayoutView.findViewById(R.id.line_game_center_discount);
        mFlGameCenterH5 = mAppbarLayoutView.findViewById(R.id.fl_game_center_h5);
        mTvGameCenterH5 = mAppbarLayoutView.findViewById(R.id.tv_game_center_h5);
        mLineGameCenterH5 = mAppbarLayoutView.findViewById(R.id.line_game_center_h5);
        mFlGameCenterSingle = mAppbarLayoutView.findViewById(R.id.fl_game_center_single);
        mTvGameCenterSingle = mAppbarLayoutView.findViewById(R.id.tv_game_center_single);
        mLineGameCenterSingle = mAppbarLayoutView.findViewById(R.id.line_game_center_single);
        mTvGameClassification = mAppbarLayoutView.findViewById(R.id.tv_game_classification);
        mLlGameCenterSecondTab = mAppbarLayoutView.findViewById(R.id.ll_game_center_second_tab);
        mTabIndicator = mAppbarLayoutView.findViewById(R.id.tab_indicator);

        mFlGameCenterBt.setOnClickListener(this);
        mFlGameCenterDiscount.setOnClickListener(this);
        mFlGameCenterH5.setOnClickListener(this);
        mFlGameCenterSingle.setOnClickListener(this);

        setTabIndicatorAdapter();
    }

    private MyTabAdapter             mTabAdapter;
    private List<GameTabIndicatorVo> gameTabVoList = new ArrayList<>();

    private void setTabIndicatorAdapter() {
        mTabAdapter = new MyTabAdapter(_mActivity, gameTabVoList);
        mTabIndicator.setAdapter(mTabAdapter);
        mTabIndicator.setOnItemSelectListener((selectItemView, select, preSelect) -> {
            if (mTabAdapter != null && mTabAdapter.getTabViewMaps() != null) {
                for (Integer key : mTabAdapter.getTabViewMaps().keySet()) {
                    View itemView = mTabAdapter.getTabViewMaps().get(key);
                    boolean isSelected = itemView == selectItemView;
                    GradientDrawable gd = new GradientDrawable();
                    gd.setCornerRadius(2 * density);
                    gd.setColor(ContextCompat.getColor(_mActivity, isSelected ? R.color.color_0052ef : R.color.color_f2f2f2));
                    itemView.setBackground(gd);
                    if (itemView instanceof TextView) {
                        ((TextView) itemView).setTextColor(ContextCompat.getColor(_mActivity, isSelected ? R.color.white : R.color.color_232323));
                    }
                }
            }
            refreshGameList(mTabAdapter.getItemData(select));
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_game_center_bt:
                onClickBtTab();
                break;
            case R.id.fl_game_center_discount:
                onClickDiscountTab();
                break;
            case R.id.fl_game_center_h5:
                onClickH5Tab();
                break;
            case R.id.fl_game_center_single:
                onClickSingleTab();
                break;
            default:
                break;
        }
    }

    private void onClickBtTab() {
        selectBTTab();
        createTabIndicatorList();
    }

    private void onClickDiscountTab() {
        selectDiscountTab();
        createTabIndicatorList();
    }

    private void onClickH5Tab() {
        selectH5Tab();
        createTabIndicatorList();
    }

    private void onClickSingleTab() {
        selectSingleTab();
        createTabIndicatorList();
    }

    private void selectBTTab() {
        game_type = 1;
        restoreTabs();
        mTvGameCenterBt.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
        mTvGameCenterBt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mLineGameCenterBt.setVisibility(View.VISIBLE);
    }

    private void selectDiscountTab() {
        game_type = 2;
        restoreTabs();
        mTvGameCenterDiscount.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
        mTvGameCenterDiscount.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mLineGameCenterDiscount.setVisibility(View.VISIBLE);
    }

    private void selectH5Tab() {
        game_type = 3;
        restoreTabs();
        mTvGameCenterH5.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
        mTvGameCenterH5.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mLineGameCenterH5.setVisibility(View.VISIBLE);
    }

    private void selectSingleTab() {
        game_type = 4;
        restoreTabs();
        mTvGameCenterSingle.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
        mTvGameCenterSingle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mLineGameCenterSingle.setVisibility(View.VISIBLE);
    }

    private void restoreTabs() {
        mTvGameCenterBt.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
        mTvGameCenterDiscount.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
        mTvGameCenterH5.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
        mTvGameCenterSingle.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));

        mTvGameCenterBt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        mTvGameCenterDiscount.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        mTvGameCenterH5.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        mTvGameCenterSingle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

        mLineGameCenterBt.setVisibility(View.GONE);
        mLineGameCenterDiscount.setVisibility(View.GONE);
        mLineGameCenterH5.setVisibility(View.GONE);
        mLineGameCenterSingle.setVisibility(View.GONE);
    }

    private int                       game_type = 1;
    private MainGameChildListFragment fragment;

    @NonNull
    @Override
    protected BaseListFragment getListView() {
        fragment = new MainGameChildListFragment();
        return fragment;
    }

    @Override
    protected View getListLayoutView() {
        return null;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        if (mFlAppbarLayout != null) {
            mFlAppbarLayout.addView(getAppBarLayout());
        }
        if (mToolbar != null) {
            mToolbar.setBackgroundColor(Color.WHITE);
            mToolbar.setVisibility(View.GONE);
        }
        setSwipeRefresh(() -> {
            getFlGameList();
            refreshGameList(lastSelectTabIndicator);
            getSearchGame();
        });
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getNetData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getNetData();
    }

    private void getNetData() {
        getGameClassificationList();
        getFlGameList();
        getSearchGame();
    }

    @Override
    public void onEvent(EventCenter eventCenter) {
        super.onEvent(eventCenter);
        if (eventCenter.getEventCode() == EventConfig.ACTION_ADD_DOWNLOAD_EVENT_CODE) {
            if (mIvGameDownload != null) {
                mIvGameDownload.setImageResource(R.mipmap.ic_download_game_marked);
            }
        } else if (eventCenter.getEventCode() == EventConfig.ACTION_READ_DOWNLOAD_EVENT_CODE) {
            if (mIvGameDownload != null) {
                mIvGameDownload.setImageResource(R.mipmap.ic_download_game_unmarked);
            }
        }
    }

    @Override
    protected boolean isCanSwipeRefresh() {
        return true;
    }

    private List<GameNavigationVo> gameNavigationVoList;

    /**
     * 接口tabIndicator数据
     */
    private void getGameClassificationList() {
        if (mViewModel != null) {
            mViewModel.getGameClassificationList(new OnBaseCallback<GameNavigationListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                }

                @Override
                public void onSuccess(GameNavigationListVo gameNavigationListVo) {
                    if (gameNavigationListVo != null && gameNavigationListVo.isStateOK() && gameNavigationListVo.getData() != null) {
                        gameNavigationVoList = gameNavigationListVo.getData();
                        createTabIndicatorList();
                    }
                }
            });
        }
    }


    /**
     * 根据game_type拼接adapter数据并刷新
     */
    private void createTabIndicatorList() {
        refreshTabIndicatorList();
        refreshAdapter(0);
    }

    /**
     * 刷新Adapter
     *
     * @param position
     */
    private void refreshAdapter(int position) {
        mTabAdapter.notifyDataSetChanged();
        if (gameTabVoList.size() > 0) {
            mTabIndicator.clearSelectedTabIndex();
            mTabIndicator.setCurrentItem(position);
        }
    }

    /**
     * 刷新adapter数据
     */
    private void refreshTabIndicatorList() {
        gameTabVoList.clear();
        GameTabIndicatorVo hotTab = getHotGameTabVo();
        gameTabVoList.add(hotTab);
        GameTabIndicatorVo newTab = getNewGameTabVo();
        gameTabVoList.add(newTab);

        if (game_type == 1) {
            GameTabIndicatorVo vipTab = getVipGameTabVo();
            gameTabVoList.add(vipTab);
            GameTabIndicatorVo gmTab = getGmGameTabVo();
            gameTabVoList.add(gmTab);

        } else if (game_type == 2 || game_type == 3) {
            GameTabIndicatorVo discountTab = getDiscountGameTabVo();
            gameTabVoList.add(discountTab);
            GameTabIndicatorVo activityTab = getActivityGameTabVo();
            gameTabVoList.add(activityTab);
        } else if (game_type == 3 || game_type == 3 || game_type == 4) {
            GameTabIndicatorVo discountTab = getDiscountGameTabVo();
            gameTabVoList.add(discountTab);
        }

        if (gameNavigationVoList != null) {
            for (GameNavigationVo navigationVo : gameNavigationVoList) {
                GameTabIndicatorVo gameTab = new GameTabIndicatorVo();
                gameTab.setGenre_id(navigationVo.getGenre_id());
                gameTab.setType(navigationVo.getType());
                gameTab.setGenre_name(navigationVo.getGenre_name());
                gameTab.addParams("genre_id", String.valueOf(navigationVo.getGenre_id()));

                gameTabVoList.add(gameTab);
            }
        }
    }

    private List<GameTabIndicatorVo> getAllTabList() {
        List<GameTabIndicatorVo> gameTabVoList = new ArrayList<>();
        gameTabVoList.clear();

        GameTabIndicatorVo hotTab = getHotGameTabVo();
        gameTabVoList.add(hotTab);
        GameTabIndicatorVo newTab = getNewGameTabVo();
        gameTabVoList.add(newTab);

        GameTabIndicatorVo vipTab = getVipGameTabVo();
        gameTabVoList.add(vipTab);
        GameTabIndicatorVo gmTab = getGmGameTabVo();
        gameTabVoList.add(gmTab);

        GameTabIndicatorVo discountTab = getDiscountGameTabVo();
        gameTabVoList.add(discountTab);
        GameTabIndicatorVo activityTab = getActivityGameTabVo();
        gameTabVoList.add(activityTab);

        if (gameNavigationVoList != null) {
            for (GameNavigationVo navigationVo : gameNavigationVoList) {
                GameTabIndicatorVo gameTab = new GameTabIndicatorVo();
                gameTab.setGenre_id(navigationVo.getGenre_id());
                gameTab.setType(navigationVo.getType());
                gameTab.setGenre_name(navigationVo.getGenre_name());
                gameTab.addParams("genre_id", String.valueOf(navigationVo.getGenre_id()));

                gameTabVoList.add(gameTab);
            }
        }
        return gameTabVoList;
    }

    @NonNull
    private GameTabIndicatorVo getHotGameTabVo() {
        return new GameTabIndicatorVo(-1, "热门")
                .addParams("order", "hot")
                .addGameType("1")
                .addGameType("2")
                .addGameType("3")
                .addGameType("4");
    }

    @NonNull
    private GameTabIndicatorVo getNewGameTabVo() {
        return new GameTabIndicatorVo(-2, "新游")
                .addParams("order", "newest")
                .addGameType("1")
                .addGameType("2")
                .addGameType("3")
                .addGameType("4");
    }

    @NonNull
    private GameTabIndicatorVo getVipGameTabVo() {
        return new GameTabIndicatorVo(-3, "满V")
                .addParams("kw", "满V")
                .addGameType("1");
    }

    @NonNull
    private GameTabIndicatorVo getGmGameTabVo() {
        return new GameTabIndicatorVo(-4, "GM")
                .addParams("kw", "GM")
                .addGameType("1");
    }

    @NonNull
    private GameTabIndicatorVo getDiscountGameTabVo() {
        return new GameTabIndicatorVo(-5, "折扣(低)")
                .addParams("order", "discount")
                .addGameType("2")
                .addGameType("3")
                .addGameType("4");
    }

    @NonNull
    private GameTabIndicatorVo getActivityGameTabVo() {
        return new GameTabIndicatorVo(-6, "有活动")
                .addParams("has_hd", "1")
                .addGameType("2")
                .addGameType("3");
    }


    private GameTabIndicatorVo lastSelectTabIndicator;

    private void refreshGameList(GameTabIndicatorVo gameTabIndicatorVo) {
        if (gameTabIndicatorVo == null) {
            return;
        }
        lastSelectTabIndicator = gameTabIndicatorVo;
        Map<String, String> treeParams = new TreeMap<>();
        treeParams.clear();
        Map<String, String> params = gameTabIndicatorVo.params;
        for (String key : params.keySet()) {
            treeParams.put(key, params.get(key));
        }
        treeParams.put("game_type", String.valueOf(game_type));
        fragment.refreshGameList(treeParams, new MainGameChildListFragment.LoadingListener() {
            @Override
            public void onLoadingBefore() {

            }

            @Override
            public void onLoadingAfter() {
                setSwipeRefreshing(false);
            }
        });
    }

    private void getFlGameList() {
        if (mViewModel != null) {
            mViewModel.getGameClassificationBastList(new OnBaseCallback<GameListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    setSwipeRefreshing(false);
                }

                @Override
                public void onSuccess(GameListVo data) {
                    if (data != null && data.isStateOK()) {
                        setFlGameLayout(data.getData());
                    }
                }
            });
        }
    }

    private void getSearchGame() {
        if (mViewModel != null) {
            mViewModel.getGameSearchData(new OnBaseCallback<GameSearchDataVo>() {
                @Override
                public void onSuccess(GameSearchDataVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                String gameSearchShow = data.getData().getS_best_title_show();
                                if (!TextUtils.isEmpty(gameSearchShow)) {
                                    mTvGameSearch.setText(gameSearchShow);
                                } else {
                                    mTvGameSearch.setText("搜索游戏");
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    class MyTabAdapter extends Indicator.IndicatorAdapter {
        private Context                  mContext;
        private List<GameTabIndicatorVo> list;

        private Map<Integer, View> tabViewMaps = new HashMap<>();


        public MyTabAdapter(Context context, List<GameTabIndicatorVo> list) {
            mContext = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new TextView(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, (int) (24 * density));
                if (position == 0) {
                    params.leftMargin = (int) (14 * density);
                }
                params.rightMargin = (int) (8 * density);
                if (position == getCount() - 1) {
                    params.rightMargin = (int) (14 * density);
                }
                convertView.setLayoutParams(params);
            }
            TextView textView = (TextView) convertView;
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(14);
            int padding = (int) (12 * density);
            textView.setPadding(padding, 0, padding, 0);
            textView.setText(list.get(position).getTabTitle());
            tabViewMaps.put(position, convertView);
            return convertView;
        }

        public Map<Integer, View> getTabViewMaps() {
            return tabViewMaps;
        }

        public GameTabIndicatorVo getItemData(int position) {
            if (list == null) {
                return null;
            }
            return list.get(position);
        }

    }

    public class GameTabIndicatorVo extends GameNavigationVo implements SlidingTabVo {

        private Map<String, String> params;

        private int tabIndicatorId;

        public GameTabIndicatorVo() {
        }

        public GameTabIndicatorVo(int tabIndicatorId, String tabTitle) {
            this.tabIndicatorId = tabIndicatorId;
            setGenre_name(tabTitle);
        }

        /**
         * 添加参数
         *
         * @param key
         * @param value
         */
        public GameTabIndicatorVo addParams(String key, String value) {
            if (params == null) {
                params = new TreeMap<>();
            }
            params.put(key, value);
            return this;
        }

        public List<String> gameTypeList;

        public GameTabIndicatorVo addGameType(String game_type) {
            if (gameTypeList == null) {
                gameTypeList = new ArrayList<>();
            }
            gameTypeList.add(game_type);
            return this;
        }

        public boolean isContainsGameType(String game_type) {
            if (gameTypeList == null) {
                return false;
            }
            return gameTypeList.contains(game_type);
        }

        @Override
        public String getTabTitle() {
            return getGenre_name();
        }

        @Override
        public int getTabId() {
            if (getType() != 0) {
                return getGenre_id();
            } else {
                return tabIndicatorId;
            }
        }
    }
}
