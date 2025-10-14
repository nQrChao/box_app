package com.zqhy.app.core.view.main.new_game.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author leeham2734
 * @date 2020/8/28-17:02
 * @description
 */
public class NewGameTopItemHolder extends NewGameStartingItemHolder {

    public NewGameTopItemHolder(Context context) {
        super(context);
    }

    public NewGameTopItemHolder(Context context, boolean isMustHideLastButton) {
        super(context, isMustHideLastButton);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_new_game_top;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull GameInfoVo item) {
        super.onBindViewHolder(viewHolder, item);
        ItemViewHolder holder = (ItemViewHolder) viewHolder;
        int ranking = item.getIndexPosition() + 1;
        if (ranking <= 3) {
            holder.mIvRanking.setVisibility(View.VISIBLE);
            holder.mTvRanking.setVisibility(View.GONE);
            switch (ranking) {
                case 1:
                    holder.mIvRanking.setImageResource(R.mipmap.ic_new_game_top_ranking_1);
                    break;
                case 2:
                    holder.mIvRanking.setImageResource(R.mipmap.ic_new_game_top_ranking_2);
                    break;
                case 3:
                    holder.mIvRanking.setImageResource(R.mipmap.ic_new_game_top_ranking_3);
                    break;
            }
        } else {
            holder.mIvRanking.setVisibility(View.GONE);
            holder.mTvRanking.setVisibility(View.VISIBLE);
            holder.mTvRanking.setText(String.valueOf(ranking));
        }
        holder.mTvPlayCount.setText(item.getGenre_str() + "    " + CommonUtils.formatNumberType2(item.getPlay_count())+"人在玩");
    }

    class ItemViewHolder extends ViewHolder {
        private ImageView mIvRanking;
        private TextView  mTvRanking;

        public ItemViewHolder(View view) {
            super(view);
            mIvRanking = findViewById(R.id.iv_ranking);
            mTvRanking = findViewById(R.id.tv_ranking);
        }
    }
}
