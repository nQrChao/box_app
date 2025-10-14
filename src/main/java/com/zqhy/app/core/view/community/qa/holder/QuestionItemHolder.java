package com.zqhy.app.core.view.community.qa.holder;

import android.content.Context;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.community.CommunityInfoVo;
import com.zqhy.app.core.data.model.community.qa.QuestionInfoVo;
import com.zqhy.app.core.view.community.user.CommunityUserFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 */
public class QuestionItemHolder extends AbsItemHolder<QuestionInfoVo, QuestionItemHolder.ViewHolder> {

    private boolean isVersionGreaterThan120;

    public QuestionItemHolder(Context context) {
        super(context);
        try {
            int versionCode = context.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
            isVersionGreaterThan120 = versionCode > 120;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        isVersionGreaterThan120 = true;
    }

    @Override
    public int getLayoutResId() {
        return isVersionGreaterThan120 ? R.layout.item_qa_question_2 : R.layout.item_qa_question;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull QuestionInfoVo item) {
        if (isVersionGreaterThan120) {
            holder.mTvGameQuestionTitle.setText(item.getContent());

            int answerCount = item.getA_count();
            holder.mTvAllAnswer.setVisibility(View.VISIBLE);
            holder.mTvAllAnswer.setTextColor(ContextCompat.getColor(mContext, R.color.color_232323));
            holder.mTvAllAnswer.setText(Html.fromHtml(mContext.getResources().getString(R.string.string_game_question_all_answer, String.valueOf(answerCount))));

            holder.mTvAnswer.setVisibility(View.GONE);
            if (item.getAnswerlist() != null && item.getAnswerlist().size() > 0) {
                try {
                    if (item.getAnswerlist().size() >= 1) {
                        holder.mTvAnswer.setVisibility(View.VISIBLE);
                        holder.mTvAnswer.setText(item.getAnswerlist().get(0).getContent());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                holder.mTvAllAnswer.setVisibility(View.GONE);
                holder.mTvAnswer.setVisibility(View.VISIBLE);
                holder.mTvAnswer.setText("暂无回答，等待大神赶来~");
            }
            CommunityInfoVo communityInfoVo = item.getCommunity_info();
            if (communityInfoVo != null) {
                GlideUtils.loadCircleImage(mContext, communityInfoVo.getUser_icon(), holder.mIvUserImage, R.mipmap.ic_user_login);

                holder.mIvUserImage.setOnClickListener(v -> {
                    if (_mFragment != null) {
                        _mFragment.start(CommunityUserFragment.newInstance(communityInfoVo.getUser_id()));
                    }
                });
            }
        } else {
            holder.mTvGameQuestionTitle.setText(item.getContent());

            int answerCount = item.getA_count();
            holder.mTvAllAnswer.setText(Html.fromHtml(mContext.getResources().getString(R.string.string_game_question_all_answer, String.valueOf(answerCount))));

            if (item.getAnswerlist() != null && item.getAnswerlist().size() > 0) {
                holder.mLlAnswerList.setVisibility(View.VISIBLE);
                holder.mFlNoAnswerList.setVisibility(View.GONE);

                holder.mLlAnswer1.setVisibility(View.GONE);
                holder.mLlAnswer2.setVisibility(View.GONE);
                holder.mLlAnswerAll.setVisibility(View.VISIBLE);

                try {
                    if (item.getAnswerlist().size() >= 1) {
                        holder.mLlAnswer1.setVisibility(View.VISIBLE);
                        holder.mTvAnswer1.setText(item.getAnswerlist().get(0).getContent());
                    }
                    if (item.getAnswerlist().size() >= 2) {
                        holder.mLlAnswer2.setVisibility(View.VISIBLE);
                        holder.mTvAnswer2.setText(item.getAnswerlist().get(1).getContent());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                holder.mTvDate.setText(CommonUtils.formatTimeStamp(item.getAdd_time() * 1000, "MM月dd日"));
            } else {
                holder.mLlAnswerList.setVisibility(View.GONE);
                holder.mFlNoAnswerList.setVisibility(View.VISIBLE);
            }
        }
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mRootView;
        private TextView mTvGameQuestionTitle;
        private LinearLayout mLlAnswerList;
        private LinearLayout mLlAnswer1;
        private TextView mTvAnswer1;
        private LinearLayout mLlAnswer2;
        private TextView mTvAnswer2;
        private LinearLayout mLlAnswerAll;
        private TextView mTvAllAnswer;
        private TextView mTvDate;
        private FrameLayout mFlNoAnswerList;

        private TextView mTvAnswer;
        private ImageView mIvUserImage;

        public ViewHolder(View view) {
            super(view);
            mRootView = findViewById(R.id.rootView);
            mTvGameQuestionTitle = findViewById(R.id.tv_game_question_title);
            mLlAnswerList = findViewById(R.id.ll_answer_list);
            mLlAnswer1 = findViewById(R.id.ll_answer_1);
            mTvAnswer1 = findViewById(R.id.tv_answer_1);
            mLlAnswer2 = findViewById(R.id.ll_answer_2);
            mTvAnswer2 = findViewById(R.id.tv_answer_2);
            mLlAnswerAll = findViewById(R.id.ll_answer_all);
            mTvAllAnswer = findViewById(R.id.tv_all_answer);
            mTvDate = findViewById(R.id.tv_date);
            mFlNoAnswerList = findViewById(R.id.fl_no_answer_list);


            mIvUserImage = findViewById(R.id.iv_user_image);
            mTvAnswer = findViewById(R.id.tv_answer);
        }
    }
}
