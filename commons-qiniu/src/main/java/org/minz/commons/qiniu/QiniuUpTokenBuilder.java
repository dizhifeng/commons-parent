package org.minz.commons.qiniu;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * 七牛云存储上传token构建器
 *
 * @author zhengmin
 * @created 2020-09-24
 */
public class QiniuUpTokenBuilder {

  private Auth auth;

  public QiniuUpTokenBuilder(String accessKey, String secretKey) {
    this.auth = Auth.create(accessKey, secretKey);
  }

  public String getToken(String bucket) {
    return auth.uploadToken(bucket);
  }

  public String getToken(String bucket, String fileName) {
    return auth.uploadToken(bucket, fileName);
  }

  public String getToken(String bucket, long expireSeconds) {
    StringMap putPolicy = new StringMap();
    putPolicy.put("returnBody",
        "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
    return auth.uploadToken(bucket, null, expireSeconds, putPolicy);
  }

  public String getToken(String bucket, String fileName, long expireSeconds) {
    StringMap putPolicy = new StringMap();
    putPolicy.put("returnBody",
        "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
    return auth.uploadToken(bucket, fileName, expireSeconds, putPolicy);
  }

  public String getToken(String bucket, long expireSeconds, String mimeLimit) {
    StringMap putPolicy = new StringMap();
    putPolicy.put("mimeLimit", mimeLimit);
    putPolicy.put("returnBody",
        "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
    return auth.uploadToken(bucket, null, expireSeconds, putPolicy);
  }
}
