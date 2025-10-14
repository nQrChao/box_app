package com.zqhy.app.utils;

import android.view.View;

import com.zqhy.app.App;
import com.zqhy.app.core.tool.ScreenUtil;

/**
 * @author Administrator
 * @date 2018/11/30
 */

public class PopupWindowUtils {


    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     *
     * @param anchorView
     * @param contentView
     * @return window显示的左上角的xOff, yOff坐标
     */
    public static int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        final int anchorPaddingTop = anchorView.getPaddingTop();

        // 获取屏幕的高宽
        final int screenHeight = ScreenUtil.getScreenHeight(App.instance());
        final int screenWidth = ScreenUtil.getScreenWidth(App.instance());

        // 测量contentView
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            //向下
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight + anchorHeight - anchorPaddingTop;
        } else {
            //向上
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight - anchorHeight + anchorPaddingTop;
        }
        return windowPos;
    }


    /**
     * pop自动调整位置显示在左右两侧
     *
     * @param anchorView  呼出window的view
     * @param contentView window的内容布局
     * @return window显示的左上角的xOff, yOff坐标
     */
    public static int[] calculatePopWindow(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        final int anchorWidth = anchorView.getWidth();
        final int anchorPaddingTop = anchorView.getPaddingTop();
        final int anchorPaddingLeft = anchorView.getPaddingLeft();

        // 获取屏幕的高宽
        final int screenHeight = ScreenUtil.getScreenHeight(App.instance());
        final int screenWidth = ScreenUtil.getScreenWidth(App.instance());

        // 测量contentView
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();

        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight + anchorHeight - anchorPaddingTop;
        } else {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight - anchorHeight + anchorPaddingTop;
        }
        boolean isRight = (screenWidth - anchorLoc[0] - anchorWidth > windowWidth);
        //偏移量
        int xOff = ScreenUtil.dp2px(App.instance(), 20);
        if (isRight) {
            //右
            windowPos[0] = anchorLoc[0] + anchorWidth + anchorPaddingLeft + xOff;
        } else {
            //左
            windowPos[0] = anchorLoc[0] - windowWidth - anchorPaddingLeft - xOff;
        }
        return windowPos;
    }
}
