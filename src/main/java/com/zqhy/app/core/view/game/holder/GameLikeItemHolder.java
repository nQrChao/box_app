package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.detail.GameLikeListVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.core.view.main.holder.GameNormalItemHolder;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2018/11/20
 */

public class GameLikeItemHolder extends AbsItemHolder<GameLikeListVo, GameLikeItemHolder.ViewHolder> {

    public GameLikeItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameLikeListVo item) {
        holder.mRecyclerViewYoulike.setLayoutManager(new LinearLayoutManager(mContext));
        BaseRecyclerAdapter mAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(GameInfoVo.class, new GameNormalItemHolder(mContext))
                .build().setTag(R.id.tag_fragment, _mFragment).setTag(R.id.tag_sub_fragment, _mFragment);
        holder.mRecyclerViewYoulike.setAdapter(mAdapter);
        if (item != null && item.getLike_game_list() != null && !item.getLike_game_list().isEmpty()) {
            ArrayList<GameInfoVo> gameInfoVos = new ArrayList<>();
            if (item.getLike_game_list().size() > 5){
                for (int i = 0; i < 5; i++) {
                    gameInfoVos.add(item.getLike_game_list().get(i));
                }
            }else {
                gameInfoVos.addAll(item.getLike_game_list());
            }
            mAdapter.addAllData(gameInfoVos);
        }
        /*int totalSize = item.getLike_game_list().size();
        int rowSize = 2;
        ViewGroup.LayoutParams layoutParams = holder.mViewPager.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.height = ScreenUtil.dp2px(mContext, 100 * rowSize);
            holder.mViewPager.setLayoutParams(layoutParams);
        }

        holder.columnSize = totalSize % rowSize == 0 ? totalSize / rowSize : (totalSize / rowSize) + 1;
        holder.pageViewList.clear();
        int index = 0;
        for (int x = 0; x < holder.columnSize; x++) {
            List<GameInfoVo> infoVos = new ArrayList<>();
            for (int y = 0; y < rowSize && index < totalSize; y++) {
                infoVos.add(item.getLike_game_list().get(index));
                index++;
            }
            View pageView = holder.getPageView(infoVos);
            holder.pageViewList.add(pageView);
        }

        DynamicPagerAdapter adapter = new DynamicPagerAdapter(holder.pageViewList);
        holder.mViewPager.setAdapter(adapter);
        holder.mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (holder.mViewPager.getLayoutParams() != null) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.mViewPager.getLayoutParams();
                    if (position == holder.columnSize - 1) {
                        //last one
                        params.rightMargin = 0;
                    } else {
                        params.rightMargin = ScreenUtil.dp2px(mContext, 48);
                    }
                    holder.mViewPager.setLayoutParams(params);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

        holder.mViewPager.setOffscreenPageLimit(holder.pageViewList.size());
*/
        holder.mTvRefresh.setOnClickListener(v -> {
            if (_mFragment != null){
                ((GameDetailInfoFragment)_mFragment).getLikeList(false);
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_detail_like;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    public class ViewHolder extends AbsHolder {
        private RecyclerView mRecyclerViewYoulike;
        private ViewPager    mViewPager;
        private List<View>    pageViewList = new ArrayList<>();
        private int           columnSize;

        private TextView mTvRefresh;

        public ViewHolder(View view) {
            super(view);
            mRecyclerViewYoulike = findViewById(R.id.recyclerView_youlike);
            mViewPager = findViewById(R.id.view_pager);
            mTvRefresh = findViewById(R.id.tv_refresh);
        }

        protected View getPageView(List<GameInfoVo> item) {
            LinearLayout layout = new LinearLayout(mContext);
            layout.setOrientation(LinearLayout.VERTICAL);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(lp);
            layout.removeAllViews();
            for (int i = 0; i < item.size(); i++) {
                View itemView = createGameItemView(item.get(i));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.dp2px(mContext, 100));
                params.gravity = Gravity.CENTER;
                layout.addView(itemView, params);
            }
            return layout;
        }
    }

    private View createGameItemView(GameInfoVo gameInfoVo) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_game_normal, null);
        GameNormalItemHolder.ViewHolder viewHolder = new GameNormalItemHolder.ViewHolder(itemView);
        GameNormalItemHolder normalItemHolder = new GameNormalItemHolder(mContext, 50);
        HashMap<Integer, Object> map = new HashMap<>();
        map.put(R.id.tag_fragment, _mFragment);
        normalItemHolder.initViewHolder(viewHolder, map);
        normalItemHolder.onBindViewHolder(viewHolder, gameInfoVo);
        return itemView;
    }

    private class DynamicPagerAdapter extends PagerAdapter {
        private List<View>         pageViews;
        private Map<Integer, View> mMap;
        private int                Tag = 0;

        public DynamicPagerAdapter(List<View> pageViews) {
            this.pageViews = pageViews;
            mMap = new HashMap<>();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return Tag == 0 ? POSITION_UNCHANGED : POSITION_NONE;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            mMap.remove(position);
        }

        @Override
        public int getCount() {
            return pageViews == null ? 0 : pageViews.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Tag = 0;
            View itemView = pageViews.get(position);
            container.addView(itemView);
            mMap.put(position, itemView);
            return itemView;
        }

        @Override
        public void notifyDataSetChanged() {
            Tag = 1;
            super.notifyDataSetChanged();
        }

    }
}