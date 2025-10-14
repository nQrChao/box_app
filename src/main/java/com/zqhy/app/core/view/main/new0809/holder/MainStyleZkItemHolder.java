package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.new0809.MainXingYouDataVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 * @date 2021/8/11 0011-12:08
 * @description
 */
public class MainStyleZkItemHolder extends BaseItemHolder<MainXingYouDataVo.ZuiXingShangJiaDataBeanVo, MainStyleZkItemHolder.ViewHolder> {
    public MainStyleZkItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_page_style_zk;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainXingYouDataVo.ZuiXingShangJiaDataBeanVo item) {
        if (!TextUtils.isEmpty(item.module_title)) {
            try {
                holder.mTvTitle.setText(item.module_title);
                holder.mTvTitle.setTextColor(Color.parseColor(item.module_title_color));
            } catch (Exception e) {

            }
        }
        holder.mLlContainer.removeAllViews();
        for (GameInfoVo gameInfoVo : item.data) {
            View itemView = createItemView(gameInfoVo);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (ScreenUtil.getScreenWidth(mContext) / 3.5f), LinearLayout.LayoutParams.WRAP_CONTENT);
            holder.mLlContainer.addView(itemView, params);
        }
    }

    private View createItemView(GameInfoVo gameInfoVo) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_main_page_new_game_zk, null);
        ImageView mIvGameIcon = itemView.findViewById(R.id.iv_game_icon);
        TextView mIvGameTag = itemView.findViewById(R.id.iv_game_tag);
        TextView mTvGameName = itemView.findViewById(R.id.tv_game_name);
        TextView mTvGameSuffix = itemView.findViewById(R.id.tv_game_suffix);

        GlideUtils.loadRoundImage(mContext, gameInfoVo.getGameicon(), mIvGameIcon);

        String tag = null;
        int showDiscount = gameInfoVo.showDiscount();
        if (showDiscount == 1 || showDiscount == 2) {
            if (showDiscount == 1){
                tag = gameInfoVo.getDiscount() + "折";
            }else if (showDiscount == 2){
                tag = gameInfoVo.getFlash_discount() + "折";
            }
        }
        if (TextUtils.isEmpty(tag)) {
            tag = gameInfoVo.getVip_label();
        }
        if (!TextUtils.isEmpty(tag)) {
            mIvGameTag.setVisibility(View.VISIBLE);
            mIvGameTag.setText(tag);
        } else {
            mIvGameTag.setVisibility(View.GONE);
        }

        GradientDrawable gd2 = new GradientDrawable();
        float radius = ScreenUtil.dp2px(mContext, 100);
        gd2.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius, 0, 0});
        gd2.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        gd2.setColors(new int[]{Color.parseColor("#FE6631"), Color.parseColor("#EF0F16")});
        mIvGameTag.setBackground(gd2);

        mTvGameName.setText(gameInfoVo.getGamename());

        if (!TextUtils.isEmpty(gameInfoVo.getGenre_str())){
            mTvGameSuffix.setVisibility(View.VISIBLE);
            mTvGameSuffix.setText(gameInfoVo.getGenre_str());
        }else {
            mTvGameSuffix.setVisibility(View.GONE);
        }

        itemView.setOnClickListener(v -> {
            if (_mFragment != null) {
                _mFragment.goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
            }
        });
        return itemView;
    }

    public class ViewHolder extends AbsHolder {
        private TextView     mTvTitle;
        private LinearLayout mLlContainer;

        public ViewHolder(View view) {
            super(view);
            mTvTitle = findViewById(R.id.tv_title);
            mLlContainer = findViewById(R.id.ll_container);
        }
    }
}
