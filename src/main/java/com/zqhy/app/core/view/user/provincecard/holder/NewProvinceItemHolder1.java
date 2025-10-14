package com.zqhy.app.core.view.user.provincecard.holder;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.user.SuperVipMemberInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class NewProvinceItemHolder1 extends AbsItemHolder<SuperVipMemberInfoVo.DataBean.DiscountCardType, NewProvinceItemHolder1.ViewHolder> {
    private boolean isDialog;
    public NewProvinceItemHolder1(Context context, boolean isDialog) {
        super(context);
        this.isDialog = isDialog;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SuperVipMemberInfoVo.DataBean.DiscountCardType item) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
        if (holder.getLayoutPosition() == 0){
            layoutParams.setMargins(ScreenUtil.dp2px(mContext, 20), 0, ScreenUtil.dp2px(mContext, 10), 0);
        }else {
            layoutParams.setMargins(0, 0, ScreenUtil.dp2px(mContext, 10), 0);
        }
        holder.itemView.setLayoutParams(layoutParams);

        holder.mTvPrice.setText(String.valueOf(item.getPrice()));
        holder.mTvTips1.setText("总价值" + item.getTotal_worth() + "元");
        holder.mTvTips2.setText("每日领" + item.getDay_worth() + "元代金券");

        SpannableString spannableString = new SpannableString("总价值" + item.getTotal_worth() + "元");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF2D3F")), 3, 3 + String.valueOf(item.getTotal_worth()).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.mTvTips1.setText(spannableString);

        SpannableString spannableString1 = new SpannableString("每日领" + item.getDay_worth() + "元代金券");
        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF2D3F")), 3, 3 + String.valueOf(item.getDay_worth()).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.mTvTips2.setText(spannableString1);

        holder.mTvVipDays.setText(item.getName() + item.getDay() + "天");

        if (item.isSelected()){
            holder.mLlInnerBg.setBackgroundResource(R.mipmap.ic_province_card_option_selected_1);
            holder.mTvVipDays.setBackgroundResource(R.drawable.shape_fe6631_ef0f16_other_radius);
            holder.mTvPrice.setTextColor(Color.parseColor("#FF6A36"));
            holder.mTvPriceType.setTextColor(Color.parseColor("#FF6A36"));
            holder.mTvVipDays.setTextColor(Color.parseColor("#FFFFFF"));
        }else {
            holder.mLlInnerBg.setBackgroundResource(R.mipmap.ic_province_card_option_unselect);
            holder.mTvVipDays.setBackgroundResource(R.drawable.shape_e2ebfa_c9d3df_other_radius);
            holder.mTvPrice.setTextColor(Color.parseColor("#203B5D"));
            holder.mTvPriceType.setTextColor(Color.parseColor("#203B5D"));
            holder.mTvVipDays.setTextColor(Color.parseColor("#232323"));
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_province_card_new;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private LinearLayout mLlInnerBg;
        private TextView mTvPrice;
        private TextView mTvPriceType;
        private TextView mTvTips1;
        private TextView mTvTips2;
        private TextView mTvVipDays;

        public ViewHolder(View view) {
            super(view);
            mLlInnerBg = itemView.findViewById(R.id.ll_inner_bg);
            mTvPrice = itemView.findViewById(R.id.tv_price);
            mTvPriceType = itemView.findViewById(R.id.tv_price_type);
            mTvTips1 = itemView.findViewById(R.id.tv_tips_1);
            mTvTips2 = itemView.findViewById(R.id.tv_tips_2);
            mTvVipDays = itemView.findViewById(R.id.tv_vip_days);
        }
    }
}
