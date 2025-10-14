package com.zqhy.app.core.view.community.qa.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.community.CommunityInfoVo;
import com.zqhy.app.core.data.model.community.qa.AnswerInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.community.qa.GameQaDetailFragment;
import com.zqhy.app.core.view.community.user.CommunityUserFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.widget.imageview.ClipRoundImageView;

/**
 * @author Administrator
 */
public class AnswerItemHolder extends AbsItemHolder<AnswerInfoVo, AnswerItemHolder.ViewHolder> {

    public AnswerItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_qa_answer;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull AnswerInfoVo item) {
        CommunityInfoVo communityInfoVo = item.getCommunity_info();
        if (communityInfoVo != null) {
            GlideUtils.loadCircleImage(mContext, communityInfoVo.getUser_icon(), holder.mProfileImage, R.mipmap.ic_user_login);
            holder.mTvUserNickname.setText(communityInfoVo.getUser_nickname());
            holder.mFlUserLevel.setVisibility(View.VISIBLE);
            UserInfoModel.setUserLevel(communityInfoVo.getUser_level(), holder.mIvUserLevel, holder.mTvUserLevel);
        }

        holder.mTvGameUserAnswer.setText(item.getContent());
        holder.mTvAnswerTime.setText(CommonUtils.formatTimeStamp(item.getAdd_time() * 1000, "MM月dd日"));

        if (item.getReward_integral() == 0) {
            holder.mLlIntegralGain.setVisibility(View.GONE);
        } else {
            //2019.03.15 暂时屏蔽
//            holder.mLlIntegralGain.setVisibility(View.VISIBLE);
//            holder.mTvIntegralGain.setText("+" + String.valueOf(item.getReward_integral()));
        }

        holder.mProfileImage.setOnClickListener(view -> {
            if (_mFragment != null) {
                _mFragment.start(CommunityUserFragment.newInstance(item.getUid()));
            }
        });
        holder.mTvUserNickname.setOnClickListener(view -> {
            if (_mFragment != null) {
                _mFragment.start(CommunityUserFragment.newInstance(item.getUid()));
            }
        });

        holder.mTvLikeCount.setText(String.valueOf(item.getLike_count()));
        if (item.getMe_like() == 1) {
            holder.mTvLikeCount.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff8f19));
            holder.mTvLikeCount.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_new_game_comment_like_select), null, null, null);
            holder.mTvLikeCount.setEnabled(false);
        } else {
            holder.mTvLikeCount.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
            holder.mTvLikeCount.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_new_game_comment_like), null, null, null);
            holder.mTvLikeCount.setEnabled(true);
        }

        holder.mTvLikeCount.setOnClickListener(v -> {
            if (_mFragment != null && _mFragment instanceof GameQaDetailFragment) {
                ((GameQaDetailFragment) _mFragment).setAnswerLike(holder.mTvLikeCount, item.getAid());
            }
        });

    }

    public class ViewHolder extends AbsHolder {
        private ClipRoundImageView mProfileImage;
        private TextView mTvUserNickname;
        private LinearLayout mLlIntegralGain;
        private TextView mTvIntegralGain;
        private TextView mTvGameUserAnswer;
        private TextView mTvAnswerTime;
        private TextView mTvLikeCount;
        private FrameLayout mFlUserLevel;
        private ImageView mIvUserLevel;
        private TextView mTvUserLevel;

        public ViewHolder(View view) {
            super(view);
            mProfileImage = findViewById(R.id.profile_image);
            mTvUserNickname = findViewById(R.id.tv_user_nickname);
            mLlIntegralGain = findViewById(R.id.ll_integral_gain);
            mTvIntegralGain = findViewById(R.id.tv_integral_gain);
            mTvGameUserAnswer = findViewById(R.id.tv_game_user_answer);
            mTvAnswerTime = findViewById(R.id.tv_answer_time);
            mTvLikeCount = findViewById(R.id.tv_like_count);
            mFlUserLevel = findViewById(R.id.fl_user_level);
            mIvUserLevel = findViewById(R.id.iv_user_level);
            mTvUserLevel = findViewById(R.id.tv_user_level);

            GradientDrawable gd = new GradientDrawable();
            gd.setShape(GradientDrawable.RECTANGLE);
            gd.setCornerRadius(16 * ScreenUtil.getScreenDensity(mContext));
            gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            gd.setColors(new int[]{Color.parseColor("#ff8a19"), Color.parseColor("#ff6119")});

            mTvIntegralGain.setBackground(gd);
        }
    }
}
