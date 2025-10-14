package com.zqhy.app.core.view.transfer;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.transfer.TransferRecordListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.transfer.holder.TransferRecordItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.transfer.TransferViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/17
 */

public class TransferRecordListFragment extends BaseListFragment<TransferViewModel> {
    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_TRANSFER_RECORD_STATE;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(TransferRecordListVo.DataBean.class, new TransferRecordItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("点数明细");
        getNetWorkData();
        addHeaderView();
    }

    private void addHeaderView() {
        View mHeaderView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_transfer_record_list_header, null);
        mHeaderView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addHeaderView(mHeaderView);
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getNetWorkData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        getNetWorkData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getNetWorkData();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (page < 0) {
            return;
        }
        getMoreNetWorkData();
    }

    private int page = 1, pageCount = 12;

    private void getNetWorkData() {
        if (mViewModel != null) {
            page = 1;
            getTransferListData();
        }
    }

    private void getMoreNetWorkData() {
        if (mViewModel != null) {
            page++;
            getTransferListData();
        }
    }

    private void getTransferListData() {
        mViewModel.getTransferListData(page, pageCount, new OnBaseCallback<TransferRecordListVo>() {
            @Override
            public void onAfter() {
                super.onAfter();
                refreshAndLoadMoreComplete();
            }

            @Override
            public void onSuccess(TransferRecordListVo transferRecordListVo) {
                if (transferRecordListVo != null) {
                    if (transferRecordListVo.isStateOK()) {
                        if (transferRecordListVo.getData() != null && !transferRecordListVo.getData().isEmpty()) {
                            if (page == 1) {
                                clearData();
                            }
                            addAllData(transferRecordListVo.getData());
                        } else {
                            if (page == 1) {
                                clearData();
                                addDataWithNotifyData(new EmptyDataVo(R.mipmap.img_empty_data_1));
                            } else {
                                page = -1;
                            }
                            setListNoMore(true);
                        }
                    } else {
                        Toaster.show( transferRecordListVo.getMsg());
                    }
                }
            }
        });
    }
}
