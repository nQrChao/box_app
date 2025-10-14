package com.zqhy.app.core.view.tryplay.holder;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.tryplay.TryGameInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.tryplay.chlid.RewardListFragment;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class TryGameRewardItemHolder extends AbsItemHolder<TryGameInfoVo.TaskListVo.DataBean, TryGameRewardItemHolder.ViewHolder> {

    private float density;

    public TryGameRewardItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_try_game_reward;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TryGameInfoVo.TaskListVo.DataBean item) {
        int left_num = item.getTask_stock() - item.getTask_got_num();
        left_num = left_num < 0 ? 0 : left_num;
        {
            StringBuilder countSb = new StringBuilder();
            countSb.append("总:")
                    .append(item.getTask_stock())
                    .append(" / ")
                    .append("剩余:");
            int startIndex = countSb.length();
            String strLeftNum = String.valueOf(left_num);
            countSb.append(strLeftNum);
            SpannableString countSs = new SpannableString(countSb);
            countSs.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_ff4949)),
                    startIndex, startIndex + strLeftNum.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            holder.mTvTryGameRewardCount.setText(countSs);
        }
        if (item.getTask_type() == 2) {
            //战力任务
            StringBuilder itemSb = new StringBuilder();
            itemSb.append("角色战力达到");
            if (!TextUtils.isEmpty(item.getTask_level())) {
                itemSb.append(item.getTask_level());
            } else {
                itemSb.append(item.getTask_val());
            }
            if (left_num == 0) {
                int startIndex = itemSb.length();
                itemSb.append("（已抢光）");
                SpannableString itemSs = new SpannableString(itemSb);
                itemSs.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_818181)),
                        startIndex, startIndex + 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                holder.mTvTryGameRewardItem.setText(itemSs);
            } else {
                holder.mTvTryGameRewardItem.setText(itemSb);
            }

        } else if (item.getTask_type() == 1) {
            //等级任务
            StringBuilder itemSb = new StringBuilder();
            itemSb.append("角色等级达到");
            if (!TextUtils.isEmpty(item.getTask_level())) {
                itemSb.append(item.getTask_level());
            } else {
                itemSb.append(item.getTask_val() + "级");
            }
            if (left_num == 0) {
                int startIndex = itemSb.length();
                itemSb.append("（已抢光）");
                SpannableString itemSs = new SpannableString(itemSb);
                itemSs.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_818181)),
                        startIndex, startIndex + 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                holder.mTvTryGameRewardItem.setText(itemSs);
            } else {
                holder.mTvTryGameRewardItem.setText(itemSb);
            }
        } else if (item.getTask_type() == 3) {
            //充值任务
            StringBuilder itemSb = new StringBuilder();
            itemSb.append("游戏内");
            if ("1".equals(item.getTask_level())) {
                itemSb.append("单笔");
            } else if ("2".equals(item.getTask_level())) {
                itemSb.append("累计");
            }
            itemSb.append("充值")
                    .append(String.valueOf(item.getTask_val()))
                    .append("元");

            if (left_num == 0) {
                int startIndex = itemSb.length();
                itemSb.append("（已抢光）");
                SpannableString itemSs = new SpannableString(itemSb);
                itemSs.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_818181)),
                        startIndex, startIndex + 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                holder.mTvTryGameRewardItem.setText(itemSs);
            } else {
                holder.mTvTryGameRewardItem.setText(itemSb);
            }
        }
        int integral = item.getTask_integral();

        StringBuilder valueIntegral = new StringBuilder();
        valueIntegral.append("+")
                .append(String.valueOf(integral))
                .append("积分");
        GradientDrawable gd = new GradientDrawable();
        switch (item.getStatus()) {
            case 1:
                //1:不可领取
                holder.mTvTryGameReward.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff8f19));
                gd.setColor(ContextCompat.getColor(mContext, R.color.transparent));
                holder.mTvTryGameReward.setEnabled(true);
                break;
            case 2:
                //2:可领取
                holder.mTvTryGameReward.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                gd.setColor(ContextCompat.getColor(mContext, R.color.color_ff8f19));
                gd.setCornerRadius(48 * density);
                holder.mTvTryGameReward.setEnabled(true);
                break;
            case 3:
                //3:已领取
                valueIntegral.append("\n")
                        .append("已领取");
                holder.mTvTryGameReward.setTextColor(ContextCompat.getColor(mContext, R.color.color_cccccc));
                gd.setColor(ContextCompat.getColor(mContext, R.color.transparent));
                holder.mTvTryGameReward.setEnabled(false);
                break;
            case 4:
                //4:已抢完
                holder.mTvTryGameReward.setTextColor(ContextCompat.getColor(mContext, R.color.color_cccccc));
                gd.setColor(ContextCompat.getColor(mContext, R.color.transparent));
                holder.mTvTryGameReward.setEnabled(true);
                break;
            default:
                break;
        }
        holder.mTvTryGameReward.setBackground(gd);
        holder.mTvTryGameReward.setText(valueIntegral);

        holder.mTvTryGameReward.setOnClickListener(v -> {
            if (_mFragment != null && _mFragment.checkLogin()) {
                if (_mFragment instanceof RewardListFragment) {
                    ((RewardListFragment) _mFragment).getRewardIntegral(item);
                }
            }
        });

    }

    public class ViewHolder extends AbsHolder {

        private TextView mTvTryGameRewardItem;
        private TextView mTvTryGameRewardCount;
        private TextView mTvTryGameReward;

        public ViewHolder(View view) {
            super(view);
            mTvTryGameRewardItem = findViewById(R.id.tv_try_game_reward_item);
            mTvTryGameRewardCount = findViewById(R.id.tv_try_game_reward_count);
            mTvTryGameReward = findViewById(R.id.tv_try_game_reward);
        }
    }
}
