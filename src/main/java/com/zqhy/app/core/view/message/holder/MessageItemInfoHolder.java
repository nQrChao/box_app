package com.zqhy.app.core.view.message.holder;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.community.comment.CommentDetailFragment;
import com.zqhy.app.core.view.community.qa.GameQaDetailFragment;
import com.zqhy.app.db.table.message.MessageDbInstance;
import com.zqhy.app.db.table.message.MessageVo;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 * @date 2018/11/14
 */

public class MessageItemInfoHolder extends AbsItemHolder<MessageVo, MessageItemInfoHolder.ViewHolder> {

    private float density;

    public MessageItemInfoHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(context);
    }

    private int message_type;
    private Activity _mActivity;

    @Override
    public void initView(View view) {
        super.initView(view);
        message_type = (int) view.getTag(R.id.tag_first);
        _mActivity = _mFragment.getActivity();
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MessageVo item) {
        switch (message_type) {
            case 1:
                holder.mIvMessageType.setImageResource(R.mipmap.ic_message_tab_common);
                break;
            case 2:
                holder.mIvMessageType.setImageResource(R.mipmap.ic_message_tab_comment);
                break;
            case 3:
                holder.mIvMessageType.setImageResource(R.mipmap.ic_message_tab_system);
                break;
            case 4:
                holder.mIvMessageType.setImageResource(R.mipmap.ic_message_tab_game);
                break;
            default:
                break;
        }
        holder.mViewUnread.setVisibility(item.getMessage_is_read() == 1 ? View.GONE : View.VISIBLE);

        holder.mTvMessageTitle.setText(item.getMessage_title());

        String messageContent = item.getMessage_content();

        String contentActionText = item.getMessage_content_action_text();
        if (!TextUtils.isEmpty(contentActionText)) {
            StringBuilder sb = new StringBuilder(messageContent);
            sb.append("\n");
            sb.append(contentActionText);

            int startIndex = sb.length() - contentActionText.length();
            int endIndex = sb.length();

            SpannableString spannableString = new SpannableString(sb.toString());
            spannableString.setSpan(new ClickableSpan() {

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setFlags(TextPaint.UNDERLINE_TEXT_FLAG);
                    super.updateDrawState(ds);
                }

                @Override
                public void onClick(View view) {
                    AppJumpAction appJumpAction = new AppJumpAction(_mFragment.getActivity());
                    appJumpAction.jumpAction(item.getMessage_content_action());
                }
            }, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_3478f6)), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            holder.mTvMessageContent.setMovementMethod(LinkMovementMethod.getInstance());
            holder.mTvMessageContent.setText(spannableString);
        } else {
            holder.mTvMessageContent.setText(messageContent);
            if (message_type == 2) {
                holder.mTvMessageContent.setMaxLines(4);
            }
        }
        holder.mTvMessageTips.setVisibility(View.GONE);

        String messageTips = item.getMessage_tips();
        if (!TextUtils.isEmpty(messageTips)) {
            holder.mTvMessageTips.setVisibility(View.VISIBLE);
            holder.mTvMessageTips.setText(messageTips);
        }


        holder.mTvMessageAction.setVisibility(View.GONE);
        if (item.getIs_copy_message_content() == 1) {
            holder.mTvMessageAction.setVisibility(View.VISIBLE);
            holder.mTvMessageAction.setText("复制内容");
            holder.mTvMessageAction.setOnClickListener(view -> {
                if (CommonUtils.copyString(mContext, messageContent)) {
                    Toaster.show( "复制成功");
                }
            });
            holder.mTvMessageAction.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_message_action_content_copy), null, null, null);
            holder.mTvMessageAction.setCompoundDrawablePadding((int) (density * 6));
        } else if (item.getIs_go_the_original() == 1) {
            if (message_type == 2 || message_type == 3) {
                holder.mTvMessageAction.setVisibility(View.VISIBLE);
                holder.mTvMessageAction.setText("查看原文");
                holder.mTvMessageAction.setOnClickListener(view -> {
                    if (_mFragment != null) {
                        switch (item.getAction_type()) {
                            case 1:
                            case 2:
                            case 3:
                                _mFragment.start(CommentDetailFragment.newInstance(item.getComment_id()));
                                break;
                            case 4:
                                _mFragment.start(GameQaDetailFragment.newInstance(item.getQuestion_id()));
                                break;
                            default:
                                break;
                        }
                    }

                });
            }
            holder.mTvMessageAction.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_message_action_comment_detail), null, null, null);
            holder.mTvMessageAction.setCompoundDrawablePadding((int) (density * 6));
        }

        holder.mTvTime.setText(CommonUtils.friendlyTime2(item.getMessage_time() * 1000));

        //设置已读消息
        MessageDbInstance.getInstance().readMessage(item);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_message_item_info;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private ImageView mIvMessageType;
        private View mViewUnread;
        private LinearLayout mLlContainer;
        private TextView mTvMessageTitle;
        private TextView mTvMessageContent;
        private TextView mTvMessageTips;
        private TextView mTvMessageAction;
        private TextView mTvTime;

        public ViewHolder(View view) {
            super(view);
            mIvMessageType = view.findViewById(R.id.iv_message_type);
            mViewUnread = view.findViewById(R.id.view_unread);
            mLlContainer = view.findViewById(R.id.ll_container);
            mTvMessageTitle = view.findViewById(R.id.tv_message_title);
            mTvMessageContent = view.findViewById(R.id.tv_message_content);
            mTvMessageTips = view.findViewById(R.id.tv_message_tips);
            mTvMessageAction = view.findViewById(R.id.tv_message_action);
            mTvTime = view.findViewById(R.id.tv_time);

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(density * 4);
            gd.setColor(mContext.getResources().getColor(R.color.color_f2f2f2));
            mLlContainer.setBackground(gd);
        }
    }
}
