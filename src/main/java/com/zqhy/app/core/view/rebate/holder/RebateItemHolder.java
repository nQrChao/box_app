package com.zqhy.app.core.view.rebate.holder;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.rebate.RebateInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.rebate.ApplyRebateFragment;
import com.zqhy.app.core.view.rebate.RebateListFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class RebateItemHolder extends AbsItemHolder<RebateInfoVo, RebateItemHolder.ViewHolder> {

    private final long twoDaysSeconds = 2 * 24 * 60 * 60;

    private float density;

    public RebateItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull RebateInfoVo item) {

        GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mIvGameIcon);

        holder.mTvGameName.setText(item.getGamename());
        holder.mTvRechargeTime.setText("充值时间：" + item.getDay_time());
        holder.mTvRechargeAmount.setText("可申请金额：" + item.getUsable_total() + "元" + "（" + item.getXh_showname() + "）");

        if (item.getRest_time() > 0) {
            holder.mTvTimeRemain.setVisibility(View.VISIBLE);
            if (item.getRest_time() > twoDaysSeconds) {
                if (holder.timer != null) {
                    holder.timer.cancel();
                }
                int differTimeDay = getDifferTimeByDay(item.getRest_time() * 1000);
                holder.mTvTimeRemain.setText("还剩" + String.valueOf(differTimeDay) + "天");
            } else {
                if (holder.timer != null) {
                    holder.timer.cancel();
                }
                holder.timer = new CountDownTimer(item.getRest_time() * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        holder.mTvTimeRemain.setText(formatTime(millisUntilFinished));
                    }

                    @Override
                    public void onFinish() {
                        if (_mFragment != null) {
                            ((RebateListFragment) _mFragment).onRefresh();
                        }
                        holder.mTvTimeRemain.setVisibility(View.INVISIBLE);
                    }
                }.start();
            }
        } else {
            holder.mTvTimeRemain.setVisibility(View.INVISIBLE);
        }

        holder.mTvApplyRebate.setOnClickListener(view -> {
            if (_mFragment != null) {
                _mFragment.startForResult(ApplyRebateFragment.newInstance(item.getGame_type(), item), RebateListFragment.GAME_REBATE_APPLY);
            }
        });
        holder.mIvGameIcon.setOnClickListener(view -> {
            if (_mFragment != null) {
                _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_rebate;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    public class ViewHolder extends AbsHolder {

        private ImageView mIvGameIcon;
        private TextView mTvGameName;
        private TextView mTvRechargeTime;
        private TextView mTvRechargeAmount;
        private TextView mTvApplyRebate;
        private TextView mTvTimeRemain;

        CountDownTimer timer;

        public ViewHolder(View view) {
            super(view);
            mIvGameIcon = findViewById(R.id.iv_game_icon);
            mTvGameName = findViewById(R.id.tv_game_name);
            mTvRechargeTime = findViewById(R.id.tv_recharge_time);
            mTvRechargeAmount = findViewById(R.id.tv_recharge_amount);
            mTvApplyRebate = findViewById(R.id.tv_apply_rebate);
            mTvTimeRemain = findViewById(R.id.tv_time_remain);

            GradientDrawable gd = new GradientDrawable();
            gd.setShape(GradientDrawable.OVAL);
            gd.setStroke((int) (1 * density), ContextCompat.getColor(mContext, R.color.color_ff8f19));
            mTvApplyRebate.setBackground(gd);
        }
    }

    public int getDifferTimeByDay(long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        return (int) (ms / dd);
    }

    public String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24 * 2;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;

        StringBuffer sb = new StringBuffer();
        if (hour < 10) {
            sb.append("0");
        }
        sb.append(String.valueOf(hour)).append(":");

        if (minute < 10) {
            sb.append("0");
        }
        sb.append(String.valueOf(minute)).append(":");

        if (second < 10) {
            sb.append("0");
        }
        sb.append(String.valueOf(second));
        return sb.toString();
    }
}
