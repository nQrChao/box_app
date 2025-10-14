package com.zqhy.app.core.view.main.holder.bt;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.mainpage.HomeBTGameIndexVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.main.AbsMainGameListFragment;
import com.zqhy.app.core.view.main.MainPageFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/8/20-13:45
 * @description
 */
public class GameBTGameGenreItemHolder extends AbsItemHolder<HomeBTGameIndexVo.GenreGameVo, GameBTGameGenreItemHolder.ViewHolder> {
    public GameBTGameGenreItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_bt_game_genre;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    private int indexPage = 1;
    private int pageSize  = 3;

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull HomeBTGameIndexVo.GenreGameVo item) {
        holder.mTvName.setText(item.getGenre_name());
        holder.mTvMore.setOnClickListener(view -> {
            if (_mFragment != null && _mFragment instanceof AbsMainGameListFragment) {
                //主界面-游戏-分类
                ((AbsMainGameListFragment) _mFragment).goToMainGamePageByGenreId(item.getGame_type(), item.getGenre_id());
            }
        });
        holder.mRecyclerView.setAdapter(new GameGenreAdapter(item.getList()));
        holder.mTvChangeList.setOnClickListener(view -> {
            if (_mFragment != null && _mFragment instanceof MainPageFragment) {
                indexPage++;
                int page = (indexPage % pageSize) + 1;
                ((MainPageFragment) _mFragment).getGenreGameByPage(page, item.getGenre_id());
            }
        });
    }


    protected class GameGenreAdapter extends RecyclerView.Adapter<GameGenreAdapter.ViewHolder> {

        private List<GameInfoVo> gameInfoVoList;

        public GameGenreAdapter(List<GameInfoVo> gameInfoVoList) {
            this.gameInfoVoList = gameInfoVoList;
        }

        @NonNull
        @Override
        public GameGenreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_game_bt_game_genre_item_game, null);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull GameGenreAdapter.ViewHolder holder, int position) {
            GameInfoVo gameInfoVo = gameInfoVoList.get(position);
            String image = gameInfoVo.getGameicon();
            GlideUtils.loadRoundImage(mContext, image, holder.mImage);

            holder.mTvGameTag.setVisibility(View.VISIBLE);

            GameInfoVo.GameLabelsBean labelsBean = gameInfoVo.getGame_top_label();
            if (labelsBean != null) {
                holder.mTvGameTag.setVisibility(View.VISIBLE);
                GradientDrawable gd = new GradientDrawable();
                float radius = 6 * ScreenUtil.getScreenDensity(mContext);
                gd.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius, 0, 0});
                gd.setColor(ContextCompat.getColor(mContext, R.color.color_fb1111));
                holder.mTvGameTag.setBackground(gd);
                holder.mTvGameTag.setText(labelsBean.getLabel_name());
            } else {
                holder.mTvGameTag.setVisibility(View.GONE);
            }
            holder.mTvGameName.setText(gameInfoVo.getGamename());
            holder.itemView.setOnClickListener(view -> {
                if (_mFragment != null) {
                    _mFragment.goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
                }
            });
        }

        @Override
        public int getItemCount() {
            return gameInfoVoList == null ? 0 : gameInfoVoList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private AppCompatImageView mImage;
            private TextView           mTvGameTag;
            private TextView           mTvGameName;

            public ViewHolder(View itemView) {
                super(itemView);
                mImage = itemView.findViewById(R.id.image);
                mTvGameTag = itemView.findViewById(R.id.tv_game_tag);
                mTvGameName = itemView.findViewById(R.id.tv_game_name);
            }
        }
    }

    public class ViewHolder extends AbsHolder {
        private TextView     mTvName;
        private TextView     mTvMore;
        private RecyclerView mRecyclerView;
        private TextView     mTvChangeList;

        public ViewHolder(View view) {
            super(view);
            mTvName = findViewById(R.id.tv_name);
            mTvMore = findViewById(R.id.tv_more);
            mRecyclerView = findViewById(R.id.recycler_view);
            mTvChangeList = findViewById(R.id.tv_change_list);


            GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3);
            mRecyclerView.setLayoutManager(layoutManager);
        }
    }
}
