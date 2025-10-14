package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.view.main.holder.GameNormalItemHolder;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2021/8/11 0011-10:59
 * @description
 */
public class TagGameItemHolder1 extends GameNormalItemHolder {

    boolean isTag;

    public TagGameItemHolder1(Context context, boolean isTag) {
        super(context, isTag ? 38 : 0);
        leftSidleWidthDP = 50;
        this.isTag = isTag;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_normal_tag1;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameNormalItemHolder.ViewHolder viewHolder, @NonNull GameInfoVo gameInfoVo) {
        super.onBindViewHolder(viewHolder, gameInfoVo);
        ((ViewHolder) viewHolder).mIvTag.setVisibility(isTag ? View.VISIBLE : View.GONE);
    }

    public class ViewHolder extends GameNormalItemHolder.ViewHolder {
        private ImageView mIvTag;

        public ViewHolder(View view) {
            super(view);
            mIvTag = findViewById(R.id.iv_tag);
        }
    }

}
