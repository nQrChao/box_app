package com.zqhy.app.core.view.transaction;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoListVo;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.main.holder.GameNoMoreItemHolder;
import com.zqhy.app.core.view.transaction.holder.TradeItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.newproject.R;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 */
public class TransactionDynamicFragment extends BaseListFragment<TransactionViewModel> {

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(TradeGoodInfoVo.class, new TradeItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(NoMoreDataVo.class, new GameNoMoreItemHolder(_mActivity))
                .build();
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    private int page = 1, pageCount = 20;
    private String scene = "trends";


    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("成交动态");
        initData();

        setLoadingMoreEnabled(true);
        setOnItemClickListener((v, position, data) -> {
            if (data != null && data instanceof TradeGoodInfoVo) {
                TradeGoodInfoVo tradeGoodInfoVo = (TradeGoodInfoVo) data;
                startForResult(TransactionGoodDetailFragment.newInstance(tradeGoodInfoVo.getGid(), tradeGoodInfoVo.getGameid(), tradeGoodInfoVo.getGoods_pic()), 0);
            }
        });
    }


    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        initData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        initData();
    }

    @Override
    public int getPageCount() {
        return pageCount;
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        loadMore();
    }

    private void initData() {
        page = 1;
        getTradeGoodList();
    }

    private void loadMore() {
        if (page < 0) {
            return;
        }
        page++;
        getTradeGoodList();
    }

    private void getTradeGoodList() {
        Map<String, String> params = new TreeMap<>();
        params.clear();

        if (!TextUtils.isEmpty(scene)) {
            params.put("scene", scene);
        }

        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        if (page == 1) {
            setListNoMore(false);
        }

        if (mViewModel != null) {
            mViewModel.getTradeGoodList(params, new OnBaseCallback<TradeGoodInfoListVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    showErrorTag1();
                }

                @Override
                public void onSuccess(TradeGoodInfoListVo data) {
                    showSuccess();
                    setTradeGoodList(data);
                }
            });
        }
    }

    private void setTradeGoodList(TradeGoodInfoListVo data) {
        if (data != null) {
            if (data.isStateOK()) {
                if (data.getData() != null) {
                    if (page == 1) {
                        clearData();
                    }
                    for (TradeGoodInfoVo tradeGoodInfoBean : data.getData()) {
                        if (scene.equals("normal")) {
                            tradeGoodInfoBean.setIsSelled(1);
                        } else if (scene.equals("trends")) {
                            tradeGoodInfoBean.setIsSelled(2);
                        }
                    }
                    addAllData(data.getData());
                } else {
                    if (page == 1) {
                        clearData();
                        //empty data
                        addData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                    } else {
                        page = -1;
                        //no more data
                        addData(new NoMoreDataVo());
                    }
                    setListNoMore(true);
                }
            } else {
                Toaster.show(data.getMsg());
            }
        }
    }
}
