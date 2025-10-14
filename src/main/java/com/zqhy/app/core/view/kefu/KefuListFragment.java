package com.zqhy.app.core.view.kefu;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zqhy.app.adapter.AdapterPool;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.kefu.KefuInfoVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/11
 */

public class KefuListFragment extends BaseListFragment {

    public static KefuListFragment newInstance(String title, int position, List<KefuInfoVo.ItemsBean> itemsBeans) {
        KefuListFragment fragment = new KefuListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("position", position);
        if (itemsBeans != null) {
            ArrayList<KefuInfoVo.ItemsBean> itemList = new ArrayList<>();
            itemList.addAll(itemsBeans);
            bundle.putParcelableArrayList("itemsBeans", itemList);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return AdapterPool.newInstance().getKefuProListAdapter(_mActivity);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    private String mTitle;
    private int position;

    private ArrayList<KefuInfoVo.ItemsBean> itemsBeans;

    @Override
    public void initView(Bundle state) {
        mTitle = getArguments().getString("title");
        position = getArguments().getInt("position");
        itemsBeans = getArguments().getParcelableArrayList("itemsBeans");
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle(mTitle,true);

        setPullRefreshEnabled(false);
        setLoadingMoreEnabled(false);

        mDelegateAdapter.setDatas(itemsBeans);
        mDelegateAdapter.notifyDataSetChanged();
        if (position != -1) {
            ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(position, 0);
        }
    }
}
