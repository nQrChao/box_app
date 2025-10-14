package com.zqhy.app.core.view.transaction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chaoji.other.hjq.toast.Toaster;
import com.google.android.flexbox.FlexboxLayout;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.KeyboardUtils;
import com.zqhy.app.core.view.transaction.holder.SearchGameItemHolder;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.db.table.search.SearchGameDbInstance;
import com.zqhy.app.db.table.search.SearchGameVo;
import com.zqhy.app.newproject.R;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**2022 6 16弃用
 * @author Administrator
 */
public class TransactionSearchFragment extends BaseFragment<TransactionViewModel> {

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_transaction_search;
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
        showSuccess();
        bindView();
        initList();
    }


    private ImageView mIcActionbarBack;
    private LinearLayout mLlSearch;
    private EditText mEtSearch;
    private TextView mTvSearch;
    private LinearLayout mLlSearchHistory;
    private XRecyclerView mXRecyclerView;
    private FlexboxLayout mFlexBoxLayout;

    private void bindView() {
        mIcActionbarBack = findViewById(R.id.ic_actionbar_back);
        mLlSearch = findViewById(R.id.ll_search);
        mEtSearch = findViewById(R.id.et_search);
        mTvSearch = findViewById(R.id.tv_search);
        mLlSearchHistory = findViewById(R.id.ll_search_history);
        mXRecyclerView = findViewById(R.id.xRecyclerView);
        mFlexBoxLayout = findViewById(R.id.flex_box_layout);

        setListeners();
    }

    private BaseRecyclerAdapter mSearchAdapter;

    private void initList() {
        initSearchHistory();

        LinearLayoutManager lLayoutManager = new LinearLayoutManager(_mActivity);
        mXRecyclerView.setLayoutManager(lLayoutManager);

        mSearchAdapter = new BaseRecyclerAdapter.Builder()
                .bind(GameInfoVo.class, new SearchGameItemHolder(_mActivity))
                .build();
        mXRecyclerView.setAdapter(mSearchAdapter);

        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotatePulse);
        mXRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);

        mSearchAdapter.setOnItemClickListener((v, position, data) -> {
            if (data != null && data instanceof GameInfoVo) {
                KeyboardUtils.hideSoftInput(_mActivity, mEtSearch);
                GameInfoVo gameInfoVo = (GameInfoVo) data;
                addSearchHistory(gameInfoVo);
                selectTargetGame(gameInfoVo.getGamename(), String.valueOf(gameInfoVo.getGameid()));
            }
        });

        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                gameSearch();
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

    private void setListeners() {
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
                if (!isSearchClick) {
                    mHandler.removeCallbacks(searchRunnable);
                    String value = mEtSearch.getText().toString().trim();
                    if (TextUtils.isEmpty(value)) {
                        mXRecyclerView.setVisibility(View.GONE);
                        showSearchHistory();
                        return;
                    }
                    mHandler.postDelayed(searchRunnable, delayMillis);
                } else {
                    isSearchClick = false;
                }
            }
        });
        mXRecyclerView.setOnTouchListener((v, event) -> {
            KeyboardUtils.hideSoftInput(_mActivity, mEtSearch);
            return false;
        });
        mEtSearch.setOnEditorActionListener((TextView textView, int actionId, KeyEvent keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                KeyboardUtils.hideSoftInput(_mActivity, mEtSearch);
                mXRecyclerView.refresh();
            }
            return false;
        });
        mTvSearch.setOnClickListener(view -> {
            mXRecyclerView.refresh();
        });
        mEtSearch.postDelayed(() -> {
            showSoftInput(mEtSearch);
        }, 200);
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
        getSearchGameList();
    }

    private View createHistorySearchTarget(SearchGameVo searchGameVo) {
        View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.item_search_history_transaction, null);
        LinearLayout mRootView = itemView.findViewById(R.id.rootView);
        TextView mTvGameName = itemView.findViewById(R.id.tv_game_name);
        ImageView mIvDelete = itemView.findViewById(R.id.iv_delete);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(4 * density);
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.white));
        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.color_dddddd));
        mRootView.setBackground(gd);

        mIvDelete.setOnClickListener(v -> deleteOneSearchHistory(searchGameVo));
        mRootView.setOnClickListener(v -> {
            refreshSearchHistory(searchGameVo);
            selectTargetGame(searchGameVo.getGamename(), String.valueOf(searchGameVo.getGameid()));
        });

        mTvGameName.setText(searchGameVo.gamename);

        float density = ScreenUtil.getScreenDensity(_mActivity);

        int width = (int) ((ScreenUtil.getScreenWidth(_mActivity) - 28 * density) / 2);

        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(width, FlexboxLayout.LayoutParams.WRAP_CONTENT);

        itemView.setLayoutParams(params);
        return itemView;
    }

    private int targetSearchType = Constants.SEARCH_GAME_TYPE_TRANSACTION;

    private void showSearchHistory() {
        mLlSearchHistory.setVisibility(View.VISIBLE);
    }

    private void initSearchHistory() {
        Runnable runnable = () -> {
            List<SearchGameVo> searchGameVos = SearchGameDbInstance.getInstance().getSearchHistoryListByTopTen(targetSearchType);
            _mActivity.runOnUiThread(() -> {
                if(searchGameVos != null){
                    mFlexBoxLayout.removeAllViews();
                    for (int index = 0; index < searchGameVos.size(); index++) {
                        View itemView = createHistorySearchTarget(searchGameVos.get(index));
                        mFlexBoxLayout.addView(itemView);
                    }
                    if (searchGameVos.size() == 0) {
                        mLlSearchHistory.setVisibility(View.GONE);
                    } else {
                        if (TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
                            mLlSearchHistory.setVisibility(View.VISIBLE);
                        }
                    }
                }else{
                    mLlSearchHistory.setVisibility(View.GONE);
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
                    mXRecyclerView.setVisibility(View.VISIBLE);
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
                    mXRecyclerView.setVisibility(View.GONE);
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
