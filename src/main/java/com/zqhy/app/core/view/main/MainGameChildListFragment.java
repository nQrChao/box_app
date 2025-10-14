package com.zqhy.app.core.view.main;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.game.GameHallJxHomeVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.main.holder.GameCollectionItemHolder;
import com.zqhy.app.core.view.main.holder.GameNoMoreItemHolder;
import com.zqhy.app.core.view.main.holder.GameReCommonedItemHolder;
import com.zqhy.app.core.view.main.holder.NewGameNormalItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.game.SearchViewModel;
import com.zqhy.app.newproject.R;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author pc
 * @date 2019/11/27-15:00
 * @description
 */
public class MainGameChildListFragment extends BaseListFragment<SearchViewModel> {

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(GameInfoVo.class, new NewGameNormalItemHolder(_mActivity, 95))
                .bind(GameListVo.class, new GameReCommonedItemHolder(_mActivity, 95))
                .bind(GameHallJxHomeVo.CollectionListBean.class, new GameCollectionItemHolder(_mActivity))
                .bind(NoMoreDataVo.class, new GameNoMoreItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, getParentFragment())
                .setTag(R.id.tag_sub_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        setListViewBackgroundColor(Color.parseColor("#FFFFFF"));
        LinearLayout mContentLayout = findViewById(R.id.content_layout);
        //mContentLayout.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //mContentLayout.setClipToPadding(false);
        setPullRefreshEnabled(false);
        setLoadingMoreEnabled(true);

        if (mRecyclerView != null) {
            DividerItemDecoration decoration = new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL);
            decoration.setDrawable(_mActivity.getResources().getDrawable(R.drawable.main_pager_item_decoration_15));
            //mRecyclerView.addItemDecoration(decoration);
            //2018.06.25 首页增加列表滑动跟随底部导航显示隐藏
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    onRecyclerViewScrolled(recyclerView, dx, dy);
                    if (dy > 0) {
                        hideToolbar();
                    }

                    if (dy < 0) {
                        showToolbar();
                    }
                }
            });
        }
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (page < 0) {
            return;
        }
        page++;
        if (!isJx){
            getGameList(mLoadingListener);
        }
    }


    Map<String, String> treeParams;
    private LoadingListener mLoadingListener;

    private int page      = 1;
    private int pageCount = 12;
    private boolean isJx = false;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    public void refreshGameList(Map<String, String> treeParams, LoadingListener loadingListener) {
        this.treeParams = treeParams;
        this.mLoadingListener = loadingListener;
        page = 1;
        isJx = false;
        setLoadingMoreEnabled(true);
        getGameList(loadingListener);
    }

    public void refreshGameListJx(Map<String, String> treeParams, LoadingListener loadingListener) {
        this.treeParams = treeParams;
        this.mLoadingListener = loadingListener;
        isJx = true;
        setLoadingMoreEnabled(false);
        getGameListJx(loadingListener);
    }

    private void getGameList(LoadingListener loadingListener) {
        if (mViewModel == null) {
            return;
        }
        Map<String, String> params = new TreeMap<>();
        if (treeParams != null) {
            for (String key : this.treeParams.keySet()) {
                params.put(key, treeParams.get(key));
            }
        }
        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));
        params.put("list_type", "game_list");

        mViewModel.getGameList(params, new OnBaseCallback<GameListVo>() {
            @Override
            public void onAfter() {
                super.onAfter();
                if (loadingListener != null) {
                    loadingListener.onLoadingAfter();
                }
                showSuccess();
                refreshAndLoadMoreComplete();
            }

            @Override
            public void onBefore() {
                super.onBefore();
                if (page == 1) {
                    showLoading();
                }
                if (loadingListener != null) {
                    loadingListener.onLoadingBefore();
                }
            }

            @Override
            public void onSuccess(GameListVo gameListVo) {
                if (gameListVo != null) {
                    if (gameListVo.isStateOK()) {
                        if (gameListVo.getData() != null && !gameListVo.getData().isEmpty()) {
                            if (page == 1) {
                                clearData();
                            }
                            addAllData(gameListVo.getData());
                            if (gameListVo.getData().size() < pageCount) {
                                //has no more data
                                page = -1;
                                setListNoMore(true);
                            }
                            notifyData();
                        } else {
                            if (page == 1) {
                                clearData();
                                //show empty
                                addData(new EmptyDataVo(R.mipmap.img_empty_data_1));
                            } else {
                                //has no more data
                            }
                            page = -1;
                            setListNoMore(true);
                            notifyData();
                        }
                    } else {
                        Toaster.show( gameListVo.getMsg());
                    }
                }
            }
        });
    }

    private void getGameListJx(LoadingListener loadingListener) {
        if (mViewModel == null) {
            return;
        }
        Map<String, String> params = new TreeMap<>();
        mViewModel.getGameListJx(params, new OnBaseCallback<GameHallJxHomeVo>() {
            @Override
            public void onAfter() {
                super.onAfter();
                if (loadingListener != null) {
                    loadingListener.onLoadingAfter();
                }
                showSuccess();
                refreshAndLoadMoreComplete();
            }

            @Override
            public void onBefore() {
                super.onBefore();
                showLoading();
                if (loadingListener != null) {
                    loadingListener.onLoadingBefore();
                }
            }

            @Override
            public void onSuccess(GameHallJxHomeVo gameListVo) {
                if (gameListVo != null) {
                    if (gameListVo.isStateOK()) {
                        if (gameListVo.getData() != null) {
                            clearData();
                            if (gameListVo.getData().getGame_recommend_list() != null && gameListVo.getData().getGame_recommend_list().size() > 0){
                                GameListVo gameListVo1 = new GameListVo();
                                gameListVo1.setData(gameListVo.getData().getGame_recommend_list());
                                addData(gameListVo1);
                            }
                            if (gameListVo.getData().getCollection_list() != null && gameListVo.getData().getCollection_list().size() > 0){
                                for (int i = 0; i < gameListVo.getData().getCollection_list().size(); i++) {
                                    addData(gameListVo.getData().getCollection_list().get(i));
                                }

                            }
                        } else {
                            clearData();
                            addData(new EmptyDataVo(R.mipmap.img_empty_data_1));
                        }
                        setListNoMore(true);
                        notifyData();
                    } else {
                        Toaster.show( gameListVo.getMsg());
                    }
                }
            }
        });
    }

    public interface LoadingListener {
        void onLoadingBefore();

        void onLoadingAfter();
    }
}
