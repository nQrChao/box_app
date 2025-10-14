package com.zqhy.app.core.view.tryplay.chlid;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.newproject.R;

public class ChildFragment extends BaseListFragment {



    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class,new EmptyItemHolder(_mActivity))
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


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        setPullRefreshEnabled(false);
        setLoadingMoreEnabled(false);
        addDataWithNotifyData(new EmptyDataVo(R.mipmap.img_empty_data_1));

    }
}
