package com.orange.mall.app.base;

import android.os.Bundle;

public interface PresenterLifeCycle {
  void onCreate(Bundle savedInstanceState);

  void onStart();

  void onRestart();

  void onResume();

  void onPause();

  void onStop();

  void onDestroy();

}
