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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.tryplay.TryGameInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.tryplay.holder.TryGameRankingItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 * @author Administrator
 */
public class RankingListFragment extends BaseListFragment {

    public static RankingListFragment newInstance(TryGameInfoVo.RankingListVo ranking_list) {
        RankingListFragment fragment = new RankingListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ranking_list", ranking_list);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(TryGameInfoVo.RankingListVo.DataBean.class, new TryGameRankingItemHolder(_mActivity))
                .build();
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }


    private TryGameInfoVo.RankingListVo ranking_list;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            ranking_list = (TryGameInfoVo.RankingListVo) getArguments().getSerializable("ranking_list");
        }
        super.initView(state);
        if (ranking_list == null) {
            showErrorTag2();
            return;
        }
        showSuccess();
        setPullRefreshEnabled(false);
        setLoadingMoreEnabled(false);
        setListViewBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f5f5f5));
        addHeaderView();
    }

    private LinearLayout mLlLayoutTopTab;
    private RadioGroup mRgTabServer;
    private RadioButton mRbTab1;
    private RadioButton mRbTab2;
    private TextView mTvTab1;
    private TextView mTvTab2;
    private TextView mTvTab3;
    private TextView mTvTab4;

    private void addHeaderView() {
        View mHeaderView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_header_try_game_ranking, null);

        mLlLayoutTopTab = mHeaderView.findViewById(R.id.ll_layout_top_tab);

        mRgTabServer = mHeaderView.findViewById(R.id.rg_tab_server);
        mRbTab1 = mHeaderView.findViewById(R.id.rb_tab_1);
        mRbTab2 = mHeaderView.findViewById(R.id.rb_tab_2);
        mTvTab1 = mHeaderView.findViewById(R.id.tv_tab_1);
        mTvTab2 = mHeaderView.findViewById(R.id.tv_tab_2);
        mTvTab3 = mHeaderView.findViewById(R.id.tv_tab_3);
        mTvTab4 = mHeaderView.findViewById(R.id.tv_tab_4);

        setSelector(mRbTab1);
        setSelector(mRbTab2);

        mRgTabServer.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.rb_tab_1:
                    initData(ranking_list.getLevel());
                    setTabName("名次", "角色名", "区服", "等级");
                    break;
                case R.id.rb_tab_2:
                    initData(ranking_list.getAttach());
                    setTabName("名次", "角色名", "区服", "战力");
                    break;
                default:
                    break;
            }

        });

        mHeaderView.setLayoutParams(new ViewGroup.LayoutParams(ScreenUtil.getScreenWidth(_mActivity), ViewGroup.LayoutParams.WRAP_CONTENT));
        addHeaderView(mHeaderView);
        mLlLayoutTopTab.setVisibility(View.VISIBLE);

        boolean hasAttach;
        if (ranking_list.getAttach() == null || ranking_list.getAttach().isEmpty()) {
            mRbTab2.setVisibility(View.GONE);
            hasAttach = false;
        } else {
            mRbTab2.setVisibility(View.VISIBLE);
            mRgTabServer.check(R.id.rb_tab_2);
            hasAttach = true;
        }

        boolean hasLevel;
        if (ranking_list.getLevel() == null || ranking_list.getLevel().isEmpty()) {
            mRbTab1.setVisibility(View.GONE);
            hasLevel = false;
        } else {
            mRbTab1.setVisibility(View.VISIBLE);
            mRgTabServer.check(R.id.rb_tab_1);
            hasLevel = true;
        }

        if (!hasAttach && !hasLevel) {
            mLlLayoutTopTab.setVisibility(View.GONE);
            initData(null);
        }
    }


    private void setTabName(String mTab1, String mTab2, String mTab3, String mTab4) {
        mTvTab1.setText(mTab1);
        mTvTab2.setText(mTab2);
        mTvTab3.setText(mTab3);
        mTvTab4.setText(mTab4);
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

    private void initData(List<TryGameInfoVo.RankingListVo.DataBean> list) {
        getTryGameRankingList(list);
    }

    private void getTryGameRankingList(List<TryGameInfoVo.RankingListVo.DataBean> list) {
        clearData();
        if (list == null || list.isEmpty()) {
            addDataWithNotifyData(new EmptyDataVo(R.mipmap.img_empty_data_1)
                    .setLayout(EmptyDataVo.LAYOUT_WRAP_CONTENT)
                    .setPaddingTop((int) (24 * density)));
        } else {
            addAllData(list);
        }
    }
}
