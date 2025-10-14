package com.zqhy.app.core.view.transaction.holder;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.transaction.TradeGoodDetailInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.imageview.ClipRoundImageView;

/**
 * @author Administrator
 */
public class TradePicItem extends AbsItemHolder<TradeGoodDetailInfoVo.PicListBean, TradePicItem.ViewHolder> {

    protected float density;

    public TradePicItem(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_transaction_pic;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull  TradeGoodDetailInfoVo.PicListBean item) {

        GlideUtils.loadRoundImage(mContext, item.getPic_path(), holder.mIvTransactionImage, R.mipmap.ic_placeholder);

    }

    public class ViewHolder extends AbsHolder {
        private ClipRoundImageView mIvTransactionImage;

        public ViewHolder(View view) {
            super(view);
            mIvTransactionImage = findViewById(R.id.iv_transaction_image);
        }
    }

}
