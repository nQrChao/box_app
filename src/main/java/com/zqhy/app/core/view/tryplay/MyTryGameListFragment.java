package com.zqhy.app.core.view.tryplay;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.tryplay.TryGameItemVo;
import com.zqhy.app.core.data.model.tryplay.TryGameListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.tryplay.holder.TryAllGameItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.tryplay.TryGameViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class MyTryGameListFragment extends BaseListFragment<TryGameViewModel> {

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(TryGameItemVo.DataBean.class, new TryAllGameItemHolder(_mActivity))
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
        return "我的试玩";
    }


    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("我的试玩");
        initData();
        addTargetHeaderView();
        setListViewBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));
        setOnItemClickListener((v, position, data) -> {
            if (checkLogin()) {
                if (data != null && data instanceof TryGameItemVo.DataBean) {
                    TryGameItemVo.DataBean dataBean = (TryGameItemVo.DataBean) data;
                    start(TryGameTaskFragment.newInstance(dataBean.getTid()));
                }
            }
        });
    }

    private void addTargetHeaderView() {
        TextView tv = new TextView(_mActivity);
        tv.setText("请完成任务后尽快领取试玩奖励避免过期哦~");
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_272727));
        tv.setTextSize(13);
        tv.setSingleLine();
        tv.setIncludeFontPadding(false);
        int topPadding = ScreenUtil.dp2px(_mActivity, 5);
        int leftPadding = ScreenUtil.dp2px(_mActivity, 12);
        tv.setPadding(leftPadding, topPadding, leftPadding, topPadding);
        tv.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_fff3e8));

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ScreenUtil.getScreenWidth(_mActivity), ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = ScreenUtil.dp2px(_mActivity, 20);
        params.bottomMargin = ScreenUtil.dp2px(_mActivity, 20);
        tv.setLayoutParams(params);
        addHeaderView(tv);
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
        getMyTryGameList();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        initData();
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        if (UserInfoModel.getInstance().isLogined()) {
            initData();
        }
    }

    private int page      = 1;
    private int pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }


    private void initData() {
        page = 1;
        getMyTryGameList();
    }

    private void getMyTryGameList() {
        if (mViewModel != null) {
            mViewModel.getUserTryGameListData(page, pageCount, new OnBaseCallback<TryGameListVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(TryGameListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                if (page == 1) {
                                    if (mFlListFixTop != null) {
                                        mFlListFixTop.setVisibility(View.VISIBLE);
                                    }
                                    clearData();
                                }
                                long curTime = System.currentTimeMillis();
                                for (TryGameItemVo.DataBean dataBean : data.getData()) {
                                    dataBean.setEndTime(curTime + dataBean.getCount_down() * 1000);
                                }
                                addAllData(data.getData());
                            } else {
                                if (page == 1) {
                                    if (mFlListFixTop != null) {
                                        mFlListFixTop.setVisibility(View.GONE);
                                    }
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
