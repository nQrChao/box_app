package com.zqhy.app.core.view.game.forum;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;

import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpannedStyleParser {
    static String TAG = "ForumPostLongFragment";

    public static int width;
    public static int height;

    public static List<Object> getAllSpans(Spanned spanned) {
        List<Object> spans = new ArrayList<>();
        // 获取所有类型的样式
        Object[] allSpans = spanned.getSpans(0, spanned.length(), Object.class);
        for (Object span : allSpans) {
            spans.add(span);
        }
        return spans;
    }

    public static <T> List<T> getSpansOfType(Spanned spanned, Class<T> type) {
        List<T> spans = new ArrayList<>();
        // 获取指定类型的样式
        T[] specificSpans = spanned.getSpans(0, spanned.length(), type);
        for (T span : specificSpans) {
            spans.add(span);
        }
        return spans;
    }

    public static List<ForumStyleBean> getSpansStyleOfType(Spanned spanned, StringBuilder stringBuilder) {
        List<ForumStyleBean> list = new ArrayList<>();
        List<Object> allSpans = getAllSpans(spanned);
        char[] charArray = stringBuilder.toString().toCharArray();
        for (char c : charArray) {
            ForumStyleBean forumStyleBean = new ForumStyleBean(c);
            list.add(forumStyleBean);
        }

        for (Object span : allSpans) {
            int start = spanned.getSpanStart(span);
            if (span instanceof StyleSpan) {
                StyleSpan styleSpan = (StyleSpan) span;
                int style = styleSpan.getStyle();
                switch (style) {
                    case 1:
                        list.get(start).setB(true);
                        break;
                    case 2:
                        list.get(start).setI(true);
                        break;
                }
            } else if (span instanceof UnderlineSpan) {
                list.get(start).setU(true);
            }
        }
        return list;
    }

    public static List<ForumTextPicBean> getImageSpansOfType(Spanned spanned, List<ForumTextPicBean> list, Context context) {
        // 获取指定类型的样式
        ImageSpan[] specificSpans = spanned.getSpans(0, spanned.length(), ImageSpan.class);
        if (list.size() == specificSpans.length) {
            for (int i = 0; i < specificSpans.length; i++) {
                if (specificSpans[i].getSource().contains("emoji_")) {
                    //emoji表情
                    ForumTextPicBean forumTextPicBean = list.get(i);
                    forumTextPicBean.setUrl(specificSpans[i].getSource());
                } else {
                    Drawable drawable = loadImageFromPath(specificSpans[i].getSource(), context);
                    if (drawable != null) {
                        float intrinsicWidth = drawable.getIntrinsicWidth();
                        float intrinsicHeight = drawable.getIntrinsicHeight();
                        drawable.setBounds(10, 0, width, (int) ((width - 10) * (intrinsicHeight / intrinsicWidth)));
                        ImageSpan imageSpan = new ImageSpan(drawable, Objects.requireNonNull(specificSpans[i].getSource()), ImageSpan.ALIGN_BOTTOM);
                        ForumTextPicBean forumTextPicBean = list.get(i);
                        forumTextPicBean.setImageSpan(imageSpan);
                        forumTextPicBean.setUrl(specificSpans[i].getSource());
                    } else {
                        drawable = context.getResources().getDrawable(R.mipmap.ic_placeholder);
                        drawable.setBounds(10, 0, 200, 200);
                        ImageSpan imageSpan = new ImageSpan(drawable, Objects.requireNonNull(specificSpans[i].getSource()), ImageSpan.ALIGN_BOTTOM);
                        ForumTextPicBean forumTextPicBean = list.get(i);
                        forumTextPicBean.setImageSpan(imageSpan);
                        forumTextPicBean.setUrl(specificSpans[i].getSource());
                    }
                }
            }
        } else {
            return new ArrayList<>();
        }
        return list;
    }

    public static void printSpanInfo(Spanned spanned) {
        Log.d(TAG, "Spanned: " + spanned);
        // 获取所有样式
        List<Object> allSpans = getAllSpans(spanned);
        Log.d(TAG, "Spanned size: " + allSpans.size());
        for (Object span : allSpans) {
            int start = spanned.getSpanStart(span);
            int end = spanned.getSpanEnd(span);
            int flags = spanned.getSpanFlags(span);
            String spanType = span.getClass().getSimpleName();
            Log.d(TAG, "Span type: " + span);
            Log.d(TAG, "Span type: " + spanType + ", Start: " + start + ", End: " + end + ", Flags: " + flags);

            // 针对不同类型的样式进行额外处理
            if (span instanceof StyleSpan) {
                StyleSpan styleSpan = (StyleSpan) span;
                int style = styleSpan.getStyle();
                Log.d(TAG, "Style: " + style);
            } else if (span instanceof URLSpan) {
                URLSpan urlSpan = (URLSpan) span;
                String url = urlSpan.getURL();
                Log.d(TAG, "URL: " + url);
            } else if (span instanceof UnderlineSpan) {
                Log.d(TAG, "Underlined text");
            } else if (span instanceof ImageSpan) {
                Log.d(TAG, "ImageSpan text");
                Log.d(TAG, "地址 " + ((ImageSpan) span).getSource());
            } else {
                Log.d(TAG, "normal text");
            }
        }
    }

    private static Drawable loadImageFromPath(String imagePath, Context context) {
        // 获取应用的Resources对象（通常你可以直接使用context.getResources()）
        Resources resources = context.getResources();

        // 使用BitmapFactory从文件路径加载Bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

        // 检查Bitmap是否成功加载
        if (bitmap != null) {
            // 将Bitmap转换为Drawable
            Drawable drawable = new BitmapDrawable(resources, bitmap);
            return drawable;
        } else {
            // 如果Bitmap加载失败，可以返回一个null或者一个默认的Drawable
            return null; // 或者 return context.getResources().getDrawable(R.drawable.default_image);
        }
    }

}