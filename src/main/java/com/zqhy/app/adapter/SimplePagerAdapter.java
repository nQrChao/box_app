package com.zqhy.app.adapter;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/12/13
 */

public class SimplePagerAdapter extends PagerAdapter {

    protected String[] mStrings;
    protected List<View> mViews;

    public SimplePagerAdapter(List<View> views, String[] mStrings) {
        this.mViews = views;
        this.mStrings = mStrings;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mStrings != null && position < mStrings.length) {
            return mStrings[position];
        }
        return "";
    }

    public List<View> getmViews() {
        return mViews;
    }

    @Override
    public int getCount() {
        return mViews == null ? 0 : mViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
