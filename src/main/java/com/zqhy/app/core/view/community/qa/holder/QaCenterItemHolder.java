package com.zqhy.app.core.view.community.qa.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.community.qa.UserQaCenterInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.community.qa.GameQaDetailFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 */
public class QaCenterItemHolder extends AbsItemHolder<UserQaCenterInfoVo.QaCenterQuestionVo, QaCenterItemHolder.ViewHolder> {

    public QaCenterItemHolder(Context context) {
        super(context);
    }

    private int itemType;

    private float density;

    public QaCenterItemHolder(Context context, int itemType) {
        super(context);
        this.itemType = itemType;
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_user_qa_center;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull UserQaCenterInfoVo.QaCenterQuestionVo item) {

        GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mIvGameIcon);
        holder.mTvGameName.setText(item.getGamename());

        SpannableStringBuilder spannable = new SpannableStringBuilder(" " + item.getContent());
        Drawable rightDrawable = mContext.getResources().getDrawable(R.mipmap.ic_game_question_ask);
        rightDrawable.setBounds(0, 0, rightDrawable.getIntrinsicWidth(), rightDrawable.getIntrinsicHeight());
        spannable.setSpan(new ImageSpan(rightDrawable), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.mTvGameQuestionTitle.setText(spannable);

        if (item.getReward_integral() == 0) {
            holder.mFlUserIntegral.setVisibility(View.GONE);
        } else {
            //2019.03.15 暂时屏蔽
//            holder.mFlUserIntegral.setVisibility(View.VISIBLE);
//            holder.mTvIntegralGain.setText("+" + String.valueOf(item.getReward_integral()));
        }

        holder.mTvDate.setText(CommonUtils.formatTimeStamp(item.getAdd_time() * 1000, "MM月dd日"));

        holder.mTvAllAnswer.setText(Html.fromHtml(mContext.getResources().getString(R.string.string_game_answer_info_count, String.valueOf(item.getA_count()))));

        holder.mIvQuestionComplete.setVisibility(View.GONE);
        holder.mLlNeedAnswer.setVisibility(View.GONE);

        if (itemType == 1) {
            holder.mTvDate.setText(CommonUtils.formatTimeStamp(item.getLast_answer_time() * 1000, "MM月dd日"));
        } else if (itemType == 2) {
            if (item.getVerify_status() == -1) {
                holder.mFlNotApprovedMaskLayer.setVisibility(View.VISIBLE);
                holder.mTvAllAnswer.setVisibility(View.GONE);
                holder.mLlRootView.setEnabled(false);
            } else {
                holder.mFlNotApprovedMaskLayer.setVisibility(View.GONE);
                holder.mTvAllAnswer.setVisibility(View.VISIBLE);
            }
        }
        holder.mLlRootView.setOnClickListener(view -> {
            if (_mFragment != null) {
                _mFragment.start(GameQaDetailFragment.newInstance(item.getQid()));
            }
        });
    }

    public class ViewHolder extends AbsHolder {

        private ImageView mIvGameIcon;
        private ImageView mIvQuestionComplete;
        private TextView mTvGameName;
        private LinearLayout mFlUserIntegral;
        private TextView mTvIntegralGain;
        private TextView mTvGameQuestionTitle;
        private TextView mTvDate;
        private TextView mTvAllAnswer;
        private FrameLayout mFlNotApprovedMaskLayer;
        private LinearLayout mLlNeedAnswer;
        private FrameLayout mFlWriteAnswer;
        private LinearLayout mLlRootView;

        public ViewHolder(View view) {
            super(view);
            mLlRootView = findViewById(R.id.ll_rootView);

            mIvGameIcon = findViewById(R.id.iv_game_icon);
            mIvQuestionComplete = findViewById(R.id.iv_question_complete);
            mTvGameName = findViewById(R.id.tv_game_name);
            mFlUserIntegral = findViewById(R.id.fl_user_integral);
            mTvIntegralGain = findViewById(R.id.tv_integral_gain);
            mTvGameQuestionTitle = findViewById(R.id.tv_game_question_title);
            mTvDate = findViewById(R.id.tv_date);
            mTvAllAnswer = findViewById(R.id.tv_all_answer);
            mFlNotApprovedMaskLayer = findViewById(R.id.fl_not_approved_mask_layer);
            mLlNeedAnswer = findViewById(R.id.ll_need_answer);
            mFlWriteAnswer = findViewById(R.id.fl_write_answer);

            GradientDrawable gd = new GradientDrawable();
            gd.setShape(GradientDrawable.RECTANGLE);
            gd.setCornerRadius(16 * density);
            gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            gd.setColors(new int[]{Color.parseColor("#ff8a19"), Color.parseColor("#ff6119")});

            mTvIntegralGain.setBackground(gd);

            GradientDrawable gd2 = new GradientDrawable();
            gd2.setCornerRadius(16 * density);
            gd2.setStroke((int) (0.8 * density), ContextCompat.getColor(mContext, R.color.color_ff8f19));
            mFlWriteAnswer.setBackground(gd2);
        }
    }
}
