package com.zqhy.app.core.view.tryplay.chlid;

import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.tryplay.TryGameInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.tryplay.TryGameDetailFragment;
import com.zqhy.app.core.view.tryplay.holder.TryGameRewardItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.tryplay.TryGameViewModel;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class RewardListFragment extends BaseListFragment<TryGameViewModel> {

    public static RewardListFragment newInstance(TryGameInfoVo.TaskListVo task_list) {
        RewardListFragment fragment = new RewardListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("task_list", task_list);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(TryGameInfoVo.TaskListVo.DataBean.class, new TryGameRewardItemHolder(_mActivity))
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


    private TryGameInfoVo.TaskListVo task_list;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            task_list = (TryGameInfoVo.TaskListVo) getArguments().getSerializable("task_list");
        }
        super.initView(state);
        if (task_list == null) {
            showErrorTag2();
            return;
        }
        showSuccess();
        setPullRefreshEnabled(false);
        setLoadingMoreEnabled(false);
        setListViewBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f5f5f5));
        addHeaderView();

    }

    private RadioGroup mRgTabServer;
    private RadioButton mRbTab1;
    private RadioButton mRbTab2;
    private RadioButton mRbTab3;

    private void addHeaderView() {
        View tabView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_header_try_game_reward, null);
        mRgTabServer = tabView.findViewById(R.id.rg_tab_server);

        mRbTab1 = tabView.findViewById(R.id.rb_tab_1);
        mRbTab2 = tabView.findViewById(R.id.rb_tab_2);
        mRbTab3 = tabView.findViewById(R.id.rb_tab_3);

        setSelector(mRbTab1);
        setSelector(mRbTab2);
        setSelector(mRbTab3);

        mRgTabServer.setOnCheckedChangeListener((radioGroup, i) -> {
            switchTabCheck(i);
        });

        tabView.setLayoutParams(new ViewGroup.LayoutParams(ScreenUtil.getScreenWidth(_mActivity), ViewGroup.LayoutParams.WRAP_CONTENT));
        addHeaderView(tabView);

        if (task_list.getPay() == null || task_list.getPay().isEmpty()) {
            mRbTab3.setVisibility(View.GONE);
        } else {
            mRbTab3.setVisibility(View.VISIBLE);
            mRgTabServer.check(R.id.rb_tab_3);
        }

        if (task_list.getAttach() == null || task_list.getAttach().isEmpty()) {
            mRbTab1.setVisibility(View.GONE);
        } else {
            mRbTab1.setVisibility(View.VISIBLE);
            mRgTabServer.check(R.id.rb_tab_1);
        }

        if (task_list.getLevel() == null || task_list.getLevel().isEmpty()) {
            mRbTab2.setVisibility(View.GONE);
        } else {
            mRbTab2.setVisibility(View.VISIBLE);
            mRgTabServer.check(R.id.rb_tab_2);
        }


    }

    private void setSelector(RadioButton mRadioButton) {
        ColorStateList tabColorState = new ColorStateList(new int[][]{{android.R.attr.state_checked, android.R.attr.state_enabled}, {}},
                new int[]{ContextCompat.getColor(_mActivity, R.color.white), ContextCompat.getColor(_mActivity, R.color.color_333333)});
        mRadioButton.setTextColor(tabColorState);

        StateListDrawable stateListDrawable = new StateListDrawable();

        GradientDrawable gdNormal = new GradientDrawable();
        gdNormal.setCornerRadius(24 * density);
        gdNormal.setColor(ContextCompat.getColor(_mActivity, R.color.color_e3e3e3));

        GradientDrawable gdCheck = new GradientDrawable();
        gdCheck.setCornerRadius(24 * density);
        gdCheck.setColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));

        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, gdCheck);
        stateListDrawable.addState(new int[]{-android.R.attr.state_checked}, gdNormal);

        mRadioButton.setBackground(stateListDrawable);
    }

    private void initData(List<TryGameInfoVo.TaskListVo.DataBean> dataBean) {
        getRewardList(dataBean);
    }

    private void getRewardList(List<TryGameInfoVo.TaskListVo.DataBean> dataBean) {
        clearData();
        if (dataBean == null || dataBean.isEmpty()) {
            addDataWithNotifyData(new EmptyDataVo(R.mipmap.img_empty_data_1));
        } else {
            addAllData(dataBean);
        }
    }

    public void refreshData(TryGameInfoVo.TaskListVo task_list) {
        this.task_list = task_list;
        switchTabCheck(mRgTabServer.getCheckedRadioButtonId());
    }

    private void switchTabCheck(int checkedId) {
        switch (checkedId) {
            case R.id.rb_tab_1:
                initData(task_list.getAttach());
                break;
            case R.id.rb_tab_2:
                initData(task_list.getLevel());
                break;
            case R.id.rb_tab_3:
                initData(task_list.getPay());
                break;
            default:
                break;
        }
    }


    /**
     * 领取单条积分
     *
     * @param item
     */
    public void getRewardIntegral(TryGameInfoVo.TaskListVo.DataBean item) {
        List<Integer> list = new ArrayList<>();
        list.add(item.getTtid());
        getTryGameReward(item.getTid(), list);
    }

    /**
     * 一键领取
     */
    public void getPageRewardIntegral() {
        List<Integer> list = new ArrayList<>();

        List<TryGameInfoVo.TaskListVo.DataBean> beanList = new ArrayList<>();
        switch (mRgTabServer.getCheckedRadioButtonId()) {
            case R.id.rb_tab_1:
                beanList.addAll(task_list.getAttach());
                break;
            case R.id.rb_tab_2:
                beanList.addAll(task_list.getLevel());
                break;
            case R.id.rb_tab_3:
                beanList.addAll(task_list.getPay());
                break;
            default:
                break;
        }

        int tid = 0;
        for (TryGameInfoVo.TaskListVo.DataBean dataBean : beanList) {
            if (dataBean.getStatus() == 2) {
                tid = dataBean.getTid();
                list.add(dataBean.getTtid());
            }
        }
        if (list.isEmpty()) {
            Toaster.show("当前没有可领取的积分");
            return;
        }
        getTryGameReward(tid, list);
    }


    /**
     * 领取奖励
     *
     * @param ttids
     */
    private void getTryGameReward(int tid, List<Integer> ttids) {
        if (mViewModel != null) {
            mViewModel.getTryGameReward(tid, ttids, new OnBaseCallback<BaseVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    if (getParentFragment() instanceof TryGameDetailFragment) {
                        ((TryGameDetailFragment) getParentFragment()).refreshData();
                    }
                }

                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            Toaster.show( "领取成功");
                            mViewModel.refreshUserData();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

}
