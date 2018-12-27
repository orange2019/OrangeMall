package com.orange.mall.app;

import android.content.Context;

public class Application extends android.app.Application {
  private static final String TAG = Application.class.getSimpleName();
  private static Context sContext = null;

  public Application () {

  }

  @Override
  public void onCreate() {
    super.onCreate();
    if (sContext == null) {
      sContext = getApplicationContext();
    }
  }

  /**
   * 返回
   * @return
   */
  public static Context getAppContext () {
    return sContext;
  }
}