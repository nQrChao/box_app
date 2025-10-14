package com.zqhy.app.core.view.user.welfare.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.welfare.GiftCardFootVo;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class GiftCardFooterItemHolder extends AbsItemHolder<GiftCardFootVo, GiftCardFooterItemHolder.ViewHolder> {
    public GiftCardFooterItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GiftCardFootVo item) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_gift_card_footer;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }
}
