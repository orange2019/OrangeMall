package com.orange.mall.app.beans.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;


public class BaseRequestBean<T> {

  @SerializedName("uuid")
  private String mUUID = "";

  @SerializedName("content")
  private T mContent;

  @SerializedName("sign")
  private String mSign;

  public BaseRequestBean(String uuid) {
    this.mUUID = uuid;
  }

  public void setContent(T content) {
    this.mContent = content;
  }

  public void setSign (String sign) {
    mSign = sign;
  }


  // 转换成json串
  public String toJson() {
    GsonBuilder sb = new GsonBuilder();
    Gson gson = sb.create();
    return gson.toJson(this, BaseRequestBean.class);
  }
}
