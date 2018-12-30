package com.orange.mall.app.base;

public interface BaseView<T extends BasePresenter> {
  T getPresenter ();
}
