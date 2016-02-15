package com.way.tunnelvision.util;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/1/6.
 */
public class ActivityCollector {
    private final static String TAG = ActivityCollector.class.getName();

    private ActivityCollector() {
        throw new AssertionError();
    }


    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        Log.d(TAG, "addActivity debug, start");
        try {
            activities.add(activity);
        } catch (Exception e) {
            Log.e(TAG, "addActivity error", e);
        }
        Log.d(TAG, "addActivity debug, end");
    }

    public static void removeActivity(Activity activity) {
        Log.d(TAG, "removeActivity debug, start");
        try {
            activities.remove(activity);
        } catch (Exception e) {
            Log.e(TAG, "removeActivity error", e);
        }
        Log.d(TAG, "removeActivity debug, end");
    }

    public static void finishAll() {
        Log.d(TAG, "finishAll debug, start");
        try {
            for (Activity activity : activities) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "finishAll error", e);
        }
        Log.d(TAG, "finishAll debug, end");
    }
}

