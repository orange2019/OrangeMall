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
import com.orange.mall.app.constants.Storage;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserCenterFragment.OnUserCenterFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserCenterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserCenterFragment extends BaseFragment {

  private static final String TAG = UserCenterFragment.class.getSimpleName();

  private OnUserCenterFragmentInteractionListener mListener;

  public UserCenterFragment() {

  }


  public static UserCenterFragment newInstance(String token, String param2) {
    UserCenterFragment fragment = new UserCenterFragment();
    Bundle args = new Bundle();
    args.putString(KEY_TOKEN, token);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnUserCenterFragmentInteractionListener) {
      mListener = (OnUserCenterFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
        + " must implement OnNewsFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  @Override
  public String getWebViewUrl() {
    String url = Api.h5Domain(Api.H5_USER_CENTER_PATH) + "?token=" + mToken;
    Log.i(TAG, "LoadUrl: " + url);
    return url;
  }


  @Override
  public int getWebViewResourceId() {
    return R.id.user_center_webview;
  }


  @Override
  public int getLayoutResourceId() {
    return R.layout.fragment_user_center;
  }


  /**
   * Activity must implement this
   */
  public interface OnUserCenterFragmentInteractionListener {
    void OnUserCenterFragmentInteractionListener(Uri uri);
  }

}

