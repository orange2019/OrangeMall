package com.orange.mall.app.utils;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;




public class CryptUtils {
  private static final String TAG = CryptUtils.class.getSimpleName();

  public static final String KEY_ALGORITHM="RSA";
  public static final String SIGNATURE_ALGORITHM="SHA256withRSA";
  private static final int KEY_SIZE=1024;
  private static final String PUBLIC_KEY="RSAPublicKey";
  private static final String PRIVATE_KEY="RSAPrivateKey";

  /**
   * HMAC-MD5 加密
   *
   * @param content
   * @param cryptKey
   * @return
   */
  public static String cryptWithHmacMD5(String content, String cryptKey) {
    Log.i(TAG, "cryptWithHmacMD5 content: " + content);
    String encodedString = null;
    try {
      SecretKeySpec key = new SecretKeySpec((cryptKey).getBytes("UTF-8"), "HmacMD5");
      Mac mac = Mac.getInstance("HmacMD5");
      mac.init(key);

      byte[] bytes = mac.doFinal(content.getBytes("ASCII"));

      StringBuffer hash = new StringBuffer();

      for (int i = 0; i < bytes.length; i++) {
        String hex = Integer.toHexString(0xFF & bytes[i]);
        if (hex.length() == 1) {
          hash.append('0');
        }
        hash.append(hex);
      }
      encodedString = hash.toString().toUpperCase();
      Log.i(TAG, "encodedString " + encodedString);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();

    } catch (InvalidKeyException e) {
      e.printStackTrace();

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    return encodedString;
  }

//  public static String cryptWithHmacMD5(String content, String key) {
//    try {
//      Mac mac = Mac.getInstance("HmacMD5");
//      SecretKeySpec secret = new SecretKeySpec(key.getBytes("UTF-8"), mac.getAlgorithm());
//      try {
//        mac.init(secret);
//      } catch (java.security.InvalidKeyException e) {
//        e.printStackTrace();
//      }
//
//      return bytesToHex(mac.doFinal(content.getBytes()));
//
//    } catch (NoSuchAlgorithmException e) {
//      e.printStackTrace();
//    } catch (UnsupportedEncodingException e) {
//      e.printStackTrace();
//
//    } catch (InvalidKeyException e) {
//      e.printStackTrace();
//    }
//    return "";
//  }
//
//  public static String bytesToHex(byte[] bytes) {
//    char[] hexArray = "0123456789ABCDEF".toCharArray();
//    char[] hexChars = new char[bytes.length * 2];
//    for ( int j = 0; j < bytes.length; j++ ) {
//      int v = bytes[j] & 0xFF;
//      hexChars[j * 2] = hexArray[v >>> 4];
//      hexChars[j * 2 + 1] = hexArray[v & 0x0F];
//    }
//    return new String(hexChars);
//  }



  /**
   * 转换私钥
   * @param base64 String to PrivateKey
   * @throws Exception
   */
  public static PrivateKey getPrivateKey(String key) throws Exception {
    byte[] keyBytes = Base64Utils.decodeStringToBytes(key);

    Log.i("CryptUtils", String.format(Base64Utils.encodeBytesToString(keyBytes)));

    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
    return privateKey;
  }

  /**
   * 签名
   * @param data
   * @param strPrivKey
   * @return
   * @throws Exception
   */
  public static byte[] sign(byte[] data, String strPrivKey) throws Exception{
    PrivateKey priK = getPrivateKey(strPrivKey);
    Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
    sig.initSign(priK);
    sig.update(data);
    return sig.sign();
  }



}
