package com.zqhy.app.adapter;

import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 手游中心PageView的Adapter
 * @author Administrator
 */
public class ViewPagerAdapter extends PagerAdapter {

    protected List<View> mViews;

    public ViewPagerAdapter(List<View> views) {
        this.mViews = views;
    }

    @Override
    public int getCount() {
        return mViews == null ? 0 : mViews.size();
    }

    public List<View> getmViews() {
        return mViews;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
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
