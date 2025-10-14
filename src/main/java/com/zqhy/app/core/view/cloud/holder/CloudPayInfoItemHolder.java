package com.zqhy.app.core.view.cloud.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.cloud.CloudPayInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

import java.math.BigDecimal;

/**
 *
 * @author Administrator
 * @date 2018/11/24
 */

public class CloudPayInfoItemHolder extends AbsItemHolder<CloudPayInfoVo.DataBean, CloudPayInfoItemHolder.ViewHolder> {

    public CloudPayInfoItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CloudPayInfoVo.DataBean item) {
        holder.mTvName.setText(item.getName());
        holder.mTvPrice.setText(item.getPrice());
        holder.mTvPriceOld.setText("¥" + item.getNeed());
        holder.mTvCost.setText("¥" + CommonUtils.saveFloatPoint(Float.parseFloat(item.getPrice())/Integer.parseInt(item.getDay()), 2, BigDecimal.ROUND_DOWN) + "/天");

        if (item.isSelected()){
            holder.mLlContent.setBackgroundResource(R.drawable.ts_shape_gradient_55c0fe_5571fe_with_line_54a6fe);
            holder.mTvName.setTextColor(Color.parseColor("#FFFFFF"));
            holder.mTvPrice.setTextColor(Color.parseColor("#FFFFFF"));
            holder.mTvPriceType.setTextColor(Color.parseColor("#FFFFFF"));
            holder.mTvPriceOld.setTextColor(Color.parseColor("#FFFFFF"));
            holder.mTvCost.setBackgroundResource(R.drawable.shape_white_big_radius);
        }else {
            holder.mLlContent.setBackgroundResource(R.drawable.shape_white_radius_with_line_c7c7c7);
            holder.mTvName.setTextColor(Color.parseColor("#676767"));
            holder.mTvPrice.setTextColor(Color.parseColor("#666666"));
            holder.mTvPriceType.setTextColor(Color.parseColor("#666666"));
            holder.mTvPriceOld.setTextColor(Color.parseColor("#BEBEBE"));
            holder.mTvCost.setBackgroundResource(R.drawable.shape_e8f3ff_big_radius);
        }

        if (!TextUtils.isEmpty(item.getFirst_discount())){
            holder.mTvtTag.setVisibility(View.VISIBLE);
            holder.mTvtTag.setText("推荐");
        }else {
            if (item.getChaozhi() == 1){
                holder.mTvtTag.setVisibility(View.VISIBLE);
                holder.mTvtTag.setText("超值优惠");
            }else {
                holder.mTvtTag.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_cloud_payinfo;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private ConstraintLayout mClRoot;
        private LinearLayout mLlContent;
        private TextView mTvName;
        private TextView mTvPrice;
        private TextView mTvPriceType;
        private TextView mTvPriceOld;
        private TextView mTvCost;
        private TextView mTvtTag;

        public ViewHolder(View view) {
            super(view);
            mClRoot = view.findViewById(R.id.cl_root);
            mLlContent = view.findViewById(R.id.ll_content);
            mTvName = view.findViewById(R.id.tv_name);
            mTvPrice = view.findViewById(R.id.tv_price);
            mTvPriceType = view.findViewById(R.id.tv_price_type);
            mTvPriceOld = view.findViewById(R.id.tv_price_old);
            mTvCost = view.findViewById(R.id.tv_cost);
            mTvtTag = view.findViewById(R.id.tv_tag);

            ViewGroup.LayoutParams layoutParams = mClRoot.getLayoutParams();
            layoutParams.width = (ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 45F)) / 3;
            mClRoot.setLayoutParams(layoutParams);

            mTvPriceOld.setPaintFlags(mTvPriceOld.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
