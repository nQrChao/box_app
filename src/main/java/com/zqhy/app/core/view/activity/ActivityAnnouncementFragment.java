package com.zqhy.app.core.view.activity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.zqhy.app.base.BaseViewPagerFragment;
import com.zqhy.app.core.view.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/17
 */

public class ActivityAnnouncementFragment extends BaseViewPagerFragment {
    @Override
    public Object getStateEventKey() {
        return null;
    }


    @Override
    protected String[] createPageTitle() {
        return new String[]{"活动推荐", "停服公告"};
    }


    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    protected List<Fragment> createFragments() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ActivityListFragment.newInstance(1));
        fragmentList.add(ActivityListFragment.newInstance(2));
        return fragmentList;
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("活动&公告",!(_mActivity instanceof MainActivity));
        setAdapter();
    }
}
