package com.zqhy.app.core.view.transaction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.box.other.hjq.toast.Toaster;
import com.google.android.flexbox.FlexboxLayout;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoListVo1;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoVo1;
import com.zqhy.app.core.data.model.transaction.TradeSearchPageInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.StringUtil;
import com.zqhy.app.core.tool.utilcode.KeyboardUtils;
import com.zqhy.app.core.view.main.holder.GameNoMoreItemHolder;
import com.zqhy.app.core.view.transaction.holder.TradeItemHolder1;
import com.zqhy.app.core.view.transaction.util.CustomPopWindow;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.db.table.search.SearchGameDbInstance;
import com.zqhy.app.db.table.search.SearchGameVo;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 */
public class TransactionSearchFragment1 extends BaseFragment<TransactionViewModel> {

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_transaction_search1;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private boolean isSearchClick = false;

    private Handler mHandler = new Handler();
    long delayMillis = 500;

    private Map<String, String> params;
    private int searchPage = 1, searchPageCount = 12;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("");
        bindView();
        initData();
        initList();
    }

    private void initData() {
        getTradeSearchPage();
    }

    ArrayList<TradeSearchPageInfoVo.DataBean.Collection> collection_list = null;
    ArrayList<TradeSearchPageInfoVo.DataBean.Genre> genre_list = null;

    private void getTradeSearchPage() {
        if (mViewModel != null) {
            mViewModel.getTradeSearchPage(new OnBaseCallback<TradeSearchPageInfoVo>() {
                @Override
                public void onSuccess(TradeSearchPageInfoVo data) {
                    showSuccess();
                    if (data.getData() != null) {
                        collection_list = data.getData().getCollection_list();
                        genre_list = data.getData().getGenre_list();
                        initCollectionList();
                        initGenreList();
                    }
                }
            });
        }
    }

    private View createHistorySearchTarget(TradeSearchPageInfoVo.DataBean.Collection searchGameVo) {
        View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.item_search_history_transaction1, null);
        LinearLayout mRootView = itemView.findViewById(R.id.rootView);
        TextView mTvGameName = itemView.findViewById(R.id.tv_game_name);
//        ImageView mIvDelete = itemView.findViewById(R.id.iv_delete);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5 * density);
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.white));
        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));
        mRootView.setBackground(gd);
        mRootView.setOnClickListener(v -> {
            kw = searchGameVo.getGamename();
            mEtSearch.setText(searchGameVo.getGamename());
            mXRecyclerView.refresh();
            KeyboardUtils.hideSoftInput(_mActivity);
        });
        mTvGameName.setText(searchGameVo.getGamename());

        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);

        itemView.setLayoutParams(params);
        return itemView;
    }

    String genre_id = "";
    private View createHistorySearchTarget(TradeSearchPageInfoVo.DataBean.Genre searchGameVo) {
        View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.item_search_history_transaction1, null);
        LinearLayout mRootView = itemView.findViewById(R.id.rootView);
        TextView mTvGameName = itemView.findViewById(R.id.tv_game_name);
