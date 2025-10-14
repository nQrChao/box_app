package com.zqhy.app.core.view.game.forum.holder;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.forum.ForumCategoryVo;
import com.zqhy.app.core.tool.MResource;
import com.zqhy.app.newproject.R;

/**
 *
 * @author Administrator
 * @date 2018/11/24
 */

public class EmoItemHolder extends AbsItemHolder<String, EmoItemHolder.ViewHolder> {

    public EmoItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull String item) {
        holder.image.setImageResource(MResource.getResourceId(mContext, "mipmap", item));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_forum_emo;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private ImageView image;


        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
        }
    }
}
