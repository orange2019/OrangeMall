package com.orange.mall.app.beans.response;


import com.google.gson.annotations.SerializedName;


public class ResponseBean<T> {
  @SerializedName("code")
  private int code = -1;

  @SerializedName("message")
  private String message = null;

  @SerializedName("data")
  private T data = null;

  public ResponseBean () {

  }

  public int getCode () {
    return this.code;
  }

  public String getMessage () {
    return this.message;
  }

  public T getData () {
    return this.data;
  }

  @Override
  public String toString () {
    return String.format("CODE: %d; Message: %s; \n",
                  getCode(),
                  getMessage());
  }
}
