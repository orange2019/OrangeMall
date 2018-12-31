package com.orange.mall.app.modules.forgetpassword;

import com.orange.mall.app.base.BasePresenter;
import com.orange.mall.app.base.BaseView;
import com.orange.mall.app.beans.request.ForgetPasswordBean;
import com.orange.mall.app.beans.request.VerifyCodeBean;

public class ForgetPasswordContract {
  public interface View extends BaseView<Presenter> {
    void onPasswordChangeSuccessed ();

    void onPasswordChangeFailed (String message);
  }

  public interface Presenter extends BasePresenter<View> {
    void sendVerifyCode (VerifyCodeBean bean);

    void changePassword (ForgetPasswordBean bean);
  }
}