//        ImageView mIvDelete = itemView.findViewById(R.id.iv_delete);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5 * density);
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.white));
        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));
        mRootView.setBackground(gd);
        mRootView.setOnClickListener(v -> {
            tv_classify.setVisibility(View.VISIBLE);
            tv_classify.setText(searchGameVo.getGenre_name());
            genre_id = searchGameVo.getGenre_id();
            getTradeGoodList();
//            Toaster.show(searchGameVo.getGenre_name());
        });
        mTvGameName.setText(searchGameVo.getGenre_name());

        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);

        itemView.setLayoutParams(params);
        return itemView;
    }

    private void initCollectionList() {
        if (collection_list != null) {
            if (collection_list.size() > 7){
                ArrayList<TradeSearchPageInfoVo.DataBean.Collection> objects = new ArrayList<>();
                for (int i = 0; i < 7; i++) {
                    objects.add(collection_list.get(i));
                }
                collection_list = objects;
            }

            mFlexBoxLayout1.removeAllViews();
            for (int index = 0; index < collection_list.size(); index++) {
                View itemView = createHistorySearchTarget(collection_list.get(index));
                mFlexBoxLayout1.addView(itemView);
            }
            if (collection_list.size() == 0) {
                tv_notsearch1.setVisibility(View.VISIBLE);
                mFlexBoxLayout1.setVisibility(View.GONE);
            } else {
                mFlexBoxLayout1.setVisibility(View.VISIBLE);
                tv_notsearch1.setVisibility(View.GONE);
            }
        }
    }
    private void initGenreList() {
        if (genre_list != null) {
            mFlexBoxLayout2.removeAllViews();
            for (int index = 0; index < genre_list.size(); index++) {
                View itemView = createHistorySearchTarget(genre_list.get(index));
                mFlexBoxLayout2.addView(itemView);
            }
            if (genre_list.size() == 0) {
                mFlexBoxLayout2.setVisibility(View.GONE);
            } else {
                mFlexBoxLayout2.setVisibility(View.VISIBLE);
            }
        }
    }

    private ImageView mIcActionbarBack;
    private LinearLayout mLlSearch;
    private EditText mEtSearch;
    private TextView mTvSearch;
    private TextView tv_notsearch;
    private TextView tv_classify;
    private TextView tv_notsearch1;
    private LinearLayout mLlSearchHistory;
    private XRecyclerView mXRecyclerView;
    private FlexboxLayout mFlexBoxLayout;
    private FlexboxLayout mFlexBoxLayout1;
    private FlexboxLayout mFlexBoxLayout2;
    private ImageView iv_clear_search;
    private ImageView iv_clear_all;
    private LinearLayout layout_bar;
    private TextView tv_select;
    private LinearLayout layout_select;

    private void bindView() {
        mIcActionbarBack = findViewById(R.id.ic_actionbar_back);
        layout_bar = findViewById(R.id.layout_bar);
        mLlSearch = findViewById(R.id.ll_search);
        iv_clear_all = findViewById(R.id.iv_clear_all);
        iv_clear_search = findViewById(R.id.iv_clear_search);
        mEtSearch = findViewById(R.id.et_search);
        mTvSearch = findViewById(R.id.tv_search);
        tv_classify = findViewById(R.id.tv_classify);
        tv_notsearch = findViewById(R.id.tv_notsearch);
        tv_notsearch1 = findViewById(R.id.tv_notsearch1);
        mLlSearchHistory = findViewById(R.id.ll_search_history);
        mXRecyclerView = findViewById(R.id.xRecyclerView);
        mFlexBoxLayout = findViewById(R.id.flex_box_layout);
        mFlexBoxLayout1 = findViewById(R.id.flex_box_layout1);
        mFlexBoxLayout2 = findViewById(R.id.flex_box_layout_2);
        layout_select = findViewById(R.id.layout_select);
        tv_select = findViewById(R.id.tv_select);

        setListeners();
    }

    private BaseRecyclerAdapter mSearchAdapter;
    BaseRecyclerAdapter mTransactionListAdapter;
    private void initList() {
        initSearchHistory();

        LinearLayoutManager lLayoutManager = new LinearLayoutManager(_mActivity);
        mXRecyclerView.setLayoutManager(lLayoutManager);
        mTransactionListAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(TradeGoodInfoVo1.class, new TradeItemHolder1(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(NoMoreDataVo.class, new GameNoMoreItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
//        mSearchAdapter = new BaseRecyclerAdapter.Builder()
//                .bind(GameInfoVo.class, new SearchGameItemHolder(_mActivity))
//                .build();
//        mXRecyclerView.setAdapter(mSearchAdapter);

        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotatePulse);
        mXRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        mXRecyclerView.setAdapter(mTransactionListAdapter);


        mTransactionListAdapter.setOnItemClickListener((v, position, data) -> {
            if (data != null && data instanceof TradeGoodInfoVo1) {
                TradeGoodInfoVo1 tradeGoodInfoVo = (TradeGoodInfoVo1) data;
                start(TransactionGoodDetailFragment.newInstance(tradeGoodInfoVo.getGid(), tradeGoodInfoVo.getGameid(), tradeGoodInfoVo.getGameicon()));
            }
        });


        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                gameSearch();
            }

            @Override
            public void onLoadMore() {
                if (page < 0) {
                    return;
                }
                page++;
                getTradeGoodList();
            }
        });
    }

    Runnable searchRunnable = () -> {
        if (mXRecyclerView != null) {
            mXRecyclerView.refresh();
        }
    };

    private void selectTargetGame(String gamename, String gameid) {
        if (getPreFragment() == null) {
            Intent intent = new Intent();
            intent.putExtra("gamename", gamename);
            intent.putExtra("gameid", gameid);
            _mActivity.setResult(Activity.RESULT_OK, intent);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("gamename", gamename);
            bundle.putString("gameid", gameid);
            setFragmentResult(RESULT_OK, bundle);
        }
        pop();
    }


    private TextView tv_item1;
    private TextView tv_item2;
    private TextView tv_item3;
