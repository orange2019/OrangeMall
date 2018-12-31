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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MallFragment.OnMallFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MallFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MallFragment extends BaseFragment {
  private static final String TAG = MallFragment.class.getSimpleName();

  private MallFragment.OnMallFragmentInteractionListener mListener;

  public MallFragment() {

  }


  public static MallFragment newInstance(String token, String param2) {
    MallFragment fragment = new MallFragment();
    Bundle args = new Bundle();
    args.putString(KEY_TOKEN, token);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof MallFragment.OnMallFragmentInteractionListener) {
      mListener = (MallFragment.OnMallFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
        + " must implement OnMallFragmentInteractionListener");
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
    String url = Api.h5Domain(Api.H5_MALL_PATH) + "?token=" + mToken;
    Log.i(TAG, "LoadUrl: " + url);
    return url;
  }


  @Override
  public int getWebViewResourceId() {
    return R.id.mall_webview;
  }


  @Override
  public int getLayoutResourceId() {
    return R.layout.fragment_mall;
  }


  /**
   * Activity must implement this
   */
  public interface OnMallFragmentInteractionListener {
    void OnMallFragmentInteractionListener(Uri uri);
  }
}
