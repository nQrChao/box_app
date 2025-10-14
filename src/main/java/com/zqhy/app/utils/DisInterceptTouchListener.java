package com.zqhy.app.utils;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class DisInterceptTouchListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v instanceof ViewGroup) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN: {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    v.onTouchEvent(event);
                    return true;
                }
                case MotionEvent.ACTION_MOVE: {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }
                case MotionEvent.ACTION_UP :
                case MotionEvent.ACTION_CANCEL: {
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
            }
            return false;
        }else {
            return false;
        }
    }
}
