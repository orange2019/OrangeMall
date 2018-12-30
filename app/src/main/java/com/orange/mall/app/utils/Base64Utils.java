package com.orange.mall.app.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Base64
 *
 * @author venshine
 */
public class Base64Utils {

  /**
   * base64编码
   *
   * @param input
   * @return
   */
  public static byte[] encodeBase64(byte[] input) {
    return Base64.encode(input, Base64.NO_WRAP);
  }

  /**
   * base64编码
   *
   * @param s
   * @return
   */
  public static String encodeBase64(String s) {
    return Base64.encodeToString(s.getBytes(), Base64.NO_WRAP);
  }


  public static String encodeBytesToString(byte[] bytes) {
    return Base64.encodeToString(bytes, Base64.NO_WRAP);
  }

  public static byte[] encodeStringToBytes(String s) {
    try {
      return Base64.encodeToString(s.getBytes(), Base64.NO_WRAP).getBytes("UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * base64解码
   *
   * @param input
   * @return
   */
  public static byte[] decodeBase64(byte[] input) {
    return Base64.decode(input, Base64.NO_WRAP);
  }


  public static byte[] decodeStringToBytes(String s) {
    return Base64.decode(s, Base64.NO_WRAP);
  }
}