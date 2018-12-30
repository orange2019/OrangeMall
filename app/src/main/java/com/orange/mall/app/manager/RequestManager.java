package com.orange.mall.app.manager;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orange.mall.app.beans.BaseRequestBean;
import com.orange.mall.app.constants.Secret;
import com.orange.mall.app.utils.CryptUtils;
import com.orange.mall.app.utils.MiscUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class RequestManager {
  private static final String TAG = RequestManager.class.getSimpleName();

  private volatile static RequestManager sRequestManager = null;
  private String mUUID;

  private RequestManager() {
    // 阻止外部构造Instance

    mUUID = MiscUtils.generateUUID();
  }

  // 单列
  public static RequestManager getInstance() {
    if (sRequestManager == null) {
      synchronized (RequestManager.class) {
        if (sRequestManager == null) {
          sRequestManager = new RequestManager();
        }
      }
    }
    return sRequestManager;
  }



  /**
   * 获取请求的数据
   * @return
   * @throws Exception
   */
  public <T> String buildRequstData (T content, Class<T> clazz) throws Exception {
    BaseRequestBean<T> brb = new BaseRequestBean<>(mUUID);

    brb.setContent(content);

    GsonBuilder gb = new GsonBuilder();
    Gson gson = gb.create();
    String contentJson = gson.toJson(content, clazz);

    JSONObject json = new JSONObject(contentJson);
    String query = json2query(json);
    Log.i(TAG, "json2query: " + query);
    String cryptedStr = CryptUtils.cryptWithHmacMD5(query, mUUID);
    Log.i(TAG, "HmacMD5: " + cryptedStr);

    byte[] sign = CryptUtils.sign(cryptedStr.getBytes(), Secret.RSA_PRIVATE_KEY);
    Log.i(TAG, "encodeBytesToString: " + MiscUtils.bytesToHex(sign));
    brb.setSign(MiscUtils.bytesToHex(sign));

    Log.i(TAG, "RequestData: " + brb.toJson());
    return brb.toJson();
  }


  /**
   * JSONObject to query
   * @param json
   * @return
   */
  String json2query(JSONObject json) {
    String query = "";
    SortedMap<String, String> map = new TreeMap<>();
    Iterator<String> it = json.keys();
    while (it.hasNext()) {
      String key = it.next();
      try {
        String value = json.getString(key);
        map.put(key, value);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }

    Log.i(TAG, "json2query map: " + map.toString());

    List<String> queries = new ArrayList<>();

    Iterator iter = map.entrySet().iterator();
    while(iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      if (entry.getValue() != null && !entry.getValue().equals("")) {
        queries.add(entry.getKey() + "=" + entry.getValue());
      }
    }

    Log.i(TAG, "json2query queries: " + queries.toString());

    for (int i = 0; i < queries.size(); i++) {
      query = query.concat(queries.get(i) + (i < (queries.size() - 1) ? "&" : ""));
    }

    return query;
  }
}
