package com.zqhy.app.core.view.currency.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.user.CurrencyListVo;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 *
 * @author Administrator
 * @date 2018/11/24
 */

public class CurrencyItemHolder extends AbsItemHolder<CurrencyListVo.DataBean, CurrencyItemHolder.ViewHolder> {

    public CurrencyItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CurrencyListVo.DataBean item) {
        try {
            long ms = item.getLogtime() * 1000;
            holder.mTvCurrencyTime.setText(CommonUtils.formatTimeStamp(ms, "yyyy-MM-dd\nHH:mm:ss"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            float amount = item.getJiapingtaibi();
            if (amount > 0) {
                holder.mTvCurrencyAmount.setTextColor(ContextCompat.getColor(mContext, R.color.color_11a8ff));
                holder.mTvCurrencyAmount.setText("+" + amount);
            } else {
                holder.mTvCurrencyAmount.setTextColor(ContextCompat.getColor(mContext, R.color.color_353535));
                holder.mTvCurrencyAmount.setText(String.valueOf(amount));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.mTvCurrencyMark.setText(item.getRemark());
        if (!TextUtils.isEmpty(item.getContent())) {
            holder.mTvCurrencyMark.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_user_currency_arrow), null);
            holder.mTvCurrencyMark.setOnClickListener(view -> {
                if(_mFragment != null){
                    _mFragment.showFloatPopView(item.getContent(), holder.mTvCurrencyMark);
                }
            });
        } else {
            holder.mTvCurrencyMark.setOnClickListener(null);
            holder.mTvCurrencyMark.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_user_currency;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private TextView mTvCurrencyTime;
        private TextView mTvCurrencyAmount;
        private TextView mTvCurrencyMark;

        public ViewHolder(View view) {
            super(view);
            mTvCurrencyTime = findViewById(R.id.tv_currency_time);
            mTvCurrencyAmount = findViewById(R.id.tv_currency_amount);
            mTvCurrencyMark = findViewById(R.id.tv_currency_mark);

        }
    }
}
