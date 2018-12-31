package com.orange.mall.app.jsbridge;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.orange.mall.app.modules.login.LoginActivity;

public class JsInterfaceForAndroid {

  private Context context = null;
  private volatile static JsInterfaceForAndroid instance = null;

  private JsInterfaceForAndroid () {

  }

  public static JsInterfaceForAndroid getInstance () {
    if (instance == null) {
      synchronized (JsInterfaceForAndroid.class) {
        if (instance == null) {
          instance = new JsInterfaceForAndroid();
        }
      }
    }
    return instance;
  }

  public JsInterfaceForAndroid init (Context ctx) {
    if (context == null) {
      context = ctx;
    }

    return this;
  }

  @JavascriptInterface
  public void gotoLogin () {
    Intent intent = new Intent (context, LoginActivity.class);
    context.startActivity(intent);
  }

}
