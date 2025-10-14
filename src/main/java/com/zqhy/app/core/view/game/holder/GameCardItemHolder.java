package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.adapter.abs.AbsAdapter;
import com.zqhy.app.adapter.abs.EmptyAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.detail.GameCardListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.core.tool.utilcode.SizeUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.core.view.user.welfare.GameWelfareFragment;
import com.zqhy.app.core.view.user.welfare.MyCardListFragment;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/20
 */

public class GameCardItemHolder extends AbsItemHolder<GameCardListVo, GameCardItemHolder.ViewHolder> {

    private float density;

    public GameCardItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(context);
    }

    private boolean isCardExpand = false;
    CardListAdapter cardListAdapter;

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameCardListVo item) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setSmoothScrollbarEnabled(true);
        holder.mRecyclerViewGift.setNestedScrollingEnabled(false);
        holder.mRecyclerViewGift.setLayoutManager(layoutManager);

        List<GameInfoVo.CardlistBean> targetGiftList = new ArrayList<>();
        cardListAdapter = new CardListAdapter(mContext, targetGiftList);
        holder.mRecyclerViewGift.setAdapter(cardListAdapter);

        if (item != null && item.getCardlist() != null && !item.getCardlist().isEmpty()) {
            holder.mLlGiftMore.setOnClickListener(v -> {
                isCardExpand = !isCardExpand;
                setCardListExpand(holder, item);
            });

            isCardExpand = false;
            setCardListExpand(holder, item);
        } else {
            List<EmptyDataVo> emptyDataVoList = new ArrayList<>();
            emptyDataVoList.add(new EmptyDataVo(R.mipmap.img_empty_data_2));
            holder.mRecyclerViewGift.setAdapter(new EmptyAdapter(mContext, emptyDataVoList));
        }
        holder.mTvUserGift.setOnClickListener(view -> {
            if (_mFragment != null) {
                if (_mFragment.checkLogin()) {
                    _mFragment.start(new MyCardListFragment());
                }
            }
        });
        holder.mLlGiftMore.setVisibility((item.getCardlist() == null || item.getCardlist().size() <= 3) ? View.GONE : View.VISIBLE);
    }

    private void setCardListExpand(ViewHolder viewHolder, GameCardListVo item) {
        if (item.getCardlist() == null && !item.getCardlist().isEmpty()) {
            return;
        }
        List<GameInfoVo.CardlistBean> targetGiftList = new ArrayList<>();
        if (cardListAdapter != null) {
            cardListAdapter.clear();
            if (viewHolder.mTvGiftMoreTextAction != null && viewHolder.mIvGiftMoreTextAction != null) {
                if (!isCardExpand) {
                    viewHolder.mTvGiftMoreTextAction.setText("查看全部礼包");
                    viewHolder.mIvGiftMoreTextAction.setImageResource(R.mipmap.ic_game_detail_more_txt_down);
                    if (item.getCardlist().size() > 3) {
                        targetGiftList.addAll(item.getCardlist().subList(0, 3));
                    } else {
                        targetGiftList.addAll(item.getCardlist());
                    }
                } else {
                    viewHolder.mTvGiftMoreTextAction.setText("收起");
                    viewHolder.mIvGiftMoreTextAction.setImageResource(R.mipmap.ic_game_detail_more_txt_up);
                    targetGiftList.addAll(item.getCardlist());
                }
            }
            cardListAdapter.addAllData(targetGiftList);
            cardListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_detail_card;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    public class ViewHolder extends AbsHolder {
        private TextView     mTvUserGift;
        private RecyclerView mRecyclerViewGift;
        private ImageView    mIvNoDataGift;
        private LinearLayout mLlGiftMore;
        private TextView     mTvGiftMoreTextAction;
        private ImageView    mIvGiftMoreTextAction;

        public ViewHolder(View view) {
            super(view);
            mTvUserGift = findViewById(R.id.tv_user_gift);
            mRecyclerViewGift = findViewById(R.id.recyclerView_gift);
            mIvNoDataGift = findViewById(R.id.iv_no_data_gift);
            mLlGiftMore = findViewById(R.id.ll_gift_more);
            mTvGiftMoreTextAction = findViewById(R.id.tv_gift_more_text_action);
            mIvGiftMoreTextAction = findViewById(R.id.iv_gift_more_text_action);
        }
    }

    public void showGiftDetail(GameInfoVo.CardlistBean cardlistBean) {
        CustomDialog cardDetailDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_card_detail, null),
                ScreenUtils.getScreenWidth(mContext) - SizeUtils.dp2px(mContext, 28),
                WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        cardDetailDialog.setCanceledOnTouchOutside(false);

        TextView tvGiftContent = cardDetailDialog.findViewById(R.id.tv_gift_content);
        TextView tvGiftUsage = cardDetailDialog.findViewById(R.id.tv_gift_usage);
        TextView tvGiftTime = cardDetailDialog.findViewById(R.id.tv_gift_time);
        TextView mTvGiftRequirement = cardDetailDialog.findViewById(R.id.tv_gift_requirement);
        LinearLayout mLlGiftRequirement = cardDetailDialog.findViewById(R.id.ll_gift_requirement);

        TextView tvClose2 = cardDetailDialog.findViewById(R.id.tv_close);
        tvClose2.setOnClickListener((v) -> {
            if (cardDetailDialog != null && cardDetailDialog.isShowing()) {
                cardDetailDialog.dismiss();
            }
        });
        if (cardlistBean.isRechargeGift()) {
            mLlGiftRequirement.setVisibility(View.VISIBLE);
            mTvGiftRequirement.setText(cardlistBean.getGiftRequirement());
        } else {
            mLlGiftRequirement.setVisibility(View.GONE);
        }
        tvGiftContent.setText(cardlistBean.getCardcontent());
        if (!TextUtils.isEmpty(cardlistBean.getCardusage())) {
            tvGiftUsage.setText(cardlistBean.getCardusage());
        } else {
            tvGiftUsage.setText("请在游戏内兑换使用");
        }
        if (!TextUtils.isEmpty(cardlistBean.getYouxiaoqi())) {
            tvGiftTime.setText(cardlistBean.getYouxiaoqi());
        } else {
            tvGiftTime.setText("无限制");
        }

        cardDetailDialog.show();
    }


    class CardListAdapter extends AbsAdapter<GameInfoVo.CardlistBean> {

        public CardListAdapter(Context context, List<GameInfoVo.CardlistBean> labels) {
            super(context, labels);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, GameInfoVo.CardlistBean item, int position) {
            ViewHolder holder = (ViewHolder) viewHolder;
            holder.mTvGameName.setText(item.getCardname());
            holder.mTvCardDetail.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            holder.mTvCardContent.setText(item.getCardcontent());

            int cardLeftCount = item.getCardkucun();
            holder.mTvGameCardLeft.setText(String.valueOf(cardLeftCount));

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(density * 5);
            if (cardLeftCount == 0) {
                holder.mTvReceive.setText("淘号");
                gd.setStroke((int) (1 * density), ContextCompat.getColor(mContext, R.color.color_cccccc));
                holder.mTvReceive.setTextColor(ContextCompat.getColor(mContext, R.color.color_cccccc));
            } else {
                holder.mTvReceive.setText("领取");
                gd.setStroke((int) (1 * density), ContextCompat.getColor(mContext, R.color.color_ff8f19));
                holder.mTvReceive.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff8f19));
            }
            //            if (item.getCard_type() == 2) {
            //                holder.mTvReceive.setText("查看");
            //                gd.setStroke((int) (1 * density), ContextCompat.getColor(mContext, R.color.color_ff6c6c));
            //                holder.mTvReceive.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff6c6c));
            //            }
            holder.mTvReceive.setBackground(gd);

            if (item.getCard_type() == 1) {
                holder.mTvCardDetail.setVisibility(View.GONE);
                holder.mTvCardRecharge.setVisibility(View.GONE);
            } else if (item.getCard_type() == 2) {
                holder.mTvCardDetail.setVisibility(View.GONE);
                holder.mTvCardRecharge.setVisibility(View.GONE);

                GradientDrawable gd1 = new GradientDrawable();
                gd1.setCornerRadius(density * 4);
                gd1.setStroke((int) (1 * density), ContextCompat.getColor(mContext, R.color.color_ff6c6c));
                holder.mTvCardRecharge.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff6c6c));
                holder.mTvCardRecharge.setBackground(gd1);

                holder.mTvCardRecharge.setText(item.getLabel());
            }

            holder.mTvCardDetail.setOnClickListener(view -> {
                showGiftDetail(item);
            });

            int finalCardLeftCount = cardLeftCount;
            holder.mTvReceive.setOnClickListener(view -> {
                if (_mFragment != null) {
                    if (_mFragment.checkLogin()) {
                        if (_mFragment.checkUserBindPhoneByCardGift()) {
                            if (finalCardLeftCount != 0) {
                                //领取礼包
                                ((GameDetailInfoFragment) _mFragment).getCardInfo(item.getCardid());
                            } else {
                                //淘号
                                ((GameDetailInfoFragment) _mFragment).getTaoCardInfo(item.getCardid());
                            }
                        }
                    }
                    /*if (item.getCard_type() == 1) {
                        //直接领取普通礼包
                    } else if (item.getCard_type() == 2) {
                        //今日付费礼包内页
                        ((GameDetailInfoFragment) _mFragment).payCardInfo(item);
                    }*/
                }
            });
        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_game_list_card;
        }

        @Override
        public AbsViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }

        class ViewHolder extends AbsViewHolder {

            private TextView mTvGameName;
            private TextView mTvGameCardLeft;
            private TextView mTvCardDetail;
            private TextView mTvCardRecharge;
            private TextView mTvReceive;
            private TextView mTvCardContent;


            public ViewHolder(View view) {
                super(view);

                mTvGameName = findViewById(R.id.tv_game_name);
                mTvGameCardLeft = findViewById(R.id.tv_game_card_left);
                mTvCardDetail = findViewById(R.id.tv_card_detail);
                mTvCardRecharge = findViewById(R.id.tv_card_recharge);
                mTvReceive = findViewById(R.id.tv_receive);
                mTvCardContent = findViewById(R.id.tv_card_content);

            }
        }
    }
}
