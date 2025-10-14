package com.zqhy.app.core.view.community.user.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;

import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.view.main.holder.GameNormalItemHolder;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class GameFootPrintItemHolder extends GameNormalItemHolder {

    public GameFootPrintItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_favourite_game;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull GameNormalItemHolder.ViewHolder viewHolder, @NonNull GameInfoVo gameInfoVo) {
        super.onBindViewHolder(viewHolder, gameInfoVo);

    }

    public class ViewHolder extends GameNormalItemHolder.ViewHolder {

        public ViewHolder(View view) {
            super(view);

        }
    }
}
