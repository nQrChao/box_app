package com.zqhy.app.core.view.community.integral;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.zqhy.app.base.BaseViewPagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class IntegralDetailFragment extends BaseViewPagerFragment {


    @Override
    protected String[] createPageTitle() {
        return new String[]{"收入","支出"};
    }

    @Override
    protected List<Fragment> createFragments() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(IntegralListFragment.newInstance(1));
        fragmentList.add(IntegralListFragment.newInstance(2));
        return fragmentList;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("积分明细");
        showSuccess();
        setAdapter();
    }
}
