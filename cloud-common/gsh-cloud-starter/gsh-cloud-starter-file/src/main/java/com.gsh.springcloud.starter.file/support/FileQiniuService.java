package com.gsh.springcloud.starter.file.support;

import com.qiniu.storage.model.DefaultPutRet;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author gsh
 */
public interface FileQiniuService {

  String getUploadToken(String fileKey);

  /**
   * 第三方资源抓取
   *
   * @param url 第三方资源URL
   * @param key 目标文件Key
   */
  String fetchExternalURL(String url, String key);

  /**
   * 第三方资源抓取(key自动生成)
   *
   * @param url 第三方资源URL
   */
  String fetchExternalURL(String url);

  /**
   * 获取文件的下载地址
   */
  String getAccessUrl(String fileKey);

  /**
   * 上传图片至七牛云
   */
  String uploadImg(File file);

  /**
   * 上传图片组到七牛云
   */
  List<String> uploadImages(List<File> files);

  InputStream getFileInputStream(String remoteFileName);

  File downloadFile(String remoteFileName, String localPath, String localFileName);


  byte[] getFileByFileName(String remoteFileName);

  /**
   * 获取文件key
   *
   * @param fileName
   * @return
   */
  String getFileKey(String fileName);

  DefaultPutRet uploadFile(File file, String fileName);
}
