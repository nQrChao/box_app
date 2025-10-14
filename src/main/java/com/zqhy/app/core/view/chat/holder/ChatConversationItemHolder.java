package com.zqhy.app.core.view.chat.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.chat.ChatRecommendVo;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 *
 * @author Administrator
 * @date 2018/11/24
 */

public class ChatConversationItemHolder extends AbsItemHolder<ChatRecommendVo.DataBean, ChatConversationItemHolder.ViewHolder> {

    public ChatConversationItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ChatRecommendVo.DataBean item) {
        GlideUtils.loadGameIcon(mContext, item.getGameicon(), holder.mIvIcon);
        holder.mTvTitle.setText(item.getGamename());
        holder.mTvContent.setText(item.getTeam_name());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_chat_notice;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private ImageView mIvIcon;
        private TextView mTvTitle;
        private TextView mTvContent;
        private TextView mTvJoin;

        public ViewHolder(View view) {
            super(view);
            mIvIcon = view.findViewById(R.id.iv_icon);
            mTvTitle = view.findViewById(R.id.tv_title);
            mTvContent = view.findViewById(R.id.tv_content);
            mTvJoin = view.findViewById(R.id.tv_join);
        }
    }
}
