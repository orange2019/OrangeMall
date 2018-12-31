package com.orange.mall.app.modules.forgetpassword;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.orange.mall.app.R;
import com.orange.mall.app.beans.request.VerifyCodeBean;
import com.orange.mall.app.utils.MiscUtils;

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

  /**
   * 点了确定修改按钮
   * @param view
   */
  public void onConfirmButtonClicked(View view) {
    mDialog.show();
  }

  /**
   * 发送验证码
   * @param view
   */
  public void sendVerifyCode (View view) {
    VerifyCodeBean bean = new VerifyCodeBean();
    bean.setMobile(mMobileTextInput.getText().toString());
    getPresenter().sendVerifyCode(bean);
  }

  @Override
  public void onPasswordChangeSuccessed() {
    Log.i(TAG, "onPasswordChangedSuccessed");
    mDialog.dismiss();
    MiscUtils.showMessageTip("修改密码成功");
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
