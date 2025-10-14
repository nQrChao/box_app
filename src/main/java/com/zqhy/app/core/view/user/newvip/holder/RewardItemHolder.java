package com.zqhy.app.core.view.user.newvip.holder;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.user.newvip.DayRrewardListInfoVo;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class RewardItemHolder extends AbsItemHolder<DayRrewardListInfoVo.DayRewardVo, RewardItemHolder.ViewHolder> {
    private boolean isToday;
    private boolean isNotTo;
    public RewardItemHolder(Context context, boolean isToday, boolean isNotTo) {
        super(context);
        this.isToday = isToday;
        this.isNotTo = isNotTo;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull DayRrewardListInfoVo.DayRewardVo item) {
        GlideUtils.loadGameIcon(mContext, item.getIcon(), holder.mIvIcon);
        holder.mTvName.setText(item.getTitle());
        SpannableString ss = new SpannableString(item.getPrice_label() + "/每日" + item.getBuy_count() + "次");
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF450A")), 0, item.getPrice_label().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.mTvContent.setText(ss);
        //holder.mTvContent.setText(item.getTitle() + "/每日" + item.getBuy_count() + "次");
        if (isToday){
            holder.mTvConfirm.setText("进行中");
            holder.mTvConfirm.setTextColor(Color.parseColor("#FB4C37"));
            holder.mTvConfirm.setBackgroundResource(R.drawable.ts_shape_big_radius_fb4c37_line);
        }else {
            if (isNotTo){
                holder.mTvConfirm.setText("待开放");
                holder.mTvConfirm.setTextColor(Color.parseColor("#999999"));
                holder.mTvConfirm.setBackgroundResource(R.drawable.ts_shape_f2f2f2_big_radius);
            }else{
                holder.mTvConfirm.setText("已结束");
                holder.mTvConfirm.setTextColor(Color.parseColor("#999999"));
                holder.mTvConfirm.setBackgroundResource(R.drawable.ts_shape_f2f2f2_big_radius);
            }
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_vip_reward;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private ImageView mIvIcon;
        private TextView mTvName;
        private TextView mTvContent;
        private TextView mTvConfirm;

        public ViewHolder(View view) {
            super(view);
            mIvIcon = itemView.findViewById(R.id.iv_icon);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvContent = itemView.findViewById(R.id.tv_content);
            mTvConfirm = itemView.findViewById(R.id.tv_confirm);
        }
    }
}
