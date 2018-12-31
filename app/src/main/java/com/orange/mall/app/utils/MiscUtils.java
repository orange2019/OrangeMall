package com.orange.mall.app.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.orange.mall.app.Application;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

public class MiscUtils {

  private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

  /**
   * 生成uuid字符串
   *
   * @return uuid 字符串
   */
  public static String generateUUID() {
    MessageDigest salt = null;
    try {
      salt = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    try {
      salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    String digest = MiscUtils.bytesToHex(salt.digest());

    return digest;
  }


  /**
   * bytes => 十六进制字符串
   *
   * @param bytes
   * @return
   */
  public static String bytesToHex(byte[] bytes) {
    char[] hexChars = new char[bytes.length * 2];
    for (int j = 0; j < bytes.length; j++) {
      int v = bytes[j] & 0xFF;
      hexChars[j * 2] = hexArray[v >>> 4];
      hexChars[j * 2 + 1] = hexArray[v & 0x0F];
    }
    return new String(hexChars);
  }

  /**
   * 安装apk
   *
   * @param apkPath
   * @param activity
   */
  public static void installApk(String apkPath, Activity activity) {

    chmod777(apkPath);

    Intent intent = new Intent(Intent.ACTION_VIEW);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
      Uri contentUri = FileProvider.getUriForFile(activity, "com.orange.wallet.fileprovider", new File(apkPath));

//      List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//      for (ResolveInfo resolveInfo : resInfoList) {
//        String packageName = resolveInfo.activityInfo.packageName;
//        activity.grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//      }

      intent.setDataAndType(contentUri, "application/vnd.android.package-archive");

    } else {
      intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
    }

    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    activity.startActivity(intent);
  }

  /**
   * 提升读写权限
   * @param filePath 文件路径
   * @return
   * @throws IOException
   */
  public static void chmod777(String filePath)  {
    String command = "chmod " + "777" + " " + filePath;
    Runtime runtime = Runtime.getRuntime();
    try {
      runtime.exec(command);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 显示 message tip 提示
   * @param message
   */
  public static void showMessageTip (String message) {
    Toast.makeText(Application.getAppContext(), message, Toast.LENGTH_SHORT).show();
  }
}
