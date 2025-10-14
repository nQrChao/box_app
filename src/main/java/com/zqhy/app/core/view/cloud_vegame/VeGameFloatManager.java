package com.zqhy.app.core.view.cloud_vegame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zqhy.app.core.tool.MResource;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.cache.ACache;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nasa on 2017/10/19.
 */

public class VeGameFloatManager implements View.OnClickListener, View.OnTouchListener {

    public interface OnClick {
        void onMyClick(View view);
    }

    private OnClick myClickListener = null;

    public void setMyClickListener(OnClick myClickListener) {
        this.myClickListener = myClickListener;
    }

    private static final String TAG = "SpeedFLOATBUTTON";
    private static final String TAG_VALUE = "SpeedFLOATBUTTON_VALUE";
    //    停靠
    //    private static final int ACTION_LOCATE = 100;
    //    变灰
    private static final int ACTION_GRAY = 101;
    //    一半
    private static final int ACTION_HALF = 102;
    //    一半变灰
    private static final int ACTION_HALF_GRAY = 103;


    private static VeGameFloatManager instance;
    private final Activity mContext;
    private final ACache aCache;
    private WindowManager mWindowManager;

    private int mScreenWidth;
    private int mScreenHeight;
    private int mFloatWidth;
    private int mFloatHeight;
    private ViewGroup mFloatLayout;
    private ImageView mFloatImage;
    private TextView mFloatText;
    private final int mFloatImageId = 0x22222;
    private final int mFloatLabelId = 0x22223;


    //    private WindowManager.LayoutParams mFloatImageLayoutParams;
    private ViewGroup.LayoutParams mFloatImageLayoutParams;

    private boolean isFloat = false;
    private int locateSide = -1;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int TOP = 3;
    public static final int BOTTOM = 4;

    private int statusBarHeight = 0;

    private int mFloatResIdSleepL;
    private int mFloatResIdSleepR;
    private int mFloatResIdNor;

    private String norNetIcon;
    private String sleepLeftNetIcon;
    private String sleepRightNetIcon;
    private String sleepTopNetIcon;
    private String sleepBottomNetIcon;
    private boolean isFloatLocal = true;
    private boolean isShowLabel = false;
    private boolean isImageShowHalf = false;

    private boolean isCanTouch = true;
    private FrameLayout view;

    public void setCanTouch(boolean canTouch) {
        isCanTouch = canTouch;
    }

    private STATE state = STATE.ACTIVE;


    enum STATE {
        ACTIVE, FULL_GRAY, HALF, HALF_GRAY, DESTROY, MOVE
    }

    public static final String CACH_KEY = "SPEEDFLOATBUTTONCACHEKEY";
    private String positionStr;

    private VeGameFloatManager(Activity mContext) {
        Log.e(TAG, "constructor");
        this.mContext = mContext;

        aCache = ACache.get(mContext);

        //  获取status_bar_height资源的ID
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = mContext.getResources().getDimensionPixelSize(resourceId);
        }

