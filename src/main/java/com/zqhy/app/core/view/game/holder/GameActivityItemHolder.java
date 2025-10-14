package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.adapter.abs.AbsAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.detail.GameActivityVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.game.GameCommentRewardFragment;
import com.zqhy.app.core.view.game.GameCouponListFragment;
import com.zqhy.app.core.view.strategy.DiscountStrategyFragment;
import com.zqhy.app.core.view.tryplay.TryGameTaskFragment;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/20
 */

public class GameActivityItemHolder extends AbsItemHolder<GameActivityVo, GameActivityItemHolder.ViewHolder> {

    private float density;

    public GameActivityItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    ActivityListAdapter mAdapter;

    private int gameid;

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameActivityVo item) {
        gameid = item.getGameid();
        holder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        List<GameActivityVo.ItemBean> itemBeanList = new ArrayList<>();
        itemBeanList.addAll(createTopMenuBeans(item));
        itemBeanList.addAll(createNewsBeans(item));

        mAdapter = new ActivityListAdapter(mContext, itemBeanList);

        holder.mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((v, position, data) -> {
            if (data != null && data instanceof GameActivityVo.ItemBean) {
                GameActivityVo.ItemBean itemBean = (GameActivityVo.ItemBean) data;
                if (itemBean.getType() == 1) {
                    if (itemBean.getNewslistBean() != null) {
                        if (_mFragment != null) {
                            BrowserActivity.newInstance(_mFragment.getActivity(), itemBean.getNewslistBean().getUrl());
                        }
                    }
                } else if (itemBean.getType() == 2) {
                    if (itemBean.getMenuInfoBean() != null) {
                        if (_mFragment != null) {
                            menuItemClick(itemBean.getMenuInfoBean().getId(), item);
                        }
                    }
                }
            }
        });

        holder.mRootView.setVisibility(itemBeanList.isEmpty() ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_detail_activity;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    /**
     * 创建菜单Item
     *
     * @param gameActivityVo
     * @return
     */
    private List<GameActivityVo.ItemBean> createTopMenuBeans(GameActivityVo gameActivityVo) {
        List<GameActivityVo.ItemBean> topMenuInfoBeanList = new ArrayList<>();

        if (gameActivityVo.isUserCommented()) {
            //限时折扣
            int menuId = 7;

            StringBuilder message = new StringBuilder("参与游戏点评，送双重豪华大礼");
            SpannableString spannableString = new SpannableString(message);
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setUnderlineText(false);
                }

                @Override
                public void onClick(@NonNull View widget) {
                    menuItemClick(menuId, gameActivityVo);
                }
            }, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);


            int[] resColors = {Color.parseColor("#8800FF"), Color.parseColor("#2000FF")};
            GameActivityVo.TopMenuInfoBean menuInfoBean = new GameActivityVo.TopMenuInfoBean(menuId, "点评送礼", spannableString, resColors);

            GameActivityVo.ItemBean itemBean = new GameActivityVo.ItemBean();
            itemBean.setType(2);
            itemBean.setMenuInfoBean(menuInfoBean);
            topMenuInfoBeanList.add(itemBean);
        }

        if (gameActivityVo.getShowDiscount() == 2) {
            //限时折扣
            int resColor = R.color.color_ea20dd;
            int menuId = 5;
            StringBuilder message = new StringBuilder();
            String value0 = "享";
            String value1 = String.valueOf(gameActivityVo.getFlash_discount()) + "折";
            String value2 = "(原" + gameActivityVo.getDiscount() + "折)，";
            String time = TimeUtils.formatTimeStamp(gameActivityVo.getFlash_discount_endtime() * 1000, "MM月dd日HH:mm");
            message.append(value0).append(value1).append(value2).append(time).append("截止");

            SpannableString spannableString = new SpannableString(message);
            ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(ContextCompat.getColor(mContext, resColor));
            ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(ContextCompat.getColor(mContext, resColor));

            int start1Count = value0.length();
            int end1Count = start1Count + value1.length();
            spannableString.setSpan(colorSpan1, start1Count, end1Count, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            int start2Count = end1Count + value2.length();
            int end2Count = start2Count + time.length();
            spannableString.setSpan(colorSpan2, start2Count, end2Count, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setUnderlineText(false);
                }

                @Override
                public void onClick(View view) {
                    menuItemClick(menuId, gameActivityVo);
                }
            }, 0, message.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            int[] resColors = {Color.parseColor("#C000FF"), Color.parseColor("#F126D7")};

            GameActivityVo.TopMenuInfoBean menuInfoBean = new GameActivityVo.TopMenuInfoBean(menuId, "限时折扣", spannableString, resColors);

            GameActivityVo.ItemBean itemBean = new GameActivityVo.ItemBean();
            itemBean.setType(2);
            itemBean.setMenuInfoBean(menuInfoBean);
            topMenuInfoBeanList.add(itemBean);
        }

        if (gameActivityVo.getShowDiscount() == 1) {
            try {
                float discount = gameActivityVo.getDiscount();
                if (discount > 0 && discount < 10) {
                    //自动折扣
                    int resColor = R.color.color_ff8f19;
                    int menuId = 1;
                    StringBuilder message = new StringBuilder();
                    String value = String.valueOf(discount);
                    message.append("自动打折，游戏内直充即享")
                            .append(value)
                            .append("折优惠！");
                    SpannableString spannableString = new SpannableString(message);
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(mContext, resColor));
                    spannableString.setSpan(colorSpan, 12, 13 + value.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new ClickableSpan() {
                        @Override
                        public void updateDrawState(TextPaint ds) {
                            ds.setUnderlineText(false);
                        }

                        @Override
                        public void onClick(View view) {
                            menuItemClick(menuId, gameActivityVo);
                        }
                    }, 0, message.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                    GameActivityVo.ItemBean itemBean = new GameActivityVo.ItemBean();
                    itemBean.setType(2);
                    itemBean.setMenuInfoBean(new GameActivityVo.TopMenuInfoBean(menuId, "自动折扣", spannableString, new int[]{ContextCompat.getColor(mContext, resColor)}));
                    topMenuInfoBeanList.add(itemBean);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (gameActivityVo.getTrial_info() != null && gameActivityVo.getGame_type() != 1) {
            //试玩赚钱
            int resColor = R.color.color_9487fb;
            int menuId = 6;
            StringBuilder message = new StringBuilder();

            String value = String.valueOf(gameActivityVo.getTrial_info().getTotal());
            message.append("玩游戏，最高奖励")
                    .append(value)
                    .append("积分/每人");
            SpannableString spannableString = new SpannableString(message);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_ff6c6c));
            spannableString.setSpan(colorSpan, 8, 8 + value.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setUnderlineText(false);
                }

                @Override
                public void onClick(View view) {
                    menuItemClick(menuId, gameActivityVo);
                }
            }, 0, message.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            GameActivityVo.ItemBean itemBean = new GameActivityVo.ItemBean();
            itemBean.setType(2);
            itemBean.setMenuInfoBean(new GameActivityVo.TopMenuInfoBean(menuId, "试玩赚钱", spannableString, new int[]{ContextCompat.getColor(mContext, resColor)}));
            topMenuInfoBeanList.add(itemBean);
        }


        if (gameActivityVo.getCoupon_amount() > 0 && gameActivityVo.getGame_type() != 1) {
            //代金券
            int resColor = R.color.color_11a8ff;
            int menuId = 3;
            StringBuilder message = new StringBuilder();
            message.append("该游戏有代金券可领，免费领取");

            SpannableString spannableString = new SpannableString(message);
            spannableString.setSpan(new ClickableSpan() {

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setUnderlineText(false);
                }

                @Override
                public void onClick(View view) {
                    menuItemClick(menuId, gameActivityVo);
                }
            }, 0, message.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            GameActivityVo.ItemBean itemBean = new GameActivityVo.ItemBean();
            itemBean.setType(2);
            itemBean.setMenuInfoBean(new GameActivityVo.TopMenuInfoBean(menuId, "代金券", spannableString, new int[]{ContextCompat.getColor(mContext, resColor)}));
            topMenuInfoBeanList.add(itemBean);
        }

        return topMenuInfoBeanList;
    }

    /**
     * 创建new item
     *
     * @param gameActivityVo
     * @return
     */
    private List<GameActivityVo.ItemBean> createNewsBeans(GameActivityVo gameActivityVo) {
        List<GameActivityVo.ItemBean> topMenuInfoBeanList = new ArrayList<>();

        if (gameActivityVo.getActivity() == null) {
            return topMenuInfoBeanList;
        }

        for (GameInfoVo.NewslistBean newslistBean : gameActivityVo.getActivity()) {
            GameActivityVo.ItemBean itemBean = new GameActivityVo.ItemBean();
            itemBean.setType(1);
            itemBean.setNewslistBean(newslistBean);
            topMenuInfoBeanList.add(itemBean);
        }
        return topMenuInfoBeanList;
    }


    public class ViewHolder extends AbsHolder {
        private LinearLayout mLlGameActivityTitle;
        private RecyclerView mRecyclerView;
        private LinearLayout mRootView;

        public ViewHolder(View view) {
            super(view);
            mRootView = findViewById(R.id.rootView);
            mLlGameActivityTitle = findViewById(R.id.ll_game_activity_title);
            mRecyclerView = findViewById(R.id.recycler_view);
        }
    }

    private void menuItemClick(int menuId, GameActivityVo item) {
        switch (menuId) {
            case 1:
            case 5:
                //自动折扣
                //限时折扣
                if (_mFragment != null) {
                    _mFragment.start(new DiscountStrategyFragment());
                }
                break;
            case 2:
                //限时任务
                break;
            case 3:
                //代金券(单游戏优惠券列表)
                if (_mFragment != null) {
                    _mFragment.start(GameCouponListFragment.newInstance(gameid));
                }
                break;
            case 4:
                //账号回收
                break;
            case 6:
                //试玩赚钱
                if (_mFragment != null) {
                    if (item.getTrial_info() != null) {
                        _mFragment.start(TryGameTaskFragment.newInstance(item.getTrial_info().getTid()));
                    }
                }
                break;
            case 7:
                //点评送礼
                if (_mFragment != null) {
                    _mFragment.startFragment(new GameCommentRewardFragment());
                }
                break;
            default:
                break;
        }
    }


    class ActivityListAdapter extends AbsAdapter<GameActivityVo.ItemBean> {

        public ActivityListAdapter(Context context, List<GameActivityVo.ItemBean> labels) {
            super(context, labels);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, GameActivityVo.ItemBean item, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;

            if (item.getType() == 1) {
                GameInfoVo.NewslistBean newslistBean = item.getNewslistBean();
                if (newslistBean != null) {
                    setActivityInfo(viewHolder, newslistBean);
                }
            } else if (item.getType() == 2) {
                GameActivityVo.TopMenuInfoBean topMenuInfoBean = item.getMenuInfoBean();
                if (topMenuInfoBean != null) {
                    setTopMenuInfo(viewHolder, topMenuInfoBean);
                }
            }
        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_game_list_activity;
        }

        @Override
        public AbsViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }


        private void setActivityInfo(ViewHolder holder, GameInfoVo.NewslistBean newslistBean) {
            try {
                GradientDrawable gd = new GradientDrawable();
                gd.setColor(Color.parseColor(newslistBean.getBg_color()));
                gd.setCornerRadius(6 * density);
                holder.mTvTxtTag.setBackground(gd);
                holder.mTvTxtTag.setTextColor(Color.parseColor(newslistBean.getText_color()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.mTvTxtTag.setText(newslistBean.getTitle2());
            holder.mTvTitle.setText(newslistBean.getTitle());
            holder.mTvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_444444));

            holder.mIvTagNewest.setVisibility(newslistBean.getIs_newest() == 1 ? View.VISIBLE : View.GONE);
            if (newslistBean.getIs_newest() == 1) {
                holder.mTvTitle.setMaxWidth((int) (200 * density));
            } else {
                holder.mTvTitle.setMaxWidth((int) (230 * density));
            }
        }

        private void setTopMenuInfo(ViewHolder holder, GameActivityVo.TopMenuInfoBean topMenuInfoBean) {
            holder.mTvTxtTag.setText(topMenuInfoBean.getTag());

            holder.mTvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_444444));
            holder.mTvTitle.setMovementMethod(LinkMovementMethod.getInstance());
            holder.mTvTitle.setText(topMenuInfoBean.getMessage());


            GradientDrawable gd = new GradientDrawable();
            if (topMenuInfoBean.getResColor().length == 1) {
                gd.setColor(topMenuInfoBean.getResColor()[0]);
            } else {
                gd.setColors(topMenuInfoBean.getResColor());
                gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            }
            gd.setCornerRadius(6 * density);
            holder.mTvTxtTag.setBackground(gd);
            holder.mTvTxtTag.setTextColor(ContextCompat.getColor(mContext, R.color.white));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.itemView.setLayoutParams(params);
        }

        class ViewHolder extends AbsAdapter.AbsViewHolder {

            private TextView mTvTxtTag;
            private TextView mTvTitle;
            private TextView mIvTagNewest;

            public ViewHolder(View view) {
                super(view);
                mTvTxtTag = itemView.findViewById(R.id.tv_txt_tag);
                mTvTitle = itemView.findViewById(R.id.tv_title);
                mIvTagNewest = itemView.findViewById(R.id.iv_tag_newest);

            }
        }
    }
}
