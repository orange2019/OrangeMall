package com.orange.mall.app.utils;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.orange.mall.app.Application;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.*;


public class VolleyUtils {

  private static final String TAG = VolleyUtils.class.getSimpleName();

  private static VolleyUtils sInstance = null;

  private RequestQueue mRequestQueue;

  private boolean mHasInited = false;

  Map<String, String> mHeaders = new HashMap<>();

  Map<String, String> mMoreHeaders = new HashMap<>();

  private VolleyUtils() {
    this.init(Application.getAppContext());
  }

  public static VolleyUtils getInstance() {
    if (sInstance == null) {
      synchronized (VolleyUtils.class) {
        if (sInstance == null) {
          sInstance = new VolleyUtils();
        }
      }
    }

    return sInstance;
  }


  public void init(Context ctx) {
    if (mHasInited) {
      return;
    }

    mRequestQueue = Volley.newRequestQueue(ctx);

    mHeaders.put("Content-Type", "application/json; charset=utf-8");

    mHasInited = true;
  }


  /**
   * 自定义的header
   *
   * @param headers
   * @return
   */
  public VolleyUtils buildHeaders(Map<String, String> headers) {
    mMoreHeaders = headers;
    return this;
  }

  /**
   * GET 请求
   *
   * @param uri
   */
  public void GET(String uri, final JSONResponseProxy responseProxy) {

    // 清空 more headers

    JsonObjectRequest jsonReq = new JsonObjectRequest(GET, uri, null, new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        responseProxy.onResponse(response);
      }

    }, new Response.ErrorListener() {

      @Override
      public void onErrorResponse(VolleyError error) {
        responseProxy.onError(error);
      }

    }) {
      @Override
      public Map<String, String> getHeaders() {
        return resolveHeaders();
      }
    };

    jsonReq.setRetryPolicy(new DefaultRetryPolicy(
      10000,
      DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
      DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    mRequestQueue.add(jsonReq);
  }


  /**
   * POST 请求
   *
   * @param uri
   */
  public void POST(String uri, JSONObject reqJson, final JSONResponseProxy responseProxy) {

    // 清空 more headers

    JsonObjectRequest jsonReq = new JsonObjectRequest(POST, uri, reqJson, new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        responseProxy.onResponse(response);
      }

    }, new Response.ErrorListener() {

      @Override
      public void onErrorResponse(VolleyError error) {
        responseProxy.onError(error);
      }

    }) {
      @Override
      public Map<String, String> getHeaders() {
        return resolveHeaders();
      }
    };

    jsonReq.setRetryPolicy(new DefaultRetryPolicy(
      10000,
      DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
      DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    mRequestQueue.add(jsonReq);
  }


  /**
   * POST 请求
   *
   * @param uri
   * @param reqJsonStr
   * @param responseListener
   * @param errorListener
   */
  public void POST(String uri, String reqJsonStr, final JSONResponseProxy responseProxy) {
    try {
      JSONObject jsonObject = new JSONObject(reqJsonStr);
      POST(uri, jsonObject, responseProxy);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }


  /**
   * 处理headers
   *
   * @return
   */
  private Map<String, String> resolveHeaders() {
    Map<String, String> headers = new HashMap<>();

    headers.putAll(mHeaders);

    headers.putAll(mMoreHeaders);

    mMoreHeaders.clear();

    return headers;
  }


  /**
   * json response proxy
   */
  public interface JSONResponseProxy {

    void onResponse(JSONObject response);

    void onError(VolleyError error);
  }
}



