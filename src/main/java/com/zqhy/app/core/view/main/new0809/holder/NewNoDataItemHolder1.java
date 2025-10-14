package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.view.main.MainActivity;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2021/8/17-17:32
 * @description
 */
public class NewNoDataItemHolder1 extends AbsItemHolder<NoMoreDataVo, NewNoDataItemHolder1.ViewHolder> {
    public NewNoDataItemHolder1(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_no_more_data_1;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull NoMoreDataVo item) {
        viewHolder.findViewById(R.id.tv_more).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.putExtra("tab_id", 1);
            mContext.startActivity(intent);
        });
    }

    public class ViewHolder extends AbsHolder {
        private TextView mTvText;

        public ViewHolder(View view) {
            super(view);
            mTvText = findViewById(R.id.tv_more);

        }
    }
}
