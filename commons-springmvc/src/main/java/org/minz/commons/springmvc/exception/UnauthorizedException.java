package org.minz.commons.springmvc.exception;

/**
 * 认证失败异常
 */
public class UnauthorizedException extends RuntimeException {

  public UnauthorizedException() {
    super("认证失败");
  }

  public UnauthorizedException(String message) {
    super(message);
  }
}
