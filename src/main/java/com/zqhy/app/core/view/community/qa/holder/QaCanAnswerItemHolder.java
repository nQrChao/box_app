package com.zqhy.app.core.view.community.qa.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.community.qa.UserQaCanAnswerInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.community.qa.GameQaDetailFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 */
public class QaCanAnswerItemHolder extends BaseItemHolder<UserQaCanAnswerInfoVo.AnswerInviteInfoVo, QaCanAnswerItemHolder.ViewHolder> {

    public QaCanAnswerItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_qa_can_answer;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull UserQaCanAnswerInfoVo.AnswerInviteInfoVo item) {
        GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mIvGameIcon);
        holder.mTvGameName.setText(item.getGamename());
        String strCount = String.valueOf(item.getA_count());
        StringBuilder sb = new StringBuilder();
        sb.append("查看全部");
        int startIndex = sb.toString().length();
        sb.append(strCount);
        int endIndex = sb.toString().length();
        sb.append("个回答");

        SpannableString ss = new SpannableString(sb.toString());
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_ff5400)),
                startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.mTvQaAnswerCount.setText(ss);
        holder.mTvTime.setText(CommonUtils.formatTimeStamp(item.getAdd_time()*1000,"MM-dd"));
        holder.mTvQaQuestion.setText(item.getContent());

        holder.mLlItem.setOnClickListener(v -> {
            if(_mFragment != null){
                _mFragment.start(GameQaDetailFragment.newInstance(item.getQid()));
            }
        });
    }

    public class ViewHolder extends AbsHolder {

        private LinearLayout mLlItem;
        private ImageView mIvGameIcon;
        private TextView mTvGameName;
        private TextView mTvTime;
        private TextView mTvQaQuestion;
        private TextView mTvQaAnswerCount;
        private TextView mBtnActionAnswer;

        public ViewHolder(View view) {
            super(view);
            mLlItem = findViewById(R.id.ll_item);
            mIvGameIcon = findViewById(R.id.iv_game_icon);
            mTvGameName = findViewById(R.id.tv_game_name);
            mTvTime = findViewById(R.id.tv_time);
            mTvQaQuestion = findViewById(R.id.tv_qa_question);
            mTvQaAnswerCount = findViewById(R.id.tv_qa_answer_count);
            mBtnActionAnswer = findViewById(R.id.btn_action_answer);

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(48* ScreenUtil.getScreenDensity(mContext));
            gd.setColor(Color.parseColor("#14FF8F19"));
            mBtnActionAnswer.setTextColor(ContextCompat.getColor(mContext,R.color.color_ff8f19));
            mBtnActionAnswer.setBackground(gd);
        }
    }
}
