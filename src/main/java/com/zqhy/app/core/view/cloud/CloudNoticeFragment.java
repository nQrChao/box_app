package com.zqhy.app.core.view.cloud;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.cloud.CloudCourseVo;
import com.zqhy.app.core.data.model.cloud.CloudNoticeVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.cloud.holder.CloudCourseItemHolder;
import com.zqhy.app.core.view.cloud.holder.CloudNoticeItemHolder;
import com.zqhy.app.core.view.cloud.holder.ServiceItemHolder;
import com.zqhy.app.core.vm.cloud.CloudViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class CloudNoticeFragment extends BaseFragment<CloudViewModel> {

    public static CloudNoticeFragment newInstance() {
        CloudNoticeFragment fragment = new CloudNoticeFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_cloud_notice;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    protected String getUmengPageName() {
        return "云挂机";
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        bindView();
        initData();
    }

    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter mAdapter;
    private void bindView() {
        findViewById(R.id.iv_back).setOnClickListener(v -> pop());

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(String.class, new CloudNoticeItemHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        if (mViewModel != null){
            mViewModel.getNotice(new OnBaseCallback<CloudNoticeVo>(){
                @Override
                public void onSuccess(CloudNoticeVo data) {
                    if (data != null && data.isStateOK()){
                        if (data.getData() != null && data.getData().size() > 0){
                            mAdapter.setDatas(data.getData());
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}
