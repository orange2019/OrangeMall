package com.orange.mall.app.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.util.ArrayMap;

import com.orange.mall.app.Application;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ActivityUtils {


  private ActivityUtils() {
    throw new UnsupportedOperationException("u can't instantiate me...");
  }

  /**
   * 在新的task中打开activity
   */
  public static void openActivityWithNewTask(Context context, Class<Activity> activityClass) {
    Intent intent = new Intent(context, activityClass);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    Bundle bundle = new Bundle();
    intent.putExtras(bundle);
    context.startActivity(intent);
  }


  public static boolean isActivityExisted(Context context, String className) {
    String pkgName = context.getPackageName();
    Intent intent = new Intent();
    intent.setClassName(pkgName, className);
    return !(context.getPackageManager().resolveActivity(intent, 0) == null ||
      intent.resolveActivity(context.getPackageManager()) == null ||
      context.getPackageManager().queryIntentActivities(intent, 0).size() == 0);
  }

  /**
   * 启动activity
   */
  public static void launchActivityWithNewTask(Context appContext, String className, Bundle bundle) {
    String packageName = appContext.getPackageName();
    Intent intent = getComponentIntent(packageName, className, bundle);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    appContext.startActivity(intent);
  }

  /**
   * 打开Activity
   *
   * @param packageName 包名
   * @param className   全类名
   */
  public static void launchActivity(String packageName, String className) {
    launchActivity(packageName, className, null);
  }

  /**
   * 打开Activity
   *
   * @param packageName 包名
   * @param className   全类名
   * @param bundle      bundle
   */
  public static void launchActivity(String packageName, String className, Bundle bundle) {
    Application.getAppContext().startActivity(getComponentIntent(packageName, className, bundle));
  }

  /**
   * 获取launcher activity
   *
   * @param packageName 包名
   * @return launcher activity
   */
  public static String getLauncherActivity(String packageName) {
    Intent intent = new Intent(Intent.ACTION_MAIN, null);
    intent.addCategory(Intent.CATEGORY_LAUNCHER);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    PackageManager pm = Application.getAppContext().getPackageManager();
    List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
    for (ResolveInfo info : infos) {
      if (info.activityInfo.packageName.equals(packageName)) {
        return info.activityInfo.name;
      }
    }
    return null;
  }


  /*
   * 获取栈顶Activity
   *
   * @return 栈顶Activity
   */
  public static Activity getTopActivity() {
    try {
      Class activityThreadClass = Class.forName("android.app.ActivityThread");
      Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
      Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
      activitiesField.setAccessible(true);
      Map activities = null;
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
        activities = (HashMap) activitiesField.get(activityThread);
      } else {
        activities = (ArrayMap) activitiesField.get(activityThread);
      }
      for (Object activityRecord : activities.values()) {
        Class activityRecordClass = activityRecord.getClass();
        Field pausedField = activityRecordClass.getDeclaredField("paused");
        pausedField.setAccessible(true);
        if (!pausedField.getBoolean(activityRecord)) {
          Field activityField = activityRecordClass.getDeclaredField("activity");
          activityField.setAccessible(true);
          return (Activity) activityField.get(activityRecord);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


  public static Intent getComponentIntent(String packageName, String className, Bundle bundle) {
    Intent intent = new Intent();

    ComponentName cn = new ComponentName(packageName, className);

    intent.setComponent(cn);

    if (bundle == null) {
      return intent;
    }

    intent.putExtras(bundle);

    return intent;
  }

  public static void openBrowser(String downloadUrl, Context context) {
    Uri uri = Uri.parse(downloadUrl);
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    context.startActivity(intent);
  }

  /**
   * 获取权限
   *
   * @param activity
   */
  public static void requestPermissions(Activity activity) {
    List<String> permissions = new ArrayList<>();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
      }

      if (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
      }

      if (activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
        permissions.add(Manifest.permission.CAMERA);
      }

    } else { // 不需要申请运行时权限

    }

    if (!permissions.isEmpty()) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        activity.requestPermissions(permissions.toArray(new String[permissions.size()]), 1);
      }
    }
  }
}
