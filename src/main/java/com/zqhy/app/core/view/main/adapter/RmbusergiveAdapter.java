package com.zqhy.app.core.view.main.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zqhy.app.adapter.abs.AbsAdapter;
import com.zqhy.app.core.data.model.user.newvip.RmbusergiveVo;
import com.zqhy.app.newproject.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author leeham2734
 * @date 2022/4/1-9:50
 * @description
 */
public class RmbusergiveAdapter extends AbsAdapter<RmbusergiveVo.CouponListBean> {

    public RmbusergiveAdapter(Context context, List<RmbusergiveVo.CouponListBean> labels) {
        super(context, labels);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, RmbusergiveVo.CouponListBean data, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mTvPrice.setText(data.getCoupon_name());
        viewHolder.mTvCondition.setText(data.getCondition());
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        String format = decimalFormat.format(data.getDiscount());
        String amount = (format.indexOf(".0") != -1)? format.substring(0, format.indexOf(".0")): format;
        viewHolder.mTvDiscount.setText(amount + "折");
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_dialog_rmbusergive;
    }

    @Override
    public AbsViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends AbsViewHolder {

        private TextView mTvPrice;
        private TextView mTvCondition;
        private TextView mTvDiscount;

        public ViewHolder(View view) {
            super(view);
            mTvPrice = view.findViewById(R.id.tv_price);
            mTvCondition = view.findViewById(R.id.tv_condition);
            mTvDiscount = view.findViewById(R.id.tv_discount);
        }
    }
}
