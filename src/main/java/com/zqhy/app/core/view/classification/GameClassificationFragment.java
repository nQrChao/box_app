package com.zqhy.app.core.view.classification;

import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.chaoji.other.hjq.toast.Toaster;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zqhy.app.adapter.AdapterPool;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.classification.GameTabVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.data.model.game.GameNavigationVo;
import com.zqhy.app.core.data.model.mainpage.navigation.GameNavigationListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.classification.dialog.GameCenterDialogHelper;
import com.zqhy.app.core.view.classification.dialog.OnGameCenterDialogClickListener;
import com.zqhy.app.core.view.game.GameSearchFragment;
import com.zqhy.app.core.view.main.MainActivity;
import com.zqhy.app.core.vm.game.SearchViewModel;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/8
 */

public class GameClassificationFragment extends BaseFragment<SearchViewModel> implements XRecyclerView.LoadingListener, View.OnClickListener {

    public static GameClassificationFragment newInstance(String game_type) {
        GameClassificationFragment fragment = new GameClassificationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("game_type", game_type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static GameClassificationFragment newInstance(String game_type, boolean showSearchDialog) {
        GameClassificationFragment fragment = new GameClassificationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("game_type", game_type);
        bundle.putBoolean("showSearchDialog", showSearchDialog);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static GameClassificationFragment newInstance(String game_type, int showSlideDialog) {
        GameClassificationFragment fragment = new GameClassificationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("game_type", game_type);
        bundle.putInt("showSlideDialog", showSlideDialog);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static GameClassificationFragment newInstance(String game_type, String default_genre_id) {
        GameClassificationFragment fragment = new GameClassificationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("game_type", game_type);
        bundle.putString("default_genre_id", default_genre_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_GAME_CLASSIFICATION_STATE;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_game_classification;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private boolean showSearchDialog;
    private String  default_genre_id;
    private int     showSlideDialog;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            game_type = getArguments().getString("game_type", "1");
            default_genre_id = getArguments().getString("default_genre_id");
            showSearchDialog = getArguments().getBoolean("showSearchDialog", showSearchDialog);
            showSlideDialog = getArguments().getInt("showSlideDialog", showSlideDialog);
        }
        super.initView(state);
        ImageView mImageView = findViewById(R.id.iv_back);
        if (mImageView != null) {
            mImageView.setVisibility(!(_mActivity instanceof MainActivity) ? View.VISIBLE : View.GONE);
            mImageView.setOnClickListener(view -> pop());
        }
        bindView();
        getGameClassificationList();

        if (showSearchDialog) {
            //            mFlSearchView.performClick();
        }
    }

    private void selectGenre() {
        if (!TextUtils.isEmpty(default_genre_id)) {
            try {
                int tabId = Integer.parseInt(default_genre_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
        }

        if (showSlideDialog == 1) {
            if (mLlGameCenterClassification != null) {
                mLlGameCenterClassification.performClick();
            }
        }
    }

    private LinearLayout     mFlSearchView;
    private LinearLayout     mLlGameCenterFirstTab;
    private FrameLayout      mFlGameCenterBt;
    private TextView         mTvGameCenterBt;
    private View             mLineGameCenterBt;
    private FrameLayout      mFlGameCenterDiscount;
    private TextView         mTvGameCenterDiscount;
    private View             mLineGameCenterDiscount;
    private FrameLayout      mFlGameCenterH5;
    private TextView         mTvGameCenterH5;
    private View             mLineGameCenterH5;
    private FrameLayout      mFlGameCenterSingle;
    private TextView         mTvGameCenterSingle;
    private View             mLineGameCenterSingle;
    private LinearLayout     mLlGameCenterClassification;
    private ImageView        mIvGameCenterClassification;
    private LinearLayout     mLlGameCenterSecondTab;
    private XRecyclerView    mRecyclerView;

    GameCenterDialogHelper gameCenterDialogHelper;

    private void bindView() {
        mFlSearchView = findViewById(R.id.fl_search_view);
        mLlGameCenterFirstTab = findViewById(R.id.ll_game_center_first_tab);
        mFlGameCenterBt = findViewById(R.id.fl_game_center_bt);
        mTvGameCenterBt = findViewById(R.id.tv_game_center_bt);
        mLineGameCenterBt = findViewById(R.id.line_game_center_bt);
        mFlGameCenterDiscount = findViewById(R.id.fl_game_center_discount);
        mTvGameCenterDiscount = findViewById(R.id.tv_game_center_discount);
        mLineGameCenterDiscount = findViewById(R.id.line_game_center_discount);
        mFlGameCenterH5 = findViewById(R.id.fl_game_center_h5);
        mTvGameCenterH5 = findViewById(R.id.tv_game_center_h5);
        mLineGameCenterH5 = findViewById(R.id.line_game_center_h5);
        mFlGameCenterSingle = findViewById(R.id.fl_game_center_single);
        mTvGameCenterSingle = findViewById(R.id.tv_game_center_single);
        mLineGameCenterSingle = findViewById(R.id.line_game_center_single);
        mLlGameCenterClassification = findViewById(R.id.ll_game_center_classification);
        mIvGameCenterClassification = findViewById(R.id.iv_game_center_classification);
        mLlGameCenterSecondTab = findViewById(R.id.ll_game_center_second_tab);
        mRecyclerView = findViewById(R.id.recycler_view);

        initList();

        mFlGameCenterBt.setOnClickListener(this);
        mFlGameCenterDiscount.setOnClickListener(this);
        mFlGameCenterH5.setOnClickListener(this);
        mFlGameCenterSingle.setOnClickListener(this);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(1 * density);
        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.color_d9d9d9));
        mLlGameCenterClassification.setBackground(gd);


        mLlGameCenterClassification.setOnClickListener(view -> {
            initGameCenterDialog();
            gameCenterDialogHelper.showDialog(game_type, gameTabVoList);
        });

        mFlSearchView.setOnClickListener(v -> {
            //显示新版搜索界面
            //            initGameCenterDialog();
            //            gameCenterDialogHelper.showSearchDialog(this);

            //2019.06.19 跳转最新版搜索页面
            start(new GameSearchFragment());
        });
        defaultTab();

    }

    private void initGameCenterDialog() {
        if (gameCenterDialogHelper == null) {
            gameCenterDialogHelper = new GameCenterDialogHelper(_mActivity);
            gameCenterDialogHelper.setOnGameCenterDialogClickListener(new OnGameCenterDialogClickListener() {
                @Override
                public void onTabClick(String select_game_type, Object data) {
                    if (!game_type.equalsIgnoreCase(select_game_type)) {
                        switch (select_game_type) {
                            case "1":
                                selectBTTab();
                                break;
                            case "2":
                                selectDiscountTab();
                                break;
                            case "3":
                                selectH5Tab();
                                break;
                            case "4":
                                selectSingleTab();
                                break;
                            default:
                                break;
                        }
                    }
                    onGameTabSelect(data);
                    if (data != null && data instanceof GameTabVo) {
                        GameTabVo gameTabVo = (GameTabVo) data;
                    }
                }
            });
        }
    }

    private BaseRecyclerAdapter mAdapter;

    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = AdapterPool.newInstance()
                .getGameListAdapter(_mActivity)
                .setTag(R.id.tag_fragment, this);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadingListener(this);
        mRecyclerView.setRefreshTimeVisible(true);
    }

    private void defaultTab() {
        switch (game_type) {
            case "1":
                onClickBtTab();
                break;
            case "2":
                onClickDiscountTab();
                break;
            case "3":
                onClickH5Tab();
                break;
            case "4":
                onClickSingleTab();
                break;
            default:
                onClickBtTab();
                break;
        }
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


    private void onClickBtTab() {
        selectBTTab();
    }

    private void onClickDiscountTab() {
        selectDiscountTab();
    }

    private void onClickH5Tab() {
        selectH5Tab();
    }

    private void onClickSingleTab() {
        selectSingleTab();
    }


    private void selectBTTab() {
        game_type = "1";
        restoreTabs();
        mTvGameCenterBt.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
        mTvGameCenterBt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mLineGameCenterBt.setVisibility(View.VISIBLE);
        setTabLayoutData();
    }

    private void selectDiscountTab() {
        game_type = "2";
        restoreTabs();
        mTvGameCenterDiscount.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
        mTvGameCenterDiscount.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mLineGameCenterDiscount.setVisibility(View.VISIBLE);
        setTabLayoutData();
    }

    private void selectH5Tab() {
        game_type = "3";
        restoreTabs();
        mTvGameCenterH5.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
        mTvGameCenterH5.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mLineGameCenterH5.setVisibility(View.VISIBLE);
        setTabLayoutData();
    }

    private void selectSingleTab() {
        game_type = "4";
        restoreTabs();
        mTvGameCenterSingle.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
        mTvGameCenterSingle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mLineGameCenterSingle.setVisibility(View.VISIBLE);
        setTabLayoutData();
    }


    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        page = 1;
        getGameList();
    }

    @Override
    public void onLoadMore() {
        if (page < 0) {
            return;
        }
        page++;
        getGameList();
    }

    private String game_type = "";

    private String order    = "hot";
    private String genre_id = "";
    private String kw       = "";
    private String has_hd   = "";

    private int page      = 1;
    private int pageCount = 12;


    private List<GameNavigationVo> gameNavigationVoList;
    private List<GameTabVo>        gameTabVoList = new ArrayList<>();

    private void onNetBefore() {
        mLlGameCenterFirstTab.setEnabled(false);
        mLlGameCenterSecondTab.setEnabled(false);

        mFlGameCenterBt.setEnabled(false);
        mFlGameCenterDiscount.setEnabled(false);
        mFlGameCenterH5.setEnabled(false);
        mFlGameCenterSingle.setEnabled(false);

        mIvGameCenterClassification.setEnabled(false);
    }

    private void onNetAfter() {
        mLlGameCenterFirstTab.setEnabled(true);
        mLlGameCenterSecondTab.setEnabled(true);

        mFlGameCenterBt.setEnabled(true);
        mFlGameCenterDiscount.setEnabled(true);
        mFlGameCenterH5.setEnabled(true);
        mFlGameCenterSingle.setEnabled(true);

        mIvGameCenterClassification.setEnabled(true);

        if (page == 1) {
            mRecyclerView.refreshComplete();
        } else {
            mRecyclerView.loadMoreComplete();
        }
    }

    private void setTabLayoutData() {
        List<GameTabVo> gameTabVos = new ArrayList<>();
        gameTabVoList.clear();
        if (gameNavigationVoList != null) {
            for (GameNavigationVo gameNavigationVo : gameNavigationVoList) {
                GameTabVo gameTabVo = new GameTabVo(1);
                gameTabVo.setGenre_name(gameNavigationVo.getGenre_name());
                gameTabVo.setType(gameNavigationVo.getType());
                gameTabVo.setGenre_id(gameNavigationVo.getGenre_id());
                gameTabVos.add(gameTabVo);
                gameTabVoList.add(gameTabVo);
            }
        }

        GameTabVo hotTab = getHotGameTabVo();

        GameTabVo newTab = getNewGameTabVo();


        GameTabVo activityTab = getActivityGameTabVo();

        if ("2".equals(game_type) || "3".equals(game_type)) {
            gameTabVos.add(0, activityTab);
        }
        gameTabVoList.add(0, activityTab);

        GameTabVo gmTab = getGmGameTabVo();
        GameTabVo vipTab = getVipGameTabVo();

        if ("1".equals(game_type)) {
            gameTabVos.add(0, gmTab);
            gameTabVos.add(0, vipTab);
        }
        gameTabVoList.add(0, gmTab);
        gameTabVoList.add(0, vipTab);

        GameTabVo discountTab = getDiscountGameTabVo();

        if (!"1".equals(game_type)) {
            gameTabVos.add(0, discountTab);
        }
        gameTabVoList.add(0, discountTab);


        gameTabVos.add(0, newTab);
        gameTabVos.add(0, hotTab);

        gameTabVoList.add(0, newTab);
        gameTabVoList.add(0, hotTab);

    }

    @NonNull
    private GameTabVo getDiscountGameTabVo() {
        GameTabVo discountTab = new GameTabVo(-5, 2);
        discountTab.setGenre_name("折扣(低)");
        discountTab.setOrder("discount");
        discountTab.addGameType("2");
        discountTab.addGameType("3");
        discountTab.addGameType("4");
        return discountTab;
    }

    @NonNull
    private GameTabVo getVipGameTabVo() {
        GameTabVo vipTab = new GameTabVo(-4, 3);
        vipTab.setGenre_name("满V");
        vipTab.setKw("满V");
        vipTab.addGameType("1");
        return vipTab;
    }

    @NonNull
    private GameTabVo getGmGameTabVo() {
        GameTabVo gmTab = new GameTabVo(-6, 3);
        gmTab.setGenre_name("GM");
        gmTab.setKw("GM");
        gmTab.addGameType("1");
        return gmTab;
    }

    @NonNull
    private GameTabVo getActivityGameTabVo() {
        GameTabVo activityTab = new GameTabVo(-3, 4);
        activityTab.setGenre_name("有活动");
        activityTab.setHas_hd("1");

        activityTab.addGameType("2");
        activityTab.addGameType("3");
        return activityTab;
    }

    @NonNull
    private GameTabVo getNewGameTabVo() {
        GameTabVo newTab = new GameTabVo(-2, 2);
        newTab.setGenre_name("新游");
        newTab.setOrder("newest");
        newTab.addGameType("1");
        newTab.addGameType("2");
        newTab.addGameType("3");
        newTab.addGameType("4");
        return newTab;
    }

    @NonNull
    private GameTabVo getHotGameTabVo() {
        GameTabVo hotTab = new GameTabVo(-1, 2);
        hotTab.setGenre_name("热门");
        hotTab.setOrder("hot");
        hotTab.addGameType("1");
        hotTab.addGameType("2");
        hotTab.addGameType("3");
        hotTab.addGameType("4");
        return hotTab;
    }


    private void onGameTabSelect(Object data) {
        if (data != null && data instanceof GameTabVo) {
            GameTabVo gameTabVo = (GameTabVo) data;
            clearParamValue();
            if (gameTabVo.getTab_type() == 1) {
                genre_id = String.valueOf(gameTabVo.getGenre_id());
            } else if (gameTabVo.getTab_type() == 2) {
                order = gameTabVo.getOrder();
            } else if (gameTabVo.getTab_type() == 3) {
                kw = gameTabVo.getKw();
            } else if (gameTabVo.getTab_type() == 4) {
                has_hd = gameTabVo.getHas_hd();
            }
        }
        mRecyclerView.refresh();
    }


    private void getGameClassificationList() {
        if (mViewModel != null) {
            mViewModel.getGameClassificationList(new OnBaseCallback<GameNavigationListVo>() {
                @Override
                public void onSuccess(GameNavigationListVo gameNavigationListVo) {
                    if (gameNavigationListVo != null && gameNavigationListVo.isStateOK() && gameNavigationListVo.getData() != null) {
                        gameNavigationVoList = gameNavigationListVo.getData();
                        setTabLayoutData();
                        selectGenre();
                    }
                }
            });
        }
    }

    Map<String, String> treeParams;

    private void clearParamValue() {
        order = "";
        genre_id = "";
        kw = "";
        has_hd = "";
    }

    private void getGameList() {
        if (mViewModel == null) {
            return;
        }
        createTreeParams();
        mViewModel.getGameList(treeParams, new OnBaseCallback<GameListVo>() {
            @Override
            public void onAfter() {
                super.onAfter();
                onNetAfter();
            }

            @Override
            public void onBefore() {
                super.onBefore();
                onNetBefore();
            }

            @Override
            public void onSuccess(GameListVo gameListVo) {
                if (gameListVo != null) {
                    if (gameListVo.isStateOK()) {
                        if (gameListVo.getData() != null && !gameListVo.getData().isEmpty()) {
                            if (page == 1) {
                                mAdapter.clear();
                            }
                            mAdapter.addAllData(gameListVo.getData());
                            if (gameListVo.getData().size() < pageCount) {
                                //has no more data
                                page = -1;
                                mRecyclerView.setNoMore(true);
                            }
                            mAdapter.notifyDataSetChanged();
                        } else {
                            if (page == 1) {
                                mAdapter.clear();
                                //show empty
                                mAdapter.addData(new EmptyDataVo(R.mipmap.img_empty_data_1));
                            } else {
                                //has no more data
                            }
                            page = -1;
                            mRecyclerView.setNoMore(true);
                            mAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toaster.show( gameListVo.getMsg());
                    }
                }
            }
        });
    }

    private void createTreeParams() {
        StringBuilder sb = new StringBuilder();

        if (treeParams == null) {
            treeParams = new TreeMap<>();
        }
        treeParams.clear();
        treeParams.put("game_type", game_type);

        if (!TextUtils.isEmpty(order)) {
            treeParams.put("order", order);
        }
        if (!TextUtils.isEmpty(genre_id)) {
            treeParams.put("genre_id", genre_id);
        }
        if (!TextUtils.isEmpty(kw)) {
            treeParams.put("kw", kw);
        }
        if (!TextUtils.isEmpty(has_hd)) {
            treeParams.put("has_hd", has_hd);
        }
        treeParams.put("page", String.valueOf(page));
        treeParams.put("pagecount", String.valueOf(pageCount));

        treeParams.put("list_type", "game_list");

        for (String key : treeParams.keySet()) {
            sb.append(key).append("=").append(treeParams.get(key)).append("\n");
        }
        Logs.e(sb.toString());
    }


    /**
     * 获取搜所游戏数据
     *
     * @param value
     * @param page
     * @param pageCount
     */
    public void getSearchGameList(String value, int page, int pageCount) {
        Map<String, String> params = new TreeMap<>();
        params.put("kw", value);
        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        params.put("list_type", "search");

        mViewModel.getGameList(params, new OnBaseCallback<GameListVo>() {
            @Override
            public void onSuccess(GameListVo gameListVo) {
                if (gameCenterDialogHelper != null) {
                    gameCenterDialogHelper.loadGameSearchData(gameListVo);
                }
            }
        });
    }
}
