package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.new0809.MainXingYouDataVo;
import com.zqhy.app.core.view.main.holder.GameNormalItemHolder;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2021/8/14 0014-10:38
 * @description
 */
public class CurrentRecommondItemHolder extends BaseItemHolder<MainXingYouDataVo.CurrentRecommendDataBeanVo, CurrentRecommondItemHolder.ViewHolder> {

    public CurrentRecommondItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_current_recommond;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainXingYouDataVo.CurrentRecommendDataBeanVo item) {
        if (TextUtils.isEmpty(item.module_title) && TextUtils.isEmpty(item.module_title_two)) {
            holder.mTvTitle.setVisibility(View.GONE);
        } else {
            holder.mTvTitle.setVisibility(View.VISIBLE);
            String title = "";
            if (!TextUtils.isEmpty(item.module_title)){
                title += item.module_title;
            }
            if (!TextUtils.isEmpty(item.module_title_two)){
                title += item.module_title_two;
            }
            SpannableString spannableString = new SpannableString(title);
            if (!TextUtils.isEmpty(item.module_title) && !TextUtils.isEmpty(item.module_title_color)){
                try {
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(item.module_title_color)), 0, item.module_title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }catch (Exception e){}
            }
            if (!TextUtils.isEmpty(item.module_title_two) && !TextUtils.isEmpty(item.module_title_two_color)){
                try {
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(item.module_title_two_color)), title.length() - item.module_title_two.length(), title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }catch (Exception e){}
            }
            holder.mTvTitle.setText(spannableString);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);

        BaseRecyclerAdapter mAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(GameInfoVo.class, new GameNormalItemHolder(mContext, false, Color.parseColor("#00000000")))
                .build().setTag(R.id.tag_fragment, _mFragment).setTag(R.id.tag_sub_fragment, _mFragment);
        holder.mRecyclerView.setAdapter(mAdapter);
        mAdapter.clear();
        if (item.data.size() > 0) mAdapter.addData(item.data.get(0));
    }


    public class ViewHolder extends AbsHolder {
        private TextView mTvTitle;
        private RecyclerView mRecyclerView;

        public ViewHolder(View view) {
            super(view);
            mTvTitle = view.findViewById(R.id.tv_title);
            mRecyclerView = view.findViewById(R.id.recycler_view);
        }
    }
}
