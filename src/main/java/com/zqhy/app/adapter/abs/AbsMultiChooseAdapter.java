package com.zqhy.app.adapter.abs;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/13
 */

public abstract class AbsMultiChooseAdapter<T> extends AbsAdapter<T> {

    /**
     * key = position
     * value = mSelectedItem  0 未选中  1 选中
     */
    protected Map<Integer, Integer> mSelectStatus;

    public AbsMultiChooseAdapter(Context context, List<T> labels) {
        super(context, labels);
        mSelectStatus = new TreeMap<>();
    }

    public void releaseSelected() {
        mSelectStatus.clear();
        notifyDataSetChanged();
    }

    public void selectAllPosition() {
        int totalSize = mLabels.size();
        for (int i = 0; i < totalSize; i++) {
            mSelectStatus.put(i, 1);
        }
        notifyDataSetChanged();
    }

    public List<T> getSelectItems() {
        if (mSelectStatus == null) {
            return null;
        }

        List<T> result = new ArrayList<>();
        for (Integer key : mSelectStatus.keySet()) {
            Integer value = mSelectStatus.get(key);
            if (value == 1) {
                result.add(mLabels.get(key));
            }
        }
        return result;
    }


    /**
     * 是否是选中条目
     *
     * @param position
     * @return
     */
    protected boolean isSelectItem(int position) {
        for (Integer key : mSelectStatus.keySet()) {
            if (key == position) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setOnClickListener(v -> {
            if (mSelectStatus.containsKey(position)) {
                mSelectStatus.remove(position);
            } else {
                mSelectStatus.put(position, 1);
            }
            notifyDataSetChanged();
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, position, mLabels.get(position));
            }
        });
    }

}
