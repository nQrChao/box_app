package com.zqhy.app.core.view.community.integral;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.community.integral.IntegralDetailListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.community.integral.holder.IntegralListItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.community.CommunityViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class IntegralListFragment extends BaseListFragment<CommunityViewModel> {

    /**
     * 收支类型, 1:收入, 2:支出
     *
     * @param type
     * @return
     */
    public static IntegralListFragment newInstance(int type) {
        IntegralListFragment fragment = new IntegralListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(IntegralDetailListVo.DataBean.class,new IntegralListItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    private int type;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
        super.initView(state);
        addTargetHeaderView();
    }

    private void addTargetHeaderView() {
        View headerView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_currency_header_view, null);
        TextView mTvTab1 = headerView.findViewById(R.id.tv_tab_1);
        TextView mTvTab2 = headerView.findViewById(R.id.tv_tab_2);
        TextView mTvTab3 = headerView.findViewById(R.id.tv_tab_3);

        mTvTab1.setText("时间");
        mTvTab2.setText("积分");
        mTvTab3.setText("备注");

        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addHeaderView(headerView);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        initData();
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
        page++;
        getIntegralListData();
    }

    private int page = 1, pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    private void initData() {
        page = 1;
        getIntegralListData();
    }

    private void getIntegralListData() {
        if (mViewModel != null) {
            mViewModel.getIntegralListData(type, page, pageCount, new OnBaseCallback<IntegralDetailListVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(IntegralDetailListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                if (page == 1) {
                                    clearData();
                                }
                                addAllData(data.getData());
                            } else {
                                if (page == 1) {
                                    clearData();
                                    addData(new EmptyDataVo(R.mipmap.img_empty_data_1));
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
