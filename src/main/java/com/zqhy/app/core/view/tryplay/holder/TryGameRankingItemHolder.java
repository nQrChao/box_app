package com.zqhy.app.core.view.tryplay.holder;

import android.content.Context;
import androidx.annotation.NonNull;
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
public class TryGameRankingItemHolder extends AbsItemHolder<TryGameInfoVo.RankingListVo.DataBean, TryGameRankingItemHolder.ViewHolder> {

    public TryGameRankingItemHolder(Context context) {
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
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TryGameInfoVo.RankingListVo.DataBean item) {
        holder.mIvRanking.setVisibility(View.VISIBLE);
        holder.mTvRanking.setVisibility(View.GONE);
        if (position == 0) {
            holder.mIvRanking.setImageResource(R.mipmap.ic_try_game_ranking_1);
        } else if (position == 1) {
            holder.mIvRanking.setImageResource(R.mipmap.ic_try_game_ranking_2);
        } else if (position == 2) {
            holder.mIvRanking.setImageResource(R.mipmap.ic_try_game_ranking_3);
        } else {
            holder.mIvRanking.setVisibility(View.GONE);
            holder.mTvRanking.setVisibility(View.VISIBLE);
            holder.mTvRanking.setText(String.valueOf(position + 1));
        }

        holder.mTvTab2.setText(item.getRole_name());
        holder.mTvTab3.setText(item.getServername());
        holder.mTvTab4.setText(item.getLevel_name());
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
