package com.zqhy.app.widget.tablayout;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;

import com.flyco.tablayout.SlidingTabLayout;

/**
 * @author Administrator
 * @date 2018/11/9
 */

public class SlidingTabCompat extends SlidingTabLayout {


    public SlidingTabCompat(Context context) {
        super(context);
    }

    public SlidingTabCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingTabCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable temp=super.onSaveInstanceState();
        if(temp instanceof Bundle){
            ((Bundle) temp).putInt("mCurrentTab",0);
        }
        return temp;
    }
}
