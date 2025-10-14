package com.zqhy.app.core.view.community.comment.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.community.CommunityInfoVo;
import com.zqhy.app.core.data.model.community.comment.CommentInfoVo;
import com.zqhy.app.core.view.community.comment.CommentDetailFragment;
import com.zqhy.app.core.view.community.user.CommunityUserFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.widget.imageview.ClipRoundImageView;

/**
 * @author Administrator
 */
public class NewCommentDetailReplyHolder extends AbsItemHolder<CommentInfoVo.ReplyInfoVo, NewCommentDetailReplyHolder.ViewHolder> {

    public NewCommentDetailReplyHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_comment_detail_reply_new;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CommentInfoVo.ReplyInfoVo item) {

        CommunityInfoVo communityInfoVo = item.getCommunity_info();
        if (communityInfoVo != null) {
            GlideUtils.loadCircleImage(mContext, communityInfoVo.getUser_icon(), holder.mCivPortrait, R.mipmap.ic_user_login_new_sign);
            holder.mTvUserNickname1.setText(communityInfoVo.getUser_nickname());

            holder.mCivPortrait.setOnClickListener(v -> goToUserMinePage(communityInfoVo.getUser_id()));
            holder.mTvUserNickname1.setOnClickListener(v -> goToUserMinePage(communityInfoVo.getUser_id()));
        }
        try {
            long ms = item.getReply_time() * 1000;
            holder.mTvCommentTime.setText(CommonUtils.formatTimeStamp(ms, "MM-dd HH:mm"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean hasMoreReply = false;
        CommunityInfoVo otherCommunityInfoVo = item.getTo_community_info();
        int toUid = otherCommunityInfoVo == null ? 0 : otherCommunityInfoVo.getUser_id();
        String toNickname = otherCommunityInfoVo == null ? "" : otherCommunityInfoVo.getUser_nickname();
        if (!TextUtils.isEmpty(toNickname)) {
            toNickname = "回复@" + toNickname + ":";
            hasMoreReply = true;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(toNickname).append(item.getContent());
        SpannableString spannableString = new SpannableString(sb.toString());
        int nicknameColor = ContextCompat.getColor(mContext, R.color.color_336ba7);

        if (hasMoreReply) {
            int nicknameStartLine = 2;
            int nicknameEndLine = toNickname.length();
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setUnderlineText(false);
                    ds.setColor(nicknameColor);
                }

                @Override
                public void onClick(View view) {
                    //跳转tonickname用户主页
                    goToUserMinePage(toUid);
                }
            }, nicknameStartLine, nicknameEndLine, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        int contentStartLine = toNickname.length();
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }

            @Override
            public void onClick(View view) {
                replyComment(item);
            }
        }, contentStartLine, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.mTvCommentReply.setMovementMethod(LinkMovementMethod.getInstance());
        holder.mTvCommentReply.setText(spannableString);

        holder.mTvReplyPraise.setText(String.valueOf(item.getLike_count()));
        if (item.getMe_like() == 1) {
            holder.mTvReplyPraise.setTextColor(ContextCompat.getColor(mContext, R.color.color_8e8e8e));
            holder.mTvReplyPraise.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_new_game_comment_like_select), null, null, null);
            holder.mTvReplyPraise.setEnabled(false);
        } else {
            holder.mTvReplyPraise.setTextColor(ContextCompat.getColor(mContext, R.color.color_8e8e8e));
            holder.mTvReplyPraise.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_new_game_comment_like), null, null, null);
            holder.mTvReplyPraise.setEnabled(true);
        }

        holder.mTvReplyPraise.setOnClickListener(v -> setReplyLike(item.getRid()));
    }

    private void setReplyLike(int rid) {
        if (_mFragment != null && _mFragment instanceof CommentDetailFragment) {
            ((CommentDetailFragment) _mFragment).setReplyLike(rid);
        }
    }

    private void replyComment(CommentInfoVo.ReplyInfoVo item) {
        if (_mFragment != null && _mFragment instanceof CommentDetailFragment) {
            ((CommentDetailFragment) _mFragment).replyComment(item);
        }
    }

    private void goToUserMinePage(int user_id) {
        if (_mFragment != null) {
            _mFragment.start(CommunityUserFragment.newInstance(user_id));
        }
    }

    public class ViewHolder extends AbsHolder {

        private ClipRoundImageView mCivPortrait;
        private TextView           mTvUserNickname1;
        private TextView           mTvCommentReply;
        private TextView           mTvCommentTime;
        private TextView           mTvReplyPraise;


        public ViewHolder(View view) {
            super(view);
            mCivPortrait = findViewById(R.id.civ_portrait);
            mTvUserNickname1 = findViewById(R.id.tv_user_nickname_1);
            mTvCommentReply = findViewById(R.id.tv_comment_reply);
            mTvCommentTime = findViewById(R.id.tv_comment_time);
            mTvReplyPraise = findViewById(R.id.tv_reply_praise);

        }
    }
}
