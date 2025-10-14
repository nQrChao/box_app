package com.zqhy.app.core.view.game;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zqhy.app.core.view.SimpleFragment;
import com.zqhy.app.newproject.R;

/**
 * @author pc
 * @date 2020/1/9-14:03
 * @description
 */
public class GameCommentRewardFragment extends SimpleFragment {

    @Override
    protected View getSimpleView() {
        LinearLayout mLinearLayout = new LinearLayout(_mActivity);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ImageView imageView = new ImageView(_mActivity);
        imageView.setAdjustViewBounds(true);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(params);
        imageView.setImageResource(R.mipmap.img_game_comment_reward);

        FrameLayout layout = new FrameLayout(_mActivity);
        layout.setBackgroundColor(Color.parseColor("#660E88"));

        ImageView icon = new ImageView(_mActivity);
        icon.setImageResource(R.mipmap.ic_game_comment_reward);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        layoutParams.topMargin = (int) (88 * density);
        layoutParams.bottomMargin = (int) (14 * density);

        layout.addView(icon, layoutParams);


        mLinearLayout.addView(imageView);
        mLinearLayout.addView(layout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return mLinearLayout;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("点评送好礼");
    }
}
