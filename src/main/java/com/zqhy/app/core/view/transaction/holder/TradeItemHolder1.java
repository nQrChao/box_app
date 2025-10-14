package com.zqhy.app.core.view.transaction.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.donkingliang.imageselector.entry.Image;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoVo1;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.transaction.PreviewActivity1;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.widget.imageview.ClipRoundImageView;

import java.util.ArrayList;

/**
 * @author Administrator
 */
public class TradeItemHolder1 extends AbsItemHolder<TradeGoodInfoVo1, TradeItemHolder1.ViewHolder> {

    protected float density;

    public TradeItemHolder1(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_transaction_list1;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TradeGoodInfoVo1 item) {
        holder.mTvTransactionTime.setTextColor(ContextCompat.getColor(mContext, R.color.color_9b9b9b));
        String regex = "yyyy-MM-dd HH:mm:ss";
        if (item.getIsSelled() == 2) {
            regex = "成交时间：MM-dd HH:mm:ss";
            holder.mTvTransactionTime.setTextColor(ContextCompat.getColor(mContext, R.color.color_3478f6));
        }
        holder.mTvTransactionTime.setText(CommonUtils.formatTimeStamp(item.getShow_time() * 1000, regex));
        if (item.getPic().size()>=2) {
            GlideUtils.loadRoundImage(mContext, item.getPic().get(0).getPic_path(), holder.mIvTransactionImage, R.mipmap.ic_placeholder);
            GlideUtils.loadRoundImage(mContext, item.getPic().get(1).getPic_path(), holder.mIvTransactionImage1, R.mipmap.ic_placeholder);
        }else{
            if (item.getPic().size()>0) {
                GlideUtils.loadRoundImage(mContext, item.getPic().get(0).getPic_path(), holder.mIvTransactionImage, R.mipmap.ic_placeholder);
                holder.mIvTransactionImage1.setVisibility(View.INVISIBLE);
            }else {
                holder.mIvTransactionImage.setVisibility(View.INVISIBLE);
                holder.mIvTransactionImage1.setVisibility(View.INVISIBLE);
            }
        }
        /*if (item.getGoods_type() != 2){
            if (item.getPic().size()>=2) {
                holder.mIvTransactionImage.setVisibility(View.VISIBLE);
                holder.mIvTransactionImage1.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams layoutParams = holder.mIvTransactionImage.getLayoutParams();
                layoutParams.width = ScreenUtil.dp2px(mContext, 100);
                layoutParams.height = ScreenUtil.dp2px(mContext, 100);
                holder.mIvTransactionImage.setLayoutParams(layoutParams);
                GlideUtils.loadRoundImage(mContext, item.getPic().get(0).getPic_path(), holder.mIvTransactionImage, R.mipmap.ic_placeholder);
                GlideUtils.loadRoundImage(mContext, item.getPic().get(1).getPic_path(), holder.mIvTransactionImage1, R.mipmap.ic_placeholder);
            }else{
                if (item.getPic().size()==1) {
                    ViewGroup.LayoutParams layoutParams = holder.mIvTransactionImage.getLayoutParams();
                    layoutParams.width = ScreenUtil.dp2px(mContext, 210);
                    layoutParams.height = ScreenUtil.dp2px(mContext, 100);
                    holder.mIvTransactionImage.setLayoutParams(layoutParams);
                    GlideUtils.loadRoundImage(mContext, item.getPic().get(0).getPic_path(), holder.mIvTransactionImage, R.mipmap.ic_placeholder);
                    holder.mIvTransactionImage1.setVisibility(View.GONE);
                }else {
                    holder.mIvTransactionImage.setVisibility(View.INVISIBLE);
                    holder.mIvTransactionImage1.setVisibility(View.INVISIBLE);
                }
            }
        }else {
            if (item.getPic().size()>=1) {
                ViewGroup.LayoutParams layoutParams = holder.mIvTransactionImage.getLayoutParams();
                layoutParams.width = ScreenUtil.dp2px(mContext, 210);
                layoutParams.height = ScreenUtil.dp2px(mContext, 100);
                holder.mIvTransactionImage.setLayoutParams(layoutParams);
                GlideUtils.loadRoundImage(mContext, item.getPic().get(0).getPic_path(), holder.mIvTransactionImage, R.mipmap.ic_placeholder);
                holder.mIvTransactionImage1.setVisibility(View.GONE);
            }else {
                holder.mIvTransactionImage.setVisibility(View.INVISIBLE);
                holder.mIvTransactionImage1.setVisibility(View.INVISIBLE);
            }
        }*/

        GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mIvTransactionImage2, R.mipmap.ic_placeholder);
//        holder.mTvTransactionTitle.setText(item.getGoods_title());
        holder.mTvTransactionGameName.setText(item.getGamename());
        holder.mTvTransactionPrice.setText(item.getGoods_price());

