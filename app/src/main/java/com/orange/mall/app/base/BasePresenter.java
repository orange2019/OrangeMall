package com.orange.mall.app.base;

public interface BasePresenter<T extends BaseView> extends PresenterLifeCycle {

  public void setView(T view);


}
