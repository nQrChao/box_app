package com.zqhy.app.core.view.main.new_game.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.new_game.NewGameTitleVo;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2020/8/24-12:19
 * @description
 */
public class NewGameStartingTitleItemHolder extends AbsItemHolder<NewGameTitleVo, NewGameStartingTitleItemHolder.ViewHolder> {
    public NewGameStartingTitleItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_new_game_starting_title;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull NewGameTitleVo item) {
        if (item.getTitle().equals("今日首发")){
            holder.mLlBg.setBackgroundResource(R.mipmap.ic_new_game_starting_title_bg_1);
        }else {
            holder.mLlBg.setBackgroundResource(R.mipmap.ic_new_game_starting_title_bg_2);
        }
        holder.mTvTitle.setText(item.getTitle());
    }


    public class ViewHolder extends AbsHolder {
        private LinearLayout mLlBg;
        private TextView mTvTitle;
        public ViewHolder(View view) {
            super(view);
            mLlBg = findViewById(R.id.ll_bg);
            mTvTitle = findViewById(R.id.tv_title);
        }
    }
}
