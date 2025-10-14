package com.zqhy.app.core.view.main.new0809.holder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.new0809.MainXingYouDataVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 * @date 2021/8/11 0011-12:08
 * @description
 */
public class MainStyle1ItemHolder extends BaseItemHolder<MainXingYouDataVo.ZuiXingShangJiaDataBeanVo, MainStyle1ItemHolder.ViewHolder> {
    public MainStyle1ItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_page_style_1;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainXingYouDataVo.ZuiXingShangJiaDataBeanVo item) {
        if (TextUtils.isEmpty(item.module_title) && TextUtils.isEmpty(item.module_title_two)) {
            holder.mTvTitle.setVisibility(View.GONE);
        } else {
            holder.mTvTitle.setVisibility(View.VISIBLE);
            String title = "";
            if (!TextUtils.isEmpty(item.module_title)){
                title += item.module_title;
            }
            if (!TextUtils.isEmpty(item.module_title_two)){
                title += item.module_title_two;
            }
            SpannableString spannableString = new SpannableString(title);
            if (!TextUtils.isEmpty(item.module_title) && !TextUtils.isEmpty(item.module_title_color)){
                try {
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(item.module_title_color)), 0, item.module_title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }catch (Exception e){}
            }
            if (!TextUtils.isEmpty(item.module_title_two) && !TextUtils.isEmpty(item.module_title_two_color)){
                try {
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(item.module_title_two_color)), title.length() - item.module_title_two.length(), title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }catch (Exception e){}
            }
            holder.mTvTitle.setText(spannableString);
        }
        if (item.additional != null) {
            holder.mIvAction.setVisibility(View.VISIBLE);
            //            GlideUtils.loadNormalImage(mContext, item.additional.icon, holder.mIvAction);
            GlideApp.with(mContext).asBitmap()
                    .load(item.additional.icon)
                    .override(ScreenUtil.dp2px(mContext, 96), ScreenUtil.dp2px(mContext, 32))
                    .into(holder.mIvAction);
            holder.mIvAction.setOnClickListener(v -> {
                appJump(item.additional);
            });
        } else {
            holder.mIvAction.setVisibility(View.GONE);
        }

        holder.mLlContainer.removeAllViews();
        for (GameInfoVo gameInfoVo : item.data) {
            View itemView = createItemView(gameInfoVo);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (ScreenUtil.getScreenWidth(mContext) / 3.05f), LinearLayout.LayoutParams.WRAP_CONTENT);
            holder.mLlContainer.addView(itemView, params);
        }

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)holder.mViewProgress.getLayoutParams();
        layoutParams.width = ScreenUtil.dp2px(mContext, 39) / item.data.size();
        layoutParams.leftMargin = 0;
        holder.mViewProgress.setLayoutParams(layoutParams);

        if (item.data.size() > 1){
            holder.mLlProgress.setVisibility(View.VISIBLE);
        }else {
            holder.mLlProgress.setVisibility(View.GONE);
        }

        int screenWidth = ScreenUtil.getScreenWidth(mContext);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.mScrollView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                layoutParams.leftMargin = ((int)((float)scrollX / ((holder.mLlContainer.getWidth()) - holder.mScrollView.getWidth()) * (ScreenUtil.dp2px(mContext, 39)- holder.mViewProgress.getWidth())));
                holder.mViewProgress.setLayoutParams(layoutParams);
            });
        }
        holder.mScrollView.scrollTo(0, 0);
    }

    private View createItemView(GameInfoVo gameInfoVo) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_main_page_new_game_xy_new, null);
        TextView mTvDate = itemView.findViewById(R.id.tv_date);
        ImageView mIvGameIcon = itemView.findViewById(R.id.iv_game_icon);
        TextView mIvGameTag = itemView.findViewById(R.id.iv_game_tag);
        TextView mTvGameName = itemView.findViewById(R.id.tv_game_name);

        if (CommonUtils.isTodayOrTomorrow(gameInfoVo.getOnline_time() * 1000) == 0){
            mTvDate.setText(CommonUtils.formatTimeStamp(gameInfoVo.getOnline_time() * 1000, "今日HH点首发"));
        }else {
            mTvDate.setText(CommonUtils.formatTimeStamp(gameInfoVo.getOnline_time() * 1000, "MM月dd日上线"));
        }

        GlideUtils.loadRoundImage(mContext, gameInfoVo.getGameicon(), mIvGameIcon);
        String tag = null;
        int showDiscount = gameInfoVo.showDiscount();
        if (showDiscount == 1 || showDiscount == 2) {
            if (showDiscount == 1){
                tag = "享" + gameInfoVo.getDiscount() + "折优惠";
            }else if (showDiscount == 2){
                tag = "享" + gameInfoVo.getFlash_discount() + "折优惠";
            }
        }
        if (TextUtils.isEmpty(tag)) {
            tag = gameInfoVo.getVip_label();
        }
        if (TextUtils.isEmpty(tag)){
            tag = gameInfoVo.getGenre_str();
        }
        if (!TextUtils.isEmpty(tag)) {
            mIvGameTag.setVisibility(View.VISIBLE);
            mIvGameTag.setText(tag);
        } else {
            mIvGameTag.setVisibility(View.INVISIBLE);
        }

        mTvGameName.setText(gameInfoVo.getGamename());

        itemView.setOnClickListener(v -> {
            if (_mFragment != null) {
                _mFragment.goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
            }
        });
        return itemView;
    }

    public class ViewHolder extends AbsHolder {
        private TextView     mTvTitle;
        private ImageView    mIvAction;
        private HorizontalScrollView mScrollView;
        private LinearLayout mLlContainer;
        private LinearLayout mLlProgress;
        private View mViewProgress;

        public ViewHolder(View view) {
            super(view);
            mTvTitle = findViewById(R.id.tv_title);
            mIvAction = findViewById(R.id.iv_action);
            mScrollView = findViewById(R.id.scrollView);
            mLlContainer = findViewById(R.id.ll_container);
            mLlProgress = findViewById(R.id.ll_progress);
            mViewProgress = findViewById(R.id.view_progress);
        }
    }
}
