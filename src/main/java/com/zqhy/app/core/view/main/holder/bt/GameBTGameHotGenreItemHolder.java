package com.zqhy.app.core.view.main.holder.bt;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameNavigationVo;
import com.zqhy.app.core.data.model.mainpage.navigation.GameNavigationListVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.main.AbsMainGameListFragment;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2020/8/20-15:00
 * @description
 */
public class GameBTGameHotGenreItemHolder extends AbsItemHolder<GameNavigationListVo, GameBTGameHotGenreItemHolder.ViewHolder> {
    public GameBTGameHotGenreItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_bt_game_hot_genre;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameNavigationListVo item) {
        holder.mFlexBoxLayout.removeAllViews();

        if (item.getData() != null) {
            for (int i = 0; i < item.getData().size(); i++) {
                GameNavigationVo label = item.getData().get(i);
                int game_genre_id = label.getGenre_id();
                View itemView = createItemView(label);
                holder.mFlexBoxLayout.addView(itemView);
                itemView.setOnClickListener(view -> {
                    if (_mFragment != null && _mFragment instanceof AbsMainGameListFragment) {
                        //主界面-游戏-分类
                        ((AbsMainGameListFragment) _mFragment).goToMainGamePageByGenreId(item.getGame_type(), game_genre_id);
                    }
                });
            }
        }
    }


    private View createItemView(GameNavigationVo label) {
        FrameLayout layout = new FrameLayout(mContext);

        float density = ScreenUtil.getScreenDensity(mContext);
        TextView text = new TextView(mContext);

        int width = (int) (96 * density);
        int height = (int) (38 * density);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.gravity = Gravity.CENTER;
        params.topMargin = (int) (8 * density);
        params.bottomMargin = (int) (8 * density);
        text.setLayoutParams(params);


        GradientDrawable gd = new GradientDrawable();
        gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        gd.setCornerRadius(6 * density);
        try {
            gd.setColors(new int[]{Color.parseColor(label.getBg_color_left()), Color.parseColor(label.getBg_color_right())});
        } catch (Exception e) {
            e.printStackTrace();
        }
        text.setBackground(gd);

        text.setGravity(Gravity.CENTER);
        text.setTextSize(14);
        text.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        text.setText(label.getGenre_name());

        int itemWidth = ScreenUtil.getScreenWidth(mContext) / 3;
        FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(itemWidth, FlexboxLayout.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(lp);
        layout.addView(text);
        return layout;
    }


    public class ViewHolder extends AbsHolder {
        private FlexboxLayout mFlexBoxLayout;

        public ViewHolder(View view) {
            super(view);
            mFlexBoxLayout = findViewById(R.id.flex_box_layout);
        }
    }
}
