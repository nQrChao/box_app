package com.zqhy.app.core.view.classification.dialog;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.google.android.flexbox.FlexboxLayout;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zqhy.app.adapter.abs.AbsAdapter;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.classification.GameTabVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.KeyboardUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.classification.GameClassificationFragment;
import com.zqhy.app.db.table.search.SearchGameDbInstance;
import com.zqhy.app.db.table.search.SearchGameVo;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/24
 */

public class GameCenterDialogHelper {

    private OnGameCenterDialogClickListener onGameCenterDialogClickListener;
    private Context                         mContext;

    private float density;

    public GameCenterDialogHelper(Context mContext) {
        this.mContext = mContext;
        density = ScreenUtil.getScreenDensity(mContext);
    }


    public void setOnGameCenterDialogClickListener(OnGameCenterDialogClickListener onGameCenterDialogClickListener) {
        this.onGameCenterDialogClickListener = onGameCenterDialogClickListener;
    }

    private RadioGroup    mRgTab;
    private RadioButton   mRbTabBt;
    private RadioButton   mRbTabDiscount;
    private RadioButton   mRbTabH5;
    private RadioButton   mRbTabSingle;
    private FlexboxLayout mFlexboxLayout1;
    private FlexboxLayout mFlexboxLayout2;
    private FlexboxLayout mFlexboxLayout3;

    private CustomDialog gameCenterDialog;


    private String choose_game_type;

    public void showDialog(String game_type, List<GameTabVo> gameTabVos) {
        if (gameCenterDialog == null) {
            gameCenterDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_game_center, null),
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    Gravity.RIGHT,
                    R.style.common_dialog_right_to_left);

            mRgTab = gameCenterDialog.findViewById(R.id.rg_tab);
            mRbTabBt = gameCenterDialog.findViewById(R.id.rb_tab_bt);
            mRbTabDiscount = gameCenterDialog.findViewById(R.id.rb_tab_discount);
            mRbTabH5 = gameCenterDialog.findViewById(R.id.rb_tab_h5);
            mRbTabSingle = gameCenterDialog.findViewById(R.id.rb_tab_single);
            mFlexboxLayout1 = gameCenterDialog.findViewById(R.id.flexbox_layout_1);
            mFlexboxLayout2 = gameCenterDialog.findViewById(R.id.flexbox_layout_2);
            mFlexboxLayout3 = gameCenterDialog.findViewById(R.id.flexbox_layout_3);

            setSelector(mRbTabBt);
            setSelector(mRbTabDiscount);
            setSelector(mRbTabH5);
            setSelector(mRbTabSingle);

            mRgTab.setOnCheckedChangeListener((radioGroup, i) -> {
                switch (i) {
                    case R.id.rb_tab_bt:
                        choose_game_type = "1";
                        break;
                    case R.id.rb_tab_discount:
                        choose_game_type = "2";
                        break;
                    case R.id.rb_tab_h5:
                        choose_game_type = "3";
                        break;
                    case R.id.rb_tab_single:
                        choose_game_type = "4";
                        break;
                    default:
                        break;
                }
                initFlexLayout3(choose_game_type, gameTabVos);
            });

