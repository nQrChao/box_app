package com.zqhy.app.core.view.transaction.holder;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.transaction.TradeHeaderVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class TradeHeaderItemHolder extends AbsItemHolder<TradeHeaderVo, TradeHeaderItemHolder.ViewHolder> {

    public TradeHeaderItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_collection_header;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TradeHeaderVo item) {
        float density = ScreenUtil.getScreenDensity(mContext);
        holder.mTvText.setPadding((int) (density * 12), (int) (density * 8), (int) (density * 12), (int) (density * 8));
        holder.mTvText.setBackgroundColor(Color.parseColor("#FFF3E6"));
        holder.mTvText.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff8f19));
        holder.mTvText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        holder.mTvText.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(mContext), LinearLayout.LayoutParams.WRAP_CONTENT);
        holder.mTvText.setLayoutParams(params);

        holder.mTvText.setText(item.getDescription());
    }

    public class ViewHolder extends AbsHolder {
        private TextView mTvText;

        public ViewHolder(View view) {
            super(view);
            mTvText = findViewById(R.id.tv_text);

        }
    }


}
