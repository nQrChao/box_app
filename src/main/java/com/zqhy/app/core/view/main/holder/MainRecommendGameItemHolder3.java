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
import com.zqhy.app.core.data.model.mainpage.recommend.MainGameRecommendItemVo3;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2021/6/7-11:01
 * @description
 */
public class MainRecommendGameItemHolder3 extends BaseItemHolder<MainGameRecommendItemVo3, MainRecommendGameItemHolder3.ViewHolder> {
    public MainRecommendGameItemHolder3(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_game_recommend_3;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainGameRecommendItemVo3 item) {
        if (item.getData() != null) {
            holder.mTvTitle.setText(item.getData().getTitle());
            holder.mTvTitle.setTextColor(Color.parseColor(item.getData().getTitle_color()));

            holder.mFlexGameContent.removeAllViews();
            if (item.getData().getData() != null && item.getData().getData().size() > 0) {
                holder.mFlexGameContent.setVisibility(View.VISIBLE);
                for (MainGameRecommendVo.GameDataVo gameDataVo : item.getData().getData()) {
                    View itemView = createGameItemView(gameDataVo);
                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.topMargin = ScreenUtil.dp2px(mContext, 6);
                    params.bottomMargin = ScreenUtil.dp2px(mContext, 6);
                    holder.mFlexGameContent.addView(itemView, params);
                }
            } else {
                holder.mFlexGameContent.setVisibility(View.GONE);
            }
        }

        if (item.getPicItem() != null) {
            holder.mImage.setVisibility(View.VISIBLE);
            GlideUtils.loadRoundImage(mContext, item.getPicItem().getPic(), holder.mImage, R.mipmap.img_placeholder_v_1);
            holder.mImage.setOnClickListener(view -> {
                try {
                    appJump(item.getPicItem().getJumpInfo());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            holder.mImage.setVisibility(View.GONE);
        }

    }


    private View createGameItemView(MainGameRecommendVo.GameDataVo gameDataVo) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_item_main_game_data_2, null);
        ImageView mIvGameIcon = itemView.findViewById(R.id.iv_game_icon);
        TextView mTvGameVip = itemView.findViewById(R.id.tv_game_vip);
        TextView mTvGameName = itemView.findViewById(R.id.tv_game_name);
        TextView mTvGameCoin = itemView.findViewById(R.id.tv_game_coin);

        GlideUtils.loadRoundImage(mContext, gameDataVo.getGameicon(), mIvGameIcon);
        mTvGameName.setText(gameDataVo.getGamename());
        mTvGameCoin.setText(gameDataVo.getCoupon_amount() + "元");

        if (!TextUtils.isEmpty(gameDataVo.getVip_label())) {
            mTvGameVip.setText(gameDataVo.getVip_label());

            GradientDrawable gd = new GradientDrawable();
            float radius = ScreenUtil.dp2px(mContext, 8);
            gd.setCornerRadii(new float[]{0, 0, radius, radius, 0, 0, radius, radius});
            gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
            gd.setColors(new int[]{Color.parseColor("#FE6631"), Color.parseColor("#EF0F16")});
            mTvGameVip.setBackground(gd);
        }


        GradientDrawable gd = new GradientDrawable();
        float radius = ScreenUtil.dp2px(mContext, 8);
        gd.setCornerRadius(radius);
        gd.setColor(Color.parseColor("#150D9EFF"));
        itemView.setBackground(gd);

        itemView.setOnClickListener(view -> {
            if (_mFragment != null) {
                _mFragment.goGameDetail(gameDataVo.getGameid(), gameDataVo.getGame_type());
            }
        });

        return itemView;
    }

    public class ViewHolder extends AbsHolder {
        private TextView      mTvTitle;
        private ImageView     mImage;
        private FlexboxLayout mFlexGameContent;

        public ViewHolder(View view) {
            super(view);
            mTvTitle = findViewById(R.id.tv_title);
            mImage = findViewById(R.id.image);
            mFlexGameContent = findViewById(R.id.flex_game_content);

        }
    }
}
