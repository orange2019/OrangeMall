package com.orange.mall.app.modules.forgetpassword;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.orange.mall.app.R;
import com.orange.mall.app.beans.request.ForgetPasswordBean;
import com.orange.mall.app.beans.request.VerifyCodeBean;
import com.orange.mall.app.modules.login.LoginActivity;
import com.orange.mall.app.utils.MiscUtils;
import com.orange.mall.app.widgets.VerifyCodeButton;

public class ForgetPasswordActivity extends AppCompatActivity implements ForgetPasswordContract.View {
  private static final String TAG = ForgetPasswordActivity.class.getSimpleName();

  private TextInputEditText mMobileTextInput;
  private TextInputEditText mVerifyCodeInput;
  private TextInputEditText mPasswordTextInput;
  private ProgressDialog mDialog = null;

  private ForgetPasswordContract.Presenter mPresenter = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forget_password);

    init();
  }

  private void init () {
    initView();
  }

  private void initView () {
    mMobileTextInput = findViewById(R.id.mobile_input);
    mVerifyCodeInput = findViewById(R.id.verifycode_input);
    mPasswordTextInput = findViewById(R.id.password_input);

    mDialog = new ProgressDialog(this);
    mDialog.setCancelable(false);
    mDialog.setMessage("正在修改密码...");
  }

  private void gotoLoginActivity () {
    Intent intent = new Intent(this, LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  /**
   * 点了确定修改按钮
   * @param view
   */
  public void onConfirmButtonClicked(View view) {
    mDialog.show();
    ForgetPasswordBean bean = new ForgetPasswordBean();
    bean.setMobile(mMobileTextInput.getText().toString());
    bean.setVerifyCode(mVerifyCodeInput.getText().toString());
    bean.setPassword(mPasswordTextInput.getText().toString());

    getPresenter().changePassword(bean);

  }

  /**
   * 发送验证码
   * @param view
   */
  public void sendVerifyCode (View view) {
    String mobile = mMobileTextInput.getText().toString();
    if (mobile == null || mobile.length() != 11) {
      Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
      return ;
    }

    VerifyCodeBean bean = new VerifyCodeBean();
    bean.setMobile(mobile);
    getPresenter().sendVerifyCode(bean);

    VerifyCodeButton button = (VerifyCodeButton) view;
    button.start();
  }

  @Override
  public void onPasswordChangeSuccessed() {
    Log.i(TAG, "onPasswordChangedSuccessed");
    mDialog.dismiss();
    MiscUtils.showMessageTip("修改密码成功");
    gotoLoginActivity();
  }

  @Override
  public void onPasswordChangeFailed(String message) {
    mDialog.dismiss();
    MiscUtils.showMessageTip(message);
  }

  @Override
  public ForgetPasswordContract.Presenter getPresenter() {
    if (mPresenter == null) {
      mPresenter = ForgetPasswordPresenter.newInstance(this);
    }

    return mPresenter;
  }
}
