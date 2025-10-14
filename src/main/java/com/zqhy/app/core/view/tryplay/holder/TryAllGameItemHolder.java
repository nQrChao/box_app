package com.zqhy.app.core.view.tryplay.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.tryplay.TryGameItemVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.tryplay.TryGamePlayListFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 */
public class TryAllGameItemHolder extends AbsItemHolder<TryGameItemVo.DataBean, TryAllGameItemHolder.ViewHolder> {

    private float density;

    public TryAllGameItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_try_game_all;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TryGameItemVo.DataBean item) {
        GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mIvTryGameIcon);
        holder.mTvTryGameName.setText(item.getGamename());

        long ms = Long.parseLong(item.getEndtime()) * 1000;
        holder.mTvTryGameEndTime.setText("试玩截止至" + CommonUtils.formatTimeStamp(ms, "MM月dd日"));
        holder.mTvTryGameIntegral.setText(item.getTotal_reward() + "积分");

        if (!TextUtils.isEmpty(item.getOtherGameName())){//游戏后缀
            holder.mTvGameSuffix.setVisibility(View.VISIBLE);
            holder.mTvGameSuffix.setText(item.getOtherGameName());
        }else {
            holder.mTvGameSuffix.setVisibility(View.GONE);
        }

        if (item.isJoinInTryGame()) {
            GradientDrawable gd = new GradientDrawable();
            float radius = density * 48;
            gd.setCornerRadius(radius);
            gd.setStroke((int) (1 * density), ContextCompat.getColor(mContext, R.color.color_fe4061));
            holder.mTvTryGameStatus.setBackground(gd);
            holder.mTvTryGameStatus.setText("试玩中");
            holder.mTvTryGameStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_fe4061));
        } else if (item.isTryGameOverdue()) {
            GradientDrawable gd = new GradientDrawable();
            float radius = density * 48;
            gd.setCornerRadius(radius);
            gd.setColor(Color.parseColor("#EFECEC"));
            holder.mTvTryGameStatus.setBackground(gd);
            holder.mTvTryGameStatus.setText("已结束");
            holder.mTvTryGameStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_919191));
        } else {
            GradientDrawable gd = new GradientDrawable();
            float radius = density * 48;
            gd.setCornerRadius(radius);
            gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            gd.setColors(new int[]{Color.parseColor("#FE964C"), Color.parseColor("#FE3D62")});
            holder.mTvTryGameStatus.setBackground(gd);
            holder.mTvTryGameStatus.setText("参加");
            holder.mTvTryGameStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        }

        //        if (item.getStatus() == 2) {
        //            GradientDrawable gd = new GradientDrawable();
        //            float radius = density * 48;
        //            gd.setCornerRadius(radius);
        //            gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        //            gd.setColors(new int[]{Color.parseColor("#FE964C"), Color.parseColor("#FE3D62")});
        //            holder.mTvTryGameStatus.setBackground(gd);
        //            holder.mTvTryGameStatus.setText("参加");
        //            holder.mTvTryGameStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        //        } else if (item.getStatus() == 3 || item.getStatus() == 4) {
        //            GradientDrawable gd = new GradientDrawable();
        //            float radius = density * 48;
        //            gd.setCornerRadius(radius);
        //            gd.setColor(ContextCompat.getColor(mContext, R.color.white));
        //            gd.setStroke((int) (1 * density), Color.parseColor("#FE4061"));
        //            holder.mTvTryGameStatus.setBackground(gd);
        //            holder.mTvTryGameStatus.setText("参加");
        //            holder.mTvTryGameStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        //        } else if (item.getStatus() == 5) {
        //            GradientDrawable gd = new GradientDrawable();
        //            float radius = density * 48;
        //            gd.setCornerRadius(radius);
        //            gd.setColor(ContextCompat.getColor(mContext, R.color.color_efecec));
        //            holder.mTvTryGameStatus.setBackground(gd);
        //            holder.mTvTryGameStatus.setText("已结束");
        //            holder.mTvTryGameStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_919191));
        //        }
        //        GradientDrawable gd = new GradientDrawable();
        //        float radius = density * 48;
        //        gd.setCornerRadii(new float[]{0, 0, radius, radius, radius, radius, 0, 0});
        //        gd.setColor(ContextCompat.getColor(mContext, R.color.color_f8f8f8));
        //        holder.mLlTryGameTime.setBackground(gd);
        //        holder.mTvCountDownTime.setVisibility(View.GONE);
        //        holder.mTvTryGameTime.setTag(R.id.tag_second, item);

        startTimer(holder);

        holder.itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {

            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                releaseTimer(holder);
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        startTimer(holder);
    }


    /**
     * 开始Timer
     *
     * @param holder
     */
    private void startTimer(@NonNull ViewHolder holder) {
        /*releaseTimer(holder);
        TryGameItemVo.DataBean item = (TryGameItemVo.DataBean) holder.mTvTryGameTime.getTag(R.id.tag_second);
        if (item.getStatus() == 1) {
            holder.mTvTryGameTime.setText("未开启");
        } else if (item.getStatus() == 2) {
            holder.mTvTryGameTime.setText("即将开启：" + formatTime(item.getEndTime() - System.currentTimeMillis()));
            holder.timer = new CountDownTimerCopyFromAPI26(item.getEndTime() - System.currentTimeMillis(), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    holder.mTvTryGameTime.setText("即将开启：" + formatTime(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    holder.mTvTryGameTime.setText("试玩中：" + item.getEndtime() + "截止");
                }
            }.start();
           Logs.e("startTime = " + holder);
        } else if (item.getStatus() == 3) {
            holder.mTvTryGameTime.setText("试玩中：" + item.getEndtime() + "截止");
        } else if (item.getStatus() == 4) {
            holder.mTvTryGameTime.setText("即将结束：" + formatTime(item.getEndTime() - System.currentTimeMillis()));
            holder.timer = new CountDownTimerCopyFromAPI26(item.getEndTime() - System.currentTimeMillis(), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    holder.mTvTryGameTime.setText("即将结束：" + formatTime(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    holder.mTvTryGameTime.setText("试玩已结束");
                }
            }.start();
           Logs.e("startTime = " + holder);
        } else if (item.getStatus() == 5) {
            holder.mTvTryGameTime.setText("试玩已结束");
        }
        holder.mTvTryGameTime.setTag(R.id.tag_first, holder.timer);*/
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        releaseTimer(holder);
    }


    /**
     * 释放Timer
     *
     * @param holder
     */
    private void releaseTimer(@NonNull ViewHolder holder) {
        //        if (holder.timer != null) {
        //            holder.timer.cancel();
        //            holder.timer = null;
        //        }
    }

    private void refreshListData() {
        if (_mFragment != null && _mFragment instanceof TryGamePlayListFragment) {
            ((TryGamePlayListFragment) _mFragment).refreshListData();
        }
    }

    private String formatTime(Long ms) {
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

    public class ViewHolder extends AbsHolder {

        //        private TextView  mTvTryGameTime;
        private ImageView mIvTryGameIcon;
        private TextView  mTvTryGameName;
        private TextView  mTvTryGameEndTime;
        private TextView  mTvTryGameIntegral;
        private TextView  mTvTryGameStatus;
        private TextView mTvGameSuffix;

        //        private LinearLayout mLlTryGameTime;
        //        private CountdownView mTvCountDownTime;
        //
        //        CountDownTimerCopyFromAPI26 timer;

        public ViewHolder(View view) {
            super(view);
            //            mTvTryGameTime = findViewById(R.id.tv_try_game_time);
            mIvTryGameIcon = findViewById(R.id.iv_try_game_icon);
            mTvTryGameName = findViewById(R.id.tv_try_game_name);
            mTvTryGameEndTime = findViewById(R.id.tv_try_game_end_time);
            mTvTryGameIntegral = findViewById(R.id.tv_try_game_integral);

            //            mLlTryGameTime = findViewById(R.id.ll_try_game_time);
            //            mTvCountDownTime = findViewById(R.id.tv_count_down_time);


            mTvTryGameStatus = findViewById(R.id.tv_try_game_status);
            mTvGameSuffix = view.findViewById(R.id.tv_game_suffix);
        }

        /*public void refreshTime(long leftTime) {
            if (leftTime > 0) {
                mTvCountDownTime.start(leftTime);
                mTvCountDownTime.setVisibility(View.VISIBLE);
            } else {
                mTvCountDownTime.stop();
                mTvCountDownTime.setVisibility(View.GONE);
                mTvCountDownTime.allShowZero();
            }
        }*/
    }
}
