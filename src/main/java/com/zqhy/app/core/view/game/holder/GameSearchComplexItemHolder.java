package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.KeyboardUtils;
import com.zqhy.app.core.view.main.holder.GameNormalItemHolder;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class GameSearchComplexItemHolder extends GameNormalItemHolder {

    private float density;

    public GameSearchComplexItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_search_complex;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull GameNormalItemHolder.ViewHolder holder, @NonNull GameInfoVo item) {
        super.onBindViewHolder(holder, item);

        if (item.isOffline()) {
            holder.rvGameLabels.setVisibility(View.GONE);
            //holder.mFlexBoxLayout.setVisibility(View.GONE);
            holder.mTvInfoBottom.setVisibility(View.VISIBLE);
            holder.mTvInfoBottom.setText("即将下架");
            holder.mTvInfoBottom.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff5400));
        }else {

        }

        holder.mLlRootview.setOnClickListener(v -> {
            if (_mFragment != null) {
                _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
                KeyboardUtils.hideSoftInput(_mFragment.getActivity());
            }
        });
    }

    public class ViewHolder extends GameNormalItemHolder.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }
}
