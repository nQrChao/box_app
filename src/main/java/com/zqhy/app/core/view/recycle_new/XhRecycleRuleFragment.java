package com.zqhy.app.core.view.recycle_new;

import android.os.Bundle;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2021/7/13-16:41
 * @description
 */
public class XhRecycleRuleFragment extends BaseFragment {

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_xh_recycle_rule;
    }

    @Override
    public int getContentResId() {
        return R.id.container;
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("小号回收");
        showSuccess();
    }
}
