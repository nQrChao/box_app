package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.mainpage.H5PlayedVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/26
 */

public class GameH5PlayedItemHolder extends AbsItemHolder<H5PlayedVo, GameH5PlayedItemHolder.ViewHolder> {

    private float density;

    public GameH5PlayedItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull H5PlayedVo item) {
        holder.mLlH5PlayNewList.removeAllViews();
        if (item == null || item.getData() == null || item.getData().isEmpty()) {
            holder.mLlMainHeaderH5PlayNew.setVisibility(View.GONE);
            return;
        }
        holder.mLlMainHeaderH5PlayNew.setVisibility(View.VISIBLE);

        int totalWidth = ScreenUtils.getScreenWidth(mContext);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(totalWidth, FrameLayout.LayoutParams.FILL_PARENT);
        holder.mLlH5PlayNewList.setLayoutParams(params);

        for (int i = 0; i < item.getData().size(); i++) {
            GameInfoVo gameInfoBean = item.getData().get(i);
            LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            childParams.gravity = Gravity.CENTER;

            int viewMarginTop = (int) (5 * density);
            int viewMarginBottom = (int) (5 * density);
            int viewMarginRight = (int) (8 * density);
            int viewMarginLeft = i == 0 ? (int) (5 * density) : 0;
            childParams.setMargins(viewMarginLeft, viewMarginTop, viewMarginRight, viewMarginBottom);

            holder.mLlH5PlayNewList.addView(createPlayedH5View(gameInfoBean), childParams);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_h5_played;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout         mLlMainHeaderH5PlayNew;
        private HorizontalScrollView mHsH5PlayNewList;
        private LinearLayout         mLlH5PlayNewList;

        public ViewHolder(View view) {
            super(view);
            mLlMainHeaderH5PlayNew = findViewById(R.id.ll_main_header_h5_play_new);
            mHsH5PlayNewList = findViewById(R.id.hs_h5_play_new_list);
            mLlH5PlayNewList = findViewById(R.id.ll_h5_play_new_list);

        }
    }

    private View createPlayedH5View(GameInfoVo gameInfoBean) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_palyed_h5_game, null);

        LinearLayout mLlItem = view.findViewById(R.id.ll_item);
        ImageView mGameIconIV = view.findViewById(R.id.gameIconIV);
        TextView mTvGameName = view.findViewById(R.id.tv_game_name);

        if (gameInfoBean != null) {
            GlideUtils.loadGameIcon(mContext, gameInfoBean.getGameicon(), mGameIconIV);
            mTvGameName.setText(gameInfoBean.getGamename());

            mLlItem.setOnClickListener(v -> {
                //跳转-游戏详情页
                if (_mFragment != null) {
                    _mFragment.goGameDetail(gameInfoBean.getGameid(), gameInfoBean.getGame_type());
                }
            });
        }

        return view;
    }
}
