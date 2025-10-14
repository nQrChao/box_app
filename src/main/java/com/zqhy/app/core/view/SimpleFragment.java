package com.zqhy.app.core.view;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/13
 */

public abstract class SimpleFragment extends BaseFragment {

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_simple;
    }

    @Override
    public int getContentResId() {
        return R.id.fl_container;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        bindView();
    }

    private FrameLayout mFlContainer;

    private void bindView() {
        mFlContainer = findViewById(R.id.fl_container);
        mFlContainer.addView(getSimpleView());
    }


    protected abstract View getSimpleView();
}
