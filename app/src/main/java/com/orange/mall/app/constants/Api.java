package com.orange.mall.app.constants;

import org.jetbrains.annotations.NotNull;

// 环境变量
enum Env {
  TEST,
  PRODUCTION
};

public class Api {

  private static final Env env = Env.TEST; // 设置环境

  // API 域名
  private static final String API_DOMAIN = "http://api.kaximumck.com";
  private static final String TEST_API_DOMAIN = "http://apitest.kaximumck.com";


  public static final String UPDATE_PATH = "/updateDapp.json";

  // H5 域名
  private static final String H5_DOMAIN = "http://h5.kaximumck.com";
  private static final String TEST_H5_DOMAIN = "http://h5test.kaximumck.com";

  // 个人中心
  public static final String H5_USER_CENTER_PATH = "/user";

  // 细胞展厅
  public static final String H5_CELLS_SHOWROOM_PATH = "/show";

  // 商城
  public static final String H5_MALL_PATH = "/mall";

  // 新闻
  public static final String H5_NEWS = "/news?type=1";

  // h5域名
  public static final String h5Domain (@NotNull String path) {
    return (env.equals(Env.TEST) ? TEST_H5_DOMAIN : H5_DOMAIN).concat(path);
  }

  // api域名
  public static final String apiDomain (@NotNull String path) {
    return (env.equals(Env.TEST) ? TEST_API_DOMAIN : API_DOMAIN).concat(path);
  }


  

}
