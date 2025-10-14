package com.zqhy.app.widget.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.zqhy.app.newproject.R;


/**
 * @author Administrator
 * @date 2018/4/20
 */

public class ClipRoundImageView extends AppCompatImageView {

    private float width, height;

    /**
     * 圆角大小的默认值
     * 默认为10dp
     */
    private static final int BORDER_RADIUS_DEFAULT = 10;
    private int mBorderRadius;

    public ClipRoundImageView(Context context) {
        this(context, null);
    }

    public ClipRoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipRoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RoundImageView);
        mBorderRadius = a.getDimensionPixelSize(R.styleable.RoundImageView_roundImageViewBorderRadius,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, BORDER_RADIUS_DEFAULT, getResources().getDisplayMetrics()));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (width > mBorderRadius && height > mBorderRadius) {
            Path path = new Path();
            path.moveTo(mBorderRadius, 0);
            path.lineTo(width - mBorderRadius, 0);
            path.quadTo(width, 0, width, mBorderRadius);
            path.lineTo(width, height - mBorderRadius);
            path.quadTo(width, height, width - mBorderRadius, height);
            path.lineTo(mBorderRadius, height);
            path.quadTo(0, height, 0, height - mBorderRadius);
            path.lineTo(0, mBorderRadius);
            path.quadTo(0, 0, mBorderRadius, 0);
            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }
}
