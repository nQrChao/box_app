package com.zqhy.app.core.view.user.newvip.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.user.newvip.SuperUserInfoVo;
import com.zqhy.app.core.view.user.newvip.NewUserVipFragment;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class VipVoucherItemHolder extends AbsItemHolder<SuperUserInfoVo.VoucherVo, VipVoucherItemHolder.ViewHolder> {
    public VipVoucherItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SuperUserInfoVo.VoucherVo item) {
        //holder.mTvAmount.setText(item.getCoupon_pft_total());
        holder.mTvName.setText(item.getBuy_name());
        holder.mTvContent.setText(item.getBuy_description().replace("|", "\n").replace("｜", "\n"));
        holder.mTvPrice.setText(item.getBuy_rmb_price());
        holder.mTvConfirm.setText(item.getStatus() == 0? "购买": "已购买");
        holder.mTvConfirm.setBackgroundResource(item.getStatus() == 0? R.drawable.shape_ffa530_ff3572_big_radius: R.drawable.shape_f6acac_big_radius);
        holder.mTvConfirm.setOnClickListener(v -> {
            if (_mFragment != null){
                if (_mFragment.checkLogin()) {
                    ((NewUserVipFragment) _mFragment).buyVoucher(item);
                }
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_vip_voucher;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        //private TextView mTvAmount;
        private TextView mTvName;
        private TextView mTvContent;
        private TextView mTvPrice;
        private TextView mTvConfirm;

        public ViewHolder(View view) {
            super(view);
            //mTvAmount = view.findViewById(R.id.tv_amount);
            mTvName = view.findViewById(R.id.tv_name);
            mTvContent = view.findViewById(R.id.tv_content);
            mTvPrice = view.findViewById(R.id.tv_price);
            mTvConfirm = view.findViewById(R.id.tv_confirm);
        }
    }
}
