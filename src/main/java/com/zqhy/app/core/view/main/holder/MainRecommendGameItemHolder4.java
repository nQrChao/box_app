package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.mainpage.MainGameRecommendVo;
import com.zqhy.app.core.data.model.mainpage.recommend.MainGameRecommendItemVo4;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

import java.util.List;

/**
 * @author leeham2734
 * @date 2021/6/7-11:01
 * @description
 */
public class MainRecommendGameItemHolder4 extends BaseItemHolder<MainGameRecommendItemVo4, MainRecommendGameItemHolder4.ViewHolder> {


    public MainRecommendGameItemHolder4(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_game_recommend_4;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    private BaseRecyclerAdapter mAdapter;

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainGameRecommendItemVo4 item) {

        holder.mTvTab1.setText(item.getTabHotName());
        holder.mTvTab2.setText(item.getTabNewName());

        if (mAdapter == null) {
            holder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mAdapter = new BaseRecyclerAdapter.Builder<>()
                    .bind(MainGameRecommendVo.GameDataVo.class, new GameDataItemHolder(mContext))
                    .build().setTag(R.id.tag_fragment, _mFragment);
            holder.mRecyclerView.setAdapter(mAdapter);
        }
        holder.mTvTab1.setOnClickListener(view -> {
            onTab1Click(holder, item.getGameHotList());
        });

        holder.mTvTab2.setOnClickListener(view -> {
            onTab2Click(holder, item.getGameNewList());
        });
        onTab1Click(holder, item.getGameHotList());
    }

    private void onTab1Click(ViewHolder holder, List<MainGameRecommendVo.GameDataVo> dataVoList) {
        tab1Status(holder, true);
        tab2Status(holder, false);
        refresh(dataVoList);
    }

    private void onTab2Click(ViewHolder holder, List<MainGameRecommendVo.GameDataVo> dataVoList) {
        tab2Status(holder, true);
        tab1Status(holder, false);
        refresh(dataVoList);
    }

    private void refresh(List<MainGameRecommendVo.GameDataVo> dataVoList) {
        mAdapter.clear();
        mAdapter.addAllData(dataVoList);
        mAdapter.notifyDataSetChanged();
    }

    private void tab1Status(ViewHolder holder, boolean isChecked) {
        holder.mTvTab1.setTextColor(ContextCompat.getColor(mContext, isChecked ? R.color.white : R.color.color_0079fb));

        GradientDrawable gd = new GradientDrawable();
        gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        gd.setCornerRadius(ScreenUtil.dp2px(mContext, 50));
        if (isChecked) {
            gd.setColors(new int[]{Color.parseColor("#0D9EFF"), Color.parseColor("#0079FB")});
        } else {
            gd.setColor(Color.parseColor("#ECF7FF"));
        }
        holder.mTvTab1.setBackground(gd);
    }

    private void tab2Status(ViewHolder holder, boolean isChecked) {
        holder.mTvTab2.setTextColor(ContextCompat.getColor(mContext, isChecked ? R.color.white : R.color.color_0079fb));

        GradientDrawable gd = new GradientDrawable();
        gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        gd.setCornerRadius(ScreenUtil.dp2px(mContext, 50));
        if (isChecked) {
            gd.setColors(new int[]{Color.parseColor("#0D9EFF"), Color.parseColor("#0079FB")});
        } else {
            gd.setColor(Color.parseColor("#ECF7FF"));
        }
        holder.mTvTab2.setBackground(gd);
    }


    public class GameDataItemHolder extends BaseItemHolder<MainGameRecommendVo.GameDataVo, GameDataItemHolder.ViewHolder> {

        private int maxGameNameTextLength;

        public GameDataItemHolder(Context context) {
            super(context);
        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_main_game_normal;
        }

        @Override
        public ViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }

        @Override
        protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainGameRecommendVo.GameDataVo item) {
            maxGameNameTextLength = 240 - 30;
            GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mGameIconIV);
            holder.mTvGameName.setText(item.getGamename());

            StringBuilder middleSb = new StringBuilder();
            middleSb.append(item.getGenre_str()).append("  ").append(CommonUtils.formatNumberType2(item.getPlay_count())).append("人在玩");
            holder.mTvInfoMiddle.setText(middleSb);

            StringBuilder bottomSb = new StringBuilder();
            int[][] index = new int[item.getGame_labels().size()][2];

            for (int i = 0; i < item.getGame_labels().size(); i++) {
                MainGameRecommendVo.GameDataVo.GameLabelVo gameLabelVo = item.getGame_labels().get(i);

                int start = bottomSb.length();
                bottomSb.append(gameLabelVo.getLabel_name()).append("  ");
                int end = bottomSb.length();

                index[i][0] = start;
                index[i][1] = end;
            }
            SpannableString ss = new SpannableString(bottomSb);
            for (int i = 0; i < index.length; i++) {
                int[] arr = index[i];

                String color = item.getGame_labels().get(i).getText_color();
                ss.setSpan(new ForegroundColorSpan(Color.parseColor(color)), arr[0], arr[1], Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            holder.mTvInfoBottom.setText(ss);

            holder.mFlGameTitleContainer.removeAllViews();
            if (item.getIs_first() == 1) {
                //新增“首发”标签
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.rightMargin = (int) (8 * ScreenUtil.getScreenDensity(mContext));
                holder.mFlGameTitleContainer.addView(createFirstView(), params);
                maxGameNameTextLength -= 40;
            }
            holder.mTvGameName.setMaxWidth(ScreenUtil.dp2px(mContext, maxGameNameTextLength));
            holder.mLlRootview.setOnClickListener(view -> {
                if (_mFragment != null) {
                    _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
                }
            });
        }

        private View createFirstView() {
            TextView view = new TextView(mContext);
            view.setText("首发");
            view.setTextSize(13);
            view.setIncludeFontPadding(false);
            view.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            float density = ScreenUtil.getScreenDensity(mContext);
            view.setPadding((int) (6 * density), (int) (2 * density), (int) (6 * density), (int) (2 * density));


            GradientDrawable gd = new GradientDrawable();
            gd.setColors(new int[]{Color.parseColor("#FE7448"), Color.parseColor("#F63653")});
            gd.setCornerRadius(6 * density);
            view.setBackground(gd);

            return view;
        }

        public class ViewHolder extends AbsHolder {
            private LinearLayout   mLlRootview;
            private RelativeLayout mRlImage;
            private ImageView      mGameIconIV;
            private TextView       mTvGameName;
            private LinearLayout   mFlGameTitleContainer;
            private TextView       mTvInfoMiddle;
            private TextView       mTvInfoBottom;

            public ViewHolder(View view) {
                super(view);
                mLlRootview = findViewById(R.id.ll_rootview);
                mRlImage = findViewById(R.id.rl_image);
                mGameIconIV = findViewById(R.id.gameIconIV);
                mTvGameName = findViewById(R.id.tv_game_name);
                mFlGameTitleContainer = findViewById(R.id.fl_game_title_container);
                mTvInfoMiddle = findViewById(R.id.tv_info_middle);
                mTvInfoBottom = findViewById(R.id.tv_info_bottom);
            }
        }
    }

    public class ViewHolder extends AbsHolder {
        private TextView     mTvTab1;
        private TextView     mTvTab2;
        private RecyclerView mRecyclerView;

        public ViewHolder(View view) {
            super(view);
            mTvTab1 = findViewById(R.id.tv_tab_1);
            mTvTab2 = findViewById(R.id.tv_tab_2);
            mRecyclerView = findViewById(R.id.recycler_view);

        }
    }
}
