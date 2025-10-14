package com.zqhy.app.core.view.user.vip;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.data.model.user.UserVipCountListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.user.vip.holder.GrowthNoMoreValueItemHolder;
import com.zqhy.app.core.view.user.vip.holder.GrowthValueItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.user.UserViewModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.decoration.TopItemDecoration;

/**
 * @author leeham2734
 * @date 2020/11/12-11:36
 * @description
 */
public class UserGrowthValueDetailFragment extends BaseListFragment<UserViewModel> {

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(UserVipCountListVo.DataBean.class, new GrowthValueItemHolder(_mActivity))
                .bind(NoMoreDataVo.class, new GrowthNoMoreValueItemHolder(_mActivity))
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


    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("成长值明细");
        mRecyclerView.addItemDecoration(new TopItemDecoration(ScreenUtil.dp2px(_mActivity, 14)));
        setTitleBottomLine(View.GONE);
        setListViewBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));

        initData();
    }


    private int page      = 1;
    private int pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        page = 1;
        initData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        onRefresh();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (page < 0) {
            return;
        }
        page++;
        initData();
    }

    private void initData() {
        if (mViewModel != null) {
            mViewModel.getVipInfoDetail(page, pageCount, new OnBaseCallback<UserVipCountListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(UserVipCountListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null && !data.getData().isEmpty()) {
                                if (page == 1) {
                                    clearData();
                                }
                                addAllData(data.getData());
                                if (data.getData().size() < getPageCount()) {
                                    addDataWithNotifyData(new NoMoreDataVo());
                                }
                            } else {
                                if (page == 1) {
                                    //show empty
                                    addData(new EmptyDataVo(R.mipmap.img_empty_data_1));
                                } else {
                                    page = -1;
                                    addData(new NoMoreDataVo());
                                }
                                setListNoMore(true);
                                notifyData();
                            }
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }
}
