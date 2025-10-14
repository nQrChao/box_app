package com.zqhy.app.base;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zqhy.app.base.holder.VHolder;
import com.zqhy.app.base.viewtype.ViewTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.jcodecraeer.xrecyclerview.util.Preconditions.checkNotNull;

/**
 * @author Administrator
 * @date 2018/11/1
 */

public class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnItemClickListener onItemClickListener;

    private OnItemLongClickListener onItemLongClickListener;

    private List<T> dataList;

    private Context context;

    protected Context getContext() {
        return context;
    }

    private LayoutInflater layoutInflater;

    private ViewTypes viewTypes;

    public HashMap<Integer, Object> tags = new HashMap<>();

    public BaseRecyclerAdapter(BaseRecyclerAdapter.Builder builder) {
        checkNotNull(builder);
        this.dataList = new ArrayList<>();
        this.viewTypes = builder.viewTypes;
    }


    public List<?> getItems() {
        return dataList;
    }

    public void clear() {
        dataList.clear();
    }

    public List<T> getData(){
        return dataList;
    }

    public void setDatas(List<T> datas) {
        checkNotNull(datas);
        this.dataList = datas;
    }

    public void addData(T data) {
        checkNotNull(data);
        this.dataList.add(data);
        notifyItemInserted(this.dataList.size() - 1);
    }

    public void addData(int index, T data) {
        checkNotNull(data);
        this.dataList.add(index, data);
        notifyItemInserted(index);
    }

    public void addAllData(List<T> datas) {
        checkNotNull(datas);
        this.dataList.addAll(datas);
        notifyItemRangeInserted(this.dataList.size() - datas.size(), this.dataList.size());
    }

    public void remove(int position) {
        this.dataList.remove(position);
        notifyItemMoved(position, 0);
    }

    public void remove(T data) {
        checkNotNull(data);
        int position = dataList.indexOf(data);
        this.dataList.remove(data);
        notifyItemMoved(position, 0);
    }

    /**
     * 置顶
     *
     * @param data
     */
    public void stick(T data) {
        checkNotNull(data);
        int position = dataList.indexOf(data);
        this.dataList.remove(data);
        this.dataList.add(0, data);
        notifyItemMoved(position, 0);
    }


    public ViewTypes getTypes() {
        return viewTypes;
    }


    public BaseRecyclerAdapter setTag(int tag, Object mObject) {
        if (mObject != null) {
            tags.put(tag, mObject);
        }
        return this;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (null == layoutInflater) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        VHolder<?, ?> vHolder = viewTypes.getItemView(viewType);

        vHolder.setTags(tags);
        return vHolder.onCreateViewHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder(holder, position, Collections.emptyList());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        VHolder vHolder = viewTypes.getItemView(holder.getItemViewType());
        vHolder.setPosition(position);
        vHolder.setListSize(getItemCount());
        vHolder.onBindViewHolder(holder, dataList.get(position), payloads);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClickListener(v, position, dataList.get(position));
            }
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClickListener(v, position, dataList.get(position));
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override
    public final long getItemId(int position) {
        int viewType = getItemViewType(position);
        VHolder itemView = viewTypes.getItemView(viewType);
        return itemView.getItemId(dataList.get(position));
    }


    private final int TYPE_REFRESH_HEADER = 10000;

    @Override
    public final void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        if (holder.getItemViewType() < TYPE_REFRESH_HEADER) {
            onViewByHolder(holder).onViewRecycled(holder);
        }
    }


    @Override
    public final boolean onFailedToRecycleView(@NonNull RecyclerView.ViewHolder holder) {
        if (holder.getItemViewType() < TYPE_REFRESH_HEADER) {
            return onViewByHolder(holder).onFailedToRecycleView(holder);
        }
        return false;
    }


    @Override
    public final void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        if (holder.getItemViewType() < TYPE_REFRESH_HEADER) {
            onViewByHolder(holder).onViewAttachedToWindow(holder);
        }
    }


    @Override
    public final void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        if (holder.getItemViewType() < TYPE_REFRESH_HEADER) {
            onViewByHolder(holder).onViewDetachedFromWindow(holder);
        }
    }

    private VHolder onViewByHolder(@NonNull RecyclerView.ViewHolder holder) {
        return viewTypes.getItemView(holder.getItemViewType());
    }

    @Override
    public int getItemViewType(int position) {
        int mViewType = getTypeOfIndex(dataList.get(position));
        return mViewType;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }


    public interface OnItemClickListener {
        void onItemClickListener(View v, int position, Object data);
    }

    public interface OnItemLongClickListener {
        void onItemLongClickListener(View v, int position, Object data);
    }


    public int getTypeOfIndex(@NonNull Object item) {
        //获取储存Item类型class集合的索引
        int index = this.viewTypes.getClassIndexOf(item.getClass());
        return index;
    }

    public static class Builder<T> {
        /**
         * item类型容器
         */
        private ViewTypes viewTypes;

        private Class<? extends T> clazz;

        private VHolder<T, ?>[] vHolders;

        public Builder() {
            viewTypes = new ViewTypes();
        }

        /**
         * 数据类型一对一
         *
         * @param clazz
         * @param vHolder
         * @return
         */
        public Builder bind(Class<? extends T> clazz, VHolder vHolder) {
            checkNotNull(clazz);
            checkNotNull(vHolder);
            this.viewTypes.save(clazz, vHolder);
            return this;
        }

        /**
         * 数据类型一对多
         *
         * @param clazz
         * @param vHolders
         * @return
         */
        public Builder bindArray(Class<? extends T> clazz, VHolder... vHolders) {
            checkNotNull(clazz);
            checkNotNull(vHolders);
            this.clazz = clazz;
            this.vHolders = vHolders;
            return this;
        }

        public BaseRecyclerAdapter build() {
            return new BaseRecyclerAdapter(this);
        }

    }
}
