package com.zqhy.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class RingView extends View {
    private Paint paint;
    private Context context;

    public RingView(Context context) {
        this(context, null);
    }

    public RingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.paint = new Paint();
        //消除锯齿
        this.paint.setAntiAlias(true);
        //绘制空心圆
        this.paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int center = dip2px(context, 50);
        //设置内圆半径
        int innerCircle = center;
        //设置圆环宽度
        int ringWidth = dip2px(context, 10);

        //绘制内圆
        this.paint.setColor(Color.RED);
        this.paint.setStrokeWidth(dip2px(context,5));
        canvas.drawCircle(center, center, innerCircle, this.paint);
//
//        //绘制圆环
//        this.paint.setColor(Color.BLUE);
//        this.paint.setStrokeWidth(ringWidth);
//        canvas.drawCircle(center, center, innerCircle + 1 + ringWidth / 2, this.paint);
//
//        //绘制外圆
//        this.paint.setColor(Color.GREEN);
//        this.paint.setStrokeWidth(dip2px(context,15));
//        canvas.drawCircle(center, center, innerCircle + ringWidth, this.paint);


    }


    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
