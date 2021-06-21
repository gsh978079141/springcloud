package com.gsh.springcloud.starter.file.support.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectResult;
import com.gsh.springcloud.common.exception.DeleteFileErrorException;
import com.gsh.springcloud.common.exception.UploadFileErrorException;
import com.gsh.springcloud.starter.file.model.PostPolicy;
import com.gsh.springcloud.starter.file.properties.AliyunProperties;
import com.gsh.springcloud.starter.file.properties.AliyunPropertiesPolicy;
import com.gsh.springcloud.starter.file.support.FileAliyunService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author gsh
 */
@Service
@Slf4j
public class FileAliyunServiceImpl implements FileAliyunService {

  @Resource
  private OSSClient ossClient;

  @Resource
  private AliyunPropertiesPolicy aliyunPropertiesPolicy;

  @Resource
  private AliyunProperties aliyunProperties;

  private static final long MIN_DOCUMENT_SIZE = 0L;
  private static final long MAX_DOCUMENT_SIZE = 1048576000L;

  /**
   * 单个文件上传
   *
   * @param file 文件
   */
  @Override
  public PutObjectResult uploadFile(File file, String fileNamePrefix, String bucketName) {
    fileNamePrefix = StringUtils.isNotBlank(fileNamePrefix) ? fileNamePrefix : "";
    try {
      if (ossClient.doesBucketExist(bucketName)) {
        log.info("您已经创建Bucket：" + bucketName + "。");
      } else {
        log.info("您的Bucket不存在，创建Bucket：" + bucketName + "。");
        ossClient.createBucket(bucketName);
      }
      PutObjectResult putObjectResult = ossClient
              .putObject(bucketName, fileNamePrefix.concat(file.getName()), file);
      log.info("文件上传OSS成功");
      return putObjectResult;
    } catch (OSSException oe) {
      throw new UploadFileErrorException(oe);
    }
  }

  /**
   * 单个文件上传
   *
   * @param file     文件byte数组
   * @param fileName 文件名
   */
  @Override
  public PutObjectResult uploadFile(byte[] file, String fileName, String fileNamePrefix,
                                    String bucketName) {
    try {
      if (ossClient.doesBucketExist(bucketName)) {
        log.info("您已经创建Bucket：" + bucketName + "。");
      } else {
        log.info("您的Bucket不存在，创建Bucket：" + bucketName + "。");
        ossClient.createBucket(bucketName);
      }
      PutObjectResult putObjectResult = ossClient
              .putObject(bucketName, fileNamePrefix.concat(fileName), new ByteArrayInputStream(file));
      log.info("文件上传OSS成功");
      return putObjectResult;
    } catch (OSSException oe) {
      throw new UploadFileErrorException(oe);
    }
  }

  /**
   * 上传文件至OSS
   *
   * @param files 文件数组
   */
  @Override
  public void uploadFiles(List<File> files, String fileNamePrefix, String bucketName) {
    try {
      if (ossClient.doesBucketExist(bucketName)) {
        log.info("您已经创建Bucket：" + bucketName + "。");
      } else {
        log.info("您的Bucket不存在，创建Bucket：" + bucketName + "。");
        ossClient.createBucket(bucketName);
      }
      files.forEach(
              file -> ossClient.putObject(bucketName, fileNamePrefix.concat(file.getName()), file));
      log.info("文件上传OSS成功");
    } catch (OSSException oe) {
      throw new UploadFileErrorException(oe);
    }
  }


  @Override
  public void downloadFiles(String[] fileNames, String bucketName) {
    try {
      Arrays.stream(fileNames)
              .forEach(fileName -> ossClient
                      .getObject(new GetObjectRequest(bucketName, fileName), new File(fileName)));
    } catch (OSSException oe) {
      log.error("uploadFiles error", oe);
      throw new UploadFileErrorException(oe);
    }
  }


  @Override
  public void deleteFile(String objectName, String fileNamePrefix, String bucketName) {
    try {
      ossClient.deleteObject(bucketName, fileNamePrefix.concat(objectName));
    } catch (OSSException ex) {
      throw new DeleteFileErrorException(ex);
    }
  }

  @Override
  public String getFileUrl(String objName) {
    return this.getFileUrl(aliyunPropertiesPolicy.getBucket(), objName);
  }

  @Override
  public String getFileUrl(String bucket, String objName) {
    Date expiration = new Date(System.currentTimeMillis() + aliyunPropertiesPolicy.getExpires() * 1000L);
    URL url = ossClient.generatePresignedUrl(bucket, objName, expiration);
    return url.toString();
  }

  @Override
  public PostPolicy getPostPolicy() {
    return this.getPostPolicy(aliyunPropertiesPolicy.getBucket(), aliyunPropertiesPolicy.getDir(),
            aliyunPropertiesPolicy.getExpires());
  }

  @Override
  public PostPolicy getPostPolicy(String bucket, String dir, Integer expires) {
    log.info("start getPostPolicy");
    Date expiration = getExpireDate(expires);
    PolicyConditions policyCondition = buildPolicyConditions(dir);
    String postPolicy = ossClient.generatePostPolicy(expiration, policyCondition);
    byte[] binaryData = null;
    try {
      binaryData = postPolicy.getBytes("utf-8");
    } catch (UnsupportedEncodingException e) {
      log.error("start getPostPolicy error{}", e.getMessage());
    }

    String encodedPolicy = BinaryUtil.toBase64String(binaryData);
    String postSignature = ossClient.calculatePostSignature(postPolicy);
    return buildPostPolicy(aliyunProperties.getAccessKeyId(), encodedPolicy, postSignature, dir,
            aliyunProperties.getHost(),
            expiration.getTime());
  }

  private Date getExpireDate(Integer expires) {
    long expireEndTime = System.currentTimeMillis() + expires * 1000;
    return new Date(expireEndTime);
  }

  private PolicyConditions buildPolicyConditions(String dir) {
    PolicyConditions policyConds = new PolicyConditions();
    policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
    policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, MIN_DOCUMENT_SIZE, MAX_DOCUMENT_SIZE);
    return policyConds;
  }

  private PostPolicy buildPostPolicy(String accessid, String policy, String signature, String dir,
                                     String host, Long expire) {
    return PostPolicy.builder()
            .accessId(accessid)
            .dir(dir)
            .expire(expire.toString())
            .host(host)
            .policy(policy)
            .signature(signature)
            .build();
  }
}