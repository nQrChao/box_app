package com.zqhy.app.core.view.server;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.zqhy.app.base.BaseViewPagerFragment;
import com.zqhy.app.config.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/8
 */

public class ServerPagerFragment extends BaseViewPagerFragment {

    public static ServerPagerFragment newInstance(int game_type) {
        ServerPagerFragment fragment = new ServerPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("game_type", game_type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_SERVER_PAGE_STATE;
    }

    @Override
    protected String[] createPageTitle() {
        return new String[]{"今日开服", "明日开服", "历史开服"};
    }

    private int game_type;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            game_type = getArguments().getInt("game_type");
        }
        super.initView(state);
        showSuccess();
        setAdapter();
    }


    @Override
    protected List<Fragment> createFragments() {
        return createServerListFragments(game_type);
    }

    private List<Fragment> createServerListFragments(int game_type) {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ServerListFragment.newInstance(game_type, "today"));
        fragmentList.add(ServerListFragment.newInstance(game_type, "tomorrow"));
        fragmentList.add(ServerListFragment.newInstance(game_type, "lishi"));

        return fragmentList;
    }
}
