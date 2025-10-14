package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.nodata.MoreGameDataVo;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.classification.GameClassificationFragment;
import com.zqhy.app.core.view.main.AbsMainGameListFragment;
import com.zqhy.app.newproject.R;

/**
 * @author pc
 * @date 2019/11/27-13:41
 * @description
 */
public class MainPagerMoreGameItemHolder extends AbsItemHolder<MoreGameDataVo, MainPagerMoreGameItemHolder.ViewHolder> {

    public MainPagerMoreGameItemHolder(Context context) {
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
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MoreGameDataVo item) {
        holder.mLlMoreGame.setOnClickListener(v -> {
            if (_mFragment != null) {
                int game_type = item.getGame_type();
                if (game_type == 1) {
                    if (_mFragment instanceof AbsMainGameListFragment) {
                        ((AbsMainGameListFragment) _mFragment).goToMainGamePage(0);
                    }
                } else {
                    FragmentHolderActivity.startFragmentInActivity(mContext, GameClassificationFragment.newInstance(String.valueOf(game_type)));
                }
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
