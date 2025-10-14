package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chaoji.im.glide.GlideApp;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.banner.BannerVo;
import com.zqhy.app.core.data.model.game.new0809.CommonDataBeanVo;
import com.zqhy.app.core.data.model.game.new0809.MainJingXuanDataVo;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideRoundTransform;
import com.zqhy.app.glide.GlideRoundTransformNew;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 * @author Administrator
 * @date 2021/8/14 0014-12:16
 * @description
 */
public class MainTuiJianHuoDongTuTuiItemHolder extends BaseItemHolder<MainJingXuanDataVo.HuoDongTuTuiItemDataBeanVo, MainTuiJianHuoDongTuTuiItemHolder.ViewHolder> {

    public MainTuiJianHuoDongTuTuiItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_page_tj_huodongtutui;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainJingXuanDataVo.HuoDongTuTuiItemDataBeanVo item) {
        if (TextUtils.isEmpty(item.module_title) && TextUtils.isEmpty(item.module_title_two)) {
            holder.mTvTitle.setVisibility(View.GONE);
        } else {
            holder.mTvTitle.setVisibility(View.VISIBLE);
            String title = "";
            if (!TextUtils.isEmpty(item.module_title)){
                title += item.module_title;
            }
            if (!TextUtils.isEmpty(item.module_title_two)){
                title += item.module_title_two;
            }
            SpannableString spannableString = new SpannableString(title);
            if (!TextUtils.isEmpty(item.module_title) && !TextUtils.isEmpty(item.module_title_color)){
                try {
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(item.module_title_color)), 0, item.module_title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }catch (Exception e){}
            }
            if (!TextUtils.isEmpty(item.module_title_two) && !TextUtils.isEmpty(item.module_title_two_color)){
                try {
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(item.module_title_two_color)), title.length() - item.module_title_two.length(), title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }catch (Exception e){}
            }
            holder.mTvTitle.setText(spannableString);
        }
        if (!TextUtils.isEmpty(item.module_sub_title)){
            holder.mTvDescription.setVisibility(View.VISIBLE);
            holder.mTvDescription.setText(item.module_sub_title);
            try {
                if (!TextUtils.isEmpty(item.module_sub_title_color)) holder.mTvDescription.setTextColor(Color.parseColor(item.module_sub_title_color));
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

        MyAdapter myAdapter = new MyAdapter(mContext, item.data);
        holder.mViewPager.setAdapter(myAdapter);
        holder.mViewPager.setOffscreenPageLimit(item.data.size());
        holder.mViewPager.setCurrentItem(0);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)holder.mViewProgress.getLayoutParams();
        layoutParams.width = ScreenUtil.dp2px(mContext, 39) / item.data.size();
        layoutParams.leftMargin = 0;
        holder.mViewProgress.setLayoutParams(layoutParams);


        holder.mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                layoutParams.leftMargin = ScreenUtil.dp2px(mContext, (39F / item.data.size()) * position);
                holder.mViewProgress.setLayoutParams(layoutParams);
            }
        });
        if (item.data.size() > 1){
            holder.mLlProgress.setVisibility(View.VISIBLE);
        }else {
            holder.mLlProgress.setVisibility(View.GONE);
        }
    }

    public class MyAdapter extends PagerAdapter {

        private Context        context;//上下文
        private List<CommonDataBeanVo.XingYouDataJumpInfoVo> list;//数据源

        public MyAdapter(Context context, List<CommonDataBeanVo.XingYouDataJumpInfoVo> list) {
            this.context = context;
            this.list = list;
        }

        //ViewPager总共有几个页面
        @Override
        public int getCount() {
            return list.size();
        }
        //判断一个页面(View)是否与instantiateItem方法返回的Object一致
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        //添加视图
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            CommonDataBeanVo.XingYouDataJumpInfoVo gameInfoVo = list.get(position);
            //加载vp的布局
            View view = View.inflate(context, R.layout.item_main_banner_rich, null);
            ImageView mIvImage = view.findViewById(R.id.iv_image);

            ViewGroup.LayoutParams layoutParams = mIvImage.getLayoutParams();
            layoutParams.width = ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 60);
            layoutParams.height = layoutParams.width * 320 / 710;
            mIvImage.setLayoutParams(layoutParams);
            GlideApp.with(mContext).asBitmap()
                    .load(gameInfoVo.getPic())
                    .placeholder(R.mipmap.img_placeholder_v_2)
                    .error(R.mipmap.img_placeholder_v_2)
//                    .transform(new GlideRoundTransformNew(mContext, 10))
                    .into(mIvImage);
            view.setOnClickListener(v -> {
                appJump(gameInfoVo.getPage_type(), gameInfoVo.getParam());
            });

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //        super.destroyItem(container, position, object);
            //移除视图
            container.removeView((View) object);
        }
    }

    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    public class ViewHolder extends AbsHolder {

        private TextView mTvTitle;
        private TextView mTvMore;
        private TextView mTvDescription;

        private ViewPager mViewPager;
        private LinearLayout mLlProgress;
        private View mViewProgress;

        public ViewHolder(View view) {
            super(view);
            mTvTitle = view.findViewById(R.id.tv_title);
            mTvMore = view.findViewById(R.id.tv_more);
            mTvDescription = view.findViewById(R.id.tv_description);

            mViewPager = view.findViewById(R.id.viewPager);
            mLlProgress = findViewById(R.id.ll_progress);
            mViewProgress = findViewById(R.id.view_progress);

            ViewGroup.LayoutParams pagerLayoutParams = mViewPager.getLayoutParams();
            pagerLayoutParams.width = ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 60);
            pagerLayoutParams.height = pagerLayoutParams.width * 320 / 710;
            mViewPager.setLayoutParams(pagerLayoutParams);
        }
    }
}
