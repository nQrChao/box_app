package com.zqhy.app.core.view.community.task.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.tryplay.TryGameItemVo;
import com.zqhy.app.core.view.community.task.TaskCenterFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class TaskTryGameItemHolder extends AbsItemHolder<TryGameItemVo.DataBean, TaskTryGameItemHolder.ViewHolder> {

    public TaskTryGameItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_task_try_game;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TryGameItemVo.DataBean item) {


        GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mIvTryGameIcon);

        holder.mTvTryGameName.setText("试玩《" + item.getGamename() + "》");
        String value = String.valueOf(item.getTotal());
        SpannableString ss = new SpannableString("最高奖励" + item.getTotal() + "积分/每人");
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_ff4949)),
                2, 2 + 2 + value.length() + 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.mTvTryGameReward.setText(ss);

        holder.mLlItemView.setOnClickListener(v -> {
            if (_mFragment != null && _mFragment instanceof TaskCenterFragment) {
                ((TaskCenterFragment) _mFragment).taskSubItemClick(item,position);
            }
        });
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mLlItemView;
        private ImageView mIvTryGameIcon;
        private TextView mTvTryGameName;
        private TextView mTvTryGameReward;

        public ViewHolder(View view) {
            super(view);
            mLlItemView = findViewById(R.id.ll_item_view);
            mIvTryGameIcon = findViewById(R.id.iv_try_game_icon);
            mTvTryGameName = findViewById(R.id.tv_try_game_name);
            mTvTryGameReward = findViewById(R.id.tv_try_game_reward);

        }
    }
}
