package com.zqhy.app.core.view.community.integral.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.community.integral.IntegralDetailListVo;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 */
public class IntegralListItemHolder extends AbsItemHolder<IntegralDetailListVo.DataBean,IntegralListItemHolder.ViewHolder> {

    public IntegralListItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_integral_detail;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull IntegralDetailListVo.DataBean item) {
        holder.mTvTime.setText(CommonUtils.formatTimeStamp(item.getAdd_time() * 1000, "yyyy-MM-dd\nHH:mm:ss"));

        if (item.getAmount() < 0) {
            holder.mTvIntegralCount.setTextColor(ContextCompat.getColor(mContext, R.color.color_333333));
            holder.mTvIntegralCount.setText(String.valueOf(item.getAmount()));
        } else {
            holder.mTvIntegralCount.setTextColor(ContextCompat.getColor(mContext, R.color.color_007aff));
            holder.mTvIntegralCount.setText("+" + String.valueOf(item.getAmount()));
        }
        holder.mTvRemark.setText(item.getType_name());

        if (!TextUtils.isEmpty(item.getRemark())) {
            holder.mTvRemark.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_user_currency_arrow), null);
            holder.mTvRemark.setOnClickListener(view -> {
                if(_mFragment != null){
                    _mFragment.showFloatPopView(item.getRemark(), holder.mTvRemark);
                }
            });
        } else {
            holder.mTvRemark.setOnClickListener(null);
            holder.mTvRemark.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    public class ViewHolder extends AbsHolder {
        private TextView mTvTime;
        private TextView mTvIntegralCount;
        private TextView mTvRemark;

        public ViewHolder(View view) {
            super(view);
            mTvTime = findViewById(R.id.tv_time);
            mTvIntegralCount = findViewById(R.id.tv_integral_count);
            mTvRemark = findViewById(R.id.tv_remark);

        }
    }
}
