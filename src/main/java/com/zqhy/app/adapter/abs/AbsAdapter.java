package com.zqhy.app.adapter.abs;

import android.content.Context;
import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zqhy.app.adapter.OnItemClickListener;

import java.util.List;

import static com.jcodecraeer.xrecyclerview.util.Preconditions.checkNotNull;

/**
 * @author Administrator
 * @date 2018/11/13
 */

public abstract class AbsAdapter<T> extends RecyclerView.Adapter {

    protected Context mContext;

    protected List<T> mLabels;

    protected OnItemClickListener onItemClickListener;

    public AbsAdapter(Context context, List<T> labels) {
        mContext = context;
        mLabels = labels;

    }

    @Override
    public AbsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(getLayoutResId(), parent, false);
        return createViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, position,mLabels.get(position));
            }
        });
        onBindViewHolder(holder,mLabels.get(position),position);

    }

    /**
     * 绑定数据
     * @param holder
     * @param position
     * @param data
     */
    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder,T data,int position);

    /**
     * Layout
     *
     * @return
     */
    public abstract int getLayoutResId();

    /**
     * 创建ViewHolder
     *
     * @param view
     * @return
     */
    public abstract AbsViewHolder createViewHolder(View view);

    public void clear() {
        mLabels.clear();
    }

    public void setDatas(List<T> datas) {
        checkNotNull(datas);
        this.mLabels = datas;
    }

    public void addData(T data) {
        checkNotNull(data);
        this.mLabels.add(data);
        notifyItemInserted(this.mLabels.size() - 1);
    }

    public void addData(int index, T data) {
        checkNotNull(data);
        this.mLabels.add(index, data);
        notifyItemInserted(index);
    }

    public void addAllData(List<T> datas) {
        checkNotNull(datas);
        this.mLabels.addAll(datas);
        notifyItemRangeInserted(this.mLabels.size() - datas.size(), this.mLabels.size());
    }

    public void remove(int position) {
        this.mLabels.remove(position);
        notifyItemMoved(position, 0);
    }

    public void remove(T data) {
        checkNotNull(data);
        int position = mLabels.indexOf(data);
        this.mLabels.remove(data);
        notifyItemMoved(position, 0);
    }

    @Override
    public int getItemCount() {
        return mLabels == null ? 0 : mLabels.size();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public static class AbsViewHolder extends RecyclerView.ViewHolder {

        public AbsViewHolder(View itemView) {
            super(itemView);
        }

        public <T extends View> T findViewById(@IdRes int viewId) {
            return itemView.findViewById(viewId);
        }
    }
}
