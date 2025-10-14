package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.new0809.GameCollectionTextVo;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2021/8/12 0012-10:44
 * @description
 */
public class GameCollectionTextItemHolder extends BaseItemHolder<GameCollectionTextVo,GameCollectionTextItemHolder.ViewHolder> {

    public GameCollectionTextItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_pager_game_collection_text;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameCollectionTextVo item) {

        if(!TextUtils.isEmpty(item.sub_title)){
            holder.mTvTitle.setVisibility(View.VISIBLE);
            holder.mTvTitle.setText(item.sub_title);
        }else{
            holder.mTvTitle.setVisibility(View.GONE);

        }
        if(!TextUtils.isEmpty(item.description)){
            holder.mTvSubTitle.setVisibility(View.VISIBLE);
            holder.mTvSubTitle.setText(item.description);
        }else{
            holder.mTvSubTitle.setVisibility(View.GONE);

        }
    }

    public class ViewHolder extends AbsHolder {
        private TextView mTvTitle;
        private TextView mTvSubTitle;
        public ViewHolder(View view) {
            super(view);
            mTvTitle = findViewById(R.id.tv_title);
            mTvSubTitle = findViewById(R.id.tv_sub_title);
        }
    }
}
