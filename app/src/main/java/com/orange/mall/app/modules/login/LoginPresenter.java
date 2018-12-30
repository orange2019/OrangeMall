package com.orange.mall.app.modules.login;

import android.os.Bundle;
import android.util.Log;

public class LoginPresenter implements LoginContract.Presenter {
  private static final String TAG = LoginPresenter.class.getSimpleName();

  private LoginContract.View mView = null;

  public LoginPresenter (LoginContract.View view) {
    this.mView = view;
  }

  public static LoginPresenter newInstance (LoginContract.View view) {
    return new LoginPresenter(view);
  }

  @Override
  public void setView(LoginContract.View view) {
    mView = view;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {

  }

  @Override
  public void onStart() {

  }

  @Override
  public void onRestart() {

  }

  @Override
  public void onResume() {

  }

  @Override
  public void onPause() {

  }

  @Override
  public void onStop() {

  }

  @Override
  public void onDestroy() {

  }


  // 登录接口
  void login (String mobile, String passwd) {
    Log.i(TAG, String.format("Login With Mobile %s : Passwd %s", mobile, passwd));
  }

  // 注册接口


  // 修改密码

  
}
