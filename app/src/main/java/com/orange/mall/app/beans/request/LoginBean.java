package com.orange.mall.app.beans.request;

import com.google.gson.annotations.SerializedName;

public class LoginBean {

  @SerializedName("mobile")
  private String mobile = null;

  @SerializedName("password")
  private String password = null;

  public LoginBean() {
    mobile = "";
    password = "";
  }


  public void setMobile(String mobile) {
    this.mobile = mobile;
  }


  public void setPassword(String password) {
    this.password = password;
  }
}
