package com.zqhy.app.core.view.user;

import android.os.Bundle;
import android.view.View;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.vm.user.CertificationViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author pc
 * @date 2019/12/13-15:13
 * @description
 */
public class AboutRealNameAuthenticationFragment extends BaseFragment<CertificationViewModel> {
    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ts_real_name_authentication;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("实名认证");
        bindViews();
    }

    private View mViewJump1;

    private void bindViews() {
        mViewJump1 = findViewById(R.id.view_jump_1);
        mViewJump1.setOnClickListener(v -> {
            //跳转懂游戏详细介绍
            if (checkLogin()) {
                startFragment(new CertificationFragment());
            }
        });
    }


}
