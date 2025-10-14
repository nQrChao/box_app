package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zqhy.app.adapter.abs.EmptyAdapter1;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.detail.GameActivityVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.view.game.GameDetaiActivityFragment;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/21
 */

public class NewGameActiviItemHolder extends AbsItemHolder<GameInfoVo, NewGameActiviItemHolder.ViewHolder>{


    public NewGameActiviItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameInfoVo gameInfoVo) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setSmoothScrollbarEnabled(true);
        holder.mRecyclerView.setNestedScrollingEnabled(false);
        holder.mRecyclerView.setLayoutManager(layoutManager);
        BaseRecyclerAdapter mAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(GameActivityVo.ItemBean.class, new NewGameDetailActiviItemHolder(mContext))
                .bind(EmptyDataVo.class, new EmptyItemHolder(mContext))
                .build().setTag(R.id.tag_fragment, _mFragment);

        List<EmptyDataVo> emptyDataVoList = new ArrayList<>();
        emptyDataVoList.add(new EmptyDataVo(R.mipmap.img_empty_data_2));
        EmptyAdapter1 emptyAdapter = new EmptyAdapter1(mContext, emptyDataVoList);

        holder.mRecyclerView.setAdapter(mAdapter);
        List<GameActivityVo.ItemBean> itemBeanList = new ArrayList<>();
        List<GameActivityVo.ItemBean> itemBeanList2 = new ArrayList<>();
        GameActivityVo gameActivityVo = gameInfoVo.getGameActivityVo();
        if (gameActivityVo.getItemCount() > 0) {
            itemBeanList.addAll(createNewsBeans(gameActivityVo));
            if (itemBeanList.size() > 2){
                itemBeanList2.add(itemBeanList.get(0));
                itemBeanList2.add(itemBeanList.get(1));
            }
        }else {
            holder.mTvCount.setText("0");
            holder.mRecyclerView.setAdapter(emptyAdapter);
        }
        if (itemBeanList.size() > 0){
            holder.mTvCount.setText(String.valueOf(itemBeanList.size()));
            if (itemBeanList.size() > 2){
                holder.mTvFold.setVisibility(View.VISIBLE);
                holder.mTvMore.setVisibility(View.VISIBLE);
                if (gameActivityVo.isFold()){
                    holder.mTvFold.setText("收起列表");
                    holder.mTvFold.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_game_detail_content_unfold), null);
                    mAdapter.setDatas(itemBeanList);
                    gameActivityVo.setFold(true);
                }else {
                    holder.mTvFold.setText("全部活动");
                    holder.mTvFold.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_game_detail_content_fold), null);
                    mAdapter.setDatas(itemBeanList2);
                    gameActivityVo.setFold(false);
                }
            }else {
                holder.mTvFold.setVisibility(View.GONE);
                //holder.mTvMore.setVisibility(View.GONE);
                mAdapter.setDatas(itemBeanList);
                gameActivityVo.setFold(true);
            }
        }else {
            holder.mTvCount.setText("0");
            //holder.mTvMore.setVisibility(View.GONE);
            holder.mRecyclerView.setAdapter(emptyAdapter);
        }
        holder.mTvMore.setOnClickListener(view -> {
            if (_mFragment != null) {
                _mFragment.startFragment(GameDetaiActivityFragment.newInstance(gameInfoVo.getGamename(), gameInfoVo.getGameid()));
            }
        });
        holder.mTvFold.setOnClickListener(view -> {
            if (gameActivityVo.isFold()){
                gameActivityVo.setFold(false);
                holder.mTvFold.setText("全部活动");
                holder.mTvFold.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_game_detail_content_fold), null);
                mAdapter.setDatas(itemBeanList2);
            }else {
                gameActivityVo.setFold(true);
                holder.mTvFold.setText("收起列表");
                holder.mTvFold.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_game_detail_content_unfold), null);
                mAdapter.setDatas(itemBeanList);
            }
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_activi_new;
    }

    /**
     * 创建new item
     *
     * @param gameActivityVo
     * @return
     */
    private List<GameActivityVo.ItemBean> createNewsBeans(GameActivityVo gameActivityVo) {
        List<GameActivityVo.ItemBean> topMenuInfoBeanList = new ArrayList<>();

        if (gameActivityVo.getActivity() == null) {
            return topMenuInfoBeanList;
        }

        for (GameInfoVo.NewslistBean newslistBean : gameActivityVo.getActivity()) {
            if ("normal".equals(newslistBean.getNews_status())){
                GameActivityVo.ItemBean itemBean = new GameActivityVo.ItemBean();
                itemBean.setType(1);
                itemBean.setNewslistBean(newslistBean);
                topMenuInfoBeanList.add(itemBean);
            }
        }
        return topMenuInfoBeanList;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private TextView      mTvCount;
        private TextView      mTvMore;
        private RecyclerView mRecyclerView;
        private TextView      mTvFold;

        public ViewHolder(View view) {
            super(view);
            mTvCount = view.findViewById(R.id.tv_count);
            mTvMore = view.findViewById(R.id.tv_more);
            mRecyclerView = view.findViewById(R.id.recyclerView);
            mTvFold = view.findViewById(R.id.tv_fold);
        }
    }
}
