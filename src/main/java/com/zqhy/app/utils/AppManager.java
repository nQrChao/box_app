package com.zqhy.app.utils;

import android.app.Activity;

import com.chaoji.other.blankj.utilcode.util.ActivityUtils;

import java.util.Iterator;
import java.util.Stack;

/**
 * @author Administrator
 * @date 2018/11/11
 */

public class AppManager {
    private static Stack<Activity> mActivityStack;
    private static AppManager      mAppManager;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getInstance() {
        if (mAppManager == null) {
            mAppManager = new AppManager();
        }
        return mAppManager;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 获取栈顶Activity（堆栈中最后一个压入的）
     */
    public Activity getTopActivity() {
        return mActivityStack.lastElement();
    }


    /**
     * 获取次栈顶Activity(栈顶Activity的前一个)
     *
     * @return
     */
    public Activity getSubTopActivity() {
        int len = mActivityStack.size();
        if (len < 2) {
            return null;
        }
        return mActivityStack.elementAt(len - 2);
    }


    /**
     * @param activity
     * @return
     */
    public Stack<Activity> getActivityListExcept(Activity activity) {
        Stack<Activity> activities = new Stack<>();
        if (mActivityStack != null) {
            for (int i = 0, size = mActivityStack.size(); i < size; i++) {
                if (activity == mActivityStack.get(i)) {
                    continue;
                }
                activities.add(mActivityStack.get(i));
            }
        }

        return activities;
    }

    /**
     * 结束栈顶Activity（堆栈中最后一个压入的）
     */
    public void finishTopActivity() {
        if (mActivityStack == null) {
            return;
        }
        Activity activity = mActivityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (mActivityStack == null) {
            return;
        }
        try {
            if (activity != null) {
                mActivityStack.remove(activity);
                activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (mActivityStack == null) {
            return;
        }
        Iterator iterator = mActivityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = (Activity) iterator.next();
            if (activity.getClass().equals(cls)) {
                iterator.remove();
                activity.finish();
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (mActivityStack == null) {
            return;
        }
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }

    /**
     * 退出应用程序
     */
    @SuppressWarnings("deprecation")
    public void AppExit() {
        try {
            ActivityUtils.finishAllActivities();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
