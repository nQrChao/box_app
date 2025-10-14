package com.zqhy.mod.view.guide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

public class GuideViewJ extends FrameLayout {

    /**
     * [已扩展] 定义引导图的八个方位
     */
    public enum Position {
        // 居中对齐
        TOP_CENTER,
        BOTTOM_CENTER,
        LEFT_CENTER,
        RIGHT_CENTER,
        // 边缘对齐 (新)
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }

    private final View targetView;
    private final Bitmap guideBitmap; // 最终用于绘制的、已经处理好尺寸的位图
    private final Position guideImagePosition;
    private final int cornerRadius;
    private final int highlightPadding;

    private final Paint backgroundPaint;
    private final Paint clearPaint;
    private final Rect targetRect;

    private GuideViewJ(@NonNull Builder builder) {
        super(builder.activity);
        this.targetView = builder.targetView;
        this.guideImagePosition = builder.guideImagePosition;
        this.cornerRadius = builder.cornerRadius;
        this.highlightPadding = builder.highlightPadding;
        this.targetRect = new Rect();

        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), builder.guideImageResId);
        if (originalBitmap == null) {
            throw new IllegalArgumentException("Provided guideImageResId could not be decoded into a Bitmap. " +
                    "Please check if the resource is a valid image (PNG, JPG, etc.) and not an XML drawable.");
        }

        if (builder.guideImageWidth > 0 && builder.guideImageHeight > 0) {
            this.guideBitmap = Bitmap.createScaledBitmap(originalBitmap, builder.guideImageWidth, builder.guideImageHeight, true);
            if (originalBitmap != this.guideBitmap) { // 防止原始bitmap和缩放后的是同一个对象
                originalBitmap.recycle();
            }
        } else {
            this.guideBitmap = originalBitmap;
        }

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(Color.parseColor("#80000000"));
        clearPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setBackgroundColor(Color.TRANSPARENT);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);

        targetView.getGlobalVisibleRect(targetRect);
        targetRect.inset(-highlightPadding, -highlightPadding);

        RectF highlightRectF = new RectF(targetRect);
        canvas.drawRoundRect(highlightRectF, cornerRadius, cornerRadius, clearPaint);

        if (guideBitmap == null || guideBitmap.isRecycled()) {
            return;
        }

        float imageX = 0, imageY = 0;
        float space = 30f; // 图片与高亮区域的间距

        // [已更新] 增加了对新位置的处理逻辑
        switch (guideImagePosition) {
            case TOP_CENTER:
                imageX = highlightRectF.centerX() - guideBitmap.getWidth() / 2f;
                imageY = highlightRectF.top - guideBitmap.getHeight() - space;
                break;
            case BOTTOM_CENTER:
                imageX = highlightRectF.centerX() - guideBitmap.getWidth() / 2f;
                imageY = highlightRectF.bottom + space;
                break;
            case LEFT_CENTER:
                imageX = highlightRectF.left - guideBitmap.getWidth() - space;
                imageY = highlightRectF.centerY() - guideBitmap.getHeight() / 2f;
                break;
            case RIGHT_CENTER:
                imageX = highlightRectF.right + space;
                imageY = highlightRectF.centerY() - guideBitmap.getHeight() / 2f;
                break;
            // 新增的位置计算逻辑
            case TOP_LEFT:
                imageX = highlightRectF.left;
                imageY = highlightRectF.top - guideBitmap.getHeight() - space;
                break;
            case TOP_RIGHT:
                imageX = highlightRectF.right - guideBitmap.getWidth();
                imageY = highlightRectF.top - guideBitmap.getHeight() - space;
                break;
            case BOTTOM_LEFT:
                imageX = highlightRectF.left;
                imageY = highlightRectF.bottom + space;
                break;
            case BOTTOM_RIGHT:
                imageX = highlightRectF.right - guideBitmap.getWidth() - 20;
                imageY = highlightRectF.bottom + space;
                break;
        }
        canvas.drawBitmap(guideBitmap, imageX, imageY, null);
    }

    /**
     * GuideViewJ的构造器
     */
    public static class Builder {
        private final Activity activity;
        private View targetView;
        @DrawableRes
        private int guideImageResId;
        // [已更新] 默认位置修改为更明确的 TOP_CENTER
        private Position guideImagePosition = Position.TOP_CENTER;
        private int cornerRadius = 30;
        private int highlightPadding = 10;
        private int guideImageWidth = -1;
        private int guideImageHeight = -1;

        public Builder(@NonNull Activity activity) {
            this.activity = activity;
        }

        public Builder setTargetView(@NonNull View targetView) {
            this.targetView = targetView;
            return this;
        }

        public Builder setGuideImage(@DrawableRes int guideImageResId) {
            this.guideImageResId = guideImageResId;
            return this;
        }

        public Builder setGuideImagePosition(@NonNull Position position) {
            this.guideImagePosition = position;
            return this;
        }

        public Builder setCornerRadius(int radius) {
            this.cornerRadius = radius;
            return this;
        }

        public Builder setHighlightPadding(int padding) {
            this.highlightPadding = padding;
            return this;
        }

        public Builder setGuideImageSize(int widthInPx, int heightInPx) {
            this.guideImageWidth = widthInPx;
            this.guideImageHeight = heightInPx;
            return this;
        }

        public Builder setGuideImageSizeInDp(int widthInDp, int heightInDp) {
            this.guideImageWidth = dpToPx(activity, widthInDp);
            this.guideImageHeight = dpToPx(activity, heightInDp);
            return this;
        }

        public GuideViewJ build() {
            if (targetView == null) {
                throw new IllegalStateException("TargetView cannot be null.");
            }
            if (guideImageResId == 0) {
                throw new IllegalStateException("GuideImageResId cannot be 0.");
            }
            return new GuideViewJ(this);
        }

        public View getTargetView() {
            return targetView;
        }

        private static int dpToPx(Context context, float dp) {
            return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
        }
    }
}
