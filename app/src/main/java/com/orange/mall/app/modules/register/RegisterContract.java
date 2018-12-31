package com.orange.mall.app.modules.register;

import com.orange.mall.app.base.BasePresenter;
import com.orange.mall.app.base.BaseView;
import com.orange.mall.app.beans.request.RegisterBean;
import com.orange.mall.app.beans.request.VerifyCodeBean;

public interface RegisterContract {

  interface View extends BaseView<Presenter> {
    void onRegisterSuccess ();

    void onRegisterFailed (String error);
  }


  interface Presenter extends BasePresenter<View> {
    void register (RegisterBean bean);

    void sendVerifyCode(VerifyCodeBean bean);
  }
}
