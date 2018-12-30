package com.orange.mall.app.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.MalformedURLException;
import java.net.URL;

public class WebViewUtils2 {

  private static final String TAG = WebViewUtils2.class.getSimpleName();

  private WebView mWebView = null;
  private WebViewClient mWebViewClient = null;
  private WebChromeClient mWebChromeClient = null;
  private WebSettings mWebSettings = null;
  private ProgressDialog mProgressDialog = null;
  private String mUrl = "about:blank"; // 加载界面

  private boolean mHasInited = false;

  /**
   * WebViewUtils2构造函数
   */
  public WebViewUtils2(WebView webView, String url) {
    mWebView = webView;
    mWebViewClient = null;
    mWebChromeClient = null;
    mProgressDialog = null;
    mUrl = url;
  }


  /**
   * 设置加载窗口（loading界面）
   * @param dlg
   */
  public void setProgressDialog(ProgressDialog dlg) {
    mProgressDialog = dlg;
  }

  /**
   * 初始化
   */
  public void init() {
    Log.i(TAG, "init()");

    if (mWebView == null) {
      initWebView();

    }

    mWebView.measure(100, 100);
    mWebView.getSettings().setUseWideViewPort(true);
    mWebView.getSettings().setLoadWithOverviewMode(true);
    mWebView.getSettings().setDomStorageEnabled(true);
    mWebView.getSettings().setJavaScriptEnabled(true);
    mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);


    Log.i(TAG, "originalUrl: " + mWebView.getOriginalUrl());
    String originalUrl = mWebView.getOriginalUrl();
    Log.i(TAG, "this url is : " + mUrl);
    if (originalUrl == null) { // 首次进来
      mWebView.loadUrl(mUrl);
      return;
    }

    URL originalUrlObj = null;
    URL urlObj = null;

    try {
      originalUrlObj = new URL(originalUrl);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    try {
      urlObj = new URL(mUrl);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    boolean needLoad = !(originalUrlObj.getHost() + originalUrlObj.getPath()).startsWith(urlObj.getHost() + urlObj.getPath());
    Log.i(TAG, String.format("NeedReload %b", needLoad));
    if (originalUrlObj == null || urlObj == null || needLoad) {
      mWebView.loadUrl(mUrl);
    }

    Log.i(TAG, "webview load url: " + mUrl);
  }


  public void destroy() {
    if (mWebView != null) {
      mWebView.destroy();
      mWebView = null;
      mWebChromeClient = null;
      mWebViewClient = null;
    }

  }

  /**
   * 添加javascript接口
   * @param name
   */
  @SuppressLint("JavascriptInterface")
  public void addJavascriptInterface(Object obj, String name) {
    if (mWebView == null) {
      return;
    }
    mWebView.addJavascriptInterface(obj, name);
  }

  /**
   * 移除javascript接口
   * @param name
   */
  public void removeJavascriptInterface(String name) {
    if (mWebView != null) {
      mWebView.removeJavascriptInterface(name);
    }
  }

  private void initWebView () {
    Log.i(TAG, "initWebView");

    initWebViewClient();
    initWebChromeClient();

    mWebView.setWebChromeClient(mWebChromeClient);
    mWebView.setWebViewClient(mWebViewClient);

    mWebSettings= mWebView.getSettings();
    mWebSettings.setJavaScriptEnabled(true);//允许使用js
    mWebSettings.setAllowUniversalAccessFromFileURLs(true);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      mWebSettings.setSafeBrowsingEnabled(true);
    }


    /**
     * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
     * LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
     * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
     * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
     */
    mWebSettings.setAppCacheEnabled(true);
    mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

    //支持屏幕缩放
    mWebSettings.setSupportZoom(true);
    mWebSettings.setBuiltInZoomControls(true);

    //不显示webview缩放按钮
    mWebSettings.setDisplayZoomControls(false);

    mWebSettings.setBlockNetworkLoads(false);
    mWebSettings.setBlockNetworkImage(false);
    mWebSettings.setAllowContentAccess(true);
    mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
    }
  }


  private void initWebViewClient () {
    Log.i(TAG, "initWebViewClient");

    //WebViewClient主要帮助WebView处理各种通知、请求事件
    mWebViewClient = new WebViewClient() {
      @Override
      public void onPageFinished(WebView view, String url) {//页面加载完成
        Log.i(TAG, "onPageFinished url: " + url);
        // 显示webView
        View parentView = (View)view.getParent();
        parentView.setVisibility(View.VISIBLE);
      }

      @Override
      public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
        Log.i(TAG, "onPageStarted url: " + url);
        // 显示loading
        View parentView = (View)view.getParent();
        parentView.setVisibility(View.INVISIBLE);
      }

      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.i(TAG, "shouldOverrideUrlLoading url: " + url);
        return super.shouldOverrideUrlLoading(view, url);
        // return false;
      }
    };
  }


  /**
   * WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
   */
  private void initWebChromeClient () {
    Log.i(TAG, "initWebChromeClient");

    mWebChromeClient = new WebChromeClient() {
      @Override
      public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
        localBuilder.setMessage(message).setPositiveButton("确定",null);
        localBuilder.setCancelable(false);
        localBuilder.create().show();

        //注意:
        //必须要这一句代码:result.confirm()表示:
        //处理结果为确定状态同时唤醒WebCore线程
        //否则不能继续点击按钮
        result.confirm();
        return true;
      }

      //获取网页标题
      @Override
      public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        Log.i(TAG,"网页标题:"+title);
      }

      //加载进度回调
      @Override
      public void onProgressChanged(WebView view, int newProgress) {
        Log.i(TAG, String.format("PageLoaded: %d", newProgress));
        if (mProgressDialog != null) {
          mProgressDialog.setMessage(String.format("正在加载...%d%%", newProgress));
        }
      }
    };

  }

}
