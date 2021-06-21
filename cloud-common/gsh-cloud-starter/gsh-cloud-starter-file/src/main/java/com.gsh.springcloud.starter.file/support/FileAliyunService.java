package com.gsh.springcloud.starter.file.support;

import com.aliyun.oss.model.PutObjectResult;
import com.gsh.springcloud.starter.file.model.PostPolicy;

import java.io.File;
import java.util.List;

public interface FileAliyunService {

  PutObjectResult uploadFile(File file, String fileNamePrefix, String bucketName);

  PutObjectResult uploadFile(byte[] file, String fileName, String fileNamePrefix,
                             String bucketName);

  void uploadFiles(List<File> files, String fileNamePrefix, String bucketName);

  void downloadFiles(String[] fileNames, String bucketName);

  void deleteFile(String objectName, String fileNamePrefix, String bucketName);

  /**
   * 根据配置文件取policy
   */
  PostPolicy getPostPolicy();

  /**
   * 根据自定义参数取policy
   *
   * @param bucket  自定义bucket dir
   * @param dir     自定义dir
   * @param expires 自定义 expires
   */
  PostPolicy getPostPolicy(String bucket, String dir, Integer expires);

  /**
   * 根据配置文件取对应的objName的临时访问路径
   *
   * @param objName 资源路径
   */
  String getFileUrl(String objName);

  /**
   * 根据自定义参数取对应的objName的临时访问路径
   */
  String getFileUrl(String bucket, String objName);
}
