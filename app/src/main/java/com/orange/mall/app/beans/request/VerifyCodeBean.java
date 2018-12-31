package com.orange.mall.app.beans.request;

import com.google.gson.annotations.SerializedName;

public class VerifyCodeBean {
  @SerializedName("mobile")
  private String mobile = null;


  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
}
