package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameNavigationVo;
import com.zqhy.app.core.data.model.mainpage.navigation.GameNavigationListVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.classification.GameClassificationFragment;
import com.zqhy.app.network.statistics.JiuYaoStatisticsApi;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public class GameNavigationItemHolder extends AbsItemHolder<GameNavigationListVo, GameNavigationItemHolder.ViewHolder> {

    private float density;

    public GameNavigationItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(context);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_navigation;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull GameNavigationListVo gameNavigationListVo) {

        GradientDrawable gd2 = new GradientDrawable();
        gd2.setCornerRadius(24 * density);
        gd2.setStroke((int) (0.8 * density), ContextCompat.getColor(mContext, R.color.color_ff8f19));
        viewHolder.mTvGameAllType.setBackground(gd2);

        viewHolder.mTvGameAllType.setOnClickListener(view -> {
            if (_mFragment != null) {
                String game_type = String.valueOf(gameNavigationListVo.getGame_type());
                GameClassificationFragment fragment =
                        GameClassificationFragment.newInstance(game_type, 1);
                FragmentHolderActivity.startFragmentInActivity(_mFragment.getActivity(), fragment);
            }
            switch (gameNavigationListVo.getGame_type()) {
                case 1:
                    JiuYaoStatisticsApi.getInstance().eventStatistics(1, 11);
                    break;
                case 2:
                    JiuYaoStatisticsApi.getInstance().eventStatistics(2, 30);
                    break;
                case 3:
                    JiuYaoStatisticsApi.getInstance().eventStatistics(3, 48);
                    break;
                case 4:
                    JiuYaoStatisticsApi.getInstance().eventStatistics(4, 62);
                    break;
                default:
                    break;
            }
        });

        viewHolder.mFlexBoxLayout.removeAllViews();

        float marginValue = (5 * density);
        int rowCount = 5;
        final float totalMarginRight = (rowCount - 1) * marginValue;
        final float totalViewGroupPadding = (12 * 2 * density);

        int width = (int) ((ScreenUtils.getScreenWidth(mContext) - totalViewGroupPadding - totalMarginRight) / rowCount);
        int height = (int) (30 * density);

        if (gameNavigationListVo.getData() != null) {
            for (int i = 0; i < gameNavigationListVo.getData().size(); i++) {
                GameNavigationVo gameGenreBean = gameNavigationListVo.getData().get(i);
                TextView textView = new TextView(mContext);

                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(4 * density);
                gd.setStroke((int) (1 * density), ContextCompat.getColor(mContext, R.color.color_d9d9d9));
                textView.setBackground(gd);

                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
                textView.setTextSize(13);

                textView.setText(gameGenreBean.getGenre_name());
                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(width, height);

                int viewMarginTop = (int) marginValue;
                int viewMarginRight = (i + 1) % rowCount == 0 ? 0 : (int) marginValue;
                params.setMargins(0, viewMarginTop, viewMarginRight, 0);

                viewHolder.mFlexBoxLayout.addView(textView, params);

                int finalI = i;
                textView.setOnClickListener(view -> {
                    String game_type = String.valueOf(gameNavigationListVo.getGame_type());
                    String genre_id = String.valueOf(gameGenreBean.getGenre_id());
                    GameClassificationFragment fragment =
                            GameClassificationFragment.newInstance(game_type, genre_id);
                    FragmentHolderActivity.startFragmentInActivity(_mFragment.getActivity(), fragment);

                    switch (gameNavigationListVo.getGame_type()) {
                        case 1:
                            JiuYaoStatisticsApi.getInstance().eventStatistics(1, 12, (finalI + 1));
                            break;
                        case 2:
                            JiuYaoStatisticsApi.getInstance().eventStatistics(2, 31, (finalI + 1));
                            break;
                        case 3:
                            JiuYaoStatisticsApi.getInstance().eventStatistics(3, 49, (finalI + 1));
                            break;
                        case 4:
                            JiuYaoStatisticsApi.getInstance().eventStatistics(4, 63, (finalI + 1));
                            break;
                        default:
                            break;
                    }
                });
            }
        }
    }

    public class ViewHolder extends AbsHolder {
        private TextView mTvGameAllType;
        private FlexboxLayout mFlexBoxLayout;

        public ViewHolder(View view) {
            super(view);
            mTvGameAllType = itemView.findViewById(R.id.tv_game_all_type);
            mFlexBoxLayout = itemView.findViewById(R.id.flex_box_layout);
        }
    }
}
