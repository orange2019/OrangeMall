package com.orange.mall.app.manager;

import com.orange.mall.app.constants.Api;

import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
  private static final String TAG = RetrofitManager.class.getSimpleName();
  private volatile static RetrofitManager instance = null;
  private Retrofit retrofit = null;
  private RetrofitManager () {
    init();
  }

  /**
   * 获取实例
   * @return RetrofitManager
   */
  public static RetrofitManager getInstance () {
    if (instance == null) {
      synchronized (RetrofitManager.class) {
        if (instance == null) {
          instance = new RetrofitManager();
        }
      }
    }
    return instance;
  }

  /**
   * 初始化
   */
  private void init () {
    if (retrofit == null) {
      retrofit = new Retrofit.Builder()
        .baseUrl(Api.apiDomain(""))
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    }
  }

  /**
   * 返回构造的Retrofit对象
   * @return
   */
  public Retrofit retrofit () {
    return retrofit;
  }
}



