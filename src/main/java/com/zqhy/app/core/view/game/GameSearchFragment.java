package com.zqhy.app.core.view.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.box.common.glide.GlideApp;
import com.box.other.hjq.toast.Toaster;
import com.google.android.flexbox.FlexboxLayout;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zqhy.app.Setting;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.data.model.game.search.GameSearchDataVo;
import com.zqhy.app.core.data.model.game.search.GameSimpleInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.inner.SoftKeyBoardListener;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.KeyboardUtils;
import com.zqhy.app.core.view.game.holder.GameSearchComplexItemHolder;
import com.zqhy.app.core.view.game.holder.GameSearchSimpleItemHolder;
import com.zqhy.app.core.vm.game.SearchViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.ButtonClickUtils;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author Administrator
 */
public class GameSearchFragment extends BaseFragment<SearchViewModel> {

    private final int SEARCH_HISTORY_STATE_NORMAL = 1;

    private final int SEARCH_HISTORY_STATE_DELETE = 2;

    private int SEARCH_HISTORY_STATE = SEARCH_HISTORY_STATE_NORMAL;

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_game_search;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private Handler mHandler = new Handler();

    private String mEditHint = "";

    private String mEditHintShow = "";

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("");
        bindViews();
        initData();
    }


    @Override
    public void onStart() {
        super.onStart();
        setClickBlankAreaHideKeyboard(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setClickBlankAreaHideKeyboard(true);

    }

    @Override
    protected void onVisible() {
        super.onVisible();
        setClickBlankAreaHideKeyboard(false);
    }

    @Override
    protected void onInVisible() {
        super.onInVisible();
        setClickBlankAreaHideKeyboard(true);
    }

    @Override
    public void start(ISupportFragment toFragment) {
        hideSoftInput();
        super.start(toFragment);
    }

    private AppCompatEditText mEtSearch;
    private ImageView mIvClearSearch;
    private TextView mBtnSearch;
    private FrameLayout mLlContentLayout;
    private LinearLayout mLlSearchLayout;
    private LinearLayout mLlHotSearch;
    private RecyclerView mRecyclerView;
    private ImageView mIvAd;
    private LinearLayout mLlSearchHistory;
    private FlexboxLayout mFlexBoxLayout;
    private FrameLayout mFlSearchContainer;
    private XRecyclerView mXRecyclerView;
    private LinearLayout mLlSearchEmpty;


    private void bindViews() {
        findViewById(R.id.iv_back).setOnClickListener(v -> pop());
        mEtSearch = findViewById(R.id.et_search);
        mIvClearSearch = findViewById(R.id.iv_clear_search);
        mBtnSearch = findViewById(R.id.btn_search);
        mLlContentLayout = findViewById(R.id.ll_content_layout);
        mLlSearchLayout = findViewById(R.id.ll_search_layout);
        mLlSearchEmpty = findViewById(R.id.ll_search_empty);
        mLlHotSearch = findViewById(R.id.ll_hot_search);
        mRecyclerView = findViewById(R.id.recycler_view);
        mIvAd = findViewById(R.id.iv_ad);
        mLlSearchHistory = findViewById(R.id.ll_search_history);
        mFlexBoxLayout = findViewById(R.id.flex_box_layout);
        mFlSearchContainer = findViewById(R.id.fl_search_container);
        mXRecyclerView = findViewById(R.id.xRecyclerView);

        mLlSearchLayout.setVisibility(View.VISIBLE);
        mFlSearchContainer.setVisibility(View.GONE);

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mHandler.removeCallbacks(searchRunnable);
                String mEditText = mEtSearch.getText().toString().trim();
                if (TextUtils.isEmpty(mEditText)) {
                    mIvClearSearch.setVisibility(View.GONE);
                    mLlSearchEmpty.setVisibility(View.GONE);
                    setLayoutSearching(false);
                } else {
                    if (Setting.HIDE_FIVE_FIGURE == 1 && ("刷充".equals(mEditText) || ("破解".equals(mEditText)) || ("现金".equals(mEditText)) || ("修改器".equals(mEditText)) || ("加速".equals(mEditText)) || ("加速器".equals(mEditText)) || ("GM".equals(mEditText) || "gm".equals(mEditText)))){
                        mIvClearSearch.setVisibility(View.VISIBLE);
                        mXRecyclerView.setVisibility(View.GONE);

                        searchComplete(false);
                    } else if ("99026".equals(BuildConfig.APP_UPDATE_ID) && ("GM".equals(mEditText) || "gm".equals(mEditText))){
                        mIvClearSearch.setVisibility(View.VISIBLE);
                        mXRecyclerView.setVisibility(View.GONE);

                        searchComplete(false);
                    } else {
                        mIvClearSearch.setVisibility(View.VISIBLE);
                        mHandler.postDelayed(searchRunnable, delayMillis);
                    }
                }
            }
        });
        mIvClearSearch.setOnClickListener(v -> {
            mEtSearch.getText().clear();
            KeyboardUtils.showSoftInput(_mActivity, mEtSearch);
        });
        initList();

        mBtnSearch.setOnClickListener(v -> {
            if (ButtonClickUtils.isFastClick()) {
                return;
            }
            searchGameContainsEditHint(true);
            searchComplete();
        });

        mEtSearch.postDelayed(() -> {
            KeyboardUtils.showSoftInput(_mActivity, mEtSearch);
        }, 200);

        mXRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                hideSoftInput();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void searchGameContainsEditHint(boolean isContainsEditHint) {
        if (isContainsEditHint) {
            String value = mEtSearch.getText().toString().trim();
            if (TextUtils.isEmpty(value)) {
                if (TextUtils.isEmpty(mEditHint)) {
                    Toaster.show("请输入要搜索的游戏名称哦");
                    //ToastT.warning("请输入要搜索的游戏名称哦~");
                    return;
                }
                mEtSearch.setText(mEditHint);
                mEtSearch.setSelection(mEditHint.length());
            }
        }
        mHandler.removeCallbacks(searchRunnable);
        mHandler.post(searchRunnable);
    }

    private SoftKeyBoardListener softKeyBoardListener;

    private void setEditText() {
        //软键盘显示/隐藏监听
        softKeyBoardListener = new SoftKeyBoardListener(_mActivity);
        softKeyBoardListener.setOnSoftKeyBoardChangeListener(new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                setEditFocus();
            }

            @Override
            public void keyBoardHide(int height) {
                setEditUnFocus();
            }
        });
        //设置点击事件,显示软键盘
        mEtSearch.setOnClickListener(v -> KeyboardUtils.showSoftInput(_mActivity, mEtSearch));
    }

    private void setEditFocus() {
        mEtSearch.setFocusable(true);
        mEtSearch.setFocusableInTouchMode(true);
        mEtSearch.setCursorVisible(true);
        mEtSearch.requestFocus();
    }

    private void setEditUnFocus() {
        mEtSearch.setFocusable(false);
        mEtSearch.setFocusableInTouchMode(false);
        mEtSearch.setCursorVisible(false);
        mEtSearch.clearFocus();
    }

    private long delayMillis = 500;

    Runnable searchRunnable = () -> {
        page = 1;
        getSearchGameList();
    };

    private BaseRecyclerAdapter mAdapter;
    private BaseRecyclerAdapter mXAdapter;

    private int page = 1, pageCount = 12;

    private void initList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setNestedScrollingEnabled(false);

        mAdapter = new BaseRecyclerAdapter.Builder()
                .bind(GameSearchDataVo.SearchJumpInfoVo.class, new SearchItemHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, this);

        mRecyclerView.setAdapter(mAdapter);


        LinearLayoutManager xLayoutManager = new LinearLayoutManager(_mActivity);
        mXRecyclerView.setLayoutManager(xLayoutManager);

        mXAdapter = new BaseRecyclerAdapter.Builder()
                .bind(GameInfoVo.class, new GameSearchComplexItemHolder(_mActivity))
                .bind(GameSimpleInfoVo.class, new GameSearchSimpleItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);

        mXRecyclerView.setAdapter(mXAdapter);

        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getSearchGameList();
            }

            @Override
            public void onLoadMore() {
                if (page < 0) {
                    return;
                }
                page++;
                getSearchGameList();
            }
        });
        mXRecyclerView.setRefreshTimeVisible(true);
    }

    private void setHotSearch(List<GameSearchDataVo.SearchJumpInfoVo> gameInfoVoList) {
        if (gameInfoVoList != null && !gameInfoVoList.isEmpty()) {
            mLlHotSearch.setVisibility(View.VISIBLE);
            mAdapter.clear();
            mAdapter.addAllData(gameInfoVoList);
        } else {
            mLlHotSearch.setVisibility(View.GONE);
        }
    }

    private void setSearchAd(GameSearchDataVo.SearchJumpInfoVo searchAd) {
        if (searchAd != null) {
            mIvAd.setVisibility(View.VISIBLE);
            GlideApp.with(_mActivity)
                    .asBitmap()
                    .load(searchAd.getPic())
                    .placeholder(R.mipmap.img_placeholder_v_2)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            if (bitmap != null) {
                                LinearLayout.LayoutParams mIvParams = (LinearLayout.LayoutParams) mIvAd.getLayoutParams();
                                int ivWidth = ScreenUtil.getScreenWidth(_mActivity) - mIvParams.leftMargin - mIvParams.rightMargin;
                                int bitWidth = bitmap.getWidth();
                                int bitHeight = bitmap.getHeight();

                                int ivHeight = (ivWidth * bitHeight) / bitWidth;

                                mIvParams.width = ivWidth;
                                mIvParams.height = ivHeight;

                                mIvAd.setLayoutParams(mIvParams);
                                mIvAd.setImageBitmap(bitmap);
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable drawable) {

                        }
                    });


            mIvAd.setOnClickListener(v -> appJumpAction(searchAd));
        } else {
            mIvAd.setVisibility(View.GONE);
        }
    }

    private void initData() {
        if (mViewModel != null) {
            mViewModel.getGameSearchData(new OnBaseCallback<GameSearchDataVo>() {
                @Override
                public void onSuccess(GameSearchDataVo data) {
                    showSuccess();
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                mEditHint = data.getData().getS_best_title();
                                mEditHintShow = data.getData().getS_best_title_show();
                                mEtSearch.setHint(TextUtils.isEmpty(mEditHintShow) ? "请输入游戏名~" : mEditHintShow);
                                setHotSearch(data.getData().getS_best());
                                setSearchAd(data.getData().getS_best_pic());
                                initSearchHotData(data.getData().getSearch_hot_word());
                            }
                        } else {
                            Toaster.show(data.getMsg());
                            //ToastT.error(_mActivity, data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private class SearchItemHolder extends BaseItemHolder<GameSearchDataVo.SearchJumpInfoVo, SearchItemHolder.ViewHolder> {

        private float density;

        public SearchItemHolder(Context context) {
            super(context);
            density = ScreenUtil.getScreenDensity(context);
        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_game_search_hot;
        }

        @Override
        public ViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }

        @Override
        protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameSearchDataVo.SearchJumpInfoVo item) {
            GlideUtils.loadGameIcon(_mActivity, item.getIcon(), holder.mIvIcon);
            holder.mTvTitle.setText(item.getTitle());
            holder.mTvGameName.setText(item.getTitle2());

            holder.mTvTag.setVisibility(View.VISIBLE);
            holder.mTvTag.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            if (item.getT_type() == 1) {
                holder.mTvTag.setText("热");
                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(4 * density);
                gd.setColor(Color.parseColor("#FFBE00"));
                holder.mTvTag.setBackground(gd);
            } else if (item.getT_type() == 2) {
                holder.mTvTag.setText("新");
                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(4 * density);
                gd.setColor(ContextCompat.getColor(mContext, R.color.color_3478f6));
                holder.mTvTag.setBackground(gd);
            } else if (item.getT_type() == 3) {
                holder.mTvTag.setText("荐");
                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(4 * density);
                gd.setColor(Color.parseColor("#FF2B3F"));
                holder.mTvTag.setBackground(gd);
            } else {
                holder.mTvTag.setVisibility(View.GONE);
            }
            holder.mLlItem.setOnClickListener(v -> {
                KeyboardUtils.hideSoftInput(_mActivity, mEtSearch);
                appJump(item);
            });
        }

        public class ViewHolder extends AbsHolder {
            private LinearLayout mLlItem;
            private TextView mTvTitle;
            private TextView mTvTag;
            private TextView mTvGameName;
            private ImageView mIvIcon;

            public ViewHolder(View view) {
                super(view);
                mLlItem = findViewById(R.id.ll_item);
                mTvTitle = findViewById(R.id.tv_title);
                mTvTag = findViewById(R.id.tv_tag);
                mTvGameName = findViewById(R.id.tv_game_name);
                mIvIcon = findViewById(R.id.iv_icon);
            }
        }
    }

    /**
     * 获取搜索游戏数据
     */
    private void getSearchGameList() {
        String value = mEtSearch.getText().toString().trim();
        if (TextUtils.isEmpty(value)) {
            Toaster.show("请输入要搜索的游戏名称哦");
            //ToastT.warning("请输入要搜索的游戏名称哦~");
            return;
        }
        if (page == 1) {
            mXRecyclerView.setNoMore(false);
        }
        getSearchGameList(value);
    }

    /**
     * 获取搜所游戏数据
     *
     * @param value
     */
    public void getSearchGameList(String value) {
        Map<String, String> params = new TreeMap<>();
        params.put("kw", value);
        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        params.put("list_type", "search");
        params.put("more", "1");
        params.put("show_reserve", "yes");
        params.put("on_page", "search");

        if (mViewModel != null) {
            mViewModel.getGameList(params, new OnBaseCallback<GameListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    if (mXRecyclerView != null) {
                        mXRecyclerView.refreshComplete();
                        mXRecyclerView.loadMoreComplete();
                    }
                }

                @Override
                public void onSuccess(GameListVo gameListVo) {
                    if (gameListVo != null) {
                        if (gameListVo.isStateOK()) {
                            mXRecyclerView.setVisibility(View.VISIBLE);
                            if (gameListVo.getData() != null && !gameListVo.getData().isEmpty()) {
                                if (page <= 1) {
                                    mXAdapter.clear();
                                    searchComplete(true);
                                    mXAdapter.setDatas(gameListVo.getData());
                                }else{
                                    mXAdapter.addAllData(gameListVo.getData());
                                }
                                if (gameListVo.getData().size() < pageCount) {
                                    //has no more data
                                    page = -1;
                                    mXRecyclerView.setNoMore(true);
                                }
                                mXAdapter.notifyDataSetChanged();
                            } else {
                                if (page == 1) {
                                    mXRecyclerView.setVisibility(View.GONE);
                                    Toaster.show("没有搜索到您想要的游戏");
                                    //ToastT.normal(_mActivity, "没有搜索到您想要的游戏");
                                    searchComplete(false);
                                } else {
                                    GameSearchFragment.this.page = -1;
                                    mXRecyclerView.setNoMore(true);
                                    mXAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    private void searchComplete() {
        if (page == 1) {
            mXRecyclerView.refreshComplete();
            mXRecyclerView.scrollToPosition(0);
        } else {
            mXRecyclerView.loadMoreComplete();
        }
        KeyboardUtils.hideSoftInput(_mActivity, mEtSearch);
    }

    private void searchComplete(boolean searchResult) {
        setLayoutSearching(searchResult);
        mLlSearchEmpty.setVisibility(searchResult ? View.GONE : View.VISIBLE);
    }

    private void setLayoutSearching(boolean isSearching) {
        if (isSearching) {
            mLlSearchLayout.setVisibility(View.GONE);
            mFlSearchContainer.setVisibility(View.VISIBLE);
        } else {
            mLlSearchLayout.setVisibility(View.VISIBLE);
            mFlSearchContainer.setVisibility(View.GONE);
        }
    }

    /**
     * 刷新搜索历史
     */
    private void initSearchHotData(List<String> searchHotWord) {
        mFlexBoxLayout.removeAllViews();
        if (searchHotWord != null && !searchHotWord.isEmpty()) {
            mLlSearchHistory.setVisibility(View.VISIBLE);
            for (String string : searchHotWord) {
                View itemView = createSearchHotItem(string);
                mFlexBoxLayout.addView(itemView);
            }
        } else {
            mLlSearchHistory.setVisibility(View.GONE);
        }
    }


    private View createSearchHotItem(String searchHotWord) {
        LinearLayout mLlContainer = new LinearLayout(_mActivity);
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, (int) (32 * density));
        params.rightMargin = (int) (10 * density);
        params.topMargin = (int) (5 * density);
        params.bottomMargin = (int) (5 * density);
        mLlContainer.setLayoutParams(params);
        mLlContainer.setGravity(Gravity.CENTER_VERTICAL);
        mLlContainer.setOrientation(LinearLayout.HORIZONTAL);

        if (SEARCH_HISTORY_STATE == SEARCH_HISTORY_STATE_NORMAL) {
            mLlContainer.setPadding((int) (16 * density), 0, (int) (16 * density), 0);
        } else if (SEARCH_HISTORY_STATE == SEARCH_HISTORY_STATE_DELETE) {
            mLlContainer.setPadding((int) (8 * density), 0, (int) (8 * density), 0);
        }
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(90 * density);
        gd.setStroke((int) (1 * density), Color.parseColor("#5571FE"));
        mLlContainer.setBackground(gd);


        //add text
        TextView text = new TextView(_mActivity);
        text.setGravity(Gravity.CENTER);
        text.setTextColor(Color.parseColor("#5571FE"));
        text.setTextSize(14);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        text.setLayoutParams(textParams);
        text.setText(searchHotWord);

        text.setOnClickListener(v -> {
            mEtSearch.setText(searchHotWord);
            mEtSearch.setSelection(mEtSearch.getText().toString().trim().length());
            searchGameContainsEditHint(false);
        });

        mLlContainer.addView(text);

        return mLlContainer;
    }
}
