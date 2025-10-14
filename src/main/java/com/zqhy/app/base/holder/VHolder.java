package com.zqhy.app.base.holder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/8
 */

public abstract class VHolder<T, VH extends RecyclerView.ViewHolder> {

    protected HashMap<Integer, Object> tags;

    protected int position;
    protected int listSize;

    public abstract @NonNull
    VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent);


    protected abstract void onBindViewHolder(@NonNull VH holder, @NonNull T item);


    public void onBindViewHolder(@NonNull VH holder, @NonNull T item, @NonNull List<Object> payloads) {
        holder.itemView.setTag(item);
        onBindViewHolder(holder, item);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }


    public int getItemPostion() {
        return position;
    }

    public final int getPosition(@NonNull final RecyclerView.ViewHolder holder) {
        return holder.getAdapterPosition();
    }

    public long getItemId(@NonNull T item) {
        return item.hashCode();
    }


    public void onViewRecycled(@NonNull VH holder) {
    }


    public boolean onFailedToRecycleView(@NonNull VH holder) {
        return false;
    }


    public void onViewAttachedToWindow(@NonNull VH holder) {
    }


    public void onViewDetachedFromWindow(@NonNull VH holder) {
    }

    public void setTags(HashMap<Integer, Object> tags) {
        this.tags = tags;
    }

}
