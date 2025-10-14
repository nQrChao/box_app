package com.zqhy.app.core.view.transaction.holder;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class SearchGameItemHolder extends AbsItemHolder<GameInfoVo, SearchGameItemHolder.ViewHolder> {

    private float density;

    public SearchGameItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_new_search_game;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameInfoVo item) {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(density * 10);
        holder.mTvGameTag.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        if (item.getGame_type() == 1) {
            holder.mTvGameTag.setText("BT手游");
            gd.setColor(ContextCompat.getColor(mContext, R.color.color_ffaa1c_bt));
        } else if (item.getGame_type() == 2) {
            holder.mTvGameTag.setText("折扣手游");
            gd.setColor(ContextCompat.getColor(mContext, R.color.color_ff7c7c_discount));
        } else if (item.getGame_type() == 3) {
            holder.mTvGameTag.setText("H5游戏");
            gd.setColor(ContextCompat.getColor(mContext, R.color.color_8fcc52_h5));
        } else if (item.getGame_type() == 4) {
            holder.mTvGameTag.setText("单机游戏");
            gd.setColor(ContextCompat.getColor(mContext, R.color.color_11a8ff_single));
        }
        holder.mTvGameTag.setBackground(gd);
        String tag = "";
        holder.mTvGameName.setText(tag + item.getGamename());

        int showDiscount = item.showDiscount();
        if (showDiscount == 1 || showDiscount == 2) {
            if (showDiscount == 1){
                holder.mTvGameTag2.setText(item.getDiscount() + "折");
            }else if (showDiscount == 2){
                holder.mTvGameTag2.setText(item.getFlash_discount() + "折");
            }
        } else {
            holder.mTvGameTag2.setText("");
        }

        //2018.05.18 折扣游戏 折扣=10折  标签改为手机游戏
        try {
            if (item.getGame_type() == 2 && item.getDiscount() == 10) {
                holder.mTvGameTag.setText("手机游戏");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ViewHolder extends AbsHolder {

        private LinearLayout mRootView;
        private TextView mTvGameName;
        private TextView mTvGameTag;
        private TextView mTvGameTag2;
        private View mViewLine;

        public ViewHolder(View view) {
            super(view);

            mRootView = findViewById(R.id.rootView);
            mTvGameName = findViewById(R.id.tv_game_name);
            mTvGameTag = findViewById(R.id.tv_game_tag);
            mTvGameTag2 = findViewById(R.id.tv_game_tag_2);
            mViewLine = findViewById(R.id.view_line);

        }
    }
}
