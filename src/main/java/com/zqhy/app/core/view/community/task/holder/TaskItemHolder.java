package com.zqhy.app.core.view.community.task.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.community.task.TaskInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.community.task.TaskCenterFragment;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class TaskItemHolder extends AbsItemHolder<TaskInfoVo, TaskItemHolder.ViewHolder> {


    private float density;

    public TaskItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_task_center;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TaskInfoVo item) {
        try {
            holder.mIvTaskIcon.setImageResource(item.getIconRes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.mTvTaskName.setText(item.getTaskTitle());
        holder.mTvTaskDescription.setText(item.getTaskDescription());

        holder.mTvItemTag.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(item.getTaskTag())) {
            holder.mTvItemTag.setVisibility(View.VISIBLE);
            holder.mTvItemTag.setText(item.getTaskTag());
            holder.mTvItemTag.setTextColor(Color.parseColor("#E51C23"));

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(3 * density);
            gd.setStroke((int) (1 * density), Color.parseColor("#E51C23"));

            holder.mTvItemTag.setBackground(gd);

            int leftPadding = (int) (4 * density);
            int topPadding = (int) (1 * density);
            holder.mTvItemTag.setTextSize(11);
            holder.mTvItemTag.setPadding(leftPadding, topPadding, leftPadding, topPadding);
        }

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(density * 48);
        switch (item.getTaskStatus()) {
            case -1:
                //参与
                gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                gd.setColors(new int[]{ContextCompat.getColor(mContext, R.color.color_ff9900), ContextCompat.getColor(mContext, R.color.color_ff4e00)});
                holder.mTvTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                holder.mTvTaskStatus.setText("参与");
                break;
            case 0:
                //未完成
                gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                gd.setColors(new int[]{Color.parseColor("#FE3764"), Color.parseColor("#FE994B")});
                holder.mTvTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                holder.mTvTaskStatus.setText("待完成");
                break;
            case 1:
                //已完成
                gd.setColor(ContextCompat.getColor(mContext, R.color.color_cccccc));
                holder.mTvTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                holder.mTvTaskStatus.setText("已完成");
                break;
            default:
                break;
        }
        holder.mTvTaskStatus.setBackground(gd);

        holder.mLlItemView.setOnClickListener(v -> {
            if (_mFragment != null && _mFragment instanceof TaskCenterFragment) {
                ((TaskCenterFragment) _mFragment).taskItemClick(item);
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        RecyclerView.LayoutParams clp = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
        if (clp instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) clp).setFullSpan(true);
        }
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mLlItemView;
        private ImageView    mIvTaskIcon;
        private TextView     mTvTaskName;
        private TextView     mTvItemTag;
        private TextView     mTvTaskDescription;
        private TextView     mTvTaskStatus;

        public ViewHolder(View view) {
            super(view);
            mLlItemView = findViewById(R.id.ll_item_view);
            mIvTaskIcon = findViewById(R.id.iv_task_icon);
            mTvTaskName = findViewById(R.id.tv_task_name);
            mTvItemTag = findViewById(R.id.tv_item_tag);
            mTvTaskDescription = findViewById(R.id.tv_task_description);
            mTvTaskStatus = findViewById(R.id.tv_task_status);

        }
    }
}
