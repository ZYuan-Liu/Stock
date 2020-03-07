package com.example.stockrecommendations.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public static void finishAllOutOf(String activityName) {
        for (Activity activity : activities) {
            if (activityName.equalsIgnoreCase(activity.getClass().getSimpleName())) {
                continue;
            }
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public static void finishAllExcludeHome() {
        for (Activity activity : activities) {
            if ("HomeIndexActivity".equalsIgnoreCase(activity.getClass().getSimpleName())) {
                continue;
            }
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }


}
