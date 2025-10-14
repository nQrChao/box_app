package com.zqhy.app.core.view.strategy;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.core.view.SimpleFragment;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/13
 */

public class DiscountStrategyFragment extends SimpleFragment {

    @Override
    protected View getSimpleView() {
        FrameLayout mFrameLayout = new FrameLayout(_mActivity);
        mFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        ImageView imageView = new ImageView(_mActivity);
        Drawable drawable = _mActivity.getResources().getDrawable(R.mipmap.img_discount_strategy);
        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();


        int paramWidth = ScreenUtils.getScreenWidth(_mActivity);
        int paramHeight = paramWidth * drawableHeight / drawableWidth;

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(paramWidth, paramHeight);
        imageView.setLayoutParams(params);
        imageView.setImageResource(R.mipmap.img_discount_strategy);

        mFrameLayout.addView(imageView);

        View clickView = new View(_mActivity);
        FrameLayout.LayoutParams clickViewParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (72 * density));
        clickViewParams.gravity = Gravity.BOTTOM;
        clickView.setLayoutParams(clickViewParams);

        clickView.setOnClickListener(view -> {
            pop();
        });

        mFrameLayout.addView(clickView);
        return mFrameLayout;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "省钱攻略";
    }

    @Override

    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("省钱攻略");
    }
}
