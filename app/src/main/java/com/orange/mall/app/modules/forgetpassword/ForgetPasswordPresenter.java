package com.orange.mall.app.modules.forgetpassword;

import android.os.Bundle;
import android.util.Log;

import com.orange.mall.app.beans.request.BaseRequestBean;
import com.orange.mall.app.beans.request.ForgetPasswordBean;
import com.orange.mall.app.beans.request.VerifyCodeBean;
import com.orange.mall.app.beans.response.ResponseBean;
import com.orange.mall.app.manager.RequestManager;
import com.orange.mall.app.models.AccountModel;
import com.orange.mall.app.models.common.CommonModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordPresenter implements ForgetPasswordContract.Presenter {
  private static final String TAG = ForgetPasswordPresenter.class.getSimpleName();

  private ForgetPasswordContract.View mView = null;

  private ForgetPasswordPresenter (ForgetPasswordContract.View view) {
    mView = view;
  }

  public static ForgetPasswordPresenter newInstance (ForgetPasswordContract.View view) {
    return new ForgetPasswordPresenter(view);
  }

  @Override
  public void sendVerifyCode(VerifyCodeBean bean) {
    CommonModel.sendVerifyCode(bean);
  }

  @Override
  public void changePassword(ForgetPasswordBean bean) {
    BaseRequestBean<ForgetPasswordBean> reqBean = RequestManager.getInstance().buildRequestData(bean, ForgetPasswordBean.class);
    Call<ResponseBean<String>> call = AccountModel.getService().forgetPassword(reqBean);

    call.enqueue(new Callback<ResponseBean<String>>() {

      @Override
      public void onResponse(Call<ResponseBean<String>> call, Response<ResponseBean<String>> response) {
        Log.i(TAG, response.body().toString());
        if (response.body().getCode() == 0) {
          mView.onPasswordChangeSuccessed();
        } else {
          mView.onPasswordChangeFailed(response.body().getMessage());
        }
      }

      @Override
      public void onFailure(Call<ResponseBean<String>> call, Throwable t) {
        mView.onPasswordChangeFailed("修改密码失败");
      }
    });
  }

  @Override
  public void setView(ForgetPasswordContract.View view) {

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
