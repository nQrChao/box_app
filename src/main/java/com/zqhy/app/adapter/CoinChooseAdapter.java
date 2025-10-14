package com.zqhy.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zqhy.app.adapter.abs.AbsChooseAdapter;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/13
 */

public class CoinChooseAdapter extends AbsChooseAdapter<String> {


    public CoinChooseAdapter(Context context, List<String> labels) {
        super(context, labels);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_coin_choose;
    }

    @Override
    public AbsViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, String data, int position) {
        String label = data;
        ViewHolder viewHolder = (ViewHolder) holder;
        String[] splits = label.split("@");
        String split1 = "";
        String split2 = "";
        if (splits.length > 0) {
            split1 = splits[0];
        }
        if (splits.length > 1) {
            split2 = splits[1];
        }
        viewHolder.mTvPtb.setText(split1);
        viewHolder.mTvPtb.setTextSize(21);
        viewHolder.itemView.setId(position);

        if (position == mSelectedItem) {
            viewHolder.mTvPtb.setTextColor(Color.parseColor("#0080FF"));
            viewHolder.mTvPtbUnit.setTextColor(Color.parseColor("#0080FF"));
            viewHolder.mFlContainer.setBackgroundResource(R.drawable.shape_fafafa_15_radius_with_line_0080ff);
        } else {
            viewHolder.mTvPtb.setTextColor(Color.parseColor("#232323"));
            viewHolder.mTvPtbUnit.setTextColor(Color.parseColor("#232323"));
            viewHolder.mFlContainer.setBackgroundResource(R.drawable.shape_fafafa_15_radius);
        }
    }


    class ViewHolder extends AbsViewHolder {
        private FrameLayout mFlContainer;
        private TextView    mTvPtb;
        private TextView    mTvPtbUnit;

        public ViewHolder(View itemView) {
            super(itemView);
            mFlContainer = itemView.findViewById(R.id.fl_container);
            mTvPtb = itemView.findViewById(R.id.tv_ptb);
            mTvPtbUnit = itemView.findViewById(R.id.tv_ptb_unit);
        }
    }
}
