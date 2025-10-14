package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.FeedbackInfoItemVo;
import com.zqhy.app.core.data.model.game.detail.GameRefundVo;
import com.zqhy.app.core.view.refund.RefundMainFragment;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2020/5/9-14:45
 * @description
 */
public class GameFeedbackItemHolder extends AbsItemHolder<FeedbackInfoItemVo.CateBean, GameFeedbackItemHolder.ViewHolder> {
    public GameFeedbackItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_detail_feedback;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull FeedbackInfoItemVo.CateBean item) {
        holder.mTvContent.setText(item.getName());
        if (item.isSelect()){
            holder.mTvContent.setBackgroundResource(R.drawable.shape_eef2ff_4_radius_white_line_4e76ff);
        }else {
            holder.mTvContent.setBackgroundResource(R.drawable.shape_ffffff_4_radius_white_line_999999);
        }
    }

    public class ViewHolder extends AbsHolder {

        private TextView mTvContent;

        public ViewHolder(View view) {
            super(view);
            mTvContent = findViewById(R.id.tv_content);
        }
    }
}
