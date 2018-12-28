package com.orange.mall.app.beans;


import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class ResponseBean {
  @SerializedName("code")
  private int code = -1;

  @SerializedName("message")
  private String message = null;

  @SerializedName("data")
  private String data = null;

  public ResponseBean () {

  }

  public int getCode () {
    return this.code;
  }

  public String getMessage () {
    return this.message;
  }

  public String getData () {
    return this.data;
  }

  /**
   * 获取json数据
   * @return
   */
  public JSONObject getJSONData () {
    try {
      return new JSONObject(getData());
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }

}
