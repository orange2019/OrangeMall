package com.orange.mall.app;

import android.content.Context;

import com.orange.mall.app.constants.Api;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.Storage;

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

  /**
   * 应用启动的初始化环境
   */
  public static void initEnv () {
    // 初始化Hawk
    Hawk.init(sContext).build();

  }
}