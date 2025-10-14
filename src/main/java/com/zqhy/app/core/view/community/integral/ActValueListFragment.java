package com.zqhy.app.core.view.community.integral;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.community.integral.ActValueListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.community.integral.holder.ActValueItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.community.CommunityViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class ActValueListFragment extends BaseListFragment<CommunityViewModel> {

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(ActValueListVo.DataBean.class, new ActValueItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment,this);
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
        initActionBackBarAndTitle("活力值明细");
        addTargetHeaderView();

        initData();
    }

    private void addTargetHeaderView() {
        View headerView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_currency_header_view, null);
        TextView mTvTab1 = headerView.findViewById(R.id.tv_tab_1);
        TextView mTvTab2 = headerView.findViewById(R.id.tv_tab_2);
        TextView mTvTab3 = headerView.findViewById(R.id.tv_tab_3);

        mTvTab1.setText("时间");
        mTvTab2.setText("活力值");
        mTvTab3.setText("备注");

        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addHeaderView(headerView);
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
        getActValueListData();
    }

    private int page = 1, pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    private void initData() {
        page = 1;
        getActValueListData();
    }

    private void getActValueListData() {
        if (mViewModel != null) {
            mViewModel.getActValueListData(page, pageCount, new OnBaseCallback<ActValueListVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(ActValueListVo data) {
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
