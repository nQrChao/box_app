package com.zqhy.app.core.view.tryplay.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.tryplay.TryGameHeaderVo;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2020/10/30-13:41
 * @description
 */
public class TryGameHeaderItemHolder extends AbsItemHolder<TryGameHeaderVo, TryGameHeaderItemHolder.ViewHolder> {
    public TryGameHeaderItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_try_game_header;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TryGameHeaderVo item) {

    }

    public class ViewHolder extends AbsHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }
}
