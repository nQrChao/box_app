package com.zqhy.app.base;

import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.mvvm.base.AbsViewModel;
import com.trecyclerview.adapter.ItemData;
import com.zqhy.app.core.tool.MResource;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;

import java.util.List;


/**
 * @author Administrator
 * @date 2018/11/1
 */

public abstract class BaseListFragment<T extends AbsViewModel> extends BaseFragment<T> implements XRecyclerView.LoadingListener {

    protected XRecyclerView mRecyclerView;
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    protected LinearLayout mLlRootview;

//    protected RecyclerView.LayoutManager layoutManager;

    protected BaseRecyclerAdapter mDelegateAdapter;

    protected boolean isLoadMore = true;

    protected boolean isLoading = true;

    protected boolean isRefresh = false;

    private ItemData itemData;

    protected ItemData tempItems;


    protected int count = 20;

    private FrameLayout mFlTitleRight;

    protected FrameLayout mFlListBottom;

    protected FrameLayout mFlListFixTop;

    private FrameLayout mFlContentLayout;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_list;
    }

    @Override
    public int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        findViews();
        addStatusBarLayout();
        addTitleRightView();

        itemData = new ItemData();
        tempItems = new ItemData();

        setRecyclerViewAdapter();
    }

    protected void addTitleRightView() {
        View titleRightView = getTitleRightView();
        if (titleRightView != null) {
            if(mFlTitleRight != null){
                mFlTitleRight.addView(titleRightView);
            }
        }
    }


    protected void findViews() {
        mLlRootview = findViewById(R.id.ll_rootview);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setEnabled(false);
        mRecyclerView = findViewById(R.id.recycler_view);
        mFlTitleRight = findViewById(R.id.fl_title_right);
        mFlListBottom = findViewById(R.id.fl_list_bottom);
        mFlListFixTop = findViewById(R.id.fl_list_fix_top);
        mFlContentLayout = findViewById(R.id.fl_content_layout);
    }

    protected void addStatusBarLayout() {
        if (isAddStatusBarLayout()) {
            View mStatusBarLayout = LayoutInflater.from(_mActivity).inflate(R.layout.layout_status_bar, null);
            if(mLlRootview != null){
                mLlRootview.addView(mStatusBarLayout, 0);
            }
            if (isSetImmersiveStatusBar()) {
                setImmersiveStatusBar(true);
            }
        }
    }


    /**
     * 添加状态栏
     * @param mContentLayout
     */
    protected void addStatusBarLayout(View mContentLayout){
        if(mContentLayout == null){
            return;
        }
        FrameLayout fl_status_bar = mContentLayout.findViewById(MResource.getResourceId(_mActivity, "id", "fl_status_bar"));
        if (fl_status_bar == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.height = ScreenUtil.getStatusBarHeight(_mActivity)+15;
            fl_status_bar.setLayoutParams(params);
        }
    }

    protected void setRecyclerViewAdapter() {
        if(mRecyclerView != null){
            mDelegateAdapter = createAdapter();
            mRecyclerView.setAdapter(mDelegateAdapter);
            mRecyclerView.setLayoutManager(createLayoutManager());
            mRecyclerView.setLoadingListener(this);
            mRecyclerView.setRefreshTimeVisible(true);
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }
            });
        }

    }

    protected View getTitleRightView() {
        return null;
    }

    /**
     * 是否添加状态栏布局
     *
     * @return
     */
    protected boolean isAddStatusBarLayout() {
        return false;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        isLoadMore = false;
    }

    protected int getPageCount() {
        return count;
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        isLoadMore = false;
    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
    }


    protected void setRefresh() {
        if (mRecyclerView != null) {
            mRecyclerView.refresh();
        }
    }

    protected void setPullRefreshEnabled(boolean pullRefreshEnabled) {
        if (mRecyclerView != null) {
            mRecyclerView.setPullRefreshEnabled(pullRefreshEnabled);
        }
    }

    protected void setLoadingMoreEnabled(boolean loadingMoreEnabled) {
        if (mRecyclerView != null) {
            mRecyclerView.setLoadingMoreEnabled(loadingMoreEnabled);
        }
    }

    protected void addHeaderView(View headerView) {
        if (mRecyclerView != null) {
            mRecyclerView.addHeaderView(headerView);
        }
    }

    /**
     * ------------------------------------------------------------------------------------
     */


    protected void clearData() {
        if (mDelegateAdapter != null) {
            mDelegateAdapter.clear();
            mDelegateAdapter.notifyDataSetChanged();
        }
    }

    /**
     * adapter 添加单个数据 不刷新
     *
     * @param data
     */
    protected void addData(Object data) {
        if (data != null) {
            mDelegateAdapter.addData(data);
        }
    }

    /**
     * 刷新数据
     */
    protected void notifyData() {
        if (mDelegateAdapter != null) {
            mDelegateAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 刷新单个条目
     *
     * @param position
     */
    protected void notifyItemData(int position) {
        if (mDelegateAdapter != null) {
            mDelegateAdapter.notifyItemChanged(position);
        }
    }

    /**
     * adapter 添加单个数据 并刷新
     *
     * @param data
     */
    protected void addDataWithNotifyData(Object data) {
        if (data != null) {
            mDelegateAdapter.addData(data);
            mDelegateAdapter.notifyDataSetChanged();
        }
    }

    /**
     * adapter 添加list数据 并刷新
     *
     * @param collection
     */
    protected void addAllData(List<?> collection) {
        if (collection != null) {
            mDelegateAdapter.addAllData(collection);
            mDelegateAdapter.notifyDataSetChanged();

            setListNoMore(collection.size() < getPageCount());
        }
    }

    /**
     * @param position
     */
    protected void removeItem(int position) {
        if (mDelegateAdapter != null) {
            mDelegateAdapter.remove(position);
        }
    }

    public void refreshAndLoadMoreComplete() {
        if (mRecyclerView != null) {
            mRecyclerView.refreshComplete();
            mRecyclerView.loadMoreComplete();
        }
    }

    /**
     * @param collection
     */
    protected void setData(List<?> collection) {
        if (collection == null) {
            return;
        }
        if (isLoadMore) {
            onLoadMoreSuccess(collection);
        } else {
            onRefreshSuccess(collection);
        }
    }

    protected void onLoadMoreSuccess(List<?> collection) {
        isLoading = true;
        isLoadMore = false;
        itemData.addAll(collection);
        if (collection.size() < getPageCount()) {
            loadMoreComplete(collection, true);
        } else {
            loadMoreComplete(collection, false);
        }
        tempItems.clear();
    }

    protected void onRefreshSuccess() {
        itemData.clear();
        itemData.addAll(tempItems);
        if (tempItems.size() < getPageCount()) {
            refreshComplete(itemData, true);
        } else {
            refreshComplete(itemData, false);
        }
        isRefresh = false;
        tempItems.clear();
    }

    protected void onRefreshSuccess(List<?> collection) {
        tempItems.addAll(collection);
        itemData.clear();
        itemData.addAll(tempItems);
        if (collection.size() < getPageCount()) {
            refreshComplete(itemData, true);
        } else {
            refreshComplete(itemData, false);
        }
        tempItems.clear();
        isRefresh = false;
    }


    private void refreshComplete(List<Object> list, boolean noMore) {
        if (mRecyclerView == null) {
            return;
        }
        mRecyclerView.refreshComplete();
        this.mDelegateAdapter.setDatas(list);
        this.mDelegateAdapter.notifyDataSetChanged();
        setListNoMore(noMore);
    }

    private void loadMoreComplete() {
        this.loadMoreComplete(null);
    }

    private void loadMoreComplete(List<?> list) {
        boolean noMore;
        if (null == list) {
            noMore = true;
            this.mDelegateAdapter.notifyItemRangeChanged(this.mDelegateAdapter.getItems().size() - 1, this.mDelegateAdapter.getItems().size());
        } else {
            noMore = false;
            this.mDelegateAdapter.notifyItemRangeChanged(this.mDelegateAdapter.getItems().size() - list.size() - 1, this.mDelegateAdapter.getItems().size());
        }
        this.mDelegateAdapter.notifyDataSetChanged();
        this.isLoading = false;
        setListNoMore(noMore);
    }

    private void loadMoreComplete(List<?> list, boolean noMore) {
        if (null == list) {
            this.mDelegateAdapter.notifyItemRangeChanged(this.mDelegateAdapter.getItems().size() - 1, this.mDelegateAdapter.getItems().size());
        } else {
            this.mDelegateAdapter.notifyItemRangeChanged(this.mDelegateAdapter.getItems().size() - list.size() - 1, this.mDelegateAdapter.getItems().size());
        }
        this.mDelegateAdapter.notifyDataSetChanged();
        this.isLoading = false;
        setListNoMore(noMore);
    }

    protected void setListNoMore(boolean noMore) {
        mRecyclerView.setNoMore(noMore);
    }

    /**
     * adapter
     *
     * @return DelegateAdapter
     */
    protected abstract BaseRecyclerAdapter createAdapter();


    /**
     * 设置item单击事件回调
     *
     * @param onItemClickListener
     */
    protected void setOnItemClickListener(BaseRecyclerAdapter.OnItemClickListener onItemClickListener) {
        if (mDelegateAdapter != null) {
            mDelegateAdapter.setOnItemClickListener(onItemClickListener);
        }
    }

    /**
     * 设置item长按事件回调
     *
     * @param onItemLongClickListener
     */
    protected void setOnItemLongClickListener(BaseRecyclerAdapter.OnItemLongClickListener onItemLongClickListener) {
        if (mDelegateAdapter != null) {
            mDelegateAdapter.setOnItemLongClickListener(onItemLongClickListener);
        }
    }


    /**
     * LayoutManager
     *
     * @return LayoutManager
     */
    protected abstract RecyclerView.LayoutManager createLayoutManager();

    /**
     * 回到顶部
     */
    public void listBackTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    /**
     * 设置列表背景颜色（include top and bottom）
     *
     * @param color
     */
    public void setListViewBackgroundColor(int color) {
        if(mFlContentLayout != null){
            mFlContentLayout.setBackgroundColor(color);
        }
    }
}
