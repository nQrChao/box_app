package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.view.main.holder.GameNormalItemHolder;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2021/8/10 0010-13:20
 * @description
 */
public class GameNormalListItemHolder extends BaseItemHolder<GameListVo,GameNormalListItemHolder.ViewHolder> {

    public GameNormalListItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.layout_recycler_view;
    }

    private BaseFragment _mSubFragment;

    @Override
    protected void initView(View view) {
        super.initView(view);
        _mSubFragment = (BaseFragment) view.getTag(R.id.tag_sub_fragment);
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameListVo item) {
        if (mPagerAdapter == null) {
            initPager(holder);
        }
        mPagerAdapter.setDatas(item.getData());
        mPagerAdapter.notifyDataSetChanged();
    }

    private BaseRecyclerAdapter mPagerAdapter;

    private void initPager(ViewHolder holder) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.mRecyclerView.setLayoutManager(layoutManager);
        mPagerAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(GameInfoVo.class, new GameNormalItemHolder(mContext))
                .build().setTag(R.id.tag_fragment, _mFragment)
                .setTag(R.id.tag_sub_fragment, _mSubFragment);
        holder.mRecyclerView.setAdapter(mPagerAdapter);
    }

    public class ViewHolder extends AbsHolder {
        private RecyclerView mRecyclerView;
        public ViewHolder(View view) {
            super(view);
            mRecyclerView = findViewById(R.id.recycler_view);
        }
    }
}
