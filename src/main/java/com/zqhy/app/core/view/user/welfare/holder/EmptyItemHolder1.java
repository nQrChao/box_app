package com.zqhy.app.core.view.user.welfare.holder;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import trecyclerview.com.mvvm.R;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class EmptyItemHolder1 extends AbsItemHolder<EmptyDataVo, EmptyItemHolder1.ViewHolder> {
    public interface Clickable{
       void onClick();
    }
    Clickable mClick;

    public void setMClick(Clickable mClick) {
        this.mClick = mClick;
    }

    public EmptyItemHolder1(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull EmptyDataVo item) {
        if (TextUtils.isEmpty(item.getEmptyWord())) {
            holder.mTvErrorDesc.setVisibility(View.GONE);
        } else {
            holder.mTvErrorDesc.setVisibility(View.VISIBLE);
            holder.mTvErrorDesc.setOnClickListener(v -> {
                if (mClick!=null){
                    mClick.onClick();
                }
            });
            try {
                holder.mTvErrorDesc.setText(item.getEmptyWord());
            } catch (Exception e) {
                e.printStackTrace();
                holder.mTvErrorDesc.setVisibility(View.GONE);
            }
        }
        try {
            holder.mIvErrorIcon.setImageResource(item.getEmptyResourceId());
            if (item.getOnLayoutListener() != null) {
                item.getOnLayoutListener().onLayout(holder);
            }

            int heightParam;
            int paddingTop;
            switch (item.getLayoutWidth()) {
                case EmptyDataVo.LAYOUT_MATCH_PARENT:
                    heightParam = ViewGroup.LayoutParams.MATCH_PARENT;
                    paddingTop = 0;
                    break;
                case EmptyDataVo.LAYOUT_WRAP_CONTENT:
                    heightParam = ViewGroup.LayoutParams.WRAP_CONTENT;
                    paddingTop = item.getPaddingTop();
                    break;
                default:
                    heightParam = ViewGroup.LayoutParams.MATCH_PARENT;
                    paddingTop = 0;
                    break;
            }
            holder.mLlRootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightParam));
            holder.mLlRootView.setPadding(0, paddingTop, 0, 0);
            if (item.isBgWhite()){
                holder.mLlRootView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.empty_data_view2;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull EmptyItemHolder1.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        RecyclerView.LayoutParams clp = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
        if (clp instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) clp).setFullSpan(true);
        }
    }

    public class ViewHolder extends AbsHolder {

        private LinearLayout mLlRootView;
        private ImageView mIvErrorIcon;
        private TextView mTvErrorDesc;

        public ViewHolder(View view) {
            super(view);
            mLlRootView = view.findViewById(R.id.ll_rootView);
            mIvErrorIcon = view.findViewById(R.id.iv_error_icon);
            mTvErrorDesc = view.findViewById(R.id.tv_error_desc);

            mLlRootView.setBackgroundColor(ContextCompat.getColor(mContext, com.zqhy.app.core.R.color.transparent));
        }
    }
}
