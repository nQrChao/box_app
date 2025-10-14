package com.zqhy.app.core.view.transaction.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.transaction.TradeMySellInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.StringUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.imageview.ClipRoundImageView;

/**
 * @author Administrator
 */
public class TradeMySellItemHolder extends AbsItemHolder<TradeMySellInfoVo, TradeMySellItemHolder.ViewHolder> {

    protected float density;

    public TradeMySellItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_transaction_mysell_list;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TradeMySellInfoVo item) {

        GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mIvTransactionImage2, R.mipmap.ic_placeholder);

        holder.mTvTransactionGameName.setText(item.getGamename());
        holder.tv_xh_username.setText(item.getXh_showname() + ",游戏充值" + item.getXh_pay_game_total()+"元");
        holder.tv_xh_reg_day.setText("创建"+item.getXh_reg_day()+"天");
        holder.tv_xh_pay_total.setText(item.getRmb_total()+"元");
        if (item.getBan_trade_info()!=null){
            if (!StringUtil.isEmpty(item.getBan_trade_info())) {
                holder.iv_ban_trade_info.setVisibility(View.INVISIBLE);
//                holder.iv_ban_trade_info.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_audit_transaction_sell_3));
                holder.tv_ban_trade_info.setVisibility(View.VISIBLE);
                holder.tv_ban_trade_info.setText(item.getBan_trade_info());
                holder.iv_1.setVisibility(View.VISIBLE);
                holder.iv_2.setVisibility(View.VISIBLE);
            }else {
                holder.iv_ban_trade_info.setVisibility(View.VISIBLE);
                holder.tv_ban_trade_info.setVisibility(View.GONE);
                holder.iv_1.setVisibility(View.GONE);
                holder.iv_2.setVisibility(View.GONE);
            }
        }
        if (!TextUtils.isEmpty(item.getOtherGameName())){//游戏后缀
            holder.mTvGameSuffix.setVisibility(View.VISIBLE);
            holder.mTvGameSuffix.setText(item.getOtherGameName());
        }else {
            holder.mTvGameSuffix.setVisibility(View.GONE);
        }
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mRootView;

        private ClipRoundImageView mIvTransactionImage2;
        private TextView mTvTransactionGameName;
        private TextView tv_xh_showname;
        private TextView tv_xh_username;
        private TextView tv_xh_reg_day;
        private TextView tv_xh_pay_total;
        private TextView tv_ban_trade_info;
        private ImageView iv_ban_trade_info;
        private ImageView iv_1;
        private ImageView iv_2;

        private TextView mTvGameSuffix;

        public ViewHolder(View view) {
            super(view);
            mRootView = findViewById(R.id.rootView);
            mIvTransactionImage2 = findViewById(R.id.iv_transaction_image2);
            mTvTransactionGameName = findViewById(R.id.tv_transaction_game_name);
            tv_xh_showname = findViewById(R.id.tv_xh_showname);
            tv_xh_username = findViewById(R.id.tv_xh_username);
            tv_xh_pay_total = findViewById(R.id.tv_xh_pay_total);
            tv_xh_reg_day = findViewById(R.id.tv_xh_reg_day);
            iv_ban_trade_info = findViewById(R.id.iv_ban_trade_info);

            tv_ban_trade_info = findViewById(R.id.tv_ban_trade_info);
            iv_1 = findViewById(R.id.iv_1);
            iv_2 = findViewById(R.id.iv_2);
            mTvGameSuffix = view.findViewById(R.id.tv_game_suffix);
        }
    }

}
