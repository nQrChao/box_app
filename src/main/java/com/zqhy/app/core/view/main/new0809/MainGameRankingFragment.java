package com.zqhy.app.core.view.main.new0809;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.box.other.hjq.toast.Toaster;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.AppMenuBeanVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.new0809.AppMenuVo;
import com.zqhy.app.core.data.model.game.new0809.MainGameRankDataVo;
import com.zqhy.app.core.data.model.splash.AppStyleConfigs;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.main.new0809.holder.MainGameRankingItemHolder;
import com.zqhy.app.core.vm.game.SearchViewModel;
import com.zqhy.app.glide.GlideRoundTransformNew;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.cache.ACache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2021/8/14 0014-16:45
 * @description
 */
public class MainGameRankingFragment extends BaseFragment<SearchViewModel> {


    public static MainGameRankingFragment newInstance(int tab_id) {
        MainGameRankingFragment fragment = new MainGameRankingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tab_id", tab_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static MainGameRankingFragment newInstance(int tab_id, boolean fromDetail) {
        MainGameRankingFragment fragment = new MainGameRankingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tab_id", tab_id);
        bundle.putBoolean("fromDetail", fromDetail);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main_game_ranking;
    }

    @Override
    public int getContentResId() {
        return R.id.swipe_refresh_layout;
    }

