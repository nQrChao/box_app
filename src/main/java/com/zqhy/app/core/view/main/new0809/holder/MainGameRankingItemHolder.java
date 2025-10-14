package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.main.holder.GameNormalItemHolder;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2021/8/14 0014-18:28
 * @description
 */
public class MainGameRankingItemHolder extends GameNormalItemHolder {

    public MainGameRankingItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_game_ranking;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameNormalItemHolder.ViewHolder viewHolder, @NonNull GameInfoVo gameInfoVo) {
        super.onBindViewHolder(viewHolder, gameInfoVo);
        int position = gameInfoVo.getIndexPosition();
        ViewHolder holder = (ViewHolder) viewHolder;
        // 15 * 24 * 60 * 60 * 1000 = 1,296,000,000
        if (System.currentTimeMillis() - gameInfoVo.getOnline_time() * 1000 < 1296000000) {
            holder.mTvRankingTitle.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_main_game_ranking_type_2), null, null, null);
        } else {
            holder.mTvRankingTitle.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_main_game_ranking_type_1), null, null, null);
        }
        holder.mTvRankingTitle.setText(gameInfoVo.getGame_summary());
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("NO.");
        builder.setSpan(new AbsoluteSizeSpan(ScreenUtil.dp2px(mContext, 10)), 0, builder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        int startIndex1 = builder.length();
        builder.append(String.valueOf(position));
        int endIndex1 = builder.length();
        builder.setSpan(new AbsoluteSizeSpan(ScreenUtil.dp2px(mContext, 16)), startIndex1, endIndex1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        builder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startIndex1, endIndex1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.mTvRanking.setText(builder);

    }

    public class ViewHolder extends GameNormalItemHolder.ViewHolder {
        private TextView mTvRankingTitle;
        private TextView mTvRanking;

        public ViewHolder(View view) {
            super(view);
            mTvRankingTitle = findViewById(R.id.tv_ranking_title);
            mTvRanking = findViewById(R.id.tv_ranking);

        }
    }
}
