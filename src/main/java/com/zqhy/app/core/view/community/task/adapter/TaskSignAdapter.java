package com.zqhy.app.core.view.community.task.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqhy.app.adapter.abs.AbsAdapter;
import com.zqhy.app.core.data.model.community.task.TaskSignInfoV2Vo;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 * @author leeham2734
 * @date 2022/4/1-9:50
 * @description
 */
public class TaskSignAdapter extends AbsAdapter<TaskSignInfoV2Vo.DataBean.SignListBean> {

    public TaskSignAdapter(Context context, List<TaskSignInfoV2Vo.DataBean.SignListBean> labels) {
        super(context, labels);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, TaskSignInfoV2Vo.DataBean.SignListBean data, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if(data.isIs_signed()){
            viewHolder.mIvStatus.setImageResource(R.mipmap.ic_task_sign_signed);
        }else {
            viewHolder.mIvStatus.setImageResource(R.mipmap.ic_task_sign_unsign);
        }
        if (data.getReward_integral() > 0){
            viewHolder.mTvReward.setVisibility(View.VISIBLE);
            viewHolder.mTvReward.setText("+" + data.getReward_integral());
        }else {
            viewHolder.mTvReward.setVisibility(View.GONE);
        }
        viewHolder.mTvDay.setText(data .getWeek_num() + "天");
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_task_sign;
    }

    @Override
    public AbsViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends AbsViewHolder {

        private TextView mTvReward;
        private ImageView mIvStatus;
        private TextView mTvDay;

        public ViewHolder(View view) {
            super(view);
            mTvReward = view.findViewById(R.id.tv_sign_reward);
            mIvStatus = view.findViewById(R.id.iv_sign_status);
            mTvDay = view.findViewById(R.id.tv_day);
        }
    }
}
