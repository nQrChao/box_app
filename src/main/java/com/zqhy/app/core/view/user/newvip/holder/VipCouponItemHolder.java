package com.zqhy.app.core.view.user.newvip.holder;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.user.newvip.SuperUserInfoVo;
import com.zqhy.app.core.view.user.newvip.NewUserVipFragment;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class VipCouponItemHolder extends AbsItemHolder<SuperUserInfoVo.CouponVo, VipCouponItemHolder.ViewHolder> {
    public VipCouponItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SuperUserInfoVo.CouponVo item) {
        holder.mTvAmount.setText(String.valueOf(item.getAmount()));
        holder.mTvExpiryLabel.setText(item.getExpiry_label());
        holder.mTvRange.setText(item.getRange());
        holder.mTvConfirm.setOnClickListener(v -> {
            if (_mFragment != null && _mFragment instanceof NewUserVipFragment) {
                if (_mFragment.checkLogin()) {
                    ((NewUserVipFragment) _mFragment).getCoupon(item.getCoupon_id());
                }
            }
        });
        holder.mTvConfirm.setText(item.getHas_get().equals("no")? "领取": "已领取");
        holder.mTvConfirm.setBackgroundResource(item.getHas_get().equals("no")? R.drawable.shape_ffa530_ff3572_big_radius: R.drawable.shape_f6acac_big_radius);
        holder.mTvConfirm.setClickable(item.getHas_get().equals("no")? true: false);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_vip_coupon;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private TextView mTvAmount;
        private TextView mTvExpiryLabel;
        private TextView mTvRange;
        private TextView mTvConfirm;

        public ViewHolder(View view) {
            super(view);
            mTvAmount = itemView.findViewById(R.id.tv_amount);
            mTvExpiryLabel = itemView.findViewById(R.id.tv_expiry_label);
            mTvRange = itemView.findViewById(R.id.tv_range);
            mTvConfirm = itemView.findViewById(R.id.tv_confirm);
        }
    }
}
