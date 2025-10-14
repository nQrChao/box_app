package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.new0809.CommonDataBeanVo;
import com.zqhy.app.core.data.model.game.new0809.MainJingXuanDataVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2021/8/14 0014-12:26
 * @description
 */
public class MainTuiJingMoreTuiJianItemHolder extends BaseItemHolder<MainJingXuanDataVo.MoreTuiJianDataBeanVo, MainTuiJingMoreTuiJianItemHolder.ViewHolder> {

    public MainTuiJingMoreTuiJianItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_page_tj_moretuijian;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainJingXuanDataVo.MoreTuiJianDataBeanVo item) {
        if (!TextUtils.isEmpty(item.module_title)) {
            holder.mTvTitle.setVisibility(View.VISIBLE);
            holder.mTvTitle.setText(item.module_title);
            try {
                holder.mTvTitle.setTextColor(Color.parseColor(item.module_title_color));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            holder.mTvTitle.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(item.module_title_two)) {
            holder.mTvSubTitle.setVisibility(View.VISIBLE);
            holder.mTvSubTitle.setText(item.module_title_two);
            try {
                holder.mTvSubTitle.setTextColor(Color.parseColor(item.module_title_two_color));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            holder.mTvSubTitle.setVisibility(View.GONE);
        }

        holder.mFlexBoxLayout.removeAllViews();
        for (int i = 0; i < item.data.size(); i++) {
            CommonDataBeanVo.XingYouDataJumpInfoVo jumpInfoVo = item.data.get(i);
            if (jumpInfoVo != null) {
                View itemView = createItemView(jumpInfoVo);
                int width = (ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 50)) / 3;
                int height = ScreenUtil.dp2px(mContext, 40);
                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(width, height);
                int margin = ScreenUtil.dp2px(mContext, 5);
                params.leftMargin = margin;
                params.rightMargin = margin;
                params.topMargin = margin;
                params.bottomMargin = margin;
                holder.mFlexBoxLayout.addView(itemView, params);
            }
        }
    }

    private View createItemView(CommonDataBeanVo.XingYouDataJumpInfoVo jumpInfoVo) {
        TextView view = new TextView(mContext);
        view.setText(jumpInfoVo.text);
        try {
            view.setTextColor(Color.parseColor(jumpInfoVo.textcolor));
        } catch (Exception e) {
            e.printStackTrace();
        }
        view.setGravity(Gravity.CENTER);
        view.setTextSize(13.5f);
        view.setIncludeFontPadding(false);

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#F9F9F9"));
        gd.setStroke(ScreenUtil.dp2px(mContext,1),Color.parseColor("#E4E4E4"));
        gd.setCornerRadius(ScreenUtil.dp2px(mContext,100));
        view.setBackground(gd);

        view.setOnClickListener(v -> {
            appJump(jumpInfoVo);
        });
        return view;
    }

    public class ViewHolder extends AbsHolder {

        private TextView      mTvSubTitle;
        private TextView      mTvTitle;
        private FlexboxLayout mFlexBoxLayout;

        public ViewHolder(View view) {
            super(view);

            mTvSubTitle = findViewById(R.id.tv_sub_title);
            mTvTitle = findViewById(R.id.tv_title);
            mFlexBoxLayout = findViewById(R.id.flex_box_layout);

        }
    }
}
