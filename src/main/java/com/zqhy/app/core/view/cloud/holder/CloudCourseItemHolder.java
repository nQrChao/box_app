package com.zqhy.app.core.view.cloud.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.cloud.CloudCourseVo;
import com.zqhy.app.newproject.R;

/**
 *
 * @author Administrator
 * @date 2018/11/24
 */

public class CloudCourseItemHolder extends AbsItemHolder<CloudCourseVo.DataBean, CloudCourseItemHolder.ViewHolder> {

    public CloudCourseItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CloudCourseVo.DataBean item) {
        holder.mTvTitle.setText(item.getTitle());
        holder.mTvContent.setText(item.getContent());

        if (item.isUpfold()){
            holder.mIvArrow.setImageResource(R.mipmap.ic_arrow_up);
            holder.mTvContent.setVisibility(View.VISIBLE);
        }else {
            holder.mIvArrow.setImageResource(R.mipmap.ic_arrow_down);
            holder.mTvContent.setVisibility(View.GONE);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_cloud_course;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mLlContent;
        private TextView mTvTitle;
        private TextView mTvContent;
        private ImageView mIvArrow;

        public ViewHolder(View view) {
            super(view);
            mLlContent = view.findViewById(R.id.ll_content);
            mTvTitle = view.findViewById(R.id.tv_title);
            mTvContent = view.findViewById(R.id.tv_content);
            mIvArrow = view.findViewById(R.id.iv_arrow);
        }
    }
}
