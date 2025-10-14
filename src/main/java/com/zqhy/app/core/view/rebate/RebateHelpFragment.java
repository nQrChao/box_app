package com.zqhy.app.core.view.rebate;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kcrason.dynamicpagerindicatorlibrary.DynamicPagerIndicator;
import com.zqhy.app.adapter.abs.AbsAdapter;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.rebate.RebateProVo;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/16
 */

public class RebateHelpFragment extends BaseFragment {

    /**
     * @param tabType 1 图文教学   2 常见问题
     * @return
     */
    public static RebateHelpFragment newInstance(int tabType) {
        return newInstance(tabType, -1);
    }

    public static RebateHelpFragment newInstance(int tabType, int position) {
        RebateHelpFragment fragment = new RebateHelpFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tabType", tabType);
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "返利帮助";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_rebate_help;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    int tabType = 0;
    int position = 0;


    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            tabType = getArguments().getInt("tabType", 0);
            position = getArguments().getInt("position", 0);
        }
        super.initView(state);
        showSuccess();
        bindView();
        initActionBackBarAndTitle("返利帮助");
    }

    private DynamicPagerIndicator mDynamicPagerIndicator;
    private ViewPager mViewPager;

    private void bindView() {
        mDynamicPagerIndicator = findViewById(R.id.dynamic_pager_indicator);
        mViewPager = findViewById(R.id.view_pager);
        List<View> mViews = createPagerViews();
        mViewPager.setAdapter(new ItemPagerAdapter(mViews, new String[]{"图文教学", "常见问题"}));
        mViewPager.setOffscreenPageLimit(mViews.size());
        mDynamicPagerIndicator.setViewPager(mViewPager);

        if (tabType == 1) {
            mViewPager.setCurrentItem(0);
        } else if (tabType == 2) {
            mViewPager.setCurrentItem(1);
            if (position != -1) {
                if (rebateAdapter != null) {
                    rebateAdapter.notifyDataSetChanged();
                }
                if (mRecyclerView != null) {
                    ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(position, 0);
                }
            }
        } else {
            mViewPager.setCurrentItem(0);
        }
    }


    private List<View> createPagerViews() {
        View itemView1 = LayoutInflater.from(_mActivity).inflate(R.layout.layout_rebate_problem_item_1, null);
        View itemView2 = LayoutInflater.from(_mActivity).inflate(R.layout.layout_rebate_problem_item_2, null);

        initItemView1(itemView1);
        initItemView2(itemView2);

        List<View> viewList = new ArrayList<>();
        viewList.add(itemView1);
        viewList.add(itemView2);

        return viewList;
    }


    private void initItemView1(View itemView1) {
        ImageView mIvImage = itemView1.findViewById(R.id.iv_image);
        Drawable drawable = _mActivity.getResources().getDrawable(R.mipmap.img_rebate_help_item_1);
        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();


        int paramWidth = ScreenUtils.getScreenWidth(_mActivity);
        int paramHeight = paramWidth * drawableHeight / drawableWidth;

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(paramWidth, paramHeight);
        mIvImage.setLayoutParams(params);

        mIvImage.setImageResource(R.mipmap.img_rebate_help_item_1);
    }

    private RecyclerView mRecyclerView;
    private RebateAdapter rebateAdapter;

    private void initItemView2(View itemView2) {
        mRecyclerView = itemView2.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        String jsonData = CommonUtils.getJsonDataFromAsset(_mActivity, "rebate_common_problems.json");
        List<RebateProVo> list = null;

        try {
            Type listType = new TypeToken<List<RebateProVo>>() {
            }.getType();
            Gson gson = new Gson();
            list = gson.fromJson(jsonData, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        rebateAdapter = new RebateAdapter(_mActivity, list);
        mRecyclerView.setAdapter(rebateAdapter);

    }


    class RebateAdapter extends AbsAdapter<RebateProVo> {

        public RebateAdapter(Context context, List labels) {
            super(context, labels);
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, RebateProVo rebateProVo, int position) {
            if (holder instanceof ViewHolder) {
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.mViewLine.setVisibility(position == mLabels.size() - 1 ? View.GONE : View.VISIBLE);
                viewHolder.mTvTxt1.setText(rebateProVo.getPro_title());
                viewHolder.mTvTxt2.setText(rebateProVo.getPro_description());
            }
        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_rebate_problem_detail_list;
        }

        @Override
        public AbsViewHolder createViewHolder(View view) {
            return new RebateAdapter.ViewHolder(view);
        }

        class ViewHolder extends AbsAdapter.AbsViewHolder {
            private TextView mTvTxt1;
            private TextView mTvTxt2;
            private View mViewLine;

            public ViewHolder(View itemView) {
                super(itemView);
                mTvTxt1 = itemView.findViewById(R.id.tv_txt1);
                mTvTxt2 = itemView.findViewById(R.id.tv_txt2);
                mViewLine = itemView.findViewById(R.id.view_line);

            }
        }
    }

    class ItemPagerAdapter extends PagerAdapter {

        private String[] mStrings;

        private List<View> mViews;

        public ItemPagerAdapter(List<View> views, String[] mStrings) {
            this.mViews = views;
            this.mStrings = mStrings;
        }

        @Override
        public int getCount() {
            return mViews == null ? 0 : mViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View currentView = mViews.get(position);
            container.addView(currentView);
            return currentView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (mStrings != null && position < mStrings.length) {
                return mStrings[position];
            }
            return "";
        }
    }
}
