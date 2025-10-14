package com.zqhy.app.core.view.user.newvip.holder;

import android.content.Context;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.user.newvip.SuperUserInfoVo;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class SuperVipItemHolder extends AbsItemHolder<SuperUserInfoVo.CardTypeVo, SuperVipItemHolder.ViewHolder> {
    public SuperVipItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SuperUserInfoVo.CardTypeVo item) {

        if (item.getSub_price() != 0) {
            holder.mTvVipMemberFreeDays.setText("减" + item.getSub_price() + "元");
            holder.mTvVipMemberFreeDays.setVisibility(View.VISIBLE);
            holder.mTvVipMemberFreeDays.setBackgroundResource(R.drawable.ts_shape_gradient_ff5b0a_ff150d);
        } else {
            holder.mTvVipMemberFreeDays.setText("");
            holder.mTvVipMemberFreeDays.setVisibility(View.GONE);
            holder.mTvVipMemberFreeDays.setBackgroundResource(R.drawable.ts_shape_gradient_ff5b0a_ff150d);
        }
        holder.mTvVipMemberAmount.setText(String.valueOf(item.getPrice()));
        holder.mTvVipMemberDays.setText(item.getName());
        //holder.mTvVipMemberTotalAmount.setText(String.valueOf(item.getDay()) + "天会员特权");
        holder.mTvVipMemberTotalAmount.setText("¥" + (item.getPrice()+ item.getSub_price()));
        holder.mTvVipMemberTotalAmount.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        if (item.isSelected()){
            holder.mLlRootBg.setBackgroundResource(R.drawable.shape_radius_stroke_fd892d_selected_1);
        }else {
            holder.mLlRootBg.setBackgroundResource(R.drawable.shape_fff8e9_radius);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_super_vip_card;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private AppCompatTextView mTvVipMemberFreeDays;
        private AppCompatTextView mTvVipMemberDays;
        private AppCompatTextView mTvVipMemberAmount;
        private AppCompatTextView mTvVipMemberTotalAmount;
        private LinearLayout mLlRootBg;

        public ViewHolder(View view) {
            super(view);
            mTvVipMemberFreeDays = itemView.findViewById(R.id.tv_vip_member_free_days);
            mTvVipMemberDays = itemView.findViewById(R.id.tv_vip_member_days);
            mTvVipMemberAmount = itemView.findViewById(R.id.tv_vip_member_amount);
            mTvVipMemberTotalAmount = itemView.findViewById(R.id.tv_vip_member_total_amount);
            mLlRootBg = itemView.findViewById(R.id.ll_root_bg);
        }
    }
}
