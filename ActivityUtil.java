package com.smz.lexunuser.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 控制activity
 *
 * @author ldx
 */
public class ActivityUtil {
    static ActivityUtil activityUtil;

    public static List<Activity> activities = new ArrayList<>();

    public static void exit() {
        if (!activities.isEmpty()) {
            for (Activity activity : activities) {
                activity.finish();
            }
            activities.clear();
        }
        System.exit(0);
    }

    /**
     * 单例
     *
     * @return
     */
    public synchronized ActivityUtil newInstance() {
        if (activityUtil == null) {
            synchronized (ActivityUtil.class) {
                activityUtil = new ActivityUtil();
            }

        }
        return activityUtil;
    }

    /**
     * 添加activity
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 移除制定的activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        if (!activities.isEmpty() && activities.contains(activity)) {
            activities.remove(activity);
        }
    }

    /**
     * 移除所有的activity
     */
    public static void removeAll() {
        if (!activities.isEmpty()) {
            for (Activity activity : activities) {
                activity.finish();
            }
            activities.clear();
        }
    }

    /**
     * 获取当前activity的名称
     *
     * @return
     */
    public static String getActivity() {
        String a = "";
        if (!activities.isEmpty()) {
            a = activities.getClass().getSimpleName();
        }
        return a;
    }


}
