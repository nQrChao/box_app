package com.zqhy.app.widget.state;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.tqzhang.stateview.stateview.BaseStateControl;
import com.zqhy.app.newproject.R;


/**
 *
 * @author Administrator
 * @date 2018/11/9
 */

public class EmptyState extends BaseStateControl {

    public static final String MY_LIST="1";
    public static final String OUR_LIST="2";
    public static final String NONE="3";
    @Override
    protected int onCreateView() {
        return R.layout.common_empty_my;
    }

    @Override
    public boolean isVisible() {
        return super.isVisible();
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return false;
    }

    @Override
    protected void onViewCreate(Context context, View view) {
        ImageView errorIcon = view.findViewById(R.id.show);
        if (view.getTag() != null) {
            if (view.getTag().equals(MY_LIST)) {
                errorIcon.setVisibility(View.VISIBLE);
                errorIcon.setImageResource(R.mipmap.img_empty_data_1);
            } else if (view.getTag().equals(OUR_LIST)) {
                errorIcon.setVisibility(View.VISIBLE);
                errorIcon.setImageResource(R.mipmap.img_empty_data_2);
            }else{
                errorIcon.setVisibility(View.GONE);
                errorIcon.setImageResource(R.mipmap.img_empty_data_2);
            }
        }
    }
}
