package com.orange.mall.app.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.orange.mall.app.utils.WebViewUtils2;

interface BaseFragmentInterface {
  boolean onKeyDown(int keyCode, KeyEvent event);

  String getWebViewUrl();

  int getWebViewResourceId();

  int getLayoutResourceId();
}


public abstract  class BaseFragment extends Fragment implements BaseFragmentInterface {

  private static final String TAG = BaseFragment.class.getSimpleName();

  protected static final String KEY_TOKEN = "TOKEN";
  protected static final String ARG_PARAM2 = "param2";

  protected String mToken;
  protected String mParam2;

  protected WebView mWebView = null;

  protected View mView = null;

  protected WebViewUtils2 mWebViewUtils2 = null;

  protected ProgressDialog mProgressDlg = null;
  private boolean mFragmentVisible;

  public BaseFragment() {

  }

  public static Fragment newInstance(String token, String param2) {
    return null;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    Log.i(TAG, "onCreate(Bundle savedInstanceState)");
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mToken = getArguments().getString(KEY_TOKEN);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    Log.i(TAG, "onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState");

    if (mView == null) {
      mView = inflater.inflate(this.getLayoutResourceId(), container, false);

    } else {

      ViewGroup parent = (ViewGroup) mView.getParent();
      if (parent != null) {
        parent.removeView(mView);
      }
    }

    return mView;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    Log.i(TAG, "onActivityCreated");

    super.onActivityCreated(savedInstanceState);

    if (mWebViewUtils2 == null) {
      mWebViewUtils2 = new WebViewUtils2((WebView) mView.findViewById(this.getWebViewResourceId()), this.getWebViewUrl());
    }

    if (mProgressDlg == null) {
      mProgressDlg = new ProgressDialog(this.getActivity());
      mProgressDlg.setCancelable(false);
    }

    if (mFragmentVisible) {
      mWebViewUtils2.setProgressDialog(mProgressDlg);
      mWebViewUtils2.init();
    }

  }

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    Log.i(TAG, String.format("setUserVisibleHint: %b ", isVisibleToUser));

    super.setUserVisibleHint(isVisibleToUser);
    mFragmentVisible = isVisibleToUser;

    if (isVisibleToUser) {
      // Do your Work
      if (mWebViewUtils2 != null) {
        mWebViewUtils2.init();
      }

    } else {
      // Do your Work
    }
  }

  @Override
  public void onStart() {
    Log.i(TAG, "onStart");
    super.onStart();
  }

  @Override
  public void onStop() {
    Log.i(TAG, "onStop");
    super.onStop();
  }

  @Override
  public void onDestroy() {
    Log.i(TAG, "onDestroy");

    super.onDestroy();
    mWebViewUtils2.destroy();
    mProgressDlg = null;
  }

  // 监听按键事件
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    Log.i(TAG, "onKeyDown");

    if (mWebView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {
      mWebView.goBack();
      return true;
    }

    return false;
  }


}
