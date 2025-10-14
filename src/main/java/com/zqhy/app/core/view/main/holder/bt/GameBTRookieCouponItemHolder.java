package com.zqhy.app.core.view.main.holder.bt;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.mainpage.HomeBTGameIndexVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2020/8/19-15:19
 * @description
 */
public class GameBTRookieCouponItemHolder extends AbsItemHolder<HomeBTGameIndexVo.RookiesCouponVo, GameBTRookieCouponItemHolder.ViewHolder> {
    public GameBTRookieCouponItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_bt_rookie_coupon;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull HomeBTGameIndexVo.RookiesCouponVo item) {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(32 * ScreenUtil.getScreenDensity(mContext));
        gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        gd.setColors(new int[]{Color.parseColor("#FDEE8A"), Color.parseColor("#F7D046")});
        holder.mTvMainBtGift.setBackground(gd);
        holder.mTvMainBtGift.setOnClickListener(view -> {
            //            if (_mFragment != null && _mFragment.checkLogin() && _mFragment.checkUserBindPhoneTips1() && _mFragment instanceof MainPageFragment) {
            //                ((MainPageFragment) _mFragment).getRookieVouchers(item.getList());
            //            }
            if (_mFragment != null) {
                BrowserActivity.newInstance(_mFragment.getActivity(), item.getGet_now_url());
            }
        });
        holder.mLlContainerCoupon.removeAllViews();
        if (item.getList() != null && !item.getList().isEmpty()) {
            holder.mLlContainerCoupon.setVisibility(View.VISIBLE);
            int index = 0;
            for (HomeBTGameIndexVo.RookiesCouponVo.DataBean data : item.getList()) {
                View itemView = createCouponView(data);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = index != 0 ? 0 : (int) (8 * ScreenUtil.getScreenDensity(mContext));
                params.rightMargin = (int) (8 * ScreenUtil.getScreenDensity(mContext));
                holder.mLlContainerCoupon.addView(itemView, params);
                index++;
            }
            holder.mLlContainerCoupon.setOnClickListener(v -> {
                if (_mFragment != null) {
                    BrowserActivity.newInstance(_mFragment.getActivity(), item.getRookies_coupon_url());
                }
            });
        } else {
            holder.mLlContainerCoupon.setVisibility(View.GONE);
        }
        holder.mLlContainerCouponGame.removeAllViews();
        if (item.getCard648list() != null && !item.getCard648list().isEmpty()) {
            holder.mLlContainerCouponGame.setVisibility(View.VISIBLE);
            int index = 0;
            for (HomeBTGameIndexVo.RookiesCouponVo.GameVo gameVo : item.getCard648list()) {
                View itemView = createCouponGameView(gameVo);
                int width = ScreenUtil.dp2px(mContext, 54);
                int height = ScreenUtil.dp2px(mContext, 54);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
                params.leftMargin = index != 0 ? 0 : (int) (8 * ScreenUtil.getScreenDensity(mContext));
                params.rightMargin = (int) (6 * ScreenUtil.getScreenDensity(mContext));
                holder.mLlContainerCouponGame.addView(itemView, params);
                index++;
            }
            holder.mLlContainerCouponGame.setOnClickListener(v -> {
                if (_mFragment != null) {
                    BrowserActivity.newInstance(_mFragment.getActivity(), item.getCard648_url());
                }
            });
        } else {
            holder.mLlContainerCouponGame.setVisibility(View.GONE);
        }
    }


    public View createCouponGameView(HomeBTGameIndexVo.RookiesCouponVo.GameVo gameVo) {
        ImageView image = new ImageView(mContext);
        GlideUtils.loadRoundImage(mContext, gameVo.getGameicon(), image);
        return image;
    }

    public View createCouponView(HomeBTGameIndexVo.RookiesCouponVo.DataBean item) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_game_bt_menu_coupon, null);
        TextView mTv1 = itemView.findViewById(R.id.tv_1);
        mTv1.setText(item.getAmount() + "元\n" + item.getCoupon_name());
        return itemView;
    }

    public class ViewHolder extends AbsHolder {
        private TextView     mTvMainBtGift;
        private LinearLayout mLlContainerCoupon;
        private LinearLayout mLlContainerCouponGame;

        public ViewHolder(View view) {
            super(view);
            mTvMainBtGift = findViewById(R.id.tv_main_bt_gift);
            mLlContainerCoupon = findViewById(R.id.ll_container_coupon);
            mLlContainerCouponGame = findViewById(R.id.ll_container_coupon_game);

        }
    }
}
