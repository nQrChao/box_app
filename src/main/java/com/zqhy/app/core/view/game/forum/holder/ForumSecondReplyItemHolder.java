package com.zqhy.app.core.view.game.forum.holder;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.forum.ForumReplyTopExplicitVo;
import com.zqhy.app.core.view.community.user.CommunityUserFragment;
import com.zqhy.app.core.view.game.forum.tool.GlideImageGetter;
import com.zqhy.app.newproject.R;

/**
 *
 * @author Administrator
 * @date 2018/11/24
 */

public class ForumSecondReplyItemHolder extends AbsItemHolder<ForumReplyTopExplicitVo, ForumSecondReplyItemHolder.ViewHolder> {
    private OnClickInterface clickInterface;

    public void setClickInterface(OnClickInterface clickInterface) {
        this.clickInterface = clickInterface;
    }
    public interface OnClickInterface {
        void onSecondReply(ForumReplyTopExplicitVo bean);
    }
    public ForumSecondReplyItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ForumReplyTopExplicitVo item) {
        GlideImageGetter imageGetter = new GlideImageGetter(mContext, holder.tv_content);
        String content = item.getContent();
        content = content.replaceAll("\\\\", "");
        Spanned spanned = Html.fromHtml(content, imageGetter, null);
        if (spanned!=null) {
            SpannableStringBuilder spannable = new SpannableStringBuilder(spanned);
            if (item.getAt_nickname()!=null&&!item.getAt_nickname().isEmpty()){
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        ds.setColor(Color.parseColor("#999999"));
                    }
                    @Override
                    public void onClick(View widget) {
                        // 在这里处理点击事件
                        // 例如，可以弹出一个提示框或者执行其他操作
                    }
                };

                String prefix = "@"+item.getAt_nickname();
                spannable.insert(0,prefix);
                spannable.setSpan(clickableSpan, 0, prefix.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                holder.tv_content.setText(removeTrailingNewLines(spannable));
            }else {
                holder.tv_content.setText(removeTrailingNewLines(spanned));
            }
        }


        holder.tv_name.setText(item.getNickname()+":");
        holder.tv_name.setOnClickListener(view -> {
            if (item.getPlat_id()==4) {
                _mFragment.startFragment(CommunityUserFragment.newInstance(item.getUid()));
            }else {
                Toaster.show("ta很神秘");
                //ToastT.warning("ta很神秘");
            }
        });
        holder.tv_content.setOnClickListener(v -> {
            if (clickInterface!=null) {
                clickInterface.onSecondReply(item);
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_forum_second_reply;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private TextView tv_content;
        private TextView tv_name;


        public ViewHolder(View view) {
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            tv_content = view.findViewById(R.id.tv_content);
        }
    }
    public Spanned removeTrailingNewLines(Spanned spanned) {
        if (spanned == null) {
            return null;
        }
        // 将 Spanned 转换为 String
        String text = spanned.toString();
        // 去除末尾的换行符
        text = text.replaceAll("\\n+$", "");
        // 创建一个新的 SpannableString 来保留样式
        SpannableString spannableString = new SpannableString(text);
        // 复制原有的样式到新的 SpannableString
        Object[] spans = spanned.getSpans(0, spanned.length(), Object.class);
        for (Object span : spans) {
            int start = spanned.getSpanStart(span);
            int end = spanned.getSpanEnd(span);
            int flags = spanned.getSpanFlags(span);
            spannableString.setSpan(span, start, end, flags);
        }
        return spannableString;
    }
}
