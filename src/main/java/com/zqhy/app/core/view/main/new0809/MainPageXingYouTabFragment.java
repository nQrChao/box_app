package com.zqhy.app.core.view.main.new0809;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mvvm.base.BaseMvvmFragment;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.game.new0809.XyTabDataVo;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.main.new_game.NewGameAppointmentFragment;
import com.zqhy.app.core.vm.main.BtGameViewModel;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 * @author Administrator
 * @date 2021/8/9 0009-15:17
 * @description
 */
public class MainPageXingYouTabFragment extends BaseFragment<BtGameViewModel> {

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main_page_xinyou_tab;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_container;
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        bindViews();
        showSuccess();
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerViewTab;
    private void bindViews() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mRecyclerViewTab = findViewById(R.id.recycler_view_tab);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerViewTab.setLayoutManager(linearLayoutManager);

        mainPageXingYouFragment = new MainPageXingYouFragment();
        newGameAppointmentFragment = new NewGameAppointmentFragment();

        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_3478f6,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            initData();
        });
    }

    private BaseMvvmFragment mContent;
    BaseMvvmFragment mainPageXingYouFragment, newGameAppointmentFragment;
    private void changeFragment(BaseMvvmFragment tagFragment) {
        if (mContent == tagFragment) {
            return;
        }
        FragmentTransaction transaction = _mActivity.getSupportFragmentManager().beginTransaction();
        if (!tagFragment.isAdded()) {
            if (mContent != null) {
                transaction.hide(mContent);
            }
            transaction.add(R.id.ll_perch, tagFragment).commitAllowingStateLoss();
        } else {
            if (mContent != null) {
                transaction.hide(mContent);
            }
            transaction.show(tagFragment).commitAllowingStateLoss();
        }
        mContent = tagFragment;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        initData();
    }

    private void initData() {
        if (mViewModel != null) {
            mViewModel.getXyTabHomePageData(new OnBaseCallback<XyTabDataVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onSuccess(XyTabDataVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.data.menu != null && data.data.menu.size() > 0){
                                data.data.menu.get(0).setLabelSelect(true);
                                MyAdapter myAdapter = new MyAdapter(data.data.menu);
                                mRecyclerViewTab.setAdapter(myAdapter);
                                changeFragment(mainPageXingYouFragment);
                                ((MainPageXingYouFragment) mainPageXingYouFragment).initData();
                            }
                        }
                    }
                }
            });
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        private List<XyTabDataVo.MenuDataBean> list;//数据源

        public MyAdapter(List<XyTabDataVo.MenuDataBean> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyViewHolder(LayoutInflater.from(_mActivity).inflate(R.layout.item_main_game_list_tab_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            XyTabDataVo.MenuDataBean menuDataBean = list.get(position);
            holder.mTvTab.setText(menuDataBean.getLabel());

            if (menuDataBean.isLabelSelect()){
                holder.mTvTab.setTextColor(Color.parseColor("#232323"));
                holder.mTvTab.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                holder.mIvTab.setVisibility(View.VISIBLE);
            }else {
                holder.mTvTab.setTextColor(Color.parseColor("#666666"));
                holder.mTvTab.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                holder.mIvTab.setVisibility(View.GONE);
            }

            holder.itemView.setOnClickListener(v -> {
                if (!TextUtils.isEmpty(menuDataBean.getPage_type())){
                    appJumpAction(new AppJumpInfoBean(menuDataBean.getPage_type(), menuDataBean.getParam()));
                    return;
                }
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setLabelSelect(false);
                }
                menuDataBean.setLabelSelect(true);
                if (position == 0){
                    changeFragment(mainPageXingYouFragment);
                }else if (position == 1){
                    changeFragment(newGameAppointmentFragment);
                }
                notifyDataSetChanged();
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView mTvTab;
        private ImageView mIvTab;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTab = itemView.findViewById(R.id.tv_tab);
            mIvTab = itemView.findViewById(R.id.iv_tab);
        }
    }
}
