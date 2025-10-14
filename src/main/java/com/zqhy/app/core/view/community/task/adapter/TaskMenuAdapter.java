package com.zqhy.app.core.view.community.task.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqhy.app.adapter.abs.AbsAdapter;
import com.zqhy.app.core.data.model.community.task.TaskInfoVo;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 * @author leeham2734
 * @date 2022/4/1-9:50
 * @description
 */
public class TaskMenuAdapter extends AbsAdapter<TaskInfoVo> {

    public TaskMenuAdapter(Context context, List<TaskInfoVo> labels) {
        super(context, labels);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, TaskInfoVo data, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mTvTitle.setText(data.getTaskTitle());
        viewHolder.mTvTips.setText(data.getTaskTag());
        viewHolder.mIvBg.setImageResource(data.getIconRes());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_task_menu;
    }

    @Override
    public AbsViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends AbsAdapter.AbsViewHolder {

        private TextView mTvTitle;
        private TextView  mTvTips;
        private ImageView mIvBg;

        public ViewHolder(View view) {
            super(view);
            mTvTitle = view.findViewById(R.id.tv_title);
            mTvTips = view.findViewById(R.id.tv_tips);
            mIvBg = view.findViewById(R.id.iv_bg);
        }
    }
}
