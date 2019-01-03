package com.orange.mall.app.beans.response;

import com.google.gson.annotations.SerializedName;

public class RegisterResponseDataBean {
  @SerializedName("uuid")
  private String uuid = null;


  public String getUuid() {
    return uuid;
  }
}
