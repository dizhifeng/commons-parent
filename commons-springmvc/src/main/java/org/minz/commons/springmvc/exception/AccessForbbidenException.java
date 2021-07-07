package org.minz.commons.springmvc.exception;

/**
 * 资源访问权限异常
 *
 * @author zhengmin
 * @created 2020-09-15
 */
public class AccessForbbidenException extends RuntimeException {

  public AccessForbbidenException() {
    super("没有资源访问权限");
  }

  public AccessForbbidenException(String message) {
    super(message);
  }
}
