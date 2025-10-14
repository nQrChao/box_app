package com.zqhy.app.widget.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/12/1
 */

public class OperationPopWindow extends PopupWindow {

    private Context context;
    private View contentView;


    int[] location = new int[2];

    public OperationPopWindow(Context context) {
        this.context = context;
        initView();
    }


    private FrameLayout mFlContainer;
    private TextView mTvContent;

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.contentView = inflater.inflate(R.layout.layout_pop_float, null);
        this.setContentView(contentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);

        mFlContainer = contentView.findViewById(R.id.fl_container);
        mTvContent = contentView.findViewById(R.id.tv_content);

    }


    /**
     * 没有半透明背景 显示popupWindow
     *
     * @param
     */
    public void showPopupWindow(View anchor, String data) {
        mTvContent.setText(data);
        //获取控件的位置坐标
        anchor.getLocationOnScreen(location);
        //获取自身的长宽高
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        float density = ScreenUtil.getScreenDensity(context);
        int padding = (int) (density * 4);

        int SCREEN_H = ScreenUtil.getScreenHeight(context);
        if (location[1] > SCREEN_H / 2 + 100) {
            //箭头向下
            mFlContainer.setBackgroundResource(R.drawable.img_bg_suspension_2);
            int yOffest = (int) (density * 0);
            this.showAtLocation(anchor, Gravity.NO_GRAVITY, (location[0]), location[1] - contentView.getMeasuredHeight() - yOffest);
        } else {
            //箭头向上
            mFlContainer.setBackgroundResource(R.drawable.img_bg_suspension);
            this.showAsDropDown(anchor, 0, 0);
        }
    }


}
