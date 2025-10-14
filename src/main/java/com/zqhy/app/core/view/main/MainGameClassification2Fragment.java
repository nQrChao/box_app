package com.zqhy.app.core.view.main;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.zqhy.app.adapter.abs.AbsAdapter;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.core.data.model.game.GameNavigationVo;
import com.zqhy.app.core.data.model.mainpage.navigation.NewGameNavigationListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.vm.game.SearchViewModel;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author leeham2734
 * @date 2020/8/20-16:58
 * @description
 */
public class MainGameClassification2Fragment extends BaseFragment<SearchViewModel> {

    public static MainGameClassification2Fragment newInstance(int game_type, int game_genre_id, boolean fromJump) {
        MainGameClassification2Fragment fragment = new MainGameClassification2Fragment();
        Bundle bundle = new Bundle();
        bundle.putInt("game_genre_id", game_genre_id);
        bundle.putInt("game_type", game_type);
        bundle.putBoolean("fromJump", fromJump);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "游戏分类页";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_game_classification2;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private int game_genre_id;
    private int game_type;
    private boolean fromJump = false;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            game_genre_id = getArguments().getInt("game_genre_id");
            game_type = getArguments().getInt("game_type", 1);
            fromJump = getArguments().getBoolean("fromJump", false);
            last_game_type = game_type;
        }
        super.initView(state);
        showSuccess();
        bindViews();
        getGameClassificationList();
    }

    private int    last_game_type = 1;
    private String itemOrder      = "hot";

    private TextView                  mTvTabBt;
    private TextView                  mTvTabDiscount;
    private TextView                  mTvTabH5;
    private RecyclerView              mRecyclerViewTab;
    private FrameLayout               mFlContainer;
    private MainGameChildListFragment fragment;

    private LinearLayout mllTab;
    private TextView     mTvTabHot;
    private TextView                  mTvTabNew;
    private TextView           mTvTabPaly;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private void bindViews() {
        if (fromJump){
            findViewById(R.id.ll_title).setVisibility(View.VISIBLE);
            if (game_type == 1){
                ((TextView) findViewById(R.id.tv_title)).setText("BT游戏");
            }else if (game_type == 2){
                ((TextView) findViewById(R.id.tv_title)).setText("折扣游戏");
            }else if (game_type == 3){
                ((TextView) findViewById(R.id.tv_title)).setText("H5游戏");
            }
            findViewById(R.id.iv_back).setOnClickListener(v -> pop());
        }else {
            findViewById(R.id.ll_title).setVisibility(View.GONE);
        }
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mTvTabBt = findViewById(R.id.tv_tab_bt);
        if (AppConfig.isSpecial1Channel()) {
            mTvTabBt.setText("福利");
        }
        mTvTabDiscount = findViewById(R.id.tv_tab_discount);
        mTvTabH5 = findViewById(R.id.tv_tab_h5);
        mRecyclerViewTab = findViewById(R.id.recycler_view_tab);
        mFlContainer = findViewById(R.id.fl_container);

        mllTab = findViewById(R.id.ll_tab);
        mTvTabHot = findViewById(R.id.tv_tab_hot);
        mTvTabNew = findViewById(R.id.tv_tab_new);
        mTvTabPaly = findViewById(R.id.tv_tab_paly);

        mTvTabBt.setOnClickListener(view -> {
            if (last_game_type == 1) {
                return;
            }
            last_game_type = 1;
            onTabBtClick();
            refreshTabIndicatorList(true);
        });
        mTvTabDiscount.setOnClickListener(view -> {
            if (last_game_type == 2) {
                return;
            }
            last_game_type = 2;
            onTabDiscountClick();
            refreshTabIndicatorList(true);
        });
        mTvTabH5.setOnClickListener(view -> {
            if (last_game_type == 3) {
                return;
            }
            last_game_type = 3;
            onTabH5Click();
            refreshTabIndicatorList(true);
        });
        mTvTabHot.setOnClickListener(v -> {
            setTabSelect("hot");
            refreshGameList();
        });
        mTvTabNew.setOnClickListener(v -> {
            setTabSelect("newest");
            refreshGameList();

        });
        mTvTabPaly.setOnClickListener(v -> {
            setTabSelect("paly");
            refreshGameList();
        });
        initSubTabList();

        if (last_game_type == 1) {
            onTabBtClick();
        } else if (last_game_type == 2) {
            onTabDiscountClick();
        } else if (last_game_type == 3) {
            onTabH5Click();
        }

        mSwipeRefreshLayout.setProgressViewOffset(true, -20, 100);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_3478f6,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            //getGameClassificationList();
            refreshGameList();
        });
        //mSwipeRefreshLayout.setEnabled(false);
        //set layout fragment
        fragment = new MainGameChildListFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.fl_container, fragment).commitAllowingStateLoss();
    }

    private void setTabSelect(String order){
        if (order.equals(itemOrder)){
            return;
        }
        mTvTabHot.setTextColor(Color.parseColor("#9B9B9B"));
        mTvTabHot.setBackgroundResource(R.drawable.shape_white_big_radius_with_line_f2f2f2);
        mTvTabNew.setTextColor(Color.parseColor("#9B9B9B"));
        mTvTabNew.setBackgroundResource(R.drawable.shape_white_big_radius_with_line_f2f2f2);
        mTvTabPaly.setTextColor(Color.parseColor("#9B9B9B"));
        mTvTabPaly.setBackgroundResource(R.drawable.shape_white_big_radius_with_line_f2f2f2);
        switch (order){
            case "hot":
                itemOrder = "hot";
                mTvTabHot.setTextColor(Color.parseColor("#4E76FF"));
                mTvTabHot.setBackgroundResource(R.drawable.ts_shape_4e76ff_big_radius_with_line);
                break;
            case "newest":
                itemOrder = "newest";
                mTvTabNew.setTextColor(Color.parseColor("#4E76FF"));
                mTvTabNew.setBackgroundResource(R.drawable.ts_shape_4e76ff_big_radius_with_line);
                break;
            case "paly":
                itemOrder = "play";
                mTvTabPaly.setTextColor(Color.parseColor("#4E76FF"));
                mTvTabPaly.setBackgroundResource(R.drawable.ts_shape_4e76ff_big_radius_with_line);
                break;
        }
    }

    private void onTabBtClick() {
        mTvTabBt.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
        mTvTabBt.setBackgroundResource(R.drawable.ts_shape_4e76ff_5_radius);
        mTvTabBt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        mTvTabDiscount.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_9b9b9b));
        mTvTabDiscount.setBackgroundResource(R.drawable.ts_shape_f2f2f2_5_radius);
        mTvTabDiscount.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

        mTvTabH5.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_9b9b9b));
        mTvTabH5.setBackgroundResource(R.drawable.ts_shape_f2f2f2_5_radius);
        mTvTabH5.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
    }

    private void onTabDiscountClick() {
        mTvTabBt.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_9b9b9b));
        mTvTabBt.setBackgroundResource(R.drawable.ts_shape_f2f2f2_5_radius);
        mTvTabBt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

        mTvTabDiscount.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
        mTvTabDiscount.setBackgroundResource(R.drawable.ts_shape_4e76ff_5_radius);
        mTvTabDiscount.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        mTvTabH5.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_9b9b9b));
        mTvTabH5.setBackgroundResource(R.drawable.ts_shape_f2f2f2_5_radius);
        mTvTabH5.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
    }

    private void onTabH5Click() {
        mTvTabBt.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_9b9b9b));
        mTvTabBt.setBackgroundResource(R.drawable.ts_shape_f2f2f2_5_radius);
        mTvTabBt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

        mTvTabDiscount.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_9b9b9b));
        mTvTabDiscount.setBackgroundResource(R.drawable.ts_shape_f2f2f2_5_radius);
        mTvTabDiscount.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

        mTvTabH5.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
        mTvTabH5.setBackgroundResource(R.drawable.ts_shape_4e76ff_5_radius);
        mTvTabH5.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    private void initSubTabList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerViewTab.setLayoutManager(layoutManager);
    }

    private List<GameNavigationVo>                       gameNavigationVoList;
    private List<NewGameNavigationListVo.SearchListBean> gameSearchList;

    /**
     * 接口tabIndicator数据
     */
    public void getGameClassificationList() {
        if (mViewModel != null) {
            mViewModel.getGameHallList(new OnBaseCallback<NewGameNavigationListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                }

                @Override
                public void onSuccess(NewGameNavigationListVo gameNavigationListVo) {
                    if (gameNavigationListVo != null && gameNavigationListVo.isStateOK() && gameNavigationListVo.getData() != null) {
                        gameNavigationVoList = gameNavigationListVo.getData().getGenre_list();
                        gameSearchList = gameNavigationListVo.getData().getSearch_list();
                        setSubTabAdapter();
                    }
                }
            });
        }
    }

    private void refreshTabIndicatorList(boolean changeMainTab) {
        if (subTabAdapter != null) {
            if (changeMainTab) {
                int last_genre_id = 0;
                try {
                    last_genre_id = subTabAdapter.getTabVoList().get(lastTabPosition).genre_id;
                } catch (Exception e) {

                }
                selectTabByGenreId(last_genre_id);
            } else {
                subTabAdapter.setCurrentPosition(lastTabPosition);
                subTabAdapter.notifyDataSetChanged();
            }
            refreshGameListHot();
        }
    }

    private SubTabAdapter subTabAdapter;
    private int           lastTabPosition = 0;

    private void setSubTabAdapter() {
        List<SubTabVo> subTabVos = getTabsByGameType(last_game_type);
        subTabAdapter = new SubTabAdapter(_mActivity, subTabVos);
        subTabAdapter.setOnItemClickListener((v, position, data) -> {
            lastTabPosition = position;
            refreshTabIndicatorList(false);
        });
        mRecyclerViewTab.setAdapter(subTabAdapter);
        if (game_genre_id == 0) {
            refreshGameListHot();
        } else {
            selectTabByGenreId(game_genre_id);
            refreshGameListHot();
        }
    }


    public void selectTabGameTypeByGenreId(int game_type, int game_genre_id) {
        switch (game_type) {
            case 1:
                onTabBtClick();
                break;
            case 2:
                onTabDiscountClick();
                break;
            case 3:
                onTabH5Click();
                break;
        }
        last_game_type = game_type;
        selectTabByGenreId(game_genre_id);
        refreshGameListHot();
    }

    private void selectTabByGenreId(int game_genre_id) {
        if (subTabAdapter != null) {
            List<SubTabVo> subTabVos = getTabsByGameType(last_game_type);
            boolean findTabPosition = false;
            for (int i = 0; i < subTabVos.size(); i++) {
                if (subTabVos.get(i).genre_id == game_genre_id) {
                    lastTabPosition = i;
                    findTabPosition = true;
                    break;
                }
            }
            if (!findTabPosition) {
                lastTabPosition = 0;
            }
            subTabAdapter.setDatas(subTabVos);
            subTabAdapter.setCurrentPosition(lastTabPosition);
            subTabAdapter.notifyDataSetChanged();
            mRecyclerViewTab.scrollToPosition(subTabAdapter.getCurrentPosition());
        }
    }

    private List<SubTabVo> getTabsByGameType(int game_type) {
        List<SubTabVo> result = new ArrayList<>();
        List<SubTabVo> all = getAllTabList(game_type);

        for (SubTabVo subTabVo : all) {
            if (subTabVo.isContainsGameType(String.valueOf(game_type))) {
                result.add(subTabVo);
            }
        }
        return result;
    }


    private List<SubTabVo> getAllTabList(int game_type) {
        List<SubTabVo> gameTabVoList = new ArrayList<>();
        gameTabVoList.clear();
        //添加精选页面
        gameTabVoList.add(getJxGameTabVo());
        //添加新游页面
        gameTabVoList.add(getNewGameTabVo());
        //添加自定义关键字列表
        for (int i = 0; i < gameSearchList.size(); i++) {
            NewGameNavigationListVo.SearchListBean searchListBean = gameSearchList.get(i);
            if (!TextUtils.isEmpty(searchListBean.getVisible_word())) gameTabVoList.add(new SubTabVo(1, searchListBean.getVisible_word()).addParams("kw", searchListBean.getSearch_word()).addParams("order", itemOrder).addGameType("1", "2", "3", "4"));
        }
        //添加分类列表
        if (gameNavigationVoList != null) {
            for (GameNavigationVo navigationVo : gameNavigationVoList) {
                SubTabVo gameTab = new SubTabVo();
                gameTab.setGenre_id(navigationVo.getGenre_id());
                gameTab.setType(navigationVo.getType());
                gameTab.setGenre_name(navigationVo.getGenre_name());
                gameTab.addParams("genre_id", String.valueOf(navigationVo.getGenre_id()));
                gameTab.addGameType("1", "2", "3", "4");
                gameTabVoList.add(gameTab);
            }
        }
        return gameTabVoList;
    }

    public static class SubTabVo extends GameNavigationVo {
        private Map<String, String> params;

        public SubTabVo() {
        }

        public SubTabVo(int tabIndicatorId, String tabTitle) {
            genre_id = tabIndicatorId;
            setGenre_name(tabTitle);
        }

        /**
         * 添加参数
         *
         * @param key
         * @param value
         */
        public SubTabVo addParams(String key, String value) {
            if (params == null) {
                params = new TreeMap<>();
            }
            params.put(key, value);
            return this;
        }

        public List<String> gameTypeList;

        public SubTabVo addGameType(String... game_type) {
            if (game_type.length > 0) {
                if (gameTypeList == null) {
                    gameTypeList = new ArrayList<>();
                }
                for (int i = 0; i < game_type.length; i++) {
                    gameTypeList.add(game_type[i]);
                }
            }
            return this;
        }

        public boolean isContainsGameType(String game_type) {
            if (gameTypeList == null) {
                return false;
            }
            return gameTypeList.contains(game_type);
        }
    }

    @NonNull
    private SubTabVo getJxGameTabVo() {
        return new SubTabVo(-10, "精选")
                .addParams("order", "hot")
                .addGameType("1", "2", "3", "4");
    }

    @NonNull
    private SubTabVo getHotGameTabVo() {
        return new SubTabVo(-1, "热门")
                .addParams("order", "hot")
                .addGameType("1", "2", "3", "4");
    }

    @NonNull
    private SubTabVo getNewGameTabVo() {
        return new SubTabVo(-2, "新游")
                .addParams("order", "newest")
                .addGameType("1", "2", "3", "4");
    }

    @NonNull
    private SubTabVo getVipGameTabVo() {
        return new SubTabVo(-3, "满V")
                .addParams("kw", "满V")
                .addGameType("1");
    }

    @NonNull
    private SubTabVo getGmGameTabVo() {
        String tabTitle = "GM";
        if (AppConfig.isSpecial1Channel()) {
            tabTitle = "特权";
        }
        return new SubTabVo(-4, tabTitle)
                .addParams("kw", "GM")
                .addGameType("1");
    }

    @NonNull
    private SubTabVo getDiscountGameTabVo() {
        return new SubTabVo(-5, "折扣(低)")
                .addParams("order", "discount")
                .addGameType("2", "3", "4");
    }


    class SubTabAdapter extends AbsAdapter<SubTabVo> {
        private int currentPosition = 0;

        public SubTabAdapter(Context context, List<SubTabVo> labels) {
            super(context, labels);
        }

        public int getCurrentPosition() {
            return currentPosition;
        }

        public void setCurrentPosition(int position) {
            currentPosition = position;
            notifyDataSetChanged();
        }

        public List<SubTabVo> getTabVoList() {
            return mLabels;
        }

        private SubTabVo getSelectedSubTvo() {
            try {
                return mLabels.get(currentPosition);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, SubTabVo data, int position) {
            ViewHolder holder = (ViewHolder) viewHolder;
            boolean isSelected = currentPosition == position;

            holder.mTvSubTitle.setText(data.getGenre_name());
            holder.mTvSubTitle.setTextColor(isSelected ? Color.parseColor("#5571FE"): Color.parseColor("#9B9B9B"));
            holder.mTvSubTitle.setBackgroundColor(isSelected ? Color.parseColor("#F2F2F2"): Color.parseColor("#FFFFFF"));
            holder.mTvSubTitle.setTextSize(14);
            holder.mTvSubTitle.setTypeface(Typeface.defaultFromStyle(isSelected ? Typeface.BOLD : Typeface.NORMAL));
            holder.mLayoutTagSelected.setVisibility(isSelected ? View.GONE : View.GONE);
        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_game_classification_sub_tab;
        }

        @Override
        public ViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }

        class ViewHolder extends AbsViewHolder {
            private TextView mTvSubTitle;
            private View     mLayoutTagSelected;

            public ViewHolder(View itemView) {
                super(itemView);
                mTvSubTitle = findViewById(R.id.tv_sub_title);
                mLayoutTagSelected = findViewById(R.id.layout_tag_selected);
            }
        }
    }

    private SubTabVo lastSelectTabVo;

    private void refreshGameList() {
        if (subTabAdapter != null) {
            SubTabVo subTabVo = subTabAdapter.getSelectedSubTvo();
            if (subTabVo.getGenre_id() == -10){//判断是不是精选，精选需要请求其他接口
                mllTab.setVisibility(View.GONE);
                Map<String, String> params = subTabVo.params;
                params.put("order", "hot");
                refreshGameListJx(subTabVo);
            } else if (subTabVo.getGenre_id() == -2){//新游
                mllTab.setVisibility(View.VISIBLE);
                Map<String, String> params = subTabVo.params;
                params.put("order", "newest");
                //params.put("game_type", String.valueOf(game_type));
                if ("play".equals(itemOrder)){
                    params.remove("only_reserve");
                    //params.put("has_coupon", "yes");
                    params.put("order", "ranking");
                }else if ("newest".equals(itemOrder)){
                    //params.remove("has_coupon");
                    params.put("only_reserve", "yes");
                }else {
                    params.remove("only_reserve");
                    //params.remove("has_coupon");
                }
                if (game_type == 1){
                    mTvTabHot.setText("默认排序");
                    mTvTabNew.setText("即将上线");
                    mTvTabPaly.setText("严选好游");
                }else if(game_type == 2){
                    mTvTabHot.setText("默认排序");
                    mTvTabNew.setText("即将上线");
                    mTvTabPaly.setText("严选好游");
                }else if(game_type == 3){
                    mTvTabHot.setText("默认排序");
                    mTvTabNew.setText("即将上线");
                    mTvTabPaly.setText("严选好游");
                }
                refreshGameList(subTabVo);
            } else{
                mllTab.setVisibility(View.VISIBLE);
                mTvTabHot.setText("热门排序");
                mTvTabNew.setText("新上架");
                mTvTabPaly.setText("严选好游");
                mTvTabPaly.setVisibility(View.VISIBLE);
                Map<String, String> params = subTabVo.params;

                if ("play".equals(itemOrder)){
                    //params.put("has_coupon", "yes");
                    params.put("order", "ranking");
                }else {
                    params.put("order", itemOrder);
                }
                refreshGameList(subTabVo);
            }
        }
    }

    private void refreshGameListHot() {
        if (subTabAdapter != null) {
            SubTabVo subTabVo = subTabAdapter.getSelectedSubTvo();
            if (subTabVo.getGenre_id() == -10){//判断是不是精选，精选需要请求其他接口
                mllTab.setVisibility(View.GONE);
                Map<String, String> params = subTabVo.params;
                params.put("order", "hot");
                refreshGameListJx(subTabVo);
            } else if (subTabVo.getGenre_id() == -2){//新游
                setTabSelect("hot");
                itemOrder = "hot";
                mllTab.setVisibility(View.VISIBLE);
                Map<String, String> params = subTabVo.params;
                params.put("order", "newest");
                params.remove("only_reserve");
                //params.remove("has_coupon");
                //params.put("game_type", String.valueOf(game_type));
                if (game_type == 1){
                    mTvTabHot.setText("默认排序");
                    mTvTabNew.setText("即将上线");
                    mTvTabPaly.setText("严选好游");
                }else if(game_type == 2){
                    mTvTabHot.setText("默认排序");
                    mTvTabNew.setText("即将上线");
                    mTvTabPaly.setText("严选好游");
                }else if(game_type == 3){
                    mTvTabHot.setText("默认排序");
                    mTvTabNew.setText("即将上线");
                    mTvTabPaly.setText("严选好游");
                }
                refreshGameList(subTabVo);
            } else {
                mllTab.setVisibility(View.VISIBLE);
                mTvTabHot.setText("热门排序");
                mTvTabNew.setText("新上架");
                mTvTabPaly.setText("严选好游");
                mTvTabPaly.setVisibility(View.VISIBLE);
                setTabSelect("hot");
                itemOrder = "hot";
                Map<String, String> params = subTabVo.params;
                params.put("order", itemOrder);
                refreshGameList(subTabVo);
            }
        }
    }

    private void refreshGameList(SubTabVo subTabVo) {
        if (subTabVo == null) {
            return;
        }
        lastSelectTabVo = subTabVo;
        Map<String, String> treeParams = new TreeMap<>();
        treeParams.clear();
        Map<String, String> params = subTabVo.params;
        for (String key : params.keySet()) {
            treeParams.put(key, params.get(key));
        }
        //treeParams.put("game_type", String.valueOf(last_game_type));
        fragment.refreshGameList(treeParams, new MainGameChildListFragment.LoadingListener() {
            @Override
            public void onLoadingBefore() {

            }

            @Override
            public void onLoadingAfter() {
            }
        });
    }

    private void refreshGameListJx(SubTabVo subTabVo) {
        if (subTabVo == null) {
            return;
        }
        lastSelectTabVo = subTabVo;
        Map<String, String> treeParams = new TreeMap<>();
        treeParams.clear();
        Map<String, String> params = subTabVo.params;
        for (String key : params.keySet()) {
            treeParams.put(key, params.get(key));
        }
        //treeParams.put("game_type", String.valueOf(last_game_type));
        fragment.refreshGameListJx(treeParams, new MainGameChildListFragment.LoadingListener() {
            @Override
            public void onLoadingBefore() {

            }

            @Override
            public void onLoadingAfter() {
            }
        });
    }
}
