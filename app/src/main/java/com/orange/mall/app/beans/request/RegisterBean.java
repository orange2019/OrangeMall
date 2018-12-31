package com.orange.mall.app.beans.request;

import com.google.gson.annotations.SerializedName;

public class RegisterBean {
  @SerializedName("mobile")
  private  String mobile = null;

  @SerializedName("password")
  private String password = null;

  @SerializedName("verify_code")
  private String verifyCode = null;

  @SerializedName("invite_code")
  private String inviteCode = null;

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
  }

  public void setInviteCode(String inviteCode) {
    this.inviteCode = inviteCode;
  }
}
