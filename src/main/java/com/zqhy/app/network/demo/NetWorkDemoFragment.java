package com.zqhy.app.network.demo;

import android.os.Bundle;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.hjq.toast.Toaster;
import com.google.gson.Gson;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.tool.AppUtil;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/6
 */

public class NetWorkDemoFragment extends BaseFragment<NewWorkViewModel> {
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_network_demo;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("NetworkDemo");
        bindView();
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_NETWORK_DEMO_STATE;
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.EVENT_KEY_NETWORK_DEMO, BaseResponseVo.class).observe(this, result -> {
            if (result != null) {
                Logs.e("result:" + result);
                String json = new Gson().toJson(result);
                mTvNetworkResponse.setText(json);
            }
        });
    }

    private AppCompatEditText mEtNetworkRequest;
    private Button mBtnExecute;
    private TextView mTvNetworkResponse;
    private Button mBtnCopy;

    private void bindView() {
        mEtNetworkRequest = findViewById(R.id.et_network_request);
        mBtnExecute = findViewById(R.id.btn_execute);
        mTvNetworkResponse = findViewById(R.id.tv_network_response);
        mBtnCopy = findViewById(R.id.btn_copy);


        mBtnExecute.setOnClickListener(view -> {
            String params = mEtNetworkRequest.getText().toString().trim();
            if (TextUtils.isEmpty(params)) {
                Toaster.show( mEtNetworkRequest.getHint());
                return;
            }
            mViewModel.execute(params);
        });
        findViewById(R.id.btn_clear).setOnClickListener(view -> {
            mEtNetworkRequest.getText().clear();
        });

        mBtnCopy.setOnClickListener(view -> {
            if(AppUtil.copyString(_mActivity, mTvNetworkResponse.getText().toString().trim())){
                Toaster.show("复制成功");
            }
        });
    }

}
