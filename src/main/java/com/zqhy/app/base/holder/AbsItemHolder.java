package com.zqhy.app.base.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/8
 */

public abstract class AbsItemHolder<T, VH extends AbsHolder> extends VHolder<T, VH> {

    protected Context mContext;

    public AbsItemHolder(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view;
        if (INFLATER_TYPE_NEED_ROOT()) {
            view = inflater.inflate(getLayoutResId(), parent, false);
        } else {
            view = inflater.inflate(getLayoutResId(), null);
        }
        if (tags != null && tags.size() > 0) {
            for (int tag : tags.keySet()) {
                view.setTag(tag, tags.get(tag));
            }
        }
        initView(view);
        return createViewHolder(view);
    }

    protected BaseFragment _mFragment;

    /**
     * 初始化设置
     */
    protected void initView(View view) {
        _mFragment = (BaseFragment) view.getTag(R.id.tag_fragment);
    }

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
    public abstract VH createViewHolder(View view);

    protected boolean INFLATER_TYPE_NEED_ROOT() {
        return true;
    }
}
