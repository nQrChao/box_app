package com.zqhy.app.core.view.main.holder.bt;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.bt.MainBTPageGameVo;
import com.zqhy.app.core.data.model.game.new0809.MainJingXuanDataVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.main.holder.GameNormalItemHolder;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.TitleTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leeham2734
 * @date 2020/8/19-18:25
 * @description
 */
public class NewJXGameBTPageItemHolder extends BaseItemHolder<MainBTPageGameVo, NewJXGameBTPageItemHolder.ViewHolder> {

    public NewJXGameBTPageItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_bt_common_layout_new_jx;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final MainBTPageGameVo item) {
        MainJingXuanDataVo.HaoYouTuiJianDataBeanVo jxHaoyoutuijian = item.jx_haoyoutuijian;
        if (TextUtils.isEmpty(jxHaoyoutuijian.module_title) && TextUtils.isEmpty(jxHaoyoutuijian.module_title_two)) {
            holder.mTvTitle.setVisibility(View.GONE);
        } else {
            holder.mTvTitle.setVisibility(View.VISIBLE);
            String title = "";
            if (!TextUtils.isEmpty(jxHaoyoutuijian.module_title)){
                title += jxHaoyoutuijian.module_title;
            }
            if (!TextUtils.isEmpty(jxHaoyoutuijian.module_title_two)){
                title += jxHaoyoutuijian.module_title_two;
            }
            SpannableString spannableString = new SpannableString(title);
            if (!TextUtils.isEmpty(jxHaoyoutuijian.module_title) && !TextUtils.isEmpty(jxHaoyoutuijian.module_title_color)){
                try {
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(jxHaoyoutuijian.module_title_color)), 0, jxHaoyoutuijian.module_title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }catch (Exception e){}
            }
            if (!TextUtils.isEmpty(jxHaoyoutuijian.module_title_two) && !TextUtils.isEmpty(jxHaoyoutuijian.module_title_two_color)){
                try {
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(jxHaoyoutuijian.module_title_two_color)), title.length() - jxHaoyoutuijian.module_title_two.length(), title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }catch (Exception e){}
            }
            holder.mTvTitle.setText(spannableString);
        }
        if (!TextUtils.isEmpty(jxHaoyoutuijian.module_sub_title)){
            holder.mTvDescription.setVisibility(View.VISIBLE);
            holder.mTvDescription.setText(jxHaoyoutuijian.module_sub_title);
            try {
                if (!TextUtils.isEmpty(jxHaoyoutuijian.module_sub_title_color)) holder.mTvDescription.setTextColor(Color.parseColor(jxHaoyoutuijian.module_sub_title_color));
            }catch (Exception e){}
        }else {
            holder.mTvDescription.setVisibility(View.GONE);
        }
        if (item.additional != null){
            holder.mTvMore.setVisibility(View.VISIBLE);
            holder.mTvMore.setText(item.additional.text);
            try {
                if (!TextUtils.isEmpty(item.additional.textcolor)) holder.mTvMore.setTextColor(Color.parseColor(item.additional.textcolor));
            }catch (Exception e){}
            holder.mTvMore.setOnClickListener(v -> {
                appJump(item.additional.getPage_type(), item.additional.getParam());
            });
        }else {
            holder.mTvMore.setVisibility(View.GONE);
        }
        int totalSize = item.getGameInfoVoList().size();
        int rowSize = item.getRowSize();

        holder.columnSize = totalSize % rowSize == 0 ? totalSize / rowSize : (totalSize / rowSize) + 1;
        holder.pageViewList.clear();
        int index = 0;
        for (int x = 0; x < holder.columnSize; x++) {
            List<GameInfoVo> infoVos = new ArrayList<>();
            for (int y = 0; y < rowSize && index < totalSize; y++) {
                infoVos.add(item.getGameInfoVoList().get(index));
                index++;
            }
            View pageView = holder.getPageView(infoVos);
            holder.pageViewList.add(pageView);
        }

        DynamicPagerAdapter adapter = new DynamicPagerAdapter(holder.pageViewList);
        holder.mViewPager.setAdapter(adapter);

        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams)holder.mViewProgress.getLayoutParams();
        layoutParams1.width = ScreenUtil.dp2px(mContext, 39) / holder.columnSize;
        layoutParams1.leftMargin = 0;
        holder.mViewProgress.setLayoutParams(layoutParams1);

        holder.mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                item.pageIndex = position;
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams)holder.mViewProgress.getLayoutParams();
                layoutParams2.leftMargin = ScreenUtil.dp2px(mContext, (39F / holder.columnSize) * position);
                holder.mViewProgress.setLayoutParams(layoutParams2);
            }
        });
        holder.mViewPager.setOffscreenPageLimit(holder.pageViewList.size());
        item.setLoad(true);

        if (holder.columnSize > 1){
            holder.mLlProgress.setVisibility(View.VISIBLE);
        }else {
            holder.mLlProgress.setVisibility(View.GONE);
        }
    }

    private class DynamicPagerAdapter extends PagerAdapter {
        private List<View>         pageViews;
        private Map<Integer, View> mMap;
        private int                Tag = 0;

        public DynamicPagerAdapter(List<View> pageViews) {
            this.pageViews = pageViews;
            mMap = new HashMap<>();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return Tag == 0 ? POSITION_UNCHANGED : POSITION_NONE;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            mMap.remove(position);
        }

        @Override
        public int getCount() {
            return pageViews == null ? 0 : pageViews.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Tag = 0;
            View itemView = pageViews.get(position);
            container.addView(itemView);
            mMap.put(position, itemView);
            return itemView;
        }

        @Override
        public void notifyDataSetChanged() {
            Tag = 1;
            super.notifyDataSetChanged();
        }


    }

    private View createGameItemView(GameInfoVo gameInfoVo) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_game_normal, null);
        GameNormalItemHolder.ViewHolder viewHolder = new GameNormalItemHolder.ViewHolder(itemView);
        GameNormalItemHolder normalItemHolder = new GameNormalItemHolder(mContext, 50);
        HashMap<Integer, Object> map = new HashMap<>();
        map.put(R.id.tag_fragment, _mFragment);
        normalItemHolder.initViewHolder(viewHolder, map);
        normalItemHolder.onBindViewHolder(viewHolder, gameInfoVo);

        return itemView;
    }

    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    public class ViewHolder extends AbsHolder {

        private ViewPager    mViewPager;

        private List<View>    pageViewList = new ArrayList<>();
        private int           columnSize;
        private TextView mTvTitle;
        private TextView mTvMore;
        private TextView mTvDescription;
        private LinearLayout mLlProgress;
        private View mViewProgress;

        public ViewHolder(View view) {
            super(view);
            mViewPager = findViewById(R.id.view_pager);
            mTvTitle = view.findViewById(R.id.tv_title);
            mTvMore = view.findViewById(R.id.tv_more);
            mTvDescription = view.findViewById(R.id.tv_description);
            mLlProgress = findViewById(R.id.ll_progress);
            mViewProgress = findViewById(R.id.view_progress);

            ViewGroup.LayoutParams layoutParams = mViewPager.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.height = ScreenUtil.dp2px(mContext, 98 * 3);
                mViewPager.setLayoutParams(layoutParams);
            }
        }

        protected View getPageView(List<GameInfoVo> item) {
            LinearLayout layout = new LinearLayout(mContext);
            layout.setOrientation(LinearLayout.VERTICAL);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(lp);
            layout.removeAllViews();
            for (int i = 0; i < item.size(); i++) {
                View itemView = createGameItemView(item.get(i));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.dp2px(mContext, 98));
                params.gravity = Gravity.CENTER;
                layout.addView(itemView, params);
            }
            return layout;
        }
    }
}
