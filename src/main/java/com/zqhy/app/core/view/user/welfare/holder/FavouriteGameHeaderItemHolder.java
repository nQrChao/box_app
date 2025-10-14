package com.zqhy.app.core.view.user.welfare.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.welfare.FavouriteGameHeadVo;
import com.zqhy.app.newproject.R;

/**
 *
 * @author Administrator
 * @date 2018/11/15
 */

public class FavouriteGameHeaderItemHolder extends AbsItemHolder<FavouriteGameHeadVo,FavouriteGameHeaderItemHolder.ViewHolder>{
    public FavouriteGameHeaderItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull FavouriteGameHeadVo item) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_favourite_game_header;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }
}
