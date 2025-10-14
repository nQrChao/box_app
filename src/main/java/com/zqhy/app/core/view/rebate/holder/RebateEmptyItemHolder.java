package com.zqhy.app.core.view.rebate.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.rebate.RebateEmptyDataVo;
import com.zqhy.app.newproject.R;

/**
 *
 * @author Administrator
 * @date 2018/11/15
 */

public class RebateEmptyItemHolder extends AbsItemHolder<RebateEmptyDataVo,RebateEmptyItemHolder.ViewHolder>{

    public RebateEmptyItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull RebateEmptyDataVo item) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_rebate_empty;
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
