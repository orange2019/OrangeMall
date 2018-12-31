package com.orange.mall.app.modules.main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;


import com.orange.mall.app.R;
import com.orange.mall.app.base.BaseFragment;
import com.orange.mall.app.constants.Api;
import com.orange.mall.app.jsbridge.JsInterfaceForAndroid;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CellsShowroomFragment.OnCellsShowroomFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CellsShowroomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CellsShowroomFragment extends BaseFragment {
  private static final String TAG = CellsShowroomFragment.class.getSimpleName();

  private CellsShowroomFragment.OnCellsShowroomFragmentInteractionListener mListener;

  public CellsShowroomFragment() {

  }


  public static CellsShowroomFragment newInstance(String token, String param2) {
    CellsShowroomFragment fragment = new CellsShowroomFragment();
    Bundle args = new Bundle();
    args.putString(KEY_TOKEN, token);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof CellsShowroomFragment.OnCellsShowroomFragmentInteractionListener) {
      mListener = (CellsShowroomFragment.OnCellsShowroomFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
        + " must implement OnCellsShowroomFragmentInteractionListener");
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
    String url = Api.h5Domain(Api.H5_CELLS_SHOWROOM_PATH) + "?token=" + mToken;
    Log.i(TAG, "LoadUrl: " + url);
    return url;
  }


  @Override
  public int getWebViewResourceId() {
    return R.id.fragment_cells_showroom_webview;
  }


  @Override
  public int getLayoutResourceId() {
    return R.layout.fragment_cells_showroom;
  }


  /**
   * Activity must implement this
   */
  public interface OnCellsShowroomFragmentInteractionListener {
    void OnCellsShowroomFragmentInteractionListener(Uri uri);
  }
}

