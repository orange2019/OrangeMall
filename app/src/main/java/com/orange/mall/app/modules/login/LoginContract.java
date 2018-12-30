package com.orange.mall.app.modules.login;

import com.orange.mall.app.base.BasePresenter;
import com.orange.mall.app.base.BaseView;

public interface LoginContract {

  interface View extends BaseView<Presenter> {
    void onShowLoginState (String state);
  }

  interface Presenter extends BasePresenter<View> {

  }
}
