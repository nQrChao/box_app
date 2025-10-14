package com.zqhy.app.core.view.tryplay.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.tryplay.TryGameInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.tryplay.TryGameTaskFragment;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2020/11/3-9:43
 * @description
 */
public class TryGameTaskItemHolder extends AbsItemHolder<TryGameInfoVo.TrialItemInfoVo, TryGameTaskItemHolder.ViewHolder> {

    public TryGameTaskItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_try_game_task;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TryGameInfoVo.TrialItemInfoVo item) {
        holder.mTvTryGameTitle.setText(item.getTitle());
        holder.mTvTryGameProgress.setText("进度：" + item.getFinished_num() + "/" + item.getCondition_num());
        holder.mTvTryGameProgressLeft.setText("剩余：" + ((item.getTotal_integral() - item.getReceive_integral()) * 100 / item.getTotal_integral()) + "%");

        SpannableString ss = new SpannableString("奖励：" + item.getIntegral() + "积分");
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_ff0000)),
                2, 2 + (String.valueOf(item.getIntegral()).length() + 2), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.mTvTryGameReward.setText(ss);

        if (item.getStatus() == 3 || item.getStatus() == 1) {
            //初始(任务未完成,不可领取)
            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(ScreenUtil.dp2px(mContext, 50));
            gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            gd.setColors(new int[]{Color.parseColor("#FE964C"), Color.parseColor("#FE3D62")});
            holder.mTvTryGameStatus.setBackground(gd);
            holder.mTvTryGameStatus.setText("领奖");
            holder.mTvTryGameStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));

            holder.mTvTryGameStatus.setEnabled(true);
            holder.mTvTryGameStatus.setOnClickListener(view -> {
                if (_mFragment != null && _mFragment.checkLogin()) {
                    if (_mFragment instanceof TryGameTaskFragment) {
                        ((TryGameTaskFragment) _mFragment).getTryGameTaskReward(item.getId());
                    }
                }
            });
        } else if (item.getStatus() == 2) {
            //奖励被领完了
            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(ScreenUtil.dp2px(mContext, 50));
            gd.setColor(Color.parseColor("#EFECEC"));
            holder.mTvTryGameStatus.setBackground(gd);
            holder.mTvTryGameStatus.setText("已抢完");
            holder.mTvTryGameStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_919191));

            holder.mTvTryGameStatus.setEnabled(false);
        } else if (item.getStatus() == 10) {
            //完成
            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(ScreenUtil.dp2px(mContext, 50));
            gd.setColor(Color.parseColor("#EFECEC"));
            holder.mTvTryGameStatus.setBackground(gd);
            holder.mTvTryGameStatus.setText("完成");
            holder.mTvTryGameStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_919191));

            holder.mTvTryGameStatus.setEnabled(false);
        }
    }

    public class ViewHolder extends AbsHolder {
        private TextView mTvTryGameTitle;
        private TextView mTvTryGameProgress;
        private TextView mTvTryGameReward;
        private TextView mTvTryGameStatus;
        private TextView mTvTryGameProgressLeft;

        public ViewHolder(View view) {
            super(view);
            mTvTryGameTitle = findViewById(R.id.tv_try_game_title);
            mTvTryGameProgress = findViewById(R.id.tv_try_game_progress);
            mTvTryGameReward = findViewById(R.id.tv_try_game_reward);
            mTvTryGameStatus = findViewById(R.id.tv_try_game_status);
            mTvTryGameProgressLeft = findViewById(R.id.tv_try_game_progress_left);

        }
    }
}
