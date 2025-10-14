package com.zqhy.app.core.view.message;

import android.os.Bundle;

import com.zqhy.app.base.BaseActivity;
import com.zqhy.app.newproject.R;

/**
 *
 * @author Administrator
 * @date 2018/11/30
 */

public class MessageActivity extends BaseActivity{
    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        showSuccess();
        loadRootFragment(R.id.fl_container,new MessageMainFragment());
    }
}
