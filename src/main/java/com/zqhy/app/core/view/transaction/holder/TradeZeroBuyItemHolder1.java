package com.zqhy.app.core.view.transaction.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.transaction.TradeZeroBuyGoodInfoListVo1;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.widget.imageview.ClipRoundImageView;

/**
 * @author Administrator
 */
public class TradeZeroBuyItemHolder1 extends AbsItemHolder<TradeZeroBuyGoodInfoListVo1.TradeZeroBuyGoodInfo, TradeZeroBuyItemHolder1.ViewHolder> {

    protected float density;
    protected OnClickButtenListener mlistener = null;


    public TradeZeroBuyItemHolder1(Context context, OnClickButtenListener listener) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
        mlistener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_transaction_zerobuy_list1;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TradeZeroBuyGoodInfoListVo1.TradeZeroBuyGoodInfo item) {
        String regex = "yyyy-MM-dd HH:mm";
        String s = CommonUtils.formatTimeStamp(Long.parseLong(item.getBuy_time()) * 1000, regex);
        holder.tv_buy_time.setText(s);
        holder.tv_buy_time.setVisibility(View.VISIBLE);
        GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.iv_transaction_image, R.mipmap.ic_placeholder);
        holder.tv_transaction_game_name.setText(item.getGamename());
        holder.tv_reg_day.setText(item.getXh_reg_day()+"天");
        holder.tv_pay_total.setText(item.getXh_pay_total()+"元");
        holder.tv_server.setText(item.getXh_server());
        holder.tv_score.setText(item.getIntegral()+"积分");
        if (getPosition(holder)==1) {
            holder.link.setVisibility(View.GONE);
        }

        holder.tv_buy.setOnClickListener(view -> {
            if (mlistener!=null){
                mlistener.onClickButten(view,item.getGameid(),item.getGame_type());
            }
        });

        holder.iv_transaction_image.setOnClickListener(view -> {
            if (mlistener!=null){
                mlistener.onClickButten(view,item.getGameid(),item.getGame_type());
            }
        });
    }

    public class ViewHolder extends AbsHolder {
        private TextView tv_transaction_game_name;
        private TextView tv_reg_day;
        private TextView tv_pay_total;
        private TextView tv_server;
        private TextView tv_score;
        private TextView tv_buy_time;
        private TextView tv_buy;
        private View link;
        private ClipRoundImageView iv_transaction_image;

        public ViewHolder(View view) {
            super(view);
            tv_transaction_game_name = findViewById(R.id.tv_transaction_game_name);
            tv_reg_day = findViewById(R.id.tv_reg_day);
            tv_pay_total = findViewById(R.id.tv_pay_total);
            tv_server = findViewById(R.id.tv_server);
            tv_buy_time = findViewById(R.id.tv_buy_time);
            tv_score = findViewById(R.id.tv_score);
            tv_buy = findViewById(R.id.tv_buy);
            link = findViewById(R.id.link);
            iv_transaction_image = findViewById(R.id.iv_transaction_image);

//            GradientDrawable gd = new GradientDrawable();
//            gd.setCornerRadius(4 * density);
//            gd.setColor(Color.parseColor("#21F5BE43"));
//            mTvTransactionXhRecharge.setBackground(gd);
//            mTvTransactionXhRecharge.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff8f19));
        }
    }

    public interface OnClickButtenListener{
         void onClickButten(View view,String id,String game_type);
    }
}
