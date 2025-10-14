package com.zqhy.app.core.view.game.forum.holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.forum.ForumListVo;
import com.zqhy.app.core.view.game.forum.ForumDetailFragment;
import com.zqhy.app.core.view.game.forum.ForumLongDetailFragment;
import com.zqhy.app.newproject.R;

/**
 *
 * @author Administrator
 * @date 2018/11/24
 */

public class StickyDetailItemHolder extends AbsItemHolder<ForumListVo.DataBean, StickyDetailItemHolder.ViewHolder> {

    public StickyDetailItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ForumListVo.DataBean item) {
        holder.tv_content.setText(item.getTitle());
        holder.tv_content.setOnClickListener(v -> {
            if (_mFragment != null) {
                int tid = item.getTid();
                //特征, 1:图文分开; 2:图文并茂
                if (item.getFeature() == 1) {
                    _mFragment.startFragment(ForumDetailFragment.newInstance(tid + ""));
                } else if (item.getFeature() == 2) {
                    _mFragment.startFragment(ForumLongDetailFragment.newInstance(tid + ""));
                }
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_forum_detail_sticky;
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
