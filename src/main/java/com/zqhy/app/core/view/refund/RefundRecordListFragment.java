package com.zqhy.app.core.view.refund;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.refund.RefundRecordListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.refund.holder.RefundRecordItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.refund.RefundViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2020/5/11-17:04
 * @description
 */
public class RefundRecordListFragment extends BaseListFragment<RefundViewModel> {

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(RefundRecordListVo.DataBean.class, new RefundRecordItemHolder(_mActivity))
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

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("退款记录");
        setListViewBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f6f6f6));

        addHeaderView();
        initData();
    }

    private void addHeaderView() {
        View view = LayoutInflater.from(_mActivity).inflate(R.layout.layout_header_refund_record, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addHeaderView(view);
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
    public void onLoadMore() {
        super.onLoadMore();
        if (page < 0) {
            return;
        }
        page++;
        getRefundRecordList();
    }

    private int page      = 1;
    private int pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    private void initData() {
        page = 1;
        getRefundRecordList();
    }


    /**
     * 获取数据
     */
    private void getRefundRecordList() {
        if (mViewModel != null) {
            mViewModel.getRefundRecordList(page, pageCount, new OnBaseCallback<RefundRecordListVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(RefundRecordListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                if (page == 1) {
                                    clearData();
                                }
                                addAllData(data.getData());
                            } else {
                                if (page == 1) {
                                    addData(new EmptyDataVo(R.mipmap.img_empty_data_1));
                                } else {
                                    page = -1;
                                }
                                setListNoMore(true);
                                notifyData();
                            }
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

}
