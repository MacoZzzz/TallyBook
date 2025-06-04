package com.example.tallybook.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {    //创建活动收集类，以定义所有Activity关闭的方法
    public static List<Activity> activities = new ArrayList<Activity>();
 
    public static void addActivity(Activity activity) {    //将Activity添加到集合
        activities.add(activity);
    }
 
    public static void removeActivity(Activity activity) {    //将Activity从集合移除
        activities.remove(activity);
    }
 
    public static void finishAll() {    //关闭集合中所有Activity
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}