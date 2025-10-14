package com.zqhy.app.core.view.main.new_game.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.new_game.NewGameMoreVo;
import com.zqhy.app.core.view.main.MainGameFragment;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2020/9/4-10:03
 * @description
 */
public class NewGameMoreItemHolder extends AbsItemHolder<NewGameMoreVo, NewGameMoreItemHolder.ViewHolder> {
    public NewGameMoreItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_more_game;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull NewGameMoreVo item) {
        holder.mLlMoreGame.setOnClickListener(v -> {
            if (_mFragment != null) {
                _mFragment.startFragment(MainGameFragment.newInstance(1, -2, true));
            }
        });
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mLlMoreGame;

        public ViewHolder(View view) {
            super(view);
            mLlMoreGame = findViewById(R.id.ll_more_game);
        }
    }
}
