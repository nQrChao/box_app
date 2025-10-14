package com.zqhy.app.core.view.main.holder.bt;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.bt.MainBTPageForecastVo;
import com.zqhy.app.core.data.model.mainpage.HomeBTGameIndexVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.main.new_game.NewGameMainFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author leeham2734
 * @date 2020/8/19-17:13
 * @description
 */
public class GameBTForecastItemHolder extends AbsItemHolder<MainBTPageForecastVo, GameBTForecastItemHolder.ViewHolder> {
    public GameBTForecastItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_bt_forecast;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainBTPageForecastVo item) {
        holder.mTvMore.setOnClickListener(view -> {
            if (_mFragment != null) {
                _mFragment.startFragment(NewGameMainFragment.newInstance(2));
            }
        });
        holder.mLlGameContainer.removeAllViews();
        float density = ScreenUtil.getScreenDensity(mContext);
        int width = ScreenUtil.getScreenWidth(mContext) / 3;
        int height = (int) (width * 1.67f);
        for (int i = 0; i < item.getData().size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            params.rightMargin = (int) (10 * density);
            holder.mLlGameContainer.addView(createGameItemView(item.getData().get(i)), params);
        }
    }


    private View createGameItemView(HomeBTGameIndexVo.ReserveVo reserveVo) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_game_bt_forecast_item_view, null);
        ImageView mImage = view.findViewById(R.id.image);
        LinearLayout mLayoutShadow = view.findViewById(R.id.layout_shadow);
        TextView mTvGameTimeFirstRelease = view.findViewById(R.id.tv_game_time_first_release);
        TextView mTvGameName = view.findViewById(R.id.tv_game_name);
        TextView mTvGameGenre = view.findViewById(R.id.tv_game_genre);

        String img = reserveVo.getScreenshot1();
        int radius = 6;
        GlideUtils.loadRoundImage(mContext, img, mImage, R.mipmap.img_placeholder_h, radius);

        mTvGameName.setText(reserveVo.getGamename());
        mTvGameGenre.setText(reserveVo.getGenre_str());

        float density = ScreenUtil.getScreenDensity(mContext);
        //设置阴影
        GradientDrawable shadowGd = new GradientDrawable();
        shadowGd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        shadowGd.setColors(new int[]{Color.parseColor("#00000000"), Color.parseColor("#FF000000")});
        shadowGd.setCornerRadius(radius * density);
        mLayoutShadow.setBackground(shadowGd);

        if (reserveVo.getOnline_time() > 0) {
            //首发日期
            GradientDrawable gd = new GradientDrawable();
            gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            gd.setColors(new int[]{Color.parseColor("#FA574E"), Color.parseColor("#EE0E15")});
            gd.setCornerRadii(new float[]{0, 0, radius * density, radius * density, 0, 0, radius * density, radius * density});
            mTvGameTimeFirstRelease.setBackground(gd);
            mTvGameTimeFirstRelease.setVisibility(View.VISIBLE);
            mTvGameTimeFirstRelease.setText(CommonUtils.formatTimeStamp(reserveVo.getOnline_time() * 1000, "MM月dd日首发"));
        } else {
            mTvGameTimeFirstRelease.setVisibility(View.GONE);
        }

        view.setOnClickListener(v -> {
            if (_mFragment != null) {
                _mFragment.goGameDetail(reserveVo.getGameid(), reserveVo.getGame_type());
            }
        });
        return view;
    }


    public class ViewHolder extends AbsHolder {
        private TextView     mTvMore;
        private LinearLayout mLlGameContainer;

        public ViewHolder(View view) {
            super(view);
            mTvMore = findViewById(R.id.tv_more);
            mLlGameContainer = findViewById(R.id.ll_game_container);

        }
    }
}