//    private TextView tv_item4;
    /**
     * 指定view下方弹出
     * 方法名@：showAsDropDown(v, 0, 10)
     * resource:PopWindow 布局
     */
    private void showPopListView(View v, int resource) {
        if (resource == 0) return;
        View contentView = LayoutInflater.from(_mActivity).inflate(resource, null);
        tv_item1 = ((TextView) contentView.findViewById(R.id.tv_item1));
        tv_item2 = ((TextView) contentView.findViewById(R.id.tv_item2));
        tv_item3 = ((TextView) contentView.findViewById(R.id.tv_item3));
//        tv_item4 = ((TextView) contentView.findViewById(R.id.tv_item4));

        switch (selected) {
            case 1:
                tv_item1.setTextColor(Color.parseColor("#232323"));
                tv_item2.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item3.setTextColor(Color.parseColor("#9b9b9b"));
//                tv_item4.setTextColor(Color.parseColor("#9b9b9b"));
                break;
            case 2:
                tv_item1.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item2.setTextColor(Color.parseColor("#232323"));
                tv_item3.setTextColor(Color.parseColor("#9b9b9b"));
//                tv_item4.setTextColor(Color.parseColor("#9b9b9b"));
                break;
            case 3:
                tv_item1.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item2.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item3.setTextColor(Color.parseColor("#232323"));
//                tv_item4.setTextColor(Color.parseColor("#9b9b9b"));
                break;
//            case 4:
//                tv_item1.setTextColor(Color.parseColor("#9b9b9b"));
//                tv_item2.setTextColor(Color.parseColor("#9b9b9b"));
//                tv_item3.setTextColor(Color.parseColor("#9b9b9b"));
//                tv_item4.setTextColor(Color.parseColor("#232323"));
//                break;
        }
        //1 最新上架 2 价格升序 3 价格降序 4 近期成交

        CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(_mActivity)
                .setView(contentView)
                .setFocusable(true)
                .setBgDarkAlpha(0.7F)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                .setOutsideTouchable(true)
                .create();
        popWindow.setBackgroundAlpha(0.7f);
        tv_item1.setOnClickListener(view -> {
            selected = 1;
            page = 1;
            scene = "normal";
            orderby = null;
            tv_select.setText("最新上架");
            popWindow.dissmiss();
            getTradeGoodList();
        });
        tv_item2.setOnClickListener(view -> {
            selected = 2;
            page = 1;
            scene = "normal";
            tv_select.setText("价格升序");
            orderby = "price_up";
            popWindow.dissmiss();
            getTradeGoodList();
        });
        tv_item3.setOnClickListener(view -> {
            selected = 3;
            page = 1;
            scene = "normal";
            tv_select.setText("价格降序");
            orderby = "price_down";
            popWindow.dissmiss();
            getTradeGoodList();
        });
//        tv_item4.setOnClickListener(view -> {
//            selected = 4;
//            page = 1;
//            orderby = null;
//            scene = "trends";
//            tv_select.setText("近期成交");
//            popWindow.dissmiss();
//            getTradeGoodList();
//        });
        popWindow.showAsDropDown(v, -v.getWidth() / 2, 0);//指定view正下方
    }

    private void setListeners() {
        layout_select.setOnClickListener(view -> {
            showPopListView(layout_select, R.layout.pop_transaction_select1);
        });
        tv_classify.setOnClickListener(view -> {
            genre_id = "";
            mEtSearch.setText("");
            page = 1;
            tv_classify.setVisibility(View.INVISIBLE);
        });

        iv_clear_all.setOnClickListener(v -> {
            mEtSearch.setText("");
            page = 1;
        });

        mIcActionbarBack.setOnClickListener(v -> pop());
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    layout_bar.setVisibility(View.GONE);
                    mLlSearchHistory.setVisibility(View.VISIBLE);
                    iv_clear_all.setVisibility(View.INVISIBLE);
                    kw = "";
                }else {
                    iv_clear_all.setVisibility(View.VISIBLE);
                    kw = editable.toString();
                }
