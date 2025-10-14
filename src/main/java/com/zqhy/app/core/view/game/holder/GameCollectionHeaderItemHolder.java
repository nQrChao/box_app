package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameCollectionHeaderVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.newproject.R;

/**
 *
 * @author Administrator
 * @date 2018/11/26
 */

public class GameCollectionHeaderItemHolder extends AbsItemHolder<GameCollectionHeaderVo,GameCollectionHeaderItemHolder.ViewHolder>{
    public GameCollectionHeaderItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull GameCollectionHeaderItemHolder.ViewHolder holder, @NonNull GameCollectionHeaderVo item) {
        float density = ScreenUtil.getScreenDensity(mContext);
        holder.mTvText.setPadding((int) (density * 12), (int) (density * 6), (int) (density * 12), (int) (density * 6));
        holder.mTvText.setBackgroundColor(Color.parseColor("#FFF3E6"));
        holder.mTvText.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff8f19));
        holder.mTvText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        holder.mTvText.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(mContext), LinearLayout.LayoutParams.WRAP_CONTENT);
        holder.mTvText.setLayoutParams(params);

        holder.mTvText.setText(item.getDescription());

    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_collection_header;
    }

    @Override
    public GameCollectionHeaderItemHolder.ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private TextView mTvText;
        public ViewHolder(View view) {
            super(view);
            mTvText = findViewById(R.id.tv_text);

        }
    }
}
