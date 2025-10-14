package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author pc
 * @date 2019/12/18-14:22
 * @description
 */
public class GameGiftItemHolder extends BaseItemHolder<GameInfoVo.CardlistBean, GameGiftItemHolder.ViewHolder> {

    private float density;

    public GameGiftItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_list_card;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameInfoVo.CardlistBean item) {
        holder.mTvGameName.setText(item.getCardname());
        holder.mTvCardContent.setText(item.getCardcontent());


        if (item.isGetCard()) {
            holder.mLlCardLeft.setVisibility(View.GONE);
            holder.mLlCardCode.setVisibility(View.VISIBLE);

            //礼包码展示
            String cardCode = item.getCard();
            holder.mTvCardCode.setText(cardCode);

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(density * 5);
            gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            gd.setColors(new int[]{Color.parseColor("#FE3764"), Color.parseColor("#FE994B")});
            holder.mTvCopy.setBackground(gd);
            holder.mTvCopy.setOnClickListener(view -> {
                if (CommonUtils.copyString(mContext, cardCode)) {
                    Toaster.show("复制成功");
                }
            });

        } else {
            holder.mLlCardLeft.setVisibility(View.VISIBLE);
            holder.mLlCardCode.setVisibility(View.GONE);
            //礼包剩余
            int cardLeftCount = item.getCardkucun();
            int left = item.getCardcountall() == 0 ? 0 : item.getCardkucun() * 100 / item.getCardcountall();
            holder.mCardCountProgressBar.setProgress(left);
            holder.mTvCardCountProgress.setText(left + "%");

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(density * 5);
            if (cardLeftCount == 0) {
                holder.mTvReceive.setText("淘号");
                gd.setColor(ContextCompat.getColor(mContext, R.color.color_d6d6d6));
                holder.mTvReceive.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            } else {
                holder.mTvReceive.setText("领取");
                gd.setColor(ContextCompat.getColor(mContext, R.color.color_ffa81a));
                holder.mTvReceive.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            }
            //        if (item.getCard_type() == 2) {
            //            holder.mTvReceive.setText("查看");
            //            gd.setColor(ContextCompat.getColor(mContext, R.color.color_ffa81a));
            //            holder.mTvReceive.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            //        }

            holder.mTvReceive.setBackground(gd);
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
                    //                if (item.getCard_type() == 1) {
                    //                    //直接领取普通礼包
                    //                } else if (item.getCard_type() == 2) {
                    //                    //今日付费礼包内页
                    //                    ((GameDetailInfoFragment) _mFragment).payCardInfo(item);
                    //                }
                }
            });
        }


        if (item.getCard_type() == 1) {
            holder.mTvCardRecharge.setVisibility(View.GONE);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.mLlCardView.getLayoutParams();
            params.topMargin = (int) (16 * density);
            holder.mLlCardView.setLayoutParams(params);
        } else if (item.getCard_type() == 2) {
            holder.mTvCardRecharge.setVisibility(View.VISIBLE);
            GradientDrawable gd1 = new GradientDrawable();
            gd1.setCornerRadii(new float[]{0, 0, 0, 0, 0, 0, density * 4, density * 4});
            gd1.setColor(ContextCompat.getColor(mContext, R.color.color_ffecec));
            holder.mTvCardRecharge.setBackground(gd1);
            holder.mTvCardRecharge.setText(item.getLabel());
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.mLlCardView.getLayoutParams();
            params.topMargin = (int) (10 * density);
            holder.mLlCardView.setLayoutParams(params);
        }

        holder.mLlCardView.setOnClickListener(view -> {
            if (_mFragment != null && _mFragment instanceof GameDetailInfoFragment)
                ((GameDetailInfoFragment) _mFragment).showGiftDetail(item);
        });
    }

    public class ViewHolder extends AbsHolder {

        private TextView     mTvGameName;
        private TextView     mTvReceive;
        private TextView     mTvCardContent;
        private ProgressBar  mCardCountProgressBar;
        private TextView     mTvCardCountProgress;
        private LinearLayout mLlCardView;
        private TextView     mTvCardRecharge;
        private LinearLayout mLlCardLeft;
        private LinearLayout mLlCardCode;
        private TextView     mTvCardCode;
        private TextView     mTvCopy;

        public ViewHolder(View view) {
            super(view);

            mLlCardView = findViewById(R.id.ll_card_view);
            mTvGameName = findViewById(R.id.tv_game_name);
            mTvReceive = findViewById(R.id.tv_receive);
            mTvCardContent = findViewById(R.id.tv_card_content);
            mCardCountProgressBar = findViewById(R.id.card_count_progress_bar);
            mTvCardCountProgress = findViewById(R.id.tv_card_count_progress);
            mTvCardRecharge = findViewById(R.id.tv_card_recharge);
            mLlCardLeft = findViewById(R.id.ll_card_left);
            mLlCardCode = findViewById(R.id.ll_card_code);
            mTvCardCode = findViewById(R.id.tv_card_code);
            mTvCopy = findViewById(R.id.tv_copy);


        }
    }
}
