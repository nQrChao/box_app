package com.zqhy.app.core.view.user.welfare;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.zqhy.app.base.BaseViewPagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class GameWelfareFragment extends BaseViewPagerFragment {

    /**
     * @param index 0 我的游戏
     *              1 我的礼包
     *              2 我的礼券
     * @return
     */
    public static GameWelfareFragment newInstance(int index) {
        GameWelfareFragment fragment = new GameWelfareFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "我的游戏（主）";
    }

    private String[] pageTitle = new String[]{"我的游戏", "我的礼包"};

    @Override
    protected String[] createPageTitle() {
        return pageTitle;
    }

    @Override
    protected List<Fragment> createFragments() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MyFavouriteGameListFragment());
        fragmentList.add(new MyCardListFragment());
        //fragmentList.add(new MyCouponsListFragment());
        return fragmentList;
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }


    int index;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            index = getArguments().getInt("index");
        }
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("我的游戏");

        setAdapter();
        post(() -> {
            if (index >= 0 && index <= pageTitle.length - 1) {
                mViewPager.setCurrentItem(index);
            }
        });

    }

}
