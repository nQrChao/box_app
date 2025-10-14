package com.zqhy.app.core.view.activity.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.activity.ActivityInfoListVo;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.TimeUtils;

/**
 * @author Administrator
 * @date 2018/11/17
 */

public class ActivityListItemHolder extends AbsItemHolder<ActivityInfoListVo.DataBean, ActivityListItemHolder.ViewHolder> {
    public ActivityListItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ActivityInfoListVo.DataBean item) {
        GlideApp.with(mContext)
                .asBitmap()
                .load(item.getPic())
                .centerCrop()
                .placeholder(R.mipmap.img_placeholder_v_1)
                .into(holder.mIvGameImage);

        try {
            long ms = Long.parseLong(item.getFabutime()) * 1000;
            holder.mTvTime.setText(TimeUtils.formatTimeStamp(ms, "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_list_activity;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private TextView mTvTime;
        private AppCompatImageView mIvGameImage;

        public ViewHolder(View view) {
            super(view);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mIvGameImage = itemView.findViewById(R.id.iv_game_image);

        }
    }
}
