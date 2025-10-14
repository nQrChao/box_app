package com.zqhy.app.core.view.server;

import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.network.statistics.JiuYaoStatisticsApi;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public class ServerFragment extends BaseFragment {
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_server;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        bindView();
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    private RadioButton mRbTab1;
    private RadioButton mRbTab2;
    private RadioButton mRbTab3;
    private RadioGroup mRgTabServer;

    private int game_type;

    private void bindView() {
        mRbTab1 = findViewById(R.id.rb_tab_1);
        mRbTab2 = findViewById(R.id.rb_tab_2);
        mRbTab3 = findViewById(R.id.rb_tab_3);
        mRgTabServer = findViewById(R.id.rg_tab_server);


        GradientDrawable gd = new GradientDrawable();
        gd.setColor(ContextCompat.getColor(_mActivity,R.color.color_f6f6f6));
        gd.setCornerRadius(32*density);
        mRgTabServer.setBackground(gd);


        setSelector(mRbTab1);
        setSelector(mRbTab2);
        setSelector(mRbTab3);

        mRgTabServer.check(R.id.rb_tab_1);
        tabCheck(1);

        mRgTabServer.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.rb_tab_1:
                    tabCheck(1);
                    JiuYaoStatisticsApi.getInstance().eventStatistics(5, 70);
                    break;
                case R.id.rb_tab_2:
                    tabCheck(2);
                    JiuYaoStatisticsApi.getInstance().eventStatistics(5, 71);
                    break;
                case R.id.rb_tab_3:
                    tabCheck(3);
                    JiuYaoStatisticsApi.getInstance().eventStatistics(5, 72);
                    break;
                default:
                    break;
            }

        });
    }

    private void tabCheck(int game_type) {
        this.game_type = game_type;
        setServerPagerFragment(this.game_type);
    }

    private void setSelector(RadioButton mRadioButton) {
        ColorStateList tabColorState = new ColorStateList(new int[][]{{android.R.attr.state_checked, android.R.attr.state_enabled}, {}},
                new int[]{ContextCompat.getColor(_mActivity, R.color.white), ContextCompat.getColor(_mActivity, R.color.color_808080)});
        mRadioButton.setTextColor(tabColorState);

        StateListDrawable stateListDrawable = new StateListDrawable();

        GradientDrawable gdNormal = new GradientDrawable();
        gdNormal.setCornerRadius(24 * density);
        gdNormal.setColor(ContextCompat.getColor(_mActivity, R.color.transparent));
        gdNormal.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.transparent));

        GradientDrawable gdCheck = new GradientDrawable();
        gdCheck.setCornerRadius(24 * density);
        gdCheck.setColor(ContextCompat.getColor(_mActivity, R.color.color_3478f6));

        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, gdCheck);
        stateListDrawable.addState(new int[]{-android.R.attr.state_checked}, gdNormal);

        mRadioButton.setBackground(stateListDrawable);
    }


    private BaseFragment serverPager1Fragment, serverPager2Fragment, serverPager3Fragment;

    private void setServerPagerFragment(int game_type) {
        switch (game_type) {
            case 1:
                if (serverPager1Fragment == null) {
//                    serverPager1Fragment = ServerPagerFragment.newInstance(game_type);
                    serverPager1Fragment = ServerListFragment.newInstance(game_type,"today");
                }
                changeTabFragment(serverPager1Fragment);
                break;
            case 2:
                if (serverPager2Fragment == null) {
//                    serverPager2Fragment = ServerPagerFragment.newInstance(game_type);
                    serverPager2Fragment = ServerListFragment.newInstance(game_type,"today");
                }
                changeTabFragment(serverPager2Fragment);
                break;
            case 3:
                if (serverPager3Fragment == null) {
//                    serverPager3Fragment = ServerPagerFragment.newInstance(game_type);
                    serverPager3Fragment = ServerListFragment.newInstance(game_type,"today");
                }
                changeTabFragment(serverPager3Fragment);
                break;
            default:
                break;
        }
    }

    private BaseFragment mContent;

    private void changeTabFragment(BaseFragment tagFragment) {
        if (mContent == tagFragment) {
            return;
        }
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (!tagFragment.isAdded()) {
            if (mContent != null) {
                transaction.hide(mContent);
            }
            transaction.add(R.id.fl_container, tagFragment).commitAllowingStateLoss();
        } else {
            if (mContent != null) {
                transaction.hide(mContent);
            }
            transaction.show(tagFragment).commitAllowingStateLoss();
        }
        mContent = tagFragment;

    }

}
