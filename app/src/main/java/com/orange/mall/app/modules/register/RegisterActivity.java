package com.orange.mall.app.modules.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.orange.mall.app.R;
import com.orange.mall.app.beans.request.RegisterBean;
import com.orange.mall.app.beans.request.VerifyCodeBean;
import com.orange.mall.app.modules.login.LoginActivity;

import org.jetbrains.annotations.NotNull;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View{
  private static final String TAG = RegisterActivity.class.getSimpleName();
  private TextInputEditText mMobileTextInput = null;
  private TextInputEditText mPasswordTextInput = null;
  private TextInputEditText mInviteCodeInput = null;
  private TextInputEditText mVerifyCodeInput = null;
  private RegisterPresenter mPresenter = null;

  ProgressDialog mDialog = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    init();
  }

  private void init () {
    initView();

    registerEvents();
  }

  private void initView () {
    mMobileTextInput = findViewById(R.id.mobile_input);
    mPasswordTextInput = findViewById(R.id.password_input);
    mVerifyCodeInput = findViewById(R.id.verifycode_input);
    mInviteCodeInput = findViewById(R.id.invite_code_input);

    mDialog = new ProgressDialog(this);
    mDialog.setCancelable(false);
    mDialog.setMessage("注册中...");
  }

  private void registerEvents () {

  }

  /**
   *
   * @return
   */
  public RegisterBean getRegisterBean () {
    RegisterBean bean = new RegisterBean();

    String mobile = mMobileTextInput.getText().toString();
    String verifyCode = mVerifyCodeInput.getText().toString();
    String inviteCode = mInviteCodeInput.getText().toString();
    String password = mPasswordTextInput.getText().toString();

    bean.setMobile(mobile);
    bean.setVerifyCode(verifyCode);
    bean.setInviteCode(inviteCode);
    bean.setPassword(password);

    return bean;
  }

  private void gotoLoginActivity () {
    Intent intent = new Intent(this, LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  /**
   * 点击了注册按钮
   * @param view
   */
  public void onRegisterButtonClicked(View view) {
    mDialog.show();
    getPresenter().register(getRegisterBean());
  }


  /**
   * 点击了登录按钮, 跳转到登录页面
   * @param view
   */
  public void onLoginButtonClicked(View view) {
    gotoLoginActivity();
  }

  /**
   * 发送验证码
   * @param view
   */
  public void sendVerifyCode (View view) {
    String mobile = mMobileTextInput.getText().toString();
    if (mobile == null) {
      Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
    }
    VerifyCodeBean bean = new VerifyCodeBean();
    bean.setMobile(mobile);
    getPresenter().sendVerifyCode(bean);
  }

  @Override
  public void onRegisterSuccess() {
    mDialog.dismiss();
    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
    gotoLoginActivity();
  }

  @Override
  public void onRegisterFailed(@NotNull String error) {
    mDialog.dismiss();
    Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
  }

  @Override
  public RegisterContract.Presenter getPresenter() {
    if (mPresenter == null) {
      mPresenter = RegisterPresenter.newInstance(this);
    }

    return mPresenter;
  }
}
