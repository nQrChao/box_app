package com.zqhy.app.core.view.kefu.holder;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.kefu.KefuInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;

/**
 *
 * @author Administrator
 * @date 2018/11/11
 */

public class KefuProItemHolder extends AbsItemHolder<KefuInfoVo.ItemsBean,KefuProItemHolder.ViewHolder>{

    public KefuProItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull KefuInfoVo.ItemsBean item) {
        holder.mTvTitle.setText(item.getTitle1());
        holder.mTvContent.setText(item.getContent());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_kefu_pro_list;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private View mViewTag;
        private TextView mTvTitle;
        private TextView mTvContent;

        public ViewHolder(View view) {
            super(view);
            mViewTag = view.findViewById(R.id.view_tag);
            mTvTitle = view.findViewById(R.id.tv_title);
            mTvContent = view.findViewById(R.id.tv_content);

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(5 * ScreenUtil.getScreenDensity(mContext));
            gd.setColor(ContextCompat.getColor(mContext, R.color.color_ff8f19));
            mViewTag.setBackground(gd);

        }
    }
}
