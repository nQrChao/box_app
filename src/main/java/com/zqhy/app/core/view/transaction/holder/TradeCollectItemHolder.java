package com.zqhy.app.core.view.transaction.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.transaction.TradeCollectInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.widget.imageview.ClipRoundImageView;

/**
 * @author Administrator
 */
public class TradeCollectItemHolder extends AbsItemHolder<TradeCollectInfoVo, TradeCollectItemHolder.ViewHolder> {

    private float density;
    protected OnClickButtenListener mlistener = null;
    public TradeCollectItemHolder(Context context, OnClickButtenListener listener) {
        super(context);
        density = ScreenUtil.getScreenDensity(context);
        mlistener = listener;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_transaction_collect_list;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TradeCollectInfoVo item) {
        String yyyy = CommonUtils.formatTimeStamp(System.currentTimeMillis(), "yyyy");
        String regex = "(yyyy-MM-dd HH:mm )";
        String s = CommonUtils.formatTimeStamp(item.getCollection_time() * 1000, regex);
        if (s.contains(yyyy)){
            holder.mTvTransactionTime.setText(CommonUtils.formatTimeStamp(item.getCollection_time() * 1000, "(MM-dd HH:mm)"));
        }else {
            holder.mTvTransactionTime.setText(s);
        }
        GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mIvTransactionImage2, R.mipmap.ic_placeholder);
        if (!TextUtils.isEmpty(item.getGame_suffix())){//游戏后缀
            holder.mTvGameSuffix.setVisibility(View.VISIBLE);
            holder.mTvGameSuffix.setText(item.getGame_suffix());
        }else {
            holder.mTvGameSuffix.setVisibility(View.GONE);
        }
        if ("1".equals(item.getGame_type())){
            if (item.getProfit_rate() <= 0.1 && item.getProfit_rate() > 0.01){
                holder.mlayoutPercent.setVisibility(View.VISIBLE);
                holder.mTvPercent.setText("0"+CommonUtils.saveTwoSizePoint(item.getProfit_rate()*10)+"折");
                holder.mTvPercent1.setText("抄底");
            }else if (item.getProfit_rate() <= 0.2 && item.getProfit_rate()>0.1){
                holder.mlayoutPercent.setVisibility(View.VISIBLE);
                holder.mTvPercent.setText(CommonUtils.saveTwoSizePoint(item.getProfit_rate()*10)+"折");
                holder.mTvPercent1.setText("捡漏");
            }else {
                holder.mlayoutPercent.setVisibility(View.INVISIBLE);
            }
        }else {
            holder.mlayoutPercent.setVisibility(View.INVISIBLE);
        }

        holder.mTvTransactionGameName.setText(item.getGamename());
        holder.mTvTransactionPrice.setText(item.getGoods_price());
        holder.tv_explain.setText(item.getGoods_description());

//        holder.tv_genre_str.setText(item.getGenre_str());
//        holder.tv_play_count.setText(" • " + item.getPlay_count() + "人在玩");
        holder.tv_server_info.setText("区服: " + item.getServer_info());
        holder.tv_cancel.setOnClickListener(view -> {
            if (mlistener!=null){
                mlistener.onClickButten(view,item.getGid());
            }
        });

    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    public interface OnClickButtenListener{
        void onClickButten(View view,String id);
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mRootView;
        private TextView mTvTransactionTime;
        private ClipRoundImageView mIvTransactionImage2;
        private TextView mTvTransactionGameName;
        private TextView mTvTransactionPrice;
        private TextView tv_genre_str;
        private TextView tv_play_count;
        private TextView tv_server_info;
        private TextView tv_cancel;
        private TextView tv_explain;
        private LinearLayout mlayoutPercent;
        private TextView mTvPercent;
        private TextView mTvPercent1;
        private TextView mTvGameSuffix;
        public ViewHolder(View view) {
            super(view);
            mRootView = findViewById(R.id.rootView);
            mTvTransactionTime = findViewById(R.id.tv_collection_time);
            mIvTransactionImage2 = findViewById(R.id.iv_transaction_image2);
            mTvTransactionGameName = findViewById(R.id.tv_transaction_game_name);
            mTvTransactionPrice = findViewById(R.id.tv_transaction_price);
            tv_genre_str = findViewById(R.id.tv_genre_str);
            tv_play_count = findViewById(R.id.tv_play_count);
            tv_server_info = findViewById(R.id.tv_server_info);
            tv_cancel = findViewById(R.id.tv_cancel);
            tv_explain = findViewById(R.id.tv_explain);
            mlayoutPercent = findViewById(R.id.layout_percent);
            mTvPercent = findViewById(R.id.tv_percent);
            mTvPercent1 = findViewById(R.id.tv_percent1);
            mTvGameSuffix = view.findViewById(R.id.tv_game_suffix);
        }
    }
}
