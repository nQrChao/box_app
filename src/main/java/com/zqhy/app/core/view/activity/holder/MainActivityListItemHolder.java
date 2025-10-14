package com.zqhy.app.core.view.activity.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.activity.ActivityInfoListVo;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.TimeUtils;

/**
 * @author pc
 * @date 2019/12/16-14:26
 * @description
 */
public class MainActivityListItemHolder extends BaseItemHolder<ActivityInfoListVo.DataBean, MainActivityListItemHolder.ViewHolder> {

    public MainActivityListItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_activity;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ActivityInfoListVo.DataBean item) {
        holder.mLayoutActivityType1.setVisibility(View.GONE);
        holder.mLayoutActivityType2.setVisibility(View.GONE);

        if (item.getType() == 1) {
            holder.mLayoutActivityType1.setVisibility(View.VISIBLE);
            GlideApp.with(mContext)
                    .asBitmap()
                    .load(item.getPic())
                    .centerCrop()
                    .placeholder(R.mipmap.img_placeholder_v_1)
                    .into(holder.mIvGameImage);

            holder.mTvName.setText(item.getGamename());
            holder.mTvContent.setText(item.getTitle());
            try {
                long ms = Long.parseLong(item.getFabutime()) * 1000;
                holder.mTvTime.setText(TimeUtils.formatTimeStamp(ms, "yyyy-MM-dd"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (item.getType() == 2) {
            holder.mLayoutActivityType2.setVisibility(View.VISIBLE);
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
    }

    public class ViewHolder extends AbsHolder {
        private CardView           mLayoutActivityType1;
        private AppCompatImageView mIvGameImage;
        private TextView           mTvName;
        private TextView           mTvContent;
        private TextView           mTvTime;
        private LinearLayout       mLayoutActivityType2;
        private ImageView          mGameIconIV;
        private TextView           mTvActivityName;
        private TextView           mTvActivityTime;
        private ImageView          mIvActivityMore;


        public ViewHolder(View view) {
            super(view);
            mLayoutActivityType1 = findViewById(R.id.layout_activity_type_1);
            mIvGameImage = findViewById(R.id.iv_game_image);
            mTvName = findViewById(R.id.tv_name);
            mTvContent = findViewById(R.id.tv_content);
            mTvTime = findViewById(R.id.tv_time);
            mLayoutActivityType2 = findViewById(R.id.layout_activity_type_2);
            mGameIconIV = findViewById(R.id.gameIconIV);
            mTvActivityName = findViewById(R.id.tv_activity_name);
            mTvActivityTime = findViewById(R.id.tv_activity_time);
            mIvActivityMore = findViewById(R.id.iv_activity_more);

        }
    }
}
