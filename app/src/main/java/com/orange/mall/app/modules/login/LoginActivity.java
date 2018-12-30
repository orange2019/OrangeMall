package com.orange.mall.app.modules.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.orange.mall.app.R;
import com.orange.mall.app.modules.forgetpassword.ForgetPasswordActivity;
import com.orange.mall.app.modules.main.MainActivity;
import com.orange.mall.app.modules.register.RegisterActivity;
import com.orange.mall.app.utils.ActivityUtils;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

  private static final String TAG = LoginActivity.class.getSimpleName();

  private TextInputEditText mMobileTextInput = null;
  private TextInputEditText mPasswordTextInput = null;
  private Button mLoginButton = null;
  private Button mLoginLaterButton = null;
  private TextView mGotoRegisterTextView = null;
  private TextView mForgetPwdTextView = null;

  private String mobile = "";
  private String password = "";

  private LoginPresenter mLoginPresenter = null;

  private boolean hasInited = false;

  ProgressDialog mDialog = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_login);

    this.init();

    mDialog = new ProgressDialog(this);
    }

  @Override
  protected void onStart() {
    super.onStart();
    mLoginPresenter.onStart();
  }

  @Override
  protected void onStop() {
    super.onStop();
    mLoginPresenter.onStop();
  }


  private void init() {
    if (this.hasInited) {
      return;
    }

    // this.initPreference();

    this.initView();

    registerEvents();

    mLoginPresenter = LoginPresenter.newInstance(this);

    this.hasInited = true;
  }

  /**
   * 初始化 view 组件
   */
  private void initView() {

    mMobileTextInput = findViewById(R.id.mobile_input);
    mPasswordTextInput = findViewById(R.id.password_input);
    mLoginButton = findViewById(R.id.login_button);
    mGotoRegisterTextView = findViewById(R.id.goto_register);
    mForgetPwdTextView = findViewById(R.id.forget_pwd);
    mLoginLaterButton = findViewById(R.id.loginlater_button);
  }

//  private void initPreference() {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//      //透明状态栏
//      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//      //透明导航栏
//      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//      SystemBarTintManager tintManager = new SystemBarTintManager(this);
//      // 激活状态栏
//      tintManager.setStatusBarTintEnabled(true);
//      // enable navigation bar tint 激活导航栏
//      tintManager.setNavigationBarTintEnabled(true);
//      //设置系统栏设置颜色
//      //tintManager.setTintColor(R.color.red);
//      //给状态栏设置颜色
//      tintManager.setStatusBarTintResource(R.color.black_overlay);
//      //Apply the specified drawable or color resource to the system navigation bar.
//      //给导航栏设置资源
//      tintManager.setNavigationBarTintResource(R.color.black_overlay);
//
//    }
//  }

  /**
   * 注册事件
   */
  private void registerEvents() {

    /// 输入手机号
    mMobileTextInput.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        mobile = mMobileTextInput.getText().toString();
        Log.i(TAG, String.format("Mobile: %s ", mobile));
      }
    });

    // 输入密码
    mPasswordTextInput.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        password = mPasswordTextInput.getText().toString();
        Log.i(TAG, String.format("Password: %s ", password));
      }
    });

  }

  /**
   * 跳转到登录界面
   * @param view
   */
  public void onLoginButtonClicked (View view) {
    mLoginPresenter.login(mobile, password);
  }

  /**
   *
   * @param view
   */
  public void onRegisterClicked(View view) {
    Intent intent = new Intent(this, RegisterActivity.class);
    // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  /**
   * 跳转到忘记密码页面
   * @param view
   */
  public void onForgetPasswordClicked(View view) {
    // 跳转到MainActivity
    Intent intent = new Intent(this, ForgetPasswordActivity.class);
    // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }


  @Override
  public void onShowLoginState(String state) {

    if ("logining".equals(state)) {
      mDialog.setCancelable(false);
      mDialog.setMessage("正在登录...");
      mDialog.show();
    }

    if ("successed".equals(state)) {

      if (mDialog.isShowing()) {
        mDialog.dismiss();
        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
      }
      gotoMainActivity();
    }

    if ("failed".equals(state)) {
      if (mDialog.isShowing()) {
        mDialog.dismiss();
        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
      }
    }
  }

  @Override
  public LoginContract.Presenter getPresenter() {
    if (mLoginPresenter == null) {
      mLoginPresenter = LoginPresenter.newInstance(this);
    }

    return mLoginPresenter;
  }


  /**
   * 稍后登录
   * @param view
   */
  public void onLoginLater(View view) {
    // 跳转到MainActivity
    gotoMainActivity();
  }


  public void gotoMainActivity () {
    Intent intent = new Intent(this, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }
}
