package com.zqhy.app.core.view.game.coupon;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.coupon.GameCouponsListVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/8/25-10:47
 * @description
 */
public class GameCouponsItemHolder extends AbsItemHolder<GameCouponsListVo.DataBean, GameCouponsItemHolder.ViewHolder> {
    public GameCouponsItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_list_coupon;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameCouponsListVo.DataBean item) {

        int gameid = 0;
        GameInfoVo gameInfoVo = item.getGameinfo();
        if (gameInfoVo != null) {
            gameid = gameInfoVo.getGameid();
            GlideUtils.loadRoundImage(mContext, gameInfoVo.getGameicon(), holder.mIvGameIcon);
            holder.mTvGameName.setText(gameInfoVo.getGamename());
            if (!TextUtils.isEmpty(gameInfoVo.getOtherGameName())){//游戏后缀
                holder.mTvGameSuffix.setVisibility(View.VISIBLE);
                holder.mTvGameSuffix.setText(gameInfoVo.getOtherGameName());
            }else {
                holder.mTvGameSuffix.setVisibility(View.GONE);
            }
            holder.mTvGameInfo.setText(gameInfoVo.getGenre_str() + "    " + CommonUtils.formatNumberType2(gameInfoVo.getPlay_count()) + "人在玩");
            holder.mLlGameInfo.setOnClickListener(view -> {
                if (_mFragment != null) {
                    _mFragment.goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
                }
            });
        } else {
            holder.mIvGameIcon.setImageResource(R.mipmap.ic_placeholder);
            holder.mTvGameName.setText("");
            holder.mTvGameInfo.setText("");
            holder.mLlGameInfo.setOnClickListener(null);
        }
        holder.mLlCouponContainer.removeAllViews();

        List<GameCouponsListVo.CouponVo> couponVoList = item.getCoupon_list();
        if (couponVoList != null && !couponVoList.isEmpty()) {
            for (int i = 0; i < couponVoList.size(); i++) {
                float density = ScreenUtil.getScreenDensity(mContext);
                int width = (int) (ScreenUtil.getScreenWidth(mContext) * 0.75f);
                int height = (int) (width * 0.36f);
                View itemView = createCouponView(couponVoList.get(i), gameid);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
                params.rightMargin = (int) (10 * density);
                holder.mLlCouponContainer.addView(itemView, params);
            }
        }
    }


    private View createCouponView(GameCouponsListVo.CouponVo couponVo, int gameid) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_game_list_coupon_single_item, null);
        LinearLayout mLlCouponItem = itemView.findViewById(R.id.ll_coupon_item);
        TextView mTvCouponCount = itemView.findViewById(R.id.tv_coupon_count);
        TextView mTvCouponCondition = itemView.findViewById(R.id.tv_coupon_condition);
        TextView mTvCouponAction = itemView.findViewById(R.id.tv_coupon_action);
        TextView mTvCouponRemain = itemView.findViewById(R.id.tv_coupon_remain);

        mTvCouponCount.setText(String.valueOf(couponVo.getAmount()));
        mTvCouponCondition.setText(couponVo.getCoupon_name());
        mTvCouponRemain.setText(couponVo.getRemain_days());

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(24 * ScreenUtil.getScreenDensity(mContext));
        if (couponVo.getStatus() == 1) {
            mLlCouponItem.setBackgroundResource(R.drawable.ts_shape_ffefea_radius);
            gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            gd.setColors(new int[]{Color.parseColor("#FF3D44"), Color.parseColor("#FE7448")});
            mTvCouponAction.setText("立即领取");
            mTvCouponAction.setEnabled(true);
        } else if (couponVo.getStatus() == 10) {
            mLlCouponItem.setBackgroundResource(R.drawable.ts_shape_f2f2f2_radius);
            gd.setColor(ContextCompat.getColor(mContext, R.color.color_b1b1b1));
            mTvCouponAction.setText("已领取");
            mTvCouponAction.setEnabled(false);
        } else if (couponVo.getStatus() == -1) {
            mLlCouponItem.setBackgroundResource(R.drawable.ts_shape_f2f2f2_radius);
            gd.setColor(ContextCompat.getColor(mContext, R.color.color_b1b1b1));
            mTvCouponAction.setText("已领完");
            mTvCouponAction.setEnabled(false);
        }

        mTvCouponAction.setBackground(gd);
        mTvCouponAction.setOnClickListener(view -> {
            if (_mFragment != null && _mFragment instanceof GameCouponsListFragment) {
                if (_mFragment.checkLogin()) {
                    ((GameCouponsListFragment) _mFragment).getCoupon(couponVo.getCoupon_id(), gameid);
                }
            }
        });
        return itemView;
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mLlGameInfo;
        private ImageView    mIvGameIcon;
        private TextView     mTvGameName;
        private TextView     mTvGameSuffix;
        private TextView     mTvGameInfo;
        private LinearLayout mLlCouponContainer;

        public ViewHolder(View view) {
            super(view);
            mLlGameInfo = findViewById(R.id.ll_game_info);
            mIvGameIcon = findViewById(R.id.iv_game_icon);
            mTvGameName = findViewById(R.id.tv_game_name);
            mTvGameSuffix = findViewById(R.id.tv_game_suffix);
            mTvGameInfo = findViewById(R.id.tv_game_info);
            mLlCouponContainer = findViewById(R.id.ll_coupon_container);

        }
    }
}
