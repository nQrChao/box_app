package com.zqhy.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2020/12/3-15:41
 * @description
 */
public class TitleTextView extends RelativeLayout {

    public int defaultTextColor  = Color.BLACK;
    public int defaultLineColor  = Color.BLUE;
    public int defaultLineHeight = 6;
    public int defaultTextSize   = 12;

    protected int     textColor;
    protected float   textSize;
    protected String  textContent;
    protected boolean isTextBold;
    protected int     lineColor, lineStartColor, lineEndColor;
    protected int lineHeight;

    private final boolean isAddLine = false;

    public TitleTextView(Context context) {
        this(context, null);
    }

    public TitleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TitleTextView);
        textColor = a.getColor(R.styleable.TitleTextView_tt_textColor, defaultTextColor);
        textSize = a.getDimension(R.styleable.TitleTextView_tt_textSize, defaultTextSize);
        textContent = a.getString(R.styleable.TitleTextView_tt_text);
        isTextBold = a.getBoolean(R.styleable.TitleTextView_tt_isTextBold, true);
        lineColor = a.getColor(R.styleable.TitleTextView_tt_lineColor, defaultLineColor);
        lineStartColor = a.getColor(R.styleable.TitleTextView_tt_lineStartColor, defaultLineColor);
        lineEndColor = a.getColor(R.styleable.TitleTextView_tt_lineEndColor, defaultLineColor);
        lineHeight = a.getDimensionPixelSize(R.styleable.TitleTextView_tt_lineColor, ScreenUtil.dp2px(context, defaultLineHeight));
        bindViews(context);
        a.recycle();
    }

    protected TextView mText;
    protected TextView mLine;

    private void initViews(Context context) {
        mText = new TextView(context);
        mText.setId(R.id.tag_first);
        mLine = new TextView(context);
    }

    private void bindViews(Context context) {
        mText.setText(textContent);
        mText.setTextColor(textColor);
        mText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mText.setIncludeFontPadding(true);
        mText.setTypeface(Typeface.defaultFromStyle(isTextBold ? Typeface.BOLD : Typeface.NORMAL));

        RelativeLayout.LayoutParams mTextParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(mText, mTextParams);

        if (isAddLine) {
            RelativeLayout.LayoutParams mLineParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, lineHeight);
            mLineParams.addRule(ALIGN_BOTTOM, mText.getId());
            mLine.setLayoutParams(mLineParams);
            setLineColors();
            addView(mLine, 0);
            mText.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
        }
    }

    private void setLineColors() {
        if (isAddLine) {
            GradientDrawable gd = new GradientDrawable();
            gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            gd.setCornerRadius(lineHeight);
            gd.setColors(new int[]{lineStartColor, lineEndColor});
            mLine.setBackground(gd);
        }
    }

    ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            RelativeLayout.LayoutParams params = (LayoutParams) mLine.getLayoutParams();
            params.width = mText.getWidth();
            mLine.setLayoutParams(params);
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isAddLine) {
            mText.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
        }
    }

    public void setText(String mainTitle) {
        textContent = mainTitle;
        mText.setText(mainTitle);
        //        setLineWidth();
    }

    public void setColors(int startColor, int endColor) {
        lineStartColor = startColor;
        lineEndColor = endColor;
        setLineColors();
    }

}
