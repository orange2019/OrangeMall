package com.orange.mall.app.modules.login;

import android.os.Bundle;
import android.util.Log;

import com.orange.mall.app.beans.request.BaseRequestBean;
import com.orange.mall.app.beans.request.LoginBean;
import com.orange.mall.app.beans.response.LoginResponseDataBean;
import com.orange.mall.app.beans.response.ResponseBean;
import com.orange.mall.app.manager.RequestManager;
import com.orange.mall.app.models.AccountModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    mView.onShowLoginState("logining");

    LoginBean bean = new LoginBean();
    bean.setMobile(mobile);
    bean.setPassword(passwd);

    BaseRequestBean<LoginBean> reqBody = RequestManager.getInstance().buildRequestData(bean, LoginBean.class);
    Call<ResponseBean<LoginResponseDataBean>> response = AccountModel.getService().login(reqBody);

    response.enqueue(new Callback<ResponseBean<LoginResponseDataBean>>() {

      @Override
      public void onResponse(Call<ResponseBean<LoginResponseDataBean>> call,
                             Response<ResponseBean<LoginResponseDataBean>> response) {
        Log.i(TAG, "Login onRespone: " + response.body().toString());
        if (response.body().getCode() == 0) {
          mView.onShowLoginState("successed");
        } else {
          mView.onShowLoginState("failed");
        }
      }

      @Override
      public void onFailure(Call<ResponseBean<LoginResponseDataBean>> call, Throwable t) {
        Log.e(TAG, "Login Error: " + call.toString());
        mView.onShowLoginState("failed");
      }
    });

  }

  
}
