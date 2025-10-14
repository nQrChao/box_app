package com.zqhy.app.core.view.kefu;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zqhy.app.adapter.AdapterPool;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.kefu.KefuInfoVo;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.main.MainActivity;
import com.zqhy.app.newproject.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

/**
 *
 * 2019.05.30 弃用
 * 新客服页面 KefuHelperFragment
 * @author Administrator
 * @date 2018/11/11
 */

public class KefuMainFragment extends BaseFragment implements View.OnClickListener {

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_kefu_main;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        setStatusBar(0x00000000);
        showSuccess();
        bindView();
        showActionBack(!(_mActivity instanceof MainActivity));
        setTitleBottomLine(View.GONE);
        setActionBackBar(R.mipmap.ic_actionbar_back_white);
    }

    private ImageView mTvKefuFeedback;
    private ImageView mTvKefuCustomerService;
    private RecyclerView mRecyclerView;

    private void bindView() {
        mTvKefuFeedback = findViewById(R.id.tv_kefu_feedback);
        mTvKefuCustomerService = findViewById(R.id.tv_kefu_customer_service);
        mRecyclerView = findViewById(R.id.recyclerView);
        initList();
        mTvKefuFeedback.setOnClickListener(this);
        mTvKefuCustomerService.setOnClickListener(this);

        findViewById(R.id.ll_tv_kefu).setOnClickListener(v -> {
            startNextFragment(new KefuHelperFragment());
        });
    }

    private BaseRecyclerAdapter mAdapter;

    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = AdapterPool.newInstance().getKefuMainAdapter(_mActivity);
        mRecyclerView.setAdapter(mAdapter);

        try {
            String json = getKefuJson();

            Type type = new TypeToken<List<KefuInfoVo.DataBean>>() {
            }.getType();
            Gson gson = new Gson();
            List<KefuInfoVo.DataBean> kefuInfoVoList = gson.fromJson(json, type);

            mAdapter.setDatas(kefuInfoVoList);
            mAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_kefu_feedback:
                if (checkLogin()) {
                    startNextFragment(new FeedBackFragment());
                }
                break;
            case R.id.tv_kefu_customer_service:
                goKefuCenter();
                break;
            default:
                break;
        }
    }

    private void startNextFragment(BaseFragment fragment) {
        FragmentHolderActivity.startFragmentInActivity(_mActivity, fragment);
    }

    private String getKefuJson() {
        try {
            InputStream is = _mActivity.getAssets().open("kefu_main.json");
            int length = is.available();
            byte[] buffer = new byte[length];
            is.read(buffer);
            String result = new String(buffer, "utf8");
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

}
