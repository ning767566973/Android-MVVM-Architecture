package com.cuining.mvvm.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;

import com.cuining.mvvm.MainActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO 添加login  main
 */
public class AppManager {
    private static AppManager mInstance;
    private static List<WeakReference<Activity>> mActivityStack;
    private Context context;
    private Object mLock = new Object();

    private AppManager(Context context) {
        this.context = context;
    }

    public static synchronized AppManager getInstance(Context context) {//单例
        if (mInstance == null) {
            mInstance = new AppManager(context.getApplicationContext());

        }
        return mInstance;
    }


    private int appStatus = -1; //APP状态初始值为没启动不在前台状态


    public int getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(int appStatus) {
        this.appStatus = appStatus;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new ArrayList<>();
        }
        mActivityStack.add(new WeakReference<>(activity));
    }

    /**
     * 获取栈顶Activity（堆栈中最后一个压入的）
     */
    public Activity getTopActivity() {
        Activity activity;
        if (mActivityStack != null && mActivityStack.size() > 0) {
            WeakReference<Activity> ref = mActivityStack.get(mActivityStack.size() - 1);
            if (ref.get() != null) {
                activity = ref.get();
            } else {
                activity = createDefaultActivity();
            }
        } else {
            activity = createDefaultActivity();
        }
        return activity;
    }

    /**
     * 默认activity
     *
     * @return
     */
    public Activity createDefaultActivity() {
        return new MainActivity();
    }

    /**
     * 获取栈顶-1Activity（堆栈中最后一个压入的）
     */
    public Activity getTopSecondActivity() {
        Activity activity = null;
        if (mActivityStack != null && mActivityStack.size() > 0) {
            if (mActivityStack.size() - 2 >= 0) {
                WeakReference<Activity> ref = mActivityStack.get(mActivityStack.size() - 2);
                if (ref.get() != null) {
                    activity = ref.get();
                } else {
                    activity = createDefaultActivity();
                }
            }
        } else {
            activity = createDefaultActivity();
        }
        return activity;
    }

    /**
     * 结束栈顶Activity（堆栈中最后一个压入的）
     */
    public void killTopActivity() {
        Activity activity = getTopActivity();
        killActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public synchronized void killActivity(Activity activity) {
        if (activity != null && mActivityStack != null) {
            for (WeakReference ref : mActivityStack) {
                if (ref.get() == activity) {
                    mActivityStack.remove(ref);
                    break;
                }
            }
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void killActivity(Class<?> cls) {
        synchronized (mLock) {
            for (WeakReference<Activity> ref : mActivityStack) {
                if (ref.get() != null && ref.get().getClass().equals(cls)) {
                    killActivity(ref.get());
                }
            }
        }
    }

    public boolean hasMantivity() {
        for (WeakReference<Activity> ref : mActivityStack) {
            if (ref.get() != null && ref.get().getClass().equals(MainActivity.class)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 结束所有Activity
     */
    public void killAllActivity() {
        synchronized (mLock) {
            for (int i = 0, size = mActivityStack.size(); i < size; i++) {
                if (mActivityStack.get(i) != null && mActivityStack.get(i).get() != null) {
                    mActivityStack.get(i).get().finish();
                }
            }
            mActivityStack.clear();
        }
    }

    /**
     * 退出应用程序
     */
    @SuppressWarnings("deprecation")
    public void AppExit(Context context) {
        try {
            killAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }

    /**
     * 除了主页都关闭
     **/
    public void finishActivityExceptMain() {
        List<WeakReference<Activity>> activityList = new ArrayList<>();
        for (int i = 0; i < mActivityStack.size(); i++) {
            WeakReference<Activity> ref = mActivityStack.get(i);
            if (ref.get() != null && !ref.get().getClass().equals(MainActivity.class)) {
                activityList.add(ref);
                ref.get().finish();
            }
        }
        mActivityStack.removeAll(activityList);
    }

    /**
     * 除了登录页都关闭
     **/
    public void finishActivityExceptLogin() {
//        List<WeakReference<Activity>> activityList = new ArrayList<>();
//        for (int i = 0; i < mActivityStack.size(); i++) {
//            WeakReference<Activity> ref = mActivityStack.get(i);
//            if (ref.get() != null && !ref.get().getClass().equals(LoginActivity.class)) {
//                activityList.add(ref);
//                ref.get().finish();
//            }
//        }
//        mActivityStack.removeAll(activityList);
    }

    /**
     * 除了设置页都关闭
     **/
    public void finishActivitySettingActivity() {
//
//        List<WeakReference<Activity>> activityList = new ArrayList<>();
//        for (int i = 0; i < mActivityStack.size(); i++) {
//            WeakReference<Activity> ref = mActivityStack.get(i);
//            if (ref.get() != null && !ref.get().getClass().equals(SettingActivity.class)) {
//                activityList.add(ref);
//                ref.get().finish();
//            }
//        }
//        mActivityStack.removeAll(activityList);
    }

    /**
     * mainactivity是否存在
     **/
    public boolean isMainActivityIn() {
        if (mActivityStack != null && mActivityStack.size() > 0) {
            for (WeakReference<Activity> ref : mActivityStack) {
                if (ref != null && ref.get() != null && ref.get().getClass().equals(MainActivity.class)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }

    }

    /**
     * 顶栈是否是mainactivity
     **/
    public boolean isMainActivity() {
        if (getTopActivity().equals(MainActivity.class)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否在前台运行
     **/
    public boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if (!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName())) {
            return true;
        }
        return false;
    }


    /**
     * 跳转到miui的权限管理页面
     */
    public static void gotoMiuiPermission(Context context) {
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        i.setComponent(componentName);
        i.putExtra("extra_pkgname", context.getPackageName());
        try {
            context.startActivity(i);
        } catch (Exception e) {
            gotoAppDetailIntent(context);
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    public static void gotoMeizuPermission(Context context) {
//        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
//        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
//        try {
//            context.startActivity(intent);
//        } catch (Exception e) {
//            gotoAppDetailIntent(context);
//        }
    }

    /**
     * 华为的权限管理页面
     */
    public static void gotoHuaweiPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            gotoAppDetailIntent(context);
        }

    }

    /**
     * 跳转到应用详情界面
     */
    public static void gotoAppDetailIntent(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    /**
     *  打开系统设置界面
     * @param context
     */
    public static void  goSetting(Context context){

        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        context.startActivity(intent);
    }
}
