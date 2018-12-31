package com.orange.mall.app.models;

import com.orange.mall.app.beans.request.ForgetPasswordBean;
import com.orange.mall.app.beans.request.LoginBean;
import com.orange.mall.app.beans.request.BaseRequestBean;
import com.orange.mall.app.beans.request.RegisterBean;
import com.orange.mall.app.beans.request.VerifyCodeBean;
import com.orange.mall.app.beans.response.LoginResponseDataBean;
import com.orange.mall.app.beans.response.ResponseBean;
import com.orange.mall.app.constants.Api;
import com.orange.mall.app.constants.Storage;
import com.orange.mall.app.manager.RetrofitManager;
import com.orhanobut.hawk.Hawk;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class AccountModel {

  public interface AccountService {
    // 登录
    @POST(Api.LOGIN_PATH)
    Call<ResponseBean<LoginResponseDataBean>> login(@Body BaseRequestBean<LoginBean> reqBody);

    // 发送验证码
    @POST(Api.SEND_VERIFY_CODE_PATH)
    Call<ResponseBean<String>> sendVerifyCode(@Body BaseRequestBean<VerifyCodeBean> reqBody);

    // 注册
    @POST(Api.REGISTER_PATH)
    Call<ResponseBean<String>> register(@Body BaseRequestBean<RegisterBean> reqBody);

    // 忘记密码
    @POST(Api.FORGET_PASSWD_PATH)
    Call<ResponseBean<String>> forgetPassword (@Body BaseRequestBean<ForgetPasswordBean> reqBody);
  }

  /**
   * 创建请求服务
   * @return
   */
  public static AccountService getService () {
    return RetrofitManager.getInstance().retrofit().create(AccountService.class);
  }


  /**
   * 存token
   */
  public static void saveToken (@NotNull String token) {
    Hawk.put(Storage.KEY_TOKEN, token);
  }

  /**
   * 获取token
   * @return
   */
  public static String getToken () {
    return Hawk.get(Storage.KEY_TOKEN, "");
  }
}
