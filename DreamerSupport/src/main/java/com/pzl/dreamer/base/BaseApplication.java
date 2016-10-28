package com.pzl.dreamer.base;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */
public class BaseApplication extends Application {

    private List<Activity> activities = new ArrayList();

    public void removeActivity(Activity a) {
        activities.remove(a);
    }

    public void addActivity(Activity a) {
        activities.add(a);
    }

    public void finishActivity() {
        for (Activity activity : this.activities)
            if (null != activity)
                activity.finish();
    }
}
