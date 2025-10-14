package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.game.GameHallJxHomeVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/20
 */

public class GameCollectionItemHolder extends AbsItemHolder<GameHallJxHomeVo.CollectionListBean, GameCollectionItemHolder.ViewHolder> {

    public GameCollectionItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameHallJxHomeVo.CollectionListBean item) {
        holder.mTvTitle.setText(item.getTitle());
        if (TextUtils.isEmpty(item.getPage_type()) || "no_jump".equals(item.getPage_type())){
            holder.mTvMore.setVisibility(View.GONE);
        }else {
            holder.mTvMore.setVisibility(View.VISIBLE);
        }
        holder.mTvMore.setOnClickListener(v -> {
            if (_mFragment != null){
                AppBaseJumpInfoBean appBaseJumpInfoBean = new AppBaseJumpInfoBean(item.getPage_type(), item.getParam());
                AppJumpAction appJumpAction = new AppJumpAction(_mFragment.getActivity());
                appJumpAction.jumpAction(appBaseJumpInfoBean);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        holder.mRecyclerView.setLayoutManager(gridLayoutManager);
        holder.mRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_game_collection_list_item, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                MyViewHolder viewHolder1 = (MyViewHolder)viewHolder;
                //图标
                GameInfoVo gameInfoVo = item.getGame_list().get(i);
                GlideUtils.loadGameIcon(mContext, gameInfoVo.getGameicon(), viewHolder1.mIvGameIcon);
                //游戏名
                String gameName = gameInfoVo.getGamename();
                viewHolder1.mTvGameName.setText(gameName);

                /*String tag = null;
                int showDiscount = gameInfoVo.showDiscount();
                if (showDiscount == 1 || showDiscount == 2) {
                    if (showDiscount == 1){
                        tag = gameInfoVo.getDiscount() + "折";
                    }else if (showDiscount == 2){
                        tag = gameInfoVo.getFlash_discount() + "折";
                    }
                }
                if (TextUtils.isEmpty(tag)) {
                    tag = gameInfoVo.getVip_label();
                }*/
                if (!TextUtils.isEmpty(gameInfoVo.getCoin_label())) {
                    viewHolder1.mTvGameTag.setVisibility(View.VISIBLE);
                    viewHolder1.mTvGameTag.setText(gameInfoVo.getCoin_label());
                } else {
                    viewHolder1.mTvGameTag.setVisibility(View.GONE);
                }

                GradientDrawable gd2 = new GradientDrawable();
                float radius = ScreenUtil.dp2px(mContext, 100);
                gd2.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius, 0, 0});
                gd2.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                gd2.setColors(new int[]{Color.parseColor("#FE6631"), Color.parseColor("#EF0F16")});
                viewHolder1.mTvGameTag.setBackground(gd2);

                viewHolder1.itemView.setOnClickListener(v -> {
                    if (_mFragment != null) {
                        _mFragment.goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
                    }
                });
            }

            @Override
            public int getItemCount() {
                if (item.getGame_list() != null && item.getGame_list().size() > 0){
                    return item.getGame_list().size();
                }else {
                    return 0;
                }
            }

            class MyViewHolder extends RecyclerView.ViewHolder{
                private ImageView mIvGameIcon;
                private TextView mTvGameTag;
                private TextView mTvGameName;
                public MyViewHolder(@NonNull View itemView) {
                    super(itemView);
                    mIvGameIcon = itemView.findViewById(R.id.iv_game_icon);
                    mTvGameTag = itemView.findViewById(R.id.iv_game_tag);
                    mTvGameName = itemView.findViewById(R.id.tv_game_name);
                }
            }
        });

    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_collection_list;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private TextView mTvTitle;
        private TextView mTvMore;
        private RecyclerView mRecyclerView;

        public ViewHolder(View view) {
            super(view);
            mTvTitle = view.findViewById(R.id.tv_title);
            mTvMore = view.findViewById(R.id.tv_more);
            mRecyclerView = view.findViewById(R.id.recycler_view);
        }
    }
}
