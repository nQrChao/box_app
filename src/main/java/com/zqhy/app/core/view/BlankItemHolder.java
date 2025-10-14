package com.zqhy.app.core.view;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.nodata.BlankDataVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class BlankItemHolder extends AbsItemHolder<BlankDataVo, BlankItemHolder.ViewHolder> {

    public BlankItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_blank;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull BlankDataVo item) {
        int height = (int) (120* ScreenUtil.getScreenDensity(mContext));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,height);
        holder.mViewBlank.setLayoutParams(params);
    }

    public class ViewHolder extends AbsHolder {
        private View mViewBlank;
        public ViewHolder(View view) {
            super(view);
            mViewBlank = findViewById(R.id.view_blank);

        }
    }
}
