package com.orange.mall.app.beans.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponseDataBean {

  @SerializedName("token")
  private String token = null;

  public String getToken() {
    return token;
  }

}
