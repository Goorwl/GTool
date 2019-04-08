package com.goorwl.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * FileName: AppManager
 * Author: Goorwl
 * Create Date: 2019/4/2 14:55
 * Github: https://github.com/Goorwl
 * Blog: https://xiaozhuanlan.com/goorwl
 */

public class AppManager {

    private static AppManager         mInstance    = null;
    private        List<CoreActivity> activityList = Collections.synchronizedList(new ArrayList<CoreActivity>());

    private AppManager() {
    }

    public static synchronized AppManager getAppManager() {
        if (mInstance == null) {
            mInstance = new AppManager();
        }
        return mInstance;
    }

    /**
     * 添加一个ACT
     *
     * @param activity
     */
    void addActivity(CoreActivity activity) {
        if (activity == null) {
            LogUtils.d("AppManager", "Failed to add BaseActivity");
            return;
        }
        activityList.add(activity);
    }

    /**
     * 移除一个ACT
     *
     * @param activity
     */
    void removeActivity(CoreActivity activity) {
        if (activityList == null || activity == null) {
            LogUtils.d("AppManager", "Failed to remove BaseActivity");
            return;
        }
        activityList.remove(activity);
    }

    /**
     * 获取当前页面的上一个页面
     *
     * @param act 当前页面
     * @return {@link CoreActivity}
     */
    public CoreActivity getPreActivity(CoreActivity act) {
        int index = activityList.indexOf(act);
        if (-1 == index || index < 1)
            return null;
        return activityList.get(index - 1);
    }

    /**
     * 返回到应用程序首页
     */
    public void backToHome() {
        backTo(1);
    }

    /**
     * 返回指定的步骤
     *
     * @param step 指定的步骤
     */
    private void backTo(int step) {
        if (activityList == null) {
            LogUtils.d("AppManager", String.format("Failed to backTo(%s)", step));
            return;
        }
        synchronized (AppManager.class) {
            if (step < 0 || step > activityList.size()) {
                return;
            }

            List<CoreActivity> sub = new ArrayList<>(activityList.subList(step, activityList.size()));
            while (!sub.isEmpty()) {
                CoreActivity a = sub.remove(0);
                activityList.remove(a);
                if (a != null && !a.isFinishing()) {
                    a.finish();
                }
            }
        }
    }

    /**
     * 获得最上层的ACT
     *
     * @return {@link CoreActivity}
     */
    public CoreActivity getTopActivity() {
        if (null == activityList || 0 == activityList.size()) {
            return null;
        }
        CoreActivity topAct = activityList.get(activityList.size() - 1);
        if (topAct.isFinishing() || topAct.isDestroyed()) {
            removeActivity(topAct);
            return getTopActivity();
        }
        return topAct;
    }

    /**
     * 返回到标记页面之前的页面，标记页面也关闭
     *
     * @param tag 页面标记
     */
    public void backToTagFront(Object tag) {
        if (activityList == null) {
            LogUtils.d("AppManager", "Failed to backToTagFront()");
            return;
        }
        int step = 0;
        synchronized (AppManager.class) {
            for (CoreActivity a : activityList) {
                step++;
                if (tag != null && tag.equals(a.getTag())) {
                    break;
                }
            }
        }
        if (step > 0) {
            step -= 1;
        }
        backTo(step);
    }

    /**
     * 返回到标记页面，标记页面不关闭
     *
     * @param tag 页面标记
     */
    public void backToTag(Object tag) {
        backToTag(tag, null);
    }

    /**
     * 返回到标记页面，标记页面不关闭
     *
     * @param tag 页面标记
     */
    void backToTag(Object tag, Object bundle) {
        if (activityList == null) {
            LogUtils.d("AppManager", "Failed to backToTag()");
            return;
        }
        int step = 0;
        synchronized (AppManager.class) {
            for (CoreActivity a : activityList) {
                step++;
                if (tag != null && tag.equals(a.getTag())) {
                    break;
                }
            }
        }
        backTo(step, bundle);
    }

    private void backTo(int step, Object bundle) {
        backTo(step);
        activityList.get(activityList.size() - 1).onResult(bundle);
    }

    /**
     * 清除所有ACT
     */
    public void clearAllActivities() {
        activityList.clear();
    }
}
