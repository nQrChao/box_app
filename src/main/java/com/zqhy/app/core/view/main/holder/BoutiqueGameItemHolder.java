package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.mainpage.boutique.BoutiqueGameListVo;
import com.zqhy.app.core.data.model.mainpage.boutique.BoutiqueGameVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.network.statistics.JiuYaoStatisticsApi;
import com.zqhy.app.newproject.R;

import java.util.Collections;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public class BoutiqueGameItemHolder extends AbsItemHolder<BoutiqueGameListVo, BoutiqueGameItemHolder.ViewHolder> {

    private float density;

    protected final float boutiqueItemWidthScale = 3.75f;

    public BoutiqueGameItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_boutique_game_list;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull BoutiqueGameListVo boutiqueGameListVo) {
        viewHolder.mTvCollectionPlay.setVisibility(View.GONE);
        viewHolder.mTvCollectionPlay.setOnClickListener(null);

        int game_type = boutiqueGameListVo.getGame_type();
        String title = "精品游戏";
        viewHolder.mTvBoutiqueTitle.setText(title);

        List<BoutiqueGameVo> boutiqueGameVos = boutiqueGameListVo.getData();
        if (boutiqueGameVos != null) {

            if (game_type != 2) {
                //集合随机排序
                Collections.shuffle(boutiqueGameVos);
            }
            //设置item长度
            viewHolder.mGameBoutiqueList.setVisibility(View.VISIBLE);
            viewHolder.mLlBoutiqueGamesList.removeAllViews();

            int totalWidth = ScreenUtils.getScreenWidth(mContext);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(totalWidth, FrameLayout.LayoutParams.MATCH_PARENT);
            viewHolder.mLlBoutiqueGamesList.setLayoutParams(params);

            int position = 1;
            for (BoutiqueGameVo boutiqueGameVo : boutiqueGameVos) {
                int itemWidth = (int) (totalWidth / boutiqueItemWidthScale);
                LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(itemWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                childParams.gravity = Gravity.CENTER;
                viewHolder.mLlBoutiqueGamesList.addView(createItemView(game_type, boutiqueGameVo, position), childParams);
                position++;
            }
        }
    }


    private View createItemView(int game_type, BoutiqueGameVo gameInfoBean, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_boutique_game, null);

        LinearLayout mLlItem = view.findViewById(R.id.ll_item);
        ImageView mGameIconIV = view.findViewById(R.id.gameIconIV);
        TextView mTvGameName = view.findViewById(R.id.tv_game_name);
        TextView mTvActionName = view.findViewById(R.id.tv_action_name);
        TextView mTvGameType = view.findViewById(R.id.tv_game_type);
        TextView mTvGameDiscount = view.findViewById(R.id.tv_game_discount);
        LinearLayout mLlDownloadTag = view.findViewById(R.id.ll_discount_tag);
        TextView mTvSubTag = view.findViewById(R.id.tv_sub_tag);

        if (gameInfoBean != null) {
            GlideUtils.loadRoundImage(mContext, gameInfoBean.getGameicon(), mGameIconIV);
            mTvGameName.setText(gameInfoBean.getGamename());
            mTvGameType.setText(gameInfoBean.getGenre_str());
            int maxWidth = (int) (density * 78);
            mTvGameName.setMaxWidth(maxWidth);

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(36 * density);
            gd.setColor(ContextCompat.getColor(mContext, R.color.white));
            gd.setStroke((int) (1 * density), ContextCompat.getColor(mContext, R.color.color_1e64eb));
            mTvActionName.setBackground(gd);
            mTvActionName.setTextColor(ContextCompat.getColor(mContext, R.color.color_1e64eb));
            mTvActionName.setText(gameInfoBean.getLabel_name());

            if (game_type == 1) {
                mTvActionName.setPadding((int) (4 * density), (int) (3 * density), (int) (4 * density), (int) (3 * density));
                mTvActionName.setTextSize(10);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (82 * density), ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER;
                params.setMargins(0, (int) (6 * density), 0, 0);
                mTvActionName.setLayoutParams(params);


                mTvSubTag.setVisibility(!TextUtils.isEmpty(gameInfoBean.getGame_label()) ? View.VISIBLE : View.GONE);

                GradientDrawable tagGd = new GradientDrawable();
                tagGd.setCornerRadius(12 * density);
                tagGd.setColor(ContextCompat.getColor(mContext,R.color.color_ff5400));
                mTvSubTag.setBackground(tagGd);

                mTvSubTag.setText(gameInfoBean.getGame_label());
            } else if (game_type == 2 || game_type == 3 || game_type == 4) {
                if (gameInfoBean.showDiscount() == 0) {
                    mLlDownloadTag.setVisibility(View.GONE);
                } else {
                    mLlDownloadTag.setVisibility(View.VISIBLE);
                    if (gameInfoBean.showDiscount() == 1) {
                        mTvGameDiscount.setText(String.valueOf(gameInfoBean.getDiscount()));
                        mLlDownloadTag.setBackgroundResource(R.mipmap.ic_discount_tag);
                    } else if (gameInfoBean.showDiscount() == 2) {
                        mTvGameDiscount.setText(String.valueOf(gameInfoBean.getFlash_discount()));
                        mLlDownloadTag.setBackgroundResource(R.mipmap.ic_discount_tag_2);
                    } else {
                        mLlDownloadTag.setVisibility(View.GONE);
                    }
                }
                mTvActionName.setTextSize(13);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (58 * density), (int) (24 * density));
                params.gravity = Gravity.CENTER;
                params.setMargins(0, (int) (6 * density), 0, 0);
                mTvActionName.setLayoutParams(params);
            }

            mLlItem.setOnClickListener(v -> {
                if (_mFragment != null) {
                    _mFragment.goGameDetail(gameInfoBean.getGameid(), gameInfoBean.getGame_type());
                }
                switch (game_type) {
                    case 1:
                        JiuYaoStatisticsApi.getInstance().eventStatistics(1, 4, position);
                        break;
                    case 2:
                        JiuYaoStatisticsApi.getInstance().eventStatistics(2, 23, position);
                        break;
                    case 3:
                        JiuYaoStatisticsApi.getInstance().eventStatistics(3, 41, position);
                        break;
                    case 4:
                        JiuYaoStatisticsApi.getInstance().eventStatistics(4, 59, position);
                        break;
                    default:
                        break;
                }
            });
        }
        return view;
    }


    public class ViewHolder extends AbsHolder {

        private FrameLayout          mLayoutBtBoutiqueList;
        private TextView             mTvCollectionPlay;
        private HorizontalScrollView mGameBoutiqueList;
        private LinearLayout         mLlBoutiqueGamesList;
        private TextView             mTvBoutiqueTitle;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvBoutiqueTitle = itemView.findViewById(R.id.tv_boutique_title);
            mLayoutBtBoutiqueList = itemView.findViewById(R.id.layout_bt_boutique_list);
            mTvCollectionPlay = itemView.findViewById(R.id.tv_collection_play);
            mGameBoutiqueList = itemView.findViewById(R.id.game_boutique_list);
            mLlBoutiqueGamesList = itemView.findViewById(R.id.ll_boutique_games_list);
        }

    }
}
