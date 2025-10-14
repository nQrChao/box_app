package com.zqhy.app.core.view.game;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.game.ReportItemVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.game.holder.MyReportItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.game.GameViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/21
 */

public class MyReportListFragment extends BaseListFragment<GameViewModel> {

    public static MyReportListFragment newInstance() {
        MyReportListFragment fragment = new MyReportListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_GAME_COUPON_LIST_STATE;
    }

    @Override
    protected String getStateEventTag() {
        return null;
    }


    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(ReportItemVo.DataBean.class, new MyReportItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
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
        initActionBackBarAndTitle("我的举报");
        setLoadingMoreEnabled(false);
        setListViewBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));
        DividerItemDecoration decoration = new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.main_pager_item_decoration));
        mRecyclerView.addItemDecoration(decoration);
        getNetWorkData();
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        getNetWorkData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getNetWorkData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        getNetWorkData();
    }


    private void getNetWorkData() {
        if (mViewModel != null) {
            mViewModel.reportList(new OnBaseCallback<ReportItemVo>(){

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(ReportItemVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.isStateOK()) {
                                clearData();
                                if (data.getData() != null) {
                                    addAllData(data.getData());
                                } else {
                                    addDataWithNotifyData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                                }
                            } else {
                                Toaster.show(data.getMsg());
                                //ToastT.error(_mActivity, data.getMsg());
                            }
                        }
                    }
                }
            });
        }
    }
}
