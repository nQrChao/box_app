package com.zqhy.app.core.view.tryplay.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.tryplay.TryGameInfoVo;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class TryGameImpactItemHolder extends AbsItemHolder<TryGameInfoVo.CompetitionInfoVo, TryGameImpactItemHolder.ViewHolder> {

    public TryGameImpactItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_try_game_impact_ranking;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TryGameInfoVo.CompetitionInfoVo item) {
        holder.mIvRanking.setVisibility(View.VISIBLE);
        holder.mTvRanking.setVisibility(View.GONE);
        if (item.getRanking() == 1) {
            holder.mIvRanking.setImageResource(R.mipmap.ic_try_game_impact_ranking_1);
        } else if (item.getRanking() == 2) {
            holder.mIvRanking.setImageResource(R.mipmap.ic_try_game_impact_ranking_2);
        } else if (item.getRanking() == 3) {
            holder.mIvRanking.setImageResource(R.mipmap.ic_try_game_impact_ranking_3);
        } else {
            holder.mIvRanking.setVisibility(View.GONE);
            holder.mTvRanking.setVisibility(View.VISIBLE);
            holder.mTvRanking.setText(String.valueOf(item.getRanking()));
        }

        holder.mTvTab2.setText(String.valueOf(item.getReward_integral()) + "积分");
        if (!TextUtils.isEmpty(item.getUsername())) {
            holder.mTvTab3.setText(hideUserName(item.getUsername()));
            holder.mTvTab3.setTextColor(ContextCompat.getColor(mContext, R.color.color_333333));
            holder.mTvTab4.setText(item.getGet_time());
            holder.mTvTab4.setTextColor(ContextCompat.getColor(mContext, R.color.color_818181));
        } else {
            holder.mTvTab3.setText("虚位以待");
            holder.mTvTab3.setTextColor(ContextCompat.getColor(mContext, R.color.color_cccccc));
            holder.mTvTab4.setText("--");
            holder.mTvTab4.setTextColor(ContextCompat.getColor(mContext, R.color.color_cccccc));
        }

    }

    private String hideUserName(String username) {
        int length = username.length();
        if (length < 3) {
            return username;
        } else if (length == 3) {
            return username.substring(0, 2) + "*";
        } else {
            String lastChar = username.substring(length - 1, length);
            return username.substring(0, 2) + "***" + lastChar;
        }
    }

    public class ViewHolder extends AbsHolder {
        private ImageView mIvRanking;
        private TextView mTvRanking;
        private TextView mTvTab2;
        private TextView mTvTab3;
        private TextView mTvTab4;

        public ViewHolder(View view) {
            super(view);

            mIvRanking = findViewById(R.id.iv_ranking);
            mTvRanking = findViewById(R.id.tv_ranking);
            mTvTab2 = findViewById(R.id.tv_tab_2);
            mTvTab3 = findViewById(R.id.tv_tab_3);
            mTvTab4 = findViewById(R.id.tv_tab_4);

        }
    }
}
