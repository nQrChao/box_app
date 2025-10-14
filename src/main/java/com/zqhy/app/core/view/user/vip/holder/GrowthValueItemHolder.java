package com.zqhy.app.core.view.user.vip.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.user.UserVipCountListVo;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author leeham2734
 * @date 2020/11/12-11:40
 * @description
 */
public class GrowthValueItemHolder extends AbsItemHolder<UserVipCountListVo.DataBean, GrowthValueItemHolder.ViewHolder> {
    public GrowthValueItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_user_growth_value;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull UserVipCountListVo.DataBean item) {
        holder.mTvTitle.setText(item.getType_name());
        holder.mTvTime.setText(CommonUtils.friendlyTime2(item.getAdd_time() * 1000));
        if (item.getVip_score() > 0) {
            holder.mTvGrowthValueCount.setText("+" + item.getAmount());
            holder.mTvGrowthValueCount.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff0000));
        } else {
            if (item.getVip_score() > 0) {
                holder.mTvGrowthValueCount.setText(String.valueOf(item.getAmount()));
                holder.mTvGrowthValueCount.setTextColor(ContextCompat.getColor(mContext, R.color.color_0052ef));
            }
        }
    }

    public class ViewHolder extends AbsHolder {

        private TextView mTvTitle;
        private TextView mTvTime;
        private TextView mTvGrowthValueCount;

        public ViewHolder(View view) {
            super(view);
            mTvTitle = findViewById(R.id.tv_title);
            mTvTime = findViewById(R.id.tv_time);
            mTvGrowthValueCount = findViewById(R.id.tv_growth_value_count);
        }
    }
}
