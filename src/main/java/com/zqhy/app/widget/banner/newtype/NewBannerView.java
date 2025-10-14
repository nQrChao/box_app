package com.zqhy.app.widget.banner.newtype;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.vm.main.data.MainPageData;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public class NewBannerView extends RelativeLayout {

    AutoHeightViewPager viewPager;
    LinearLayout points;
    View line;
    private CompositeDisposable compositeSubscription;

    /**
     * 默认轮播时间，10s
     */
    private int delayTime = 10;

    private List<MainPageData.BannerData> bannerList;


    /**
     * 选中显示Indicator
     */
    private int selectRes = R.drawable.a_test_bg_5400;
    /**
     * 非选中显示Indicator
     */
    private int unSelectRes = R.drawable.a_test_bg_d7d7;
    /**
     * 当前页的下标
     */
    private int currentPos;

    private NewBannerAdapter bannerAdapter;

    public NewBannerView(Context context) {
        this(context, null);
    }

    public NewBannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.common_banner_new, this, true);
        viewPager = findViewById(R.id.layout_banner_viewpager);
        points = findViewById(R.id.layout_banner_points_group);
        line = findViewById(R.id.line);
        viewPager.setPageMargin(20);
    }

    /**
     * 设置轮播间隔时间
     *
     * @param time 轮播间隔时间，单位秒
     */
    public NewBannerView delayTime(int time) {
        this.delayTime = time;
        return this;
    }

    /**
     * 设置Points资源 Res
     *
     * @param selectRes   选中状态
     * @param unselcetRes 非选中状态
     */
    public void setPointsRes(int selectRes, int unselcetRes) {
        this.selectRes = selectRes;
        this.unSelectRes = unselcetRes;
    }


    /**
     * 图片轮播需要传入参数
     */
    public void build(List<? extends MainPageData.BannerData> list, boolean showFlag) {
        if (list == null) {
            return;
        }
        destroy();
        if (list.size() == 0) {
            this.setVisibility(GONE);
            return;
        }
        bannerList = new ArrayList<>();
        bannerList.clear();
        bannerList.addAll(list);
        final int pointSize;
        pointSize = bannerList.size();

        if (points.getChildCount() != 0) {
            points.removeAllViewsInLayout();
        }
        //初始化与个数相同的指示器点
        for (int i = 0; i < pointSize; i++) {
            View dot = new View(getContext());
            dot.setBackgroundResource(unSelectRes);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ScreenUtil.dp2px(getContext(), 10),
                    ScreenUtil.dp2px(getContext(), 5));
            params.leftMargin = ScreenUtil.dp2px(getContext(), 5);
            params.gravity= Gravity.CENTER;
            dot.setLayoutParams(params);
            dot.setEnabled(false);
            points.addView(dot);
        }
        points.getChildAt(0).setBackgroundResource(selectRes);
        //监听图片轮播，改变指示器状态
        viewPager.clearOnPageChangeListeners();
        viewPager.setOffscreenPageLimit(3);
        if(!showFlag){
            viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int pos) {
                pos = pos % pointSize;
                currentPos = pos;
                for (int i = 0; i < points.getChildCount(); i++) {
                    points.getChildAt(i).setBackgroundResource(unSelectRes);
                }
                points.getChildAt(pos).setBackgroundResource(selectRes);

                View view = viewPager.getChildAt(pos);
                if(view!=null){
                    int height = view.getMeasuredHeight();
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewPager.getLayoutParams();
                    layoutParams.height = height;
                    viewPager.setLayoutParams(layoutParams);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (isStopScroll) {
                            startScroll();
                        }
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        stopScroll();
                        if (compositeSubscription != null) {
                            compositeSubscription.clear();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        bannerAdapter = new NewBannerAdapter(showFlag,bannerList, pointSize);
        viewPager.setAdapter(bannerAdapter);
        viewPager.setCurrentItem(100*bannerList.size());
        bannerAdapter.notifyDataSetChanged();
        bannerAdapter.setViewPagerOnItemClickListener(position -> {
            if (mOnBannerItemClickListener == null) {
                return;
            }
            mOnBannerItemClickListener.onItemClick(position);
        });


        if (bannerList.size() > 1) {
            //图片开始轮播
            startScroll();
            points.setVisibility(VISIBLE);
        } else {
            points.setVisibility(GONE);
        }

        //判断是否清空 指示器点
        if(showFlag){
            if(bannerList.size()!=1){
                points.setVisibility(VISIBLE);
            }else{
                points.setVisibility(GONE);
            }
            line.setVisibility(VISIBLE);
        }else{
            points.setVisibility(GONE);
            line.setVisibility(GONE);
        }
        points.setVisibility(GONE);
    }

    private boolean isStopScroll = false;


    /**
     * 图片开始轮播
     */
    private void startScroll() {
        compositeSubscription = new CompositeDisposable();
        isStopScroll = false;
        Disposable subscription = Observable.timer(delayTime, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (isStopScroll) {
                        return;
                    }
                    isStopScroll = true;
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                });
        compositeSubscription.add(subscription);
    }


    /**
     * 图片停止轮播
     */
    private void stopScroll() {
        isStopScroll = true;
    }


    public void destroy() {
        if (compositeSubscription != null) {
            compositeSubscription.clear();
        }
    }


    public onBannerItemClickListener mOnBannerItemClickListener;


    public void setOnBannerItemClickListener(onBannerItemClickListener mOnBannerItemClickListener) {
        this.mOnBannerItemClickListener = mOnBannerItemClickListener;
    }

    public NewBannerView setBannerView() {
        return this;
    }

    public interface onBannerItemClickListener {
        void onItemClick(int position);
    }

    class ZoomOutPageTransformer implements ViewPager.PageTransformer {

        private static final float MIN_SCALE_Y = 0.8f;
        @Override
        public void transformPage(@NonNull View view, float position) {
            if (position >= 1 || position <= -1) {
                view.setScaleY(MIN_SCALE_Y);
            } else if (position < 0) {
                //  -1 < position < 0
                //View 在再从中间往左边移动，或者从左边往中间移动
                float scaleY = MIN_SCALE_Y + (1 + position) * (1 - MIN_SCALE_Y);
                view.setScaleY(scaleY);
            } else {
                // 0 <= positin < 1
                //View 在从中间往右边移动 或者从右边往中间移动
                float scaleY = (1 - MIN_SCALE_Y) * (1 - position) + MIN_SCALE_Y;
                view.setScaleY(scaleY);
            }
        }
    }
}
