package com.zqhy.app.core.view.game.forum.tool;

import android.content.Context;
import android.text.Editable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.donkingliang.imageselector.PreviewActivity;
import com.donkingliang.imageselector.entry.Image;
import com.zqhy.app.core.data.model.community.comment.CommentInfoVo;
import com.zqhy.app.core.view.game.forum.SpannedStyleParser;

import java.util.ArrayList;
import java.util.List;

public class ClickableTextView extends androidx.appcompat.widget.AppCompatTextView {

    private OnImageClickListener onImageClickListener;

    public ClickableTextView(Context context) {
        super(context);
    }

    public ClickableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnImageClickListener(OnImageClickListener listener) {
        this.onImageClickListener = listener;
    }
    private static final int TOUCH_SLOP = 20; // 定义一个阈值，用于判断是否为点击事件
    private float startX, startY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 记录按下时的坐标
                startX = event.getX();
                startY = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                // 计算手指移动的距离
                float endX = event.getX();
                float endY = event.getY();
                float dx = Math.abs(endX - startX);
                float dy = Math.abs(endY - startY);

                // 判断是否为点击事件
                if (dx < TOUCH_SLOP && dy < TOUCH_SLOP) {
                    // 处理点击事件
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    int offset = getOffsetForPosition(x, y);
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(getText());
                    Object[] spans =spannableStringBuilder.getSpans(offset, offset, ImageSpan.class);
                    if (spans.length > 0) {
                        ImageSpan drawable = (ImageSpan) spans[0];
                        if (onImageClickListener!=null){
                            onImageClickListener.onImageClick(drawable.getSource());
                        }
                    }

//                    performClick();
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    public interface OnImageClickListener {
        void onImageClick(String imageUrl);
    }
}