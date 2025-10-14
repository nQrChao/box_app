package com.zqhy.app.adapter.abs;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/13
 */

public abstract class AbsChooseAdapter<T> extends AbsAdapter<T> {

    protected int mSelectedItem = -1;

    public AbsChooseAdapter(Context context, List<T> labels) {
        super(context, labels);
    }

    public void releaseSelected() {
        mSelectedItem = -1;
        notifyDataSetChanged();
    }

    public void selectPosition(int position) {
        mSelectedItem = position;
        notifyDataSetChanged();
    }

    public T getSelectItem(){
        if(mSelectedItem == -1){
            return null;
        }
        return mLabels.get(mSelectedItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setOnClickListener(v -> {
            mSelectedItem = position;
            notifyDataSetChanged();
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, mSelectedItem, mLabels.get(mSelectedItem));
            }
        });
    }

}
