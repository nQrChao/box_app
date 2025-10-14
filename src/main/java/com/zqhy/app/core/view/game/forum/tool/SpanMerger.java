package com.zqhy.app.core.view.game.forum.tool;

import android.os.Build;
import android.text.Editable;
import android.text.Spannable;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SpanMerger {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Editable  mergeSpans(Editable editable) {
        // 获取所有的 ForegroundColorSpan
        return mergeUnderlineSpans(mergeStyleSpans(editable));
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static  Editable mergeStyleSpans(Editable editable) {
        StyleSpan[] styleSpans = editable.getSpans(0, editable.length(), StyleSpan.class);
        if (styleSpans.length == 0) {
            return editable;
        }

        List<StyleSpanInfo> styleSpanInfos = new ArrayList<>();
        for (StyleSpan span : styleSpans) {
            int start = editable.getSpanStart(span);
            int end = editable.getSpanEnd(span);
            int style = span.getStyle();
            styleSpanInfos.add(new StyleSpanInfo(start, end, style));
        }

        styleSpanInfos.sort(Comparator.comparingInt(o -> o.start));

        List<StyleSpanInfo> mergedStyleSpans = new ArrayList<>();
        StyleSpanInfo currentStyleSpan = styleSpanInfos.get(0);
        for (int i = 1; i < styleSpanInfos.size(); i++) {
            StyleSpanInfo nextStyleSpan = styleSpanInfos.get(i);
            if (currentStyleSpan.style == nextStyleSpan.style && currentStyleSpan.end == nextStyleSpan.start) {
                currentStyleSpan.end = nextStyleSpan.end;
            } else {
                mergedStyleSpans.add(currentStyleSpan);
                currentStyleSpan = nextStyleSpan;
            }
        }
        mergedStyleSpans.add(currentStyleSpan);

        for (StyleSpan span : styleSpans) {
            editable.removeSpan(span);
        }

        for (StyleSpanInfo info : mergedStyleSpans) {
            StyleSpan newSpan = new StyleSpan(info.style);
            editable.setSpan(newSpan, info.start, info.end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return editable;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Editable mergeUnderlineSpans(Editable editable) {
        UnderlineSpan[] underlineSpans = editable.getSpans(0, editable.length(), UnderlineSpan.class);
        if (underlineSpans.length == 0) {
            return editable;
        }

        List<UnderlineSpanInfo> underlineSpanInfos = new ArrayList<>();
        for (UnderlineSpan span : underlineSpans) {
            int start = editable.getSpanStart(span);
            int end = editable.getSpanEnd(span);
            underlineSpanInfos.add(new UnderlineSpanInfo(start, end));
        }

        underlineSpanInfos.sort(Comparator.comparingInt(o -> o.start));

        List<UnderlineSpanInfo> mergedUnderlineSpans = new ArrayList<>();
        UnderlineSpanInfo currentUnderlineSpan = underlineSpanInfos.get(0);
        for (int i = 1; i < underlineSpanInfos.size(); i++) {
            UnderlineSpanInfo nextUnderlineSpan = underlineSpanInfos.get(i);
            if (currentUnderlineSpan.end == nextUnderlineSpan.start) {
                currentUnderlineSpan.end = nextUnderlineSpan.end;
            } else {
                mergedUnderlineSpans.add(currentUnderlineSpan);
                currentUnderlineSpan = nextUnderlineSpan;
            }
        }
        mergedUnderlineSpans.add(currentUnderlineSpan);

        for (UnderlineSpan span : underlineSpans) {
            editable.removeSpan(span);
        }

        for (UnderlineSpanInfo info : mergedUnderlineSpans) {
            UnderlineSpan newSpan = new UnderlineSpan();
            editable.setSpan(newSpan, info.start, info.end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return editable;
    }

    public static  class StyleSpanInfo {
        int start;
        int end;
        int style;

        StyleSpanInfo(int start, int end, int style) {
            this.start = start;
            this.end = end;
            this.style = style;
        }
    }

    public static class UnderlineSpanInfo {
        int start;
        int end;

        UnderlineSpanInfo(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
