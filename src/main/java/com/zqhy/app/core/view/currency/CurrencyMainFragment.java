package com.zqhy.app.core.view.currency;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.zqhy.app.base.BaseViewPagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/24
 */

public class CurrencyMainFragment extends BaseViewPagerFragment {

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "充值明细页面";
    }

    @Override
    protected String[] createPageTitle() {
        return new String[]{"收入", "支出"};
    }

    @Override
    protected List<Fragment> createFragments() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(CurrencyListFragment.newInstance(1));
        fragmentList.add(CurrencyListFragment.newInstance(2));
        return fragmentList;
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("充值明细");
        setAdapter();
    }
}
