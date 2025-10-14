package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.zqhy.app.adapter.ViewPagerAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.tryplay.TryGameItemVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.tryplay.TryGamePlayListFragment;
import com.zqhy.app.core.view.tryplay.TryGameTaskFragment;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.widget.CommonViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class TryGameItemHolder extends AbsItemHolder<TryGameItemVo, TryGameItemHolder.ViewHolder> {

    private static String SP_TRY_GAME_LAST_ID = "SP_TRY_GAME_LAST_ID";


    private float density;

    public TryGameItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_try_game;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TryGameItemVo item) {
        int maxID = 0;
        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < item.getTryGameList().size(); i++) {
            TryGameItemVo.DataBean dataBean = item.getTryGameList().get(i);
            View itemPageView = createLabelPage(dataBean);

            if (item.getTryGameList().size() == 1) {
                itemPageView.setPadding(0, 0, 0, 0);
            } else {
                int padding = (int) (4 * density);
                if (i == 0) {
                    itemPageView.setPadding(0, 0, padding, 0);
                } else if (i == item.getTryGameList().size() - 1) {
                    itemPageView.setPadding(padding, 0, 0, 0);
                } else {
                    itemPageView.setPadding(padding, 0, padding, 0);
                }
            }
            viewList.add(itemPageView);

            if (dataBean.getTid() > maxID) {
                maxID = dataBean.getTid();
            }
        }

        holder.mViewRedDot.setVisibility(View.GONE);
        holder.mFlAllTryGame.setOnClickListener(v -> {
            if (_mFragment != null) {
                FragmentHolderActivity.startFragmentInActivity(mContext,
                        TryGamePlayListFragment.newInstance());
            }
        });


        holder.mViewpager.setAdapter(new ViewPagerAdapter(viewList));
        holder.mViewpager.setOffscreenPageLimit(viewList.size());
        holder.mViewpager.setPageTransformer(true, new ViewPager.PageTransformer() {
            float scale = 1f;

            @Override
            public void transformPage(View page, float position) {
                if (position >= 0 && position <= 1) {
                    page.setScaleY(scale + (1 - scale) * (1 - position));
                } else if (position > -1 && position < 0) {
                    page.setScaleY(1 + (1 - scale) * position);
                } else {
                    page.setScaleY(scale);
                }
            }
        });
        holder.mViewpager.setCurrentItem(0);
        if (viewList.size() > 1) {
            holder.mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    setSlidingIndicator(holder, i, viewList.size());
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
            setSlidingIndicator(holder, 0, viewList.size());
        } else {
            holder.mLlSlidingIndicator.setVisibility(View.GONE);
        }
        setViewPagerMargin(holder, viewList);
    }


    private View createLabelPage(TryGameItemVo.DataBean dataBean) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_page_try_game_label, null);

        FrameLayout mLlItemView = itemView.findViewById(R.id.ll_item_view);
        ImageView mIvTryGameBg = itemView.findViewById(R.id.iv_try_game_bg);
        TextView mTvTryGameName = itemView.findViewById(R.id.tv_try_game_name);
        TextView mTvTryGameReward = itemView.findViewById(R.id.tv_try_game_reward);
        TextView mTvTryGameTime = itemView.findViewById(R.id.tv_try_game_time);
        TextView mTvTryGameStatus = itemView.findViewById(R.id.tv_try_game_status);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5 * density);
        gd.setColor(ContextCompat.getColor(mContext, R.color.color_f8f8f8));
        mLlItemView.setBackground(gd);

        mIvTryGameBg.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        GlideApp.with(mContext).asBitmap().load(dataBean.getPic()).into(mIvTryGameBg);

        mTvTryGameName.setText("试玩《" + dataBean.getGamename() + "》");
        String value = String.valueOf(dataBean.getTotal());
        SpannableString ss = new SpannableString("最高奖励" + dataBean.getTotal() + "积分/每人");
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_ff4949)),
                2, 2 + 2 + value.length() + 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvTryGameReward.setText(ss);
        mTvTryGameTime.setText("开始时间：" + dataBean.getBegintime());

        if (dataBean.getStatus() == 1) {
            //未开始
            mTvTryGameStatus.setText("了解详情");
        } else if (dataBean.getStatus() == 2) {
            //已开始
            mTvTryGameStatus.setText("马上试玩");
            if (dataBean.getGot_total() > 0) {
                String reward = CommonUtils.formatAmount(dataBean.getGot_total());
                SpannableString ss2 = new SpannableString("已累计发放" + reward + "积分奖励");
                ss2.setSpan(new StyleSpan(Typeface.BOLD), 5, 5 + reward.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mTvTryGameTime.setText(ss2);
            }
        }

        mLlItemView.setOnClickListener(v -> {
            if (_mFragment != null) {
                FragmentHolderActivity.startFragmentInActivity(_mFragment.getActivity(),
                        TryGameTaskFragment.newInstance(dataBean.getTid()));
            }
        });
        return itemView;
    }

    private void setViewPagerMargin(ViewHolder viewHolder, List<View> viewList) {
        int totalSize = viewList.size();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (138 * density));
        if (totalSize <= 1) {
            params.setMargins(0, 0, 0, 0);
        } else {
            params.setMargins((int) (16 * density), 0, (int) (16 * density), 0);
        }

        viewHolder.mViewpager.setClipChildren(false);
        viewHolder.mViewpager.setLayoutParams(params);
    }

    private void setSlidingIndicator(ViewHolder holder, int position, int totalSize) {
        holder.mLlSlidingIndicator.setVisibility(View.VISIBLE);
        holder.mLlSlidingIndicator.removeAllViews();

        for (int i = 0; i < totalSize; i++) {
            View indicator = new View(mContext);
            int width = 0;
            int height = (int) (density * 3);

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(2 * density);
            if (i == position) {
                //选中
                width = (int) (10 * density);
                gd.setColor(ContextCompat.getColor(mContext, R.color.color_ff8f19));
            } else {
                //未选中
                width = (int) (5 * density);
                gd.setColor(ContextCompat.getColor(mContext, R.color.color_cccccc));
            }
            indicator.setBackground(gd);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            params.leftMargin = (int) (1.5 * density);
            params.rightMargin = (int) (1.5 * density);
            params.topMargin = (int) (4 * density);
            params.bottomMargin = (int) (4 * density);

            indicator.setLayoutParams(params);
            holder.mLlSlidingIndicator.addView(indicator);
        }

    }

    public class ViewHolder extends AbsHolder {

        private FrameLayout mFlAllTryGame;
        private TextView mTvCollectionPlay;
        private View mViewRedDot;
        private TextView mTvBoutiqueTitle;
        private CommonViewPager mViewpager;
        private LinearLayout mLlSlidingIndicator;

        public ViewHolder(View view) {
            super(view);
            mFlAllTryGame = findViewById(R.id.fl_all_try_game);
            mTvCollectionPlay = findViewById(R.id.tv_collection_play);
            mViewRedDot = findViewById(R.id.view_red_dot);
            mTvBoutiqueTitle = findViewById(R.id.tv_boutique_title);
            mViewpager = findViewById(R.id.viewpager);
            mLlSlidingIndicator = findViewById(R.id.ll_sliding_indicator);

        }
    }
}