        initView();
    }

    private static Map<String, VeGameFloatManager> mActivityMap = new HashMap<>();

    public static VeGameFloatManager getInstance(Activity mContext) {
        Log.e(TAG, "getInstance");
        /*Activity mActivityContext = null;
        if (mContext instanceof Activity) {
            mActivityContext = (Activity) mContext;
            //            mContext = mContext.getApplicationContext();
        }
        if (instance == null) {
            synchronized (FloatWindowManager.class) {
                if (instance == null) {
                    instance = new FloatWindowManager(mContext);
                }
            }
        }*/

        String activityName = mContext.getClass().getName();
        for (String name : mActivityMap.keySet()) {
            if (activityName.equals(name)) {
                return mActivityMap.get(activityName);
            }
        }

        instance = new VeGameFloatManager(mContext);
        if (!activityName.startsWith("com.zqhy.sdk.ui")) {
            instance.createFloat();
            mActivityMap.put(activityName, instance);
        }
        return instance;
    }

    public int getmFloatWidth() {
        return mFloatWidth;
    }

    public int getmFloatHeight() {
        return mFloatHeight;
    }

    private void initView() {
        Log.e(TAG, "initView");
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = SizeUtils.getScreenWidth(mContext);
        mScreenHeight = SizeUtils.getScreenHeight(mContext);

        mFloatWidth = DisplayUtil.dip2px(mContext, 40);
        mFloatHeight = DisplayUtil.dip2px(mContext, 40);

        Log.e(TAG_VALUE, "ScreenWidth - ScreenHeight - x - y : " + mScreenWidth + " - " + mScreenHeight + " - " + mFloatWidth + " - " + mFloatHeight);


        createFloatImageView();
    }
    RelativeLayout.LayoutParams mFloatlayoutParams;
    private void createFloatImageView() {
        isCanTouch = true;
        Log.e(TAG, "createFloatImageView");
        mFloatLayout = new RelativeLayout(mContext);
        mFloatImage = new ImageView(mContext);
        mFloatText = new TextView(mContext);
        mFloatImage.setId(mFloatImageId);
        View view1 = new View(mContext);
        mFloatLayout.addView(view1, new RelativeLayout.LayoutParams(mFloatWidth, mFloatHeight));

        RelativeLayout.LayoutParams tv_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DisplayUtil.dip2px(mContext, 24));
        tv_params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        tv_params.setMargins(0,DisplayUtil.dip2px(mContext, 16),0,0);
        mFloatText.setGravity(Gravity.CENTER);
        mFloatText.setTextSize(8);
        mFloatText.setTextColor(Color.WHITE);
        mFloatText.setVisibility(View.INVISIBLE);
        mFloatLayout.addView(mFloatText,tv_params);

        mFloatlayoutParams = new RelativeLayout.LayoutParams(mFloatWidth, mFloatHeight);
        mFloatLayout.addView(mFloatImage,mFloatlayoutParams);


        mFloatLayout.setOnClickListener(this);
        mFloatLayout.setOnTouchListener(this);

        mFloatLayout.setVisibility(View.VISIBLE);
        mFloatLayout.setBackgroundColor(Color.TRANSPARENT);

        setFloatImageLayoutParam();
    }

    private void setFloatImageLayoutParam() {
        Log.e(TAG, "setFloatImageLayoutParam");
        mFloatImageLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        shape_333333_big_radius
        positionStr = aCache.getAsString(CACH_KEY);
//        if (positionStr == null || positionStr.isEmpty()) {
            positionStr = "0#" + String.valueOf(mScreenHeight / 4 - mFloatHeight / 2 + mFloatHeight) + "#0";
            aCache.put(CACH_KEY, positionStr);
            //            mFloatImageLayoutParams.x = 10;
            mFloatLayout.setX(0);
            //            mFloatImageLayoutParams.y = mScreenHeight / 2 - mFloatHeight / 2;
            mFloatLayout.setY(mScreenHeight / 4 - mFloatHeight / 2 + mFloatHeight);
            //            mFloatImageLayoutParams.height = mScreenHeight / 2 - mFloatHeight / 2;
            mFloatImage.setImageResource(mFloatResIdNor);
//        } else {
//            initHistoryPosition(positionStr);
//        }
    }

    public void setText(String s){
        if (mFloatText!=null){
            mFloatText.setText(s);
        }
    }
    public void setImgResource(int res){
        this.mFloatResIdNor = res;
        this.mFloatResIdSleepL = res;
        this.mFloatResIdSleepR = res;
        if (mFloatImage!=null) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mFloatImage.getLayoutParams();
            layoutParams.height = DisplayUtil.dip2px(mContext, 15);
            layoutParams.width = DisplayUtil.dip2px(mContext, 15);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            layoutParams.setMargins(0,DisplayUtil.dip2px(mContext, 5),0,0);
            mFloatImage.setLayoutParams(layoutParams);
            mFloatImage.setImageResource(res);
            mFloatLayout.setBackgroundResource(R.drawable.shape_333333_big_radius);
            if (mFloatText!=null){
                mFloatText.setVisibility(View.VISIBLE);
            }
        }
        Log.d(TAG, "setImgResource()"+mFloatResIdNor);
        Log.d(TAG, "setImgResource()"+mFloatResIdSleepL);
        Log.d(TAG, "setImgResource()"+mFloatResIdSleepR);
    }


    public void createFloat() {
        Log.e(TAG, "createFloat()");
        // TODO: 2017/9/12 ReLogin 之后就崩溃了。
        if (mFloatLayout == null) {
            createFloatImageView();
        }

        //        mIcon.setImageResource((MResource.getResourceId(mContext, "drawable", "ic_zqsdk_cy_icon_normal")));

        setIcons(MResource.getResourceId(mContext, "mipmap", "ic_fragment_cloud_vegame_1"),
                MResource.getResourceId(mContext, "mipmap", "ic_fragment_cloud_vegame_1"));

        addFloat2Window();
    }

    public void setIcons(int sleepLeftIconResId, int sleepRightIconResId) {
        if (mFloatResIdNor !=0) {
            return;
        }
        Log.e(TAG, "setIcons start");
        if (this.mFloatResIdNor == 0) {
            this.mFloatResIdNor = sleepLeftIconResId;
        }
        this.mFloatResIdSleepL = sleepLeftIconResId;
        this.mFloatResIdSleepR = sleepRightIconResId;
//        this.mFloatResIdSleepT = sleepTopIconResId;
//        this.mFloatResIdSleepB = sleepBottomIconResId;
        Log.e(TAG, "setIcons end");
    }


    private void addFloat2Window() {
        view = (FrameLayout) mContext.getWindow().getDecorView();
        if (mFloatLayout != null && !isFloat) {
            /*if (view != null && view instanceof FrameLayout) {
                ViewGroup parent = (ViewGroup) mFloatLayout.getParent();
                if (parent!=null) {
                    parent.removeView(mFloatLayout);
                }
                view.addView(mFloatLayout, mFloatImageLayoutParams);
                isFloat = true;
            }*/
            if (view != null && view instanceof FrameLayout) {
                ViewGroup parent = (ViewGroup) mFloatLayout.getParent();
                if (parent != null) {
                    parent.removeView(mFloatLayout);
                }
                SurfaceView surfaceView = new SurfaceView(mContext);
                view.addView(surfaceView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                view.addView(mFloatLayout, mFloatImageLayoutParams);
                surfaceView.setBackgroundColor(Color.parseColor("#00000000"));
                surfaceView.setZOrderOnTop(true);
                isFloat = true;
            }
        }
        if (mFloatLayout != null && !isFloat) {
            //            mWindowManager.addView(mFloatLayout, mFloatImageLayoutParams);
            //            isFloat = true;
        }
    }


    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick view" + view.toString());
        if (touchTime <= 180 && touchTime != 0 && state == STATE.ACTIVE && movedTimes < 25) {
            if (myClickListener!=null) {
                myClickListener.onMyClick(view);
            }
        }
        if (state != STATE.ACTIVE) {
            state = STATE.ACTIVE;
        }
        movedTimes = 0;
        touchTime = 0;
        startTime = 0;
        freshLocate();
    }


    private void removeAllMessage() {
        handler.removeMessages(ACTION_GRAY);
        handler.removeMessages(ACTION_HALF);
        handler.removeMessages(ACTION_HALF_GRAY);
        handler.removeCallbacks(grayRunnable);
        handler.removeCallbacks(halfRunnable);
        handler.removeCallbacks(halfGrayRunnable);
    }

    private Runnable grayRunnable = new Runnable() {
        @Override
        public void run() {
            freshGray();
        }
    };

    private Runnable halfRunnable = new Runnable() {
        @Override
        public void run() {
            freshHalf();
        }
    };

    private Runnable halfGrayRunnable = new Runnable() {
        @Override
        public void run() {
            freshHalfGray();
        }
    };


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ACTION_GRAY:
                    handler.postDelayed(grayRunnable, 3000);
                    break;
                case ACTION_HALF:
                    handler.postDelayed(halfRunnable, 2000);
                    break;
                case ACTION_HALF_GRAY:
                    handler.postDelayed(halfGrayRunnable, 2000);
                    break;
                default:
                    break;
            }
        }
    };

    private void freshGray() {
        if (mFloatLayout != null && mWindowManager != null) {
            //            mFloatImageLayoutParams.alpha = 0.3f;
            mFloatLayout.setAlpha(0.3f);
            //            ViewGroup parent = (ViewGroup) mFloatLayout.getParent();
            //            if (parent!=null) {
            //                parent.removeView(mFloatLayout);
            //            }
            //            view.addView(mFloatLayout,mFloatImageLayoutParams);
            //            mWindowManager.updateViewLayout(mFloatLayout, mFloatImageLayoutParams);
            handler.sendEmptyMessage(ACTION_HALF);
            state = STATE.FULL_GRAY;
        }
    }

    private void activeNormal() {
        if (mFloatLayout != null && mWindowManager != null) {
            mFloatImage.setImageResource(mFloatResIdNor);
            mFloatLayout.setAlpha(1.0f);
            //            mFloatImageLayoutParams.alpha = 1.0f;
            //            mFloatLayout.setLayoutParams(mFloatImageLayoutParams);
            //            ViewGroup parent = (ViewGroup) mFloatLayout.getParent();
            //            if (parent!=null) {
            //                parent.removeView(mFloatLayout);
            //            }
            //            view.addView(mFloatLayout,mFloatImageLayoutParams);
            //            mWindowManager.updateViewLayout(mFloatLayout, mFloatImageLayoutParams);
            //            ((FrameLayout)view).updateViewLayout(mFloatLayout,mFloatImageLayoutParams);
            isImageShowHalf = false;

            setLabelLocation();
        }
    }

    private void freshHalf() {
        switch (locateSide) {
            case LEFT:
                this.mFloatResIdNor = mFloatResIdSleepL;
                mFloatImage.setImageResource(mFloatResIdSleepL);
                break;
            case RIGHT:
                this.mFloatResIdNor = mFloatResIdSleepR;
                mFloatImage.setImageResource(mFloatResIdSleepR);
                break;
            default:
                break;
        }
        if (mFloatLayout != null && mWindowManager != null) {
            //            mFloatImageLayoutParams.alpha = 1.0f;
            mFloatLayout.setAlpha(1.0f);
            //            ViewGroup parent = (ViewGroup) mFloatLayout.getParent();
            //            if (parent!=null) {
            //                parent.removeView(mFloatLayout);
            //            }
            //            view.addView(mFloatLayout,mFloatImageLayoutParams);
            //            mWindowManager.updateViewLayout(mFloatLayout, mFloatImageLayoutParams);
            isImageShowHalf = true;
            handler.sendEmptyMessage(ACTION_HALF_GRAY);
            state = STATE.HALF;
        }
    }

    private void freshHalfGray() {
        if (mFloatLayout != null && mWindowManager != null) {
            //            mFloatImageLayoutParams.alpha = 0.3f;
            mFloatLayout.setAlpha(0.3f);
            //            ViewGroup parent = (ViewGroup) mFloatLayout.getParent();
            //            if (parent!=null) {
            //                parent.removeView(mFloatLayout);
            //            }
            //            view.addView(mFloatLayout,mFloatImageLayoutParams);
            //            mWindowManager.updateViewLayout(mFloatLayout, mFloatImageLayoutParams);
            isImageShowHalf = true;
            state = STATE.HALF_GRAY;
        }
    }

    private float lastX = 0;
    private float lastY = 0;

    private long touchTime = 0;
    private long startTime = 0;

    private int movedTimes = 0;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (!isCanTouch) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Intent intent = new Intent(mContext, FloatActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(intent);
                startTime = System.currentTimeMillis();
                removeAllMessage();
                activeNormal();
                lastX = motionEvent.getRawX();
                lastY = motionEvent.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
//                try{
//                    dismissActivity = (FloatActivity) getCurrentActivity();
//                }catch (Exception e){ e.printStackTrace(); }
//                if (dismissActivity != null) {
//                    dismissActivity.setFloatButtonPosition(motionEvent.getRawX(), motionEvent.getRawY());
//                }
                movedTimes++;
                lastX = motionEvent.getRawX();
                lastY = motionEvent.getRawY();

                freshNew(lastX, lastY);
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
//                try{
//                    dismissActivity = (FloatActivity) getCurrentActivity();
//                }catch (Exception e){ e.printStackTrace(); }
//                if (dismissActivity != null) {
//                    boolean hide = dismissActivity.setFloatButtonPosition(motionEvent.getRawX(), motionEvent.getRawY());
//                    if (hide) {
//                        dismissActivity.showDialog();
//                    } else {
//                        dismissActivity.finish();
//                    }
//                    dismissActivity = null;
//                }
                long endTime = System.currentTimeMillis();
                touchTime = endTime - startTime;
                Log.d(TAG, "ACTION_UP___touchTime:" + touchTime);
                freshLocate();
                mFloatLayout.performClick();

                Log.d(TAG, "setImgResource()"+mFloatResIdNor);
                Log.d(TAG, "setImgResource()"+mFloatResIdSleepL);
                Log.d(TAG, "setImgResource()"+mFloatResIdSleepR);
//                finishFloatActivity();
                break;
            default:
                break;
        }
        return false;
    }

    public static Activity getCurrentActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(
                    null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void finishFloatActivity() {
//        if (dismissActivity != null) {
//            dismissActivity.finish();
//            dismissActivity = null;
//        }
    }

    private int nowState = LEFT;

    public int getNowState() {
        return nowState;
    }

    private void freshLocate() {
        removeAllMessage();
        int side = calculateSide();
        nowState = side;
        //        int mFloatWidth = mFloatImageLayoutParams.width;
        //        int mFloatHeight = mFloatImageLayoutParams.height;

        this.locateSide = side;
        switch (side) {
            case LEFT:
                //                mFloatImageLayoutParams.x = 10;
                mFloatLayout.setX(0);
                break;
            case RIGHT:
                mFloatLayout.setX(mScreenWidth - mFloatWidth);
                //                mFloatImageLayoutParams.x = mScreenWidth - mFloatWidth - 10;
                break;
            case TOP:
                //                mFloatImageLayoutParams.y = 10;
                mFloatLayout.setY(mFloatHeight*2);
                break;
            case BOTTOM:
                mFloatLayout.setY(mScreenHeight - mFloatHeight);
                //                mFloatImageLayoutParams.y = mScreenHeight - mFloatHeight - 10;
                break;
            default:
                try {
                    throw new Exception("calculateSide error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        if (mFloatLayout != null && mWindowManager != null) {
            //            ViewGroup parent = (ViewGroup) mFloatLayout.getParent();
            //            if (parent!=null) {
            //                parent.removeView(mFloatLayout);
            //            }
            //            view.addView(mFloatLayout,mFloatImageLayoutParams);
            //            mWindowManager.updateViewLayout(mFloatLayout, mFloatImageLayoutParams);
            handler.sendEmptyMessage(ACTION_GRAY);
            //            aCache.put(CACH_KEY, mFloatImageLayoutParams.x + "#" + mFloatImageLayoutParams.y);
            if (this.locateSide == LEFT) {
                aCache.put(CACH_KEY, (int) mFloatLayout.getX() + "#" + (int) mFloatLayout.getY() + "#0");
            } else {
                aCache.put(CACH_KEY, (int) mFloatLayout.getX() + "#" + (int) mFloatLayout.getY() + "#1");
            }
        }
//        finishFloatActivity();
    }


    private int calculateSide() {
        //        int x = mFloatImageLayoutParams.x;
        //        int y = mFloatImageLayoutParams.y;
        int x = (int) mFloatLayout.getX();
//        int y = (int) mFloatLayout.getY();
//        boolean left, top;
        if (x < mScreenWidth - x) {
            return LEFT;
        } else {
            return RIGHT;
        }
//        if (y < mScreenHeight - y) {
//            top = true;
//        } else {
//            top = false;
//        }
//        if (left && top) {
//            if (x < y) {
//                return LEFT;
//            } else {
//                return TOP;
//            }
//        }
//        if (left && (!top)) {
//            if (x < mScreenHeight - y) {
//                return LEFT;
//            } else {
//                return BOTTOM;
//            }
//        }
//        if (!left && top) {
//            if (mScreenWidth - x < y) {
//                return RIGHT;
//            } else {
//                return TOP;
//            }
//        }
//        if (!left && !top) {
//            if (mScreenWidth - x < mScreenHeight - y) {
//                return RIGHT;
//            } else {
//                return BOTTOM;
//            }
//        }
    }

    private void freshNew(float X, float Y) {
        if (mFloatLayout != null && mWindowManager != null) {

            //            int mFloatWidth = mFloatImageLayoutParams.width;
            //            int mFloatHeight = mFloatImageLayoutParams.height;

            //            mFloatImageLayoutParams.x = (int) X - mFloatWidth / 2;
            //            mFloatImageLayoutParams.y = (int) Y - mFloatHeight / 2;
            mFloatLayout.setX((int) X - mFloatWidth / 2);
            if (0 >= (Y - mFloatHeight*2) || Y >= mScreenHeight) {
                if (0 >= Y - mFloatHeight*2) {
                    mFloatLayout.setY(mFloatHeight*2);
                } else {
                    mFloatLayout.setY(mScreenHeight - mFloatHeight*2);
                }
            } else {
                mFloatLayout.setY((int) Y - mFloatHeight);
            }
            if (X < mScreenWidth / 2) {
                if (this.mFloatResIdNor != mFloatResIdSleepL) {
                    this.mFloatResIdNor = mFloatResIdSleepL;
                    mFloatImage.setImageResource(mFloatResIdNor);
                }
            } else {
                if (this.mFloatResIdNor != mFloatResIdSleepR) {
                    this.mFloatResIdNor = mFloatResIdSleepR;
                    mFloatImage.setImageResource(mFloatResIdNor);
                }
            }
            mFloatLayout.postInvalidate();
            //            ((FrameLayout)view).updateViewLayout(mFloatLayout,mFloatImageLayoutParams);
            //            ViewGroup parent = (ViewGroup) mFloatLayout.getParent();
            //            if (parent!=null) {
            //                parent.removeView(mFloatLayout);
            //            }
            //            view.addView(mFloatLayout,mFloatImageLayoutParams);
            //            mWindowManager.updateViewLayout(mFloatLayout, mFloatImageLayoutParams);
        }
    }

    public void showFloat() {
        Log.e(TAG, "showFloat");
        mScreenWidth = SizeUtils.getScreenWidth(mContext);
        mScreenHeight = SizeUtils.getScreenHeight(mContext);
        if (mFloatLayout != null && isFloat) {
            mFloatLayout.setVisibility(View.VISIBLE);

            activeNormal();
            freshLocate();
            setLabelLocation();
        }
    }


    private void setLabelLocation() {

    }

    public void hideFloat() {
        Log.e(TAG, "hideFloat");
        if (mFloatLayout != null) {
            mFloatLayout.setVisibility(View.GONE);
        }
    }

    public void destroyFloat(Activity mContext) {
        removeAllMessage();
        if (mFloatLayout != null) {
            mFloatLayout.setVisibility(View.GONE);
        }
//        if (dismissActivity != null) {
//            dismissActivity.finish();
//            dismissActivity = null;
//        }
        if (mFloatLayout != null && isFloat) {
            view = null;
            //            mWindowManager.removeView(mFloatLayout);
            mFloatLayout = null;
            mFloatImage = null;
            mFloatLayout = null;
            isShowLabel = false;
            isFloat = false;
        }
        mActivityMap.remove(mContext.getClass().getName());
    }


    public void setStatusBarHeight(int top) {
        statusBarHeight = top;
    }

    public boolean isFloat() {
        return isFloat;
    }

    public void setFloatLocal(boolean floatLocal) {
        this.isFloatLocal = floatLocal;
    }
}
