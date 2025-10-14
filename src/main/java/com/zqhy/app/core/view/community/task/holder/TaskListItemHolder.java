package com.zqhy.app.core.view.community.task.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.community.task.TaskItemVo;
import com.zqhy.app.core.tool.MResource;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.community.task.NewbieTaskListFragment;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class TaskListItemHolder extends AbsItemHolder<TaskItemVo.DataBean, TaskListItemHolder.ViewHolder> {

    private float density;

    public TaskListItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_task_info;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TaskItemVo.DataBean taskInfoBean) {
        if(position == listSize - 1){
            holder.mViewLine.setVisibility(View.GONE);
        }else{
            holder.mViewLine.setVisibility(View.VISIBLE);
        }

        try {
            int resId = MResource.getResourceId(mContext, "drawable", taskInfoBean.getTask_icon());
            if (resId != 0) {
                holder.mIvTaskIcon.setImageResource(resId);
            }
            holder.mTvTaskTitle.setText(taskInfoBean.getTask_name());
            holder.mTvTaskSubTitle.setText(taskInfoBean.getDescription());

            StringBuilder sb = new StringBuilder();

            String taskCompleted = String.valueOf(taskInfoBean.getFinished_count());
            String taskTotal = String.valueOf(taskInfoBean.getTask_count());

            sb.append("(").append(taskCompleted).append("/").append(taskTotal).append(")");

            SpannableString spannableString = new SpannableString(sb.toString());
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FF0005"));
            spannableString.setSpan(colorSpan, 1, 1 + taskCompleted.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            holder.mTvTaskProgress.setMovementMethod(LinkMovementMethod.getInstance());
            holder.mTvTaskProgress.setText(spannableString);


            GradientDrawable gd1 = new GradientDrawable();
            gd1.setCornerRadius(12 * density);
            gd1.setColor(ContextCompat.getColor(mContext, R.color.color_fff4e8));
            holder.mFlTaskIntegral.setBackground(gd1);
            holder.mTvTaskIntegralCount.setText("+" + taskInfoBean.getReward_integral());


            GradientDrawable gd2 = new GradientDrawable();
            gd2.setCornerRadius(12 * density);

            int mainColor = ContextCompat.getColor(mContext, R.color.color_ff8f19);

            if (taskInfoBean.getTask_status() == 0) {
                //未完成
                gd2.setColor(ContextCompat.getColor(mContext, R.color.white));
                gd2.setStroke((int) (1 * density), mainColor);
                holder.mTvTaskStatus.setTextColor(mainColor);
                holder.mTvTaskStatus.setText("进行中");
                holder.mTvTaskStatus.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            } else if (taskInfoBean.getTask_status() == 1) {
                //已完成（待领取）
                gd2.setColor(mainColor);
                gd2.setStroke((int) (0 * density), mainColor);
                holder.mTvTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                holder.mTvTaskStatus.setText("领取");
                holder.mTvTaskStatus.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_task_complete), null, null, null);
            } else if (taskInfoBean.getTask_status() == 10) {
                // 已完成（已领取）
                gd2.setColor(ContextCompat.getColor(mContext, R.color.transparent));
                holder.mTvTaskStatus.setText("");
                holder.mTvTaskStatus.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_task_complete_all), null, null, null);
            }
            holder.mFlTaskStatus.setBackground(gd2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.mFlTaskStatus.setOnClickListener(v -> {
            if(_mFragment != null && _mFragment instanceof NewbieTaskListFragment){
                ((NewbieTaskListFragment)_mFragment).actionTask(taskInfoBean);
            }
        });
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mLlRootview;
        private ImageView mIvTaskIcon;
        private TextView mTvTaskTitle;
        private TextView mTvTaskProgress;
        private TextView mTvTaskTimeLimit;
        private TextView mTvTaskSubTitle;
        private FrameLayout mFlTaskStatus;
        private TextView mTvTaskStatus;
        private TextView mTvTaskLimitTimeCountDown;
        private FrameLayout mFlTaskIntegral;
        private TextView mTvTaskIntegral;
        private TextView mTvTaskIntegralCount;
        private View mViewLine;


        public ViewHolder(View view) {
            super(view);
            mLlRootview = findViewById(R.id.ll_rootview);
            mIvTaskIcon = findViewById(R.id.iv_task_icon);
            mTvTaskTitle = findViewById(R.id.tv_task_title);
            mTvTaskProgress = findViewById(R.id.tv_task_progress);
            mTvTaskTimeLimit = findViewById(R.id.tv_task_time_limit);
            mTvTaskSubTitle = findViewById(R.id.tv_task_sub_title);
            mFlTaskStatus = findViewById(R.id.fl_task_status);
            mTvTaskStatus = findViewById(R.id.tv_task_status);
            mTvTaskLimitTimeCountDown = findViewById(R.id.tv_task_limit_time_count_down);
            mFlTaskIntegral = findViewById(R.id.fl_task_integral);
            mTvTaskIntegral = findViewById(R.id.tv_task_integral);
            mTvTaskIntegralCount = findViewById(R.id.tv_task_integral_count);
            mViewLine = findViewById(R.id.view_line);

        }
    }
}
