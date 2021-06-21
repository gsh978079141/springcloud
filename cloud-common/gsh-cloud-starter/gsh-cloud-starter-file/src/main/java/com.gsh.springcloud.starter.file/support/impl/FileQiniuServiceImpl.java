package com.gsh.springcloud.starter.file.support.impl;

import com.google.gson.Gson;
import com.gsh.springcloud.common.exception.UploadFileErrorException;
import com.gsh.springcloud.common.vo.UploadVO;
import com.gsh.springcloud.starter.file.properties.QiniuProperties;
import com.gsh.springcloud.starter.file.support.FileQiniuService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author gsh
 */
@Slf4j
public class FileQiniuServiceImpl implements FileQiniuService {

  @Resource
  private Auth qiniuAuth;

  @Resource
  QiniuProperties qiniuProperties;

  @Resource
  UploadManager qiniuUploadManager;

  @Resource
  BucketManager qiniuBucketManager;

  @Override
  public String getUploadToken(String fileKey) {
    return qiniuAuth.uploadToken(qiniuProperties.getBucketName(), fileKey, 3600, null, true);
  }

  @Override
  public String fetchExternalURL(String url, String key) {
    String result = qiniuProperties.getAccessRootUrl() + key;
    try {
      FetchRet fetch = qiniuBucketManager.fetch(url, qiniuProperties.getBucketName(), key);
    } catch (QiniuException e) {
      log.error("exception happened when fetching external url", e);
    }
    return result;
  }

  @Override
  public String fetchExternalURL(String url) {
    String result = null;
    try {
      URL urlObj = new URL(url);
      String path = urlObj.getPath();
      String key = "" + System.currentTimeMillis() + "-" + FilenameUtils.getName(path);
      result = fetchExternalURL(url, key);
    } catch (MalformedURLException e) {
      log.error("exception happened when fetching external url", e);
    }
    return result;
  }

  @Override
  public String getAccessUrl(String fileKey) {
    return qiniuProperties.getAccessRootUrl() + fileKey;
  }

  /**
   * 上传图片到七牛云里面
   */
  @Override
  public String uploadImg(File file) {
    if (file != null) {
      String originName = file.getName();
      String format = originName.substring(originName.lastIndexOf(".") + 1);
      String fileName = System.currentTimeMillis() + "." + format;
      uploadFile(file, fileName);
      return qiniuProperties.getAccessRootUrl() + fileName;
    }
    return null;
  }

  @Override
  public List<String> uploadImages(List<File> files) {
    List<String> imgPaths = new ArrayList<>();
    if (files.size() > 0) {
      for (File file : files) {
        String imgPath = uploadImg(file);
        if (StringUtils.isNotBlank(imgPath)) {
          imgPaths.add(imgPath);
        }
      }
    }
    return imgPaths;
  }

  @Override
  public InputStream getFileInputStream(String remoteFileName) {
    String targetUrl = String.format("%s/%s", qiniuProperties.getAccessRootUrl(), remoteFileName);
    String downloadUrl = qiniuAuth.privateDownloadUrl(targetUrl);
    log.info("downloadUrl={}", downloadUrl);
    OkHttpClient client = new OkHttpClient();
    Request req = new Request.Builder().url(downloadUrl).build();
    try {
      okhttp3.Response resp = null;
      resp = client.newCall(req).execute();
      log.info("success={}", resp.isSuccessful());
      if (resp.isSuccessful()) {
        ResponseBody body = resp.body();
        return body.byteStream();
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return null;
  }


  @Override
  public File downloadFile(String remoteFileName, String localPath, String localFileName) {
    String targetUrl = String.format("%s/%s", qiniuProperties.getAccessRootUrl(), remoteFileName);
    String downloadUrl = qiniuAuth.privateDownloadUrl(targetUrl);
    log.info("downloadUrl={}", downloadUrl);
    OkHttpClient client = new OkHttpClient();
    Request req = new Request.Builder().url(downloadUrl).build();
    try {
      okhttp3.Response resp = null;
      resp = client.newCall(req).execute();
      log.info("success={}", resp.isSuccessful());
      if (resp.isSuccessful()) {
        ResponseBody body = resp.body();
        InputStream is = body.byteStream();
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        byte[] buff = new byte[1024 * 2];
        int len = 0;
        try {
          while ((len = is.read(buff)) != -1) {
            writer.write(buff, 0, len);
          }
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
        byte[] data = writer.toByteArray();
        String downloadFileUrl = String.format("%s/%s", localPath, localFileName);
        File downloadFile = new File(downloadFileUrl);
        if (!downloadFile.getParentFile().exists()) {
          downloadFile.getParentFile().mkdirs();
        }
        boolean isSuccess = downloadFile.createNewFile();
        log.info("localfile is create success {}", isSuccess);
        FileOutputStream fos = new FileOutputStream(downloadFile);
        fos.write(data);
        fos.close();
        return downloadFile;
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  @Override
  public byte[] getFileByFileName(String remoteFileName) {
    String targetUrl = String.format("%s/%s", qiniuProperties.getAccessRootUrl(), remoteFileName);
    String downloadUrl = qiniuAuth.privateDownloadUrl(targetUrl);
    log.info("downloadUrl={}", downloadUrl);
    OkHttpClient client = new OkHttpClient();
    Request req = new Request.Builder().url(downloadUrl).build();
    try {
      okhttp3.Response resp = null;
      resp = client.newCall(req).execute();
      log.info("success={}", resp.isSuccessful());
      if (resp.isSuccessful()) {
        ResponseBody body = resp.body();
        InputStream is = body.byteStream();
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        byte[] buff = new byte[1024 * 2];
        int len = 0;
        try {
          while ((len = is.read(buff)) != -1) {
            writer.write(buff, 0, len);
          }
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
        return writer.toByteArray();
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  @Override
  public String getFileKey(String fileName) {
    UploadVO vo = new UploadVO();
    String randomPrefix = UUID.randomUUID().toString();
    String suffix = FilenameUtils.getExtension(fileName);
    String fileKey = randomPrefix + "." + suffix;
    return fileKey;
  }

  @Override
  public DefaultPutRet uploadFile(File file, String fileName) {
    String uploadToken = qiniuAuth.uploadToken(qiniuProperties.getBucketName());
    try {
      Response response = qiniuUploadManager.put(file, fileName, uploadToken);
      //解析上传成功的结果
      DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
      log.info("uploadSuccessKey:{},  uploadSuccessHash:{}", putRet.key, putRet.hash);
      return putRet;
    } catch (QiniuException ex) {
      Response r = ex.response;
      log.error(r.toString());
      try {
        log.error(r.bodyString());
      } catch (QiniuException ex2) {
        throw new UploadFileErrorException();
      }
    }
    return null;
  }
}
