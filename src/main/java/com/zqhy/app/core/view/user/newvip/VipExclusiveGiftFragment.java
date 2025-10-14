package com.zqhy.app.core.view.user.newvip;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.user.newvip.DayRrewardListInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.user.newvip.holder.DayRewardItemHolder;
import com.zqhy.app.core.vm.user.UserViewModel;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/11/10-10:32
 * @description
 */
public class VipExclusiveGiftFragment extends BaseFragment<UserViewModel> implements View.OnClickListener {
    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_vip_exclusive_gift;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    protected String getUmengPageName() {
        return "会员7日专属礼";
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("会员7日专属礼");
        bindViews();
        initData();
    }

    private RecyclerView        mRecyclerView;
    private BaseRecyclerAdapter mAdapter;
    private void bindViews() {
        mRecyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(DayRrewardListInfoVo.RewardVo.class, new DayRewardItemHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        if (mViewModel != null) {
            mViewModel.getSuperDayRewardList(new OnBaseCallback<DayRrewardListInfoVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                }

                @Override
                public void onSuccess(DayRrewardListInfoVo data) {
                    if (data != null && data.isStateOK()) {
                        mAdapter.clear();

                        List<DayRrewardListInfoVo.RewardVo> list = data.getData().getList();
                        for (int i = 0; i < list.size(); i++) {
                            if (data.getData().getCurrent_week() == list.get(i).getWeek()){
                                list.get(i).setToday(true);
                            }else {
                                list.get(i).setToday(false);
                            }
                            if (data.getData().getCurrent_week() > list.get(i).getWeek()){
                                list.get(i).setNotTo(false);
                            }else{
                                list.get(i).setNotTo(true);
                            }
                        }
                        mAdapter.setDatas(list);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        initData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                pop();
                break;


            default:
                break;
        }
    }
}
