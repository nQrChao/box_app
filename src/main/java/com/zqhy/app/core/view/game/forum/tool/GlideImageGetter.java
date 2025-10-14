package com.zqhy.app.core.view.game.forum.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.box.common.glide.GlideApp;
import com.zqhy.app.core.tool.MResource;
import com.zqhy.app.newproject.R;

public class GlideImageGetter implements Html.ImageGetter {

    private Context mContext;
    private TextView mTextView;

    public GlideImageGetter(Context context, TextView textView) {
        mContext = context;
        mTextView = textView;
    }

    @Override
    public Drawable getDrawable(String source) {
        // 创建占位 Drawable
        final UrlDrawable urlDrawable = new UrlDrawable();

        if (source.contains("emoji") && !source.contains("http")) {
            // \"emoji_00\"
            String url = source.replace("\"", "").replace("\\", "");
            Drawable drawable = mContext.getResources().getDrawable(MResource.getResourceId(mContext, "mipmap", url));
            float intrinsicWidth = drawable.getIntrinsicWidth();
            float intrinsicHeight = drawable.getIntrinsicHeight();

            // 获取 TextView 的行高
            Paint.FontMetrics fontMetrics = mTextView.getPaint().getFontMetrics();
            int lineHeight = (int) Math.ceil(fontMetrics.descent - fontMetrics.ascent);

            // 计算新的宽度，保持原有的宽高比
            int newWidth = (int) (intrinsicWidth * (lineHeight / intrinsicHeight));

            // 设置 drawable 的边界，让图片底部与文字底部对齐
            int bottomOffset = (int) fontMetrics.descent;
            drawable.setBounds(0, 0, newWidth, lineHeight);
            urlDrawable.setDrawable(drawable);
            urlDrawable.setBounds(0, bottomOffset, newWidth, lineHeight + bottomOffset);
            mTextView.setText(mTextView.getText());

            mTextView.invalidate();
            mTextView.requestLayout();
        } else {
            // 使用 Glide 异步加载图片
            GlideApp.with(mContext).asBitmap()
                    .load(source)
                    .placeholder(ContextCompat.getDrawable(mContext, R.mipmap.img_placeholder_v_1))
                    .error(ContextCompat.getDrawable(mContext, R.mipmap.img_placeholder_v_1))
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            mTextView.post(() -> {
                                BitmapDrawable drawable = new BitmapDrawable(mContext.getResources(), resource);
                                float intrinsicWidth = drawable.getIntrinsicWidth();
                                float intrinsicHeight = drawable.getIntrinsicHeight();
                                //   drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                                drawable.setBounds(10, 0, mTextView.getWidth(), (int) ((mTextView.getWidth() - 10) * (intrinsicHeight / intrinsicWidth)));
                                urlDrawable.setDrawable(drawable);
                                urlDrawable.setBounds(10, 0, mTextView.getWidth(), (int) ((mTextView.getWidth() - 10) * (intrinsicHeight / intrinsicWidth)));
                                // 刷新 TextView 以显示图片
                                mTextView.setText(mTextView.getText());

                                mTextView.invalidate();
                                mTextView.requestLayout();
                            });

                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable drawable) {
                            // 处理加载清除的情况
                            mTextView.post(() -> {
                                if (drawable != null) {
                                    float intrinsicWidth = drawable.getIntrinsicWidth();
                                    float intrinsicHeight = drawable.getIntrinsicHeight();
                                    //   drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                                    drawable.setBounds(10, 0, mTextView.getWidth(), (int) ((mTextView.getWidth() - 10) * (intrinsicHeight / intrinsicWidth)));
                                    urlDrawable.setDrawable(drawable);
                                    urlDrawable.setBounds(10, 0, mTextView.getWidth(), (int) ((mTextView.getWidth() - 10) * (intrinsicHeight / intrinsicWidth)));
                                    // 刷新 TextView 以显示图片
                                    mTextView.setText(mTextView.getText());
                                    mTextView.invalidate();
                                    mTextView.requestLayout();
                                }
                            });
                        }
                    });
        }
        return urlDrawable;
    }

    private static class UrlDrawable extends Drawable {
        private Drawable drawable;

        @Override
        public void draw(android.graphics.Canvas canvas) {
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }

        @Override
        public void setAlpha(int alpha) {
            if (drawable != null) {
                drawable.setAlpha(alpha);
            }
        }

        @Override
        public void setColorFilter(android.graphics.ColorFilter cf) {
            if (drawable != null) {
                drawable.setColorFilter(cf);
            }
        }

        @Override
        public int getOpacity() {
            if (drawable != null) {
                return drawable.getOpacity();
            }
            return android.graphics.PixelFormat.TRANSLUCENT;
        }

        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
        }
    }
}
