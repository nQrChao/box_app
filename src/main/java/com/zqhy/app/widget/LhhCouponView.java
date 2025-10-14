package com.zqhy.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.zqhy.app.newproject.R;


/**
 * Created by Administrator on 2018/6/4.
 */

public class LhhCouponView extends FrameLayout {


    private static final int DEFAULT_RADIUS = 5;
    private static final int DEFAULT_DASH_LINE_GAP = 5;
    private static final int DEFAULT_DASH_LINE_GAP_WIDTH = 1;
    private static final int DEFAULT_DASH_LINE_GAP_HEIGHT = 1;
    private static final int DEFAULT_DASH_LINE_COLOR = 0xFFFFFFFF;
    private static final int DEFAULT_SEMICIRCLE_COLOR = 0xFFFFFFFF;

    /**
     * 半圆半径
     */
    private float semicircleRadius = DEFAULT_RADIUS;
    /**
     * 半圆颜色
     */
    private int semicircleColor = DEFAULT_SEMICIRCLE_COLOR;
    /**
     * 坐标点
     */
    private float x_point;

    /**
     * 虚线的间距
     */
    private float dashLineGap = DEFAULT_DASH_LINE_GAP;
    /**
     * 虚线的宽度
     */
    private float dashLineWidth = DEFAULT_DASH_LINE_GAP_WIDTH;
    /**
     * 虚线的高度
     */
    private float dashLineHeight = DEFAULT_DASH_LINE_GAP_HEIGHT;
    /**
     * 虚线的颜色
     */
    private int dashLineColor = DEFAULT_DASH_LINE_COLOR;


    /**
     * view宽度
     */
    private int viewWidth;

    /**
     * view的高度
     */
    private int viewHeight;

    public LhhCouponView(@NonNull Context context) {
        super(context);
        init(context, null, 0);
    }

    public LhhCouponView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public LhhCouponView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private Context mContext;

    private void init(Context context, AttributeSet attrs, int defStyle) {
        this.mContext = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Lhh_CouponView, defStyle, 0);
        semicircleRadius = a.getDimensionPixelSize(R.styleable.Lhh_CouponView_semicircle_radius, dp2Px(DEFAULT_RADIUS));
        semicircleColor = a.getColor(R.styleable.Lhh_CouponView_semicircle_color, DEFAULT_SEMICIRCLE_COLOR);
        x_point = a.getDimensionPixelSize(R.styleable.Lhh_CouponView_x_point, 0);

        dashLineGap = a.getDimensionPixelSize(R.styleable.Lhh_CouponView_dash_line_gap, dp2Px(DEFAULT_DASH_LINE_GAP));
        dashLineWidth = a.getDimensionPixelSize(R.styleable.Lhh_CouponView_dash_line_width, dp2Px(DEFAULT_DASH_LINE_GAP_WIDTH));
        dashLineHeight = a.getDimensionPixelSize(R.styleable.Lhh_CouponView_dash_line_height, dp2Px(DEFAULT_DASH_LINE_GAP_HEIGHT));
        dashLineColor = a.getColor(R.styleable.Lhh_CouponView_dash_line_color, DEFAULT_DASH_LINE_COLOR);
        a.recycle();

        initPaint();
    }

    /**
     * 半圆画笔
     */
    private Paint semicirclePaint;

    /**
     * 虚线画笔
     */

    private Paint dashLinePaint;

    private void initPaint() {
        semicirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        semicirclePaint.setDither(true);
        semicirclePaint.setColor(semicircleColor);
        semicirclePaint.setStyle(Paint.Style.FILL);

        dashLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dashLinePaint.setDither(true);
        dashLinePaint.setColor(dashLineColor);
        dashLinePaint.setStyle(Paint.Style.FILL);
    }


    /**
     * 绘制虚线后Y轴剩余距离
     */
    private int remindDashLineY;

    /**
     * 半圆数量Y
     */
    private int dashLineNumY;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
        calculate();
    }


    private void calculate() {
        remindDashLineY = (int) ((viewHeight + dashLineGap) % (dashLineWidth + dashLineGap));
        dashLineNumY = (int) ((viewHeight + dashLineGap) / (dashLineWidth + dashLineGap));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //semicircle
        //LEFT-TOP
        {
            float x = 0, y = 0;
            canvas.drawCircle(x, y, semicircleRadius, semicirclePaint);
        }
        //RIGHT-TOP
        {
            float x = viewWidth, y = 0;
            canvas.drawCircle(x, y, semicircleRadius, semicirclePaint);
        }
        //LEFT-BOTTOM
        {
            float x = 0, y = viewHeight;
            canvas.drawCircle(x, y, semicircleRadius, semicirclePaint);
        }
        //RIGHT-BOTTOM
        {
            float x = viewWidth, y = viewHeight;
            canvas.drawCircle(x, y, semicircleRadius, semicirclePaint);
        }

        //x_point  TOP
        {
            float x = x_point, y = 0;
            canvas.drawCircle(x, y, semicircleRadius, semicirclePaint);
        }
        //x_point  BOTTOM
        {
            float x = x_point, y = viewHeight;
            canvas.drawCircle(x, y, semicircleRadius, semicirclePaint);
        }


        //GASH-LINE
        {
            for (int i = 0; i < dashLineNumY; i++) {
                float y = remindDashLineY / 2 + (dashLineGap + dashLineHeight) * i;
                float left = x_point;
                float top = y;
                float right = x_point + dashLineWidth;
                float bottom = y + dashLineHeight;
                canvas.drawRect(left, top, right, bottom, dashLinePaint);
            }
        }


    }


    private int dp2Px(float dp) {
        return (int) (dp * mContext.getResources().getDisplayMetrics().density + 0.5f);
    }

    private int px2Dp(float px) {
        return (int) (px / mContext.getResources().getDisplayMetrics().density + 0.5f);
    }


    public void setSemicircleRadius(float semicircleRadius) {
        if (this.semicircleRadius != semicircleRadius) {
            this.semicircleRadius = semicircleRadius;
            invalidate();
        }
    }

    public void setSemicircleColor(int semicircleColor) {
        if (this.semicircleColor != semicircleColor) {
            this.semicircleColor = semicircleColor;
            if (semicirclePaint != null) {
                semicirclePaint.setColor(semicircleColor);
            }
            invalidate();
        }
    }

    public void setDashLineColorColor(int dashLineColor) {
        if (this.dashLineColor != dashLineColor) {
            this.dashLineColor = dashLineColor;
            if (dashLinePaint != null) {
                dashLinePaint.setColor(dashLineColor);
            }
            invalidate();
        }
    }


    public void setX_point(float x_point) {
        if (this.x_point != x_point) {
            this.x_point = x_point;
            calculate();
            invalidate();
        }
    }

}
