package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.view.community.integral.CommunityIntegralMallFragment;
import com.zqhy.app.core.view.game.GameDetailCouponListFragment;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.core.view.login.LoginActivity;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

import java.text.DecimalFormat;

/**
 *
 * @author Administrator
 * @date 2018/11/21
 */

public class NewGameCouponListItemHolder extends AbsItemHolder<GameInfoVo.CouponListBean, NewGameCouponListItemHolder.ViewHolder>{
    public NewGameCouponListItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameInfoVo.CouponListBean item) {
        if (item.getCoupon_type().equals("game_coupon")){//游戏券
            if (item.getStatus() == 1) {
                holder.mTvStatus.setText("领券");
                if (item.getSign() == 1){
                    holder.mTvStatus.setTextColor(Color.parseColor("#361702"));
                    holder.mTvStatus.setBackgroundResource(R.drawable.shape_361702_big_radius_with_line);
                }else {
                    holder.mTvStatus.setTextColor(Color.parseColor("#FF6337"));
                    holder.mTvStatus.setBackgroundResource(R.drawable.shape_ff6337_big_radius_with_line);
                }
                holder.mTvStatus.setEnabled(true);
            } else if (item.getStatus() == 10) {
                holder.mTvStatus.setText("已领");
                holder.mTvStatus.setTextColor(Color.parseColor("#9B9B9B"));
                holder.mTvStatus.setEnabled(false);
                holder.mTvStatus.setBackgroundResource(R.drawable.shape_9b9b9b_big_radius_with_line);
            } else if (item.getStatus() == -1) {
                holder.mTvStatus.setText("已领完");
                holder.mTvStatus.setTextColor(Color.parseColor("#9B9B9B"));
                holder.mTvStatus.setEnabled(false);
                holder.mTvStatus.setBackgroundResource(R.drawable.shape_9b9b9b_big_radius_with_line);
            } else{
                holder.mTvStatus.setText("领券");
                if (item.getSign() == 1){
                    holder.mTvStatus.setTextColor(Color.parseColor("#361702"));
                    holder.mTvStatus.setBackgroundResource(R.drawable.shape_361702_big_radius_with_line);
                }else {
                    holder.mTvStatus.setTextColor(Color.parseColor("#FF6337"));
                    holder.mTvStatus.setBackgroundResource(R.drawable.shape_ff6337_big_radius_with_line);
                }
                holder.mTvStatus.setEnabled(true);
            }
        }else if (item.getCoupon_type().equals("shop_goods")){//商城券
            if (item.getStatus() == 1) {
                holder.mTvStatus.setText("兑换");
                holder.mTvStatus.setTextColor(Color.parseColor("#FF6337"));
                holder.mTvStatus.setEnabled(true);
                holder.mTvStatus.setBackgroundResource(R.drawable.shape_ff6337_big_radius_with_line);
            } else if (item.getStatus() == -1) {
                holder.mTvStatus.setText("已兑完");
                holder.mTvStatus.setTextColor(Color.parseColor("#9B9B9B"));
                holder.mTvStatus.setEnabled(false);
                holder.mTvStatus.setBackgroundResource(R.drawable.shape_9b9b9b_big_radius_with_line);
            } else{
                holder.mTvStatus.setText("兑换");
                holder.mTvStatus.setTextColor(Color.parseColor("#FF6337"));
                holder.mTvStatus.setEnabled(true);
                holder.mTvStatus.setBackgroundResource(R.drawable.shape_ff6337_big_radius_with_line);
            }
        }
        if (item.getSign() == 1){//判断是不是vip券
            holder.mTvTipsVip.setVisibility(View.VISIBLE);
            holder.mLlLeftBg.setBackgroundResource(R.mipmap.ic_game_detail_coupon_list_item_vip);
            holder.mTvAmountType.setTextColor(Color.parseColor("#361702"));
            holder.mTvAmount.setTextColor(Color.parseColor("#361702"));
            holder.mTvAccount.setTextColor(Color.parseColor("#361702"));
        }else {
            holder.mTvTipsVip.setVisibility(View.GONE);
            holder.mLlLeftBg.setBackgroundResource(R.mipmap.ic_game_detail_coupon_list_item_other);
            holder.mTvAmountType.setTextColor(Color.parseColor("#FFFFFF"));
            holder.mTvAmount.setTextColor(Color.parseColor("#FFFFFF"));
            holder.mTvAccount.setTextColor(Color.parseColor("#FFFFFF"));
        }
        /*if ("0".equals(item.getUser_get_count())){
            holder.mTvTips1.setVisibility(View.VISIBLE);
        }else {
            holder.mTvTips1.setVisibility(View.GONE);
        }*/

        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        String format = decimalFormat.format(item.getAmount());
        String amount = (format.indexOf(".0") != -1)? format.substring(0, format.indexOf(".0")): format;
        holder.mTvAmount.setText(amount);

        holder.mTvAccount.setText(item.getCondition());
        holder.mTvContent1.setText(item.getExpiry_label());
        holder.mTvContent2.setText(item.getCan_get_label());
        holder.mTvContent3.setText("仅限：" + item.getRange());

        if (!TextUtils.isEmpty(item.getOhterRange())){//游戏后缀
            holder.mTvGameSuffix.setVisibility(View.VISIBLE);
            String str = item.getOhterRange() + " 可用";
            SpannableString ss = new SpannableString(str);
            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), item.getOhterRange().length(), str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            holder.mTvGameSuffix.setText(ss);
        }else {
            holder.mTvGameSuffix.setVisibility(View.GONE);
        }

        holder.mTvTips1.setVisibility(View.VISIBLE);

        if ("day".equals(item.getFrequency())){
            holder.mTvTips1.setText("每日可领");
            holder.mTvContent1.setText("有效截至: 当日有效");
        }else {
            if ("0".equals(item.getUser_get_count())){
                holder.mTvTips1.setText("无限可领");
            }else {
                holder.mTvTips1.setText("限领1次");
            }
        }

        holder.mTvStatus.setOnClickListener(v -> {
            if (item.getGameid() > 0 && item.getCoupon_type().equals("game_coupon")) {
                if (_mFragment != null) {
                    if (UserInfoModel.getInstance().isLogined()) {
                        if (item.getSign() == 1) {
                            if (UserInfoModel.getInstance().getUserInfo().getSuper_user().getStatus().equals("yes")){
                                ((GameDetailCouponListFragment) _mFragment).getCoupon(Integer.parseInt(item.getId()));
                            }else {
                                ((GameDetailCouponListFragment) _mFragment).showVipTipsDialog();
                            }
                        }else{
                            ((GameDetailCouponListFragment) _mFragment).getCoupon(Integer.parseInt(item.getId()));
                        }
                    }else {
                        _mFragment.startActivity(new Intent(mContext, LoginActivity.class));
                    }
                }
            }else{
                if (_mFragment != null){
                    _mFragment.startFragment(new CommunityIntegralMallFragment());
                }
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_coupon_list;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mLlLeftBg;
        private TextView  mTvAmountType;
        private TextView mTvAmount;
        private TextView mTvAccount;
        private TextView mTvContent1;
        private TextView mTvContent2;
        private TextView mTvContent3;
        private TextView mTvStatus;
        private TextView mTvTips;
        private TextView mTvTipsVip;
        private TextView mTvGameSuffix;
        private TextView mTvTips1;

        public ViewHolder(View view) {
            super(view);
            mLlLeftBg = view.findViewById(R.id.ll_left_bg);
            mTvAmountType = view.findViewById(R.id.tv_amount_type);
            mTvAmount = view.findViewById(R.id.tv_amount);
            mTvAccount = view.findViewById(R.id.tv_account);
            mTvContent1 = view.findViewById(R.id.tv_content_1);
            mTvContent2 = view.findViewById(R.id.tv_content_2);
            mTvContent3 = view.findViewById(R.id.tv_content_3);
            mTvStatus = view.findViewById(R.id.tv_status);
            mTvTips = view.findViewById(R.id.tv_tips);
            mTvTipsVip = view.findViewById(R.id.tv_tips_vip);
            mTvGameSuffix = view.findViewById(R.id.tv_game_suffix);
            mTvTips1 = view.findViewById(R.id.tv_tips1);
        }
    }
}
