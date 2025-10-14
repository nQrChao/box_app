package com.zqhy.app.core.view.game.forum.holder;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.exoplayer2.C;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.forum.ForumCategoryVo;
import com.zqhy.app.newproject.R;

/**
 *
 * @author Administrator
 * @date 2018/11/24
 */

public class CategoryItemHolder extends AbsItemHolder<ForumCategoryVo.DataBean, CategoryItemHolder.ViewHolder> {

    public CategoryItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ForumCategoryVo.DataBean item) {
        holder.tv_content.setText("#"+item.getName());
        if (item.isClick()){
            holder.tv_content.setTextColor(Color.parseColor("#ffffff"));
            holder.tv_content.setBackgroundResource(R.drawable.ts_shape_5571fe_big_radius);
        }else {
            holder.tv_content.setTextColor(Color.parseColor("#333333"));
            holder.tv_content.setBackgroundResource(R.drawable.shape_f5f5f5_big_radius);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_forum_category;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private TextView tv_content;


        public ViewHolder(View view) {
            super(view);
            tv_content = view.findViewById(R.id.tv_content);
        }
    }
}
