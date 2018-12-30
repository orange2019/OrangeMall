package com.orange.mall.app.utils;

import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;

public class NetUtils {

  // 下载文件
  public static int download(String uri, String dirPath, String fileName, OnDownloadListener listener) {
    int downloadId = PRDownloader.download(uri, dirPath, fileName)
      .build()
      .setOnStartOrResumeListener(new OnStartOrResumeListener() {
        @Override
        public void onStartOrResume() {

        }
      }).setOnPauseListener(new OnPauseListener() {
        @Override
        public void onPause() {

        }
      }).setOnCancelListener(new OnCancelListener() {
        @Override
        public void onCancel() {

        }
      }).setOnProgressListener(new OnProgressListener() {
        @Override
        public void onProgress(Progress progress) {

        }
      }).start(listener);

    return downloadId;
  }


}
