package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.mainpage.AntiAddictionVo;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class GameAntiAddictionItemHolder extends AbsItemHolder<AntiAddictionVo, GameAntiAddictionItemHolder.ViewHolder> {

    public GameAntiAddictionItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_anit_addiction;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull AntiAddictionVo item) {

    }

    public class ViewHolder extends AbsHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }
}
