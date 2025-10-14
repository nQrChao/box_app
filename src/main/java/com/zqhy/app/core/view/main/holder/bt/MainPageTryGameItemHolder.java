package com.zqhy.app.core.view.main.holder.bt;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.mainpage.MainPageTryGameVo;
import com.zqhy.app.core.data.model.tryplay.TryGameItemVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.tryplay.TryGamePlayListFragment;
import com.zqhy.app.core.view.tryplay.TryGameTaskFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.TitleTextView;

/**
 * @author leeham2734
 * @date 2020/11/24-10:34
 * @description
 */
public class MainPageTryGameItemHolder extends AbsItemHolder<MainPageTryGameVo, MainPageTryGameItemHolder.ViewHolder> {
    public MainPageTryGameItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_page_try_game;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainPageTryGameVo item) {
        holder.mTitleTextView.setText(item.getTitle());
        holder.mLlGameInfo.removeAllViews();
        for (TryGameItemVo.DataBean gameInfoVo : item.getInfoVoList()) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.rightMargin = ScreenUtil.dp2px(mContext, 3);
            holder.mLlGameInfo.addView(createItemView(mContext, gameInfoVo), params);
        }

        holder.mTvMore.setOnClickListener(v -> {
            if (_mFragment != null) {
                _mFragment.startFragment(TryGamePlayListFragment.newInstance());
            }
        });
    }


    private View createItemView(Context mContext, TryGameItemVo.DataBean gameInfoVo) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_layout_main_page_try_game, null);
        ImageView mIvIcon = itemView.findViewById(R.id.iv_icon);
        TextView mTvGameName = itemView.findViewById(R.id.tv_game_name);
        TextView mTvTryGameInfo = itemView.findViewById(R.id.tv_try_game_info);
        TextView mTvTryGame = itemView.findViewById(R.id.tv_try_game);

        GlideUtils.loadRoundImage(mContext, gameInfoVo.getGameicon(), mIvIcon);
        mTvGameName.setText(gameInfoVo.getGamename());
        mTvTryGameInfo.setText("送" + gameInfoVo.getTotal_reward() + "积分");

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(ScreenUtil.dp2px(mContext, 4));
        gd.setColor(ContextCompat.getColor(mContext, R.color.white));
        gd.setStroke(ScreenUtil.dp2px(mContext, 1), Color.parseColor("#4C34F6"));
        mTvTryGame.setBackground(gd);
        mTvTryGame.setTextColor(Color.parseColor("#4F02D5"));

        itemView.setOnClickListener(v -> {
            if (_mFragment != null) {
                _mFragment.startFragment(TryGameTaskFragment.newInstance(gameInfoVo.getTid()));
            }
        });
        return itemView;
    }


    public class ViewHolder extends AbsHolder {

        private LinearLayout  mLlGameInfo;
        private TextView      mTvMore;
        private TitleTextView mTitleTextView;

        public ViewHolder(View view) {
            super(view);
            mLlGameInfo = findViewById(R.id.ll_game_info);
            mTvMore = findViewById(R.id.tv_more);
            mTitleTextView = findViewById(R.id.title_text_view);
        }
    }
}
