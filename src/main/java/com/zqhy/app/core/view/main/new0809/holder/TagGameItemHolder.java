package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import androidx.annotation.NonNull;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.view.main.holder.GameNormalItemHolder;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 * @date 2021/8/11 0011-10:59
 * @description
 */
public class TagGameItemHolder extends GameNormalItemHolder {

    boolean isTag;

    public TagGameItemHolder(Context context, boolean isTag) {
        super(context, isTag ? 38 : 0);
        this.isTag = isTag;
    }

    private boolean showDate = false;
    public TagGameItemHolder(Context context, boolean isTag, boolean showDate) {
        super(context, isTag ? 38 : 0);
        this.isTag = isTag;
        this.showDate = showDate;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_normal_tag;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameNormalItemHolder.ViewHolder viewHolder, @NonNull GameInfoVo gameInfoVo) {
        super.onBindViewHolder(viewHolder, gameInfoVo);
        ((ViewHolder) viewHolder).mIvTag.setVisibility(isTag ? View.VISIBLE : View.GONE);

        if (showDate){
            if (gameInfoVo.getIs_first() == 1){
                viewHolder.mTvGameFirstTag.setVisibility(View.VISIBLE);
            }else {
                viewHolder.mTvGameFirstTag.setVisibility(View.GONE);
            }
            viewHolder.mLlGameReserveTag.setVisibility(View.GONE);
            if (gameInfoVo.getPlay_count() > 0){
                viewHolder.mTvPlayCount.setVisibility(View.VISIBLE);
                viewHolder.mTvPlayCount.setText(CommonUtils.formatTimeStamp(gameInfoVo.getOnline_time() * 1000, "MM月dd日上线"));
                viewHolder.mTvPlayCount.setTextColor(Color.parseColor("#4E76FF"));
            }else {
                viewHolder.mTvPlayCount.setVisibility(View.GONE);
            }
        }
    }

    public class ViewHolder extends GameNormalItemHolder.ViewHolder {
        private ImageView mIvTag;

        public ViewHolder(View view) {
            super(view);
            mIvTag = findViewById(R.id.iv_tag);
        }
    }

}