//                if (!isSearchClick) {
//                    mHandler.removeCallbacks(searchRunnable);
//                    String value = mEtSearch.getText().toString().trim();
//                    if (TextUtils.isEmpty(value)) {
//                        mXRecyclerView.setVisibility(View.GONE);
//                        showSearchHistory();
//                        return;
//                    }
//                    mHandler.postDelayed(searchRunnable, delayMillis);
//                } else {
//                    isSearchClick = false;
//                }
            }
        });
        mXRecyclerView.setOnTouchListener((v, event) -> {
            KeyboardUtils.hideSoftInput(_mActivity, mEtSearch);
            return false;
        });
        mEtSearch.setOnEditorActionListener((TextView textView, int actionId, KeyEvent keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                KeyboardUtils.hideSoftInput(_mActivity, mEtSearch);
                Editable text = mEtSearch.getText();
                GameInfoVo gameInfoVo = new GameInfoVo();
                gameInfoVo.setGamename(text.toString());
                addSearchHistory(gameInfoVo);
                mXRecyclerView.refresh();
            }
            return false;
        });
        mTvSearch.setOnClickListener(view -> {
            Editable text = mEtSearch.getText();
            if (StringUtil.isEmpty(text.toString())) {
                Toaster.show("搜索内容为空!");
                return;
            }
            GameInfoVo gameInfoVo = new GameInfoVo();
            gameInfoVo.setGamename(text.toString());
            addSearchHistory(gameInfoVo);
            mXRecyclerView.refresh();
        });

        iv_clear_search.setOnClickListener(view -> {
            deleteAllSearchHistory();
        });
        mEtSearch.postDelayed(() -> {
            showSoftInput(mEtSearch);
        }, 200);
    }

    private void deleteAllSearchHistory() {
        Runnable runnable = () -> {
            //数据库操作
            SearchGameDbInstance.getInstance().deleteAllSearchHistory(targetSearchType);

            //刷新数据
            initSearchHistory();
        };
        new Thread(runnable).start();
    }

    private void gameSearch() {
        if (mEtSearch != null && mXRecyclerView != null) {
            mXRecyclerView.setNoMore(false);
            doSearchGame();
        }
    }

    private void doSearchGame() {
        if (params == null) {
            params = new TreeMap<>();
        }
        searchPage = 1;
        initDataGoodList();
//        getSearchGameList();
    }

    private View createHistorySearchTarget(SearchGameVo searchGameVo) {
        View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.item_search_history_transaction1, null);
        LinearLayout mRootView = itemView.findViewById(R.id.rootView);
        TextView mTvGameName = itemView.findViewById(R.id.tv_game_name);
//        ImageView mIvDelete = itemView.findViewById(R.id.iv_delete);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5 * density);
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.white));
        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));
        mRootView.setBackground(gd);

//        mIvDelete.setOnClickListener(v -> deleteOneSearchHistory(searchGameVo));
        mRootView.setOnClickListener(v -> {
            mEtSearch.setText(searchGameVo.getGamename());
            Editable text = mEtSearch.getText();
            GameInfoVo gameInfoVo = new GameInfoVo();
            gameInfoVo.setGamename(text.toString());
            addSearchHistory(gameInfoVo);
            kw = searchGameVo.getGamename();
            mEtSearch.setText(searchGameVo.getGamename());
            mXRecyclerView.refresh();
//            refreshSearchHistory(searchGameVo);
//            selectTargetGame(searchGameVo.getGamename(), String.valueOf(searchGameVo.getGameid()));
        });

        mTvGameName.setText(searchGameVo.gamename);

//        float density = ScreenUtil.getScreenDensity(_mActivity);

