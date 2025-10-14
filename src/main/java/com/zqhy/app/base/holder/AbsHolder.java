package com.zqhy.app.base.holder;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 *
 * @author Administrator
 * @date 2018/11/8
 */

public class AbsHolder extends RecyclerView.ViewHolder{
    private final SparseArray<View> views;

    public View convertView;


    public AbsHolder(final View view) {
        super(view);
        this.views = new SparseArray<>();
        this.convertView = view;
    }

    public <T extends View> T findViewById(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

}
