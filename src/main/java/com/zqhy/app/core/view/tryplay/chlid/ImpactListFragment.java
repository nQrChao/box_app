package com.zqhy.app.core.view.tryplay.chlid;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.tryplay.TryGameInfoVo;
import com.zqhy.app.core.view.tryplay.holder.TryGameImpactItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;

/**
 * @author Administrator
 */
public class ImpactListFragment extends BaseListFragment {

    public static ImpactListFragment newInstance(String competition_description, ArrayList<TryGameInfoVo.CompetitionInfoVo> competition_list) {
        ImpactListFragment fragment = new ImpactListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("competition_description", competition_description);
        bundle.putParcelableArrayList("competition_list", competition_list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(TryGameInfoVo.CompetitionInfoVo.class, new TryGameImpactItemHolder(_mActivity))
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


    private ArrayList<TryGameInfoVo.CompetitionInfoVo> competition_list;
    private String competition_description;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            competition_description = getArguments().getString("competition_description");
            competition_list = getArguments().getParcelableArrayList("competition_list");
        }
        super.initView(state);
        if (competition_list == null) {
            showErrorTag2();
            return;
        }
        showSuccess();
        setPullRefreshEnabled(false);
        setLoadingMoreEnabled(false);
        setListViewBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f5f5f5));
        addHeaderView();
        initData();
    }

    private void addHeaderView() {
        View mHeaderView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_header_try_game_impact, null);
        TextView mTvText = mHeaderView.findViewById(R.id.tv_text);

        TextView mTvTab1 = mHeaderView.findViewById(R.id.tv_tab_1);
        TextView mTvTab2 = mHeaderView.findViewById(R.id.tv_tab_2);
        TextView mTvTab3 = mHeaderView.findViewById(R.id.tv_tab_3);
        TextView mTvTab4 = mHeaderView.findViewById(R.id.tv_tab_4);

        mTvTab1.setText("名次");
        mTvTab2.setText("奖励");
        mTvTab3.setText("账号");
        mTvTab4.setText("领奖时间");

        mTvText.setText(competition_description);
        addHeaderView(mHeaderView);
    }

    private void initData() {
        getTryGameImpactList();
    }


    private void getTryGameImpactList() {
        clearData();
        if (competition_list.isEmpty()) {
            addDataWithNotifyData(new EmptyDataVo(R.mipmap.img_empty_data_1));
        } else {
            addAllData(competition_list);
        }
    }

    /**
     * 刷新数据
     * @param dataList
     */
    public void refreshData(ArrayList dataList) {
        if(dataList == null || dataList.isEmpty()){
            return;
        }
        competition_list.clear();
        competition_list.addAll(dataList);
        initData();
    }
}