            mRgTab.check(R.id.rb_tab_bt);
            initFlexLayout(gameTabVos);
        }

        refreshFirstTabs(game_type);
        gameCenterDialog.show();
    }


    private void refreshFirstTabs(String game_type) {
        if (mRgTab != null) {
            switch (game_type) {
                case "1":
                    mRgTab.check(R.id.rb_tab_bt);
                    break;
                case "2":
                    mRgTab.check(R.id.rb_tab_discount);
                    break;
                case "3":
                    mRgTab.check(R.id.rb_tab_h5);
                    break;
                case "4":
                    mRgTab.check(R.id.rb_tab_single);
                    break;
                default:
                    mRgTab.check(R.id.rb_tab_bt);
                    break;
            }
        }
        choose_game_type = game_type;
    }


    private void initFlexLayout(List<GameTabVo> gameTabVos) {
        if (gameTabVos != null) {
            for (GameTabVo gameTabVo : gameTabVos) {
                if (gameTabVo.getType() == 1) {
                    mFlexboxLayout1.addView(createGenreTabView(gameTabVo));
                } else if (gameTabVo.getType() == 2) {
                    mFlexboxLayout2.addView(createGenreTabView(gameTabVo));
                }
            }
        }
    }


    private void initFlexLayout3(String game_type, List<GameTabVo> gameTabVos) {
        if (gameTabVos != null) {
            mFlexboxLayout3.removeAllViews();
            for (GameTabVo gameTabVo : gameTabVos) {
                if (gameTabVo.getType() == 0) {
                    if (gameTabVo.isContainsGameType(game_type)) {
                        mFlexboxLayout3.addView(createGenreTabView(gameTabVo));
                    }
                }
            }
        }
    }


    private View createGenreTabView(GameTabVo gameTabVo) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_item_genre, null);

        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins((int) (2 * density), (int) (1 * density), (int) (2 * density), (int) (1 * density));
        itemView.setLayoutParams(params);

        TextView mTvTxt = itemView.findViewById(R.id.tv_txt);

        mTvTxt.setText(gameTabVo.getGenre_name());

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(6);
        mTvTxt.setTextColor(Color.parseColor("#666666"));
        gd.setStroke(0, Color.parseColor("#FF8F19"));
        gd.setColor(Color.parseColor("#EEEEEE"));
        mTvTxt.setBackground(gd);
        mTvTxt.setOnClickListener(v -> {
            if (gameCenterDialog != null && gameCenterDialog.isShowing()) {
                gameCenterDialog.dismiss();
            }
            if (onGameCenterDialogClickListener != null) {
                onGameCenterDialogClickListener.onTabClick(choose_game_type, gameTabVo);
            }
        });
        return itemView;
    }


    private ImageView     mIcActionbarBack;
    private LinearLayout  mLlSearch;
    private EditText      mEtSearch;
    private TextView      mTvSearch;
    private FrameLayout   mFlSearchContent;
    private XRecyclerView mLRecyclerView;
    private LinearLayout  mLlSearchHistory;
    private RecyclerView  mRecyclerViewHistory;
    private TextView      mTvClearSearchHistory;
    private View          mOtherView;

    private boolean isSearchClick = false;

    private Handler mHandler = new Handler();

    private long delayMillis = 1000;

    private int searchPage = 1, searchPageCount = 12;

    private CustomDialog searchGameDialog;

    private BaseFragment fragment;

    public void showSearchDialog(BaseFragment fragment) {
        this.fragment = fragment;
        if (searchGameDialog == null) {
            searchGameDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_search_dialog, null),
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    Gravity.TOP,
                    R.style.common_dialog_right_to_left);
            mIcActionbarBack = searchGameDialog.findViewById(R.id.ic_actionbar_back);
            mLlSearch = searchGameDialog.findViewById(R.id.ll_search);
            mEtSearch = searchGameDialog.findViewById(R.id.et_search);
            mTvSearch = searchGameDialog.findViewById(R.id.tv_search);
            mFlSearchContent = searchGameDialog.findViewById(R.id.fl_search_content);
            mLRecyclerView = searchGameDialog.findViewById(R.id.lRecyclerView);
            mLlSearchHistory = searchGameDialog.findViewById(R.id.ll_search_history);
            mRecyclerViewHistory = searchGameDialog.findViewById(R.id.recyclerView_history);
            mTvClearSearchHistory = searchGameDialog.findViewById(R.id.tv_clear_search_history);
            mOtherView = searchGameDialog.findViewById(R.id.other_view);

            mTvClearSearchHistory.setOnClickListener(view -> {
                deleteAllSearchData();
            });

            initSearchList();

            mEtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (!isSearchClick) {
                        mHandler.removeCallbacks(searchRunnable);
                        String value = mEtSearch.getText().toString().trim();
                        if (TextUtils.isEmpty(value)) {
                            mLRecyclerView.setVisibility(View.GONE);
                            initSearchData();
                            return;
                        }
                        mHandler.postDelayed(searchRunnable, delayMillis);
                    } else {
                        isSearchClick = false;
                    }
                }
            });
            mLRecyclerView.setOnTouchListener((view, motionEvent) -> {
                KeyboardUtils.hideSoftInput(mContext, mEtSearch);
                return false;
            });
            mEtSearch.setOnEditorActionListener((TextView textView, int actionId, KeyEvent keyEvent) -> {
                if (actionId == EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    KeyboardUtils.hideSoftInput(mContext, mEtSearch);
                    searchValue();
                    mLRecyclerView.refresh();
                }
                return false;
            });
            mTvSearch.setOnClickListener(view -> {
                searchValue();
                mLRecyclerView.refresh();
            });
            mIcActionbarBack.setOnClickListener(view -> {
                actionDialogDismiss();
            });
            mOtherView.setOnClickListener(view -> {
                actionDialogDismiss();
            });
            searchGameDialog.setOnDismissListener(dialogInterface -> {
                KeyboardUtils.hideSoftInput(mContext, mEtSearch);
                mEtSearch.getText().clear();
            });

        }
        searchGameDialog.show();
        mEtSearch.postDelayed(() -> {
            mFlSearchContent.setVisibility(View.VISIBLE);
            initSearchData();
            KeyboardUtils.showSoftInput(mContext, mEtSearch);
        }, 200);
    }

    Runnable searchRunnable = () -> mLRecyclerView.refresh();

    private void searchValue() {
        String value = mEtSearch.getText().toString().trim();
        if (!TextUtils.isEmpty(value)) {

        }
    }

    private void actionDialogDismiss() {
        mLlSearchHistory.setVisibility(View.GONE);
        mFlSearchContent.setVisibility(View.GONE);
        KeyboardUtils.hideSoftInput(mContext, mEtSearch);
        mFlSearchContent.postDelayed(() -> {
            if (searchGameDialog != null && searchGameDialog.isShowing()) {
                searchGameDialog.dismiss();
            }
        }, 20);
    }

    SearchAdapter mSearchAdapter;
    GameAdapter   mGameAdapter;

    private void initSearchList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerViewHistory.setNestedScrollingEnabled(false);
        mRecyclerViewHistory.setLayoutManager(layoutManager);

        List<SearchGameVo> list = new ArrayList<>();
        mSearchAdapter = new SearchAdapter(mContext, list);
        mRecyclerViewHistory.setAdapter(mSearchAdapter);

        mSearchAdapter.setOnItemClickListener((v, position, data) -> {
            if (data != null && data instanceof SearchGameVo) {
                isSearchClick = true;
                SearchGameVo gameVo = (SearchGameVo) data;
                mEtSearch.setText(gameVo.getGamename());
                mEtSearch.setSelection(mEtSearch.getText().toString().length());
                mLRecyclerView.refresh();
            }
        });


        LinearLayoutManager lLayoutManager = new LinearLayoutManager(mContext);
        mLRecyclerView.setLayoutManager(lLayoutManager);

        List<GameInfoVo> gameInfoVos = new ArrayList<>();
        mGameAdapter = new GameAdapter(mContext, gameInfoVos);
        mLRecyclerView.setAdapter(mGameAdapter);

        mGameAdapter.setOnItemClickListener((v, position, data) -> {
            if (data != null && data instanceof GameInfoVo) {
                GameInfoVo gameInfoVo = (GameInfoVo) data;

                addSearchData(gameInfoVo);
                if (fragment != null) {
                    fragment.goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
                }
                KeyboardUtils.hideSoftInput(mContext, mEtSearch);
            }
            if (searchGameDialog != null && searchGameDialog.isShowing()) {
                searchGameDialog.dismiss();
            }
        });
        mLRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                searchPage = 1;
                getSearchGameList();
            }

            @Override
            public void onLoadMore() {
                if (searchPage < 0) {
                    return;
                }
                searchPage++;
                getSearchGameList();
            }
        });
    }

    private void getSearchGameList() {
        String value = mEtSearch.getText().toString().trim();
        if (TextUtils.isEmpty(value)) {
            Toaster.show( mEtSearch.getHint());
            return;
        }
        if (fragment instanceof GameClassificationFragment) {
            if (searchPage == 1) {
                mLRecyclerView.setNoMore(false);
            }
            ((GameClassificationFragment) fragment).getSearchGameList(value, searchPage, searchPageCount);
        }
    }

    public void loadGameSearchData(GameListVo gameListVo) {
        searchComplete();
        if (gameListVo != null) {
            if (gameListVo.isStateOK()) {
                mLRecyclerView.setVisibility(View.VISIBLE);

                if (gameListVo.getData() != null && !gameListVo.getData().isEmpty()) {
                    if (searchPage == 1) {
                        mGameAdapter.clear();
                    }
                    mGameAdapter.addAllData(gameListVo.getData());
                    mGameAdapter.notifyDataSetChanged();
                    mLRecyclerView.setNoMore(gameListVo.getData().size() < searchPageCount);
                } else {
                    if (searchPage == 1) {
                        mLRecyclerView.setVisibility(View.GONE);
                        Toaster.show( "没有搜索到您想要的游戏");
                    } else {
                        searchPage = -1;
                        mLRecyclerView.setNoMore(true);
                    }
                }
            }
        }
    }

    private void searchComplete() {
        if (searchPage == 1) {
            mLRecyclerView.refreshComplete();
            mLRecyclerView.scrollToPosition(0);
        } else {
            mLRecyclerView.loadMoreComplete();
        }
        mLlSearchHistory.setVisibility(View.GONE);
    }

    private int targetSearchType = Constants.SEARCH_GAME_TYPE_NORMAL;

    private void initSearchData() {
        List<SearchGameVo> searchGameVos = SearchGameDbInstance.getInstance().getSearchHistoryListByTopTen(targetSearchType);
        mSearchAdapter.clear();
        if (searchGameVos != null) {
            mSearchAdapter.addAllData(searchGameVos);
        }
        mSearchAdapter.notifyDataSetChanged();

        if (mSearchAdapter.getItemCount() == 0) {
            mLlSearchHistory.setVisibility(View.GONE);
        } else {
            if (TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
                mLlSearchHistory.setVisibility(View.VISIBLE);
            }
        }
    }

    private void addSearchData(GameInfoVo gameInfoVo) {
        SearchGameVo gameVo = new SearchGameVo();

        gameVo.setGameid(gameInfoVo.getGameid());
        gameVo.setGame_type(gameInfoVo.getGame_type());
        gameVo.setGamename(gameInfoVo.getGamename());
        gameVo.setAdd_time(System.currentTimeMillis());
        gameVo.setSearch_type(targetSearchType);

        //数据库操作
        SearchGameDbInstance.getInstance().addSearchHistory(gameVo);
        //刷新数据
        initSearchData();
    }

    private void deleteOneSearchData(SearchGameVo searchGameVo, int position) {
        //数据库操作
        SearchGameDbInstance.getInstance().deleteSearchHistoryItem(searchGameVo);

        //适配器操作
        mSearchAdapter.remove(position);
        mSearchAdapter.notifyDataSetChanged();

        if (mSearchAdapter.getItemCount() == 0) {
            mLlSearchHistory.setVisibility(View.GONE);
        }
    }

    private void deleteAllSearchData() {
        //数据库操作
        SearchGameDbInstance.getInstance().deleteAllSearchHistory(1);

        //适配器操作
        mSearchAdapter.clear();
        mSearchAdapter.notifyDataSetChanged();
        mLlSearchHistory.setVisibility(View.GONE);
    }


    private void setSelector(RadioButton targetRadioButton) {
        GradientDrawable gdCheck = new GradientDrawable();
        gdCheck.setCornerRadius(24 * density);
        gdCheck.setColor(ContextCompat.getColor(mContext, R.color.color_3478f6));

        GradientDrawable gdNormal = new GradientDrawable();
        gdNormal.setCornerRadius(24 * density);
        gdNormal.setColor(ContextCompat.getColor(mContext, R.color.color_eeeeee));

        int normalColor = ContextCompat.getColor(mContext, R.color.color_666666);
        int selectedColor = ContextCompat.getColor(mContext, R.color.white);

        setTabButtonBackgroundSelector(targetRadioButton, normalColor, selectedColor, gdNormal, gdCheck);
    }

    /**
     * 设置RadioButton样式
     *
     * @param targetRadioButton
     * @param normalColor
     * @param selectedColor
     * @param drawableDefault
     * @param drawableChecked
     */
    public void setTabButtonBackgroundSelector(RadioButton targetRadioButton,
                                               int normalColor, int selectedColor,
                                               Drawable drawableDefault, Drawable drawableChecked) {
        StateListDrawable stateListDrawable = new StateListDrawable();

        stateListDrawable.addState(new int[]{android.R.attr.state_checked},
                drawableChecked);
        stateListDrawable.addState(new int[]{-android.R.attr.state_checked},
                drawableDefault);
        stateListDrawable.addState(new int[]{android.R.attr.state_selected},
                drawableChecked);
        stateListDrawable.addState(new int[]{-android.R.attr.state_selected},
                drawableDefault);

        targetRadioButton.setBackground(stateListDrawable);

        ColorStateList tabColorState = new ColorStateList(new int[][]{{android.R.attr.state_checked, android.R.attr.state_enabled}, {}},
                new int[]{selectedColor, normalColor});
        targetRadioButton.setTextColor(tabColorState);

    }


    class SearchAdapter extends AbsAdapter<SearchGameVo> {

        public SearchAdapter(Context context, List<SearchGameVo> labels) {
            super(context, labels);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, SearchGameVo data, int position) {
            ViewHolder holder = (ViewHolder) viewHolder;
            holder.mTvGameName.setText(data.getGamename());
            holder.mIvDelete.setOnClickListener(view -> {
                deleteOneSearchData(data, position);
            });
        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_search_game;
        }

        @Override
        public AbsViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }


        class ViewHolder extends AbsViewHolder {
            private LinearLayout mRootView;
            private TextView     mTvGameName;
            private ImageView    mIvDelete;

            public ViewHolder(View itemView) {
                super(itemView);
                mRootView = findViewById(R.id.rootView);
                mTvGameName = findViewById(R.id.tv_game_name);
                mIvDelete = findViewById(R.id.iv_delete);

            }
        }
    }


    class GameAdapter extends AbsAdapter<GameInfoVo> {

        public GameAdapter(Context context, List<GameInfoVo> labels) {
            super(context, labels);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, GameInfoVo data, int position) {
            ViewHolder holder = (ViewHolder) viewHolder;

            if (position == mLabels.size() - 1) {
                holder.mViewLine.setVisibility(View.GONE);
            } else {
                holder.mViewLine.setVisibility(View.VISIBLE);
            }

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(density * 10);
            holder.mTvGameTag.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            if (data.getGame_type() == 1) {
                holder.mTvGameTag.setText("BT手游");
                gd.setColor(ContextCompat.getColor(mContext, R.color.color_ffaa1c_bt));
            } else if (data.getGame_type() == 2) {
                holder.mTvGameTag.setText("折扣手游");
                gd.setColor(ContextCompat.getColor(mContext, R.color.color_ff7c7c_discount));
            } else if (data.getGame_type() == 3) {
                holder.mTvGameTag.setText("H5游戏");
                gd.setColor(ContextCompat.getColor(mContext, R.color.color_8fcc52_h5));
            } else if (data.getGame_type() == 4) {
                holder.mTvGameTag.setText("单机游戏");
                gd.setColor(ContextCompat.getColor(mContext, R.color.color_11a8ff_single));
            }
            holder.mTvGameTag.setBackground(gd);
            holder.mTvGameName.setText(data.getGamename());

            int showDiscount = data.showDiscount();
            if (showDiscount == 0) {
                holder.mTvGameTag2.setText("");
            } else if (showDiscount == 1) {
                holder.mTvGameTag2.setText(data.getDiscount() + "折");
            } else if (showDiscount == 2) {
                holder.mTvGameTag2.setText(data.getFlash_discount() + "折");
            } else {
                holder.mTvGameTag2.setText("");
            }

            //2018.05.18 折扣游戏 折扣=10折  标签改为手机游戏
            try {
                if (data.getGame_type() == 2 && data.getDiscount() == 10) {
                    holder.mTvGameTag.setText("手机游戏");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_search_game_2;
        }

        @Override
        public AbsViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }

        class ViewHolder extends AbsViewHolder {

            private LinearLayout mRootView;
            private TextView     mTvGameName;
            private TextView     mTvGameTag;
            private TextView     mTvGameTag2;
            private View         mViewLine;

            public ViewHolder(View itemView) {
                super(itemView);

                mRootView = findViewById(R.id.rootView);
                mTvGameName = findViewById(R.id.tv_game_name);
                mTvGameTag = findViewById(R.id.tv_game_tag);
                mTvGameTag2 = findViewById(R.id.tv_game_tag_2);
                mViewLine = findViewById(R.id.view_line);

            }
        }
    }


}
