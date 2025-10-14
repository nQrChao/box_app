package com.zqhy.app.adapter.abs;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import trecyclerview.com.mvvm.R;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/21
 */

public class EmptyAdapter extends AbsAdapter<EmptyDataVo> {

    public EmptyAdapter(Context context, List labels) {
        super(context, labels);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, EmptyDataVo emptyDataVo, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;

        holder.mTvErrorDesc.setVisibility(View.GONE);

        try{
            holder.mIvErrorIcon.setImageResource(emptyDataVo.getEmptyResourceId());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.empty_data_view;
    }

    @Override
    public AbsViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends AbsAdapter.AbsViewHolder {

        private ImageView mIvErrorIcon;
        private TextView mTvErrorDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            mIvErrorIcon = findViewById(R.id.iv_error_icon);
            mTvErrorDesc = findViewById(R.id.tv_error_desc);

        }
    }
}
