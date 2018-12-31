package com.orange.mall.app.models.common;

import android.util.Log;

import com.orange.mall.app.beans.request.BaseRequestBean;
import com.orange.mall.app.beans.request.VerifyCodeBean;
import com.orange.mall.app.beans.response.ResponseBean;
import com.orange.mall.app.constants.Api;
import com.orange.mall.app.manager.RequestManager;
import com.orange.mall.app.manager.RetrofitManager;
import com.orange.mall.app.utils.MiscUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;


public class CommonModel {
  private static final String TAG = CommonModel.class.getSimpleName();

  public interface CommonService {
    // 发送验证码
    @POST(Api.SEND_VERIFY_CODE_PATH)
    Call<ResponseBean<String>> sendVerifyCode(@Body BaseRequestBean<VerifyCodeBean> reqBody);

  }

  public static CommonService getService () {
    return RetrofitManager.getInstance().retrofit().create(CommonModel.CommonService.class);
  }

  /**
   * 发送验证码
   *
   * @param bean
   */
  public static void sendVerifyCode(VerifyCodeBean bean) {
    BaseRequestBean<VerifyCodeBean> reqBody = RequestManager.getInstance().buildRequestData(bean, VerifyCodeBean.class);
    Call<ResponseBean<String>> response = CommonModel.getService().sendVerifyCode(reqBody);

    Callback<ResponseBean<String>> responseBeanCallback = new Callback<ResponseBean<String>>() {
      @Override
      public void onResponse(Call<ResponseBean<String>> call, Response<ResponseBean<String>> response) {
        Log.i(TAG, response.body().toString());
        if (response.body().getCode() == 0) {
          MiscUtils.showMessageTip("发送验证码成功");
        } else {
          MiscUtils.showMessageTip("发送验证码失败,请重试");
        }
      }

      @Override
      public void onFailure(Call<ResponseBean<String>> call, Throwable t) {
        MiscUtils.showMessageTip("发送验证码失败,请重试");
      }
    };

    response.enqueue(responseBeanCallback);
  }
}