//        int width = (int) ((ScreenUtil.getScreenWidth(_mActivity) - 28 * density) / 2);

        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);

        itemView.setLayoutParams(params);
        return itemView;
    }

    private int targetSearchType = Constants.SEARCH_GAME_TYPE_TRANSACTION;

    private void showSearchHistory() {
        mLlSearchHistory.setVisibility(View.VISIBLE);
    }

    private void initSearchHistory() {
        Runnable runnable = () -> {
            List<SearchGameVo> searchGameVos = SearchGameDbInstance.getInstance().getSearchHistoryListByTopFive(targetSearchType);
            _mActivity.runOnUiThread(() -> {
                if (searchGameVos != null) {
                    mFlexBoxLayout.removeAllViews();
                    for (int index = 0; index < searchGameVos.size(); index++) {
                        View itemView = createHistorySearchTarget(searchGameVos.get(index));
                        mFlexBoxLayout.addView(itemView);
                    }
                    if (searchGameVos.size() == 0) {
                        tv_notsearch.setVisibility(View.VISIBLE);
                        mFlexBoxLayout.setVisibility(View.GONE);
                        mLlSearchHistory.setVisibility(View.VISIBLE);
//                        mLlSearchHistory.setVisibility(View.GONE);
                    } else {
                        if (TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
                            mLlSearchHistory.setVisibility(View.VISIBLE);
                            mFlexBoxLayout.setVisibility(View.VISIBLE);
                            tv_notsearch.setVisibility(View.GONE);
                        }
                    }
                } else {
                    tv_notsearch.setVisibility(View.VISIBLE);
                    mFlexBoxLayout.setVisibility(View.GONE);

                    mLlSearchHistory.setVisibility(View.VISIBLE);
//                    mLlSearchHistory.setVisibility(View.GONE);
                }
            });
        };
        new Thread(runnable).start();
    }

    private void addSearchHistory(GameInfoVo gameInfoVo) {
        Runnable runnable = () -> {
            SearchGameVo gameVo = new SearchGameVo();
            gameVo.setGameid(gameInfoVo.getGameid());
            gameVo.setGame_type(gameInfoVo.getGame_type());
            gameVo.setGamename(gameInfoVo.getGamename());
            gameVo.setAdd_time(System.currentTimeMillis());
            gameVo.setSearch_type(targetSearchType);
            //数据库操作
            SearchGameDbInstance.getInstance().addSearchHistory(gameVo);
            //刷新数据
            initSearchHistory();
        };
        new Thread(runnable).start();
    }

    private void refreshSearchHistory(SearchGameVo gameInfoVo) {
        Runnable runnable = () -> {
            gameInfoVo.setAdd_time(System.currentTimeMillis());
            //数据库操作
            SearchGameDbInstance.getInstance().addSearchHistory(gameInfoVo);

            //刷新数据
            initSearchHistory();
        };
        new Thread(runnable).start();
    }


    private void deleteOneSearchHistory(SearchGameVo searchGameVo) {
        Runnable runnable = () -> {
            //数据库操作
            SearchGameDbInstance.getInstance().deleteSearchHistoryItem(searchGameVo);

            //刷新数据
            initSearchHistory();
        };
        new Thread(runnable).start();
    }

    private void initDataGoodList() {
        page = 1;
        getTradeGoodList();
    }

    //1 最新上架 2 价格升序 3 价格降序 4 近期成交
    int selected = 1;

    private String gamename, gameid;
    private int page = 1, pageCount = 12;
    private String scene = "normal";
    private String orderby;
    private String kw = "";
    private String listTag = "";

    private void getTradeGoodList() {
        Map<String, String> params = new TreeMap<>();
        params.clear();

        if (!TextUtils.isEmpty(scene)) {
            params.put("scene", scene);
        }
        if (!TextUtils.isEmpty(orderby)) {
            params.put("orderby", orderby);
        }
        if (!TextUtils.isEmpty(genre_id)) {
            params.put("genre_id", genre_id);
        }

        if (!TextUtils.isEmpty(gameid)) {
            params.put("gameid", gameid);
        }

        params.put("pic", "multiple");
        params.put("kw", kw);
        params.put("goods_type", "0");
        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        if (page == 1) {
            showLoading();
            mXRecyclerView.setNoMore(false);
        } else {
            if (!TextUtils.isEmpty(listTag)) {
                params.put("r_time", listTag);
            }
        }

        if (mViewModel != null) {
            mViewModel.getTradeGoodList1(params, new OnBaseCallback<TradeGoodInfoListVo1>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    mXRecyclerView.loadMoreComplete();
                    mXRecyclerView.refreshComplete();
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    showSuccess();
                    showErrorTag1();
                }

                @Override
                public void onSuccess(TradeGoodInfoListVo1 data) {
                    showSuccess();
                    if (data.isStateOK()) {
                        if (data.getData() != null) {
                            layout_bar.setVisibility(View.VISIBLE);
                            mLlSearchHistory.setVisibility(View.INVISIBLE);
                        }
                    }
                    setTradeGoodList(data);
                }
            });
        }

    }

    private void setTradeGoodList(TradeGoodInfoListVo1 data) {
        if (data != null) {
            if (data.isStateOK()) {
                if (data.getData() != null) {
                    if (page == 1) {
                        mTransactionListAdapter.clear();
                        mXRecyclerView.smoothScrollToPosition(0);
                    }
                    for (TradeGoodInfoVo1 tradeGoodInfoBean : data.getData()) {
                        if (scene.equals("normal")) {
                            tradeGoodInfoBean.setIsSelled(1);
                        } else if (scene.equals("trends")) {
                            tradeGoodInfoBean.setIsSelled(2);
                        }
                    }
                    mTransactionListAdapter.addAllData(data.getData());
                    mTransactionListAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mTransactionListAdapter.clear();
                        //empty data
                        layout_bar.setVisibility(View.VISIBLE);
                        mLlSearchHistory.setVisibility(View.INVISIBLE);
                        mTransactionListAdapter.addData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                    } else {
                        page = -1;
                        //no more data
                        mTransactionListAdapter.addData(new NoMoreDataVo());
                    }
                    mTransactionListAdapter.notifyDataSetChanged();
                    mXRecyclerView.setNoMore(true);
                }
                if (page == 1) {
                    listTag = data.getMsg();
                    mXRecyclerView.smoothScrollToPosition(0);
//                    mAppBarLayout.setExpanded(true, true);
                }
            } else {
                Toaster.show(data.getMsg());
            }
        }
    }


    private void getSearchGameList() {
        String value = mEtSearch.getText().toString().trim();
        if (TextUtils.isEmpty(value)) {
            Toaster.show(mEtSearch.getHint());
            return;
        }
        params.clear();
        if (params == null) {
            params = new TreeMap<>();
        }
        params.put("kw", value);
        params.put("page", String.valueOf(searchPage));
        params.put("pagecount", String.valueOf(searchPageCount));
        if (searchPage == 1) {
            mXRecyclerView.setNoMore(false);
        }

        if (mViewModel != null) {
            mViewModel.searchGame(params, new OnBaseCallback<GameListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    if (searchPage == 1) {
                        mXRecyclerView.refreshComplete();
                        mXRecyclerView.scrollToPosition(0);
                    } else {
                        mXRecyclerView.loadMoreComplete();
                    }
                    mLlSearchHistory.setVisibility(View.GONE);
                }

                @Override
                public void onSuccess(GameListVo data) {
                    layout_bar.setVisibility(View.VISIBLE);
                    setSearchGameList(data);
                }
            });
        }
    }

    private void setSearchGameList(GameListVo data) {
        if (data.isStateOK()) {
            if (data.getData() != null) {
                if (searchPage == 1) {
                    mSearchAdapter.clear();
                }
                mSearchAdapter.addAllData(data.getData());
                mSearchAdapter.notifyDataSetChanged();
            } else {
                if (searchPage == 1) {
                    //empty data
                    mSearchAdapter.clear();
                    mSearchAdapter.notifyDataSetChanged();
                    layout_bar.setVisibility(View.GONE);
                    Toaster.show("没有搜索到您想要的游戏");
                } else {
                    //no more data
                    searchPage = -1;
                    mXRecyclerView.setNoMore(true);
                }
            }
        } else {
            Toaster.show(data.getMsg());
        }
    }
}
