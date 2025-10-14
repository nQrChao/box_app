package com.zqhy.app.base.viewtype;

import com.zqhy.app.base.holder.VHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/8
 */

public class ViewTypes {

    private final List<Class<?>> classes;

    private final List<VHolder<?, ?>> vHolders;

    public ViewTypes() {
        this.classes = new ArrayList<>();
        this.vHolders = new ArrayList<>();
    }


    public <T> void save(Class<? extends T> clazz, VHolder<T, ?> vHolder) {
        classes.add(clazz);
        vHolders.add(vHolder);
    }

    public int size() {
        return classes.size();
    }


    public int getClassIndexOf(final Class<?> clazz) {
        int index = classes.indexOf(clazz);
        if (index != -1) {
            return index;
        }
        return -1;
    }

    public VHolder<?, ?> getItemView(int index) {
        return vHolders.get(index);
    }
}
