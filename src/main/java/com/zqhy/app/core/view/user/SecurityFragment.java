package com.zqhy.app.core.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.kefu.FeedBackFragment;
import com.zqhy.app.core.vm.user.UserViewModel;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.sp.SPUtils;
import com.zqhy.app.widget.SwitchView;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class SecurityFragment extends BaseFragment<UserViewModel> implements View.OnClickListener {

    public static SecurityFragment newInstance() {
        SecurityFragment fragment = new SecurityFragment();
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
        return R.layout.fragment_security;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    protected String getUmengPageName() {
        return "隐私权限安全";
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("隐私权限安全");
        bindView();
    }

    private TextView mTvUserAgreement;
    private TextView mTvPrivacyAgreement;
    private TextView mTvDataSharing;
    private TextView mTvComplaint;
    private TextView mTvCancellation;
    private SwitchView       mSwitchView;
    private ConstraintLayout mConstraintLayout;

    private void bindView() {
        mTvUserAgreement = findViewById(R.id.tv_user_agreement);
        mTvPrivacyAgreement = findViewById(R.id.tv_privacy_agreement);
        mTvDataSharing = findViewById(R.id.tv_data_sharing);
        mTvComplaint = findViewById(R.id.tv_complaint);
        mTvCancellation = findViewById(R.id.tv_cancellation);
        mSwitchView = findViewById(R.id.switch_view);
        mConstraintLayout = findViewById(R.id.cl_recommendation_switch);

        mTvUserAgreement.setOnClickListener(this);
        mTvPrivacyAgreement.setOnClickListener(this);
        mTvDataSharing.setOnClickListener(this);
        mTvComplaint.setOnClickListener(this);
        mTvCancellation.setOnClickListener(this);

        if (BuildConfig.NEED_RECOMMENDATION_SWITCH){
            mConstraintLayout.setVisibility(View.VISIBLE);
        }else {
            mConstraintLayout.setVisibility(View.GONE);
        }
        SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
        boolean recommendation_switch = spUtils.getBoolean("recommendation_switch", false);
        mSwitchView.toggleSwitch(recommendation_switch);
        mSwitchView.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                spUtils.putBoolean("recommendation_switch", true);
                view.toggleSwitch(true); // or false
            }

            @Override
            public void toggleToOff(SwitchView view) {
                spUtils.putBoolean("recommendation_switch", false);
                view.toggleSwitch(false); // or false
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_user_agreement://用户协议
                goUserAgreement();
                break;

            case R.id.tv_privacy_agreement://隐私协议
                goPrivacyAgreement();
                break;

            case R.id.tv_data_sharing://第三方信息数据共享
                goDataDeclaration();
                break;

            case R.id.tv_complaint://安全投诉
                startFragment(FeedBackFragment.newInstance(true));
                break;

            case R.id.tv_cancellation:
                startFragment(CancellationOneFragment.newInstance());
                break;
            default:
                break;
        }
    }

    /**
     * 用户协议
     */
    public void goUserAgreement() {
        Intent intent = new Intent(_mActivity, BrowserActivity.class);
        intent.putExtra("url", AppConfig.APP_REGISTRATION_PROTOCOL);
        startActivity(intent);
    }

    /**
     * 隐私协议
     */
    public void goPrivacyAgreement() {
        Intent intent = new Intent(_mActivity, BrowserActivity.class);
        intent.putExtra("url", AppConfig.APP_PRIVACY_PROTOCOL);
        startActivity(intent);
    }

    /**
     * 第三方SDK信息数据
     */
    public void goDataDeclaration() {
        Intent intent = new Intent(_mActivity, BrowserActivity.class);
        intent.putExtra("url", AppConfig.APP_DATA_DECLARATION);
        startActivity(intent);
    }
}
