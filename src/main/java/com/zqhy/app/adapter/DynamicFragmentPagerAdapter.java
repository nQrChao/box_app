package com.zqhy.app.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/6
 */

public class DynamicFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mStrings;

    private List<Fragment> mFragments;

    public DynamicFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, String[] mStrings) {
        super(fm);
        this.mFragments = fragments;
        this.mStrings = mStrings;
    }

    public void update(List<Fragment> fragments) {
        this.mFragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mStrings != null && position < mStrings.length) {
            return mStrings[position];
        }
        return "";
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }
}
