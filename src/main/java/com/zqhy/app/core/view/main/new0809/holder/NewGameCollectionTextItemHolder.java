package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zqhy.app.adapter.abs.AbsAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.new0809.GameCollectionTextVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2021/8/12 0012-10:44
 * @description
 */
public class NewGameCollectionTextItemHolder extends BaseItemHolder<GameCollectionTextVo, NewGameCollectionTextItemHolder.ViewHolder> {

    public NewGameCollectionTextItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_pager_game_collection_text_new;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameCollectionTextVo item) {
        if(!TextUtils.isEmpty(item.description)){
            holder.mLlSubTitle.setVisibility(View.VISIBLE);
            holder.mTvSubTitle.setText(item.description);
        }else{
            holder.mLlSubTitle.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(item.sub_title)){
            List<GameCollectionTextVo> str = new ArrayList<>();
            for (int i = 0; i < item.sub_title.length(); i++) {
                GameCollectionTextVo gameCollectionTextVo = new GameCollectionTextVo();
                gameCollectionTextVo.title = item.sub_title.substring(i, i+1);
                gameCollectionTextVo.title_color = item.title_color;
                gameCollectionTextVo.title_border_color = item.title_border_color;
                str.add(gameCollectionTextVo);
            }
            if (str.size() > 8){
                holder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 8));
            }else {
                holder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, str.size() == 0? 1: str.size()));
            }
            holder.mRecyclerView.setAdapter(new MyAdapter(mContext, str));
        }
    }

    public class ViewHolder extends AbsHolder {
        private TextView     mTvSubTitle;
        private LinearLayout mLlSubTitle;
        private RecyclerView mRecyclerView;
        public ViewHolder(View view) {
            super(view);
            mTvSubTitle = findViewById(R.id.tv_sub_title);
            mLlSubTitle = findViewById(R.id.ll_sub_title);
            mRecyclerView = findViewById(R.id.recycler_view);
        }
    }

    class MyAdapter extends AbsAdapter<GameCollectionTextVo> {

        public MyAdapter(Context context, List<GameCollectionTextVo> labels) {
            super(context, labels);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, GameCollectionTextVo data, int position) {
            Holder viewHolder = (Holder) holder;
            viewHolder.mTvTitle.setText(data.title);
            try {
                viewHolder.mTvTitle.setTextColor(Color.parseColor(data.title_color));
                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(ScreenUtil.dp2px(mContext, 5));
                gd.setColor(Color.parseColor("#FFFFFF"));
                gd.setStroke(1, Color.parseColor(data.title_border_color));
                viewHolder.mTvTitle.setBackground(gd);
            }catch (Exception e){}
            if (position%7 != 0){
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)viewHolder.mTvTitle.getLayoutParams();
                layoutParams.setMargins(0, 0, ScreenUtil.dp2px(mContext, 5), ScreenUtil.dp2px(mContext, 5));
                viewHolder.mTvTitle.setLayoutParams(layoutParams);
            }
        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_game_figure_push_title_item;
        }

        @Override
        public AbsViewHolder createViewHolder(View view) {
            return new Holder(view);
        }

        class Holder extends AbsViewHolder{
            private TextView           mTvTitle;
            public Holder(View view) {
                super(view);
                mTvTitle = view.findViewById(R.id.tv_title);
            }
        }
    }
}