        holder.mTvTransactionXhRecharge.setText(CommonUtils.saveTwoSizePoint(item.getXh_pay_game_total()));
        if ("1".equals(item.getGame_type())){
            if (item.getProfit_rate() <= 0.1 && item.getProfit_rate() > 0.01){
                holder.mlayoutPercent.setVisibility(View.GONE);
                holder.mTvPercent.setText("0"+CommonUtils.saveTwoSizePoint(item.getProfit_rate()*10)+"折");
                holder.mTvPercent1.setText("抄底");
            }else if (item.getProfit_rate() <= 0.2 && item.getProfit_rate()>0.1){
                holder.mlayoutPercent.setVisibility(View.GONE);
                holder.mTvPercent.setText(CommonUtils.saveTwoSizePoint(item.getProfit_rate()*10)+"折");
                holder.mTvPercent1.setText("捡漏");
            }else {
                holder.mlayoutPercent.setVisibility(View.GONE);
            }
        }else {
            holder.mlayoutPercent.setVisibility(View.GONE);
        }

//        holder.tv_genre_str.setText(item.getGenre_str());
        holder.tv_explain.setText(item.getGoods_description());
//        holder.tv_play_count.setText(" • "+item.getPlay_count()+"人在玩");
        holder.tv_server_info.setText("区服: "+item.getServer_info());

        if (!TextUtils.isEmpty(item.getGame_suffix())){//游戏后缀
            holder.mTvGameSuffix.setVisibility(View.VISIBLE);
            holder.mTvGameSuffix.setText(item.getGame_suffix());
        }else {
            holder.mTvGameSuffix.setVisibility(View.GONE);
        }

        holder.mIvTransactionImage.setOnClickListener(v -> {
            if (_mFragment != null) {
                //预览图片
                ArrayList<Image> images = new ArrayList();
                for (TradeGoodInfoVo1.PicBean picBean : item.getPic()) {
                    Image image = new Image();
                    image.setType(1);
                    image.setHigh_path(picBean.getPic_path());
                    image.setPath(picBean.getPic_path());
                    images.add(image);
                }
                PreviewActivity1.openActivity(_mFragment.getActivity(), images, true, 0, false, item.getGid(), item.getGameid());
            }
        });
        holder.mIvTransactionImage1.setOnClickListener(v -> {
            if (_mFragment != null) {
                //预览图片
                ArrayList<Image> images = new ArrayList();
                for (TradeGoodInfoVo1.PicBean picBean : item.getPic()) {
                    Image image = new Image();
                    image.setType(1);
                    image.setHigh_path(picBean.getPic_path());
                    image.setPath(picBean.getPic_path());
                    images.add(image);
                }
                PreviewActivity1.openActivity(_mFragment.getActivity(), images, true, 1, false, item.getGid(), item.getGameid());
            }
        });
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mRootView;
        private TextView mTvTransactionTime;
        private ClipRoundImageView mIvTransactionImage;
        private ClipRoundImageView mIvTransactionImage1;
        private ClipRoundImageView mIvTransactionImage2;
//        private TextView mTvTransactionTitle;
        private TextView mTvTransactionGameName;
        private TextView mTvTransactionPrice;
        private TextView mTvTransactionXhRecharge;
        private TextView mTvPercent;
        private TextView mTvPercent1;
        private TextView tv_genre_str;
        private TextView tv_play_count;
        private TextView tv_server_info;
        private TextView tv_explain;

        private TextView mTvGameSuffix;
        private LinearLayout mlayoutPercent;

        public ViewHolder(View view) {
            super(view);
            mRootView = findViewById(R.id.rootView);
            mTvTransactionTime = findViewById(R.id.tv_transaction_time);
            mIvTransactionImage = findViewById(R.id.iv_transaction_image);
            mIvTransactionImage1 = findViewById(R.id.iv_transaction_image1);
            mIvTransactionImage2 = findViewById(R.id.iv_transaction_image2);
//            mTvTransactionTitle = findViewById(R.id.tv_transaction_title);
            mTvTransactionGameName = findViewById(R.id.tv_transaction_game_name);
            mTvTransactionPrice = findViewById(R.id.tv_transaction_price);
            mTvTransactionXhRecharge = findViewById(R.id.tv_transaction_xh_recharge);
            mTvPercent = findViewById(R.id.tv_percent);
            mTvPercent1 = findViewById(R.id.tv_percent1);
            mlayoutPercent = findViewById(R.id.layout_percent);
            tv_genre_str = findViewById(R.id.tv_genre_str);
            tv_play_count = findViewById(R.id.tv_play_count);
            tv_explain = findViewById(R.id.tv_explain);
            tv_server_info = findViewById(R.id.tv_server_info);
            mTvGameSuffix = view.findViewById(R.id.tv_game_suffix);
//            GradientDrawable gd = new GradientDrawable();
//            gd.setCornerRadius(4 * density);
//            gd.setColor(Color.parseColor("#21F5BE43"));
//            mTvTransactionXhRecharge.setBackground(gd);
//            mTvTransactionXhRecharge.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff8f19));
        }
    }

}