    private int tab_id;
    private boolean fromDetail;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            tab_id = getArguments().getInt("tab_id");
            fromDetail = getArguments().getBoolean("fromDetail");
        }
        super.initView(state);
        bindViews();
        addFixHeaderView();
    }

    private SwipeRefreshLayout  mSwipeRefreshLayout;
    private RecyclerView        mRecyclerView;
    private LinearLayout        mLlContainer;
    private ImageView           mImage;
    private BaseRecyclerAdapter mAdapter;

    private void bindViews() {
        if (fromDetail){
            findViewById(R.id.fl_status_bar).setVisibility(View.VISIBLE);
            findViewById(R.id.rl_title).setVisibility(View.VISIBLE);
            if (tab_id == 6){
                ((TextView)findViewById(R.id.tv_title)).setText("热游榜");
            }else if (tab_id == 7){
                ((TextView)findViewById(R.id.tv_title)).setText("新游榜");
            }else if (tab_id == 24){
                ((TextView)findViewById(R.id.tv_title)).setText("折扣榜");
            }
            findViewById(R.id.iv_back).setOnClickListener(v -> {
                pop();
            });
            findViewById(R.id.ll_container).setVisibility(View.GONE);
        }else {
            findViewById(R.id.fl_status_bar).setVisibility(View.GONE);
            findViewById(R.id.rl_title).setVisibility(View.GONE);
            findViewById(R.id.ll_container).setVisibility(View.GONE);
        }
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = findViewById(R.id.recycler_view);
        mLlContainer = findViewById(R.id.ll_container);
        mImage = findViewById(R.id.image);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mImage.getLayoutParams();
        params.width = ScreenUtil.getScreenWidth(_mActivity);
        params.height = ScreenUtil.getScreenWidth(_mActivity) / 3;
        mImage.setLayoutParams(params);


        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_3478f6,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            initData();
        });

        DividerItemDecoration decoration = new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.main_pager_item_decoration));
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        manager.setSmoothScrollbarEnabled(true);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(GameInfoVo.class, new MainGameRankingItemHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, this).setTag(R.id.tag_sub_fragment, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void addFixHeaderView() {
        TabInfoListVo infoListVo = new TabInfoListVo();
        infoListVo.setDefaultItemGameId(tab_id);
        String json = ACache.get(_mActivity).getAsString(AppStyleConfigs.APP_MENU_JSON_KEY);
        Gson gson = new Gson();
        AppMenuBeanVo appMenuBeanVo = gson.fromJson(json, new TypeToken<AppMenuBeanVo>() {
        }.getType());

        if (appMenuBeanVo != null && appMenuBeanVo.paihang_menu != null) {
            for (AppMenuVo appMenuVo : appMenuBeanVo.paihang_menu) {
                infoListVo.addItem(new TabInfoVo(appMenuVo.id, appMenuVo.name, appMenuVo.api,
                        appMenuVo.params == null ? null : appMenuVo.params.getParams()));
            }
        }
        if (infoListVo.data_list == null) {
            return;
        }

        Map<Integer, View> viewMap = new HashMap<>();
        View lastItemView = null;

        for (int i = 0; i < infoListVo.data_list.size(); i++) {
            TabInfoVo tabInfoVo = infoListVo.data_list.get(i);
            View itemView = createItemHeaderView(tabInfoVo, viewMap);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtil.dp2px(_mActivity, 100), ScreenUtil.dp2px(_mActivity, 36));
            params.gravity = Gravity.CENTER;
            params.topMargin = ScreenUtil.dp2px(_mActivity, 16);
            params.bottomMargin = ScreenUtil.dp2px(_mActivity, 16);
            params.rightMargin = ScreenUtil.dp2px(_mActivity, 10);
            if (i == 0) {
                params.leftMargin = ScreenUtil.dp2px(_mActivity, 10);
            }
            if (tabInfoVo.id == infoListVo.getLastIndexItemGameId()) {
                lastItemView = itemView;
            }
            mLlContainer.addView(itemView, params);

        }
        for (Integer gameid : viewMap.keySet()) {
            View target = viewMap.get(gameid);
            target.setOnClickListener(v -> {
                infoListVo.setLastIndexItemGameId(gameid);
                onHeaderTabClick(v, viewMap);
            });
        }
        if (lastItemView != null) {
            lastItemView.performClick();
        }
    }

    private void onHeaderTabClick(View view, Map<Integer, View> viewMap) {
        for (Integer gameid : viewMap.keySet()) {
            TextView target = (TextView) viewMap.get(gameid);
            boolean isCheck = view == target;
            if (isCheck) {
                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(ScreenUtil.dp2px(_mActivity, 5));
                gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                gd.setColor(Color.parseColor("#4E76FF"));
                target.setBackground(gd);
                target.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                target.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
                currentTabInfoVo = (TabInfoVo) view.getTag(R.id.tag_first);
                initData();
            } else {
                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(ScreenUtil.dp2px(_mActivity, 5));
                gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));
                target.setBackground(gd);
                target.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                target.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_9b9b9b));
            }
        }
    }

    private View createItemHeaderView(TabInfoVo tabInfoVo, Map<Integer, View> viewMap) {
        TextView item = new TextView(_mActivity);
        item.setText(tabInfoVo.text);
        item.setGravity(Gravity.CENTER);
        item.setTextSize(16);
        item.setTag(R.id.tag_first, tabInfoVo);
        viewMap.put(tabInfoVo.id, item);
        return item;
    }


    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        //        initData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        initData();
    }

    private void loadingDataComplete() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }


    private TabInfoVo currentTabInfoVo;

    private void initData() {
        if (mViewModel != null && currentTabInfoVo != null) {
            Map<String, String> params = currentTabInfoVo.params;
            Map<String, String> valueParams = new HashMap<>();
            if (params != null) {
                valueParams.putAll(params);
            }
            valueParams.put("api", currentTabInfoVo.api);
            mViewModel.getMainRankingData(valueParams, new OnBaseCallback<MainGameRankDataVo>() {


                @Override
                public void onBefore() {
                    super.onBefore();
                    if (!mSwipeRefreshLayout.isRefreshing()) {
                        showLoading();
                    }
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    loadingDataComplete();
                }

                @Override
                public void onSuccess(MainGameRankDataVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.data != null) {
                                if (data.data.cover != null) {
                                    //                                    GlideUtils.loadImage(_mActivity, data.data.cover.pic, mImage, R.mipmap.img_placeholder_v_2, 10);
                                    int width = ScreenUtil.getScreenWidth(_mActivity);
                                    int height = width / 3;

                                    GradientDrawable gd = new GradientDrawable();
                                    gd.setSize(width, height);
                                    gd.setColor(Color.WHITE);

                                    GlideApp.with(_mActivity).asBitmap()
                                            .load(data.data.cover.pic)
                                            .override(width, height)
                                            .transform(new GlideRoundTransformNew(_mActivity, 10))
                                            .into(mImage);

                                }
                                mAdapter.clear();
                                if (data.data.list != null && !data.data.list.isEmpty()) {
                                    int index = 1;
                                    for (GameInfoVo gameInfoVo : data.data.list) {
                                        gameInfoVo.setIndexPosition(index);
                                        index++;
                                    }
                                    mAdapter.addAllData(data.data.list);
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private class TabInfoListVo {
        public List<TabInfoVo> data_list;

        public TabInfoListVo addItem(TabInfoVo tabInfoVo) {
            if (data_list == null) {
                data_list = new ArrayList<>();
            }
            data_list.add(tabInfoVo);
            return this;
        }

        private int     lastIndexItemGameId;
        private boolean hasSetDefaultItemGameId;
        private boolean hasChangeItemGameId;

        public int getLastIndexItemGameId() {
            if (hasChangeItemGameId) {
                return lastIndexItemGameId;
            } else {
                return getDefaultItemGameId();
            }
        }

        public void setDefaultItemGameId(int defaultIndexItemGameId) {
            this.lastIndexItemGameId = defaultIndexItemGameId;
            hasSetDefaultItemGameId = true;
        }

        public void setLastIndexItemGameId(int lastIndexItemGameId) {
            if (this.lastIndexItemGameId == lastIndexItemGameId) {
                return;
            }
            hasChangeItemGameId = true;
            this.lastIndexItemGameId = lastIndexItemGameId;
        }

        private int getDefaultItemGameId() {
            if (hasSetDefaultItemGameId && lastIndexItemGameId != 0) {
                return lastIndexItemGameId;
            }
            if (data_list == null || data_list.isEmpty()) {
                return 0;
            }
            return data_list.get(0).id;
        }
    }

    private class TabInfoVo {
        public int                 id;
        public String              text;
        public String              api;
        public Map<String, String> params;

        public TabInfoVo(int id, String text, String api) {
            this.id = id;
            this.text = text;
            this.api = api;
        }

        public TabInfoVo(int id, String text, String api, Map<String, String> params) {
            this.id = id;
            this.text = text;
            this.api = api;
            this.params = params;
        }
    }

}
