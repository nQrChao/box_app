package com.zqhy.app.core.view.user.newvip.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.user.newvip.DayRrewardListInfoVo;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class DayRewardItemHolder extends AbsItemHolder<DayRrewardListInfoVo.RewardVo, DayRewardItemHolder.ViewHolder> {
    public DayRewardItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull DayRrewardListInfoVo.RewardVo item) {
        holder.mTvWeek.setText("周" + item.getWeek_label());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);
        BaseRecyclerAdapter mAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(DayRrewardListInfoVo.DayRewardVo.class, new RewardItemHolder(mContext, item.isToday(), item.isNotTo()))
                .build().setTag(R.id.tag_fragment, _mFragment)
                .setTag(R.id.tag_fragment, _mFragment);
        holder.mRecyclerView.setAdapter(mAdapter);
        mAdapter.setDatas(item.getReward_list());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_day_reward;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private TextView mTvWeek;
        private RecyclerView mRecyclerView;

        public ViewHolder(View view) {
            super(view);
            mTvWeek = itemView.findViewById(R.id.tv_week);
            mRecyclerView = itemView.findViewById(R.id.recycler_view);
        }
    }
}
