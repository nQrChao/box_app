package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zqhy.app.adapter.ViewPagerAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.mainpage.ChoiceListVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author pc
 * @date 2019/11/26-17:52
 * @description
 */
public class GameChoiceItemHolder extends BaseItemHolder<ChoiceListVo, GameChoiceItemHolder.ViewHolder> {

    public GameChoiceItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_page_game_choice;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    final int totalWidth = ScreenUtil.getScreenWidth(mContext) - (int) (16 * ScreenUtil.getScreenDensity(mContext));

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ChoiceListVo item) {
        List<ChoiceListVo.DataBean> list = item.getData();
        if (list != null && list.size() > 0) {
            String label_name = item.getData().get(0).getLabel_name();

            Drawable left = null;
            if (item.getGame_type() == 2) {
                holder.mTvName.setCompoundDrawablePadding((int) (4 * ScreenUtil.getScreenDensity(mContext)));
                left = mContext.getResources().getDrawable(R.mipmap.ic_main_page_choice_discount);
            } else if (item.getGame_type() == 3) {
                left = mContext.getResources().getDrawable(R.mipmap.ic_main_page_choice_h5);
                holder.mTvName.setCompoundDrawablePadding((int) (4 * ScreenUtil.getScreenDensity(mContext)));
            } else {
                holder.mTvName.setCompoundDrawablePadding(0);
            }
            holder.mTvName.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
            holder.mTvName.setText(label_name);
            Collections.shuffle(list);
        }

        List<View> viewList = createLineViews(item.getData());
        holder.mViewpager.setAdapter(new MyViewPager(viewList));

        holder.mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectViewPager(holder, viewList.size(), position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        selectViewPager(holder, viewList.size(), 0);
    }

    private void selectViewPager(ViewHolder holder, int pageSize, int position) {
        if (pageSize == 1) {
            holder.mTvViewPage1.setVisibility(View.GONE);
            holder.mTvViewPage2.setVisibility(View.GONE);
            holder.mTvViewPage3.setVisibility(View.GONE);
            return;
        }
        if (pageSize == 2) {
            holder.mTvViewPage1.setVisibility(View.VISIBLE);
            holder.mTvViewPage2.setVisibility(View.VISIBLE);
            holder.mTvViewPage3.setVisibility(View.GONE);
        }
        if (pageSize == 3) {
            holder.mTvViewPage1.setVisibility(View.VISIBLE);
            holder.mTvViewPage2.setVisibility(View.VISIBLE);
            holder.mTvViewPage3.setVisibility(View.VISIBLE);
        }

        if (position < 0) {
            position = 0;
        } else if (position > 2) {
            position = 2;
        }

        GradientDrawable gd1 = new GradientDrawable();
        gd1.setShape(GradientDrawable.OVAL);
        gd1.setColor(ContextCompat.getColor(mContext, R.color.color_ff5400));

        GradientDrawable gd2 = new GradientDrawable();
        gd2.setShape(GradientDrawable.OVAL);
        gd2.setColor(ContextCompat.getColor(mContext, R.color.color_dcdcdc));

        if (position == 0) {
            holder.mTvViewPage1.setBackground(gd1);
            holder.mTvViewPage2.setBackground(gd2);
            holder.mTvViewPage3.setBackground(gd2);
        } else if (position == 1) {
            holder.mTvViewPage1.setBackground(gd2);
            holder.mTvViewPage2.setBackground(gd1);
            holder.mTvViewPage3.setBackground(gd2);
        } else if (position == 2) {
            holder.mTvViewPage1.setBackground(gd2);
            holder.mTvViewPage2.setBackground(gd2);
            holder.mTvViewPage3.setBackground(gd1);
        }

    }

    private List<View> createLineViews(List<ChoiceListVo.DataBean> list) {

        List<List<ChoiceListVo.DataBean>> itemList = new ArrayList<>();

        int eachPieceSize = 4;

        for (int index = 0; index < list.size(); index += eachPieceSize) {
            int fromIndex = index;
            int toIndex = index + eachPieceSize >= list.size() ? list.size() : index + eachPieceSize;
            itemList.add(list.subList(fromIndex, toIndex));
        }

        List<View> lineViews = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            LinearLayout layout = new LinearLayout(mContext);
            layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            for (ChoiceListVo.DataBean dataBean : itemList.get(i)) {
                View itemView = createItemView2(dataBean);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(totalWidth / 4, ViewGroup.LayoutParams.MATCH_PARENT);
                layout.addView(itemView, params);
            }
            lineViews.add(layout);
        }

        return lineViews;
    }


    private View createItemView2(ChoiceListVo.DataBean dataBean) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_game_choice, null);

        AppCompatImageView mIvImage = itemView.findViewById(R.id.iv_image);
        TextView mTvGameName = itemView.findViewById(R.id.tv_game_name);
        TextView mTvLabelTag = itemView.findViewById(R.id.tv_label_tag);
        TextView mTvGameBenefits = itemView.findViewById(R.id.tv_game_benefits);


        GlideUtils.loadGameIcon(mContext, dataBean.getPic(), mIvImage);
        mTvGameName.setText(dataBean.getGamename());
        if (TextUtils.isEmpty(dataBean.getTop_label_name())) {
            mTvLabelTag.setVisibility(View.GONE);
        } else {
            mTvLabelTag.setVisibility(View.VISIBLE);
            mTvLabelTag.setText(dataBean.getTop_label_name());
        }
        mTvGameBenefits.setText(dataBean.getTitle());

        float density = ScreenUtil.getScreenDensity(mContext);

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(ContextCompat.getColor(mContext, R.color.white));
        gd.setCornerRadius(4 * density);
        gd.setStroke((int) (1 * density), ContextCompat.getColor(mContext, R.color.color_4c34f6));
        mTvGameBenefits.setBackground(gd);
        mTvGameBenefits.setTextColor(Color.parseColor("#4F02D5"));


        GradientDrawable gd2 = new GradientDrawable();
        float radius = 6 * density;
        gd2.setCornerRadii(new float[]{0, 0, radius, radius, 0, 0, radius, radius});
        if (dataBean.getGame_type() == 2) {
            gd2.setColors(new int[]{Color.parseColor("#8800FF"), Color.parseColor("#2000FF")});
        } else {
            gd2.setColors(new int[]{Color.parseColor("#A620FF"), Color.parseColor("#FF0008")});
        }
        mTvLabelTag.setBackground(gd2);

        itemView.setOnClickListener(v -> {
            //通用跳转
            appJump(dataBean);
        });
        return itemView;
    }

    private View createItemView(ChoiceListVo.DataBean dataBean) {
        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);

        float density = ScreenUtil.getScreenDensity(mContext);

        RelativeLayout ImageLayout = new RelativeLayout(mContext);

        int imageWidth = (int) (92 * density);
        int imageHeight = imageWidth;
        ImageLayout.setBackgroundResource(R.drawable.ts_shadow_game_icon);

        ImageView image = new ImageView(mContext);
        image.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        GlideUtils.loadGameIcon(mContext, dataBean.getPic(), image);
        ImageLayout.addView(image);

        layout.addView(ImageLayout, new LinearLayout.LayoutParams(imageWidth, imageHeight));

        TextView tv = new TextView(mContext);
        tv.setText(dataBean.getTitle());
        tv.setTextColor(ContextCompat.getColor(mContext, R.color.color_232323));
        tv.setTextSize(12);
        tv.setMaxLines(2);
        int padding = (int) (6 * density);
        tv.setPadding(padding, 0, padding, 0);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.addView(tv, tvParams);

        layout.setOnClickListener(v -> {
            //通用跳转
            appJump(dataBean);
        });

        return layout;
    }


    class MyViewPager extends ViewPagerAdapter {

        public MyViewPager(List<View> views) {
            super(views);
        }
    }

    public class ViewHolder extends AbsHolder {
        private TextView  mTvName;
        private TextView  mTvViewPage1;
        private TextView  mTvViewPage2;
        private TextView  mTvViewPage3;
        private ViewPager mViewpager;

        public ViewHolder(View view) {
            super(view);
            mTvName = findViewById(R.id.tv_name);
            mTvViewPage1 = findViewById(R.id.tv_view_page_1);
            mTvViewPage2 = findViewById(R.id.tv_view_page_2);
            mTvViewPage3 = findViewById(R.id.tv_view_page_3);
            mViewpager = findViewById(R.id.viewpager);

        }
    }
}
