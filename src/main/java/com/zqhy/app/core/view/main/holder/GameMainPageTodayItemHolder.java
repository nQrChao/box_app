package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameMainPageTodayVo;
import com.zqhy.app.core.view.main.AbsMainGameListFragment;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.TitleTextView;

/**
 * @author leeham2734
 * @date 2020/11/23-14:07
 * @description
 */
public class GameMainPageTodayItemHolder extends AbsItemHolder<GameMainPageTodayVo, GameMainPageTodayItemHolder.ViewHolder> {


    public GameMainPageTodayItemHolder(Context context) {
        super(context);
    }


    @Override
    public int getLayoutResId() {
        return R.layout.item_game_bt_today_new;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    BaseRecyclerAdapter mAdapter;

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameMainPageTodayVo item) {
        holder.mTitleTextView.setText(item.getMainTitle());
        holder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(GameInfoVo.class, new GameNormalItemHolder(mContext, true))
                .build().setTag(R.id.tag_fragment, _mFragment);

        holder.mRecyclerView.setAdapter(mAdapter);
        mAdapter.clear();
        mAdapter.addAllData(item.getGameInfoVoList());

        if (item.isShowMore()) {
            holder.mTvMore.setVisibility(View.VISIBLE);
            holder.mTvMore.setOnClickListener(v -> {
                if(item.mCustomRouteListener != null){
                    item.mCustomRouteListener.onRoute();
                }else{
                    if (_mFragment != null && _mFragment instanceof AbsMainGameListFragment) {
                        //主界面-游戏-分类
                        ((AbsMainGameListFragment) _mFragment).goToMainGamePageByGenreId(item.getGame_type(), item.getGenre_id());
                    }
                }
            });
        } else {
            holder.mTvMore.setVisibility(View.GONE);
        }
    }

    public class ViewHolder extends AbsHolder {
        private TitleTextView mTitleTextView;
        private RecyclerView mRecyclerView;
        private TextView     mTvMore;

        public ViewHolder(View view) {
            super(view);
            mTitleTextView = findViewById(R.id.title_text_view);
            mRecyclerView = findViewById(R.id.recycler_view);
            mTvMore = findViewById(R.id.tv_more);
        }
    }
}
