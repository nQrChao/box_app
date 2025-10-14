package com.zqhy.app.core.view.chat.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.chat.ChatTeamNoticeListVo;
import com.zqhy.app.core.data.model.cloud.CloudCourseVo;
import com.zqhy.app.newproject.R;

/**
 *
 * @author Administrator
 * @date 2018/11/24
 */

public class ChatNoticeItemHolder extends AbsItemHolder<ChatTeamNoticeListVo.DataBean, ChatNoticeItemHolder.ViewHolder> {

    public ChatNoticeItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ChatTeamNoticeListVo.DataBean item) {
        holder.mTvTime.setText(item.getCreate_time());
        holder.mTvTitle.setText(item.getTitle());
        holder.mTvContent.setText(item.getContent());
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
        private TextView mTvTime;
        private TextView mTvTitle;
        private TextView mTvContent;

        public ViewHolder(View view) {
            super(view);
            mTvTime = view.findViewById(R.id.tv_time);
            mTvTitle = view.findViewById(R.id.tv_title);
            mTvContent = view.findViewById(R.id.tv_content);
        }
    }
}
