package com.zqhy.app.core.view.transaction.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.widget.imageview.ClipRoundImageView;

/**
 * @author Administrator
 */
public class TradeItemHolder extends AbsItemHolder<TradeGoodInfoVo, TradeItemHolder.ViewHolder> {

    protected float density;

    public TradeItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_transaction_list;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TradeGoodInfoVo item) {
        if (holder.getLayoutPosition() == 1){
            holder.mRootView.setBackgroundResource(R.drawable.shape_white_radius_2);
        }

        holder.mTvTransactionTime.setTextColor(ContextCompat.getColor(mContext, R.color.color_9b9b9b));
        String regex = "yyyy-MM-dd HH:mm:ss";
        if (item.getIsSelled() == 2) {
            regex = "成交时间：MM-dd HH:mm:ss";
            holder.mTvTransactionTime.setTextColor(ContextCompat.getColor(mContext, R.color.color_3478f6));
        }
        holder.mTvTransactionTime.setText(CommonUtils.formatTimeStamp(item.getShow_time() * 1000, regex));
        GlideUtils.loadRoundImage(mContext, item.getGoods_pic(), holder.mIvTransactionImage, R.mipmap.ic_placeholder);
        holder.mTvTransactionTitle.setText(item.getGoods_title());
        holder.mTvTransactionGameName.setText(item.getGamename());
        holder.mTvTransactionPrice.setText(item.getGoods_price());

        holder.mTvTransactionXhRecharge.setText("小号累充" + CommonUtils.saveTwoSizePoint(item.getXh_pay_game_total()) + "元");
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mRootView;
        private TextView mTvTransactionTime;
        private ClipRoundImageView mIvTransactionImage;
        private TextView mTvTransactionTitle;
        private TextView mTvTransactionGameName;
        private TextView mTvTransactionPrice;
        private TextView mTvTransactionXhRecharge;

        public ViewHolder(View view) {
            super(view);
            mRootView = findViewById(R.id.rootView);
            mTvTransactionTime = findViewById(R.id.tv_transaction_time);
            mIvTransactionImage = findViewById(R.id.iv_transaction_image);
            mTvTransactionTitle = findViewById(R.id.tv_transaction_title);
            mTvTransactionGameName = findViewById(R.id.tv_transaction_game_name);
            mTvTransactionPrice = findViewById(R.id.tv_transaction_price);
            mTvTransactionXhRecharge = findViewById(R.id.tv_transaction_xh_recharge);

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(4 * density);
            gd.setColor(Color.parseColor("#21F5BE43"));
            mTvTransactionXhRecharge.setBackground(gd);
            mTvTransactionXhRecharge.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff8f19));
        }
    }
}
