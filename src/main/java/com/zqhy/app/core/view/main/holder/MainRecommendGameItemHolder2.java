package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.mainpage.MainGameRecommendVo;
import com.zqhy.app.core.data.model.mainpage.recommend.MainGameRecommendItemVo2;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2021/6/7-11:01
 * @description
 */
public class MainRecommendGameItemHolder2 extends BaseItemHolder<MainGameRecommendItemVo2, MainRecommendGameItemHolder2.ViewHolder> {
    public MainRecommendGameItemHolder2(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_game_recommend_2;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainGameRecommendItemVo2 item) {
        try {
            holder.mFlexGameContent.removeAllViews();

            if (item.getBean() != null && item.getBean().getData() != null && item.getBean().getData().size() > 0) {
                holder.mFlexGameContent.setVisibility(View.VISIBLE);
                for (MainGameRecommendVo.GameDataVo gameDataVo : item.getBean().getData()) {
                    View itemView = createGameItemView(gameDataVo);
                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.topMargin = ScreenUtil.dp2px(mContext, 4);
                    params.bottomMargin = ScreenUtil.dp2px(mContext, 12);
                    holder.mFlexGameContent.addView(itemView, params);
                }
            } else {
                holder.mFlexGameContent.setVisibility(View.GONE);
            }
            holder.mTvTitle.setText(item.getBean().getTitle());
            holder.mTvTitle.setTextColor(Color.parseColor(item.getBean().getTitle_color()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private View createGameItemView(MainGameRecommendVo.GameDataVo gameDataVo) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_item_main_game_data_1, null);
        ImageView mIvGameIcon = itemView.findViewById(R.id.iv_game_icon);
        TextView mTvGameName = itemView.findViewById(R.id.tv_game_name);
        TextView mTvGameCoin = itemView.findViewById(R.id.tv_game_coin);
        TextView mTvGameVip = itemView.findViewById(R.id.tv_game_vip);

        GlideUtils.loadRoundImage(mContext, gameDataVo.getGameicon(), mIvGameIcon);
        mTvGameName.setText(gameDataVo.getGamename());
        mTvGameCoin.setText(gameDataVo.getCoin_label());

        if (!TextUtils.isEmpty(gameDataVo.getVip_label())) {
            mTvGameVip.setText(gameDataVo.getVip_label());

            GradientDrawable gd = new GradientDrawable();
            float radius = ScreenUtil.dp2px(mContext, 8);
            gd.setCornerRadii(new float[]{0, 0, radius, radius, 0, 0, radius, radius});
            gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
            gd.setColors(new int[]{Color.parseColor("#FE6631"), Color.parseColor("#EF0F16")});
            mTvGameVip.setBackground(gd);
        }
        itemView.setOnClickListener(view -> {
            if (_mFragment != null) {
                _mFragment.goGameDetail(gameDataVo.getGameid(), gameDataVo.getGame_type());
            }
        });
        return itemView;
    }


    public class ViewHolder extends AbsHolder {
        private TextView      mTvTitle;
        private FlexboxLayout mFlexGameContent;

        public ViewHolder(View view) {
            super(view);
            mTvTitle = findViewById(R.id.tv_title);
            mFlexGameContent = findViewById(R.id.flex_game_content);

        }
    }
}
