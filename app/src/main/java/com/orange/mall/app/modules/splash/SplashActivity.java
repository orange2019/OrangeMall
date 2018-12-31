package com.orange.mall.app.modules.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.orange.mall.app.Application;
import com.orange.mall.app.R;
import com.orange.mall.app.constants.Storage;
import com.orange.mall.app.modules.main.MainActivity;
import com.orange.mall.app.modules.login.LoginActivity;
import com.orhanobut.hawk.Hawk;

public class SplashActivity extends AppCompatActivity {

  private static final String TAG = SplashActivity.class.getSimpleName();

  private final int SPLASH_TIME = 1000; // 1 second

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    Thread timer = new Thread() {
      public void run() {
        Log.i(TAG, "start thread");

        try {
          sleep(SPLASH_TIME);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          Log.i(TAG, "start init environment");
          init();
        }


      }
    };

    timer.start();
  }

  /**
   * 初始化
   */
  private void init () {
    Application.initEnv();

    // 如果是第一次进来就打开登录界面，否则就直接进入MainActivity
    if (Hawk.get(Storage.KEY_FIRST_TIME, true)) { // 第一次进来
      Intent intent = new Intent(this, LoginActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
      Hawk.put(Storage.KEY_FIRST_TIME, false);
    } else {
      Intent intent = new Intent(this, MainActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
    }

    //

    //

    //
  }





}
