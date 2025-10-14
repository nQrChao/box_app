package com.zqhy.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2020/11/9-15:42
 * @description
 */
public class GradientColorTextView extends AppCompatTextView {


    private int startColor   = Color.parseColor("#FAF5DC");
    private int endColor = Color.parseColor("#FFE9B2");

    private final int HORIZONTAL = 1;
    private final int VERTICAL   = 2;

    private int orientation = VERTICAL;

    private LinearGradient mLinearGradient;

    public GradientColorTextView(Context context) {
        super(context);
    }

    public GradientColorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GradientColorTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientColor_TextView);
        if (typedArray != null) {
            startColor = typedArray.getColor(R.styleable.GradientColor_TextView_startColor, startColor);
            endColor = typedArray.getColor(R.styleable.GradientColor_TextView_endColor, endColor);
            orientation = typedArray.getInt(R.styleable.GradientColor_TextView_color_orientation, orientation);

            typedArray.recycle();
        }
    }


    public void setColor(int startColor, int endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
        invalidate();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        TextPaint paint = getPaint();
        if (orientation == HORIZONTAL) {
            mLinearGradient = new LinearGradient(0, 0, w, 0,
                    startColor, endColor, Shader.TileMode.CLAMP);
        } else if (orientation == VERTICAL) {
            mLinearGradient = new LinearGradient(0, 0, 0, h,
                    startColor, endColor, Shader.TileMode.CLAMP);
        }
        paint.setShader(mLinearGradient);
    }
}
