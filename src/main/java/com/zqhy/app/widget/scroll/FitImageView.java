package com.zqhy.app.widget.scroll;

import android.content.Context;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * @author pc
 * @date 2019/12/10-10:34
 * @description 图片宽度充满屏幕、高度按原始比例自适应
 */
public class FitImageView extends AppCompatImageView {

    public FitImageView(Context context) {
        super(context);
    }

    public FitImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*try {
            Drawable drawable = getDrawable();
            if (drawable != null) {
                int width = MeasureSpec.getSize(widthMeasureSpec);
                int height = (int) Math.ceil((float) width * (float) drawable.getIntrinsicHeight() / (float) drawable.getIntrinsicWidth());
                setMeasuredDimension(width, height);
            } else {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        } catch (Exception e) {
            e.printStackTrace();
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }*/
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
