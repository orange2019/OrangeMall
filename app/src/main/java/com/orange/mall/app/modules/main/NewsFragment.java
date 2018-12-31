package com.orange.mall.app.modules.main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orange.mall.app.R;
import com.orange.mall.app.base.BaseFragment;
import com.orange.mall.app.constants.Api;
import com.orange.mall.app.jsbridge.JsInterfaceForAndroid;

import static com.orange.mall.app.constants.Storage.KEY_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFragment.OnNewsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends BaseFragment {
  private static final String TAG = NewsFragment.class.getSimpleName();

  private NewsFragment.OnNewsFragmentInteractionListener mListener;

  public NewsFragment() {

  }


  public static NewsFragment newInstance(String token, String param2) {
    NewsFragment fragment = new NewsFragment();
    Bundle args = new Bundle();
    args.putString(KEY_TOKEN, token);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof NewsFragment.OnNewsFragmentInteractionListener) {
      mListener = (NewsFragment.OnNewsFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
        + " must implement OnNewsFragmentInteractionListener");
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    mWebViewUtils2.addJavascriptInterface(
      JsInterfaceForAndroid.getInstance().init(this.getActivity()),
      "android");
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  @Override
  public String getWebViewUrl() {
    String url = Api.h5Domain(Api.H5_NEWS) + "&token=" + mToken;
    Log.i(TAG, "LoadUrl: " + url);
    return url;
  }


  @Override
  public int getWebViewResourceId() {
    return R.id.news_webview;
  }


  @Override
  public int getLayoutResourceId() {
    return R.layout.fragment_news;
  }


  /**
   * Activity must implement this
   */
  public interface OnNewsFragmentInteractionListener {
    void OnNewsFragmentInteractionListener(Uri uri);
  }
}
