package com.zqhy.app.core.view.activity.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
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

public class AnnouncementListItemHolder extends AbsItemHolder<ActivityInfoListVo.DataBean, AnnouncementListItemHolder.ViewHolder> {
    public AnnouncementListItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ActivityInfoListVo.DataBean item) {
        GlideApp.with(mContext)
                .asBitmap()
                .load(item.getIcon())
                .centerCrop()
                .placeholder(R.mipmap.ic_placeholder)
                .into(holder.mGameIconIV);

        holder.mTvActivityName.setText(item.getTitle());
        try {
            long ms = Long.parseLong(item.getFabutime()) * 1000;
            holder.mTvActivityTime.setText("发布时间：" + TimeUtils.formatTimeStamp(ms, "yyyy-MM-dd HH:mm"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_list_announcement;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private ImageView mGameIconIV;
        private TextView mTvActivityName;
        private TextView mTvActivityTime;
        private ImageView mIvActivityMore;

        public ViewHolder(View view) {
            super(view);
            mGameIconIV = (ImageView) view.findViewById(R.id.gameIconIV);
            mTvActivityName = (TextView) view.findViewById(R.id.tv_activity_name);
            mTvActivityTime = (TextView) view.findViewById(R.id.tv_activity_time);
            mIvActivityMore = (ImageView) view.findViewById(R.id.iv_activity_more);
        }
    }
}
