package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.tryplay.TryGameItemVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.tryplay.TryGameTaskFragment;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class GameTryItemHolder extends AbsItemHolder<TryGameItemVo.DataBean, GameTryItemHolder.ViewHolder> {

    public GameTryItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_detail_try_game;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TryGameItemVo.DataBean item) {
        StringBuilder sb = new StringBuilder();
        String total = String.valueOf(item.getTotal());
        sb.append("玩游戏，最高奖励")
                .append(total)
                .append("积分/每人");

        SpannableString ss = new SpannableString(sb.toString());
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_ff6c6c)),
                8, 8 + total.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        holder.mTvTryGameReward.setText(ss);

        holder.mLlTryGame.setOnClickListener(v -> {
            if (_mFragment != null) {
                _mFragment.start(TryGameTaskFragment.newInstance(item.getTid()));
            }
        });
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mLlTryGame;
        private TextView mTvTryGameReward;
        private TextView mTvTryGameRewardTag;


        public ViewHolder(View view) {
            super(view);
            mLlTryGame = findViewById(R.id.ll_try_game);
            mTvTryGameReward = findViewById(R.id.tv_try_game_reward);
            mTvTryGameRewardTag = findViewById(R.id.tv_try_game_reward_tag);

            GradientDrawable gd = new GradientDrawable();
            gd.setColor(ContextCompat.getColor(mContext, R.color.color_9487fb));
            gd.setCornerRadius(24 * ScreenUtil.getScreenDensity(mContext));
            mTvTryGameRewardTag.setBackground(gd);
            mTvTryGameRewardTag.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        }
    }
}
