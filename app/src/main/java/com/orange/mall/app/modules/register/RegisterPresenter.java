package com.orange.mall.app.modules.register;

import android.os.Bundle;
import android.util.Log;

import com.orange.mall.app.beans.request.BaseRequestBean;
import com.orange.mall.app.beans.request.RegisterBean;
import com.orange.mall.app.beans.request.VerifyCodeBean;
import com.orange.mall.app.beans.response.ResponseBean;
import com.orange.mall.app.manager.RequestManager;
import com.orange.mall.app.models.AccountModel;
import com.orange.mall.app.models.common.CommonModel;
import com.orange.mall.app.utils.MiscUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter implements RegisterContract.Presenter {
  private static final String TAG = RegisterPresenter.class.getSimpleName();

  RegisterContract.View mView = null;
  private RegisterPresenter (RegisterContract.View view) {
    mView = view;
  }


  public static RegisterPresenter newInstance (RegisterContract.View view) {
    return new RegisterPresenter(view);
  }

  @Override
  public void register(RegisterBean bean) {
    BaseRequestBean<RegisterBean> reqBean = RequestManager.getInstance().buildRequestData(bean, RegisterBean.class);

    final Call<ResponseBean<String>> call = AccountModel.getService().register(reqBean);
    Callback<ResponseBean<String>> callback = new Callback<ResponseBean<String>>() {
      @Override
      public void onResponse(Call<ResponseBean<String>> call, Response<ResponseBean<String>> response) {
        Log.i(TAG, response.body().toString());
        if (response.body().getCode() == 0) {
          mView.onRegisterSuccess();
        } else {
          mView.onRegisterFailed(response.body().getMessage());
        }
      }

      @Override
      public void onFailure(Call<ResponseBean<String>> call, Throwable t) {
        mView.onRegisterFailed("注册失败,请重试");
      }
    };
    call.enqueue(callback);
  }

  @Override
  public void sendVerifyCode(VerifyCodeBean bean) {
    CommonModel.sendVerifyCode(bean);
  }

  @Override
  public void setView(RegisterContract.View view) {
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
}
