package com.zqhy.app.core.view.activity;


import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.activity.ActivityInfoListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.activity.holder.MainActivityListItemHolder;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.activity.ActivityViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author pc
 * @date 2019/12/16-13:44
 * @description
 */
public class MainActivityFragment extends BaseListFragment<ActivityViewModel> {

    public static MainActivityFragment newInstance(String title) {
        MainActivityFragment fragment = new MainActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(ActivityInfoListVo.DataBean.class, new MainActivityListItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
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
    protected String getUmengPageName() {
        return "活动公告页";
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return !TextUtils.isEmpty(title);
    }

    private String title;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            title = getArguments().getString("title");
        }
        super.initView(state);
        if (!TextUtils.isEmpty(title)) {
            initActionBackBarAndTitle(title);
        }
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
        addFixHeaderView();
        setOnItemClickListener((v, position, data) -> {
            if (data != null && data instanceof ActivityInfoListVo.DataBean) {
                ActivityInfoListVo.DataBean dataBean = (ActivityInfoListVo.DataBean) data;
                BrowserActivity.newInstance(_mActivity, dataBean.getUrl());
            }
        });
        showSuccess();
    }


    TextView tab1, tab2;

    private void addFixHeaderView() {
        if (mFlListFixTop != null) {
            LinearLayout layout = new LinearLayout(_mActivity);

            int tabWidth = (int) (78 * density);
            int tabHeight = (int) (28 * density);

            tab1 = new TextView(_mActivity);
            tab1.setTextSize(13);
            tab1.setGravity(Gravity.CENTER);
            tab1.setText("游戏活动");
            LinearLayout.LayoutParams tabParams1 = new LinearLayout.LayoutParams(tabWidth, tabHeight);

            tabParams1.leftMargin = (int) (14 * density);
            tabParams1.topMargin = (int) (14 * density);
            tabParams1.bottomMargin = (int) (6 * density);
            tabParams1.rightMargin = (int) (10 * density);
            layout.addView(tab1, tabParams1);

            tab1.setOnClickListener(v -> {
                onTab1Click();
            });


            tab2 = new TextView(_mActivity);
            tab2.setTextSize(13);
            tab2.setGravity(Gravity.CENTER);
            tab2.setText("停服公告");
            LinearLayout.LayoutParams tabParams2 = new LinearLayout.LayoutParams(tabWidth, tabHeight);
            tabParams2.topMargin = (int) (14 * density);
            tabParams2.bottomMargin = (int) (6 * density);
            tabParams2.rightMargin = (int) (10 * density);
            layout.addView(tab2, tabParams2);

            tab2.setOnClickListener(v -> {
                onTab2Click();
            });

            mFlListFixTop.addView(layout, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));

            mFlListFixTop.setVisibility(View.GONE);
            onTab2Click();
        }
    }


    private void onTab1Click() {
        clearTabStatus();
        if (tab1 != null) {
            tab1.setBackgroundResource(R.drawable.ts_shape_0052fe_small_radius);
            tab1.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
        }
        type = 1;
        page = 1;
        getNetWorkData();
    }

    private void onTab2Click() {
        clearTabStatus();
        if (tab2 != null) {
            tab2.setBackgroundResource(R.drawable.ts_shape_0052fe_small_radius);
            tab2.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
        }
        type = 2;
        page = 1;
        getNetWorkData();
    }


    private void clearTabStatus() {
        if (tab1 != null) {
            tab1.setBackgroundResource(R.drawable.ts_shape_f2f2f2_small_radius);
            tab1.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_000000));

        }
        if (tab2 != null) {
            tab2.setBackgroundResource(R.drawable.ts_shape_f2f2f2_small_radius);
            tab2.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_000000));
        }

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

    private int type;

    private int page = 1, pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    private void getNetWorkData() {
        if (mViewModel != null) {
            page = 1;
            if (type == 1) {
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
                    for (ActivityInfoListVo.DataBean data : activityInfoListVo.getData()) {
                        data.setType(type);
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
                //ToastT.error(_mActivity, activityInfoListVo.getMsg());
                Toaster.show(activityInfoListVo.getMsg());
            }
        }
    }
}
