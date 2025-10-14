package com.zqhy.app.core.view.user.provincecard.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;

import com.zqhy.app.core.data.model.user.MoneyCardOrderVo;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class ProvinceCardOrderHolder extends AbsItemHolder<MoneyCardOrderVo.CardOrderInfo, ProvinceCardOrderHolder.ViewHolder> {
    public ProvinceCardOrderHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MoneyCardOrderVo.CardOrderInfo item) {
        holder.pay_time.setText("购买时间："+CommonUtils.formatTimeStamp(Long.parseLong(item.getPay_time())*1000,"yyyy-MM-dd"));
        holder.expiry_time.setText("有效期至："+CommonUtils.formatTimeStamp(Long.parseLong(item.getExpiry_time())*1000,"yyyy-MM-dd"));
        if (item.getGood_type() == 2){
            holder.purchase_member_type.setText("省钱卡");
        }else if (item.getGood_type() == 5){
            holder.purchase_member_type.setText("特惠卡");
        }else {
            holder.purchase_member_type.setText("");
        }

        holder.purchase_days.setText(item.getPurchase_days() + "天" + item.getType_label());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_new_province_card_order;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private TextView  pay_time;
        private TextView  expiry_time;
        private TextView  purchase_member_type;
        private TextView  purchase_days;

        public ViewHolder(View view) {
            super(view);
            pay_time = findViewById(R.id.pay_time);
            expiry_time = findViewById(R.id.expiry_time);
            purchase_member_type = findViewById(R.id.purchase_member_type);
            purchase_days = findViewById(R.id.purchase_days);
        }
    }
}
