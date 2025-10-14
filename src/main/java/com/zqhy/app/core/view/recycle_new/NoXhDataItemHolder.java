package com.zqhy.app.core.view.recycle_new;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.recycle.NoXhDataVo;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2021/6/17-11:35
 * @description
 */
class NoXhDataItemHolder extends BaseItemHolder<NoXhDataVo, NoXhDataItemHolder.ViewHolder> {

    public NoXhDataItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_xh_no_data;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull NoXhDataVo item) {
       holder.mTvAction.setOnClickListener(v -> {
            if (_mFragment != null) _mFragment.startFragment(new XhUnRecoverableListFragment());
       });
    }

    public class ViewHolder extends AbsHolder {
        private TextView mTvAction;
        public ViewHolder(View view) {
            super(view);
            mTvAction = view.findViewById(R.id.tv_action);
        }
    }
}
