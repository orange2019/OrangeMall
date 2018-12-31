package com.orange.mall.app.beans.response;

import com.google.gson.annotations.SerializedName;

public class VerifyCodeResponseDataBean {
  @SerializedName("code")
  private String code = null;


  public String getCode() {
    return code;
  }
}
