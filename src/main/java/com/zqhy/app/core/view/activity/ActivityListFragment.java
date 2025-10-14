package com.zqhy.app.core.view.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.activity.ActivityInfoListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.activity.holder.ActivityListItemHolder;
import com.zqhy.app.core.view.activity.holder.ActivityNewListItemHolder;
import com.zqhy.app.core.view.activity.holder.AnnouncementListItemHolder;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.activity.ActivityViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/17
 */

public class ActivityListFragment extends BaseListFragment<ActivityViewModel> {

    public static ActivityListFragment newInstance(int type) {
        ActivityListFragment fragment = new ActivityListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_ACTIVITY_LIST_STATE;
    }

    @Override
    protected String getStateEventTag() {
        return String.valueOf(type);
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(ActivityInfoListVo.DataBean.class,
                        type == 1 ? new ActivityListItemHolder(_mActivity) :
                                (type == 2 ? new AnnouncementListItemHolder(_mActivity) : new ActivityNewListItemHolder(_mActivity)))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }


    private int type;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
        super.initView(state);

        setOnItemClickListener((v, position, data) -> {
            if (data != null && data instanceof ActivityInfoListVo.DataBean) {
                ActivityInfoListVo.DataBean dataBean = (ActivityInfoListVo.DataBean) data;
                BrowserActivity.newInstance(_mActivity, dataBean.getUrl());
            }
        });

    }


    @Override
    protected void dataObserver() {
        super.dataObserver();
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
        getMoreNetWorkData();
    }


    private int page = 1, pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    private void getNetWorkData() {
        if (mViewModel != null) {
            page = 1;
            if (type == 1 || type == 3) {
                getActivityListData();
            } else if (type == 2) {
                getAnnouncementListData();
            }

        }
    }

    private void getMoreNetWorkData() {
        if (mViewModel != null) {
            page++;
            if (type == 1) {
                getActivityListData();
            } else if (type == 2) {
                getAnnouncementListData();
            }

        }
    }


    private void getActivityListData() {
        mViewModel.getActivityListData(type, page, pageCount, new OnBaseCallback<ActivityInfoListVo>() {
            @Override
            public void onAfter() {
                super.onAfter();
                refreshAndLoadMoreComplete();
            }

            @Override
            public void onSuccess(ActivityInfoListVo data) {
                setListData(data);
            }
        });
    }

    private void getAnnouncementListData() {
        mViewModel.getAnnouncementListData(type, page, pageCount, new OnBaseCallback<ActivityInfoListVo>() {
            @Override
            public void onAfter() {
                super.onAfter();
                refreshAndLoadMoreComplete();
            }

            @Override
            public void onSuccess(ActivityInfoListVo data) {
                setListData(data);
            }
        });
    }

    private void setListData(ActivityInfoListVo activityInfoListVo) {
        if (activityInfoListVo != null) {
            if (activityInfoListVo.isStateOK()) {
                if (activityInfoListVo.getData() != null && !activityInfoListVo.getData().isEmpty()) {
                    if (page == 1) {
                        clearData();
                    }
                    addAllData(activityInfoListVo.getData());
                } else {
                    if (page == 1) {
                        clearData();
                        //empty data
                        addDataWithNotifyData(new EmptyDataVo(R.mipmap.img_empty_data_1));
                    } else {
                        // no more data
                        page = -1;
                    }
                    setListNoMore(true);
                }
            } else {
                //Toaster.show(_mActivity, activityInfoListVo.getMsg());
                Toaster.show(activityInfoListVo.getMsg());
            }
        }
    }
}

