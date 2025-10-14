package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.mainpage.TopGameListVo;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 * @author pc
 * @date 2019/12/16-15:40
 * @description
 */
public class MainRankingItemHolder extends BaseItemHolder<TopGameListVo, MainRankingItemHolder.ViewHolder> {

    public MainRankingItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_ranking;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TopGameListVo item) {
        holder.mLlContainer.removeAllViews();
        for (GameInfoVo gameInfoVo : item.getGameInfoVos()) {
            View indexView = createIndexView(gameInfoVo);
            LinearLayout.LayoutParams params;
            if (indexView.getLayoutParams() != null && indexView.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                params = (LinearLayout.LayoutParams) indexView.getLayoutParams();
            } else {
                params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER_HORIZONTAL;
            }
            params.weight = 1;
            holder.mLlContainer.addView(indexView, params);
        }
    }


    private View createIndexView(GameInfoVo gameInfoVo) {
        View indexView = LayoutInflater.from(mContext).inflate(R.layout.layout_item_main_ranking_index, null);
        AppCompatImageView mIvImage = indexView.findViewById(R.id.iv_image);
        TextView mTvIndex = indexView.findViewById(R.id.tv_index);
        TextView mTvName = indexView.findViewById(R.id.tv_name);
        TextView mTvTag = indexView.findViewById(R.id.tv_tag);

        GlideUtils.loadRoundImage(mContext, gameInfoVo.getGameicon(), mIvImage);
        mTvIndex.setText(String.valueOf(gameInfoVo.getIndexPosition() + 1));
        mTvName.setText(gameInfoVo.getGamename());
        mTvTag.setText(gameInfoVo.getRanking_label());


        indexView.setOnClickListener(v -> {
            if (_mFragment != null) {
                _mFragment.goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
            }
        });
        return indexView;
    }


    public class ViewHolder extends AbsHolder {
        private LinearLayout mLlContainer;

        public ViewHolder(View view) {
            super(view);
            mLlContainer = findViewById(R.id.ll_container);

        }
    }
}
