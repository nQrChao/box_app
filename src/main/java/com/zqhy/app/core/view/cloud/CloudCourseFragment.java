package com.zqhy.app.core.view.cloud;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.cloud.CloudCourseVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.cloud.holder.CloudCourseItemHolder;
import com.zqhy.app.core.view.cloud.holder.ServiceItemHolder;
import com.zqhy.app.core.vm.cloud.CloudViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class CloudCourseFragment extends BaseFragment<CloudViewModel> {

    public static CloudCourseFragment newInstance() {
        CloudCourseFragment fragment = new CloudCourseFragment();
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
        return R.layout.fragment_cloud_course;
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
                .bind(CloudCourseVo.DataBean.class, new CloudCourseItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new ServiceItemHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);
        mRecyclerView.setAdapter(mAdapter);

        findViewById(R.id.tv_feedback).setOnClickListener(v -> {
            startFragment(CloudFeedbackFragment.newInstance());
        });

        mAdapter.setOnItemClickListener((v, position, data) -> {
            if (data instanceof CloudCourseVo.DataBean){
                for (int i = 0; i < mAdapter.getData().size() - 1; i++) {
                    CloudCourseVo.DataBean bean = (CloudCourseVo.DataBean) mAdapter.getData().get(i);
                    bean.setUpfold(false);
                }
                ((CloudCourseVo.DataBean) data).setUpfold(true);
                mAdapter.notifyDataSetChanged();
            }else if (data instanceof EmptyDataVo){
                goKefuCenter();
            }
        });
    }

    private void initData() {
        if (mViewModel != null){
            mViewModel.getCourseData(new OnBaseCallback<CloudCourseVo>(){
                @Override
                public void onSuccess(CloudCourseVo data) {
                    if (data != null && data.isStateOK()){
                        if (data.getData() != null && data.getData().size() > 0){
                            mAdapter.setDatas(data.getData());
                            mAdapter.addData(new EmptyDataVo());
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}
