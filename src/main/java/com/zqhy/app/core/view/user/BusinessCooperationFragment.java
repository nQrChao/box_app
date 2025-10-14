package com.zqhy.app.core.view.user;

import android.os.Bundle;
import android.widget.TextView;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.connection.ConnectionWayInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.vm.connection.ConnectionViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author pc
 * @date 2019/12/16-9:25
 * @description
 */
public class BusinessCooperationFragment extends BaseFragment<ConnectionViewModel> {
    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ts_business_cooperation;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    protected String getUmengPageName() {
        return "商务合作";
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("商务合作");
        bindViews();
        initData();
    }


    private TextView mTvBusinessWechatId;
    private TextView mTvBusinessMobile;
    private TextView mTvBusinessEmail;
    private TextView mTvApp;
    private TextView mTvApp2;

    private void bindViews() {
        mTvBusinessWechatId = findViewById(R.id.tv_business_wechat_id);
        mTvBusinessMobile = findViewById(R.id.tv_business_mobile);
        mTvBusinessEmail = findViewById(R.id.tv_business_email);
        mTvApp = findViewById(R.id.tv_app);
        mTvApp2 = findViewById(R.id.tv_app_2);

        mTvApp.setText(getAppNameByXML(R.string.string_dyx_business));
        mTvApp2.setText(getAppNameByXML(R.string.string_dyx_introduce));
    }

    private void initData() {
        getConnectionInfoData();
    }

    private void getConnectionInfoData() {
        if (mViewModel != null) {
            mViewModel.getConnectionInfo(new OnBaseCallback<ConnectionWayInfoVo>() {
                @Override
                public void onSuccess(ConnectionWayInfoVo data) {
                    if (data != null && data.isStateOK()) {
                        if (data.getData() != null) {
                            String moblie = data.getData().getBusiness_cooperation_mobile();
                            String wechatId = data.getData().getBusiness_cooperation_wechat_id();
                            String email = data.getData().getOpen_platform_email();
                            mTvBusinessMobile.setText("微信：" + moblie);
                            mTvBusinessWechatId.setText("M/T：" + wechatId);
                            mTvBusinessEmail.setText("E-mail：" + email);
                        }
                    }
                }
            });
        }
    }


}
