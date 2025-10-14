package com.zqhy.app.core.view.community.qa.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.community.qa.UserQaCenterInfoVo;
import com.zqhy.app.core.view.community.qa.GameQaDetailFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 */
public class QaCollapsingCenterItemHolder extends AbsItemHolder<UserQaCenterInfoVo.QaCenterQuestionVo, QaCollapsingCenterItemHolder.ViewHolder> {

    public QaCollapsingCenterItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_user_qa_center_collapsing;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull UserQaCenterInfoVo.QaCenterQuestionVo item) {
        GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mIvGameIcon);
        holder.mTvGameName.setText(item.getGamename());
        holder.mTvTime.setText(CommonUtils.formatTimeStamp(item.getAdd_time() * 1000, "MM月dd日"));
        holder.mTvGameQuestionTitle.setText(item.getContent());
        StringBuilder sb = new StringBuilder();
        sb.append("查看全部");
        int startIndex = sb.toString().length();
        sb.append(item.getA_count());
        int endIndex = sb.toString().length();
        sb.append("个回答");

        SpannableString ss = new SpannableString(sb.toString());
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_ff5400)),
                startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.mTvAllAnswer.setText(ss);

        holder.mLlRootView.setOnClickListener(view -> {
            if (_mFragment != null) {
                _mFragment.start(GameQaDetailFragment.newInstance(item.getQid()));
            }
        });
    }

    public class ViewHolder extends AbsHolder {

        private LinearLayout mLlRootView;
        private ImageView mIvGameIcon;
        private TextView mTvGameName;
        private TextView mTvGameQuestionTitle;
        private TextView mTvAllAnswer;
        private TextView mTvTime;

        public ViewHolder(View view) {
            super(view);

            mTvTime = findViewById(R.id.tv_time);
            mLlRootView = findViewById(R.id.ll_rootView);
            mIvGameIcon = findViewById(R.id.iv_game_icon);
            mTvGameName = findViewById(R.id.tv_game_name);
            mTvGameQuestionTitle = findViewById(R.id.tv_game_question_title);
            mTvAllAnswer = findViewById(R.id.tv_all_answer);

        }
    }
}
